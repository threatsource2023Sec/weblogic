package monfox.toolkit.snmp.mgr.usm;

import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpCounter;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.engine.SnmpBuffer;
import monfox.toolkit.snmp.engine.SnmpCoderException;
import monfox.toolkit.snmp.engine.SnmpContext;
import monfox.toolkit.snmp.engine.SnmpEngine;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.engine.SnmpErrorListener;
import monfox.toolkit.snmp.engine.SnmpMessage;
import monfox.toolkit.snmp.engine.SnmpMessageProcessor;
import monfox.toolkit.snmp.engine.SnmpMessageProfile;
import monfox.toolkit.snmp.engine.SnmpRequestPDU;
import monfox.toolkit.snmp.engine.TransportEntity;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.v3.SnmpSecurityCoderException;
import monfox.toolkit.snmp.v3.V3SnmpMessageModule;
import monfox.toolkit.snmp.v3.V3SnmpMessageParameters;
import monfox.toolkit.snmp.v3.usm.USMEngineInfo;
import monfox.toolkit.snmp.v3.usm.USMEngineMap;
import monfox.toolkit.snmp.v3.usm.USMLocalizedUserData;
import monfox.toolkit.snmp.v3.usm.USMNotInTimeWindowListener;
import monfox.toolkit.snmp.v3.usm.USMSecurityCoder;
import monfox.toolkit.snmp.v3.usm.USMSecurityDB;
import monfox.toolkit.snmp.v3.usm.USMUser;
import monfox.toolkit.snmp.v3.usm.USMUserTable;
import monfox.toolkit.snmp.v3.usm.ext.UsmUserSecurityExtension;

public class Usm {
   private V3SnmpMessageModule a;
   private UsmNotInTimeWindowListener b;
   private SnmpContext c;
   private SnmpEngine d;
   private SnmpEngineID e;
   private USMEngineInfo f;
   private USMEngineMap g;
   private USMSecurityCoder h;
   private USMUserTable i;
   private SnmpSession j;
   private UsmAdmin k;
   private Logger l;
   private static final SnmpOid m = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 1L, 1L, 0L});
   private static final SnmpOid n = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 1L, 2L, 0L});
   private static final SnmpOid o = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 1L, 3L, 0L});
   private static final SnmpOid p = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 1L, 4L, 0L});
   private static final SnmpOid q = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 1L, 5L, 0L});
   private static final SnmpOid r = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 1L, 6L, 0L});
   private static final SnmpOid s = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L});
   private static final SnmpOid t = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 1L});
   private static final SnmpOid u = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 2L});
   private static final SnmpOid v = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 3L});
   private static final SnmpOid w = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 4L});
   private static final SnmpOid x = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 5L});
   private static final SnmpOid y = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 6L});
   private static final SnmpOid z = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 7L});
   private static final SnmpOid A = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 8L});
   private static final SnmpOid B = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 9L});
   private static final SnmpOid C = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 10L});
   private static final SnmpOid D = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 11L});
   private static final SnmpOid E = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 12L});
   private static final SnmpOid F = SnmpOid.getStatic(new long[]{1L, 3L, 6L, 1L, 6L, 3L, 15L, 1L, 2L, 2L, 1L, 13L});
   private static final int G = 1;
   private static final int H = 2;
   private static final int I = 3;
   private static final int J = 4;
   private static final int K = 5;
   private static final int L = 6;
   public static boolean M;

   public Usm(SnmpEngine var1, SnmpSession var2) throws SnmpException {
      boolean var4 = M;
      super();
      this.b = null;
      this.c = null;
      this.l = Logger.getInstance(a("!82o(\u001f\u0012"));
      this.d = var1;
      this.j = var2;
      var1.addErrorListener(new ErrorHandler());
      if (this.l.isDebugEnabled()) {
         this.l.debug(a("\u000f\r\u0005\u0000)\u0005\u0011\u0007a.),3(\u0012\"\u007f\u00152\u0010"));
      }

      this.e = var1.getEngineID();
      if (this.l.isDebugEnabled()) {
         this.l.debug(a("ar`\u0004\u0013+6.$4\be`") + this.e);
      }

      this.c = new SnmpContext(var1.getEngineID());
      SnmpMessageProcessor var3 = var1.getMessageProcessor();
      this.a = (V3SnmpMessageModule)var3.getMessageModule(3);
      this.h = (USMSecurityCoder)this.a.getSecurityCoder();
      this.g = this.h.getEngineMap();
      this.h.setNotInTimeWindowListener(new LOCAL_USMNotInTimeWindowListener());
      if (this.e != null) {
         this.f = this.g.get(this.e);
      }

      if (this.l.isDebugEnabled()) {
         this.l.debug(a("ar`\u0004\u0013+6.$4\"9/{]") + this.f);
      }

      this.i = new USMUserTable();
      this.h.setDefaultUserTable(this.i);
      if (SnmpException.b) {
         M = !var4;
      }

   }

   public void setNotInTimeWindowListener(UsmNotInTimeWindowListener var1) {
      this.b = var1;
   }

   public UsmNotInTimeWindowListener getNotInTimeWindowListener() {
      return this.b;
   }

   public void setSecurityDB(USMSecurityDB var1) {
      this.h.setSecurityDB(var1);
   }

   public void setUserExtension(UsmUserSecurityExtension var1) {
      this.h.setUsmUserSecurityExtension(var1);
   }

   public USMUser addDefaultUser(String var1, String var2, String var3, int var4) throws SnmpValueException, NoSuchAlgorithmException {
      USMUser var5 = new USMUser(var1, var2, var3, var4);
      return this.addDefaultUser(var5);
   }

   public USMUser addDefaultUser(String var1, String var2, String var3, int var4, int var5) throws SnmpValueException, NoSuchAlgorithmException {
      USMUser var6 = new USMUser(var1, var2, var3, var4, var5);
      return this.addDefaultUser(var6);
   }

   public USMUser addDefaultUser(USMUser var1) throws SnmpValueException {
      if (this.l.isDebugEnabled()) {
         this.l.debug(a("-;$\u0005\u0018*>5-\t\u0019,%3G") + var1);
      }

      USMUser var2 = this.i.addUser(var1);
      return var2;
   }

   public void addDefaultUserTable(USMUserTable var1) {
      this.i.putAll(var1);
   }

   public USMUser getDefaultUser(String var1) {
      return this.i.getUser(var1);
   }

   public USMUser removeDefaultUser(String var1) throws SnmpValueException {
      return this.i.removeUser(var1);
   }

   public USMUserTable getDefaultUserTable() {
      return this.i;
   }

   public USMLocalizedUserData addUser(SnmpEngineID var1, String var2, String var3, String var4, int var5) throws SnmpValueException, NoSuchAlgorithmException {
      USMUser var6 = new USMUser(var2, var3, var4, var5);
      return this.addUser(var1, var6);
   }

   public USMLocalizedUserData addUser(SnmpEngineID var1, String var2, String var3, String var4, int var5, int var6) throws SnmpValueException, NoSuchAlgorithmException {
      USMUser var7 = new USMUser(var2, var3, var4, var5, var6);
      return this.addUser(var1, var7);
   }

   public USMLocalizedUserData addUser(SnmpEngineID var1, String var2, byte[] var3, byte[] var4, int var5, int var6) throws SnmpValueException, NoSuchAlgorithmException {
      USMLocalizedUserData var7 = new USMLocalizedUserData(var2, 3, var5, var6, var3, var4);
      this.a(var1, var7);
      return var7;
   }

   public USMLocalizedUserData addUser(SnmpEngineID var1, USMUser var2) throws SnmpValueException {
      this.l.debug(a("-;$\u0014\u000e)-h") + var1 + "," + var2 + ")");
      USMLocalizedUserData var3 = new USMLocalizedUserData(var2, var1);
      this.a(var1, var3);
      return var3;
   }

   public void addUserTable(SnmpEngineID var1, USMUserTable var2) {
      this.l.debug(a("-;$\u0014\u000e)-h") + var1 + "," + var2 + ")");
      USMEngineInfo var3 = this.g.get(var1);
      if (var3 == null) {
         var3 = new USMEngineInfo(var1);
         this.g.add(var3);
      }

      var3.addUserData(var2);
   }

   public USMLocalizedUserData getUser(SnmpEngineID var1, String var2) {
      USMEngineInfo var3 = this.g.get(var1);
      return var3 == null ? null : var3.getUserData(var2);
   }

   void a(SnmpEngineID var1, USMLocalizedUserData var2) throws SnmpValueException {
      USMEngineInfo var3 = this.g.get(var1);
      if (var3 == null) {
         var3 = new USMEngineInfo(var1);
         this.g.add(var3);
      }

      var3.addUserData(var2);
   }

   public USMLocalizedUserData removeUser(SnmpEngineID var1, String var2) throws SnmpValueException {
      USMEngineInfo var3 = this.g.get(var1);
      if (var3 == null) {
         return null;
      } else {
         USMLocalizedUserData var4 = var3.removeUserData(var2);
         return var4;
      }
   }

   public Iterator getUsers(SnmpEngineID var1) {
      USMEngineInfo var2 = this.g.get(var1);
      return var2 == null ? null : var2.getAllUserData();
   }

   public USMLocalizedUserData addLocalUser(String var1, String var2, String var3, int var4) throws SnmpValueException, NoSuchAlgorithmException {
      USMUser var5 = new USMUser(var1, var2, var3, var4);
      return this.addLocalUser(var5);
   }

   public USMLocalizedUserData addLocalUser(String var1, String var2, String var3, int var4, int var5) throws SnmpValueException, NoSuchAlgorithmException {
      USMUser var6 = new USMUser(var1, var2, var3, var4, var5);
      return this.addLocalUser(var6);
   }

   public USMLocalizedUserData addLocalUser(USMUser var1) throws SnmpValueException {
      this.l.debug(a("-;$\r\u0012/>,\u0014\u000e)-h") + var1 + ")");
      if (this.e != null && this.f != null) {
         USMLocalizedUserData var2 = new USMLocalizedUserData(var1, this.e);
         this.a(var2);
         return var2;
      } else {
         throw new SnmpValueException(a("\"04a\u001c\"\u007f!4\t$02(\t-+)7\u0018l:.&\u0014\":"));
      }
   }

   public void addLocalUserTable(USMUserTable var1) throws SnmpValueException {
      this.l.debug(a("-;$\r\u0012/>,\u0014\u000e)-\u0014 \u001f :h") + var1 + ")");
      if (this.e != null && this.f != null) {
         this.f.addUserData(var1);
      } else {
         throw new SnmpValueException(a("\"04a\u001c\"\u007f!4\t$02(\t-+)7\u0018l:.&\u0014\":"));
      }
   }

   void a(USMLocalizedUserData var1) throws SnmpValueException {
      if (this.e != null && this.f != null) {
         if (this.f.getUserData(var1.getName()) != null) {
            throw new SnmpValueException(a("9,%3]\">-$]-32$\u001c(&`(\u0013l*3$Gl") + var1.getName());
         } else {
            this.f.addUserData(var1);
         }
      } else {
         throw new SnmpValueException(a("\"04a\u001c\"\u007f!4\t$02(\t-+)7\u0018l:.&\u0014\":"));
      }
   }

   public USMLocalizedUserData getLocalUser(String var1) {
      return this.e != null && this.f != null ? this.f.getUserData(var1) : null;
   }

   public USMLocalizedUserData removeLocalUser(String var1) throws SnmpValueException {
      if (this.e != null && this.f != null) {
         USMLocalizedUserData var2 = this.f.removeUserData(var1);
         return var2 == null ? null : var2;
      } else {
         return null;
      }
   }

   public Iterator getLocalUsers() {
      return this.e != null && this.f != null ? this.f.getAllUserData() : null;
   }

   public USMEngineMap getEngineMap() {
      return this.g;
   }

   public SnmpVarBindList getReportVarBindList(int var1) {
      boolean var5 = M;
      SnmpOid var2;
      int var3;
      switch (var1) {
         case 2:
            var2 = m;
            var3 = this.a.getUnsupportedSecLevels();
            if (!var5) {
               break;
            }
         case 3:
            var2 = p;
            var3 = this.a.getUnknownEngineIDs();
            if (!var5) {
               break;
            }
         case 4:
            var2 = o;
            var3 = this.a.getUnknownUserNames();
            if (!var5) {
               break;
            }
         case 5:
            var2 = n;
            var3 = this.a.getNotInTimeWindows();
            if (!var5) {
               break;
            }
         case 6:
            var2 = q;
            var3 = this.a.getWrongDigests();
            if (!var5) {
               break;
            }
         case 7:
         case 8:
            var2 = r;
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

   public void processDiscovery(TransportEntity var1, SnmpMessage var2) {
      this.a(var2, var1);
   }

   public UsmAdmin getAdmin() {
      if (this.j == null) {
         return null;
      } else {
         if (this.k == null) {
            try {
               this.k = new UsmAdmin(this.j);
            } catch (SnmpException var2) {
               this.l.debug(a(")-2.\u000fl0.a\u001c(2)/]/-% \t%0."));
            }
         }

         return this.k;
      }
   }

   private void a(SnmpMessage var1, TransportEntity var2) {
      this.a(var2, var1.getMsgID(), this.getReportVarBindList(3), 0, (String)null);
   }

   private void a(TransportEntity var1, int var2, SnmpVarBindList var3, int var4, String var5) {
      if (this.l.isDebugEnabled()) {
         this.l.debug(a("?:.%/)//3\tv\u007f") + var1);
      }

      if (var3 != null) {
         SnmpRequestPDU var6 = new SnmpRequestPDU();
         var6.setRequestId(var2);
         var6.setType(168);
         var6.setVarBindList(var3);
         SnmpMessage var7 = new SnmpMessage();
         var7.setVersion(3);
         var7.setMsgID(var2);
         var7.setSnmpEngineID(this.d.getEngineID());
         var7.setData(var6);
         var7.setContext(this.c);
         SnmpMessageProfile var8 = new SnmpMessageProfile(3, 3, var4, var5);
         var7.setMessageProfile(var8);

         try {
            this.d.send(var7, var1);
         } catch (SnmpException var10) {
            if (this.l.isDebugEnabled()) {
               this.l.debug(a(")-2.\u000fl,%/\u0019%1'a\u000f)//3\t"), var10);
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
               var10003 = 76;
               break;
            case 1:
               var10003 = 95;
               break;
            case 2:
               var10003 = 64;
               break;
            case 3:
               var10003 = 65;
               break;
            default:
               var10003 = 125;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class LOCAL_USMNotInTimeWindowListener implements USMNotInTimeWindowListener {
      private LOCAL_USMNotInTimeWindowListener() {
      }

      public boolean handleNotInTimeWindow(boolean var1, SnmpEngineID var2, int var3, int var4, int var5, int var6, V3SnmpMessageParameters var7, byte[] var8) {
         if (Usm.this.b != null) {
            try {
               return Usm.this.b.handleNotInTimeWindow(new UsmTimelinessParameters(var1, var2, var3, var4, var5, var6, var7, var8));
            } catch (Exception var10) {
               Usm.this.l.error(a("\b\u0002\u001dD'M\u0019\u0001]:\u0006\u0015\u0006E2M\u0004\u0007Nu8\u0003\u0002e:\u00199\u0001\u007f<\u0000\u00158B;\t\u001f\u0018g<\u001e\u0004\nE0\u001f"), var10);
            }
         }

         return false;
      }

      // $FF: synthetic method
      LOCAL_USMNotInTimeWindowListener(Object var2) {
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
                  var10003 = 109;
                  break;
               case 1:
                  var10003 = 112;
                  break;
               case 2:
                  var10003 = 111;
                  break;
               case 3:
                  var10003 = 43;
                  break;
               default:
                  var10003 = 85;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   class ErrorHandler implements SnmpErrorListener {
      public void handleError(SnmpBuffer var1, TransportEntity var2, int var3, int var4, SnmpCoderException var5) {
         if (var4 != 0) {
            SnmpVarBindList var6 = Usm.this.getReportVarBindList(var3);
            int var7 = 0;
            String var8 = null;
            if (var5 instanceof SnmpSecurityCoderException) {
               SnmpSecurityCoderException var9 = (SnmpSecurityCoderException)var5;
               if (var9.getUserName() != null) {
                  var8 = new String(var9.getUserName());
               }

               var7 = var9.getSecurityLevel();
            }

            Usm.this.a(var2, var4, var6, var7, var8);
         }
      }
   }
}
