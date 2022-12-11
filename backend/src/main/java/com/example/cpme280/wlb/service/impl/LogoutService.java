package com.example.cpme273.wlb.service.impl;

import com.example.cpme273.wlb.data.CookieRepository;
import com.example.cpme273.wlb.data.entity.Cookie;
import com.example.cpme273.wlb.exception.SessionNotFoundException;
import com.example.cpme273.wlb.service.ILogoutService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@Transactional
public class LogoutService implements ILogoutService {

    @Autowired
    private CookieRepository cookieRepository;

    @Override
    public void logout(String cookieId) {
        Optional<Cookie> cookieOptional = cookieRepository.findByCookie(cookieId);
        if (cookieOptional.isPresent()){
            cookieRepository.delete(cookieOptional.get());
        } else {
            throw new SessionNotFoundException();
        }
    }
}
