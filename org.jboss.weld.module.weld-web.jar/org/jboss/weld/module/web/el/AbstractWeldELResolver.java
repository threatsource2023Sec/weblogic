package org.jboss.weld.module.web.el;

import java.lang.reflect.Type;
import java.util.Iterator;
import javax.el.ELContext;
import javax.el.ELResolver;
import javax.enterprise.context.Dependent;
import javax.enterprise.inject.spi.Bean;
import org.jboss.weld.logging.ElLogger;
import org.jboss.weld.manager.BeanManagerImpl;

public abstract class AbstractWeldELResolver extends ELResolver {
   protected abstract BeanManagerImpl getManager(ELContext var1);

   public Class getCommonPropertyType(ELContext context, Object base) {
      return null;
   }

   public Iterator getFeatureDescriptors(ELContext context, Object base) {
      return null;
   }

   public Class getType(ELContext context, Object base, Object property) {
      return null;
   }

   public Object getValue(ELContext context, Object base, Object property) {
      BeanManagerImpl beanManager = this.getManager(context);
      if (property != null) {
         String propertyString = property.toString();
         ElLogger.LOG.propertyLookup(propertyString);
         Namespace namespace = null;
         Namespace value;
         if (base == null) {
            if (this.getRootNamespace().contains(propertyString)) {
               value = this.getRootNamespace().get(propertyString);
               context.setPropertyResolved(true);
               ElLogger.LOG.propertyResolved(propertyString, value);
               return value;
            }
         } else {
            if (!(base instanceof Namespace)) {
               return null;
            }

            namespace = (Namespace)base;
            context.setPropertyResolved(true);
            if (namespace.contains(propertyString)) {
               value = namespace.get(propertyString);
               ElLogger.LOG.propertyResolved(propertyString, value);
               return value;
            }
         }

         String name;
         if (namespace != null) {
            name = namespace.qualifyName(propertyString);
         } else {
            name = propertyString;
         }

         Object value = this.lookup(beanManager, context, name);
         if (value != null) {
            context.setPropertyResolved(true);
            ElLogger.LOG.propertyResolved(propertyString, value);
            return value;
         }
      }

      return null;
   }

   private Object lookup(BeanManagerImpl beanManager, ELContext context, String name) {
      Bean bean = beanManager.resolve(beanManager.getBeans(name));
      if (bean == null) {
         return null;
      } else {
         Class scope = bean.getScope();
         if (!scope.equals(Dependent.class)) {
            return beanManager.getReference(bean, (Type)null, beanManager.createCreationalContext(bean), true);
         } else {
            ELCreationalContextStack stack = ELCreationalContextStack.getCreationalContextStore(context);
            boolean release = stack.isEmpty();
            if (release) {
               stack.push(new CreationalContextCallable());
            }

            boolean var16 = false;

            Object var11;
            try {
               var16 = true;
               ELCreationalContext ctx = ((CreationalContextCallable)stack.peek()).get();
               String beanName = bean.getName();
               Object value = ctx.getDependentInstanceForExpression(beanName);
               if (value == null) {
                  value = this.getManager(context).getReference(bean, (Type)null, ctx, true);
                  ctx.registerDependentInstanceForExpression(beanName, value);
               }

               var11 = value;
               var16 = false;
            } finally {
               if (var16) {
                  if (release) {
                     CreationalContextCallable callable = (CreationalContextCallable)stack.pop();
                     if (callable.exists()) {
                        callable.get().release();
                     }
                  }

               }
            }

            if (release) {
               CreationalContextCallable callable = (CreationalContextCallable)stack.pop();
               if (callable.exists()) {
                  callable.get().release();
               }
            }

            return var11;
         }
      }
   }

   public boolean isReadOnly(ELContext context, Object base, Object property) {
      return false;
   }

   public void setValue(ELContext context, Object base, Object property, Object value) {
   }

   protected abstract Namespace getRootNamespace();
}
