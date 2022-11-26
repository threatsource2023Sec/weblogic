package org.opensaml.xmlsec.keyinfo;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.LazyMap;
import org.opensaml.security.credential.Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NamedKeyInfoGeneratorManager {
   private final Logger log = LoggerFactory.getLogger(NamedKeyInfoGeneratorManager.class);
   private final Map managers = new LazyMap();
   private final KeyInfoGeneratorManager defaultManager = new KeyInfoGeneratorManager();
   private boolean useDefaultManager = true;

   public void setUseDefaultManager(boolean newValue) {
      this.useDefaultManager = newValue;
   }

   @Nonnull
   public Set getManagerNames() {
      return Collections.unmodifiableSet(this.managers.keySet());
   }

   @Nonnull
   public KeyInfoGeneratorManager getManager(@Nonnull String name) {
      KeyInfoGeneratorManager manager = (KeyInfoGeneratorManager)this.managers.get(name);
      if (manager == null) {
         manager = new KeyInfoGeneratorManager();
         this.managers.put(name, manager);
      }

      return manager;
   }

   public void removeManager(@Nonnull String name) {
      this.managers.remove(name);
   }

   public void registerFactory(@Nonnull String name, @Nonnull KeyInfoGeneratorFactory factory) {
      KeyInfoGeneratorManager manager = this.getManager(name);
      manager.registerFactory(factory);
   }

   public void deregisterFactory(@Nonnull String name, @Nonnull KeyInfoGeneratorFactory factory) {
      KeyInfoGeneratorManager manager = (KeyInfoGeneratorManager)this.managers.get(name);
      if (manager == null) {
         throw new IllegalArgumentException("Manager with name '" + name + "' does not exist");
      } else {
         manager.deregisterFactory(factory);
      }
   }

   public void registerDefaultFactory(@Nonnull KeyInfoGeneratorFactory factory) {
      this.defaultManager.registerFactory(factory);
   }

   public void deregisterDefaultFactory(@Nonnull KeyInfoGeneratorFactory factory) {
      this.defaultManager.deregisterFactory(factory);
   }

   @Nonnull
   public KeyInfoGeneratorManager getDefaultManager() {
      return this.defaultManager;
   }

   @Nullable
   public KeyInfoGeneratorFactory getFactory(@Nonnull String name, @Nonnull Credential credential) {
      KeyInfoGeneratorManager manager = (KeyInfoGeneratorManager)this.managers.get(name);
      if (manager == null) {
         if (!this.useDefaultManager) {
            this.log.warn("Manager with name '{}' was not registered, and 'useDefaultManager' is false", name);
            return null;
         }

         this.log.debug("Manger with name '{}' was not registered, using default manager", name);
         manager = this.defaultManager;
      }

      KeyInfoGeneratorFactory factory = manager.getFactory(credential);
      if (factory == null && this.useDefaultManager && manager != this.defaultManager) {
         this.log.debug("Factory not found in manager with name '{}', attempting lookup in default manager", name);
         factory = this.defaultManager.getFactory(credential);
      }

      return factory;
   }
}
