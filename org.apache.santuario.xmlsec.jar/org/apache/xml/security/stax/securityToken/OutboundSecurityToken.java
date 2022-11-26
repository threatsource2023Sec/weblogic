package org.apache.xml.security.stax.securityToken;

import java.security.Key;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.w3c.dom.Element;

public interface OutboundSecurityToken extends SecurityToken {
   Object getProcessor();

   Key getSecretKey(String var1) throws XMLSecurityException;

   void addWrappedToken(OutboundSecurityToken var1);

   Element getCustomTokenReference();
}
