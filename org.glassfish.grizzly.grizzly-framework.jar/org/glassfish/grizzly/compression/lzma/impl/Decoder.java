package org.glassfish.grizzly.compression.lzma.impl;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.compression.lzma.LZMADecoder;
import org.glassfish.grizzly.compression.lzma.impl.lz.OutWindow;
import org.glassfish.grizzly.compression.lzma.impl.rangecoder.BitTreeDecoder;
import org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeDecoder;

public class Decoder {
   final OutWindow m_OutWindow = new OutWindow();
   final RangeDecoder m_RangeDecoder = new RangeDecoder();
   final short[] m_IsMatchDecoders = new short[192];
   final short[] m_IsRepDecoders = new short[12];
   final short[] m_IsRepG0Decoders = new short[12];
   final short[] m_IsRepG1Decoders = new short[12];
   final short[] m_IsRepG2Decoders = new short[12];
   final short[] m_IsRep0LongDecoders = new short[192];
   final BitTreeDecoder[] m_PosSlotDecoder = new BitTreeDecoder[4];
   final short[] m_PosDecoders = new short[114];
   final BitTreeDecoder m_PosAlignDecoder = new BitTreeDecoder(4);
   final LenDecoder m_LenDecoder = new LenDecoder();
   final LenDecoder m_RepLenDecoder = new LenDecoder();
   final LiteralDecoder m_LiteralDecoder = new LiteralDecoder();
   int m_DictionarySize = -1;
   int m_DictionarySizeCheck = -1;
   int m_PosStateMask;

   public Decoder() {
      for(int i = 0; i < 4; ++i) {
         this.m_PosSlotDecoder[i] = new BitTreeDecoder(6);
      }

   }

   public boolean setDecoderProperties(byte[] properties) {
      if (properties.length < 5) {
         return false;
      } else {
         int val = properties[0] & 255;
         int lc = val % 9;
         int remainder = val / 9;
         int lp = remainder % 5;
         int pb = remainder / 5;
         int dictionarySize = 0;

         for(int i = 0; i < 4; ++i) {
            dictionarySize += (properties[1 + i] & 255) << i * 8;
         }

         return this.setLcLpPb(lc, lp, pb) && this.setDictionarySize(dictionarySize);
      }
   }

   boolean setDictionarySize(int dictionarySize) {
      if (dictionarySize < 0) {
         return false;
      } else {
         if (this.m_DictionarySize != dictionarySize) {
            this.m_DictionarySize = dictionarySize;
            this.m_DictionarySizeCheck = Math.max(this.m_DictionarySize, 1);
            this.m_OutWindow.create(Math.max(this.m_DictionarySizeCheck, 4096));
         }

         return true;
      }
   }

   boolean setLcLpPb(int lc, int lp, int pb) {
      if (lc <= 8 && lp <= 4 && pb <= 4) {
         this.m_LiteralDecoder.create(lp, lc);
         int numPosStates = 1 << pb;
         this.m_LenDecoder.create(numPosStates);
         this.m_RepLenDecoder.create(numPosStates);
         this.m_PosStateMask = numPosStates - 1;
         return true;
      } else {
         return false;
      }
   }

   void init() throws IOException {
      this.m_OutWindow.init(false);
      RangeDecoder.initBitModels(this.m_IsMatchDecoders);
      RangeDecoder.initBitModels(this.m_IsRep0LongDecoders);
      RangeDecoder.initBitModels(this.m_IsRepDecoders);
      RangeDecoder.initBitModels(this.m_IsRepG0Decoders);
      RangeDecoder.initBitModels(this.m_IsRepG1Decoders);
      RangeDecoder.initBitModels(this.m_IsRepG2Decoders);
      RangeDecoder.initBitModels(this.m_PosDecoders);
      this.m_LiteralDecoder.init();

      for(int i = 0; i < 4; ++i) {
         this.m_PosSlotDecoder[i].init();
      }

      this.m_LenDecoder.init();
      this.m_RepLenDecoder.init();
      this.m_PosAlignDecoder.init();
      this.m_RangeDecoder.init();
   }

   public State code(LZMADecoder.LZMAInputState decoderState, long outSize) throws IOException {
      Buffer inputBuffer = decoderState.getSrc();
      this.m_RangeDecoder.initFromState(decoderState);
      this.m_OutWindow.initFromState(decoderState);
      if (!decoderState.isInitialized()) {
         if (inputBuffer.remaining() < 13) {
            return Decoder.State.NEED_MORE_DATA;
         }

         decoderState.initialize(inputBuffer);
         this.init();
      }

      while(true) {
         label56:
         while(true) {
            switch (decoderState.inner1State) {
               case 0:
                  if (outSize < 0L || decoderState.nowPos64 < outSize) {
                     decoderState.inner1State = 1;
                     break label56;
                  }
                  break;
               case 1:
                  break label56;
               case 2:
                  if (!this.processState2(decoderState)) {
                     return Decoder.State.NEED_MORE_DATA;
                  }

                  decoderState.inner1State = 0;
                  continue;
               case 3:
                  State internalState = this.processState3(decoderState);
                  if (internalState == Decoder.State.NEED_MORE_DATA || internalState == Decoder.State.ERR) {
                     return internalState;
                  }

                  decoderState.inner1State = 0;
                  if (internalState == Decoder.State.DONE) {
                     break;
                  }
               default:
                  continue;
            }

            this.m_OutWindow.flush();
            this.m_OutWindow.releaseBuffer();
            this.m_RangeDecoder.releaseBuffer();
            return Decoder.State.DONE;
         }

         decoderState.posState = (int)decoderState.nowPos64 & this.m_PosStateMask;
         if (!this.m_RangeDecoder.decodeBit(decoderState, this.m_IsMatchDecoders, (decoderState.state << 4) + decoderState.posState)) {
            return Decoder.State.NEED_MORE_DATA;
         }

         int result = decoderState.lastMethodResult;
         decoderState.inner1State = result == 0 ? 2 : 3;
      }
   }

   private boolean processState2(LZMADecoder.LZMAInputState decoderState) throws IOException {
      while(true) {
         switch (decoderState.inner2State) {
            case 0:
               decoderState.decoder2 = this.m_LiteralDecoder.getDecoder((int)decoderState.nowPos64, decoderState.prevByte);
               decoderState.inner2State = !Base.stateIsCharState(decoderState.state) ? 1 : 2;
               break;
            case 1:
               if (!decoderState.decoder2.decodeWithMatchByte(decoderState, this.m_RangeDecoder, this.m_OutWindow.getByte(decoderState.rep0))) {
                  return false;
               }

               decoderState.prevByte = (byte)decoderState.lastMethodResult;
               decoderState.inner2State = 3;
               break;
            case 2:
               if (!decoderState.decoder2.decodeNormal(decoderState, this.m_RangeDecoder)) {
                  return false;
               }

               decoderState.prevByte = (byte)decoderState.lastMethodResult;
               decoderState.inner2State = 3;
            case 3:
               this.m_OutWindow.putByte(decoderState.prevByte);
               decoderState.state = Base.stateUpdateChar(decoderState.state);
               ++decoderState.nowPos64;
               decoderState.inner2State = 0;
               return true;
         }
      }
   }

   private State processState3(LZMADecoder.LZMAInputState decoderState) throws IOException {
      while(true) {
         switch (decoderState.inner2State) {
            case 0:
               if (!this.m_RangeDecoder.decodeBit(decoderState, this.m_IsRepDecoders, decoderState.state)) {
                  return Decoder.State.NEED_MORE_DATA;
               }

               decoderState.inner2State = decoderState.lastMethodResult == 1 ? 1 : 2;
               break;
            case 1:
               if (!this.processState31(decoderState)) {
                  return Decoder.State.NEED_MORE_DATA;
               }

               decoderState.inner2State = 3;
               break;
            case 2:
               State internalResult = this.processState32(decoderState);
               if (internalResult != Decoder.State.CONTINUE) {
                  return internalResult;
               }

               decoderState.inner2State = 3;
            case 3:
               if ((long)decoderState.rep0 < decoderState.nowPos64 && decoderState.rep0 < this.m_DictionarySizeCheck) {
                  this.m_OutWindow.copyBlock(decoderState.rep0, decoderState.state3Len);
                  decoderState.nowPos64 += (long)decoderState.state3Len;
                  decoderState.prevByte = this.m_OutWindow.getByte(0);
                  decoderState.inner2State = 0;
                  return Decoder.State.CONTINUE;
               }

               return Decoder.State.ERR;
         }
      }
   }

   private boolean processState31(LZMADecoder.LZMAInputState decoderState) throws IOException {
      while(true) {
         switch (decoderState.state31) {
            case 0:
               decoderState.state3Len = 0;
               if (!this.m_RangeDecoder.decodeBit(decoderState, this.m_IsRepG0Decoders, decoderState.state)) {
                  return false;
               }

               decoderState.state31 = decoderState.lastMethodResult == 0 ? 1 : 2;
               break;
            case 1:
               if (!this.m_RangeDecoder.decodeBit(decoderState, this.m_IsRep0LongDecoders, (decoderState.state << 4) + decoderState.posState)) {
                  return false;
               }

               if (decoderState.lastMethodResult == 0) {
                  decoderState.state = Base.stateUpdateShortRep(decoderState.state);
                  decoderState.state3Len = 1;
               }

               decoderState.state31 = 3;
               break;
            case 2:
               if (!this.processState311(decoderState)) {
                  return false;
               }

               decoderState.state31 = 3;
            case 3:
               if (decoderState.state3Len != 0) {
                  decoderState.state31 = 0;
                  return true;
               }

               decoderState.state31 = 4;
            case 4:
               if (!this.m_RepLenDecoder.decode(decoderState, this.m_RangeDecoder, decoderState.posState)) {
                  return false;
               }

               decoderState.state3Len = decoderState.lastMethodResult + 2;
               decoderState.state = Base.stateUpdateRep(decoderState.state);
               decoderState.state31 = 0;
               return true;
         }
      }
   }

   private boolean processState311(LZMADecoder.LZMAInputState decoderState) throws IOException {
      while(true) {
         switch (decoderState.state311) {
            case 0:
               if (!this.m_RangeDecoder.decodeBit(decoderState, this.m_IsRepG1Decoders, decoderState.state)) {
                  return false;
               }

               decoderState.state311 = decoderState.lastMethodResult == 0 ? 1 : 2;
               break;
            case 1:
               decoderState.state311Distance = decoderState.rep1;
               decoderState.state311 = 3;
               break;
            case 2:
               if (!this.m_RangeDecoder.decodeBit(decoderState, this.m_IsRepG2Decoders, decoderState.state)) {
                  return false;
               }

               if (decoderState.lastMethodResult == 0) {
                  decoderState.state311Distance = decoderState.rep2;
               } else {
                  decoderState.state311Distance = decoderState.rep3;
                  decoderState.rep3 = decoderState.rep2;
               }

               decoderState.rep2 = decoderState.rep1;
            case 3:
               decoderState.rep1 = decoderState.rep0;
               decoderState.rep0 = decoderState.state311Distance;
               decoderState.state311 = 0;
               return true;
         }
      }
   }

   private State processState32(LZMADecoder.LZMAInputState decoderState) throws IOException {
      while(true) {
         switch (decoderState.state32) {
            case 0:
               decoderState.rep3 = decoderState.rep2;
               decoderState.rep2 = decoderState.rep1;
               decoderState.rep1 = decoderState.rep0;
               decoderState.state32 = 1;
            case 1:
               if (!this.m_LenDecoder.decode(decoderState, this.m_RangeDecoder, decoderState.posState)) {
                  return Decoder.State.NEED_MORE_DATA;
               }

               decoderState.state3Len = 2 + decoderState.lastMethodResult;
               decoderState.state = Base.stateUpdateMatch(decoderState.state);
               decoderState.state32 = 2;
            case 2:
               if (!this.m_PosSlotDecoder[Base.getLenToPosState(decoderState.state3Len)].decode(decoderState, this.m_RangeDecoder)) {
                  return Decoder.State.NEED_MORE_DATA;
               }

               decoderState.state32PosSlot = decoderState.lastMethodResult;
               decoderState.state32 = decoderState.state32PosSlot >= 4 ? 3 : 4;
               break;
            case 3:
               State localState = this.processState321(decoderState);
               if (localState == Decoder.State.CONTINUE) {
                  decoderState.state32 = 0;
               }

               return localState;
            case 4:
               decoderState.rep0 = decoderState.state32PosSlot;
               decoderState.state32 = 0;
               return Decoder.State.CONTINUE;
         }
      }
   }

   private State processState321(LZMADecoder.LZMAInputState decoderState) throws IOException {
      while(true) {
         switch (decoderState.state321) {
            case 0:
               decoderState.state321NumDirectBits = (decoderState.state32PosSlot >> 1) - 1;
               decoderState.rep0 = (2 | decoderState.state32PosSlot & 1) << decoderState.state321NumDirectBits;
               decoderState.state321 = decoderState.state32PosSlot < 14 ? 1 : 2;
               break;
            case 1:
               if (!BitTreeDecoder.reverseDecode(decoderState, this.m_PosDecoders, decoderState.rep0 - decoderState.state32PosSlot - 1, this.m_RangeDecoder, decoderState.state321NumDirectBits)) {
                  return Decoder.State.NEED_MORE_DATA;
               }

               decoderState.rep0 += decoderState.lastMethodResult;
               decoderState.state321 = 0;
               return Decoder.State.CONTINUE;
            case 2:
               if (!this.m_RangeDecoder.decodeDirectBits(decoderState, decoderState.state321NumDirectBits - 4)) {
                  return Decoder.State.NEED_MORE_DATA;
               }

               decoderState.rep0 += decoderState.lastMethodResult << 4;
               decoderState.state321 = 3;
               break;
            case 3:
               if (!this.m_PosAlignDecoder.reverseDecode(decoderState, this.m_RangeDecoder)) {
                  return Decoder.State.NEED_MORE_DATA;
               }

               decoderState.rep0 += decoderState.lastMethodResult;
               decoderState.state321 = 0;
               if (decoderState.rep0 < 0) {
                  if (decoderState.rep0 == -1) {
                     return Decoder.State.DONE;
                  }

                  return Decoder.State.ERR;
               }

               return Decoder.State.CONTINUE;
         }
      }
   }

   public static class LiteralDecoder {
      Decoder2[] m_Coders;
      int m_NumPrevBits;
      int m_NumPosBits;
      int m_PosMask;

      public void create(int numPosBits, int numPrevBits) {
         if (this.m_Coders == null || this.m_NumPrevBits != numPrevBits || this.m_NumPosBits != numPosBits) {
            this.m_NumPosBits = numPosBits;
            this.m_PosMask = (1 << numPosBits) - 1;
            this.m_NumPrevBits = numPrevBits;
            int numStates = 1 << this.m_NumPrevBits + this.m_NumPosBits;
            this.m_Coders = new Decoder2[numStates];

            for(int i = 0; i < numStates; ++i) {
               this.m_Coders[i] = new Decoder2();
            }

         }
      }

      public void init() {
         int numStates = 1 << this.m_NumPrevBits + this.m_NumPosBits;

         for(int i = 0; i < numStates; ++i) {
            this.m_Coders[i].init();
         }

      }

      Decoder2 getDecoder(int pos, byte prevByte) {
         return this.m_Coders[((pos & this.m_PosMask) << this.m_NumPrevBits) + ((prevByte & 255) >>> 8 - this.m_NumPrevBits)];
      }

      public static class Decoder2 {
         final short[] m_Decoders = new short[768];
         int decodeNormalMethodState;
         int decodeWithMatchByteMethodState;
         int symbol;
         int matchBit;
         byte matchByte;

         public void init() {
            this.decodeNormalMethodState = 0;
            this.decodeWithMatchByteMethodState = 0;
            RangeDecoder.initBitModels(this.m_Decoders);
         }

         public boolean decodeNormal(LZMADecoder.LZMAInputState decoderState, RangeDecoder rangeDecoder) throws IOException {
            while(true) {
               switch (this.decodeNormalMethodState) {
                  case 0:
                     this.symbol = 1;
                     this.decodeNormalMethodState = 1;
                  case 1:
                     if (!rangeDecoder.decodeBit(decoderState, this.m_Decoders, this.symbol)) {
                        return false;
                     }

                     this.symbol = this.symbol << 1 | decoderState.lastMethodResult;
                     if (this.symbol >= 256) {
                        this.decodeNormalMethodState = 0;
                        decoderState.lastMethodResult = this.symbol;
                        return true;
                     }
               }
            }
         }

         public boolean decodeWithMatchByte(LZMADecoder.LZMAInputState decoderState, RangeDecoder rangeDecoder, byte matchByteParam) throws IOException {
            while(true) {
               switch (this.decodeWithMatchByteMethodState) {
                  case 0:
                     this.symbol = 1;
                     this.matchByte = matchByteParam;
                  case 1:
                     this.matchBit = this.matchByte >> 7 & 1;
                     this.matchByte = (byte)(this.matchByte << 1);
                     this.decodeWithMatchByteMethodState = 2;
                  case 2:
                     if (!rangeDecoder.decodeBit(decoderState, this.m_Decoders, (1 + this.matchBit << 8) + this.symbol)) {
                        return false;
                     }

                     int bit = decoderState.lastMethodResult;
                     this.symbol = this.symbol << 1 | bit;
                     if (this.matchBit == bit) {
                        if (this.symbol >= 256) {
                           this.decodeWithMatchByteMethodState = 4;
                           break;
                        }

                        this.decodeWithMatchByteMethodState = 1;
                        break;
                     } else {
                        this.decodeWithMatchByteMethodState = 3;
                     }
                  case 3:
                     if (this.symbol >= 256) {
                        this.decodeWithMatchByteMethodState = 4;
                        break;
                     }

                     if (!rangeDecoder.decodeBit(decoderState, this.m_Decoders, this.symbol)) {
                        return false;
                     }

                     this.symbol = this.symbol << 1 | decoderState.lastMethodResult;
                     break;
                  case 4:
                     this.decodeWithMatchByteMethodState = 0;
                     decoderState.lastMethodResult = this.symbol;
                     return true;
               }
            }
         }
      }
   }

   static class LenDecoder {
      final short[] m_Choice = new short[2];
      final BitTreeDecoder[] m_LowCoder = new BitTreeDecoder[16];
      final BitTreeDecoder[] m_MidCoder = new BitTreeDecoder[16];
      final BitTreeDecoder m_HighCoder = new BitTreeDecoder(8);
      int m_NumPosStates = 0;
      private int decodeMethodState;

      public void create(int numPosStates) {
         while(this.m_NumPosStates < numPosStates) {
            this.m_LowCoder[this.m_NumPosStates] = new BitTreeDecoder(3);
            this.m_MidCoder[this.m_NumPosStates] = new BitTreeDecoder(3);
            ++this.m_NumPosStates;
         }

      }

      public void init() {
         this.decodeMethodState = 0;
         RangeDecoder.initBitModels(this.m_Choice);

         for(int posState = 0; posState < this.m_NumPosStates; ++posState) {
            this.m_LowCoder[posState].init();
            this.m_MidCoder[posState].init();
         }

         this.m_HighCoder.init();
      }

      public boolean decode(LZMADecoder.LZMAInputState decoderState, RangeDecoder rangeDecoder, int posState) throws IOException {
         while(true) {
            switch (this.decodeMethodState) {
               case 0:
                  if (!rangeDecoder.decodeBit(decoderState, this.m_Choice, 0)) {
                     return false;
                  }

                  this.decodeMethodState = decoderState.lastMethodResult == 0 ? 1 : 2;
                  break;
               case 1:
                  if (!this.m_LowCoder[posState].decode(decoderState, rangeDecoder)) {
                     return false;
                  }

                  this.decodeMethodState = 5;
                  break;
               case 2:
                  if (!rangeDecoder.decodeBit(decoderState, this.m_Choice, 1)) {
                     return false;
                  }

                  this.decodeMethodState = decoderState.lastMethodResult == 0 ? 3 : 4;
                  break;
               case 3:
                  if (!this.m_MidCoder[posState].decode(decoderState, rangeDecoder)) {
                     return false;
                  }

                  decoderState.lastMethodResult += 8;
                  this.decodeMethodState = 5;
                  break;
               case 4:
                  if (!this.m_HighCoder.decode(decoderState, rangeDecoder)) {
                     return false;
                  }

                  decoderState.lastMethodResult += 16;
                  this.decodeMethodState = 5;
                  break;
               case 5:
                  this.decodeMethodState = 0;
                  return true;
            }
         }
      }
   }

   public static enum State {
      ERR,
      NEED_MORE_DATA,
      DONE,
      CONTINUE;
   }
}
