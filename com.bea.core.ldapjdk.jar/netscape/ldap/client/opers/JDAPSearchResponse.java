package netscape.ldap.client.opers;

import java.io.IOException;
import netscape.ldap.LDAPAttribute;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.ber.stream.BERTag;

public class JDAPSearchResponse implements JDAPProtocolOp {
   protected String m_object_name = null;
   protected BERElement m_element = null;
   protected LDAPAttribute[] m_attributes = null;

   public JDAPSearchResponse(BERElement var1) throws IOException {
      this.m_element = var1;
      BERTag var2 = (BERTag)var1;
      BERSequence var3 = (BERSequence)var2.getValue();
      BEROctetString var4 = (BEROctetString)var3.elementAt(0);
      Object var5 = null;
      byte[] var9 = var4.getValue();
      if (var9 == null) {
         this.m_object_name = null;
      } else {
         try {
            this.m_object_name = new String(var9, "UTF8");
         } catch (Throwable var8) {
         }
      }

      BERSequence var6 = (BERSequence)var3.elementAt(1);
      if (var6.size() > 0) {
         this.m_attributes = new LDAPAttribute[var6.size()];

         for(int var7 = 0; var7 < var6.size(); ++var7) {
            this.m_attributes[var7] = new LDAPAttribute(var6.elementAt(var7));
         }
      }

   }

   public BERElement getBERElement() {
      return this.m_element;
   }

   public String getObjectName() {
      return this.m_object_name;
   }

   public LDAPAttribute[] getAttributes() {
      return this.m_attributes;
   }

   public int getType() {
      return 4;
   }

   public String toString() {
      String var1 = "";
      if (this.m_attributes != null) {
         for(int var2 = 0; var2 < this.m_attributes.length; ++var2) {
            if (var2 != 0) {
               var1 = var1 + ",";
            }

            var1 = var1 + this.m_attributes[var2].toString();
         }
      }

      return "SearchResponse {entry='" + this.m_object_name + "', attributes='" + var1 + "'}";
   }
}
