package zipkin2.reporter.rsocket;

import org.junit.jupiter.api.Test;
import zipkin2.Call;
import zipkin2.Callback;
import zipkin2.Span;
import zipkin2.codec.SpanBytesEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RSocketSenderTest {
    private RSocketSender sender = new RSocketSender(new RSocketMock());

    @Test
    public void sendsSpans() throws Exception {
        List<byte[]> encodedSpans = spans().stream()
                .map(SpanBytesEncoder.PROTO3::encode)
                .collect(Collectors.toList());
        Call<Void> call = sender.sendSpans(encodedSpans);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onSuccess(Void value) {
                System.out.println("success");
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Error");
            }
        });
        call.execute();
    }

    public List<Span> spans() {
        Span spanRequester = Span.newBuilder().traceId(1, 1).id(1L).build();
        Span spanBroker = Span.newBuilder().traceId(1, 1).id(2L).parentId(1).build();
        Span spanResponder = Span.newBuilder().traceId(1, 1).id(3L).parentId(1).build();
        return Arrays.asList(spanRequester, spanBroker, spanResponder);
    }

}
