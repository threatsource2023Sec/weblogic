package weblogic.management.mbeanservers.edit.internal;

import java.io.IOException;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.DynamicMBean;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.NoAccessRuntimeException;
import weblogic.management.jmx.mbeanserver.WLSMBeanServer;
import weblogic.management.jmx.mbeanserver.WLSMBeanServerInterceptorBase;
import weblogic.management.jmx.modelmbean.WLSModelMBean;
import weblogic.management.provider.EditAccess;

public class EditLockInterceptor extends WLSMBeanServerInterceptorBase {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugJMXEdit");
   private static final EditConfigTextFormatter editConfigTextFormatter = new EditConfigTextFormatter();
   private volatile WLSMBeanServer wlsMBeanServer;
   private static final ObjectName JMX_FRAMEWORK_SESSION_MANAGER_MBEAN_NAME = getJMXFrameworkConfigurationSessionManagerMBeanName();
   private volatile EditAccess editAccess;

   public EditLockInterceptor(EditAccess editAccess, WLSMBeanServer mbeanServer) {
      this.editAccess = editAccess;
      this.wlsMBeanServer = mbeanServer;
   }

   public ObjectInstance createMBean(String s, ObjectName objectName) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
      this.checkEditLock();
      ObjectInstance newInstance = super.createMBean(s, objectName);
      return newInstance;
   }

   public ObjectInstance createMBean(String s, ObjectName objectName, ObjectName objectName1) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
      this.checkEditLock();
      ObjectInstance newInstance = super.createMBean(s, objectName, objectName1);
      return newInstance;
   }

   public ObjectInstance createMBean(String s, ObjectName objectName, Object[] objects, String[] strings) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
      this.checkEditLock();
      ObjectInstance newInstance = super.createMBean(s, objectName, objects, strings);
      return newInstance;
   }

   public ObjectInstance createMBean(String s, ObjectName objectName, ObjectName objectName1, Object[] objects, String[] strings) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
      this.checkEditLock();
      ObjectInstance newInstance = super.createMBean(s, objectName, objectName1, objects, strings);
      return newInstance;
   }

   public void setAttribute(ObjectName objectName, Attribute attribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException, IOException {
      this.checkEditLock();
      super.setAttribute(objectName, attribute);
   }

   public AttributeList setAttributes(ObjectName objectName, AttributeList attributeList) throws InstanceNotFoundException, ReflectionException, IOException {
      this.checkEditLock();
      AttributeList result = super.setAttributes(objectName, attributeList);
      return result;
   }

   public Object invoke(ObjectName objectName, String s, Object[] objects, String[] strings) throws InstanceNotFoundException, MBeanException, ReflectionException, IOException {
      if (this.editAccess.isEditor()) {
         return super.invoke(objectName, s, objects, strings);
      } else {
         boolean requireEdit = false;
         Object mbean = this.wlsMBeanServer.lookupObject(objectName);
         if (mbean != null) {
            if (mbean != null && mbean instanceof WLSModelMBean) {
               WLSModelMBean modelMBean = (WLSModelMBean)mbean;
               String role = modelMBean.getRole(s, objects, strings);
               if (role != null) {
                  if (!role.equals("factory") && !role.equals("collection")) {
                     if (role.equals("operation")) {
                        String impact = modelMBean.getImpact(s, objects, strings);
                        if (impact != null && !impact.equals("info")) {
                           requireEdit = true;
                        }
                     }
                  } else {
                     requireEdit = true;
                  }
               }
            } else if (JMX_FRAMEWORK_SESSION_MANAGER_MBEAN_NAME.equals(objectName)) {
               requireEdit = false;
            } else if (mbean != null && mbean instanceof DynamicMBean) {
               MBeanInfo info = ((DynamicMBean)DynamicMBean.class.cast(mbean)).getMBeanInfo();
               requireEdit = this.checkOperationImpact(info, s, strings);
            } else {
               requireEdit = true;
            }
         }

         if (requireEdit) {
            this.checkEditLock();
         }

         return super.invoke(objectName, s, objects, strings);
      }
   }

   private boolean checkOperationImpact(MBeanInfo info, String operationName, String[] operationSignature) {
      int numberOfParameters = operationSignature == null ? 0 : operationSignature.length;
      MBeanOperationInfo[] opersInfo = info.getOperations();
      if (opersInfo != null && opersInfo.length != 0) {
         boolean requireCheck = true;
         MBeanOperationInfo matchingOperInfo = null;
         MBeanOperationInfo[] var8 = opersInfo;
         int var9 = opersInfo.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            MBeanOperationInfo operInfo = var8[var10];
            if (operationName.equals(operInfo.getName())) {
               MBeanParameterInfo[] params = operInfo.getSignature();
               int numParam = params == null ? 0 : params.length;
               if (numParam == numberOfParameters) {
                  if (numberOfParameters == 0) {
                     matchingOperInfo = operInfo;
                     break;
                  }

                  boolean signatureMatches = true;

                  for(int i = 0; i < numberOfParameters; ++i) {
                     if (params[i].getType() == null || !params[i].getType().equals(operationSignature[i])) {
                        signatureMatches = false;
                        break;
                     }
                  }

                  if (signatureMatches) {
                     matchingOperInfo = operInfo;
                     break;
                  }
               }
            }
         }

         if (matchingOperInfo != null) {
            requireCheck = matchingOperInfo.getImpact() != 0;
         }

         return requireCheck;
      } else {
         return true;
      }
   }

   private void checkEditLock() {
      if (this.editAccess != null) {
         if (!this.editAccess.isEditor()) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Caller does not own the edit lock.");
            }

            throw new NoAccessRuntimeException(editConfigTextFormatter.callerHasNotStartedEditSession());
         }
      }
   }

   void releaseEditAccess() {
      this.editAccess = null;
   }

   private static ObjectName getJMXFrameworkConfigurationSessionManagerMBeanName() {
      try {
         return new ObjectName("oracle.as.jmx:type=ConfigurationSessionManager");
      } catch (MalformedObjectNameException var1) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Error creating ObjectName for JMX Framework Configuration Session Manager: oracle.as.jmx:type=ConfigurationSessionManager", var1);
         }

         return null;
      }
   }
}
