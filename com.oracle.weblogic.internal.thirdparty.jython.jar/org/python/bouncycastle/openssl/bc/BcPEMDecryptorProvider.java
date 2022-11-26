package org.python.bouncycastle.openssl.bc;

import org.python.bouncycastle.openssl.PEMDecryptor;
import org.python.bouncycastle.openssl.PEMDecryptorProvider;
import org.python.bouncycastle.openssl.PEMException;
import org.python.bouncycastle.openssl.PasswordException;

public class BcPEMDecryptorProvider implements PEMDecryptorProvider {
   private final char[] password;

   public BcPEMDecryptorProvider(char[] var1) {
      this.password = var1;
   }

   public PEMDecryptor get(final String var1) {
      return new PEMDecryptor() {
         public byte[] decrypt(byte[] var1x, byte[] var2) throws PEMException {
            if (BcPEMDecryptorProvider.this.password == null) {
               throw new PasswordException("Password is null, but a password is required");
            } else {
               return PEMUtilities.crypt(false, var1x, BcPEMDecryptorProvider.this.password, var1, var2);
            }
         }
      };
   }
}
