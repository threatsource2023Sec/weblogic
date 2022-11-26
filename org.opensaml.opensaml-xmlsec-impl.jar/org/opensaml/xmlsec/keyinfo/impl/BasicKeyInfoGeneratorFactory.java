package org.opensaml.xmlsec.keyinfo.impl;

import com.google.common.base.Strings;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.keyinfo.KeyInfoGenerator;
import org.opensaml.xmlsec.keyinfo.KeyInfoGeneratorFactory;
import org.opensaml.xmlsec.keyinfo.KeyInfoSupport;
import org.opensaml.xmlsec.signature.KeyInfo;

public class BasicKeyInfoGeneratorFactory implements KeyInfoGeneratorFactory {
   private final BasicOptions options = this.newOptions();

   @Nonnull
   public Class getCredentialType() {
      return Credential.class;
   }

   public boolean handles(@Nonnull Credential credential) {
      return true;
   }

   @Nonnull
   public KeyInfoGenerator newInstance() {
      BasicOptions newOptions = this.options.clone();
      return new BasicKeyInfoGenerator(newOptions);
   }

   public boolean emitEntityIDAsKeyName() {
      return this.options.emitEntityIDAsKeyName;
   }

   public void setEmitEntityIDAsKeyName(boolean newValue) {
      this.options.emitEntityIDAsKeyName = newValue;
   }

   public boolean emitKeyNames() {
      return this.options.emitKeyNames;
   }

   public void setEmitKeyNames(boolean newValue) {
      this.options.emitKeyNames = newValue;
   }

   public boolean emitPublicKeyValue() {
      return this.options.emitPublicKeyValue;
   }

   public void setEmitPublicKeyValue(boolean newValue) {
      this.options.emitPublicKeyValue = newValue;
   }

   public boolean emitPublicDEREncodedKeyValue() {
      return this.options.emitPublicDEREncodedKeyValue;
   }

   public void setEmitPublicDEREncodedKeyValue(boolean newValue) {
      this.options.emitPublicDEREncodedKeyValue = newValue;
   }

   @Nonnull
   protected BasicOptions newOptions() {
      return new BasicOptions();
   }

   @Nonnull
   protected BasicOptions getOptions() {
      return this.options;
   }

   protected class BasicOptions implements Cloneable {
      private boolean emitKeyNames;
      private boolean emitEntityIDAsKeyName;
      private boolean emitPublicKeyValue;
      private boolean emitPublicDEREncodedKeyValue;

      protected BasicOptions clone() {
         try {
            return (BasicOptions)super.clone();
         } catch (CloneNotSupportedException var2) {
            return null;
         }
      }
   }

   public class BasicKeyInfoGenerator implements KeyInfoGenerator {
      private final BasicOptions options;
      private final XMLObjectBuilder keyInfoBuilder;

      protected BasicKeyInfoGenerator(@Nonnull BasicOptions newOptions) {
         this.options = newOptions;
         this.keyInfoBuilder = XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(KeyInfo.DEFAULT_ELEMENT_NAME);
      }

      @Nullable
      public KeyInfo generate(@Nullable Credential credential) throws SecurityException {
         if (credential == null) {
            return null;
         } else {
            KeyInfo keyInfo = (KeyInfo)this.keyInfoBuilder.buildObject(KeyInfo.DEFAULT_ELEMENT_NAME);
            this.processKeyNames(keyInfo, credential);
            this.processEntityID(keyInfo, credential);
            this.processPublicKey(keyInfo, credential);
            List children = keyInfo.getOrderedChildren();
            return children != null && children.size() > 0 ? keyInfo : null;
         }
      }

      protected void processKeyNames(@Nonnull KeyInfo keyInfo, @Nonnull Credential credential) {
         if (this.options.emitKeyNames) {
            Iterator var3 = credential.getKeyNames().iterator();

            while(var3.hasNext()) {
               String keyNameValue = (String)var3.next();
               if (!Strings.isNullOrEmpty(keyNameValue)) {
                  KeyInfoSupport.addKeyName(keyInfo, keyNameValue);
               }
            }
         }

      }

      protected void processEntityID(@Nonnull KeyInfo keyInfo, @Nonnull Credential credential) {
         if (this.options.emitEntityIDAsKeyName) {
            String keyNameValue = credential.getEntityId();
            if (!Strings.isNullOrEmpty(keyNameValue)) {
               KeyInfoSupport.addKeyName(keyInfo, keyNameValue);
            }
         }

      }

      protected void processPublicKey(@Nonnull KeyInfo keyInfo, @Nonnull Credential credential) throws SecurityException {
         if (credential.getPublicKey() != null) {
            if (this.options.emitPublicKeyValue) {
               KeyInfoSupport.addPublicKey(keyInfo, credential.getPublicKey());
            }

            if (this.options.emitPublicDEREncodedKeyValue) {
               try {
                  KeyInfoSupport.addDEREncodedPublicKey(keyInfo, credential.getPublicKey());
               } catch (NoSuchAlgorithmException var4) {
                  throw new SecurityException("Can't DER-encode key, unsupported key algorithm", var4);
               } catch (InvalidKeySpecException var5) {
                  throw new SecurityException("Can't DER-encode key, invalid key specification", var5);
               }
            }
         }

      }
   }
}
