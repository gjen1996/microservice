package com.glen.billing.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.glen.billing.entity.MonthsPrepayCardEntity;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author chenkb3
 * @since 2019-07-09
 */
public interface MonthsPrepayCardDao extends BaseMapper<MonthsPrepayCardEntity> {

}
