package weblogic.ejb.container.manager;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.ejb.EntityBean;
import javax.naming.Context;
import javax.transaction.Transaction;
import weblogic.cluster.GroupMessage;
import weblogic.cluster.MulticastSession;
import weblogic.cluster.RecoverListener;
import weblogic.cluster.ClusterServices.Locator;
import weblogic.ejb.container.EJBLogger;
import weblogic.ejb.container.InternalException;
import weblogic.ejb.container.ReadConfig;
import weblogic.ejb.container.cache.CacheKey;
import weblogic.ejb.container.cache.PassivationListener;
import weblogic.ejb.container.interfaces.BaseEJBLocalHomeIntf;
import weblogic.ejb.container.interfaces.BaseEJBRemoteHomeIntf;
import weblogic.ejb.container.interfaces.BeanInfo;
import weblogic.ejb.container.interfaces.ISecurityHelper;
import weblogic.ejb.container.interfaces.InvalidationBeanManager;
import weblogic.ejb.container.interfaces.ReadOnlyManager;
import weblogic.ejb.container.internal.InvocationWrapper;
import weblogic.ejb.spi.EJBCache;
import weblogic.ejb.spi.EJBRuntimeHolder;
import weblogic.ejb.spi.InvalidationMessage;
import weblogic.ejb.spi.WLDeploymentException;
import weblogic.logging.Loggable;

public final class ReadOnlyEntityManager extends ExclusiveEntityManager implements PassivationListener, InvalidationBeanManager, RecoverListener, ReadOnlyManager {
   private long readTimeoutMS;
   private Map lastReadMap = Collections.synchronizedMap(new HashMap());
   private boolean inCluster;
   private MulticastSession multicastSession;

   public ReadOnlyEntityManager(EJBRuntimeHolder runtime) {
      super(runtime);
   }

   public void setup(BaseEJBRemoteHomeIntf remoteHome, BaseEJBLocalHomeIntf localHome, BeanInfo i, Context environmentContext, EJBCache cache, ISecurityHelper sh) throws WLDeploymentException {
      super.setup(remoteHome, localHome, i, environmentContext, cache, sh);
      this.readTimeoutMS = (long)i.getCachingDescriptor().getReadTimeoutSeconds() * 1000L;
      this.inCluster = ReadConfig.isClusteredServer();
      if (this.inCluster) {
         this.multicastSession = Locator.locate().createMulticastSession(this, -1);
      }

   }

   public void passivated(Object key) {
      this.lastReadMap.remove(key);
   }

   public void remove(InvocationWrapper wrap) throws InternalException {
      this.lastReadMap.remove(wrap.getPrimaryKey());
      super.remove(wrap);
   }

   protected EntityBean alreadyCached(Transaction invokeTx, Object pk) {
      return (EntityBean)this.cache.get(new CacheKey(pk, this));
   }

   protected boolean shouldLoad(Object pk, boolean dbIsShared, boolean alreadyLoadedInthisTx) {
      if (alreadyLoadedInthisTx) {
         return false;
      } else {
         long currentTime = System.currentTimeMillis();
         Long lastRead = (Long)this.lastReadMap.get(pk);
         if (lastRead != null) {
            if (this.readTimeoutMS == 0L) {
               return false;
            }

            if (Math.abs(lastRead - currentTime) < this.readTimeoutMS) {
               return false;
            }
         }

         this.lastReadMap.put(pk, new Long(currentTime));
         return true;
      }
   }

   protected void initLastRead(Object pk) {
      this.lastReadMap.put(pk, new Long(System.currentTimeMillis()));
   }

   protected boolean shouldStoreAfterMethod(InvocationWrapper wrap) {
      return false;
   }

   protected boolean shouldStore(EntityBean bean) {
      return false;
   }

   public GroupMessage createRecoverMessage() {
      return new InvalidationMessage(this.info.getDeploymentInfo().getApplicationId(), this.info.getDeploymentInfo().getModuleId(), this.info.getEJBName());
   }

   private void sendInvalidate(Object pk) throws InternalException {
      InvalidationMessage msg;
      if (pk == null) {
         msg = new InvalidationMessage(this.info.getDeploymentInfo().getApplicationId(), this.info.getDeploymentInfo().getModuleId(), this.info.getEJBName());
      } else if (pk instanceof Collection) {
         msg = new InvalidationMessage(this.info.getDeploymentInfo().getApplicationId(), this.info.getDeploymentInfo().getModuleId(), this.info.getEJBName(), (Collection)pk);
      } else {
         msg = new InvalidationMessage(this.info.getDeploymentInfo().getApplicationId(), this.info.getDeploymentInfo().getModuleId(), this.info.getEJBName(), pk);
      }

      try {
         this.multicastSession.send(msg);
      } catch (IOException var5) {
         Loggable l = EJBLogger.logErrorWhileMulticastingInvalidationLoggable(this.getDisplayName());
         throw new InternalException(l.getMessageText(), var5);
      }
   }

   public void invalidate(Object txOrThread, Object pk) throws InternalException {
      this.lastReadMap.remove(pk);
      if (this.inCluster) {
         this.sendInvalidate(pk);
      }

   }

   public void invalidate(Object txOrThread, Collection pks) throws InternalException {
      Iterator it = pks.iterator();

      while(it.hasNext()) {
         Object pk = it.next();
         this.lastReadMap.remove(pk);
      }

      if (this.inCluster) {
         this.sendInvalidate(pks);
      }

   }

   public void invalidateAll(Object txOrThread) throws InternalException {
      this.lastReadMap.clear();
      if (this.inCluster) {
         this.sendInvalidate((Object)null);
      }

   }

   public void invalidateLocalServer(Object txOrThread, Object pk) {
      this.lastReadMap.remove(pk);
   }

   public void invalidateLocalServer(Object txOrThread, Collection pks) {
      Iterator it = pks.iterator();

      while(it.hasNext()) {
         this.lastReadMap.remove(it.next());
      }

   }

   public void invalidateAllLocalServer(Object txOrThread) {
      this.lastReadMap.clear();
   }

   public void updateReadTimeoutSeconds(int seconds) {
      this.readTimeoutMS = (long)seconds * 1000L;
   }
}
