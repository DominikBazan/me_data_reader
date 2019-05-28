package client;

import java.util.*;
import java.text.*;
  
//To tylko demo

public class DateDemo {

   public static void main(String args[]) 
   {
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
