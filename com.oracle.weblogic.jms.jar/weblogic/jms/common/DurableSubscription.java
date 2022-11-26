package weblogic.jms.common;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;
import javax.naming.NamingException;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.common.internal.PeerInfo;
import weblogic.common.internal.PeerInfoable;
import weblogic.jms.JMSService;
import weblogic.jms.backend.BEConsumerImpl;
import weblogic.jndi.Aggregatable;
import weblogic.jndi.internal.NamingNode;
import weblogic.store.common.PersistentStoreOutputStream;

public final class DurableSubscription extends SingularAggregatable implements Subscription {
   private static final byte EXTVERSION = 1;
   private static final byte EXTVERSION1 = 1;
   private static final byte EXTVERSION2 = 2;
   static final long serialVersionUID = 4168599304001594429L;
   private DestinationImpl destinationImpl;
   private String selector;
   private boolean noLocal;
   private Vector dsVector = new Vector();
   private JMSID dsId;
   private transient BEConsumerImpl myConsumer;
   private transient Map subscribers = new HashMap();
   private transient String jndiName;
   private transient String consumerName;
   private transient int clientIdPolicy;
   private transient int subscriptionSharingPolicy;
   private transient boolean pending = true;
   private transient boolean stale;
   private transient int waits;
   private transient int subscribersTotalCount;
   private transient int subscribersHighCount;
   private static final int _HASDESTIMPL = 1;
   private static final int _HASSELECTOR = 2;
   private static final int _NOLOCAL = 4;
   private static final int _HASID = 8;
   private static final int _HASNAME = 16;

   public synchronized int getSubscribersTotalCount() {
      return this.subscribersTotalCount;
   }

   public synchronized int getSubscribersHighCount() {
      return this.subscribersHighCount;
   }

   public synchronized void resetSubscribersCount() {
      this.subscribersHighCount = 0;
      this.subscribersTotalCount = 0;
   }

   public DurableSubscription(String consumerName, DestinationImpl destinationImpl, String selector, boolean noLocal, int clientIdPolicy, int subscriptionSharingPolicy) {
      this.destinationImpl = destinationImpl;
      if (selector != null && selector.trim().length() > 0) {
         this.selector = selector;
      }

      this.noLocal = noLocal;
      this.clientIdPolicy = clientIdPolicy;
      this.subscriptionSharingPolicy = subscriptionSharingPolicy;
      this.consumerName = consumerName;
      this.dsId = JMSService.getNextId();
   }

   public DurableSubscription() {
   }

   public synchronized BEConsumerImpl getConsumer() {
      if (this.myConsumer != null) {
         return this.myConsumer;
      } else {
         Iterator itr = this.subscribers.values().iterator();
         if (itr.hasNext()) {
            this.myConsumer = (BEConsumerImpl)itr.next();
            return this.myConsumer;
         } else {
            return null;
         }
      }
   }

   public synchronized void addSubscriber(BEConsumerImpl consumer) {
      if (this.myConsumer == null || this.subscribers.size() == 0 || this.myConsumer.getJMSID() == null) {
         this.myConsumer = consumer;
      }

      this.subscriptionSharingPolicy = consumer.getSubscriptionSharingPolicy();
      if (consumer.getJMSID() != null) {
         this.subscribers.put(consumer.getJMSID(), consumer);
         ++this.subscribersTotalCount;
         if (this.getSubscribersCount() > this.subscribersHighCount) {
            this.subscribersHighCount = this.getSubscribersCount();
         }
      }

   }

   public synchronized void removeSubscriber(JMSID id) {
      BEConsumerImpl consumer = (BEConsumerImpl)this.subscribers.remove(id);
      if (this.myConsumer == consumer) {
         this.myConsumer = null;
         Iterator itr = this.subscribers.values().iterator();
         if (itr.hasNext()) {
            this.myConsumer = (BEConsumerImpl)itr.next();
         }
      }

      if (this.myConsumer == null && consumer != null) {
         this.myConsumer = consumer;
      }

   }

   public synchronized int getSubscribersCount() {
      return this.subscribers.size();
   }

   public boolean isNoLocal() {
      return this.noLocal;
   }

   public String getSelector() {
      return this.selector;
   }

   public DestinationImpl getDestinationImpl() {
      return this.destinationImpl;
   }

   public JMSServerId getBackEndId() {
      return this.destinationImpl.getBackEndId();
   }

   public synchronized int getSubscriptionSharingPolicy() {
      return this.subscriptionSharingPolicy;
   }

   public static boolean noLocalAndSelectorMatch(Subscription sub, boolean noLocal, String subSelector) {
      if (noLocal != sub.isNoLocal()) {
         return false;
      } else {
         String lSelector;
         if (sub.getSelector() == null || (lSelector = sub.getSelector().trim()).length() == 0 || lSelector.equals("TRUE")) {
            lSelector = null;
         }

         if (subSelector == null || (subSelector = subSelector.trim()).length() == 0 || subSelector.equals("TRUE")) {
            subSelector = null;
         }

         if (lSelector == null && subSelector != null || lSelector != null && subSelector == null) {
            return false;
         } else {
            return lSelector == null || lSelector.equals(subSelector);
         }
      }
   }

   public boolean equalsForSerialized(Object o) {
      if (!(o instanceof DurableSubscription)) {
         return false;
      } else {
         DurableSubscription sub = (DurableSubscription)o;
         return noLocalAndSelectorMatch(this, sub.noLocal, sub.selector) && (this.destinationImpl != null || sub.destinationImpl == null) && (this.destinationImpl == null || sub.destinationImpl != null) ? Destination.equalsForDS(this.destinationImpl, sub.destinationImpl) : false;
      }
   }

   public boolean equals(Object o) {
      if (!(o instanceof DurableSubscription)) {
         return false;
      } else {
         DurableSubscription sub = (DurableSubscription)o;
         return noLocalAndSelectorMatch(this, sub.noLocal, sub.selector) && (this.destinationImpl != null || sub.destinationImpl == null) && (this.destinationImpl == null || sub.destinationImpl != null) && (this.consumerName == null || this.consumerName.equals(sub.consumerName)) && this.clientIdPolicy == sub.clientIdPolicy ? Destination.equalsForDS(this.destinationImpl, sub.destinationImpl) : false;
      }
   }

   public int hashCode() {
      return this.jndiName.hashCode();
   }

   public void hadConflict(boolean didWin) {
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("durableSubscription.hadConflict jndiName=" + this.jndiName + " didWin=" + didWin);
      }

      if (!didWin) {
         if (this.getSubscribersCount() != 0 || this.subscriptionSharingPolicy == 0) {
            try {
               if (this.getConsumer() != null) {
                  this.getConsumer().delete(false, false);
               }
            } catch (JMSException var3) {
            } catch (javax.jms.JMSException var4) {
            }

         }
      }
   }

   public synchronized boolean hasWaits() {
      return this.waits > 0;
   }

   public synchronized void incrementWaits() {
      ++this.waits;
   }

   public synchronized void decrementWaits() {
      --this.waits;
   }

   public synchronized void setPending(boolean pending) {
      this.pending = pending;
   }

   public synchronized boolean isPending() {
      return this.pending;
   }

   public synchronized void setStale(boolean stale) {
      this.stale = stale;
   }

   public synchronized boolean isStale() {
      return this.stale;
   }

   public String toString() {
      return "DurableSubscription((" + super.toString() + ") " + this.destinationImpl + ":" + this.selector + ":" + this.noLocal + ")";
   }

   private synchronized void add(DurableSubscription ds) {
      if (ds != this) {
         ds.dsVector = this.dsVector;
      }

      this.dsVector.add(ds);
   }

   private synchronized void remove(DurableSubscription ds) {
      for(int i = 0; i < this.dsVector.size(); ++i) {
         DurableSubscription ds1 = (DurableSubscription)this.dsVector.elementAt(i);
         if (ds1.dsId.equals(ds.dsId)) {
            this.dsVector.remove(i);
         }
      }

   }

   public synchronized Vector getDSVector() {
      return (Vector)this.dsVector.clone();
   }

   public String getName() {
      return this.jndiName;
   }

   public void onBind(NamingNode node, String name, Aggregatable other) {
      super.onBind(node, name, other);
      if (this.hasAggregatable()) {
         DurableSubscription ds = (DurableSubscription)((DurableSubscription)(other == null ? this : other));
         this.add(ds);
         if (other == null) {
            DSManager.manager().add(ds);
         }
      }

   }

   public boolean onUnbind(NamingNode store, String name, Aggregatable other) throws NamingException {
      boolean ret = super.onUnbind(store, name, other);
      DurableSubscription ds1;
      if (ret) {
         ds1 = null;
         synchronized(this) {
            ds1 = (DurableSubscription)this.dsVector.firstElement();
         }

         if (ds1 != null) {
            DSManager.manager().remove(ds1);
         }
      }

      ds1 = (DurableSubscription)((DurableSubscription)(other == null ? this : other));
      this.remove(ds1);
      return ret;
   }

   public void delete() throws javax.jms.JMSException {
      Throwable throwMe = null;
      BEConsumerImpl consumer;
      Iterator itr;
      synchronized(this) {
         itr = ((HashMap)((HashMap)this.subscribers).clone()).values().iterator();
         consumer = this.myConsumer;
      }

      if (!itr.hasNext() && consumer != null) {
         consumer.delete(false, true);
      }

      while(itr.hasNext()) {
         consumer = (BEConsumerImpl)itr.next();

         try {
            if (consumer != null) {
               consumer.delete(false, true);
            }
         } catch (Throwable var6) {
            throwMe = var6;
         }
      }

      if (throwMe instanceof JMSException) {
         throw (JMSException)throwMe;
      } else if (throwMe instanceof RuntimeException) {
         throw (RuntimeException)throwMe;
      } else if (throwMe instanceof Error) {
         throw (Error)throwMe;
      }
   }

   public void close() throws javax.jms.JMSException {
      Throwable throwMe = null;
      BEConsumerImpl consumer;
      Iterator itr;
      synchronized(this) {
         itr = ((HashMap)((HashMap)this.subscribers).clone()).values().iterator();
         consumer = this.myConsumer;
      }

      if (!itr.hasNext() && consumer != null) {
         try {
            consumer.close(0L);
         } catch (Throwable var7) {
            throwMe = var7;
         }
      }

      String reason = "Consumer destination was closed";

      while(itr.hasNext()) {
         consumer = (BEConsumerImpl)itr.next();

         try {
            consumer.closeWithError(reason);
         } catch (Throwable var6) {
            throwMe = var6;
         }
      }

      if (consumer != null) {
         consumer.cleanupDurableSubscription(true, false, false, false, true);
      }

      if (throwMe instanceof JMSException) {
         throw (JMSException)throwMe;
      } else if (throwMe instanceof RuntimeException) {
         throw (RuntimeException)throwMe;
      } else if (throwMe instanceof Error) {
         throw (Error)throwMe;
      }
   }

   public boolean isActive() {
      Iterator itr;
      synchronized(this) {
         itr = ((HashMap)((HashMap)this.subscribers).clone()).values().iterator();
      }

      BEConsumerImpl consumer;
      do {
         if (!itr.hasNext()) {
            return false;
         }

         consumer = (BEConsumerImpl)itr.next();
      } while(!consumer.isUsed());

      return true;
   }

   private int getVersion(Object oo) throws IOException {
      if (oo instanceof PeerInfoable) {
         PeerInfo pi = ((PeerInfoable)oo).getPeerInfo();
         int majorVer = pi.getMajor();
         int minorVer = pi.getMinor();
         if (majorVer < 6) {
            throw new IOException("Peer neither compatible with 1 or 2 .  PeerInfo is " + pi);
         }

         if (majorVer == 6 && minorVer < 2) {
            return 1;
         }
      }

      return 2;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      boolean isJMSStoreStream = false;
      int peerVersion = this.getVersion(out);
      if (peerVersion == 1) {
         this.writeExternal1(out);
      } else {
         if (peerVersion >= 2) {
            if (out instanceof PersistentStoreOutputStream) {
               isJMSStoreStream = true;
            }

            int flags = 0;
            out.writeByte(2);
            if (this.destinationImpl != null) {
               flags |= 1;
            }

            if (this.selector != null) {
               flags |= 2;
            }

            if (this.noLocal) {
               flags |= 4;
            }

            if (!isJMSStoreStream) {
               if (this.dsId != null) {
                  flags |= 8;
               }

               if (this.consumerName != null) {
                  flags |= 16;
               }
            }

            out.writeByte((byte)flags);
         }

         super.writeExternal(out);
         if (this.destinationImpl != null) {
            this.destinationImpl.writeExternal(out);
         }

         if (this.selector != null) {
            out.writeUTF(this.selector);
         }

         if (!isJMSStoreStream && peerVersion >= 2) {
            if (this.dsId != null) {
               this.dsId.writeExternal(out);
            }

            if (this.getConsumer() != null) {
               this.consumerName = this.getConsumer().getName();
            }

            if (this.consumerName != null) {
               if (out instanceof WLObjectOutput) {
                  ((WLObjectOutput)out).writeAbbrevString(this.consumerName);
               } else {
                  out.writeUTF(this.consumerName);
               }
            }
         }

      }
   }

   private void writeExternal1(ObjectOutput out) throws IOException {
      out.writeByte(1);
      if (this.destinationImpl == null) {
         out.writeBoolean(false);
      } else {
         out.writeBoolean(true);
         this.destinationImpl.writeExternal(out);
      }

      if (this.selector == null) {
         out.writeBoolean(false);
      } else {
         out.writeBoolean(true);
         out.writeUTF(this.selector);
      }

      out.writeBoolean(this.noLocal);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      byte vrsn = in.readByte();
      if (vrsn == 1) {
         this.readExternal1(in);
      } else if (vrsn != 2) {
         throw JMSUtilities.versionIOException(vrsn, 1, 2);
      } else {
         int flags = in.readByte();
         super.readExternal(in);
         if ((flags & 1) != 0) {
            this.destinationImpl = new DestinationImpl();
            this.destinationImpl.readExternal(in);
         }

         if ((flags & 2) != 0) {
            this.selector = in.readUTF();
         }

         this.noLocal = (flags & 4) != 0;
         int peerVersion = this.getVersion(in);
         if (peerVersion >= 2) {
            if ((flags & 8) != 0) {
               this.dsId = new JMSID();
               this.dsId.readExternal(in);
            }

            if ((flags & 16) != 0) {
               if (in instanceof WLObjectInput) {
                  this.consumerName = ((WLObjectInput)in).readAbbrevString();
               } else {
                  this.consumerName = in.readUTF();
               }

               this.jndiName = BEConsumerImpl.JNDINameForSubscription(this.consumerName);
            }
         }

      }
   }

   private void readExternal1(ObjectInput in) throws IOException, ClassNotFoundException {
      byte vrsn = in.readByte();
      if (vrsn != 1) {
         throw JMSUtilities.versionIOException(vrsn, 1, 1);
      } else {
         super.readExternal(in);
         if (in.readBoolean()) {
            this.destinationImpl = new DestinationImpl();
            this.destinationImpl.readExternal(in);
         }

         if (in.readBoolean()) {
            this.selector = in.readUTF();
         }

         this.noLocal = in.readBoolean();
      }
   }
}
