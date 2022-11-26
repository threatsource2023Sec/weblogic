package monfox.toolkit.snmp.agent.x.master;

import java.util.List;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValueException;

class d {
   public MasterAgentX.Session _session;
   private List a;
   private List b;
   private SnmpOid c;
   private boolean d;
   private int e;
   private int f;
   private String g;
   private Logger h;

   d(MasterAgentX.Session var1, String var2, SnmpOid var3, int var4, int var5, int var6, int var7, boolean var8) throws AgentXSubtreeException {
      boolean var16 = MasterAgentX.n;
      super();
      this.a = new Vector();
      this.b = new Vector();
      this.g = null;
      this.h = Logger.getInstance(a("J!(\u0001 "), a("O5#\u0002$#*"), a("]\u0007\u00048\u0002k\u0017"));
      this._session = var1;
      this.g = var2;
      this.c = var3;
      this.e = var6;
      this.f = var7;
      this.d = var8;
      if (var4 <= 0) {
         this.b.add(this.c);
         if (!var16) {
            return;
         }
      }

      if (var4 - 1 >= this.c.getLength()) {
         this.h.error(a("g\u001c\u0010-\u001cg\u0016F>\u0011`\u0015\u0003a\u0003{\u0010\u000f(P)") + var4 + a(")R\u0000#\u0002.\u0001\u0013.\u0004|\u0017\u0003lW") + var3 + "'");
         throw new AgentXSubtreeException(267);
      } else {
         try {
            long var9 = var3.get(var4 - 1);
            long var11 = (long)var5;
            if (var9 > var11) {
               this.h.error(a("g\u001c\u0010-\u001cg\u0016F9\u0000~\u0017\u0014a\u0012a\u0007\b(P&N\n#\u0007k\u0000\u0004#\u0005`\u0016Ok") + var5 + a(")R\u0000#\u0002.\u0001\u0013.\u0004|\u0017\u0003lW") + var3 + "'");
               throw new AgentXSubtreeException(267);
            }

            long var13 = var9;

            while(var13 <= var11) {
               SnmpOid var15 = (SnmpOid)var3.clone();
               var15.set(var4 - 1, var13);
               this.b.add(var15);
               ++var13;
               if (var16 || var16) {
                  break;
               }
            }
         } catch (SnmpValueException var17) {
            this.h.error(a("k\u0000\u0014#\u0002.\u001b\bl\u001fg\u0016Fk") + var3 + "'", var17);
            throw new AgentXSubtreeException(267);
         }

      }
   }

   public void addNode(a var1) {
      this.a.add(var1);
   }

   public List getNodes() {
      return this.a;
   }

   public MasterAgentX.Session getSession() {
      return this._session;
   }

   public String getContext() {
      return this.g;
   }

   public List getRegisteredOids() {
      return this.b;
   }

   public SnmpOid getSubtreeOid() {
      return this.c;
   }

   public boolean isInstance() {
      return this.d;
   }

   public int getTimeoutSecs() {
      return this.e;
   }

   public int getPriority() {
      return this.f;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 14;
               break;
            case 1:
               var10003 = 114;
               break;
            case 2:
               var10003 = 102;
               break;
            case 3:
               var10003 = 76;
               break;
            default:
               var10003 = 112;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
