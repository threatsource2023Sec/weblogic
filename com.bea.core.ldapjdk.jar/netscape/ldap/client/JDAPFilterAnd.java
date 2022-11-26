package netscape.ldap.client;

public class JDAPFilterAnd extends JDAPFilterSet {
   public JDAPFilterAnd() {
      super(160);
   }

   public String toString() {
      return "JDAPFilterAnd {" + super.getParamString() + "}";
   }
}
