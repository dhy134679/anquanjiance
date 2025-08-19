package cn.iocoder.yudao.module.ssm.listener;
import cn.hutool.http.HttpUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Base64;

public class AesDecryptUtil {

    private static final String KEY = "0123456789abcdef"; // 16 bytes
    private static final String IV  = "abcdef9876543210"; // 16 bytes
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    public static String decrypt(String base64CipherText) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decoded = Base64.getDecoder().decode(base64CipherText);
        byte[] plain   = cipher.doFinal(decoded);
        return new String(plain, StandardCharsets.UTF_8);
    }


    public static String decryptFile(String filePath) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decoded = Files.readAllBytes(Paths.get(filePath));
        byte[] plain   = cipher.doFinal(Arrays.copyOfRange(decoded, 16, decoded.length));
        String outputFilePath = filePath.replace(".enc","");
        Files.write(Paths.get(outputFilePath), plain);
        return outputFilePath;
    }


    public static byte[] decryptByte(byte[] decoded) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(StandardCharsets.UTF_8), "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes(StandardCharsets.UTF_8));
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] plain   = cipher.doFinal(Arrays.copyOfRange(decoded, 16, decoded.length));
        return plain;
    }

}
