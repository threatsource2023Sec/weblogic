package org.apache.xml.security.stax.impl.securityToken;

import java.security.Key;
import java.security.PublicKey;
import java.security.interfaces.DSAKey;
import java.security.interfaces.ECKey;
import java.security.interfaces.RSAKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.crypto.SecretKey;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.InboundSecurityContext;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.securityEvent.AlgorithmSuiteSecurityEvent;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public abstract class AbstractInboundSecurityToken extends AbstractSecurityToken implements InboundSecurityToken {
   private boolean invoked = false;
   private InboundSecurityContext inboundSecurityContext;
   private List elementPath;
   private XMLSecEvent xmlSecEvent;
   private SecurityTokenConstants.KeyIdentifier keyIdentifier;
   private final List wrappedTokens = new ArrayList();
   private InboundSecurityToken keyWrappingToken;
   private boolean includedInMessage = false;

   public AbstractInboundSecurityToken(InboundSecurityContext inboundSecurityContext, String id, SecurityTokenConstants.KeyIdentifier keyIdentifier, boolean includedInMessage) {
      super(id);
      if (keyIdentifier == null) {
         throw new IllegalArgumentException("No keyIdentifier specified");
      } else {
         this.inboundSecurityContext = inboundSecurityContext;
         this.keyIdentifier = keyIdentifier;
         this.includedInMessage = includedInMessage;
      }
   }

   private void testAndSetInvocation() throws XMLSecurityException {
      if (this.invoked) {
         throw new XMLSecurityException("stax.recursiveKeyReference");
      } else {
         this.invoked = true;
      }
   }

   private void unsetInvocation() {
      this.invoked = false;
   }

   public SecurityTokenConstants.KeyIdentifier getKeyIdentifier() {
      return this.keyIdentifier;
   }

   public List getElementPath() {
      return this.elementPath;
   }

   public void setElementPath(List elementPath) {
      this.elementPath = Collections.unmodifiableList(elementPath);
   }

   public XMLSecEvent getXMLSecEvent() {
      return this.xmlSecEvent;
   }

   public void setXMLSecEvent(XMLSecEvent xmlSecEvent) {
      this.xmlSecEvent = xmlSecEvent;
   }

   protected Key getKey(String algorithmURI, XMLSecurityConstants.AlgorithmUsage algorithmUsage, String correlationID) throws XMLSecurityException {
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

   public final Key getSecretKey(String algorithmURI, XMLSecurityConstants.AlgorithmUsage algorithmUsage, String correlationID) throws XMLSecurityException {
      if (correlationID == null) {
         throw new IllegalArgumentException("correlationID must not be null");
      } else {
         this.testAndSetInvocation();
         Key key = this.getKey(algorithmURI, algorithmUsage, correlationID);
         if (key != null && this.inboundSecurityContext != null) {
            AlgorithmSuiteSecurityEvent algorithmSuiteSecurityEvent = new AlgorithmSuiteSecurityEvent();
            algorithmSuiteSecurityEvent.setAlgorithmURI(algorithmURI);
            algorithmSuiteSecurityEvent.setAlgorithmUsage(algorithmUsage);
            algorithmSuiteSecurityEvent.setCorrelationID(correlationID);
            if (SecurityTokenConstants.DerivedKeyToken.equals(this.getTokenType())) {
               algorithmSuiteSecurityEvent.setDerivedKey(true);
            }

            if (key instanceof RSAKey) {
               algorithmSuiteSecurityEvent.setKeyLength(((RSAKey)key).getModulus().bitLength());
            } else if (key instanceof DSAKey) {
               algorithmSuiteSecurityEvent.setKeyLength(((DSAKey)key).getParams().getP().bitLength());
            } else if (key instanceof ECKey) {
               algorithmSuiteSecurityEvent.setKeyLength(((ECKey)key).getParams().getOrder().bitLength());
            } else {
               if (!(key instanceof SecretKey)) {
                  throw new XMLSecurityException("java.security.UnknownKeyType", new Object[]{key.getClass().getName()});
               }

               algorithmSuiteSecurityEvent.setKeyLength(key.getEncoded().length * 8);
            }

            this.inboundSecurityContext.registerSecurityEvent(algorithmSuiteSecurityEvent);
         }

         this.unsetInvocation();
         return key;
      }
   }

   protected PublicKey getPubKey(String algorithmURI, XMLSecurityConstants.AlgorithmUsage algorithmUsage, String correlationID) throws XMLSecurityException {
      return this.getPublicKey();
   }

   public final PublicKey getPublicKey(String algorithmURI, XMLSecurityConstants.AlgorithmUsage algorithmUsage, String correlationID) throws XMLSecurityException {
      if (correlationID == null) {
         throw new IllegalArgumentException("correlationID must not be null");
      } else {
         this.testAndSetInvocation();
         PublicKey publicKey = this.getPubKey(algorithmURI, algorithmUsage, correlationID);
         if (publicKey != null && this.inboundSecurityContext != null) {
            AlgorithmSuiteSecurityEvent algorithmSuiteSecurityEvent = new AlgorithmSuiteSecurityEvent();
            algorithmSuiteSecurityEvent.setAlgorithmURI(algorithmURI);
            algorithmSuiteSecurityEvent.setAlgorithmUsage(algorithmUsage);
            algorithmSuiteSecurityEvent.setCorrelationID(correlationID);
            if (publicKey instanceof RSAKey) {
               algorithmSuiteSecurityEvent.setKeyLength(((RSAKey)publicKey).getModulus().bitLength());
            } else if (publicKey instanceof DSAKey) {
               algorithmSuiteSecurityEvent.setKeyLength(((DSAKey)publicKey).getParams().getP().bitLength());
            } else {
               if (!(publicKey instanceof ECKey)) {
                  throw new XMLSecurityException("java.security.UnknownKeyType", new Object[]{publicKey.getClass().getName()});
               }

               algorithmSuiteSecurityEvent.setKeyLength(((ECKey)publicKey).getParams().getOrder().bitLength());
            }

            this.inboundSecurityContext.registerSecurityEvent(algorithmSuiteSecurityEvent);
         }

         this.unsetInvocation();
         return publicKey;
      }
   }

   public void verify() throws XMLSecurityException {
   }

   public List getWrappedTokens() {
      return Collections.unmodifiableList(this.wrappedTokens);
   }

   public void addWrappedToken(InboundSecurityToken inboundSecurityToken) {
      this.wrappedTokens.add(inboundSecurityToken);
   }

   public void addTokenUsage(SecurityTokenConstants.TokenUsage tokenUsage) throws XMLSecurityException {
      this.testAndSetInvocation();
      if (!this.tokenUsages.contains(tokenUsage)) {
         this.tokenUsages.add(tokenUsage);
      }

      if (this.getKeyWrappingToken() != null) {
         this.getKeyWrappingToken().addTokenUsage(tokenUsage);
      }

      this.unsetInvocation();
   }

   public InboundSecurityToken getKeyWrappingToken() throws XMLSecurityException {
      return this.keyWrappingToken;
   }

   public void setKeyWrappingToken(InboundSecurityToken keyWrappingToken) {
      this.keyWrappingToken = keyWrappingToken;
   }

   public boolean isIncludedInMessage() {
      return this.includedInMessage;
   }
}
