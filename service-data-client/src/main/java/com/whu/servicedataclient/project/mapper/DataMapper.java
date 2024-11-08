package com.whu.servicedataclient.project.mapper;

import com.whu.servicedataclient.project.entity.AcLineSegment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface DataMapper {
    public void insertAcLine(AcLineSegment acLineSegment);
}
