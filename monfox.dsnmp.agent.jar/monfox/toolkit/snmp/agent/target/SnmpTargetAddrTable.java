package monfox.toolkit.snmp.agent.target;

import java.net.InetAddress;
import java.net.UnknownHostException;
import monfox.log.Logger;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpInt;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.agent.tc.StorageType;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.mgr.SnmpPeer;

public class SnmpTargetAddrTable extends SnmpMibTable {
   private SnmpTarget a;
   private Logger l = Logger.getInstance(a("C\u0010\tpT"), a("F\u0004\u0002sP*\u0017\u0006oCB\u0017"), a("T-*MPf1 XpF'#OPf!+X"));
   private static final SnmpOid b = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 1L, 1L});
   private static final SnmpOid c = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 1L, 6L});
   public static final int DEFAULT_TIMEOUT = 1500;
   public static final int DEFAULT_RETRY_COUNT = 3;
   static final int LEAF = 1;
   static final int RANGE = 2;
   static final int RANGE_END = 3;
   static final int SUB_TREE = 4;
   static final int d = 5;
   static final int e = 6;
   static final int f = 7;
   static final int g = 8;
   static final int h = 9;

   SnmpTargetAddrTable(SnmpTarget var1, SnmpMetadata var2) throws SnmpMibException, SnmpValueException {
      super(new SnmpOid(var2, a("t-*MPf1 XpF'#OPf!+X")));
      this.a = var1;
      this.isSettableWhenActive(true);
      this.isSettableWhenActive(2, false);
      this.isSettableWhenActive(3, false);
      this.setInitialValue(4, new SnmpInt(1500));
      this.setInitialValue(5, new SnmpInt(3));
      this.setInitialValue(6, new SnmpString());
      this.setFactory(8, StorageType.getFactory(3));
   }

   public SnmpMibTableRow add(String var1, String var2, int var3, String var4, String var5) throws SnmpMibException, SnmpValueException, UnknownHostException {
      return this.add(var1, var2, var3, 1500, 3, var4, var5);
   }

   public SnmpMibTableRow add(String var1, String var2, int var3, int var4, int var5, String var6, String var7) throws SnmpMibException, SnmpValueException, UnknownHostException {
      byte[] var8 = this.a(var2, var3);
      return this.add(var1, var8, var4, var5, var6, var7);
   }

   public SnmpMibTableRow add(String var1, String var2, int var3, int var4, int var5, String var6, String var7, SnmpOid var8) throws SnmpMibException, SnmpValueException, UnknownHostException {
      byte[] var9 = this.a(var2, var3);
      return this.add(var1, var9, var4, var5, var6, var7, var8);
   }

   public SnmpMibTableRow add(String var1, byte[] var2, int var3, int var4, String var5, String var6) throws SnmpMibException, SnmpValueException {
      int var9 = SnmpTarget.f;
      if (var1 == null) {
         throw new NullPointerException();
      } else {
         SnmpMibTableRow var7 = this.initRow(new SnmpValue[]{new SnmpString(var1)});
         SnmpOid var8 = Snmp.snmpUDPDomain;
         if (var2.length == 18) {
            var8 = Snmp.transportDomainUdpIpv6;
         }

         var7.getLeaf(2).setValue((SnmpValue)var8);
         var7.getLeaf(3).setValue((SnmpValue)(new SnmpString(var2)));
         var7.getLeaf(4).setValue((SnmpValue)(new SnmpInt(var3)));
         var7.getLeaf(5).setValue((SnmpValue)(new SnmpInt(var4)));
         var7.getLeaf(6).setValue((SnmpValue)(new SnmpString(var5)));
         var7.getLeaf(7).setValue((SnmpValue)(new SnmpString(var6)));
         this.addRow(var7);
         var7.getRowStatusLeaf().setActive();
         if (var9 != 0) {
            SnmpException.b = !SnmpException.b;
         }

         return var7;
      }
   }

   public SnmpMibTableRow add(String var1, byte[] var2, int var3, int var4, String var5, String var6, SnmpOid var7) throws SnmpMibException, SnmpValueException {
      int var9 = SnmpTarget.f;
      if (var1 == null) {
         throw new NullPointerException();
      } else {
         SnmpMibTableRow var8 = this.initRow(new SnmpValue[]{new SnmpString(var1)});
         var8.getLeaf(2).setValue((SnmpValue)var7);
         var8.getLeaf(3).setValue((SnmpValue)(new SnmpString(var2)));
         var8.getLeaf(4).setValue((SnmpValue)(new SnmpInt(var3)));
         var8.getLeaf(5).setValue((SnmpValue)(new SnmpInt(var4)));
         var8.getLeaf(6).setValue((SnmpValue)(new SnmpString(var5)));
         var8.getLeaf(7).setValue((SnmpValue)(new SnmpString(var6)));
         this.addRow(var8);
         var8.getRowStatusLeaf().setActive();
         if (this.l.isDebugEnabled()) {
            this.l.debug(var8.toString());
         }

         if (SnmpException.b) {
            ++var9;
            f = var9;
         }

         return var8;
      }
   }

   private byte[] a(String var1, int var2) throws UnknownHostException {
      int var8 = SnmpTarget.f;
      InetAddress var3 = InetAddress.getByName(var1);
      byte[] var4 = var3.getAddress();
      int var5 = var4.length;
      byte[] var6 = new byte[var5 + 2];
      int var7 = 0;

      while(true) {
         if (var7 < var5) {
            var6[var7] = var4[var7];
            ++var7;
            if (var8 != 0) {
               break;
            }

            if (var8 == 0) {
               continue;
            }
         }

         var6[var5] = (byte)(var2 & 255);
         var6[var5 + 1] = (byte)(var2 >> 8 & 255);
         this.l.debug(a("S\u0002#Yvb04\u001dhb- Il=c") + var6.length + a("'~z\u0003$") + var3);
         break;
      }

      return var6;
   }

   protected void columnUpdated(SnmpMibTableRow var1, SnmpMibLeaf var2) {
      this.b(var1).a();
   }

   public void remove(String var1) throws SnmpMibException, SnmpValueException {
      SnmpMibTableRow var2 = this.removeRow(new SnmpValue[]{new SnmpString(var1)});
      if (var2 != null) {
         var2.destroy();
      }

   }

   public SnmpMibTableRow get(String var1) throws SnmpMibException, SnmpValueException {
      return this.getRow(new SnmpValue[]{new SnmpString(var1)});
   }

   public Row getRowByName(String var1) throws SnmpMibException, SnmpValueException {
      return this.b(this.get(var1));
   }

   Row b(SnmpMibTableRow var1) {
      if (var1 == null) {
         return null;
      } else if (var1.getUserObject() != null && var1.getUserObject() instanceof Row) {
         return (Row)var1.getUserObject();
      } else {
         Row var2 = new Row(var1);
         if (var1.getUserObject() == null) {
            var1.setUserObject(var1);
         }

         return var2;
      }
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 7;
               break;
            case 1:
               var10003 = 67;
               break;
            case 2:
               var10003 = 71;
               break;
            case 3:
               var10003 = 61;
               break;
            default:
               var10003 = 4;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public class Row {
      private SnmpMibTableRow a;
      private SnmpPeer b;

      Row(SnmpMibTableRow var2) {
         this.a = var2;
      }

      public String getName() {
         try {
            return this.a.getLeaf(1).getValue().getString();
         } catch (Exception var2) {
            return null;
         }
      }

      public SnmpOid getTDomain() {
         try {
            return (SnmpOid)this.a.getLeaf(2).getValue();
         } catch (Exception var2) {
            return null;
         }
      }

      public byte[] getTAddress() {
         try {
            return ((SnmpString)this.a.getLeaf(3).getValue()).toByteArray();
         } catch (Exception var2) {
            return null;
         }
      }

      public int getTimeout() {
         try {
            return this.a.getLeaf(4).getValue().intValue();
         } catch (Exception var2) {
            return -1;
         }
      }

      public int getRetryCount() {
         try {
            return this.a.getLeaf(5).getValue().intValue();
         } catch (Exception var2) {
            return -1;
         }
      }

      public String getTagList() {
         try {
            return this.a.getLeaf(6).getValue().getString();
         } catch (Exception var2) {
            return null;
         }
      }

      public String getParams() {
         try {
            return this.a.getLeaf(7).getValue().getString();
         } catch (Exception var2) {
            return null;
         }
      }

      public SnmpMibTableRow getTableRow() {
         return this.a;
      }

      public boolean isActive() {
         return this.a.isActive();
      }

      public String toString() {
         return this.a.toString();
      }

      public SnmpPeer getPeer() {
         if (!this.a.isActive()) {
            return null;
         } else if (this.b != null) {
            return this.b;
         } else {
            try {
               this.b = SnmpTargetAddrTable.this.a.a(this.a);
               return this.b;
            } catch (Exception var2) {
               SnmpTargetAddrTable.this.l.debug(a(">1{e\u0012)pvy\u0018<$p+\r85g+\u001b2\"5y\u0012*j5") + this.a, var2);
               return null;
            }
         }
      }

      void a() {
         this.b = null;
      }

      public SnmpMibTableRow getMibTableRow() {
         return this.a;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 93;
                  break;
               case 1:
                  var10003 = 80;
                  break;
               case 2:
                  var10003 = 21;
                  break;
               case 3:
                  var10003 = 11;
                  break;
               default:
                  var10003 = 125;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
