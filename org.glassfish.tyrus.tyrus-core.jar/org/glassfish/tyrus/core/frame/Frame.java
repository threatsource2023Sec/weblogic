package org.glassfish.tyrus.core.frame;

public class Frame {
   private final boolean fin;
   private final boolean rsv1;
   private final boolean rsv2;
   private final boolean rsv3;
   private final boolean mask;
   private final byte opcode;
   private final long payloadLength;
   private final Integer maskingKey;
   private final byte[] payloadData;
   private final boolean controlFrame;

   protected Frame(Frame frame) {
      this.fin = frame.fin;
      this.rsv1 = frame.rsv1;
      this.rsv2 = frame.rsv2;
      this.rsv3 = frame.rsv3;
      this.mask = frame.mask;
      this.opcode = frame.opcode;
      this.payloadLength = frame.payloadLength;
      this.maskingKey = frame.maskingKey;
      this.payloadData = frame.payloadData;
      this.controlFrame = (this.opcode & 8) == 8;
   }

   private Frame(boolean fin, boolean rsv1, boolean rsv2, boolean rsv3, boolean mask, byte opcode, long payloadLength, Integer maskingKey, byte[] payloadData) {
      this.fin = fin;
      this.rsv1 = rsv1;
      this.rsv2 = rsv2;
      this.rsv3 = rsv3;
      this.mask = mask;
      this.opcode = opcode;
      this.payloadLength = payloadLength;
      this.maskingKey = maskingKey;
      this.payloadData = payloadData;
      this.controlFrame = (opcode & 8) == 8;
   }

   public boolean isFin() {
      return this.fin;
   }

   public boolean isRsv1() {
      return this.rsv1;
   }

   public boolean isRsv2() {
      return this.rsv2;
   }

   public boolean isRsv3() {
      return this.rsv3;
   }

   public boolean isMask() {
      return this.mask;
   }

   public byte getOpcode() {
      return this.opcode;
   }

   public long getPayloadLength() {
      return this.payloadLength;
   }

   public Integer getMaskingKey() {
      return this.maskingKey;
   }

   public byte[] getPayloadData() {
      byte[] tmp = new byte[(int)this.payloadLength];
      System.arraycopy(this.payloadData, 0, tmp, 0, (int)this.payloadLength);
      return tmp;
   }

   public boolean isControlFrame() {
      return this.controlFrame;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder("Frame{");
      sb.append("fin=").append(this.fin);
      sb.append(", rsv1=").append(this.rsv1);
      sb.append(", rsv2=").append(this.rsv2);
      sb.append(", rsv3=").append(this.rsv3);
      sb.append(", mask=").append(this.mask);
      sb.append(", opcode=").append(this.opcode);
      sb.append(", payloadLength=").append(this.payloadLength);
      sb.append(", maskingKey=").append(this.maskingKey);
      sb.append('}');
      return sb.toString();
   }

   public static Builder builder() {
      return new Builder();
   }

   public static Builder builder(Frame frame) {
      return new Builder(frame);
   }

   // $FF: synthetic method
   Frame(boolean x0, boolean x1, boolean x2, boolean x3, boolean x4, byte x5, long x6, Integer x7, byte[] x8, Object x9) {
      this(x0, x1, x2, x3, x4, x5, x6, x7, x8);
   }

   public static final class Builder {
      private boolean fin;
      private boolean rsv1;
      private boolean rsv2;
      private boolean rsv3;
      private boolean mask;
      private byte opcode;
      private long payloadLength;
      private Integer maskingKey = null;
      private byte[] payloadData;

      public Builder() {
      }

      public Builder(Frame frame) {
         this.fin = frame.fin;
         this.rsv1 = frame.rsv1;
         this.rsv2 = frame.rsv2;
         this.rsv3 = frame.rsv3;
         this.mask = frame.mask;
         this.opcode = frame.opcode;
         this.payloadLength = frame.payloadLength;
         this.maskingKey = frame.maskingKey;
         this.payloadData = frame.payloadData;
      }

      public Frame build() {
         return new Frame(this.fin, this.rsv1, this.rsv2, this.rsv3, this.mask, this.opcode, this.payloadLength, this.maskingKey, this.payloadData);
      }

      public Builder fin(boolean fin) {
         this.fin = fin;
         return this;
      }

      public Builder rsv1(boolean rsv1) {
         this.rsv1 = rsv1;
         return this;
      }

      public Builder rsv2(boolean rsv2) {
         this.rsv2 = rsv2;
         return this;
      }

      public Builder rsv3(boolean rsv3) {
         this.rsv3 = rsv3;
         return this;
      }

      public Builder mask(boolean mask) {
         this.mask = mask;
         return this;
      }

      public Builder opcode(byte opcode) {
         this.opcode = (byte)(opcode & 15);
         return this;
      }

      public Builder payloadLength(long payloadLength) {
         this.payloadLength = payloadLength;
         return this;
      }

      public Builder maskingKey(Integer maskingKey) {
         this.maskingKey = maskingKey;
         return this;
      }

      public Builder payloadData(byte[] payloadData) {
         this.payloadData = payloadData;
         this.payloadLength = (long)payloadData.length;
         return this;
      }
   }
}
