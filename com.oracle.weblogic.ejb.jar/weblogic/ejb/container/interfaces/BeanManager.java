package weblogic.ejb.container.interfaces;

import java.lang.reflect.Method;
import java.util.Collection;
import javax.ejb.EJBLocalObject;
import javax.ejb.EJBObject;
import javax.ejb.Timer;
import javax.naming.Context;
import javax.transaction.Transaction;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.internal.InvocationWrapper;
import weblogic.ejb.spi.Injector;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.management.runtime.EJBRuntimeMBean;

public interface BeanManager {
   void setup(BaseEJBRemoteHomeIntf var1, BaseEJBLocalHomeIntf var2, BeanInfo var3, Context var4, ISecurityHelper var5) throws WLDeploymentException;

   boolean isServerShuttingDown();

   boolean isPartitionShuttingDown();

   String getPartitionName();

   void setIsDeployed(boolean var1);

   boolean getIsDeployed();

   void ensureDeployed();

   void undeploy();

   Object preInvoke(InvocationWrapper var1) throws InternalException;

   void postInvoke(InvocationWrapper var1) throws InternalException;

   void destroyInstance(InvocationWrapper var1, Throwable var2) throws InternalException;

   void beforeCompletion(InvocationWrapper var1) throws InternalException;

   void beforeCompletion(Collection var1, Transaction var2) throws InternalException;

   void afterCompletion(InvocationWrapper var1);

   void afterCompletion(Collection var1, Transaction var2, int var3, Object var4);

   EJBObject remoteCreate(InvocationWrapper var1, Method var2, Method var3, Object[] var4) throws InternalException;

   EJBLocalObject localCreate(InvocationWrapper var1, Method var2, Method var3, Object[] var4) throws InternalException;

   void remove(InvocationWrapper var1) throws InternalException;

   EJBRuntimeMBean getEJBRuntimeMBean();

   void beanImplClassChangeNotification();

   void releaseBean(InvocationWrapper var1);

   void handleUncommittedLocalTransaction(InvocationWrapper var1) throws InternalException;

   Context getEnvironmentContext();

   BeanInfo getBeanInfo();

   TimerManager getTimerManager();

   void perhapsStartTimerManager();

   void reInitializePool();

   void clearInjectors();

   void registerInjector(Injector var1);

   void invokeTimeoutMethod(Object var1, Timer var2, Method var3);
}
