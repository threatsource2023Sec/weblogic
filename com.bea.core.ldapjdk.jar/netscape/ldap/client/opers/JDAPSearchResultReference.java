package netscape.ldap.client.opers;

import java.io.IOException;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.ber.stream.BERTag;

public class JDAPSearchResultReference implements JDAPProtocolOp {
   protected String[] m_urls = null;
   protected BERElement m_element = null;

   public JDAPSearchResultReference(BERElement var1) throws IOException {
      this.m_element = var1;
      BERSequence var2 = (BERSequence)((BERTag)var1).getValue();
      if (var2.size() >= 0) {
         this.m_urls = new String[var2.size()];

         for(int var3 = 0; var3 < var2.size(); ++var3) {
            BEROctetString var4 = (BEROctetString)var2.elementAt(var3);
            this.m_urls[var3] = new String(var4.getValue(), "UTF8");
         }

      }
   }

   public int getType() {
      return 19;
   }

   public BERElement getBERElement() {
      return this.m_element;
   }

   public String[] getUrls() {
      return this.m_urls;
   }

   public String toString() {
      String var1 = "";
      if (this.m_urls != null) {
         for(int var2 = 0; var2 < this.m_urls.length; ++var2) {
            if (var2 != 0) {
               var1 = var1 + ",";
            }

            var1 = var1 + this.m_urls[var2];
         }
      }

      return "SearchResultReference " + var1;
   }
}
