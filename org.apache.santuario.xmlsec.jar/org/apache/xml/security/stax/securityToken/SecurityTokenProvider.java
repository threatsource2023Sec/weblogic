package org.apache.xml.security.stax.securityToken;

import org.apache.xml.security.exceptions.XMLSecurityException;

public interface SecurityTokenProvider {
   Object getSecurityToken() throws XMLSecurityException;

   String getId();
}
