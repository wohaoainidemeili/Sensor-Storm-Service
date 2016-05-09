package storm.socketOperation.protocol;

/**
 * Created by Yuan on 2016/4/19.
 */
public class CRC16 {
    //����CRCУ���룬��Ҫ�������ݣ�������Ҫ�����У�������λ�ֽ�
    // ���ؼ���õ�����У������
    public static byte[] CRCCaculate(byte[] crcbuf){
        int crc=0xffff;//��ʼ��crc
        int len=crcbuf.length-2;//��ͷ��β��������,����λ�����洢crc
        for (int n=0;n<len;n++){
            crc= crc^crcbuf[n];//ֱ�ӽ��е�λ������㣬û�е�λ����λ�������XOR����
            for (int i=0;i<8;i++) {
                int _out = crc & 1;//&���㣬ֻ�������һλ,�õ��Ƴ�λ���ж�Ϊ1����0
                crc = crc >> 1;//����һλ���Ϸ��Զ���Ϊ0
                if (_out == 1) {
                    crc = crc ^ 0xa001;//�����ʽ���
                }
            }
        }
        //crc�����Ҫ��ǰ�ߺ�
        crcbuf[len+1]=(byte)(crc>>8);
        crcbuf[len]=(byte)crc;
        return crcbuf;
    }

    /**
     * CRC16��֤���յ��������Ƿ���ȷ
     * @param recieveBuffer Ϊ���յ�������
     * @return ����CRC16Ҫ�󣬷����棬����Ϊ��
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
