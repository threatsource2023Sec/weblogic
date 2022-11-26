package org.apache.xml.security.stax.impl.securityToken;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.ECPublicKeySpec;
import java.security.spec.EllipticCurve;
import java.security.spec.InvalidKeySpecException;
import org.apache.xml.security.algorithms.implementations.ECDSAUtils;
import org.apache.xml.security.binding.xmldsig11.ECKeyValueType;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.InboundSecurityContext;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class ECKeyValueSecurityToken extends AbstractInboundSecurityToken {
   private ECKeyValueType ecKeyValueType;

   public ECKeyValueSecurityToken(ECKeyValueType ecKeyValueType, InboundSecurityContext inboundSecurityContext) throws XMLSecurityException {
      super(inboundSecurityContext, IDGenerator.generateID((String)null), SecurityTokenConstants.KeyIdentifier_KeyValue, true);
      if (ecKeyValueType.getECParameters() != null) {
         throw new XMLSecurityException("stax.ecParametersNotSupported");
      } else if (ecKeyValueType.getNamedCurve() == null) {
         throw new XMLSecurityException("stax.namedCurveMissing");
      } else {
         this.ecKeyValueType = ecKeyValueType;
      }
   }

   private PublicKey buildPublicKey(ECKeyValueType ecKeyValueType) throws InvalidKeySpecException, NoSuchAlgorithmException, XMLSecurityException {
      String oid = ecKeyValueType.getNamedCurve().getURI();
      if (oid.startsWith("urn:oid:")) {
         oid = oid.substring(8);
      }

      ECDSAUtils.ECCurveDefinition ecCurveDefinition = ECDSAUtils.getECCurveDefinition(oid);
      if (ecCurveDefinition == null) {
         throw new XMLSecurityException("stax.unsupportedKeyValue");
      } else {
         EllipticCurve curve = new EllipticCurve(new ECFieldFp(new BigInteger(ecCurveDefinition.getField(), 16)), new BigInteger(ecCurveDefinition.getA(), 16), new BigInteger(ecCurveDefinition.getB(), 16));
         ECPoint ecPointG = ECDSAUtils.decodePoint(ecKeyValueType.getPublicKey(), curve);
         ECPublicKeySpec ecPublicKeySpec = new ECPublicKeySpec(new ECPoint(ecPointG.getAffineX(), ecPointG.getAffineY()), new ECParameterSpec(curve, new ECPoint(new BigInteger(ecCurveDefinition.getX(), 16), new BigInteger(ecCurveDefinition.getY(), 16)), new BigInteger(ecCurveDefinition.getN(), 16), ecCurveDefinition.getH()));
         KeyFactory keyFactory = KeyFactory.getInstance("EC");
         return keyFactory.generatePublic(ecPublicKeySpec);
      }
   }

   public PublicKey getPublicKey() throws XMLSecurityException {
      if (super.getPublicKey() == null) {
         try {
            this.setPublicKey(this.buildPublicKey(this.ecKeyValueType));
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
