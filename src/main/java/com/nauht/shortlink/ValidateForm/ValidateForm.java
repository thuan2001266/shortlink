package com.nauht.shortlink.ValidateForm;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidateForm {
    private static final String GOOGLE_RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    public static ResponseEntity<Map<String, String>> validate(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                String errorMessage = fieldError.getDefaultMessage();
                errors.put(fieldError.getField(), errorMessage);
            }
            return new ResponseEntity<Map<String, String>>(errors, HttpStatus.NOT_ACCEPTABLE);
        }
        return null;
    }

    public static ResponseEntity<Map<String, String>> captcha (String captcha){
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            String url = GOOGLE_RECAPTCHA_VERIFY_URL + "?secret=6LetkVonAAAAAIcANDYT43_A8AOE-pKF1wWCzUQr" + "&response=" + captcha;
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity("", ContentType.APPLICATION_FORM_URLENCODED));
            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    try (InputStream inputStream = entity.getContent()) {
                        String jsonString = new String(inputStream.readAllBytes());
                        JSONObject jsonObject = new JSONObject(jsonString);
                        boolean success = jsonObject.getBoolean("success");
                        if (success) {
                            System.out.println("reCAPTCHA verification successful.");
                        } else {
                            System.out.println("reCAPTCHA verification failed.");
                            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return null;
    }
}
