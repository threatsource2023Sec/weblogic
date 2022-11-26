package javax.enterprise.inject.spi;

import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;

public class Unmanaged {
   private final InjectionTarget injectionTarget;
   private final BeanManager beanManager;

   public Unmanaged(BeanManager manager, Class clazz) {
      this.beanManager = manager;
      AnnotatedType type = manager.createAnnotatedType(clazz);
      this.injectionTarget = manager.getInjectionTargetFactory(type).createInjectionTarget((Bean)null);
   }

   public Unmanaged(Class clazz) {
      this(CDI.current().getBeanManager(), clazz);
   }

   public UnmanagedInstance newInstance() {
      return new UnmanagedInstance(this.beanManager, this.injectionTarget);
   }

   public static class UnmanagedInstance {
      private final CreationalContext ctx;
      private final InjectionTarget injectionTarget;
      private Object instance;
      private boolean disposed;

      private UnmanagedInstance(BeanManager beanManager, InjectionTarget injectionTarget) {
         this.disposed = false;
         this.injectionTarget = injectionTarget;
         this.ctx = beanManager.createCreationalContext((Contextual)null);
      }

      public Object get() {
         return this.instance;
      }

      public UnmanagedInstance produce() {
         if (this.instance != null) {
            throw new IllegalStateException("Trying to call produce() on already constructed instance");
         } else if (this.disposed) {
            throw new IllegalStateException("Trying to call produce() on an already disposed instance");
         } else {
            this.instance = this.injectionTarget.produce(this.ctx);
            return this;
         }
      }

      public UnmanagedInstance inject() {
         if (this.instance == null) {
            throw new IllegalStateException("Trying to call inject() before produce() was called");
         } else if (this.disposed) {
            throw new IllegalStateException("Trying to call inject() on already disposed instance");
         } else {
            this.injectionTarget.inject(this.instance, this.ctx);
            return this;
         }
      }

      public UnmanagedInstance postConstruct() {
         if (this.instance == null) {
            throw new IllegalStateException("Trying to call postConstruct() before produce() was called");
         } else if (this.disposed) {
            throw new IllegalStateException("Trying to call postConstruct() on already disposed instance");
         } else {
            this.injectionTarget.postConstruct(this.instance);
            return this;
         }
      }

      public UnmanagedInstance preDestroy() {
         if (this.instance == null) {
            throw new IllegalStateException("Trying to call preDestroy() before produce() was called");
         } else if (this.disposed) {
            throw new IllegalStateException("Trying to call preDestroy() on already disposed instance");
         } else {
            this.injectionTarget.preDestroy(this.instance);
            return this;
         }
      }

      public UnmanagedInstance dispose() {
         if (this.instance == null) {
            throw new IllegalStateException("Trying to call dispose() before produce() was called");
         } else if (this.disposed) {
            throw new IllegalStateException("Trying to call dispose() on already disposed instance");
         } else {
            this.disposed = true;
            this.injectionTarget.dispose(this.instance);
            this.ctx.release();
            return this;
         }
      }

      // $FF: synthetic method
      UnmanagedInstance(BeanManager x0, InjectionTarget x1, Object x2) {
         this(x0, x1);
      }
   }
}
