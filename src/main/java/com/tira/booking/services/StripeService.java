package com.tira.booking.services;

import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService extends BaseService {

    @Value("${STRIPE_SECRET_KEY}")
    private String secretKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = secretKey;
    }

    public Charge processStripePayment(String token) throws CardException,
            APIException, AuthenticationException,
            InvalidRequestException, APIConnectionException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", 2000);
        chargeParams.put("currency", "usd");
        chargeParams.put("description", "User paid for a room");
        chargeParams.put("source",token);
        return Charge.create(chargeParams);
    }
}
