package com.bea.security.utils.gss;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class GSSTokenUtils {
   public static final int ACO = 96;
   public static final byte[] KERBEROS_V5_OID = new byte[]{6, 9, 42, -122, 72, -122, -9, 18, 1, 2, 2};
   private static final int ONEOCTET = 256;
   private static final int TWOOCTETS = 65536;
   private static final int THREEOCTETS = 16777216;
   private static final byte ONEOCTETSIZE = -127;
   private static final byte TWOOCTETSIZE = -126;
   private static final byte THREEOCTETSIZE = -125;
   private static final byte FOUROCTETSIZE = -124;

   private GSSTokenUtils() {
   }

   public static void encodeLength(ByteArrayOutputStream theOutput, int theLength) {
      if (theLength < 128 && theLength >= 0) {
         theOutput.write((byte)theLength);
      } else {
         if (theLength >= 16777216) {
            theOutput.write(-124);
            theOutput.write((byte)(theLength >> 24));
            theOutput.write((byte)(theLength >> 16));
            theOutput.write((byte)(theLength >> 8));
         } else if (theLength >= 65536) {
            theOutput.write(-125);
            theOutput.write((byte)(theLength >> 16));
            theOutput.write((byte)(theLength >> 8));
         } else if (theLength >= 256) {
            theOutput.write(-126);
            theOutput.write((byte)(theLength >> 8));
         } else {
            theOutput.write(-127);
         }

         theOutput.write((byte)theLength);
      }
   }

   public static int decodeLength(ByteArrayInputStream is) {
      int len = 0;
      int b = is.read();
      if (b == -1) {
         return -1;
      } else {
         if (b < 128 && b >= 0) {
            len = b;
         } else {
            int numOctets = b & 127;
            if (is.available() < numOctets - 1) {
               return -1;
            }

            switch (numOctets) {
               case 1:
                  len = is.read() & 255;
                  break;
               case 2:
                  len = is.read() << 8 & '\uff00' | is.read() & 255;
                  break;
               case 3:
                  len = is.read() << 16 & 16711680 | is.read() << 8 & '\uff00' | is.read() & 255;
                  break;
               case 4:
                  len = is.read() << 24 & -16777216 | is.read() << 16 & 16711680 | is.read() << 8 & '\uff00' | is.read() & 255;
            }
         }

         return len;
      }
   }

   public static byte[] encodeData(int typeTag, byte[] data) throws IOException {
      ByteArrayOutputStream datastream = new ByteArrayOutputStream();
      datastream.write(typeTag);
      encodeLength(datastream, data.length);
      datastream.write(data);
      return datastream.toByteArray();
   }
}
