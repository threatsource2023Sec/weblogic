package weblogic.messaging.kernel.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.transaction.xa.Xid;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.messaging.kernel.internal.events.EventImpl;
import weblogic.security.subject.AbstractSubject;
import weblogic.store.PersistentHandle;
import weblogic.store.gxa.GXAOperation;
import weblogic.store.gxa.GXAOperationWrapper;
import weblogic.store.gxa.GXATraceLogger;
import weblogic.store.gxa.GXATransaction;
import weblogic.store.gxa.GXid;

public abstract class AbstractOperation implements GXAOperation, Comparable {
   protected static final DebugLogger logger = DebugLogger.getDebugLogger("DebugMessagingKernel");
   public static final int OP_SEND = 1;
   public static final int OP_RECEIVE = 2;
   public static final int OP_EXPIRATION = 3;
   public static final int OP_REDELIVERY_LIMIT = 4;
   public static final int OP_DELETE = 5;
   public static final int OP_MULTI_SEND = 6;
   protected long id;
   protected GXid xid;
   protected GXATransaction gxaTransaction;
   protected String userID = null;
   private String debugPrefix;
   protected int type;
   private AbstractSubject subject;
   private String subjectName;
   protected QueueImpl queue;
   protected MessageReference element;
   protected KernelImpl kernel;
   protected volatile PersistentHandle persistentHandle;
   protected boolean isTwoPhase;
   protected boolean recovered;
   protected List eventList;
   protected boolean localTran;

   protected AbstractOperation(int type, String debugPrefix, KernelImpl kernel, QueueImpl queue, MessageReference element, String subjectName, boolean localTran) {
      this.type = type;
      this.debugPrefix = debugPrefix;
      this.kernel = kernel;
      this.queue = queue;
      this.element = element;
      this.subjectName = subjectName;
      this.localTran = localTran;
   }

   protected AbstractOperation(int type, String debugPrefix, KernelImpl kernel, QueueImpl queue, MessageReference element, AbstractSubject subject, boolean localTran) {
      this.type = type;
      this.debugPrefix = debugPrefix;
      this.kernel = kernel;
      this.queue = queue;
      this.element = element;
      this.subject = subject;
      this.localTran = localTran;
   }

   public void onInitialize(GXATraceLogger traceLogger, GXATransaction gxaTransaction, GXAOperationWrapper operationWrapper) {
      this.xid = gxaTransaction.getGXid();
      this.gxaTransaction = gxaTransaction;
      if (gxaTransaction.isRecovered()) {
         this.recovered = true;
         this.isTwoPhase = true;
      }

   }

   public QueueImpl getQueue() {
      return this.queue;
   }

   public MessageReference getMessageReference() {
      return this.element;
   }

   public long getID() {
      return this.id;
   }

   public void setID(long id) {
      this.id = id;
   }

   protected void assignID() {
      this.id = this.kernel.getNextOperationID();
   }

   public GXid getGXid() {
      return this.xid;
   }

   public Xid getXid() {
      return this.xid.getXAXid();
   }

   public GXATransaction getTransaction() {
      return this.gxaTransaction;
   }

   void setGXid(GXid xid) {
      this.xid = xid;
   }

   public String getDebugPrefix() {
      return this.debugPrefix;
   }

   public int getType() {
      return this.type;
   }

   public String getSubjectName() {
      if (this.subjectName == null && this.subject != null) {
         this.subjectName = SecurityHelper.getSubjectName(this.subject);
      }

      return this.subjectName;
   }

   public void setSubjectName(String subjectName) {
      this.subjectName = subjectName;
   }

   void setPersistentHandle(PersistentHandle handle) {
      this.persistentHandle = handle;
   }

   public PersistentHandle getPersistentHandle() {
      return this.persistentHandle;
   }

   synchronized void addEvent(EventImpl event) {
      if (this.eventList == null) {
         this.eventList = new ArrayList();
      }

      this.eventList.add(event);
   }

   synchronized void logEvents(QueueImpl queue) {
      if (this.eventList != null) {
         Iterator i = this.eventList.iterator();

         while(i.hasNext()) {
            EventImpl event = (EventImpl)i.next();
            event.setTime();
         }

         queue.addEvent(this.eventList);
      }
   }

   public boolean equals(Object o) {
      try {
         return ((AbstractOperation)o).id == this.id;
      } catch (ClassCastException var3) {
         return false;
      }
   }

   public int compareTo(Object o) {
      try {
         AbstractOperation op = (AbstractOperation)o;
         if (this.id < op.id) {
            return -1;
         } else {
            return this.id > op.id ? 1 : 0;
         }
      } catch (ClassCastException var3) {
         return -1;
      }
   }

   public int hashCode() {
      return (int)this.id;
   }

   public Xid getNonLocalTranForLogging() {
      return !this.localTran && this.xid != null ? this.getXid() : null;
   }

   public String getUserID() {
      return this.userID;
   }
}
