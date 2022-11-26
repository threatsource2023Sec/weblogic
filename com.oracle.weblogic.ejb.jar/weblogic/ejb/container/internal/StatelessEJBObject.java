package weblogic.ejb.container.internal;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.ejb.EJBHome;
import javax.ejb.EJBObject;
import javax.ejb.Handle;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb20.internal.HandleImpl;
import weblogic.logging.Loggable;
import weblogic.rmi.extensions.NotificationListener;

public abstract class StatelessEJBObject extends StatelessRemoteObject implements Remote, NotificationListener, EJBObject {
   protected final Object getPrimaryKey(MethodDescriptor md) throws RemoteException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsRemote(new EJBContextHandler(md, new Object[0]));
      Loggable l = EJBLogger.logsessionBeanCannotCallGetPrimaryKeyLoggable();
      throw new RemoteException(l.getMessageText());
   }

   protected final EJBHome getEJBHome(MethodDescriptor md) throws RemoteException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsRemote(new EJBContextHandler(md, new Object[0]));
      return this.ejbHome;
   }

   protected final boolean isIdentical(MethodDescriptor md, EJBObject o) throws RemoteException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsRemote(new EJBContextHandler(md, new Object[]{o}));
      return o != null && this.ejbHome.isIdenticalTo(o.getEJBHome());
   }

   protected final Handle getHandle(MethodDescriptor md) throws RemoteException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsRemote(new EJBContextHandler(md, new Object[0]));
      return new HandleImpl(this);
   }

   public final void remove(MethodDescriptor md) throws RemoteException {
      InvocationWrapper.newInstance(md).checkMethodPermissionsRemote(EJBContextHandler.EMPTY);
   }
}
