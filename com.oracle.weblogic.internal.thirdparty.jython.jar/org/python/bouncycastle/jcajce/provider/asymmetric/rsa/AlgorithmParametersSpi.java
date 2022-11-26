package org.python.bouncycastle.jcajce.provider.asymmetric.rsa;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PSSParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.python.bouncycastle.asn1.pkcs.RSAESOAEPparams;
import org.python.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.jcajce.provider.util.DigestFactory;
import org.python.bouncycastle.jcajce.util.MessageDigestUtils;

public abstract class AlgorithmParametersSpi extends java.security.AlgorithmParametersSpi {
   protected boolean isASN1FormatString(String var1) {
      return var1 == null || var1.equals("ASN.1");
   }

   protected AlgorithmParameterSpec engineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
      if (var1 == null) {
         throw new NullPointerException("argument to getParameterSpec must not be null");
      } else {
         return this.localEngineGetParameterSpec(var1);
      }
   }

   protected abstract AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException;

   public static class OAEP extends AlgorithmParametersSpi {
      OAEPParameterSpec currentSpec;

      protected byte[] engineGetEncoded() {
         AlgorithmIdentifier var1 = new AlgorithmIdentifier(DigestFactory.getOID(this.currentSpec.getDigestAlgorithm()), DERNull.INSTANCE);
         MGF1ParameterSpec var2 = (MGF1ParameterSpec)this.currentSpec.getMGFParameters();
         AlgorithmIdentifier var3 = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, new AlgorithmIdentifier(DigestFactory.getOID(var2.getDigestAlgorithm()), DERNull.INSTANCE));
         PSource.PSpecified var4 = (PSource.PSpecified)this.currentSpec.getPSource();
         AlgorithmIdentifier var5 = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_pSpecified, new DEROctetString(var4.getValue()));
         RSAESOAEPparams var6 = new RSAESOAEPparams(var1, var3, var5);

         try {
            return var6.getEncoded("DER");
         } catch (IOException var8) {
            throw new RuntimeException("Error encoding OAEPParameters");
         }
      }

      protected byte[] engineGetEncoded(String var1) {
         return !this.isASN1FormatString(var1) && !var1.equalsIgnoreCase("X.509") ? null : this.engineGetEncoded();
      }

      protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
         if (var1 != OAEPParameterSpec.class && var1 != AlgorithmParameterSpec.class) {
            throw new InvalidParameterSpecException("unknown parameter spec passed to OAEP parameters object.");
         } else {
            return this.currentSpec;
         }
      }

      protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
         if (!(var1 instanceof OAEPParameterSpec)) {
            throw new InvalidParameterSpecException("OAEPParameterSpec required to initialise an OAEP algorithm parameters object");
         } else {
            this.currentSpec = (OAEPParameterSpec)var1;
         }
      }

      protected void engineInit(byte[] var1) throws IOException {
         try {
            RSAESOAEPparams var2 = RSAESOAEPparams.getInstance(var1);
            if (!var2.getMaskGenAlgorithm().getAlgorithm().equals(PKCSObjectIdentifiers.id_mgf1)) {
               throw new IOException("unknown mask generation function: " + var2.getMaskGenAlgorithm().getAlgorithm());
            } else {
               this.currentSpec = new OAEPParameterSpec(MessageDigestUtils.getDigestName(var2.getHashAlgorithm().getAlgorithm()), OAEPParameterSpec.DEFAULT.getMGFAlgorithm(), new MGF1ParameterSpec(MessageDigestUtils.getDigestName(AlgorithmIdentifier.getInstance(var2.getMaskGenAlgorithm().getParameters()).getAlgorithm())), new PSource.PSpecified(ASN1OctetString.getInstance(var2.getPSourceAlgorithm().getParameters()).getOctets()));
            }
         } catch (ClassCastException var3) {
            throw new IOException("Not a valid OAEP Parameter encoding.");
         } catch (ArrayIndexOutOfBoundsException var4) {
            throw new IOException("Not a valid OAEP Parameter encoding.");
         }
      }

      protected void engineInit(byte[] var1, String var2) throws IOException {
         if (!var2.equalsIgnoreCase("X.509") && !var2.equalsIgnoreCase("ASN.1")) {
            throw new IOException("Unknown parameter format " + var2);
         } else {
            this.engineInit(var1);
         }
      }

      protected String engineToString() {
         return "OAEP Parameters";
      }
   }

   public static class PSS extends AlgorithmParametersSpi {
      PSSParameterSpec currentSpec;

      protected byte[] engineGetEncoded() throws IOException {
         PSSParameterSpec var1 = this.currentSpec;
         AlgorithmIdentifier var2 = new AlgorithmIdentifier(DigestFactory.getOID(var1.getDigestAlgorithm()), DERNull.INSTANCE);
         MGF1ParameterSpec var3 = (MGF1ParameterSpec)var1.getMGFParameters();
         AlgorithmIdentifier var4 = new AlgorithmIdentifier(PKCSObjectIdentifiers.id_mgf1, new AlgorithmIdentifier(DigestFactory.getOID(var3.getDigestAlgorithm()), DERNull.INSTANCE));
         RSASSAPSSparams var5 = new RSASSAPSSparams(var2, var4, new ASN1Integer((long)var1.getSaltLength()), new ASN1Integer((long)var1.getTrailerField()));
         return var5.getEncoded("DER");
      }

      protected byte[] engineGetEncoded(String var1) throws IOException {
         return !var1.equalsIgnoreCase("X.509") && !var1.equalsIgnoreCase("ASN.1") ? null : this.engineGetEncoded();
      }

      protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
         if (var1 == PSSParameterSpec.class && this.currentSpec != null) {
            return this.currentSpec;
         } else {
            throw new InvalidParameterSpecException("unknown parameter spec passed to PSS parameters object.");
         }
      }

      protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
         if (!(var1 instanceof PSSParameterSpec)) {
            throw new InvalidParameterSpecException("PSSParameterSpec required to initialise an PSS algorithm parameters object");
         } else {
            this.currentSpec = (PSSParameterSpec)var1;
         }
      }

      protected void engineInit(byte[] var1) throws IOException {
         try {
            RSASSAPSSparams var2 = RSASSAPSSparams.getInstance(var1);
            if (!var2.getMaskGenAlgorithm().getAlgorithm().equals(PKCSObjectIdentifiers.id_mgf1)) {
               throw new IOException("unknown mask generation function: " + var2.getMaskGenAlgorithm().getAlgorithm());
            } else {
               this.currentSpec = new PSSParameterSpec(MessageDigestUtils.getDigestName(var2.getHashAlgorithm().getAlgorithm()), PSSParameterSpec.DEFAULT.getMGFAlgorithm(), new MGF1ParameterSpec(MessageDigestUtils.getDigestName(AlgorithmIdentifier.getInstance(var2.getMaskGenAlgorithm().getParameters()).getAlgorithm())), var2.getSaltLength().intValue(), var2.getTrailerField().intValue());
            }
         } catch (ClassCastException var3) {
            throw new IOException("Not a valid PSS Parameter encoding.");
         } catch (ArrayIndexOutOfBoundsException var4) {
            throw new IOException("Not a valid PSS Parameter encoding.");
         }
      }

      protected void engineInit(byte[] var1, String var2) throws IOException {
         if (!this.isASN1FormatString(var2) && !var2.equalsIgnoreCase("X.509")) {
            throw new IOException("Unknown parameter format " + var2);
         } else {
            this.engineInit(var1);
         }
      }

      protected String engineToString() {
         return "PSS Parameters";
      }
   }
}
