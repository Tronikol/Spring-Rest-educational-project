package tronikol.projects.Project3.dto;

public class RainyDaysCountDTO {
    private Long numberOfRainyDays;

    public Long getNumberOfRainyDays() {
        return numberOfRainyDays;
    }

    public void setNumberOfRainyDays(Long numberOfRainyDays) {
        this.numberOfRainyDays = numberOfRainyDays;
    }

    public RainyDaysCountDTO(Long numberOfRainyDays) {
        this.numberOfRainyDays = numberOfRainyDays;
    }
}
