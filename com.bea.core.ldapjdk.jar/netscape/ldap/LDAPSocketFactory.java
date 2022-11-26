package netscape.ldap;

import java.net.Socket;

public interface LDAPSocketFactory {
   Socket makeSocket(String var1, int var2) throws LDAPException;
}
