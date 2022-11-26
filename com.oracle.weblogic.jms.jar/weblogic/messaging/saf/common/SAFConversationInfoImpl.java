package weblogic.messaging.saf.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import weblogic.messaging.runtime.DiagnosticImageTimeoutException;
import weblogic.messaging.saf.SAFConversationInfo;
import weblogic.messaging.saf.SAFErrorHandler;
import weblogic.messaging.saf.internal.SAFDiagnosticImageSource;
import weblogic.messaging.saf.internal.SAFManagerImpl;
import weblogic.messaging.saf.utils.Util;

public final class SAFConversationInfoImpl implements SAFConversationInfo {
   static final long serialVersionUID = -6685748132717633470L;
   private static final int EXTVERSION1 = 1;
   private static final int VERSION_MASK = 255;
   private static final int EXTVERSION = 1;
   private String conversationName;
   private int transportType;
   private int qos = 1;
   private long timestamp;
   private int destinationType;
   private String sourceURL;
   private String destinationURL;
   private long ttl = Long.MAX_VALUE;
   private long maximumIdleTime = Long.MAX_VALUE;
   private boolean inorder = true;
   private SAFRemoteContext remoteContext;
   private SAFErrorHandler errorHandler;
   private int conversationType;
   private boolean dynamic = true;
   private String dynamicConversationName;
   private Externalizable conversationContext;
   private SAFConversationInfo conversationOffer;
   private long conversationTimeout = Long.MAX_VALUE;
   private int timeoutPolicy;
   private String createConvMsgID;
   private boolean conversationAlreadyCreated = false;
   private static final int _HASREMOTECONTEXT = 512;
   private static final int _ISINORDER = 2048;
   private static final int _HASERRORHANDLER = 4096;
   private static final int _HASTIMESTAMP = 8192;
   private static final int _ISDYNAMIC = 32768;
   private static final int _ISWSRMSAFCONVERSATIONINFO = 65536;
   private static final int _HASDYNAMICCONVERSATIONNAME = 131072;
   private static final int _HASCONVERSATIONCONTEXT = 262144;
   private static final int _HASCONVTIMEOUT = 524288;
   private static final int _HASOFFER = 1048576;
   private static final int _HASCONVTIMEOUTPOLICY = 2097152;
   private static final int _HASCREATECONVMSGID = 4194304;
   private static final int _ISCONVERSATIONALREADYCREATED = 16777216;

   public SAFConversationInfoImpl(int conversationType) {
      this.conversationType = conversationType;
   }

   public synchronized SAFConversationInfo getConversationOffer() {
      return this.conversationOffer;
   }

   public synchronized void setConversationOffer(SAFConversationInfo conversationOffer) {
      this.conversationOffer = conversationOffer;
   }

   public int getTimeoutPolicy() {
      return this.timeoutPolicy;
   }

   public void setTimeoutPolicy(int timeoutPolicy) {
      this.timeoutPolicy = timeoutPolicy;
   }

   public void setCreateConversationMessageID(String id) {
      this.createConvMsgID = id;
   }

   public String getCreateConversationMessageID() {
      return this.createConvMsgID;
   }

   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (!(o instanceof SAFConversationInfoImpl)) {
         return false;
      } else {
         SAFConversationInfoImpl safConversationInfo = (SAFConversationInfoImpl)o;
         if (this.conversationType == 2) {
            return this.conversationName.equals(safConversationInfo.conversationName);
         } else if (this.destinationType != safConversationInfo.destinationType) {
            return false;
         } else if (this.qos != safConversationInfo.qos) {
            return false;
         } else if (this.transportType != safConversationInfo.transportType) {
            return false;
         } else if (!this.conversationName.equals(safConversationInfo.conversationName)) {
            return false;
         } else if (!this.destinationURL.equals(safConversationInfo.destinationURL)) {
            return false;
         } else {
            return this.sourceURL.equals(safConversationInfo.sourceURL);
         }
      }
   }

   public int hashCode() {
      int result = this.conversationName != null ? this.conversationName.hashCode() : 0;
      if (this.conversationType == 2) {
         return result;
      } else {
         result = 29 * result + this.transportType;
         result = 29 * result + this.qos;
         result = 29 * result + this.destinationType;
         if (this.sourceURL != null) {
            result = 29 * result + this.sourceURL.hashCode();
         }

         if (this.destinationURL != null) {
            result = 29 * result + this.destinationURL.hashCode();
         }

         return result;
      }
   }

   public String toString() {
      return "<ConversationInfo> = { ConversationName=" + this.conversationName + ", DynamicName=" + this.dynamicConversationName + ", SourceURL=" + this.sourceURL + ", DestinationURL=" + this.destinationURL + ", DestinationType=" + this.destinationType + ", QOS=" + this.qos + ", timestamp=" + this.timestamp + ", timeoutPolicy=" + this.timeoutPolicy + ", timeToLive=" + this.ttl + ", conversationTimeout=" + this.conversationTimeout + ", maximumIdleTime=" + this.maximumIdleTime + ", createConversationMessageID=" + this.createConvMsgID + ", offeredConversationName=" + (this.getConversationOffer() == null ? null : this.getConversationOffer().getConversationName()) + "}";
   }

   public int getQOS() {
      return this.qos;
   }

   public void setQOS(int qos) {
      this.qos = qos;
   }

   public String getSourceURL() {
      return this.sourceURL;
   }

   public void setSourceURL(String sourceUrl) {
      this.sourceURL = sourceUrl;
   }

   public int getTransportType() {
      return this.transportType;
   }

   public void setTransportType(int transportType) {
      this.transportType = transportType;
   }

   public int getDestinationType() {
      return this.destinationType;
   }

   public void setDestinationType(int type) {
      this.destinationType = type;
   }

   public String getDestinationURL() {
      return this.destinationURL;
   }

   public void setDestinationURL(String destinationUrl) {
      this.destinationURL = destinationUrl;
   }

   public long getTimeToLive() {
      return this.ttl;
   }

   public void setTimeToLive(long ttl) {
      this.ttl = ttl;
   }

   public long getMaximumIdleTime() {
      return this.maximumIdleTime;
   }

   public void setMaximumIdleTime(long maximumIdleTime) {
      this.maximumIdleTime = maximumIdleTime;
   }

   public long getConversationTimeout() {
      return this.conversationTimeout;
   }

   public void setConversationTimeout(long conversationTimeout) {
      this.conversationTimeout = conversationTimeout;
   }

   public String getConversationName() {
      return this.conversationName;
   }

   public void setConversationName(String conversationName) {
      this.conversationName = conversationName;
   }

   public boolean isInorder() {
      return this.inorder;
   }

   public void setInorder(boolean inorder) {
      this.inorder = inorder;
   }

   public SAFRemoteContext getRemoteContext() {
      return this.remoteContext;
   }

   public void setRemoteContext(SAFRemoteContext remoteContext) {
      this.remoteContext = remoteContext;
   }

   public SAFErrorHandler getErrorHandler() {
      return this.errorHandler;
   }

   public void setErrorHandler(SAFErrorHandler errorHandler) {
      this.errorHandler = errorHandler;
   }

   public long getTimestamp() {
      return this.timestamp;
   }

   public void setTimestamp(long timestamp) {
      this.timestamp = timestamp;
   }

   public boolean isDynamic() {
      return this.dynamic;
   }

   public void setDynamic(boolean dynamic) {
      this.dynamic = dynamic;
   }

   public synchronized String getDynamicConversationName() {
      return this.dynamicConversationName;
   }

   public synchronized void setDynamicConversationName(String dynamicConversationName) {
      this.dynamicConversationName = dynamicConversationName;
   }

   public SAFConversationInfoImpl() {
   }

   public synchronized void setContext(Externalizable conversationContext) {
      this.conversationContext = conversationContext;
   }

   public synchronized Externalizable getContext() {
      return this.conversationContext;
   }

   public boolean isConversationAlreadyCreated() {
      return this.conversationAlreadyCreated;
   }

   public void setConversationAlreadyCreated(boolean conversationAlreadyCreated) {
      this.conversationAlreadyCreated = conversationAlreadyCreated;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int version = 1;
      int flags = 0;
      flags |= version;
      if (this.timestamp != 0L) {
         flags |= 8192;
      }

      if (this.remoteContext != null) {
         flags |= 512;
      }

      if (this.inorder) {
         flags |= 2048;
      }

      if (this.dynamic) {
         flags |= 32768;
      }

      if (this.errorHandler != null) {
         flags |= 4096;
      }

      if (this.conversationType == 2) {
         flags |= 65536;
      }

      if (this.dynamicConversationName != null) {
         flags |= 131072;
      }

      if (this.conversationContext != null) {
         flags |= 262144;
      }

      if (this.conversationTimeout != Long.MAX_VALUE) {
         flags |= 524288;
      }

      if (this.conversationOffer != null) {
         flags |= 1048576;
      }

      if (this.timeoutPolicy != 0) {
         flags |= 2097152;
      }

      if (this.createConvMsgID != null) {
         flags |= 4194304;
      }

      if (this.conversationAlreadyCreated) {
         flags |= 16777216;
      }

      out.writeInt(flags);
      out.writeInt(this.qos);
      out.writeInt(this.destinationType);
      out.writeInt(this.transportType);
      out.writeUTF(this.conversationName);
      if (this.dynamicConversationName != null) {
         out.writeUTF(this.dynamicConversationName);
      }

      out.writeObject(this.sourceURL);
      out.writeUTF(this.destinationURL);
      out.writeLong(this.ttl);
      out.writeLong(this.maximumIdleTime);
      if (this.conversationTimeout != Long.MAX_VALUE) {
         out.writeLong(this.conversationTimeout);
      }

      if (this.remoteContext != null) {
         this.remoteContext.writeExternal(out);
      }

      if (this.conversationContext != null) {
         out.writeObject(this.conversationContext);
      }

      if (this.errorHandler != null) {
         this.errorHandler.writeExternal(out);
      }

      if (this.timestamp != 0L) {
         out.writeLong(this.timestamp);
      }

      out.writeInt(0);
      if (this.conversationOffer != null) {
         this.conversationOffer.writeExternal(out);
      }

      if (this.timeoutPolicy != 0) {
         out.writeInt(this.timeoutPolicy);
      }

      if (this.createConvMsgID != null) {
         out.writeUTF(this.createConvMsgID);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readInt();
      int version = flags & 255;
      if (version != 1) {
         throw Util.versionIOException(version, 1, 1);
      } else {
         this.conversationType = (flags & 65536) != 0 ? 2 : 1;
         this.qos = in.readInt();
         this.destinationType = in.readInt();
         this.transportType = in.readInt();
         this.conversationName = in.readUTF();
         if ((flags & 131072) != 0) {
            this.dynamicConversationName = in.readUTF();
         }

         this.sourceURL = (String)in.readObject();
         this.destinationURL = in.readUTF();
         this.ttl = in.readLong();
         this.maximumIdleTime = in.readLong();
         if ((flags & 524288) != 0) {
            this.conversationTimeout = in.readLong();
         }

         if ((flags & 512) != 0) {
            this.remoteContext = new SAFRemoteContext();
            this.remoteContext.readExternal(in);
         }

         if ((flags & 262144) != 0) {
            this.conversationContext = (Externalizable)in.readObject();
         }

         this.inorder = (flags & 2048) != 0;
         this.dynamic = (flags & '耀') != 0;
         if ((flags & 4096) != 0) {
            this.errorHandler = SAFManagerImpl.getManager().getEndpointManager(this.destinationType).createErrorHandlerInstance();
            if (this.errorHandler != null) {
               this.errorHandler.readExternal(in);
            }
         }

         if ((flags & 8192) != 0) {
            this.timestamp = in.readLong();
         }

         in.readInt();
         if ((flags & 1048576) != 0) {
            this.conversationOffer = new SAFConversationInfoImpl();
            this.conversationOffer.readExternal(in);
         }

         if ((flags & 2097152) != 0) {
            this.timeoutPolicy = in.readInt();
         }

         if ((flags & 4194304) != 0) {
            this.createConvMsgID = in.readUTF();
         }

         this.conversationAlreadyCreated = (flags & 16777216) != 0;
      }
   }

   public void dump(SAFDiagnosticImageSource imageSource, XMLStreamWriter xsw) throws XMLStreamException, DiagnosticImageTimeoutException {
      imageSource.checkTimeout();
      xsw.writeStartElement("ConversationInfo");
      xsw.writeAttribute("qos", String.valueOf(this.qos));
      xsw.writeAttribute("sourceURL", this.sourceURL);
      xsw.writeAttribute("destinationType", String.valueOf(this.destinationType));
      xsw.writeAttribute("destinationURL", this.destinationURL);
      xsw.writeAttribute("name", this.conversationName);
      xsw.writeAttribute("transportType", String.valueOf(this.transportType));
      xsw.writeAttribute("isInorder", String.valueOf(this.inorder));
      xsw.writeAttribute("isDynamic", String.valueOf(this.dynamic));
      xsw.writeAttribute("ttl", String.valueOf(this.ttl));
      xsw.writeAttribute("maximumIdleTime", String.valueOf(this.maximumIdleTime));
      xsw.writeAttribute("timeout", String.valueOf(this.conversationTimeout));
      xsw.writeAttribute("timeoutPolicy", String.valueOf(this.timeoutPolicy));
      xsw.writeEndElement();
   }
}
