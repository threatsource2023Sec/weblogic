package netscape.ldap;

public interface LDAPv3 extends LDAPv2 {
   int CLIENTCONTROLS = 11;
   int SERVERCONTROLS = 12;
   String NO_ATTRS = "1.1";
   String ALL_USER_ATTRS = "*";

   void connect(int var1, String var2, int var3, String var4, String var5) throws LDAPException;

   void authenticate(int var1, String var2, String var3) throws LDAPException;

   void bind(int var1, String var2, String var3) throws LDAPException;

   LDAPExtendedOperation extendedOperation(LDAPExtendedOperation var1) throws LDAPException;

   void rename(String var1, String var2, String var3, boolean var4) throws LDAPException;

   void rename(String var1, String var2, String var3, boolean var4, LDAPConstraints var5) throws LDAPException;

   LDAPControl[] getResponseControls();
}
