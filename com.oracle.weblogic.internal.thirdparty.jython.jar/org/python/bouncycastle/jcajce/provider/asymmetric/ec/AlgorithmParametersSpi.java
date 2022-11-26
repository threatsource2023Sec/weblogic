package org.python.bouncycastle.jcajce.provider.asymmetric.ec;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import org.python.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.python.bouncycastle.asn1.x9.X962Parameters;
import org.python.bouncycastle.asn1.x9.X9ECParameters;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.EC5Util;
import org.python.bouncycastle.jcajce.provider.asymmetric.util.ECUtil;
import org.python.bouncycastle.jce.provider.BouncyCastleProvider;
import org.python.bouncycastle.jce.spec.ECNamedCurveSpec;
import org.python.bouncycastle.math.ec.ECCurve;

public class AlgorithmParametersSpi extends java.security.AlgorithmParametersSpi {
   private ECParameterSpec ecParameterSpec;
   private String curveName;

   protected boolean isASN1FormatString(String var1) {
      return var1 == null || var1.equals("ASN.1");
   }

   protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
      if (var1 instanceof ECGenParameterSpec) {
         ECGenParameterSpec var2 = (ECGenParameterSpec)var1;
         X9ECParameters var3 = ECUtils.getDomainParametersFromGenSpec(var2);
         if (var3 == null) {
            throw new InvalidParameterSpecException("EC curve name not recognized: " + var2.getName());
         }

         this.curveName = var2.getName();
         this.ecParameterSpec = EC5Util.convertToSpec(var3);
      } else {
         if (!(var1 instanceof ECParameterSpec)) {
            throw new InvalidParameterSpecException("AlgorithmParameterSpec class not recognized: " + var1.getClass().getName());
         }

         if (var1 instanceof ECNamedCurveSpec) {
            this.curveName = ((ECNamedCurveSpec)var1).getName();
         } else {
            this.curveName = null;
         }

         this.ecParameterSpec = (ECParameterSpec)var1;
      }

   }

   protected void engineInit(byte[] var1) throws IOException {
      this.engineInit(var1, "ASN.1");
   }

   protected void engineInit(byte[] var1, String var2) throws IOException {
      if (this.isASN1FormatString(var2)) {
         X962Parameters var3 = X962Parameters.getInstance(var1);
         ECCurve var4 = EC5Util.getCurve(BouncyCastleProvider.CONFIGURATION, var3);
         if (var3.isNamedCurve()) {
            ASN1ObjectIdentifier var5 = ASN1ObjectIdentifier.getInstance(var3.getParameters());
            this.curveName = ECNamedCurveTable.getName(var5);
            if (this.curveName == null) {
               this.curveName = var5.getId();
            }
         }

         this.ecParameterSpec = EC5Util.convertToSpec(var3, var4);
      } else {
         throw new IOException("Unknown encoded parameters format in AlgorithmParameters object: " + var2);
      }
   }

   protected AlgorithmParameterSpec engineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
      if (!ECParameterSpec.class.isAssignableFrom(var1) && var1 != AlgorithmParameterSpec.class) {
         if (ECGenParameterSpec.class.isAssignableFrom(var1)) {
            ASN1ObjectIdentifier var2;
            if (this.curveName != null) {
               var2 = ECUtil.getNamedCurveOid(this.curveName);
               if (var2 != null) {
                  return new ECGenParameterSpec(var2.getId());
               }

               return new ECGenParameterSpec(this.curveName);
            }

            var2 = ECUtil.getNamedCurveOid(EC5Util.convertSpec(this.ecParameterSpec, false));
            if (var2 != null) {
               return new ECGenParameterSpec(var2.getId());
            }
         }

         throw new InvalidParameterSpecException("EC AlgorithmParameters cannot convert to " + var1.getName());
      } else {
         return this.ecParameterSpec;
      }
   }

   protected byte[] engineGetEncoded() throws IOException {
      return this.engineGetEncoded("ASN.1");
   }

   protected byte[] engineGetEncoded(String var1) throws IOException {
      if (this.isASN1FormatString(var1)) {
         X962Parameters var2;
         if (this.ecParameterSpec == null) {
            var2 = new X962Parameters(DERNull.INSTANCE);
         } else if (this.curveName != null) {
            var2 = new X962Parameters(ECUtil.getNamedCurveOid(this.curveName));
         } else {
            org.python.bouncycastle.jce.spec.ECParameterSpec var3 = EC5Util.convertSpec(this.ecParameterSpec, false);
            X9ECParameters var4 = new X9ECParameters(var3.getCurve(), var3.getG(), var3.getN(), var3.getH(), var3.getSeed());
            var2 = new X962Parameters(var4);
         }

         return var2.getEncoded();
      } else {
         throw new IOException("Unknown parameters format in AlgorithmParameters object: " + var1);
      }
   }

   protected String engineToString() {
      return "EC AlgorithmParameters ";
   }
}
