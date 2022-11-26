package netscape.ldap.client.opers;

import netscape.ldap.ber.stream.BERBoolean;
import netscape.ldap.ber.stream.BERElement;
import netscape.ldap.ber.stream.BEREnumerated;
import netscape.ldap.ber.stream.BERInteger;
import netscape.ldap.ber.stream.BEROctetString;
import netscape.ldap.ber.stream.BERSequence;
import netscape.ldap.ber.stream.BERTag;
import netscape.ldap.client.JDAPFilter;
import netscape.ldap.client.JDAPFilterOpers;

public class JDAPSearchRequest extends JDAPBaseDNRequest implements JDAPProtocolOp {
   public static final int BASE_OBJECT = 0;
   public static final int SINGLE_LEVEL = 1;
   public static final int WHOLE_SUBTREE = 2;
   public static final int NEVER_DEREF_ALIASES = 0;
   public static final int DEREF_IN_SEARCHING = 1;
   public static final int DEREF_FINDING_BASE_OBJ = 2;
   public static final int DEREF_ALWAYS = 3;
   public static final String DEFAULT_FILTER = "(objectclass=*)";
   protected String m_base_dn = null;
   protected int m_scope;
   protected int m_deref;
   protected int m_size_limit;
   protected int m_time_limit;
   protected boolean m_attrs_only;
   protected String m_filter = null;
   protected JDAPFilter m_parsedFilter = null;
   protected String[] m_attrs = null;

   public JDAPSearchRequest(String var1, int var2, int var3, int var4, int var5, boolean var6, String var7, String[] var8) throws IllegalArgumentException {
      this.m_base_dn = var1;
      this.m_scope = var2;
      this.m_deref = var3;
      this.m_size_limit = var4;
      this.m_time_limit = var5;
      this.m_attrs_only = var6;
      this.m_filter = var7 == null ? "(objectclass=*)" : var7;
      this.m_parsedFilter = JDAPFilter.getFilter(JDAPFilterOpers.convertLDAPv2Escape(this.m_filter));
      if (this.m_parsedFilter == null) {
         throw new IllegalArgumentException("Bad search filter");
      } else {
         this.m_attrs = var8;
      }
   }

   public int getType() {
      return 3;
   }

   public void setBaseDN(String var1) {
      this.m_base_dn = var1;
   }

   public String getBaseDN() {
      return this.m_base_dn;
   }

   public BERElement getBERElement() {
      BERSequence var1 = new BERSequence();
      var1.addElement(new BEROctetString(this.m_base_dn));
      var1.addElement(new BEREnumerated(this.m_scope));
      var1.addElement(new BEREnumerated(this.m_deref));
      var1.addElement(new BERInteger(this.m_size_limit));
      var1.addElement(new BERInteger(this.m_time_limit));
      var1.addElement(new BERBoolean(this.m_attrs_only));
      var1.addElement(this.m_parsedFilter.getBERElement());
      BERSequence var2 = new BERSequence();
      if (this.m_attrs != null) {
         for(int var3 = 0; var3 < this.m_attrs.length; ++var3) {
            var2.addElement(new BEROctetString(this.m_attrs[var3]));
         }
      }

      var1.addElement(var2);
      BERTag var4 = new BERTag(99, var1, true);
      return var4;
   }

   public String toString() {
      String var1 = null;
      if (this.m_attrs != null) {
         var1 = "";

         for(int var2 = 0; var2 < this.m_attrs.length; ++var2) {
            if (var2 != 0) {
               var1 = var1 + "+";
            }

            var1 = var1 + this.m_attrs[var2];
         }
      }

      return "SearchRequest {baseObject=" + this.m_base_dn + ", scope=" + this.m_scope + ", derefAliases=" + this.m_deref + ",sizeLimit=" + this.m_size_limit + ", timeLimit=" + this.m_time_limit + ", attrsOnly=" + this.m_attrs_only + ", filter=" + this.m_filter + ", attributes=" + var1 + "}";
   }
}
