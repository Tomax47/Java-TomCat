package org.bathat.models;

// MINIMAL HW

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@EqualsAndHashCode
public class User {

    private long id;
    private String name;
    private String surname;
    private int age;
    private String email;
    private String password;

}
