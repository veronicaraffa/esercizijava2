package com.dsbd.project.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull(message = "Origin cannot be blank")
    private String origin;

    @NotNull(message = "Destination cannot be blank")
    private String destination;

    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;

    @NotNull(message = "Price cannot be null")
    private BigDecimal price;

    // Getters and Setters
    public Integer getId() { return id; }
    public Trip setId(Integer id) { this.id = id; return this; }

    public String getOrigin() { return origin; }
    public Trip setOrigin(String origin) { this.origin = origin; return this; }

    public String getDestination() { return destination; }
    public Trip setDestination(String destination) { this.destination = destination; return this; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public Trip setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; return this; }

    public BigDecimal getPrice() { return price; }
    public Trip setPrice(BigDecimal price) { this.price = price; return this; }
}
