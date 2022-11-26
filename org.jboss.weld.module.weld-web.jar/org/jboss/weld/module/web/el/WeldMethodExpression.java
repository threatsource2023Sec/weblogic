package org.jboss.weld.module.web.el;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import org.jboss.weld.module.web.util.el.ForwardingMethodExpression;

public class WeldMethodExpression extends ForwardingMethodExpression {
   private static final long serialVersionUID = 7070020110515571744L;
   private final MethodExpression delegate;

   public WeldMethodExpression(MethodExpression delegate) {
      this.delegate = delegate;
   }

   protected MethodExpression delegate() {
      return this.delegate;
   }

   public Object invoke(ELContext context, Object[] params) {
      ELCreationalContextStack store = ELCreationalContextStack.getCreationalContextStore(context);
      boolean var9 = false;

      Object var4;
      try {
         var9 = true;
         store.push(new CreationalContextCallable());
         var4 = super.invoke(context, params);
         var9 = false;
      } finally {
         if (var9) {
            CreationalContextCallable callable = (CreationalContextCallable)store.pop();
            if (callable.exists()) {
               callable.get().release();
            }

         }
      }

      CreationalContextCallable callable = (CreationalContextCallable)store.pop();
      if (callable.exists()) {
         callable.get().release();
      }

      return var4;
   }

   public MethodInfo getMethodInfo(ELContext context) {
      ELCreationalContextStack store = ELCreationalContextStack.getCreationalContextStore(context);
      boolean var8 = false;

      MethodInfo var3;
      try {
         var8 = true;
         store.push(new CreationalContextCallable());
         var3 = super.getMethodInfo(context);
         var8 = false;
      } finally {
         if (var8) {
            CreationalContextCallable callable = (CreationalContextCallable)store.pop();
            if (callable.exists()) {
               callable.get().release();
            }

         }
      }

      CreationalContextCallable callable = (CreationalContextCallable)store.pop();
      if (callable.exists()) {
         callable.get().release();
      }

      return var3;
   }
}
