package netscape.ldap;

public interface LDAPAsynchronousConnection {
   LDAPResponseListener add(LDAPEntry var1, LDAPResponseListener var2) throws LDAPException;

   LDAPResponseListener add(LDAPEntry var1, LDAPResponseListener var2, LDAPConstraints var3) throws LDAPException;

   LDAPResponseListener bind(String var1, String var2, LDAPResponseListener var3) throws LDAPException;

   LDAPResponseListener bind(String var1, String var2, LDAPResponseListener var3, LDAPConstraints var4) throws LDAPException;

   LDAPResponseListener delete(String var1, LDAPResponseListener var2) throws LDAPException;

   LDAPResponseListener delete(String var1, LDAPResponseListener var2, LDAPConstraints var3) throws LDAPException;

   LDAPResponseListener modify(String var1, LDAPModification var2, LDAPResponseListener var3) throws LDAPException;

   LDAPResponseListener modify(String var1, LDAPModification var2, LDAPResponseListener var3, LDAPConstraints var4) throws LDAPException;

   LDAPResponseListener modify(String var1, LDAPModificationSet var2, LDAPResponseListener var3) throws LDAPException;

   LDAPResponseListener modify(String var1, LDAPModificationSet var2, LDAPResponseListener var3, LDAPConstraints var4) throws LDAPException;

   LDAPResponseListener rename(String var1, String var2, boolean var3, LDAPResponseListener var4) throws LDAPException;

   LDAPResponseListener rename(String var1, String var2, boolean var3, LDAPResponseListener var4, LDAPConstraints var5) throws LDAPException;

   LDAPSearchListener search(String var1, int var2, String var3, String[] var4, boolean var5, LDAPSearchListener var6) throws LDAPException;

   LDAPSearchListener search(String var1, int var2, String var3, String[] var4, boolean var5, LDAPSearchListener var6, LDAPSearchConstraints var7) throws LDAPException;

   LDAPResponseListener compare(String var1, LDAPAttribute var2, LDAPResponseListener var3) throws LDAPException;

   LDAPResponseListener compare(String var1, LDAPAttribute var2, LDAPResponseListener var3, LDAPConstraints var4) throws LDAPException;

   void abandon(int var1) throws LDAPException;

   void abandon(LDAPSearchListener var1) throws LDAPException;
}
