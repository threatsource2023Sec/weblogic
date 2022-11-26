package com.sun.faces.facelets.tag.composite;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.FacesException;

public class CompositeComponentBeanInfo extends SimpleBeanInfo implements BeanInfo, Externalizable {
   private BeanDescriptor descriptor = null;
   private List propertyDescriptors;

   public BeanDescriptor getBeanDescriptor() {
      return this.descriptor;
   }

   public void setBeanDescriptor(BeanDescriptor newDescriptor) {
      this.descriptor = newDescriptor;
   }

   public PropertyDescriptor[] getPropertyDescriptors() {
      List list = this.getPropertyDescriptorsList();
      PropertyDescriptor[] result = new PropertyDescriptor[list.size()];
      list.toArray(result);
      return result;
   }

   public List getPropertyDescriptorsList() {
      if (null == this.propertyDescriptors) {
         this.propertyDescriptors = new ArrayList();
      }

      return this.propertyDescriptors;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this.descriptor.getBeanClass());
      if (this.propertyDescriptors != null && this.propertyDescriptors.size() > 0) {
         out.writeObject(this.propertyDescriptors.size());
         Iterator var2 = this.propertyDescriptors.iterator();

         while(var2.hasNext()) {
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor)var2.next();
            out.writeObject(propertyDescriptor.getName());
            out.writeObject(propertyDescriptor.getValue("type"));
         }
      } else {
         out.writeObject(0);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.descriptor = new BeanDescriptor((Class)in.readObject());
      int size = (Integer)in.readObject();

      for(int i = 0; i < size; ++i) {
         try {
            String name = (String)in.readObject();
            CompositeAttributePropertyDescriptor pd = new CompositeAttributePropertyDescriptor(name, (Method)null, (Method)null);
            Object type = in.readObject();
            if (type != null) {
               pd.setValue("type", type);
            }

            this.getPropertyDescriptorsList().add(pd);
         } catch (IntrospectionException var7) {
            throw new FacesException(var7);
         }
      }

   }
}
