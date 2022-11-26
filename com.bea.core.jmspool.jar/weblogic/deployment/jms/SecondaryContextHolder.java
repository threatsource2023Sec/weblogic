package weblogic.deployment.jms;

import com.oracle.jms.jmspool.PhantomReferenceCloseable;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSRuntimeException;
import javax.jms.MessageProducer;
import javax.jms.TemporaryQueue;
import javax.jms.TemporaryTopic;
import javax.jms.XAJMSContext;
import javax.transaction.xa.XAResource;
import weblogic.common.ResourceException;
import weblogic.common.resourcepool.PooledResource;
import weblogic.common.resourcepool.PooledResourceInfo;
import weblogic.common.resourcepool.ResourceCleanupHandler;
import weblogic.common.resourcepool.ResourcePool;
import weblogic.common.resourcepool.ResourcePoolGroup;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;

public class SecondaryContextHolder implements PooledResource {
   private PrimaryContextHelperService primaryContextHelper;
   private JMSContext secondaryContext;
   private XAJMSContext xaJMSContext;
   private XAResource xaResource;
   private int sessionMode;
   private boolean enabled = true;
   private boolean inUse;
   private boolean broken;
   private long creationTime;
   private HolderReference phantomRef;
   private PooledResourceInfo prInfo;
   private AbstractSubject subject = null;
   private static final AbstractSubject ANON_SUBJECT = SubjectManager.getSubjectManager().getAnonymousSubject();
   private static Set allTemporaryDestinations = new HashSet();
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private volatile boolean destroyAfterRelease = false;

   protected SecondaryContextHolder(PrimaryContextHelperService primaryContextHelper, JMSContext secondaryContext, XAJMSContext xaJMSContext, int sessionMode, long creationStartTime) {
      this.primaryContextHelper = primaryContextHelper;
      this.secondaryContext = secondaryContext;
      this.xaJMSContext = xaJMSContext;
      this.creationTime = System.currentTimeMillis() - creationStartTime;
      this.sessionMode = sessionMode;
      if (primaryContextHelper != null) {
         this.subject = primaryContextHelper.getSubject();
      }

      if (this.subject == null) {
         this.subject = SecurityServiceManager.getCurrentSubject(KERNEL_ID);
      }

      if (this.subject.equals(KERNEL_ID)) {
         this.subject = ANON_SUBJECT;
      }

   }

   public void initialize() {
      if (JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("Created a new SecondaryContextHolder," + this);
      }

   }

   public void setup() {
   }

   public void cleanup() throws ResourceException {
      if (this.xaJMSContext == null) {
         if (this.sessionMode == 0) {
            this.pushSubject();

            try {
               JMSPoolDebug.logger.debug("Rolling back a transacted, pooled secondary JMSContext");
               this.secondaryContext.rollback();
            } catch (JMSRuntimeException var14) {
               if (JMSPoolDebug.logger.isDebugEnabled()) {
                  JMSPoolDebug.logger.debug("Error rolling back secondary JMSContext before returning to pool: " + var14);
               }
            } finally {
               this.popSubject();
            }
         } else if (this.sessionMode == 2) {
            this.pushSubject();

            try {
               JMSPoolDebug.logger.debug("Calling recover on a pooled secondary JMSContext");
               this.secondaryContext.recover();
            } catch (JMSRuntimeException var12) {
               if (JMSPoolDebug.logger.isDebugEnabled()) {
                  JMSPoolDebug.logger.debug("Error recovering session before returning to pool", var12);
               }
            } finally {
               this.popSubject();
            }
         }
      }

   }

   public void destroy() {
      JMSPoolDebug.logger.debug("Closing a secondary JMSContext not needed by the pool subject = " + this.subject + " currentSubject " + SecurityServiceManager.getCurrentSubject(KERNEL_ID));
      JMSRuntimeException savedException = null;
      this.pushSubject();

      try {
         if (this.xaJMSContext != null) {
            this.xaJMSContext.close();
         }

         if (this.secondaryContext != null) {
            this.secondaryContext.close();
         }
      } catch (JMSRuntimeException var6) {
         savedException = var6;
      } finally {
         this.xaJMSContext = null;
         this.secondaryContext = null;
         this.popSubject();
      }

      if (this.primaryContextHelper != null) {
         this.primaryContextHelper.decrementReferenceCount();
      }

      if (savedException != null) {
         JMSPoolDebug.logger.debug("Warning: Error while closing secondary JMSContext", savedException);
      }

   }

   public void forceDestroy() {
      this.destroy();
   }

   public int test() {
      return this.broken ? -1 : 1;
   }

   public synchronized void enable() {
      this.enabled = true;
   }

   public synchronized void disable() {
      this.enabled = false;
   }

   public synchronized void setUsed(boolean inUse) {
      this.inUse = inUse;
   }

   public synchronized boolean getUsed() {
      return this.inUse;
   }

   public void setResourceCleanupHandler(ResourceCleanupHandler hdlr) {
   }

   public ResourceCleanupHandler getResourceCleanupHandler() {
      return null;
   }

   public PooledResourceInfo getPooledResourceInfo() {
      return this.prInfo;
   }

   public void setPooledResourceInfo(PooledResourceInfo info) {
      this.prInfo = info;
   }

   public long getCreationTime() {
      return this.creationTime;
   }

   public JMSContext getSecondaryContext() {
      return this.secondaryContext;
   }

   public XAJMSContext getXAJMSContext() {
      return this.xaJMSContext;
   }

   public XAResource getXAResource() {
      return this.xaResource;
   }

   public void setXAResource(XAResource res) {
      this.xaResource = res;
   }

   public PrimaryContextHelperService getPrimaryContextHelper() {
      return this.primaryContextHelper;
   }

   HolderReference makePhantomReference(PooledSecondaryContext referent, ResourcePool pool, ReferenceQueue queue) {
      this.phantomRef = new HolderReference(referent, this, pool, queue);
      return this.phantomRef;
   }

   public synchronized boolean isEnabled() {
      return this.enabled;
   }

   public int getSessionMode() {
      return this.sessionMode;
   }

   public String toString() {
      return "SecondaryContextHolder for: " + this.secondaryContext;
   }

   public void setBroken(boolean b) {
      this.broken = b;
   }

   protected void temporaryDestinationCreated(Destination dest) {
      synchronized(this.phantomRef) {
         this.phantomRef.openTemporaryDestinations.add(dest);
      }

      synchronized(allTemporaryDestinations) {
         allTemporaryDestinations.add(dest);
      }
   }

   protected boolean isValidTemporary(Destination dest) {
      synchronized(allTemporaryDestinations) {
         if (!allTemporaryDestinations.contains(dest)) {
            return true;
         }
      }

      synchronized(this.phantomRef) {
         return this.phantomRef.openTemporaryDestinations.contains(dest);
      }
   }

   private synchronized void pushSubject() {
      if (this.subject != null) {
         SubjectManager.getSubjectManager().pushSubject(KERNEL_ID, this.subject);
      }

   }

   private synchronized void popSubject() {
      if (this.subject != null) {
         SubjectManager.getSubjectManager().popSubject(KERNEL_ID);
      }

   }

   private static final void pushSubjectAnon() {
      SubjectManager.getSubjectManager().pushSubject(KERNEL_ID, ANON_SUBJECT);
   }

   private static final void popSubjectAnon() {
      SubjectManager.getSubjectManager().popSubject(KERNEL_ID);
   }

   private static final void producerClose(MessageProducer mp) {
      pushSubjectAnon();

      try {
         mp.close();
      } catch (Throwable var5) {
         if (JMSPoolDebug.logger.isDebugEnabled()) {
            JMSPoolDebug.logger.debug("Warning: Error while closing producer.", var5);
         }
      } finally {
         popSubjectAnon();
      }

   }

   public ResourcePoolGroup getPrimaryGroup() {
      return null;
   }

   public Collection getGroups() {
      return null;
   }

   public ResourcePoolGroup getGroup(String category) {
      return null;
   }

   public void setDestroyAfterRelease() {
      this.destroyAfterRelease = true;
   }

   public boolean needDestroyAfterRelease() {
      return this.destroyAfterRelease;
   }

   protected static class MessageProducerParams {
      private int deliveryMode;
      private boolean disableMessageID;
      private boolean disableMessageTimestamp;
      private int priority;
      private long timeToLive;
      private long timeToDeliver;
      private int redeliveryLimit;
      private long sendTimeout;
      private String unitOfOrder;
      private static Class producerClass = null;
      private static Method setTimeToDeliver = null;
      private static Method setRedeliveryLimit = null;
      private static Method setSendTimeout = null;
      private static Method setUnitOfOrder = null;
      private static Method getTimeToDeliver = null;
      private static Method getRedeliveryLimit = null;
      private static Method getSendTimeout = null;
      private static Method getUnitOfOrder = null;

      protected MessageProducerParams(MessageProducer producer) throws JMSException {
         this.deliveryMode = producer.getDeliveryMode();
         this.disableMessageID = producer.getDisableMessageID();
         this.disableMessageTimestamp = producer.getDisableMessageTimestamp();
         this.priority = producer.getPriority();
         this.timeToLive = producer.getTimeToLive();
         Throwable th = null;

         try {
            if (producerClass == null || !producerClass.isInstance(producer)) {
               return;
            }

            Object retobj = getTimeToDeliver.invoke(producer, (Object[])null);
            Long retval = (Long)retobj;
            this.timeToDeliver = retval;
            retobj = getRedeliveryLimit.invoke(producer, (Object[])null);
            Integer iretval = (Integer)retobj;
            this.redeliveryLimit = iretval;
            retobj = getSendTimeout.invoke(producer, (Object[])null);
            retval = (Long)retobj;
            this.sendTimeout = retval;
            retobj = getUnitOfOrder.invoke(producer, (Object[])null);
            if (retobj != null) {
               this.unitOfOrder = (String)retobj;
            }
         } catch (IllegalAccessException var6) {
            th = var6;
         } catch (InvocationTargetException var7) {
            th = var7;
         }

         if (th != null) {
            JMSException je = new JMSException(((Throwable)th).toString());
            je.initCause((Throwable)th);
            throw je;
         }
      }

      protected void apply(MessageProducer producer) throws JMSException {
         producer.setDeliveryMode(this.deliveryMode);
         producer.setDisableMessageID(this.disableMessageID);
         producer.setDisableMessageTimestamp(this.disableMessageTimestamp);
         producer.setPriority(this.priority);
         producer.setTimeToLive(this.timeToLive);
         Throwable th = null;

         try {
            if (producerClass == null || !producerClass.isInstance(producer)) {
               return;
            }

            setTimeToDeliver.invoke(producer, new Long(this.timeToDeliver));
            setRedeliveryLimit.invoke(producer, new Integer(this.redeliveryLimit));
            setSendTimeout.invoke(producer, new Long(this.sendTimeout));
            if (this.unitOfOrder != null) {
               setUnitOfOrder.invoke(producer, new String(this.unitOfOrder));
            } else {
               setUnitOfOrder.invoke(producer, (String)null);
            }
         } catch (IllegalAccessException var4) {
            th = var4;
         } catch (InvocationTargetException var5) {
            th = var5;
         }

         if (th != null) {
            JMSException je = new JMSException(((Throwable)th).toString());
            je.initCause((Throwable)th);
            throw je;
         }
      }

      static {
         try {
            producerClass = Class.forName("weblogic.jms.extensions.WLMessageProducer");
            setTimeToDeliver = producerClass.getMethod("setTimeToDeliver", Long.TYPE);
            setRedeliveryLimit = producerClass.getMethod("setRedeliveryLimit", Integer.TYPE);
            setSendTimeout = producerClass.getMethod("setSendTimeout", Long.TYPE);
            setUnitOfOrder = producerClass.getMethod("setUnitOfOrder", String.class);
            getTimeToDeliver = producerClass.getMethod("getTimeToDeliver", (Class[])null);
            getRedeliveryLimit = producerClass.getMethod("getRedeliveryLimit", (Class[])null);
            getSendTimeout = producerClass.getMethod("getSendTimeout", (Class[])null);
            getUnitOfOrder = producerClass.getMethod("getUnitOfOrder", (Class[])null);
         } catch (ClassNotFoundException var1) {
         } catch (NoSuchMethodException var2) {
            throw new RuntimeException("prog error", var2);
         }

      }
   }

   static class HolderReference extends PhantomReference implements PhantomReferenceCloseable {
      private SecondaryContextHolder holder;
      private ResourcePool pool;
      private Set openTemporaryDestinations;

      protected HolderReference(PooledSecondaryContext referent, SecondaryContextHolder holder, ResourcePool pool, ReferenceQueue queue) {
         super(referent, queue);
         this.holder = holder;
         this.pool = pool;
         this.openTemporaryDestinations = new HashSet();
      }

      SecondaryContextHolder getHolder() {
         return this.holder;
      }

      public void closePhantomReference() {
         this.deleteTemporaryDestinations();
         if (this.pool != null) {
            try {
               this.holder.phantomRef = null;
               this.pool.releaseResource(this.holder);
               this.pool = null;
            } catch (ResourceException var2) {
               throw JMSExceptions.getJMSRuntimeException(JMSPoolLogger.logResourcePoolErrorLoggable(), var2);
            }
         }

      }

      void deleteTemporaryDestinations() {
         Iterator temporaries = this.openTemporaryDestinations.iterator();

         while(temporaries.hasNext()) {
            JMSPoolDebug.logger.debug("In PooledPrimaryContext.close, deleting a temporary destination");
            Object obj = temporaries.next();

            try {
               synchronized(SecondaryContextHolder.allTemporaryDestinations) {
                  SecondaryContextHolder.allTemporaryDestinations.remove(obj);
               }

               if (obj instanceof TemporaryQueue) {
                  ((TemporaryQueue)obj).delete();
               } else if (obj instanceof TemporaryTopic) {
                  ((TemporaryTopic)obj).delete();
               }
            } catch (JMSException var6) {
            }
         }

         this.openTemporaryDestinations.clear();
      }
   }
}
