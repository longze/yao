package com.news.www.test;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * des���ܽ���
 */
public class desTest {
    private static String strDefaultKey = "PLFP"; //Ĭ����Կ

    private static final byte[] iv = {0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte) 0xab, (byte) 0xcd, (byte) 0xef};//des ����

    private static BASE64Encoder enc = new BASE64Encoder();//��byte[]ת����String

    private static BASE64Decoder dec = new BASE64Decoder(); //��Stringת����byte[]

    /**
     * �����ֽ�����
     *
     * @param arrB
     *            ����ܵ��ֽ�����
     * @param key
     *            ��Կ
     * @return ���ܺ���ֽ�����
     * @throws Exception
     */
    public static byte[] encrypt(byte[] arrB, String key) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec ivp = new IvParameterSpec(desTest.iv);

        Cipher encryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey, ivp);

        return encryptCipher.doFinal(arrB);
    }

    /**
     * �����ַ���
     *
     * @param xml
     *            ����ܵ��ַ���
     * @param key
     *            ��Կ
     * @return ���ܺ���ַ���
     * @throws Exception
     */
    public static String encrypt(String xml, String key) throws Exception {
        //return DESPlus.enc.encode(encrypt(xml.getBytes(), key));
	return new String(encrypt(xml.getBytes(), key)); 
    }

    /**
     * ʹ��Ĭ�Ϲ�Կ�����ַ���
     * @param xml ����ܵ��ַ���
     * @return ���ܺ���ַ���
     * @throws Exception
     */
    public static String encrypt(String xml) throws Exception {
        return encrypt(xml, strDefaultKey);
    }

    /**
     * �����ֽ�����
     *
     * @param arrB
     *            ����ܵ��ֽ�����
     * @param key
     *            ��Կ
     * @return ���ܺ���ֽ�����
     * @throws Exception
     */
    public static byte[] decrypt(byte[] arrB, String key) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes());

        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec ivp = new IvParameterSpec(desTest.iv);

        Cipher decryptCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        decryptCipher.init(Cipher.DECRYPT_MODE, secretKey, ivp);

        return decryptCipher.doFinal(arrB);
    }

    /**
     * �����ַ���
     *
     * @param xml
     *            ����ܵ��ַ���
     * @param key
     *            ��Կ
     * @return ���ܺ���ַ���
     * @throws Exception
     */
    public static String decrypt(String xml, String key) throws Exception {
        return new String(decrypt(desTest.dec.decodeBuffer(xml), key));
    }

    /**
     * ʹ��Ĭ�Ϲ�Կ�����ַ���
     * @param xml ����ܵ��ַ���
     * @return ���ܺ���ַ���
     * @throws Exception
     */
    public static String decrypt(String xml) throws Exception {
        return decrypt(xml, strDefaultKey);
    }

    /**
     * ��ָ���ַ���������Կ����Կ������ֽ����鳤��Ϊ8λ ����8λʱ���油0������8λֻȡǰ8λ
     *
     * @param arrBTmp
     *            ���ɸ��ַ������ֽ�����
     * @return ���ɵ���Կ
     * @throws java.lang.Exception
     */
    private Key getKey(byte[] arrBTmp) throws Exception {
        // ����һ���յ�8λ�ֽ����飨Ĭ��ֵΪ0��
        byte[] arrB = new byte[8];

        // ��ԭʼ�ֽ�����ת��Ϊ8λ
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }

        // ������Կ
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

        return key;
    }

    /**
     * ��ȡĬ����Կ
     * @return
     */
    public static String getDesKey() {
        return desTest.strDefaultKey;
    }

    public static void main(String[] args) {
        try {
            //�����ܳ�
            String key = "BOC_PLFP";
            //004���װ���
            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><ROOT><HEADER><TRANNO>004</TRANNO><BNKNO>17850</BNKNO><COMNO>COM01</COMNO><SN>1109141222222660161</SN></HEADER><BODY><TRANLOG><APPNO>0000000123</APPNO><NOTATION>�ͻ���Ϣ���������벹�����ϡ�</NOTATION></TRANLOG></BODY></ROOT>";

            System.out.println("��Կ��" + key);
            System.out.println("����ǰ���ַ�����" + xml);
            System.out.println("����ǰ���ַ������ȣ�" + xml.getBytes().length);

            //����
            xml = desTest.encrypt(xml, key);
            System.out.println("���ܺ���ַ�����" + xml);
            System.out.println("���ܺ���ַ������ȣ�" + xml.getBytes().length);

            //����
            xml = desTest.decrypt(xml, key);
            System.out.println("���ܺ���ַ�����" + xml);
            System.out.println("���ܺ���ַ������ȣ�" + xml.getBytes().length);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}