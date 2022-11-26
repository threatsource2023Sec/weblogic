package monfox.toolkit.snmp.agent.usm;

import java.util.Iterator;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpCounter;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpString;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.agent.SnmpAgent;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibTableRow;
import monfox.toolkit.snmp.agent.SnmpSecurityModel;
import monfox.toolkit.snmp.agent.tc.TestAndIncr;
import monfox.toolkit.snmp.engine.SnmpEngine;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.engine.SnmpMessageProcessor;
import monfox.toolkit.snmp.v3.V3SnmpMessageModule;
import monfox.toolkit.snmp.v3.usm.USMEngineInfo;
import monfox.toolkit.snmp.v3.usm.USMEngineMap;
import monfox.toolkit.snmp.v3.usm.USMLocalizedUserData;
import monfox.toolkit.snmp.v3.usm.USMSecurityCoder;
import monfox.toolkit.snmp.v3.usm.USMUser;
import monfox.toolkit.snmp.v3.usm.USMUserTable;

public class Usm extends SnmpSecurityModel {
   private V3SnmpMessageModule a;
   private UsmUserTable b;
   private SnmpEngineID c;
   private SnmpString d;
   private USMEngineInfo e;
   private USMEngineMap f;
   private USMUserTable g;
   private USMSecurityCoder h;
   private Logger i;
   private Listener j;
   public static final int USM_USER_ADDED = 1;
   public static final int USM_USER_REMOVED = 2;
   public static final int USM_LOCALIZED_AUTH_KEY_CHANGED = 3;
   public static final int USM_LOCALIZED_PRIV_KEY_CHANGED = 4;
   public static final SnmpOid usmStatsUnsupportedSecLevels = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 1L, 1L, 0L});
   public static final SnmpOid usmStatsNotInTimeWindows = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 1L, 2L, 0L});
   public static final SnmpOid usmStatsUnknownUserNames = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 1L, 3L, 0L});
   public static final SnmpOid usmStatsUnknownEngineIDs = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 1L, 4L, 0L});
   public static final SnmpOid usmStatsWrongDigests = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 1L, 5L, 0L});
   public static final SnmpOid usmStatsDecryptionErrors = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 1L, 6L, 0L});
   public static final SnmpOid usmUserTable = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L});
   public static final SnmpOid usmUserEngineID = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 1L});
   public static final SnmpOid usmUserName = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 2L});
   public static final SnmpOid usmUserSecurityName = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 3L});
   public static final SnmpOid usmUserCloneFrom = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 4L});
   public static final SnmpOid usmUserAuthProtocol = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 5L});
   public static final SnmpOid usmUserAuthKeyChange = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 6L});
   public static final SnmpOid usmUserOwnAuthKeyChange = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 7L});
   public static final SnmpOid usmUserPrivProtocol = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 8L});
   public static final SnmpOid usmUserPrivKeyChange = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 9L});
   public static final SnmpOid usmUserOwnPrivKeyChange = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 10L});
   public static final SnmpOid usmUserPublic = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 11L});
   public static final SnmpOid usmUserStorageType = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 12L});
   public static final SnmpOid usmUserRowStatus = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 13L});
   public static final SnmpOid usmUserSpinLock = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 1L});
   private static final int k = 1;
   private static final int l = 2;
   private static final int m = 3;
   private static final int n = 4;
   private static final int o = 5;
   private static final int p = 6;
   public static boolean q;

   void a(int var1, USMLocalizedUserData var2, SnmpMibTableRow var3) {
      Listener var4 = this.j;
      if (var4 != null) {
         try {
            var4.handleEvent(new Event(var1, var2, var3));
         } catch (Exception var6) {
            this.i.error(a("wD9/>gH%>A1A\"9\u000ftC.8[tU(/\u000beD$$"), var6);
         }
      }

   }

   public void addListener(Listener var1) {
      if (!monfox.toolkit.snmp.agent.usm.a.contains(this.j, var1)) {
         this.j = monfox.toolkit.snmp.agent.usm.a.add(this.j, var1);
      }

   }

   public void removeListener(Listener var1) {
      this.j = monfox.toolkit.snmp.agent.usm.a.remove(this.j, var1);
   }

   public Usm(SnmpAgent var1) throws SnmpException {
      boolean var5 = q;
      super();
      this.g = new USMUserTable();
      this.i = Logger.getInstance(a("D~\u0006"));
      SnmpEngine var2 = var1.getEngine();
      this.c = var2.getEngineID();
      this.d = new SnmpString(this.c.getValue());
      SnmpMessageProcessor var3 = var2.getMessageProcessor();
      this.a = (V3SnmpMessageModule)var3.getMessageModule(3);
      this.h = (USMSecurityCoder)this.a.getSecurityCoder();
      this.f = this.h.getEngineMap();
      this.e = this.f.get(this.c);
      this.b = new UsmUserTable(this, this.e);
      SnmpMib var4 = var1.getMib();
      var4.add(this.b, true);
      var4.add(new UsmStats(usmStatsUnsupportedSecLevels), true);
      var4.add(new UsmStats(usmStatsNotInTimeWindows), true);
      var4.add(new UsmStats(usmStatsUnknownUserNames), true);
      var4.add(new UsmStats(usmStatsUnknownEngineIDs), true);
      var4.add(new UsmStats(usmStatsWrongDigests), true);
      var4.add(new UsmStats(usmStatsDecryptionErrors), true);
      var4.add(new TestAndIncr(usmUserSpinLock), true);
      this.h.setDefaultUserTable(this.g);
      if (var5) {
         SnmpException.b = !SnmpException.b;
      }

   }

   public String getModelName() {
      return a("D^.8VsL8/\u001f1~.)\u000ecD?3[\\B//\u00171\u0005\u001e\u001968");
   }

   public USMSecurityCoder getSecurityCoder() {
      return this.h;
   }

   public UsmUserTable getUserTable() {
      return this.b;
   }

   public USMLocalizedUserData addUser(USMUser var1) throws SnmpMibException, SnmpValueException {
      USMLocalizedUserData var2 = new USMLocalizedUserData(var1, this.c);
      this.addUser(var2);
      if (this.g != null) {
         this.g.addUser(var1);
      }

      return var2;
   }

   public USMLocalizedUserData addUser(SnmpEngineID var1, USMUser var2) throws SnmpMibException, SnmpValueException {
      USMLocalizedUserData var3 = new USMLocalizedUserData(var2, var1);
      this.addUser(var1, var3);
      return var3;
   }

   public void addUser(USMLocalizedUserData var1) throws SnmpMibException, SnmpValueException {
      if (this.e.getUserData(var1.getName()) != null) {
         throw new SnmpMibException(a("d^.8[\u007fL&/[pA9/\u001auTk#\u00151X8/A1") + var1.getName());
      } else {
         this.e.addUserData(var1);
         if (this.b != null) {
            this.b.add(var1);
         }

      }
   }

   public void addUser(SnmpEngineID var1, USMLocalizedUserData var2) throws SnmpMibException, SnmpValueException {
      USMEngineInfo var3 = this.a(var1);
      if (var3.getUserData(var2.getName()) != null) {
         throw new SnmpMibException(a("d^.8[\u007fL&/[pA9/\u001auTk#\u00151X8/A1") + var2.getName());
      } else {
         var3.addUserData(var2);
         if (this.b != null) {
            this.b.add(var1, var2);
         }

      }
   }

   public USMLocalizedUserData getUser(String var1) {
      return this.e.getUserData(var1);
   }

   public USMLocalizedUserData getUser(SnmpEngineID var1, String var2) {
      USMEngineInfo var3 = this.a(var1);
      return var3.getUserData(var2);
   }

   public USMLocalizedUserData removeUser(String var1) throws SnmpMibException, SnmpValueException {
      USMLocalizedUserData var2 = this.e.removeUserData(var1);
      if (var2 == null) {
         return null;
      } else {
         if (this.b != null) {
            this.b.remove(var1);
         }

         if (this.g != null) {
            this.g.removeUser(var1);
         }

         return var2;
      }
   }

   public USMLocalizedUserData removeUser(SnmpEngineID var1, String var2) throws SnmpMibException, SnmpValueException {
      USMEngineInfo var3 = this.a(var1);
      USMLocalizedUserData var4 = var3.removeUserData(var2);
      if (var4 == null) {
         return null;
      } else {
         if (this.b != null) {
            this.b.remove(var1, var2);
         }

         return var4;
      }
   }

   public Iterator getUsers() {
      return this.e.getAllUserData();
   }

   public Iterator getUsers(SnmpEngineID var1) {
      USMEngineInfo var2 = this.a(var1);
      return var2.getAllUserData();
   }

   USMEngineInfo a(SnmpEngineID var1) {
      USMEngineInfo var2 = this.f.get(var1);
      if (var2 == null) {
         var2 = new USMEngineInfo(var1);
         this.f.add(var2);
      }

      return var2;
   }

   USMEngineInfo a(SnmpString var1) {
      if (var1.equals(this.d)) {
         return this.e;
      } else {
         SnmpEngineID var2 = new SnmpEngineID(var1.toByteArray());
         return this.a(var2);
      }
   }

   public SnmpVarBindList getReportVarBindList(int var1) {
      boolean var5 = q;
      SnmpOid var2;
      int var3;
      switch (var1) {
         case 2:
            var2 = usmStatsUnsupportedSecLevels;
            var3 = this.a.getUnsupportedSecLevels();
            if (!var5) {
               break;
            }
         case 3:
            var2 = usmStatsUnknownEngineIDs;
            var3 = this.a.getUnknownEngineIDs();
            if (!var5) {
               break;
            }
         case 4:
            var2 = usmStatsUnknownUserNames;
            var3 = this.a.getUnknownUserNames();
            if (!var5) {
               break;
            }
         case 5:
            var2 = usmStatsNotInTimeWindows;
            var3 = this.a.getNotInTimeWindows();
            if (!var5) {
               break;
            }
         case 6:
            var2 = usmStatsWrongDigests;
            var3 = this.a.getWrongDigests();
            if (!var5) {
               break;
            }
         case 7:
         case 8:
            var2 = usmStatsDecryptionErrors;
            var3 = this.a.getDecryptionErrors();
            if (!var5) {
               break;
            }
         default:
            var2 = null;
            var3 = 0;
      }

      if (var2 == null) {
         return null;
      } else {
         SnmpVarBindList var4 = new SnmpVarBindList();
         var4.add((SnmpOid)var2, (SnmpValue)(new SnmpCounter(var3)));
         return var4;
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
               var10003 = 17;
               break;
            case 1:
               var10003 = 45;
               break;
            case 2:
               var10003 = 75;
               break;
            case 3:
               var10003 = 74;
               break;
            default:
               var10003 = 123;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   class UsmStats extends SnmpMibLeaf {
      public UsmStats(SnmpOid var2) throws SnmpMibException {
         super(var2);
      }

      public SnmpValue getValue() {
         boolean var3 = Usm.q;
         int var1 = (int)this.getClassOid().getLast();
         int var2;
         switch (var1) {
            case 1:
               var2 = Usm.this.a.getUnsupportedSecLevels();
               if (!var3) {
                  break;
               }
            case 2:
               var2 = Usm.this.a.getNotInTimeWindows();
               if (!var3) {
                  break;
               }
            case 3:
               var2 = Usm.this.a.getUnknownUserNames();
               if (!var3) {
                  break;
               }
            case 4:
               var2 = Usm.this.a.getUnknownEngineIDs();
               if (!var3) {
                  break;
               }
            case 5:
               var2 = Usm.this.a.getWrongDigests();
               if (!var3) {
                  break;
               }
            case 6:
               var2 = Usm.this.a.getDecryptionErrors();
               if (!var3) {
                  break;
               }
            default:
               var2 = 0;
         }

         return new SnmpCounter(var2);
      }
   }

   public interface Listener {
      void handleEvent(Event var1);
   }

   public static class Event {
      private int a;
      private USMLocalizedUserData b;
      private SnmpMibTableRow c;

      Event(int var1, USMLocalizedUserData var2, SnmpMibTableRow var3) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
      }

      public int getEventType() {
         return this.a;
      }

      public USMLocalizedUserData getUserData() {
         return this.b;
      }

      public SnmpMibTableRow getUsmUserTableRow() {
         return this.c;
      }

      private String a(int var1) {
         switch (var1) {
            case 1:
               return a("=$%\u0005+;2:\u0005?,3-\u001e");
            case 2:
               return a("=$%\u0005+;2:\u0005,-:'\f;,");
            case 3:
               return a("=$%\u00052'4)\u0016722,\u0005?=# \u00055-.7\u00196)9/\u001f:");
            case 4:
               return a("=$%\u00052'4)\u0016722,\u0005.:>>\u00055-.7\u00196)9/\u001f:");
            default:
               return a("=9#\u00141?97\u001f(-9<");
         }
      }

      public String toString() {
         StringBuffer var1 = new StringBuffer();
         var1.append(a("bZE\u0001^=\u0004\u0005t;\u001e\u0012\u0006.^5WEwSEZEwSEZEwSEZEwSEZEwSEZEwSEZEwSE"));
         var1.append("\n");
         var1.append(a("bWHz\u001b\u001e\u0012\u0006.*\u0011\u0007\r`^")).append(this.a(this.a));
         var1.append("\n");
         var1.append(a("bWHz\u0012\u0007\u0014\t6:\t\u0003\t`^")).append(this.b);
         var1.append(a("bZEwSEZEwSEZEwSEZEwSEZEwSEZEwSEZEwSEZEwSEZEwSEZEwSE"));
         var1.append("\n");
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
                  var10003 = 104;
                  break;
               case 1:
                  var10003 = 119;
                  break;
               case 2:
                  var10003 = 104;
                  break;
               case 3:
                  var10003 = 90;
                  break;
               default:
                  var10003 = 126;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
