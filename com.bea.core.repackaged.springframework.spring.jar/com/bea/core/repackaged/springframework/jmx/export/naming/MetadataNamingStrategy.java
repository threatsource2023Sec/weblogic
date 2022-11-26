package com.bea.core.repackaged.springframework.jmx.export.naming;

import com.bea.core.repackaged.springframework.aop.support.AopUtils;
import com.bea.core.repackaged.springframework.beans.factory.InitializingBean;
import com.bea.core.repackaged.springframework.jmx.export.metadata.JmxAttributeSource;
import com.bea.core.repackaged.springframework.jmx.export.metadata.ManagedResource;
import com.bea.core.repackaged.springframework.jmx.support.ObjectNameManager;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.Assert;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.util.Hashtable;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

public class MetadataNamingStrategy implements ObjectNamingStrategy, InitializingBean {
   @Nullable
   private JmxAttributeSource attributeSource;
   @Nullable
   private String defaultDomain;

   public MetadataNamingStrategy() {
   }

   public MetadataNamingStrategy(JmxAttributeSource attributeSource) {
      Assert.notNull(attributeSource, (String)"JmxAttributeSource must not be null");
      this.attributeSource = attributeSource;
   }

   public void setAttributeSource(JmxAttributeSource attributeSource) {
      Assert.notNull(attributeSource, (String)"JmxAttributeSource must not be null");
      this.attributeSource = attributeSource;
   }

   public void setDefaultDomain(String defaultDomain) {
      this.defaultDomain = defaultDomain;
   }

   public void afterPropertiesSet() {
      if (this.attributeSource == null) {
         throw new IllegalArgumentException("Property 'attributeSource' is required");
      }
   }

   public ObjectName getObjectName(Object managedBean, @Nullable String beanKey) throws MalformedObjectNameException {
      Assert.state(this.attributeSource != null, "No JmxAttributeSource set");
      Class managedClass = AopUtils.getTargetClass(managedBean);
      ManagedResource mr = this.attributeSource.getManagedResource(managedClass);
      if (mr != null && StringUtils.hasText(mr.getObjectName())) {
         return ObjectNameManager.getInstance(mr.getObjectName());
      } else {
         Assert.state(beanKey != null, "No ManagedResource attribute and no bean key specified");

         try {
            return ObjectNameManager.getInstance(beanKey);
         } catch (MalformedObjectNameException var8) {
            String domain = this.defaultDomain;
            if (domain == null) {
               domain = ClassUtils.getPackageName(managedClass);
            }

            Hashtable properties = new Hashtable();
            properties.put("type", ClassUtils.getShortName(managedClass));
            properties.put("name", beanKey);
            return ObjectNameManager.getInstance(domain, properties);
         }
      }
   }
}
