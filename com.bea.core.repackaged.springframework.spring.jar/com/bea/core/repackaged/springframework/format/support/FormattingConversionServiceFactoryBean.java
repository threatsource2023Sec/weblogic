package com.bea.core.repackaged.springframework.format.support;

import com.bea.core.repackaged.springframework.beans.factory.FactoryBean;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.context.EmbeddedValueResolverAware;
import com.bea.core.repackaged.springframework.core.convert.support.ConversionServiceFactory;
import com.bea.core.repackaged.springframework.format.AnnotationFormatterFactory;
import com.bea.core.repackaged.springframework.format.Formatter;
import com.bea.core.repackaged.springframework.format.FormatterRegistrar;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.StringValueResolver;
import java.util.Iterator;
import java.util.Set;

public class FormattingConversionServiceFactoryBean implements FactoryBean, EmbeddedValueResolverAware, InitializingBean {
   @Nullable
   private Set converters;
   @Nullable
   private Set formatters;
   @Nullable
   private Set formatterRegistrars;
   private boolean registerDefaultFormatters = true;
   @Nullable
   private StringValueResolver embeddedValueResolver;
   @Nullable
   private FormattingConversionService conversionService;

   public void setConverters(Set converters) {
      this.converters = converters;
   }

   public void setFormatters(Set formatters) {
      this.formatters = formatters;
   }

   public void setFormatterRegistrars(Set formatterRegistrars) {
      this.formatterRegistrars = formatterRegistrars;
   }

   public void setRegisterDefaultFormatters(boolean registerDefaultFormatters) {
      this.registerDefaultFormatters = registerDefaultFormatters;
   }

   public void setEmbeddedValueResolver(StringValueResolver embeddedValueResolver) {
      this.embeddedValueResolver = embeddedValueResolver;
   }

   public void afterPropertiesSet() {
      this.conversionService = new DefaultFormattingConversionService(this.embeddedValueResolver, this.registerDefaultFormatters);
      ConversionServiceFactory.registerConverters(this.converters, this.conversionService);
      this.registerFormatters(this.conversionService);
   }

   private void registerFormatters(FormattingConversionService conversionService) {
      Iterator var2;
      if (this.formatters != null) {
         var2 = this.formatters.iterator();

         while(var2.hasNext()) {
            Object formatter = var2.next();
            if (formatter instanceof Formatter) {
               conversionService.addFormatter((Formatter)formatter);
            } else {
               if (!(formatter instanceof AnnotationFormatterFactory)) {
                  throw new IllegalArgumentException("Custom formatters must be implementations of Formatter or AnnotationFormatterFactory");
               }

               conversionService.addFormatterForFieldAnnotation((AnnotationFormatterFactory)formatter);
            }
         }
      }

      if (this.formatterRegistrars != null) {
         var2 = this.formatterRegistrars.iterator();

         while(var2.hasNext()) {
            FormatterRegistrar registrar = (FormatterRegistrar)var2.next();
            registrar.registerFormatters(conversionService);
         }
      }

   }

   @Nullable
   public FormattingConversionService getObject() {
      return this.conversionService;
   }

   public Class getObjectType() {
      return FormattingConversionService.class;
   }

   public boolean isSingleton() {
      return true;
   }
}
