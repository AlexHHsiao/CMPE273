package com.example.cpme273.wlb.data;

import com.example.cpme273.wlb.data.entity.Cookie;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookieRepository extends JpaRepository<Cookie, String> {

  Optional<Cookie> findByCookie(String cookie);
}
