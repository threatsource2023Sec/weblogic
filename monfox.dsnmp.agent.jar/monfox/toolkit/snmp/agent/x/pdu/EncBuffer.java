package monfox.toolkit.snmp.agent.x.pdu;

import java.util.List;
import java.util.ListIterator;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.util.ByteFormatter;

public class EncBuffer {
   private static final int a = 256;
   private static final int b = 256;
   public int INSTANCE_REGISTRATION;
   public int NEW_INDEX;
   public int ANY_INDEX;
   public int NON_DEFAULT_CONTEXT;
   public int NETWORK_BYTE_ORDER;
   private byte[] c;
   private int d;
   private boolean e;

   public EncBuffer() {
      this(256);
   }

   public EncBuffer(int var1) {
      this.INSTANCE_REGISTRATION = 1;
      this.NEW_INDEX = 2;
      this.ANY_INDEX = 4;
      this.NON_DEFAULT_CONTEXT = 8;
      this.NETWORK_BYTE_ORDER = 16;
      this.d = 0;
      this.e = false;
      this.c = new byte[var1];
   }

   public void setFlags(int var1) {
      if ((var1 & this.NETWORK_BYTE_ORDER) != 0) {
         this.e = true;
         if (AgentXPDU.i == 0) {
            return;
         }
      }

      this.e = false;
   }

   public void addHeader(int var1, int var2, int var3) {
      this.addByte(var1);
      this.addByte(var2);
      this.addByte(var3);
      this.addByte((int)0);
      this.setFlags(var3);
   }

   public void addOid(SnmpOid var1) {
      this.addOid(var1, true);
   }

   public void addOid(SnmpOid var1, boolean var2) {
      if (var1 == null) {
         this.addOid((long[])null, 0, false);
         if (AgentXPDU.i == 0) {
            return;
         }
      }

      this.addOid(var1.toLongArray(false), var1.getLength(), var2);
   }

   public void addOid(long[] var1, int var2) {
      this.addOid(var1, var2, false);
   }

   public void addOid(long[] var1, int var2, boolean var3) {
      int var8 = AgentXPDU.i;
      if (var1 == null) {
         this.addByte((int)0);
         this.addByte((int)0);
         this.addByte((int)0);
         this.addByte((int)0);
         if (var8 == 0) {
            return;
         }
      }

      byte var5;
      label34: {
         boolean var4 = var2 >= 5 && var1[3] == 1L && var1[2] == 6L && var1[1] == 3L && var1[0] == 1L;
         var5 = 0;
         if (var4) {
            var5 = 5;
            this.addByte(var2 - 5);
            this.addByte((byte)((int)(var1[4] & 255L)));
            if (var8 == 0) {
               break label34;
            }
         }

         this.addByte(var2);
         this.addByte((int)0);
      }

      this.addByte(var3 ? 1 : 0);
      this.addByte((int)0);
      int var7 = var5;

      while(var7 < var2) {
         this.addInt32(var1[var7]);
         ++var7;
         if (var8 != 0) {
            break;
         }
      }

   }

   public void addSearchRangeList(List var1) {
      if (var1 != null) {
         ListIterator var2 = var1.listIterator();

         while(var2.hasNext()) {
            SearchRange var3 = (SearchRange)var2.next();
            this.addSearchRange(var3);
            if (AgentXPDU.i != 0) {
               break;
            }
         }
      }

   }

   public void addSearchRange(SearchRange var1) {
      this.addOid(var1.getStart(), var1.isInclude());
      this.addOid(var1.getEnd(), false);
   }

   public void addVarBind(SnmpVarBind var1) {
      int var6 = AgentXPDU.i;
      SnmpValue var2 = var1.getValue();
      int var3 = 5;
      if (var2 != null) {
         var3 = var2.getTag();
      }

      this.addInt16(var3);
      this.addByte((int)0);
      this.addByte((int)0);
      SnmpOid var4 = var1.getOid();
      this.addOid(var4.toLongArray(false), var4.getLength());
      if (var2 != null) {
         switch (var3) {
            case 2:
            case 65:
            case 66:
            case 67:
               this.addInt32(var2.longValue());
               if (var6 == 0) {
                  break;
               }
            case 70:
               this.addInt64(var2.longValue());
               if (var6 == 0) {
                  break;
               }
            case 4:
            case 64:
            case 68:
               this.addString(var2.getByteArray());
               if (var6 == 0) {
                  break;
               }
            case 6:
               SnmpOid var5 = (SnmpOid)var2;
               this.addOid(var5.toLongArray(false), var5.getLength());
         }
      }

   }

   public void addString(String var1) {
      if (var1 != null) {
         this.addString(var1.getBytes());
         if (AgentXPDU.i == 0) {
            return;
         }
      }

      this.addString((String)null);
   }

   public void addString(byte[] var1) {
      int var4 = AgentXPDU.i;
      if (var1 == null) {
         this.addInt32(0L);
      } else {
         this.addInt32((long)var1.length);
         int var2 = 0;

         while(true) {
            if (var2 < var1.length) {
               this.addByte(var1[var2]);
               ++var2;
               if (var4 != 0) {
                  break;
               }

               if (var4 == 0) {
                  continue;
               }
            }

            var2 = 4 - var1.length % 4;
            break;
         }

         if (var2 < 4) {
            int var3 = 0;

            while(var3 < var2) {
               this.addByte((int)0);
               ++var3;
               if (var4 != 0) {
                  break;
               }
            }
         }

      }
   }

   public void addInt32(long var1) {
      if (this.e) {
         this.addByte((byte)((int)(var1 >> 24 & 255L)));
         this.addByte((byte)((int)(var1 >> 16 & 255L)));
         this.addByte((byte)((int)(var1 >> 8 & 255L)));
         this.addByte((byte)((int)(var1 & 255L)));
         if (AgentXPDU.i == 0) {
            return;
         }
      }

      this.addByte((byte)((int)(var1 & 255L)));
      this.addByte((byte)((int)(var1 >> 8 & 255L)));
      this.addByte((byte)((int)(var1 >> 16 & 255L)));
      this.addByte((byte)((int)(var1 >> 24 & 255L)));
   }

   public void addInt16(int var1) {
      if (this.e) {
         this.addByte((byte)(var1 >> 8 & 255));
         this.addByte((byte)(var1 & 255));
         if (AgentXPDU.i == 0) {
            return;
         }
      }

      this.addByte((byte)(var1 & 255));
      this.addByte((byte)(var1 >> 8 & 255));
   }

   public void addInt64(long var1) {
      if (this.e) {
         this.addByte((byte)((int)(var1 >> 56 & 255L)));
         this.addByte((byte)((int)(var1 >> 48 & 255L)));
         this.addByte((byte)((int)(var1 >> 40 & 255L)));
         this.addByte((byte)((int)(var1 >> 32 & 255L)));
         this.addByte((byte)((int)(var1 >> 24 & 255L)));
         this.addByte((byte)((int)(var1 >> 16 & 255L)));
         this.addByte((byte)((int)(var1 >> 8 & 255L)));
         this.addByte((byte)((int)(var1 & 255L)));
         if (AgentXPDU.i == 0) {
            return;
         }
      }

      this.addByte((byte)((int)(var1 & 255L)));
      this.addByte((byte)((int)(var1 >> 8 & 255L)));
      this.addByte((byte)((int)(var1 >> 16 & 255L)));
      this.addByte((byte)((int)(var1 >> 24 & 255L)));
      this.addByte((byte)((int)(var1 >> 32 & 255L)));
      this.addByte((byte)((int)(var1 >> 40 & 255L)));
      this.addByte((byte)((int)(var1 >> 48 & 255L)));
      this.addByte((byte)((int)(var1 >> 56 & 255L)));
   }

   public void addPadding(int var1) {
      int var2 = 0;

      while(var2 < var1) {
         this.addByte((int)0);
         ++var2;
         if (AgentXPDU.i != 0) {
            break;
         }
      }

   }

   public void addByte(int var1) {
      this.addByte((byte)var1);
   }

   public void addByte(byte var1) {
      int var3 = AgentXPDU.i;

      while(true) {
         if (this.d >= this.c.length) {
            byte[] var2 = new byte[this.c.length + 256];
            System.arraycopy(this.c, 0, var2, 0, this.c.length);
            this.c = var2;
            if (var3 != 0) {
               break;
            }

            if (var3 == 0) {
               continue;
            }
         }

         this.c[this.d++] = (byte)(255 & var1);
         break;
      }

   }

   public void addBuffer(EncBuffer var1) {
      int var2 = this.d + var1.d;
      if (var2 > var1.d) {
      }

      byte[] var3 = new byte[var2];
      System.arraycopy(this.c, 0, var3, 0, this.d);
      this.c = var3;
      System.arraycopy(var1.c, 0, this.c, this.d, var1.d);
      this.d += var1.d;
   }

   public int getLength() {
      return this.d;
   }

   public byte[] getBytes() {
      if (this.c.length == this.d) {
         return this.c;
      } else {
         byte[] var1 = new byte[this.d];
         System.arraycopy(this.c, 0, var1, 0, this.d);
         return var1;
      }
   }

   public String toString() {
      return ByteFormatter.toString(this.c, 0, this.d);
   }
}
