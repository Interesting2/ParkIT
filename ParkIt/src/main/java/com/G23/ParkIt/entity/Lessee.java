package com.G23.ParkIt.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lessee {
    private Integer lesseeId;
    private Integer listingId;
    private Integer userId;
}
