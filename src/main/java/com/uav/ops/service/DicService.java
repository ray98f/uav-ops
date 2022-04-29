package com.uav.ops.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.uav.ops.dto.PageReqDTO;
import com.uav.ops.dto.req.DicDataReqDTO;
import com.uav.ops.dto.req.DicReqDTO;
import com.uav.ops.dto.res.DicDataResDTO;
import com.uav.ops.dto.res.DicResDTO;

public interface DicService {

    /**
     * 分页查询字典类型
     *
     * @param pageReqDTO
     * @param name
     * @param type
     * @param isEnable
     * @return
     */
    Page<DicResDTO> getDics(PageReqDTO pageReqDTO, String name, String type, Integer isEnable);

    /**
     * 添加字典类型
     *
     * @param reqDTO
     */
    void addDic(DicReqDTO reqDTO);

    /**
     * 更新字典类型
     *
     * @param reqDTO
     */
    void updateDic(DicReqDTO reqDTO);

    /**
     * 删除字典类型
     *
     * @param reqDTO
     */
    void deleteDic(DicReqDTO reqDTO);

    /**
     * 分页查询字典数据
     *
     * @param pageReqDTO
     * @param type
     * @param value
     * @param isEnable
     * @return
     */
    Page<DicDataResDTO> getDicDataByPage(PageReqDTO pageReqDTO, String type, String value, Integer isEnable);

    /**
     * 添加字典数据
     *
     * @param reqDTO
     */
    void addDicData(DicDataReqDTO reqDTO);

    /**
     * 更新字典数据
     *
     * @param reqDTO
     */
    void updateDicData(DicDataReqDTO reqDTO);

    /**
     * 删除字典数据
     *
     * @param reqDTO
     */
    void deleteDicData(DicDataReqDTO reqDTO);


}
