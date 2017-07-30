package com.syscom.rest.error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Eric Legba on 29/07/17.
 */
@RestController
@RequestMapping(value = "/error", produces = MediaType.APPLICATION_JSON_VALUE)
public class DbErrorController implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping
    public Map<String, Object> error(HttpServletRequest request) {
        RequestAttributes requestAttributes = new ServletRequestAttributes(request);
        return errorAttributes.getErrorAttributes(requestAttributes, false);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
