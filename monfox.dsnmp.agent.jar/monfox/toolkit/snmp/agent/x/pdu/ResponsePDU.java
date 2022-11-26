package monfox.toolkit.snmp.agent.x.pdu;

import java.util.Enumeration;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;

public class ResponsePDU extends AgentXPDU {
   public static final int NO_AGENT_X_ERROR = 0;
   public static final int OPEN_FAILED_ERROR = 256;
   public static final int NOT_OPEN_ERROR = 257;
   public static final int INDEX_WRONG_TYPE_ERROR = 258;
   public static final int INDEX_ALREADY_ALLOCATED_ERROR = 259;
   public static final int INDEX_NONE_AVAILABLE_ERROR = 260;
   public static final int INDEX_NOT_ALLOCATED_ERROR = 261;
   public static final int UNSUPPORTED_CONTEXT_ERROR = 262;
   public static final int DUPLICATE_REGISTRATION_ERROR = 263;
   public static final int UNKNOWN_REGISTRATION_ERROR = 264;
   public static final int UNKNOWN_AGENT_CAPS_ERROR = 265;
   public static final int PARSE_ERROR = 266;
   public static final int REQUEST_DENIED_ERROR = 267;
   public static final int PROCESSING_ERROR = 268;
   private long a = 0L;
   private int b = 0;
   private int c = 0;
   private SnmpVarBindList d = null;

   public ResponsePDU() {
      super(18);
   }

   public void setSysUpTime(long var1) {
      this.a = var1;
   }

   public void setError(int var1) {
      this.b = var1;
   }

   public void setIndex(int var1) {
      this.c = var1;
   }

   public void setVarBindList(SnmpVarBindList var1) {
      this.d = var1;
   }

   public long getSysUpTime() {
      return this.a;
   }

   public int getError() {
      return this.b;
   }

   public int getIndex() {
      return this.c;
   }

   public SnmpVarBindList getVarBindList() {
      return this.d;
   }

   public String getErrorString() {
      return getErrorString(this.b);
   }

   public static String getErrorString(int var0) {
      String var1 = null;
      switch (var0) {
         case 0:
            var1 = a("M%/%\\M>6\u0007KQ%\u001c");
            break;
         case 256:
            var1 = a("L:\u000b,\u007fB#\u0002']");
            break;
         case 257:
            var1 = a("M%\u001a\rIF$");
            break;
         case 258:
            var1 = a("J$\n'At8\u0001,^w3\u001e'");
            break;
         case 259:
            var1 = a("J$\n'Ab&\u001c'XG3/.UL)\u000f6\\G");
            break;
         case 260:
            var1 = a("J$\n'Am%\u0000'xU+\u0007.XA&\u000b");
            break;
         case 261:
            var1 = a("J$\n'Am%\u001a\u0003UO%\r#MJ%\u0000");
            break;
         case 262:
            var1 = a("V$\u001d7IS%\u001c6\\G\t\u0001,MF2\u001a");
            break;
         case 263:
            var1 = a("G?\u001e.P@+\u001a'kF-\u00071MQ+\u001a+VM");
            break;
         case 264:
            var1 = a("V$\u0005,VT$<'^J9\u001a0XW#\u0001,");
            break;
         case 265:
            var1 = a("V$\u0005,VT$/%\\M>-#IP");
            break;
         case 266:
            var1 = a("S+\u001c1\\f8\u001c-K");
            break;
         case 267:
            var1 = a("Q/\u001f7\\P>*'WJ/\n");
            break;
         case 268:
            var1 = a("S8\u0001!\\P9\u0007,^f8\u001c-K");
            break;
         default:
            var1 = Snmp.errorStatusToString(var0);
      }

      return var1;
   }

   protected EncBuffer encodePayload() throws CoderException {
      int var4 = AgentXPDU.i;
      EncBuffer var1 = new EncBuffer();
      var1.setFlags(this.getFlags());
      var1.addInt32(this.a);
      var1.addInt16(this.b);
      var1.addInt16(this.c);
      EncBuffer var10000;
      if (this.d != null) {
         Enumeration var2 = this.d.getVarBinds();

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
      this.a = var2.nextInt32();
      this.b = var2.nextInt16();
      this.c = var2.nextInt16();
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

         this.d = var3;
      }

   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      var1.append(a(")\u000b\t'WW\u0012C\u0010\\P:\u0001,JFg>\u0006l\u00031"));
      super.toString(var1);
      var1.append(a(")jNbJZ9;2mJ'\u000bb\u0004\u0003")).append(this.a);
      var1.append(a(")jNb\\Q8\u00010\u0019\u0003jNb\u0004\u0003")).append(this.b);
      var1.append(a(")jNbPM.\u000b:\u0019\u0003jNb\u0004\u0003")).append(this.c);
      var1.append(a(")jNbOB8,+WG9Nb\u0004\u0003@")).append(this.d);
      var1.append(a(")7d"));
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
               var10003 = 35;
               break;
            case 1:
               var10003 = 74;
               break;
            case 2:
               var10003 = 110;
               break;
            case 3:
               var10003 = 66;
               break;
            default:
               var10003 = 57;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
