package weblogic.management.scripting;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.Descriptor;
import javax.management.InstanceNotFoundException;
import javax.management.JMException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.NoAccessRuntimeException;
import weblogic.management.WebLogicMBean;
import weblogic.management.scripting.utils.ErrorInformation;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;
import weblogic.management.security.RealmMBean;
import weblogic.security.UserConfigFileManager;
import weblogic.security.UsernameAndPassword;
import weblogic.security.internal.SerializedSystemIni;
import weblogic.security.internal.encryption.ClearOrEncryptedService;
import weblogic.security.internal.encryption.EncryptionService;
import weblogic.utils.StringUtils;

public class EditHandler {
   WLScriptContext ctx = null;
   private WLSTMsgTextFormatter txtFmt;
   List specialMBeans = new ArrayList();
   List creatableDescriptorBeans = new ArrayList();
   static final String REALM_MBEAN = "weblogic.management.security.RealmMBean";
   List baseSecurityProviderAttributeTypes = new ArrayList();
   static final String ADJ = "Adjudicator";
   static final String AUD = "Auditor";
   static final String ATN = "AuthenticationProvider";
   static final String ATZ = "Authorizer";
   static final String CPP = "CertPathProvider";
   static final String CDM = "CredentialMapper";
   static final String RMP = "RoleMapper";
   private static final String CREATOR = "creator";
   private static final String DESTROYER = "destroyer";
   private static final String SLASH = "/";
   private static final String[] CREATABLE_DESCRIPTOR_BEANS = new String[]{"weblogic.j2ee.descriptor.wl.JDBCPropertyBean", "weblogic.diagnostics.descriptor.WLDFHarvestedTypeBean", "weblogic.diagnostics.descriptor.WLDFImageNotificationBean", "weblogic.diagnostics.descriptor.WLDFInstrumentationMonitorBean", "weblogic.diagnostics.descriptor.WLDFJMXNotificationBean", "weblogic.diagnostics.descriptor.WLDFSMTPNotificationBean", "weblogic.diagnostics.descriptor.WLDFSNMPNotificationBean", "weblogic.diagnostics.descriptor.WLDFWatchNotificationBean", "weblogic.diagnostics.descriptor.WLDFJMSNotificationBean", "weblogic.diagnostics.descriptor.WLDFWatchNotificationBean", "weblogic.j2ee.descriptor.wl.JMSConnectionFactoryBean", "weblogic.j2ee.descriptor.wl.DistributedDestinationMemberBean", "weblogic.j2ee.descriptor.wl.PropertyBean"};
   private static final String[] SPECIAL_BEANS = new String[]{"JMSSessionPool", "JMSConnectionConsumer"};

   public EditHandler(WLScriptContext ctx) {
      this.ctx = ctx;
      this.txtFmt = ctx.getWLSTMsgFormatter();

      int i;
      for(i = 0; i < SPECIAL_BEANS.length; ++i) {
         this.specialMBeans.add(SPECIAL_BEANS[i]);
      }

      this.baseSecurityProviderAttributeTypes.add("Adjudicator");
      this.baseSecurityProviderAttributeTypes.add("Auditor");
      this.baseSecurityProviderAttributeTypes.add("AuthenticationProvider");
      this.baseSecurityProviderAttributeTypes.add("Authorizer");
      this.baseSecurityProviderAttributeTypes.add("CertPathProvider");
      this.baseSecurityProviderAttributeTypes.add("CredentialMapper");
      this.baseSecurityProviderAttributeTypes.add("RoleMapper");

      for(i = 0; i < CREATABLE_DESCRIPTOR_BEANS.length; ++i) {
         this.creatableDescriptorBeans.add(CREATABLE_DESCRIPTOR_BEANS[i]);
      }

   }

   Object get(String attrName) throws ScriptException {
      try {
         String currentPwd;
         String currenTree;
         ObjectName oname;
         label103: {
            currentPwd = this.ctx.prompt;
            currenTree = this.ctx.getCurrentTree();
            oname = this.ctx.getObjectName();
            WLScriptContext var10001 = this.ctx;
            if (!this.ctx.domainType.equals("Custom_Domain")) {
               var10001 = this.ctx;
               if (!this.ctx.domainType.equals("DomainCustom_Domain")) {
                  var10001 = this.ctx;
                  if (!this.ctx.domainType.equals("EditCustom_Domain")) {
                     break label103;
                  }
               }
            }

            oname = new ObjectName((String)this.ctx.prompts.peek());
         }

         MBeanServerConnection connection = this.ctx.getMBSConnection((String)null);

         try {
            return connection.getAttribute(oname, attrName);
         } catch (AttributeNotFoundException var18) {
            AttributeNotFoundException e = var18;

            Object var12;
            try {
               String tree = this.ctx.getTreeFromArgument(attrName);
               if (tree != null) {
                  attrName = this.ctx.removeTreeFromArgument(attrName);
                  this.ctx.browseHandler.jumpTree(tree);
               }

               String[] attributes = StringUtils.splitCompletely(attrName, "/");
               if (attributes.length <= 1) {
                  throw e;
               }

               String attributeName = attributes[attributes.length - 1];
               attributes[attributes.length - 1] = "";
               String attribute = StringUtils.join(attributes, "/");
               if (attrName.startsWith("/")) {
                  attribute = "/" + attribute;
               }

               this.ctx.browseHandler.cd(attribute);
               Object retObj = this.get(attributeName);
               var12 = retObj;
            } finally {
               this.ctx.browseHandler.jumpTree(currenTree);
               this.ctx.browseHandler.takeBackToRoot();
               this.ctx.browseHandler.cd(currentPwd);
            }

            return var12;
         }
      } catch (Throwable var19) {
         if (var19 instanceof ScriptException) {
            throw (ScriptException)var19;
         } else {
            this.ctx.throwWLSTException(this.txtFmt.getErrorGettingAttribute(attrName), var19);
            return null;
         }
      }
   }

   private Object convertToRightObject(String theType, Object value) throws ScriptException {
      if (theType.equals("java.lang.String")) {
         return value instanceof String ? (String)value : value.toString();
      } else if (theType.equals("java.lang.Integer")) {
         return value instanceof String ? new Integer((String)value) : new Integer(value.toString());
      } else if (theType.equals("java.lang.Boolean")) {
         if (value instanceof String) {
            return new Boolean((String)value);
         } else {
            String strVal = value.toString();
            if ("0".equals(strVal)) {
               return Boolean.FALSE;
            } else {
               return "1".equals(strVal) ? Boolean.TRUE : new Boolean(strVal);
            }
         }
      } else if (theType.equals("java.lang.Byte")) {
         return value instanceof String ? new Byte((String)value) : new Byte(value.toString());
      } else if (theType.equals("java.util.Properties")) {
         return this.makePropertiesObject(value);
      } else if (theType.equals("java.util.Map")) {
         return this.makeMapObject(value);
      } else if (!theType.equals("java.lang.Byte") && !theType.equals("[B")) {
         if (theType.equals("java.lang.Long")) {
            return this.makeLongObject(value);
         } else if (theType.equals("java.lang.Double")) {
            return value instanceof String ? new Double((String)value) : new Double(value.toString());
         } else if (theType.equals("java.lang.Float")) {
            return value instanceof String ? new Float((String)value) : new Float(value.toString());
         } else if (theType.equals("java.lang.Short")) {
            return value instanceof String ? new Short((String)value) : new Short(value.toString());
         } else if (theType.equals("javax.management.ObjectName")) {
            return this.ctx.getObjectName(value);
         } else {
            return theType.equals("[Ljavax.management.ObjectName;") ? this.ctx.getObjectNames(value) : value;
         }
      } else {
         return this.makeByteArray(value);
      }
   }

   private Long makeLongObject(Object val) {
      String theVal = (String)val;
      long lo1;
      if (theVal.endsWith("L")) {
         theVal = theVal.replaceAll("L", "");
         lo1 = Long.parseLong(theVal);
         return new Long(lo1);
      } else {
         lo1 = Long.parseLong(theVal);
         return new Long(lo1);
      }
   }

   private byte[] makeByteArray(Object val) {
      String theVal = (String)val;
      if (theVal.startsWith("{3DES}")) {
         byte[] y = null;

         try {
            byte[] y = SerializedSystemIni.getEncryptionService().decryptBytes(theVal.getBytes());
            return y;
         } catch (Exception var5) {
            return theVal.getBytes();
         }
      } else {
         return theVal.getBytes();
      }
   }

   private Properties makePropertiesObject(Object val) {
      String propString = (String)val;
      Properties props = new Properties();
      if (propString.startsWith("{") && propString.endsWith("}")) {
         propString = propString.substring(1, propString.length() - 1);
      }

      String[] vals = StringUtils.splitCompletely(propString, ",");

      for(int i = 0; i < vals.length; ++i) {
         String[] realVals = StringUtils.splitCompletely(vals[i], "=");
         props.setProperty(realVals[0], realVals[1]);
      }

      return props;
   }

   private Map makeMapObject(Object val) {
      String propString = (String)val;
      HashMap props = new HashMap();
      if (propString.startsWith("{") && propString.endsWith("}")) {
         propString = propString.substring(1, propString.length() - 1);
      }

      String[] vals = StringUtils.splitCompletely(propString, ",");

      for(int i = 0; i < vals.length; ++i) {
         String[] realVals = StringUtils.splitCompletely(vals[i], "=");
         props.put(realVals[0], realVals[1]);
      }

      return props;
   }

   private Object getRealObject(MBeanServerConnection conn, ObjectName oname, String attribName, Object value) throws ScriptException {
      try {
         MBeanInfo info;
         label31: {
            info = null;
            WLScriptContext var10001 = this.ctx;
            if (!this.ctx.domainType.equals("Custom_Domain")) {
               var10001 = this.ctx;
               if (!this.ctx.domainType.equals("DomainCustom_Domain")) {
                  var10001 = this.ctx;
                  if (!this.ctx.domainType.equals("EditCustom_Domain")) {
                     info = this.ctx.getMBeanInfo(this.ctx.wlcmo);
                     break label31;
                  }
               }
            }

            info = conn.getMBeanInfo(oname);
         }

         MBeanAttributeInfo[] attrInfos = info.getAttributes();

         for(int i = 0; i < attrInfos.length; ++i) {
            MBeanAttributeInfo attrInfo = attrInfos[i];
            if (attrInfo.getName().equals(attribName)) {
               this.ctx.printDebug("Value will be converted to type " + attrInfo.getType());
               return this.convertToRightObject(attrInfo.getType(), value);
            }
         }

         return value;
      } catch (Exception var9) {
         return value;
      }
   }

   Object getMBean(String mbeanPath) {
      Object result = null;

      try {
         String currentPwd = this.ctx.prompt;

         try {
            this.ctx.browseHandler.cd(mbeanPath);
            result = this.ctx.wlcmo;
            this.ctx.browseHandler.takeBackToRoot();
         } finally {
            this.ctx.browseHandler.cd(currentPwd);
         }

         return result;
      } catch (Throwable var8) {
         return result;
      }
   }

   void setEncrypted(String attrName, String propertyName, String configFile, String secretFile) throws ScriptException {
      UsernameAndPassword up;
      if (configFile != null && secretFile != null) {
         this.ctx.printDebug("Looking for the property in the locations specified ... ");
         up = UserConfigFileManager.getUsernameAndPassword(configFile, secretFile, propertyName);
         if (up == null) {
            String msg = this.txtFmt.getUserConfigPropertyNotFound(propertyName);
            throw new ScriptException(msg, "setEncrypted");
         }

         this.set(attrName, new String(up.getPassword()));
      } else {
         this.ctx.printDebug("Looking for the default store ... ");
         up = UserConfigFileManager.getUsernameAndPassword(propertyName);
         if (up == null) {
            throw new ScriptException(this.txtFmt.getUserConfigNotFound(), "setEncrypted");
         }

         this.ctx.printDebug("got the value and now setting ... ");
         this.set(attrName, new String(up.getPassword()));
      }

   }

   boolean set(String attrName, Object value) throws ScriptException {
      boolean result = false;
      String currentPwd = this.ctx.prompt;
      String currenTree = this.ctx.getCurrentTree();

      try {
         ObjectName oname = this.ctx.getObjectName();
         MBeanServerConnection connection = this.ctx.getMBSConnection((String)null);
         Attribute attr = new Attribute(attrName, this.getRealObject(connection, oname, attrName, value));

         try {
            if (this.ctx.wlcmo instanceof DescriptorBean) {
               connection.setAttribute(oname, attr);
            } else {
               label124: {
                  WLScriptContext var10001 = this.ctx;
                  if (!this.ctx.domainType.equals("Custom_Domain")) {
                     var10001 = this.ctx;
                     if (!this.ctx.domainType.equals("DomainCustom_Domain")) {
                        var10001 = this.ctx;
                        if (!this.ctx.domainType.equals("EditCustom_Domain")) {
                           connection.setAttribute(oname, attr);
                           break label124;
                        }
                     }
                  }

                  ObjectName on = new ObjectName((String)this.ctx.prompts.peek());
                  connection.setAttribute(on, attr);
               }
            }
         } catch (AttributeNotFoundException var19) {
            AttributeNotFoundException anf = var19;

            try {
               String tree = this.ctx.getTreeFromArgument(attrName);
               if (tree != null) {
                  attrName = this.ctx.removeTreeFromArgument(attrName);
                  this.ctx.browseHandler.jumpTree(tree);
               }

               String[] attributes = StringUtils.splitCompletely(attrName, "/");
               if (attributes.length <= 0) {
                  throw anf;
               }

               String attributeName = attributes[attributes.length - 1];
               attributes[attributes.length - 1] = "";
               String attribute = StringUtils.join(attributes, "/");
               if (attrName.startsWith("/")) {
                  attribute = "/" + attribute;
               }

               this.ctx.browseHandler.cd(attribute);
               oname = this.ctx.getObjectName();
               connection = this.ctx.getMBSConnection((String)null);
               attr = new Attribute(attributeName, this.getRealObject(connection, oname, attributeName, value));
               connection.setAttribute(oname, attr);
            } finally {
               this.ctx.browseHandler.jumpTree(currenTree);
               this.ctx.browseHandler.takeBackToRoot();
               this.ctx.browseHandler.cd(currentPwd);
            }
         }

         result = true;
      } catch (Throwable var20) {
         if (var20 instanceof ScriptException) {
            throw (ScriptException)var20;
         }

         this.ctx.throwWLSTException(this.txtFmt.getErrorSettingAttribute(attrName), var20);
      }

      return result;
   }

   Object invoke(String methodName, Object[] parameters, String[] signatures) throws ScriptException {
      try {
         if (!this.ctx.inMBeanType && !this.ctx.inMBeanTypes) {
            if (this.ctx.atBeanLevel) {
               ObjectName oname = this.ctx.getObjectName();
               if (oname != null) {
                  return this.ctx.getMBSConnection(this.ctx.domainType).invoke(oname, methodName, parameters, signatures);
               }

               this.ctx.printDebug("could be a custom mbean");
               ObjectName on = new ObjectName((String)this.ctx.prompts.peek());
               return this.ctx.getMBSConnection(this.ctx.domainType).invoke(on, methodName, parameters, signatures);
            }
         } else {
            this.ctx.println(this.txtFmt.getInvokeNotApplicable());
         }
      } catch (Throwable var6) {
         if (var6 instanceof ScriptException) {
            throw (ScriptException)var6;
         }

         this.ctx.throwWLSTException((String)null, var6);
      }

      return this.txtFmt.getInvokeNotApplicable();
   }

   private boolean checkForAdminServer(String cmdName) throws ScriptException {
      if (!this.ctx.isAdminServer) {
         this.ctx.throwWLSTException(this.txtFmt.getCannotUseCommandOnMS(cmdName));
         return false;
      } else {
         return true;
      }
   }

   private boolean isEditOrConfigTree(String cmdName) throws ScriptException {
      WLScriptContext var10001 = this.ctx;
      if (!this.ctx.domainType.equals("Domain")) {
         var10001 = this.ctx;
         if (!this.ctx.domainType.equals("ConfigEdit")) {
            var10001 = this.ctx;
            if (!this.ctx.domainType.equals("Custom_Domain")) {
               var10001 = this.ctx;
               if (!this.ctx.domainType.equals("DomainCustom_Domain")) {
                  var10001 = this.ctx;
                  if (!this.ctx.domainType.equals("EditCustom_Domain")) {
                     this.ctx.throwWLSTException(this.txtFmt.getCannotUseCommandUnlessEditConfig(cmdName));
                     return false;
                  }
               }
            }
         }
      }

      return true;
   }

   Object create(String mbeanName, String mbeanType, String providerType) throws ScriptException {
      if (!this.checkForAdminServer("create")) {
         return null;
      } else if (!this.isEditOrConfigTree("create")) {
         return null;
      } else {
         Object obj = null;
         if (mbeanType == null) {
            if (this.ctx.atBeanLevel) {
               this.ctx.throwWLSTException(this.txtFmt.getMBeanTypeMustBeNonNull());
            }

            mbeanType = (String)this.ctx.prompts.peek();
         }

         if (mbeanType.indexOf(".") != -1) {
            return this.createSecurityMBean(mbeanName, mbeanType, providerType);
         } else {
            String type = this.getParentType();
            mbeanType = this.ctx.getRightType(mbeanType);
            String creator = null;
            String method;
            if (this.isCreatePossible(type, mbeanType)) {
               creator = this.getCreatorOrDestroyer(mbeanType, "creator");
               if (creator == null) {
                  this.ctx.throwWLSTException(this.txtFmt.getcouldNotDetermineCreate());
               }
            } else {
               method = "create" + mbeanType;
               MBeanOperationInfo[] operInfos = this.ctx.getMBeanInfo(this.ctx.wlcmo).getOperations();

               for(int i = 0; i < operInfos.length; ++i) {
                  if (method.equals(operInfos[i].getName())) {
                     creator = method;
                     break;
                  }
               }
            }

            if (creator != null) {
               try {
                  method = null;
                  Object[] args = new Object[1];
                  Class[] params = new Class[]{String.class};

                  Method method;
                  try {
                     method = this.ctx.wlcmo.getClass().getMethod(creator, params);
                  } catch (NoSuchMethodException var11) {
                     method = this.ctx.wlcmo.getClass().getMethod(creator);
                     obj = method.invoke(this.ctx.wlcmo);
                     this.ctx.println(this.txtFmt.getOptionalSingletonCreated(mbeanType));
                     return obj;
                  }

                  args[0] = mbeanName;
                  obj = method.invoke(this.ctx.wlcmo, args);
               } catch (Throwable var12) {
                  WLScriptContext var10001;
                  if (var12 instanceof InvocationTargetException) {
                     InvocationTargetException ite = (InvocationTargetException)var12;
                     UndeclaredThrowableException udTh = null;
                     if (ite.getTargetException() instanceof UndeclaredThrowableException) {
                        udTh = (UndeclaredThrowableException)ite.getTargetException();
                     } else if (ite.getTargetException() instanceof NoAccessRuntimeException) {
                        this.ctx.throwWLSTException(this.txtFmt.getCouldNotCreateMBean(), ite.getTargetException());
                     }

                     if (udTh != null) {
                        if (udTh.getUndeclaredThrowable() instanceof MBeanException) {
                           if (((MBeanException)udTh.getUndeclaredThrowable()).getTargetException() instanceof BeanAlreadyExistsException) {
                              String msg = this.txtFmt.getChooseDifferentName(mbeanType, mbeanName);
                              this.ctx.throwWLSTException(msg, var12.getCause());
                           } else {
                              var10001 = this.ctx;
                              this.ctx.throwWLSTException("Unexpected Error: ", var12);
                           }
                        } else {
                           if (var12 instanceof ScriptException) {
                              throw (ScriptException)var12;
                           }

                           var10001 = this.ctx;
                           this.ctx.throwWLSTException("Unexpected Error: ", var12);
                        }
                     } else {
                        if (var12 instanceof ScriptException) {
                           throw (ScriptException)var12;
                        }

                        var10001 = this.ctx;
                        this.ctx.throwWLSTException("Unexpected Error: ", var12);
                     }
                  } else {
                     if (var12 instanceof ScriptException) {
                        throw (ScriptException)var12;
                     }

                     var10001 = this.ctx;
                     this.ctx.throwWLSTException("Unexpected Error: ", var12);
                  }
               }
            } else {
               this.ctx.throwWLSTException(this.txtFmt.getCannotCreateNonChild1(mbeanType));
            }

            this.ctx.println(this.txtFmt.getMBeanCreatedSuccessfully(mbeanType, mbeanName));
            return obj;
         }
      }
   }

   Object lookup(String mbeanName, String mbeanType) throws ScriptException {
      try {
         if (mbeanType == null) {
            if (this.ctx.atBeanLevel) {
               this.ctx.errorMsg = this.txtFmt.getMBeanTypeMustBeNonNull();
               this.ctx.errorInfo = new ErrorInformation(this.ctx.errorMsg);
               this.ctx.exceptionHandler.handleException(this.ctx.errorInfo);
            }

            mbeanType = (String)this.ctx.prompts.peek();
         }

         if (mbeanType.indexOf(".") != -1) {
            return null;
         }

         if (mbeanType.endsWith("ies")) {
            mbeanType = mbeanType.substring(0, mbeanType.length() - 3) + "y";
         } else if (mbeanType.endsWith("sses")) {
            mbeanType = mbeanType.substring(0, mbeanType.length() - 2);
         } else if (mbeanType.endsWith("s") && !mbeanType.endsWith("ss")) {
            mbeanType = mbeanType.substring(0, mbeanType.length() - 1);
         }

         if (this.isCreatePossible(this.getParentType(), mbeanType)) {
            String lookupName = this.getLookup(mbeanType);
            if (lookupName != null) {
               Class[] params = new Class[]{String.class};
               Method[] mths = this.ctx.wlcmo.getClass().getMethods();
               Method method = this.ctx.wlcmo.getClass().getMethod(lookupName, params);
               Object[] args = new Object[]{mbeanName};
               Object bean = method.invoke(this.ctx.wlcmo, args);
               if (bean != null) {
                  return bean;
               }

               this.ctx.throwWLSTException(this.txtFmt.getInstanceNotFound(mbeanName));
            }
         }
      } catch (Throwable var9) {
         if (var9 instanceof ScriptException) {
            throw (ScriptException)var9;
         }

         WLScriptContext var10001 = this.ctx;
         this.ctx.throwWLSTException("Unexpected Error: ", var9);
      }

      return null;
   }

   void delete(String mbeanName, String mbeanType) throws ScriptException {
      if (this.checkForAdminServer("delete")) {
         if (this.isEditOrConfigTree("delete")) {
            try {
               if (mbeanType == null) {
                  if (this.ctx.atBeanLevel) {
                     this.ctx.throwWLSTException(this.txtFmt.getMBeanTypeMustBeNonNull());
                  }

                  mbeanType = (String)this.ctx.prompts.peek();
               }

               String lookupName;
               if (this.ctx.getMBeanServer().isInstanceOf(this.ctx.getObjectName(this.ctx.wlcmo), "weblogic.management.security.RealmMBean")) {
                  lookupName = mbeanType;
                  if (mbeanType.endsWith("s")) {
                     lookupName = this.removeS(mbeanType);
                  }

                  if (this.baseSecurityProviderAttributeTypes.contains(lookupName)) {
                     this.deleteSecurityMBean(mbeanName, lookupName);
                     return;
                  }
               }

               if (mbeanType.endsWith("ies")) {
                  mbeanType = mbeanType.substring(0, mbeanType.length() - 3) + "y";
               } else if (mbeanType.endsWith("sses")) {
                  mbeanType = mbeanType.substring(0, mbeanType.length() - 2);
               } else if (mbeanType.endsWith("s") && !mbeanType.endsWith("ss")) {
                  mbeanType = mbeanType.substring(0, mbeanType.length() - 1);
               }

               if (this.isCreatePossible(this.getParentType(), mbeanType)) {
                  lookupName = this.getLookup(mbeanType);
                  String destroyName = this.getCreatorOrDestroyer(mbeanType, "destroyer");
                  Method method = null;
                  if (lookupName != null) {
                     Class[] params = new Class[]{String.class};
                     Method[] mths = this.ctx.wlcmo.getClass().getMethods();
                     method = this.ctx.wlcmo.getClass().getMethod(lookupName, params);
                     Object[] args = new Object[]{mbeanName};
                     Object bean = method.invoke(this.ctx.wlcmo, args);
                     if (bean == null) {
                        this.ctx.throwWLSTException(this.txtFmt.getInstanceNotFound(mbeanName));
                     }

                     Class clz = Class.forName(this.getInterfaceClassName(mbeanType));
                     Class[] params1 = new Class[]{clz};

                     Method method1;
                     try {
                        method1 = this.ctx.wlcmo.getClass().getMethod(destroyName, params1);
                     } catch (NoSuchMethodException var14) {
                        method1 = this.findDestroyMethod(destroyName, clz);
                     }

                     Object[] args1 = new Object[]{bean};
                     method1.invoke(this.ctx.wlcmo, args1);
                     this.ctx.println(this.txtFmt.getMBeanDeletedSuccessfully(mbeanType, mbeanName));
                  } else if (destroyName != null) {
                     method = this.ctx.wlcmo.getClass().getMethod(destroyName);
                     method.invoke(this.ctx.wlcmo);
                     this.ctx.println(this.txtFmt.getMBeanDeletedSuccessfully1(mbeanType));
                  } else {
                     String msg = this.txtFmt.getCannotDeleteNonChild2(mbeanType, mbeanName);
                     this.ctx.throwWLSTException(msg);
                  }
               } else {
                  this.ctx.throwWLSTException(this.txtFmt.getCannotDeleteNonChild2(mbeanType, mbeanName));
               }
            } catch (InstanceNotFoundException var15) {
               this.ctx.throwWLSTException(this.txtFmt.getInstanceNotFound(mbeanName), var15);
            } catch (Throwable var16) {
               if (var16 instanceof ScriptException) {
                  throw (ScriptException)var16;
               }

               this.ctx.throwWLSTException(this.txtFmt.getErrorDeletingABean(), var16);
            }

         }
      }
   }

   private Method findDestroyMethod(String destroyName, Class clz) {
      Class[] superInterfaces = clz.getInterfaces();
      Class[] var4 = superInterfaces;
      int var5 = superInterfaces.length;
      int var6 = 0;

      while(var6 < var5) {
         Class superInterface = var4[var6];

         try {
            Class[] params1 = new Class[]{superInterface};
            return this.ctx.wlcmo.getClass().getMethod(destroyName, params1);
         } catch (NoSuchMethodException var10) {
            Method findMethod = this.findDestroyMethod(destroyName, superInterface);
            if (findMethod != null) {
               return findMethod;
            }

            ++var6;
         }
      }

      return null;
   }

   private String getParentType() throws ScriptException {
      String type = this.ctx.getObjectName().getKeyProperty("Type");
      return type != null ? type : null;
   }

   private Object createSecurityMBean(String mbeanName, String providerType, String baseProviderType) throws ScriptException {
      this.ctx.printDebug("In creating security mbean with name " + mbeanName);
      if (providerType == null) {
         this.ctx.throwWLSTException(this.txtFmt.getParameterMayNotBeNull("providerType"));
      }

      if (baseProviderType == null) {
         if (this.ctx.atBeanLevel) {
            this.ctx.throwWLSTException(this.txtFmt.getParameterMayNotBeNull("baseProviderType"));
         }

         baseProviderType = (String)this.ctx.prompts.peek();
      }

      try {
         if (this.ctx.getMBeanServer().isInstanceOf(this.ctx.getObjectName(this.ctx.wlcmo), "weblogic.management.security.RealmMBean")) {
            if (baseProviderType.endsWith("s")) {
               baseProviderType = this.removeS(baseProviderType);
            }

            if (!this.baseSecurityProviderAttributeTypes.contains(baseProviderType)) {
               this.ctx.throwWLSTException(this.txtFmt.getCannotCreateNonChild());
            }

            this.nowCreateSecurityMBean(mbeanName, providerType, baseProviderType);
            this.ctx.println(this.txtFmt.getProviderCreatedSuccessfully(baseProviderType, providerType, mbeanName));
         } else {
            this.ctx.throwWLSTException(this.txtFmt.getCannotCreateNonChild());
         }
      } catch (InstanceNotFoundException var5) {
         this.ctx.throwWLSTException(this.txtFmt.getRealmInstanceNotFound(), var5);
      } catch (IOException var6) {
         this.ctx.throwWLSTException(this.txtFmt.getRealmInstanceNotFound(), var6);
      }

      return null;
   }

   private Object nowCreateSecurityMBean(String name, String providerType, String baseProviderType) throws ScriptException {
      try {
         RealmMBean rbean = (RealmMBean)this.ctx.wlcmo;
         if (baseProviderType.equals("Adjudicator")) {
            return rbean.createAdjudicator(name, providerType);
         }

         if (baseProviderType.equals("AuthenticationProvider")) {
            return rbean.createAuthenticationProvider(name, providerType);
         }

         if (baseProviderType.equals("Authorizer")) {
            return rbean.createAuthorizer(name, providerType);
         }

         if (baseProviderType.equals("Auditor")) {
            return rbean.createAuditor(name, providerType);
         }

         if (baseProviderType.equals("CertPathProvider")) {
            return rbean.createCertPathProvider(name, providerType);
         }

         if (baseProviderType.equals("RoleMapper")) {
            return rbean.createRoleMapper(name, providerType);
         }

         if (baseProviderType.equals("CredentialMapper")) {
            return rbean.createCredentialMapper(name, providerType);
         }
      } catch (ClassNotFoundException var5) {
         this.ctx.throwWLSTException(this.txtFmt.getProviderClassNotFound(), var5);
      } catch (JMException var6) {
         this.ctx.throwWLSTException(this.txtFmt.getErrorCreatingProvider(), var6);
      }

      return null;
   }

   private String removeS(String baseType) {
      return baseType.substring(0, baseType.length() - 1);
   }

   private void deleteSecurityMBean(String name, String baseProviderType) throws ScriptException {
      RealmMBean rbean = (RealmMBean)this.ctx.wlcmo;
      if (baseProviderType.equals("Adjudicator")) {
         rbean.destroyAdjudicator();
      } else if (baseProviderType.equals("AuthenticationProvider")) {
         rbean.destroyAuthenticationProvider(rbean.lookupAuthenticationProvider(name));
      } else if (baseProviderType.equals("Authorizer")) {
         rbean.destroyAuthorizer(rbean.lookupAuthorizer(name));
      } else if (baseProviderType.equals("Auditor")) {
         rbean.destroyAuditor(rbean.lookupAuditor(name));
      } else if (baseProviderType.equals("CredentialMapper")) {
         rbean.destroyCredentialMapper(rbean.lookupCredentialMapper(name));
      } else if (baseProviderType.equals("CertPathProvider")) {
         rbean.destroyCertPathProvider(rbean.lookupCertPathProvider(name));
      } else if (baseProviderType.equals("RoleMapper")) {
         rbean.destroyRoleMapper(rbean.lookupRoleMapper(name));
      }

      this.ctx.println(this.txtFmt.getProviderDeletedSuccessfully(baseProviderType, name));
   }

   WebLogicMBean getTarget(String path) throws ScriptException {
      if (!this.checkForAdminServer("getTarget")) {
         return null;
      } else {
         this.ctx.println(this.txtFmt.getGetTargetIsForconfig());
         return null;
      }
   }

   WebLogicMBean[] getTargetArray(String type, String values) throws ScriptException {
      try {
         if (this.ctx.debug) {
            this.ctx.println("The type passed in is " + type);
         }

         String[] valueArray = StringUtils.splitCompletely(values, ",");
         WebLogicMBean[] result = new WebLogicMBean[valueArray.length];

         for(int i = 0; i < valueArray.length; ++i) {
            String path = type + "/" + valueArray[i];
            result[i] = this.getTarget(path);
         }

         return result;
      } catch (Throwable var7) {
         if (var7 instanceof ScriptException) {
            throw (ScriptException)var7;
         } else {
            this.ctx.throwWLSTException(this.txtFmt.getErrorGettingMBeanArray(), var7);
            return null;
         }
      }
   }

   private boolean isCreatePossible(String mbeanType, String childType) throws ScriptException {
      List list = null;

      try {
         list = ((InformationHandler)InformationHandler.class.cast(this.ctx.infoHandler)).getChildrenTypes();
         Iterator iter = list.iterator();
         String child = childType;

         while(iter.hasNext()) {
            String s = (String)iter.next();
            String configString = new String("weblogic.management.configuration");
            if (s.startsWith(configString)) {
               s = s.substring(configString.length() + 1, s.length() - 5);
            }

            if (s.equals(child)) {
               return true;
            }
         }
      } catch (Throwable var8) {
         this.ctx.throwWLSTException(this.txtFmt.getErrorDeterminingIfCreate(), var8);
      }

      return false;
   }

   private String getLookup(String mbeanType) throws ScriptException {
      try {
         MBeanInfo info = this.ctx.getMBeanInfo(this.ctx.wlcmo);
         MBeanOperationInfo[] operInfo = info.getOperations();

         for(int i = 0; i < operInfo.length; ++i) {
            MBeanOperationInfo oInfo = operInfo[i];
            if (oInfo.getName().equals("lookup" + mbeanType)) {
               return oInfo.getName();
            }
         }
      } catch (Throwable var6) {
         this.ctx.throwWLSTException(this.txtFmt.getErrorOnLookup(), var6);
      }

      return null;
   }

   private String getCreatorOrDestroyer(String attributeName, String whatever) throws ScriptException {
      try {
         MBeanInfo info = this.ctx.getMBeanInfo(this.ctx.wlcmo);
         String pluralAttrName = attributeName;
         if (!this.ctx.isPlural(attributeName)) {
            pluralAttrName = this.ctx.getPlural(attributeName);
         }

         ModelMBeanInfo modelInfo = (ModelMBeanInfo)info;
         MBeanAttributeInfo[] attrInfo = modelInfo.getAttributes();

         int j;
         Descriptor desc;
         String methodName;
         for(j = 0; j < attrInfo.length; ++j) {
            if (pluralAttrName.equals(attrInfo[j].getName())) {
               desc = ((ModelMBeanAttributeInfo)attrInfo[j]).getDescriptor();
               methodName = null;
               if (whatever.equals("creator")) {
                  methodName = (String)desc.getFieldValue("com.bea.creator");
               } else {
                  methodName = (String)desc.getFieldValue("com.bea.destroyer");
               }

               if (methodName != null) {
                  return methodName;
               }
            }
         }

         for(j = 0; j < attrInfo.length; ++j) {
            if (attrInfo[j].getName().startsWith(attributeName)) {
               desc = ((ModelMBeanAttributeInfo)attrInfo[j]).getDescriptor();
               methodName = null;
               if (whatever.equals("creator")) {
                  methodName = (String)desc.getFieldValue("com.bea.creator");
               } else {
                  methodName = (String)desc.getFieldValue("com.bea.destroyer");
               }

               if (methodName != null) {
                  return methodName;
               }
            }
         }
      } catch (Throwable var10) {
         this.ctx.throwWLSTException(this.txtFmt.getErrorGettingCreator(), var10);
      }

      return null;
   }

   private String getInterfaceClassName(String mbeanType) throws ScriptException {
      try {
         MBeanInfo info = this.ctx.getMBeanInfo(this.ctx.wlcmo);
         ModelMBeanInfo modelInfo = (ModelMBeanInfo)info;
         MBeanAttributeInfo[] attrInfo = modelInfo.getAttributes();
         String retType = "weblogic.management.configuration." + mbeanType + "MBean";

         for(int i = 0; i < attrInfo.length; ++i) {
            Descriptor desc = ((ModelMBeanAttributeInfo)attrInfo[i]).getDescriptor();
            String intClzName = (String)((ModelMBeanAttributeInfo)attrInfo[i]).getDescriptor().getFieldValue("interfaceClassName");
            if (intClzName != null) {
               if (intClzName.startsWith("[L")) {
                  intClzName = intClzName.substring(2, intClzName.length() - 1);
               }

               if (retType.equals(intClzName) || !intClzName.startsWith("weblogic.management.configuration.") && intClzName.indexOf(mbeanType) != -1) {
                  if (!intClzName.startsWith("weblogic.j2ee.descriptor.wl") || !mbeanType.equals("Topic") && !mbeanType.equals("Queue") && !mbeanType.equals("DistributedTopic") && !mbeanType.equals("DistributedQueue")) {
                     return intClzName;
                  }

                  if (this.hasSameType(intClzName, mbeanType)) {
                     return intClzName;
                  }
               }
            }
         }
      } catch (Throwable var9) {
         this.ctx.throwWLSTException(this.txtFmt.getErrorGettingInterfaceClassName(), var9);
      }

      return null;
   }

   private boolean hasSameType(String intClzName, String mbeanType) {
      String clzName = intClzName.substring(intClzName.lastIndexOf(".") + 1, intClzName.length());
      return clzName.equals(mbeanType + "Bean");
   }

   Object encrypt(Object obj, String domainDir) throws ScriptException {
      try {
         EncryptionService es = null;
         ClearOrEncryptedService ces = null;
         if (domainDir != null) {
            File f = new File(domainDir);
            this.ctx.printDebug("Setting the root directory to " + f.getAbsolutePath());
            es = SerializedSystemIni.getEncryptionService(f.getAbsolutePath());
         } else {
            es = SerializedSystemIni.getExistingEncryptionService();
         }

         if (es == null) {
            this.ctx.errorMsg = this.txtFmt.getErrorInitializingEncryptionService();
            this.ctx.errorInfo = new ErrorInformation(this.ctx.errorMsg);
            this.ctx.exceptionHandler.handleException(this.ctx.errorInfo);
         }

         ces = new ClearOrEncryptedService(es);
         return obj instanceof String ? ces.encrypt((String)obj) : ces.encryptBytes((byte[])((byte[])obj));
      } catch (Throwable var6) {
         this.ctx.throwWLSTException(this.txtFmt.getErrorEncryptingValue(), var6);
         return null;
      }
   }
}
