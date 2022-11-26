package com.bea.security.providers.xacml.store.file;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import com.bea.security.providers.xacml.store.PolicyStoreConfigInfo;
import com.bea.security.providers.xacml.store.PolicyStoreConfigurator;
import com.bea.security.xacml.PolicyStoreException;
import com.bea.security.xacml.store.PolicyStore;

public class FilePolicyStoreConfigurator implements PolicyStoreConfigurator {
   private String fileStoreDirectory;
   private char[] password;
   private int cacheSize;
   private PolicyStoreConfigInfo info;
   private String encryptAlgorithm;

   public FilePolicyStoreConfigurator(PolicyStoreConfigInfo info, String fileStoreDirectory, String password, String encryptAlgorithm, int cacheSize) {
      this.fileStoreDirectory = fileStoreDirectory;
      this.info = info;
      this.password = password.toCharArray();
      this.cacheSize = cacheSize;
      this.encryptAlgorithm = encryptAlgorithm;
   }

   public PolicyStore newPolicyStore(int type, AttributeRegistry registry) throws DocumentParseException, URISyntaxException, PolicyStoreException {
      return new EncryptedFilePolicyStore(registry, this.info, this.fileStoreDirectory, this.password, type, this.encryptAlgorithm, this.cacheSize);
   }
}
