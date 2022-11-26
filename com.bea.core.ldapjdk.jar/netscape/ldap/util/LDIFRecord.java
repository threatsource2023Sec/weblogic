package netscape.ldap.util;

import java.io.Serializable;
import netscape.ldap.LDAPControl;

public class LDIFRecord implements Serializable {
   private String m_dn = null;
   private LDIFBaseContent m_content = null;
   static final long serialVersionUID = -6537481934870076178L;

   public LDIFRecord(String var1, LDIFContent var2) {
      this.m_dn = var1;
      this.m_content = (LDIFBaseContent)var2;
   }

   public String getDN() {
      return this.m_dn;
   }

   public LDIFContent getContent() {
      return this.m_content;
   }

   public LDAPControl[] getControls() {
      return this.m_content == null ? null : this.m_content.getControls();
   }

   public String toString() {
      return "LDIFRecord {dn=" + this.m_dn + ", content=" + this.m_content + "}";
   }
}
