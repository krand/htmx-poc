package sandbox.htmxpoc.web.cars;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import sandbox.htmxpoc.model.Manufacturer;
import sandbox.htmxpoc.model.Model;
import sandbox.htmxpoc.model.Vehicle;
import sandbox.htmxpoc.repository.ManufacturerRepository;
import sandbox.htmxpoc.repository.ModelRepository;
import sandbox.htmxpoc.repository.VehicleRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(CarsTabController.class)
class CarsTabControllerTest {

    @MockBean
    private ManufacturerRepository manufacturerRepository;
    @MockBean
    private ModelRepository modelRepository;
    @MockBean
    private VehicleRepository vehicleRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void saveCar() throws Exception {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("aManufacturer");
        Model model = new Model();
        model.setName("aModel");

        Vehicle persistedVehicle = new Vehicle();
        persistedVehicle.setId(10000);
        persistedVehicle.setManufacturer(manufacturer);
        persistedVehicle.setModel(model);

        Mockito.when(manufacturerRepository.getById(1)).thenReturn(manufacturer);
        Mockito.when(modelRepository.getById(2)).thenReturn(model);
        Mockito.when(vehicleRepository.save(ArgumentMatchers.any())).thenReturn(persistedVehicle);
        Mockito.when(vehicleRepository.getById(10000)).thenReturn(persistedVehicle);

        mockMvc.perform(post("/cars-tab")
                        .contentType(APPLICATION_FORM_URLENCODED) //from MediaType
                        .param("manufacturerId", "1")
                        .param("modelId", "2")
                        .param("mileage", "100")
                        .param("price", "5000"))
                .andDo(print());

        ArgumentCaptor<Vehicle> captor = ArgumentCaptor.forClass(Vehicle.class);
        Mockito.verify(vehicleRepository).save(captor.capture());

        Vehicle actualVehicle = captor.getValue();
        assertThat(actualVehicle.getMileage(), Matchers.equalTo(100));
    }
}