package com.glen.terminnal.service;

import com.baomidou.mybatisplus.service.IService;
import com.glen.terminnal.entity.StoreTerminnalDetailEntity;

import java.util.Map;


/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wemoucy
 * @since 2019-03-22
 */
public interface StoreTerminnalDetailService extends IService<StoreTerminnalDetailEntity> {

    /**
     * 根据iccid返回StoreTerminaldetail
     *
     * @param iccid
     * @return
     * @author chenkb3
     */
    StoreTerminnalDetailEntity getStoreTerminalByIccid(String iccid);
}
