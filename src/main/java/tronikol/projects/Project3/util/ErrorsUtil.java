package tronikol.projects.Project3.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorsUtil {
    public static String mapErrors(BindingResult bindingResult) {
        StringBuilder allErrors = new StringBuilder();
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            allErrors.append(error.getField()).append(" : ")
                    .append(error.getDefaultMessage())
                    .append("; ");
        }

        return allErrors.toString();

    }
}