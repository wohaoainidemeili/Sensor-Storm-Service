package storm.SOS;

import java.io.Serializable;

/**
 * 观测现象属性及属性值
 */
public class ObsProperties implements Serializable{
    String id;//ID
    String name;//名称
    String unit;//单位
    int startpos;//属性在传感器接收数据中的起点位置
    int len;//这个属性值占的长度
    double value;//值


    public ObsProperties(){

    }
    //从数据库中获取该传感器接收的参数
    public ObsProperties(String id,String name,String unit,int startpos,int len){
        this.id=id;
        this.name=name;
        this.unit=unit;
        this.startpos=startpos;
        this.len=len;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getStartpos() {
        return startpos;
    }

    public void setStartpos(int startpos) {
        this.startpos = startpos;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

}
