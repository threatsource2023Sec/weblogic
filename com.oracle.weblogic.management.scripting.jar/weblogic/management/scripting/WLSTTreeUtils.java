package weblogic.management.scripting;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.management.MBeanInfo;
import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.QueryExp;
import javax.management.modelmbean.ModelMBeanInfo;
import weblogic.management.NoAccessRuntimeException;
import weblogic.management.WebLogicMBean;
import weblogic.management.commo.StandardInterface;
import weblogic.management.jmx.MBeanServerInvocationHandler;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;

public class WLSTTreeUtils extends WLScriptConstants implements WLSTConstants {
   private WLSTMsgTextFormatter txtFmt = new WLSTMsgTextFormatter();
   protected boolean showLSResult = true;

   public String getTree() {
      if (this.domainType.equals("RuntimeConfigServerDomain")) {
         return "serverConfig";
      } else if (this.domainType.equals("RuntimeRuntimeServerDomain")) {
         return "serverRuntime";
      } else if (this.domainType.equals("ConfigDomainRuntime")) {
         return "domainConfig";
      } else if (this.domainType.equals("RuntimeDomainRuntime")) {
         return "domainRuntime";
      } else if (this.domainType.equals("Domain")) {
         return "config";
      } else if (this.domainType.equals("DomainConfig")) {
         return "config";
      } else if (this.domainType.equals("DomainRuntime")) {
         return "runtime";
      } else if (this.domainType.equals("Custom_Domain")) {
         return "custom";
      } else if (this.domainType.equals("DomainCustom_Domain")) {
         return "domainCustom";
      } else if (this.domainType.equals("EditCustom_Domain")) {
         return "editCustom";
      } else if (this.domainType.equals("ConfigEdit")) {
         return "edit";
      } else if (this.domainType.equals("JSR77")) {
         return "jsr77";
      } else {
         return this.domainType.equals("JNDI") ? "jndi" : "serverConfig";
      }
   }

   String getTreeFromArgument(String attr) {
      if (attr.startsWith("config:")) {
         return "config";
      } else if (attr.startsWith("runtime:")) {
         return "runtime";
      } else if (attr.startsWith("adminConfig:")) {
         return "adminConfig";
      } else if (attr.startsWith("custom:")) {
         return "custom";
      } else if (attr.startsWith("domainCustom:")) {
         return "domainCustom";
      } else if (attr.startsWith("editCustom:")) {
         return "editCustom";
      } else if (attr.startsWith("jndi:")) {
         return "jndi";
      } else if (attr.startsWith("serverConfig:")) {
         return "serverConfig";
      } else if (attr.startsWith("serverRuntime:")) {
         return "serverRuntime";
      } else if (attr.startsWith("domainConfig:")) {
         return "domainConfig";
      } else if (attr.startsWith("domainRuntime:")) {
         return "domainRuntime";
      } else {
         return attr.startsWith("edit:") ? "edit" : null;
      }
   }

   String removeTreeFromArgument(String attr) {
      if (!attr.startsWith("config:") && !attr.startsWith("runtime:") && !attr.startsWith("adminConfig:") && !attr.startsWith("custom:") && !attr.startsWith("domainCustom:") && !attr.startsWith("editCustom:") && !attr.startsWith("jndi:") && !attr.startsWith("serverConfig:") && !attr.startsWith("serverRuntime:") && !attr.startsWith("domainConfig:") && !attr.startsWith("domainRuntime:") && !attr.startsWith("edit:")) {
         return attr;
      } else {
         String _attr = attr.substring(attr.indexOf(":/") + 1, attr.length());
         return _attr;
      }
   }

   String getCurrentTree() {
      return this.getTree();
   }

   public String calculateTabSpace(String s) {
      return this.calculateTabSpace(s, 45);
   }

   public String calculateTabSpace(String s, int minimumFieldWidth) {
      if (s == null) {
         s = "";
      }

      int len = s.length();
      if (len >= minimumFieldWidth - 1) {
         return " ";
      } else {
         char[] chars = new char[minimumFieldWidth - len];
         Arrays.fill(chars, ' ');
         return new String(chars);
      }
   }

   boolean isMBeanExcluded(String bean) {
      return this.excludedMBeans.contains(bean);
   }

   boolean isAnythingInThisExcluded(String key) {
      if (key == null) {
         return false;
      } else {
         Iterator it = this.excludedMBeans.iterator();

         String excludedBean;
         do {
            if (!it.hasNext()) {
               return false;
            }

            excludedBean = (String)it.next();
         } while(key.indexOf(excludedBean) == -1);

         return true;
      }
   }

   String printAttributes(TreeMap attrs) {
      String retString = "\n";
      Iterator iters = attrs.keySet().iterator();

      while(true) {
         String key;
         boolean isExcluded;
         String permission;
         do {
            if (!iters.hasNext()) {
               return retString;
            }

            key = (String)iters.next();
            Iterator it = this.excludedMBeans.iterator();
            isExcluded = false;

            while(it.hasNext()) {
               permission = (String)it.next();
               if (key.indexOf(permission) != -1) {
                  isExcluded = true;
               }
            }
         } while(isExcluded && !this.showExcluded());

         permission = (String)attrs.get(key);
         if (this.showLSResult) {
            this.println(permission + "   " + key);
         }

         retString = retString + permission + "   " + key + "   \n";
      }
   }

   boolean showExcluded() {
      return this.showExcluded;
   }

   public void setShowExcluded(String bool) {
      if (this.getBoolean(bool)) {
         this.showExcluded = true;
      } else {
         this.showExcluded = false;
      }

   }

   public String printNameValuePairs(TreeMap attrs) {
      String retString = "\n";

      String name;
      String value;
      for(Iterator iters = attrs.keySet().iterator(); iters.hasNext(); retString = retString + name + "   " + value + "   \n") {
         name = (String)iters.next();
         value = (String)attrs.get(name);
         this.println(name + "   " + value);
      }

      return retString;
   }

   String printAttrs(TreeSet set) {
      Iterator iter = set.iterator();

      String result;
      String s;
      for(result = ""; iter.hasNext(); result = result + s) {
         s = (String)iter.next();
         this.println(s);
      }

      return result;
   }

   boolean inNewTree() {
      return this.domainType.equals("RuntimeConfigServerDomain") || this.domainType.equals("RuntimeRuntimeServerDomain") || this.domainType.equals("ConfigDomainRuntime") || this.domainType.equals("RuntimeDomainRuntime") || this.domainType.equals("ConfigEdit") || this.domainType.equals("JNDI") || this.domainType.equals("JSR77");
   }

   ObjectName getObjectName() throws ScriptException {
      return this.getObjectName(this.wlcmo);
   }

   Object getMBeanFromObjectName(ObjectName objName) throws Throwable {
      MBeanServerConnection connection = this.getMBSConnection(this.domainType);
      return MBeanServerInvocationHandler.newProxyInstance(connection, objName);
   }

   ObjectName getObjectName(Object cmo) throws ScriptException {
      if (cmo == null) {
         cmo = this.wlcmo;
      }

      try {
         if (this.domainType.equals("Custom_Domain") || this.domainType.equals("DomainCustom_Domain") || this.domainType.equals("EditCustom_Domain")) {
            String s = (String)this.prompts.peek();
            return new ObjectName(s);
         }
      } catch (MalformedObjectNameException var3) {
         return null;
      }

      if (cmo instanceof ObjectName) {
         return (ObjectName)cmo;
      } else {
         if (Proxy.isProxyClass(cmo.getClass())) {
            InvocationHandler handler = Proxy.getInvocationHandler(cmo);
            if (handler instanceof MBeanServerInvocationHandler) {
               return ((MBeanServerInvocationHandler)handler)._getObjectName();
            }

            if (cmo instanceof WebLogicMBean) {
               return ((WebLogicMBean)cmo).getObjectName();
            }

            if (cmo instanceof StandardInterface) {
               return ((StandardInterface)cmo).wls_getObjectName();
            }
         } else {
            if (cmo instanceof WebLogicMBean) {
               return ((WebLogicMBean)cmo).getObjectName();
            }

            if (cmo instanceof StandardInterface) {
               return ((StandardInterface)cmo).wls_getObjectName();
            }
         }

         if (cmo instanceof String) {
            this.throwWLSTException(this.txtFmt.getValueNotObjectName((String)cmo));
         }

         return null;
      }
   }

   ObjectName[] getObjectNames(Object cmo) {
      if (cmo instanceof ObjectName[]) {
         return (ObjectName[])((ObjectName[])cmo);
      } else {
         Object[] cmos = (Object[])((Object[])cmo);
         ObjectName[] ons = new ObjectName[cmos.length];

         for(int i = 0; i < cmos.length; ++i) {
            if (Proxy.isProxyClass(cmos[i].getClass())) {
               InvocationHandler handler = Proxy.getInvocationHandler(cmos[i]);
               if (handler instanceof MBeanServerInvocationHandler) {
                  ons[i] = ((MBeanServerInvocationHandler)handler)._getObjectName();
               } else if (cmos[i] instanceof WebLogicMBean) {
                  ons[i] = ((WebLogicMBean)cmo).getObjectName();
               } else if (cmos[i] instanceof StandardInterface) {
                  ons[i] = ((StandardInterface)cmos[i]).wls_getObjectName();
               }
            } else if (cmos[i] instanceof WebLogicMBean) {
               ons[i] = ((WebLogicMBean)cmos[i]).getObjectName();
            } else if (cmos[i] instanceof StandardInterface) {
               ons[i] = ((StandardInterface)cmos[i]).wls_getObjectName();
            }
         }

         return ons;
      }
   }

   MBeanInfo getMBeanInfo(Object cmo) throws ScriptException {
      if (cmo == null) {
         cmo = this.wlcmo;
      }

      try {
         if (Proxy.isProxyClass(cmo.getClass())) {
            InvocationHandler handler = Proxy.getInvocationHandler(cmo);
            if (handler instanceof MBeanServerInvocationHandler) {
               return this.getMBSConnection(this.domainType).getMBeanInfo(((MBeanServerInvocationHandler)handler)._getObjectName());
            }
         }

         if (cmo instanceof WebLogicMBean) {
            return this.getMBSConnection(this.domainType).getMBeanInfo(((WebLogicMBean)cmo).getObjectName());
         }

         if (cmo instanceof StandardInterface) {
            return this.getMBSConnection(this.domainType).getMBeanInfo(((StandardInterface)cmo).wls_getObjectName());
         }

         if (cmo instanceof ObjectName) {
            return this.getMBSConnection(this.domainType).getMBeanInfo((ObjectName)cmo);
         }
      } catch (Throwable var3) {
         this.throwWLSTException(this.txtFmt.getErrorGettingMBeanInfoForMBean(), var3);
      }

      return null;
   }

   WebLogicMBean getCurrentRootMBean() {
      if (this.domainType.equals("RuntimeConfigServerDomain")) {
         return this.runtimeDomainMBean;
      } else if (this.domainType.equals("RuntimeRuntimeServerDomain")) {
         return this.runtimeServerRuntimeMBean;
      } else if (this.domainType.equals("ConfigDomainRuntime")) {
         return this.configDomainRuntimeDRMBean;
      } else if (this.domainType.equals("RuntimeDomainRuntime")) {
         return this.runtimeDomainRuntimeDRMBean;
      } else if (this.domainType.equals("Domain")) {
         return this.compatDomainMBean;
      } else if (this.domainType.equals("DomainConfig")) {
         return this.compatDomainMBean;
      } else if (this.domainType.equals("DomainRuntime")) {
         return this.compatDomainRuntimeMBean;
      } else if (this.domainType.equals("ConfigEdit")) {
         return this.edit == null ? null : this.edit.domainMBean;
      } else {
         return this.domainType.equals("JSR77") ? null : null;
      }
   }

   WebLogicMBean getRootMBean(String domainType) {
      if (domainType.equals("RuntimeConfigServerDomain")) {
         return this.runtimeDomainMBean;
      } else if (domainType.equals("RuntimeRuntimeServerDomain")) {
         return this.runtimeServerRuntimeMBean;
      } else if (domainType.equals("ConfigDomainRuntime")) {
         return this.configDomainRuntimeDRMBean;
      } else if (domainType.equals("RuntimeDomainRuntime")) {
         return this.runtimeDomainRuntimeDRMBean;
      } else if (domainType.equals("Domain")) {
         return this.compatDomainMBean;
      } else if (domainType.equals("DomainConfig")) {
         return this.compatDomainMBean;
      } else if (domainType.equals("ConfigEdit")) {
         return this.edit == null ? null : this.edit.domainMBean;
      } else if (domainType.equals("JSR77")) {
         return null;
      } else if (domainType.equals("DomainRuntime")) {
         return (WebLogicMBean)(this.isAdminServer ? this.compatDomainRuntimeMBean : this.compatServerRuntimeMBean);
      } else {
         return null;
      }
   }

   MBeanServerConnection getMBSConnection(String domainType) {
      if (domainType == null) {
         domainType = this.domainType;
      }

      if (domainType.equals("RuntimeConfigServerDomain")) {
         return this.runtimeMSC;
      } else if (domainType.equals("RuntimeRuntimeServerDomain")) {
         return this.runtimeMSC;
      } else if (domainType.equals("ConfigDomainRuntime")) {
         return this.domainRTMSC;
      } else if (domainType.equals("RuntimeDomainRuntime")) {
         return this.domainRTMSC;
      } else if (domainType.equals("Custom_Domain")) {
         return this.runtimeMSC;
      } else if (domainType.equals("DomainCustom_Domain")) {
         return this.domainRTMSC;
      } else if (!domainType.equals("EditCustom_Domain") && !domainType.equals("ConfigEdit")) {
         return domainType.equals("JSR77") ? this.jsr77MSC : null;
      } else {
         return this.edit == null ? null : this.edit.msc;
      }
   }

   String getMBeanServerNameFromTree(String treeName) {
      if (treeName.equals("RuntimeConfigServerDomain")) {
         return "RuntimeMBeanServer";
      } else if (treeName.equals("RuntimeRuntimeServerDomain")) {
         return "RuntimeMBeanServer";
      } else if (treeName.equals("ConfigDomainRuntime")) {
         return "DomainRuntimeMBeanServer";
      } else if (treeName.equals("RuntimeDomainRuntime")) {
         return "DomainRuntimeMBeanServer";
      } else if (treeName.equals("Domain")) {
         return "DeprecatedMBeanServer";
      } else if (treeName.equals("DomainConfig")) {
         return "DeprecatedMBeanServer";
      } else if (treeName.equals("DomainRuntime")) {
         return "DeprecatedMBeanServer";
      } else if (treeName.equals("Custom_Domain")) {
         return "RuntimeMBeanServer";
      } else if (treeName.equals("DomainCustom_Domain")) {
         return "DomainRuntimeMBeanServer";
      } else if (treeName.equals("EditCustom_Domain")) {
         return "EditMBeanServer";
      } else if (treeName.equals("ConfigEdit")) {
         return "EditMBeanServer";
      } else {
         return treeName.equals("JSR77") ? "JSR77MBeanServer" : null;
      }
   }

   void getCustomMBeans(String objectNamePattern) throws ScriptException {
      this.customMBeanDomainObjNameMap.clear();

      try {
         ObjectName pattern;
         if (objectNamePattern != null) {
            pattern = new ObjectName(objectNamePattern);
         } else {
            pattern = new ObjectName("*:*");
         }

         MBeanServerConnection _mbsc;
         Set _s;
         Iterator __i;
         ObjectName objname;
         if (this.isCompatabilityServerEnabled) {
            _mbsc = this.getMBSConnection("Domain");
            _s = _mbsc.queryNames(pattern, (QueryExp)null);
            __i = _s.iterator();

            while(__i.hasNext()) {
               objname = (ObjectName)__i.next();
               if (this.isCustomMBean(objname, _mbsc)) {
                  addCustomMBeanToMap(objname, this.customMBeanDomainObjNameMap);
               }
            }
         }

         _mbsc = this.getMBSConnection("Custom_Domain");
         _s = _mbsc.queryNames(pattern, (QueryExp)null);
         __i = _s.iterator();

         while(__i.hasNext()) {
            objname = (ObjectName)__i.next();
            if (this.isCustomMBean(objname, _mbsc)) {
               addCustomMBeanToMap(objname, this.customMBeanDomainObjNameMap);
            }
         }
      } catch (Throwable var7) {
         this.throwWLSTException(this.txtFmt.getErrorGettingCustomMBeans(), var7);
      }

   }

   void getDomainCustomMBeans(String objectNamePattern) throws ScriptException {
      this.domainCustomMBeanDomainObjNameMap.clear();

      try {
         ObjectName pattern;
         if (objectNamePattern != null) {
            pattern = new ObjectName(objectNamePattern);
         } else {
            pattern = new ObjectName("*:*");
         }

         MBeanServerConnection _mbsc = this.getMBSConnection("DomainCustom_Domain");
         Set _s = _mbsc.queryNames(pattern, (QueryExp)null);
         Iterator __i = _s.iterator();

         while(__i.hasNext()) {
            ObjectName objname = (ObjectName)__i.next();
            if (this.isCustomMBean(objname, _mbsc)) {
               addCustomMBeanToMap(objname, this.domainCustomMBeanDomainObjNameMap);
            }
         }
      } catch (Throwable var7) {
         this.throwWLSTException(this.txtFmt.getErrorGettingCustomMBeans(), var7);
      }

   }

   void getEditCustomMBeans(String objectNamePattern) throws ScriptException {
      this.editCustomMBeanDomainObjNameMap.clear();

      try {
         ObjectName pattern;
         if (objectNamePattern != null) {
            pattern = new ObjectName(objectNamePattern);
         } else {
            pattern = new ObjectName("*:*");
         }

         MBeanServerConnection _mbsc = this.getMBSConnection("EditCustom_Domain");
         Set _s = _mbsc.queryNames(pattern, (QueryExp)null);
         Iterator __i = _s.iterator();

         while(__i.hasNext()) {
            ObjectName objname = (ObjectName)__i.next();
            if (this.isCustomMBean(objname, _mbsc)) {
               addCustomMBeanToMap(objname, this.editCustomMBeanDomainObjNameMap);
            }
         }
      } catch (Throwable var7) {
         this.throwWLSTException(this.txtFmt.getErrorGettingCustomMBeans(), var7);
      }

   }

   private boolean isCustomMBean(ObjectName on, MBeanServerConnection mbsc) throws Throwable {
      if (on == null) {
         return false;
      } else {
         try {
            if (!"Security".equals(on.getDomain()) && !"com.bea".equals(on.getDomain())) {
               MBeanInfo mbeanInfo = mbsc.getMBeanInfo(on);
               if (!(mbeanInfo instanceof ModelMBeanInfo)) {
                  return true;
               } else {
                  ModelMBeanInfo modelInfo = (ModelMBeanInfo)mbeanInfo;
                  Boolean custom = (Boolean)modelInfo.getMBeanDescriptor().getFieldValue("com.bea.custom");
                  if (custom != null && custom) {
                     return true;
                  } else {
                     String clzName = (String)modelInfo.getMBeanDescriptor().getFieldValue("interfaceclassname");
                     if (clzName != null) {
                        try {
                           this.mbeanTypeService.getMBeanInfo(clzName);
                           return false;
                        } catch (NoAccessRuntimeException var8) {
                           return false;
                        } catch (Exception var9) {
                           return true;
                        }
                     } else {
                        return true;
                     }
                  }
               }
            } else {
               return false;
            }
         } catch (Throwable var10) {
            return false;
         }
      }
   }

   static void addCustomMBeanToMap(ObjectName oname, TreeMap objectNameMap) {
      ArrayList onames = new ArrayList();
      synchronized(objectNameMap) {
         if (objectNameMap.containsKey(oname.getDomain())) {
            onames = (ArrayList)objectNameMap.get(oname.getDomain());
            onames.add(oname.toString());
         } else {
            onames.add(oname.toString());
         }

         objectNameMap.put(oname.getDomain(), onames);
      }
   }

   static void removeCustomMBeanFromMap(ObjectName oname, TreeMap objectNameMap) {
      synchronized(objectNameMap) {
         if (objectNameMap.containsKey(oname.getDomain())) {
            ArrayList onames = (ArrayList)objectNameMap.get(oname.getDomain());
            if (onames != null) {
               onames.remove(oname.toString());
               objectNameMap.put(oname.getDomain(), onames);
            }

         }
      }
   }

   boolean inConfigRT() {
      return this.domainType.equals("RuntimeConfigServerDomain") || this.domainType.equals("RuntimeRuntimeServerDomain");
   }

   boolean inDomainRT() {
      return this.domainType.equals("ConfigDomainRuntime") || this.domainType.equals("RuntimeDomainRuntime");
   }
}
