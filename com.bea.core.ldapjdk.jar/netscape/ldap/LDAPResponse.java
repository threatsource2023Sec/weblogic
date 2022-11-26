package netscape.ldap;

import netscape.ldap.client.opers.JDAPProtocolOp;
import netscape.ldap.client.opers.JDAPResult;

public class LDAPResponse extends LDAPMessage {
   static final long serialVersionUID = 5822205242593427418L;

   LDAPResponse(int var1, JDAPProtocolOp var2, LDAPControl[] var3) {
      super(var1, var2, var3);
   }

   public String getErrorMessage() {
      JDAPResult var1 = (JDAPResult)this.getProtocolOp();
      return var1.getErrorMessage();
   }

   public String getMatchedDN() {
      JDAPResult var1 = (JDAPResult)this.getProtocolOp();
      return var1.getMatchedDN();
   }

   public String[] getReferrals() {
      JDAPResult var1 = (JDAPResult)this.getProtocolOp();
      return var1.getReferrals();
   }

   public int getResultCode() {
      JDAPResult var1 = (JDAPResult)this.getProtocolOp();
      return var1.getResultCode();
   }
}
