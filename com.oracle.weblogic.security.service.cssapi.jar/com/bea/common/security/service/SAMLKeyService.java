package com.bea.common.security.service;

public interface SAMLKeyService {
   boolean isKeystoreAccessible();

   SAMLKeyInfoSpi getKeyInfo(String var1, String var2, char[] var3);

   SAMLKeyInfoSpi getKeyInfo(String var1);

   SAMLKeyInfoSpi getDefaultKeyInfo();

   SAMLKeyInfoSpi getDefaultKeyInfo(String var1);
}
