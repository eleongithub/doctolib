package com.syscom.rest.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author el1638en
 * @since 14/06/17 16:51
 */
@RestController
public class HelloController {

	@RequestMapping("/")
	public String index(){
		return "Greetings from Spring Boot !!";
	}

}
