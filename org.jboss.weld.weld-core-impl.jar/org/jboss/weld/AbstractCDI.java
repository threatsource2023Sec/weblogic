package org.jboss.weld;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import javax.enterprise.context.spi.Contextual;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.Unmanaged;
import javax.enterprise.util.TypeLiteral;
import org.jboss.weld.bean.builtin.BeanManagerProxy;
import org.jboss.weld.bean.builtin.PriorityComparator;
import org.jboss.weld.inject.WeldInstance;
import org.jboss.weld.logging.BeanManagerLogger;
import org.jboss.weld.util.AnnotationApiAbstraction;
import org.jboss.weld.util.cache.ComputingCache;
import org.jboss.weld.util.cache.ComputingCacheBuilder;
import org.jboss.weld.util.collections.ImmutableSet;
import org.jboss.weld.util.reflection.Reflections;

public abstract class AbstractCDI extends CDI implements WeldInstance {
   protected final Set knownClassNames;
   private final ComputingCache instanceCache;

   public AbstractCDI() {
      ImmutableSet.Builder names = ImmutableSet.builder();

      for(Class clazz = this.getClass(); clazz != CDI.class; clazz = clazz.getSuperclass()) {
         names.add(clazz.getName());
      }

      names.add(Unmanaged.class.getName());
      this.knownClassNames = names.build();
      this.instanceCache = ComputingCacheBuilder.newBuilder().build((b) -> {
         return (WeldInstance)Reflections.cast(b.getInstance(b.createCreationalContext((Contextual)null)));
      });
   }

   public Iterator iterator() {
      return this.instanceInternal().iterator();
   }

   public Object get() {
      return this.instanceInternal().get();
   }

   public WeldInstance select(Annotation... qualifiers) {
      return this.instanceInternal().select(qualifiers);
   }

   public WeldInstance select(Class subtype, Annotation... qualifiers) {
      return this.instanceInternal().select(subtype, qualifiers);
   }

   public WeldInstance select(TypeLiteral subtype, Annotation... qualifiers) {
      return this.instanceInternal().select(subtype, qualifiers);
   }

   public WeldInstance select(Type subtype, Annotation... qualifiers) {
      return this.instanceInternal().select(subtype, qualifiers);
   }

   public boolean isUnsatisfied() {
      return this.instanceInternal().isUnsatisfied();
   }

   public boolean isAmbiguous() {
      return this.instanceInternal().isAmbiguous();
   }

   public void destroy(Object instance) {
      this.instanceInternal().destroy(instance);
   }

   public WeldInstance.Handler getHandler() {
      return this.getInstance().getHandler();
   }

   public boolean isResolvable() {
      return this.getInstance().isResolvable();
   }

   public Iterable handlers() {
      return this.getInstance().handlers();
   }

   public Comparator getPriorityComparator() {
      return new PriorityComparator((AnnotationApiAbstraction)BeanManagerProxy.unwrap(this.getBeanManager()).getServices().get(AnnotationApiAbstraction.class));
   }

   protected String getCallingClassName() {
      boolean outerSubclassReached = false;
      StackTraceElement[] var2 = Thread.currentThread().getStackTrace();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         StackTraceElement element = var2[var4];
         if (!this.knownClassNames.contains(element.getClassName())) {
            if (outerSubclassReached) {
               return element.getClassName();
            }
         } else {
            outerSubclassReached = true;
         }
      }

      throw BeanManagerLogger.LOG.unableToIdentifyBeanManager();
   }

   private WeldInstance instanceInternal() {
      this.checkState();
      return this.getInstance();
   }

   protected WeldInstance getInstance() {
      return (WeldInstance)this.instanceCache.getValue(BeanManagerProxy.unwrap(this.getBeanManager()));
   }

   protected void checkState() {
   }
}
