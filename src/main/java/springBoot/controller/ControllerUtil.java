package springBoot.controller;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by Vladimir on 01.06.2020.
 */
class ControllerUtil {
    static Map<String, String> getErrors(BindingResult bindingResult) {
//        Map<String, String> errors = new HashMap<>();
//        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//        for (FieldError error : fieldErrors) {
//            errors.put(error.getField() + "Error", error.getDefaultMessage());
//        }
//
//        return errors;
//
//        the same with Stream API
        return bindingResult.getFieldErrors().stream().collect(Collectors.toMap(
                error -> error.getField() + "Error", FieldError::getDefaultMessage));
    }
}
