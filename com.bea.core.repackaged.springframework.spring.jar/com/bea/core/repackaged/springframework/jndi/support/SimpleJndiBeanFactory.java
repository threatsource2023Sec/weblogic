package com.bea.core.repackaged.springframework.jndi.support;

import com.bea.core.repackaged.springframework.beans.BeansException;
import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.BeanFactory;
import com.bea.core.repackaged.springframework.beans.factory.BeanNotOfRequiredTypeException;
import com.bea.core.repackaged.springframework.beans.factory.NoSuchBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.NoUniqueBeanDefinitionException;
import com.bea.core.repackaged.springframework.beans.factory.ObjectProvider;
import com.bea.core.repackaged.springframework.core.ResolvableType;
import com.bea.core.repackaged.springframework.jndi.JndiLocatorSupport;
import com.bea.core.repackaged.springframework.jndi.TypeMismatchNamingException;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;

public class SimpleJndiBeanFactory extends JndiLocatorSupport implements BeanFactory {
   private final Set shareableResources = new HashSet();
   private final Map singletonObjects = new HashMap();
   private final Map resourceTypes = new HashMap();

   public SimpleJndiBeanFactory() {
      this.setResourceRef(true);
   }

   public void addShareableResource(String shareableResource) {
      this.shareableResources.add(shareableResource);
   }

   public void setShareableResources(String... shareableResources) {
      Collections.addAll(this.shareableResources, shareableResources);
   }

   public Object getBean(String name) throws BeansException {
      return this.getBean(name, Object.class);
   }

   public Object getBean(String name, Class requiredType) throws BeansException {
      try {
         return this.isSingleton(name) ? this.doGetSingleton(name, requiredType) : this.lookup(name, requiredType);
      } catch (NameNotFoundException var4) {
         throw new NoSuchBeanDefinitionException(name, "not found in JNDI environment");
      } catch (TypeMismatchNamingException var5) {
         throw new BeanNotOfRequiredTypeException(name, var5.getRequiredType(), var5.getActualType());
      } catch (NamingException var6) {
         throw new BeanDefinitionStoreException("JNDI environment", name, "JNDI lookup failed", var6);
      }
   }

   public Object getBean(String name, @Nullable Object... args) throws BeansException {
      if (args != null) {
         throw new UnsupportedOperationException("SimpleJndiBeanFactory does not support explicit bean creation arguments");
      } else {
         return this.getBean(name);
      }
   }

   public Object getBean(Class requiredType) throws BeansException {
      return this.getBean(requiredType.getSimpleName(), requiredType);
   }

   public Object getBean(Class requiredType, @Nullable Object... args) throws BeansException {
      if (args != null) {
         throw new UnsupportedOperationException("SimpleJndiBeanFactory does not support explicit bean creation arguments");
      } else {
         return this.getBean(requiredType);
      }
   }

   public ObjectProvider getBeanProvider(final Class requiredType) {
      return new ObjectProvider() {
         public Object getObject() throws BeansException {
            return SimpleJndiBeanFactory.this.getBean(requiredType);
         }

         public Object getObject(Object... args) throws BeansException {
            return SimpleJndiBeanFactory.this.getBean(requiredType, args);
         }

         @Nullable
         public Object getIfAvailable() throws BeansException {
            try {
               return SimpleJndiBeanFactory.this.getBean(requiredType);
            } catch (NoUniqueBeanDefinitionException var2) {
               throw var2;
            } catch (NoSuchBeanDefinitionException var3) {
               return null;
            }
         }

         @Nullable
         public Object getIfUnique() throws BeansException {
            try {
               return SimpleJndiBeanFactory.this.getBean(requiredType);
            } catch (NoSuchBeanDefinitionException var2) {
               return null;
            }
         }
      };
   }

   public ObjectProvider getBeanProvider(ResolvableType requiredType) {
      throw new UnsupportedOperationException("SimpleJndiBeanFactory does not support resolution by ResolvableType");
   }

   public boolean containsBean(String name) {
      if (!this.singletonObjects.containsKey(name) && !this.resourceTypes.containsKey(name)) {
         try {
            this.doGetType(name);
            return true;
         } catch (NamingException var3) {
            return false;
         }
      } else {
         return true;
      }
   }

   public boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
      return this.shareableResources.contains(name);
   }

   public boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
      return !this.shareableResources.contains(name);
   }

   public boolean isTypeMatch(String name, ResolvableType typeToMatch) throws NoSuchBeanDefinitionException {
      Class type = this.getType(name);
      return type != null && typeToMatch.isAssignableFrom(type);
   }

   public boolean isTypeMatch(String name, @Nullable Class typeToMatch) throws NoSuchBeanDefinitionException {
      Class type = this.getType(name);
      return typeToMatch == null || type != null && typeToMatch.isAssignableFrom(type);
   }

   @Nullable
   public Class getType(String name) throws NoSuchBeanDefinitionException {
      try {
         return this.doGetType(name);
      } catch (NameNotFoundException var3) {
         throw new NoSuchBeanDefinitionException(name, "not found in JNDI environment");
      } catch (NamingException var4) {
         return null;
      }
   }

   public String[] getAliases(String name) {
      return new String[0];
   }

   private Object doGetSingleton(String name, @Nullable Class requiredType) throws NamingException {
      synchronized(this.singletonObjects) {
         Object singleton = this.singletonObjects.get(name);
         if (singleton != null) {
            if (requiredType != null && !requiredType.isInstance(singleton)) {
               throw new TypeMismatchNamingException(this.convertJndiName(name), requiredType, singleton.getClass());
            } else {
               return singleton;
            }
         } else {
            Object jndiObject = this.lookup(name, requiredType);
            this.singletonObjects.put(name, jndiObject);
            return jndiObject;
         }
      }
   }

   private Class doGetType(String name) throws NamingException {
      if (this.isSingleton(name)) {
         return this.doGetSingleton(name, (Class)null).getClass();
      } else {
         synchronized(this.resourceTypes) {
            Class type = (Class)this.resourceTypes.get(name);
            if (type == null) {
               type = this.lookup(name, (Class)null).getClass();
               this.resourceTypes.put(name, type);
            }

            return type;
         }
      }
   }
}
