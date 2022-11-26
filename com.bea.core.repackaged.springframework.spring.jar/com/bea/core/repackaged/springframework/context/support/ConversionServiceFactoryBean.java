package com.bea.core.repackaged.springframework.context.support;

import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.core.convert.ConversionService;
import com.bea.core.repackaged.springframework.core.convert.support.ConversionServiceFactory;
import com.bea.core.repackaged.springframework.core.convert.support.DefaultConversionService;
import com.bea.core.repackaged.springframework.core.convert.support.GenericConversionService;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Set;

public class ConversionServiceFactoryBean implements FactoryBean, InitializingBean {
   @Nullable
   private Set converters;
   @Nullable
   private GenericConversionService conversionService;

   public void setConverters(Set converters) {
      this.converters = converters;
   }

   public void afterPropertiesSet() {
      this.conversionService = this.createConversionService();
      ConversionServiceFactory.registerConverters(this.converters, this.conversionService);
   }

   protected GenericConversionService createConversionService() {
      return new DefaultConversionService();
   }

   @Nullable
   public ConversionService getObject() {
      return this.conversionService;
   }

   public Class getObjectType() {
      return GenericConversionService.class;
   }

   public boolean isSingleton() {
      return true;
   }
}
