package weblogic.jms.common;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.messaging.common.SQLExpression;

public final class JMSSQLExpression extends SQLExpression {
   public static final long serialVersionUID = 1287505877942351248L;
   private static final int EXTERNAL_VERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int HAS_SELECTOR_FLAG = 256;
   private static final int HAS_CONNECTION_ID_FLAG = 512;
   private static final int NO_LOCAL_FLAG = 1024;
   private static final int NOT_FORWARDED_FLAG = 2048;
   private static final int HAS_CLIENT_ID_FLAG = 4096;
   private static final int HAS_CLIENT_ID_POLICY_FLAG = 8192;
   private boolean noLocal;
   private boolean notForwarded;
   private JMSID connectionId;
   private transient JMSID sessionId = null;
   private String clientId;
   private int clientIdPolicy = 0;

   public JMSSQLExpression() {
   }

   public JMSSQLExpression(String selector, boolean noLocal, JMSID connectionId, boolean notForwarded) {
      super(selector);
      this.setNoLocal(noLocal);
      this.setConnectionId(connectionId);
      this.setSessionId((JMSID)null);
      this.setNotForwarded(notForwarded);
   }

   public JMSSQLExpression(String selector, boolean noLocal, JMSID connectionId, JMSID sessionId, String clientId, int subscriptionSharingPolicy) {
      super(selector);
      this.setNoLocal(noLocal);
      this.setConnectionId(connectionId);
      this.setSessionId(sessionId);
      this.setNotForwarded(false);
      this.setClientId(clientId);
      this.setClientIdPolicy(subscriptionSharingPolicy);
   }

   public JMSSQLExpression(String selector) {
      super(selector);
      this.setNoLocal(false);
      this.setConnectionId((JMSID)null);
      this.setSessionId((JMSID)null);
      this.setNotForwarded(false);
   }

   public boolean isNull() {
      return this.selector == null && !this.notForwarded && (!this.noLocal || this.connectionId == null);
   }

   public boolean isNoLocal() {
      return this.noLocal;
   }

   public void setNoLocal(boolean noLocal) {
      this.noLocal = noLocal;
   }

   public JMSID getConnectionId() {
      return this.connectionId;
   }

   private void setConnectionId(JMSID connectionId) {
      this.connectionId = connectionId;
   }

   public JMSID getSessionId() {
      return this.sessionId;
   }

   private void setSessionId(JMSID sessionId) {
      this.sessionId = sessionId;
   }

   public void setClientId(String clientId) {
      this.clientId = clientId;
   }

   public String getClientId() {
      return this.clientId;
   }

   public int getClientIdPolicy() {
      return this.clientIdPolicy;
   }

   public void setClientIdPolicy(int clientIdPolicy) {
      this.clientIdPolicy = clientIdPolicy;
   }

   public boolean isNotForwarded() {
      return this.notForwarded;
   }

   public void setNotForwarded(boolean notForwarded) {
      this.notForwarded = notForwarded;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int flags = 1;
      if (this.selector != null) {
         flags |= 256;
      }

      if (this.connectionId != null) {
         flags |= 512;
      }

      if (this.clientId != null) {
         flags |= 4096;
      }

      if (this.clientIdPolicy != 0) {
         flags |= 8192;
      }

      if (this.noLocal) {
         flags |= 1024;
      }

      if (this.notForwarded) {
         flags |= 2048;
      }

      out.writeInt(flags);
      if (this.selector != null) {
         out.writeUTF(this.selector);
      }

      if (this.connectionId != null) {
         this.connectionId.writeExternal(out);
      }

      if (this.clientId != null) {
         out.writeUTF(this.clientId);
      }

      if ((flags & 8192) != 0) {
         out.writeInt(this.clientIdPolicy);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readInt();
      if ((flags & 255) != 1) {
         throw new IOException("External version mismatch");
      } else {
         this.noLocal = (flags & 1024) != 0;
         this.notForwarded = (flags & 2048) != 0;
         if ((flags & 256) != 0) {
            this.selector = in.readUTF();
         }

         if ((flags & 512) != 0) {
            this.connectionId = new JMSID();
            this.connectionId.readExternal(in);
         }

         if ((flags & 4096) != 0) {
            this.clientId = in.readUTF();
         }

         if ((flags & 8192) != 0) {
            this.clientIdPolicy = in.readInt();
         }

      }
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("[ ");
      if (this.selector != null) {
         buf.append("selector=\"");
         buf.append(this.selector);
         buf.append("\" ");
      }

      if (this.connectionId != null) {
         buf.append("connectionID=\"");
         buf.append(this.connectionId);
         buf.append("\" ");
      }

      if (this.sessionId != null) {
         buf.append("sessionID=\"");
         buf.append(this.sessionId);
         buf.append("\" ");
      }

      if (this.clientId != null) {
         buf.append("clientId=\"");
         buf.append(this.clientId);
         buf.append("\" ");
      }

      buf.append("clientIdPoliy=\"");
      buf.append(this.clientIdPolicy);
      buf.append("\" ");
      if (this.noLocal) {
         buf.append("noLocal=true ");
      }

      if (this.notForwarded) {
         buf.append("notForwarded=true ");
      }

      buf.append(']');
      return buf.toString();
   }
}
