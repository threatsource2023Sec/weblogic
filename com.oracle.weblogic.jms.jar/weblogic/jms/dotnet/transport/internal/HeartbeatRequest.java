package weblogic.jms.dotnet.transport.internal;

import weblogic.jms.dotnet.transport.MarshalReadable;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWritable;
import weblogic.jms.dotnet.transport.MarshalWriter;

class HeartbeatRequest implements MarshalWritable, MarshalReadable {
   private int heartbeatNumber;

   public HeartbeatRequest() {
   }

   public HeartbeatRequest(int heartbeatNumber) {
      this.heartbeatNumber = heartbeatNumber;
   }

   int getHeartbeatNumber() {
      return this.heartbeatNumber;
   }

   public int getMarshalTypeCode() {
      return 20003;
   }

   public synchronized void unmarshal(MarshalReader mr) {
      int version = mr.read();

      while((mr.readByte() & 1) != 0) {
      }

      this.heartbeatNumber = mr.readInt();
   }

   public synchronized void marshal(MarshalWriter mw) {
      mw.writeUnsignedByte(1);
      mw.writeUnsignedByte(0);
      mw.writeInt(this.heartbeatNumber);
   }
}
