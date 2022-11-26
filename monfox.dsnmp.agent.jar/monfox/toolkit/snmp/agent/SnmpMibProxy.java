package monfox.toolkit.snmp.agent;

import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpNull;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.mgr.SnmpTimeoutException;

public class SnmpMibProxy extends SnmpMibNode {
   protected SnmpOid _maxOid;
   protected SnmpSession _session;

   public SnmpMibProxy(SnmpMetadata var1, String var2, SnmpSession var3) throws SnmpValueException {
      super(var1, var2);
      this._maxOid = null;
      this._session = var3;
   }

   public SnmpMibProxy(String var1, SnmpSession var2) throws SnmpValueException {
      super(var1);
      this._maxOid = null;
      this._session = var2;
   }

   public SnmpMibProxy(SnmpOid var1, SnmpSession var2) {
      super(var1);
      this._maxOid = null;
      this._session = var2;
   }

   public SnmpMibProxy(SnmpMetadata var1, String var2, String var3, SnmpSession var4) throws SnmpValueException, SnmpMibException {
      this(new SnmpOid(var1, var2), new SnmpOid(var1, var3), var4);
   }

   public SnmpMibProxy(String var1, String var2, SnmpSession var3) throws SnmpValueException, SnmpMibException {
      this(new SnmpOid(var1), new SnmpOid(var2), var3);
      this._session = var3;
   }

   public SnmpMibProxy(SnmpOid var1, SnmpOid var2, SnmpSession var3) throws SnmpMibException {
      super(var1);
      if (var2 != null && var2.compareTo(var1) < 0) {
         throw new SnmpMibException(a("\rM\u0011x\t^V\u0019n]\u0017JPf\u0018\rJP~\u0015\u001fWPg\u001c\u0006\u0019\u001fc\u0019"));
      } else {
         this._maxOid = var2;
         this._session = var3;
      }
   }

   public SnmpSession getSession() {
      return this._session;
   }

   public SnmpOid getMaxOid() {
      return this._maxOid;
   }

   public void getRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      SnmpVarBindList var4 = new SnmpVarBindList();
      var4.add(var3.getOid());

      SnmpVarBindList var5;
      try {
         var5 = this._session.performGet(var4);
      } catch (SnmpTimeoutException var7) {
         var1.setError(5, var2);
         return;
      } catch (SnmpErrorException var8) {
         var1.setError(var8.getStatus(), var2);
         return;
      } catch (SnmpException var9) {
         var1.setError(5, var2);
         return;
      }

      var3.setOid(var5.get(0).getOid());
      var3.setValue(var5.get(0).getValue());
   }

   public void getNextRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      boolean var9 = SnmpMibNode.b;
      SnmpVarBindList var4 = new SnmpVarBindList();
      var4.add(var3.getOid());
      Object var5 = null;
      SnmpOid var6 = null;

      do {
         SnmpVarBindList var7;
         try {
            var7 = this._session.performGetNext(var4);
         } catch (SnmpTimeoutException var10) {
            var1.setError(5, var2);
            return;
         } catch (SnmpErrorException var11) {
            var1.setError(var11.getStatus(), var2);
            return;
         } catch (SnmpException var12) {
            var1.setError(5, var2);
            return;
         }

         var5 = var7.get(0).getValue();
         var6 = var7.get(0).getOid();
         if (this._oid.compareTo(var6) <= 0) {
            break;
         }

         var4.get(0).setOid(var6);
      } while(!var9);

      label30: {
         if (this._maxOid == null) {
            if (this.getOid().contains(var6)) {
               break label30;
            }

            var5 = SnmpNull.endOfMibView;
            var6 = var3.getOid();
            if (!var9) {
               break label30;
            }
         }

         if (this._maxOid.compareTo(var6) < 0) {
            var5 = SnmpNull.endOfMibView;
            var6 = var3.getOid();
         }
      }

      var3.setOid(var6);
      var3.setValue((SnmpValue)var5);
   }

   public int prepareSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      SnmpVarBindList var4 = new SnmpVarBindList();
      var4.add(var3);

      try {
         this._session.performSet(var4);
         return 0;
      } catch (SnmpTimeoutException var7) {
         return 5;
      } catch (SnmpErrorException var8) {
         return var8.getStatus();
      } catch (SnmpException var9) {
         return 5;
      }
   }

   public boolean commitSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      return true;
   }

   public boolean undoSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
      return true;
   }

   public void cleanupSetRequest(SnmpPendingIndication var1, int var2, SnmpVarBind var3) {
   }

   public boolean checkAccess(SnmpPendingIndication var1, int var2, SnmpAccessPolicy var3) {
      return true;
   }

   public String toString() {
      boolean var2 = SnmpMibNode.b;
      StringBuffer var1 = new StringBuffer();
      var1.append(a("\u000eK\u001fr\u0004C"));
      var1.append('{');
      var1.append(a("\u0011P\u00147"));
      var1.append(this.getOid());
      var1.append(',');
      var1.append(a("\u0013X\bE\u0014\u001a\u0004"));
      var1.append(this.getMaxOid());
      var1.append(',');
      var1.append(a("\u0010V\u0014o)\u0007I\u00157"));
      switch (this.getNodeType()) {
         case 1:
            var1.append(a("2|1L"));
            if (!var2) {
               break;
            }
         case 2:
            var1.append(a(",x>M8"));
            if (!var2) {
               break;
            }
         case 3:
            var1.append(a("-l2U),|5"));
            if (!var2) {
               break;
            }
         default:
            var1.append(a("A\u0006O"));
      }

      var1.append('}');
      return var1.toString();
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
               var10003 = 57;
               break;
            case 2:
               var10003 = 112;
               break;
            case 3:
               var10003 = 10;
               break;
            default:
               var10003 = 125;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
