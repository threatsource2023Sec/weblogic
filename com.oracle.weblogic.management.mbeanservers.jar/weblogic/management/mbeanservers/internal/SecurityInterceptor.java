package weblogic.management.mbeanservers.internal;

import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.FeatureDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.security.AccessController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanRegistrationException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.logging.Loggable;
import weblogic.management.NoAccessRuntimeException;
import weblogic.management.WebLogicObjectName;
import weblogic.management.commo.StandardInterface;
import weblogic.management.internal.ConfigurationAuditor;
import weblogic.management.internal.DefaultJMXPolicyManager;
import weblogic.management.internal.JMXContextHandler;
import weblogic.management.internal.SecurityHelper;
import weblogic.management.jmx.JMXLogger;
import weblogic.management.jmx.mbeanserver.WLSMBeanServer;
import weblogic.management.jmx.mbeanserver.WLSMBeanServerInterceptorBase;
import weblogic.management.jmx.modelmbean.WLSModelMBean;
import weblogic.management.provider.ManagementService;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.audit.AuditorMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.ConsumptionException;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.JMXResource;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.service.MBeanResource.ActionType;
import weblogic.security.service.SecurityService.ServiceType;

public class SecurityInterceptor extends WLSMBeanServerInterceptorBase {
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXCoreConcise");
   WLSMBeanServer wlsMBeanServer;
   private boolean useSecurityFramework;
   private boolean auditorsConfigured;
   private AuthorizationManager authorizer;
   static boolean registeredPolicies;
   private static Map securityInterceptors = Collections.synchronizedMap(new HashMap());
   private static final String[] APP_SCOPED_TYPES = new String[]{"ApplicationRuntime", "JDBCSystemResource", "JMSSystemResource", "WLDFSystemResource", "CustomResource"};
   private static AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private static String BEA_DOMAIN = "com.bea";

   public SecurityInterceptor(WLSMBeanServer mbeanServer) {
      this(mbeanServer, (String)null);
   }

   public SecurityInterceptor(WLSMBeanServer mbeanServer, String name) {
      this.wlsMBeanServer = mbeanServer;
      RealmMBean realmMBean = ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration().getDefaultRealm();
      this.useSecurityFramework = realmMBean.isDelegateMBeanAuthorization();
      AuditorMBean[] auditorMBeans = realmMBean.getAuditors();
      if (this.useSecurityFramework && auditorMBeans != null && auditorMBeans.length > 0) {
         this.auditorsConfigured = true;
      }

      this.authorizer = (AuthorizationManager)SecurityServiceManager.getSecurityService(kernelId, SecurityServiceManager.getDefaultRealmName(), ServiceType.AUTHORIZE);
      if (name != null) {
         securityInterceptors.put(name, this);
      }

   }

   public ObjectInstance createMBean(String s, ObjectName objectName) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
      try {
         this.checkForBEADomain(objectName);
         this.checkCreateSecurity(s, objectName);
         ObjectInstance newInstance = super.createMBean(s, objectName);
         ConfigurationAuditor.getInstance().create(objectName, (Exception)null);
         return newInstance;
      } catch (NoAccessRuntimeException var4) {
         ConfigurationAuditor.getInstance().create(objectName, var4);
         throw var4;
      } catch (ReflectionException var5) {
         ConfigurationAuditor.getInstance().create(objectName, var5);
         throw var5;
      } catch (InstanceAlreadyExistsException var6) {
         ConfigurationAuditor.getInstance().create(objectName, var6);
         throw var6;
      } catch (MBeanRegistrationException var7) {
         ConfigurationAuditor.getInstance().create(objectName, var7);
         throw var7;
      } catch (MBeanException var8) {
         ConfigurationAuditor.getInstance().create(objectName, var8);
         throw var8;
      } catch (NotCompliantMBeanException var9) {
         ConfigurationAuditor.getInstance().create(objectName, var9);
         throw var9;
      } catch (IOException var10) {
         ConfigurationAuditor.getInstance().create(objectName, var10);
         throw var10;
      }
   }

   public ObjectInstance createMBean(String s, ObjectName objectName, ObjectName objectName1) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
      try {
         this.checkForBEADomain(objectName);
         this.checkCreateSecurity(s, objectName, objectName1);
         ObjectInstance newInstance = super.createMBean(s, objectName, objectName1);
         ConfigurationAuditor.getInstance().create(objectName, (Exception)null);
         return newInstance;
      } catch (NoAccessRuntimeException var5) {
         ConfigurationAuditor.getInstance().create(objectName, var5);
         throw var5;
      } catch (ReflectionException var6) {
         ConfigurationAuditor.getInstance().create(objectName, var6);
         throw var6;
      } catch (InstanceAlreadyExistsException var7) {
         ConfigurationAuditor.getInstance().create(objectName, var7);
         throw var7;
      } catch (MBeanRegistrationException var8) {
         ConfigurationAuditor.getInstance().create(objectName, var8);
         throw var8;
      } catch (MBeanException var9) {
         ConfigurationAuditor.getInstance().create(objectName, var9);
         throw var9;
      } catch (NotCompliantMBeanException var10) {
         ConfigurationAuditor.getInstance().create(objectName, var10);
         throw var10;
      } catch (InstanceNotFoundException var11) {
         ConfigurationAuditor.getInstance().create(objectName, var11);
         throw var11;
      } catch (IOException var12) {
         ConfigurationAuditor.getInstance().create(objectName, var12);
         throw var12;
      }
   }

   public ObjectInstance createMBean(String s, ObjectName objectName, Object[] objects, String[] strings) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, IOException {
      try {
         this.checkForBEADomain(objectName);
         this.checkCreateSecurity(s, objectName, objects, strings);
         ObjectInstance newInstance = super.createMBean(s, objectName, objects, strings);
         ConfigurationAuditor.getInstance().create(objectName, (Exception)null);
         return newInstance;
      } catch (NoAccessRuntimeException var6) {
         ConfigurationAuditor.getInstance().create(objectName, var6);
         throw var6;
      } catch (ReflectionException var7) {
         ConfigurationAuditor.getInstance().create(objectName, var7);
         throw var7;
      } catch (InstanceAlreadyExistsException var8) {
         ConfigurationAuditor.getInstance().create(objectName, var8);
         throw var8;
      } catch (MBeanRegistrationException var9) {
         ConfigurationAuditor.getInstance().create(objectName, var9);
         throw var9;
      } catch (MBeanException var10) {
         ConfigurationAuditor.getInstance().create(objectName, var10);
         throw var10;
      } catch (NotCompliantMBeanException var11) {
         ConfigurationAuditor.getInstance().create(objectName, var11);
         throw var11;
      } catch (IOException var12) {
         ConfigurationAuditor.getInstance().create(objectName, var12);
         throw var12;
      }
   }

   public ObjectInstance createMBean(String s, ObjectName objectName, ObjectName objectName1, Object[] objects, String[] strings) throws ReflectionException, InstanceAlreadyExistsException, MBeanRegistrationException, MBeanException, NotCompliantMBeanException, InstanceNotFoundException, IOException {
      try {
         this.checkForBEADomain(objectName);
         this.checkCreateSecurity(s, objectName, objectName1, objects, strings);
         ObjectInstance newInstance = super.createMBean(s, objectName, objectName1, objects, strings);
         ConfigurationAuditor.getInstance().create(objectName, (Exception)null);
         return newInstance;
      } catch (NoAccessRuntimeException var7) {
         ConfigurationAuditor.getInstance().create(objectName, var7);
         throw var7;
      } catch (ReflectionException var8) {
         ConfigurationAuditor.getInstance().create(objectName, var8);
         throw var8;
      } catch (InstanceAlreadyExistsException var9) {
         ConfigurationAuditor.getInstance().create(objectName, var9);
         throw var9;
      } catch (MBeanRegistrationException var10) {
         ConfigurationAuditor.getInstance().create(objectName, var10);
         throw var10;
      } catch (MBeanException var11) {
         ConfigurationAuditor.getInstance().create(objectName, var11);
         throw var11;
      } catch (NotCompliantMBeanException var12) {
         ConfigurationAuditor.getInstance().create(objectName, var12);
         throw var12;
      } catch (InstanceNotFoundException var13) {
         ConfigurationAuditor.getInstance().create(objectName, var13);
         throw var13;
      } catch (IOException var14) {
         ConfigurationAuditor.getInstance().create(objectName, var14);
         throw var14;
      }
   }

   public Object getAttribute(ObjectName objectName, String s) throws MBeanException, AttributeNotFoundException, InstanceNotFoundException, ReflectionException, IOException {
      this.checkGetSecurity(objectName, s);
      Object instance = super.getAttribute(objectName, s);
      return instance;
   }

   public AttributeList getAttributes(ObjectName objectName, String[] strings) throws InstanceNotFoundException, ReflectionException, IOException {
      strings = this.checkGetSecurity(objectName, strings);
      return super.getAttributes(objectName, strings);
   }

   public void unregisterMBean(ObjectName name) throws InstanceNotFoundException, MBeanRegistrationException, IOException {
      try {
         if (this.isWLSMBean(name)) {
            Loggable loggable = JMXLogger.logWLSMBeanUnregisterFailedLoggable(name.getCanonicalName());
            throw new NoAccessRuntimeException(loggable.getMessage());
         } else {
            this.checkUnregisterSecurity(name);
            super.unregisterMBean(name);
            ConfigurationAuditor.getInstance().remove(name, (Exception)null);
         }
      } catch (NoAccessRuntimeException var3) {
         ConfigurationAuditor.getInstance().remove(name, var3);
         throw var3;
      } catch (InstanceNotFoundException var4) {
         ConfigurationAuditor.getInstance().remove(name, var4);
         throw var4;
      } catch (MBeanRegistrationException var5) {
         ConfigurationAuditor.getInstance().remove(name, var5);
         throw var5;
      } catch (IOException var6) {
         ConfigurationAuditor.getInstance().remove(name, var6);
         throw var6;
      }
   }

   public void setAttribute(ObjectName objectName, Attribute attribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException, IOException {
      Object oattr = null;
      PropertyDescriptor desc = null;

      try {
         if (ConfigurationAuditor.getInstance().isAuditable(objectName) || this.auditorsConfigured) {
            desc = this.getPropertyDescriptor(objectName, attribute.getName());
            oattr = super.getAttribute(objectName, attribute.getName());
         }
      } catch (Exception var13) {
         System.out.println("Exception caught while performing getAttribute for setAttribute " + var13);
      }

      try {
         this.checkSetSecurity(objectName, attribute, oattr);
         super.setAttribute(objectName, attribute);
         ConfigurationAuditor.getInstance().modify(objectName, oattr, attribute, desc, (Exception)null);
      } catch (NoAccessRuntimeException var6) {
         ConfigurationAuditor.getInstance().modify(objectName, oattr, attribute, desc, var6);
         throw var6;
      } catch (InstanceNotFoundException var7) {
         ConfigurationAuditor.getInstance().modify(objectName, oattr, attribute, desc, var7);
         throw var7;
      } catch (AttributeNotFoundException var8) {
         ConfigurationAuditor.getInstance().modify(objectName, oattr, attribute, desc, var8);
         throw var8;
      } catch (InvalidAttributeValueException var9) {
         ConfigurationAuditor.getInstance().modify(objectName, oattr, attribute, desc, var9);
         throw var9;
      } catch (MBeanException var10) {
         ConfigurationAuditor.getInstance().modify(objectName, oattr, attribute, desc, var10);
         throw var10;
      } catch (ReflectionException var11) {
         ConfigurationAuditor.getInstance().modify(objectName, oattr, attribute, desc, var11);
         throw var11;
      } catch (IOException var12) {
         ConfigurationAuditor.getInstance().modify(objectName, oattr, attribute, desc, var12);
         throw var12;
      }
   }

   public AttributeList setAttributes(ObjectName objectName, AttributeList attributeList) throws InstanceNotFoundException, ReflectionException, IOException {
      AttributeList oldAttributes = null;
      PropertyDescriptor[] descs = null;
      if (ConfigurationAuditor.getInstance().isAuditable(objectName) || this.auditorsConfigured) {
         try {
            Iterator i = attributeList.iterator();
            descs = new PropertyDescriptor[attributeList.size()];
            String[] attrList = new String[attributeList.size()];

            for(int j = 0; j < attributeList.size(); ++j) {
               Object nextAttribute = i.next();
               if (!(nextAttribute instanceof Attribute)) {
                  throw new RuntimeException("AttributeList must contain instances of Attribute");
               }

               Attribute a = (Attribute)nextAttribute;
               attrList[j] = new String(a.getName());
               descs[j] = this.getPropertyDescriptor(objectName, a.getName());
            }

            oldAttributes = super.getAttributes(objectName, attrList);
         } catch (Exception var14) {
         }
      }

      try {
         this.checkSetSecurity(objectName, attributeList);
         AttributeList result = super.setAttributes(objectName, attributeList);
         ConfigurationAuditor.getInstance().modify(objectName, oldAttributes, attributeList, descs, (Exception)null);
         return result;
      } catch (NoAccessRuntimeException var10) {
         ConfigurationAuditor.getInstance().modify(objectName, oldAttributes, attributeList, descs, var10);
         throw var10;
      } catch (InstanceNotFoundException var11) {
         ConfigurationAuditor.getInstance().modify(objectName, oldAttributes, attributeList, descs, var11);
         throw var11;
      } catch (ReflectionException var12) {
         ConfigurationAuditor.getInstance().modify(objectName, oldAttributes, attributeList, descs, var12);
         throw var12;
      } catch (IOException var13) {
         ConfigurationAuditor.getInstance().modify(objectName, oldAttributes, attributeList, descs, var13);
         throw var13;
      }
   }

   public Object invoke(ObjectName objectName, String s, Object[] objects, String[] strings) throws InstanceNotFoundException, MBeanException, ReflectionException, IOException {
      Object result = null;
      MethodDescriptor md = this.getMethodDescriptor(objectName, s, strings);

      try {
         this.checkInvokeSecurity(objectName, s, objects, strings, md);
         result = super.invoke(objectName, s, objects, strings);
         ConfigurationAuditor.getInstance().invoke(objectName, md, s, objects, (Exception)null);
         return result;
      } catch (NoAccessRuntimeException var8) {
         ConfigurationAuditor.getInstance().invoke(objectName, md, s, objects, var8);
         throw var8;
      } catch (InstanceNotFoundException var9) {
         ConfigurationAuditor.getInstance().invoke(objectName, md, s, objects, var9);
         throw var9;
      } catch (MBeanException var10) {
         ConfigurationAuditor.getInstance().invoke(objectName, md, s, objects, var10);
         throw var10;
      } catch (ReflectionException var11) {
         ConfigurationAuditor.getInstance().invoke(objectName, md, s, objects, var11);
         throw var11;
      } catch (IOException var12) {
         ConfigurationAuditor.getInstance().invoke(objectName, md, s, objects, var12);
         throw var12;
      }
   }

   public static boolean isGetAccessAllowed(String mbeanServerName, ObjectName objectName, String attr) throws AttributeNotFoundException, InstanceNotFoundException {
      SecurityInterceptor secInterceptor = (SecurityInterceptor)securityInterceptors.get(mbeanServerName);
      if (secInterceptor == null) {
         throw new InstanceNotFoundException("MBeanServer " + mbeanServerName + "does not exist");
      } else {
         try {
            secInterceptor.checkGetSecurity(objectName, attr);
            return true;
         } catch (NoAccessRuntimeException var5) {
            return false;
         }
      }
   }

   private void checkCreateSecurity(String s, ObjectName objectName) {
      this.initDefaultPolicies();
      this.checkMLetCreation(s);
      if (objectName != null) {
         if (this.useSecurityFramework) {
            this.isAccessAllowed(objectName, "create", (String)null, this.getBeanDescriptor(objectName), (FeatureDescriptor)null);
         } else if (!this.isWLSMBean(objectName) && this.isCommoMBean(objectName)) {
            SecurityHelper.isAccessAllowedCommo(objectName, ActionType.REGISTER, (String)null, "createMBean", this.getBeanDescriptor(objectName));
         } else {
            SecurityHelper.isAccessAllowed(objectName, ActionType.REGISTER, (String)null, "createMBean", this.getBeanDescriptor(objectName));
         }

      }
   }

   private void checkCreateSecurity(String s, ObjectName objectName, ObjectName objectName1) {
      this.initDefaultPolicies();
      this.checkMLetCreation(s);
      if (objectName != null) {
         if (this.useSecurityFramework) {
            this.isAccessAllowed(objectName, "create", (String)null, this.getBeanDescriptor(objectName), (FeatureDescriptor)null);
         } else if (!this.isWLSMBean(objectName) && this.isCommoMBean(objectName)) {
            SecurityHelper.isAccessAllowedCommo(objectName, ActionType.REGISTER, (String)null, "createMBean", this.getBeanDescriptor(objectName));
         } else {
            SecurityHelper.isAccessAllowed(objectName, ActionType.REGISTER, (String)null, "createMBean", this.getBeanDescriptor(objectName));
         }

      }
   }

   private void checkCreateSecurity(String s, ObjectName objectName, Object[] objects, String[] strings) {
      this.initDefaultPolicies();
      this.checkMLetCreation(s);
      if (this.useSecurityFramework) {
         this.isAccessAllowed(objectName, "create", (String)null, this.getBeanDescriptor(objectName), (FeatureDescriptor)null);
      } else if (!this.isWLSMBean(objectName) && this.isCommoMBean(objectName)) {
         SecurityHelper.isAccessAllowedCommo(objectName, ActionType.REGISTER, (String)null, "createMBean", this.getBeanDescriptor(objectName));
      } else {
         SecurityHelper.isAccessAllowed(objectName, ActionType.REGISTER, (String)null, "createMBean", this.getBeanDescriptor(objectName));
      }

   }

   private void checkCreateSecurity(String s, ObjectName objectName, ObjectName objectName1, Object[] objects, String[] strings) {
      this.initDefaultPolicies();
      this.checkMLetCreation(s);
      if (this.useSecurityFramework) {
         this.isAccessAllowed(objectName, "create", (String)null, this.getBeanDescriptor(objectName), (FeatureDescriptor)null);
      } else if (!this.isWLSMBean(objectName) && this.isCommoMBean(objectName)) {
         SecurityHelper.isAccessAllowedCommo(objectName, ActionType.REGISTER, (String)null, "createMBean", this.getBeanDescriptor(objectName));
      } else {
         SecurityHelper.isAccessAllowed(objectName, ActionType.REGISTER, (String)null, "createMBean", this.getBeanDescriptor(objectName));
      }

   }

   private void checkGetSecurity(ObjectName objectName, String attr) throws AttributeNotFoundException {
      this.initDefaultPolicies();
      PropertyDescriptor prop = this.getPropertyDescriptor(objectName, attr);
      Boolean encrypted = null;
      Boolean sensitive = null;
      if (prop != null) {
         encrypted = (Boolean)prop.getValue("encrypted");
         sensitive = (Boolean)prop.getValue("sensitive");
      }

      if (encrypted != null && encrypted && !attr.endsWith("Encrypted")) {
         String sysprop = System.getProperty("weblogic.management.clearTextCredentialAccessEnabled");
         boolean clearTextAllowed;
         if (sysprop != null && sysprop.length() > 0) {
            clearTextAllowed = Boolean.parseBoolean(sysprop);
         } else {
            clearTextAllowed = ManagementService.getRuntimeAccess(kernelId).getDomain().getSecurityConfiguration().isClearTextCredentialAccessEnabled();
         }

         if (!clearTextAllowed) {
            throw new NoAccessRuntimeException("Access to sensitive attribute in clear text is not allowed due to the setting of ClearTextCredentialAccessEnabled attribute in SecurityConfigurationMBean. Attr: " + attr + ", MBean name: " + objectName);
         }
      }

      if (this.useSecurityFramework) {
         String operation = "get";
         if (encrypted != null && encrypted || sensitive != null && sensitive) {
            operation = "getEncrypted";
         }

         this.isAccessAllowed(objectName, operation, attr, this.getBeanDescriptor(objectName), prop);
      } else if (!this.isWLSMBean(objectName) && this.isCommoMBean(objectName)) {
         SecurityHelper.isAccessAllowedCommo(objectName, ActionType.READ, attr, "getAttribute", this.getBeanDescriptor(objectName), this.getPropertyDescriptor(objectName, attr));
      } else {
         SecurityHelper.isAccessAllowed(objectName, ActionType.READ, attr, "getAttribute", this.getBeanDescriptor(objectName), this.getPropertyDescriptor(objectName, attr));
      }

   }

   private String[] checkGetSecurity(ObjectName objectName, String[] attributes) {
      ArrayList permittedAttributes = new ArrayList();
      this.initDefaultPolicies();

      for(int i = 0; attributes != null && i < attributes.length; ++i) {
         try {
            this.checkGetSecurity(objectName, attributes[i]);
            permittedAttributes.add(attributes[i]);
         } catch (AttributeNotFoundException var6) {
         } catch (NoAccessRuntimeException var7) {
         }
      }

      return (String[])permittedAttributes.toArray(new String[0]);
   }

   private void checkUnregisterSecurity(ObjectName objectName) {
      this.initDefaultPolicies();
      if (this.useSecurityFramework) {
         this.isAccessAllowed(objectName, "unregister", (String)null, this.getBeanDescriptor(objectName), (FeatureDescriptor)null);
      } else {
         if (!this.isWLSMBean(objectName) && !this.isCommoMBean(objectName) && !Boolean.getBoolean("weblogic.management.checkunregister")) {
            return;
         }

         if (!this.isWLSMBean(objectName) && this.isCommoMBean(objectName)) {
            SecurityHelper.isAccessAllowedCommo(objectName, ActionType.UNREGISTER, (String)null, "unregisterMBean", this.getBeanDescriptor(objectName));
         } else {
            SecurityHelper.isAccessAllowed(objectName, ActionType.UNREGISTER, (String)null, "unregisterMBean", this.getBeanDescriptor(objectName));
         }
      }

   }

   private void checkSetSecurity(ObjectName objectName, Attribute attribute, Object oldAttr) throws AttributeNotFoundException {
      this.initDefaultPolicies();
      String attr = attribute.getName();
      PropertyDescriptor prop = this.getPropertyDescriptor(objectName, attr);
      if (this.useSecurityFramework) {
         String operation = "set";
         Boolean encrypted = null;
         Boolean sensitive = null;
         String[] signature = new String[1];
         Object[] parameters = new Object[]{attribute.getValue()};
         signature[0] = "java.lang.Object";
         String auditArgInfo = null;
         if (prop != null) {
            encrypted = (Boolean)prop.getValue("encrypted");
            sensitive = (Boolean)prop.getValue("sensitive");
            signature[0] = prop.getPropertyType().getName();
         }

         if (encrypted != null && encrypted || sensitive != null && sensitive) {
            operation = "setEncrypted";
            auditArgInfo = "1";
         }

         this.isAccessAllowedInvoke(objectName, operation, attr, parameters, signature, auditArgInfo, oldAttr, this.getBeanDescriptor(objectName), prop);
      } else if (!this.isWLSMBean(objectName) && this.isCommoMBean(objectName)) {
         SecurityHelper.isAccessAllowedCommo(objectName, ActionType.WRITE, attr, "setAttribute", this.getBeanDescriptor(objectName), prop);
      } else {
         SecurityHelper.isAccessAllowed(objectName, ActionType.WRITE, attr, "setAttribute", this.getBeanDescriptor(objectName), prop);
      }

   }

   private void checkSetSecurity(ObjectName objectName, AttributeList attributeList) {
      this.initDefaultPolicies();
      boolean isCommo = true;
      if (this.isWLSMBean(objectName) || !this.isCommoMBean(objectName)) {
         isCommo = false;
      }

      synchronized(attributeList) {
         Iterator i = attributeList.iterator();

         while(i.hasNext()) {
            try {
               Object nextAttribute = i.next();
               if (!(nextAttribute instanceof Attribute)) {
                  throw new RuntimeException("AttributeList must contain instances of Attribute");
               }

               Attribute attribute = (Attribute)nextAttribute;
               if (this.useSecurityFramework) {
                  PropertyDescriptor prop = this.getPropertyDescriptor(objectName, attribute.getName());
                  String operation = "set";
                  Boolean encrypted = null;
                  Boolean sensitive = null;
                  if (prop != null) {
                     encrypted = (Boolean)prop.getValue("encrypted");
                     sensitive = (Boolean)prop.getValue("sensitive");
                  }

                  if (encrypted != null && encrypted || sensitive != null && sensitive) {
                     operation = "setEncrypted";
                  }

                  this.isAccessAllowed(objectName, operation, attribute.getName(), this.getBeanDescriptor(objectName), prop);
               } else if (!isCommo) {
                  SecurityHelper.isAccessAllowed(objectName, ActionType.WRITE, attribute.getName(), "setAttributes", this.getBeanDescriptor(objectName), this.getPropertyDescriptor(objectName, attribute.getName()));
               } else {
                  SecurityHelper.isAccessAllowedCommo(objectName, ActionType.WRITE, attribute.getName(), "setAttributes", this.getBeanDescriptor(objectName), this.getPropertyDescriptor(objectName, attribute.getName()));
               }
            } catch (AttributeNotFoundException var13) {
               i.remove();
            } catch (NoAccessRuntimeException var14) {
               i.remove();
            }
         }

      }
   }

   private void checkInvokeSecurity(ObjectName objectName, String operationName, Object[] objects, String[] strings, MethodDescriptor methodDescriptor) {
      this.initDefaultPolicies();
      Object mbean = this.wlsMBeanServer.lookupObject(objectName);
      String role = null;
      if (mbean != null && mbean instanceof WLSModelMBean) {
         WLSModelMBean modelMBean = (WLSModelMBean)mbean;
         role = modelMBean.getRole(operationName, objects, strings);
      } else if (methodDescriptor != null) {
         role = (String)methodDescriptor.getValue("role");
      }

      String auditArgInfo;
      if (role != null && role.equals("finder")) {
         if (this.useSecurityFramework) {
            auditArgInfo = null;
            if (methodDescriptor != null) {
               auditArgInfo = (String)methodDescriptor.getValue("wls:auditProtectedArgs");
            }

            this.isAccessAllowedInvoke(objectName, "find", operationName, objects, strings, auditArgInfo, (Object)null, this.getBeanDescriptor(objectName), methodDescriptor);
         } else if (!this.isWLSMBean(objectName) && this.isCommoMBean(objectName)) {
            SecurityHelper.isAccessAllowedCommo(objectName, ActionType.FIND, operationName, "invoke", this.getBeanDescriptor(objectName), methodDescriptor);
         } else {
            SecurityHelper.isAccessAllowed(objectName, ActionType.FIND, operationName, "invoke", this.getBeanDescriptor(objectName), methodDescriptor);
         }

      } else {
         if (this.useSecurityFramework) {
            auditArgInfo = null;
            if (methodDescriptor != null) {
               auditArgInfo = (String)methodDescriptor.getValue("wls:auditProtectedArgs");
            }

            this.isAccessAllowedInvoke(objectName, "invoke", operationName, objects, strings, auditArgInfo, (Object)null, this.getBeanDescriptor(objectName), methodDescriptor);
         } else if (!this.isWLSMBean(objectName) && this.isCommoMBean(objectName)) {
            SecurityHelper.isAccessAllowedCommo(objectName, ActionType.EXECUTE, operationName, "invoke", this.getBeanDescriptor(objectName), methodDescriptor);
         } else {
            SecurityHelper.isAccessAllowed(objectName, ActionType.EXECUTE, operationName, "invoke", this.getBeanDescriptor(objectName), methodDescriptor);
         }

      }
   }

   private boolean isCommoMBean(ObjectName objectName) {
      Object mbean = this.wlsMBeanServer.lookupObject(objectName);
      if (mbean != null && mbean instanceof WLSModelMBean) {
         WLSModelMBean modelMBean = (WLSModelMBean)mbean;
         return StandardInterface.class.isAssignableFrom(modelMBean.getManagedResourceClass());
      } else {
         return false;
      }
   }

   private boolean isWLSMBean(ObjectName objectName) {
      if (objectName == null) {
         return false;
      } else if (objectName instanceof WebLogicObjectName) {
         return true;
      } else {
         return WLSObjectNameManager.isBEADomain(objectName.getDomain());
      }
   }

   private BeanDescriptor getBeanDescriptor(ObjectName objectName) {
      if (objectName == null) {
         return null;
      } else {
         Object mbean = this.wlsMBeanServer.lookupObject(objectName);
         if (mbean != null && mbean instanceof WLSModelMBean) {
            WLSModelMBean modelMBean = (WLSModelMBean)mbean;
            BeanInfo beanInfo = modelMBean.getBeanInfo();
            if (beanInfo != null) {
               return beanInfo.getBeanDescriptor();
            }
         }

         String type = objectName.getKeyProperty("Type");
         return type != null ? SecurityHelper.getBeanDescriptor(type) : null;
      }
   }

   private PropertyDescriptor getPropertyDescriptor(ObjectName objectName, String attr) throws AttributeNotFoundException {
      if (objectName == null) {
         return null;
      } else {
         Object mbean = this.wlsMBeanServer.lookupObject(objectName);
         if (mbean != null && mbean instanceof WLSModelMBean) {
            WLSModelMBean modelMBean = (WLSModelMBean)mbean;
            return modelMBean.getPropertyDescriptorForAttribute(attr);
         } else {
            String type = objectName.getKeyProperty("Type");
            return type != null ? SecurityHelper.getPropertyDescriptor(type, attr) : null;
         }
      }
   }

   private MethodDescriptor getMethodDescriptor(ObjectName objectName, String method, String[] sig) {
      Object mbean = this.wlsMBeanServer.lookupObject(objectName);
      if (mbean != null && mbean instanceof WLSModelMBean) {
         WLSModelMBean modelMBean = (WLSModelMBean)mbean;
         return modelMBean.getMethodDescriptor(method, sig);
      } else {
         String type = objectName.getKeyProperty("Type");
         return type != null ? SecurityHelper.getMethodDescriptor(type, method) : null;
      }
   }

   private void initDefaultPolicies() {
      if (this.useSecurityFramework) {
         try {
            DefaultJMXPolicyManager.init();
         } catch (ConsumptionException var2) {
            throw new RuntimeException(var2);
         }
      }

   }

   private void isAccessAllowed(ObjectName objectName, String operation, String target, FeatureDescriptor parentDescriptor, FeatureDescriptor childDescriptor) throws NoAccessRuntimeException {
      if (this.isWLSMBean(objectName) || this.isCommoMBean(objectName) || objectName == null || objectName.getKeyProperty("Type") != null) {
         String beanType = this.getBeanType(objectName);
         String app = this.getAppName(objectName);
         JMXResource resource = new JMXResource(operation, app, beanType, target);
         AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(kernelId);
         ContextHandler ctxHdlr = SecurityHelper.getResourceContextHandler(objectName, new JMXContextHandler(objectName), parentDescriptor, childDescriptor, operation);
         if (!this.authorizer.isAccessAllowed(subject, resource, ctxHdlr)) {
            String errorMsg = "Access not allowed for subject: " + subject + ", on Resource " + beanType + " Operation: " + operation + " , Target: " + target;
            throw new NoAccessRuntimeException(errorMsg);
         }
      }
   }

   private void isAccessAllowedInvoke(ObjectName objectName, String operation, String target, Object[] parameters, String[] signature, String auditProtectedArgInfo, Object oldValue, FeatureDescriptor parentDescriptor, FeatureDescriptor childDescriptor) throws NoAccessRuntimeException {
      if (this.isWLSMBean(objectName) || this.isCommoMBean(objectName) || objectName == null || objectName.getKeyProperty("Type") != null) {
         String beanType = this.getBeanType(objectName);
         String app = this.getAppName(objectName);
         JMXResource resource = new JMXResource(operation, app, beanType, target);
         AuthenticatedSubject subject = SecurityServiceManager.getCurrentSubject(kernelId);
         ContextHandler ctxHdlr = SecurityHelper.getResourceContextHandler(objectName, new JMXContextHandler(objectName, parameters, signature, auditProtectedArgInfo, oldValue), parentDescriptor, childDescriptor, operation);
         if (!this.authorizer.isAccessAllowed(subject, resource, ctxHdlr)) {
            String errorMsg = "Access not allowed for subject: " + subject + ", on Resource " + beanType + " Operation: " + operation + " , Target: " + target;
            throw new NoAccessRuntimeException(errorMsg);
         }
      }
   }

   private String getBeanType(ObjectName objectName) {
      if (objectName == null) {
         return null;
      } else {
         Object mbean = this.wlsMBeanServer.lookupObject(objectName);
         BeanInfo beanClass;
         if (mbean != null && mbean instanceof WLSModelMBean) {
            WLSModelMBean modelMBean = (WLSModelMBean)mbean;
            beanClass = modelMBean.getBeanInfo();
            if (beanClass != null) {
               BeanDescriptor beanDescriptor = beanClass.getBeanDescriptor();
               if (beanDescriptor != null) {
                  return (String)beanDescriptor.getValue("interfaceclassname");
               }
            }
         }

         String type = objectName.getKeyProperty("Type");
         if (type != null) {
            beanClass = null;
            String beanClass;
            if (type.endsWith("Runtime")) {
               beanClass = "weblogic.management.runtime." + type + "MBean";
            } else {
               beanClass = "weblogic.management.configuration." + type + "MBean";
            }

            try {
               Class cls = Class.forName(beanClass);
               return beanClass;
            } catch (Exception var6) {
            }
         }

         return type;
      }
   }

   private String getAppName(ObjectName objectName) {
      if (objectName == null) {
         return null;
      } else {
         for(int i = 0; i < APP_SCOPED_TYPES.length; ++i) {
            String appKey = objectName.getKeyProperty(APP_SCOPED_TYPES[i]);
            if (appKey != null) {
               return appKey;
            }

            String type = objectName.getKeyProperty("Type");
            if (APP_SCOPED_TYPES[i].equals(type)) {
               return objectName.getKeyProperty("Name");
            }
         }

         String path = objectName.getKeyProperty("Path");
         if (path != null) {
            int start = path.indexOf("[");
            int end = path.indexOf("]");
            if (start != -1 && end != -1 && start < end) {
               return path.substring(start + 1, end);
            } else {
               return objectName.getKeyProperty("Name");
            }
         } else {
            return null;
         }
      }
   }

   private void checkForBEADomain(ObjectName objectName) {
      if (objectName != null && BEA_DOMAIN.equals(objectName.getDomain())) {
         Loggable loggable = JMXLogger.logMBeanRegistrationFailedLoggable(objectName.getCanonicalName());
         throw new NoAccessRuntimeException(loggable.getMessage());
      }
   }

   private void checkMLetCreation(String className) throws NoAccessRuntimeException {
      if (className != null && className.startsWith("javax.management.loading")) {
         if (!ManagementService.getRuntimeAccess(kernelId).getDomain().getJMX().isManagementAppletCreateEnabled()) {
            throw new NoAccessRuntimeException("Creation of MLet MBeans is not allowed based on the ManagementAppletCreateEnabled attribute value.");
         }
      }
   }
}
