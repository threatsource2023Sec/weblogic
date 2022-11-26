package org.apache.xml.security.stax.securityToken;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import org.apache.xml.security.exceptions.XMLSecurityException;

public interface SecurityToken {
   String getId();

   boolean isAsymmetric() throws XMLSecurityException;

   Map getSecretKey() throws XMLSecurityException;

   PublicKey getPublicKey() throws XMLSecurityException;

   X509Certificate[] getX509Certificates() throws XMLSecurityException;

   SecurityToken getKeyWrappingToken() throws XMLSecurityException;

   List getWrappedTokens() throws XMLSecurityException;

   SecurityTokenConstants.KeyIdentifier getKeyIdentifier();

   SecurityTokenConstants.TokenType getTokenType();

   List getTokenUsages();

   void addTokenUsage(SecurityTokenConstants.TokenUsage var1) throws XMLSecurityException;

   String getSha1Identifier();
}
