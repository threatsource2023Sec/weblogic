package org.apache.openjpa.event;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UserException;

public class BeanLifecycleCallbacks extends MethodLifecycleCallbacks {
   private static final Localizer _loc = Localizer.forPackage(BeanLifecycleCallbacks.class);
   private transient Object _listener;

   public BeanLifecycleCallbacks(Class cls, String method, boolean arg, Class type) {
      this(cls, getMethod(cls, method, arg ? new Class[]{Object.class, type} : new Class[]{type}), arg);
   }

   public BeanLifecycleCallbacks(Class cls, Method method, boolean arg) {
      super(method, arg);
      this._listener = this.newListener(cls);
   }

   private Object newListener(Class cls) {
      try {
         return AccessController.doPrivileged(J2DoPrivHelper.newInstanceAction(cls));
      } catch (Throwable var3) {
         Throwable t = var3;
         if (var3 instanceof PrivilegedActionException) {
            t = ((PrivilegedActionException)var3).getException();
         }

         throw new UserException(_loc.get("bean-constructor", (Object)cls.getName()), (Throwable)t);
      }
   }

   public void makeCallback(Object obj, Object rel, int eventType) throws Exception {
      Method callback = this.getCallbackMethod();
      if (!callback.isAccessible()) {
         AccessController.doPrivileged(J2DoPrivHelper.setAccessibleAction(callback, true));
      }

      if (this.requiresArgument()) {
         callback.invoke(this._listener, obj, rel);
      } else {
         callback.invoke(this._listener, obj);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      super.readExternal(in);
      Class cls = (Class)in.readObject();
      this._listener = this.newListener(cls);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      super.writeExternal(out);
      out.writeObject(this._listener.getClass());
   }
}
