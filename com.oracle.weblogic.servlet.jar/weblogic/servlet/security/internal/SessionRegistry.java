package weblogic.servlet.security.internal;

import weblogic.servlet.spi.SubjectHandle;

public interface SessionRegistry {
   SubjectHandle getUser(String var1);

   void setUser(String var1, SubjectHandle var2);

   void addCookieId(String var1, String var2);

   String getCookieId(String var1);

   void unregister(String var1);
}
