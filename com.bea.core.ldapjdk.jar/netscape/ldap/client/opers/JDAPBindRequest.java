package netscape.ldap.client.opers;

import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BERInteger;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.ber.stream.BERTag;

public class JDAPBindRequest implements JDAPProtocolOp {
   protected int m_version;
   protected String m_name = null;
   protected String m_password = null;
   protected String m_mechanism = null;
   protected byte[] m_credentials = null;

   public JDAPBindRequest(int var1, String var2, String var3) {
      this.m_version = var1;
      this.m_name = var2;
      this.m_password = var3;
   }

   public JDAPBindRequest(int var1, String var2, String var3, byte[] var4) {
      this.m_version = var1;
      this.m_name = var2;
      this.m_mechanism = var3;
      this.m_credentials = var4;
   }

   public int getType() {
      return 0;
   }

   public BERElement getBERElement() {
      BERSequence var1 = new BERSequence();
      var1.addElement(new BERInteger(this.m_version));
      var1.addElement(new BEROctetString(this.m_name));
      BERTag var2 = null;
      if (this.m_mechanism == null) {
         var2 = new BERTag(128, new BEROctetString(this.m_password), true);
      } else {
         BERSequence var3 = new BERSequence();
         var3.addElement(new BEROctetString(this.m_mechanism));
         if (this.m_credentials == null) {
            var3.addElement(new BEROctetString((byte[])null));
         } else {
            var3.addElement(new BEROctetString(this.m_credentials, 0, this.m_credentials.length));
         }

         var2 = new BERTag(163, var3, true);
      }

      var1.addElement(var2);
      BERTag var4 = new BERTag(96, var1, true);
      return var4;
   }

   public String getParamString() {
      return "{version=" + this.m_version + ", name=" + this.m_name + ", authentication=" + (this.m_password == null ? this.m_password : "********") + "}";
   }

   public String toString() {
      return "BindRequest " + this.getParamString();
   }
}
