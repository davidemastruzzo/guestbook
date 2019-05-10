package ch.bbw.guestbook.service;

import ch.bbw.guestbook.exception.GuestbookRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CaptchaService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    final private String RECAPTCHA_SECRET_KEY = "6Lc37woUAAAAAMwb9Nc8_FNruC9TLXqZIAy8C76-";
    final private String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    public boolean isValid(String recaptchaResponse) {
        Map<String, String> body = new HashMap<>();
        body.put("secret", RECAPTCHA_SECRET_KEY);
        body.put("response", recaptchaResponse);
        ResponseEntity<Map> recaptchaResponseEntity =
                restTemplateBuilder.build()
                        .postForEntity(RECAPTCHA_VERIFY_URL +
                                        "?secret={secret}&response={response}",
                                body, Map.class, body);
        Map responseBody =
                recaptchaResponseEntity.getBody();

        return (Boolean) responseBody.get("success");
    }

    public void processResponse(String recaptchaResponse) {
        if (!isValid(recaptchaResponse)){
            throw new GuestbookRuntimeException();
        }
    }
}
