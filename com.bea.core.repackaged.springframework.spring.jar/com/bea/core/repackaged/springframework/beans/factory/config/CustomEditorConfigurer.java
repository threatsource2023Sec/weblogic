package com.bea.core.repackaged.springframework.beans.factory.config;

import com.bea.core.repackaged.apache.commons.logging.Log;
import com.bea.core.repackaged.apache.commons.logging.LogFactory;
import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.PropertyEditorRegistrar;
import com.bea.core.repackaged.springframework.core.Ordered;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Map;

public class CustomEditorConfigurer implements BeanFactoryPostProcessor, Ordered {
   protected final Log logger = LogFactory.getLog(this.getClass());
   private int order = Integer.MAX_VALUE;
   @Nullable
   private PropertyEditorRegistrar[] propertyEditorRegistrars;
   @Nullable
   private Map customEditors;

   public void setOrder(int order) {
      this.order = order;
   }

   public int getOrder() {
      return this.order;
   }

   public void setPropertyEditorRegistrars(PropertyEditorRegistrar[] propertyEditorRegistrars) {
      this.propertyEditorRegistrars = propertyEditorRegistrars;
   }

   public void setCustomEditors(Map customEditors) {
      this.customEditors = customEditors;
   }

   public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
      if (this.propertyEditorRegistrars != null) {
         PropertyEditorRegistrar[] var2 = this.propertyEditorRegistrars;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            PropertyEditorRegistrar propertyEditorRegistrar = var2[var4];
            beanFactory.addPropertyEditorRegistrar(propertyEditorRegistrar);
         }
      }

      if (this.customEditors != null) {
         this.customEditors.forEach(beanFactory::registerCustomEditor);
      }

   }
}
