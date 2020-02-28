package zipkin2.reporter.rsocket;

import io.rsocket.RSocket;
import zipkin2.Call;
import zipkin2.codec.Encoding;
import zipkin2.reporter.Sender;

import java.util.List;

/**
 * Zipkin RSocket Sender
 *
 * @author linux_china
 */
public class RSocketSender extends Sender {
    private RSocket rsocket;
    int messageMaxBytes = 500_000;
    final Encoding encoding = Encoding.PROTO3;

    public RSocketSender(RSocket rsocket) {
        this.rsocket = rsocket;
    }

    @Override
    public Encoding encoding() {
        return encoding;
    }

    @Override
    public int messageMaxBytes() {
        return messageMaxBytes;
    }

    @Override
    public int messageSizeInBytes(List<byte[]> encodedSpans) {
        return encoding.listSizeInBytes(encodedSpans);
    }

    @Override
    public Call<Void> sendSpans(List<byte[]> encodedSpans) {
        return new RSocketFNFCall(this.rsocket, encodedSpans);
    }
}
