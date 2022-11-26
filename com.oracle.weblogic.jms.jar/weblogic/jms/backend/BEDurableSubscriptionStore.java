package weblogic.jms.backend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.jms.JMSException;
import weblogic.jms.common.JMSDebug;
import weblogic.jms.common.JMSSQLExpression;
import weblogic.store.PersistentHandle;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreException;
import weblogic.store.PersistentStoreRecord;
import weblogic.store.PersistentStoreTransaction;
import weblogic.store.xa.PersistentStoreXA;

public class BEDurableSubscriptionStore {
   private static final String CONN_PREFIX = "weblogic.messaging.";
   private static final String CONN_SUFFIX = ".durablesubs";
   private PersistentStoreXA store;
   private PersistentStoreConnection storeConnection;
   private Map topicMap;

   public BEDurableSubscriptionStore(String backEndName, PersistentStoreXA store) throws JMSException {
      this.store = store;
      this.topicMap = new HashMap();

      try {
         this.storeConnection = store.createConnection("weblogic.messaging." + backEndName + ".durablesubs");
      } catch (PersistentStoreException var4) {
         throw new weblogic.jms.common.JMSException("Error opening persistent store for durable subscriptions", var4);
      }
   }

   public void close() {
      this.storeConnection.close();
   }

   public PersistentHandle createSubscription(String destinationName, String clientId, String subscriptionName, JMSSQLExpression selector) throws JMSException {
      return this.createSubscription(destinationName, clientId, 0, subscriptionName, selector);
   }

   public PersistentHandle createSubscription(String destinationName, String clientId, int clientIdPolicy, String subscriptionName, JMSSQLExpression selector) throws JMSException {
      SubscriptionRecord rec = new SubscriptionRecord(destinationName, clientId, clientIdPolicy, subscriptionName, selector);
      PersistentStoreTransaction tran = this.store.begin();
      PersistentHandle ret = this.storeConnection.create(tran, rec, 0);

      try {
         tran.commit();
      } catch (PersistentStoreException var10) {
         throw new weblogic.jms.common.JMSException("Error persisting a durable subscriber record", var10);
      }

      if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
         JMSDebug.JMSBackEnd.debug("Persisted a record for durable subscription " + rec);
      }

      return ret;
   }

   void deleteSubscription(PersistentHandle handle) throws JMSException {
      PersistentStoreTransaction tran = this.store.begin();
      this.storeConnection.delete(tran, handle, 0);

      try {
         tran.commit();
      } catch (PersistentStoreException var4) {
         throw new weblogic.jms.common.JMSException("Error deleting a durable subscriber record", var4);
      }

      JMSDebug.JMSBackEnd.debug("Deleted a persistent durable subscription record");
   }

   void recover() throws JMSException {
      try {
         PersistentStoreRecord rec;
         SubscriptionRecord subRec;
         Object topicList;
         for(PersistentStoreConnection.Cursor cursor = this.storeConnection.createCursor(0); (rec = cursor.next()) != null; ((List)topicList).add(subRec)) {
            subRec = (SubscriptionRecord)rec.getData();
            subRec.setHandle(rec.getHandle());
            topicList = (List)this.topicMap.get(subRec.getDestinationName());
            if (topicList == null) {
               topicList = new ArrayList();
               this.topicMap.put(subRec.getDestinationName(), topicList);
            }
         }

      } catch (PersistentStoreException var5) {
         throw new weblogic.jms.common.JMSException("Error recovering durable subscriber records", var5);
      }
   }

   synchronized void restoreSubscriptions(BETopicImpl topic) throws JMSException {
      List subscriptions = (List)this.topicMap.get(topic.getName());
      if (subscriptions != null) {
         SubscriptionRecord rec;
         for(Iterator i = subscriptions.iterator(); i.hasNext(); topic.recoverDurableSubscription(rec.getHandle(), rec.getClientId(), rec.getClientIdPolicy(), rec.getSubscriptionName(), rec.getExpression())) {
            rec = (SubscriptionRecord)i.next();
            if (JMSDebug.JMSBackEnd.isDebugEnabled()) {
               JMSDebug.JMSBackEnd.debug("Restoring durable subscription " + rec);
            }
         }

         this.topicMap.remove(topic.getName());
      }

   }

   synchronized void deleteOrphanedSubscriptions() throws JMSException {
      JMSException lastException = null;
      Iterator topicI = this.topicMap.values().iterator();

      while(topicI.hasNext()) {
         List subs = (List)topicI.next();
         Iterator subI = subs.iterator();

         while(subI.hasNext()) {
            SubscriptionRecord sub = (SubscriptionRecord)subI.next();

            try {
               this.deleteSubscription(sub.getHandle());
            } catch (JMSException var7) {
               lastException = var7;
            }
         }
      }

      this.topicMap.clear();
      if (lastException != null) {
         throw lastException;
      }
   }

   public static final class SubscriptionRecord implements Externalizable {
      public static final long serialVersionUID = 5570891599555644794L;
      private static final int EXTERNAL_VERSION1 = 1;
      private static final int EXTERNAL_VERSION2 = 2;
      private static final int EXTERNAL_VERSION = 2;
      private String destinationName;
      private String clientId;
      private int clientIdPolicy = 0;
      private String subscriptionName;
      private JMSSQLExpression selector;
      private transient PersistentHandle handle;

      SubscriptionRecord(String destinationName, String clientId, int clientIdPolicy, String subscriptionName, JMSSQLExpression selector) {
         this.destinationName = destinationName;
         this.clientId = clientId;
         this.clientIdPolicy = clientIdPolicy;
         this.subscriptionName = subscriptionName;
         this.selector = selector;
      }

      public SubscriptionRecord() {
      }

      String getDestinationName() {
         return this.destinationName;
      }

      String getClientId() {
         return this.clientId;
      }

      int getClientIdPolicy() {
         return this.clientIdPolicy;
      }

      String getSubscriptionName() {
         return this.subscriptionName;
      }

      JMSSQLExpression getExpression() {
         return this.selector;
      }

      PersistentHandle getHandle() {
         return this.handle;
      }

      void setHandle(PersistentHandle handle) {
         this.handle = handle;
      }

      public void writeExternal(ObjectOutput out) throws IOException {
         out.writeInt(2);
         out.writeUTF(this.destinationName);
         out.writeUTF(this.clientId == null ? "" : this.clientId);
         out.writeUTF(this.subscriptionName);
         this.selector.writeExternal(out);
         out.writeInt(this.clientIdPolicy);
      }

      public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
         int version = in.readInt();
         if (version != 1 && version != 2) {
            throw new IOException("External version mismatch");
         } else {
            this.destinationName = in.readUTF();
            this.clientId = in.readUTF();
            if (this.clientId.length() == 0) {
               this.clientId = null;
            }

            this.subscriptionName = in.readUTF();
            this.selector = new JMSSQLExpression();
            this.selector.readExternal(in);
            if (version == 2) {
               this.clientIdPolicy = in.readInt();
            }

         }
      }

      public String toString() {
         StringBuffer buf = new StringBuffer();
         buf.append("[ name = ");
         buf.append(this.subscriptionName);
         buf.append(" topic = ");
         buf.append(this.destinationName);
         buf.append(" client ID = ");
         buf.append(this.clientId == null ? "" : this.clientId);
         if (this.selector != null) {
            buf.append(" selector = ");
            buf.append(this.selector);
         }

         buf.append(" ]");
         return buf.toString();
      }
   }
}
