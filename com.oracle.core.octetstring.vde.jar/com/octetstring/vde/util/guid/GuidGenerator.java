package com.octetstring.vde.util.guid;

public final class GuidGenerator {
   private static final int NUM_BYTES_IN_GUID = 16;
   private static final int TIME_LOW_INDEX_IN_TIME_ARRAY = 4;
   private static final int TIME_MID_INDEX_IN_TIME_ARRAY = 2;
   private static final int TIME_HIGH_INDEX_IN_TIME_ARRAY = 0;
   private static final int GUID_TIME_LOW_START = 0;
   private static final int GUID_TIME_LOW_SIZE = 4;
   private static final int GUID_TIME_MID_START = 4;
   private static final int GUID_TIME_MID_SIZE = 2;
   private static final int GUID_TIME_HIGH_START = 6;
   private static final int GUID_TIME_HIGH_SIZE = 2;
   private static final int CLOCK_SEQUENCE_LOW_BYTE_INDEX = 9;
   private static final int CLOCK_SEQUENCE_HIGH_BYTE_INDEX = 8;
   private static final int NODE_ID_START_INDEX = 10;
   private static final int NODE_ID_BYTE_LENGTH = 6;
   private static GuidGenerator instance = new GuidGenerator();

   private GuidGenerator() {
   }

   public static GuidGenerator getInstance() {
      return instance;
   }

   String nextGUID() {
      return GuidUtils.toHexString(this.nextGuidInBytes());
   }

   public byte[] nextGuidInBytes() {
      byte[] guidBytes = new byte[16];
      GuidParams guidParams = GuidStateManager.getInstance().nextGUIDParams();
      this.setTimeStampFields(guidParams.getTimeStamp(), guidBytes);
      this.setVersion(guidBytes);
      this.setClockSequenceFields(guidParams.getClockSequence(), guidBytes);
      this.setVariant(guidBytes);
      this.setNodeIDField(guidParams.getNodeID(), guidBytes);
      return guidBytes;
   }

   private void setTimeStampFields(long timeStamp, byte[] guidByteArray) {
      byte[] timeStampBytes = GuidUtils.toByteArray(timeStamp);
      this.setTimeLowField(timeStampBytes, guidByteArray);
      this.setTimeMidField(timeStampBytes, guidByteArray);
      this.setTimeHighField(timeStampBytes, guidByteArray);
   }

   private void setClockSequenceFields(short clockSequence, byte[] guidByteArray) {
      this.setClockSequenceLowField(clockSequence, guidByteArray);
      this.setClockSequenceHighField(clockSequence, guidByteArray);
   }

   private void setVersion(byte[] guidBytes) {
      guidBytes[6] = (byte)(guidBytes[6] & 15);
      guidBytes[6] = (byte)(guidBytes[6] | 16);
   }

   private void setVariant(byte[] guidBytes) {
      guidBytes[8] = (byte)(guidBytes[8] & 63);
      guidBytes[8] = (byte)(guidBytes[8] | 143);
   }

   private void setTimeLowField(byte[] timeStamp, byte[] guidBytes) {
      System.arraycopy(timeStamp, 4, guidBytes, 0, 4);
   }

   private void setTimeMidField(byte[] timeStamp, byte[] guidBytes) {
      System.arraycopy(timeStamp, 2, guidBytes, 4, 2);
   }

   private void setTimeHighField(byte[] timeStamp, byte[] guidBytes) {
      System.arraycopy(timeStamp, 0, guidBytes, 6, 2);
   }

   private void setClockSequenceLowField(short clockSequence, byte[] guidBytes) {
      guidBytes[9] = (byte)clockSequence;
   }

   private void setClockSequenceHighField(short clockSequence, byte[] guidBytes) {
      guidBytes[8] = (byte)(clockSequence >>> 8);
   }

   private void setNodeIDField(byte[] nodeID, byte[] guidBytes) {
      System.arraycopy(nodeID, 0, guidBytes, 10, 6);
   }
}
