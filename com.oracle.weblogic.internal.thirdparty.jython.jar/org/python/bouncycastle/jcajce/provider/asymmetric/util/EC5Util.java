package org.python.bouncycastle.jcajce.provider.asymmetric.util;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.python.bouncycastle.asn1.x9.X962Parameters;
import org.python.bouncycastle.asn1.x9.X9ECParameters;
import org.python.bouncycastle.crypto.ec.CustomNamedCurves;
import org.python.bouncycastle.crypto.params.ECDomainParameters;
import org.python.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import org.python.bouncycastle.jce.provider.BouncyCastleProvider;
import org.python.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.python.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.python.bouncycastle.math.ec.ECAlgorithms;
import org.python.bouncycastle.math.ec.ECCurve;
import org.python.bouncycastle.math.field.FiniteField;
import org.python.bouncycastle.math.field.Polynomial;
import org.python.bouncycastle.math.field.PolynomialExtensionField;
import org.python.bouncycastle.util.Arrays;

public class EC5Util {
   private static Map customCurves = new HashMap();

   public static ECCurve getCurve(ProviderConfiguration var0, X962Parameters var1) {
      Set var2 = var0.getAcceptableNamedCurves();
      ECCurve var5;
      if (var1.isNamedCurve()) {
         ASN1ObjectIdentifier var3 = ASN1ObjectIdentifier.getInstance(var1.getParameters());
         if (!var2.isEmpty() && !var2.contains(var3)) {
            throw new IllegalStateException("named curve not acceptable");
         }

         X9ECParameters var4 = ECUtil.getNamedCurveByOid(var3);
         if (var4 == null) {
            var4 = (X9ECParameters)var0.getAdditionalECParameters().get(var3);
         }

         var5 = var4.getCurve();
      } else if (var1.isImplicitlyCA()) {
         var5 = var0.getEcImplicitlyCa().getCurve();
      } else {
         if (!var2.isEmpty()) {
            throw new IllegalStateException("encoded parameters not acceptable");
         }

         X9ECParameters var6 = X9ECParameters.getInstance(var1.getParameters());
         var5 = var6.getCurve();
      }

      return var5;
   }

   public static ECDomainParameters getDomainParameters(ProviderConfiguration var0, ECParameterSpec var1) {
      ECDomainParameters var3;
      if (var1 == null) {
         org.python.bouncycastle.jce.spec.ECParameterSpec var2 = var0.getEcImplicitlyCa();
         var3 = new ECDomainParameters(var2.getCurve(), var2.getG(), var2.getN(), var2.getH(), var2.getSeed());
      } else {
         var3 = ECUtil.getDomainParameters(var0, convertSpec(var1, false));
      }

      return var3;
   }

   public static ECParameterSpec convertToSpec(X962Parameters var0, ECCurve var1) {
      EllipticCurve var5;
      Object var6;
      if (var0.isNamedCurve()) {
         ASN1ObjectIdentifier var2 = (ASN1ObjectIdentifier)var0.getParameters();
         X9ECParameters var3 = ECUtil.getNamedCurveByOid(var2);
         if (var3 == null) {
            Map var4 = BouncyCastleProvider.CONFIGURATION.getAdditionalECParameters();
            if (!var4.isEmpty()) {
               var3 = (X9ECParameters)var4.get(var2);
            }
         }

         var5 = convertCurve(var1, var3.getSeed());
         var6 = new ECNamedCurveSpec(ECUtil.getCurveName(var2), var5, new ECPoint(var3.getG().getAffineXCoord().toBigInteger(), var3.getG().getAffineYCoord().toBigInteger()), var3.getN(), var3.getH());
      } else if (var0.isImplicitlyCA()) {
         var6 = null;
      } else {
         X9ECParameters var7 = X9ECParameters.getInstance(var0.getParameters());
         var5 = convertCurve(var1, var7.getSeed());
         if (var7.getH() != null) {
            var6 = new ECParameterSpec(var5, new ECPoint(var7.getG().getAffineXCoord().toBigInteger(), var7.getG().getAffineYCoord().toBigInteger()), var7.getN(), var7.getH().intValue());
         } else {
            var6 = new ECParameterSpec(var5, new ECPoint(var7.getG().getAffineXCoord().toBigInteger(), var7.getG().getAffineYCoord().toBigInteger()), var7.getN(), 1);
         }
      }

      return (ECParameterSpec)var6;
   }

   public static ECParameterSpec convertToSpec(X9ECParameters var0) {
      return new ECParameterSpec(convertCurve(var0.getCurve(), (byte[])null), new ECPoint(var0.getG().getAffineXCoord().toBigInteger(), var0.getG().getAffineYCoord().toBigInteger()), var0.getN(), var0.getH().intValue());
   }

   public static EllipticCurve convertCurve(ECCurve var0, byte[] var1) {
      ECField var2 = convertField(var0.getField());
      BigInteger var3 = var0.getA().toBigInteger();
      BigInteger var4 = var0.getB().toBigInteger();
      return new EllipticCurve(var2, var3, var4, (byte[])null);
   }

   public static ECCurve convertCurve(EllipticCurve var0) {
      ECField var1 = var0.getField();
      BigInteger var2 = var0.getA();
      BigInteger var3 = var0.getB();
      if (var1 instanceof ECFieldFp) {
         ECCurve.Fp var7 = new ECCurve.Fp(((ECFieldFp)var1).getP(), var2, var3);
         return (ECCurve)(customCurves.containsKey(var7) ? (ECCurve)customCurves.get(var7) : var7);
      } else {
         ECFieldF2m var4 = (ECFieldF2m)var1;
         int var5 = var4.getM();
         int[] var6 = ECUtil.convertMidTerms(var4.getMidTermsOfReductionPolynomial());
         return new ECCurve.F2m(var5, var6[0], var6[1], var6[2], var2, var3);
      }
   }

   public static ECField convertField(FiniteField var0) {
      if (ECAlgorithms.isFpField(var0)) {
         return new ECFieldFp(var0.getCharacteristic());
      } else {
         Polynomial var1 = ((PolynomialExtensionField)var0).getMinimalPolynomial();
         int[] var2 = var1.getExponentsPresent();
         int[] var3 = Arrays.reverse(Arrays.copyOfRange((int[])var2, 1, var2.length - 1));
         return new ECFieldF2m(var1.getDegree(), var3);
      }
   }

   public static ECParameterSpec convertSpec(EllipticCurve var0, org.python.bouncycastle.jce.spec.ECParameterSpec var1) {
      return (ECParameterSpec)(var1 instanceof ECNamedCurveParameterSpec ? new ECNamedCurveSpec(((ECNamedCurveParameterSpec)var1).getName(), var0, new ECPoint(var1.getG().getAffineXCoord().toBigInteger(), var1.getG().getAffineYCoord().toBigInteger()), var1.getN(), var1.getH()) : new ECParameterSpec(var0, new ECPoint(var1.getG().getAffineXCoord().toBigInteger(), var1.getG().getAffineYCoord().toBigInteger()), var1.getN(), var1.getH().intValue()));
   }

   public static org.python.bouncycastle.jce.spec.ECParameterSpec convertSpec(ECParameterSpec var0, boolean var1) {
      ECCurve var2 = convertCurve(var0.getCurve());
      return new org.python.bouncycastle.jce.spec.ECParameterSpec(var2, convertPoint(var2, var0.getGenerator(), var1), var0.getOrder(), BigInteger.valueOf((long)var0.getCofactor()), var0.getCurve().getSeed());
   }

   public static org.python.bouncycastle.math.ec.ECPoint convertPoint(ECParameterSpec var0, ECPoint var1, boolean var2) {
      return convertPoint(convertCurve(var0.getCurve()), var1, var2);
   }

   public static org.python.bouncycastle.math.ec.ECPoint convertPoint(ECCurve var0, ECPoint var1, boolean var2) {
      return var0.createPoint(var1.getAffineX(), var1.getAffineY());
   }

   static {
      Enumeration var0 = CustomNamedCurves.getNames();

      while(var0.hasMoreElements()) {
         String var1 = (String)var0.nextElement();
         X9ECParameters var2 = ECNamedCurveTable.getByName(var1);
         if (var2 != null) {
            customCurves.put(var2.getCurve(), CustomNamedCurves.getByName(var1).getCurve());
         }
      }

      X9ECParameters var3 = CustomNamedCurves.getByName("Curve25519");
      customCurves.put(new ECCurve.Fp(var3.getCurve().getField().getCharacteristic(), var3.getCurve().getA().toBigInteger(), var3.getCurve().getB().toBigInteger()), var3.getCurve());
   }
}
