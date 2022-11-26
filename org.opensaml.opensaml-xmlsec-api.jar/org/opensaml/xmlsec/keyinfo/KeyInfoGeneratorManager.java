package org.opensaml.xmlsec.keyinfo;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.security.credential.Credential;

public class KeyInfoGeneratorManager {
   private final Map factories = new HashMap(5);

   public void registerFactory(@Nonnull KeyInfoGeneratorFactory factory) {
      this.factories.put(factory.getCredentialType(), factory);
   }

   public void deregisterFactory(@Nonnull KeyInfoGeneratorFactory factory) {
      this.factories.remove(factory.getCredentialType());
   }

   @Nonnull
   public Collection getFactories() {
      return Collections.unmodifiableCollection(this.factories.values());
   }

   @Nullable
   public KeyInfoGeneratorFactory getFactory(@Nonnull Credential credential) {
      Constraint.isNotNull(credential, "Credential cannot be null");
      return (KeyInfoGeneratorFactory)this.factories.get(credential.getCredentialType());
   }
}
