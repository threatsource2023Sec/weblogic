package javax.faces.lifecycle;

import java.util.Iterator;
import javax.faces.FacesWrapper;

public abstract class LifecycleFactory implements FacesWrapper {
   private LifecycleFactory wrapped;
   public static final String DEFAULT_LIFECYCLE = "DEFAULT";

   /** @deprecated */
   @Deprecated
   public LifecycleFactory() {
   }

   public LifecycleFactory(LifecycleFactory wrapped) {
      this.wrapped = wrapped;
   }

   public LifecycleFactory getWrapped() {
      return this.wrapped;
   }

   public abstract void addLifecycle(String var1, Lifecycle var2);

   public abstract Lifecycle getLifecycle(String var1);

   public abstract Iterator getLifecycleIds();
}
