package protocol;

import domain.Patient;

import java.util.List;

public class FindAllPatientsResponse implements Response{
    private final List<Patient> patients;

    public FindAllPatientsResponse(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Patient> getPatients() {
        return patients;
    }
}
