package com.cisco.pmtpf.server;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {MultipartAutoConfiguration.class })
//@EnableMongoRepositories("com.cisco.pmtpf.repository")
@ComponentScan(basePackages = { "com.cisco.*" })
public class PmtpfApiServerApplication implements WebApplicationInitializer{

	public static void main(String[] args) {
		SpringApplication.run(PmtpfApiServerApplication.class, args);
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(PmtpfViewAdapter.class);
		ctx.setServletContext(servletContext);
		ServletRegistration.Dynamic servlet = servletContext.addServlet("dispatcher", new DispatcherServlet(ctx));
		servlet.setLoadOnStartup(1);
		servlet.addMapping(new String[] {"/","*.jsx"});
	}
	
}
