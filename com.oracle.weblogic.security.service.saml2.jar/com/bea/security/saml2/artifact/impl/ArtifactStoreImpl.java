package com.bea.security.saml2.artifact.impl;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.saml2.SingleSignOnServicesConfigSpi;
import com.bea.security.saml2.artifact.ArtifactDataObject;
import com.bea.security.saml2.artifact.ArtifactStore;
import com.bea.security.saml2.artifact.SAML2ArtifactException;
import com.bea.security.saml2.artifact.SAMLArtifact;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import com.bea.security.saml2.util.SAML2Utils;
import com.bea.security.saml2.util.cache.SAML2Cache;
import com.bea.security.saml2.util.cache.SAML2CacheException;
import com.bea.security.saml2.util.cache.SAML2CacheFactory;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import org.opensaml.saml.common.SAMLObject;

public class ArtifactStoreImpl implements ArtifactStore {
   private final SecureRandom random = new SecureRandom();
   private SAML2Cache messageStore;
   private LoggerSpi log;
   private short ARS_ENDPOINT_INDEX = 0;
   private byte[] entityId_HashValue;

   public ArtifactStoreImpl(SAML2ConfigSpi config) {
      this.log = config.getLogger();
      SingleSignOnServicesConfigSpi localConfig = config.getLocalConfiguration();
      this.messageStore = SAML2CacheFactory.createArtifactStoreCache(config);

      try {
         this.entityId_HashValue = SAML2Utils.sha1Hash(localConfig.getEntityID());
      } catch (Exception var4) {
         if (this.log != null && this.log.isDebugEnabled()) {
            this.log.debug("can't get SHA-1 hash value of entity id.", var4);
         }
      }

   }

   public String store(SAMLObject samlObj, String partnerId) throws SAML2ArtifactException {
      if (samlObj != null && partnerId != null) {
         byte[] messageHandle = this.genMessageHandle();

         try {
            SAMLArtifact artifact = new SAMLArtifact(this.ARS_ENDPOINT_INDEX, this.entityId_HashValue, messageHandle);
            String base64Art = artifact.toBase64String();
            ArtifactDataObject ado = new ArtifactDataObject(samlObj, partnerId);
            this.messageStore.put(base64Art, ado);
            return base64Art;
         } catch (IllegalArgumentException var7) {
            throw new SAML2ArtifactException(var7);
         } catch (NoSuchAlgorithmException var8) {
            throw new SAML2ArtifactException(var8);
         } catch (SAML2CacheException var9) {
            throw new SAML2ArtifactException(var9);
         }
      } else {
         throw new IllegalArgumentException("The SAMLObject and partnerId to be stored can not be null.");
      }
   }

   public ArtifactDataObject retrieve(String base64Atr) throws SAML2ArtifactException {
      if (base64Atr != null && base64Atr.length() != 0) {
         ArtifactDataObject ado = null;

         try {
            ado = (ArtifactDataObject)this.messageStore.remove(base64Atr);
         } catch (SAML2CacheException var4) {
            throw new SAML2ArtifactException(var4);
         }

         if (ado == null && this.log.isDebugEnabled()) {
            this.log.debug("retrieve: no message was found in cache with the messageHandle, return null.");
         }

         return ado;
      } else {
         return null;
      }
   }

   public void updateConfig(int maxSize, int timeout) throws SAML2ArtifactException {
      try {
         this.messageStore.configUpdated(maxSize, timeout);
      } catch (SAML2CacheException var4) {
         throw new SAML2ArtifactException(var4);
      }
   }

   private byte[] genMessageHandle() {
      byte[] buf = new byte[20];
      this.random.nextBytes(buf);
      return buf;
   }
}
