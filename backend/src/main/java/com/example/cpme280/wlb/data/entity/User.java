package com.example.cpme273.wlb.data.entity;

import com.example.cpme273.wlb.dto.Role;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "user", schema = "wlb")
@Data
@Slf4j
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", unique = true)
  private UUID id;

  @Column(name = "username", unique = true)
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "email")
  private String email;

  @Column(name = "role")
  @Enumerated(EnumType.STRING)
  private Role role;

  @Column(name = "legal_name")
  private String legalName;

  @Column(name = "phone")
  private String phone;

  @Column(name = "birthday")
  private String birthday;

  @Column(name = "ssn")
  private String ssn;

  @Column(name = "credit_score")
  private Integer creditScore;

  @Column(name = "created", nullable = false, updatable = false)
  private Date created;

  @Column(name = "updated")
  private Date updated;

  @Column(name = "is_active")
  private Boolean isActive;

  @PreUpdate
  public void onPreUpdate() {
    setUpdated(new Date());
  }

  @PrePersist
  public void onPrePersist() {
    if (Objects.isNull(id)) {
      id = UUID.randomUUID();
      if(role == Role.ADMIN){
        isActive = true;
      } else {
        isActive = false;
      }
    }

    setCreated(new Date());
  }
}
