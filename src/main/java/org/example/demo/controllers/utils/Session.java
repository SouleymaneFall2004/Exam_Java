package org.example.demo.controllers.utils;

import lombok.Getter;
import lombok.Setter;
import org.example.demo.data.entities.Account;

public class Session {
    @Getter
    @Setter
    private static Account account;
}