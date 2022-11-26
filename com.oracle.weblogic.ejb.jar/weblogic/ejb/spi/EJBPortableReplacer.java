package weblogic.ejb.spi;

import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJBHome;
import javax.ejb.EJBMetaData;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import javax.ejb.HomeHandle;
import weblogic.ejb20.portable.PortableEJBMetaDataImpl;
import weblogic.ejb20.portable.PortableHandleImpl;
import weblogic.ejb20.portable.PortableHomeHandleImpl;
import weblogic.iiop.PortableReplaceable;
import weblogic.iiop.PortableReplacer;

public final class EJBPortableReplacer extends PortableReplacer {
   public static PortableReplacer getReplacer() {
      return EJBPortableReplacer.ReplacerMaker.PORTABLE_REPLACER;
   }

   public Serializable replaceObject(Serializable o) throws IOException {
      if (o instanceof PortableReplaceable) {
         if (o instanceof Handle) {
            Handle h = (Handle)o;
            EJBObject ejbObject = h.getEJBObject();
            return new PortableHandleImpl(ejbObject);
         }

         if (o instanceof HomeHandle) {
            HomeHandle h = (HomeHandle)o;
            EJBHome ejbHome = h.getEJBHome();
            return new PortableHomeHandleImpl(ejbHome);
         }

         if (o instanceof EJBMetaData) {
            return new PortableEJBMetaDataImpl((EJBMetaData)o);
         }
      }

      return o;
   }

   private static final class ReplacerMaker {
      private static final PortableReplacer PORTABLE_REPLACER = new EJBPortableReplacer();
   }
}
