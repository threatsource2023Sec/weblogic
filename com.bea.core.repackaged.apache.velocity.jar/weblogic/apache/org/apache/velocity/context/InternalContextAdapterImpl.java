package weblogic.apache.org.apache.velocity.context;

import weblogic.apache.org.apache.velocity.app.event.EventCartridge;
import weblogic.apache.org.apache.velocity.runtime.resource.Resource;
import weblogic.apache.org.apache.velocity.util.introspection.IntrospectionCacheData;

public final class InternalContextAdapterImpl implements InternalContextAdapter {
   Context context = null;
   InternalHousekeepingContext icb = null;
   InternalEventContext iec = null;

   public InternalContextAdapterImpl(Context c) {
      this.context = c;
      if (!(c instanceof InternalHousekeepingContext)) {
         this.icb = new InternalContextBase();
      } else {
         this.icb = (InternalHousekeepingContext)this.context;
      }

      if (c instanceof InternalEventContext) {
         this.iec = (InternalEventContext)this.context;
      }

   }

   public void pushCurrentTemplateName(String s) {
      this.icb.pushCurrentTemplateName(s);
   }

   public void popCurrentTemplateName() {
      this.icb.popCurrentTemplateName();
   }

   public String getCurrentTemplateName() {
      return this.icb.getCurrentTemplateName();
   }

   public Object[] getTemplateNameStack() {
      return this.icb.getTemplateNameStack();
   }

   public IntrospectionCacheData icacheGet(Object key) {
      return this.icb.icacheGet(key);
   }

   public void icachePut(Object key, IntrospectionCacheData o) {
      this.icb.icachePut(key, o);
   }

   public void setCurrentResource(Resource r) {
      this.icb.setCurrentResource(r);
   }

   public Resource getCurrentResource() {
      return this.icb.getCurrentResource();
   }

   public Object put(String key, Object value) {
      return this.context.put(key, value);
   }

   public Object get(String key) {
      return this.context.get(key);
   }

   public boolean containsKey(Object key) {
      return this.context.containsKey(key);
   }

   public Object[] getKeys() {
      return this.context.getKeys();
   }

   public Object remove(Object key) {
      return this.context.remove(key);
   }

   public Context getInternalUserContext() {
      return this.context;
   }

   public InternalContextAdapter getBaseContext() {
      return this;
   }

   public EventCartridge attachEventCartridge(EventCartridge ec) {
      return this.iec != null ? this.iec.attachEventCartridge(ec) : null;
   }

   public EventCartridge getEventCartridge() {
      return this.iec != null ? this.iec.getEventCartridge() : null;
   }
}
