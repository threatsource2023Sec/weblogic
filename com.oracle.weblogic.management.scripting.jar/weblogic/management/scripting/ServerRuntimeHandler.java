package weblogic.management.scripting;

import java.util.TreeMap;
import javax.management.ObjectName;
import weblogic.management.jmx.MBeanServerInvocationHandler;
import weblogic.management.mbeanservers.Service;
import weblogic.management.runtime.ServerRuntimeMBean;

public class ServerRuntimeHandler {
   WLScriptContext ctx = null;

   ServerRuntimeHandler(WLScriptContext ctx) {
      this.ctx = ctx;
   }

   Object ls(String attribute) {
      return this.listServerServices();
   }

   Object listServerServices() {
      Service[] srBeans = this.ctx.runtimeServiceMBean.getServices();
      TreeMap attribPerms = new TreeMap();
      String perm = ((InformationHandler)InformationHandler.class.cast(this.ctx.infoHandler)).getPermission();

      for(int i = 0; i < srBeans.length; ++i) {
         attribPerms.put(srBeans[i].getName(), perm);
      }

      return this.ctx.printAttributes(attribPerms);
   }

   ServerRuntimeMBean[] getServerRuntimes() {
      return this.ctx.domainRuntimeServiceMBean.getServerRuntimes();
   }

   void cd(String attribute) throws Throwable {
      ServerRuntimeMBean[] srBeans = this.getServerRuntimes();
      if (this.ctx.inMBeanTypes) {
         boolean fnd = false;

         for(int i = 0; i < srBeans.length; ++i) {
            if (srBeans[i].getName().equals(attribute)) {
               this.ctx.prompts.add(attribute);
               this.ctx.prompt = this.ctx.evaluatePrompt();
               this.ctx.atBeanLevel = true;
               this.ctx.inMBeanType = false;
               this.ctx.inMBeanTypes = false;
               this.ctx.wlcmo = MBeanServerInvocationHandler.newProxyInstance(this.ctx.getMBSConnection(this.ctx.domainType), srBeans[i].getObjectName());
               this.ctx.beans.push(this.ctx.wlcmo);
               fnd = true;
               break;
            }
         }

         if (!fnd) {
            this.ctx.throwWLSTException(this.ctx.getWLSTMsgFormatter().getAttributeNotFound(attribute));
         }
      } else {
         this.ctx.prompts.add(attribute);
         this.ctx.prompt = this.ctx.evaluatePrompt();
         this.ctx.atBeanLevel = false;
         this.ctx.inMBeanType = false;
         this.ctx.inMBeanTypes = true;
         this.ctx.beans.push(this.ctx.wlcmo);
         Service[] beans = this.ctx.runtimeServiceMBean.getServices();
         ObjectName[] onames = new ObjectName[beans.length];

         for(int i = 0; i < beans.length; ++i) {
            onames[i] = this.ctx.getObjectName(beans[i]);
         }

         this.ctx.wlInstanceObjNames = onames;
         this.ctx.browseHandler.populateNames(onames);
      }

   }

   void pop() throws ScriptException {
      if (this.ctx.atBeanLevel) {
         this.ctx.prompts.pop();
         this.ctx.beans.pop();
         this.ctx.atBeanLevel = false;
         this.ctx.inMBeanType = false;
         this.ctx.inMBeanTypes = true;
         this.ctx.prompt = this.ctx.evaluatePrompt();
      } else {
         this.ctx.prompts.pop();
         this.ctx.beans.pop();
         this.ctx.atBeanLevel = true;
         this.ctx.inMBeanType = false;
         this.ctx.inMBeanTypes = false;
         this.ctx.wlcmo = this.ctx.beans.peek();
         this.ctx.prompt = this.ctx.evaluatePrompt();
      }

   }
}
