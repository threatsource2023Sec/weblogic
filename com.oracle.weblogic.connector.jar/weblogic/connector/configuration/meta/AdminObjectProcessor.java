package weblogic.connector.configuration.meta;

import java.io.Externalizable;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.resource.Referenceable;
import javax.resource.spi.AdministeredObject;
import javax.resource.spi.ResourceAdapterAssociation;
import weblogic.connector.exception.RAException;
import weblogic.connector.utils.ValidationMessage;
import weblogic.j2ee.descriptor.AdminObjectBean;
import weblogic.j2ee.descriptor.ResourceAdapterBean;

@AnnotationProcessorDescription(
   targetAnnotation = AdministeredObject.class
)
class AdminObjectProcessor implements TypeAnnotationProcessor {
   private static final Set EXCLUDING_INTERFACE_SET = new HashSet();
   private final ConnectorBeanNavigator beanNavigator;

   AdminObjectProcessor(ConnectorBeanNavigator connectorBeanNav) {
      this.beanNavigator = connectorBeanNav;
   }

   public void processClass(Class clz, AdministeredObject annotation) throws RAException {
      Class[] adminObjectInterfaces = annotation.adminObjectInterfaces();
      ResourceAdapterBean resourceAdapterBean = this.beanNavigator.getOrCreateResourceAdapter();
      AdminObjectBean[] adminObjects = resourceAdapterBean.getAdminObjects();
      if (adminObjectInterfaces.length == 0) {
         adminObjectInterfaces = this.getAdminObjectInterface(clz);
         if (adminObjectInterfaces.length > 1) {
            Set interfaceInDD = this.getAdminObjectInterface(clz.getName(), adminObjects);
            if (interfaceInDD.isEmpty()) {
               this.beanNavigator.context.error(ValidationMessage.RAComplianceTextMsg.CANT_DETERMINE_ADMIN_INTERFACE(clz.getName()));
               return;
            }
         }
      }

      Class[] var12 = adminObjectInterfaces;
      int var7 = adminObjectInterfaces.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         Class intf = var12[var8];
         String interfaceName = intf.getName();
         String className = clz.getName();
         this.getOrCreateAdminObjectBean(interfaceName, className, adminObjects, resourceAdapterBean, this.beanNavigator.context);
      }

   }

   AdminObjectBean getOrCreateAdminObjectBean(String interfaceName, String className, AdminObjectBean[] adminObjects, ResourceAdapterBean resourceAdapterBean, ConnectorAPContextImpl context) {
      if (adminObjects != null) {
         AdminObjectBean[] var6 = adminObjects;
         int var7 = adminObjects.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            AdminObjectBean adminObjectBean = var6[var8];
            if (className.equals(adminObjectBean.getAdminObjectClass()) && interfaceName.equals(adminObjectBean.getAdminObjectInterface())) {
               return adminObjectBean;
            }
         }
      }

      AdminObjectBean adminObject = resourceAdapterBean.createAdminObject();
      adminObject.setAdminObjectInterface(interfaceName);
      adminObject.setAdminObjectClass(className);
      context.readPath("adminObject", interfaceName, className);
      return adminObject;
   }

   private Set getAdminObjectInterface(String className, AdminObjectBean[] adminObjects) {
      Set intfs = new HashSet();
      if (adminObjects != null) {
         AdminObjectBean[] var4 = adminObjects;
         int var5 = adminObjects.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            AdminObjectBean adminObjectBean = var4[var6];
            if (className.equals(adminObjectBean.getAdminObjectClass())) {
               intfs.add(adminObjectBean.getAdminObjectInterface());
            }
         }
      }

      return intfs;
   }

   Class[] getAdminObjectInterface(Class clz) {
      Set result = new HashSet();

      for(Class it = clz; !Object.class.equals(it); it = it.getSuperclass()) {
         Class[] interfaces = it.getInterfaces();
         Class[] var5 = interfaces;
         int var6 = interfaces.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Class intf = var5[var7];
            if (!this.shouldBeExcluded(intf)) {
               result.add(intf);
            }
         }
      }

      return (Class[])result.toArray(new Class[0]);
   }

   private boolean shouldBeExcluded(Class clz) {
      return EXCLUDING_INTERFACE_SET.contains(clz);
   }

   static {
      EXCLUDING_INTERFACE_SET.add(Serializable.class);
      EXCLUDING_INTERFACE_SET.add(Externalizable.class);
      EXCLUDING_INTERFACE_SET.add(ResourceAdapterAssociation.class);
      EXCLUDING_INTERFACE_SET.add(Referenceable.class);
   }
}
