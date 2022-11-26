package org.python.bouncycastle.cert.ocsp;

import java.io.OutputStream;
import org.python.bouncycastle.asn1.DERNull;
import org.python.bouncycastle.asn1.DEROctetString;
import org.python.bouncycastle.asn1.ocsp.ResponderID;
import org.python.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.python.bouncycastle.asn1.x500.X500Name;
import org.python.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.python.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.python.bouncycastle.operator.DigestCalculator;

public class RespID {
   public static final AlgorithmIdentifier HASH_SHA1;
   ResponderID id;

   public RespID(ResponderID var1) {
      this.id = var1;
   }

   public RespID(X500Name var1) {
      this.id = new ResponderID(var1);
   }

   public RespID(SubjectPublicKeyInfo var1, DigestCalculator var2) throws OCSPException {
      try {
         if (!var2.getAlgorithmIdentifier().equals(HASH_SHA1)) {
            throw new IllegalArgumentException("only SHA-1 can be used with RespID - found: " + var2.getAlgorithmIdentifier().getAlgorithm());
         } else {
            OutputStream var3 = var2.getOutputStream();
            var3.write(var1.getPublicKeyData().getBytes());
            var3.close();
            this.id = new ResponderID(new DEROctetString(var2.getDigest()));
         }
      } catch (Exception var4) {
         throw new OCSPException("problem creating ID: " + var4, var4);
      }
   }

   public ResponderID toASN1Primitive() {
      return this.id;
   }

   public boolean equals(Object var1) {
      if (!(var1 instanceof RespID)) {
         return false;
      } else {
         RespID var2 = (RespID)var1;
         return this.id.equals(var2.id);
      }
   }

   public int hashCode() {
      return this.id.hashCode();
   }

   static {
      HASH_SHA1 = new AlgorithmIdentifier(OIWObjectIdentifiers.idSHA1, DERNull.INSTANCE);
   }
}
