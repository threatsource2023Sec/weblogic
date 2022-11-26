package org.python.bouncycastle.openssl.jcajce;

import java.security.Provider;
import org.python.bouncycastle.jcajce.util.DefaultJcaJceHelper;
import org.python.bouncycastle.jcajce.util.JcaJceHelper;
import org.python.bouncycastle.jcajce.util.NamedJcaJceHelper;
import org.python.bouncycastle.jcajce.util.ProviderJcaJceHelper;
import org.python.bouncycastle.openssl.PEMDecryptor;
import org.python.bouncycastle.openssl.PEMDecryptorProvider;
import org.python.bouncycastle.openssl.PEMException;
import org.python.bouncycastle.openssl.PasswordException;

public class JcePEMDecryptorProviderBuilder {
   private JcaJceHelper helper = new DefaultJcaJceHelper();

   public JcePEMDecryptorProviderBuilder setProvider(Provider var1) {
      this.helper = new ProviderJcaJceHelper(var1);
      return this;
   }

   public JcePEMDecryptorProviderBuilder setProvider(String var1) {
      this.helper = new NamedJcaJceHelper(var1);
      return this;
   }

   public PEMDecryptorProvider build(final char[] var1) {
      return new PEMDecryptorProvider() {
         public PEMDecryptor get(final String var1x) {
            return new PEMDecryptor() {
               public byte[] decrypt(byte[] var1xx, byte[] var2) throws PEMException {
                  if (var1 == null) {
                     throw new PasswordException("Password is null, but a password is required");
                  } else {
                     return PEMUtilities.crypt(false, JcePEMDecryptorProviderBuilder.this.helper, var1xx, var1, var1x, var2);
                  }
               }
            };
         }
      };
   }
}
