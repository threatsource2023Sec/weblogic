package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.beans.factory.BeanNameAware;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.context.ApplicationContext;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.StringUtils;

public abstract class AbstractRefreshableConfigApplicationContext extends AbstractRefreshableApplicationContext implements BeanNameAware, InitializingBean {
   @Nullable
   private String[] configLocations;
   private boolean setIdCalled = false;

   public AbstractRefreshableConfigApplicationContext() {
   }

   public AbstractRefreshableConfigApplicationContext(@Nullable ApplicationContext parent) {
      super(parent);
   }

   public void setConfigLocation(String location) {
      this.setConfigLocations(StringUtils.tokenizeToStringArray(location, ",; \t\n"));
   }

   public void setConfigLocations(@Nullable String... locations) {
      if (locations != null) {
         Assert.noNullElements(locations, (String)"Config locations must not be null");
         this.configLocations = new String[locations.length];

         for(int i = 0; i < locations.length; ++i) {
            this.configLocations[i] = this.resolvePath(locations[i]).trim();
         }
      } else {
         this.configLocations = null;
      }

   }

   @Nullable
   protected String[] getConfigLocations() {
      return this.configLocations != null ? this.configLocations : this.getDefaultConfigLocations();
   }

   @Nullable
   protected String[] getDefaultConfigLocations() {
      return null;
   }

   protected String resolvePath(String path) {
      return this.getEnvironment().resolveRequiredPlaceholders(path);
   }

   public void setId(String id) {
      super.setId(id);
      this.setIdCalled = true;
   }

   public void setBeanName(String name) {
      if (!this.setIdCalled) {
         super.setId(name);
         this.setDisplayName("ApplicationContext '" + name + "'");
      }

   }

   public void afterPropertiesSet() {
      if (!this.isActive()) {
         this.refresh();
      }

   }
}
