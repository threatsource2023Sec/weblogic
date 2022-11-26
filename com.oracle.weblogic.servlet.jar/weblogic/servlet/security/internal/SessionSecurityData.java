package weblogic.servlet.security.internal;

public interface SessionSecurityData {
   String getInternalId();

   String getIdWithServerInfo();

   boolean isValid();

   void invalidate();

   Object getInternalAttribute(String var1) throws IllegalStateException;

   void setInternalAttribute(String var1, Object var2) throws IllegalStateException, IllegalArgumentException;

   void removeInternalAttribute(String var1) throws IllegalStateException;

   int getConcurrentRequestCount();
}
