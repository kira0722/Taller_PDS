package co.com.poli.booking.clientFeign;

import co.com.poli.booking.helper.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "movies", fallback = MovieClientImplHystrixFallBack.class)
public interface MovieClient {
    @GetMapping("/api/v1/movies/{id}")
    Response findById(@PathVariable("id")Long id);
}
