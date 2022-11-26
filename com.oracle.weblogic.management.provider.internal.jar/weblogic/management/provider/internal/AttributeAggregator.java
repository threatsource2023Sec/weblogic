package weblogic.management.provider.internal;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.utils.ArrayUtils;

public class AttributeAggregator {
   Class subClass;
   List getterMethods;
   List attributeNames;

   public AttributeAggregator(String interfaceClassName, String attribute, String attributeValue) {
      BeanInfoAccess beanInfoAccess = ManagementService.getBeanInfoAccess();
      this.attributeNames = new ArrayList(8);
      BeanInfo beanInfo = beanInfoAccess.getBeanInfoForInterface(interfaceClassName, true, (String)null);
      PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

      for(int i = 0; i < propertyDescriptors.length; ++i) {
         PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
         String relationship = (String)propertyDescriptor.getValue(attribute);
         if (relationship != null && relationship.equals(attributeValue)) {
            this.attributeNames.add(propertyDescriptor.getName());
         }
      }

   }

   public AttributeAggregator(String interfaceClassName, Class subClass) {
      BeanInfoAccess beanInfoAccess = ManagementService.getBeanInfoAccess();
      this.subClass = subClass;
      this.getterMethods = new ArrayList(8);
      BeanInfo beanInfo = beanInfoAccess.getBeanInfoForInterface(interfaceClassName, true, (String)null);
      PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

      for(int i = 0; i < propertyDescriptors.length; ++i) {
         PropertyDescriptor propertyDescriptor = propertyDescriptors[i];
         Class propertyType = propertyDescriptor.getPropertyType();
         if (propertyType.isArray()) {
            Class componentType = propertyType.getComponentType();
            if (!subClass.equals(componentType) && subClass.isAssignableFrom(componentType)) {
               this.getterMethods.add(propertyDescriptor.getReadMethod());
            }
         }
      }

   }

   public AttributeAggregator(Class interfaceClass, Class subClass, String getterName) {
      this.subClass = subClass;
      this.getterMethods = new ArrayList(8);
      Method[] allMethods = interfaceClass.getMethods();

      for(int i = 0; i < allMethods.length; ++i) {
         Method aMethod = allMethods[i];
         if (aMethod.getName().startsWith("get") && !aMethod.getName().equals(getterName) && (aMethod.getParameterTypes() == null || aMethod.getParameterTypes().length == 0)) {
            Class returnType = aMethod.getReturnType();
            if (returnType.isArray() && subClass.isAssignableFrom(returnType.getComponentType())) {
               this.getterMethods.add(aMethod);
            }
         }
      }

   }

   public List returnReferencedAttributes() {
      return this.attributeNames;
   }

   public Object[] getAll(Object instance) {
      List result = new ArrayList(32);

      for(int i = 0; i < this.getterMethods.size(); ++i) {
         Method method = (Method)this.getterMethods.get(i);

         try {
            Object[] subset = (Object[])((Object[])method.invoke(instance));
            ArrayUtils.addAll(result, subset);
         } catch (IllegalAccessException var6) {
            throw new Error(var6);
         } catch (InvocationTargetException var7) {
            throw new Error(var7);
         }
      }

      Object[] resultArray = (Object[])((Object[])Array.newInstance(this.subClass, result.size()));
      return result.toArray(resultArray);
   }

   public Object lookup(Object instance, String name) {
      for(int i = 0; i < this.getterMethods.size(); ++i) {
         Method method = (Method)this.getterMethods.get(i);

         try {
            Object[] subset = (Object[])((Object[])method.invoke(instance));

            for(int j = 0; j < subset.length; ++j) {
               Object o = subset[j];

               try {
                  Method getNameMethod = o.getClass().getMethod("getName");
                  String beanName = (String)getNameMethod.invoke(o);
                  if (beanName.equals(name)) {
                     return o;
                  }
               } catch (NoSuchMethodException var10) {
               }
            }
         } catch (IllegalAccessException var11) {
            throw new Error(var11);
         } catch (InvocationTargetException var12) {
            throw new Error(var12);
         }
      }

      return null;
   }
}
