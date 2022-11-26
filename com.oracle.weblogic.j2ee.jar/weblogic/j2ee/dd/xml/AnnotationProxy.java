package weblogic.j2ee.dd.xml;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import weblogic.j2ee.ApplicationUtilitiesJ2EETextTextFormatter;
import weblogic.j2ee.descriptor.JavaEEPropertyBean;

public class AnnotationProxy implements InvocationHandler {
   protected static final ApplicationUtilitiesJ2EETextTextFormatter logger = ApplicationUtilitiesJ2EETextTextFormatter.getInstance();
   private Map values = new HashMap();
   private String[] attributeNames;
   private boolean[] isNotFromDescriptor;
   private Map descriptorProperties;
   private Map annotationProperties;

   public Object newInstance(Class interfaceClass) {
      return Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, this);
   }

   protected String[] getAttributeNames() {
      return this.attributeNames;
   }

   protected AnnotationProxy(boolean isNotInDescriptor, JavaEEPropertyBean[] beanProperties, String[] attributeNames, Object[] beanValues, String[] annoProperties, Object[] annoAttributes) {
      this.attributeNames = attributeNames;
      this.isNotFromDescriptor = new boolean[attributeNames.length];
      int var8;
      int var9;
      if (annoProperties != null && annoProperties.length > 0) {
         this.annotationProperties = new HashMap();
         String[] var7 = annoProperties;
         var8 = annoProperties.length;

         for(var9 = 0; var9 < var8; ++var9) {
            String property = var7[var9];
            int splitLoc = property.indexOf(61);
            String name = property.substring(0, splitLoc);
            String value = property.substring(splitLoc + 1);
            this.annotationProperties.put(name, value);
         }
      }

      for(int i = 0; i < attributeNames.length; ++i) {
         this.isNotFromDescriptor[i] = isNotInDescriptor || beanValues[i] == null;
         this.values.put(attributeNames[i], annoAttributes[i]);
      }

      if (beanProperties != null && beanProperties.length > 0) {
         this.descriptorProperties = new HashMap();
         JavaEEPropertyBean[] var15 = beanProperties;
         var8 = beanProperties.length;

         for(var9 = 0; var9 < var8; ++var9) {
            JavaEEPropertyBean prop = var15[var9];
            this.descriptorProperties.put(prop.getName(), prop);
         }
      }

   }

   protected void merge(Set knownPropertyNames, Object annotation, String[] properties, Object... attributes) throws DuplicateAnnotationException {
      try {
         for(int i = 0; i < this.attributeNames.length; ++i) {
            if (this.isNotFromDescriptor[i]) {
               this.verifyAndMerge(this.attributeNames[i], attributes[i]);
            }
         }

         if (properties != null && properties.length > 0) {
            String[] var13 = properties;
            int var6 = properties.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               String property = var13[var7];
               int splitLoc = property.indexOf(61);
               String name = property.substring(0, splitLoc);
               String value = property.substring(splitLoc + 1);
               if (this.descriptorProperties == null || !this.descriptorProperties.containsKey(name)) {
                  this.verifyAndMergeProperty(knownPropertyNames, name, value);
               }
            }
         }

      } catch (DuplicateAnnotationException var12) {
         throw new DuplicateAnnotationException(logger.incompatibleAnnotations(var12.getMessage(), annotation.toString()));
      }
   }

   private void verifyAndMergeProperty(Set knownPropertyNames, String name, String value) throws DuplicateAnnotationException {
      if (this.annotationProperties == null) {
         this.annotationProperties = new HashMap();
         this.annotationProperties.put(name, value);
      } else {
         String existingValue = (String)this.annotationProperties.get(name);
         if (existingValue == null) {
            this.annotationProperties.put(name, value);
         } else if (!existingValue.equals(value) && (knownPropertyNames == null || knownPropertyNames.contains(name))) {
            throw new DuplicateAnnotationException(logger.incompatibleAnnotationProperties(name, value, existingValue));
         }
      }

   }

   private void verifyAndMerge(String name, Object newValue) throws DuplicateAnnotationException {
      Object value = this.values.get(name);
      if (!this.isCompatible(value, newValue)) {
         throw new DuplicateAnnotationException(logger.incompatibleAnnotationAttributes(name, newValue.toString(), value.toString()));
      } else {
         if (newValue != null) {
            this.values.put(name, newValue);
         }

      }
   }

   private boolean isCompatible(Object baseValue, Object newValue) {
      if (baseValue == null) {
         return newValue == null;
      } else {
         return baseValue.getClass().isArray() ? this.isCompatibleArrays((Object[])((Object[])baseValue), (Object[])((Object[])newValue)) : baseValue.equals(newValue);
      }
   }

   private boolean isCompatibleArrays(Object[] baseValues, Object[] newValues) {
      return Arrays.equals(baseValues, newValues);
   }

   public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
      throw new UnsupportedOperationException("This is not needed yet, but maybe needed so that we can merge together all the annotations of a given name prior to merging them in the bean tree. Method:" + m);
   }
}
