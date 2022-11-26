package org.apache.xml.security.stax.securityToken;

import java.security.Key;
import java.security.PublicKey;
import java.util.List;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;

public interface InboundSecurityToken extends SecurityToken {
   Key getSecretKey(String var1, XMLSecurityConstants.AlgorithmUsage var2, String var3) throws XMLSecurityException;

   PublicKey getPublicKey(String var1, XMLSecurityConstants.AlgorithmUsage var2, String var3) throws XMLSecurityException;

   void addWrappedToken(InboundSecurityToken var1);

   void verify() throws XMLSecurityException;

   List getElementPath();

   XMLSecEvent getXMLSecEvent();

   boolean isIncludedInMessage();
}
