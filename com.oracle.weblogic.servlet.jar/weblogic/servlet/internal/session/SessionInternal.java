package weblogic.servlet.internal.session;

import java.util.Enumeration;
import weblogic.servlet.security.internal.SessionSecurityData;

public interface SessionInternal extends SessionSecurityData {
   SessionContext getContext();

   long getLAT();

   void setLastAccessedTime(long var1);

   void setNew(boolean var1);

   void setVersionId(String var1);

   String getVersionId();

   Enumeration getInternalAttributeNames();

   boolean hasStateAttributes();

   boolean isExpiring();

   boolean isInvalidating();

   String changeSessionId(String var1);
}
