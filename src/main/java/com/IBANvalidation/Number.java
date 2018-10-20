package com.IBANvalidation;

public class Number {

    final private String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    final private int LT = 20;

    private String number;

    public Number(String number){
        this.number = number;
    }

    public Boolean validateNumber(){

        try {
            //check country code
            if (!"LT".equals(number.substring(0, 2))){
                throw new IllegalArgumentException("Country code is incorrect.");
            }
            //check length
            if (number.length() != LT){
                throw new IllegalArgumentException("IBANnumber length is incorrect.");
            }
            Boolean validation = getIbanMod(replaceLetters(moveLetters()));
            if (validation){
                return true;
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        return false;
    }

    private Boolean getIbanMod(String iban){
        System.out.println(iban);
        int length = iban.length();

        int i = 0;
        int mod = 0;
        while (length >= 7){
            if (i == 0){
                System.out.println(i + " " + iban.substring(0, 9));
                mod = Integer.parseInt(iban.substring(0, 9))%97;
                i++;
                length -= 9;
            }
            else {
                int start = (i*7) + 2;
                String newIban = Integer.toString(mod) + iban.substring(start, start + 7);
                System.out.println(i + " " + newIban);
                mod = Integer.parseInt(newIban)%97;
                i++;
                length -= 7;
            }
        }

        if (length > 0){
            System.out.println(i + " " + mod + iban.substring(i*7 + 2, i*7 + 2 + length));
            String newIban = Integer.toString(mod) + iban.substring(i*7 + 2, i*7 + 2 + length);
            mod = Integer.parseInt(newIban)%97;
            System.out.println("mod: " + mod);
        }

        if (mod == 1){
            return true;
        }
        return false;
    }

    private String moveLetters(){
        int length = number.length();

        return number.substring(4, length) + number.substring(0, 4);
    }

    private String replaceLetters(String iban){
        String ibanNumber = "";

        for (int i = 0; i < iban.length(); i++){
            if (Character.isLetter(iban.charAt(i))){
                String charToNumber = Integer.toString(iban.indexOf(Character.toUpperCase(iban.charAt(i))) + 11);
                ibanNumber += charToNumber;
            }
            else {
                ibanNumber += iban.charAt(i);
            }
        }

        return ibanNumber;
    }
}
