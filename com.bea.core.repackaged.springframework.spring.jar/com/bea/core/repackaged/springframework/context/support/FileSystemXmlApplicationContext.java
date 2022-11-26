package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.core.io.FileSystemResource;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.lang.Nullable;

public class FileSystemXmlApplicationContext extends AbstractXmlApplicationContext {
   public FileSystemXmlApplicationContext() {
   }

   public FileSystemXmlApplicationContext(ApplicationContext parent) {
      super(parent);
   }

   public FileSystemXmlApplicationContext(String configLocation) throws BeansException {
      this(new String[]{configLocation}, true, (ApplicationContext)null);
   }

   public FileSystemXmlApplicationContext(String... configLocations) throws BeansException {
      this(configLocations, true, (ApplicationContext)null);
   }

   public FileSystemXmlApplicationContext(String[] configLocations, ApplicationContext parent) throws BeansException {
      this(configLocations, true, parent);
   }

   public FileSystemXmlApplicationContext(String[] configLocations, boolean refresh) throws BeansException {
      this(configLocations, refresh, (ApplicationContext)null);
   }

   public FileSystemXmlApplicationContext(String[] configLocations, boolean refresh, @Nullable ApplicationContext parent) throws BeansException {
      super(parent);
      this.setConfigLocations(configLocations);
      if (refresh) {
         this.refresh();
      }

   }

   protected Resource getResourceByPath(String path) {
      if (path.startsWith("/")) {
         path = path.substring(1);
      }

      return new FileSystemResource(path);
   }
}
