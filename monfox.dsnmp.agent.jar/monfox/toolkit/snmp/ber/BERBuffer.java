package monfox.toolkit.snmp.ber;

import java.io.Serializable;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpFramework;

public final class BERBuffer implements Serializable {
   public static final int ENCODE = 0;
   public static final int DECODE = 1;
   private static final int a = 256;
   private static final int b = 128;
   private byte[] c;
   private int d;
   private int e;
   private int f;
   private int g;
   private static char[] h = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
   private static final String i = "$Id: BERBuffer.java,v 1.8 2002/01/15 01:54:17 sking Exp $";
   public static int j;

   public BERBuffer(BERBuffer var1) {
      this.d = 0;
      this.e = 0;
      this.f = 0;
      this.g = -1;
      this.c = var1.c;
      this.d = var1.d;
      this.f = var1.f;
      this.g = var1.g;
   }

   public BERBuffer(BERBuffer var1, int var2) {
      this.d = 0;
      this.e = 0;
      this.f = 0;
      this.g = -1;
      this.c = var1.c;
      this.d = var1.d;
      this.f = var1.f;
      this.g = var2;
   }

   public BERBuffer(byte[] var1) {
      this(var1, false);
   }

   public BERBuffer(byte[] var1, boolean var2) {
      this(var1, 0, var1.length, var2);
   }

   public BERBuffer(byte[] var1, int var2, int var3) {
      this(var1, var2, var3, false);
   }

   public BERBuffer(byte[] var1, int var2, int var3, boolean var4) {
      this.d = 0;
      this.e = 0;
      this.f = 0;
      this.g = -1;
      this.f = 1;
      this.c = var1;
      this.d = 0;
      this.e = var2;
      this.g = var2 + var3;
      if (var4) {
         try {
            int var5 = BERCoder.getTag(this);
            int var6 = BERCoder.getLength(this);
            int var7 = this.d;
            this.d = 0;
            this.g = var2 + var7 + var6;
         } catch (Exception var8) {
            SnmpFramework.handleException(this, var8);
         }
      }

   }

   public BERBuffer() {
      this(256);
   }

   public BERBuffer(int var1) {
      this.d = 0;
      this.e = 0;
      this.f = 0;
      this.g = -1;
      this.c = new byte[var1];
      this.d = 0;
      this.e = 0;
      this.f = 0;
      this.g = this.c.length;
   }

   public int getMode() {
      return this.f;
   }

   public byte atIndex() {
      return this.c[this.e + this.d];
   }

   public byte getByteAtIndex() {
      return this.c[this.e + this.d];
   }

   public int getEndPoint() {
      return this.g;
   }

   public byte nextByte() {
      return this.c[this.e + this.d++];
   }

   public int getOffset() {
      return this.f == 0 ? this.c.length - this.d : this.e;
   }

   public int getLength() {
      return this.f == 0 ? this.d : this.g - this.e;
   }

   public boolean hasMoreData() {
      return this.e + this.d < this.g;
   }

   public int getIndex() {
      return this.d;
   }

   public void setIndex(int var1) {
      this.d = var1;
   }

   public byte getByteAt(int var1) {
      return this.c[this.e + var1];
   }

   public void setByteAt(int var1, byte var2) {
      this.c[this.e + var1] = var2;
   }

   public void nextBytes(byte[] var1, int var2) {
      System.arraycopy(this.c, this.e + this.d, var1, 0, var2);
      this.d += var2;
   }

   public byte getRelativeByteAt(int var1) {
      return this.c[this.e + this.d + var1];
   }

   public byte at(int var1) {
      return this.c[this.e + var1];
   }

   public int getRemainingSize() {
      return this.g - this.d;
   }

   public void setRelativeIndex(int var1) {
      this.setIndex(this.d + var1);
   }

   public byte[] getRemainingBytes() {
      int var1 = this.getRemainingSize();
      byte[] var2 = new byte[var1];
      System.arraycopy(this.c, this.d + this.e, var2, 0, var1);
      return var2;
   }

   public byte[] getData() {
      int var4 = j;
      if (this.e == 0) {
         return this.c;
      } else {
         int var1 = this.g - this.e;
         byte[] var2 = new byte[var1];
         int var3 = 0;

         byte[] var10000;
         while(true) {
            if (var3 < var1) {
               var10000 = var2;
               if (var4 != 0) {
                  break;
               }

               var2[var3] = this.c[this.e + var3];
               ++var3;
               if (var4 == 0) {
                  continue;
               }
            }

            var10000 = var2;
            break;
         }

         return var10000;
      }
   }

   public int add(byte[] var1) {
      return this.add(var1, 0, var1.length);
   }

   public synchronized int add(byte[] var1, int var2, int var3) {
      int var5 = j;

      while(true) {
         if (var3 + this.d > this.c.length) {
            byte[] var4 = new byte[this.c.length + 128];
            System.arraycopy(this.c, 0, var4, 128, this.c.length);
            this.c = var4;
            this.g = this.c.length;
            if (var5 != 0) {
               break;
            }

            if (var5 == 0) {
               continue;
            }
         }

         this.d += var3;
         break;
      }

      int var6 = this.c.length - this.d;
      System.arraycopy(var1, var2, this.c, var6, var3);
      return var3;
   }

   public int add(byte var1) {
      try {
         this.c[this.c.length - 1 - this.d] = var1;
      } catch (ArrayIndexOutOfBoundsException var3) {
         this.a(this.d);
         this.c[this.c.length - 1 - this.d] = var1;
      }

      ++this.d;
      return 1;
   }

   void a(byte var1) {
      try {
         this.c[this.c.length - 1 - this.d] = var1;
      } catch (ArrayIndexOutOfBoundsException var3) {
         this.a(this.d);
         this.c[this.c.length - 1 - this.d] = var1;
      }

      ++this.d;
   }

   public void set(int var1, byte var2) {
      try {
         this.c[this.c.length - 1 - var1] = var2;
      } catch (ArrayIndexOutOfBoundsException var4) {
         this.a(var1);
         this.c[this.c.length - 1 - var1] = var2;
      }

   }

   private void a(int var1) {
      while(true) {
         if (var1 >= this.c.length) {
            byte[] var2 = new byte[this.c.length + 128];
            System.arraycopy(this.c, 0, var2, 128, this.c.length);
            this.c = var2;
            this.g = this.c.length;
            if (j == 0) {
               continue;
            }
         }

         return;
      }
   }

   public int getEncLength() {
      return this.d;
   }

   public byte[] getRawData() {
      return this.c;
   }

   public synchronized byte[] getBytes() {
      int var4 = j;
      int var1;
      byte[] var2;
      int var3;
      if (this.f == 0) {
         var1 = this.d;
         var2 = new byte[var1];
         var3 = this.getOffset();
         System.arraycopy(this.c, var3, var2, 0, var1);
         return var2;
      } else if (this.e == 0 && this.g == this.c.length) {
         return this.c;
      } else {
         var1 = this.g - this.e;
         var2 = new byte[var1];
         var3 = 0;

         byte[] var10000;
         while(true) {
            if (var3 < var1) {
               var10000 = var2;
               if (var4 != 0) {
                  break;
               }

               var2[var3] = this.c[this.e + var3];
               ++var3;
               if (var4 == 0) {
                  continue;
               }
            }

            var10000 = var2;
            break;
         }

         return var10000;
      }
   }

   public synchronized int getBytes(byte[] var1) {
      int var2;
      if (this.f == 0) {
         var2 = this.d;
         int var3 = this.getOffset();
         System.arraycopy(this.c, var3, var1, 0, var2);
         return var2;
      } else {
         var2 = this.g - this.e;
         System.arraycopy(this.c, this.e, var1, 0, var2);
         return var2;
      }
   }

   public String toString() {
      int var5 = j;
      StringBuffer var1 = new StringBuffer();
      var1.append(a("_\u0000\u0007%u")).append(this.d);
      var1.append(a("(\u0006\u0005;u")).append(this.e);
      var1.append(a("(\f\r9u")).append(this.g);
      var1.append(a("(\u0004\f9-9")).append(this.f);
      var1.append(a("YSC"));
      int var2 = this.getOffset();
      int var3 = this.g == -1 ? this.d : this.g;
      int var4 = var2;

      StringBuffer var10000;
      while(true) {
         if (var4 < var3) {
            var1.append(h[this.c[var4] >> 4 & 15]);
            var1.append(h[this.c[var4] & 15]);
            var10000 = var1.append(" ");
            if (var5 != 0) {
               break;
            }

            ++var4;
            if (var5 == 0) {
               continue;
            }
         }

         var10000 = var1;
         break;
      }

      String var6 = var10000.toString();
      if (SnmpException.b) {
         ++var5;
         j = var5;
      }

      return var6;
   }

   public void setFrom(BERBuffer var1) {
      this.c = var1.c;
      this.d = var1.d;
      this.e = var1.e;
      this.f = var1.f;
      this.g = var1.g;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 4;
               break;
            case 1:
               var10003 = 105;
               break;
            case 2:
               var10003 = 99;
               break;
            case 3:
               var10003 = 93;
               break;
            default:
               var10003 = 72;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
