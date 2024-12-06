package io.dcloud.uhfplugins;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.handheld.uhfr.UHFRManager;
import com.uhf.api.cls.Reader;

import java.util.ArrayList;
import java.util.List;

import cn.pda.serialport.Tools;
import io.dcloud.feature.uniapp.annotation.UniJSMethod;
import io.dcloud.feature.uniapp.bridge.UniJSCallback;
import io.dcloud.feature.uniapp.common.UniModule;

public class UHFModule extends UniModule {
    protected UHFRManager mUhfrManager;//uhf
    private final String TAG = "UHFModule-console";

    @UniJSMethod(uiThread = true)
    public void testRegionConfConversion(UniJSCallback callback) {
        TagInfoConverterTest test = new TagInfoConverterTest();
        test.testRegionConfConversion(callback);
    }

    @UniJSMethod(uiThread = true)
    public void testReaderErrConversion(UniJSCallback callback) {
        TagInfoConverterTest test = new TagInfoConverterTest();
        test.testReaderErrConversion(callback);
    }

    @UniJSMethod(uiThread = true)
    public void testTagInfoConversion(UniJSCallback callback) {
        TagInfoConverterTest test = new TagInfoConverterTest();
        test.testTagInfoConversion(callback);
    }

    @UniJSMethod(uiThread = true)
    public void testTempTagInfoConversion(UniJSCallback callback) {
        TagInfoConverterTest test = new TagInfoConverterTest();
        test.testTempTagInfoConversion(callback);
    }

    @UniJSMethod(uiThread = true)
    public void tagInventoryRealTime1(UniJSCallback callback) {
        testRegionConfConversion(callback);
        testReaderErrConversion(callback);
        testTagInfoConversion(callback);
        testTempTagInfoConversion(callback);
    }

    public UHFModule() {
    }

    @UniJSMethod(uiThread = true)
    public UHFRManager getInstance(UniJSCallback callback) {
        mUhfrManager = UHFRManager.getInstance();
        JSONObject result = new JSONObject();
        if (mUhfrManager == null) {
            result.put("code", -1);
            result.put("data", "null");
        } else {
            result.put("code", 0);
            result.put("data", "connect");
        }
        if (callback != null) callback.invokeAndKeepAlive(result);
        return mUhfrManager;
    }

    private void getInstance() {
        mUhfrManager = UHFRManager.getInstance();
    }

    @UniJSMethod(uiThread = true)
    public void getHardware(UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            String hardware = mUhfrManager.getHardware();
            JSONObject result = new JSONObject();
            result.put("code", 0);
            result.put("data", hardware);
            if (callback != null) callback.invoke(result);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void asyncStartReading(UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            Reader.READER_ERR result = mUhfrManager.asyncStartReading();
            TagInfoConverter.convertReaderErr(result, callback);


        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void asyncStopReading(UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            Reader.READER_ERR result = mUhfrManager.asyncStopReading();
            TagInfoConverter.convertReaderErr(result, callback);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void setInventoryFilter(byte[] fdata, int fbank, int fstartaddr, boolean matching, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            boolean result = mUhfrManager.setInventoryFilter(fdata, fbank, fstartaddr, matching);
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("code", 0);
            jsonResult.put("data", result);
            if (callback != null) callback.invoke(jsonResult);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void setCancleInventoryFilter(UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            boolean result = mUhfrManager.setCancleInventoryFilter();
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("code", 0);
            jsonResult.put("data", result);
            if (callback != null) callback.invoke(jsonResult);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void tagInventoryRealTime(UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            List<Reader.TAGINFO> result = mUhfrManager.tagInventoryRealTime();
            if (result == null) {
                result = new ArrayList<>();  // 如果为null则返回空列表
            }
            TagInfoConverter.convertTagInfo(result, callback);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void tagEpcTidInventoryByTimer(short readtime, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            List<Reader.TAGINFO> result = mUhfrManager.tagEpcTidInventoryByTimer(readtime);
            if (result == null) {
                result = new ArrayList<>();  // 如果为null则返回空列表
            }
            TagInfoConverter.convertTagInfo(result, callback);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void stopTagInventory(UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            boolean result = mUhfrManager.stopTagInventory();
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("code", 0);
            jsonResult.put("data", result);
            if (callback != null) callback.invoke(jsonResult);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void tagInventoryByTimer(short readtime, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            List<Reader.TAGINFO> result = mUhfrManager.tagInventoryByTimer(readtime);
            if (result == null) {
                result = new ArrayList<>();  // 如果为null则返回空列表
            }
            TagInfoConverter.convertTagInfo(result, callback);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void getTagData(int mbank, int startaddr, int len, byte[] rdata, byte[] password, short timeout, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            Reader.READER_ERR result = mUhfrManager.getTagData(mbank, startaddr, len, rdata, password, timeout);
            TagInfoConverter.convertReaderErr(result, callback);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void getTagDataByFilter(int mbank, int startaddr, int len, byte[] password, short timeout, byte[] fdata, int fbank, int fstartaddr, boolean matching, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            byte[] result = mUhfrManager.getTagDataByFilter(mbank, startaddr, len, password, timeout, fdata, fbank, fstartaddr, matching);
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("code", 0);
            jsonResult.put("data", android.util.Base64.encodeToString(result, android.util.Base64.DEFAULT));
            if (callback != null) callback.invoke(jsonResult);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void writeTagData(char mbank, int startaddress, byte[] data, int datalen, byte[] accesspasswd, short timeout, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            Reader.READER_ERR result = mUhfrManager.writeTagData(mbank, startaddress, data, datalen, accesspasswd, timeout);
            TagInfoConverter.convertReaderErr(result, callback);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void writeTagDataByFilter(char mbank, int startaddress, byte[] data, int datalen, byte[] accesspasswd, short timeout, byte[] fdata, int fbank, int fstartaddr, boolean matching, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            Reader.READER_ERR result = mUhfrManager.writeTagDataByFilter(mbank, startaddress, data, datalen, accesspasswd, timeout, fdata, fbank, fstartaddr, matching);
            TagInfoConverter.convertReaderErr(result, callback);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void writeTagEPC(byte[] data, byte[] accesspwd, short timeout, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            Reader.READER_ERR result = mUhfrManager.writeTagEPC(data, accesspwd, timeout);
            TagInfoConverter.convertReaderErr(result, callback);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void writeTagEPCByFilter(byte[] data, byte[] accesspwd, short timeout, byte[] fdata, int fbank, int fstartaddr, boolean matching, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            Reader.READER_ERR result = mUhfrManager.writeTagEPCByFilter(data, accesspwd, timeout, fdata, fbank, fstartaddr, matching);
            TagInfoConverter.convertReaderErr(result, callback);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void lockTag(Reader.Lock_Obj lockobject, Reader.Lock_Type locktype, byte[] accesspasswd, short timeout, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            Reader.READER_ERR result = mUhfrManager.lockTag(lockobject, locktype, accesspasswd, timeout);
            TagInfoConverter.convertReaderErr(result, callback);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void lockTagByFilter(Reader.Lock_Obj lockobject, Reader.Lock_Type locktype, byte[] accesspasswd, short timeout, byte[] fdata, int fbank, int fstartaddr, boolean matching, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            Reader.READER_ERR result = mUhfrManager.lockTagByFilter(lockobject, locktype, accesspasswd, timeout, fdata, fbank, fstartaddr, matching);
            TagInfoConverter.convertReaderErr(result, callback);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void killTag(byte[] killpasswd, short timeout, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            Reader.READER_ERR result = mUhfrManager.killTag(killpasswd, timeout);
            TagInfoConverter.convertReaderErr(result, callback);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void killTagByFilter(byte[] killpasswd, short timeout, byte[] fdata, int fbank, int fstartaddr, boolean matching, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            Reader.READER_ERR result = mUhfrManager.killTagByFilter(killpasswd, timeout, fdata, fbank, fstartaddr, matching);
            TagInfoConverter.convertReaderErr(result, callback);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void setRegion(Reader.Region_Conf region, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            Reader.READER_ERR result = mUhfrManager.setRegion(region);
            TagInfoConverter.convertReaderErr(result, callback);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void getRegion(UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            Reader.Region_Conf result = mUhfrManager.getRegion();
            TagInfoConverter.convertRegionConf(result, callback);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void getFrequencyPoints(UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            int[] results = mUhfrManager.getFrequencyPoints();
            // 转换成 JSONArray，这样 uniapp 可以直接使用
            JSONArray jsonArray = new JSONArray();
            if (results != null) {
                for (int value : results) {
                    jsonArray.add(value);
                }
            }
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("code", 0);
            jsonResult.put("data", jsonArray);
            if (callback != null) callback.invoke(jsonResult);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void setFrequencyPoints(int[] frequencyPoints, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            Reader.READER_ERR result = mUhfrManager.setFrequencyPoints(frequencyPoints);
            TagInfoConverter.convertReaderErr(result, callback);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void setPower(int readPower, int writePower, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            Reader.READER_ERR result = mUhfrManager.setPower(readPower, writePower);
            TagInfoConverter.convertReaderErr(result, callback);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void getPower(UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            int[] powerArray = mUhfrManager.getPower();
            JSONObject result = new JSONObject();
            if (powerArray != null) {
                JSONArray jsonArray = new JSONArray();
                for (int value : powerArray) {
                    jsonArray.add(value);
                }
                result.put("code", 0);
                result.put("data", jsonArray);
            } else {
                result.put("code", 0);
                result.put("data", null);
            }
            if (callback != null) callback.invoke(result);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void close(UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            boolean result = mUhfrManager.close();
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("code", 0);
            jsonResult.put("data", result);
            if (callback != null) callback.invoke(jsonResult);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void setGen2session(int session, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            boolean result = mUhfrManager.setGen2session(session);
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("code", 0);
            jsonResult.put("data", result);
            if (callback != null) callback.invoke(jsonResult);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void setTarget(int target, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            boolean result = mUhfrManager.setTarget(target);
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("code", 0);
            jsonResult.put("data", result);
            if (callback != null) callback.invoke(jsonResult);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void setQvaule(int qvaule, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            boolean result = mUhfrManager.setQvaule(qvaule);
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("code", 0);
            jsonResult.put("data", result);
            if (callback != null) callback.invoke(jsonResult);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void setFastID(boolean isOpenFastTiD, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            boolean result = mUhfrManager.setFastID(isOpenFastTiD);
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("code", 0);
            jsonResult.put("data", result);
            if (callback != null) callback.invoke(jsonResult);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void getGen2session(UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            int result = mUhfrManager.getGen2session();
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("code", 0);
            jsonResult.put("data", result);
            if (callback != null) callback.invoke(jsonResult);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void getYueheTagTemperature(byte[] accesspassword, UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            List<com.handheld.uhfr.Reader.TEMPTAGINFO> result = mUhfrManager.getYueheTagTemperature(accesspassword);
            if (result == null) {
                result = new ArrayList<>();  // 如果为null则返回空列表
            }
            TagInfoConverter.convertTempTagInfo(result, callback);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod(uiThread = true)
    public void getYilianTagTemperature(UniJSCallback callback) {
        try {
            if (mUhfrManager == null) getInstance();
            List<com.handheld.uhfr.Reader.TEMPTAGINFO> result = mUhfrManager.getYilianTagTemperature();
            if (result == null) {
                result = new ArrayList<>();  // 如果为null则返回空列表
            }
            TagInfoConverter.convertTempTagInfo(result, callback);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    /*-------Tools----------- */
    @UniJSMethod(uiThread = true)
    public static void HexString2Bytes(String src, UniJSCallback callback) {
        try {
            byte[] bytes = Tools.HexString2Bytes(src);
            // 转换为Base64字符串
            String base64String = android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT);
            // 构建返回结果
            JSONObject result = new JSONObject();
            result.put("code", 0);
            result.put("data", base64String);
            // 回调返回
            if (callback != null) callback.invoke(result);
        } catch (Exception e) {
            // 错误处理
            TagInfoConverter.handleError(callback, e);
        }
    }


    /**
     * @param bytes      "data"
     * @param jsCallback
     * @return
     */
    @UniJSMethod(uiThread = true)
    public static void Bytes2HexString(byte[] bytes, UniJSCallback jsCallback) {
        try {
            // 获取传入的字节数组
//            byte[] byteArray = bytes.getBytes("data");
            // 转换为16进制字符串
            String hexString = Tools.Bytes2HexString(bytes, bytes.length);
            // 处理成功的回调
            if (jsCallback != null) {
                JSONObject result = new JSONObject();
                result.put("code", 0);
                result.put("data", hexString);
                jsCallback.invoke(result);
            }
        } catch (Exception e) {
            // 处理异常情况
            if (jsCallback != null) {
                JSONObject error = new JSONObject();
                error.put("code", -1);
                error.put("msg", e.getMessage());
                jsCallback.invoke(error);
            }
        }
    }

    @UniJSMethod(uiThread = true)
    public static void uniteBytes(int src0, int src1, UniJSCallback callback) {
        // 检查范围（可选）
        if (src0 < -128 || src0 > 127 || src1 < -128 || src1 > 127) {
            if (callback != null) {
                JSONObject result = new JSONObject();
                result.put("code", -1);
                result.put("data", "Input values must be between -127 and 128");
                callback.invoke(result);
            }
        } else {
            byte b = (byte) (((src0 & 0x0F) << 4) | (src1 & 0x0F));
            if (callback != null) {
                JSONObject result = new JSONObject();
                result.put("code", 0);
                result.put("data", b);
                callback.invoke(result);
            }
        }

    }

    @UniJSMethod(uiThread = true)
    public void bytesToInt(byte[] bytes, UniJSCallback callback) {
        try {
            int result = Tools.bytesToInt(bytes);
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("code", 0);
            jsonResult.put("data", result);
            if (callback != null) callback.invoke(jsonResult);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod
    public int bytesToInt(byte[] bytes) {
        return Tools.bytesToInt(bytes);
    }

    @UniJSMethod(uiThread = true)
    public void intToByte(int i, UniJSCallback callback) {
        try {
            byte[] result = Tools.intToByte(i);
            String base64String = android.util.Base64.encodeToString(result, android.util.Base64.DEFAULT);
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("code", 0);
            jsonResult.put("data", base64String);
            if (callback != null) callback.invoke(jsonResult);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod
    public String intToByte(int i) {
        byte[] result = Tools.intToByte(i);
        return android.util.Base64.encodeToString(result, android.util.Base64.DEFAULT);
    }

    @UniJSMethod(uiThread = true)
    public void getMyTime(UniJSCallback callback) {
        try {
            String result = Tools.getmyTime();
            JSONObject jsonResult = new JSONObject();
            jsonResult.put("code", 0);
            jsonResult.put("data", result);
            if (callback != null) callback.invoke(jsonResult);
        } catch (Exception e) {
            TagInfoConverter.handleError(callback, e);
        }
    }

    @UniJSMethod
    public String getMyTime() {
        return Tools.getmyTime();
    }

}