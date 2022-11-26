package org.apache.openjpa.event;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.Arrays;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.UserException;

public class MethodLifecycleCallbacks implements LifecycleCallbacks, Externalizable {
   private static final Localizer _loc = Localizer.forPackage(MethodLifecycleCallbacks.class);
   private transient Method _callback;
   private boolean _arg;

   public MethodLifecycleCallbacks(Class cls, String method, boolean arg) {
      Class[] args = arg ? new Class[]{Object.class} : null;
      this._callback = getMethod(cls, method, args);
      this._arg = arg;
   }

   public MethodLifecycleCallbacks(Method method, boolean arg) {
      this._callback = method;
      this._arg = arg;
   }

   public Method getCallbackMethod() {
      return this._callback;
   }

   public boolean requiresArgument() {
      return this._arg;
   }

   public boolean hasCallback(Object obj, int eventType) {
      return true;
   }

   public void makeCallback(Object obj, Object arg, int eventType) throws Exception {
      if (!this._callback.isAccessible()) {
         AccessController.doPrivileged(J2DoPrivHelper.setAccessibleAction(this._callback, true));
      }

      if (this._arg) {
         this._callback.invoke(obj, arg);
      } else {
         this._callback.invoke(obj, (Object[])null);
      }

   }

   public String toString() {
      return this.getClass().getName() + ":" + this._callback;
   }

   protected static Method getMethod(Class cls, String method, Class[] args) {
      Class currentClass = cls;

      do {
         Method[] methods = (Method[])((Method[])AccessController.doPrivileged(J2DoPrivHelper.getDeclaredMethodsAction(currentClass)));

         for(int i = 0; i < methods.length; ++i) {
            if (method.equals(methods[i].getName()) && isAssignable(methods[i].getParameterTypes(), args)) {
               return methods[i];
            }
         }
      } while((currentClass = currentClass.getSuperclass()) != null);

      throw new UserException(_loc.get("method-notfound", cls.getName(), method, args == null ? null : Arrays.asList(args)));
   }

   private static boolean isAssignable(Class[] from, Class[] to) {
      if (from == null) {
         return to == null || to.length == 0;
      } else if (to != null) {
         if (from.length != to.length) {
            return false;
         } else {
            for(int i = 0; i < from.length; ++i) {
               if (from[i] != null && !from[i].isAssignableFrom(to[i])) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return from == null || from.length == 0;
      }
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      Class cls = (Class)in.readObject();
      String methName = (String)in.readObject();
      this._arg = in.readBoolean();
      Class[] args = this._arg ? new Class[]{Object.class} : null;
      this._callback = getMethod(cls, methName, args);
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeObject(this._callback.getClass());
      out.writeObject(this._callback.getName());
      out.writeBoolean(this._arg);
   }
}
