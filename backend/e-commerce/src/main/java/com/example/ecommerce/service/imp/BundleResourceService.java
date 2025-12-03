package com.example.ecommerce.service.imp;

import com.example.ecommerce.helper.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import java.util.Locale;

@Component
public class BundleResourceService {
    private final MessageSource messageSource;


    @Autowired
    public BundleResourceService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    public String getMessage_ar(String key) {
        return messageSource.getMessage(key, null,  Locale.forLanguageTag("ar"));
    }

    public String getMessage_en(String key) {
        return messageSource.getMessage(key, null,  Locale.forLanguageTag("en"));

    }

    public MessageResponse getMessages(String key) {
        return new MessageResponse(
                getMessage_en(key),
                getMessage_ar(key)
        );
    }





}
