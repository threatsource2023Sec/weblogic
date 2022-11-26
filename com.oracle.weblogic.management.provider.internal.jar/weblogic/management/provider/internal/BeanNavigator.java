package weblogic.management.provider.internal;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class BeanNavigator {
   private final Helper helper;
   private final String[] navigationAttributeNames;
   private final Object[] originalBeans;
   private final Class tClass;

   BeanNavigator(String[] navigationAttributeNames, Object[] originalBeans, Helper helper, Class tClass) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
      this.ensureCompatibleArrays(navigationAttributeNames, originalBeans);
      this.navigationAttributeNames = navigationAttributeNames == null ? new String[0] : navigationAttributeNames;
      this.originalBeans = originalBeans;
      this.helper = helper;
      this.tClass = tClass;
   }

   private void ensureCompatibleArrays(String[] navigationAttributeNames, Object[] originalBeans) {
      if ((navigationAttributeNames != null || originalBeans != null) && (navigationAttributeNames == null || originalBeans == null || navigationAttributeNames.length != originalBeans.length)) {
         throw new IllegalArgumentException("Navigation attributes name list and bean list are incompatible; either they must both be null or they must have the same length");
      }
   }

   Object navigate(Object newStartingPoint) throws InvocationTargetException, IllegalAccessException, IntrospectionException {
      Object bean = newStartingPoint;

      label24:
      for(int i = 0; i < this.navigationAttributeNames.length; ++i) {
         BeanInfo bi = this.helper.getBeanInfo(bean);
         PropertyDescriptor[] var5 = bi.getPropertyDescriptors();
         int var6 = var5.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            PropertyDescriptor pd = var5[var7];
            if (pd.getName().equals(this.navigationAttributeNames[i])) {
               bean = this.navigateUsing(bean, pd, this.originalBeans[i]);
               continue label24;
            }
         }

         throw new IllegalArgumentException("Cannot navigate because property " + this.navigationAttributeNames[i] + " does not exist in BeanInfo for " + bean);
      }

      return bean;
   }

   private Object navigateUsing(Object bean, PropertyDescriptor pd, Object originalBean) throws InvocationTargetException, IllegalAccessException, IntrospectionException {
      if (pd.getPropertyType().isArray()) {
         Object[] children = (Object[])((Object[])pd.getReadMethod().invoke(bean));
         return this.findMatchingChild(pd.getPropertyType().getComponentType(), children, originalBean);
      } else {
         return this.tClass.cast(pd.getReadMethod().invoke(bean));
      }
   }

   private Object findMatchingChild(Class baseType, Object[] children, Object originalBean) throws InvocationTargetException, IllegalAccessException, IntrospectionException {
      String originalBeanName = this.getNameFromObject(originalBean);
      List childNames = new ArrayList();
      Object[] var6 = children;
      int var7 = children.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Object o = var6[var8];
         if (!baseType.isAssignableFrom(o.getClass())) {
            throw new IllegalArgumentException("Retrieved child beans of " + originalBean.toString() + " expected to be of type " + baseType.getName() + " but instead found a child of type " + originalBean.getClass().getName());
         }

         Object child = this.tClass.cast(o);
         BeanInfo childBI = this.helper.getBeanInfo(child);
         PropertyDescriptor childGetNamePD = this.findGetNamePropertyDescriptor(childBI);
         String childName = (String)childGetNamePD.getReadMethod().invoke(child);
         if (this.helper.childNameMatches(originalBeanName, childName)) {
            return child;
         }

         childNames.add(childName);
      }

      throw new IllegalArgumentException("Looking for child with name " + originalBeanName + " but found " + childNames);
   }

   private PropertyDescriptor findGetNamePropertyDescriptor(BeanInfo childBI) {
      PropertyDescriptor[] var2 = childBI.getPropertyDescriptors();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         PropertyDescriptor pd = var2[var4];
         if (pd.getName().equalsIgnoreCase("name") && pd.getPropertyType().isAssignableFrom(String.class)) {
            return pd;
         }
      }

      throw new IllegalArgumentException("Cannot find name attribute of type String in bean info for " + childBI.getBeanDescriptor().getName());
   }

   private String getNameFromObject(Object o) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
      BeanInfo bi = this.helper.getBeanInfo(o);
      PropertyDescriptor getNamePD = this.findGetNamePropertyDescriptor(bi);
      return (String)getNamePD.getReadMethod().invoke(o);
   }

   interface Helper {
      BeanInfo getBeanInfo(Object var1) throws IntrospectionException;

      boolean childNameMatches(String var1, String var2);
   }
}
