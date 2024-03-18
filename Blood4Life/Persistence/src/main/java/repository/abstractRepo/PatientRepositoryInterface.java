package repository.abstractRepo;

import domain.Patient;
import domain.enums.BloodType;
import domain.enums.Rh;
import domain.enums.Severity;

import java.util.List;

public interface PatientRepositoryInterface extends RepositoryInterface<Long, Patient> {
    Patient findPatientsByQuantity(Integer quantity);
    Patient findPatientsBySeverity(Severity severity);
    List<Patient> findPatientsByBloodTypeAndRh(BloodType bloodType, Rh rh);
}
