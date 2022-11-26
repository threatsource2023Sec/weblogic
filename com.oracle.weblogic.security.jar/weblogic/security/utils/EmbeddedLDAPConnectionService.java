package weblogic.security.utils;

import netscape.ldap.LDAPCache;
import netscape.ldap.LDAPException;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface EmbeddedLDAPConnectionService {
   void setProperty(String var1, Object var2) throws LDAPException;

   void connect(String var1, int var2) throws LDAPException;

   void bind(int var1, String var2, String var3) throws LDAPException;

   void setCache(LDAPCache var1);
}
