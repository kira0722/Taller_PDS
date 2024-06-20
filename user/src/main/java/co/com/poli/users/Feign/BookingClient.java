package co.com.poli.users.Feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "booking")
public interface BookingClient {
    @GetMapping("api/v1/poli/bookings/existsByUserId")
    Boolean existsByUserId(@RequestParam("userId") Long userId);
}
