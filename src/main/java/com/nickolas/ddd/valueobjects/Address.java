package com.nickolas.ddd.valueobjects;

import java.util.regex.Pattern;

public final class Address {
    private final String country;
    private final String city;
    private final String street;
    private final String postalCode;

    public Address(String country, String city, String street, String postalCode) {
        if (country == null || city == null || street == null || postalCode == null) {
            throw new IllegalArgumentException("All fields must be non-null");
        }
        this.country = country;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;

        if (!isPostalCodeValid(postalCode)) {
            throw new IllegalArgumentException("Invalid postal code format");
        }
    }

    private boolean isPostalCodeValid(String postalCode) {
        switch (country.toLowerCase()) {
            case "usa":
                return Pattern.matches("\\d{5}(-\\d{4})?", postalCode);
            case "uk":
                return Pattern.matches("[A-Z]{1,2}\\d[A-Z\\d]? \\d[A-Z]{2}", postalCode);
            case "canada":
                return Pattern.matches("[A-Z]\\d[A-Z] \\d[A-Z]\\d", postalCode);
            default:
                return Pattern.matches("\\d{5}", postalCode);
        }
    }

    public String toDisplayFormat() {
        return street + ", " + city + ", " + country + ", " + postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getStreet() {
        return street;
    }

    public String getPostalCode() {
        return postalCode;
    }
}
