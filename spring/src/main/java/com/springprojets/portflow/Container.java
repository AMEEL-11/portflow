package com.springprojets.portflow;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "containers")
public class Container {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Container number is required")
    private String containerNumber;

    @NotNull(message = "Type is required")
    @Enumerated(EnumType.STRING)
    private ContainerType type;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private ContainerStatus status;

    private Double weight;
    private String content;
    private String destination;
    private String origin;

    @ManyToOne
    @JoinColumn(name = "storage_zone_id")
    private StorageZone storageZone;

    @ManyToOne
    @JoinColumn(name = "escale_id")
    private Escale escale;

    private LocalDateTime arrivalDate;
    private LocalDateTime departureDate;

    @OneToMany(mappedBy = "container", cascade = CascadeType.ALL)
    private List<ContainerMovement> movements;

    // Constructors
    public Container() {}

    public Container(String containerNumber, ContainerType type, ContainerStatus status) {
        this.containerNumber = containerNumber;
        this.type = type;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContainerNumber() {
        return containerNumber;
    }

    public void setContainerNumber(String containerNumber) {
        this.containerNumber = containerNumber;
    }

    public ContainerType getType() {
        return type;
    }

    public void setType(ContainerType type) {
        this.type = type;
    }

    public ContainerStatus getStatus() {
        return status;
    }

    public void setStatus(ContainerStatus status) {
        this.status = status;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public StorageZone getStorageZone() {
        return storageZone;
    }

    public void setStorageZone(StorageZone storageZone) {
        this.storageZone = storageZone;
    }

    public Escale getEscale() {
        return escale;
    }

    public void setEscale(Escale escale) {
        this.escale = escale;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDateTime arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDateTime departureDate) {
        this.departureDate = departureDate;
    }

    public List<ContainerMovement> getMovements() {
        return movements;
    }

    public void setMovements(List<ContainerMovement> movements) {
        this.movements = movements;
    }
}
