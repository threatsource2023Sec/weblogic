package weblogic.iiop.contexts;

import org.omg.CORBA.MARSHAL;
import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;
import weblogic.security.acl.internal.AuthenticatedSubject;

public class SASServiceContext extends ServiceContext {
   public static final String NO_AUTHENTICATION_METHOD = "_non_existent";
   private short ctxMsgType;
   private ContextBody ctxBody;
   private transient AuthenticatedSubject subject;

   private SASServiceContext(short msgType, ContextBody body) {
      super(15);
      this.ctxMsgType = msgType;
      this.ctxBody = body;
   }

   public SASServiceContext(EstablishContext establishContext) {
      this((short)0, establishContext);
   }

   public SASServiceContext(ContextError error) {
      this((short)4, error);
   }

   public SASServiceContext(long clientContextId) {
      super(15);
      this.ctxMsgType = 5;
      this.ctxBody = new MessageInContext(clientContextId, false);
   }

   public SASServiceContext(CorbaInputStream in) {
      super(15);
      this.readEncapsulatedContext(in);
   }

   public long getClientContextId() {
      return this.getBody().getClientContextId();
   }

   protected void readEncapsulation(CorbaInputStream in) {
      short msgType = in.read_short();
      switch (msgType) {
         case 0:
            this.ctxBody = new EstablishContext(in);
            break;
         case 1:
            this.ctxBody = new CompleteEstablishContext(in);
            break;
         case 2:
         case 3:
         default:
            throw new MARSHAL("Unsupported CSI MsgType.");
         case 4:
            this.ctxBody = new ContextError(in);
            break;
         case 5:
            this.ctxBody = new MessageInContext(in);
      }

      this.ctxMsgType = msgType;
   }

   public short getMsgType() {
      return this.ctxMsgType;
   }

   public ContextBody getBody() {
      return this.ctxBody;
   }

   public void write(CorbaOutputStream out) {
      this.writeEncapsulatedContext(out);
   }

   public void writeEncapsulation(CorbaOutputStream out) {
      out.write_short(this.ctxMsgType);
      this.ctxBody.write(out);
   }

   public boolean shouldEstablishContext() {
      return this.ctxBody.shouldEstablishContext();
   }

   public boolean shouldDiscardContext() {
      return this.ctxBody.shouldDiscardContext();
   }

   public void setSubject(AuthenticatedSubject subject) {
      this.subject = subject;
   }

   public AuthenticatedSubject getSubject() {
      return this.subject;
   }

   public static SASServiceContext createCompleteEstablishContext(EstablishContext establishCtx) {
      CompleteEstablishContext newCtxBody = new CompleteEstablishContext(establishCtx.getClientContextId(), establishCtx.getClientContextId() != 0L, (byte[])null);
      return new SASServiceContext((short)1, newCtxBody);
   }

   public static SASServiceContext createStatelessCompleteEstablishContext(EstablishContext establishCtx) {
      CompleteEstablishContext newCtxBody = new CompleteEstablishContext(establishCtx.getClientContextId(), false, (byte[])null);
      return new SASServiceContext((short)1, newCtxBody);
   }

   public String toString() {
      return "SASServiceContext Context (msgType = " + this.ctxMsgType + ", body = " + this.ctxBody + ")";
   }
}
