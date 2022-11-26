package netscape.ldap;

import java.io.Serializable;
import netscape.ldap.client.opers.JDAPExtendedResponse;

public class LDAPExtendedResponse extends LDAPResponse implements Serializable {
   static final long serialVersionUID = -3813049515964705320L;

   LDAPExtendedResponse(int var1, JDAPExtendedResponse var2, LDAPControl[] var3) {
      super(var1, var2, var3);
   }

   public String getID() {
      JDAPExtendedResponse var1 = (JDAPExtendedResponse)this.getProtocolOp();
      return var1.getID();
   }

   /** @deprecated */
   public String getOID() {
      return this.getID();
   }

   public byte[] getValue() {
      JDAPExtendedResponse var1 = (JDAPExtendedResponse)this.getProtocolOp();
      return var1.getValue();
   }
}
