package org.cryptacular.bean;

import java.io.IOException;
import java.security.PrivateKey;
import org.cryptacular.EncodingException;
import org.cryptacular.StreamException;
import org.cryptacular.io.Resource;
import org.cryptacular.util.KeyPairUtil;

public class ResourceBasedPrivateKeyFactoryBean implements FactoryBean {
   private Resource resource;
   private String password;

   public ResourceBasedPrivateKeyFactoryBean() {
   }

   public ResourceBasedPrivateKeyFactoryBean(Resource resource) {
      this.setResource(resource);
   }

   public ResourceBasedPrivateKeyFactoryBean(Resource resource, String decryptionPassword) {
      this.setResource(resource);
      this.setPassword(decryptionPassword);
   }

   public Resource getResource() {
      return this.resource;
   }

   public void setResource(Resource resource) {
      this.resource = resource;
   }

   public void setPassword(String decryptionPassword) {
      this.password = decryptionPassword;
   }

   public PrivateKey newInstance() throws EncodingException, StreamException {
      try {
         return this.password != null ? KeyPairUtil.readPrivateKey(this.resource.getInputStream(), this.password.toCharArray()) : KeyPairUtil.readPrivateKey(this.resource.getInputStream());
      } catch (IOException var2) {
         throw new StreamException(var2);
      }
   }
}
