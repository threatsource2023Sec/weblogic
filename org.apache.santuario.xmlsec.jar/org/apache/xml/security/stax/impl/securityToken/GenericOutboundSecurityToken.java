package org.apache.xml.security.stax.impl.securityToken;

import java.security.Key;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.securityToken.OutboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.w3c.dom.Element;

public class GenericOutboundSecurityToken extends AbstractSecurityToken implements OutboundSecurityToken {
   private SecurityTokenConstants.TokenType tokenType;
   private Object processor;
   private final List wrappedTokens;
   private OutboundSecurityToken keyWrappingToken;
   private Element customTokenReference;

   public GenericOutboundSecurityToken(String id, SecurityTokenConstants.TokenType tokenType, Key key, X509Certificate[] x509Certificates) {
      this(id, tokenType, key);
      this.setX509Certificates(x509Certificates);
   }

   public GenericOutboundSecurityToken(String id, SecurityTokenConstants.TokenType tokenType, Key key) {
      this(id, tokenType);
      this.setSecretKey("", key);
      if (key instanceof PublicKey) {
         this.setPublicKey((PublicKey)key);
      }

   }

   public GenericOutboundSecurityToken(String id, SecurityTokenConstants.TokenType tokenType) {
      super(id);
      this.wrappedTokens = new ArrayList();
      this.tokenType = tokenType;
   }

   public Object getProcessor() {
      return this.processor;
   }

   public void setProcessor(Object processor) {
      this.processor = processor;
   }

   public Key getSecretKey(String algorithmURI) throws XMLSecurityException {
      if (algorithmURI == null) {
         return null;
      } else {
         Key key = (Key)this.keyTable.get(algorithmURI);
         if (key == null) {
            key = (Key)this.keyTable.get("");
         }

         return key;
      }
   }

   public OutboundSecurityToken getKeyWrappingToken() throws XMLSecurityException {
      return this.keyWrappingToken;
   }

   public void setKeyWrappingToken(OutboundSecurityToken keyWrappingToken) {
      this.keyWrappingToken = keyWrappingToken;
   }

   public List getWrappedTokens() throws XMLSecurityException {
      return Collections.unmodifiableList(this.wrappedTokens);
   }

   public void addWrappedToken(OutboundSecurityToken securityToken) {
      this.wrappedTokens.add(securityToken);
   }

   public void setTokenType(SecurityTokenConstants.TokenType tokenType) {
      this.tokenType = tokenType;
   }

   public SecurityTokenConstants.TokenType getTokenType() {
      return this.tokenType;
   }

   public SecurityTokenConstants.KeyIdentifier getKeyIdentifier() {
      return null;
   }

   public Element getCustomTokenReference() {
      return this.customTokenReference;
   }

   public void setCustomTokenReference(Element customTokenReference) {
      this.customTokenReference = customTokenReference;
   }
}
