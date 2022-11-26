package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.core.io.ClassPathResource;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;

public class ClassPathXmlApplicationContext extends AbstractXmlApplicationContext {
   @Nullable
   private Resource[] configResources;

   public ClassPathXmlApplicationContext() {
   }

   public ClassPathXmlApplicationContext(ApplicationContext parent) {
      super(parent);
   }

   public ClassPathXmlApplicationContext(String configLocation) throws BeansException {
      this(new String[]{configLocation}, true, (ApplicationContext)null);
   }

   public ClassPathXmlApplicationContext(String... configLocations) throws BeansException {
      this(configLocations, true, (ApplicationContext)null);
   }

   public ClassPathXmlApplicationContext(String[] configLocations, @Nullable ApplicationContext parent) throws BeansException {
      this(configLocations, true, parent);
   }

   public ClassPathXmlApplicationContext(String[] configLocations, boolean refresh) throws BeansException {
      this(configLocations, refresh, (ApplicationContext)null);
   }

   public ClassPathXmlApplicationContext(String[] configLocations, boolean refresh, @Nullable ApplicationContext parent) throws BeansException {
      super(parent);
      this.setConfigLocations(configLocations);
      if (refresh) {
         this.refresh();
      }

   }

   public ClassPathXmlApplicationContext(String path, Class clazz) throws BeansException {
      this(new String[]{path}, clazz);
   }

   public ClassPathXmlApplicationContext(String[] paths, Class clazz) throws BeansException {
      this(paths, clazz, (ApplicationContext)null);
   }

   public ClassPathXmlApplicationContext(String[] paths, Class clazz, @Nullable ApplicationContext parent) throws BeansException {
      super(parent);
      Assert.notNull(paths, (String)"Path array must not be null");
      Assert.notNull(clazz, (String)"Class argument must not be null");
      this.configResources = new Resource[paths.length];

      for(int i = 0; i < paths.length; ++i) {
         this.configResources[i] = new ClassPathResource(paths[i], clazz);
      }

      this.refresh();
   }

   @Nullable
   protected Resource[] getConfigResources() {
      return this.configResources;
   }
}
