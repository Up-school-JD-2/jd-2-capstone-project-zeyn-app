package io.upschool.controller;

import io.upschool.dto.BaseResponse;
import io.upschool.dto.routeDto.RouteRequest;
import io.upschool.dto.routeDto.RouteResponse;
import io.upschool.service.RouteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/routes")
@RequiredArgsConstructor
public class RouteController {
    private final RouteService routeService;

    @GetMapping
    public ResponseEntity<BaseResponse<List<RouteResponse>>> getAllRoutes() {
        List<RouteResponse> routes = routeService.getAllRoutes();
        BaseResponse<List<RouteResponse>> response = BaseResponse.<List<RouteResponse>>builder()
                .status(HttpStatus.OK.value())
                .data(routes)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BaseResponse<RouteResponse>> createRoute(@Valid @RequestBody RouteRequest routeRequest){
        RouteResponse routeResponse = routeService.createRoute(routeRequest);
        BaseResponse<RouteResponse> response = BaseResponse.<RouteResponse>builder()
                .status(HttpStatus.CREATED.value())
                .data(routeResponse)
                .isSuccess(true)
                .build();
        return ResponseEntity.ok(response);
    }
}
