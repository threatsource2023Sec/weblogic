package org.opensaml.xmlsec.signature.support;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import javax.annotation.Nonnull;
import org.opensaml.xmlsec.signature.Signature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Signer {
   private static SignerProvider signerInstance;

   protected Signer() {
   }

   public static void signObjects(@Nonnull List signatures) throws SignatureException {
      SignerProvider signer = getSignerProvider();
      getLogger().debug("Using a signer of implementation: {}", signer.getClass().getName());
      Iterator var2 = signatures.iterator();

      while(var2.hasNext()) {
         Signature signature = (Signature)var2.next();
         signer.signObject(signature);
      }

   }

   public static void signObject(@Nonnull Signature signature) throws SignatureException {
      SignerProvider signer = getSignerProvider();
      getLogger().debug("Using a signer of implemenation: {}", signer.getClass().getName());
      signer.signObject(signature);
   }

   @Nonnull
   private static SignerProvider getSignerProvider() throws SignatureException {
      if (signerInstance == null) {
         ServiceLoader loader = ServiceLoader.load(SignerProvider.class);
         Iterator iterator = loader.iterator();
         if (!iterator.hasNext()) {
            throw new SignatureException("Could not load a signer implementation via service API");
         }

         signerInstance = (SignerProvider)iterator.next();
      }

      return signerInstance;
   }

   @Nonnull
   private static Logger getLogger() {
      return LoggerFactory.getLogger(Signer.class);
   }
}
