package weblogic.ejb.container.internal;

import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import weblogic.ejb20.internal.HandleImpl;
import weblogic.ejb20.internal.HomeHandleImpl;
import weblogic.ejb20.portable.PortableHandleImpl;
import weblogic.ejb20.portable.PortableHomeHandleImpl;

public final class HandleReplacer {
   private static HandleReplacer handleReplacer = null;

   public static HandleReplacer getReplacer() {
      if (handleReplacer == null) {
         handleReplacer = new HandleReplacer();
      }

      return handleReplacer;
   }

   public Serializable replaceObject(Serializable o) throws IOException {
      if (o instanceof HandleImpl) {
         HandleImpl h = (HandleImpl)o;
         EJBObject ejbObject = h.getEJBObject();
         PortableHandleImpl portableHandle = new PortableHandleImpl(ejbObject);
         return portableHandle;
      } else if (o instanceof HomeHandleImpl) {
         HomeHandleImpl h = (HomeHandleImpl)o;
         EJBHome ejbHome = h.getEJBHome();
         PortableHomeHandleImpl portableHomeHandle = new PortableHomeHandleImpl(ejbHome);
         return portableHomeHandle;
      } else {
         return o;
      }
   }
}
