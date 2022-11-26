package netscape.ldap;

import java.io.Serializable;

public class LDAPExtendedOperation implements Serializable {
   static final long serialVersionUID = 4010382829133611945L;
   private String m_oid;
   private byte[] m_vals;

   public LDAPExtendedOperation(String var1, byte[] var2) {
      this.m_oid = var1;
      this.m_vals = var2;
   }

   public String getID() {
      return this.m_oid;
   }

   public byte[] getValue() {
      return this.m_vals;
   }
}
