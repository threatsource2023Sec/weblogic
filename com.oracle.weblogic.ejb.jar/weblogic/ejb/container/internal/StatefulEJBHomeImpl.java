package weblogic.ejb.container.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJBException;
import javax.ejb.EJBMetaData;
import javax.ejb.Handle;
import javax.ejb.HomeHandle;
import javax.ejb.RemoveException;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.interfaces.Ejb3RemoteHome;
import weblogic.ejb.container.interfaces.Ejb3StatefulHome;
import weblogic.ejb.container.interfaces.SessionBeanInfo;
import weblogic.ejb.container.manager.ReplicatedStatefulSessionManager;
import weblogic.ejb.container.manager.StatefulSessionManager;
import weblogic.ejb.container.replication.ReplicatedBeanManager;
import weblogic.ejb.spi.BusinessObject;
import weblogic.ejb20.internal.HomeHandleImpl;
import weblogic.invocation.ManagedInvocationContext;
import weblogic.rmi.annotation.internal.DgcPolicy;
import weblogic.rmi.annotation.internal.RmiInternal;
import weblogic.rmi.extensions.activation.Activator;
import weblogic.rmi.extensions.server.ServerHelper;
import weblogic.utils.collections.SoftHashMap;

@RmiInternal(
   remoteInterfaces = {Ejb3RemoteHome.class},
   dgcPolicy = DgcPolicy.MANAGED,
   clusterable = true
)
public class StatefulEJBHomeImpl extends StatefulEJBHome implements Ejb3StatefulHome, Ejb3RemoteHome {
   private final Map opaqueReferenceMap = new HashMap();
   private final Map ifaceNameToIface = new HashMap();
   private Map bosMap = Collections.synchronizedMap(new SoftHashMap());

   public StatefulEJBHomeImpl() {
      super((Class)null);
   }

   public StatefulEJBHomeImpl(Class eoClass) {
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
         Class implClass = sbi.getGeneratedRemoteBusinessImplClass(iface);
         Class intfClass = sbi.getGeneratedRemoteBusinessIntfClass(iface);
         EJBBusinessActivator eba = new EJBBusinessActivator(this, implClass, iface);
         OpaqueReferenceImpl or = new OpaqueReferenceImpl(this, implClass, eba, iface, intfClass);
         this.opaqueReferenceMap.put(iface.getName(), or);
         this.ifaceNameToIface.put(iface.getName(), iface);
      }

   }

   public Object getBindableImpl(String ifaceName) {
      return this.opaqueReferenceMap.get(ifaceName);
   }

   public Object getComponentImpl(Object primaryKey) throws RemoteException {
      if (this.beanManager instanceof ReplicatedStatefulSessionManager) {
         try {
            return ((ReplicatedStatefulSessionManager)this.beanManager).registerReplicatedObject(primaryKey);
         } catch (InternalException var3) {
            throw new RemoteException("Remote Exception: ", var3);
         }
      } else {
         return null;
      }
   }

   public boolean needToConsiderReplicationService() throws RemoteException {
      if (this.beanManager instanceof ReplicatedStatefulSessionManager) {
         boolean inCluster = ((ReplicatedStatefulSessionManager)this.beanManager).isInCluster();
         if (inCluster && this.getBeanInfo().hasDeclaredRemoteHome()) {
            return true;
         }
      }

      return false;
   }

   public Object getBusinessImpl(Object pk, String ifaceName) throws RemoteException {
      return this.getBusinessImpl(pk, (Class)this.ifaceNameToIface.get(ifaceName));
   }

   public Object getBusinessImpl(Object pk, Class iface) throws RemoteException {
      Class implClass = this.getBeanInfo().getGeneratedRemoteBusinessImplClass(iface);
      if (implClass == null) {
         return null;
      } else {
         OpaqueReferenceImpl or = (OpaqueReferenceImpl)this.opaqueReferenceMap.get(iface.getName());
         EJBBusinessActivator activator = (EJBBusinessActivator)or.getActivator();

         try {
            Remote eo = ((StatefulSessionManager)this.beanManager).remoteCreateForBI(pk, implClass, activator, iface);
            return this.getProxyForRemoteBO(eo, iface, or.getGeneratedRemoteInterface());
         } catch (InternalException var7) {
            throw new RemoteException("Remote Exception: ", var7);
         }
      }
   }

   public Object getBusinessImpl(String ifaceName) throws RemoteException {
      OpaqueReferenceImpl or = (OpaqueReferenceImpl)this.opaqueReferenceMap.get(ifaceName);
      return this.getBusinessImpl(or.getBusinessImplClass(), or.getActivator(), or.getBusinessIntfClass(), or.getGeneratedRemoteInterface());
   }

   public Object getBusinessImpl(Class implClass, Activator activator, Class iface, Class generatedRemoteInterface) throws RemoteException {
      try {
         ManagedInvocationContext mic = this.getBeanInfo().setCIC();
         Throwable var6 = null;

         Object var8;
         try {
            Remote eo = ((StatefulSessionManager)this.beanManager).remoteCreateForBI((Object)null, implClass, activator, iface);
            var8 = this.getProxyForRemoteBO(eo, iface, generatedRemoteInterface);
         } catch (Throwable var18) {
            var6 = var18;
            throw var18;
         } finally {
            if (mic != null) {
               if (var6 != null) {
                  try {
                     mic.close();
                  } catch (Throwable var17) {
                     var6.addSuppressed(var17);
                  }
               } else {
                  mic.close();
               }
            }

         }

         return var8;
      } catch (InternalException var20) {
         throw new RemoteException("Remote Exception: ", var20.detail);
      }
   }

   public Remote allocateBI(Object pk, Class implClass, Class iface, Activator activator) {
      if (this.getIsInMemoryReplication()) {
         Map boMap = (Map)this.bosMap.get(pk);
         if (boMap == null) {
            boMap = new HashMap();
            this.bosMap.put(pk, boMap);
         }

         Remote bo = (Remote)((Map)boMap).get(iface.getName());
         if (bo == null) {
            bo = this.createNewBO(pk, implClass, iface, activator);

            try {
               ServerHelper.exportObject(bo, "");
            } catch (RemoteException var8) {
               throw new EJBException(var8);
            }

            ((Map)boMap).put(iface.getName(), bo);
         }

         return bo;
      } else {
         return this.createNewBO(pk, implClass, iface, activator);
      }
   }

   private Remote createNewBO(Object pk, Class implClass, Class iface, Activator activator) {
      try {
         StatefulRemoteObject sro = new StatefulRemoteObject();
         sro.setEJBHome(this);
         sro.setBeanManager(this.getBeanManager());
         sro.setBeanInfo(this.getBeanInfo());
         sro.setImplementsRemote(Remote.class.isAssignableFrom(iface));
         Constructor ctr;
         if (this.getBeanInfo().hasAsyncMethods()) {
            ctr = implClass.getConstructor(StatefulRemoteObject.class, AsyncInvocationManager.class, RMIAsyncInvState.class, Object.class, Activator.class);
            return (Remote)ctr.newInstance(sro, this.getBeanInfo().getAsyncInvocationManager(), new RMIAsyncInvState(), pk, activator);
         } else {
            ctr = implClass.getConstructor(StatefulRemoteObject.class, Object.class, Activator.class);
            return (Remote)ctr.newInstance(sro, pk, activator);
         }
      } catch (IllegalAccessException var7) {
         throw new AssertionError(var7);
      } catch (IllegalArgumentException | SecurityException | InvocationTargetException | NoSuchMethodException | InstantiationException var8) {
         throw new AssertionError(var8);
      }
   }

   public SessionBeanInfo getBeanInfo() {
      return (SessionBeanInfo)super.getBeanInfo();
   }

   public void undeploy() {
      Iterator var1 = this.opaqueReferenceMap.values().iterator();

      while(var1.hasNext()) {
         OpaqueReferenceImpl or = (OpaqueReferenceImpl)var1.next();
         this.unexportEJBActivator(or.getActivator(), or.getBusinessImplClass());
      }

      if (this.getIsInMemoryReplication()) {
         List remObjects = new LinkedList();
         synchronized(this.bosMap) {
            Iterator var3 = this.bosMap.values().iterator();

            while(true) {
               if (!var3.hasNext()) {
                  break;
               }

               Map bo = (Map)var3.next();
               remObjects.addAll(bo.values());
            }
         }

         Iterator var8 = remObjects.iterator();

         while(var8.hasNext()) {
            Remote r = (Remote)var8.next();
            this.unexport(r);
         }
      }

      super.undeploy();
   }

   private Object getProxyForRemoteBO(Remote eo, Class iface, Class generatedRemoteInterface) throws RemoteException {
      Thread currentThread = Thread.currentThread();
      ClassLoader savedCL = currentThread.getContextClassLoader();
      currentThread.setContextClassLoader(this.beanInfo.getClassLoader());
      EJBRuntimeUtils.pushEnvironment(this.beanManager.getEnvironmentContext());

      Object var7;
      try {
         if (this.beanInfo.useCallByReference()) {
            ServerHelper.getRuntimeDescriptor(eo.getClass());
         }

         if (Remote.class.isAssignableFrom(iface)) {
            Object var11 = this.perhapsWrap(eo);
            return var11;
         }

         if (generatedRemoteInterface == null) {
            throw new AssertionError();
         }

         RemoteBusinessIntfProxy rBusinessIntfProxy = new RemoteBusinessIntfProxy(this.perhapsWrap(eo), this.deploymentInfo.getModuleClassLoader().getAnnotation().getAnnotationString(), iface.getName(), generatedRemoteInterface.getName());
         var7 = Proxy.newProxyInstance(iface.getClassLoader(), new Class[]{iface, BusinessObject.class}, rBusinessIntfProxy);
      } finally {
         currentThread.setContextClassLoader(savedCL);
         EJBRuntimeUtils.popEnvironment();
      }

      return var7;
   }

   public Object createSecondaryForBI(Object key, String ifaceClassName) throws RemoteException {
      try {
         ClassLoader cl = this.getBeanInfo().getBeanClass().getClassLoader();
         Class iface = cl.loadClass(ifaceClassName);
         return ((ReplicatedBeanManager)this.beanManager).createSecondaryForBI(key, iface);
      } catch (ClassNotFoundException var5) {
         throw new RemoteException("encounter a remote exception, the nested exception is: ", var5);
      }
   }

   public void removeSecondary(Object key) throws RemoteException {
      super.removeSecondary(key);
      Map boMap = (Map)this.bosMap.remove(key);
      if (boMap != null) {
         Iterator var3 = boMap.values().iterator();

         while(var3.hasNext()) {
            Remote rem = (Remote)var3.next();
            if (rem != null) {
               this.unexport(rem, false);
            }
         }
      }

   }

   public void releaseBOs(Object pk) {
      Map boMap = (Map)this.bosMap.remove(pk);
      if (this.getIsInMemoryReplication() && boMap != null) {
         Iterator var3 = boMap.values().iterator();

         while(var3.hasNext()) {
            Remote rem = (Remote)var3.next();
            if (rem != null) {
               this.unexport(rem, false);
            }
         }
      }

   }
}
