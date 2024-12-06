package io.dcloud.uhfplugins;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.uhf.api.cls.Reader;

import java.util.List;

import io.dcloud.feature.uniapp.bridge.UniJSCallback;

public class TagInfoConverter {

    // 转换单个标签为JSONObject
    private static JSONObject convertTagToJson(Reader.TAGINFO tag) {
        JSONObject tagJson = new JSONObject();
        try {
            tagJson.put("AntennaID", tag.AntennaID);
            tagJson.put("Frequency", tag.Frequency);
            tagJson.put("TimeStamp", tag.TimeStamp);
            tagJson.put("EmbededDatalen", tag.EmbededDatalen);
            tagJson.put("Epclen", tag.Epclen);
            tagJson.put("Phase", tag.Phase);
            tagJson.put("ReadCnt", tag.ReadCnt);
            tagJson.put("RSSI", tag.RSSI);
            if (tag.EmbededData != null) {
                tagJson.put("EmbededData", bytesToHexString(tag.EmbededData));
            }
            tagJson.put("Res", bytesToHexString(tag.Res));
            tagJson.put("PC", bytesToHexString(tag.PC));
            tagJson.put("CRC", bytesToHexString(tag.CRC));
            if (tag.EpcId != null) {
                tagJson.put("EpcId", bytesToHexString(tag.EpcId));
            }
            if (tag.protocol != null) {
                tagJson.put("protocol", tag.protocol.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tagJson;
    }

    // 转换标签列表并返回结果
    public static void convertTagInfo(List<Reader.TAGINFO> tagList, UniJSCallback callback) {
        try {
            JSONObject jsonResult = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            if (tagList != null && !tagList.isEmpty()) {
                for (Reader.TAGINFO tag : tagList) {
                    if (tag != null) {
                        jsonArray.add(convertTagToJson(tag));
                    }
                }
            }
            jsonResult.put("code", 0);
            jsonResult.put("data", jsonArray);
            if (callback != null) {
                callback.invoke(jsonResult);
            }
        } catch (Exception e) {
            handleError(callback, e);
        }
    }

    private static JSONObject convertTempTagToJson(com.handheld.uhfr.Reader.TEMPTAGINFO tag) {
        JSONObject tagJson = new JSONObject();
        try {
            tagJson.put("AntennaID", tag.AntennaID);
            tagJson.put("Frequency", tag.Frequency);
            tagJson.put("TimeStamp", tag.TimeStamp);
            tagJson.put("EmbededDatalen", tag.EmbededDatalen);
            tagJson.put("Epclen", tag.Epclen);
            tagJson.put("Phase", tag.Phase);
            tagJson.put("ReadCnt", tag.ReadCnt);
            tagJson.put("RSSI", tag.RSSI);
            tagJson.put("Temperature", tag.Temperature);
            tagJson.put("index", tag.index);
            tagJson.put("count", tag.count);
            if (tag.EmbededData != null) {
                tagJson.put("EmbededData", bytesToHexString(tag.EmbededData));
            }
            tagJson.put("Res", bytesToHexString(tag.Res));
            tagJson.put("PC", bytesToHexString(tag.PC));
            tagJson.put("CRC", bytesToHexString(tag.CRC));
            if (tag.EpcId != null) {
                tagJson.put("EpcId", bytesToHexString(tag.EpcId));
            }
            if (tag.protocol != null) {
                tagJson.put("protocol", tag.protocol.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tagJson;
    }

    // 转换温度标签列表并返回结果
    public static void convertTempTagInfo(List<com.handheld.uhfr.Reader.TEMPTAGINFO> tagList, UniJSCallback callback) {
        try {
            JSONObject jsonResult = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            if (tagList != null && !tagList.isEmpty()) {
                for (com.handheld.uhfr.Reader.TEMPTAGINFO tag : tagList) {
                    if (tag != null) {
                        jsonArray.add(convertTempTagToJson(tag));
                    }
                }
            }
            jsonResult.put("code", 0);
            jsonResult.put("data", jsonArray);
            if (callback != null) {
                callback.invoke(jsonResult);
            }
        } catch (Exception e) {
            handleError(callback, e);
        }
    }

    // 转换 READER_ERR 并返回结果
    public static void convertReaderErr(Reader.READER_ERR result, UniJSCallback callback) {
        try {
            JSONObject jsonResult = new JSONObject();
            JSONObject errorData = new JSONObject();
            errorData.put("code", result.value());      // 数字错误码
            errorData.put("name", result.name());       // 错误名称
            String description = getReaderErrDescription(result);
            errorData.put("description", description);
            jsonResult.put("code", 0);
            jsonResult.put("data", errorData);
            if (callback != null) {
                callback.invoke(jsonResult);
            }
        } catch (Exception e) {
            handleError(callback, e);
        }
    }

    // 获取错误描述
    private static String getReaderErrDescription(Reader.READER_ERR error) {
        switch (error) {
            case MT_OK_ERR:
                return "操作成功(MT_OK_ERR: 0)";
            case MT_IO_ERR:
                return "IO错误(MT_IO_ERR: 1)";
            case MT_INTERNAL_DEV_ERR:
                return "内部设备错误(MT_INTERNAL_DEV_ERR: 2)";
            case MT_CMD_FAILED_ERR:
                return "命令执行失败(MT_CMD_FAILED_ERR: 3)";
            case MT_CMD_NO_TAG_ERR:
                return "没有找到标签(MT_CMD_NO_TAG_ERR: 4)";
            case MT_M5E_FATAL_ERR:
                return "M5E致命错误(MT_M5E_FATAL_ERR: 5)";
            case MT_OP_NOT_SUPPORTED:
                return "不支持的操作(MT_OP_NOT_SUPPORTED: 6)";
            case MT_INVALID_PARA:
                return "无效的参数(MT_INVALID_PARA: 7)";
            case MT_INVALID_READER_HANDLE:
                return "无效的读写器句柄(MT_INVALID_READER_HANDLE: 8)";
            case MT_HARDWARE_ALERT_ERR_BY_HIGN_RETURN_LOSS:
                return "高回波损耗硬件警告(MT_HARDWARE_ALERT_ERR_BY_HIGN_RETURN_LOSS: 9)";
            case MT_HARDWARE_ALERT_ERR_BY_TOO_MANY_RESET:
                return "过多重置硬件警告(MT_HARDWARE_ALERT_ERR_BY_TOO_MANY_RESET: 10)";
            case MT_HARDWARE_ALERT_ERR_BY_NO_ANTENNAS:
                return "无天线硬件警告(MT_HARDWARE_ALERT_ERR_BY_NO_ANTENNAS: 11)";
            case MT_HARDWARE_ALERT_ERR_BY_HIGH_TEMPERATURE:
                return "高温硬件警告(MT_HARDWARE_ALERT_ERR_BY_HIGH_TEMPERATURE: 12)";
            case MT_HARDWARE_ALERT_ERR_BY_READER_DOWN:
                return "读写器宕机硬件警告(MT_HARDWARE_ALERT_ERR_BY_READER_DOWN: 13)";
            case MT_HARDWARE_ALERT_ERR_BY_UNKNOWN_ERR:
                return "未知硬件警告(MT_HARDWARE_ALERT_ERR_BY_UNKNOWN_ERR: 14)";
            case M6E_INIT_FAILED:
                return "M6E初始化失败(M6E_INIT_FAILED: 15)";
            case MT_OP_EXECING:
                return "操作执行中(MT_OP_EXECING: 16)";
            case MT_UNKNOWN_READER_TYPE:
                return "未知读写器类型(MT_UNKNOWN_READER_TYPE: 17)";
            case MT_OP_INVALID:
                return "无效操作(MT_OP_INVALID: 18)";
            case MT_HARDWARE_ALERT_BY_FAILED_RESET_MODLUE:
                return "模块重置失败硬件警告(MT_HARDWARE_ALERT_BY_FAILED_RESET_MODLUE: 19)";
            case MT_MAX_ERR_NUM:
                return "最大错误数(MT_MAX_ERR_NUM: 20)";
            case MT_MAX_INT_NUM:
                return "最大中断数(MT_MAX_INT_NUM: 21)";
            case MT_TEST_DEV_FAULT_1:
                return "设备故障1(MT_TEST_DEV_FAULT_1: 51)";
            case MT_TEST_DEV_FAULT_2:
                return "设备故障2(MT_TEST_DEV_FAULT_2: 52)";
            case MT_TEST_DEV_FAULT_3:
                return "设备故障3(MT_TEST_DEV_FAULT_3: 53)";
            case MT_TEST_DEV_FAULT_4:
                return "设备故障4(MT_TEST_DEV_FAULT_4: 54)";
            case MT_TEST_DEV_FAULT_5:
                return "设备故障5(MT_TEST_DEV_FAULT_5: 55)";
            case MT_UPDFWFROMSP_OPENFILE_FAILED:
                return "打开固件文件失败(MT_UPDFWFROMSP_OPENFILE_FAILED: 80)";
            case MT_UPDFWFROMSP_FILE_FORMAT_ERR:
                return "固件文件格式错误(MT_UPDFWFROMSP_FILE_FORMAT_ERR: 81)";
            case MT_JNI_INVALID_PARA:
                return "JNI无效参数(MT_JNI_INVALID_PARA: 101)";
            case MT_OTHER_ERR:
                return "其他错误(MT_OTHER_ERR: -268435457)";
            default:
                return "未知错误: " + error.name() + " (" + error.value() + ")";
        }
    }

    // 转换 Region_Conf 并返回结果
    public static void convertRegionConf(Reader.Region_Conf result, UniJSCallback callback) {
        try {
            JSONObject jsonResult = new JSONObject();
            JSONObject regionData = new JSONObject();
            regionData.put("code", result.value());    // 数字代码
            regionData.put("name", result.name());     // 区域名称
            String description = getRegionConfDescription(result);
            regionData.put("description", description);
            jsonResult.put("code", 0);
            jsonResult.put("data", regionData);
            if (callback != null) {
                callback.invoke(jsonResult);
            }
        } catch (Exception e) {
            handleError(callback, e);
        }
    }

    // 获取区域描述
    private static String getRegionConfDescription(Reader.Region_Conf region) {
        switch (region) {
            case RG_NONE:
                return "未设置区域(RG_NONE: 0)";
            case RG_NA:
                return "北美区域(RG_NA: 1)";
            case RG_EU:
                return "欧盟区域(RG_EU: 2)";
            case RG_EU2:
                return "欧盟区域2(RG_EU2: 7)";
            case RG_EU3:
                return "欧盟区域3(RG_EU3: 8)";
            case RG_KR:
                return "韩国区域(RG_KR: 3)";
            case RG_PRC:
                return "中国区域(RG_PRC: 6)";
            case RG_PRC2:
                return "中国区域2(RG_PRC2: 10)";
            case RG_OPEN:
                return "开放区域(RG_OPEN: 255)";
            default:
                return "未知区域: " + (region != null ? region.name() + " (" + region.value() + ")" : "null");
        }
    }

    // 错误处理
    public static void handleError(UniJSCallback callback, Exception e) {
        if (callback != null) {
            try {
                JSONObject errorResult = new JSONObject();
                errorResult.put("code", -1);
                errorResult.put("msg", e.getMessage());
                callback.invoke(errorResult);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // 字节数组转十六进制字符串
    private static String bytesToHexString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString().toUpperCase();
    }
}
