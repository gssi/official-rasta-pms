package it.gssi.cs.rastapms.presentation;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice({"it.gssi.cs.rastapms.presentation.backoffice", "it.gssi.cs.rastapms.presentation.frontoffice"})
public class GlobalExceptionHandler {


	@ExceptionHandler(Exception.class)
	public String handleException(HttpServletRequest request, Exception ex, Model model) {
		//log.info("Exception Occured:: URL=" + request.getRequestURL() + ", method=" + request.getMethod());
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		ex.printStackTrace(printWriter);
		printWriter.flush();

		String message = (ex.getCause() == null) ? "" : ex.getCause().getMessage();
		model.addAttribute("errorCause", message);
		model.addAttribute("errorMessage", stringWriter.toString());
		return "/backoffice/common/error";
	}

}
