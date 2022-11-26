package com.bea.core.repackaged.springframework.beans.factory.support;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.MutablePropertyValues;
import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.CannotLoadBeanClassException;
import com.bea.core.repackaged.springframework.beans.factory.config.ConstructorArgumentValues;
import com.bea.core.repackaged.springframework.beans.factory.config.RuntimeBeanReference;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.support.EncodedResource;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.DefaultPropertiesPersister;
import com.bea.core.repackaged.springframework.util.PropertiesPersister;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

public class PropertiesBeanDefinitionReader extends AbstractBeanDefinitionReader {
   public static final String TRUE_VALUE = "true";
   public static final String SEPARATOR = ".";
   public static final String CLASS_KEY = "(class)";
   public static final String PARENT_KEY = "(parent)";
   public static final String SCOPE_KEY = "(scope)";
   public static final String SINGLETON_KEY = "(singleton)";
   public static final String ABSTRACT_KEY = "(abstract)";
   public static final String LAZY_INIT_KEY = "(lazy-init)";
   public static final String REF_SUFFIX = "(ref)";
   public static final String REF_PREFIX = "*";
   public static final String CONSTRUCTOR_ARG_PREFIX = "$";
   @Nullable
   private String defaultParentBean;
   private PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();

   public PropertiesBeanDefinitionReader(BeanDefinitionRegistry registry) {
      super(registry);
   }

   public void setDefaultParentBean(@Nullable String defaultParentBean) {
      this.defaultParentBean = defaultParentBean;
   }

   @Nullable
   public String getDefaultParentBean() {
      return this.defaultParentBean;
   }

   public void setPropertiesPersister(@Nullable PropertiesPersister propertiesPersister) {
      this.propertiesPersister = (PropertiesPersister)(propertiesPersister != null ? propertiesPersister : new DefaultPropertiesPersister());
   }

   public PropertiesPersister getPropertiesPersister() {
      return this.propertiesPersister;
   }

   public int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException {
      return this.loadBeanDefinitions((EncodedResource)(new EncodedResource(resource)), (String)null);
   }

   public int loadBeanDefinitions(Resource resource, @Nullable String prefix) throws BeanDefinitionStoreException {
      return this.loadBeanDefinitions(new EncodedResource(resource), prefix);
   }

   public int loadBeanDefinitions(EncodedResource encodedResource) throws BeanDefinitionStoreException {
      return this.loadBeanDefinitions((EncodedResource)encodedResource, (String)null);
   }

   public int loadBeanDefinitions(EncodedResource encodedResource, @Nullable String prefix) throws BeanDefinitionStoreException {
      if (this.logger.isTraceEnabled()) {
         this.logger.trace("Loading properties bean definitions from " + encodedResource);
      }

      Properties props = new Properties();

      try {
         InputStream is = encodedResource.getResource().getInputStream();
         Throwable var5 = null;

         try {
            if (encodedResource.getEncoding() != null) {
               this.getPropertiesPersister().load(props, (Reader)(new InputStreamReader(is, encodedResource.getEncoding())));
            } else {
               this.getPropertiesPersister().load(props, is);
            }
         } catch (Throwable var15) {
            var5 = var15;
            throw var15;
         } finally {
            if (is != null) {
               if (var5 != null) {
                  try {
                     is.close();
                  } catch (Throwable var14) {
                     var5.addSuppressed(var14);
                  }
               } else {
                  is.close();
               }
            }

         }

         int count = this.registerBeanDefinitions(props, prefix, encodedResource.getResource().getDescription());
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Loaded " + count + " bean definitions from " + encodedResource);
         }

         return count;
      } catch (IOException var17) {
         throw new BeanDefinitionStoreException("Could not parse properties from " + encodedResource.getResource(), var17);
      }
   }

   public int registerBeanDefinitions(ResourceBundle rb) throws BeanDefinitionStoreException {
      return this.registerBeanDefinitions((ResourceBundle)rb, (String)null);
   }

   public int registerBeanDefinitions(ResourceBundle rb, @Nullable String prefix) throws BeanDefinitionStoreException {
      Map map = new HashMap();
      Enumeration keys = rb.getKeys();

      while(keys.hasMoreElements()) {
         String key = (String)keys.nextElement();
         map.put(key, rb.getObject(key));
      }

      return this.registerBeanDefinitions((Map)map, prefix);
   }

   public int registerBeanDefinitions(Map map) throws BeansException {
      return this.registerBeanDefinitions((Map)map, (String)null);
   }

   public int registerBeanDefinitions(Map map, @Nullable String prefix) throws BeansException {
      return this.registerBeanDefinitions(map, prefix, "Map " + map);
   }

   public int registerBeanDefinitions(Map map, @Nullable String prefix, String resourceDescription) throws BeansException {
      if (prefix == null) {
         prefix = "";
      }

      int beanCount = 0;
      Iterator var5 = map.keySet().iterator();

      while(var5.hasNext()) {
         Object key = var5.next();
         if (!(key instanceof String)) {
            throw new IllegalArgumentException("Illegal key [" + key + "]: only Strings allowed");
         }

         String keyString = (String)key;
         if (keyString.startsWith(prefix)) {
            String nameAndProperty = keyString.substring(prefix.length());
            int sepIdx = true;
            int propKeyIdx = nameAndProperty.indexOf("[");
            int sepIdx;
            if (propKeyIdx != -1) {
               sepIdx = nameAndProperty.lastIndexOf(".", propKeyIdx);
            } else {
               sepIdx = nameAndProperty.lastIndexOf(".");
            }

            if (sepIdx != -1) {
               String beanName = nameAndProperty.substring(0, sepIdx);
               if (this.logger.isTraceEnabled()) {
                  this.logger.trace("Found bean name '" + beanName + "'");
               }

               if (!this.getRegistry().containsBeanDefinition(beanName)) {
                  this.registerBeanDefinition(beanName, map, prefix + beanName, resourceDescription);
                  ++beanCount;
               }
            } else if (this.logger.isDebugEnabled()) {
               this.logger.debug("Invalid bean name and property [" + nameAndProperty + "]");
            }
         }
      }

      return beanCount;
   }

   protected void registerBeanDefinition(String beanName, Map map, String prefix, String resourceDescription) throws BeansException {
      String className = null;
      String parent = null;
      String scope = "singleton";
      boolean isAbstract = false;
      boolean lazyInit = false;
      ConstructorArgumentValues cas = new ConstructorArgumentValues();
      MutablePropertyValues pvs = new MutablePropertyValues();
      Iterator var12 = map.entrySet().iterator();

      while(true) {
         while(true) {
            Map.Entry entry;
            String key;
            do {
               if (!var12.hasNext()) {
                  if (this.logger.isTraceEnabled()) {
                     this.logger.trace("Registering bean definition for bean name '" + beanName + "' with " + pvs);
                  }

                  if (parent == null && className == null && !beanName.equals(this.defaultParentBean)) {
                     parent = this.defaultParentBean;
                  }

                  try {
                     AbstractBeanDefinition bd = BeanDefinitionReaderUtils.createBeanDefinition(parent, className, this.getBeanClassLoader());
                     bd.setScope(scope);
                     bd.setAbstract(isAbstract);
                     bd.setLazyInit(lazyInit);
                     bd.setConstructorArgumentValues(cas);
                     bd.setPropertyValues(pvs);
                     this.getRegistry().registerBeanDefinition(beanName, bd);
                     return;
                  } catch (ClassNotFoundException var18) {
                     throw new CannotLoadBeanClassException(resourceDescription, beanName, className, var18);
                  } catch (LinkageError var19) {
                     throw new CannotLoadBeanClassException(resourceDescription, beanName, className, var19);
                  }
               }

               entry = (Map.Entry)var12.next();
               key = StringUtils.trimWhitespace((String)entry.getKey());
            } while(!key.startsWith(prefix + "."));

            String property = key.substring(prefix.length() + ".".length());
            if ("(class)".equals(property)) {
               className = StringUtils.trimWhitespace((String)entry.getValue());
            } else if ("(parent)".equals(property)) {
               parent = StringUtils.trimWhitespace((String)entry.getValue());
            } else {
               String ref;
               if ("(abstract)".equals(property)) {
                  ref = StringUtils.trimWhitespace((String)entry.getValue());
                  isAbstract = "true".equals(ref);
               } else if ("(scope)".equals(property)) {
                  scope = StringUtils.trimWhitespace((String)entry.getValue());
               } else if (!"(singleton)".equals(property)) {
                  if ("(lazy-init)".equals(property)) {
                     ref = StringUtils.trimWhitespace((String)entry.getValue());
                     lazyInit = "true".equals(ref);
                  } else if (property.startsWith("$")) {
                     int index;
                     if (property.endsWith("(ref)")) {
                        index = Integer.parseInt(property.substring(1, property.length() - "(ref)".length()));
                        cas.addIndexedArgumentValue(index, (Object)(new RuntimeBeanReference(entry.getValue().toString())));
                     } else {
                        index = Integer.parseInt(property.substring(1));
                        cas.addIndexedArgumentValue(index, this.readValue(entry));
                     }
                  } else if (property.endsWith("(ref)")) {
                     property = property.substring(0, property.length() - "(ref)".length());
                     ref = StringUtils.trimWhitespace((String)entry.getValue());
                     Object val = new RuntimeBeanReference(ref);
                     pvs.add(property, val);
                  } else {
                     pvs.add(property, this.readValue(entry));
                  }
               } else {
                  ref = StringUtils.trimWhitespace((String)entry.getValue());
                  scope = !"".equals(ref) && !"true".equals(ref) ? "prototype" : "singleton";
               }
            }
         }
      }
   }

   private Object readValue(Map.Entry entry) {
      Object val = entry.getValue();
      if (val instanceof String) {
         String strVal = (String)val;
         if (strVal.startsWith("*")) {
            String targetName = strVal.substring(1);
            if (targetName.startsWith("*")) {
               val = targetName;
            } else {
               val = new RuntimeBeanReference(targetName);
            }
         }
      }

      return val;
   }
}
