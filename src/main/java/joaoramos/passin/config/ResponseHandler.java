package joaoramos.passin.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ResponseHandler {
    public static ResponseEntity<Object> generateResponse(Object responseObj, HttpStatus status, Integer total)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", total);
        map.put("status", status.value());
        map.put("data", responseObj);

        return new ResponseEntity<Object>(map, status);
    }
}
