package id.co.javan.keboot.core.form;

import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidationErrorFormatter {

    public static Map errorsAsMap(List<FieldError> errors) {
        Map<String, List<String>> result = new HashMap();
        for (FieldError err : errors) {
            if (!result.containsKey(err.getField())) {
                result.put(err.getField(), new ArrayList<>());
            }
            result.get(err.getField()).add(err.getDefaultMessage());
        }
        return result;
    }
}
