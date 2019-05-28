package client;

import agh.dfbazan.communication.AddMeasurementInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Date;

//To tylko demo

public class TestObjectMaper {
    
    public static void main(String args[]) 
   {
       try {

            AddMeasurementInfo measurementInfo = new AddMeasurementInfo("Ala","haslo","2.3",new Date(),"zegarek");
                                    
            ObjectMapper mapper = new ObjectMapper();
            String input = mapper.writeValueAsString(measurementInfo);
                       
            
            ObjectMapper objectMapper = new ObjectMapper();
            AddMeasurementInfo m = objectMapper.readValue(input, AddMeasurementInfo.class);
            
            System.out.println(m.getUserName()+" "+m.getPassword()+" "+m.getMeasValue()+" "+m.getDeviceName()+"  "+m.getMeasDate().toString());
    
    
       } 
       catch (Exception e) 
       {
           e.printStackTrace();           
       }

   }
    
            
    
    
}
