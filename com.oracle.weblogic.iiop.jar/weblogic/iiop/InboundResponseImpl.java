package weblogic.iiop;

import java.io.IOException;
import java.rmi.UnmarshalException;
import javax.transaction.Transaction;
import javax.transaction.TransactionRolledbackException;
import org.omg.CORBA.SystemException;
import weblogic.iiop.contexts.ServiceContext;
import weblogic.iiop.contexts.VendorInfoReplicaVersion;
import weblogic.iiop.contexts.WorkAreaContext;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.messages.ReplyMessage;
import weblogic.iiop.messages.ReplyStatusConstants;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.rmi.cluster.PiggybackResponse;
import weblogic.rmi.cluster.ReplicaList;
import weblogic.rmi.cluster.ReplicaVersion;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.rmi.internal.ObjectIO;
import weblogic.rmi.spi.InboundResponse;
import weblogic.rmi.spi.MsgInput;
import weblogic.transaction.TransactionHelper;
import weblogic.utils.StackTraceUtils;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.WorkContextInput;
import weblogic.workarea.spi.WorkContextMapInterceptor;

final class InboundResponseImpl implements InboundResponse, ReplyStatusConstants {
   private MsgInput msgInput;
   final EndPoint endPoint;
   final ReplyMessage reply;
   private final RuntimeMethodDescriptor md;

   static void p(String s) {
      System.err.println("<InboundResponseImpl> " + s);
   }

   InboundResponseImpl(EndPoint endPoint, ReplyMessage reply, boolean rmiType, RuntimeMethodDescriptor md, String codebase) {
      CorbaInputStream inputStream = reply.getInputStream();
      inputStream.setPossibleCodebase(codebase);
      if (rmiType) {
         this.msgInput = inputStream;
      } else {
         this.msgInput = new IDLMsgInput((IIOPInputStream)inputStream);
      }

      this.endPoint = endPoint;
      this.reply = reply;
      this.md = md;
   }

   IOR needsForwarding() {
      return this.reply.needsForwarding() ? this.reply.getIOR() : null;
   }

   private Throwable getThrowable() {
      Throwable t = this.reply.getThrowable();
      return t instanceof SystemException && !(this.msgInput instanceof IDLMsgInput) ? this.reply.getMappedThrowable() : t;
   }

   public MsgInput getMsgInput() {
      return this.msgInput;
   }

   public Object unmarshalReturn() throws Throwable {
      this.retrieveThreadLocalContext();
      Throwable throwable = this.getThrowable();
      if (throwable != null) {
         if (throwable instanceof TransactionRolledbackException) {
            Transaction tx = TransactionHelper.getTransactionHelper().getTransaction();
            if (tx != null) {
               if (tx instanceof weblogic.transaction.Transaction) {
                  ((weblogic.transaction.Transaction)tx).setRollbackOnly(throwable);
               } else {
                  tx.setRollbackOnly();
               }
            }
         }

         throw StackTraceUtils.getThrowableWithCause(throwable);
      } else {
         Class returnType = this.md.getReturnType();
         short returnTypeCode = this.md.getReturnTypeAbbrev();

         try {
            Object returnVal = ObjectIO.readObject(this.getMsgInput(), returnType, returnTypeCode);
            return returnVal;
         } catch (ClassNotFoundException | IOException var6) {
            throw new UnmarshalException("failed to unmarshal " + returnType, var6);
         }
      }
   }

   public void retrieveThreadLocalContext() throws IOException {
      this.retrieveThreadLocalContext(true);
   }

   public void retrieveThreadLocalContext(boolean forceReset) throws IOException {
      WorkAreaContext wac = (WorkAreaContext)this.endPoint.getMessageServiceContext(this.reply, 1111834891);
      WorkContextMapInterceptor interceptor = WorkContextHelper.getWorkContextHelper().getInterceptor();
      if (wac != null) {
         interceptor.receiveResponse(wac.getInputStream());
      } else if (forceReset) {
         interceptor.receiveResponse((WorkContextInput)null);
      }

   }

   public Object getTxContext() {
      return this.endPoint.getInboundResponseTxContext(this.reply);
   }

   public Object getContext(int id) throws IOException {
      return null;
   }

   public PiggybackResponse getReplicaInfo() throws IOException {
      PiggybackResponse response = this.getReplicaList();
      return (PiggybackResponse)(response != null ? response : this.getReplicaVersion());
   }

   public ReplicaList getReplicaList() {
      return (ReplicaList)this.endPoint.getMessageServiceContext(this.reply, 1111834883);
   }

   public ReplicaVersion getReplicaVersion() {
      ServiceContext serviceContext = this.endPoint.getMessageServiceContext(this.reply, 1111834895);
      return serviceContext instanceof VendorInfoReplicaVersion ? ((VendorInfoReplicaVersion)serviceContext).getReplicaVersion() : null;
   }

   public Object getActivatedPinnedRef() throws IOException {
      return null;
   }

   public void close() throws IOException {
      this.msgInput.close();
   }
}
