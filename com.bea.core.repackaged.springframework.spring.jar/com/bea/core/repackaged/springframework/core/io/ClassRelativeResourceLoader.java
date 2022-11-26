package com.bea.core.repackaged.springframework.core.io;

import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;

public class ClassRelativeResourceLoader extends DefaultResourceLoader {
   private final Class clazz;

   public ClassRelativeResourceLoader(Class clazz) {
      Assert.notNull(clazz, (String)"Class must not be null");
      this.clazz = clazz;
      this.setClassLoader(clazz.getClassLoader());
   }

   protected Resource getResourceByPath(String path) {
      return new ClassRelativeContextResource(path, this.clazz);
   }

   private static class ClassRelativeContextResource extends ClassPathResource implements ContextResource {
      private final Class clazz;

      public ClassRelativeContextResource(String path, Class clazz) {
         super(path, clazz);
         this.clazz = clazz;
      }

      public String getPathWithinContext() {
         return this.getPath();
      }

      public Resource createRelative(String relativePath) {
         String pathToUse = StringUtils.applyRelativePath(this.getPath(), relativePath);
         return new ClassRelativeContextResource(pathToUse, this.clazz);
      }
   }
}
