package org.jboss.weld.module.ejb.context.beanstore;

import java.util.Iterator;
import javax.interceptor.InvocationContext;
import org.jboss.weld.contexts.beanstore.AttributeBeanStore;
import org.jboss.weld.contexts.beanstore.LockStore;
import org.jboss.weld.contexts.beanstore.NamingScheme;

public class InvocationContextBeanStore extends AttributeBeanStore {
   private final InvocationContext ctx;

   public InvocationContextBeanStore(NamingScheme namingScheme, InvocationContext ctx) {
      super(namingScheme, false);
      this.ctx = ctx;
   }

   protected Object getAttribute(String prefixedId) {
      return this.ctx.getContextData().get(prefixedId);
   }

   protected void removeAttribute(String prefixedId) {
      this.ctx.getContextData().remove(prefixedId);
   }

   protected Iterator getAttributeNames() {
      return this.ctx.getContextData().keySet().iterator();
   }

   protected void setAttribute(String prefixedId, Object instance) {
      this.ctx.getContextData().put(prefixedId, instance);
   }

   public LockStore getLockStore() {
      return null;
   }
}
