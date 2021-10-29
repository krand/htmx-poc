package sandbox.htmxpoc.web.cars.dto;

import java.math.BigDecimal;

public record CarDTO(Integer id, String manufacturer, String model, Integer mileage, BigDecimal price) {
}
