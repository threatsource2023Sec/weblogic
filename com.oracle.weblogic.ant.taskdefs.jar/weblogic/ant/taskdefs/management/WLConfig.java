package weblogic.ant.taskdefs.management;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.Descriptor;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.ReflectionException;
import javax.management.RuntimeMBeanException;
import javax.management.modelmbean.ModelMBeanInfoSupport;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.naming.NamingException;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import weblogic.management.WebLogicMBean;
import weblogic.management.WebLogicObjectName;
import weblogic.management.jmx.MBeanServerInvocationHandler;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.mbeanservers.edit.EditServiceMBean;
import weblogic.management.security.ProviderMBean;
import weblogic.management.security.RealmMBean;
import weblogic.management.security.audit.AuditorMBean;
import weblogic.management.security.authentication.AuthenticationProviderMBean;
import weblogic.management.security.authorization.AdjudicatorMBean;
import weblogic.management.security.authorization.AuthorizerMBean;
import weblogic.management.security.authorization.RoleMapperMBean;
import weblogic.management.security.credentials.CredentialMapperMBean;
import weblogic.management.security.pk.CertPathProviderMBean;
import weblogic.security.UserConfigFileManager;
import weblogic.security.UsernameAndPassword;
import weblogic.utils.StringUtils;
import weblogic.utils.TypeConversionUtils;

public class WLConfig extends Task {
   private static String JNDI = "/jndi/";
   private static String RUNTIME_MBEANSERVER = "weblogic.management.mbeanservers.runtime";
   private static String EDIT_SERVICE;
   private static String EDIT_MBEANSERVER;
   private static String DOMAINRUNTIME_MBEANSERVER;
   private static String[][] baseProviderTypes;
   private static final String[] CREATOR_SIGNATURE;
   private static final String[] DESTROY_SIGNATURE;
   private String adminurl;
   private String username;
   private String password;
   private String userConfigFile;
   private String userKeyFile;
   private ArrayList commands = new ArrayList();
   private boolean failOnError = true;
   private final int EDIT = 0;
   private final int DOMAINRUNTIME = 1;
   private final int RUNTIME = 2;
   private final String[] mbeanServerNames = new String[]{"edit", "domain runtime", "runtime"};
   private final String[] mbeanServerUrls;
   private final int MBS_COUNT;
   private MBeanServerConnection[] jmxConnection;
   private JMXConnector[] jmxConnector;
   private boolean editStarted;
   String domainName;

   public WLConfig() {
      this.mbeanServerUrls = new String[]{EDIT_MBEANSERVER, DOMAINRUNTIME_MBEANSERVER, RUNTIME_MBEANSERVER};
      this.MBS_COUNT = this.mbeanServerNames.length;
      this.jmxConnection = new MBeanServerConnection[this.MBS_COUNT];
      this.jmxConnector = new JMXConnector[this.MBS_COUNT];
      this.editStarted = false;
      this.domainName = null;
   }

   public void setFailOnError(boolean failOnError) {
      this.failOnError = failOnError;
   }

   public void setUserName(String username) {
      this.username = username;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public void setUrl(String adminurl) {
      this.adminurl = adminurl;
   }

   public void addCreate(MBeanCreateCommand mbcc) {
      this.commands.add(mbcc);
   }

   public void addDelete(MBeanDeleteCommand mbdc) {
      this.commands.add(mbdc);
   }

   public void addSet(MBeanSetCommand mbsc) {
      this.commands.add(mbsc);
   }

   public void addGet(MBeanGetCommand mbgc) {
      this.commands.add(mbgc);
   }

   public void addQuery(MBeanQueryCommand mbqc) {
      this.commands.add(mbqc);
   }

   public void setUserConfigFile(String configFile) {
      this.userConfigFile = configFile;
   }

   public void setUserKeyFile(String keyFile) {
      this.userKeyFile = keyFile;
   }

   public void addInvoke(MBeanInvokeCommand mbic) {
      this.commands.add(mbic);
   }

   public void execute() throws BuildException {
      Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
      this.reset();
      boolean hasError = false;
      Iterator cmds = this.commands.iterator();
      boolean var9 = false;

      MBeanServerConnection mbs;
      label148: {
         label149: {
            try {
               try {
                  var9 = true;
                  this.executeCommands(cmds, (ObjectName)null);
                  var9 = false;
                  break label148;
               } catch (BuildException var10) {
                  hasError = true;
                  this.log(var10.toString());
                  if (this.failOnError) {
                     throw var10;
                  }
               } catch (Throwable var11) {
                  hasError = true;
                  this.log(var11.toString());
                  if (this.failOnError) {
                     throw new BuildException(var11);
                  }

                  var9 = false;
                  break label149;
               }

               var9 = false;
            } finally {
               if (var9) {
                  if (this.editStarted) {
                     MBeanServerConnection mbs = this.getMbsEdit();
                     if (!hasError) {
                        this.activate();
                     } else {
                        this.rollback();
                     }
                  }

                  this.closeCachedMBeanServerConnectors();
               }
            }

            if (this.editStarted) {
               mbs = this.getMbsEdit();
               if (!hasError) {
                  this.activate();
               } else {
                  this.rollback();
               }
            }

            this.closeCachedMBeanServerConnectors();
            return;
         }

         if (this.editStarted) {
            mbs = this.getMbsEdit();
            if (!hasError) {
               this.activate();
            } else {
               this.rollback();
            }
         }

         this.closeCachedMBeanServerConnectors();
         return;
      }

      if (this.editStarted) {
         mbs = this.getMbsEdit();
         if (!hasError) {
            this.activate();
         } else {
            this.rollback();
         }
      }

      this.closeCachedMBeanServerConnectors();
   }

   public void executeCommands(Iterator cmds, ObjectName parent) throws BuildException {
      while(cmds.hasNext()) {
         MBeanCommand cmd = (MBeanCommand)cmds.next();
         switch (cmd.getCommandType()) {
            case 1:
               this.invokeCreateCommand(parent, (MBeanCreateCommand)cmd);
               break;
            case 2:
               this.invokeDeleteCommand((MBeanDeleteCommand)cmd);
               break;
            case 3:
               this.invokeSetCommand((MBeanSetCommand)cmd, (ObjectName)null);
               break;
            case 4:
               this.invokeGetCommand((MBeanGetCommand)cmd, (MBeanServerConnection)null);
               break;
            case 5:
               this.invokeQueryCommand((MBeanQueryCommand)cmd);
            case 6:
            default:
               break;
            case 7:
               this.invokeInvokeCommand((MBeanInvokeCommand)cmd, (ObjectName)null);
         }
      }

   }

   private boolean isFullJavaClassName(String name) {
      return name.indexOf(".") > 0;
   }

   private void invokeCreateCommand(ObjectName parent, MBeanCreateCommand mbcc) {
      if (mbcc.getType() == null) {
         this.handleException("Type not specified for create command");
      } else {
         MBeanServerConnection edit = this.getMbsEdit();
         ObjectName mbeanName = null;
         this.startEdit();
         if (!this.isFullJavaClassName(mbcc.getType())) {
            ObjectName parentName = parent == null ? this.getDomain(edit) : parent;
            String createMethodName = "create" + mbcc.getType();
            Object[] params = new String[]{mbcc.getName()};

            try {
               mbeanName = (ObjectName)edit.invoke(parentName, createMethodName, params, CREATOR_SIGNATURE);
               this.log("created mbean " + mbeanName, 3);
            } catch (InstanceNotFoundException var10) {
               this.handleException("Unable to create new bean of type " + mbcc.getType() + " for " + parentName + ":parent not found", var10);
               return;
            } catch (MBeanException var11) {
               this.handleException("Unable to create new bean of type " + mbcc.getType() + " for " + parentName + ":unexpected exception", var11);
               return;
            } catch (RuntimeMBeanException var12) {
               this.handleException("Unable to create new bean of type " + mbcc.getType() + " for " + parentName + ":unexpected exception", var12);
               return;
            } catch (IOException var13) {
               this.handleException("Unable to create new bean of type " + mbcc.getType() + " for " + parentName + ":unexpected exception", var13);
               return;
            } catch (ReflectionException var14) {
               if (mbcc.getType() != null && mbcc.getType().endsWith("Runtime")) {
                  this.handleException("Explicit create of a RuntimeMBean is not allowed");
               } else {
                  this.handleException("Unable to create new bean with type " + mbcc.getType() + ", and parent mbean " + parentName + ":missing create method", var14);
               }

               return;
            }
         } else {
            try {
               mbeanName = this.createSecurityBean(edit, mbcc.getName(), mbcc.getType(), mbcc.getRealm());
               this.log("created security mbean: " + mbeanName, 3);
            } catch (Exception var9) {
               this.handleException("Unable to create security mbean with type " + mbcc.getType(), var9);
               return;
            }
         }

         if (mbcc.getProperty() != null) {
            this.getProject().setProperty(mbcc.getProperty(), mbeanName.toString());
         }

         Iterator setcmds = mbcc.getSetCommands();

         while(setcmds.hasNext()) {
            MBeanSetCommand mbsc = (MBeanSetCommand)setcmds.next();
            mbsc.setMBean(mbeanName.toString());
            this.invokeSetCommand(mbsc, mbeanName);
         }

         Iterator invokecmds = mbcc.getInvokeCommands();

         while(invokecmds.hasNext()) {
            MBeanInvokeCommand mbic = (MBeanInvokeCommand)invokecmds.next();
            mbic.setMBean(mbeanName.toString());
            this.invokeInvokeCommand(mbic, mbeanName);
         }

         Iterator createcmds = mbcc.getCreateCommands();
         this.executeCommands(createcmds, mbeanName);
      }
   }

   private void invokeInvokeCommand(MBeanInvokeCommand mbic, ObjectName oname) throws BuildException {
      if (this.isProperty(mbic.getMBean())) {
         mbic.setMBean(this.getRequiredProperty(mbic.getMBean()));
      }

      if (mbic.getType() == null && mbic.getMBean() == null) {
         this.handleException("MBean name or type not specified for invoke command");
      } else if (mbic.getMethodName() == null) {
         this.handleException("Method name not specified for invoke command");
      } else {
         if (mbic.getArguments() == null) {
            mbic.setArguments("");
         }

         MBeanServerConnection mbs = this.getMbsEdit();
         Set names = null;
         ObjectName beanName;
         if (mbic.getMBean() != null) {
            beanName = null;
            if (oname != null) {
               beanName = oname;
            } else {
               try {
                  beanName = new ObjectName(mbic.getMBean());
                  beanName = this.replaceDefaultDomainName(beanName);
               } catch (MalformedObjectNameException var19) {
                  this.handleException("Invalid MBean name for set command: " + mbic.getMBean());
                  return;
               }
            }

            names = new HashSet();
            ((Set)names).add(beanName);
         } else {
            try {
               beanName = new ObjectName("*:Type=" + mbic.getType() + ",*");
            } catch (MalformedObjectNameException var18) {
               this.handleException("Invalid mbean type: " + mbic.getType());
               return;
            }

            try {
               names = mbs.queryNames(beanName, (QueryExp)null);
            } catch (IOException var17) {
               this.handleException("invoke mbean with type: " + mbic.getType() + " failed.", var17);
               return;
            }
         }

         String[] argsInStr = StringUtils.splitCompletely(mbic.getArguments(), " ");
         Iterator var6 = ((Set)names).iterator();

         while(var6.hasNext()) {
            ObjectName name = (ObjectName)var6.next();
            MBeanInfo mInfo = null;

            try {
               mInfo = mbs.getMBeanInfo(name);
            } catch (Exception var20) {
               continue;
            }

            MBeanOperationInfo oInfo = null;
            MBeanOperationInfo[] oInfos = mInfo.getOperations();
            Object[] params = null;
            String[] signature = null;

            for(int i = 0; i < oInfos.length; ++i) {
               if (oInfos[i].getName().equalsIgnoreCase(mbic.getMethodName())) {
                  MBeanParameterInfo[] paramInfos = oInfos[i].getSignature();
                  if (argsInStr.length == paramInfos.length) {
                     signature = new String[paramInfos.length];

                     int k;
                     for(k = 0; k < paramInfos.length; ++k) {
                        signature[k] = paramInfos[k].getType();
                     }

                     params = new Object[signature.length];

                     try {
                        for(k = 0; k < signature.length; ++k) {
                           params[k] = this.convertObject(argsInStr[k], signature[k]);
                        }
                     } catch (Exception var21) {
                        continue;
                     }

                     oInfo = oInfos[i];
                  }
               }
            }

            if (oInfo == null) {
               this.log("warning: operation " + mbic.getMethodName() + " not found on mbean " + name + " with parameters \"" + mbic.getArguments() + "\"");
            } else {
               this.log("invoke command: operation " + mbic.getMethodName() + " on mbean " + name + " with parameters \"" + mbic.getArguments() + "\"", 3);

               try {
                  if ("Security".equals(name.getDomain())) {
                     this.getMbsRuntime().invoke(name, mbic.getMethodName(), params, signature);
                  } else {
                     this.startEdit();
                     mbs.invoke(name, mbic.getMethodName(), params, signature);
                  }
               } catch (Exception var16) {
                  this.handleException("Invoke operation " + mbic.getMethodName() + " on mbean " + name + " with parameters \"" + mbic.getArguments() + "\" error" + var16);
               }
            }
         }

      }
   }

   private void invokeSetCommand(MBeanSetCommand mbsc, ObjectName oname) throws BuildException {
      if (this.isProperty(mbsc.getValue())) {
         mbsc.setValue(this.getRequiredProperty(mbsc.getValue()));
      }

      if (oname == null && this.isProperty(mbsc.getMBean())) {
         mbsc.setMBean(this.getRequiredProperty(mbsc.getMBean()));
      }

      if (oname == null && mbsc.getMBean() == null) {
         this.handleException("MBean not specified for set command");
      } else if (mbsc.getAttribute() == null) {
         this.handleException("Attribute not specified for set command");
      } else if (mbsc.getValue() == null) {
         this.handleException("Value not specified for set command");
      } else {
         ObjectName beanName = null;
         if (oname == null) {
            try {
               beanName = new ObjectName(mbsc.getMBean());
               beanName = this.replaceDefaultDomainName(beanName);
            } catch (MalformedObjectNameException var5) {
               throw new BuildException(var5);
            }
         } else {
            beanName = oname;
         }

         this.invokeSetCommandInternal(beanName, mbsc.getAttribute(), mbsc.getValue());
      }
   }

   private void invokeSetCommandInternal(ObjectName oname, String attrName, String attrValue) {
      MBeanServerConnection edit = this.getMbsEdit();
      MBeanInfo minfo = null;

      try {
         minfo = edit.getMBeanInfo(oname);
      } catch (Exception var10) {
         this.handleException("set command error on mbean " + oname + " with attr " + attrName, var10);
         return;
      }

      String intfName = this.getMBeanInterfaceName(minfo);
      Class beanIntf = null;

      try {
         beanIntf = intfName == null ? null : Class.forName(intfName);
      } catch (ClassNotFoundException var9) {
         this.handleException("set command error on mbean " + oname + ": interface class " + intfName + " not found", var9);
         return;
      }

      if (beanIntf != null && ProviderMBean.class.isAssignableFrom(beanIntf)) {
         this.setOnProviderBean(oname, beanIntf, attrName, attrValue);
      } else if (beanIntf != null && RealmMBean.class.isAssignableFrom(beanIntf)) {
         this.setOnRealm(oname, attrName, attrValue);
      } else {
         this.setOnBean(oname, attrName, attrValue);
      }

      this.log("set command succeed: mbean " + oname + ", attr " + attrName + ", new value " + attrValue, 3);
   }

   private String getMBeanInterfaceName(MBeanInfo minfo) {
      Descriptor desc = null;
      if (minfo instanceof ModelMBeanInfoSupport) {
         try {
            desc = ((ModelMBeanInfoSupport)minfo).getMBeanDescriptor();
         } catch (MBeanException var4) {
            desc = null;
         }
      }

      String intfName = desc == null ? null : (String)desc.getFieldValue("interfaceclassname");
      return intfName;
   }

   private void setOnProviderBean(ObjectName oname, Class beanIntf, String attrName, String attrValue) {
      try {
         MBeanServerConnection edit = this.getMbsEdit();
         String providerName = (String)edit.getAttribute(oname, "Name");
         ObjectName realm = (ObjectName)edit.getAttribute(oname, "Realm");
         String lookup = this.getProviderMethod(beanIntf, "lookup");
         ObjectName bean;
         if (lookup.startsWith("get")) {
            bean = (ObjectName)edit.getAttribute(realm, lookup.substring(3));
            if (bean == null) {
               throw new BuildException("Could not find " + providerName);
            }
         } else {
            Object[] params = new Object[]{providerName};
            String[] ptypes = new String[]{"java.lang.String"};
            bean = (ObjectName)edit.invoke(realm, lookup, params, ptypes);
            if (bean == null) {
               throw new BuildException("Could not find " + providerName);
            }
         }

         this.setOnBean(bean, attrName, attrValue);
      } catch (Exception var12) {
         this.handleException("mbean set command error", var12);
      }
   }

   private String getProviderMethod(Class beanIntf, String prefix) {
      if (AuthenticationProviderMBean.class.isAssignableFrom(beanIntf)) {
         return prefix + "AuthenticationProvider";
      } else if (AdjudicatorMBean.class.isAssignableFrom(beanIntf)) {
         if ("lookup".equals(prefix)) {
            prefix = "get";
         }

         return prefix + "Adjudicator";
      } else if (AuditorMBean.class.isAssignableFrom(beanIntf)) {
         return prefix + "Auditor";
      } else if (AuthorizerMBean.class.isAssignableFrom(beanIntf)) {
         return prefix + "Authorizer";
      } else if (RoleMapperMBean.class.isAssignableFrom(beanIntf)) {
         return prefix + "RoleMapper";
      } else if (CredentialMapperMBean.class.isAssignableFrom(beanIntf)) {
         return prefix + "CredentialMapper";
      } else if (CertPathProviderMBean.class.isAssignableFrom(beanIntf)) {
         return prefix + "CertPathProvider";
      } else {
         throw new BuildException("unknown provider type: " + beanIntf.getName());
      }
   }

   private void setOnBean(ObjectName bean, String attrName, String attrValue) throws BuildException {
      try {
         MBeanServerConnection edit = this.getMbsEdit();
         MBeanInfo mbi = edit.getMBeanInfo(bean);
         MBeanAttributeInfo[] ai = mbi.getAttributes();
         MBeanAttributeInfo attrInfo = null;

         for(int i = 0; i < ai.length; ++i) {
            if (ai[i].getName().equals(attrName)) {
               attrInfo = ai[i];
               break;
            }
         }

         if (attrInfo == null) {
            throw new BuildException("No such attribute: " + attrName);
         } else {
            String type = attrInfo.getType();
            Attribute attr = new Attribute(attrName, this.convertObject(attrValue, type));
            this.startEdit();
            edit.setAttribute(bean, attr);
         }
      } catch (Exception var10) {
         throw new BuildException(var10);
      }
   }

   private void setOnRealm(ObjectName realm, String attrName, String attrValue) {
      try {
         MBeanServerConnection edit = this.getMbsEdit();
         MBeanInfo mbi = edit.getMBeanInfo(realm);
         MBeanAttributeInfo[] ai = mbi.getAttributes();
         MBeanAttributeInfo attrInfo = null;

         for(int i = 0; i < ai.length; ++i) {
            if (ai[i].getName().equals(attrName)) {
               attrInfo = ai[i];
               break;
            }
         }

         if (attrInfo == null) {
            throw new BuildException("set command on realm mbean " + realm + " error: attribute " + attrName + " not found.");
         } else {
            String type = attrInfo.getType();
            Attribute attr = new Attribute(attrName, this.convertObject(attrValue, type));
            this.startEdit();
            edit.setAttribute(realm, attr);
         }
      } catch (Exception var10) {
         this.handleException("set command on realm mbean " + realm + " error", var10);
      }
   }

   private void invokeGetCommand(MBeanGetCommand mbgc, MBeanServerConnection parentMbs) throws BuildException {
      if (mbgc.getAttribute() == null) {
         this.handleException("Attribute not specified in get command");
      } else if (mbgc.getProperty() == null) {
         this.handleException("Property not specified in get command");
      } else {
         if (this.isProperty(mbgc.getMBean())) {
            mbgc.setMBean(this.getRequiredProperty(mbgc.getMBean()));
         }

         try {
            ObjectName oname = new ObjectName(mbgc.getMBean());
            oname = this.replaceDefaultDomainName(oname);
            MBeanServerConnection conn = null;

            try {
               conn = this.getMBeanServer(mbgc.getMbeanserver());
               if (conn == null) {
                  conn = parentMbs == null ? this.getMbsEdit() : parentMbs;
               }
            } catch (BuildException var10) {
               this.log("invalid mbeanserver for get command on mbean " + mbgc.getMBean() + " with attr " + mbgc.getAttribute() + ":" + mbgc.getMbeanserver());
            }

            Object result = conn.getAttribute(oname, mbgc.getAttribute());
            String resultString = "";
            if (result != null) {
               if (!(result instanceof ObjectName[])) {
                  resultString = result.toString();
               } else {
                  ObjectName[] names = (ObjectName[])((ObjectName[])result);
                  StringBuffer buf = new StringBuffer();

                  for(int i = 0; i < names.length; ++i) {
                     if (i != 0) {
                        buf.append(";");
                     }

                     buf.append(names[i].toString());
                  }

                  resultString = buf.toString();
               }
            }

            this.getProject().setProperty(mbgc.getProperty(), resultString);
         } catch (MalformedObjectNameException var11) {
            this.handleException("Invalid MBean name for get command on mbean " + mbgc.getMBean(), var11);
         } catch (InstanceNotFoundException var12) {
            this.handleException("No such MBean: " + mbgc.getMBean());
         } catch (AttributeNotFoundException var13) {
            this.handleException("No such attribute: " + mbgc.getAttribute(), var13);
         } catch (MBeanException var14) {
            this.handleException("Error retrieving attribute: " + mbgc.getAttribute(), var14);
         } catch (ReflectionException var15) {
            this.handleException("Error retrieving attribute: " + mbgc.getAttribute(), var15);
         } catch (Exception var16) {
            this.handleException("Error retrieving attribute: " + mbgc.getAttribute(), var16);
         }

      }
   }

   private void invokeDeleteCommand(MBeanDeleteCommand mbdc) throws BuildException {
      if (this.isProperty(mbdc.getMBean())) {
         mbdc.setMBean(this.getRequiredProperty(mbdc.getMBean()));
      }

      MBeanServerConnection edit = this.getMbsEdit();
      ObjectName oname = null;

      try {
         oname = new ObjectName(mbdc.getMBean());
         oname = this.replaceDefaultDomainName(oname);
      } catch (MalformedObjectNameException var12) {
         throw new BuildException("Malformed Object Name: " + mbdc.getMBean());
      }

      MBeanInfo minfo = null;

      try {
         minfo = edit.getMBeanInfo(oname);
      } catch (InstanceNotFoundException var10) {
         throw new BuildException("delete command error: mbean " + mbdc.getMBean() + " not found.");
      } catch (Exception var11) {
         throw new BuildException("delete mbean " + mbdc.getMBean() + " error", var11);
      }

      String intfName = this.getMBeanInterfaceName(minfo);
      Class beanIntf = null;

      try {
         beanIntf = intfName == null ? null : Class.forName(intfName);
      } catch (ClassNotFoundException var9) {
         this.handleException("delete mbean " + mbdc.getMBean() + " error: interface class " + intfName + " not found");
         return;
      }

      if (beanIntf != null && WebLogicMBean.class.isAssignableFrom(beanIntf)) {
         this.destroyWebLogicBean(oname);
      } else if (beanIntf != null && ProviderMBean.class.isAssignableFrom(beanIntf)) {
         this.destroySecurityBean(oname, beanIntf);
      } else if (beanIntf != null && RealmMBean.class.isAssignableFrom(beanIntf)) {
         this.destroyRealmBean(oname);
      } else {
         this.startEdit();

         try {
            edit.unregisterMBean(oname);
            this.log("deleted mbean " + oname, 3);
         } catch (Exception var8) {
            this.handleException("delete mbean " + mbdc.getMBean() + " failed", var8);
         }
      }

   }

   private void destroyWebLogicBean(ObjectName oname) {
      MBeanServerConnection edit = this.getMbsEdit();

      try {
         ObjectName parent = (ObjectName)edit.getAttribute(oname, "Parent");
         String method = "destroy" + oname.getKeyProperty("Type");
         Object[] params = new Object[]{oname};
         String[] ptypes = new String[]{"javax.management.ObjectName"};
         this.startEdit();
         edit.invoke(parent, method, params, ptypes);
         this.log("deleted config mbean " + oname, 3);
      } catch (Exception var7) {
         throw new BuildException("Error removing config MBean " + oname, var7);
      }
   }

   private void destroySecurityBean(ObjectName oname, Class beanIntf) {
      MBeanServerConnection edit = this.getMbsEdit();
      String destroyMethod = this.getProviderMethod(beanIntf, "destroy");

      try {
         ObjectName realm = (ObjectName)edit.getAttribute(oname, "Realm");
         if (this.operationExist(edit, realm, destroyMethod, DESTROY_SIGNATURE)) {
            Object[] params = new Object[]{oname};
            String[] ptypes = new String[]{"javax.management.ObjectName"};
            this.startEdit();
            edit.invoke(realm, destroyMethod, params, ptypes);
         } else {
            if (!this.operationExist(edit, realm, destroyMethod, (String[])null)) {
               throw new BuildException("delete security mbean " + oname + " failed: operation " + destroyMethod + " not found.");
            }

            this.startEdit();
            edit.invoke(realm, destroyMethod, (Object[])null, (String[])null);
         }

         this.log("deleted security mbean " + oname, 3);
      } catch (BuildException var8) {
         throw var8;
      } catch (Exception var9) {
         throw new BuildException("delete security mbean " + oname + " error: " + var9.getMessage(), var9);
      }
   }

   private boolean operationExist(MBeanServerConnection mbs, ObjectName oname, String operName, String[] ptypes) {
      try {
         MBeanInfo mInfo = mbs.getMBeanInfo(oname);
         MBeanOperationInfo[] oInfos = mInfo.getOperations();

         for(int i = 0; i < oInfos.length; ++i) {
            if (oInfos[i].getName().equals(operName)) {
               MBeanParameterInfo[] paramInfos = oInfos[i].getSignature();
               ptypes = ptypes == null ? new String[0] : ptypes;
               if (ptypes.length == paramInfos.length) {
                  boolean found = true;
                  if (ptypes.length > 0) {
                     for(int j = 0; j < paramInfos.length; ++j) {
                        if (!ptypes[j].equals(paramInfos[j].getType())) {
                           found = false;
                           break;
                        }
                     }
                  }

                  if (found) {
                     return true;
                  }
               }
            }
         }
      } catch (Exception var11) {
      }

      return false;
   }

   private void destroyRealmBean(ObjectName oname) {
      MBeanServerConnection edit = this.getMbsEdit();
      ObjectName domainOName = this.getDomain(edit);

      try {
         ObjectName secCfgName = (ObjectName)edit.getAttribute(domainOName, "SecurityConfiguration");
         Object[] params = new Object[]{oname};
         String[] ptypes = new String[]{"javax.management.ObjectName"};
         this.startEdit();
         edit.invoke(secCfgName, "destroyRealm", params, ptypes);
         this.log("deleted realm mbean " + oname, 3);
      } catch (Exception var7) {
         throw new BuildException("delet realm mbean " + oname + " error", var7);
      }
   }

   private MBeanServerConnection getMBeanServer(String type) throws BuildException {
      if ("runtime".equalsIgnoreCase(type)) {
         return this.getMbsRuntime();
      } else if ("domainruntime".equalsIgnoreCase(type)) {
         return this.getMbsDomainRuntime();
      } else if ("edit".equalsIgnoreCase(type)) {
         return this.getMbsEdit();
      } else if (type != null && type.trim().length() != 0) {
         throw new BuildException("invalid mbeanserver name: " + type);
      } else {
         return null;
      }
   }

   private void invokeQueryCommand(MBeanQueryCommand mbqc) throws BuildException {
      ObjectName pattern = null;
      this.log("query pattern " + mbqc.getPattern(), 3);

      try {
         pattern = new ObjectName(mbqc.getPattern());
         pattern = this.replaceDefaultDomainName(pattern);
      } catch (MalformedObjectNameException var8) {
         this.handleException("Error in query pattern", var8);
         return;
      }

      MBeanServerConnection mbs = null;

      try {
         mbs = this.getMBeanServer(mbqc.getMbeanserver());
         if (mbs == null) {
            mbs = this.getMbsEdit();
         }
      } catch (BuildException var7) {
         this.handleException("invalid mbeanserver for query \"" + pattern + "\": " + mbqc.getMbeanserver());
      }

      Set mbeans = null;

      try {
         mbeans = mbs.queryNames(pattern, (QueryExp)null);
      } catch (IOException var6) {
         this.handleException("query command error", var6);
         return;
      }

      this.log("query result size is " + mbeans.size(), 3);
      StringBuffer propValue = new StringBuffer();
      this.processQueryNestedCommands(mbs, mbeans, mbqc, propValue);
      if (mbqc.getProperty() != null && !propValue.toString().equals("")) {
         this.getProject().setProperty(mbqc.getProperty(), propValue.toString());
      }

   }

   private void processQueryNestedCommands(MBeanServerConnection mbs, Set mbeans, MBeanQueryCommand mbqc, StringBuffer propValue) {
      Iterator it = mbeans.iterator();

      while(it.hasNext()) {
         ObjectName oname = (ObjectName)it.next();
         propValue.append(oname.toString());
         if (it.hasNext()) {
            propValue.append(";");
         }

         Iterator getcmds = mbqc.getGetCommands();

         while(getcmds.hasNext()) {
            MBeanGetCommand mbgc = (MBeanGetCommand)getcmds.next();
            mbgc.setMBean(oname.toString());
            this.invokeGetCommand(mbgc, mbs);
         }

         Iterator setcmds = mbqc.getSetCommands();

         while(setcmds.hasNext()) {
            MBeanSetCommand mbsc = (MBeanSetCommand)setcmds.next();
            mbsc.setMBean(oname.toString());
            this.invokeSetCommand(mbsc, oname);
         }

         if (mbqc.getCreateCommands().hasNext()) {
            MBeanInfo minfo = null;

            try {
               minfo = mbs.getMBeanInfo(oname);
            } catch (Exception var14) {
               this.handleException("error in nest create of query: " + mbqc.getPattern(), var14);
            }

            if (minfo != null) {
               String intfName = this.getMBeanInterfaceName(minfo);
               Class beanIntf = null;

               try {
                  beanIntf = intfName == null ? null : Class.forName(intfName);
               } catch (ClassNotFoundException var13) {
               }

               if (beanIntf != null && WebLogicMBean.class.isAssignableFrom(beanIntf)) {
                  Iterator createcmds = mbqc.getCreateCommands();
                  this.executeCommands(createcmds, oname);
               }
            }
         }

         MBeanDeleteCommand mbdc = mbqc.getDeleteCommand();
         if (mbdc != null) {
            mbdc.setMBean(oname.toString());
            this.invokeDeleteCommand(mbdc);
         }

         MBeanInvokeCommand mbic = mbqc.getInvokeCommand();
         if (mbic != null) {
            mbic.setMBean(oname.toString());
            this.invokeInvokeCommand(mbic, oname);
         }
      }

   }

   private JMXConnector getMBeanServerConnector(String URI) throws NamingException, BuildException {
      if (this.adminurl != null && this.adminurl.trim().length() != 0) {
         String[] parts = StringUtils.splitCompletely(this.adminurl, ":");
         if (parts.length != 3) {
            throw new BuildException("Invalid URL specified: " + URI);
         } else {
            String protocol = parts[0];
            String hostname = parts[1];
            String port = parts[2];
            if (hostname.startsWith("//")) {
               hostname = hostname.substring(2);
            }

            int portNum = true;

            int portNum;
            try {
               portNum = Integer.parseInt(port);
            } catch (Exception var10) {
               throw new BuildException("Invalid port specified: " + port);
            }

            try {
               JMXServiceURL serviceURL = new JMXServiceURL(protocol, hostname, portNum, JNDI + URI);
               if (this.username == null && this.password == null) {
                  UsernameAndPassword UAndP = null;
                  if (this.userConfigFile == null && this.userKeyFile == null) {
                     UAndP = UserConfigFileManager.getUsernameAndPassword("weblogic.management");
                  } else {
                     UAndP = UserConfigFileManager.getUsernameAndPassword(this.userConfigFile, this.userKeyFile, "weblogic.management");
                  }

                  if (UAndP != null) {
                     this.username = UAndP.getUsername();
                     this.password = new String(UAndP.getPassword());
                  }
               }

               Hashtable h = new Hashtable();
               h.put("java.naming.security.principal", this.username);
               h.put("java.naming.security.credentials", this.password);
               h.put("jmx.remote.protocol.provider.pkgs", "weblogic.management.remote");
               JMXConnector connector = JMXConnectorFactory.connect(serviceURL, h);
               return connector;
            } catch (IOException var11) {
               this.handleException("Failed to connect", var11);
               return null;
            }
         }
      } else {
         throw new BuildException("No URL specified. Please specify valid URL");
      }
   }

   public MBeanServerConnection getMbsEdit() {
      return this.getCachedMBeanServerConnection(0);
   }

   public MBeanServerConnection getMbsDomainRuntime() {
      return this.getCachedMBeanServerConnection(1);
   }

   public MBeanServerConnection getMbsRuntime() {
      return this.getCachedMBeanServerConnection(2);
   }

   private MBeanServerConnection getCachedMBeanServerConnection(int id) {
      if (id < 0 || id >= this.MBS_COUNT) {
         this.handleException("Internal error: invalid mbeanserver id " + id);
      }

      if (this.jmxConnection[id] == null) {
         try {
            this.jmxConnector[id] = this.getMBeanServerConnector(this.mbeanServerUrls[id]);
            this.jmxConnection[id] = this.jmxConnector[id].getMBeanServerConnection();
         } catch (Exception var4) {
            String name = this.mbeanServerNames[id];
            this.handleException("failed to connect to " + name + " mbean server", var4);
            return null;
         }
      }

      return this.jmxConnection[id];
   }

   private void closeCachedMBeanServerConnectors() {
      for(int i = 0; i < this.MBS_COUNT; ++i) {
         if (this.jmxConnector[i] != null) {
            try {
               this.jmxConnector[i].close();
            } catch (IOException var3) {
            }

            this.jmxConnector[i] = null;
            this.jmxConnection[i] = null;
         }
      }

   }

   private boolean isProperty(String value) {
      return value == null ? false : value.startsWith("${");
   }

   private String getProperty(String value) {
      if (value == null) {
         return null;
      } else {
         String propname = value.substring(2, value.length() - 1);
         return this.getProject().getProperty(propname);
      }
   }

   private String getRequiredProperty(String property) throws BuildException {
      if (property.indexOf(";") != -1) {
         StringBuffer multiProp = new StringBuffer();
         StringTokenizer tokenizer = new StringTokenizer(property, ";");
         int tokenCount = tokenizer.countTokens();
         int processCount = 0;

         while(tokenizer.hasMoreTokens()) {
            ++processCount;
            multiProp.append(this.getSingularRequiredProperty(tokenizer.nextToken()));
            if (processCount != tokenCount) {
               multiProp.append(";");
            }
         }

         return multiProp.toString();
      } else {
         return this.getSingularRequiredProperty(property);
      }
   }

   private String getSingularRequiredProperty(String property) throws BuildException {
      String propvalue = this.getProperty(property);
      if (propvalue != null) {
         return propvalue;
      } else {
         throw new BuildException("Property not set: " + property);
      }
   }

   private void handleException(String message) {
      this.handleException(message, (Throwable)null);
   }

   private void handleException(String message, Throwable cause) {
      String errormessage = cause == null ? message : message + ": " + cause;
      if (this.failOnError) {
         throw new BuildException(errormessage, cause);
      } else {
         this.log(errormessage, 0);
      }
   }

   private ObjectName getDomain(MBeanServerConnection conn) throws BuildException {
      try {
         ObjectName pattern = new ObjectName("com.bea:Type=Domain,*");
         return this.getSingletonObject(conn, pattern);
      } catch (Exception var3) {
         this.handleException("Error getting Domain", var3);
         return null;
      }
   }

   private ObjectName createSecurityBean(MBeanServerConnection conn, String name, String type, String realm) {
      if ("weblogic.management.security.Realm".equals(type)) {
         ObjectName result = this.createRealm(conn, name);
         return result;
      } else {
         return this.createProvider(conn, name, type, realm);
      }
   }

   private ObjectName createProvider(MBeanServerConnection conn, String name, String type, String realm) {
      ObjectName on = this.getRealm(conn, realm);

      for(int j = 0; j < baseProviderTypes.length; ++j) {
         String[] types;
         try {
            types = (String[])((String[])conn.getAttribute(on, baseProviderTypes[j][0]));
         } catch (Exception var14) {
            throw new BuildException("Error determining provider types " + baseProviderTypes[j][0], var14);
         }

         for(int i = 0; i < types.length; ++i) {
            if (types[i].equals(type)) {
               String createMethod = baseProviderTypes[j][1];
               Object[] params = new Object[]{name, type};
               String[] ptypes = new String[]{"java.lang.String", "java.lang.String"};

               try {
                  return (ObjectName)conn.invoke(on, createMethod, params, ptypes);
               } catch (Throwable var13) {
                  throw new BuildException("Error creating security mbean " + name, var13);
               }
            }
         }
      }

      throw new BuildException("Invalid security mbean type " + type);
   }

   private ObjectName createRealm(MBeanServerConnection conn, String name) {
      try {
         ObjectName on = this.getDomain(conn);
         on = (ObjectName)conn.getAttribute(on, "SecurityConfiguration");
         Object[] params = new Object[]{name};
         String[] ptypes = new String[]{"java.lang.String"};
         return (ObjectName)conn.invoke(on, "createRealm", params, ptypes);
      } catch (Throwable var6) {
         throw new BuildException("Error creating Realm " + name + ": " + var6.getMessage(), var6);
      }
   }

   private ObjectName getRealm(MBeanServerConnection conn, String realm) throws BuildException {
      try {
         ObjectName on = this.getDomain(conn);
         on = (ObjectName)conn.getAttribute(on, "SecurityConfiguration");
         if (realm == null) {
            on = (ObjectName)conn.getAttribute(on, "DefaultRealm");
         } else {
            Object[] params = new Object[]{realm};
            String[] ptypes = new String[]{"java.lang.String"};
            on = (ObjectName)conn.invoke(on, "lookupRealm", params, ptypes);
            if (on == null) {
               throw new BuildException("No such realm: " + realm);
            }
         }

         return on;
      } catch (BuildException var6) {
         throw var6;
      } catch (Exception var7) {
         throw new BuildException("Error getting realm " + realm, var7);
      }
   }

   protected ObjectName getSingletonObject(MBeanServerConnection conn, ObjectName pattern) throws BuildException {
      ObjectName result = null;

      try {
         Set s = conn.queryNames(pattern, (QueryExp)null);
         Iterator i = s.iterator();
         if (i.hasNext()) {
            result = (ObjectName)i.next();
         }

         return result;
      } catch (Exception var6) {
         this.handleException("Error getting object", var6);
         return null;
      }
   }

   private void startEdit() throws BuildException {
      if (!this.editStarted) {
         try {
            MBeanServerConnection conn = this.getMbsEdit();
            this.getConfigurationManagerMBean(conn).startEdit(0, 3600000);
            this.editStarted = true;
         } catch (Exception var2) {
            throw new BuildException("Failed to start edit: " + var2.getMessage(), var2);
         }
      }
   }

   private void activate() throws BuildException {
      try {
         MBeanServerConnection conn = this.getMbsEdit();
         this.getConfigurationManagerMBean(conn).activate(-1L);
         this.log("changes are activated.", 3);
      } catch (Exception var2) {
         throw new BuildException("Failed to activate: " + var2.getMessage(), var2);
      }
   }

   private void rollback() throws BuildException {
      try {
         MBeanServerConnection conn = this.getMbsEdit();
         this.getConfigurationManagerMBean(conn).undo();
         this.getConfigurationManagerMBean(conn).stopEdit();
         this.log("changes are rollbacked.", 3);
      } catch (Exception var2) {
         throw new BuildException("Failed to rollback: " + var2.getMessage(), var2);
      }
   }

   private ConfigurationManagerMBean getConfigurationManagerMBean(MBeanServerConnection conn) throws BuildException {
      return this.getEditServiceMBean(conn).getConfigurationManager();
   }

   private EditServiceMBean getEditServiceMBean(MBeanServerConnection conn) throws BuildException {
      ObjectName oname = this.getEditService(conn);
      return (EditServiceMBean)MBeanServerInvocationHandler.newProxyInstance(conn, oname);
   }

   private ObjectName getEditService(MBeanServerConnection conn) throws BuildException {
      try {
         ObjectName pattern = new ObjectName(EDIT_SERVICE);
         return this.getSingletonObject(conn, pattern);
      } catch (Exception var3) {
         this.handleException("Error getting EditService", var3);
         return null;
      }
   }

   private Object convertObject(String value, String type) throws BuildException {
      StringTokenizer tok = null;
      String actualType = null;
      Object[] retArrayValue = null;
      Object returnValue = null;
      boolean isArray;
      if (type.startsWith("[L") && type.endsWith(";")) {
         isArray = true;
         actualType = type.substring("[L".length(), type.lastIndexOf(";"));
         tok = new StringTokenizer(value, ";");
         if (actualType.startsWith("weblogic.management") && actualType.endsWith("MBean")) {
            retArrayValue = new ObjectName[tok.countTokens()];
         } else {
            try {
               Class cls = Class.forName(actualType);
               retArrayValue = (Object[])((Object[])Array.newInstance(cls, tok.countTokens()));
            } catch (NegativeArraySizeException var9) {
            } catch (ClassNotFoundException var10) {
               throw new BuildException(var10);
            }
         }
      } else {
         isArray = false;
         actualType = type;
         tok = new StringTokenizer(value, "");
      }

      for(int iIdx = 0; tok.hasMoreTokens(); ((Object[])retArrayValue)[iIdx++] = returnValue) {
         returnValue = this.getSingleObjectFromString(actualType, (String)tok.nextElement());
         if (!isArray) {
            return returnValue;
         }
      }

      return retArrayValue;
   }

   private Object getSingleObjectFromString(String propType, String value) throws IllegalArgumentException {
      if (propType.equals("int")) {
         Integer intVal = new Integer(value);
         return intVal;
      } else if (!propType.equals("java.util.Properties") && !propType.equals("java.util.Map")) {
         if (propType.equals("boolean")) {
            Boolean boolVal = new Boolean(value);
            return boolVal;
         } else if (propType.equals("long")) {
            Long longVal = new Long(value);
            return longVal;
         } else if (propType.startsWith("weblogic.management") && propType.endsWith("MBean")) {
            try {
               Object obj = new WebLogicObjectName(value);
               return obj;
            } catch (MalformedObjectNameException var8) {
               throw new IllegalArgumentException(var8.toString());
            }
         } else {
            try {
               Class cls = Class.forName(propType);
               Class[] sig = new Class[]{Class.forName("java.lang.String")};
               Constructor cons = cls.getConstructor(sig);
               String[] params = new String[]{value};
               Object objVal = cons.newInstance((Object[])params);
               return objVal;
            } catch (Exception var9) {
               throw new BuildException("Unable to convert the argument value " + value + " to class " + propType + ". " + var9);
            }
         }
      } else {
         Properties props = new Properties();
         TypeConversionUtils.stringToDictionary(value, props, ";");
         return props;
      }
   }

   private String getWlsDomainName() {
      if (this.domainName == null) {
         MBeanServerConnection edit = this.getMbsEdit();
         ObjectName domainOName = this.getDomain(edit);
         this.domainName = domainOName == null ? "" : domainOName.getKeyProperty("Name");
      }

      return this.domainName;
   }

   private ObjectName replaceDefaultDomainName(ObjectName oname) {
      if (!oname.isDomainPattern() && this.getWlsDomainName().equals(oname.getDomain())) {
         try {
            ObjectName newName = new ObjectName("com.bea" + oname.toString().substring(this.getWlsDomainName().length()));
            return newName;
         } catch (MalformedObjectNameException var3) {
         }
      }

      return oname;
   }

   private void reset() {
      this.domainName = null;
      this.editStarted = false;
   }

   static {
      EDIT_SERVICE = EditServiceMBean.OBJECT_NAME;
      EDIT_MBEANSERVER = "weblogic.management.mbeanservers.edit";
      DOMAINRUNTIME_MBEANSERVER = "weblogic.management.mbeanservers.domainruntime";
      baseProviderTypes = new String[][]{{"AuditorTypes", "createAuditor"}, {"AuthenticationProviderTypes", "createAuthenticationProvider"}, {"RoleMapperTypes", "createRoleMapper"}, {"AuthorizerTypes", "createAuthorizer"}, {"AdjudicatorTypes", "createAdjudicator"}, {"CredentialMapperTypes", "createCredentialMapper"}, {"CertPathProviderTypes", "createCertPathProvider"}};
      CREATOR_SIGNATURE = new String[]{String.class.getName()};
      DESTROY_SIGNATURE = new String[]{"javax.management.ObjectName"};
   }
}
