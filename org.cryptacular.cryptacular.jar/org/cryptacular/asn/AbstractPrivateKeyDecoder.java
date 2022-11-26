package org.cryptacular.asn;

import org.cryptacular.EncodingException;
import org.cryptacular.util.PemUtil;

public abstract class AbstractPrivateKeyDecoder implements ASN1Decoder {
   public Object decode(byte[] encoded, Object... args) throws EncodingException {
      try {
         byte[] asn1Bytes;
         if (args != null && args.length > 0 && args[0] instanceof char[]) {
            asn1Bytes = this.decryptKey(encoded, (char[])((char[])args[0]));
         } else {
            asn1Bytes = this.tryConvertPem(encoded);
         }

         return this.decodeASN1(asn1Bytes);
      } catch (EncodingException var4) {
         throw var4;
      } catch (RuntimeException var5) {
         throw new EncodingException("Key encoding error", var5);
      }
   }

   protected byte[] tryConvertPem(byte[] input) {
      return PemUtil.isPem(input) ? PemUtil.decode(input) : input;
   }

   protected abstract byte[] decryptKey(byte[] var1, char[] var2);

   protected abstract Object decodeASN1(byte[] var1);
}
