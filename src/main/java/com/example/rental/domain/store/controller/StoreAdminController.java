package com.example.rental.domain.store.controller;

import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import main.java.com.example.rental.domain.store.service.StoreAdminService;

@RestController
@RequiredArgsConstructor
public class StoreAdminController {

    public final StoreAdminService StoreAdminService;

}
