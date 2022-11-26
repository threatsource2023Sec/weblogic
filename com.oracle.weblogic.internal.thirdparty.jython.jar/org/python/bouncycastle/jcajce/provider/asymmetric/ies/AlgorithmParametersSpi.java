package org.python.bouncycastle.jcajce.provider.asymmetric.ies;

import java.io.IOException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import org.python.bouncycastle.asn1.ASN1EncodableVector;
import org.python.bouncycastle.asn1.ASN1Integer;
import org.python.bouncycastle.asn1.ASN1OctetString;
import org.python.bouncycastle.asn1.ASN1Primitive;
import org.python.bouncycastle.asn1.ASN1Sequence;
import org.python.bouncycastle.asn1.ASN1TaggedObject;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.DERSequence;
import org.python.bouncycastle.asn1.DERTaggedObject;
import org.python.bouncycastle.jce.spec.IESParameterSpec;

public class AlgorithmParametersSpi extends java.security.AlgorithmParametersSpi {
   IESParameterSpec currentSpec;

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

   protected byte[] engineGetEncoded() {
      try {
         ASN1EncodableVector var1 = new ASN1EncodableVector();
         if (this.currentSpec.getDerivationV() != null) {
            var1.add(new DERTaggedObject(false, 0, new DEROctetString(this.currentSpec.getDerivationV())));
         }

         if (this.currentSpec.getEncodingV() != null) {
            var1.add(new DERTaggedObject(false, 1, new DEROctetString(this.currentSpec.getEncodingV())));
         }

         var1.add(new ASN1Integer((long)this.currentSpec.getMacKeySize()));
         if (this.currentSpec.getNonce() != null) {
            ASN1EncodableVector var2 = new ASN1EncodableVector();
            var2.add(new ASN1Integer((long)this.currentSpec.getCipherKeySize()));
            var2.add(new ASN1Integer(this.currentSpec.getNonce()));
            var1.add(new DERSequence(var2));
         }

         return (new DERSequence(var1)).getEncoded("DER");
      } catch (IOException var3) {
         throw new RuntimeException("Error encoding IESParameters");
      }
   }

   protected byte[] engineGetEncoded(String var1) {
      return !this.isASN1FormatString(var1) && !var1.equalsIgnoreCase("X.509") ? null : this.engineGetEncoded();
   }

   protected AlgorithmParameterSpec localEngineGetParameterSpec(Class var1) throws InvalidParameterSpecException {
      if (var1 != IESParameterSpec.class && var1 != AlgorithmParameterSpec.class) {
         throw new InvalidParameterSpecException("unknown parameter spec passed to ElGamal parameters object.");
      } else {
         return this.currentSpec;
      }
   }

   protected void engineInit(AlgorithmParameterSpec var1) throws InvalidParameterSpecException {
      if (!(var1 instanceof IESParameterSpec)) {
         throw new InvalidParameterSpecException("IESParameterSpec required to initialise a IES algorithm parameters object");
      } else {
         this.currentSpec = (IESParameterSpec)var1;
      }
   }

   protected void engineInit(byte[] var1) throws IOException {
      try {
         ASN1Sequence var2 = (ASN1Sequence)ASN1Primitive.fromByteArray(var1);
         if (var2.size() == 1) {
            this.currentSpec = new IESParameterSpec((byte[])null, (byte[])null, ASN1Integer.getInstance(var2.getObjectAt(0)).getValue().intValue());
         } else {
            ASN1TaggedObject var3;
            if (var2.size() == 2) {
               var3 = ASN1TaggedObject.getInstance(var2.getObjectAt(0));
               if (var3.getTagNo() == 0) {
                  this.currentSpec = new IESParameterSpec(ASN1OctetString.getInstance(var3, false).getOctets(), (byte[])null, ASN1Integer.getInstance(var2.getObjectAt(1)).getValue().intValue());
               } else {
                  this.currentSpec = new IESParameterSpec((byte[])null, ASN1OctetString.getInstance(var3, false).getOctets(), ASN1Integer.getInstance(var2.getObjectAt(1)).getValue().intValue());
               }
            } else {
               ASN1TaggedObject var4;
               if (var2.size() == 3) {
                  var3 = ASN1TaggedObject.getInstance(var2.getObjectAt(0));
                  var4 = ASN1TaggedObject.getInstance(var2.getObjectAt(1));
                  this.currentSpec = new IESParameterSpec(ASN1OctetString.getInstance(var3, false).getOctets(), ASN1OctetString.getInstance(var4, false).getOctets(), ASN1Integer.getInstance(var2.getObjectAt(2)).getValue().intValue());
               } else if (var2.size() == 4) {
                  var3 = ASN1TaggedObject.getInstance(var2.getObjectAt(0));
                  var4 = ASN1TaggedObject.getInstance(var2.getObjectAt(1));
                  ASN1Sequence var5 = ASN1Sequence.getInstance(var2.getObjectAt(3));
                  this.currentSpec = new IESParameterSpec(ASN1OctetString.getInstance(var3, false).getOctets(), ASN1OctetString.getInstance(var4, false).getOctets(), ASN1Integer.getInstance(var2.getObjectAt(2)).getValue().intValue(), ASN1Integer.getInstance(var5.getObjectAt(0)).getValue().intValue(), ASN1OctetString.getInstance(var5.getObjectAt(1)).getOctets());
               }
            }
         }

      } catch (ClassCastException var6) {
         throw new IOException("Not a valid IES Parameter encoding.");
      } catch (ArrayIndexOutOfBoundsException var7) {
         throw new IOException("Not a valid IES Parameter encoding.");
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
      return "IES Parameters";
   }
}
