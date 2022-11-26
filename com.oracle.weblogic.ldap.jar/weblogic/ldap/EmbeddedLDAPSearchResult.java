package weblogic.ldap;

import netscape.ldap.LDAPEntry;

public class EmbeddedLDAPSearchResult {
   private LDAPEntry entry;

   EmbeddedLDAPSearchResult(LDAPEntry entry) {
      this.entry = entry;
   }

   public LDAPEntry getEntry() {
      return this.entry;
   }
}
