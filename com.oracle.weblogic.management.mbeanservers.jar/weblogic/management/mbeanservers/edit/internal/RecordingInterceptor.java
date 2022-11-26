package weblogic.management.mbeanservers.edit.internal;

import java.io.IOException;
import java.security.AccessController;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.InvalidAttributeValueException;
import javax.management.MBeanException;
import javax.management.MBeanInfo;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.ManagementLogger;
import weblogic.management.WebLogicMBean;
import weblogic.management.jmx.mbeanserver.WLSMBeanServerInterceptorBase;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.mbeanservers.edit.RecordingManagerMBean;
import weblogic.management.mbeanservers.internal.RecordingManager;
import weblogic.management.provider.EditAccess;
import weblogic.management.provider.ManagementService;
import weblogic.management.scripting.WLSTPathUtil;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.utils.StringUtils;

public class RecordingInterceptor extends WLSMBeanServerInterceptorBase {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugJMXEdit");
   private static HashMap cmOperToWLSTMap = null;
   private static final AuthenticatedSubject kernelIdentity;
   private volatile EditAccess editAccess;
   private HashMap pathCache = new HashMap();
   private String domainName = null;

   public RecordingInterceptor(EditAccess editAccess) {
      this.editAccess = editAccess;
      this.domainName = ManagementService.getRuntimeAccess(kernelIdentity).getDomainName();
   }

   public void setAttribute(ObjectName objectName, Attribute attribute) throws InstanceNotFoundException, AttributeNotFoundException, InvalidAttributeValueException, MBeanException, ReflectionException, IOException {
      this.recordSetAttribute(objectName, attribute);
      super.setAttribute(objectName, attribute);
   }

   public Object invoke(ObjectName objectName, String s, Object[] objects, String[] strings) throws InstanceNotFoundException, MBeanException, ReflectionException, IOException {
      try {
         if (RecordingManager.getInstance(this.editAccess).isRecording() && !s.startsWith("lookup") && !s.equals("isSet")) {
            String wlstCmd;
            if (ConfigurationManagerMBean.OBJECT_NAME.equals(objectName.toString())) {
               wlstCmd = (String)cmOperToWLSTMap.get(s);
               if (wlstCmd != null) {
                  if (s.equals("removeReferencesToBean")) {
                     String paramList = this.getParameterList(objects);
                     wlstCmd = wlstCmd + "(" + paramList + ")";
                  }

                  if (s.equals("startEdit") && RecordingManager.getInstance(this.editAccess).isVerbose()) {
                     AuthenticatedSubject currSubject = SecurityServiceManager.getCurrentSubject(kernelIdentity);
                     String comment = "# User " + SubjectUtils.getUsername(currSubject) + " starts a new edit session at " + (new Date()).toString();
                     wlstCmd = comment + "\n" + wlstCmd;
                  }

                  this.write(wlstCmd, true);
               }
            } else if (!RecordingManagerMBean.OBJECT_NAME.equals(objectName.toString())) {
               wlstCmd = this.lookupPath(objectName);
               if (wlstCmd != null && !wlstCmd.startsWith("/Internal")) {
                  this.write("cd('" + wlstCmd + "')", true);
                  this.write("cmo." + s + "(" + this.getParameterList(objects) + ")");
               }
            }
         }
      } catch (IOException var8) {
         ManagementLogger.logInvokeRecordingIOException(objectName, s, var8);
      }

      return super.invoke(objectName, s, objects, strings);
   }

   public AttributeList setAttributes(ObjectName objectName, AttributeList attributeList) throws InstanceNotFoundException, ReflectionException, IOException {
      if (attributeList != null && RecordingManager.getInstance(this.editAccess).isRecording()) {
         List attributes = attributeList.asList();
         Iterator var4 = attributes.iterator();

         while(var4.hasNext()) {
            Attribute attribute = (Attribute)var4.next();
            this.recordSetAttribute(objectName, attribute);
         }
      }

      return super.setAttributes(objectName, attributeList);
   }

   private String objectToString(Object object) {
      if (object == null) {
         return "None";
      } else {
         String path;
         if (object instanceof String) {
            path = StringUtils.escapeString((String)object);
            return "'" + path + "'";
         } else if (object instanceof ObjectName) {
            path = this.lookupPath((ObjectName)object);
            return "getMBean('" + path + "')";
         } else if (object instanceof String[]) {
            String[] strArr = (String[])((String[])object);
            return this.arrayToString(strArr, "String");
         } else if (object instanceof ObjectName[]) {
            ObjectName[] onArr = (ObjectName[])((ObjectName[])object);
            return this.arrayToString(onArr, "ObjectName");
         } else if (!(object instanceof WebLogicMBean[])) {
            return object.toString();
         } else {
            WebLogicMBean[] beans = (WebLogicMBean[])((WebLogicMBean[])object);
            ObjectName[] onArr = new ObjectName[beans.length];

            for(int i = 0; i < beans.length; ++i) {
               onArr[i] = beans[i].getObjectName();
            }

            return this.arrayToString(onArr, "ObjectName");
         }
      }
   }

   private String arrayToString(Object[] objArr, String type) {
      String arr = "";

      for(int i = 0; i < objArr.length; ++i) {
         if (i != 0) {
            arr = arr + ", ";
         }

         arr = arr + type + "('" + objArr[i] + "')";
      }

      return "jarray.array([" + arr + "], " + type + ")";
   }

   private String getParameterList(Object[] objects) {
      if (objects != null && objects.length != 0) {
         StringBuffer args = new StringBuffer();

         for(int i = 0; i < objects.length; ++i) {
            if (i != 0) {
               args.append(", ");
            }

            args.append(this.objectToString(objects[i]));
         }

         return args.toString();
      } else {
         return "";
      }
   }

   private String lookupPath(ObjectName on) {
      if (on == null) {
         throw new IllegalArgumentException("ObjectName can not be null.");
      } else {
         String path = (String)this.pathCache.get(on);
         if (path != null) {
            return path;
         } else {
            try {
               path = WLSTPathUtil.lookupPath(this, this.domainName, on);
            } catch (Exception var4) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Recording error: can not get WLST path for " + on.getCanonicalName(), var4);
               }
            }

            if (path == null) {
               return null;
            } else {
               if (path.startsWith("Domains")) {
                  path = "/";
               } else {
                  path = "/" + path;
               }

               this.pathCache.put(on, path);
               return path;
            }
         }
      }
   }

   private boolean isEncrypted(ObjectName objectName, String attributeName) {
      try {
         MBeanInfo info = this.getMBeanInfo(objectName);
         if (info != null && info instanceof ModelMBeanInfo) {
            ModelMBeanAttributeInfo attributeInfo = ((ModelMBeanInfo)info).getAttribute(attributeName);
            if (attributeInfo != null) {
               Object value = attributeInfo.getDescriptor().getFieldValue("com.bea.encrypted");
               if (value != null) {
                  return (Boolean)value;
               }
            }
         }
      } catch (Exception var6) {
      }

      return false;
   }

   private void write(String cmd) throws IOException {
      this.write(cmd, false);
   }

   private void write(String cmd, boolean prependNewLine) throws IOException {
      RecordingManager.getInstance(this.editAccess).write(cmd, prependNewLine, true);
   }

   private void encrypt(String attributeName, String propName, String attributeValue) throws IOException {
      RecordingManager.getInstance(this.editAccess).encrypt(attributeName, propName, attributeValue);
   }

   private void recordSetAttribute(ObjectName objectName, Attribute attribute) throws IOException {
      if (objectName != null && attribute != null) {
         try {
            if (RecordingManager.getInstance(this.editAccess).isRecording()) {
               String attributeName = attribute.getName();
               Object attributeValue = attribute.getValue();
               String path = this.lookupPath(objectName);
               if (path != null) {
                  this.write("cd('" + path + "')", true);
                  String attrStringValue;
                  if (attributeValue instanceof String && this.isEncrypted(objectName, attributeName)) {
                     attrStringValue = attributeName + "_" + (new Date()).getTime();
                     this.encrypt(attributeName, attrStringValue, (String)attributeValue);
                  } else {
                     attrStringValue = this.objectToString(attributeValue);
                     if (attrStringValue.startsWith("jarray")) {
                        this.write("set('" + attributeName + "'," + attrStringValue + ")");
                     } else {
                        this.write("cmo.set" + attributeName + "(" + attrStringValue + ")");
                     }
                  }
               }
            }
         } catch (IOException var7) {
            ManagementLogger.logSetAttributeRecordingIOException(objectName, attribute.getName(), var7);
         }

      }
   }

   void releaseEditAccess() {
      this.editAccess = null;
   }

   static {
      cmOperToWLSTMap = new HashMap();
      cmOperToWLSTMap.put("startEdit", "startEdit()");
      cmOperToWLSTMap.put("stopEdit", "stopEdit('y')");
      cmOperToWLSTMap.put("cancelEdit", "cancelEdit('y')");
      cmOperToWLSTMap.put("undo", "undo(defaultAnswer='y')");
      cmOperToWLSTMap.put("undoUnactivatedChanges", "undo(defaultAnswer='y', unactivatedChanges='true')");
      cmOperToWLSTMap.put("activate", "activate()");
      cmOperToWLSTMap.put("removeReferencesToBean", "editService.getConfigurationManager().removeReferencesToBean");
      kernelIdentity = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   }
}
