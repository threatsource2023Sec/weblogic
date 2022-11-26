package com.oracle.tyrus.fallback;

import com.oracle.tyrus.fallback.spi.ReadHandler;

public class Base16FilterReadHandler implements ReadHandler {
   private final ReadHandler rh;
   private boolean remaining;
   private byte ch;

   Base16FilterReadHandler(ReadHandler rh) {
      this.rh = rh;
   }

   public void handle(byte[] data, int offset, int length) {
      if (length != 0) {
         int actualLength = this.remaining ? length + 1 : length;
         boolean actualRemaining = actualLength % 2 != 0;
         actualLength /= 2;
         byte[] actualData = new byte[actualLength];

         for(int i = 0; i < actualLength; ++i) {
            byte byte1 = this.remaining ? this.ch : data[offset++];
            this.remaining = false;
            byte byte2 = data[offset++];
            if (byte1 >= 48 && byte1 <= 57) {
               byte1 = (byte)(byte1 - 48);
            } else if (byte1 >= 65 && byte1 <= 70) {
               byte1 = (byte)(byte1 - 55);
            } else {
               if (byte1 < 97 || byte1 > 102) {
                  throw new RuntimeException("Invalid byte=" + byte1 + " in base16");
               }

               byte1 = (byte)(byte1 - 87);
            }

            if (byte2 >= 48 && byte2 <= 57) {
               byte2 = (byte)(byte2 - 48);
            } else if (byte2 >= 65 && byte2 <= 70) {
               byte2 = (byte)(byte2 - 55);
            } else {
               if (byte2 < 97 || byte2 > 102) {
                  throw new RuntimeException("Invalid byte=" + byte2 + " in base16");
               }

               byte2 = (byte)(byte2 - 87);
            }

            actualData[i] = (byte)((byte1 << 4) + byte2);
         }

         if (actualRemaining) {
            this.remaining = true;
            this.ch = data[offset];
         }

         if (actualLength > 0) {
            this.rh.handle(actualData, 0, actualLength);
         }

      }
   }
}
