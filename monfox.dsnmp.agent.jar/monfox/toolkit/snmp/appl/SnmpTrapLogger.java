package monfox.toolkit.snmp.appl;

import gnu.regexp.RE;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.ListIterator;
import java.util.PropertyResourceBundle;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.engine.TransportEntity;
import monfox.toolkit.snmp.mgr.SnmpInformListener;
import monfox.toolkit.snmp.mgr.SnmpParameters;
import monfox.toolkit.snmp.mgr.SnmpPendingInform;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.mgr.SnmpSessionConfig;
import monfox.toolkit.snmp.mgr.SnmpTrapListener;
import monfox.toolkit.snmp.util.SimpleQueue;
import monfox.toolkit.snmp.v3.usm.USMUserTable;

public class SnmpTrapLogger extends a implements SnmpTrapListener, SnmpInformListener {
   private int h = 30000;
   private SimpleQueue a = new SimpleQueue(50000);
   private String f = a("\u000f\u0003Qr\bEN\f)A\t\tWa\u000eThWf\u000e)9");
   private SimpleDateFormat b = new SimpleDateFormat(a("9~ar\u0017\u0010\u0013d\u001aI\u0019^\u0016!\u0000"));
   private String g = a("\u0000AM\"]\u0018\\K");
   private List c = null;
   private Logger J = null;
   private static final String e = "$Id: SnmpTrapLogger.java,v 1.7 2007/03/23 04:12:00 sking Exp $";
   public static boolean i;

   public static void main(String[] var0) {
      try {
         SnmpTrapLogger var1 = new SnmpTrapLogger(var0);
         var1.g();
      } catch (Throwable var2) {
         h();
         System.out.println("\n");
         System.out.println(a("T\u0013\f\u0017!&|~hS") + var2.getMessage());
         System.out.println("\n");
      }

   }

   SnmpTrapLogger(String[] var1) throws Throwable {
      super(var1, "", new String[0], a("\u0005Uo"), new String[]{a("\u0005FI'\u0016\u0007ZV7"), a("\u0012Z@7\u001d\u0015^I"), a("\u0017\\B4\u001a\u0013")});
   }

   void g() {
      boolean var9 = i;

      try {
         if (this.d.hasFlag("?")) {
            h();
            System.exit(1);
         }

         this.e();
         this.J = Logger.getInstance(a("']A\"'\u0006R\\\u001e\u001c\u0013TI "));
         this.C = this.d.getIntOption(a("\u0004\t\\=\u0001\u0000"), 162);
         this.g = this.d.getOption(a("\u0012\tJ;\u001f\u0011]M?\u0016"), a("\u0000AM\"]\u0018\\K"));
         String var1 = this.d.getOption(a("7\tO=\u001d\u0012ZK"), (String)null);
         if (var1 != null) {
            this.b(var1);
         }

         this.h = this.d.getIntOption(a("\u0005\t]'\u0016\u0001V"), 30000);
         this.a = new SimpleQueue();
         Thread var2 = new Thread(new Worker());
         var2.setPriority(3);
         var2.setDaemon(true);
         var2.start();
         SnmpParameters var3 = this.a(true);
         SnmpSessionConfig var4 = SnmpSession.newConfig();
         var4.setTransportType(this.E);
         var4.setLocalPort(this.C);
         SnmpSession var5 = null;
         if (this.x != null) {
            var4.setMetadata(SnmpFramework.getMetadata());
            var4.setEngineID(this.x);
            var5 = new SnmpSession(var4);
            if (this.y >= 0 && this.z >= 0) {
               var5.getSnmpEngine().setEngineBoots(this.y);
               var5.getSnmpEngine().setEngineTime(this.z);
            }
         } else {
            var5 = new SnmpSession(var4);
         }

         if (var3.getUserTable() != null) {
            var5.getUsm().addDefaultUserTable((USMUserTable)var3.getUserTable());
         }

         var5.addTrapListener(this);
         var5.addInformListener(this);
         System.out.println(a("8Z_&\u0016\u001aZB5S\u001b]\f\"\u001c\u0006G\u0016") + this.C);
         Runtime var6 = Runtime.getRuntime();

         while(true) {
            Thread.sleep(5000L);

            while(System.getProperty(a("\u0019VA")) != null) {
               var6.gc();
               long var7 = var6.totalMemory() - var6.freeMemory();
               System.out.println(a("9var&'rk\u0017IT") + var7 / 1000L + "K");
               if (!var9 && !var9) {
                  break;
               }
            }
         }
      } catch (Exception var10) {
         var10.printStackTrace();
         System.out.println(a("1a~\u001d!N\u0013") + var10);
      }
   }

   private void b(String var1) {
      boolean var8 = i;
      this.c = new Vector();

      try {
         FileInputStream var2 = new FileInputStream(var1);
         PropertyResourceBundle var3 = new PropertyResourceBundle(var2);
         Enumeration var4 = var3.getKeys();

         while(var4.hasMoreElements()) {
            String var5 = (String)var4.nextElement();
            String var6 = var3.getString(var5);

            label27: {
               try {
                  this.c.add(new LogPattern(var5, new RE(var6)));
               } catch (Exception var9) {
                  System.err.println(a("1a~\u001d!N\u0013N3\u0017TCM&\u0007\u0011ABrT") + var6 + "'");
                  break label27;
               }

               if (var8) {
                  break;
               }
            }

            if (var8) {
               break;
            }
         }
      } catch (IOException var10) {
         System.err.println(a("1a~\u001d!N\u0013O3\u001d\u001a\\Xr\u001f\u001bRHr\u001f\u001bT\u00011\u001c\u001aUE5S\u0012Z@7SS") + var1 + "'");
      }

   }

   private String a(String var1, String var2) {
      boolean var5 = i;
      if (this.c != null) {
         ListIterator var3 = this.c.listIterator();

         boolean var10000;
         LogPattern var4;
         while(true) {
            if (var3.hasNext()) {
               var4 = (LogPattern)var3.next();
               var10000 = var4.pattern.isMatch(var2);
               if (var5) {
                  break;
               }

               if (var10000) {
                  return var4.filename;
               }

               if (!var5) {
                  continue;
               }
            }

            var3 = this.c.listIterator();
            var10000 = var3.hasNext();
            break;
         }

         while(var10000) {
            var4 = (LogPattern)var3.next();
            if (var4.pattern.isMatch(var1)) {
               return var4.filename;
            }

            if (var5) {
               break;
            }

            var10000 = var3.hasNext();
         }
      }

      return this.g;
   }

   public void handleTrap(monfox.toolkit.snmp.mgr.SnmpTrap var1) {
      try {
         if (this.a.size() < this.h) {
            this.a.pushBack(var1);
            if (!i) {
               return;
            }
         }

         System.err.println(a("1a~\u001d!N\u0013x \u0012\u0004\u0013n'\u0015\u0012V^r<\u0002V^4\u001f\u001bD\f\t\u001e\u0015Kn'\u0015\u0012V^\u0001\u001a\u000eV\u0011") + this.h + a("TG^3\u0003\u0007n\u0002X") + a("T\u0013\frST\u0013x=S\u0007VXr\u0012T[E5\u001b\u0011A\f?\u0012\f\u0013Z3\u001f\u0001V\u0000r\u0006\u0007V\f&\u001b\u0011\u0013\u000b\u007f\u0002S\u0013C\"\u0007\u001d\\B"));
      } catch (InterruptedException var3) {
      }

   }

   private void a(monfox.toolkit.snmp.mgr.SnmpTrap var1) {
      boolean var15 = i;
      String var2 = this.b.format(new Date());
      String var3 = a("\u0001]G<\u001c\u0003]");
      String var4 = a("D\u001d\u001c|CZ\u0003");
      String var5 = a("\u0007]A\"\u0007\u0006R\\");
      String var6 = a("\u0001]G<\u001c\u0003]\u0001&\u0001\u0015C");
      long var7 = 0L;
      TransportEntity var9 = var1.getSource();

      try {
         var3 = var9.getAddress().getHostName();
         if (var3 == null) {
            var3 = var9.getAddress().getHostAddress();
         }
      } catch (Exception var16) {
         var3 = a("\u0001]G<\u001c\u0003]");
      }

      label39: {
         if (var1.isGenericTrap()) {
            int var10 = var1.getGenericTrap();
            var6 = Snmp.genericTrapToString(var10);
            if (!var15) {
               break label39;
            }
         }

         SnmpOid var17 = var1.getTrapOid();
         var6 = var17.toString();
      }

      var7 = var1.getSysUpTime();
      SnmpVarBindList var18 = var1.getObjectValues();
      StringBuffer var11 = new StringBuffer();
      Enumeration var12 = var18.getVarBinds();

      StringBuffer var10000;
      while(true) {
         if (var12.hasMoreElements()) {
            SnmpVarBind var13 = (SnmpVarBind)var12.nextElement();
            var11.append(var13.getOid());
            var11.append("=");
            var10000 = var11.append(var13.getValueString());
            if (var15) {
               break;
            }

            if (var12.hasMoreElements()) {
               var11.append(",");
            }

            if (!var15) {
               continue;
            }
         }

         var10000 = new StringBuffer();
         break;
      }

      StringBuffer var19 = var10000;
      var19.append(var2).append(" ");
      var19.append(var3).append(" ");
      var19.append(var5).append(":");
      var19.append(var6).append(" ");
      var19.append(var7).append(" ");
      var19.append(var11);
      var19.append("\n");
      String var14 = this.a(var4, var3);
      this.b(var14, var19.toString());
   }

   public void handleInform(SnmpPendingInform var1) {
      boolean var16 = i;

      try {
         var1.sendResponse();
      } catch (Exception var18) {
         this.J.error(a("1A^=\u0001TZBr\u0000\u0011]H;\u001d\u0013\u0013E<\u0015\u001bAAr\u0001\u0011@\\=\u001d\u0007V\u0016r"), var18);
      }

      String var2 = this.b.format(new Date());
      String var3 = a("\u0001]G<\u001c\u0003]");
      String var4 = a("D\u001d\u001c|CZ\u0003");
      String var5 = a("\u0007]A\"\u001a\u001aUC \u001e");
      String var6 = a("\u0001]G<\u001c\u0003]\u0001;\u001d\u0012\\^?");
      long var7 = 0L;
      TransportEntity var9 = var1.getSource();

      try {
         var3 = var9.getAddress().getHostName();
         if (var3 == null) {
            var3 = var9.getAddress().getHostAddress();
         }
      } catch (Exception var17) {
         var3 = a("\u0001]G<\u001c\u0003]");
      }

      SnmpOid var10 = var1.getTrapOid();
      var6 = var10.toString();
      var7 = (long)var1.getSysUpTime();
      SnmpVarBindList var11 = var1.getRequestObjectValues();
      StringBuffer var12 = new StringBuffer();
      Enumeration var13 = var11.getVarBinds();

      StringBuffer var10000;
      while(true) {
         if (var13.hasMoreElements()) {
            SnmpVarBind var14 = (SnmpVarBind)var13.nextElement();
            var12.append(var14.getOid());
            var12.append("=");
            var10000 = var12.append(var14.getValueString());
            if (var16) {
               break;
            }

            if (var13.hasMoreElements()) {
               var12.append(",");
            }

            if (!var16) {
               continue;
            }
         }

         var10000 = new StringBuffer();
         break;
      }

      StringBuffer var19 = var10000;
      var19.append(var2).append(" ");
      var19.append(var3).append(" ");
      var19.append(var5).append(":");
      var19.append(var6).append(" ");
      var19.append(var7).append(" ");
      var19.append(var12);
      var19.append("\n");
      String var15 = this.a(var4, var3);
      this.b(var15, var19.toString());
   }

   private void b(String var1, String var2) {
      if (var1 == null) {
         var1 = this.g;
      }

      try {
         FileOutputStream var3 = new FileOutputStream(var1, true);
         var3.write(var2.getBytes());
         var3.close();
      } catch (IOException var4) {
         System.err.println(a("1a~\u001d!N\u0013O3\u001d\u001a\\Xr\u001c\u0004VBr\u001f\u001bT\f4\u001a\u0018V\fu") + var1);
         System.err.println(a("T\u0013\f\u007f^N\u0013") + var2);
      }

   }

   static void h() {
      System.out.println(a("~\u0013\f\u0007 5tiXy~\u0013\frSTYM$\u0012T`B?\u0003 AM\"?\u001bTK7\u0001Th\u0001m\u000f\u001bCX;\u001c\u001a@qXy~\u0013\f\u00166'p~\u001b# zc\u001cy~\u0013\frST\u007fE!\u0007\u0011]\f4\u001c\u0006\u0013X \u0012\u0004@\f=\u001dTGD7S\u0007CI1\u001a\u0012ZI6S\u0004\\^&S\u0015]Hr\u001f\u001bT\f&\u001b\u0011^\u0002XyT\u0013c\u0002'=|b\u0001y~\u0013\frST\u001e\\\t\u001c\u0006GqrST\u0013\frST\u000f\\=\u0001\u0000\r\frST\u0013\fhS\u0018\\O3\u001fTG^3\u0003T_E!\u0007\u0011]\f\"\u001c\u0006G\frST\u0013\frST\u0013\fr(E\u0005\u001e\u000fyT\u0013\frSYUw;\u001f\u0011]M?\u0016)\u0013\frSH_C5\u0015\u001d_IlST\u0013\u0016r\u001d\u0015^Ir\u001c\u0012\u0013H7\u0015\u0015F@&S\u0018\\Kr\u0015\u001d_IrST\u0013\frST\u0013\f\t\u0007\u0006R\\|\u001f\u001bTqXST\u0013\fr^7hC<\u0015\u001dTJ;\u001f\u0011n\frO\u0017UK\u007f\u0015\u001d_IlST\t\f>\u001c\u0013\u0013O=\u001d\u0012ZKr\u0003\u0006\\\\7\u0001\u0000ZI!S\u0012Z@7ST\u0013\frST\u0013w<\u001c\u001aVqXST\u0013\fr^\u0005hY7\u0006\u0011n\frST\u0013\frO\u0005FI'\u0016Y@E(\u0016J\t\f&\u0001\u0015C\f0\u0006\u0006@Xr\u0011\u0001UJ7\u0001T@E(\u0016T\u001b\u000f&\u0001\u0015C_{ST\u0013waCD\u0003\u001c\u000fyT\u0013\frSYGO\"ST\u0013\frST\u0013\frST\u0013\frST\u0013\frST\u0013\u0016r\u001f\u001d@X7\u001dTUC S p|r\u001d\u001bG\f\u00077$\u0013\frST\u0013\frST\u0013\f\t&0cqXy~\u0013\fr=;gihS\u0000[Ir\u001f\u001bT\f1\u001c\u001aUE5S\u0012Z@7S\u0007[C'\u001f\u0010\u0013N7S\u0015\u0013f3\u0005\u0015\u0013\\ \u001c\u0004V^&\u001a\u0011@\f4\u001a\u0018V\f%\u001b\u0011AIXST\u0013\frST\u0013\f&\u001b\u0011\u0013\\ \u001c\u0004V^&\nT]M?\u0016\u0007\u0013M \u0016T_C5\u0015\u001d_Ir\u001d\u0015^I!S\u0015]Hr\u0007\u001cV\f$\u0012\u0018FIr\u001a\u00079\frST\u0013\frSTR\f\u000063vtr\u0003\u0015GX7\u0001\u001a\u0013[:\u001a\u0017[\f6\u0016\u0012ZB7\u0000TGD7S\u001c\\_&\\\u0015WH \u0016\u0007@I!S\u0003[E1\u001b~\u0013\frST\u0013\frS\u0013\\\f&\u001cTGD3\u0007T_C5\u0015\u001d_I|S2\\^r\u0016\fRA\"\u001f\u0011\t&XST\u0013\frST\u0013\frST^C<\u0015\u001bK\u0002>\u001c\u0013\u000e\u001dkAZ\u0002\u001aj]G\u001d\u0002xyT\u0013\frST\u0013\frST\u0013@=\u0010\u0015_D=\u0000\u0000\u001d@=\u0014I_C1\u0012\u0018[C!\u0007Z\u0019&XST`b\u001f#\u0002\u0000\f\u001d# zc\u001c ~9") + c() + "\n" + "\n" + a("T\u0013\frS:|x\u0017ITe\u001fr\u0016\u001aTE<\u0016TAI>\u0012\u0000VHr\u0003\u0015AM?\u0016\u0000V^!S\\\u001eI~^.\u001a\f3\u0003\u0004_Ur\u0007\u001b\u0013X:\u0016T_C1\u0012\u0018\u0013&") + a("T\u0013\frST\u0013\frSTRY&\u001b\u001bAE&\u0012\u0000ZZ7S\u0011]K;\u001d\u0011\u0013J=\u0001TAI1\u0016\u001dEE<\u0014TzB4\u001c\u0006^\f?\u0016\u0007@M5\u0016\u0007\u001d\f\u0007 99") + a("T\u0013\frST\u0013\frSTF_7\u0001TZB4\u001c\u0006^M&\u001a\u001b]\f3\u0003\u0004_E7\u0000TGCr\u0016\u001dGD7\u0001TGD7S\u0018\\O3\u001fT\\^r\u0001\u0011^C&\u0016~") + a("T\u0013\frST\u0013\frSTf\u007f\u001fS\u0001@I \u0000Z9"));
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 116;
               break;
            case 1:
               var10003 = 51;
               break;
            case 2:
               var10003 = 44;
               break;
            case 3:
               var10003 = 82;
               break;
            default:
               var10003 = 115;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private class Worker implements Runnable {
      private Worker() {
      }

      public void run() {
         try {
            while(true) {
               monfox.toolkit.snmp.mgr.SnmpTrap var1 = (monfox.toolkit.snmp.mgr.SnmpTrap)SnmpTrapLogger.this.a.popFront();
               SnmpTrapLogger.this.a(var1);
            }
         } catch (InterruptedException var2) {
         }
      }

      // $FF: synthetic method
      Worker(Object var2) {
         this();
      }
   }

   private class LogPattern {
      public String filename;
      public RE pattern;

      LogPattern(String var2, RE var3) {
         this.filename = var2;
         this.pattern = var3;
      }
   }
}
