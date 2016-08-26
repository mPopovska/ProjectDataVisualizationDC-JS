package mk.ukim.finki.wp.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;


public class CustomDateSerializer extends JsonSerializer<Date> {    
    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider arg2) throws 
        IOException, JsonProcessingException {      

        //SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy HH:mm:ss");
        String formattedDate = formatter.format(value);

        gen.writeString(formattedDate);

    }

	public CustomDateSerializer() {
		System.out.println("date constructor");
	}
    
    
}