package netscape.ldap;

import netscape.ldap.client.opers.JDAPSearchResponse;

public class LDAPSearchResult extends LDAPMessage {
   static final long serialVersionUID = 36890821518462301L;
   private LDAPEntry m_entry;

   LDAPSearchResult(int var1, JDAPSearchResponse var2, LDAPControl[] var3) {
      super(var1, var2, var3);
   }

   public LDAPEntry getEntry() {
      if (this.m_entry == null) {
         JDAPSearchResponse var1 = (JDAPSearchResponse)this.getProtocolOp();
         LDAPAttribute[] var2 = var1.getAttributes();
         LDAPAttributeSet var3;
         if (var2 != null) {
            var3 = new LDAPAttributeSet(var2);
         } else {
            var3 = new LDAPAttributeSet();
         }

         String var4 = var1.getObjectName();
         this.m_entry = new LDAPEntry(var4, var3);
      }

      return this.m_entry;
   }
}
