package weblogic.iiop.server;

import java.io.IOException;
import java.lang.reflect.Method;
import java.rmi.MarshalException;
import java.rmi.RemoteException;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.UNKNOWN;
import org.omg.CORBA.UserException;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ResponseHandler;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.iiop.IDLMsgOutput;
import weblogic.iiop.IIOPLogger;
import weblogic.iiop.IIOPOutputStream;
import weblogic.iiop.Utils;
import weblogic.iiop.contexts.UnknownExceptionInfo;
import weblogic.iiop.contexts.VendorInfoCluster;
import weblogic.iiop.contexts.VendorInfoReplicaVersion;
import weblogic.iiop.contexts.WorkAreaContext;
import weblogic.iiop.messages.ReplyMessage;
import weblogic.iiop.messages.ReplyStatusConstants;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.rmi.cluster.PiggybackResponse;
import weblogic.rmi.cluster.ReplicaList;
import weblogic.rmi.cluster.ReplicaVersion;
import weblogic.rmi.internal.RMIEnvironment;
import weblogic.rmi.spi.MsgOutput;
import weblogic.transaction.TxHelper;
import weblogic.utils.Debug;
import weblogic.utils.DebugCategory;
import weblogic.utils.StackTraceUtilsClient;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.WorkContextOutput;
import weblogic.workarea.spi.WorkContextMapInterceptor;

class OutboundResponseImpl implements ReplyStatusConstants, OutboundResponse, ResponseHandler {
   private static final DebugCategory debugTransport = Debug.getCategory("weblogic.iiop.transport");
   private static final DebugCategory debugMarshal = Debug.getCategory("weblogic.iiop.marshal");
   private static final DebugLogger debugIIOPTransport = DebugLogger.getDebugLogger("DebugIIOPTransport");
   private static final DebugLogger debugIIOPMarshal = DebugLogger.getDebugLogger("DebugIIOPMarshal");
   private static final DebugLogger debugIIOPDetail = DebugLogger.getDebugLogger("DebugIIOPDetail");
   private final ServerEndPoint endPoint;
   private ReplyMessage reply;
   private final Method invokedMethod;
   private MsgOutput msgOutput;
   private boolean pending;
   private boolean contextsMarshaled;
   private boolean rmiType;
   private InboundRequest requestForResponseHandler;

   OutboundResponseImpl(ServerEndPoint endPoint, ReplyMessage reply, Method invokedMethod, boolean rmiType, boolean pending) {
      this.pending = false;
      this.contextsMarshaled = false;
      this.rmiType = rmiType;
      this.endPoint = endPoint;
      this.reply = reply;
      this.invokedMethod = invokedMethod;
      this.pending = pending;
   }

   OutboundResponseImpl(ServerEndPoint endPoint, ReplyMessage reply) {
      this(endPoint, reply, (Method)null, false, false);
   }

   ReplyMessage getReply() {
      return this.reply;
   }

   public MsgOutput getMsgOutput() {
      if (this.msgOutput == null) {
         this.msgOutput = this.createMsgOutput();
      }

      return this.msgOutput;
   }

   private MsgOutput createMsgOutput() {
      return (MsgOutput)(this.rmiType ? this.getOutputStream() : new IDLMsgOutput(this.getOutputStream()));
   }

   private IIOPOutputStream getOutputStream() {
      return (IIOPOutputStream)this.reply.marshalTo(this.endPoint.createOutputStream());
   }

   public void send() throws MarshalException {
      try {
         if (this.pending) {
            this.endPoint.decrementPendingRequests();
         }

         if (this.reply.getOutputStream() == null) {
            this.reply.marshalTo(this.endPoint.createOutputStream());
         }

         this.endPoint.send(this.reply.getOutputStream());
      } catch (IOException var5) {
         throw new MarshalException("IOException while sending", var5);
      } finally {
         this.closeOrThrowMarshalException();
      }

   }

   private void closeOrThrowMarshalException() throws MarshalException {
      try {
         this.close();
      } catch (IOException var2) {
         throw new MarshalException("IOException while closing", var2);
      }
   }

   public void sendThrowable(Throwable throwable) {
      if (this.pending) {
         this.endPoint.decrementPendingRequests();
      }

      if (!RMIEnvironment.getEnvironment().printExceptionStackTrace()) {
         StackTraceUtilsClient.scrubExceptionStackTrace(throwable);
      }

      try {
         this.writeThrowable(throwable);
      } catch (SystemException var5) {
         IIOPLogger.logMarshalExceptionFailure(throwable.getClass().toString(), var5);

         try {
            this.writeMarshalException();
         } catch (Throwable var4) {
            IIOPLogger.logCompleteMarshalExceptionFailure(throwable.getClass().toString(), var4);
            this.closeQuietly();
            return;
         }
      }

      this.sendAndCloseReply(throwable);
   }

   private void sendAndCloseReply(Throwable problem) {
      try {
         this.endPoint.send(this.reply.getOutputStream());
      } catch (Throwable var6) {
         this.sendMarshalException(problem, var6);
      } finally {
         this.closeQuietly();
      }

   }

   private void sendMarshalException(Throwable problem, Throwable t) {
      try {
         this.writeMarshalException();
         this.endPoint.send(this.reply.getOutputStream());
         IIOPLogger.logSendExceptionFailed(problem.getClass().toString(), t);
      } catch (Throwable var4) {
         IIOPLogger.logSendExceptionCompletelyFailed(problem.getClass().toString(), var4);
      }

   }

   private void closeQuietly() {
      try {
         this.close();
      } catch (IOException var2) {
      }

   }

   private void writeThrowable(Throwable throwable) {
      if (throwable instanceof SystemException) {
         this.writeSystemException((SystemException)throwable);
      } else if (throwable instanceof UserException) {
         this.writeUserException((UserException)throwable);
      } else if (this.isUncheckedException(throwable)) {
         this.writeUncheckedException(throwable);
      } else if (throwable instanceof RemoteException) {
         this.writeRemoteException(throwable);
      } else {
         this.writeCheckedException((Exception)throwable);
      }

   }

   private boolean isUncheckedException(Throwable t) {
      return t instanceof RuntimeException || t instanceof Error;
   }

   public void transferThreadLocalContext(weblogic.rmi.spi.InboundRequest request) throws IOException {
      if (!this.contextsMarshaled) {
         this.contextsMarshaled = true;
         this.setTxContext(TxHelper.getTransactionManager().getInterceptor().sendResponse(request.getTxContext()));
         WorkContextMapInterceptor interceptor = WorkContextHelper.getWorkContextHelper().getLocalInterceptor();
         if (interceptor != null) {
            WorkContextOutput out = this.endPoint.createWorkContextOutput();
            interceptor.sendResponse(out, 4);
            this.endPoint.setMessageServiceContext(this.reply, new WorkAreaContext(out));
         }

      }
   }

   public void setTxContext(Object txContext) {
      this.endPoint.setOutboundResponseTxContext(this.reply, txContext);
   }

   public void setContext(int id, Object data) {
   }

   public void setPiggybackResponse(PiggybackResponse piggybackResponse) {
      this.reply.removeServiceContext(1111834883);
      this.reply.removeServiceContext(1111834895);
      if (piggybackResponse instanceof ReplicaList) {
         this.endPoint.setMessageServiceContext(this.reply, VendorInfoCluster.createResponse((ReplicaList)piggybackResponse));
      } else if (piggybackResponse instanceof ReplicaVersion) {
         this.endPoint.setMessageServiceContext(this.reply, VendorInfoReplicaVersion.createResponse((ReplicaVersion)piggybackResponse));
      }

   }

   private Class getDeclaredException(Class wantToThrow) {
      return this.invokedMethod == null ? wantToThrow : this.getMostDerivedCompatibleExceptionClass(wantToThrow, this.invokedMethod.getExceptionTypes());
   }

   private Class getMostDerivedCompatibleExceptionClass(Class wantToThrow, Class[] candidates) {
      Class mostDerived = null;
      Class[] var4 = candidates;
      int var5 = candidates.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Class candidate = var4[var6];
         if (candidate.isAssignableFrom(wantToThrow) && !candidate.equals(RemoteException.class) && (mostDerived == null || mostDerived.isAssignableFrom(candidate))) {
            mostDerived = candidate;
         }
      }

      return mostDerived != null ? mostDerived : wantToThrow;
   }

   public void close() throws IOException {
      if (this.msgOutput != null) {
         this.msgOutput.close();
      }

   }

   public ResponseHandler createResponseHandler(InboundRequest request) {
      this.requestForResponseHandler = request;
      return this;
   }

   private void writeUserException(UserException problem) {
      IIOPOutputStream os = (IIOPOutputStream)this.createExceptionReply();
      os.write_IDLEntity(problem, problem.getClass());
   }

   public OutputStream createExceptionReply() {
      this.reply = this.createReplyForUserException();

      try {
         this.transferThreadLocalContext(this.requestForResponseHandler);
      } catch (IOException var2) {
         throw Utils.mapToCORBAException(var2);
      }

      return this.reply.marshalTo(this.endPoint.createOutputStream());
   }

   public OutputStream createReply() {
      try {
         this.transferThreadLocalContext(this.requestForResponseHandler);
      } catch (IOException var2) {
         throw Utils.mapToCORBAException(var2);
      }

      return this.reply.marshalTo(this.endPoint.createOutputStream());
   }

   private void writeCheckedException(Exception exception) {
      this.reply = this.createReplyForUserException();
      CorbaOutputStream os = this.reply.marshalTo(this.endPoint.createOutputStream());
      Class declared = this.getDeclaredException(exception.getClass());
      String typeID = Utils.getIDFromException(declared);
      os.write_string(typeID);
      os.write_value(exception, declared);
   }

   private ReplyMessage createReplyForUserException() {
      return ReplyMessage.createFromReplyMessage(this.reply, 1);
   }

   private void writeRemoteException(Throwable problem) {
      SystemException re = Utils.mapRemoteToCORBAException((RemoteException)problem);
      if (re != null) {
         this.writeSystemException(re);
      } else {
         this.writeUncheckedException(problem);
      }

   }

   private void writeMarshalException() {
      this.writeSystemException(new MARSHAL(0, CompletionStatus.COMPLETED_MAYBE));
   }

   private void writeSystemException(SystemException problem) {
      this.reply = this.createReplyForSystemException();
      CorbaOutputStream os = this.reply.marshalTo(this.endPoint.createOutputStream());
      os.write_string(Utils.getRepositoryID(problem.getClass()));
      os.write_long(problem.minor);
      os.write_long(problem.completed.value());
   }

   private void writeUncheckedException(Throwable problem) {
      this.reply = this.createReplyForSystemException();
      this.reply.addExceptionServiceContext(new UnknownExceptionInfo(problem));
      CorbaOutputStream os = this.reply.marshalTo(this.endPoint.createOutputStream());
      os.write_string(Utils.getRepositoryID(UNKNOWN.class));
      os.write_long(0);
      os.write_long(CompletionStatus.COMPLETED_MAYBE.value());
   }

   private ReplyMessage createReplyForSystemException() {
      return ReplyMessage.createFromReplyMessage(this.reply, 2);
   }
}
