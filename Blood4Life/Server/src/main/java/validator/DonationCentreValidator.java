package validator;

import domain.DonationCentre;
import exception.ValidationException;

import java.util.Arrays;

public class DonationCentreValidator {
    private String errors;
    private final AddressValidator addressValidator;

    public DonationCentreValidator(AddressValidator addressValidator) {
        this.addressValidator = addressValidator;
        errors = "";
    }

    private String validateName(String name) {
        if (name.isEmpty())
            return "\nName must not be empty";
        if (name.charAt(0) < 'A' || name.charAt(0) > 'Z')
            return "\nName must begin with capital letter";
        String[] words = name.split(" ");
        boolean validator = Arrays.stream(words).allMatch(s -> s.substring(1).matches("[a-z]+"));
        if (!validator)
            return "\nWords in name must contain letters only";
        validator = Arrays.stream(words).allMatch(s -> s.matches("[a-zA-Z]+"));
        if (!validator)
            return "\nWords in name must contain letters only";
        return "";
    }

    public void validateDonationCentre(DonationCentre donationCentre) {
        errors = "";
        errors = errors.concat(validateName(donationCentre.getName()));
        try {
            addressValidator.validateAddress(donationCentre.getAddress());
        } catch (ValidationException validationException) {
            errors = errors.concat(validationException.getMessage());
        }
        if (!errors.isEmpty())
            throw new ValidationException(errors.substring(1));
    }
}
