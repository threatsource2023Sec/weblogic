package org.cryptacular.x509.dn;

import javax.security.auth.x500.X500Principal;

public interface NameFormatter {
   String format(X500Principal var1);
}
