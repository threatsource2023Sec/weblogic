package weblogic.jms.dotnet.transport.internal;

import weblogic.jms.dotnet.transport.MarshalReadable;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWritable;
import weblogic.jms.dotnet.transport.MarshalWriter;

class BootstrapRequest implements MarshalReadable, MarshalWritable {
   private String bootstrapServiceClassName;
   private int heartBeatInterval = 0;
   private int allowedMissedBeats = 0;

   BootstrapRequest() {
   }

   BootstrapRequest(String sc) {
      this.bootstrapServiceClassName = sc;
   }

   synchronized String getBootstrapServiceClassName() {
      return this.bootstrapServiceClassName;
   }

   synchronized int getHeartbeatInterval() {
      return this.heartBeatInterval;
   }

   synchronized int getAllowedMissedBeats() {
      return this.allowedMissedBeats;
   }

   public int getMarshalTypeCode() {
      return 20000;
   }

   public void marshal(MarshalWriter mw) {
      mw.writeUnsignedByte(1);
      mw.writeUnsignedByte(0);
      mw.writeString(this.bootstrapServiceClassName);
      mw.writeInt(this.heartBeatInterval);
      mw.writeInt(this.allowedMissedBeats);
   }

   public synchronized void unmarshal(MarshalReader mr) {
      int version = mr.read();

      while((mr.readByte() & 1) != 0) {
      }

      this.bootstrapServiceClassName = mr.readString();
      this.heartBeatInterval = mr.readInt();
      this.allowedMissedBeats = mr.readInt();
   }
}
