package com.octetstring.vde.util.guid;

final class GuidStateManager {
   private long lastTimeStamp;
   private short clockSequence;
   private byte[] nodeID;
   private static GuidStateManager instance = new GuidStateManager();

   private GuidStateManager() {
      this.initializeGUIDParameters();
   }

   static GuidStateManager getInstance() {
      return instance;
   }

   GuidParams nextGUIDParams() {
      GuidParams nextGuidParams = new GuidParams();
      nextGuidParams.setTimeStamp(this.getTimeStamp());
      nextGuidParams.setClockSequence(this.getClockSequence());
      nextGuidParams.setNodeID(this.nodeID);
      return nextGuidParams;
   }

   private void initializeGUIDParameters() {
      this.nodeID = GuidParamGenerator.generateNodeID();
      this.clockSequence = GuidParamGenerator.generateClockSequence();
      this.lastTimeStamp = GuidClock.getInstance().getTime();
   }

   private long getTimeStamp() {
      long currentTimeStamp = GuidClock.getInstance().getTime();
      if (this.isTimeSetBackward(currentTimeStamp)) {
         this.incrementClockSequence();
      }

      this.lastTimeStamp = currentTimeStamp;
      return currentTimeStamp;
   }

   boolean isTimeSetBackward(long currentTimeStamp) {
      return currentTimeStamp < this.lastTimeStamp;
   }

   void incrementClockSequence() {
      ++this.clockSequence;
   }

   private short getClockSequence() {
      return this.clockSequence;
   }
}
