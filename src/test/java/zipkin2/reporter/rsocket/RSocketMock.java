package zipkin2.reporter.rsocket;

import io.rsocket.Payload;
import io.rsocket.RSocket;
import reactor.core.publisher.Mono;

/**
 * RSocket Mock
 *
 * @author linux_china
 */
public class RSocketMock implements RSocket {

    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        return Mono.empty();
    }
}
