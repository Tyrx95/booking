package com.tira.booking.controllers;


import com.stripe.model.StripeObject;
import com.tira.booking.services.StripeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StripeController extends BaseController {

    @Autowired
    private StripeService service;


    @RequestMapping(value = "/api/v1/processStripePayment",
            method = RequestMethod.POST, produces="application/json")
    public ResponseEntity processStripePayment(@RequestBody String token) {
        return wrapForUser(() -> this.service.processStripePayment(token));
    }

}
