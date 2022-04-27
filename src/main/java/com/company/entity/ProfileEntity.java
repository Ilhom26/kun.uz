package com.company.entity;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "profile")
public class ProfileEntity extends BaseEntity{
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private Boolean visible = true;

    @Column
    @Enumerated(EnumType.STRING)
    private ProfileRole role;// 0,1,2

    @Column
    @Enumerated(EnumType.STRING)
    private ProfileStatus status;

    @OneToOne
    @JoinColumn(name = "attach_id",nullable = true)
    private AttachEntity attach;

}
