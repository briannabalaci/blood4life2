package validator;

import java.time.LocalDate;

public class UserValidator {

    public boolean validateName(String name){
        String pattern = "^[A-Z]([A-Za-z- ]+)$";
        return name.matches(pattern);
    }

    public boolean validateEmail(String email){
        String pattern = "^([a-z.0-9_]+)@([a-z]+)[.]([a-z]{2,4})$";
        return email.matches(pattern);
    }

    public boolean validateCNP(String cnp){
        String pattern = "^[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)(00[1-9]|0[1-9]\\d|[1-9]\\d\\d)\\d$";
        return cnp.matches(pattern);
    }

    public boolean validateHeight(Integer height){
        return (height > 0 && height < 300);
    }

    public boolean validateWeight(Double weight){
        return (weight > 0.0 && weight < 700.0);
    }

    public boolean validateBirthDateCNP(LocalDate date, String cnp){
        int year = Integer.parseInt(cnp.substring(1, 3));
        int yearFromCNP;
        if(year >= 0 && year <= 9)
            yearFromCNP = Integer.parseInt("200" + year);
        else
        if(year >= 10 && year <= 22)
            yearFromCNP = Integer.parseInt("20" + year);
        else
            yearFromCNP = Integer.parseInt("19" + year);
        int monthFromCNP = Integer.parseInt(cnp.substring(3, 5));
        int dayFromCNP = Integer.parseInt(cnp.substring(5, 7));
        int yearFromDate = date.getYear();
        int monthFromDate = date.getMonthValue();
        int dayFromDate = date.getDayOfMonth();

        if(yearFromCNP != yearFromDate)
            return false;
        if(monthFromCNP != monthFromDate)
            return false;
        if(dayFromCNP != dayFromDate)
            return false;
        return true;
    }
}