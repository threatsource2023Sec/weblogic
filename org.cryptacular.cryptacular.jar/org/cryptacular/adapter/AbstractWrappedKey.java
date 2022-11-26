package org.cryptacular.adapter;

import java.io.IOException;
import java.security.Key;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyInfoFactory;
import org.bouncycastle.crypto.util.SubjectPublicKeyInfoFactory;
import org.cryptacular.EncodingException;

public abstract class AbstractWrappedKey implements Key {
   public static final String PKCS8_FORMAT = "PKCS#8";
   public static final String X509_FORMAT = "X.509";
   protected final AsymmetricKeyParameter delegate;

   public AbstractWrappedKey(AsymmetricKeyParameter wrappedKey) {
      if (wrappedKey == null) {
         throw new IllegalArgumentException("Wrapped key cannot be null.");
      } else {
         this.delegate = wrappedKey;
      }
   }

   public String getFormat() {
      return this.delegate.isPrivate() ? "PKCS#8" : "X.509";
   }

   public byte[] getEncoded() {
      try {
         return this.delegate.isPrivate() ? PrivateKeyInfoFactory.createPrivateKeyInfo(this.delegate).getEncoded() : SubjectPublicKeyInfoFactory.createSubjectPublicKeyInfo(this.delegate).getEncoded();
      } catch (IOException var2) {
         throw new EncodingException("Key encoding error", var2);
      }
   }
}
