package monfox.toolkit.snmp.appl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;
import monfox.log.Logger;
import monfox.log.SimpleLogger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.engine.SnmpBuffer;
import monfox.toolkit.snmp.engine.SnmpContext;
import monfox.toolkit.snmp.engine.SnmpEngine;
import monfox.toolkit.snmp.engine.SnmpEngineID;
import monfox.toolkit.snmp.engine.SnmpMessage;
import monfox.toolkit.snmp.engine.TransportEntity;
import monfox.toolkit.snmp.metadata.Result;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.xml.SnmpMetadataRepository;
import monfox.toolkit.snmp.mgr.SnmpErrorException;
import monfox.toolkit.snmp.mgr.SnmpParameters;
import monfox.toolkit.snmp.mgr.SnmpPeer;
import monfox.toolkit.snmp.mgr.SnmpSession;
import monfox.toolkit.snmp.mgr.SnmpSessionConfig;
import monfox.toolkit.snmp.util.ByteFormatter;
import monfox.toolkit.snmp.util.Commandline;
import monfox.toolkit.snmp.util.FileLocator;
import monfox.toolkit.snmp.util.FormatUtil;
import monfox.toolkit.snmp.v3.usm.USMEngineInfo;
import monfox.toolkit.snmp.v3.usm.USMEngineMap;
import monfox.toolkit.snmp.v3.usm.USMUserTable;

class a {
   private static final String a;
   SnmpPeer b;
   SnmpSession c;
   Commandline d;
   String[] e;
   String f;
   String g;
   int h;
   String i;
   String j;
   String k;
   String l;
   String m;
   String n;
   String o;
   String p;
   String q;
   String r;
   String s;
   String t;
   String u;
   String v;
   SnmpEngineID w;
   SnmpEngineID x;
   int y;
   int z;
   int A;
   int B;
   int C;
   int D;
   int E;
   boolean F;
   boolean G;
   boolean H;
   SnmpMetadataRepository I;
   Logger J;
   String K;
   Properties L;

   a(String[] var1) throws Throwable {
      this(var1, "", new String[0], "", new String[0]);
   }

   a(String[] var1, String var2, String[] var3, String var4, String[] var5) throws Throwable {
      boolean var22 = SnmpTrapLogger.i;
      super();
      this.b = null;
      this.c = null;
      this.d = null;
      this.e = null;
      this.f = null;
      this.g = null;
      this.h = 0;
      this.i = null;
      this.j = null;
      this.k = null;
      this.l = null;
      this.m = null;
      this.n = null;
      this.o = null;
      this.p = null;
      this.q = null;
      this.r = null;
      this.s = null;
      this.t = null;
      this.u = null;
      this.v = null;
      this.w = null;
      this.x = null;
      this.y = -1;
      this.z = -1;
      this.A = 3;
      this.B = 3000;
      this.C = 161;
      this.D = -1;
      this.E = 1;
      this.F = false;
      this.G = false;
      this.H = false;
      this.I = null;
      this.J = null;
      this.K = a("O\u0011$dx");
      this.L = null;
      this.a();
      String var6 = this.getClass().getName();
      int var7 = var6.lastIndexOf(46);
      if (var7 > 0) {
         this.K = var6.substring(var7 + 1);
      }

      String var8 = a("}\u0006u") + var2;
      String[] var9 = new String[]{a("]S"), a("]P)"), a("]P"), a("]Q"), a("^\u0016,1"), a("^\u0011+nm"), a("O\u0017'y"), a("G\u000b9}"), a("[\t>z"), a("_\u0001:"), a("H\r'yi_"), a("E\r)fe[\u0003>"), a("B\f<hdB\u0006\u0003MED\u0006/")};
      String[] var10 = new String[var3.length + var9.length];
      int var11 = 0;

      while(true) {
         if (var11 < var9.length) {
            var10[var11] = var9[var11];
            ++var11;
            if (var22) {
               break;
            }

            if (!var22) {
               continue;
            }

            SnmpException.b = !SnmpException.b;
         }

         var11 = 0;
         break;
      }

      while(var11 < var3.length) {
         var10[var11 + var9.length] = var3[var11];
         ++var11;
         if (var22) {
            break;
         }
      }

      String var26 = a("N'\u0010aeY\u0016:jEj:&g}J\u001a<F") + var4;
      String[] var12 = new String[]{a("C\r9}"), a("F\u0007>hlJ\u0016+"), a("Y\u0007>{aN\u0011"), a("N\f)flB\f-"), a("_\u000b'lg^\u0016"), a("[\r8}"), a("H\r'd}E\u000b>p"), a("F\u000b(z"), a("F\u000b(maY\u0011"), a("^\u0011/{"), a("H\u00103y|D"), a("F\u00032\u007fjX"), a("G\r-"), a("^\u0006:"), a("_\u0001:")};
      String[] var13 = new String[var5.length + var12.length];
      int var14 = 0;

      while(true) {
         if (var14 < var12.length) {
            var13[var14] = var12[var14];
            ++var14;
            if (!var22 || !var22) {
               continue;
            }
            break;
         }

         var14 = 0;
         break;
      }

      while(true) {
         if (var14 < var5.length) {
            var13[var14 + var12.length] = var5[var14];
            ++var14;
            if (var22) {
               break;
            }

            if (!var22) {
               continue;
            }
         }

         this.d = new Commandline(var1, var8, var26, var10, var13);
         break;
      }

      label209: {
         if (this.d.hasFlag(a("]S"))) {
            this.h = 0;
            if (!var22) {
               break label209;
            }
         }

         if (this.d.hasFlag(a("]P)"))) {
            this.h = 1;
            if (!var22) {
               break label209;
            }
         }

         if (this.d.hasFlag(a("]P"))) {
            this.h = 1;
            if (!var22) {
               break label209;
            }
         }

         if (this.d.hasFlag(a("]Q"))) {
            this.h = 3;
         }
      }

      label159: {
         if (this.d.hasFlag(a("E\r)fe[\u0003>"))) {
            SnmpFramework.isCompatibilityMode(false);
            if (!var22) {
               break label159;
            }
         }

         SnmpFramework.isCompatibilityMode(true);
      }

      if (this.d.hasFlag(a("B\f<hdB\u0006\u0003MED\u0006/"))) {
         SnmpSession.getDefaultConfig().setProcessInvalidResponseMessageIDs(true);
      }

      label153: {
         if (this.d.hasFlag(a("^\u0016,1"))) {
            SnmpFramework.getCharacterSetSupport().setDefaultCharSet(a("~6\f$0"));
            SnmpFramework.setUTF8Encoding(a("~6\f$0"));
            if (!var22) {
               break label153;
            }
         }

         SnmpFramework.setUTF8Encoding((String)null);
      }

      String var27 = this.d.getOption(a("N\f)flB\f-"));
      if (var27 != null) {
         SnmpFramework.getCharacterSetSupport().setDefaultCharSet(var27);
         SnmpFramework.setUTF8Encoding(var27);
      }

      label210: {
         String var15 = this.d.getOption("O", "i");
         if (var15.indexOf(110) >= 0) {
            SnmpFramework.setDefaultOidFormat(1);
            if (!var22) {
               break label210;
            }
         }

         if (var15.indexOf(108) >= 0) {
            SnmpFramework.setDefaultOidFormat(2);
            if (!var22) {
               break label210;
            }
         }

         SnmpFramework.setDefaultOidFormat(3);
      }

      String var16 = this.d.getOption("v", (String)null);
      if (var16 != null) {
         label211: {
            if (var16.equals("1")) {
               this.h = 0;
               if (!var22) {
                  break label211;
               }
            }

            if (var16.equals("2")) {
               this.h = 1;
               if (!var22) {
                  break label211;
               }
            }

            if (var16.equals(a("\u0019\u0001"))) {
               this.h = 1;
               if (!var22) {
                  break label211;
               }
            }

            if (var16.equals("3")) {
               this.h = 3;
            }
         }
      }

      label124: {
         this.G = this.d.hasFlag(a("G\u000b9}"));
         this.H = this.d.hasFlag(a("[\t>z"));
         SimpleLogger.Provider var17;
         if (this.d.hasOption(a("G\r-"))) {
            this.F = true;
            var17 = new SimpleLogger.Provider(this.d.getOption(a("G\r-")));
            var17.setAppTimeLogged(true);
            Logger.setProvider(var17);
            if (!var22) {
               break label124;
            }
         }

         if (this.d.hasFlag(a("OX.|e["))) {
            this.F = true;
            var17 = new SimpleLogger.Provider(a("x\u001b9}mFL%||"));
            var17.setAppTimeLogged(true);
            Logger.setProvider(var17);
         }
      }

      this.J = Logger.getInstance(a("x\f'yKF\u0006"));
      int var28 = 161;

      try {
         var28 = Integer.parseInt(this.L.getProperty(a("[\r8}"), a("\u001aT{")));
      } catch (Exception var25) {
      }

      int var18 = 3000;

      try {
         var18 = Integer.parseInt(this.L.getProperty(a("_\u000b'lg^\u0016"), a("\u0018Rz9")));
      } catch (Exception var24) {
      }

      int var19 = 3000;

      try {
         var19 = Integer.parseInt(this.L.getProperty(a("Y\u0007>{aN\u0011"), "3"));
      } catch (Exception var23) {
      }

      String var20 = this.L.getProperty(a("H\r'd}E\u000b>p"), a("[\u0017(eaH"));
      String var21 = this.L.getProperty(a("C\r9}"), a("G\r)hdC\r9}"));
      this.g = this.d.getOption(a("CX\"f{_"), var21);
      this.i = this.d.getOption(a("F\u0007>hlJ\u0016+"), (String)null);
      this.f = this.d.getOption(a("HX)feF\u0017$`|R"), var20);
      this.A = this.d.getIntOption(a("YX8l|Y\u000b/z"), var19);
      this.B = this.d.getIntOption(a("_X>`eN\r?}"), var18);
      this.C = this.d.getIntOption(a("[X:fz_"), var28);
      this.E = this.d.hasFlag(a("_\u0001:")) ? 2 : 1;
      this.D = this.d.getIntOption(a("F\u00032\u007fjX"), -1);
      this.l = this.d.getOption(a("^X?zmY"), (String)null);
      this.j = this.d.getOption(a("FX'`jX"), a("x,\u0007Y~\u0019O\u0007@J\u0011+\f$Eb p]K{O\u0007@J\u0011") + this.L.getProperty(a("F\u000b(z"), ""));
      this.k = this.d.getOption(a("fX'`jO\u000b8z"), this.L.getProperty(a("F\u000b(maY\u0011"), (String)null));
      this.n = this.d.getOption("A", (String)null);
      this.o = this.d.getOption("a", a("f&\u007f"));
      this.p = this.d.getOption("x", a("o'\u0019"));
      this.q = this.d.getOption("X", (String)null);
      this.m = this.d.getOption("l", (String)null);
      this.v = this.d.getOption("n", "");
      this.r = this.d.getOption(a("H\u00103y|D"), (String)null);
      this.t = this.d.getOption("E", (String)null);
      this.u = this.d.getOption("e", (String)null);
      this.s = this.d.getOption("Z", (String)null);
      if (this.l != null || this.n != null || this.q != null || this.m != null || this.t != null || this.u != null || this.s != null) {
         this.h = 3;
      }

      this.e = this.d.params;
      this.d();
   }

   void a(SnmpVarBindList var1) {
      this.a("", var1);
   }

   void a() {
      this.L = new Properties();
      FileLocator var1 = new FileLocator(a);
      InputStream var2 = null;

      try {
         var2 = var1.getInputStream(this.K + a("\u0005\u0001%gn"));
      } catch (Exception var6) {
      }

      if (var2 == null) {
         try {
            var2 = var1.getInputStream(a("O\u0011$dx\u0005\u0001%gn"));
         } catch (Exception var5) {
         }
      }

      if (var2 != null) {
         try {
            this.L.load(var2);
         } catch (Exception var4) {
            this.J.error(a("N\u00108fz\u000b\u000b$)dD\u0003.`fLB:{g[\u00078}aN\u0011joaG\u0007"));
         }
      }

   }

   void a(String var1, SnmpVarBindList var2) {
      if (var2 != null) {
         Enumeration var3 = var2.getVarBinds();

         while(var3.hasMoreElements()) {
            SnmpVarBind var4 = (SnmpVarBind)var3.nextElement();
            System.out.println(var1 + var4.getOid() + "=" + var4.getFormattedString());
            if (SnmpTrapLogger.i) {
               break;
            }
         }
      }

   }

   void a(SnmpErrorException var1) {
      this.J.debug(var1);
      System.out.println(a("!B\u0019ge[B\u000f{zD\u0010j[mH\u0007#\u007fmOXj"));
      System.out.println(a("\u000bBj){_\u0003>|{\u0011B") + var1.getErrorString());
      System.out.println(a("\u000bBj)aE\u0006/q2\u000b") + var1.getErrorIndex());
      System.out.println(a("\u000bBj)aE\u0006/q2\u000b") + var1.getErrorIndex());
      System.out.println("");
   }

   void a(SnmpErrorException var1, SnmpVarBindList var2) {
      this.J.debug(var1);
      System.out.println(a("!B\u0019ge[B\u000f{zD\u0010j[mH\u0007#\u007fmOXj"));
      System.out.println(a("\u000bBj){_\u0003>|{\u0011B") + var1.getErrorString());
      System.out.println(a("\u000bBj)aE\u0006/q2\u000b") + var1.getErrorIndex());
      if (var2 != null && var2.size() >= var1.getErrorIndex() && var1.getErrorIndex() > 0) {
         int var3 = var1.getErrorIndex() - 1;
         System.out.println(a("\u000bBj\u007fiY\u0000#gl\u0011B") + var2.get(var3));
      }

      System.out.println("");
   }

   static String b() {
      return a("\u000bBj)(\u0006\u0014{u~\u00199)Tt]Qj)(\u000bBj)(\u000bBj)2\u000b\u0011$dx\u000b\u0014/{{B\r$)(\u000bBj)(\u000bBj)(\u000bBj)(\u000bBj)(p\u0014{T\u0002\u000bBj)(\u0006\u0001\u0011feF\u0017$`|R?j5kD\u000f'|fB\u0016372\u000b\u0011$dx\u000b\u0001%de^\f#}q\u000b\u0016%)}X\u0007j)(\u000bBj)(\u000bBj)(p\u0012?kdB\u0001\u0017\u0003\u0002\u000bBj)(\u0006\n\u0011f{_?j)(\u000bBj5`D\u0011>7(\u000bBj)2\u000b\u0011$dx\u000b\u0003-lf_B\"f{_Bj)(\u000bBj)(\u000bBj)(\u000bBj)(p\u000e%jiG\n%z|vhj)(\u000bBgySD\u0010>T(\u000bBj)(\u0017\u0012%{|\u0015Bj)(\u000bXjzfF\u0012jhoN\f>)xD\u0010>)(\u000bBj)(\u000bBj)(\u000bBj)(\u000bB\u00118>\u001a?@)(\u000bBj$zp\u0007>{aN\u0011\u0017)(\u000b^8l|Y\u000b/z6\u000bBp)+\u000b\r,)zN\u00168`mXBj)(\u000bBj)(\u000bBj)(\u000bBj)(\u000bBjR;vhj)(\u000bBg}SB\u000f/f}_?j)(\u0017\u000f#edB\u0011t)(\u000bXjdmX\u0011+nm\u000b\u0016#dmD\u0017>)aEB'`dG\u000b9)(\u000bBj)(\u000bB\u0011:8\u001bR\u0017\u0003(\u000bBj)%F\u00032\u007fjXBj)(\u000bBvdiS=<k{\u0015Bj3(F\u00032)+\u000b\r,)~J\u0010(`fO\u0011j`f\u000b\u0003jzaE\u0005&l(Y\u0007;'SE\rgdiS?@\u0003(\u000bBj)%F\u0007>hlJ\u0016+)(\u000bBvoaG\u0007$heN\\j3(F\u0007>hlJ\u0016+)nB\u000e/)|DB&fiOBj)(\u000bBj)(\u000bBj)SF\u000b($:vhj)(\u000bBgdSB\u00009T(\u000bBj)(\u0017\u000f#k%G\u000b9}6\u000bXjeaX\u0016jfn\u000b/\u0003K{\u000b\u0016%)dD\u0003.)nY\r')eB\u0000.`zXB\u0011daIOxT\u0002\u000bBj)(\u000bBj)(\u000bBj)(\u000bBj)(\u000bBj)(\u000bBj)(\u000bJ.ln\u0011B\u0019GE{\u0014x$Eb p@N\u0006/\u0003K2\u007f!\u001a$Eb c\u0003(\u000bBj)%f\u001e'`jO\u000b8z(\u000bBvmaYO:h|C\\j3(O\u000b8lk_\r8`mXB%o([\u0010/jgF\u0012#emOB\u0007@JXBj)SO\u0007,h}G\u0016\u0017\u0003(\u000bBj)%G\u000b9}(\u000bBj)(\u000bBj)(\u000bBj)(\u000bBj3(G\u000b9}(J\u0014+`dJ\u0000&l(f+\bz(\u000bBj)(\u000bBj)(\u000bBj)SM\u0003&zmvh@)(\u000bBj$dD\u0005j)(\u000bBj)(\u000b^&foM\u000b&l6\u000bBp)dD\u0005,`dNB>f(X\u0016%{m\u000b\u0006/k}LB%||[\u0017>)(\u000bBjRfD\f/T\u0002\u000bBj)(\u0006\u0006\u0011|e[?j)(\u000bBj)(\u000bBj)(\u000bBj)2\u000b\u0006?dx\u000b\u0006/k}LB#gnDB>f(X\u0016.f}_Bj)(\u000bBj)(p\r,oU!Bj)(\u000bBj)(\u000bBj)(\u000bBj)(\u000bBj)(\u000bBj)(\u000bBbgg_\u0007p)\u007fB\u000e&)fD\u0016j~gY\tj~a_\nj$dD\u0005c\u0003\u0002\u000bBj)(\u0006\u0012!}{\u000bBj)(\u000bBj)(\u000bBj)(\u000bBj)2\u000b\u0006#zxG\u00033)lJ\u0016+)xJ\u0001!l|XBj)(\u000bBj)(\u000bBj)(p\r,oU!Bj)(\u000bO\u0005)g^\u0016%y|XBj)(\u000bBj)(\u000bBj)(\u0011B.`{[\u000e+p(D\u0017>y}_B%y|B\r$z(\u000bBj)(\u000bBj)(\u000b9#T\u0002\u000bBj)(\u000bBj)(\u000bBj)(\u000bBj)(\u000bBj)(\u000bBj)(\u000b\fp)xY\u000b$}(d+\u000ez(B\fjg}F\u00078`k\u000b\u0004%{eJ\u0016@)(\u000bBj)(\u000bBj)(\u000bBj)(\u000bBj)(\u000bBj)(\u000bBj)d\u0011B:{aE\u0016jFAo\u0011j~a_\nj{mX\r&\u007fmOB&hjN\u000e9\u0003(\u000bBj)(\u000bBj)(\u000bBj)(\u000bBj)(\u000bBj)(\u000bBj)(BXjyzB\f>)Gb&9)\u007fB\u0016\")nD\u0010'h|_\u0007.)aE\u0006/qmXh@)(\u000bBj$}_\u0004r)(\u000bBj)(\u000bBj)(\u000bBj)(\u000bBp){N\u0016jmmM\u0003?e|\u000b\u0001\"hz\u000b\u0007$jgO\u000b$n(_\rj\\\\mZjRgM\u0004\u0017\u0003(\u000bBj)%N\f)flB\f-)4N\f)flB\f-7(\u000bBj3(X\u0007>)lN\u0004+|d_B)aiYB/gkD\u0006#go\u000bBj)(\u000bBj)S[\u000e+}nD\u0010'T\u0002\u000bBj)(\u0006\u0016)y(\u000bBj)(\u000bBj)(\u000bBj)(\u000bBj)2\u000b\u00179l(\u007f!\u001a)zJ\u0016\"lz\u000b\u0016\"hf\u000b7\u000eY(\u000bBj)(\u000bBj)(p\u0004+e{N?@\u0003(\u000bBj)%E\r)fe[\u0003>)(\u000bBj)(\u000bBj)(\u000bBj3(O\u000b9hjG\u0007jjgF\u0012+}aI\u000b&`|RB'flNBj)(\u000bBj)SN\f+kdN\u0006\u0017\u0003\u0002\u000bBj)(\u0006\u000b$\u007fiG\u000b.@Lf\r.l(\u000bBj)(\u000bBj)2\u000b\u0003>}mF\u0012>)|DB:{gH\u00079z(I\u0003.)eX\u0005\u0003M{\u000bBj)(p\u0006#ziI\u000e/mU!hj)(\u000bB\u0004F\\nXjPg^B'hq\u000b\u000b$jd^\u0006/)i\u000bE.zfF\u0012djgE\u0004m)nB\u000e/)aEB3f}YB)eiX\u0011:h|CBjfz!Bj)(\u000bBj)(\u000bB,`dN\u00113z|N\u000fjjgE\u0016+`fB\f-)lN\u0004+|d_B<hd^\u00079)nD\u0010j}`NB,fdG\r=`fLX@\u0003(\u000bBj)(\u000bBj)(\u000bBjdaI\u0011w5eB\u0000geaX\u0016t)\u0002\u000bBj)(\u000bBj)(\u000bBj)eB\u0000.`zX_vmaYO:h|C\\j\u0003(\u000bBj)(\u000bBj)(\u000bBj{m_\u0010#l{\u0016^8l|Y\u000b/z6\u000bhj)(\u000bBj)(\u000bBj)(\u000b\u0016#dmD\u0017>44_\u000b'lg^\u0016gdaG\u000e#z6\u000bhj)(\u000bBj)(\u000bBj)(\u000b\n%z|\u0016^.lnJ\u0017&}%C\r9}6\u000bhj)(\u000bBj)(\u000bBj)(\u000b\u0012%{|\u0016^.lnJ\u0017&}%[\r8}6\u000bhj)(\u000bBj)(\u000bBj)(\u000b\u0001%de^\f#}q\u0016^.lnJ\u0017&}%H\r'd}E\u000b>p6\u000bh@)(\u000bBj)(\u000bBj)\\C\u000b9)/O\u0011$dx\u0005\u0001%gn\fB,`dNB'hq\u000b\u0000/)dD\u0001+}mOB#g(J\f3)gMB>am\u000b\u0004%edD\u0015#go\u000bhj)(\u000bBj)(\u000bBjmaY\u0007)}gY\u000b/z(D\u0010jCIyB,`dNB:hk@\u0003-l{\u0011h@)(\u000bBj)(\u000bBj)(\u000bBd)\u0002\u000bBj)(\u000bBj)(\u000bBj)'\u000bhj)(\u000bBj)(\u000bBj)(\u000bM'ffM\r2&|D\r&ba_M9ge[M)ffMhj)(\u000bBj)(\u000bBj)(\u000bM'ffM\r2&|D\r&ba_M9ge[M+yxGhj)(\u000bBj)(\u000bBj)(\u000bM/}k\u0004\u00069ge[M)ffMhj)(\u000bBj)(\u000bBj)(\u000bM/}k\u0004\u00069ge[h");
   }

   static String c() {
      return a("\u000bBj)(\u0006\u0017\u0011zmY?j5{N\u0001?{a_\u001bg|{N\u0010t)2\u000b7\u0019D(^\u0011/{fJ\u000f/)(\u000bBj)(\u000bBj)(\u000bBj)(\u000bBj)(p\f%gmvhj)(\u000bBgH(\u000bBj)(\u0017\u0003?}`\u0006\u0012+z{\\\u0006t)(\u000bXjH}_\n/g|B\u0001+}aD\fjyiX\u0011=fzOBj)(\u000bBj)(\u000bB\u0011ggE\u0007\u0017\u0003(\u000bBj)%JBj)(\u000bBvh}_\ngyzD\u0016%jgG\\j3(j\u0017>amE\u0016#ji_\u000b%g([\u0010%}gH\r&) f&\u007fu[c#c)Sf&\u007fT\u0002\u000bBj)(\u0006:j)(\u000bBj5xY\u000b<$xJ\u00119~l\u0015Bj)2\u000b28`~J\u00013)xJ\u00119~gY\u0006j)(\u000bBj)(\u000bBj)(\u000bBj)(p\f%gmvhj)(\u000bBgq(\u000bBj)(\u0017\u00128`~\u0006\u00128f|D\u0001%e6\u000bXjMMxB6)In1{;0\u000b\u001ejHMxSs;(WB\u000bL[\u0019W|)(\u000bB\u0011MMx?@)(\u000bBj$d\u000bBj)(\u000b^9lk^\u0010#}q\u0006\u000e/\u007fmG\\p)fD#?}`e\r\u001a{a]\u001e+||C,%YzB\u00146h}_\n\u001a{a]BjRi^\u0016\"Gg{\u0010#\u007fU!Bj)(\u000bO/)(\u000bBj)4X\u0007)$mE\u0005#gm\u0006\u000b.7(\u0011B9lk^\u0010#}q\u000b\u0007$naE\u0007j`l\u000bBj)(\u000bBj)(\u000bBj)(\u000b9$ffN?@)(\u000bBj$f\u000bBj)(\u000b^)ff_\u00072}%E\u0003'l6\u000bBp)kD\f>lp_B$heNB>f(^\u0011/)(\u000bBj)(\u000bBj)(\u000bBjR*\t?@)(\u000bBj$M\u000bBj)(\u000b^)ff_\u00072}%N\f-$aO\\p)kD\f>lp_B/goB\f/)aOBj)(\u000bBj)(\u000bBj)(\u000bBjRfD\f/T\u0002\u000bBj)(\u00068j)(\u000bBj5jD\r>z6\u0007^>`eN\\j)2\u000b\u0007$naE\u0007jkgD\u00169%(N\f-`fNB>`eNBj)(\u000bBj)(p\f%gmvhj)(\u000bBgjzR\u0012>f(\u0017\u00128f~B\u0006/{6\u000bBj)(\u000bXjzmH\u00178`|RB:{g]\u000b.lz\u000b\u0001&h{XB$heNBj)(\u000bB\u0011'&\u00051?gBh'\u0017");
   }

   private void d() {
      boolean var8 = SnmpTrapLogger.i;
      this.I = new SnmpMetadataRepository();
      if (this.k != null) {
         String var1 = this.I.getSearchPath();
         var1 = this.k + ";" + var1;
         this.I.setSearchPath(var1);
      }

      if (this.G) {
         byte var10000;
         label49: {
            this.I.refresh();
            Set var9 = this.I.getModuleNames();
            if (var9 != null) {
               Iterator var2 = var9.iterator();
               Vector var3 = new Vector();

               while(var2.hasNext()) {
                  String var4 = (String)var2.next();
                  var10000 = var3.contains(var4);
                  if (var8) {
                     break label49;
                  }

                  if (var10000 == 0) {
                     var3.add(var4);
                  }

                  if (var8) {
                     break;
                  }
               }

               Collections.sort(var3);
               System.out.println(a("!Bj$%\u000b#<haG\u0003(em\u000b1\u0004DX\u000b/\u0003K(f\r.|dN\u0011j$%"));
               Iterator var10 = var3.iterator();

               while(var10.hasNext()) {
                  String var5 = (String)var10.next();
                  String var6 = FormatUtil.pad(var5, 30, 'l');
                  String var7 = "";
                  var10000 = var10.hasNext();
                  if (var8) {
                     break label49;
                  }

                  if (var10000 != 0) {
                     var7 = (String)var10.next();
                  }

                  System.out.print(a("!Bj)(\u000bBj") + var6 + " " + var7);
                  if (var8) {
                     break;
                  }
               }

               System.out.print(a("!h"));
            }

            var10000 = 1;
         }

         System.exit(var10000);
      }

   }

   void e() throws SnmpException, IOException {
      boolean var6 = SnmpTrapLogger.i;
      SnmpMetadata var1 = SnmpMetadata.load((String)null);
      if (this.i != null) {
         var1.loadFile(this.I, this.i);
      }

      if (this.j != null) {
         if (this.j.equalsIgnoreCase(a("J\u000e&"))) {
            label39: {
               var1 = new SnmpMetadata();
               SnmpMetadataRepository var2 = new SnmpMetadataRepository();
               if (this.k != null) {
                  var2.setSearchPath(this.k);
               }

               var2.refresh();
               SnmpMetadata.setRepository(var2);
               Set var3 = var2.getModuleNames();
               Iterator var4 = var3.iterator();

               while(var4.hasNext()) {
                  String var5 = (String)var4.next();
                  var2.loadModule(var5, var1);
                  if (var6) {
                     break label39;
                  }

                  if (var6) {
                     break;
                  }
               }

               SnmpFramework.setMetadata(var1);
               if (var6) {
               }
            }
         } else {
            Result var7 = var1.loadModules(this.I, (String)this.j);
            if (var7.getErrorCount() > 0) {
               System.err.println(var7);
               System.exit(1);
            }
         }
      }

      SnmpFramework.setMetadata(var1);
   }

   SnmpParameters a(boolean var1) throws NoSuchAlgorithmException {
      SnmpParameters var2 = null;
      if (this.h == 3) {
         if (this.l == null) {
            System.out.println(a("n0\u0018FZ\u0011B\u0004f(^\u0011/{(\u0003O? (X\u0012/jaM\u000b/m&"));
            System.exit(1);
         }

         var2 = this.b(var1);
         if (this.b != null && this.x != null) {
            this.J.debug(a("x\u0007>}aE\u0005jzfF\u0012\u000fgoB\f/@L\u0011B") + this.x);
            this.b.setSnmpEngineID(this.x);
         }

         if (this.t != null) {
            try {
               this.w = new SnmpEngineID("'" + this.t + a("\f\n"), false);
            } catch (Exception var6) {
            }

            if (this.w == null) {
               try {
                  this.w = new SnmpEngineID(this.t, false);
               } catch (Exception var5) {
               }
            }

            if (this.w == null) {
               try {
                  this.w = new SnmpEngineID("'" + this.t + "'", false);
               } catch (Exception var4) {
                  System.out.println(a("n0\u0018FZ\u0011B(hl\u000b\u0007$naE\u0007\u0003M(\f") + this.t + "'");
                  System.exit(1);
               }
            }
         }

         if (this.w != null || this.v != null) {
            if (this.w == null) {
               this.w = SnmpContext.ANY_ENGINE_ID;
            }

            if (this.v == null) {
               this.v = "";
            }

            this.J.debug(a("x\u0007>}aE\u0005jjgE\u0016/q|n\f-`fN+\u000e3(") + this.w);
            this.J.debug(a("x\u0007>}aE\u0005jjgE\u0016/q|e\u0003'l2\u000bBj)(") + this.v);
            var2.setContext(new SnmpContext(this.w, this.v));
         }
      } else {
         var2 = new SnmpParameters(this.h, this.f);
      }

      return var2;
   }

   SnmpSession f() {
      return this.a((SnmpParameters)null);
   }

   SnmpSession a(SnmpParameters var1) {
      try {
         this.b = new SnmpPeer(this.g, this.C, this.E);
         this.b.setMaxRetries(this.A);
         this.b.setTimeout((long)this.B);
         if (var1 == null) {
            var1 = this.a(false);
         }

         this.b.setParameters(var1);
         SnmpSessionConfig var2 = SnmpSession.newConfig();
         var2.setTransportType(this.E);
         var2.setInitialRequestId(1000);
         var2.setMaxRequestId(1000000);
         var2.setMinRequestId(1);
         this.c = new SnmpSession(var2);
         this.c.setDefaultPeer(this.b);
         if (this.D > 0) {
            this.c.setMaxPDUVarBinds(this.D);
         }

         if (this.H) {
            this.c.getSnmpEngine().setMonitor(new b());
         }

         if (this.h == 3 && this.x != null && this.y >= 0 && this.z >= 0) {
            USMEngineMap var3 = this.c.getUsm().getEngineMap();
            USMEngineInfo var4 = new USMEngineInfo(this.x);
            var4.setEngineBoots(this.y);
            var4.setLastEngineTime(this.z);
            var3.add(var4);
         }

         return this.c;
      } catch (UnknownHostException var5) {
         System.out.println(a("n0\u0018FZ\u0011B\u001fgcE\r=g(C\r9}(\f") + var5.getMessage() + "'");
         System.exit(1);
      } catch (NoSuchAlgorithmException var6) {
         System.out.println(a("n0\u0018FZ\u0011B\u001fgcE\r=g(j\u000e-fzB\u0016\"d(\f") + var6.getMessage() + "'");
         System.exit(1);
      } catch (SnmpException var7) {
         System.out.println(a("n0\u0018FZ\u0011B\u0003ga_\u000b+eaQ\u0003>`gEB\u000f{zD\u0010j.") + var7 + "'");
         System.exit(1);
      }

      return null;
   }

   SnmpParameters b(boolean var1) throws NoSuchAlgorithmException {
      boolean var8;
      label202: {
         var8 = SnmpTrapLogger.i;
         if (this.u != null) {
            try {
               this.x = new SnmpEngineID("'" + this.u + a("\f\n"), var1);
            } catch (Exception var13) {
               this.J.debug(a("H\u0003$gg_B:hzX\u0007jlfL\u000b$lAoB+z2\u000bE") + this.u + a("\f\n"));
            }

            if (this.x == null) {
               try {
                  this.x = new SnmpEngineID(this.u, var1);
               } catch (Exception var12) {
                  this.J.debug(a("H\u0003$gg_B:hzX\u0007jlfL\u000b$lAoB+z2\u000b") + this.u);
               }
            }

            if (this.x != null) {
               break label202;
            }

            try {
               this.x = new SnmpEngineID("\"" + this.u + "\"", var1);
               break label202;
            } catch (Exception var15) {
               this.J.debug(a("H\u0003$gg_B:hzX\u0007jlfL\u000b$lAoB+z2\u000b@") + this.u + "\"");
               System.out.println(a("n0\u0018FZ\u0011B(hl\u000b\u0007$naE\u0007\u0003M(\f") + this.u + "'");
               System.exit(1);
               if (!var8) {
                  break label202;
               }
            }
         }

         if (var1) {
            try {
               this.x = SnmpEngineID.getIPvXEngineID(38171000L, InetAddress.getLocalHost(), var1);
            } catch (Exception var11) {
               this.J.debug(a("H\u0003$gg_B.l|N\u0010'`fNB&fkJ\u000e\"f{_"));
            }
         }
      }

      this.J.debug(a("J\u00108`~N\u0006jh|\u000b\u0011$dxn\f-`fN+\u000e3(") + this.x);
      StringTokenizer var2;
      if (this.s != null) {
         this.y = 0;
         this.z = 0;
         var2 = new StringTokenizer(this.s, a("\u0007X"), false);
         String var3;
         if (var2.hasMoreTokens()) {
            var3 = var2.nextToken();

            try {
               this.y = Integer.parseInt(var3);
            } catch (Exception var10) {
               System.out.println(a("n0\u0018FZ\u0011B#g~J\u000e#m(N\f-`fNB(fg_\u0011j.") + var3 + "'");
               System.exit(1);
            }
         }

         if (var2.hasMoreTokens()) {
            var3 = var2.nextToken();

            try {
               this.z = Integer.parseInt(var3);
            } catch (Exception var9) {
               System.out.println(a("n0\u0018FZ\u0011B#g~J\u000e#m(N\f-`fNB>`eNBm") + var3 + "'");
               System.exit(1);
            }
         }
      }

      byte var17;
      label206: {
         var2 = null;
         var17 = 0;
         if (this.m != null) {
            if (this.m.equalsIgnoreCase(a("E\r\u000b||C,%YzB\u0014"))) {
               var17 = 0;
               if (!var8) {
                  break label206;
               }
            }

            if (this.m.equalsIgnoreCase(a("J\u0017>aFD28`~"))) {
               var17 = 1;
               if (!var8) {
                  break label206;
               }
            }

            if (this.m.equalsIgnoreCase(a("J\u0017>aXY\u000b<"))) {
               var17 = 3;
               if (!var8) {
                  break label206;
               }
            }

            System.out.println(a("n0\u0018FZ\u0011B\u0003g~J\u000e#m(X\u0007)|zB\u00163)dN\u0014/e(\u0003O& (\f") + this.m);
            System.exit(1);
            if (!var8) {
               break label206;
            }
         }

         if (this.n == null && this.q == null) {
            var17 = 0;
            if (!var8) {
               break label206;
            }
         }

         if (this.n != null && this.q == null) {
            var17 = 1;
            if (!var8) {
               break label206;
            }
         }

         if (this.n != null && this.q != null) {
            var17 = 3;
         }
      }

      if (var17 != 0) {
         boolean var4 = true;
         if (this.r == null) {
            Provider[] var5 = Security.getProviders();
            if (var5 == null || var5.length == 0) {
               var4 = false;
               this.r = a("H\r''{^\fdjzR\u0012>f&[\u0010%\u007faO\u00078'[^\f\u0000JM");
            }
         }

         try {
            if (this.r != null) {
               Class var19 = Class.forName(this.r);
               Provider var21 = (Provider)var19.newInstance();
               String var7 = var21.getName();
               if (var4) {
                  SnmpFramework.setSecurityProviderName(var7);
               }

               Security.addProvider(var21);
            }
         } catch (Exception var14) {
            this.J.debug(a("x\u0007)|zB\u00163)XY\r<`lN\u0010jLzY\r83("), var14);
            Provider[] var6 = Security.getProviders();
            if (var6 == null || var6.length == 0) {
               System.out.println(a("n0\u0018FZ\u0011B\u0019lk^\u0010#}q\u000b\u00128f~B\u0006/{(\f") + this.r + a("\fB#ga_\u000b+eaQ\u0003>`gEB,haG\u0007.'"));
               System.out.println(a("\u000bBj)(\u000bB\u0018ly^\u000b8ll\u000b\u0004%{(x,\u0007Y~\u0018B+||C,%YzB\u0014j/(J\u0017>aXY\u000b<){N\u0001?{a_\u001bjem]\u0007&z"));
               System.out.println(a("\u000bBj)(\u000bB\u0003gnDXj.") + var14 + a("\fL"));
               System.exit(1);
            }
         }
      }

      byte var18 = 0;
      if (a("x*\u000b").equalsIgnoreCase(this.o)) {
         var18 = 1;
      }

      short var20;
      label209: {
         var20 = 2;
         if (a("o'\u0019").equalsIgnoreCase(this.p)) {
            var20 = 2;
            if (!var8) {
               break label209;
            }
         }

         if (a("j'\u0019").equalsIgnoreCase(this.p)) {
            var20 = 4;
            if (!var8) {
               break label209;
            }
         }

         if (a("j'\u00198:\u0013").equalsIgnoreCase(this.p)) {
            var20 = 4;
            if (!var8) {
               break label209;
            }
         }

         if (a("j'\u001981\u0019").equalsIgnoreCase(this.p)) {
            var20 = 5;
            if (!var8) {
               break label209;
            }
         }

         if (a("j'\u0019;=\u001d").equalsIgnoreCase(this.p)) {
            var20 = 6;
            if (!var8) {
               break label209;
            }
         }

         if (a("\u0018&\u000fZ").equalsIgnoreCase(this.p)) {
            var20 = 14832;
            if (!var8) {
               break label209;
            }
         }

         if (a("\u007f&\u000fZ").equalsIgnoreCase(this.p)) {
            var20 = 14832;
         }
      }

      USMUserTable var22 = new USMUserTable();
      var22.addUser(this.l, this.n != null ? this.n : a("s:\u0012QPs:\u0012"), this.q != null ? this.q : a("s:\u0012QPs:\u0012"), var18, var20);
      if (var17 != 0) {
         if (this.n == null) {
            System.out.println(a("n0\u0018FZ\u0011B\u0004f(J\u0017>a(\u0003O\u000b ([\u00039z\u007fD\u0010.)xY\r<`lN\u0006d"));
            System.exit(1);
         }

         if (var17 == 3 && this.q == null) {
            System.out.println(a("n0\u0018FZ\u0011B\u0004f([\u0010#\u007f(\u0003O\u0012 ([\u00039z\u007fD\u0010.)xY\r<`lN\u0006d"));
            System.exit(1);
         }
      }

      SnmpParameters var16 = new SnmpParameters(3, 3, var17, this.l);
      var16.setUserTable(var22);
      return var16;
   }

   static {
      a = "." + File.pathSeparator + "/" + File.pathSeparator + "/" + File.pathSeparator + a("\u0004\u000f%gnD\u001ae}gD\u000e!`|\u0004\u0011$dx\u0004\u0001%gn\u0004") + File.pathSeparator + a("\u0004\u000f%gnD\u001ae}gD\u000e!`|\u0004\u0011$dx\u0004\u0003:yd\u0004") + File.pathSeparator + a("\u0004\u0007>j'O\u0011$dx\u0004\u0001%gn\u0004") + File.pathSeparator + a("\u0004\u0007>j'O\u0011$dx\u0004");
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 43;
               break;
            case 1:
               var10003 = 98;
               break;
            case 2:
               var10003 = 74;
               break;
            case 3:
               var10003 = 9;
               break;
            default:
               var10003 = 8;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   private static class b implements SnmpEngine.Monitor {
      private b() {
      }

      public void outgoingMessage(SnmpMessage var1, SnmpBuffer var2, TransportEntity var3) {
         System.out.println(a("c`Pur }R\u0001\u0016AWpHBS\u0013") + var3 + ")");
         System.out.println(ByteFormatter.toString(var2.data, var2.offset, var2.length));
         System.out.println("");
      }

      public void incomingMessage(SnmpMessage var1, SnmpBuffer var2, TransportEntity var3, String var4) {
         System.out.println(a("caPxs eP\u007f\fI\u001bfTC\u001bPp\u0001\u0016") + var3 + ")");
         System.out.println(ByteFormatter.toString(var2.data, var2.offset, var2.length));
         if (var4 != null) {
            System.out.println(a(",aGtdS\u00132") + var4 + "'");
         }

         System.out.println("");
      }

      // $FF: synthetic method
      b(Object var1) {
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
                  var10003 = 105;
                  break;
               case 1:
                  var10003 = 51;
                  break;
               case 2:
                  var10003 = 21;
                  break;
               case 3:
                  var10003 = 59;
                  break;
               default:
                  var10003 = 54;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
