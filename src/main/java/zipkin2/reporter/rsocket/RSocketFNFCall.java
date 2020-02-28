package zipkin2.reporter.rsocket;

import io.rsocket.RSocket;
import io.rsocket.util.DefaultPayload;
import reactor.core.publisher.Flux;
import zipkin2.Call;
import zipkin2.Callback;

import java.io.IOException;
import java.util.List;

/**
 * RSocket Fire and Forget call for Zipkin
 *
 * @author linux_china
 */
public class RSocketFNFCall extends Call<Void> {
    private RSocket rsocket;
    private List<byte[]> encodedSpans;
    private Callback<Void> callback;

    public RSocketFNFCall(RSocket rsocket, List<byte[]> encodedSpans) {

        this.rsocket = rsocket;
        this.encodedSpans = encodedSpans;
    }

    @Override
    public Void execute() throws IOException {
        return Flux.fromIterable(encodedSpans)
                .map(DefaultPayload::create)
                .flatMap(payload -> rsocket.fireAndForget(payload))
                .then()
                .doOnError(error -> {
                    if (callback != null) {
                        callback.onError(error);
                    }
                })
                .doOnSuccess(aVoid -> {
                    if (callback != null) {
                        callback.onSuccess(aVoid);
                    }
                })
                .block();
    }

    @Override
    public void enqueue(Callback<Void> callback) {
        this.callback = callback;
    }

    @Override
    public void cancel() {

    }

    @Override
    public boolean isCanceled() {
        return false;
    }

    @Override
    public Call<Void> clone() {
        RSocketFNFCall fnfCall = new RSocketFNFCall(this.rsocket, this.encodedSpans);
        if (this.callback != null) {
            fnfCall.callback = this.callback;
        }
        return fnfCall;
    }
}
