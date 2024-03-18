package protocol;

import domain.Patient;

import java.util.List;

public class FindCompatiblePatientsResponse implements Response {
    private final List<Patient> patients;

    public FindCompatiblePatientsResponse(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Patient> getPatients() {
        return patients;
    }
}
