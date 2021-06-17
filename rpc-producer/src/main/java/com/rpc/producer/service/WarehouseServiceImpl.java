package com.rpc.producer.service;

import com.rpc.annotation.RpcService;
import com.rpc.common.model.Warehouse;
import com.rpc.common.service.WarehouseService;

/**
 * <p>
 *
 * </p>
 *
 * @author kiki
 * @date 2021/6/17
 */
@RpcService
public class WarehouseServiceImpl implements WarehouseService {

    @Override
    public Warehouse getWarehouse(int id) {
        System.out.println("调用成功");
        return new Warehouse(id, "hahahahahahaaha");
    }
}
