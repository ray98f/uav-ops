package com.uav.ops.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author frp
 */
@Data
public class FlyHistoryDataResDTO {

    @ApiModelProperty(value = "时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(
            pattern = "yyyy-MM-dd HH:mm:ss",
            timezone = "GMT+8"
    )
    private Date time;

    private BatteryState batteryState;

    private LocationState locationState;

    private SpeedState speedState;

    private HeightState heightState;

    @Data
    public static class BatteryState {

        private Double batteryStatus1;

        private Double batteryStatus2;

        private Double percentageRemaining1;

        private Double percentageRemaining2;

        private Double voltageLevel1;

        private Double voltageLevel2;
    }

    @Data
    public static class LocationState {

        private Double lng;

        private Double lat;

        private String address;
    }

    @Data
    public static class SpeedState {

        private Double speed;
    }

    @Data
    public static class HeightState {

        private Double height;
    }
}
