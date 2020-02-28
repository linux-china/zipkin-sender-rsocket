package zipkin2.reporter.rsocket;

import io.rsocket.AbstractRSocket;
import io.rsocket.Payload;
import reactor.core.publisher.Mono;

/**
 * RSocket Mock
 *
 * @author linux_china
 */
public class RSocketMock extends AbstractRSocket {

    @Override
    public Mono<Void> fireAndForget(Payload payload) {
        return Mono.empty();
    }
}
