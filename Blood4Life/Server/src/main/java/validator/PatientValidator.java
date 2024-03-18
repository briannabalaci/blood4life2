package validator;

import domain.Patient;
import exception.ValidationException;

public class PatientValidator {
    private String errors;

    public PatientValidator() {
        errors = "";
    }

    private String validateCNP(String cnp) {
        if (cnp.isEmpty())
            return "\nCNP must not be empty";
        if (cnp.length() != 13)
            return "\nCNP must have 13 digits";
        if (!cnp.matches("[0-9]+"))
            return "\nCNP must contain digits only";
        return "";
    }

    private String validateNames(String name, String identifier) {
        if (name.isEmpty())
            return "\n" + identifier + " must not be empty";
        if (name.length() == 1)
            return "\n" + identifier + " must have more than 1 letter";
        if (name.charAt(0) < 'A' || name.charAt(0) > 'Z')
            return "\n" + identifier + " must begin with capital letter";
        if (!name.substring(1).matches("[a-z]+"))
            return "\n" + identifier + " must contain letters only";
        return "";
    }

    public void validatePatient(Patient patient) {
        errors = "";
        errors = errors.concat(validateCNP(patient.getCnp()));
        errors = errors.concat(validateNames(patient.getFirstName(), "First name"));
        errors = errors.concat(validateNames(patient.getLastName(), "Last name"));
        if (!errors.isEmpty())
            throw new ValidationException(errors.substring(1));
    }
}
