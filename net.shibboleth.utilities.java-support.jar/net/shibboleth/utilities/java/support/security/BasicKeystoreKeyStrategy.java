package net.shibboleth.utilities.java.support.security;

import java.io.IOException;
import java.io.InputStream;
import java.security.Key;
import java.security.KeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.crypto.SecretKey;
import net.shibboleth.utilities.java.support.annotation.Duration;
import net.shibboleth.utilities.java.support.annotation.constraint.NonNegative;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.logic.ConstraintViolationException;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.primitive.TimerSupport;
import net.shibboleth.utilities.java.support.resource.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicKeystoreKeyStrategy extends AbstractInitializableComponent implements DataSealerKeyStrategy {
   @Nonnull
   @NotEmpty
   public static final String CURRENT_VERSION_PROP = "CurrentVersion";
   @Nonnull
   private Logger log = LoggerFactory.getLogger(BasicKeystoreKeyStrategy.class);
   @NonnullAfterInit
   private String keystoreType = "JCEKS";
   @NonnullAfterInit
   private Resource keystoreResource;
   @NonnullAfterInit
   private Resource keyVersionResource;
   @NonnullAfterInit
   private String keystorePassword;
   @NonnullAfterInit
   private String keyAlias;
   @NonnullAfterInit
   private String keyPassword;
   @NonnullAfterInit
   private String currentAlias;
   @NonnullAfterInit
   private SecretKey defaultKey;
   @Duration
   @NonNegative
   private long updateInterval = 900000L;
   private Timer updateTaskTimer;
   private Timer internalTaskTimer;
   private TimerTask updateTask;

   public void setKeystoreType(@Nonnull @NotEmpty String type) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.keystoreType = (String)Constraint.isNotNull(StringSupport.trimOrNull(type), "Keystore type cannot be null or empty");
   }

   public void setKeystoreResource(@Nonnull @NotEmpty Resource resource) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.keystoreResource = (Resource)Constraint.isNotNull(resource, "Keystore resource cannot be null");
   }

   public void setKeyVersionResource(@Nonnull @NotEmpty Resource resource) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.keyVersionResource = (Resource)Constraint.isNotNull(resource, "Key version resource cannot be null");
   }

   public void setKeystorePassword(@Nullable String password) {
      synchronized(this) {
         if (password != null && !password.isEmpty()) {
            this.keystorePassword = password;
            if (this.isInitialized() && this.keyPassword != null) {
               try {
                  this.updateDefaultKey();
               } catch (KeyException var5) {
               }
            }
         } else {
            this.keystorePassword = null;
         }

      }
   }

   public void setKeyAlias(@Nonnull @NotEmpty String alias) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.keyAlias = (String)Constraint.isNotNull(StringSupport.trimOrNull(alias), "Key alias base cannot be null or empty");
   }

   public void setKeyPassword(@Nullable String password) {
      synchronized(this) {
         if (password != null && !password.isEmpty()) {
            this.keyPassword = password;
            if (this.isInitialized() && this.keystorePassword != null) {
               try {
                  this.updateDefaultKey();
               } catch (KeyException var5) {
               }
            }
         } else {
            this.keyPassword = null;
         }

      }
   }

   @Duration
   public void setUpdateInterval(@Duration @NonNegative long interval) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.updateInterval = Constraint.isGreaterThanOrEqual(0L, interval, "Update interval must be greater than or equal to zero");
   }

   public void setUpdateTaskTimer(@Nullable Timer timer) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.updateTaskTimer = timer;
   }

   public void doInitialize() throws ComponentInitializationException {
      try {
         try {
            Constraint.isNotNull(this.keystoreType, "Keystore type cannot be null");
            Constraint.isNotNull(this.keystoreResource, "Keystore resource cannot be null");
            Constraint.isNotNull(this.keyVersionResource, "Key version resource cannot be null");
            Constraint.isNotNull(this.keyAlias, "Key alias base cannot be null");
         } catch (ConstraintViolationException var2) {
            throw new ComponentInitializationException(var2);
         }

         this.updateDefaultKey();
      } catch (KeyException var3) {
         this.log.error("Error loading default key from base name '{}'", this.keyAlias, var3);
         throw new ComponentInitializationException("Exception loading the default key", var3);
      }

      if (this.updateInterval > 0L) {
         this.updateTask = new TimerTask() {
            public void run() {
               try {
                  BasicKeystoreKeyStrategy.this.updateDefaultKey();
               } catch (KeyException var2) {
               }

            }
         };
         if (this.updateTaskTimer == null) {
            this.internalTaskTimer = new Timer(TimerSupport.getTimerName(this), true);
         } else {
            this.internalTaskTimer = this.updateTaskTimer;
         }

         this.internalTaskTimer.schedule(this.updateTask, this.updateInterval, this.updateInterval);
      }

   }

   protected void doDestroy() {
      if (this.updateTask != null) {
         this.updateTask.cancel();
         this.updateTask = null;
         if (this.updateTaskTimer == null) {
            this.internalTaskTimer.cancel();
         }

         this.internalTaskTimer = null;
      }

      super.doDestroy();
   }

   @Nonnull
   public Pair getDefaultKey() throws KeyException {
      ComponentSupport.ifNotInitializedThrowUninitializedComponentException(this);
      synchronized(this) {
         if (this.defaultKey != null) {
            return new Pair(this.currentAlias, this.defaultKey);
         } else {
            throw new KeyException("Passwords not supplied, keystore is locked");
         }
      }
   }

   @Nonnull
   public SecretKey getKey(@Nonnull @NotEmpty String name) throws KeyException {
      synchronized(this) {
         if (this.defaultKey != null && name.equals(this.currentAlias)) {
            return this.defaultKey;
         }

         if (this.keystorePassword == null || this.keyPassword == null) {
            throw new KeyException("Passwords not supplied, keystore is locked");
         }
      }

      try {
         KeyStore ks = KeyStore.getInstance(this.keystoreType);
         ks.load(this.keystoreResource.getInputStream(), this.keystorePassword.toCharArray());
         Key loadedKey = ks.getKey(name, this.keyPassword.toCharArray());
         if (loadedKey == null) {
            this.log.info("Key '{}' not found", name);
            throw new KeyNotFoundException("Key was not present in keystore");
         } else if (!(loadedKey instanceof SecretKey)) {
            this.log.error("Key '{}' is not a symmetric key", name);
            throw new KeyException("Key was of incorrect type");
         } else {
            return (SecretKey)loadedKey;
         }
      } catch (NoSuchAlgorithmException | CertificateException | IOException | UnrecoverableKeyException | KeyStoreException var4) {
         this.log.error("Error loading key named '{}'", name, var4);
         throw new KeyException(var4);
      }
   }

   private void updateDefaultKey() throws KeyException {
      synchronized(this) {
         if (this.keystorePassword != null && this.keyPassword != null) {
            try {
               InputStream is = this.keyVersionResource.getInputStream();
               Throwable var3 = null;

               try {
                  Properties props = new Properties();
                  props.load(is);
                  StringBuilder builder = new StringBuilder(this.keyAlias);
                  builder.append(props.getProperty("CurrentVersion", ""));
                  String newAlias = builder.toString();
                  if (this.currentAlias == null) {
                     this.log.info("Loading initial default key: {}", newAlias);
                  } else {
                     if (this.currentAlias.equals(newAlias)) {
                        this.log.debug("Default key version has not changed, still {}", this.currentAlias);
                        return;
                     }

                     this.log.info("Updating default key from {} to {}", this.currentAlias, newAlias);
                  }

                  this.defaultKey = this.getKey(newAlias);
                  this.currentAlias = newAlias;
                  this.log.info("Default key updated to {}", this.currentAlias);
               } catch (Throwable var19) {
                  var3 = var19;
                  throw var19;
               } finally {
                  if (is != null) {
                     if (var3 != null) {
                        try {
                           is.close();
                        } catch (Throwable var18) {
                           var3.addSuppressed(var18);
                        }
                     } else {
                        is.close();
                     }
                  }

               }
            } catch (IOException var21) {
               this.log.error("IOException updating key version", var21);
               throw new KeyException(var21);
            }
         } else {
            this.log.info("Passwords not supplied, keystore left locked");
         }
      }
   }
}
