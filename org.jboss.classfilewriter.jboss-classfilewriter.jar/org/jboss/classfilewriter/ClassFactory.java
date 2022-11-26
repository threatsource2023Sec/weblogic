package org.jboss.classfilewriter;

import java.security.ProtectionDomain;

public interface ClassFactory {
   Class defineClass(ClassLoader var1, String var2, byte[] var3, int var4, int var5, ProtectionDomain var6) throws ClassFormatError;
}
