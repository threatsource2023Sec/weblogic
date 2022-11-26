package weblogic.iiop.jndi;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameNotFoundException;
import javax.naming.NamingException;
import javax.naming.spi.ObjectFactory;
import weblogic.corba.j2ee.naming.ORBHelper;
import weblogic.ejb20.portable.HandleDelegateImpl;
import weblogic.kernel.KernelStatus;

public class IiopObjectFactory implements ObjectFactory {
   private static Object orb;
   private static Object handleDelegate;

   public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment) throws Exception {
      if (name.toString().equals("ORB")) {
         return this.getOrb();
      } else if (name.toString().equals("HandleDelegate")) {
         return this.getHandleDelegate();
      } else {
         throw new NameNotFoundException();
      }
   }

   private Object getOrb() throws NamingException {
      if (orb != null) {
         return orb;
      } else {
         Class var1 = IiopObjectFactory.class;
         synchronized(IiopObjectFactory.class) {
            if (orb == null) {
               orb = ORBHelper.getORBHelper().getLocalORB();
            }
         }

         return orb;
      }
   }

   private Object getHandleDelegate() {
      if (handleDelegate != null) {
         return handleDelegate;
      } else {
         Class var1 = IiopObjectFactory.class;
         synchronized(IiopObjectFactory.class) {
            if (handleDelegate == null) {
               handleDelegate = this.createHandleDelegate();
            }
         }

         return handleDelegate;
      }
   }

   private Object createHandleDelegate() {
      return KernelStatus.isServer() ? new HandleDelegateImpl() : new weblogic.ejb20.internal.HandleDelegateImpl();
   }
}
