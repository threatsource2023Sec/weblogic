package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.rmi.RemoteException;
import javax.naming.NamingException;
import weblogic.jms.dispatcher.JMSDispatcher;
import weblogic.jndi.Aggregatable;
import weblogic.jndi.internal.NamingNode;

public abstract class SingularAggregatable implements Aggregatable, Externalizable {
   static final long serialVersionUID = 3833976158056390134L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int HASLEADERJMSID = 256;
   private transient JMSDispatcher leaderDispatcher;
   private transient String jndiName;
   private transient int aggregatableCount = 0;
   private JMSID leaderJMSID;
   private long leaderSequenceNumber;

   public abstract void hadConflict(boolean var1);

   public final void setJNDIName(String jndiName) {
      this.jndiName = jndiName;
   }

   public final String getJNDIName() {
      return this.jndiName;
   }

   public final JMSDispatcher getLeaderDispatcher() {
      return this.leaderDispatcher;
   }

   public final void setLeaderDispatcher(JMSDispatcher leaderDispatcher) {
      this.leaderDispatcher = leaderDispatcher;
   }

   public final void setLeaderJMSID(JMSID leaderJMSID) {
      this.leaderJMSID = leaderJMSID;
   }

   public final JMSID getLeaderJMSID() {
      return this.leaderJMSID;
   }

   public final void setLeaderSequenceNumber(long leaderSequenceNumber) {
      this.leaderSequenceNumber = leaderSequenceNumber;
   }

   public final long getLeaderSequenceNumber() {
      return this.leaderSequenceNumber;
   }

   protected boolean hasAggregatable() {
      synchronized(this) {
         return this.aggregatableCount > 0;
      }
   }

   public void onBind(NamingNode store, String name, Aggregatable other) {
      String jndiName = null;
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("SingularAggregatable.onBind(" + jndiName + ":" + this.aggregatableCount + ")");
      }

      try {
         jndiName = store.getNameInNamespace(name);
      } catch (NamingException var10) {
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("SingularAggregatble.onBind failed with naming excption: " + var10);
         }

         return;
      } catch (RemoteException var11) {
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("SingularAggregatble.onBind failed with remote excption: " + var11);
         }

         return;
      }

      SingularAggregatable target;
      if (other == null) {
         target = this;
      } else {
         target = (SingularAggregatable)other;
      }

      try {
         SingularAggregatableManager sam = SingularAggregatableManager.findThrowsJMSException();
         sam.aggregatableDidBind(jndiName, target);
         synchronized(this) {
            ++this.aggregatableCount;
         }
      } catch (javax.jms.JMSException var12) {
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("SingularAggregatble.onBind encountered: " + var12, var12);
         }
      }

   }

   public final void onRebind(NamingNode store, String name, Aggregatable other) {
      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         try {
            JMSDebug.JMSCommon.debug("SingularAggregatable:onRebind(" + store.getNameInNamespace(name) + ":" + this.aggregatableCount + ")");
         } catch (NamingException var5) {
         } catch (RemoteException var6) {
         }
      }

   }

   public boolean onUnbind(NamingNode store, String name, Aggregatable other) throws NamingException {
      boolean retVal = false;
      synchronized(this) {
         if (--this.aggregatableCount <= 0) {
            this.aggregatableCount = 0;
            retVal = true;
         }
      }

      String jndiName = null;

      try {
         jndiName = store.getNameInNamespace(name);
      } catch (NamingException var11) {
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("SingularAggregatble.onUnBind failed with naming excption: " + var11);
         }

         return retVal;
      } catch (RemoteException var12) {
         if (JMSDebug.JMSCommon.isDebugEnabled()) {
            JMSDebug.JMSCommon.debug("SingularAggregatble.onUnBind failed with remote excption: " + var12);
         }

         return retVal;
      }

      if (JMSDebug.JMSCommon.isDebugEnabled()) {
         JMSDebug.JMSCommon.debug("SingularAggregatable:onUnbind(" + jndiName + ":" + this.aggregatableCount + ")");
      }

      SingularAggregatable target;
      if (other == null) {
         target = this;
      } else {
         target = (SingularAggregatable)other;
      }

      LeaderManager lm = LeaderManager.getLeaderManager();
      lm.aggregatableDidBind(jndiName, target.getLeaderJMSID(), target.getLeaderSequenceNumber());
      synchronized(this) {
         if (--this.aggregatableCount <= 0) {
            this.aggregatableCount = 0;
            return true;
         } else {
            return false;
         }
      }
   }

   public String toString() {
      return "SingularAggregatable(" + this.leaderJMSID + ":" + this.leaderSequenceNumber + ")";
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int control = 1;
      if (this.leaderJMSID != null) {
         control |= 256;
      }

      out.writeInt(control);
      if (this.leaderJMSID != null) {
         this.leaderJMSID.writeExternal(out);
      }

      out.writeLong(this.leaderSequenceNumber);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         if ((mask & 256) != 0) {
            this.leaderJMSID = new JMSID();
            this.leaderJMSID.readExternal(in);
         }

         this.leaderSequenceNumber = in.readLong();
      }
   }
}
