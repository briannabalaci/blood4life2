package protocol;

import domain.enums.BloodType;
import domain.enums.Rh;

public class FindCompatiblePatientsRequest implements Request {
    private final BloodType bloodType;
    private final Rh rh;

    public FindCompatiblePatientsRequest(BloodType bloodType, Rh rh) {
        this.bloodType = bloodType;
        this.rh = rh;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    public Rh getRh() {
        return rh;
    }
}
