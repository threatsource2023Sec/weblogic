package org.apache.xml.security.encryption;

public interface XMLCipherParameters {
   String AES_128 = "http://www.w3.org/2001/04/xmlenc#aes128-cbc";
   String AES_256 = "http://www.w3.org/2001/04/xmlenc#aes256-cbc";
   String AES_192 = "http://www.w3.org/2001/04/xmlenc#aes192-cbc";
   String RSA_1_5 = "http://www.w3.org/2001/04/xmlenc#rsa-1_5";
   String RSA_OAEP = "http://www.w3.org/2001/04/xmlenc#rsa-oaep-mgf1p";
   String DIFFIE_HELLMAN = "http://www.w3.org/2001/04/xmlenc#dh";
   String TRIPLEDES_KEYWRAP = "http://www.w3.org/2001/04/xmlenc#kw-tripledes";
   String AES_128_KEYWRAP = "http://www.w3.org/2001/04/xmlenc#kw-aes128";
   String AES_256_KEYWRAP = "http://www.w3.org/2001/04/xmlenc#kw-aes256";
   String AES_192_KEYWRAP = "http://www.w3.org/2001/04/xmlenc#kw-aes192";
   String SHA1 = "http://www.w3.org/2000/09/xmldsig#sha1";
   String SHA256 = "http://www.w3.org/2001/04/xmlenc#sha256";
   String SHA512 = "http://www.w3.org/2001/04/xmlenc#sha512";
   String RIPEMD_160 = "http://www.w3.org/2001/04/xmlenc#ripemd160";
   String XML_DSIG = "http://www.w3.org/2000/09/xmldsig#";
   String N14C_XML = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
   String N14C_XML_CMMNTS = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments";
   String EXCL_XML_N14C = "http://www.w3.org/2001/10/xml-exc-c14n#";
   String EXCL_XML_N14C_CMMNTS = "http://www.w3.org/2001/10/xml-exc-c14n#WithComments";
}
