package weblogic.jms.dotnet.proxy.protocol;

import javax.jms.JMSException;
import javax.jms.Session;
import weblogic.jms.client.WLSessionImpl;
import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;
import weblogic.jms.extensions.WLSession;

public class ProxySessionCreateResponse extends ProxyResponse {
   private static final int EXTVERSION = 1;
   private static final int _HAS_EXTENSIONS = 1;
   private long sessionId;
   private int messagesMaximum;
   private long redeliveryDelay;
   private int pipelineGeneration;
   private long sequenceNumber;
   private boolean hasExtensions;

   public ProxySessionCreateResponse(long sessionId, Session session) {
      this.sessionId = sessionId;
      if (session instanceof WLSession) {
         try {
            this.hasExtensions = true;
            this.messagesMaximum = ((WLSession)session).getMessagesMaximum();
            this.redeliveryDelay = ((WLSession)session).getRedeliveryDelay();
            this.pipelineGeneration = ((WLSessionImpl)session).getPipelineGenerationFromProxy();
            this.sequenceNumber = ((WLSessionImpl)session).getLastSequenceNumber();
         } catch (JMSException var5) {
            throw new RuntimeException(var5);
         }
      }

   }

   public long getSessionId() {
      return this.sessionId;
   }

   public int getMessagesMaximum() {
      return this.messagesMaximum;
   }

   public long getRedeliveryDelay() {
      return this.redeliveryDelay;
   }

   public ProxySessionCreateResponse() {
   }

   public int getMarshalTypeCode() {
      return 31;
   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      if (this.hasExtensions) {
         this.versionFlags.setBit(1);
      }

      this.versionFlags.marshal(mw);
      mw.writeLong(this.sessionId);
      if (this.hasExtensions) {
         mw.writeInt(this.messagesMaximum);
         mw.writeLong(this.redeliveryDelay);
         mw.writeInt(this.pipelineGeneration);
         mw.writeLong(this.sequenceNumber);
      }

   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      this.sessionId = mr.readLong();
      if (this.versionFlags.isSet(1)) {
         this.messagesMaximum = mr.readInt();
         this.redeliveryDelay = mr.readLong();
         this.pipelineGeneration = mr.readInt();
         this.sequenceNumber = mr.readLong();
      }

   }
}
