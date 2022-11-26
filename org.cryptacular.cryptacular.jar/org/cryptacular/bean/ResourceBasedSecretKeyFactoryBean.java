package org.cryptacular.bean;

import java.io.IOException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.cryptacular.StreamException;
import org.cryptacular.io.Resource;
import org.cryptacular.util.StreamUtil;

public class ResourceBasedSecretKeyFactoryBean implements FactoryBean {
   private String algorithm;
   private Resource resource;

   public ResourceBasedSecretKeyFactoryBean() {
   }

   public ResourceBasedSecretKeyFactoryBean(Resource resource, String algorithm) {
      this.setResource(resource);
      this.setAlgorithm(algorithm);
   }

   public String getAlgorithm() {
      return this.algorithm;
   }

   public void setAlgorithm(String algorithm) {
      this.algorithm = algorithm;
   }

   public Resource getResource() {
      return this.resource;
   }

   public void setResource(Resource resource) {
      this.resource = resource;
   }

   public SecretKey newInstance() throws StreamException {
      try {
         return new SecretKeySpec(StreamUtil.readAll(this.resource.getInputStream()), this.algorithm);
      } catch (IOException var2) {
         throw new StreamException(var2);
      }
   }
}
