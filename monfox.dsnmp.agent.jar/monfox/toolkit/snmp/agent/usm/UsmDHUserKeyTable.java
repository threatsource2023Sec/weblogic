package monfox.toolkit.snmp.agent.usm;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Iterator;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
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
import monfox.toolkit.snmp.engine.SnmpSecurityParameters;
import monfox.toolkit.snmp.util.CryptoUtil;
import monfox.toolkit.snmp.v3.usm.USMAuthProtocolSpec;
import monfox.toolkit.snmp.v3.usm.USMLocalizedUserData;
import monfox.toolkit.snmp.v3.usm.USMPrivProtocolSpec;

public class UsmDHUserKeyTable extends SnmpMibTable implements SnmpMibLeafFactory {
   private UsmUserTable a;
   private SecureRandom b = new SecureRandom();
   private static final int LEAF = 1;
   private static final int RANGE = 2;
   private static final int RANGE_END = 3;
   private static final int SUB_TREE = 4;
   private Logger l = Logger.getInstance(a("0\u0007\u0000\u001dw"), a("!\u0007\u0003"), a("!'#\u0014o!'+\"l\u0011-\u001a1E\u00181"));
   private UserTableRowListener c;

   UsmDHUserKeyTable(UsmUserTable var1) throws SnmpValueException, SnmpMibException {
      super(UsmDH.usmDHUserKeyTable);
      this.isSettableWhenActive(true);
      this.a = var1;
      this.setDefaultFactory(this);
      this.c = new UserTableRowListener();
      this.a.addListener(this.c);
   }

   void a() {
      boolean var6 = Usm.q;
      Iterator var1 = this.getRows();

      label35:
      while(var1.hasNext()) {
         SnmpMibTableRow var2 = (SnmpMibTableRow)var1.next();
         int var3 = 1;

         while(var3 < 4) {
            UsmDHUserKeyChange var4 = (UsmDHUserKeyChange)var2.getLeaf(var3);

            label29: {
               try {
                  var4.generateNewKey();
               } catch (Exception var7) {
                  this.l.error(a("\u00175 >H\u0000t; C\u0015 +pc<t%5^"), var7);
                  break label29;
               }

               if (var6) {
                  continue label35;
               }
            }

            ++var3;
            if (var6) {
               break;
            }
         }

         if (var6) {
            break;
         }
      }

   }

   public SnmpMibLeaf getInstance(SnmpMibTable var1, SnmpOid var2, SnmpOid var3) throws SnmpMibException, SnmpValueException {
      int var4 = (int)var2.getLast();
      switch (var4) {
         case 1:
            return new UsmDHUserKeyChange(UsmDH.usmDHUserAuthKeyChange, var3, true);
         case 2:
            return new UsmDHUserOwnKeyChange(UsmDH.usmDHUserOwnAuthKeyChange, var3, true);
         case 3:
            return new UsmDHUserKeyChange(UsmDH.usmDHUserPrivKeyChange, var3, false);
         case 4:
            return new UsmDHUserOwnKeyChange(UsmDH.usmDHUserOwnAuthKeyChange, var3, false);
         default:
            return new SnmpMibLeaf(var2, var3);
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
               var10003 = 116;
               break;
            case 1:
               var10003 = 84;
               break;
            case 2:
               var10003 = 78;
               break;
            case 3:
               var10003 = 80;
               break;
            default:
               var10003 = 39;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class UserTableRowListener implements SnmpMibTableListener {
      private UserTableRowListener() {
      }

      public void rowInit(SnmpMibTableRow var1) {
      }

      public void rowAdded(SnmpMibTableRow var1) {
         SnmpOid var2 = var1.getIndex();

         try {
            SnmpMibTableRow var3 = UsmDHUserKeyTable.this.addRow(var2);
            if (var3 != null) {
               var3.setUserObject(var1.getUserObject());
            }
         } catch (Exception var4) {
            UsmDHUserKeyTable.this.l.error(a("QqjOOF0gSESda\u0001dz0QRE@0ODY\u0012DeCLW0VNW"), var4);
         }

      }

      public void rowActivated(SnmpMibTableRow var1) {
      }

      public void rowDeactivated(SnmpMibTableRow var1) {
      }

      public void rowRemoved(SnmpMibTableRow var1) {
         UsmDHUserKeyTable.this.removeRow(var1.getIndex());
      }

      // $FF: synthetic method
      UserTableRowListener(Object var2) {
         this();
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 50;
                  break;
               case 1:
                  var10003 = 16;
                  break;
               case 2:
                  var10003 = 4;
                  break;
               case 3:
                  var10003 = 33;
                  break;
               default:
                  var10003 = 32;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   class UsmDHUserOwnKeyChange extends UsmDHUserKeyChange {
      public UsmDHUserOwnKeyChange(SnmpOid var2, SnmpOid var3, boolean var4) throws SnmpMibException {
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

   class UsmDHUserKeyChange extends SnmpMibLeaf {
      public static final boolean AUTH = true;
      public static final boolean PRIV = false;
      boolean a = false;
      BigInteger b;
      BigInteger c;
      BigInteger d;
      BigInteger e;
      byte[] f;
      private Runnable g = null;

      public UsmDHUserKeyChange(SnmpOid var2, SnmpOid var3, boolean var4) throws SnmpMibException {
         super(var2, var3);
         this.a = var4;
         this.generateNewKey();
      }

      public synchronized void generateNewKey() throws SnmpMibException {
         try {
            UsmDHParameters var1 = ((UsmDH)UsmDHUserKeyTable.this.a.getUsm()).getUsmDHParameters();
            this.generateNewKey(var1);
         } catch (SnmpMibException var2) {
            throw var2;
         } catch (Exception var3) {
            UsmDHUserKeyTable.this.l.error(a("[ 3EjLa4ElL(<GlB$}oM\u00184.Nw\u0018*8R%[)<Eb]"), var3);
            throw new SnmpMibException(a("[ 3EjLa4ElL(<GlB$}oM\u00184.Nw\u0018*8R%[)<Eb]a2Io]\")"));
         }
      }

      public synchronized void generateNewKey(UsmDHParameters var1) throws SnmpMibException {
         boolean var7 = Usm.q;

         Exception var10000;
         label40: {
            BigInteger var2;
            BigInteger var3;
            int var4;
            BigInteger var5;
            boolean var10001;
            try {
               var2 = var1.getPrime();
               var3 = var1.getBase();
               var4 = var1.getPrivLength();
               var5 = null;
            } catch (Exception var10) {
               var10000 = var10;
               var10001 = false;
               break label40;
            }

            label37:
            while(true) {
               try {
                  var5 = new BigInteger(var4, UsmDHUserKeyTable.this.b);
               } catch (Exception var9) {
                  var10000 = var9;
                  var10001 = false;
                  break;
               }

               do {
                  try {
                     if (var5.compareTo(var2) >= 0) {
                        continue label37;
                     }

                     BigInteger var6 = var3.modPow(var5, var2);
                     this.b = var2;
                     this.c = var3;
                     this.d = var5;
                     this.e = var6;
                     this.f = CryptoUtil.encodeUnsigned(var6);
                     this.setValue(new SnmpString(this.f));
                  } catch (Exception var8) {
                     var10000 = var8;
                     var10001 = false;
                     break label37;
                  }
               } while(var7);

               if (SnmpException.b) {
                  Usm.q = !var7;
               }

               return;
            }
         }

         Exception var11 = var10000;
         UsmDHUserKeyTable.this.l.error(a("]3/Dw\u0018(3\u000blV()BdT('Bk_a3Nr\u0018\u0005\u0015\u000bN]8"), var11);
         throw new SnmpMibException(a("[ 3EjLa>Y`Y58\u000bApa6N|\u0018 :Y`],8Eq\u0018i") + var11 + ")");
      }

      public int prepareSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
         boolean var13 = Usm.q;

         try {
            int var4 = super.prepareSetRequest(var1, var2, var3);
            if (var4 != 0) {
               return var4;
            } else {
               SnmpValue var5 = var3.getValue();
               if (var5 == null) {
                  return 10;
               } else {
                  byte[] var6 = this.getValue().getByteArray();
                  byte[] var7 = var5.getByteArray();
                  if (var7.length <= var6.length) {
                     return 10;
                  } else {
                     int var8 = 0;

                     Object var10000;
                     while(true) {
                        if (var8 < var6.length) {
                           var10000 = var6;
                           if (var13) {
                              break;
                           }

                           if (var6[var8] != var7[var8]) {
                              return 10;
                           }

                           ++var8;
                           if (!var13) {
                              continue;
                           }
                        }

                        var10000 = this.getRow().getUserObject();
                        break;
                     }

                     USMLocalizedUserData var15 = (USMLocalizedUserData)var10000;
                     if (var15 == null) {
                        UsmDHUserKeyTable.this.l.warn(a("V.}Gj[ 1B\u007f]%}^v]3}OdL }MjJa6N|\u0018\"5Jk_$"));
                        return 18;
                     } else {
                        byte[] var9 = new byte[var7.length - var6.length];
                        System.arraycopy(var7, var6.length, var9, 0, var9.length);
                        BigInteger var10 = new BigInteger(1, var9);
                        BigInteger var11 = var10.modPow(this.d, this.b);
                        byte[] var12 = CryptoUtil.encodeUnsigned(var11);
                        var1.setUserObject(var2, var12);
                        return 0;
                     }
                  }
               }
            }
         } catch (Exception var14) {
            UsmDHUserKeyTable.this.l.error(a("]3/Dw\u0018(3\u000bu]3;DwU(3L%k$)\u000bw]0(NvLa;Dw\u0018\u0005\u0015\u000bN]8}HmY/:N"), var14);
            return 10;
         }
      }

      public boolean commitSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
         USMLocalizedUserData var4 = (USMLocalizedUserData)this.getRow().getUserObject();
         if (var4 == null) {
            UsmDHUserKeyTable.this.l.warn(a("V.}Gj[ 1B\u007f]%}^v]3}OdL }MjJa6N|\u0018\"5Jk_$"));
            return false;
         } else {
            boolean var5 = true;
            byte[] var6 = (byte[])((byte[])var1.getUserObject(var2));
            if (var6 == null) {
               UsmDHUserKeyTable.this.l.error(a("V.}Gj[ 1B\u007f]%}^v]3}OdL }MjJa6N|\u0018\"5Jk_$"));
               return false;
            } else {
               Object var7 = null;
               int var8;
               int var10;
               byte[] var12;
               if (this.a) {
                  if (!var4.hasAuth()) {
                     return true;
                  }

                  var8 = var4.getAuthProtocol();
                  USMAuthProtocolSpec var9 = USMAuthProtocolSpec.getSpec(var8);
                  var10 = var9.getDigestLength();
                  var12 = new byte[var10];
                  System.arraycopy(var6, var6.length - var10, var12, 0, var10);
               } else {
                  if (!var4.hasPriv()) {
                     return true;
                  }

                  var8 = var4.getPrivProtocol();
                  USMPrivProtocolSpec var13 = USMPrivProtocolSpec.getSpec(var8);
                  var10 = var13.getKeyLength();
                  var12 = new byte[var10];
                  System.arraycopy(var6, var6.length - var10, var12, 0, var10);
               }

               if (var5) {
                  try {
                     this.generateNewKey();
                  } catch (Exception var11) {
                     UsmDHUserKeyTable.this.l.error(a("]3/Dw\u0018(3\u000bb]/8YdL(3L%V$*\u000bn]8}JbJ$8F`V5"), var11);
                     return false;
                  }

                  this.g = new KeyChangeProc(var4, var12);
                  var1.addPostProcessor(this.g);
                  if (!Usm.q) {
                     return true;
                  }
               }

               return false;
            }
         }
      }

      public boolean undoSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
         if (this.g != null) {
            var1.removePostProcessor(this.g);
         }

         return true;
      }

      public void cleanupValue() {
         this.g = null;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 56;
                  break;
               case 1:
                  var10003 = 65;
                  break;
               case 2:
                  var10003 = 93;
                  break;
               case 3:
                  var10003 = 43;
                  break;
               default:
                  var10003 = 5;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }

      class KeyChangeProc implements Runnable {
         private USMLocalizedUserData a = null;
         private byte[] b = null;

         KeyChangeProc(USMLocalizedUserData var2, byte[] var3) {
            this.a = var2;
            this.b = var3;
         }

         public void run() {
            if (UsmDHUserKeyChange.this.a) {
               this.a.setLocalizedAuthKey(this.b);
               UsmDHUserKeyTable.this.a.getUsm().a(3, this.a, UsmDHUserKeyChange.this.getRow());
               if (!Usm.q) {
                  return;
               }
            }

            this.a.setLocalizedPrivKey(this.b);
            UsmDHUserKeyTable.this.a.getUsm().a(4, this.a, UsmDHUserKeyChange.this.getRow());
         }
      }
   }
}
