package weblogic.apache.org.apache.velocity.context;

import java.util.HashMap;
import weblogic.apache.org.apache.velocity.app.event.EventCartridge;
import weblogic.apache.org.apache.velocity.runtime.RuntimeServices;
import weblogic.apache.org.apache.velocity.runtime.directive.VMProxyArg;
import weblogic.apache.org.apache.velocity.runtime.resource.Resource;
import weblogic.apache.org.apache.velocity.util.introspection.IntrospectionCacheData;

public class VMContext implements InternalContextAdapter {
   HashMap vmproxyhash = new HashMap();
   HashMap localcontext = new HashMap();
   InternalContextAdapter innerContext = null;
   InternalContextAdapter wrappedContext = null;
   private boolean localcontextscope = false;

   public VMContext(InternalContextAdapter inner, RuntimeServices rsvc) {
      this.localcontextscope = rsvc.getBoolean("velocimacro.context.localscope", false);
      this.wrappedContext = inner;
      this.innerContext = inner.getBaseContext();
   }

   public Context getInternalUserContext() {
      return this.innerContext.getInternalUserContext();
   }

   public InternalContextAdapter getBaseContext() {
      return this.innerContext.getBaseContext();
   }

   public void addVMProxyArg(VMProxyArg vmpa) {
      String key = vmpa.getContextReference();
      if (vmpa.isConstant()) {
         this.localcontext.put(key, vmpa.getObject(this.wrappedContext));
      } else {
         this.vmproxyhash.put(key, vmpa);
      }

   }

   public Object put(String key, Object value) {
      VMProxyArg vmpa = (VMProxyArg)this.vmproxyhash.get(key);
      if (vmpa != null) {
         return vmpa.setObject(this.wrappedContext, value);
      } else if (this.localcontextscope) {
         return this.localcontext.put(key, value);
      } else {
         return this.localcontext.containsKey(key) ? this.localcontext.put(key, value) : this.innerContext.put(key, value);
      }
   }

   public Object get(String key) {
      Object o = null;
      VMProxyArg vmpa = (VMProxyArg)this.vmproxyhash.get(key);
      if (vmpa != null) {
         o = vmpa.getObject(this.wrappedContext);
      } else if (this.localcontextscope) {
         o = this.localcontext.get(key);
      } else {
         o = this.localcontext.get(key);
         if (o == null) {
            o = this.innerContext.get(key);
         }
      }

      return o;
   }

   public boolean containsKey(Object key) {
      return false;
   }

   public Object[] getKeys() {
      return this.vmproxyhash.keySet().toArray();
   }

   public Object remove(Object key) {
      return this.vmproxyhash.remove(key);
   }

   public void pushCurrentTemplateName(String s) {
      this.innerContext.pushCurrentTemplateName(s);
   }

   public void popCurrentTemplateName() {
      this.innerContext.popCurrentTemplateName();
   }

   public String getCurrentTemplateName() {
      return this.innerContext.getCurrentTemplateName();
   }

   public Object[] getTemplateNameStack() {
      return this.innerContext.getTemplateNameStack();
   }

   public IntrospectionCacheData icacheGet(Object key) {
      return this.innerContext.icacheGet(key);
   }

   public void icachePut(Object key, IntrospectionCacheData o) {
      this.innerContext.icachePut(key, o);
   }

   public EventCartridge attachEventCartridge(EventCartridge ec) {
      return this.innerContext.attachEventCartridge(ec);
   }

   public EventCartridge getEventCartridge() {
      return this.innerContext.getEventCartridge();
   }

   public void setCurrentResource(Resource r) {
      this.innerContext.setCurrentResource(r);
   }

   public Resource getCurrentResource() {
      return this.innerContext.getCurrentResource();
   }
}
