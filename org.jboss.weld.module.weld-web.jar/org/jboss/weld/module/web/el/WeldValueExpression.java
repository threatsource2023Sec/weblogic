package org.jboss.weld.module.web.el;

import javax.el.ELContext;
import javax.el.ValueExpression;
import org.jboss.weld.module.web.util.el.ForwardingValueExpression;

public class WeldValueExpression extends ForwardingValueExpression {
   private static final long serialVersionUID = 1122137212009930853L;
   private final ValueExpression delegate;

   public WeldValueExpression(ValueExpression delegate) {
      this.delegate = delegate;
   }

   protected ValueExpression delegate() {
      return this.delegate;
   }

   public Object getValue(ELContext context) {
      ELCreationalContextStack store = ELCreationalContextStack.getCreationalContextStore(context);
      boolean var8 = false;

      Object var3;
      try {
         var8 = true;
         store.push(new CreationalContextCallable());
         var3 = this.delegate().getValue(context);
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

   public void setValue(ELContext context, Object value) {
      ELCreationalContextStack store = ELCreationalContextStack.getCreationalContextStore(context);
      boolean var8 = false;

      try {
         var8 = true;
         store.push(new CreationalContextCallable());
         this.delegate().setValue(context, value);
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

   }

   public boolean isReadOnly(ELContext context) {
      ELCreationalContextStack store = ELCreationalContextStack.getCreationalContextStore(context);
      boolean var8 = false;

      boolean var3;
      try {
         var8 = true;
         store.push(new CreationalContextCallable());
         var3 = this.delegate().isReadOnly(context);
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

   public Class getType(ELContext context) {
      ELCreationalContextStack store = ELCreationalContextStack.getCreationalContextStore(context);
      boolean var8 = false;

      Class var3;
      try {
         var8 = true;
         store.push(new CreationalContextCallable());
         var3 = this.delegate().getType(context);
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
