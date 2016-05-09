package storm.socketOperation.protocol;

/**
 * Created by Yuan on 2016/4/19.
 */
public class CRC16 {
    //计算CRC校验码，需要计算数据，包含需要填入的校验码的两位字节
    // 返回计算好的整个校验数据
    public static byte[] CRCCaculate(byte[] crcbuf){
        int crc=0xffff;//初始化crc
        int len=crcbuf.length-2;//从头到尾进行运算,后两位用来存储crc
        for (int n=0;n<len;n++){
            crc= crc^crcbuf[n];//直接进行低位异或运算，没有的位不补位，详情见XOR测试
            for (int i=0;i<8;i++) {
                int _out = crc & 1;//&运算，只保留最后一位,得到移除位，判断为1还是0
                crc = crc >> 1;//右移一位，上方自动添补为0
                if (_out == 1) {
                    crc = crc ^ 0xa001;//与多项式异或
                }
            }
        }
        //crc结果需要低前高后
        crcbuf[len+1]=(byte)(crc>>8);
        crcbuf[len]=(byte)crc;
        return crcbuf;
    }

    /**
     * CRC16验证接收到的数据是否正确
     * @param recieveBuffer 为接收到的数据
     * @return 满足CRC16要求，返回真，否则为假
     */
    public static boolean CRCcheck(byte[] recieveBuffer){
        boolean isCorrect=true;

        int CRC=0xffff;
        int temp=0xA001;
        for (int i=0;i<recieveBuffer.length;i++){
            CRC^=recieveBuffer[i];
            for (int j=0;j<8;j++){
                int lastOne=CRC&1;
                CRC>>=1;
                CRC&=0x7fff;
                if (lastOne==1){
                    CRC^=temp;
                }
            }
        }
        if (CRC==0) isCorrect=true;
        else isCorrect=false;
        return  isCorrect;
    }
}
