package org.apache.xml.security.stax.securityEvent;

import org.apache.xml.security.exceptions.XMLSecurityException;

public interface SecurityEventListener {
   void registerSecurityEvent(SecurityEvent var1) throws XMLSecurityException;
}
