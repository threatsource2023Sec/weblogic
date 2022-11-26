package netscape.ldap;

public interface LDAPSSLSocketFactoryExt extends LDAPSocketFactory {
   Object getCipherSuites();

   boolean isClientAuth();
}
