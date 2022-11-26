package com.bea.core.repackaged.springframework.beans.factory.groovy;

import com.bea.core.repackaged.springframework.beans.MutablePropertyValues;
import com.bea.core.repackaged.springframework.beans.factory.BeanDefinitionStoreException;
import com.bea.core.repackaged.springframework.beans.factory.config.RuntimeBeanReference;
import com.bea.core.repackaged.springframework.beans.factory.parsing.BeanDefinitionParsingException;
import com.bea.core.repackaged.springframework.beans.factory.parsing.Location;
import com.bea.core.repackaged.springframework.beans.factory.parsing.ParseState;
import com.bea.core.repackaged.springframework.beans.factory.parsing.Problem;
import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.AbstractBeanDefinitionReader;
import com.bea.core.repackaged.springframework.beans.factory.support.BeanDefinitionRegistry;
import com.bea.core.repackaged.springframework.beans.factory.support.GenericBeanDefinition;
import com.bea.core.repackaged.springframework.beans.factory.support.ManagedList;
import com.bea.core.repackaged.springframework.beans.factory.support.ManagedMap;
import com.bea.core.repackaged.springframework.beans.factory.xml.BeanDefinitionParserDelegate;
import com.bea.core.repackaged.springframework.beans.factory.xml.NamespaceHandler;
import com.bea.core.repackaged.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import com.bea.core.repackaged.springframework.beans.factory.xml.XmlReaderContext;
import com.bea.core.repackaged.springframework.core.io.DescriptiveResource;
import com.bea.core.repackaged.springframework.core.io.Resource;
import com.bea.core.repackaged.springframework.core.io.support.EncodedResource;
import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import groovy.lang.Binding;
import groovy.lang.Closure;
import groovy.lang.GString;
import groovy.lang.GroovyObject;
import groovy.lang.GroovyObjectSupport;
import groovy.lang.GroovyShell;
import groovy.lang.GroovySystem;
import groovy.lang.MetaClass;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.codehaus.groovy.runtime.InvokerHelper;

public class GroovyBeanDefinitionReader extends AbstractBeanDefinitionReader implements GroovyObject {
   private final XmlBeanDefinitionReader standardXmlBeanDefinitionReader;
   private final XmlBeanDefinitionReader groovyDslXmlBeanDefinitionReader;
   private final Map namespaces = new HashMap();
   private final Map deferredProperties = new HashMap();
   private MetaClass metaClass = GroovySystem.getMetaClassRegistry().getMetaClass(this.getClass());
   private Binding binding;
   private GroovyBeanDefinitionWrapper currentBeanDefinition;

   public GroovyBeanDefinitionReader(BeanDefinitionRegistry registry) {
      super(registry);
      this.standardXmlBeanDefinitionReader = new XmlBeanDefinitionReader(registry);
      this.groovyDslXmlBeanDefinitionReader = new XmlBeanDefinitionReader(registry);
      this.groovyDslXmlBeanDefinitionReader.setValidating(false);
   }

   public GroovyBeanDefinitionReader(XmlBeanDefinitionReader xmlBeanDefinitionReader) {
      super(xmlBeanDefinitionReader.getRegistry());
      this.standardXmlBeanDefinitionReader = new XmlBeanDefinitionReader(xmlBeanDefinitionReader.getRegistry());
      this.groovyDslXmlBeanDefinitionReader = xmlBeanDefinitionReader;
   }

   public void setMetaClass(MetaClass metaClass) {
      this.metaClass = metaClass;
   }

   public MetaClass getMetaClass() {
      return this.metaClass;
   }

   public void setBinding(Binding binding) {
      this.binding = binding;
   }

   public Binding getBinding() {
      return this.binding;
   }

   public int loadBeanDefinitions(Resource resource) throws BeanDefinitionStoreException {
      return this.loadBeanDefinitions(new EncodedResource(resource));
   }

   public int loadBeanDefinitions(EncodedResource encodedResource) throws BeanDefinitionStoreException {
      String filename = encodedResource.getResource().getFilename();
      if (StringUtils.endsWithIgnoreCase(filename, ".xml")) {
         return this.standardXmlBeanDefinitionReader.loadBeanDefinitions(encodedResource);
      } else {
         if (this.logger.isTraceEnabled()) {
            this.logger.trace("Loading Groovy bean definitions from " + encodedResource);
         }

         Closure beans = new Closure(this) {
            public Object call(Object... args) {
               GroovyBeanDefinitionReader.this.invokeBeanDefiningClosure((Closure)args[0]);
               return null;
            }
         };
         Binding binding = new Binding() {
            public void setVariable(String name, Object value) {
               if (GroovyBeanDefinitionReader.this.currentBeanDefinition != null) {
                  GroovyBeanDefinitionReader.this.applyPropertyToBeanDefinition(name, value);
               } else {
                  super.setVariable(name, value);
               }

            }
         };
         binding.setVariable("beans", beans);
         int countBefore = this.getRegistry().getBeanDefinitionCount();

         try {
            GroovyShell shell = new GroovyShell(this.getBeanClassLoader(), binding);
            shell.evaluate(encodedResource.getReader(), "beans");
         } catch (Throwable var7) {
            throw new BeanDefinitionParsingException(new Problem("Error evaluating Groovy script: " + var7.getMessage(), new Location(encodedResource.getResource()), (ParseState)null, var7));
         }

         int count = this.getRegistry().getBeanDefinitionCount() - countBefore;
         if (this.logger.isDebugEnabled()) {
            this.logger.debug("Loaded " + count + " bean definitions from " + encodedResource);
         }

         return count;
      }
   }

   public GroovyBeanDefinitionReader beans(Closure closure) {
      return this.invokeBeanDefiningClosure(closure);
   }

   public GenericBeanDefinition bean(Class type) {
      GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
      beanDefinition.setBeanClass(type);
      return beanDefinition;
   }

   public AbstractBeanDefinition bean(Class type, Object... args) {
      GroovyBeanDefinitionWrapper current = this.currentBeanDefinition;

      AbstractBeanDefinition var11;
      try {
         Closure callable = null;
         Collection constructorArgs = null;
         if (!ObjectUtils.isEmpty(args)) {
            int index = args.length;
            Object lastArg = args[index - 1];
            if (lastArg instanceof Closure) {
               callable = (Closure)lastArg;
               --index;
            }

            constructorArgs = this.resolveConstructorArguments(args, 0, index);
         }

         this.currentBeanDefinition = new GroovyBeanDefinitionWrapper((String)null, type, constructorArgs);
         if (callable != null) {
            callable.call(this.currentBeanDefinition);
         }

         var11 = this.currentBeanDefinition.getBeanDefinition();
      } finally {
         this.currentBeanDefinition = current;
      }

      return var11;
   }

   public void xmlns(Map definition) {
      if (!definition.isEmpty()) {
         Iterator var2 = definition.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            String namespace = (String)entry.getKey();
            String uri = (String)entry.getValue();
            if (uri == null) {
               throw new IllegalArgumentException("Namespace definition must supply a non-null URI");
            }

            NamespaceHandler namespaceHandler = this.groovyDslXmlBeanDefinitionReader.getNamespaceHandlerResolver().resolve(uri);
            if (namespaceHandler == null) {
               throw new BeanDefinitionParsingException(new Problem("No namespace handler found for URI: " + uri, new Location(new DescriptiveResource("Groovy"))));
            }

            this.namespaces.put(namespace, uri);
         }
      }

   }

   public void importBeans(String resourcePattern) throws IOException {
      this.loadBeanDefinitions((String)resourcePattern);
   }

   public Object invokeMethod(String name, Object arg) {
      Object[] args = (Object[])((Object[])arg);
      if ("beans".equals(name) && args.length == 1 && args[0] instanceof Closure) {
         return this.beans((Closure)args[0]);
      } else if ("ref".equals(name)) {
         if (args[0] == null) {
            throw new IllegalArgumentException("Argument to ref() is not a valid bean or was not found");
         } else {
            String refName;
            if (args[0] instanceof RuntimeBeanReference) {
               refName = ((RuntimeBeanReference)args[0]).getBeanName();
            } else {
               refName = args[0].toString();
            }

            boolean parentRef = false;
            if (args.length > 1 && args[1] instanceof Boolean) {
               parentRef = (Boolean)args[1];
            }

            return new RuntimeBeanReference(refName, parentRef);
         }
      } else {
         if (this.namespaces.containsKey(name) && args.length > 0 && args[0] instanceof Closure) {
            GroovyDynamicElementReader reader = this.createDynamicElementReader(name);
            reader.invokeMethod("doCall", args);
         } else {
            if (args.length > 0 && args[0] instanceof Closure) {
               return this.invokeBeanDefiningMethod(name, args);
            }

            if (args.length > 0 && (args[0] instanceof Class || args[0] instanceof RuntimeBeanReference || args[0] instanceof Map)) {
               return this.invokeBeanDefiningMethod(name, args);
            }

            if (args.length > 1 && args[args.length - 1] instanceof Closure) {
               return this.invokeBeanDefiningMethod(name, args);
            }
         }

         MetaClass mc = DefaultGroovyMethods.getMetaClass(this.getRegistry());
         if (!mc.respondsTo(this.getRegistry(), name, args).isEmpty()) {
            return mc.invokeMethod(this.getRegistry(), name, args);
         } else {
            return this;
         }
      }
   }

   private boolean addDeferredProperty(String property, Object newValue) {
      if (!(newValue instanceof List) && !(newValue instanceof Map)) {
         return false;
      } else {
         this.deferredProperties.put(this.currentBeanDefinition.getBeanName() + '.' + property, new DeferredProperty(this.currentBeanDefinition, property, newValue));
         return true;
      }
   }

   private void finalizeDeferredProperties() {
      DeferredProperty dp;
      for(Iterator var1 = this.deferredProperties.values().iterator(); var1.hasNext(); dp.apply()) {
         dp = (DeferredProperty)var1.next();
         if (dp.value instanceof List) {
            dp.value = this.manageListIfNecessary((List)dp.value);
         } else if (dp.value instanceof Map) {
            dp.value = this.manageMapIfNecessary((Map)dp.value);
         }
      }

      this.deferredProperties.clear();
   }

   protected GroovyBeanDefinitionReader invokeBeanDefiningClosure(Closure callable) {
      callable.setDelegate(this);
      callable.call();
      this.finalizeDeferredProperties();
      return this;
   }

   private GroovyBeanDefinitionWrapper invokeBeanDefiningMethod(String beanName, Object[] args) {
      boolean hasClosureArgument = args[args.length - 1] instanceof Closure;
      if (args[0] instanceof Class) {
         Class beanClass = (Class)args[0];
         if (hasClosureArgument) {
            if (args.length - 1 != 1) {
               this.currentBeanDefinition = new GroovyBeanDefinitionWrapper(beanName, beanClass, this.resolveConstructorArguments(args, 1, args.length - 1));
            } else {
               this.currentBeanDefinition = new GroovyBeanDefinitionWrapper(beanName, beanClass);
            }
         } else {
            this.currentBeanDefinition = new GroovyBeanDefinitionWrapper(beanName, beanClass, this.resolveConstructorArguments(args, 1, args.length));
         }
      } else if (args[0] instanceof RuntimeBeanReference) {
         this.currentBeanDefinition = new GroovyBeanDefinitionWrapper(beanName);
         this.currentBeanDefinition.getBeanDefinition().setFactoryBeanName(((RuntimeBeanReference)args[0]).getBeanName());
      } else {
         List constructorArgs;
         if (args[0] instanceof Map) {
            if (args.length > 1 && args[1] instanceof Class) {
               constructorArgs = this.resolveConstructorArguments(args, 2, hasClosureArgument ? args.length - 1 : args.length);
               this.currentBeanDefinition = new GroovyBeanDefinitionWrapper(beanName, (Class)args[1], constructorArgs);
               Map namedArgs = (Map)args[0];
               Iterator var14 = namedArgs.keySet().iterator();

               while(var14.hasNext()) {
                  Object o = var14.next();
                  String propName = (String)o;
                  this.setProperty(propName, namedArgs.get(propName));
               }
            } else {
               this.currentBeanDefinition = new GroovyBeanDefinitionWrapper(beanName);
               Map.Entry factoryBeanEntry = (Map.Entry)((Map)args[0]).entrySet().iterator().next();
               int constructorArgsTest = hasClosureArgument ? 2 : 1;
               if (args.length > constructorArgsTest) {
                  int endOfConstructArgs = hasClosureArgument ? args.length - 1 : args.length;
                  this.currentBeanDefinition = new GroovyBeanDefinitionWrapper(beanName, (Class)null, this.resolveConstructorArguments(args, 1, endOfConstructArgs));
               } else {
                  this.currentBeanDefinition = new GroovyBeanDefinitionWrapper(beanName);
               }

               this.currentBeanDefinition.getBeanDefinition().setFactoryBeanName(factoryBeanEntry.getKey().toString());
               this.currentBeanDefinition.getBeanDefinition().setFactoryMethodName(factoryBeanEntry.getValue().toString());
            }
         } else if (args[0] instanceof Closure) {
            this.currentBeanDefinition = new GroovyBeanDefinitionWrapper(beanName);
            this.currentBeanDefinition.getBeanDefinition().setAbstract(true);
         } else {
            constructorArgs = this.resolveConstructorArguments(args, 0, hasClosureArgument ? args.length - 1 : args.length);
            this.currentBeanDefinition = new GroovyBeanDefinitionWrapper(beanName, (Class)null, constructorArgs);
         }
      }

      if (hasClosureArgument) {
         Closure callable = (Closure)args[args.length - 1];
         callable.setDelegate(this);
         callable.setResolveStrategy(1);
         callable.call(this.currentBeanDefinition);
      }

      GroovyBeanDefinitionWrapper beanDefinition = this.currentBeanDefinition;
      this.currentBeanDefinition = null;
      beanDefinition.getBeanDefinition().setAttribute(GroovyBeanDefinitionWrapper.class.getName(), beanDefinition);
      this.getRegistry().registerBeanDefinition(beanName, beanDefinition.getBeanDefinition());
      return beanDefinition;
   }

   protected List resolveConstructorArguments(Object[] args, int start, int end) {
      Object[] constructorArgs = Arrays.copyOfRange(args, start, end);

      for(int i = 0; i < constructorArgs.length; ++i) {
         if (constructorArgs[i] instanceof GString) {
            constructorArgs[i] = constructorArgs[i].toString();
         } else if (constructorArgs[i] instanceof List) {
            constructorArgs[i] = this.manageListIfNecessary((List)constructorArgs[i]);
         } else if (constructorArgs[i] instanceof Map) {
            constructorArgs[i] = this.manageMapIfNecessary((Map)constructorArgs[i]);
         }
      }

      return Arrays.asList(constructorArgs);
   }

   private Object manageMapIfNecessary(Map map) {
      boolean containsRuntimeRefs = false;
      Iterator var3 = map.values().iterator();

      while(var3.hasNext()) {
         Object element = var3.next();
         if (element instanceof RuntimeBeanReference) {
            containsRuntimeRefs = true;
            break;
         }
      }

      if (containsRuntimeRefs) {
         Map managedMap = new ManagedMap();
         managedMap.putAll(map);
         return managedMap;
      } else {
         return map;
      }
   }

   private Object manageListIfNecessary(List list) {
      boolean containsRuntimeRefs = false;
      Iterator var3 = list.iterator();

      while(var3.hasNext()) {
         Object element = var3.next();
         if (element instanceof RuntimeBeanReference) {
            containsRuntimeRefs = true;
            break;
         }
      }

      if (containsRuntimeRefs) {
         List managedList = new ManagedList();
         managedList.addAll(list);
         return managedList;
      } else {
         return list;
      }
   }

   public void setProperty(String name, Object value) {
      if (this.currentBeanDefinition != null) {
         this.applyPropertyToBeanDefinition(name, value);
      }

   }

   protected void applyPropertyToBeanDefinition(String name, Object value) {
      if (value instanceof GString) {
         value = value.toString();
      }

      if (!this.addDeferredProperty(name, value)) {
         if (value instanceof Closure) {
            GroovyBeanDefinitionWrapper current = this.currentBeanDefinition;

            try {
               Closure callable = (Closure)value;
               Class parameterType = callable.getParameterTypes()[0];
               if (Object.class == parameterType) {
                  this.currentBeanDefinition = new GroovyBeanDefinitionWrapper("");
                  callable.call(this.currentBeanDefinition);
               } else {
                  this.currentBeanDefinition = new GroovyBeanDefinitionWrapper((String)null, parameterType);
                  callable.call((Object)null);
               }

               value = this.currentBeanDefinition.getBeanDefinition();
            } finally {
               this.currentBeanDefinition = current;
            }
         }

         this.currentBeanDefinition.addProperty(name, value);
      }
   }

   public Object getProperty(String name) {
      Binding binding = this.getBinding();
      if (binding != null && binding.hasVariable(name)) {
         return binding.getVariable(name);
      } else if (this.namespaces.containsKey(name)) {
         return this.createDynamicElementReader(name);
      } else if (this.getRegistry().containsBeanDefinition(name)) {
         GroovyBeanDefinitionWrapper beanDefinition = (GroovyBeanDefinitionWrapper)this.getRegistry().getBeanDefinition(name).getAttribute(GroovyBeanDefinitionWrapper.class.getName());
         return beanDefinition != null ? new GroovyRuntimeBeanReference(name, beanDefinition, false) : new RuntimeBeanReference(name, false);
      } else if (this.currentBeanDefinition != null) {
         MutablePropertyValues pvs = this.currentBeanDefinition.getBeanDefinition().getPropertyValues();
         if (pvs.contains(name)) {
            return pvs.get(name);
         } else {
            DeferredProperty dp = (DeferredProperty)this.deferredProperties.get(this.currentBeanDefinition.getBeanName() + name);
            return dp != null ? dp.value : this.getMetaClass().getProperty(this, name);
         }
      } else {
         return this.getMetaClass().getProperty(this, name);
      }
   }

   private GroovyDynamicElementReader createDynamicElementReader(String namespace) {
      XmlReaderContext readerContext = this.groovyDslXmlBeanDefinitionReader.createReaderContext(new DescriptiveResource("Groovy"));
      BeanDefinitionParserDelegate delegate = new BeanDefinitionParserDelegate(readerContext);
      boolean decorating = this.currentBeanDefinition != null;
      if (!decorating) {
         this.currentBeanDefinition = new GroovyBeanDefinitionWrapper(namespace);
      }

      return new GroovyDynamicElementReader(namespace, this.namespaces, delegate, this.currentBeanDefinition, decorating) {
         protected void afterInvocation() {
            if (!this.decorating) {
               GroovyBeanDefinitionReader.this.currentBeanDefinition = null;
            }

         }
      };
   }

   private class GroovyRuntimeBeanReference extends RuntimeBeanReference implements GroovyObject {
      private final GroovyBeanDefinitionWrapper beanDefinition;
      private MetaClass metaClass;

      public GroovyRuntimeBeanReference(String beanName, GroovyBeanDefinitionWrapper beanDefinition, boolean toParent) {
         super(beanName, toParent);
         this.beanDefinition = beanDefinition;
         this.metaClass = InvokerHelper.getMetaClass(this);
      }

      public MetaClass getMetaClass() {
         return this.metaClass;
      }

      public Object getProperty(String property) {
         if (property.equals("beanName")) {
            return this.getBeanName();
         } else if (property.equals("source")) {
            return this.getSource();
         } else {
            return this.beanDefinition != null ? new GroovyPropertyValue(property, this.beanDefinition.getBeanDefinition().getPropertyValues().get(property)) : this.metaClass.getProperty(this, property);
         }
      }

      public Object invokeMethod(String name, Object args) {
         return this.metaClass.invokeMethod(this, name, args);
      }

      public void setMetaClass(MetaClass metaClass) {
         this.metaClass = metaClass;
      }

      public void setProperty(String property, Object newValue) {
         if (!GroovyBeanDefinitionReader.this.addDeferredProperty(property, newValue)) {
            this.beanDefinition.getBeanDefinition().getPropertyValues().add(property, newValue);
         }

      }

      private class GroovyPropertyValue extends GroovyObjectSupport {
         private final String propertyName;
         private final Object propertyValue;

         public GroovyPropertyValue(String propertyName, Object propertyValue) {
            this.propertyName = propertyName;
            this.propertyValue = propertyValue;
         }

         public void leftShift(Object value) {
            InvokerHelper.invokeMethod(this.propertyValue, "leftShift", value);
            this.updateDeferredProperties(value);
         }

         public boolean add(Object value) {
            boolean retVal = (Boolean)InvokerHelper.invokeMethod(this.propertyValue, "add", value);
            this.updateDeferredProperties(value);
            return retVal;
         }

         public boolean addAll(Collection values) {
            boolean retVal = (Boolean)InvokerHelper.invokeMethod(this.propertyValue, "addAll", values);
            Iterator var3 = values.iterator();

            while(var3.hasNext()) {
               Object value = var3.next();
               this.updateDeferredProperties(value);
            }

            return retVal;
         }

         public Object invokeMethod(String name, Object args) {
            return InvokerHelper.invokeMethod(this.propertyValue, name, args);
         }

         public Object getProperty(String name) {
            return InvokerHelper.getProperty(this.propertyValue, name);
         }

         public void setProperty(String name, Object value) {
            InvokerHelper.setProperty(this.propertyValue, name, value);
         }

         private void updateDeferredProperties(Object value) {
            if (value instanceof RuntimeBeanReference) {
               GroovyBeanDefinitionReader.this.deferredProperties.put(GroovyRuntimeBeanReference.this.beanDefinition.getBeanName(), new DeferredProperty(GroovyRuntimeBeanReference.this.beanDefinition, this.propertyName, this.propertyValue));
            }

         }
      }
   }

   private static class DeferredProperty {
      private final GroovyBeanDefinitionWrapper beanDefinition;
      private final String name;
      public Object value;

      public DeferredProperty(GroovyBeanDefinitionWrapper beanDefinition, String name, Object value) {
         this.beanDefinition = beanDefinition;
         this.name = name;
         this.value = value;
      }

      public void apply() {
         this.beanDefinition.addProperty(this.name, this.value);
      }
   }
}
