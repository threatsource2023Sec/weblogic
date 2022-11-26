package weblogic.iiop.messages;

import java.rmi.RemoteException;
import java.rmi.ServerError;
import java.rmi.ServerException;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INTERNAL;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.UNKNOWN;
import org.omg.CORBA.portable.IDLEntity;
import weblogic.corba.utils.CorbaUtils;
import weblogic.corba.utils.RepositoryId;
import weblogic.iiop.EndPoint;
import weblogic.iiop.contexts.SFVContext;
import weblogic.iiop.contexts.ServiceContext;
import weblogic.iiop.contexts.ServiceContextList;
import weblogic.iiop.contexts.UnknownExceptionInfo;
import weblogic.iiop.ior.IOR;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public final class ReplyMessage extends SequencedMessage implements ServiceContextMessage {
   private EndPoint endPoint;
   private int reply_status;
   private RepositoryId exceptionId;
   private int minorCode;
   private CompletionStatus completionStatus;
   private Throwable throwable;
   private IOR ior;
   private ServiceContextList serviceContexts;

   public static ReplyMessage readFromInputStream(EndPoint endPoint, MessageHeader msgHdr, CorbaInputStream is) {
      return new ReplyMessage(endPoint, msgHdr, is);
   }

   private ReplyMessage(EndPoint endPoint, MessageHeader msgHdr, CorbaInputStream is) {
      super(msgHdr, is);
      this.endPoint = endPoint;
      this.serviceContexts = new ServiceContextList();
      if (!msgHdr.isFragmented()) {
         this.unmarshal();
      } else {
         this.request_id = is.peek_long();
      }

   }

   public static ReplyMessage createLocationForwardReply(ReplyMessage reply, IOR ior) {
      ReplyMessage replyMessage = createFromReplyMessage(reply, 3);
      replyMessage.ior = ior;
      return replyMessage;
   }

   public static ReplyMessage createLocationForwardReply(RequestMessage request, ServiceContextList serviceCtxs, IOR ior) {
      return new ReplyMessage(request, serviceCtxs, ior);
   }

   private ReplyMessage(RequestMessage request, ServiceContextList serviceCtxs, IOR ior) {
      this(request, serviceCtxs, 3);
      this.ior = ior;
   }

   public static ReplyMessage createFromReplyMessage(ReplyMessage reply, int status) {
      return new ReplyMessage(reply, status);
   }

   private ReplyMessage(ReplyMessage reply, int status) {
      this(reply.getRequestID(), reply.getMinorVersion(), reply.getServiceContexts(), status, reply.getMaxStreamFormatVersion());
   }

   public ReplyMessage(RequestMessage request, ServiceContextList serviceCtxs, int reply_status) {
      this(request.getRequestID(), request.getMinorVersion(), serviceCtxs, reply_status, request.getMaxStreamFormatVersion());
   }

   private ReplyMessage(int request_id, int theMinorVersion, ServiceContextList serviceContexts, int reply_status, byte maxFormatVersion) {
      super(new MessageHeader(1, theMinorVersion));
      this.request_id = request_id;
      this.reply_status = reply_status;
      this.serviceContexts = serviceContexts;
      this.setMaxStreamFormatVersion(maxFormatVersion);
   }

   public IOR getIOR() {
      return this.ior;
   }

   public final int getReplyStatus() {
      return this.reply_status;
   }

   public void setThrowable(Throwable throwable) {
      this.throwable = throwable;
   }

   public Throwable getThrowable() {
      if (this.throwable != null) {
         return this.throwable;
      } else {
         switch (this.reply_status) {
            case 0:
            default:
               break;
            case 1:
               CorbaInputStream in = this.getInputStream();
               Class ex = CorbaUtils.getClassFromID(this.exceptionId);
               if (ex == null) {
                  this.throwable = new INTERNAL("Bad UserException: " + this.exceptionId, 0, CompletionStatus.COMPLETED_MAYBE);
               } else if (IDLEntity.class.isAssignableFrom(ex)) {
                  this.throwable = (Throwable)in.read_IDLEntity(ex);
               } else {
                  in.read_string();
                  this.throwable = (Throwable)in.read_value(ex);
               }
               break;
            case 2:
               try {
                  SystemException se = (SystemException)CorbaUtils.getClassFromID(this.exceptionId).newInstance();
                  se.minor = this.minorCode;
                  se.completed = this.completionStatus;
                  this.throwable = se;
               } catch (Exception var3) {
                  this.throwable = new INTERNAL("Bad SystemException: " + this.exceptionId, 0, CompletionStatus.COMPLETED_MAYBE);
               }
         }

         return this.throwable;
      }
   }

   public Throwable getMappedThrowable() {
      Throwable t = this.getThrowable();
      return t instanceof SystemException ? this.mapSystemException((SystemException)t) : t;
   }

   public String getStatusAsString() {
      switch (this.reply_status) {
         case 0:
         default:
            return "SUCCESS";
         case 1:
            return "USER_EXCEPTION(" + this.exceptionId + ")";
         case 2:
            return "SYSTEM_EXCEPTION(" + this.exceptionId + ")";
         case 3:
            return "LOCATION_FORWARD";
         case 4:
            return "LOCATION_FORWARD_PERM";
         case 5:
            return "NEEDS_ADDRESSING_MODE";
      }
   }

   public final ServiceContext getServiceContext(int ctxid) {
      return this.serviceContexts.getServiceContext(ctxid);
   }

   public ServiceContextList getServiceContexts() {
      return this.serviceContexts;
   }

   public final void removeServiceContext(int ctxid) {
      this.serviceContexts.removeServiceContext(ctxid);
   }

   public final void addServiceContext(ServiceContext sc) {
      this.serviceContexts.addServiceContext(sc);
   }

   public final RepositoryId getExceptionId() {
      return this.exceptionId;
   }

   private Throwable mapSystemException(SystemException ex) {
      if (ex instanceof UNKNOWN) {
         UnknownExceptionInfo uei = (UnknownExceptionInfo)this.getServiceContext(9);
         if (uei != null && uei.getNestedThrowable(this.endPoint) != null) {
            Throwable t = uei.getNestedThrowable(this.endPoint);
            if (t instanceof Error) {
               return new ServerError("Error occurred in server thread", (Error)t);
            }

            if (t instanceof RemoteException) {
               return new ServerException("RemoteException occurred in server thread", (Exception)t);
            }

            if (t instanceof RuntimeException) {
               return t;
            }
         }
      }

      return CorbaUtils.mapSystemException(ex);
   }

   public void write(CorbaOutputStream out) {
      super.write(out);
      switch (this.getMinorVersion()) {
         case 0:
         case 1:
            this.writeServiceContexts(out);
            this.writeRequestId(out);
            out.write_long(this.reply_status);
            break;
         case 2:
            this.writeRequestId(out);
            out.write_long(this.reply_status);
            this.writeServiceContexts(out);
      }

      this.alignOnEightByteBoundry(out);
      if (this.reply_status == 3 && this.ior != null) {
         this.ior.write(out);
      }

   }

   private void writeServiceContexts(CorbaOutputStream os) {
      this.serviceContexts.write(os);
   }

   public void read(CorbaInputStream in) {
      switch (this.getMinorVersion()) {
         case 0:
         case 1:
            this.readServiceContexts(in);
            this.readRequestId(in);
            this.reply_status = in.read_long();
            break;
         case 2:
            this.readRequestId(in);
            this.reply_status = in.read_long();
            this.readServiceContexts(in);
      }

      this.alignOnEightByteBoundry(in);
      switch (this.reply_status) {
         case 1:
            in.mark(0);
            this.exceptionId = in.read_repository_id();
            in.reset();
            break;
         case 2:
            this.exceptionId = in.read_repository_id();
            this.minorCode = in.read_long();
            int status = in.read_long();
            switch (status) {
               case 0:
                  this.completionStatus = CompletionStatus.COMPLETED_YES;
                  return;
               case 1:
                  this.completionStatus = CompletionStatus.COMPLETED_NO;
                  return;
               case 2:
                  this.completionStatus = CompletionStatus.COMPLETED_MAYBE;
                  return;
               default:
                  throw new INTERNAL("BAD completion status: " + status, 0, CompletionStatus.COMPLETED_MAYBE);
            }
         case 3:
         case 4:
            this.ior = new IOR(in, true);
      }

   }

   private void readServiceContexts(CorbaInputStream is) {
      this.serviceContexts.read(is);
      SFVContext sfv = (SFVContext)this.serviceContexts.getServiceContext(17);
      if (sfv != null) {
         this.setMaxStreamFormatVersion(sfv.getMaxFormatVersion());
      }

   }

   public final void addExceptionServiceContext(ServiceContext sc) {
      this.addServiceContext(sc);
   }

   public boolean needsForwarding() {
      switch (this.reply_status) {
         case 3:
         case 4:
            return true;
         default:
            return false;
      }
   }

   protected static void p(String m) {
      System.err.println("<ReplyMessage> " + m);
   }
}
