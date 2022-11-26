package weblogic.management.mbeanservers.internal;

import java.beans.BeanDescriptor;
import java.beans.PropertyDescriptor;
import javax.management.ObjectName;
import weblogic.management.NoAccessRuntimeException;
import weblogic.management.internal.SecurityHelper;
import weblogic.management.jmx.ObjectSecurityManager;
import weblogic.security.service.MBeanResource.ActionType;

public class WLSObjectSecurityManagerImpl implements ObjectSecurityManager {
   public static WLSObjectSecurityManagerImpl getInstance() {
      return WLSObjectSecurityManagerImpl.Maker.SINGLETON;
   }

   public boolean isAnonAccessAllowed(ObjectName objectName, String propertyName, String methodName) {
      return SecurityHelper.isAllowedAnon(objectName, ActionType.READ, propertyName, methodName, (PropertyDescriptor)null);
   }

   public void isAccessAllowed(ObjectName objectName, String propertyName, String methodName, BeanDescriptor beanDescriptor, PropertyDescriptor propertyDescriptor) throws NoAccessRuntimeException {
      SecurityHelper.isAccessAllowed(objectName, ActionType.READ, propertyName, methodName, beanDescriptor, propertyDescriptor);
   }

   private static class Maker {
      private static WLSObjectSecurityManagerImpl SINGLETON = new WLSObjectSecurityManagerImpl();
   }
}
