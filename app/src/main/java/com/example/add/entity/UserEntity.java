package com.example.add.entity;

/**
 * 包名：com.example.DBAppDemo.entity
 * 描述：用户表
 * User 张伟
 * Date 2015/7/17 0017.
 * Time 上午 9:48.
 * 修改日期：
 * 修改内容：
 */
public class UserEntity extends BaseEntity {
    private String name;//姓名
    private int age;//年龄
    private String address;//地址

    //数据库修改后的字段
    private String sex;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
}
