package com.rpc.consumer.service;

import com.rpc.annotation.RpcReference;
import com.rpc.annotation.Service;
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
@Service
public class WarehouseServiceImpl {

    @RpcReference
    private WarehouseService warehouseService;

    public Warehouse getWarehouse(int id) {
        return warehouseService.getWarehouse(id);
    }
}
