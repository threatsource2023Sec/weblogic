package netscape.ldap.client;

public class JDAPFilterOr extends JDAPFilterSet {
   public JDAPFilterOr() {
      super(161);
   }

   public String toString() {
      return "JDAPFilterOr {" + super.getParamString() + "}";
   }
}
