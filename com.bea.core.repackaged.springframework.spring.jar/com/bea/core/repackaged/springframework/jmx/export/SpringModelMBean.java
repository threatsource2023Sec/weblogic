package com.bea.core.repackaged.springframework.jmx.export;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;
import javax.management.modelmbean.InvalidTargetObjectTypeException;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.RequiredModelMBean;

public class SpringModelMBean extends RequiredModelMBean {
   private ClassLoader managedResourceClassLoader = Thread.currentThread().getContextClassLoader();

   public SpringModelMBean() throws MBeanException, RuntimeOperationsException {
   }

   public SpringModelMBean(ModelMBeanInfo mbi) throws MBeanException, RuntimeOperationsException {
      super(mbi);
   }

   public void setManagedResource(Object managedResource, String managedResourceType) throws MBeanException, InstanceNotFoundException, InvalidTargetObjectTypeException {
      this.managedResourceClassLoader = managedResource.getClass().getClassLoader();
      super.setManagedResource(managedResource, managedResourceType);
   }

   public Object invoke(String opName, Object[] opArgs, String[] sig) throws MBeanException, ReflectionException {
      ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();

      Object var5;
      try {
         Thread.currentThread().setContextClassLoader(this.managedResourceClassLoader);
         var5 = super.invoke(opName, opArgs, sig);
      } finally {
         Thread.currentThread().setContextClassLoader(currentClassLoader);
      }

      return var5;
   }

   public Object getAttribute(String attrName) throws AttributeNotFoundException, MBeanException, ReflectionException {
      ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();

      Object var3;
      try {
         Thread.currentThread().setContextClassLoader(this.managedResourceClassLoader);
         var3 = super.getAttribute(attrName);
      } finally {
         Thread.currentThread().setContextClassLoader(currentClassLoader);
      }

      return var3;
   }

   public AttributeList getAttributes(String[] attrNames) {
      ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();

      AttributeList var3;
      try {
         Thread.currentThread().setContextClassLoader(this.managedResourceClassLoader);
         var3 = super.getAttributes(attrNames);
      } finally {
         Thread.currentThread().setContextClassLoader(currentClassLoader);
      }

      return var3;
   }

   public void setAttribute(Attribute attribute) throws AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException {
      ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();

      try {
         Thread.currentThread().setContextClassLoader(this.managedResourceClassLoader);
         super.setAttribute(attribute);
      } finally {
         Thread.currentThread().setContextClassLoader(currentClassLoader);
      }

   }

   public AttributeList setAttributes(AttributeList attributes) {
      ClassLoader currentClassLoader = Thread.currentThread().getContextClassLoader();

      AttributeList var3;
      try {
         Thread.currentThread().setContextClassLoader(this.managedResourceClassLoader);
         var3 = super.setAttributes(attributes);
      } finally {
         Thread.currentThread().setContextClassLoader(currentClassLoader);
      }

      return var3;
   }
}
