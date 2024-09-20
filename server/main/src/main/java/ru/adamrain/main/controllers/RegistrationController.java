package ru.adamrain.main.controllers;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.adamrain.main.entitys.User;
import ru.adamrain.main.services.UserService;

@RestController
@RequestMapping("/api/register")
public class RegistrationController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> register(@Valid @RequestBody User user) throws InterruptedException {
        Thread.sleep(1000);
        try {
            String phoneNumber = isPhoneValid(user.getTel());
            if (phoneNumber == null) {
                return ResponseEntity.badRequest().body("Некорректный номер телефона");
            }
            user.setTel(phoneNumber);
            return ResponseEntity.ok(userService.registerUser(user));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String isPhoneValid(String tel) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber phoneNumber = phoneUtil.parse(tel, "RU");
            if (phoneUtil.isValidNumber(phoneNumber)) {
                return phoneUtil.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.E164);
            }
        } catch (NumberParseException e) {
            System.err.println("Номер телефона не может быть распознан: " + e.getMessage());
        }
        return null;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        StringBuilder errorMessage = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.append(error.getDefaultMessage()).append("; ");
        });
        return ResponseEntity.badRequest().body(errorMessage.toString());
    }
}
