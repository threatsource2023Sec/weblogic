package weblogic.management.scripting;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.management.Descriptor;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.OperationsException;
import javax.management.Query;
import javax.management.QueryExp;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanInfo;
import weblogic.management.WebLogicMBean;
import weblogic.management.internal.Pair;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;
import weblogic.utils.StringUtils;

public class FindUtil {
   WLScriptContext ctx = null;
   FindUtil util = null;
   List haveISeenYou = new ArrayList();
   List beanToAttribute = new ArrayList();
   String delimiter = "\t";
   private WLSTMsgTextFormatter txtFmt;
   List didIScanYou = new ArrayList();
   List matchedAttributes = new ArrayList();

   public FindUtil(WLScriptContext ctx) {
      this.ctx = ctx;
      this.txtFmt = ctx.getWLSTMsgFormatter();
   }

   List findAttribute(String name, String searchInstancesOnly) throws ScriptException {
      this.ctx.println(this.txtFmt.getFindingNameInRegisteredInstances(name));
      boolean searchTypes = this.ctx.getBoolean(searchInstancesOnly);
      List fromInstances = this.findAttributeInCurrentTree(name);
      if (searchTypes) {
         return fromInstances;
      } else {
         this.ctx.println(this.txtFmt.getFindingNameInMBeanTypes(name));
         List typeAttrs = this.findAttributeInTypes(name);
         if (typeAttrs.isEmpty()) {
            this.ctx.println(this.txtFmt.getNoResultsFound());
            return fromInstances;
         } else {
            Iterator itr = typeAttrs.iterator();

            while(itr.hasNext()) {
               this.ctx.println((String)itr.next());
            }

            fromInstances.addAll(typeAttrs);
            return fromInstances;
         }
      }
   }

   List findTypeInTypes(String name) {
      this.didIScanYou = new ArrayList();
      this.matchedAttributes = new ArrayList();

      try {
         this.scanForTypes("weblogic.management.configuration.DomainMBean", name, "");
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      return this.matchedAttributes;
   }

   void scanForTypes(String interfaceName, String attrName, String parentAttr) throws ScriptException {
      if (!this.didIScanYou.contains(interfaceName)) {
         this.didIScanYou.add(interfaceName);

         try {
            ModelMBeanInfo modelInfo = this.ctx.mbeanTypeService.getMBeanInfo(interfaceName);
            MBeanAttributeInfo[] attrInfos = modelInfo.getAttributes();

            for(int j = 0; j < attrInfos.length; ++j) {
               ModelMBeanAttributeInfo attrInfo = (ModelMBeanAttributeInfo)attrInfos[j];
               Descriptor desc = attrInfo.getDescriptor();
               String s;
               if (this.childOrReference(desc)) {
                  s = this.getInterfaceClassName(desc);
                  if ("weblogic.management.WebLogicMBean".equals(s)) {
                     continue;
                  }

                  this.scanForTypes(s, attrName, parentAttr + "/" + attrInfo.getName());
               }

               if (attrInfo.getName().toLowerCase(Locale.US).indexOf(attrName.toLowerCase(Locale.US)) != -1) {
                  s = "";
                  s = s + parentAttr + "/" + attrInfo.getName();
                  if (this.childOrReference(desc)) {
                     this.matchedAttributes.add(s);
                  }
               }
            }
         } catch (Throwable var10) {
         }

      }
   }

   List findAttributeInTypes(String name) {
      this.didIScanYou = new ArrayList();
      this.matchedAttributes = new ArrayList();

      try {
         this.scanForAttributes("weblogic.management.configuration.DomainMBean", name, "");
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      return this.matchedAttributes;
   }

   void scanForAttributes(String interfaceName, String attrName, String parentAttr) throws ScriptException {
      if (!this.didIScanYou.contains(interfaceName)) {
         this.didIScanYou.add(interfaceName);

         try {
            ModelMBeanInfo modelInfo = this.ctx.mbeanTypeService.getMBeanInfo(interfaceName);
            MBeanAttributeInfo[] attrInfos = modelInfo.getAttributes();

            for(int j = 0; j < attrInfos.length; ++j) {
               ModelMBeanAttributeInfo attrInfo = (ModelMBeanAttributeInfo)attrInfos[j];
               Descriptor desc = attrInfo.getDescriptor();
               String s;
               if (this.childOrReference(desc)) {
                  s = this.getInterfaceClassName(desc);
                  if ("weblogic.management.WebLogicMBean".equals(s)) {
                     continue;
                  }

                  this.scanForAttributes(s, attrName, parentAttr + "/" + attrInfo.getName());
               }

               if (attrInfo.getName().toLowerCase(Locale.US).indexOf(attrName.toLowerCase(Locale.US)) != -1) {
                  s = "";
                  s = s + parentAttr + this.ctx.calculateTabSpace(parentAttr) + " " + attrInfo.getName();
                  this.matchedAttributes.add(s);
               }
            }
         } catch (OperationsException var10) {
            this.ctx.printDebug("Warning: " + var10.getMessage() + ". The MBean is coming from " + parentAttr);
         } catch (Throwable var11) {
            this.ctx.throwWLSTException(this.txtFmt.getErrorInScanningForAttrs(), var11);
         }

      }
   }

   private boolean childOrReference(Descriptor desc) {
      String val = (String)desc.getFieldValue("com.bea.relationship");
      if (val != null && val.equals("containment")) {
         Boolean _val = (Boolean)desc.getFieldValue("com.bea.exclude");
         return _val == null || !_val;
      } else {
         return false;
      }
   }

   String getInterfaceClassName(Descriptor desc) {
      String intClzName = (String)desc.getFieldValue("interfaceClassName");
      if (intClzName.startsWith("[L")) {
         intClzName = intClzName.substring(2, intClzName.length() - 1);
      }

      return intClzName;
   }

   List findMBean(String name, String type, String searchInstancesOnly) throws ScriptException {
      this.ctx.println(this.ctx.getWLSTMsgFormatter().getFindBeanOfType(type));
      boolean bool = this.ctx.getBoolean(searchInstancesOnly);
      List list = this.findMBeanInCurrentTree(name, type);
      if (!bool) {
         this.ctx.println(this.ctx.getWLSTMsgFormatter().getNowFinding(type));
         List _list = this.findTypeInTypes(type);
         if (_list.isEmpty()) {
            this.ctx.println(this.ctx.getWLSTMsgFormatter().getFindByTypeResultEmpty());
            return list;
         }

         Iterator itr = _list.iterator();

         while(itr.hasNext()) {
            this.ctx.println((String)itr.next());
         }

         list.addAll(_list);
      }

      return list;
   }

   private List findAttributeInThisTree(String name, ObjectName root) throws ScriptException {
      this.haveISeenYou = new ArrayList();
      this.beanToAttribute = new ArrayList();
      this.walkTheTree(root, name);
      Iterator iter = this.beanToAttribute.iterator();
      List list = new ArrayList();

      while(true) {
         String attrName;
         String value;
         String s;
         do {
            do {
               do {
                  if (!iter.hasNext()) {
                     if (list.isEmpty()) {
                        this.ctx.println(this.ctx.getWLSTMsgFormatter().getFindByInstanceResultEmpty());
                     }

                     return list;
                  }

                  Pair pair = (Pair)iter.next();
                  ObjectName on = (ObjectName)pair.getKey();
                  Pair attrNameValue = (Pair)pair.getValue();
                  attrName = (String)attrNameValue.getKey();
                  value = null;
                  if (attrNameValue.getValue() != null) {
                     value = attrNameValue.getValue().toString();
                  }

                  s = this.lookupPath(on);
               } while(s == null);
            } while(this.ctx.isAnythingInThisExcluded(s));
         } while(!this.doesThisBelongHere(s));

         if (s.startsWith("Domains") || s.startsWith("DomainRuntimes") || s.startsWith("ServerRuntimes")) {
            s = "";
         }

         s = "/" + s + this.ctx.calculateTabSpace(s) + " " + attrName + this.ctx.calculateTabSpace(attrName, 50) + " " + value;
         this.ctx.println(s);
         list.add(s);
      }
   }

   private boolean doesThisBelongHere(String path) {
      if (path == null) {
         return false;
      } else {
         String[] mbeans;
         int i;
         if (!this.ctx.domainType.equals("ConfigDomainRuntime") && !this.ctx.domainType.equals("RuntimeConfigServerDomain")) {
            if (!this.ctx.domainType.equals("RuntimeDomainRuntime") && !this.ctx.domainType.equals("RuntimeRuntimeServerDomain")) {
               return true;
            } else {
               mbeans = StringUtils.splitCompletely(path, "/");

               for(i = 0; i < mbeans.length; ++i) {
                  if (mbeans[i].endsWith("Runtime") || mbeans[i].endsWith("Runtimes")) {
                     return true;
                  }
               }

               return false;
            }
         } else {
            mbeans = StringUtils.splitCompletely(path, "/");

            for(i = 0; i < mbeans.length; ++i) {
               if (mbeans[i].endsWith("Runtime") || mbeans[i].endsWith("Runtimes")) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private List findAttributeInCurrentTree(String name) throws ScriptException {
      WebLogicMBean root = this.ctx.getRootMBean(this.ctx.domainType);
      return this.findAttributeInThisTree(name, root.getObjectName());
   }

   private void walkTheTree(ObjectName root, String name) throws ScriptException {
      MBeanServerConnection connection = this.ctx.getMBSConnection(this.ctx.domainType);
      MBeanAttributeInfo[] info = null;

      try {
         info = connection.getMBeanInfo(root).getAttributes();
      } catch (InstanceNotFoundException var13) {
         this.ctx.printDebug("MBean of Type '" + root.getKeyProperty("Type") + "' and ObjectName " + root + "is EXCLUDED but its parents getter is Not excluded.");
         return;
      } catch (Throwable var14) {
         this.ctx.throwWLSTException(this.txtFmt.getErrorGettingAttributes(), var14);
      }

      if (!this.haveISeenYou.contains(root)) {
         this.haveISeenYou.add(root);
         this.ctx.printDebug("Added " + root + " to the already seen list");

         try {
            for(int i = 0; i < info.length; ++i) {
               MBeanAttributeInfo attrInfo = null;
               ModelMBeanAttributeInfo model_ainfo = null;
               attrInfo = info[i];
               model_ainfo = (ModelMBeanAttributeInfo)attrInfo;
               String attrName = attrInfo.getName();
               this.ctx.printDebug("    Processing attribute " + attrName + " of " + root);
               if (attrInfo.getName().toLowerCase(Locale.US).indexOf(name.toLowerCase(Locale.US)) != -1) {
                  Pair pair = null;
                  if (((InformationHandler)InformationHandler.class.cast(this.ctx.infoHandler)).isEncrypted(model_ainfo)) {
                     pair = new Pair(attrInfo.getName(), "******");
                  } else {
                     pair = new Pair(attrInfo.getName(), connection.getAttribute(root, attrInfo.getName()));
                  }

                  Pair onToAttrNameValue = new Pair(root, pair);
                  this.beanToAttribute.add(onToAttrNameValue);
               }

               if (attrInfo.getType().startsWith("[L") && attrInfo.getType().indexOf("ObjectName") != -1) {
                  ObjectName[] beans = (ObjectName[])((ObjectName[])connection.getAttribute(root, attrInfo.getName()));
                  if (beans != null) {
                     for(int j = 0; j < beans.length; ++j) {
                        this.walkTheTree(beans[j], name);
                     }
                  }
               } else if (attrInfo.getType().indexOf("ObjectName") != -1 && !attrInfo.getType().equals("weblogic.management.WebLogicMBean") && !attrInfo.getName().equals("Parent") && !attrInfo.getName().equals("MBeanInfo")) {
                  ObjectName bean = null;
                  Object retObj = connection.getAttribute(root, attrInfo.getName());
                  if (retObj != null) {
                     if (retObj instanceof ObjectName) {
                        bean = (ObjectName)retObj;
                     }

                     if (bean != null) {
                        try {
                           this.walkTheTree(bean, name);
                        } catch (Exception var12) {
                           System.out.println(this.txtFmt.getExceptionWalkingBean("" + bean, "" + var12));
                           var12.printStackTrace();
                        }
                     }
                  }
               }
            }
         } catch (Throwable var15) {
            this.ctx.throwWLSTException(this.txtFmt.getErrorWalkingTheTree(), var15);
         }

      }
   }

   private List findMBeanInThisTree(String name, String type, MBeanServerConnection connection) throws ScriptException {
      if (name == null) {
         name = "*";
      }

      QueryExp exp = Query.match(Query.attr("Name"), Query.value(name));

      try {
         Set objNames = connection.queryNames(new ObjectName("*:*"), exp);
         this.ctx.printDebug("Found " + objNames.size() + " mbeans in this tree ...");
         Iterator iter = objNames.iterator();
         List list = new ArrayList();

         while(true) {
            ObjectName on;
            do {
               do {
                  do {
                     if (!iter.hasNext()) {
                        List pathList = new ArrayList();
                        Iterator it = list.iterator();

                        while(it.hasNext()) {
                           ObjectName objName = (ObjectName)it.next();
                           this.ctx.printDebug("Evaluating path for " + objName);
                           String path = this.lookupPath(objName);
                           if (!this.ctx.isAnythingInThisExcluded(path) && this.doesThisBelongHere(path)) {
                              pathList.add("/" + path);
                           }
                        }

                        if (pathList.isEmpty()) {
                           this.ctx.println(this.ctx.getWLSTMsgFormatter().getFindByInstanceResultEmpty());
                        }

                        Iterator iterator = pathList.iterator();

                        while(iterator.hasNext()) {
                           this.ctx.println((String)iterator.next());
                        }

                        return pathList;
                     }

                     on = (ObjectName)iter.next();
                     this.ctx.printDebug("  Observing mbean " + on);
                  } while(on.getKeyProperty("Type") == null);
               } while(on.getKeyProperty("Type").toLowerCase(Locale.US).indexOf(type.toLowerCase(Locale.US)) == -1);
            } while(on.getKeyProperty("Type").equals("MessageDrivenControlEJBRuntime") && on.getKeyProperty("Name").equals("MessageDrivenControlEJBRuntime"));

            this.ctx.printDebug("MBean " + on + " matched the query and added for further examination");
            list.add(on);
         }
      } catch (Throwable var12) {
         this.ctx.throwWLSTException(this.txtFmt.getErrorFindingTheMBean(), var12);
         return null;
      }
   }

   private List findMBeanInCurrentTree(String name, String type) throws ScriptException {
      MBeanServerConnection connection = this.ctx.getMBSConnection(this.ctx.domainType);
      return this.findMBeanInThisTree(name, type, connection);
   }

   public String lookupPath(ObjectName on) {
      try {
         return WLSTPathUtil.lookupPath(this.ctx.getMBeanServer(), this.ctx.getDomainName(), on);
      } catch (Throwable var3) {
         this.ctx.printDebug("Error while looking up the path ");
         return null;
      }
   }
}
