package weblogic.jms.backend;

import java.util.HashMap;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.QuotaBean;
import weblogic.management.utils.GenericBeanListener;
import weblogic.messaging.kernel.Quota;
import weblogic.messaging.kernel.QuotaPolicy;

public final class BEQuota {
   private static final HashMap quotaSignature = new HashMap();
   private String name;
   private Quota quota;
   private int references = 0;
   private GenericBeanListener listener;

   BEQuota(String paramName, Quota paramQuota) {
      this.name = paramName;
      this.quota = paramQuota;
   }

   String getName() {
      return this.name;
   }

   void setQuotaBean(QuotaBean trueBean) {
      if (this.listener == null) {
         DescriptorBean db = (DescriptorBean)trueBean;
         this.listener = new GenericBeanListener(db, this, quotaSignature);
      }

      ++this.references;
   }

   boolean close() {
      --this.references;
      if (this.references <= 0 && this.listener != null) {
         this.listener.close();
         this.listener = null;
         this.references = 0;
         return true;
      } else {
         return false;
      }
   }

   Quota getQuota() {
      return this.quota;
   }

   public synchronized void setBytesMaximum(long bytesMaximum) {
      this.quota.setBytesMaximum(bytesMaximum);
   }

   public synchronized void setMessagesMaximum(long paramMessagesMaximum) {
      int messagesMaximum;
      if (paramMessagesMaximum > 2147483647L) {
         messagesMaximum = Integer.MAX_VALUE;
      } else {
         messagesMaximum = (int)paramMessagesMaximum;
      }

      this.quota.setMessagesMaximum(messagesMaximum);
   }

   public synchronized void setPolicy(String policy) {
      this.quota.setPolicy(this.findPolicy(policy));
   }

   private QuotaPolicy findPolicy(String policy) {
      if (policy == null) {
         return QuotaPolicy.FIFO;
      } else if (policy.equalsIgnoreCase("FIFO")) {
         return QuotaPolicy.FIFO;
      } else if (policy.equalsIgnoreCase("Preemptive")) {
         return QuotaPolicy.PREEMPTIVE;
      } else {
         throw new AssertionError("Invalid quota policy " + policy);
      }
   }

   static {
      quotaSignature.put("BytesMaximum", Long.TYPE);
      quotaSignature.put("MessagesMaximum", Long.TYPE);
      quotaSignature.put("Policy", String.class);
   }
}
