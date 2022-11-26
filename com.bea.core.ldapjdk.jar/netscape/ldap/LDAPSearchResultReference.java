package netscape.ldap;

import netscape.ldap.client.opers.JDAPSearchResultReference;

public class LDAPSearchResultReference extends LDAPMessage {
   static final long serialVersionUID = -7816778029315223117L;
   private String[] m_URLs;

   LDAPSearchResultReference(int var1, JDAPSearchResultReference var2, LDAPControl[] var3) {
      super(var1, var2, var3);
      this.m_URLs = var2.getUrls();
   }

   public String[] getUrls() {
      return this.m_URLs;
   }
}
