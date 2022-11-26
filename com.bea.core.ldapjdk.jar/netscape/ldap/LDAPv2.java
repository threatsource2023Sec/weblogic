package netscape.ldap;

public interface LDAPv2 {
   int DEFAULT_PORT = 389;
   int DEREF = 2;
   int SIZELIMIT = 3;
   int TIMELIMIT = 4;
   int SERVER_TIMELIMIT = 5;
   int REFERRALS = 8;
   int REFERRALS_REBIND_PROC = 9;
   int REFERRALS_HOP_LIMIT = 10;
   int BIND = 13;
   int PROTOCOL_VERSION = 17;
   int BATCHSIZE = 20;
   int SCOPE_BASE = 0;
   int SCOPE_ONE = 1;
   int SCOPE_SUB = 2;
   int DEREF_NEVER = 0;
   int DEREF_SEARCHING = 1;
   int DEREF_FINDING = 2;
   int DEREF_ALWAYS = 3;

   void connect(String var1, int var2) throws LDAPException;

   void connect(String var1, int var2, String var3, String var4) throws LDAPException;

   void disconnect() throws LDAPException;

   void abandon(LDAPSearchResults var1) throws LDAPException;

   void authenticate(String var1, String var2) throws LDAPException;

   void bind(String var1, String var2) throws LDAPException;

   LDAPEntry read(String var1) throws LDAPException;

   LDAPEntry read(String var1, String[] var2) throws LDAPException;

   LDAPEntry read(String var1, String[] var2, LDAPSearchConstraints var3) throws LDAPException;

   LDAPSearchResults search(String var1, int var2, String var3, String[] var4, boolean var5) throws LDAPException;

   LDAPSearchResults search(String var1, int var2, String var3, String[] var4, boolean var5, LDAPSearchConstraints var6) throws LDAPException;

   boolean compare(String var1, LDAPAttribute var2) throws LDAPException;

   boolean compare(String var1, LDAPAttribute var2, LDAPConstraints var3) throws LDAPException;

   void add(LDAPEntry var1) throws LDAPException;

   void add(LDAPEntry var1, LDAPConstraints var2) throws LDAPException;

   void modify(String var1, LDAPModification var2) throws LDAPException;

   void modify(String var1, LDAPModification var2, LDAPConstraints var3) throws LDAPException;

   void modify(String var1, LDAPModificationSet var2) throws LDAPException;

   void modify(String var1, LDAPModificationSet var2, LDAPConstraints var3) throws LDAPException;

   void delete(String var1) throws LDAPException;

   void delete(String var1, LDAPConstraints var2) throws LDAPException;

   void rename(String var1, String var2, boolean var3) throws LDAPException;

   void rename(String var1, String var2, boolean var3, LDAPConstraints var4) throws LDAPException;

   Object getOption(int var1) throws LDAPException;

   void setOption(int var1, Object var2) throws LDAPException;
}
