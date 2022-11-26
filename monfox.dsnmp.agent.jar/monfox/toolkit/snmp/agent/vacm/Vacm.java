package monfox.toolkit.snmp.agent.vacm;

import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.agent.SnmpAccessControlModel;
import monfox.toolkit.snmp.agent.SnmpAccessPolicy;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpPendingIndication;
import monfox.toolkit.snmp.agent.tc.TestAndIncr;
import monfox.toolkit.snmp.engine.SnmpContext;
import monfox.toolkit.snmp.engine.SnmpSecurityParameters;
import monfox.toolkit.snmp.v3.V3SnmpMessageParameters;

public class Vacm extends SnmpAccessControlModel {
   private SnmpAgent a;
   private VacmContextTable b;
   private VacmSecurityToGroupTable c;
   private VacmAccessTable d;
   private VacmViewTreeFamilyTable e;
   private SnmpCommunityTable f;
   private int g;
   private int h;
   private Logger i;
   public static final SnmpOid vacmContextTable = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 16L, 1L, 1L});
   public static final SnmpOid vacmSecurityToGroupTable = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 16L, 1L, 2L});
   public static final SnmpOid vacmAccessTable = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 16L, 1L, 4L});
   public static final SnmpOid vacmViewTreeFamilyTable = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 16L, 1L, 5L, 2L});
   public static final SnmpOid snmpCommunityTable = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 18L, 1L, 1L});
   public static final SnmpOid vacmViewSpinLock = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 16L, 1L, 5L, 1L});
   public static boolean j;

   public Vacm(SnmpAgent var1) throws SnmpException {
      boolean var3 = j;
      super();
      this.g = 0;
      this.h = 0;
      this.i = Logger.getInstance(a(">p%W"));
      this.a = var1;
      this.b = new VacmContextTable();
      this.c = new VacmSecurityToGroupTable();
      this.d = new VacmAccessTable();
      this.e = new VacmViewTreeFamilyTable();
      this.f = new SnmpCommunityTable(var1.getEngine().getEngineID());
      SnmpMib var2 = var1.getMib();
      var2.add(this.c);
      var2.add(this.d);
      var2.add(this.e);
      var2.add(this.f);
      var2.add(new TestAndIncr(vacmViewSpinLock), true);
      var2.add(this.b);
      if (SnmpException.b) {
         j = !var3;
      }

   }

   public String getModelName() {
      return a(">X\u0003m:\nP\u0015\u007fsHp\u0005yr\u001bBFYx\u0006E\u0014u{H|\t~r\u0004\u0011NLV+|O");
   }

   public boolean supportsVersion(int var1) {
      return var1 == 0 || var1 == 1 || var1 == 3;
   }

   public SnmpAccessPolicy getAccessPolicy(SnmpPendingIndication var1) {
      return var1.getVersion() == 3 ? this.a(var1) : this.b(var1);
   }

   private SnmpAccessPolicy a(SnmpPendingIndication var1) {
      byte var2 = 3;
      SnmpSecurityParameters var3 = var1.getRequest().getSecurityParameters();
      String var4 = new String(var3.getSecurityName());
      V3SnmpMessageParameters var5 = (V3SnmpMessageParameters)var1.getRequest().getMessageParameters();
      byte var6 = var5.getFlags();
      int var7 = var1.getRequestType();
      String var8 = null;
      SnmpContext var9 = var1.getRequest().getContext();
      if (var9 != null) {
         var8 = var9.getContextName();
      }

      return this.a(var2, var4, var6, var7, var8);
   }

   private SnmpAccessPolicy b(SnmpPendingIndication var1) {
      byte var2;
      label30: {
         if (var1.getVersion() == 0) {
            var2 = 1;
            if (!j) {
               break label30;
            }
         }

         var2 = 2;
      }

      String var3 = null;
      if (var1.getCommunity() != null) {
         var3 = var1.getCommunity();
      } else {
         var3 = new String(var1.getRequest().getData().getCommunity());
      }

      if (this.i.isDebugEnabled()) {
         this.i.debug(a("\u000fT\u0012n~\u0006VF[t\u000bT\u0015iG\u0007]\u000fynHW\th7\u000b^\u000bwb\u0006X\u0012c-H") + var3);
      }

      String var4 = this.f.getSecurityName(var3);
      if (var4 == null && var3 != null && var1.getCommunity() != null && !var3.equals(var1.getCommunity())) {
         var4 = this.f.getSecurityName(var1.getCommunity());
      }

      byte var5 = 0;
      int var6 = var1.getRequestType();
      String var7 = var1.getContextName();
      return this.a(var2, var4, var5, var6, var7);
   }

   private SnmpAccessPolicy a(int var1, String var2, int var3, int var4, String var5) {
      if (this.i.isDebugEnabled()) {
         this.i.debug(a("\u000fT\u0012[t\u000bT\u0015iG\u0007]\u000fynR\u0011\u0015\u007ft%^\u0002\u007f{U") + var1 + a("D\u0011\u0015\u007ft&P\u000b\u007f*") + var2 + a("DG\u000f\u007f`<H\u0016\u007f*") + var4 + a("DR\u0012bY\t\\\u0003'") + var5);
      }

      if (var5 == null) {
         return null;
      } else {
         if (var5.length() != 0) {
            ++this.g;
            if (!this.b.hasContextName(var5)) {
               this.i.comms(a("\u0006^Fib\u000bYFyx\u0006E\u0003bcR\u0011") + var5);
               return null;
            }
         }

         String var6 = this.c.getGroupName(var1, var2);
         if (var6 == null) {
            return null;
         } else {
            String var7 = this.d.getViewName(var6, var5, var1, var3, var4);
            if (this.i.isDebugEnabled()) {
               this.i.debug(a("=B\u000ftpHg\u000f\u007f`R\u0011") + var7);
            }

            Vector var8 = this.e.getView(var7);
            return var8 != null && var8.size() != 0 ? new VacmAccessPolicy(var7, var8) : null;
         }
      }
   }

   public SnmpCommunityTable getCommunityTable() {
      return this.f;
   }

   public VacmContextTable getContextTable() {
      return this.b;
   }

   public VacmSecurityToGroupTable getSecurityToGroupTable() {
      return this.c;
   }

   public VacmAccessTable getAccessTable() {
      return this.d;
   }

   public VacmViewTreeFamilyTable getViewTreeFamilyTable() {
      return this.e;
   }

   public int getSnmpUnavailableContexts() {
      return this.h;
   }

   public int getSnmpUnknownContexts() {
      return this.g;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 104;
               break;
            case 1:
               var10003 = 49;
               break;
            case 2:
               var10003 = 102;
               break;
            case 3:
               var10003 = 26;
               break;
            default:
               var10003 = 23;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
