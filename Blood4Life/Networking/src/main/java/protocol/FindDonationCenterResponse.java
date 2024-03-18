package protocol;

import domain.DonationCentre;

import java.util.List;

public class FindDonationCenterResponse implements Response{

    List<DonationCentre> donationCentres;

    public FindDonationCenterResponse(List<DonationCentre> donationCentres) {
        this.donationCentres = donationCentres;
    }

    public List<DonationCentre> getDonationCentres() {
        return donationCentres;
    }
}
