package monfox.toolkit.snmp.agent.x.pdu;

import java.util.Enumeration;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;

public abstract class VarBindPDU extends AgentXPDU {
   private SnmpVarBindList a = null;

   public VarBindPDU(int var1) {
      super(var1);
   }

   public void setVarBindList(SnmpVarBindList var1) {
      this.a = var1;
   }

   public SnmpVarBindList getVarBindList() {
      return this.a;
   }

   protected EncBuffer encodePayload() throws CoderException {
      int var4 = AgentXPDU.i;
      EncBuffer var1 = new EncBuffer();
      var1.setFlags(this.getFlags());
      this.encodeContext(var1);
      EncBuffer var10000;
      if (this.a != null) {
         Enumeration var2 = this.a.getVarBinds();

         while(var2.hasMoreElements()) {
            SnmpVarBind var3 = (SnmpVarBind)var2.nextElement();
            var10000 = var1;
            if (var4 != 0) {
               return var10000;
            }

            var1.addVarBind(var3);
            if (var4 != 0) {
               break;
            }
         }
      }

      var10000 = var1;
      return var10000;
   }

   public void decodePayload(byte[] var1) throws CoderException {
      int var5 = AgentXPDU.i;
      DecBuffer var2 = new DecBuffer(var1);
      var2.setFlags(this.getFlags());
      this.decodeContext(var2);
      if (var2.hasMoreData()) {
         SnmpVarBindList var3 = new SnmpVarBindList();

         while(var2.hasMoreData()) {
            SnmpVarBind var4 = var2.nextVarBind();
            var3.add(var4);
            if (var5 != 0) {
               return;
            }

            if (var5 != 0) {
               break;
            }
         }

         this.a = var3;
      }

   }

   public void toString(StringBuffer var1) {
      super.toString(var1);
      var1.append(a("3\u001f\u0011\u00059XMsL!]L\u0011\u0005r\u00195")).append(this.a);
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 57;
               break;
            case 1:
               var10003 = 63;
               break;
            case 2:
               var10003 = 49;
               break;
            case 3:
               var10003 = 37;
               break;
            default:
               var10003 = 79;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
