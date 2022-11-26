package com.bea.core.repackaged.springframework.jmx.export.annotation;

import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.jmx.export.MBeanExporter;
import com.bea.core.repackaged.springframework.jmx.export.assembler.MetadataMBeanInfoAssembler;
import com.bea.core.repackaged.springframework.jmx.export.naming.MetadataNamingStrategy;

public class AnnotationMBeanExporter extends MBeanExporter {
   private final AnnotationJmxAttributeSource annotationSource = new AnnotationJmxAttributeSource();
   private final MetadataNamingStrategy metadataNamingStrategy;
   private final MetadataMBeanInfoAssembler metadataAssembler;

   public AnnotationMBeanExporter() {
      this.metadataNamingStrategy = new MetadataNamingStrategy(this.annotationSource);
      this.metadataAssembler = new MetadataMBeanInfoAssembler(this.annotationSource);
      this.setNamingStrategy(this.metadataNamingStrategy);
      this.setAssembler(this.metadataAssembler);
      this.setAutodetectMode(3);
   }

   public void setDefaultDomain(String defaultDomain) {
      this.metadataNamingStrategy.setDefaultDomain(defaultDomain);
   }

   public void setBeanFactory(BeanFactory beanFactory) {
      super.setBeanFactory(beanFactory);
      this.annotationSource.setBeanFactory(beanFactory);
   }
}
