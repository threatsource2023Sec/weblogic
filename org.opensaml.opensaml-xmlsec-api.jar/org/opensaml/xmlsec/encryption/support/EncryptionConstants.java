package org.opensaml.xmlsec.encryption.support;

public final class EncryptionConstants {
   public static final String XMLENC_NS = "http://www.w3.org/2001/04/xmlenc#";
   public static final String XMLENC_PREFIX = "xenc";
   public static final String TYPE_CONTENT = "http://www.w3.org/2001/04/xmlenc#Content";
   public static final String TYPE_ELEMENT = "http://www.w3.org/2001/04/xmlenc#Element";
   public static final String TYPE_ENCRYPTION_PROPERTIES = "http://www.w3.org/2001/04/xmlenc#EncryptionProperties";
   public static final String TYPE_ENCRYPTED_KEY = "http://www.w3.org/2001/04/xmlenc#EncryptedKey";
   public static final String TYPE_KEYINFO_DH_KEYVALUE = "http://www.w3.org/2001/04/xmlenc#DHKeyValue";
   public static final String ALGO_ID_BLOCKCIPHER_TRIPLEDES = "http://www.w3.org/2001/04/xmlenc#tripledes-cbc";
   public static final String ALGO_ID_BLOCKCIPHER_AES128 = "http://www.w3.org/2001/04/xmlenc#aes128-cbc";
   public static final String ALGO_ID_BLOCKCIPHER_AES256 = "http://www.w3.org/2001/04/xmlenc#aes256-cbc";
   public static final String ALGO_ID_BLOCKCIPHER_AES192 = "http://www.w3.org/2001/04/xmlenc#aes192-cbc";
   public static final String ALGO_ID_KEYTRANSPORT_RSA15 = "http://www.w3.org/2001/04/xmlenc#rsa-1_5";
   public static final String ALGO_ID_KEYTRANSPORT_RSAOAEP = "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p";
   public static final String ALGO_ID_KEYAGREEMENT_DH = "http://www.w3.org/2001/04/xmlenc#dh";
   public static final String ALGO_ID_KEYWRAP_TRIPLEDES = "http://www.w3.org/2001/04/xmlenc#kw-tripledes";
   public static final String ALGO_ID_KEYWRAP_AES128 = "http://www.w3.org/2001/04/xmlenc#kw-aes128";
   public static final String ALGO_ID_KEYWRAP_AES256 = "http://www.w3.org/2001/04/xmlenc#kw-aes256";
   public static final String ALGO_ID_KEYWRAP_AES192 = "http://www.w3.org/2001/04/xmlenc#kw-aes192";
   public static final String ALGO_ID_DIGEST_SHA256 = "http://www.w3.org/2001/04/xmlenc#sha256";
   public static final String ALGO_ID_DIGEST_SHA512 = "http://www.w3.org/2001/04/xmlenc#sha512";
   public static final String ALGO_ID_DIGEST_RIPEMD160 = "http://www.w3.org/2001/04/xmlenc#ripemd160";
   public static final String XMLENC11_NS = "http://www.w3.org/2009/xmlenc11#";
   public static final String XMLENC11_PREFIX = "xenc11";
   public static final String ALGO_ID_KEYTRANSPORT_RSAOAEP11 = "http://www.w3.org/2009/xmlenc11#rsa-oaep";
   public static final String ALGO_ID_BLOCKCIPHER_AES128_GCM = "http://www.w3.org/2009/xmlenc11#aes128-gcm";
   public static final String ALGO_ID_BLOCKCIPHER_AES192_GCM = "http://www.w3.org/2009/xmlenc11#aes192-gcm";
   public static final String ALGO_ID_BLOCKCIPHER_AES256_GCM = "http://www.w3.org/2009/xmlenc11#aes256-gcm";
   public static final String ALGO_ID_MGF1_SHA1 = "http://www.w3.org/2009/xmlenc11#mgf1sha1";
   public static final String ALGO_ID_MGF1_SHA224 = "http://www.w3.org/2009/xmlenc11#mgf1sha224";
   public static final String ALGO_ID_MGF1_SHA256 = "http://www.w3.org/2009/xmlenc11#mgf1sha256";
   public static final String ALGO_ID_MGF1_SHA384 = "http://www.w3.org/2009/xmlenc11#mgf1sha384";
   public static final String ALGO_ID_MGF1_SHA512 = "http://www.w3.org/2009/xmlenc11#mgf1sha512";
   public static final String TYPE_DERIVED_KEY = "http://www.w3.org/2009/xmlenc11#DerivedKey";

   private EncryptionConstants() {
   }
}
