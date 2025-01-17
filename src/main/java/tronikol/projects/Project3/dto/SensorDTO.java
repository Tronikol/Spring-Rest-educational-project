package tronikol.projects.Project3.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SensorDTO {
    @Size(min = 3, max = 20, message = "Name should be from 3 to 20 characters")
    @NotNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
