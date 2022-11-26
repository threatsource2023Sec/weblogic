package weblogic.management.internal;

import java.beans.MethodDescriptor;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyDescriptor;
import java.security.AccessController;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.WebLogicObjectName;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ManagementService;
import weblogic.security.Security;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.Auditor;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.SecurityService.ServiceType;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.utils.PartitionUtils;
import weblogic.utils.StringUtils;

public class ConfigurationAuditor implements PropertyChangeListener {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugConfigurationEdit");
   private static ConfigurationAuditor instance;
   private static boolean instantiated = false;
   private static boolean initialized = false;
   private static AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private boolean oldChangeLoggingAttr = Boolean.getBoolean("weblogic.AdministrationMBeanAuditingEnabled");
   private boolean newChangeLoggingAttr = false;
   private boolean changeLoggingEnabled = false;
   private boolean changeAuditingEnabled = false;
   private WebLogicObjectName domainName;
   private static final String OLD_AUDIT_ENABLED_ATTRIBUTE = "AdministrationMBeanAuditingEnabled";
   private static final String NEW_AUDIT_ENABLED_ATTRIBUTE = "ConfigurationAuditType";
   private static String domain;
   private static final String ARRAY_DELIMITOR_FOR_PARAMS_STRING = "; ";
   private static final String PARAMS_TOKENIZER_STRING = ";";

   private ConfigurationAuditor() {
   }

   public void propertyChange(PropertyChangeEvent evt) {
      if (evt.getPropertyName().equals("ConfigurationAuditType")) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Property change for ConfigurationAuditType");
         }

         String value = (String)evt.getNewValue();
         boolean oldChangeAuditingEnabled = this.changeAuditingEnabled;
         this.setConfiguredAuditing(value);
         if (oldChangeAuditingEnabled && !this.changeAuditingEnabled) {
            this.auditModify(this.domainName.toString(), evt.getPropertyName(), evt.getOldValue(), value, (Exception)null);
         }

         this.logStatus(true);
      } else if (evt.getPropertyName().equals("AdministrationMBeanAuditingEnabled")) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Property change for AdministrationMBeanAuditingEnabled");
         }

         Boolean value = (Boolean)evt.getNewValue();
         this.oldChangeLoggingAttr = value;
         this.setLoggingEnabled(this.newChangeLoggingAttr || this.oldChangeLoggingAttr);
         this.logStatus(true);
      }

   }

   void setLoggingEnabled(boolean enabled) {
      this.changeLoggingEnabled = enabled;
   }

   void intialize(DomainMBean domain) {
      if (initialized) {
         throw new AssertionError("The auditor can only be initialized once");
      } else if (ManagementService.getRuntimeAccess(KERNEL_ID).isAdminServer()) {
         initialized = true;
         this.domainName = domain.getObjectName();
         this.oldChangeLoggingAttr = this.oldChangeLoggingAttr || domain.isAdministrationMBeanAuditingEnabled();
         this.setConfiguredAuditing(domain.getConfigurationAuditType());
         this.logStatus(false);
         domain.addPropertyChangeListener(this);
      }
   }

   public static final ConfigurationAuditor getInstance() {
      if (!instantiated) {
         Class var0 = ConfigurationAuditor.class;
         synchronized(ConfigurationAuditor.class) {
            if (!instantiated) {
               instance = new ConfigurationAuditor();
            }

            instantiated = true;
         }
      }

      return instance;
   }

   public void create(ObjectName objectID, Exception ex) {
      if (this.isAuditable(objectID)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Config auditor create - object name " + objectID);
         }

         if (this.changeLoggingEnabled) {
            if (ex == null) {
               ConfigAuditorLogger.logInfoAuditCreateSuccess(this.getSubjectUser(), objectID.toString());
            } else {
               ConfigAuditorLogger.logInfoAuditCreateFailure(this.getSubjectUser(), objectID.toString(), ex);
            }
         }

         if (this.changeAuditingEnabled) {
            this.auditCreate(objectID.toString(), ex);
         }

      }
   }

   public void remove(ObjectName objectID, Exception ex) {
      if (this.isAuditable(objectID)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Config auditor remove - object name " + objectID);
         }

         if (this.changeLoggingEnabled) {
            if (ex == null) {
               ConfigAuditorLogger.logInfoAuditRemoveSuccess(this.getSubjectUser(), objectID.toString());
            } else {
               ConfigAuditorLogger.logInfoAuditRemoveFailure(this.getSubjectUser(), objectID.toString(), ex);
            }
         }

         if (this.changeAuditingEnabled) {
            this.auditDelete(objectID.toString(), ex);
         }

      }
   }

   public void modify(ObjectName objectID, Object oldAttr, Attribute newAttr, PropertyDescriptor propertyDescriptor, Exception ex) {
      if (this.isAuditable(objectID)) {
         this.auditJMXAttribute(objectID, oldAttr, newAttr, propertyDescriptor, ex);
      }
   }

   public void modify(ObjectName objectID, AttributeList oldAttrList, AttributeList newAttrList, PropertyDescriptor[] propertyDescriptors, Exception ex) {
      if (this.isAuditable(objectID)) {
         Iterator i = newAttrList.iterator();
         Iterator i2 = oldAttrList.iterator();

         for(int j = 0; i.hasNext(); ++j) {
            Attribute oneAttribute = (Attribute)i.next();
            Attribute oldAttribute = (Attribute)i2.next();
            this.auditJMXAttribute(objectID, oldAttribute, oneAttribute, propertyDescriptors[j], ex);
         }

      }
   }

   private void auditJMXAttribute(ObjectName oname, Object oldAttribute, Attribute newAttribute, PropertyDescriptor propertyDescriptor, Exception ex) {
      String humanReadableParams = null;
      String oldValueReadable = null;
      String attributeName = newAttribute.getName();
      boolean encrypted = this.isProtectedAttribute(oname, attributeName, propertyDescriptor);
      if (encrypted) {
         humanReadableParams = "****";
         oldValueReadable = "****";
      } else {
         humanReadableParams = this.convertParamsToHumanReadableString(newAttribute);
         oldValueReadable = this.convertParamsToHumanReadableString(oldAttribute);
      }

      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Config auditor modify - object name " + oname + " old " + oldValueReadable + " new " + humanReadableParams);
      }

      if (this.changeLoggingEnabled) {
         if (ex == null) {
            ConfigAuditorLogger.logInfoAuditModifySuccess(this.getSubjectUser(), oname.toString(), attributeName, oldValueReadable, humanReadableParams);
         } else {
            ConfigAuditorLogger.logInfoAuditModifyFailure(this.getSubjectUser(), oname.toString(), attributeName, oldValueReadable, humanReadableParams, ex);
         }
      }

      if (this.changeAuditingEnabled) {
         this.auditModify(oname.toString(), attributeName, oldValueReadable, humanReadableParams, ex);
      }

   }

   public void invoke(ObjectName objectID, MethodDescriptor md, String actionName, Object[] params, Exception ex) {
      if (this.isAuditable(objectID)) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Config auditor invoke - object name " + objectID + " action " + actionName);
         }

         if (!this.isFilteredMethod(actionName)) {
            if (!actionName.startsWith("createMBean") && !actionName.startsWith("registerMBean")) {
               if (actionName.startsWith("create") && params != null && params.length == 1) {
                  try {
                     this.create(this.getObjectNameFromAction(actionName, params[0]), ex);
                     return;
                  } catch (MalformedObjectNameException var8) {
                  }
               }

               if (actionName.startsWith("unregisterMBean")) {
                  this.remove(objectID, ex);
               } else if (actionName.startsWith("destroy") && params != null && params.length == 1 && params[0] instanceof ObjectName) {
                  this.remove((ObjectName)params[0], ex);
               } else {
                  String protectedArgs = null;
                  if (md != null) {
                     protectedArgs = (String)md.getValue("wls:auditProtectedArgs");
                  }

                  String humanReadableParams = null;
                  if (this.changeLoggingEnabled || this.changeAuditingEnabled) {
                     humanReadableParams = this.convertParamsToHumanReadableString(params);
                     if (protectedArgs != null) {
                        humanReadableParams = this.replaceClearTextPasswords(humanReadableParams, protectedArgs);
                     }

                     if (this.changeLoggingEnabled) {
                        if (ex == null) {
                           ConfigAuditorLogger.logInfoAuditInvokeSuccess(this.getSubjectUser(), objectID.toString(), actionName, humanReadableParams);
                        } else {
                           ConfigAuditorLogger.logInfoAuditInvokeFailure(this.getSubjectUser(), objectID.toString(), actionName, humanReadableParams, ex);
                        }
                     }

                     if (this.changeAuditingEnabled) {
                        this.auditInvoke(objectID.toString(), actionName, humanReadableParams, ex);
                     }
                  }

               }
            } else {
               this.create(objectID, ex);
            }
         }
      }
   }

   private boolean isProtectedAttribute(ObjectName oName, String attributeName, PropertyDescriptor propertyDescriptor) {
      return SecurityHelper.isProtectedAttribute(oName, attributeName, propertyDescriptor);
   }

   private String convertParamsToHumanReadableString(Object param) {
      if (param == null) {
         return "";
      } else {
         if (param instanceof Attribute) {
            Attribute attribute = (Attribute)param;
            String attributeName = attribute.getName();
            param = attribute.getValue();
            if (param == null) {
               return "";
            }
         }

         if (param.getClass().isArray()) {
            StringBuffer buf = new StringBuffer();
            Object[] arrayParam = (Object[])((Object[])param);
            int i = 0;

            for(int n = arrayParam.length; i < n; ++i) {
               if (arrayParam[i] != null) {
                  buf.append(arrayParam[i].toString());
               }

               if (i < n - 1) {
                  buf.append("; ");
               }
            }

            return buf.toString();
         } else {
            return param.toString();
         }
      }
   }

   private String replaceClearTextPasswords(String str, String indices) {
      StringBuffer result = new StringBuffer();
      String[] indicesStringArray = StringUtils.split(indices, ',');
      if (indicesStringArray != null && indicesStringArray.length != 0) {
         int[] indicesArray = new int[indicesStringArray.length];

         for(int i = 0; i < indicesStringArray.length; ++i) {
            try {
               indicesArray[i] = Integer.parseInt(indicesStringArray[i].trim());
            } catch (NumberFormatException var10) {
               ConfigAuditorLogger.logInvalidNumberReplacingClearText(indicesStringArray[i].trim(), var10);
            }
         }

         StringTokenizer argsTokenizer = new StringTokenizer(str, ";");

         for(int i = 1; argsTokenizer.hasMoreTokens(); ++i) {
            String arg = argsTokenizer.nextToken();

            for(int j = 0; j < indicesArray.length; ++j) {
               if (indicesArray[j] == i) {
                  arg = " ****";
                  break;
               }
            }

            result.append(arg);
            if (argsTokenizer.hasMoreElements()) {
               result.append(";");
            }
         }

         return result.toString();
      } else {
         return str;
      }
   }

   public boolean isAuditable(ObjectName name) {
      return this.private_isAuditable(name);
   }

   private boolean private_isAuditable(ObjectName name) {
      if (!initialized) {
         return false;
      } else if (!this.changeLoggingEnabled && !this.changeAuditingEnabled) {
         return false;
      } else if (name.getKeyProperty("Type") == null) {
         return true;
      } else {
         return name.getKeyProperty("Location") == null;
      }
   }

   private boolean isFilteredMethod(String actionName) {
      if (actionName.startsWith("lookup")) {
         return true;
      } else if (actionName.startsWith("find")) {
         return true;
      } else if (actionName.startsWith("getMBean")) {
         return true;
      } else if (actionName.startsWith("getXml")) {
         return true;
      } else if (actionName.equals("preDeregister")) {
         return true;
      } else if (actionName.equals("userExists")) {
         return true;
      } else if (actionName.equals("groupExists")) {
         return true;
      } else if (actionName.equals("advance")) {
         return true;
      } else if (actionName.equals("haveCurrent")) {
         return true;
      } else if (actionName.equals("close")) {
         return true;
      } else if (actionName.equals("saveDomain")) {
         return true;
      } else {
         return actionName.endsWith("DescriptorValue");
      }
   }

   private void logStatus(boolean logDisabled) {
      if (!this.changeLoggingEnabled && !this.changeAuditingEnabled) {
         if (logDisabled) {
            ConfigAuditorLogger.logInfoConfigurationAuditingDisabled(this.getSubjectUser());
         }
      } else {
         ConfigAuditorLogger.logInfoConfigurationAuditingEnabled(this.getSubjectUser());
      }

   }

   private String getSubjectUser() {
      String userId = SubjectUtils.getUsername(Security.getCurrentSubject());
      if (userId == null) {
         userId = new String("<UNKNOWN>");
      }

      return userId;
   }

   private void auditCreate(String objectName, Exception ex) {
      Auditor auditor = this.getAuditor();
      if (auditor != null) {
         AuditCreateConfigurationEventImpl createAuditEvent;
         if (ex == null) {
            createAuditEvent = new AuditCreateConfigurationEventImpl(AuditSeverity.SUCCESS, SecurityServiceManager.getCurrentSubject(KERNEL_ID), objectName);
         } else {
            createAuditEvent = new AuditCreateConfigurationEventImpl(AuditSeverity.FAILURE, SecurityServiceManager.getCurrentSubject(KERNEL_ID), objectName);
            createAuditEvent.setFailureException(ex);
         }

         auditor.writeEvent(createAuditEvent);
      }

   }

   private void auditDelete(String objectName, Exception ex) {
      Auditor auditor = this.getAuditor();
      if (auditor != null) {
         AuditDeleteConfigurationEventImpl deleteAuditEvent;
         if (ex == null) {
            deleteAuditEvent = new AuditDeleteConfigurationEventImpl(AuditSeverity.SUCCESS, SecurityServiceManager.getCurrentSubject(KERNEL_ID), objectName);
         } else {
            deleteAuditEvent = new AuditDeleteConfigurationEventImpl(AuditSeverity.FAILURE, SecurityServiceManager.getCurrentSubject(KERNEL_ID), objectName);
            deleteAuditEvent.setFailureException(ex);
         }

         auditor.writeEvent(deleteAuditEvent);
      }

   }

   private void auditInvoke(String objectName, String methodName, String params, Exception ex) {
      Auditor auditor = this.getAuditor();
      if (auditor != null) {
         AuditInvokeConfigurationEventImpl invokeAuditEvent;
         if (ex == null) {
            invokeAuditEvent = new AuditInvokeConfigurationEventImpl(AuditSeverity.SUCCESS, SecurityServiceManager.getCurrentSubject(KERNEL_ID), objectName, methodName, params);
         } else {
            invokeAuditEvent = new AuditInvokeConfigurationEventImpl(AuditSeverity.FAILURE, SecurityServiceManager.getCurrentSubject(KERNEL_ID), objectName, methodName, params);
            invokeAuditEvent.setFailureException(ex);
         }

         auditor.writeEvent(invokeAuditEvent);
      }

   }

   private void auditModify(String objectName, String attrName, Object oldValue, Object newValue, Exception ex) {
      Auditor auditor = this.getAuditor();
      if (auditor != null) {
         AuditSetAttributeConfigurationEventImpl modifyAuditEvent;
         if (ex == null) {
            modifyAuditEvent = new AuditSetAttributeConfigurationEventImpl(AuditSeverity.SUCCESS, SecurityServiceManager.getCurrentSubject(KERNEL_ID), objectName, attrName, oldValue, newValue);
         } else {
            modifyAuditEvent = new AuditSetAttributeConfigurationEventImpl(AuditSeverity.FAILURE, SecurityServiceManager.getCurrentSubject(KERNEL_ID), objectName, attrName, oldValue, newValue);
            modifyAuditEvent.setFailureException(ex);
         }

         auditor.writeEvent(modifyAuditEvent);
      }

   }

   private void setConfiguredAuditing(String auditVal) {
      if (auditVal.equalsIgnoreCase("audit")) {
         this.changeAuditingEnabled = true;
         this.newChangeLoggingAttr = false;
      } else if (auditVal.equalsIgnoreCase("logaudit")) {
         this.changeAuditingEnabled = true;
         this.newChangeLoggingAttr = true;
      } else if (auditVal.equalsIgnoreCase("log")) {
         this.changeAuditingEnabled = false;
         this.newChangeLoggingAttr = true;
      } else {
         this.changeAuditingEnabled = false;
         this.newChangeLoggingAttr = false;
      }

      this.setLoggingEnabled(this.newChangeLoggingAttr || this.oldChangeLoggingAttr);
   }

   private ObjectName getObjectNameFromAction(String actionName, Object name) throws MalformedObjectNameException {
      String sName = domain + ":Name=" + name.toString() + ",Type=" + actionName.substring(6);
      return new ObjectName(sName);
   }

   private Auditor getAuditor() {
      String realmName = SecurityServiceManager.getRealmName(PartitionUtils.getPartitionName());
      if (realmName == null) {
         realmName = SecurityServiceManager.getAdministrativeRealmName();
      }

      return (Auditor)SecurityServiceManager.getSecurityService(KERNEL_ID, realmName, ServiceType.AUDIT);
   }

   static {
      domain = ManagementService.getRuntimeAccess(KERNEL_ID).getDomainName();
   }
}
