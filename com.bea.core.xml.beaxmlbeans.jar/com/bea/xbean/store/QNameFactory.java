package com.bea.xbean.store;

import javax.xml.namespace.QName;

public interface QNameFactory {
   QName getQName(String var1, String var2);

   QName getQName(String var1, String var2, String var3);

   QName getQName(char[] var1, int var2, int var3, char[] var4, int var5, int var6);

   QName getQName(char[] var1, int var2, int var3, char[] var4, int var5, int var6, char[] var7, int var8, int var9);
}
