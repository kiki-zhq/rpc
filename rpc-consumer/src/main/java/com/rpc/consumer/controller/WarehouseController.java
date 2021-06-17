package com.rpc.consumer.controller;

import com.rpc.annotation.Autowire;
import com.rpc.annotation.RequestMapping;
import com.rpc.annotation.RequestParam;
import com.rpc.annotation.RestController;
import com.rpc.common.model.Warehouse;
import com.rpc.consumer.service.WarehouseServiceImpl;


/**
 * <p>
 *
 * </p>
 *
 * @author kiki
 * @date 2021/6/17
 */
@RestController
@RequestMapping("/warehouse")
public class WarehouseController {

    @Autowire
    private WarehouseServiceImpl warehouseService;

    @RequestMapping(value = "/test")
    public Warehouse getWarehouse(@RequestParam("id") Integer id) {
        return warehouseService.getWarehouse(id);
    }
}
