package com.example.cpme273.wlb.data.entity;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "cookie", schema = "wlb")
@Data
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cookie {

  @Id
  @Column(name = "cookie")
  private String cookie;

  @Column(name = "created")
  private Date created;

  @Column(name = "expire_duration")
  private Integer expireDuration;

  @Column(name = "user_id")
  private UUID userId;

  @Column(name = "role")
  private String role;

  @PrePersist
  public void onPrePersist() {
    setCreated(new Date());
  }
}
