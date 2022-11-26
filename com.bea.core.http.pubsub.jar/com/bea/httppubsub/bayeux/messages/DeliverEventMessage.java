package com.bea.httppubsub.bayeux.messages;

import com.bea.httppubsub.BayeuxMessage;
import com.bea.httppubsub.Client;
import com.bea.httppubsub.EventMessage;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.UnsupportedEncodingException;

public class DeliverEventMessage extends AbstractBayeuxMessage implements EventMessage, Externalizable {
   private static final long serialVersionUID = 5907037191570341529L;
   private static final short MAJOR_VERSION = 1;
   private static final short MINOR_VERSION = 0;
   private String channel;
   private long createdTimestamp;
   private String payload;

   public DeliverEventMessage() {
   }

   public DeliverEventMessage(EventMessageImpl eventMessage) {
      if (eventMessage == null) {
         throw new IllegalArgumentException("EventMessage cannot be null.");
      } else {
         this.channel = eventMessage.getChannel();
         this.createdTimestamp = System.currentTimeMillis();
         this.payload = eventMessage.getPayLoad();
      }
   }

   public BayeuxMessage.TYPE getType() {
      return BayeuxMessage.TYPE.PUBLISH;
   }

   public boolean isMetaMessage() {
      return false;
   }

   public String toJSONRequestString() {
      throw new UnsupportedOperationException();
   }

   public String toJSONResponseString() {
      StringBuilder sbuf = new StringBuilder();
      sbuf.append("{");
      sbuf.append("\"channel\": \"").append(this.getChannel()).append("\", ");
      sbuf.append("\"data\": ").append(this.convertPayLoadToString(this.payload));
      sbuf.append("}");
      return sbuf.toString();
   }

   public void setCreatedTime(long timestamp) {
      this.createdTimestamp = timestamp;
   }

   public long getCreatedTime() {
      return this.createdTimestamp;
   }

   public byte[] getJSONResponseUTF8Bytes() {
      try {
         return this.toJSONResponseString().getBytes("UTF-8");
      } catch (UnsupportedEncodingException var2) {
         return null;
      }
   }

   public String getChannel() {
      return this.channel;
   }

   public void setPayLoad(String obj) {
      throw new UnsupportedOperationException();
   }

   public String getPayLoad() {
      return this.payload;
   }

   public void setClientId(String clientId) {
      throw new UnsupportedOperationException();
   }

   public String getClientId() {
      throw new UnsupportedOperationException();
   }

   public void setClient(Client client) {
      throw new UnsupportedOperationException();
   }

   public Client getClient() {
      throw new UnsupportedOperationException();
   }

   public void setExt(Ext ext) {
      throw new UnsupportedOperationException();
   }

   public Ext getExt() {
      throw new UnsupportedOperationException();
   }

   public void setAdvice(Advice advice) {
      throw new UnsupportedOperationException();
   }

   public Advice getAdvice() {
      throw new UnsupportedOperationException();
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      short major = in.readShort();
      short minor = in.readShort();
      if (major == 1 && minor == 0) {
         this.payload = (String)in.readObject();
         this.channel = (String)in.readObject();
         this.createdTimestamp = in.readLong();
      }

   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeShort(1);
      out.writeShort(0);
      out.writeObject(this.payload);
      out.writeObject(this.channel);
      out.writeLong(this.createdTimestamp);
   }
}
