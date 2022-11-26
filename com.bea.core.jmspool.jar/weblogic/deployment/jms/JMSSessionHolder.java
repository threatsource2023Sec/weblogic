package weblogic.deployment.jms;

import com.oracle.jms.jmspool.PhantomReferenceCloseable;
import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Stack;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.XASession;
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

public class JMSSessionHolder implements PooledResource {
   private JMSConnectionHelperService connectionHelper;
   private Session session;
   private XASession xaSession;
   private XAResource xaResource;
   private int sessionType;
   private int acknowledgeMode;
   private boolean transacted;
   private boolean enabled = true;
   private boolean inUse;
   private boolean broken;
   private long creationTime;
   private ProducerCache producerCache = new ProducerCache();
   private HolderReference phantomRef;
   private PooledResourceInfo prInfo;
   private AbstractSubject subject = null;
   private static final AbstractSubject ANON_SUBJECT = SubjectManager.getSubjectManager().getAnonymousSubject();
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private volatile boolean destroyAfterRelease = false;

   protected JMSSessionHolder(JMSConnectionHelperService connectionHelper, Session session, XASession xaSession, int sessionType, int acknowledgeMode, boolean transacted, long creationStartTime) {
      this.connectionHelper = connectionHelper;
      this.session = session;
      this.xaSession = xaSession;
      this.sessionType = sessionType;
      this.creationTime = System.currentTimeMillis() - creationStartTime;
      this.acknowledgeMode = acknowledgeMode;
      this.transacted = transacted;
      if (connectionHelper != null) {
         this.subject = connectionHelper.getSubject();
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
         JMSPoolDebug.logger.debug("Created a new JMSSessionHolder," + this);
      }

   }

   public void setup() {
   }

   public void cleanup() throws ResourceException {
      if (this.xaSession == null) {
         if (this.transacted) {
            this.pushSubject();

            try {
               JMSPoolDebug.logger.debug("Rolling back a transacted, pooled JMS session");
               this.session.rollback();
            } catch (JMSException var14) {
               if (JMSPoolDebug.logger.isDebugEnabled()) {
                  JMSPoolDebug.logger.debug("Error rolling back session before returning to pool: " + var14);
               }
            } finally {
               this.popSubject();
            }
         } else if (this.acknowledgeMode == 2) {
            this.pushSubject();

            try {
               JMSPoolDebug.logger.debug("Calling recover on a pooled JMS session");
               this.session.recover();
            } catch (JMSException var12) {
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
      JMSPoolDebug.logger.debug("Closing a JMS session not needed by the pool subject = " + this.subject + " currentSubject " + SecurityServiceManager.getCurrentSubject(KERNEL_ID));
      JMSException savedException = null;
      this.pushSubject();

      try {
         if (this.xaSession != null) {
            this.xaSession.close();
         }

         if (this.session != null) {
            this.session.close();
         }
      } catch (JMSException var6) {
         savedException = var6;
      } finally {
         this.xaSession = null;
         this.session = null;
         this.popSubject();
      }

      if (this.connectionHelper != null) {
         this.connectionHelper.decrementReferenceCount();
      }

      if (savedException != null && JMSPoolDebug.logger.isDebugEnabled()) {
         JMSPoolDebug.logger.debug("Warning: Error while closing session", savedException);
      }

   }

   public void forceDestroy() {
      this.destroy();
   }

   public int test() {
      return this.broken ? -1 : 1;
   }

   public String getCachedProducersDebugString() {
      return this.producerCache.getDebugString();
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

   public int getSessionType() {
      return this.sessionType;
   }

   public Session getSession() {
      return this.session;
   }

   public XASession getXASession() {
      return this.xaSession;
   }

   public XAResource getXAResource() {
      return this.xaResource;
   }

   public void setXAResource(XAResource res) {
      this.xaResource = res;
   }

   public JMSConnectionHelperService getConnectionHelper() {
      return this.connectionHelper;
   }

   MessageProducer removeCachedProducer(int wrapType, Destination dest) {
      return this.producerCache.remove(wrapType, dest);
   }

   MessageProducer createProducer(Session session, Destination dest, int wrapType) throws JMSException {
      return this.producerCache.createProducer(session, dest, wrapType);
   }

   void addCachedProducer(int wrapType, MessageProducer producer) {
      this.producerCache.add(wrapType, producer);
   }

   HolderReference makePhantomReference(PooledSession referent, ResourcePool pool, ReferenceQueue queue) {
      this.phantomRef = new HolderReference(referent, this, pool, queue);
      return this.phantomRef;
   }

   public synchronized boolean isEnabled() {
      return this.enabled;
   }

   public int getAcknowledgeMode() {
      return this.transacted ? 0 : this.acknowledgeMode;
   }

   public String toString() {
      return "JMSSessionHolder for: " + this.session;
   }

   public void setBroken(boolean b) {
      this.broken = b;
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

   static final Destination getProducerDest(MessageProducer producer, int wrapType) throws JMSException {
      switch (wrapType) {
         case 9:
            return producer.getDestination();
         case 10:
         case 12:
         default:
            throw new AssertionError();
         case 11:
            return ((QueueSender)producer).getQueue();
         case 13:
            return ((TopicPublisher)producer).getTopic();
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

   private static class ProducerKey {
      private int wrapType;
      private Destination dest;

      ProducerKey(int wt, Destination d) {
         this.wrapType = wt;
         this.dest = d;
      }

      public boolean equals(Object o) {
         ProducerKey other = (ProducerKey)o;
         boolean isEquals = this.wrapType == other.wrapType && (this.dest != null && other.dest != null && this.dest.equals(other.dest) || this.dest == null && other.dest == null);
         return isEquals;
      }

      public int hashCode() {
         return this.dest == null ? this.wrapType : this.dest.hashCode();
      }

      public int getWrapType() {
         return this.wrapType;
      }

      public Destination getDest() {
         return this.dest;
      }
   }

   private static class ProducerCache {
      private static final String PCACHEPROP_PREFIX = "weblogic.jmspool.unsupported.ProducerCache.";
      private static final String PCACHEPROP_ANON_THRESHOLD = "weblogic.jmspool.unsupported.ProducerCache.AnonModeThreshhold";
      private static final String PCACHEPROP_MAX_DESTS = "weblogic.jmspool.unsupported.ProducerCache.MaxDests";
      private static final String PCACHEPROP_ANON_ENABLED = "weblogic.jmspool.unsupported.ProducerCache.AnonModeEnabled";
      private static final String PCACHEPROP_FOREIGN_ENABLED = "weblogic.jmspool.unsupported.ProducerCache.ForeignEnabled";
      private static final String PCACHEPROP_WL_ENABLED = "weblogic.jmspool.unsupported.ProducerCache.WLEnabled";
      private static Class producerClass = null;
      private static final int PCACHE_ANON_THRESHOLD;
      private static final int PCACHE_MAX_DESTS;
      private static final boolean PCACHE_ANON_ENABLED;
      private static final boolean PCACHE_FOREIGN_ENABLED;
      private static final boolean PCACHE_WL_ENABLED;
      private long hits;
      private long misses;
      private LinkedHashMap hm;
      private boolean anonProducerOverride;
      private MessageProducerParams defaultProducerParams;
      static final String EOL;

      private ProducerCache() {
         this.hits = 0L;
         this.misses = 0L;
         this.hm = new LinkedHashMap(8, 0.75F, true);
      }

      synchronized void add(int wrapType, MessageProducer producer) {
         if (PCACHE_ANON_ENABLED && !this.anonProducerOverride && this.destCount() > PCACHE_ANON_THRESHOLD) {
            label69: {
               if (producerClass != null && producerClass.isInstance(producer)) {
                  if (!PCACHE_WL_ENABLED) {
                     break label69;
                  }
               } else if (!PCACHE_FOREIGN_ENABLED) {
                  break label69;
               }

               this.anonProducerOverride = true;
               this.removeAndCloseAll();
            }
         }

         Destination destination;
         try {
            destination = JMSSessionHolder.getProducerDest(producer, wrapType);
         } catch (JMSException var5) {
            JMSSessionHolder.producerClose(producer);
            return;
         }

         if (this.anonProducerOverride && destination != null) {
            JMSSessionHolder.producerClose(producer);
         } else {
            label70: {
               if (producerClass != null && producerClass.isInstance(producer)) {
                  if (PCACHE_WL_ENABLED) {
                     break label70;
                  }
               } else if (PCACHE_FOREIGN_ENABLED) {
                  break label70;
               }

               this.removeAndCloseAll();
            }

            this.addInner(wrapType, destination, producer);

            while(this.destCount() > 0 && this.destCount() > PCACHE_MAX_DESTS) {
               this.removeAndCloseEldest();
            }

         }
      }

      synchronized MessageProducer createProducer(Session vendorSession, Destination dest, int wrapType) throws JMSException {
         Destination d = this.anonProducerOverride ? null : dest;
         Object producer;
         switch (wrapType) {
            case 9:
               producer = vendorSession.createProducer(d);
               break;
            case 10:
            case 12:
            default:
               throw new AssertionError();
            case 11:
               producer = ((QueueSession)vendorSession).createSender((Queue)d);
               break;
            case 13:
               producer = ((TopicSession)vendorSession).createPublisher((Topic)d);
         }

         this.setProducerDefaults((MessageProducer)producer);
         return (MessageProducer)producer;
      }

      synchronized MessageProducer remove(int wrapType, Destination d) {
         if (this.anonProducerOverride) {
            d = null;
         }

         ProducerKey pk = new ProducerKey(wrapType, d);

         while(true) {
            MessageProducer ret = this.removeInner(pk);
            if (ret == null) {
               if (this.misses++ % 1000L == 0L) {
                  ++this.misses;
               }

               if (JMSPoolDebug.logger.isDebugEnabled()) {
                  JMSPoolDebug.logger.debug("PRODUCER CACHE: Producer misses = " + this.misses);
               }

               return null;
            }

            JMSSessionHolder.pushSubjectAnon();

            try {
               this.defaultProducerParams.apply(ret);
               if (this.hits++ % 1000L == 0L) {
                  ++this.hits;
               }

               if (JMSPoolDebug.logger.isDebugEnabled()) {
                  JMSPoolDebug.logger.debug("PRODUCER CACHE: Producer hits = " + this.hits);
               }

               MessageProducer var5 = ret;
               return var5;
            } catch (JMSException var9) {
               if (JMSPoolDebug.logger.isDebugEnabled()) {
                  JMSPoolDebug.logger.debug("Error re-setting producer defaults" + var9);
               }
            } finally {
               JMSSessionHolder.popSubjectAnon();
            }

            JMSSessionHolder.producerClose(ret);
         }
      }

      private synchronized void removeAndCloseEldest() {
         if (this.hm.size() != 0) {
            Iterator iter = this.hm.values().iterator();
            Stack eldest = (Stack)iter.next();
            iter.remove();

            do {
               MessageProducer mp = (MessageProducer)eldest.pop();
               JMSSessionHolder.producerClose(mp);
            } while(!eldest.empty());

         }
      }

      private synchronized void removeAndCloseAll() {
         Iterator var1 = this.hm.values().iterator();

         while(var1.hasNext()) {
            Stack s = (Stack)var1.next();

            while(true) {
               MessageProducer mp = (MessageProducer)s.pop();
               JMSSessionHolder.producerClose(mp);
               if (s.empty()) {
                  break;
               }
            }
         }

         this.hm.clear();
      }

      private synchronized MessageProducer removeInner(ProducerKey pk) {
         Stack stack = (Stack)this.hm.get(pk);
         if (stack == null) {
            return null;
         } else {
            MessageProducer ret = (MessageProducer)stack.pop();
            if (stack.empty()) {
               this.hm.remove(pk);
            }

            return ret;
         }
      }

      private synchronized void addInner(int wrapType, Destination d, MessageProducer mp) {
         ProducerKey pk = new ProducerKey(wrapType, d);
         Stack stack = (Stack)this.hm.get(pk);
         if (stack == null) {
            stack = new Stack();
            stack.push(mp);
            this.hm.put(pk, stack);
         } else {
            stack.push(mp);
         }

      }

      synchronized String getDebugString() {
         String s = "Session Producer Pool Debug " + EOL;
         int total = 0;

         ProducerKey pk1;
         for(Iterator it = ((LinkedHashMap)this.hm.clone()).keySet().iterator(); it.hasNext(); s = s + " dest=" + pk1.getDest() + " wt=" + pk1.getWrapType() + " pcount=" + ((Stack)this.hm.get(pk1)).size() + EOL) {
            pk1 = (ProducerKey)it.next();
            total += ((Stack)this.hm.get(pk1)).size();
         }

         s = s + " Total Producer Count=" + total + EOL;
         s = s + " Dest Count = " + this.hm.size() + EOL;
         return s;
      }

      private synchronized void setProducerDefaults(MessageProducer producer) throws JMSException {
         if (this.defaultProducerParams == null) {
            this.defaultProducerParams = new MessageProducerParams(producer);
         }

      }

      private synchronized int destCount() {
         return this.hm.size();
      }

      private static boolean getBoolean(String prop, boolean defaultVal) {
         return System.getProperty(prop) == null ? defaultVal : Boolean.getBoolean(prop);
      }

      // $FF: synthetic method
      ProducerCache(Object x0) {
         this();
      }

      static {
         try {
            producerClass = Class.forName("weblogic.jms.extensions.WLMessageProducer");
         } catch (ClassNotFoundException var1) {
         }

         PCACHE_ANON_THRESHOLD = Integer.getInteger("weblogic.jmspool.unsupported.ProducerCache.AnonModeThreshhold", new Integer(4));
         PCACHE_MAX_DESTS = Math.max(1, Integer.getInteger("weblogic.jmspool.unsupported.ProducerCache.MaxDests", new Integer(8)));
         PCACHE_ANON_ENABLED = getBoolean("weblogic.jmspool.unsupported.ProducerCache.AnonModeEnabled", true);
         PCACHE_FOREIGN_ENABLED = getBoolean("weblogic.jmspool.unsupported.ProducerCache.ForeignEnabled", false);
         PCACHE_WL_ENABLED = getBoolean("weblogic.jmspool.unsupported.ProducerCache.WLEnabled", true);
         EOL = System.getProperty("line.separator");
      }
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
      private JMSSessionHolder holder;
      private ResourcePool pool;

      protected HolderReference(PooledSession referent, JMSSessionHolder holder, ResourcePool pool, ReferenceQueue queue) {
         super(referent, queue);
         this.holder = holder;
         this.pool = pool;
      }

      JMSSessionHolder getHolder() {
         return this.holder;
      }

      public void closePhantomReference() {
         if (this.pool != null) {
            try {
               this.holder.phantomRef = null;
               this.pool.releaseResource(this.holder);
               this.pool = null;
            } catch (ResourceException var2) {
               throw new RuntimeException(JMSExceptions.getJMSException(JMSPoolLogger.logResourcePoolErrorLoggable(), var2));
            }
         }

      }
   }
}
