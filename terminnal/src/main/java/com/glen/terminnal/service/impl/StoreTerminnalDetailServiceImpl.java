package com.glen.terminnal.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.glen.terminnal.dao.StoreTerminnalDetailDao;
import com.glen.terminnal.entity.StoreTerminnalDetailEntity;
import com.glen.terminnal.service.StoreTerminnalDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wemoucy
 * @since 2019-03-22
 */
@Service
@Slf4j
public class StoreTerminnalDetailServiceImpl extends ServiceImpl<StoreTerminnalDetailDao, StoreTerminnalDetailEntity> implements StoreTerminnalDetailService {

    @Override
    public StoreTerminnalDetailEntity getStoreTerminalByIccid(String iccid) {
        EntityWrapper<StoreTerminnalDetailEntity> ew = new EntityWrapper<>();
        ew.eq("iccid", iccid);
        return this.selectOne(ew);
    }

}
