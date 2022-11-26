package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.client.ConsumerInternal;
import weblogic.jms.common.JMSConsumerReceiveResponse;
import weblogic.jms.common.JMSID;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.Request;
import weblogic.messaging.dispatcher.CompletionListener;
import weblogic.messaging.dispatcher.Response;

public final class FEConsumerReceiveRequest extends Request implements Externalizable, CompletionListener {
   static final long serialVersionUID = 8859525699995436679L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int TIMEOUT_MASK = 3840;
   private static final int TIMEOUT_NEVER = 256;
   private static final int TIMEOUT_NO_WAIT = 512;
   static final int START = 0;
   static final int CONTINUE = 1;
   private long timeout;
   private transient CompletionListener appListener;
   private transient ConsumerInternal consumerInternal;

   public FEConsumerReceiveRequest(JMSID consumerId, long timeout, CompletionListener appListener, ConsumerInternal consumerInternal) {
      super(consumerId, 3338);
      this.timeout = timeout;
      if (appListener != null) {
         this.consumerInternal = consumerInternal;
         this.appListener = appListener;
         this.setListener(this);
      }

   }

   long getTimeout() {
      return this.timeout;
   }

   public int remoteSignature() {
      return 19;
   }

   public Response createResponse() {
      return new JMSConsumerReceiveResponse();
   }

   public void onCompletion(Object result) {
      CompletionListener stableListener;
      synchronized(this) {
         if (this.appListener == null) {
            return;
         }

         stableListener = this.appListener;
         this.appListener = null;
      }

      try {
         this.consumerInternal.getSession().proccessReceiveResponse(this.consumerInternal, result, stableListener);
      } catch (Throwable var5) {
         stableListener.onException(var5);
      }

   }

   public void onException(Throwable throwable) {
      CompletionListener stableListener;
      synchronized(this) {
         if (this.appListener == null) {
            return;
         }

         stableListener = this.appListener;
         this.appListener = null;
      }

      stableListener.onException(throwable);
   }

   public FEConsumerReceiveRequest() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out = this.getVersionedStream(out);
      int mask = 1;
      if (this.timeout == Long.MAX_VALUE) {
         mask |= 256;
      } else if (this.timeout == 9223372036854775806L) {
         mask |= 512;
      }

      out.writeInt(mask);
      super.writeExternal(out);
      if ((mask & 3840) == 0) {
         out.writeLong(this.timeout);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         super.readExternal(in);
         if ((mask & 3840) == 256) {
            this.timeout = Long.MAX_VALUE;
         } else if ((mask & 3840) == 512) {
            this.timeout = 9223372036854775806L;
         } else {
            this.timeout = in.readLong();
         }

      }
   }
}
