package agh.dfbazan.measurements_3_0.client;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDemo {

    public static void main(String args[]) {
        try {

            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
            String dateInString = "07/05/13 23:12:33";
            Date convertedDate = formatter.parse(dateInString);
            System.out.println(convertedDate);

        } catch (ParseException e) {
            System.out.println("Unparseable using ");
        }

    }
}