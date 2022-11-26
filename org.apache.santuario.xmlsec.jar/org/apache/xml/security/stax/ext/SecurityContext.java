package org.apache.xml.security.stax.ext;

import java.util.List;
import java.util.Map;
import org.apache.xml.security.stax.securityEvent.SecurityEventListener;

public interface SecurityContext extends SecurityEventListener {
   void put(String var1, Object var2);

   Object get(String var1);

   Object remove(String var1);

   void putList(Object var1, List var2);

   void putAsList(Object var1, Object var2);

   List getAsList(Object var1);

   void putAsMap(Object var1, Object var2, Object var3);

   Map getAsMap(Object var1);

   void addSecurityEventListener(SecurityEventListener var1);
}
