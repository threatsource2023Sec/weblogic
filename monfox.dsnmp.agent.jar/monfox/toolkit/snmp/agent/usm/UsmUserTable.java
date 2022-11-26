package monfox.toolkit.snmp.agent.usm;

import monfox.log.Logger;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibLeafFactory;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpMibTableListener;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.agent.SnmpPendingIndication;
import monfox.toolkit.snmp.agent.tc.StorageType;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.engine.SnmpSecurityParameters;
import monfox.toolkit.snmp.v3.usm.USMEngineInfo;
import monfox.toolkit.snmp.v3.usm.USMLocalizedUserData;

public class UsmUserTable extends SnmpMibTable implements SnmpMibLeafFactory, SnmpMibTableListener {
   private USMEngineInfo a;
   private SnmpEngineID b;
   private SnmpString c;
   private Usm d;
   private static final int LEAF = 1;
   private static final int RANGE = 2;
   private static final int RANGE_END = 3;
   private static final int SUB_TREE = 4;
   private static final int e = 5;
   private static final int f = 6;
   private static final int g = 7;
   private static final int h = 8;
   private static final int i = 9;
   private static final int j = 10;
   private static final int k = 11;
   private static final int l = 12;
   private static final int m = 13;
   private static final SnmpOid n = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 10L, 1L, 1L, 1L});
   private static final SnmpOid o = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 10L, 1L, 1L, 2L});
   private static final SnmpOid p = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 10L, 1L, 1L, 3L});
   private static final SnmpOid q = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 10L, 1L, 2L, 1L});
   private static final SnmpOid r = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 10L, 1L, 2L, 2L});
   private static final SnmpString s = new SnmpString(new byte[0]);
   private static final SnmpOid t = SnmpOid.getStatic(new long[]{0L, 0L});
   private Logger u = Logger.getInstance(a("\b0I:\u0002"), a("\r$B9\u0006a6T:"), a("\u0019\u0010j\"!)\u0011S\u00160 \u0006"));

   protected UsmUserTable(Usm var1, USMEngineInfo var2) throws SnmpValueException, SnmpMibException {
      super(Usm.usmUserTable);
      this.isSettableWhenActive(true);
      this.d = var1;
      this.a = var2;
      this.b = var2.getEngineID();
      this.c = new SnmpString(this.b.getValue());
      this.setDefaultFactory(this);
      this.addListener(this);
   }

   public Usm getUsm() {
      return this.d;
   }

   public SnmpMibLeaf getInstance(SnmpMibTable var1, SnmpOid var2, SnmpOid var3) throws SnmpMibException, SnmpValueException {
      int var4 = (int)var2.getLast();
      switch (var4) {
         case 3:
            return new UsmUserSecurityName(var3);
         case 4:
            return new UsmUserCloneFrom(var3);
         case 5:
            return new UsmUserAuthProtocol(var3);
         case 6:
            return new UsmUserKeyChange(Usm.usmUserAuthKeyChange, var3, true);
         case 7:
            return new UsmUserOwnKeyChange(Usm.usmUserOwnAuthKeyChange, var3, true);
         case 8:
            return new UsmUserPrivProtocol(var3);
         case 9:
            return new UsmUserKeyChange(Usm.usmUserPrivKeyChange, var3, false);
         case 10:
            return new UsmUserOwnKeyChange(Usm.usmUserOwnAuthKeyChange, var3, false);
         case 11:
            return new UsmUserPublic(var3);
         case 12:
            return new StorageType(var2, var3, 3);
         default:
            return new SnmpMibLeaf(var2, var3);
      }
   }

   public boolean validateIndex(SnmpOid var1, SnmpValue[] var2) {
      return true;
   }

   public SnmpMibTableRow add(SnmpEngineID var1, USMLocalizedUserData var2) throws SnmpMibException, SnmpValueException {
      SnmpString var3 = new SnmpString(var2.getName());
      SnmpMibTableRow var4 = this.initRow(new SnmpValue[]{new SnmpString(var1.getValue()), var3});
      var4.setUserObject(var2);
      var4.getRowStatusLeaf().setValue(1);
      this.addRow(var4);
      return var4;
   }

   public SnmpMibTableRow add(USMLocalizedUserData var1) throws SnmpMibException, SnmpValueException {
      SnmpString var2 = new SnmpString(var1.getName());
      SnmpMibTableRow var3 = this.initRow(new SnmpValue[]{this.c, var2});
      var3.setUserObject(var1);
      var3.getRowStatusLeaf().setValue(1);
      this.addRow(var3);
      return var3;
   }

   public void remove(String var1) throws SnmpMibException, SnmpValueException {
      SnmpMibTableRow var2 = this.removeRow(new SnmpValue[]{this.c, new SnmpString(var1)});
      if (var2 != null) {
         var2.destroy();
      }

   }

   public void remove(SnmpEngineID var1, String var2) throws SnmpMibException, SnmpValueException {
      SnmpMibTableRow var3 = this.removeRow(new SnmpValue[]{new SnmpString(var1.getValue()), new SnmpString(var2)});
      if (var3 != null) {
         var3.destroy();
      }

   }

   public SnmpMibTableRow get(String var1) throws SnmpMibException, SnmpValueException {
      return this.getRow(new SnmpValue[]{this.c, new SnmpString(var1)});
   }

   public SnmpMibTableRow get(SnmpEngineID var1, String var2) throws SnmpMibException, SnmpValueException {
      return this.getRow(new SnmpValue[]{new SnmpString(var1.getValue()), new SnmpString(var2)});
   }

   public void rowInit(SnmpMibTableRow var1) {
   }

   public void rowAdded(SnmpMibTableRow var1) {
   }

   public void rowActivated(SnmpMibTableRow var1) {
      USMLocalizedUserData var2 = (USMLocalizedUserData)var1.getUserObject();
      if (var2 != null) {
         USMEngineInfo var3 = this.d.a((SnmpString)var1.getLeaf(1).getValue());
         var3.addUserData(var2);
      }

      this.d.a(1, var2, var1);
   }

   public void rowDeactivated(SnmpMibTableRow var1) {
      USMLocalizedUserData var2 = (USMLocalizedUserData)var1.getUserObject();
      if (var2 != null) {
         USMEngineInfo var3 = this.d.a((SnmpString)var1.getLeaf(1).getValue());
         var3.removeUserData(var2.getName());
      }

      this.d.a(2, var2, var1);
   }

   public void rowRemoved(SnmpMibTableRow var1) {
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 76;
               break;
            case 1:
               var10003 = 99;
               break;
            case 2:
               var10003 = 7;
               break;
            case 3:
               var10003 = 119;
               break;
            default:
               var10003 = 82;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   class UsmUserPublic extends SnmpMibLeaf {
      public UsmUserPublic(SnmpOid var2) throws SnmpMibException, SnmpValueException {
         super(Usm.usmUserPublic, var2);
         this.setValue(UsmUserTable.s);
      }
   }

   class UsmUserOwnKeyChange extends UsmUserKeyChange {
      public UsmUserOwnKeyChange(SnmpOid var2, SnmpOid var3, boolean var4) throws SnmpMibException {
         super(var2, var3, var4);
      }

      public int prepareSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
         int var4 = super.prepareSetRequest(var1, var2, var3);
         if (var4 != 0) {
            return var4;
         } else {
            SnmpSecurityParameters var5 = var1.getRequest().getSecurityParameters();
            if (var5.getSecurityModel() != 3) {
               return 6;
            } else {
               USMLocalizedUserData var6 = (USMLocalizedUserData)this.getRow().getUserObject();
               return var6 != null && var6.getName() != null && var6.getName().equals(new String(var5.getSecurityName())) ? 0 : 6;
            }
         }
      }
   }

   class UsmUserKeyChange extends SnmpMibLeaf {
      public static final boolean AUTH = true;
      public static final boolean PRIV = false;
      boolean a = false;
      private Runnable b = null;

      public UsmUserKeyChange(SnmpOid var2, SnmpOid var3, boolean var4) throws SnmpMibException {
         super(var2, var3);
         this.a = var4;
      }

      public SnmpValue getValue() {
         return UsmUserTable.s;
      }

      public int valueOk(SnmpValue var1) {
         byte[] var2 = var1.getByteArray();
         if (var2 == null) {
            return 10;
         } else {
            USMLocalizedUserData var3 = (USMLocalizedUserData)this.getRow().getUserObject();
            if (var3 == null) {
               return 18;
            } else {
               int var4;
               if (this.a) {
                  if (!var3.hasAuth()) {
                     return 0;
                  }

                  var4 = var3.getLocalizedAuthKey().length;
                  if (!Usm.q) {
                     return var2.length != var4 * 2 ? 8 : 0;
                  }
               }

               if (!var3.hasPriv()) {
                  return 0;
               } else {
                  var4 = var3.getLocalizedPrivKey().length;
                  return var2.length != var4 * 2 ? 8 : 0;
               }
            }
         }
      }

      public boolean commitSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
         boolean var8 = Usm.q;
         SnmpValue var4 = var3.getValue();
         byte[] var5 = var4.getByteArray();
         USMLocalizedUserData var6 = (USMLocalizedUserData)this.getRow().getUserObject();
         if (var6 == null) {
            return true;
         } else {
            boolean var7;
            label32: {
               if (this.a) {
                  if (!var6.hasAuth()) {
                     return true;
                  }

                  var7 = var6.validateAuthKeyChange(var5);
                  if (!var8) {
                     break label32;
                  }
               }

               if (!var6.hasPriv()) {
                  return true;
               }

               var7 = var6.validatePrivKeyChange(var5);
            }

            if (var7) {
               this.b = new KeyChangeProc(var6, var5);
               var1.addPostProcessor(this.b);
               if (!var8) {
                  return true;
               }
            }

            return false;
         }
      }

      public boolean undoSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
         if (this.b != null) {
            var1.removePostProcessor(this.b);
         }

         return true;
      }

      public void cleanupValue() {
         this.b = null;
      }

      class KeyChangeProc implements Runnable {
         private USMLocalizedUserData a = null;
         private byte[] b = null;

         KeyChangeProc(USMLocalizedUserData var2, byte[] var3) {
            this.a = var2;
            this.b = var3;
         }

         public void run() {
            if (UsmUserKeyChange.this.a) {
               this.a.authKeyChange(this.b);
               UsmUserTable.this.d.a(3, this.a, UsmUserKeyChange.this.getRow());
               if (!Usm.q) {
                  return;
               }
            }

            this.a.privKeyChange(this.b);
            UsmUserTable.this.d.a(4, this.a, UsmUserKeyChange.this.getRow());
         }
      }
   }

   class UsmUserPrivProtocol extends SnmpMibLeaf {
      private boolean a = false;

      public UsmUserPrivProtocol(SnmpOid var2) throws SnmpMibException {
         super(Usm.usmUserPrivProtocol, var2);
      }

      public SnmpValue getValue() {
         USMLocalizedUserData var1 = (USMLocalizedUserData)this.getRow().getUserObject();
         if (var1 != null && var1.hasPriv()) {
            if (var1.getPrivProtocol() == 4) {
               return Snmp.AES128_PRIV_PROTOCOL_OID;
            } else if (var1.getPrivProtocol() == 5) {
               return Snmp.AES192_PRIV_PROTOCOL_OID;
            } else if (var1.getPrivProtocol() == 6) {
               return Snmp.AES256_PRIV_PROTOCOL_OID;
            } else {
               return var1.getPrivProtocol() == 14832 ? Snmp.TDES_PRIV_PROTOCOL_OID : UsmUserTable.r;
            }
         } else {
            return UsmUserTable.q;
         }
      }

      public int valueOk(SnmpValue var1) {
         return !UsmUserTable.q.equals(var1) ? 12 : 0;
      }

      public void setValue(SnmpValue var1) {
         this.a = true;
      }

      public boolean unsetValue() {
         this.a = false;
         return true;
      }

      public void cleanupValue() {
         if (this.a) {
            USMLocalizedUserData var1 = (USMLocalizedUserData)this.getRow().getUserObject();
            if (var1 != null) {
               var1.clearPriv();
            }

         }
      }
   }

   class UsmUserAuthProtocol extends SnmpMibLeaf {
      private boolean a = false;

      public UsmUserAuthProtocol(SnmpOid var2) throws SnmpMibException {
         super(Usm.usmUserAuthProtocol, var2);
      }

      public SnmpValue getValue() {
         USMLocalizedUserData var1 = (USMLocalizedUserData)this.getRow().getUserObject();
         if (UsmUserTable.this.u.isDebugEnabled()) {
            UsmUserTable.this.u.debug(a("+Z\u001dGX\u001b[1g_\u0016y\u0002}_\u0011J\u001f~\u0005\u0019L\u0004DJ\u0012\\\u0015(\u000b") + var1);
         }

         if (var1 != null && var1.hasAuth()) {
            if (var1.getAuthProtocol() == 0) {
               return UsmUserTable.o;
            } else {
               return var1.getAuthProtocol() == 1 ? UsmUserTable.p : UsmUserTable.n;
            }
         } else {
            return UsmUserTable.n;
         }
      }

      public int valueOk(SnmpValue var1) {
         if (!UsmUserTable.n.equals(var1)) {
            return 12;
         } else {
            return !UsmUserTable.q.equals(this.getRow().getLeaf(8).getValue()) ? 12 : 0;
         }
      }

      public void setValue(SnmpValue var1) {
         this.a = true;
      }

      public boolean unsetValue() {
         this.a = false;
         return true;
      }

      public void cleanupValue() {
         if (this.a) {
            USMLocalizedUserData var1 = (USMLocalizedUserData)this.getRow().getUserObject();
            if (var1 != null) {
               var1.clearAuth();
            }

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
                  var10003 = 126;
                  break;
               case 1:
                  var10003 = 41;
                  break;
               case 2:
                  var10003 = 112;
                  break;
               case 3:
                  var10003 = 18;
                  break;
               default:
                  var10003 = 43;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   class UsmUserCloneFrom extends SnmpMibLeaf {
      private boolean a = false;

      public UsmUserCloneFrom(SnmpOid var2) throws SnmpMibException {
         super(Usm.usmUserCloneFrom, var2);
      }

      public SnmpValue getValue() {
         return UsmUserTable.t;
      }

      public int valueOk(SnmpValue var1) {
         if (!(var1 instanceof SnmpOid)) {
            return 7;
         } else {
            SnmpOid var2 = (SnmpOid)var1;
            return this.a(var2) == null ? 18 : 0;
         }
      }

      public void setValue(SnmpValue var1) throws SnmpValueException {
         if (!this.getRow().hasUserObject()) {
            SnmpMibTableRow var2 = this.a((SnmpOid)var1);
            if (var2 == null) {
               throw new SnmpValueException(a("7jQ>+7`\u0007/(7jS:5d$") + var1);
            } else {
               String var3 = this.getRow().getLeaf(2).getValue().getString();
               USMLocalizedUserData var4 = (USMLocalizedUserData)var2.getUserObject();
               USMLocalizedUserData var5 = var4.clone(var3);
               this.getRow().setUserObject(var5);
               this.a = true;
            }
         }
      }

      public boolean unsetValue() {
         if (!this.a) {
            return true;
         } else {
            this.getRow().setUserObject((Object)null);
            return true;
         }
      }

      public void cleanupValue() {
         this.a = false;
      }

      private SnmpMibTableRow a(SnmpOid var1) {
         if (!UsmUserTable.this.validateInstance(var1)) {
            return null;
         } else {
            SnmpOid var2 = UsmUserTable.this.indexFromInstance(var1);
            if (var2 == null) {
               return null;
            } else {
               SnmpMibTableRow var3 = this.getTable().getRow(var2);
               if (var3 == null) {
                  return null;
               } else {
                  return var3.getRowStatus() != 1 ? null : var3;
               }
            }
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
                  var10003 = 94;
                  break;
               case 1:
                  var10003 = 4;
                  break;
               case 2:
                  var10003 = 39;
                  break;
               case 3:
                  var10003 = 95;
                  break;
               default:
                  var10003 = 71;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   class UsmUserSecurityName extends SnmpMibLeaf {
      public UsmUserSecurityName(SnmpOid var2) throws SnmpMibException {
         super(Usm.usmUserSecurityName, var2);
      }

      public SnmpValue getValue() {
         return this.getRow().getLeaf(2).getValue();
      }
   }
}
