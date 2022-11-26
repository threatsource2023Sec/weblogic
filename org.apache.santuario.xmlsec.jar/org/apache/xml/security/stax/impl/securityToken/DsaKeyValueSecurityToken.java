package org.apache.xml.security.stax.impl.securityToken;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.DSAPublicKeySpec;
import java.security.spec.InvalidKeySpecException;
import org.apache.xml.security.binding.xmldsig.DSAKeyValueType;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.InboundSecurityContext;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class DsaKeyValueSecurityToken extends AbstractInboundSecurityToken {
   private DSAKeyValueType dsaKeyValueType;

   public DsaKeyValueSecurityToken(DSAKeyValueType dsaKeyValueType, InboundSecurityContext inboundSecurityContext) {
      super(inboundSecurityContext, IDGenerator.generateID((String)null), SecurityTokenConstants.KeyIdentifier_KeyValue, true);
      this.dsaKeyValueType = dsaKeyValueType;
   }

   private PublicKey buildPublicKey(DSAKeyValueType dsaKeyValueType) throws InvalidKeySpecException, NoSuchAlgorithmException {
      DSAPublicKeySpec dsaPublicKeySpec = new DSAPublicKeySpec(new BigInteger(1, dsaKeyValueType.getY()), new BigInteger(1, dsaKeyValueType.getP()), new BigInteger(1, dsaKeyValueType.getQ()), new BigInteger(1, dsaKeyValueType.getG()));
      KeyFactory keyFactory = KeyFactory.getInstance("DSA");
      return keyFactory.generatePublic(dsaPublicKeySpec);
   }

   public PublicKey getPublicKey() throws XMLSecurityException {
      if (super.getPublicKey() == null) {
         try {
            this.setPublicKey(this.buildPublicKey(this.dsaKeyValueType));
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
