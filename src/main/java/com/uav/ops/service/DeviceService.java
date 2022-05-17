package com.uav.ops.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.DeviceReqDTO;
import com.uav.ops.dto.res.DeptTreeResDTO;
import com.uav.ops.dto.res.DeviceResDTO;
import com.uav.ops.dto.res.UserResDTO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author frp
 */
public interface DeviceService {

    Page<DeviceResDTO> listDevice(String name, PageReqDTO pageReqDTO);

    List<DeviceResDTO> listAllDevice();

    DeviceResDTO getDeviceDetail(String id);

    void addDevice(DeviceReqDTO deviceReqDTO);

    void modifyDevice(DeviceReqDTO deviceReqDTO);

    void deleteDevice(DeviceReqDTO deviceReqDTO);

    void addDeviceFault(DeviceResDTO.DeviceFault deviceFault);

    void operateDrone(String id, String operate);

}
