package repository.abstractRepo;

import domain.Address;
import domain.DonationCentre;

import java.sql.Time;
import java.util.List;

public interface DonationCentreRepositoryInterface extends RepositoryInterface<Long, DonationCentre> {
    List<DonationCentre> findDonationCentresByCapacity(int capacity);
    List<DonationCentre> findDonationCentresByProgram(Time openHour, Time closeHour);
    DonationCentre findDonationCentreByAddress(Address address);
}
