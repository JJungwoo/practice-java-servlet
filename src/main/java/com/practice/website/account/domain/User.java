package com.practice.website.account.domain;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    private Long nid;
    private String name;
    private String password;
    private String email;
}
