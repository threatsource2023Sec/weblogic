package netscape.ldap;

public class LDAPInterruptedException extends LDAPException {
   static final long serialVersionUID = 5267455101797397456L;

   LDAPInterruptedException(String var1) {
      super(var1, 80, (String)null);
   }

   public String toString() {
      String var1 = "netscape.ldap.LDAPInterruptedException: ";
      String var2 = super.getMessage();
      if (var2 != null) {
         var1 = var1 + var2;
      }

      return var1;
   }
}
