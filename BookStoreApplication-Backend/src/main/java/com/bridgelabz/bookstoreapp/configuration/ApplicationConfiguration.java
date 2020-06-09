package com.bridgelabz.bookstoreapp.configuration;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfiguration 
{
	private static MessageSourceAccessor messageSourceAccessor;
	
	 @PostConstruct
    private static void initMessageSourceAccessor() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:messages");
        messageSourceAccessor = new MessageSourceAccessor(messageSource, Locale.getDefault());
    }

    public static MessageSourceAccessor getMessageAccessor() {
        return messageSourceAccessor;
    }
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
}