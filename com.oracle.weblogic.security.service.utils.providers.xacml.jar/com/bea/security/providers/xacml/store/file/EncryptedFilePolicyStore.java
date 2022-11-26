package com.bea.security.providers.xacml.store.file;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.JAXPFactoryService;
import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URI;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicySet;
import com.bea.common.store.bootstrap.BootStrapPersistence;
import com.bea.common.store.bootstrap.BootStrapService;
import com.bea.common.store.bootstrap.EntryConverter;
import com.bea.security.providers.xacml.store.GlobalPolicyUpdateImpl;
import com.bea.security.providers.xacml.store.PolicyStoreConfigInfo;
import com.bea.security.utils.DigestUtils;
import com.bea.security.utils.encryption.EncryptedStreamFactory;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.policy.PolicyUtils;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;

public class EncryptedFilePolicyStore extends FilePolicyStore implements BootStrapPersistence {
   private LoggerSpi log;
   private AttributeRegistry registry;
   private JAXPFactoryService jaxpService;

   public EncryptedFilePolicyStore(AttributeRegistry registry, PolicyStoreConfigInfo info, String fileStoreDirectory, char[] password, int storeType, String encryptAlgorithm, int cacheSize) throws PolicyStoreException, DocumentParseException, URISyntaxException {
      super(registry, new File(fileStoreDirectory), info.getJaxpService(), new EncryptedFileAccess(storeType, password, encryptAlgorithm), cacheSize, password, encryptAlgorithm, storeType, info.getLogger());
      this.log = info.getLogger();
      this.registry = registry;
      this.jaxpService = info.getJaxpService();
      if (this.store.exists() && this.store.isDirectory()) {
         if (this.log.isDebugEnabled()) {
            this.log.debug("Constructing EncryptedFilePolicyStore under directory: " + fileStoreDirectory);
            this.log.debug("Store type: " + (storeType == 0 ? "Authorization" : "RoleMapping"));
         }

         this.bootstrap(info, storeType);
      } else {
         throw new PolicyStoreException("The specified store " + this.store.getAbsolutePath() + " is not a directory (and can't be created)");
      }
   }

   public EncryptedFilePolicyStore(AttributeRegistry registry, JAXPFactoryService jaxp, LoggerSpi logger, String fileStoreDirectory, char[] password, int storeType, String encryptAlgorithm, int cacheSize) throws PolicyStoreException, DocumentParseException, URISyntaxException {
      super(registry, new File(fileStoreDirectory), jaxp, new EncryptedFileAccess(storeType, password, encryptAlgorithm), cacheSize, password, encryptAlgorithm, storeType, logger);
      this.log = logger;
   }

   private void bootstrap(PolicyStoreConfigInfo info, int storeType) {
      if (this.log.isDebugEnabled()) {
         this.log.debug("Bootstrapping file store");
      }

      EntryConverter xacmlEntryConverter = new FileEntryConverter();
      BootStrapService bs = info.getBootstrapService();
      if (bs == null) {
         this.log.debug("bootstrap service is null, no bootstrap performed.");
      } else {
         if (storeType == 0) {
            bs.loadLDIFXACMLAuthorizerTemplate(this.log, this, xacmlEntryConverter, info.getDomainName(), info.getRealmName());
            bs.updateXACMLAuthorizerPolicies(this.log, new GlobalPolicyUpdateImpl(this, this.log), this, info.getDomainName(), info.getRealmName());
         } else {
            bs.loadLDIFXACMLRoleMapperTemplate(this.log, this, xacmlEntryConverter, info.getDomainName(), info.getRealmName());
            bs.updateXACMLRoleMapperPolicies(this.log, new GlobalPolicyUpdateImpl(this, this.log), this, info.getDomainName(), info.getRealmName());
         }

      }
   }

   public boolean has(Object obj) throws Exception {
      if (this.log.isDebugEnabled()) {
         this.log.debug("checkExist: " + obj);
      }

      if (obj instanceof XACMLEntry) {
         XACMLEntry entry = (XACMLEntry)obj;
         return this.hasPolicy(new URI(entry.getId()), entry.getVersion());
      } else {
         return this.wlsCollectionStore.has(obj);
      }
   }

   public void makePersistentAll(Collection objects) throws Exception {
      if (this.log.isDebugEnabled()) {
         this.log.debug("makePersistentAll: persistenting " + objects);
      }

      Iterator var2 = objects.iterator();

      while(var2.hasNext()) {
         Object obj = var2.next();
         if (obj instanceof XACMLEntry) {
            XACMLEntry role = (XACMLEntry)obj;
            InputStream stream = new ByteArrayInputStream(role.getXacmlDocument());
            AbstractPolicy policy = PolicyUtils.read(this.registry, (InputStream)stream, this.jaxpService.newDocumentBuilderFactory());
            int status = false;

            int status;
            try {
               status = Integer.parseInt(role.getStatus());
            } catch (NumberFormatException var9) {
               status = 0;
            }

            if (policy instanceof PolicySet) {
               this.addPolicySet((PolicySet)policy, status, role.getMetadata());
            } else {
               this.addPolicy((Policy)policy, status, role.getMetadata());
            }
         } else {
            this.wlsCollectionStore.makePersistence(obj);
         }
      }

   }

   public void close() {
   }

   public String getStoreId() {
      try {
         return "file_" + DigestUtils.digestSHA1(this.store.getAbsolutePath());
      } catch (Exception var2) {
         this.log.warn("Unable to hash the store id", var2);
         return this.store.getAbsolutePath();
      }
   }

   public Collection postProcessObjects(Collection objs) {
      return objs;
   }

   private static class EncryptedFileAccess implements FileAccess {
      String type;
      private char[] password;
      private String encryptAlgorithm;
      private static final String SUFFIX = ".xacml";

      public EncryptedFileAccess(int storeType, char[] password, String encryptAlgorithm) throws PolicyStoreException {
         if (password != null && password.length != 0) {
            this.password = password;
            this.encryptAlgorithm = encryptAlgorithm;
            this.type = "role";
            if (storeType == 0) {
               this.type = "atz";
            }

         } else {
            throw new PolicyStoreException("The password for the policy store is not set.");
         }
      }

      public String getPolicyFileNamePrefix(boolean isPolicy) {
         String prefix = "pol";
         if (!isPolicy) {
            prefix = "pset";
         }

         return this.type + "_" + prefix + "_";
      }

      public String getPolicyFileNameSuffix() {
         return ".xacml";
      }

      public String getIndexFileName() {
         return this.type + "_xacml.index";
      }

      public boolean isAcceptableName(String name) {
         return name.startsWith(this.type) && name.endsWith(".xacml");
      }

      public InputStream filterRead(InputStream stream) {
         return EncryptedStreamFactory.getDecryptedInputStream(stream, this.password, this.encryptAlgorithm);
      }

      public OutputStream filterWrite(OutputStream stream) {
         return new BufferedOutputStream(EncryptedStreamFactory.getEncryptedOutputStream(stream, this.password, this.encryptAlgorithm));
      }
   }
}
