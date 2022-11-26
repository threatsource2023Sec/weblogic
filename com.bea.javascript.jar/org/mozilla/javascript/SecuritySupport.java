package org.mozilla.javascript;

public interface SecuritySupport {
   Class defineClass(String var1, byte[] var2, Object var3);

   Class[] getClassContext();

   Object getSecurityDomain(Class var1);

   boolean visibleToScripts(String var1);
}
