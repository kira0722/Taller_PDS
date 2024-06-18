package co.com.poli.booking.clientFeign;


import co.com.poli.booking.helper.Response;
import co.com.poli.booking.helper.ResponseBuild;
import co.com.poli.booking.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MovieClientImplHystrixFallBack implements MovieClient{
    private final ResponseBuild responseBuild;


    @Override
    public Response findById(Long id) {
        return this.responseBuild.failedServerUnavailable(new Movie());
    }
}
