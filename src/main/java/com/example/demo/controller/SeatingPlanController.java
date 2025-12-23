// package com.example.demo.controller;

// import com.example.demo.model.SeatingPlan;
// import com.example.demo.service.SeatingPlanService;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/plans")
// public class SeatingPlanController {

//     private final SeatingPlanService service;

//     public SeatingPlanController(SeatingPlanService service) {
//         this.service = service;
//     }

//     @PostMapping("/generate/{sessionId}")
//     public SeatingPlan generate(@PathVariable Long sessionId) {
//         return service.generate(sessionId);
//     }
// }
