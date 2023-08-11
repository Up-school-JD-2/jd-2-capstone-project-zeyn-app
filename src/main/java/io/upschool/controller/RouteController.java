package io.upschool.controller;

import io.upschool.dto.routeDto.RouteRequest;
import io.upschool.dto.routeDto.RouteResponse;
import io.upschool.service.RouteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/routes")
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;

    @GetMapping
    public ResponseEntity<List<RouteResponse>> getAllRoutes() {
        return ResponseEntity.ok(routeService.getAllRoutes());
    }

    @PostMapping
    public ResponseEntity<RouteResponse> createRoute(@Valid @RequestBody RouteRequest routeRequest) throws Exception {
        return ResponseEntity.ok(routeService.createRoute(routeRequest));
    }
}
