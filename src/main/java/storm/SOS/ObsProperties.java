package storm.SOS;

import java.io.Serializable;

/**
 * �۲��������Լ�����ֵ
 */
public class ObsProperties implements Serializable{
    String id;//ID
    String name;//����
    String unit;//��λ
    int startpos;//�����ڴ��������������е����λ��
    int len;//�������ֵռ�ĳ���
    double value;//ֵ


    public ObsProperties(){

    }
    //�����ݿ��л�ȡ�ô��������յĲ���
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
