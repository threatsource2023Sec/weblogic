package monfox.toolkit.snmp.metadata.gen;

import java.util.Hashtable;
import monfox.jdom.Element;
import monfox.log.Logger;

abstract class n {
   private int a;
   private int b;
   private int c;
   private boolean d;
   private boolean e;
   private boolean f;
   private boolean g;
   private boolean h;
   private Element i;
   private Hashtable j;
   private String k;
   private Class l;
   private String[] m;
   private int n;
   protected Logger _log;
   protected SnmpMibGen _mibGen;
   private static final String o = "$Id: ValidationCoordinator.java,v 1.14 2003/10/31 03:59:48 sking Exp $";

   n() {
      this.a = 0;
      this.b = 0;
      this.c = 0;
      this.d = false;
      this.e = false;
      this.f = false;
      this.g = false;
      this.h = false;
      this.i = null;
      this.j = new Hashtable();
      this.k = null;
      this.l = null;
      this.m = null;
      this.n = 0;
      this._log = Logger.getInstance(b("_\ffZ\u001dh\u0019c\\\u0017J\u0002eA\u001d`\u0003kG\u0016{"));
   }

   n(String[] var1, String var2, Class var3, int var4, SnmpMibGen var5) {
      this();
      this.m = var1;
      this.k = var2;
      this.l = var3;
      this.n = var4;
      this._mibGen = var5;
   }

   int a() {
      return this.a;
   }

   void a(int var1) {
      this.a = var1;
   }

   void a(String var1) {
      this.a((InfoMessage)(new InfoMessage.ValidationStage(this.a, var1)), (Element)null);
   }

   void b() {
   }

   void a(InfoMessage var1) {
      this.a((InfoMessage)var1, (Element)null);
   }

   void a(InfoMessage var1, Element var2) {
      this._mibGen.a((InfoMessage)var1, (Object)null);
   }

   void a(ErrorMessage var1) {
      this.a((ErrorMessage)var1, (Element)null);
   }

   void a(ErrorMessage var1, Element var2) {
      this._mibGen.a((ErrorMessage)var1, (Object)var2);
   }

   int c() {
      return this.b;
   }

   int d() {
      return this.c;
   }

   void a(Element var1) {
      int var5 = Message.d;
      this.i = var1;
      Element var2 = this.i;
      int var3 = 0;

      do {
         int var10000 = var3;

         label29:
         while(true) {
            if (var10000 >= this.m.length) {
               return;
            }

            this.a(var3);
            this.a(this.m[var3]);
            int var4 = 0;

            while(true) {
               if (var4 >= this.n) {
                  break label29;
               }

               var10000 = this.b(var2);
               if (var5 != 0) {
                  break;
               }

               if (var10000 != 0) {
                  break label29;
               }

               ++var4;
               if (var5 != 0) {
                  break label29;
               }
            }
         }

         this.b();
         ++var3;
      } while(var5 == 0);

   }

   void a(String var1, p var2) {
      this.j.put(var1, var2);
   }

   boolean b(Element var1) {
      if (var1 == null) {
         this.a((ErrorMessage)(new ErrorMessage.ProcessingFailure(b("G8F\u007fYJ\u0002gC\u0016g\bdGY'C$\u0013)f\u001eyZ\u001be\b*v\u000b{\u0002x"))));
         return false;
      } else {
         p var2 = (p)this.j.get(var1.getName());
         if (var2 == null) {
            try {
               Class var3 = null;
               if (this.k != null) {
                  var3 = Class.forName(this.k + "." + var1.getName() + b("_\ffZ\u001dh\u0019eA"));
               } else {
                  var3 = Class.forName(var1.getName() + b("_\ffZ\u001dh\u0019eA"));
               }

               var2 = (p)var3.newInstance();
            } catch (Exception var5) {
            } catch (Error var6) {
            }

            if (var2 == null) {
               try {
                  this.a((InfoMessage)(new InfoMessage.Debug(b("\\\u001ec]\u001e))oU\u0018|\u0001~\u0013/h\u0001cW\u0018}\u0002x\u0013\u001ff\u001f0\u0013") + var1.getName())));
                  var2 = (p)this.l.newInstance();
               } catch (Exception var4) {
                  this.a((ErrorMessage)(new ErrorMessage.ProcessingFailure(b("J\fd]\u0016}Mc]\n}\fdG\u0010h\u0019o\u0013\u001dl\u000bkF\u0015}M|R\u0015`\tkG\u0016{"))));
                  Thread.dumpStack();
                  return false;
               }
            }

            this.j.put(var1.getName(), var2);
         }

         return var2.a(this, var1);
      }
   }

   boolean e() {
      return this.d;
   }

   void a(boolean var1) {
      this.d = var1;
   }

   boolean f() {
      return this.e;
   }

   void b(boolean var1) {
      this.e = var1;
   }

   boolean g() {
      return this.f;
   }

   void c(boolean var1) {
      this.f = var1;
   }

   Element h() {
      return this.i;
   }

   boolean i() {
      return this.h;
   }

   void d(boolean var1) {
      this.h = var1;
   }

   public boolean isRichMode() {
      return this.g;
   }

   public void isRichMode(boolean var1) {
      this.g = var1;
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 9;
               break;
            case 1:
               var10003 = 109;
               break;
            case 2:
               var10003 = 10;
               break;
            case 3:
               var10003 = 51;
               break;
            default:
               var10003 = 121;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
