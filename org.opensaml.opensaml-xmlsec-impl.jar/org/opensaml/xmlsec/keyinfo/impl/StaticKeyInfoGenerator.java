package org.opensaml.xmlsec.keyinfo.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.keyinfo.KeyInfoGenerator;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.w3c.dom.Element;

public class StaticKeyInfoGenerator implements KeyInfoGenerator {
   private KeyInfo keyInfo;
   private Unmarshaller keyInfoUnmarshaller;
   private Marshaller keyInfoMarshaller;

   public StaticKeyInfoGenerator(@Nonnull KeyInfo newKeyInfo) {
      this.setKeyInfo(newKeyInfo);
   }

   @Nonnull
   public KeyInfo generate(@Nullable Credential credential) throws SecurityException {
      return this.keyInfo.getParent() == null ? this.keyInfo : this.clone(this.keyInfo);
   }

   @Nonnull
   public KeyInfo getKeyInfo() {
      return this.keyInfo;
   }

   public void setKeyInfo(@Nonnull KeyInfo newKeyInfo) {
      this.keyInfo = (KeyInfo)Constraint.isNotNull(newKeyInfo, "KeyInfo cannot be null");
   }

   @Nonnull
   private KeyInfo clone(@Nonnull KeyInfo origKeyInfo) throws SecurityException {
      Element origDOM = origKeyInfo.getDOM();
      if (origDOM == null) {
         try {
            this.getMarshaller().marshall(origKeyInfo);
         } catch (MarshallingException var6) {
            throw new SecurityException("Error marshalling the original KeyInfo during cloning", var6);
         }
      }

      KeyInfo newKeyInfo = null;

      try {
         newKeyInfo = (KeyInfo)this.getUnmarshaller().unmarshall(origKeyInfo.getDOM());
      } catch (UnmarshallingException var5) {
         throw new SecurityException("Error unmarshalling the new KeyInfo during cloning", var5);
      }

      if (origDOM == null) {
         origKeyInfo.releaseChildrenDOM(true);
         origKeyInfo.releaseDOM();
      } else {
         newKeyInfo.releaseChildrenDOM(true);
         newKeyInfo.releaseDOM();
      }

      return newKeyInfo;
   }

   @Nonnull
   private Marshaller getMarshaller() throws SecurityException {
      if (this.keyInfoMarshaller == null) {
         this.keyInfoMarshaller = XMLObjectProviderRegistrySupport.getMarshallerFactory().getMarshaller(KeyInfo.DEFAULT_ELEMENT_NAME);
         if (this.keyInfoMarshaller == null) {
            throw new SecurityException("Could not obtain KeyInfo marshaller from the configuration");
         }
      }

      return this.keyInfoMarshaller;
   }

   @Nonnull
   private Unmarshaller getUnmarshaller() throws SecurityException {
      if (this.keyInfoUnmarshaller == null) {
         this.keyInfoUnmarshaller = XMLObjectProviderRegistrySupport.getUnmarshallerFactory().getUnmarshaller(KeyInfo.DEFAULT_ELEMENT_NAME);
         if (this.keyInfoUnmarshaller == null) {
            throw new SecurityException("Could not obtain KeyInfo unmarshaller from the configuration");
         }
      }

      return this.keyInfoUnmarshaller;
   }
}
