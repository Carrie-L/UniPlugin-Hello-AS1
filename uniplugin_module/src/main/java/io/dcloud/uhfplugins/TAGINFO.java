package io.dcloud.uhfplugins;

import com.uhf.api.cls.Reader;


public class TAGINFO implements Cloneable {
    public byte AntennaID;
    public int Frequency;
    public int TimeStamp;
    public short EmbededDatalen;
    public byte[] EmbededData = null;
    public byte[] Res = new byte[2];
    public short Epclen;
    public byte[] PC = new byte[2];
    public byte[] CRC = new byte[2];
    public byte[] EpcId = null;
    public int Phase;
    public Reader.SL_TagProtocol protocol;
    public int ReadCnt;
    public int RSSI;

    public TAGINFO() {
    }

    public Object clone() {
        Object o = null;

        try {
            o = (Reader.TAGINFO)super.clone();
            return o;
        } catch (CloneNotSupportedException var3) {
            return null;
        }
    }
}