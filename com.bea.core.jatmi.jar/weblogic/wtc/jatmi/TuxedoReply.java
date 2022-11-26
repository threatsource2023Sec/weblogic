package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TuxedoReply implements Reply {
   private static final long serialVersionUID = 6610152513624509334L;
   private TypedBuffer myTypedBuffer;
   private int myTpurcode;
   private CallDescriptor reply_cd;
   private String myTypedBuffer_key;

   private void writeObject(ObjectOutputStream out) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(50000);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoReply/writeObject/");
      }

      if (null != TypedBufferFactory.getBufferPool() && null != this.myTypedBuffer) {
         this.myTypedBuffer_key = this.myTypedBuffer.toString();
         TypedBufferFactory.getBufferPool().put(this.myTypedBuffer_key, this.myTypedBuffer);
         TypedBuffer temp = this.myTypedBuffer;
         this.myTypedBuffer = null;
         out.defaultWriteObject();
         this.myTypedBuffer = temp;
         this.myTypedBuffer_key = null;
         if (traceEnabled) {
            ntrace.doTrace("]/TuxedoReply/writeObject/20/true");
         }

      } else {
         this.myTypedBuffer_key = null;
         out.defaultWriteObject();
         if (traceEnabled) {
            ntrace.doTrace("]/TuxedoReply/writeObject/10/false");
         }

      }
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      boolean traceEnabled = ntrace.isTraceEnabled(50000);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoReply/readObject/");
      }

      this.myTypedBuffer_key = null;
      in.defaultReadObject();
      if (null != TypedBufferFactory.getBufferPool() && null != this.myTypedBuffer_key) {
         this.myTypedBuffer = TypedBufferFactory.getBufferPool().get(this.myTypedBuffer_key);
         this.myTypedBuffer_key = null;
         if (traceEnabled) {
            ntrace.doTrace("]/TuxedoReply/readObject/20/true");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/TuxedoReply/readObject/10/false");
         }

      }
   }

   public TuxedoReply() {
   }

   public TuxedoReply(TypedBuffer tb, int tpurcode, CallDescriptor cd) {
      this.myTypedBuffer = tb;
      this.myTpurcode = tpurcode;
      this.reply_cd = cd;
   }

   public TypedBuffer getReplyBuffer() {
      return this.myTypedBuffer;
   }

   public int gettpurcode() {
      return this.myTpurcode;
   }

   public CallDescriptor getCallDescriptor() {
      return this.reply_cd;
   }

   public void setReplyBuffer(TypedBuffer tb) {
      this.myTypedBuffer = tb;
   }

   public void settpurcode(int tpurcode) {
      this.myTpurcode = tpurcode;
   }

   public void setCallDescriptor(CallDescriptor cd) {
      this.reply_cd = cd;
   }

   public String toString() {
      return new String(this.myTypedBuffer + ":" + this.myTpurcode + ":" + this.reply_cd);
   }
}
