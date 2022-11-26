package netscape.ldap;

import java.io.Serializable;

public class LDAPRebindAuth implements Serializable {
   static final long serialVersionUID = 7161655313564756294L;
   private String m_dn;
   private String m_password;

   public LDAPRebindAuth(String var1, String var2) {
      this.m_dn = var1;
      this.m_password = var2;
   }

   public String getDN() {
      return this.m_dn;
   }

   public String getPassword() {
      return this.m_password;
   }
}
