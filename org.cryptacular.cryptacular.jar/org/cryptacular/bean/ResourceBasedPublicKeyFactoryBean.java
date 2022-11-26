package org.cryptacular.bean;

import java.io.IOException;
import java.security.PublicKey;
import org.cryptacular.EncodingException;
import org.cryptacular.StreamException;
import org.cryptacular.io.Resource;
import org.cryptacular.util.KeyPairUtil;

public class ResourceBasedPublicKeyFactoryBean implements FactoryBean {
   private Resource resource;

   public ResourceBasedPublicKeyFactoryBean() {
   }

   public ResourceBasedPublicKeyFactoryBean(Resource resource) {
      this.setResource(resource);
   }

   public Resource getResource() {
      return this.resource;
   }

   public void setResource(Resource resource) {
      this.resource = resource;
   }

   public PublicKey newInstance() throws EncodingException, StreamException {
      try {
         return KeyPairUtil.readPublicKey(this.resource.getInputStream());
      } catch (IOException var2) {
         throw new StreamException(var2);
      }
   }
}
