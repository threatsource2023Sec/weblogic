package com.octetstring.vde.util.guid;

class GuidParams {
   private long timeStamp;
   private short clockSequence;
   private byte[] nodeID;

   long getTimeStamp() {
      return this.timeStamp;
   }

   void setTimeStamp(long timeStamp) {
      this.timeStamp = timeStamp;
   }

   short getClockSequence() {
      return this.clockSequence;
   }

   void setClockSequence(short clockSequence) {
      this.clockSequence = clockSequence;
   }

   byte[] getNodeID() {
      return this.nodeID;
   }

   void setNodeID(byte[] nodeID) {
      this.nodeID = nodeID;
   }
}
