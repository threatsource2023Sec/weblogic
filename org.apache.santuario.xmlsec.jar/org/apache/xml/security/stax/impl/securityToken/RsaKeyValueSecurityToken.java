package org.apache.xml.security.stax.impl.securityToken;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import org.apache.xml.security.binding.xmldsig.RSAKeyValueType;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.InboundSecurityContext;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class RsaKeyValueSecurityToken extends AbstractInboundSecurityToken {
   private RSAKeyValueType rsaKeyValueType;

   public RsaKeyValueSecurityToken(RSAKeyValueType rsaKeyValueType, InboundSecurityContext inboundSecurityContext) {
      super(inboundSecurityContext, IDGenerator.generateID((String)null), SecurityTokenConstants.KeyIdentifier_KeyValue, true);
      this.rsaKeyValueType = rsaKeyValueType;
   }

   private PublicKey buildPublicKey(RSAKeyValueType rsaKeyValueType) throws InvalidKeySpecException, NoSuchAlgorithmException {
      RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(new BigInteger(1, rsaKeyValueType.getModulus()), new BigInteger(1, rsaKeyValueType.getExponent()));
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      return keyFactory.generatePublic(rsaPublicKeySpec);
   }

   public PublicKey getPublicKey() throws XMLSecurityException {
      if (super.getPublicKey() == null) {
         try {
            this.setPublicKey(this.buildPublicKey(this.rsaKeyValueType));
         } catch (InvalidKeySpecException var2) {
            throw new XMLSecurityException(var2);
         } catch (NoSuchAlgorithmException var3) {
            throw new XMLSecurityException(var3);
         }
      }

      return super.getPublicKey();
   }

   public boolean isAsymmetric() {
      return true;
   }

   public SecurityTokenConstants.TokenType getTokenType() {
      return SecurityTokenConstants.KeyValueToken;
   }
}
