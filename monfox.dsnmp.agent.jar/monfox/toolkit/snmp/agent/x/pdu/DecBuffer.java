package monfox.toolkit.snmp.agent.x.pdu;

import java.util.List;
import java.util.Vector;
import monfox.toolkit.snmp.SnmpCounter;
import monfox.toolkit.snmp.SnmpCounter64;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpGauge;
import monfox.toolkit.snmp.SnmpInt;
import monfox.toolkit.snmp.SnmpIpAddress;
import monfox.toolkit.snmp.SnmpNull;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpOpaque;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpTimeTicks;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpVarBind;

public class DecBuffer {
   public int INSTANCE_REGISTRATION;
   public int NEW_INDEX;
   public int ANY_INDEX;
   public int NON_DEFAULT_CONTEXT;
   public int NETWORK_BYTE_ORDER;
   private byte[] a;
   private int b;
   private int c;
   private int d;
   private int e;
   private boolean f;

   public DecBuffer(byte[] var1) {
      this(var1, var1.length);
   }

   public DecBuffer(byte[] var1, int var2) {
      this.INSTANCE_REGISTRATION = 1;
      this.NEW_INDEX = 2;
      this.ANY_INDEX = 4;
      this.NON_DEFAULT_CONTEXT = 8;
      this.NETWORK_BYTE_ORDER = 16;
      this.b = 0;
      this.c = 0;
      this.d = 0;
      this.e = 0;
      this.f = false;
      this.a = var1;
   }

   public void setFlags(int var1) {
      this.e = var1;
      if ((this.e & this.NETWORK_BYTE_ORDER) != 0) {
         this.f = true;
         if (AgentXPDU.i == 0) {
            return;
         }
      }

      this.f = false;
   }

   public void decodeHeader() throws CoderException {
      this.c = this.nextByte();
      this.d = this.nextByte();
      this.setFlags(this.nextByte());
      this.nextByte();
   }

   public int getVersion() {
      return this.c;
   }

   public int getType() {
      return this.d;
   }

   public int getFlags() {
      return this.e;
   }

   public SnmpVarBind nextVarBind() throws CoderException {
      int var1 = this.nextInt16();
      this.nextByte();
      this.nextByte();
      long[] var2 = this.nextOid();
      SnmpOid var3 = new SnmpOid(var2);
      Object var4 = null;
      switch (var1) {
         case 2:
            var4 = new SnmpInt(this.nextInt32());
            break;
         case 4:
            var4 = new SnmpString(this.nextString());
            break;
         case 6:
            var4 = new SnmpOid(this.nextOid());
            break;
         case 64:
            var4 = new SnmpIpAddress(this.nextString());
            break;
         case 65:
            var4 = new SnmpCounter(this.nextInt32());
            break;
         case 66:
            var4 = new SnmpGauge(this.nextInt32());
            break;
         case 67:
            var4 = new SnmpTimeTicks(this.nextInt32());
            break;
         case 68:
            var4 = new SnmpOpaque(this.nextString());
            break;
         case 70:
            var4 = new SnmpCounter64(this.nextInt64());
            break;
         default:
            var4 = new SnmpNull(var1);
      }

      SnmpVarBind var5 = new SnmpVarBind(var3, (SnmpValue)var4);
      return var5;
   }

   public List nextSearchRangeList() throws CoderException {
      int var3 = AgentXPDU.i;
      Vector var1 = new Vector();

      Vector var10000;
      while(true) {
         if (this.hasMoreData()) {
            SearchRange var2 = this.nextSearchRange();
            var10000 = var1;
            if (var3 != 0) {
               break;
            }

            var1.add(var2);
            if (var3 == 0) {
               continue;
            }
         }

         var10000 = var1;
         break;
      }

      return var10000;
   }

   public SearchRange nextSearchRange() throws CoderException {
      a var1 = this.a();
      a var2 = this.a();
      SnmpOid var3 = var1.getValue() != null ? new SnmpOid(var1.getValue()) : null;
      SnmpOid var4 = var2.getValue() != null ? new SnmpOid(var2.getValue()) : null;
      SearchRange var5 = new SearchRange(var3, var1.isInclude(), var4);
      return var5;
   }

   public long[] nextOid() throws CoderException {
      a var1 = this.a();
      return var1.getValue();
   }

   a a() throws CoderException {
      int var1 = this.nextByte();
      int var2 = this.nextByte();
      int var3 = this.nextByte();
      int var4 = this.nextByte();
      if (var1 == 0 && var2 == 0 && var3 == 0) {
         return new a((long[])null, false);
      } else {
         Object var5 = null;
         boolean var6 = false;
         long[] var8;
         byte var9;
         if (var2 > 0) {
            var8 = new long[var1 + 5];
            var8[0] = 1L;
            var8[1] = 3L;
            var8[2] = 6L;
            var8[3] = 1L;
            var8[4] = (long)var2;
            var9 = 5;
         } else {
            var8 = new long[var1];
            var9 = 0;
         }

         int var7 = 0;

         while(var7 < var1) {
            var8[var9 + var7] = this.nextInt32();
            ++var7;
            if (AgentXPDU.i != 0) {
               break;
            }
         }

         return new a(var8, var3 != 0);
      }
   }

   public byte[] nextString() throws CoderException {
      int var1 = (int)this.nextInt32();
      byte[] var2 = new byte[var1];
      System.arraycopy(this.a, this.b, var2, 0, var1);
      this.b += var1;
      int var3 = 4 - var2.length % 4;
      if (var3 < 4) {
         int var4 = 0;

         while(var4 < var3) {
            this.nextByte();
            ++var4;
            if (AgentXPDU.i != 0) {
               break;
            }
         }
      }

      return var2;
   }

   public int nextInt16() throws CoderException {
      int var1 = 0;
      if (this.f) {
         var1 |= this.nextByte() << 8 & '\uff00';
         var1 |= this.nextByte() & 255;
         if (AgentXPDU.i == 0) {
            return var1;
         }
      }

      var1 |= this.nextByte() & 255;
      var1 |= this.nextByte() << 8 & '\uff00';
      return var1;
   }

   public long nextInt32() throws CoderException {
      long var1 = 0L;
      if (this.f) {
         var1 |= (long)(this.nextByte() << 24 & -16777216);
         var1 |= (long)(this.nextByte() << 16 & 16711680);
         var1 |= (long)(this.nextByte() << 8 & '\uff00');
         var1 |= (long)(this.nextByte() & 255);
         if (AgentXPDU.i == 0) {
            return var1;
         }
      }

      var1 |= (long)(this.nextByte() & 255);
      var1 |= (long)(this.nextByte() << 8 & '\uff00');
      var1 |= (long)(this.nextByte() << 16 & 16711680);
      var1 |= (long)(this.nextByte() << 24 & -16777216);
      return var1;
   }

   public long nextInt64() throws CoderException {
      long var1;
      int var3;
      label15: {
         var3 = AgentXPDU.i;
         var1 = 0L;
         if (this.f) {
            var1 |= (long)(this.nextByte() << 56) & -72057594037927936L;
            var1 |= (long)(this.nextByte() << 48) & 71776119061217280L;
            var1 |= (long)(this.nextByte() << 40) & 280375465082880L;
            var1 |= (long)(this.nextByte() << 32) & 1095216660480L;
            var1 |= (long)(this.nextByte() << 24) & 4278190080L;
            var1 |= (long)(this.nextByte() << 16) & 16711680L;
            var1 |= (long)(this.nextByte() << 8) & 65280L;
            var1 |= (long)this.nextByte() & 255L;
            if (var3 == 0) {
               break label15;
            }
         }

         var1 |= (long)this.nextByte() & 255L;
         var1 |= (long)(this.nextByte() << 8) & 65280L;
         var1 |= (long)(this.nextByte() << 16) & 16711680L;
         var1 |= (long)(this.nextByte() << 24) & 4278190080L;
         var1 |= (long)(this.nextByte() << 32) & 1095216660480L;
         var1 |= (long)(this.nextByte() << 40) & 280375465082880L;
         var1 |= (long)(this.nextByte() << 48) & 71776119061217280L;
         var1 |= (long)(this.nextByte() << 56) & -72057594037927936L;
      }

      if (SnmpException.b) {
         ++var3;
         AgentXPDU.i = var3;
      }

      return var1;
   }

   public void nextPadding(int var1) throws CoderException {
      int var2 = 0;

      while(var2 < var1) {
         this.nextByte();
         ++var2;
         if (AgentXPDU.i != 0) {
            break;
         }
      }

   }

   public int nextByte() throws CoderException {
      if (this.b < this.a.length) {
         int var1 = this.a[this.b++] & 255;
         return var1;
      } else {
         throw new CoderException(a("0/jf5c%pr'&5"));
      }
   }

   public boolean hasMoreData() {
      return this.b + 1 < this.a.length;
   }

   public void reset() {
      this.b = 0;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 67;
               break;
            case 1:
               var10003 = 71;
               break;
            case 2:
               var10003 = 5;
               break;
            case 3:
               var10003 = 20;
               break;
            default:
               var10003 = 65;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
