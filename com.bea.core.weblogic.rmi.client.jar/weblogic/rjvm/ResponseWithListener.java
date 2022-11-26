package weblogic.rjvm;

import java.io.IOException;
import java.rmi.UnmarshalException;
import weblogic.common.WLObjectInput;
import weblogic.rmi.extensions.server.RuntimeMethodDescriptor;
import weblogic.utils.StackTraceUtils;

final class ResponseWithListener extends ResponseImpl {
   private final ResponseListener l;

   ResponseWithListener(RJVM rjvm, int timeout, ResponseListener l, RuntimeMethodDescriptor md) {
      super(rjvm, timeout, md);
      this.l = l;
   }

   public void notify(WLObjectInput msg) {
      super.notify(msg);
      this.l.response(this);
   }

   public void notifyError(WLObjectInput msgThrowable) {
      super.notifyError(msgThrowable);
      this.l.response(this);
   }

   public void notify(Throwable t) {
      super.notify(t);
      this.l.response(this);
   }

   public final Object unmarshalReturn() throws Throwable {
      this.retrieveThreadLocalContext();
      Throwable throwable = this.getThrowable();
      if (throwable != null) {
         throw StackTraceUtils.getThrowableWithCause(throwable);
      } else {
         try {
            return this.getMsgInput().readObject(Object.class);
         } catch (IOException var3) {
            throw new UnmarshalException("failed to unmarshal async result", var3);
         } catch (ClassNotFoundException var4) {
            throw new UnmarshalException("failed to unmarshal async result", var4);
         }
      }
   }

   public void peerGone(PeerGoneEvent e) {
      super.peerGone(e);
      this.l.response(this);
   }
}
