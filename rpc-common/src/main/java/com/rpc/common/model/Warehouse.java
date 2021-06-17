package com.rpc.common.model;



/**
 * <p>
 *
 * </p>
 *
 * @author kiki
 * @date 2021/6/17
 */
public class Warehouse {

    /**
     * id
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Warehouse(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}
