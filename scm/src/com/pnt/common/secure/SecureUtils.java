package com.pnt.common.secure;

/**
 * @author ERP 시스템즈 이주용
 * @since 2015.04.18
 * @version 1.0
 * @see
 * 
 * <pre>
 * << 개정이력(Modification Information) >>
 * 
 *     수정일         수정자               수정내용
 *  --------------    ------------    ---------------------------
 *  2015.04.18        이주용              최초 생성
 * </pre>
 */

import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.bouncycastle.util.encoders.Base64;

import com.pnt.common.util.CoreUtils;
import com.pnt.context.config.Configurator;

final public class SecureUtils {

    public static Object secure(Object param) {
        if (param == null) {
            param = "";
        } else {
            if (param instanceof String) {
                String paramStr = (String) param;

                paramStr = paramStr.replaceAll("&", "&amp;");
                paramStr = paramStr.replaceAll("\"", "&quot;");
                paramStr = paramStr.replaceAll("<", "&lt;");
                paramStr = paramStr.replaceAll(">", "&gt;");
                paramStr = paramStr.replaceAll("'", "&#39;");
//                paramStr = paramStr.replaceAll("[.][.]/", "");
//                paramStr = paramStr.replaceAll("[.]/", "");
                paramStr = paramStr.replaceAll("%2F", "");

                param = paramStr;
            }
        }

        return param;
    }

    public static String unsecure(String secured) {
        if (secured == null) {
            secured = "";
        } else {
            secured = secured.replaceAll("&lt;", "<");
            secured = secured.replaceAll("&gt;", ">");
            secured = secured.replaceAll("&amp;", "&");
            secured = secured.replaceAll("&quot;", "\"");
            secured = secured.replaceAll("&#39;", "'");
        }

        return secured;
    }

    public static String getParameter(HttpServletRequest request, String param) {
        return SecureUtils.getParameter(request, param, "");
    }

    public static String getParameter(HttpServletRequest request, String param, String dephault) {
        String value;
        Map<String, String[]> securedParams = SecureUtils.getParameters(request);

        if (securedParams.containsKey(param)) {
            value = ((String[]) securedParams.get(param))[0];
            value = value.trim();
            if (dephault == null && "".equals(value)) {
                value = null;
            }
        } else {
            value = dephault;
        }

        return value;
    }

    /**
     * 크로스 사이트 스크립트(XSS) 보안
     * 
     * @param http
     *            request
     * @return 보안된 파라메터를 담은 Map
     */
    public static Map<String, String[]> getParameters(HttpServletRequest request) {
        String key = null;
        String[] values = null;
        Map<String, String[]> securedParamMap = new HashMap<String, String[]>();

        @SuppressWarnings("unchecked")
        Enumeration<String> keyset = request.getParameterNames();

        for (; keyset.hasMoreElements();) {
            key = keyset.nextElement();
            values = request.getParameterValues(key);

            String[] securedParamValues = new String[values.length];

            for (int i = 0; i < securedParamValues.length; i++) {
                securedParamValues[i] = (String) SecureUtils.secure(values[i]);
                // securedParamValues[i] = SecureUtils.secure(((String[]) values)[i]);
            }

            securedParamMap.put(key, securedParamValues);
        }

        return securedParamMap;
    }

    public static JSONObject getSecuredJSONObject(HttpServletRequest request, String param) {
        String paramVal = request.getParameter(param);
        JSONObject json = JSONObject.fromObject(paramVal);

        return SecureUtils.getSecuredJSONObject(json);
    }

    public static JSONObject getSecuredJSONObject(JSONObject json) {
        JSONObject secured = new JSONObject();
        Object[] keys = json.keySet().toArray();

        for (int index = 0; index < keys.length; index++) {
            secured.put(keys[index], SecureUtils.secure(json.get(keys[index])));
            // secured.put(keys[index], SecureUtils.secure((String) json.get(keys[index])));
        }

        return secured;
    }

    public static JSONArray getSecuredJSONArray(HttpServletRequest request, String param) {
        String paramVal = request.getParameter(param);
        JSONArray jsonArray = JSONArray.fromObject(paramVal);

        return SecureUtils.getSecuredJSONArray(jsonArray);
    }

    public static JSONArray getSecuredJSONArray(JSONArray jsonArray) {
        JSONArray secured = new JSONArray();

        for (int index = 0; index < jsonArray.size(); index++) {
            secured.add(index, SecureUtils.getSecuredJSONObject(jsonArray.getJSONObject(index)));
        }

        return secured;
    }

    public static void addHeader(HttpServletResponse response, String headerName, String headerValue) {
    }

    public static String getHeader(HttpServletRequest request, String headerName) {
        return SecureUtils.getHeader(request, headerName, "");
    }

    public static String getHeader(HttpServletRequest request, String headerName, String dephault) {
        String header = request.getHeader(headerName);
        header = (String) SecureUtils.secure(header);

        if (header == null || "".equals(header)) {
            header = dephault;
        }

        return header;
    }

    public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, int maxAge) {
    }

    public static void addCookie(HttpServletResponse response, Cookie cookie) {
    }

    public static String encode(String qwer, int round) {
        qwer += CoreUtils.getRandom(3);

        for (int index = 0; index < round; index++) {
            qwer = new String(Base64.encode(qwer.getBytes()));
        }

        String t1 = qwer.substring(0, 4);
        String t2 = qwer.substring(4);

        String[] caret = new String[] { "o", "v", "x", "!", "b", "8", "3", "m", "f", "9" };
        int chip = Integer.valueOf(CoreUtils.getRandom(1));

        qwer = t1 + caret[chip] + t2;
        qwer = new String(Base64.encode(qwer.getBytes()));

        return qwer;
    }

    public static String decode(String qwer, int round) {
        qwer = new String(Base64.decode(qwer.getBytes()));

        String t1 = qwer.substring(0, 4);
        String t2 = qwer.substring(5);

        qwer = t1 + t2;

        for (int index = 0; index < round; index++) {
            qwer = new String(Base64.decode(qwer.getBytes()));
        }

        qwer = qwer.substring(0, qwer.length() - 3);

        return qwer;
    }

    public static String sha256(String plain) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(plain.getBytes(Configurator.getInstance().getConfig("encoding")));
//        byte[] hash = digest.digest(plain.getBytes("utf-8"));

        return new String(Base64.encode(hash));
    }
//    public static String sha256(String plainText) throws Exception {
//        MessageDigest digest = MessageDigest.getInstance("SHA-256");
//        byte[] hash = digest.digest(plainText.getBytes("UTF-8"));
//        StringBuffer hexString = new StringBuffer();
//
//        for (int i = 0; i < hash.length; i++) {
//            String hex = Integer.toHexString(0xff & hash[i]);
//            if (hex.length() == 1)
//                hexString.append('0');
//            hexString.append(hex);
//        }
//
//        return hexString.toString();
//    }
    
    public static String encSeed(String key, String plain) throws Exception {
        key = SecureUtils.decode(key, Configurator.Constants.SEED_CBC_ENCRIPTION_KEY_ROUND);
        
        int[] seedKey = SEED.getSeedRoundKey(key);
        String cipher = SEED.getSeedEncrypt(plain, seedKey);
        
        return cipher;
    }
    
    public static String decSeed(String key, String cipher) throws Exception {
        key = SecureUtils.decode(key, Configurator.Constants.SEED_CBC_ENCRIPTION_KEY_ROUND);
        
        int[] seedKey = SEED.getSeedRoundKey(key);
        String palin = SEED.getSeedDecrypt(cipher, seedKey);
        
        return palin;
    }
    
    public static void main(String[] s) throws Exception {
        String key = Configurator.Constants.SEED_CBC_ENCRIPTION_KEY;
        
//        System.out.println("jdbc url = " + SecureUtils.encSeed(key, "jdbc:oracle:thin:@(DESCRIPTION=(ADDERSS=(PROTOCOL=TCP)(HOST=10.20.120.53)(PORT=1521)) (ADDRESS=(PROTOCOL=TCP)(HOST=10.20.120.54)(PORT=1521))(LOAD_BALANCE=NO)(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=LDDW)))"));
//        System.out.println("pwd(Qwer1234%) = " + SecureUtils.encSeed(key, "Qwer1234%"));
//        
//        System.out.println("jdbc url = " + SecureUtils.encSeed(key, "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=172.24.1.190)(PORT=1521))(CONNECT_DATA=(GLOBAL_NAME=hwtest.WORLD)(SID=hwtest)))"));
//        System.out.println("TESTSYN = " + SecureUtils.encSeed(key, "TESTSYN"));
//        System.out.println("eiscon1 = " + SecureUtils.encSeed(key, "eiscon1"));
        
        
        System.out.println(SecureUtils.encSeed(key,"qwert1234"));
//        System.out.println(SecureUtils.decSeed(key,"Pe4lb21lcZ/30uyAliS8Zg=="));
        
        
        
//        System.out.println("jdbc url = " + SecureUtils.encSeed(key, "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS_LIST=(ADDRESS=(COMMUNITY=SAP.WORLD)(PROTOCOL=TCP)(HOST=172.24.1.206)(PORT=1526)))(CONNECT_DATA=(SID=GAP)(GLOBAL_NAME=GAP.WORLD)))"));
//        System.out.println("sapsr3db = " + SecureUtils.encSeed(key, "sapsr3db"));
//        System.out.println("uasadmin = " + SecureUtils.encSeed(key, "uasadmin"));
//        System.out.println("jdbc:oracle:thin:hwgbeadev:1521:GAD.WORLD = " + SecureUtils.encSeed(key, "jdbc:oracle:thin:hwgbeadev:1521:GAD.WORLD"));
//        System.out.println("jdbc:oracle:thin:172.24.1.98:1526:GAD = " + SecureUtils.encSeed(key, "jdbc:oracle:thin:172.24.1.98:1526:GAD"));
        
//        System.out.println("SWG8Re55V26l6hUwDDv5nNfUKTDEeg2gI+yzVlYOxUsQHsqrgn3TYBude9I5GBle = " + SecureUtils.decSeed(key, "SWG8Re55V26l6hUwDDv5nNfUKTDEeg2gI+yzVlYOxUsQHsqrgn3TYBude9I5GBle"));
        
    }
    
}
