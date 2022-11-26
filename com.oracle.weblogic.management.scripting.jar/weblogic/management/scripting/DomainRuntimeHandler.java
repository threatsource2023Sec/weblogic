package weblogic.management.scripting;

import java.util.TreeMap;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import weblogic.management.jmx.MBeanServerInvocationHandler;
import weblogic.management.mbeanservers.domainruntime.DomainRuntimeServiceMBean;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;

public class DomainRuntimeHandler {
   private WLScriptContext ctx = null;
   private WLSTMsgTextFormatter txtFmt;
   private static final String DOMAIN_SERVICES = "DomainServices";
   private static final String SERVER_SERVICES = "ServerServices";

   DomainRuntimeHandler(WLScriptContext ctx) {
      this.ctx = ctx;
      this.txtFmt = ctx.getWLSTMsgFormatter();
   }

   Object ls(String attribute) {
      String s = (String)this.ctx.prompts.peek();
      if (s.equals("DomainServices")) {
         return this.listDomainServices();
      } else {
         return s.equals("ServerServices") ? this.listActiveServers() : this.listActiveServers();
      }
   }

   void cd(String attribute) throws ScriptException {
      if (this.ctx.inMBeanTypes) {
         String s = (String)this.ctx.prompts.peek();
         boolean fnd;
         ObjectName[] onames;
         if (s.equals("DomainServices")) {
            fnd = false;
            WLScriptContext var10001 = this.ctx;
            MBeanServerConnection mbs = this.ctx.getMBSConnection("RuntimeDomainRuntime");
            onames = this.getDomainServices((String)null);

            for(int i = 0; i < onames.length; ++i) {
               if (this.getName(onames[i], mbs).equals(attribute)) {
                  this.ctx.prompts.add(attribute);
                  this.ctx.prompt = this.ctx.evaluatePrompt();
                  this.ctx.browseHandler.changeToBeanLevel();
                  this.ctx.wlcmo = onames[i];
                  this.ctx.beans.push(this.ctx.wlcmo);
                  fnd = true;
                  break;
               }
            }

            if (!fnd) {
               this.ctx.throwWLSTException(this.txtFmt.getAttributeNotFound(attribute));
            }
         } else {
            ServerRuntimeMBean[] srBeans;
            int i;
            if (s.equals("ServerServices")) {
               fnd = false;
               srBeans = this.getServerRuntimes();

               for(i = 0; i < srBeans.length; ++i) {
                  if (srBeans[i].getName().equals(attribute)) {
                     fnd = true;
                     break;
                  }
               }

               if (!fnd) {
                  this.ctx.throwWLSTException(this.txtFmt.getAttributeNotFound(attribute));
               }

               onames = this.getDomainServices(attribute);
               this.ctx.wlInstanceObjNames = onames;
               this.ctx.prompts.add(attribute);
               this.ctx.prompt = this.ctx.evaluatePrompt();
               this.ctx.browseHandler.changeToMBeanTypesLevel();
               this.ctx.beans.push(this.ctx.wlcmo);
               this.ctx.browseHandler.populateNames(onames);
            } else {
               fnd = false;
               srBeans = this.getServerRuntimes();

               for(i = 0; i < srBeans.length; ++i) {
                  if (srBeans[i].getName().equals(attribute)) {
                     this.ctx.prompts.add(attribute);
                     this.ctx.prompt = this.ctx.evaluatePrompt();
                     this.ctx.browseHandler.changeToBeanLevel();
                     this.ctx.wlcmo = MBeanServerInvocationHandler.newProxyInstance(this.ctx.getMBSConnection(this.ctx.domainType), srBeans[i].getObjectName());
                     this.ctx.beans.push(this.ctx.wlcmo);
                     fnd = true;
                     break;
                  }
               }

               if (!fnd) {
                  this.ctx.throwWLSTException(this.txtFmt.getAttributeNotFound(attribute));
               }
            }
         }
      } else {
         this.ctx.prompts.add(attribute);
         this.ctx.prompt = this.ctx.evaluatePrompt();
         this.ctx.browseHandler.changeToMBeanTypesLevel();
         this.ctx.beans.push(this.ctx.wlcmo);
      }

   }

   ServerRuntimeMBean[] getServerRuntimes() {
      return this.ctx.domainRuntimeServiceMBean.getServerRuntimes();
   }

   Object listActiveServers() {
      ServerRuntimeMBean[] srBeans = this.getServerRuntimes();
      TreeMap attribPerms = new TreeMap();
      String perm = ((InformationHandler)InformationHandler.class.cast(this.ctx.infoHandler)).getPermission();

      for(int i = 0; i < srBeans.length; ++i) {
         attribPerms.put(srBeans[i].getName(), perm);
      }

      return this.ctx.printAttributes(attribPerms);
   }

   Object listDomainServices() {
      WLScriptContext var10001 = this.ctx;
      MBeanServerConnection mbs = this.ctx.getMBSConnection("RuntimeDomainRuntime");
      ObjectName[] srBeans = this.getDomainServices((String)null);
      TreeMap attribPerms = new TreeMap();
      String perm = ((InformationHandler)InformationHandler.class.cast(this.ctx.infoHandler)).getPermission();

      for(int i = 0; i < srBeans.length; ++i) {
         attribPerms.put(this.getName(srBeans[i], mbs), perm);
      }

      return this.ctx.printAttributes(attribPerms);
   }

   Object handleServerServicesNodeForLs() {
      return this.listActiveServers();
   }

   boolean isDomainRuntimeService(String name) {
      return "DomainServices".equals(name) || "ServerServices".equals(name);
   }

   void pop() throws ScriptException {
      if (this.ctx.atBeanLevel) {
         this.ctx.prompts.pop();
         this.ctx.beans.pop();
         this.ctx.browseHandler.changeToMBeanTypesLevel();
         this.ctx.prompt = this.ctx.evaluatePrompt();
      } else {
         String s1 = (String)this.ctx.prompts.elementAt(0);
         if (s1.equals("ServerServices") && this.ctx.prompts.size() > 1) {
            this.ctx.browseHandler.changeToMBeanTypesLevel();
            this.ctx.browseHandler.popAndPeek();
            this.ctx.prompt = this.ctx.evaluatePrompt();
            return;
         }

         this.ctx.browseHandler.popAndPeek();
         this.ctx.browseHandler.changeToBeanLevel();
         this.ctx.prompt = this.ctx.evaluatePrompt();
      }

   }

   private ObjectName[] getDomainServices(String serverName) {
      WLScriptContext var10001 = this.ctx;
      MBeanServerConnection mbs = this.ctx.getMBSConnection("RuntimeDomainRuntime");

      try {
         DomainRuntimeServiceMBean var10003 = this.ctx.domainRuntimeServiceMBean;
         ObjectName[] srBeans = (ObjectName[])((ObjectName[])mbs.invoke(new ObjectName(DomainRuntimeServiceMBean.OBJECT_NAME), "getServices", new Object[]{serverName}, new String[]{"java.lang.String"}));
         return srBeans;
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }

   private String getName(ObjectName obj, MBeanServerConnection mbs) {
      try {
         return (String)mbs.getAttribute(obj, "Name");
      } catch (Exception var4) {
         throw new RuntimeException(var4);
      }
   }
}
