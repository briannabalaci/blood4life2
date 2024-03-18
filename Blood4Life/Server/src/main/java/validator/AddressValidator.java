package validator;

import domain.Address;
import exception.ValidationException;

import java.util.Arrays;

public class AddressValidator {
    private String errors;

    public AddressValidator() {
        errors = "";
    }

    private String validateLocation(String location, String identifier) {
        if (location.isEmpty())
            return "\n" + identifier + " must not be empty";
        if (location.charAt(0) < 'A' || location.charAt(0) > 'Z')
            return "\n" + identifier + " must begin with capital letter";
        String[] words = location.split(" ");
        for (String word : words) {
            String[] item = word.split("-");
            boolean validator = Arrays.stream(item).allMatch(s -> s.substring(1).matches("[a-z]+"));
            if (!validator)
                return "\n" + identifier + " must contain only letters ad delimiters (- or space)";
            validator = Arrays.stream(item).allMatch(s -> s.substring(1).matches("[a-zA-Z]+"));
            if (!validator)
                return "\n" + identifier + " must contain only letters ad delimiters (- or space)";
        }
        return "";
    }

    public void validateAddress(Address address) {
        errors = "";
        errors = errors.concat(validateLocation(address.getCounty(), "County"));
        errors = errors.concat(validateLocation(address.getCity(), "City"));
        errors = errors.concat(validateLocation(address.getStreet(), "Street"));
        if (!errors.isEmpty())
            throw new ValidationException(errors);
    }
}
