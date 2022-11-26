package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.factory.config.BeanDefinition;
import com.bea.core.repackaged.springframework.core.io.AbstractResource;
import com.bea.core.repackaged.springframework.util.Assert;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

class BeanDefinitionResource extends AbstractResource {
   private final BeanDefinition beanDefinition;

   public BeanDefinitionResource(BeanDefinition beanDefinition) {
      Assert.notNull(beanDefinition, (String)"BeanDefinition must not be null");
      this.beanDefinition = beanDefinition;
   }

   public final BeanDefinition getBeanDefinition() {
      return this.beanDefinition;
   }

   public boolean exists() {
      return false;
   }

   public boolean isReadable() {
      return false;
   }

   public InputStream getInputStream() throws IOException {
      throw new FileNotFoundException("Resource cannot be opened because it points to " + this.getDescription());
   }

   public String getDescription() {
      return "BeanDefinition defined in " + this.beanDefinition.getResourceDescription();
   }

   public boolean equals(Object other) {
      return this == other || other instanceof BeanDefinitionResource && ((BeanDefinitionResource)other).beanDefinition.equals(this.beanDefinition);
   }

   public int hashCode() {
      return this.beanDefinition.hashCode();
   }
}
