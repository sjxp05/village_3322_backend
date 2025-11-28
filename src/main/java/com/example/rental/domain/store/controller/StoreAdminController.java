package com.example.rental.domain.store.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.rental.domain.store.service.StoreService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StoreAdminController {

    public final StoreService storeService;

}
