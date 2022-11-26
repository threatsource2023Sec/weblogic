package monfox.toolkit.snmp.metadata.builder;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpModule;
import monfox.toolkit.snmp.metadata.SnmpModuleIdentityInfo;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.metadata.SnmpTableEntryInfo;
import monfox.toolkit.snmp.metadata.SnmpTableInfo;
import monfox.toolkit.snmp.metadata.SnmpTypeInfo;

public class MibApi {
   private SnmpMetadata a;
   private static SimpleDateFormat b = new SimpleDateFormat(e("\u001ai\njL\u0007X\u000fJEDJ`"));
   private Logger c;

   public MibApi() {
      this(SnmpFramework.getMetadata());
   }

   public MibApi(SnmpMetadata var1) {
      this.c = Logger.getInstance(e("'C\tjx"), e(".Y\u0005\ni3Y"), e(".y%fX\n"));
      this.a = var1;
   }

   public void createModule(String var1, String var2, SnmpOid var3, String var4, String var5, String var6) throws SnmpValueException, MibException {
      SnmpModule var7 = null;

      try {
         var7 = this.a.getModule(var1);
      } catch (Exception var11) {
      }

      if (var7 != null) {
         throw new MibException(e("\u000e\u007f#RD\u00060&KZ\u0006q#^\b\u0006h.T\\\u0010*g") + var1);
      } else {
         StringBuffer var8 = new StringBuffer();
         b.format(new Date(), var8, new FieldPosition(0));
         String var9 = var8.toString();
         SnmpModuleIdentityInfo var10 = new SnmpModuleIdentityInfo(var9, var5, var6, var4);
         this.a.add(var1, var3.toNumericString(), var2, var10);
         var7 = this.a.getModule(var1);
         if (var7 == null) {
            throw new MibException(e("\u0016~&ED\u000603H\b\u0000b\"F\\\u00060*HL\u0016|\"\u000b\b\r\u007f3\u0007N\fe)C"));
         }
      }
   }

   public Table createTable(String var1, String var2, String var3) throws SnmpValueException, MibException {
      SnmpModule var4 = null;

      try {
         var4 = this.a.getModule(var1);
      } catch (Exception var14) {
      }

      if (var4 == null) {
         throw new MibException(e("\r\u007fgT]\u0000xgJG\u0007e+B\u0012C") + var1);
      } else {
         SnmpModuleIdentityInfo var5 = var4.getIdentity();
         if (var5 == null) {
            throw new MibException(e("\r\u007fgjg'E\u000bb\u0005*T\u0002i|*D\u001e\u0007L\u0006v.IA\u0006tgAG\u00110*HL\u0016|\"\u0007\u0012C") + var1);
         } else {
            SnmpOid var6 = var5.getOid();
            var2 = a(var2);
            SnmpOid var7 = (SnmpOid)var6.clone();
            var7.append(1L);
            String var8 = var5.getName() + e("7q%KM\u0010");
            SnmpOidInfo var9 = var4.getOidInfo(var8);
            if (var9 == null) {
               this.a.addOid(var1, var7.toNumericString(), var8);
            }

            long[] var10 = d(var2);
            SnmpOid var11 = (SnmpOid)var7.clone();
            var11.append(var10);
            String var12 = var2 + e("N_.C");
            SnmpOidInfo var13 = var4.getOidInfo(var12);
            if (var13 == null) {
               this.a.addOid(var1, var11.toNumericString(), var12);
            }

            var11.append(1L);
            return this.createTable(var1, var2, var11, var3);
         }
      }
   }

   public Table createTable(String var1, String var2, SnmpOid var3, String var4) throws SnmpValueException, MibException {
      SnmpModule var5 = null;

      try {
         var5 = this.a.getModule(var1);
      } catch (Exception var7) {
      }

      if (var5 == null) {
         throw new MibException(e("\r\u007fgT]\u0000xgJG\u0007e+B\u0012C") + var1);
      } else {
         Table var6 = new Table(var1, var2, var3, var4);
         return var6;
      }
   }

   public Scalar createScalar(String var1, String var2, String var3, String var4, String var5) throws SnmpValueException, MibException {
      boolean var16 = MibException.a;
      SnmpModule var6 = null;

      try {
         var6 = this.a.getModule(var1);
      } catch (Exception var17) {
      }

      if (var6 == null) {
         throw new MibException(e("\r\u007fgT]\u0000xgJG\u0007e+B\u0012C") + var1);
      } else {
         SnmpModuleIdentityInfo var7 = var6.getIdentity();
         if (var7 == null) {
            throw new MibException(e("\r\u007fgjg'E\u000bb\u0005*T\u0002i|*V\u001e\u0007L\u0006v.IA\u0006tgAG\u00110*HL\u0016|\"\u0007\u0012C") + var1);
         } else {
            SnmpOid var8 = var7.getOid();
            var2 = a(var2);
            SnmpOid var9 = (SnmpOid)var8.clone();
            var9.append(2L);
            String var10 = var7.getName() + e("0s&KI\u0011c");
            SnmpOidInfo var11 = var6.getOidInfo(var10);
            if (var11 == null) {
               this.a.addOid(var1, var9.toNumericString(), var10);
            }

            long[] var12 = d(var2);
            SnmpOid var13 = (SnmpOid)var9.clone();
            var13.append(var12);
            String var14 = var2 + e("N_.C");
            SnmpOidInfo var15 = var6.getOidInfo(var14);
            if (var15 == null) {
               this.a.addOid(var1, var13.toNumericString(), var14);
            }

            var13.append(1L);
            Scalar var10000 = this.createScalar(var1, var2, var13, var3, var4, var5);
            if (SnmpException.b) {
               MibException.a = !var16;
            }

            return var10000;
         }
      }
   }

   public Scalar createScalar(String var1, String var2, SnmpOid var3, String var4, String var5, String var6) throws SnmpValueException, MibException {
      SnmpModule var7 = null;

      try {
         var7 = this.a.getModule(var1);
      } catch (Exception var9) {
      }

      if (var7 == null) {
         throw new MibException(e("\r\u007fgT]\u0000xgJG\u0007e+B\u0012C") + var1);
      } else {
         Scalar var8 = new Scalar(var1, var2, var3, var4, var5, var6);
         return var8;
      }
   }

   static String a(String var0) {
      var0 = c(var0);
      String var1 = Character.toLowerCase(var0.charAt(0)) + var0.substring(1);
      return var1;
   }

   static String b(String var0) {
      var0 = c(var0);
      String var1 = Character.toUpperCase(var0.charAt(0)) + var0.substring(1);
      return var1;
   }

   static String c(String var0) {
      boolean var5 = MibException.a;
      StringBuffer var1 = new StringBuffer();
      int var2 = var0.length();
      int var3 = 0;

      String var10000;
      while(true) {
         if (var3 < var2) {
            var10000 = var0;
            if (var5) {
               break;
            }

            label35: {
               char var4 = var0.charAt(var3);
               if (Character.isDigit(var4) || Character.isLetter(var4) || var4 == '-') {
                  var1.append(var4);
                  if (!var5) {
                     break label35;
                  }
               }

               var1.append('-');
            }

            ++var3;
            if (!var5) {
               continue;
            }
         }

         var10000 = var1.toString();
         break;
      }

      return var10000;
   }

   static long[] d(String var0) {
      boolean var5 = MibException.a;
      byte[] var1 = new byte[0];
      if (var0.endsWith(e("7q%KM"))) {
         var1 = new byte[]{2};
         var0 = var0.substring(0, var0.length() - 5);
      }

      Object var2 = null;

      byte[] var7;
      try {
         var7 = var0.getBytes(e("6D\u0001\n\u0010"));
      } catch (Exception var6) {
         var7 = var0.getBytes();
      }

      long[] var3 = new long[var7.length + var1.length];
      int var4 = 0;

      while(true) {
         if (var4 < var7.length) {
            var3[var4] = (long)(var7[var4] & 255);
            ++var4;
            if (!var5 || !var5) {
               continue;
            }
            break;
         }

         var4 = 0;
         break;
      }

      long[] var10000;
      while(true) {
         if (var4 < var1.length) {
            var10000 = var3;
            if (var5) {
               break;
            }

            var3[var4 + var7.length] = (long)(var1[var4] & 255);
            ++var4;
            if (!var5) {
               continue;
            }
         }

         var10000 = var3;
         break;
      }

      return var10000;
   }

   static {
      b.setTimeZone(TimeZone.getTimeZone(e("$]\u0013")));
   }

   private static String e(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 99;
               break;
            case 1:
               var10003 = 16;
               break;
            case 2:
               var10003 = 71;
               break;
            case 3:
               var10003 = 39;
               break;
            default:
               var10003 = 40;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   public class Scalar {
      private SnmpOid a;
      private String b;
      private String c;
      private String d;
      private int e;
      private SnmpTypeInfo f;
      private SnmpObjectInfo g;
      private int h;
      private boolean i;

      Scalar(String var2, String var3, SnmpOid var4, String var5, String var6, String var7) throws MibParameterException {
         label30: {
            super();
            this.i = false;
            this.b = var2;
            this.c = MibApi.a(var3);
            this.a = var4;
            this.d = var7;
            if (var5.indexOf(58) > 0) {
               try {
                  SnmpTypeInfo var10 = MibApi.this.a.getType(var5);
                  if (var10 != null) {
                     this.e = var10.getType();
                     this.f = var10;
                  }
                  break label30;
               } catch (Exception var9) {
                  if (!MibException.a) {
                     break label30;
                  }
               }
            }

            int var8 = SnmpValue.stringToType(var5);
            if (var8 == -1) {
               throw new MibParameterException(a("s\u0011F6_s\u001b\u0010#Jj\u001a\nw") + var5);
            }

            this.e = var8;
            this.f = null;
         }

         this.h = SnmpObjectInfo.stringToAccess(var6);
         if (this.h == -1) {
            throw new MibParameterException(a("s\u0011F6_s\u001b\u00106Py\u001aC$\t:") + var6);
         }
      }

      public void exportToMetadata() throws MibParameterException {
         if (this.g != null) {
            throw new MibParameterException(a("i\u001cQ;Rh__5Y\u007f\u001cDwRv\rU6Wc_U/Cu\rD2W"));
         } else {
            this.g = MibApi.this.a.addObject(this.b, this.a.toNumericString(), this.getName(), this.getType(), this.getAccess());
            if (this.getDescription() != null) {
               this.g.setDescription(this.getDescription());
            }

         }
      }

      public String getName() {
         return this.c;
      }

      public int getType() {
         return this.e;
      }

      public SnmpTypeInfo getTypeInfo() {
         return this.f;
      }

      public int getAccess() {
         return this.h;
      }

      public String getDescription() {
         return this.d;
      }

      public SnmpOid getOid() {
         return this.a;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 26;
                  break;
               case 1:
                  var10003 = 127;
                  break;
               case 2:
                  var10003 = 48;
                  break;
               case 3:
                  var10003 = 87;
                  break;
               default:
                  var10003 = 51;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public class Column {
      private Table a;
      private String b;
      private String c;
      private int d;
      private SnmpTypeInfo e;
      private int f;
      private boolean g;

      Column(Table var2, String var3, String var4, String var5, boolean var6, String var7) throws MibParameterException {
         label30: {
            super();
            this.g = false;
            this.a = var2;
            this.b = var3;
            this.c = var7;
            if (var4.indexOf(58) > 0) {
               try {
                  SnmpTypeInfo var10 = MibApi.this.a.getType(var4);
                  if (var10 != null) {
                     this.d = var10.getType();
                     this.e = var10;
                  }
                  break label30;
               } catch (Exception var9) {
                  if (!MibException.a) {
                     break label30;
                  }
               }
            }

            int var8 = SnmpValue.stringToType(var4);
            if (var8 == -1) {
               throw new MibParameterException(a("QKdAzQA2ToH@(\u0000") + var4);
            }

            this.d = var8;
            this.e = null;
         }

         this.f = SnmpObjectInfo.stringToAccess(var5);
         if (this.f == -1) {
            throw new MibParameterException(a("QKdAzQA2Au[@aS,\u0018") + var5);
         } else {
            this.g = var6;
         }
      }

      public String getName() {
         return this.b;
      }

      public int getType() {
         return this.d;
      }

      public SnmpTypeInfo getTypeInfo() {
         return this.e;
      }

      public int getAccess() {
         return this.f;
      }

      public boolean isIndex() {
         return this.g;
      }

      public String getDescription() {
         return this.c;
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
                  var10003 = 37;
                  break;
               case 2:
                  var10003 = 18;
                  break;
               case 3:
                  var10003 = 32;
                  break;
               default:
                  var10003 = 22;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   public class Table {
      private String a = null;
      private String b = null;
      private SnmpOid c = null;
      private String d = null;
      private SnmpTableInfo e = null;
      private int f = 0;
      private List g = new Vector();

      Table(String var2, String var3, SnmpOid var4, String var5) {
         this.a = var2;
         this.b = MibApi.a(var3);
         this.c = var4;
         this.d = var5;
      }

      public Column addColumn(String var1, String var2, String var3, String var4, boolean var5) throws MibParameterException {
         String var6 = MibApi.a(var1);
         Column var7 = MibApi.this.new Column(this, var6, var2, var3, var5, var4);
         this.g.add(var7);
         if (var5) {
            ++this.f;
         }

         return var7;
      }

      public void exportToMetadata() throws MibParameterException {
         boolean var9 = MibException.a;
         if (this.e != null) {
            throw new MibParameterException(a("muw!\u00069uy?\u0006xplm\u0006adz?\u0017|p"));
         } else if (this.f == 0) {
            throw new MibParameterException(a("w{5$\r}qm(\u00109ge(\u0000pr|(\u0007"));
         } else {
            SnmpObjectInfo[] var1 = new SnmpObjectInfo[this.f];
            SnmpObjectInfo[] var2 = new SnmpObjectInfo[this.g.size()];
            SnmpOid var3 = (SnmpOid)this.c.clone();
            var3.append(1L);
            int var4 = 0;
            int var5 = 0;

            Table var10000;
            while(true) {
               if (var5 < this.g.size()) {
                  SnmpOid var6 = (SnmpOid)var3.clone();
                  var6.append((long)(var5 + 1));
                  Column var7 = (Column)this.g.get(var5);
                  var10000 = this;
                  if (var9) {
                     break;
                  }

                  SnmpObjectInfo var8 = MibApi.this.a.addObject(this.a, var6.toNumericString(), var7.getName(), var7.getType(), var7.getAccess());
                  if (var7.getTypeInfo() != null) {
                     var8.setTypeInfo(var7.getTypeInfo());
                  }

                  if (var7.getDescription() != null) {
                     var8.setDescription(var7.getDescription());
                  }

                  var2[var5] = var8;
                  if (var7.isIndex()) {
                     var1[var4++] = var8;
                  }

                  ++var5;
                  if (!var9) {
                     continue;
                  }

                  SnmpException.b = !SnmpException.b;
               }

               var10000 = this;
               break;
            }

            String var10 = var10000.b;
            if (this.b.endsWith(a("Muw!\u0006"))) {
               var10 = this.b.substring(0, this.b.length() - 5);
            }

            var10 = var10 + a("\\za?\u001a");
            SnmpTableEntryInfo var11 = new SnmpTableEntryInfo(var2, var1);
            MibApi.this.a.add(this.a, var3.toNumericString(), var10, (SnmpOidInfo)var11);
            SnmpTableInfo var12 = new SnmpTableInfo(var2, var1, var11);
            if (this.getDescription() != null) {
               var12.setDescription(this.getDescription());
            }

            MibApi.this.a.add(this.a, this.c.toNumericString(), this.b, var12);
            int var13 = 0;

            while(true) {
               if (var13 < var2.length) {
                  var2[var13].setColumnar(true);
                  var2[var13].setTableInfo(var12);
                  ++var13;
                  if (var9) {
                     break;
                  }

                  if (!var9) {
                     continue;
                  }
               }

               this.e = var12;
               break;
            }

         }
      }

      public List getColumns() {
         return this.g;
      }

      public String getModuleName() {
         return this.a;
      }

      public String getName() {
         return this.b;
      }

      public SnmpOid getTableOid() {
         return this.c;
      }

      public String getDescription() {
         return this.d;
      }

      private static String a(String var0) {
         char[] var1 = var0.toCharArray();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            char var10002 = var1[var3];
            byte var10003;
            switch (var3 % 5) {
               case 0:
                  var10003 = 25;
                  break;
               case 1:
                  var10003 = 20;
                  break;
               case 2:
                  var10003 = 21;
                  break;
               case 3:
                  var10003 = 77;
                  break;
               default:
                  var10003 = 99;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
