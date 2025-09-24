package com.example.math.controller;

import com.example.math.service.MathService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/math")
public class MathController {

    private final MathService math;

    public MathController(MathService math) {
        this.math = math;
    }

    @GetMapping("/add/{a}/{b}")
    public double add(@PathVariable double a, @PathVariable double b) {
        return math.add(a, b);
    }

    @GetMapping("/sub")
    public double sub(@RequestParam double a, @RequestParam double b) {
        return math.sub(a, b);
    }

    @PostMapping("/mul")
    public double mul(@RequestBody Input input) {
        return math.mul(input.a, input.b);
    }

    @PostMapping("/div")
    public double div(@RequestBody Input input) {
        return math.div(input.a, input.b);
    }

    // piccola classe interna per ricevere il JSON {"a":10,"b":5}
    static class Input {
        public double a;
        public double b;
    }
}
