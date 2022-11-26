package org.python.bouncycastle.cert.crmf;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.python.bouncycastle.asn1.crmf.EncryptedValue;
import org.python.bouncycastle.asn1.x509.Certificate;
import org.python.bouncycastle.cert.X509CertificateHolder;
import org.python.bouncycastle.operator.InputDecryptor;
import org.python.bouncycastle.util.Strings;
import org.python.bouncycastle.util.io.Streams;

public class EncryptedValueParser {
   private EncryptedValue value;
   private EncryptedValuePadder padder;

   public EncryptedValueParser(EncryptedValue var1) {
      this.value = var1;
   }

   public EncryptedValueParser(EncryptedValue var1, EncryptedValuePadder var2) {
      this.value = var1;
      this.padder = var2;
   }

   private byte[] decryptValue(ValueDecryptorGenerator var1) throws CRMFException {
      if (this.value.getIntendedAlg() != null) {
         throw new UnsupportedOperationException();
      } else if (this.value.getValueHint() != null) {
         throw new UnsupportedOperationException();
      } else {
         InputDecryptor var2 = var1.getValueDecryptor(this.value.getKeyAlg(), this.value.getSymmAlg(), this.value.getEncSymmKey().getBytes());
         InputStream var3 = var2.getInputStream(new ByteArrayInputStream(this.value.getEncValue().getBytes()));

         try {
            byte[] var4 = Streams.readAll(var3);
            return this.padder != null ? this.padder.getUnpaddedData(var4) : var4;
         } catch (IOException var5) {
            throw new CRMFException("Cannot parse decrypted data: " + var5.getMessage(), var5);
         }
      }
   }

   public X509CertificateHolder readCertificateHolder(ValueDecryptorGenerator var1) throws CRMFException {
      return new X509CertificateHolder(Certificate.getInstance(this.decryptValue(var1)));
   }

   public char[] readPassphrase(ValueDecryptorGenerator var1) throws CRMFException {
      return Strings.fromUTF8ByteArray(this.decryptValue(var1)).toCharArray();
   }
}
