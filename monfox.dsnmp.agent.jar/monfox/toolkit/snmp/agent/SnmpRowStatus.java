package monfox.toolkit.snmp.agent;

import monfox.toolkit.snmp.SnmpInt;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;

public class SnmpRowStatus extends SnmpMibLeaf {
   public static final int DOES_NOT_EXIST = 0;
   public static final int ACTIVE = 1;
   public static final int NOT_IN_SERVICE = 2;
   public static final int NOT_READY = 3;
   public static final int CREATE_AND_GO = 4;
   public static final int CREATE_AND_WAIT = 5;
   public static final int DESTROY = 6;

   public SnmpRowStatus(SnmpOid var1, SnmpOid var2) throws SnmpMibException, SnmpValueException {
      super(var1, var2);
   }

   public void setValue(SnmpValue var1) throws SnmpValueException {
      super.setValue(var1);
      this.a(this.getSavedValue(), this.getValue());
   }

   public boolean unsetValue() {
      this.a(this.getValue(), this.getSavedValue());
      return super.unsetValue();
   }

   public int intValue() {
      return this.hasValue() ? this.getValue().intValue() : 0;
   }

   public void setValue(int var1) throws SnmpValueException {
      this.setValue(new SnmpInt(var1));
   }

   public void setActive() throws SnmpValueException {
      this.setValue(1);
   }

   public boolean isActive() {
      return this.intValue() == 1;
   }

   public int getAccess() {
      return 5;
   }

   public int getType() {
      return 2;
   }

   public int valueOk(SnmpValue var1) {
      if (var1 == null) {
         return 7;
      } else if (2 != var1.getType()) {
         return 7;
      } else {
         int var2 = var1.intValue();
         if (var2 >= 1 && 6 >= var2) {
            return var2 == 3 ? 10 : 0;
         } else {
            return 10;
         }
      }
   }

   private void a(SnmpValue var1, SnmpValue var2) {
      if (this.getTable() != null && this.getRow() != null) {
         int var3 = var1 == null ? 0 : var1.intValue();
         int var4 = var2 == null ? 0 : var2.intValue();
         if (var3 != var4) {
            if (var4 == 1) {
               this.getTable().fireRowActivatedEvent(this.getRow());
            }

            if (var3 == 1) {
               this.getTable().fireRowDeactivatedEvent(this.getRow());
            }

         }
      }
   }

   public static String intToString(int var0) {
      switch (var0) {
         case 0:
            return a("\u0005sYY)\u000fsHU3\u0019uO^");
         case 1:
            return a("\u0000\u007fHC \u0004");
         case 2:
            return a("\u000fsHU?\u000fcOO$\u0017u_O");
         case 3:
            return a("\u000fsHU$\u0004}XS");
         case 4:
            return a("\u0002nYK\"\u0004c]D2\u001e{S");
         case 5:
            return a("\u0002nYK\"\u0004c]D2\u001ek]C\"");
         case 6:
            return a("\u0005yO^$\u000ee");
         default:
            return a("~\u0003#");
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
               var10003 = 65;
               break;
            case 1:
               var10003 = 60;
               break;
            case 2:
               var10003 = 28;
               break;
            case 3:
               var10003 = 10;
               break;
            default:
               var10003 = 118;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
