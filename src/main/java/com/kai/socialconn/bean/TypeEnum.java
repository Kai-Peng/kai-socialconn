package com.kai.socialconn.bean;

public enum TypeEnum {
    EATOUT(1, "eating out"), HIKING(2, "go hiking"), SWIM(3, "go swimming"), FOOTBALL(4, "playing football");
    private int id;
    private String name;

    TypeEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static TypeEnum getEnumById(int id){
        for(TypeEnum type : TypeEnum.values()){
            if(type.getId()==id){
                return type;
            }
        }
        return null;
    }
}
