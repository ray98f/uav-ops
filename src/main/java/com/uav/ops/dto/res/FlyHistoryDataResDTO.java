package com.uav.ops.dto.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author frp
 */
@Data
public class FlyHistoryDataResDTO {

    private BatteryState batteryState;

    private FlightControllerState flightControllerState;

    @Data
    public static class BatteryState {

        @ApiModelProperty(value = "电流")
        private Double current;

        @ApiModelProperty(value = "电压")
        private Double voltage;

        @ApiModelProperty(value = "电压等级")
        private Double cellVoltageLevel;

        @ApiModelProperty(value = "剩余电量")
        private Double chargeRemaining;

        @ApiModelProperty(value = "剩余电量百分比")
        private Double chargeRemainingInPercent;
    }

    @Data
    public static class FlightControllerState {

        @ApiModelProperty(value = "经度")
        private Double lng;

        @ApiModelProperty(value = "纬度")
        private Double lat;

        @ApiModelProperty(value = "X方向速度, 左右速度")
        private Double velocityX;

        @ApiModelProperty(value = "y方向速度, 前后速度")
        private Double velocityY;

        @ApiModelProperty(value = "Z方向速度, 垂直速度")
        private Double velocityZ;

        @ApiModelProperty(value = "无人机高度")
        private Double altitude;

        @ApiModelProperty(value = "飞行模式")
        private String flightModeString;
    }
}
