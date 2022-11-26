package netscape.ldap;

import java.net.Socket;

public interface LDAPTLSSocketFactory extends LDAPSocketFactory {
   Socket makeSocket(Socket var1) throws LDAPException;
}
