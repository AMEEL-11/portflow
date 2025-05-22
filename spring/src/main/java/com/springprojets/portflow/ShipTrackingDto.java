package com.springprojets.portflow;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShipTrackingDto {
    private Long shipId;
    private String name;
    private String imoNumber;
    private Double latitude;
    private Double longitude;
    private Double speed;
    private ShipStatus status;
    private LocalDateTime lastUpdate;
    private String currentBerthName;
} 