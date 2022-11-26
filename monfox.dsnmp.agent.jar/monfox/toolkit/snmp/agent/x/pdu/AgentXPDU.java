package monfox.toolkit.snmp.agent.x.pdu;

import monfox.log.Logger;

public abstract class AgentXPDU {
   public static final int OPEN_PDU = 1;
   public static final int CLOSE_PDU = 2;
   public static final int REGISTER_PDU = 3;
   public static final int UNREGISTER_PDU = 4;
   public static final int GET_PDU = 5;
   public static final int GETNEXT_PDU = 6;
   public static final int GETBULK_PDU = 7;
   public static final int TESTSET_PDU = 8;
   public static final int COMMITSET_PDU = 9;
   public static final int UNDOSET_PDU = 10;
   public static final int CLEANUPSET_PDU = 11;
   public static final int NOTIFY_PDU = 12;
   public static final int PING_PDU = 13;
   public static final int INDEXALLOCATE_PDU = 14;
   public static final int INDEXDEALLOCATE_PDU = 15;
   public static final int ADDAGENTCAPS_PDU = 16;
   public static final int REMOVEAGENTCAPS_PDU = 17;
   public static final int RESPONSE_PDU = 18;
   public static final int DEFAULT_VERSION = 1;
   public static final int DEFAULT_FLAGS = 0;
   public static final int INSTANCE_REGISTRATION = 1;
   public static final int NEW_INDEX = 2;
   public static final int ANY_INDEX = 4;
   public static final int NON_DEFAULT_CONTEXT = 8;
   public static final int NETWORK_BYTE_ORDER = 16;
   private int a = 1;
   private int b = 0;
   private int c = 0;
   private int d = 0;
   private int e = 0;
   private int f = 0;
   private String g = null;
   private Logger h = Logger.getInstance(a("Z\u001d\u0019j'"), a("_\t\u0012i#Fc\u0004r5"), a("_)2I\u0003F\u001e\u0013r"));
   public static int i;

   public AgentXPDU(int var1) {
      this.setType(var1);
   }

   public int getVersion() {
      return this.a;
   }

   public int getType() {
      return this.b;
   }

   public int getFlags() {
      return this.c;
   }

   public int getSessionId() {
      return this.d;
   }

   public int getTransactionId() {
      return this.e;
   }

   public int getPacketId() {
      return this.f;
   }

   public void setVersion(int var1) {
      this.a = var1;
   }

   public void setType(int var1) {
      this.b = var1;
   }

   public void setFlags(int var1) {
      this.c = var1;
   }

   public void setSessionId(int var1) {
      this.d = var1;
   }

   public void setTransactionId(int var1) {
      this.e = var1;
   }

   public void setPacketId(int var1) {
      this.f = var1;
   }

   public void setFlag(int var1) {
      this.setFlags(this.getFlags() | var1);
   }

   public void resetFlag(int var1) {
      this.setFlags(this.getFlags() & ~var1);
   }

   public boolean isSet(int var1) {
      return (this.getFlags() & var1) != 0;
   }

   public byte[] encode() throws CoderException {
      label15: {
         if (this.getContext() != null) {
            this.setFlag(8);
            if (i == 0) {
               break label15;
            }
         }

         this.resetFlag(8);
      }

      EncBuffer var1 = new EncBuffer();
      var1.addHeader(this.a, this.b, this.c);
      var1.addInt32((long)this.d);
      var1.addInt32((long)this.e);
      var1.addInt32((long)this.f);
      EncBuffer var2 = this.encodePayload();
      int var3 = var2.getLength();
      var1.addInt32((long)var3);
      if (this.h.isDebugEnabled()) {
         this.h.debug(a("v+6C\u0012ln2I\u0014q*>I\u0010>\u0015;B\u0019>sw") + var1.getLength() + a("Ct]") + var1);
         this.h.debug(a("{ 4H\u0013w 0\u0007\u0007\u007f7;H\u0016z\u0015;B\u0019>sw") + var3 + a("Ct]") + var2);
      }

      var1.addBuffer(var2);
      return var1.getBytes();
   }

   protected void encodeContext(EncBuffer var1) throws CoderException {
      if ((this.c & 8) != 0) {
         if (this.getContext() != null) {
            var1.addString(this.getContext());
            if (i == 0) {
               return;
            }
         }

         var1.addString("");
      }

   }

   protected abstract EncBuffer encodePayload() throws CoderException;

   public int decodeHeader(byte[] var1) throws CoderException {
      DecBuffer var2 = new DecBuffer(var1);
      var2.decodeHeader();
      this.a = var2.getVersion();
      this.b = var2.getType();
      this.c = var2.getFlags();
      this.d = (int)var2.nextInt32();
      this.e = (int)var2.nextInt32();
      this.f = (int)var2.nextInt32();
      int var3 = (int)var2.nextInt32();
      return var3;
   }

   protected void decodeContext(DecBuffer var1) throws CoderException {
      if ((this.getFlags() & 8) != 0) {
         this.g = new String(var1.nextString());
      }

   }

   public abstract void decodePayload(byte[] var1) throws CoderException;

   protected void toString(StringBuffer var1) {
      var1.append(a("\u0014nw\u0007\u0001{<$N\u0018pnw\u0007J>")).append(this.a);
      var1.append(a("\u0014nw\u0007\u0003g>2\u0007W>nw\u0007J>")).append(this.b);
      var1.append(a("\u0014nw\u0007\u0011r/0TW>nw\u0007J>")).append(this.c);
      if ((this.c & 1) != 0) {
         var1.append(a("\u0014nw\u0007W>nw\u0007W>nw\u0007W>nw\u0007W6\u0007\u0019t#_\u0000\u0014b(L\u000b\u0010n$J\u001c\u0016s>Q\u0000~"));
      }

      if ((this.c & 2) != 0) {
         var1.append(a("\u0014nw\u0007W>nw\u0007W>nw\u0007W>nw\u0007W6\u0000\u0012p(W\u0000\u0013b/7"));
      }

      if ((this.c & 4) != 0) {
         var1.append(a("\u0014nw\u0007W>nw\u0007W>nw\u0007W>nw\u0007W6\u000f\u0019~(W\u0000\u0013b/7"));
      }

      if ((this.c & 8) != 0) {
         var1.append(a("\u0014nw\u0007W>nw\u0007W>nw\u0007W>nw\u0007W6\u0000\u0018i(Z\u000b\u0011f\"R\u001a\bd8P\u001a\u0012\u007f#7"));
      }

      if ((this.c & 16) != 0) {
         var1.append(a("\u0014nw\u0007W>nw\u0007W>nw\u0007W>nw\u0007W6\u0000\u0012s Q\u001c\u001cx5G\u001a\u0012x8L\n\u0012u^"));
      }

      var1.append(a("\u0014nw\u0007\u0004{=$N\u0018p\u00073\u0007J>")).append(this.d);
      var1.append(a("\u0014nw\u0007\u0003l/9T>znw\u0007J>")).append(this.e);
      var1.append(a("\u0014nw\u0007\u0007\u007f-<B\u0003W*w\u0007J>")).append(this.f);
      var1.append(a("\u0014nw\u0007\u0014q #B\u000fjnw\u0007J>")).append(this.g);
   }

   public String getContext() {
      switch (this.b) {
         case 1:
         case 2:
         case 9:
         case 10:
         case 11:
         case 18:
            return null;
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
         case 12:
         case 13:
         case 14:
         case 15:
         case 16:
         case 17:
         default:
            return this.g;
      }
   }

   public void setContext(String var1) {
      this.g = var1;
   }

   public static String typeToString(int var0) {
      String var1 = null;
      switch (var0) {
         case 1:
            var1 = a("_)2I\u0003Fc\u0018W\u0012pc\u0007c\"");
            break;
         case 2:
            var1 = a("_)2I\u0003Fc\u0014K\u0018m+zw3K");
            break;
         case 3:
            var1 = a("_)2I\u0003Fc\u0005B\u0010w=#B\u00053\u001e\u0013r");
            break;
         case 4:
            var1 = a("_)2I\u0003Fc\u0002I\u0005{)>T\u0003{<zw3K");
            break;
         case 5:
            var1 = a("_)2I\u0003Fc\u0010B\u00033\u001e\u0013r");
            break;
         case 6:
            var1 = a("_)2I\u0003Fc\u0010B\u0003P+/SZN\n\u0002");
            break;
         case 7:
            var1 = a("_)2I\u0003Fc\u0010B\u0003\\;;LZN\n\u0002");
            break;
         case 8:
            var1 = a("_)2I\u0003Fc\u0003B\u0004j\u001d2SZN\n\u0002");
            break;
         case 9:
            var1 = a("_)2I\u0003Fc\u0014H\u001as'#t\u0012jc\u0007c\"");
            break;
         case 10:
            var1 = a("_)2I\u0003Fc\u0002I\u0013q\u001d2SZN\n\u0002");
            break;
         case 11:
            var1 = a("_)2I\u0003Fc\u0014K\u0012\u007f \"W${:zw3K");
            break;
         case 12:
            var1 = a("_)2I\u0003Fc\u0019H\u0003w(.\n'Z\u001b");
            break;
         case 13:
            var1 = a("_)2I\u0003Fc\u0007N\u0019yc\u0007c\"");
            break;
         case 14:
            var1 = a("_)2I\u0003Fc\u001eI\u0013{6\u0016K\u001bq-6S\u00123\u001e\u0013r");
            break;
         case 15:
            var1 = a("_)2I\u0003Fc\u001eI\u0013{6\u0013B\u0016r\"8D\u0016j+zw3K");
            break;
         case 16:
            var1 = a("_)2I\u0003Fc\u0016C\u0013_)2I\u0003]/'TZN\n\u0002");
            break;
         case 17:
            var1 = a("_)2I\u0003Fc\u0005B\u001aq82f\u0010{ #d\u0016n=zw3K");
            break;
         case 18:
            var1 = a("_)2I\u0003Fc\u0005B\u0004n!9T\u00123\u001e\u0013r");
            break;
         default:
            var1 = a("K <I\u0018i ww3Kf") + var0 + ")";
      }

      return var1;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 30;
               break;
            case 1:
               var10003 = 78;
               break;
            case 2:
               var10003 = 87;
               break;
            case 3:
               var10003 = 39;
               break;
            default:
               var10003 = 119;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
