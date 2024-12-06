package io.dcloud.uhfplugins;

import com.uhf.api.cls.Reader;

import java.util.ArrayList;
import java.util.List;

import io.dcloud.feature.uniapp.bridge.UniJSCallback;


public class TagInfoConverterTest {
    private Reader mReader;

    // 测试TAGINFO转换
    public void testTagInfoConversion(UniJSCallback callback) {
        System.out.println("\n=== Testing TAGINFO Conversion ===");
        try {
            // 创建模拟的TAGINFO数据
            List<TAGINFO> tagList = new ArrayList<>();
            TAGINFO tag = new TAGINFO();
            tag.AntennaID = 1;
            tag.Frequency = 915000;
            tag.TimeStamp = (int)(System.currentTimeMillis() / 1000);
            tag.EmbededDatalen = 0;
            tag.Epclen = 12;
            tag.PC = new byte[]{0x30, 0x00};
            tag.CRC = new byte[]{(byte)0xFF, (byte)0xEE};
            tag.EpcId = new byte[]{0x11, 0x22, 0x33, 0x44, 0x55, 0x66};
            tag.Phase = 0;
            tag.ReadCnt = 1;
            tag.RSSI = -60;
            tagList.add(tag);

            // 测试转换
            TagInfoConverter.convertTagInfo(new ArrayList<>(), callback);
            System.out.println("TAGINFO conversion test completed");
        } catch (Exception e) {
            System.out.println("TAGINFO conversion test failed: " + e.getMessage());
        }
    }

    // 测试TEMPTAGINFO转换
    public void testTempTagInfoConversion(UniJSCallback callback) {
        System.out.println("\n=== Testing TEMPTAGINFO Conversion ===");
        try {
            // 创建模拟的TEMPTAGINFO数据
            List<com.handheld.uhfr.Reader.TEMPTAGINFO> tempTagList = new ArrayList<>();
            com.handheld.uhfr.Reader.TEMPTAGINFO tempTag = new com.handheld.uhfr.Reader.TEMPTAGINFO();
            tempTag.AntennaID = 1;
            tempTag.Frequency = 915000;
            tempTag.Temperature = 25.5;
            tempTag.EpcId = new byte[]{0x11, 0x22, 0x33, 0x44};
            tempTag.RSSI = -65;
            tempTagList.add(tempTag);

            // 测试转换
            TagInfoConverter.convertTempTagInfo(new ArrayList<>(), callback);
            System.out.println("TEMPTAGINFO conversion test completed");
        } catch (Exception e) {
            System.out.println("TEMPTAGINFO conversion test failed: " + e.getMessage());
        }
    }

    // 测试READER_ERR转换
    public void testReaderErrConversion(UniJSCallback callback) {
        System.out.println("\n=== Testing READER_ERR Conversion ===");
        try {
            // 测试不同的错误类型
            Reader.READER_ERR[] errors = {
                    Reader.READER_ERR.MT_OK_ERR,
                    Reader.READER_ERR.MT_CMD_FAILED_ERR,
                    Reader.READER_ERR.MT_INVALID_PARA
            };

            for (Reader.READER_ERR error : errors) {
                TagInfoConverter.convertReaderErr(error, callback);
                System.out.println("Tested error: " + error.name());
            }
            System.out.println("READER_ERR conversion test completed");
        } catch (Exception e) {
            System.out.println("READER_ERR conversion test failed: " + e.getMessage());
        }
    }

    // 测试Region_Conf转换
    public void testRegionConfConversion(UniJSCallback callback) {
        System.out.println("\n=== Testing Region_Conf Conversion ===");
        try {
            // 测试不同的区域配置
            Reader.Region_Conf[] regions = {
                    Reader.Region_Conf.RG_NA,
                    Reader.Region_Conf.RG_PRC,
                    Reader.Region_Conf.RG_EU
            };

            for (Reader.Region_Conf region : regions) {
                TagInfoConverter.convertRegionConf(region, callback);
                System.out.println("Tested region: " + region.name());
            }
            System.out.println("Region_Conf conversion test completed");
        } catch (Exception e) {
            System.out.println("Region_Conf conversion test failed: " + e.getMessage());
        }
    }


}