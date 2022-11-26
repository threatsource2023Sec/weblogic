package monfox.toolkit.snmp.v3.usm;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

interface a {
   int ENCRYPT = 0;
   int DECRYPT = 1;

   void setTransform(String var1);

   byte[] crypt(int var1, byte[] var2, byte[] var3, byte[] var4) throws GeneralSecurityException;

   MessageDigest getMessageDigest(String var1) throws NoSuchAlgorithmException;
}
