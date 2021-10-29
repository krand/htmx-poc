package sandbox.htmxpoc.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Manufacturer manufacturer;

    @ManyToOne
    private Model model;

    @Column
    private String color;

    @Column
    private Integer manufactureYear;

    @Column
    private Integer mileage;

    @Column
    private BigDecimal price;

    private String description;
}
