package it.gssi.cs.rastapms.presentation;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {

    @ModelAttribute("currentUri")
    String getRequestServletPath(HttpServletRequest request) {
        return request.getRequestURI();
    }
}