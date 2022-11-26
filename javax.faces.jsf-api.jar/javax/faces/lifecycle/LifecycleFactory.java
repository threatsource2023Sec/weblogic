package javax.faces.lifecycle;

import java.util.Iterator;

public abstract class LifecycleFactory {
   public static final String DEFAULT_LIFECYCLE = "DEFAULT";

   public abstract void addLifecycle(String var1, Lifecycle var2);

   public abstract Lifecycle getLifecycle(String var1);

   public abstract Iterator getLifecycleIds();
}
