package sandbox.htmxpoc.web.cars.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EditCarDTO {
    private Integer id;
    private Integer manufacturerId;
    private Integer modelId;
    private Integer mileage;
    private BigDecimal price;
}
