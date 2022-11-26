package weblogic.ejb.container.internal;

import javax.ejb.EJBException;
import javax.ejb.EJBLocalHome;
import javax.ejb.EJBLocalObject;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.interfaces.BaseEJBLocalObjectIntf;
import weblogic.ejb20.interfaces.LocalHandle;
import weblogic.ejb20.internal.LocalHandleImpl;
import weblogic.logging.Loggable;

public abstract class StatelessEJBLocalObject extends StatelessLocalObject implements BaseEJBLocalObjectIntf {
   private BaseEJBLocalHome ejbLocalHome;

   void setEJBLocalHome(BaseEJBLocalHome h) {
      this.ejbLocalHome = h;
   }

   public final void remove(MethodDescriptor md) throws EJBException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsLocal(EJBContextHandler.EMPTY);
   }

   protected final LocalHandle getLocalHandle(MethodDescriptor md) throws EJBException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsLocal(new EJBContextHandler(md, new Object[0]));
      return this.getLocalHandleObject();
   }

   public final LocalHandle getLocalHandleObject() {
      return new LocalHandleImpl(this);
   }

   protected final boolean isIdentical(MethodDescriptor md, EJBLocalObject o) throws EJBException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsLocal(new EJBContextHandler(md, new Object[]{o}));
      return o != null && o instanceof StatelessEJBLocalObject && this.ejbLocalHome.isIdenticalTo(o.getEJBLocalHome());
   }

   protected final EJBLocalHome getEJBLocalHome(MethodDescriptor md) {
      InvocationWrapper.newInstance(md).checkMethodPermissionsLocal(new EJBContextHandler(md, new Object[0]));
      return this.ejbLocalHome;
   }

   protected final Object getPrimaryKey(MethodDescriptor md) throws EJBException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsLocal(new EJBContextHandler(md, new Object[0]));
      Loggable l = EJBLogger.loglocalSessionBeanCannotCallGetPrimaryKeyLoggable();
      throw new EJBException(l.getMessageText());
   }
}
