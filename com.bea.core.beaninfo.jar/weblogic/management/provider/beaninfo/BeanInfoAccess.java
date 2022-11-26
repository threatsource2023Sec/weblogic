package weblogic.management.provider.beaninfo;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.provider.BeanInfoKey;

public interface BeanInfoAccess {
   String BEANINFO_FACTORY = "META-INF/beaninfofactory.txt";

   BeanInfo getBeanInfoForInstance(Object var1, boolean var2, String var3);

   BeanInfo getBeanInfoForInterface(String var1, boolean var2, String var3);

   BeanInfoKey getBeanInfoKeyForInstance(Object var1, boolean var2, String var3);

   BeanInfoKey getBeanInfoKeyForInterface(String var1, boolean var2, String var3);

   BeanInfo getBeanInfo(BeanInfoKey var1);

   String[] getSubtypes(String var1);

   Class getClassForName(String var1) throws ClassNotFoundException;

   boolean hasBeanInfo(Class var1);

   Class getInterfaceForInstance(Object var1);

   String[] getBeanInfoFactoryNames();

   String[] getInterfacesWithRoleInfo(String var1);

   String getRoleInfoImplementationFactoryTimestamp(String var1);

   BeanInfo getBeanInfoForDescriptorBean(DescriptorBean var1);

   PropertyDescriptor getPropertyDescriptor(BeanInfo var1, String var2);

   void disableProperty(String var1, String... var2);

   void enableProperty(String var1, String... var2);
}
