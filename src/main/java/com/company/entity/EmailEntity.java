package com.company.entity;

import com.company.enums.EmailType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "email_history")
@Getter
@Setter
public class EmailEntity extends BaseEntity{
    @Column
    private String toEmail;
    @Column
    @Enumerated(EnumType.STRING)
    private EmailType type;

    @Column(name = "send_date")
    private LocalDateTime sendDate = LocalDateTime.now();
}
