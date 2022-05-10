package com.uav.ops.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.DeviceReqDTO;
import com.uav.ops.dto.res.DeptTreeResDTO;
import com.uav.ops.dto.res.DeviceResDTO;
import com.uav.ops.dto.res.UserResDTO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author frp
 */
@Mapper
@Repository
public interface DeviceMapper {

    Page<DeviceResDTO> listDevice(Page<DeviceResDTO> page, String name);

    List<DeviceResDTO> listAllDevice();

    DeviceResDTO getDeviceDetail(String id);

    List<DeviceResDTO.DeviceFault> selectDeviceFault(String id);

    Integer selectDeviceIsExist(DeviceReqDTO deviceReqDTO);

    Integer addDevice(DeviceReqDTO deviceReqDTO);

    Integer modifyDevice(DeviceReqDTO deviceReqDTO);

    Integer deleteDevice(DeviceReqDTO deviceReqDTO);
}
