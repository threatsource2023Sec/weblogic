package com.oracle.weblogic.diagnostics.utils;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.IntrospectionException;
import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import weblogic.diagnostics.debug.DebugLogger;

public class BeanInfoCopy extends SimpleBeanInfo {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugExpressionExtensionsManager");
   private BeanInfo delegate;
   private BeanDescriptor beanDescriptor;
   private MethodDescriptor[] methods = new MethodDescriptor[0];
   private PropertyDescriptor[] properties = new PropertyDescriptor[0];

   public BeanInfoCopy(BeanInfo beanInfo) {
      this.delegate = beanInfo;
      BeanDescriptor originalDescriptor = beanInfo.getBeanDescriptor();
      this.beanDescriptor = new BeanDescriptor(originalDescriptor.getBeanClass(), originalDescriptor.getCustomizerClass());
      this.copyProperties();
      this.copyMethods();
   }

   public MethodDescriptor[] getMethodDescriptors() {
      return this.methods;
   }

   public BeanDescriptor getBeanDescriptor() {
      return this.beanDescriptor;
   }

   public PropertyDescriptor[] getPropertyDescriptors() {
      return this.properties;
   }

   public int getDefaultPropertyIndex() {
      return this.delegate.getDefaultPropertyIndex();
   }

   public EventSetDescriptor[] getEventSetDescriptors() {
      return this.delegate.getEventSetDescriptors();
   }

   public int getDefaultEventIndex() {
      return this.delegate.getDefaultEventIndex();
   }

   public BeanInfo[] getAdditionalBeanInfo() {
      return this.delegate.getAdditionalBeanInfo();
   }

   private void copyProperties() {
      PropertyDescriptor[] propertyDescriptors = this.delegate.getPropertyDescriptors();
      if (propertyDescriptors != null) {
         List propsCopy = new ArrayList();
         PropertyDescriptor[] var3 = propertyDescriptors;
         int var4 = propertyDescriptors.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            PropertyDescriptor pd = var3[var5];

            try {
               PropertyDescriptor pdCopy = new PropertyDescriptor(pd.getName(), pd.getReadMethod(), pd.getWriteMethod());
               propsCopy.add(pdCopy);
            } catch (IntrospectionException var8) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Error copying bean property " + pd.getName() + " for bean " + this.beanDescriptor.getName(), var8);
               }
            }
         }

         this.properties = (PropertyDescriptor[])propsCopy.toArray(new PropertyDescriptor[propsCopy.size()]);
      }

   }

   private void copyMethods() {
      MethodDescriptor[] methodDescriptors = this.delegate.getMethodDescriptors();
      if (methodDescriptors != null) {
         List mdList = new ArrayList();
         MethodDescriptor[] var3 = methodDescriptors;
         int var4 = methodDescriptors.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            MethodDescriptor md = var3[var5];
            ParameterDescriptor[] parameterDescriptors = md.getParameterDescriptors();
            if (parameterDescriptors == null || parameterDescriptors.length == 0) {
               Method method = md.getMethod();
               List pdList = this.createParameterList(method.getParameterTypes());
               parameterDescriptors = (ParameterDescriptor[])pdList.toArray(new ParameterDescriptor[pdList.size()]);
            }

            MethodDescriptor mdCopy = new MethodDescriptor(md.getMethod(), parameterDescriptors);
            mdList.add(mdCopy);
         }

         this.methods = (MethodDescriptor[])mdList.toArray(new MethodDescriptor[mdList.size()]);
      }
   }

   private List createParameterList(Class[] parameterTypes) {
      List pdList = new ArrayList();

      for(int i = 0; i < parameterTypes.length; ++i) {
         ParameterDescriptor pd = new ParameterDescriptor();
         pd.setName("arg" + i);
         pdList.add(pd);
      }

      return pdList;
   }
}
