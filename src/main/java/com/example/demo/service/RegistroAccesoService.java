package com.example.demo.service;

import com.example.demo.dto.RegistroAccesoRequest;
import com.example.demo.dto.RegistroAccesoResponse;

public interface RegistroAccesoService {
    RegistroAccesoResponse registrarAcceso(RegistroAccesoRequest request);
}
