package com.whu.servicedataclient.project.service;

import com.google.gson.Gson;
import com.whu.servicedataclient.project.entity.AcLineSegment;
import com.whu.servicedataclient.project.mapper.DataMapper;
import com.whu.servicedataclient.util.Global;
import com.whu.servicedataclient.util.IDUtil;

public class DataDealService {

    private static final DataMapper dataMapper = Global.ac.getBean(DataMapper.class);
    public static void insertAcLine(String res) {
        AcLineSegment aclinesegment;
        try {
            aclinesegment = new Gson().fromJson(res, AcLineSegment.class);
        } catch (Exception e) {
            return;
        }
        aclinesegment.setId(IDUtil.generate());
        dataMapper.insertAcLine(aclinesegment);
    }
}
