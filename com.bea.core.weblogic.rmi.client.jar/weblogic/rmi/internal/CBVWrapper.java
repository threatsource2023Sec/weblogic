package weblogic.rmi.internal;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.concurrent.Future;
import weblogic.rmi.extensions.server.CBVInputStream;
import weblogic.rmi.extensions.server.CBVOutputStream;

public abstract class CBVWrapper {
   public abstract Remote getDelegate();

   protected final Object copy(Object o) {
      Object replacement = RMIEnvironment.getEnvironment().replaceSpecialCBVObject(o);
      if (replacement != null) {
         return replacement;
      } else {
         if (o instanceof Remote) {
            if (o instanceof CBVWrapper) {
               return o;
            }

            RuntimeDescriptor desc;
            try {
               desc = DescriptorManager.getDescriptor(o);
            } catch (RemoteException var8) {
               throw new AssertionError(var8);
            }

            if (desc.getEnforceCallByValue()) {
               return getCBVWrapper(desc, o);
            }
         }

         if (o instanceof Serializable) {
            try {
               CBVOutputStream out = new CBVOutputStream();
               out.writeObject(o);
               out.flush();
               CBVInputStream in = out.makeCBVInputStream();
               Object returnValue = in.readObject();
               out.close();
               in.close();
               return returnValue;
            } catch (IOException var6) {
               throw new AssertionError(var6);
            } catch (ClassNotFoundException var7) {
               throw new AssertionError(var7);
            }
         } else {
            return o;
         }
      }
   }

   public static CBVWrapper getCBVWrapper(RuntimeDescriptor desc, Object impl) {
      try {
         Constructor cons = desc.getCBVWrapper();
         CBVWrapper wrapper = (CBVWrapper)cons.newInstance(impl);
         return wrapper;
      } catch (IllegalAccessException var4) {
         throw new AssertionError(var4);
      } catch (InstantiationException var5) {
         throw new AssertionError(var5);
      } catch (InvocationTargetException var6) {
         throw new AssertionError(var6);
      }
   }

   protected final Future getFutureWrapper(Future f) {
      return new CBVFutureImpl(f, this);
   }
}
