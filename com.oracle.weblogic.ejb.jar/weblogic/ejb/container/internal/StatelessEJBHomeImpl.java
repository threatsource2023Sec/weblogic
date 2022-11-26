package weblogic.ejb.container.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.ejb.EJBMetaData;
import javax.ejb.Handle;
import javax.ejb.HomeHandle;
import javax.ejb.RemoveException;
import weblogic.ejb.container.interfaces.Ejb3RemoteHome;
import weblogic.ejb.container.interfaces.Ejb3SessionHome;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.spi.BusinessObject;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.ejb20.internal.HomeHandleImpl;
import weblogic.rmi.annotation.internal.DgcPolicy;
import weblogic.rmi.annotation.internal.RmiInternal;

@RmiInternal(
   remoteInterfaces = {Ejb3RemoteHome.class},
   dgcPolicy = DgcPolicy.MANAGED,
   clusterable = true
)
public class StatelessEJBHomeImpl extends StatelessEJBHome implements Ejb3SessionHome, Ejb3RemoteHome {
   private final Map viewToRemoteImpl = new HashMap();
   private final Map viewToWrapper = new HashMap();

   public StatelessEJBHomeImpl() {
      super((Class)null);
   }

   public StatelessEJBHomeImpl(Class eoClass) {
      super(eoClass);
   }

   public EJBMetaData getEJBMetaData() throws RemoteException {
      throw new IllegalStateException();
   }

   public HomeHandle getHomeHandle() throws RemoteException {
      return new HomeHandleImpl(this, this.getBeanInfo().getJNDIName(), URLDelegateProvider.getURLDelegate(this.isHomeClusterable()));
   }

   public void remove(Object pk) throws RemoteException, RemoveException {
      throw new IllegalStateException();
   }

   public void remove(Handle h) throws RemoteException, RemoveException {
      throw new IllegalStateException();
   }

   public void prepare() {
      SessionBeanInfo sbi = this.getBeanInfo();
      Iterator var2 = sbi.getBusinessRemotes().iterator();

      while(var2.hasNext()) {
         Class iface = (Class)var2.next();
         Class busImplCls = sbi.getGeneratedRemoteBusinessImplClass(iface);
         this.viewToRemoteImpl.put(iface.getName(), this.allocateBI(busImplCls, iface));
      }

   }

   public void activate() throws WLDeploymentException {
      super.activate();
      this.getWrapperMap();
   }

   private Map getWrapperMap() {
      if (this.viewToWrapper.isEmpty()) {
         SessionBeanInfo sbi = this.getBeanInfo();

         Class bi;
         Object wrap;
         for(Iterator var2 = sbi.getBusinessRemotes().iterator(); var2.hasNext(); this.viewToWrapper.put(bi.getName(), wrap)) {
            bi = (Class)var2.next();
            wrap = this.perhapsWrap((Remote)this.viewToRemoteImpl.get(bi.getName()));
            if (!Remote.class.isAssignableFrom(bi)) {
               wrap = this.createProxy(wrap, bi, sbi.getGeneratedRemoteBusinessIntfClass(bi));
            }
         }
      }

      return this.viewToWrapper;
   }

   private Object createProxy(Object busImpl, Class bi, Class rbi) {
      RemoteBusinessIntfProxy invHandler = new RemoteBusinessIntfProxy(busImpl, this.deploymentInfo.getModuleClassLoader().getAnnotation().getAnnotationString(), bi.getName(), rbi.getName());
      return Proxy.newProxyInstance(bi.getClassLoader(), new Class[]{bi, BusinessObject.class}, invHandler);
   }

   private Remote allocateBI(Class implClass, Class iface) {
      try {
         StatelessRemoteObject sro = new StatelessRemoteObject();
         sro.setEJBHome(this);
         sro.setBeanManager(this.getBeanManager());
         sro.setBeanInfo(this.getBeanInfo());
         sro.setImplementsRemote(Remote.class.isAssignableFrom(iface));
         Constructor ctr;
         if (this.getBeanInfo().hasAsyncMethods()) {
            ctr = implClass.getConstructor(StatelessRemoteObject.class, AsyncInvocationManager.class, RMIAsyncInvState.class);
            return (Remote)ctr.newInstance(sro, this.getBeanInfo().getAsyncInvocationManager(), new RMIAsyncInvState());
         } else {
            ctr = implClass.getConstructor(StatelessRemoteObject.class);
            return (Remote)ctr.newInstance(sro);
         }
      } catch (Exception var5) {
         throw new AssertionError(var5);
      }
   }

   public SessionBeanInfo getBeanInfo() {
      return (SessionBeanInfo)super.getBeanInfo();
   }

   public Object getBindableImpl(String ifaceName) {
      return this.getWrapperMap().get(ifaceName);
   }

   public Remote getBindableEO(Class iface) {
      return (Remote)this.viewToRemoteImpl.get(iface.getName());
   }

   public Object getBusinessImpl(Object pk, String ifaceName) {
      return this.getWrapperMap().get(ifaceName);
   }

   public Object getBusinessImpl(Object pk, Class iface) {
      return this.getBusinessImpl(pk, iface.getName());
   }

   public void undeploy() {
      Iterator var1 = this.viewToRemoteImpl.values().iterator();

      while(var1.hasNext()) {
         Remote r = (Remote)var1.next();
         this.unexport(r);
      }

      this.viewToWrapper.clear();
      super.undeploy();
   }

   public void unprepare() {
      if (!this.viewToWrapper.isEmpty()) {
         Iterator var1 = this.viewToRemoteImpl.values().iterator();

         while(var1.hasNext()) {
            Remote r = (Remote)var1.next();
            this.unexport(r);
         }

         this.viewToWrapper.clear();
      }

      super.unprepare();
   }
}
