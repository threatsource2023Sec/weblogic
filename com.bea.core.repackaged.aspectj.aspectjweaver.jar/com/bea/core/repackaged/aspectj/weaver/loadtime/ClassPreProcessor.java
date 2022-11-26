package com.bea.core.repackaged.aspectj.weaver.loadtime;

import java.security.ProtectionDomain;

public interface ClassPreProcessor {
   void initialize();

   byte[] preProcess(String var1, byte[] var2, ClassLoader var3, ProtectionDomain var4);
}
