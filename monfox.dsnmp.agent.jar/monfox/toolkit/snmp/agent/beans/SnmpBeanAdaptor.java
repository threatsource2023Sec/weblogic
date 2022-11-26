package monfox.toolkit.snmp.agent.beans;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Map;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;

public abstract class SnmpBeanAdaptor {
   static final Class[] a;
   static final Class[] b;
   static final Class[] c;
   static final Class[] d;
   static final Class[] e;
   static final Class[] f;
   static final Class[] g;
   static final Class[] h;
   static final Class[] i;
   private SnmpMib j = null;
   private Map k = null;
   private String l = null;
   // $FF: synthetic field
   static Class m;
   // $FF: synthetic field
   static Class n;
   // $FF: synthetic field
   static Class o;
   // $FF: synthetic field
   static Class p;
   // $FF: synthetic field
   static Class q;
   // $FF: synthetic field
   static Class r;
   // $FF: synthetic field
   static Class s;
   // $FF: synthetic field
   static Class t;
   // $FF: synthetic field
   static Class u;
   // $FF: synthetic field
   static Class v;
   // $FF: synthetic field
   static Class w;
   // $FF: synthetic field
   static Class x;
   // $FF: synthetic field
   static Class y;
   // $FF: synthetic field
   static Class z;
   public static boolean A;

   protected SnmpBeanAdaptor(SnmpMib var1) {
      this.j = var1;
   }

   public SnmpMib getMib() {
      return this.j;
   }

   public void setBaseName(String var1) {
      this.l = var1;
   }

   public String getBaseName() {
      return this.l;
   }

   public void setNameMap(Map var1) {
      this.k = var1;
   }

   public Map getNameMap() {
      return this.k;
   }

   public String getNameProperty(String var1, String var2) {
      if (this.k == null) {
         return var2;
      } else {
         String var3 = (String)this.k.get(var1);
         if (var3 == null) {
            var3 = var2;
         }

         return var3;
      }
   }

   void a(PropertyDescriptor var1, SnmpObjectInfo var2) throws SnmpMibException, SnmpValueException, IntrospectionException {
      boolean var6 = A;
      Class var3 = var1.getPropertyType();
      String var4 = var1.getName();
      String var5 = var2.getName();
      switch (var2.getType()) {
         case 68:
            this.a(var4, var3, c, b("5\n\"f9\u001f"));
            if (!var6) {
               break;
            }
         case 4:
            this.a(var4, var3, b, b("59\u0017R\u0018Z)\u0017E\u00054="));
            if (!var6) {
               break;
            }
         case 64:
            this.a(var4, var3, a, b("3\n\u0002s(\b\u001f0d"));
            if (!var6) {
               break;
            }
         case 6:
            this.a(var4, var3, i, b("58\tR\u000f.Z\nS\t4.\nQ\u0005?("));
            if (!var6) {
               break;
            }
         case 65:
            this.a(var4, var3, f, b("9\u00156y8\u001f\b\u0018$~'"));
            if (!var6) {
               break;
            }
         case 2:
            this.a(var4, var3, h, b("3\u00147r+\u001f\b\u0018$~'"));
            if (!var6) {
               break;
            }
         case 66:
            this.a(var4, var3, e, b("=\u001b6p)!IqJ"));
            if (!var6) {
               break;
            }
         case 67:
            this.a(var4, var3, g, b(".\u0013.r\u0018\u0013\u0019(d"));
            if (!var6) {
               break;
            }
         case 70:
            this.a(var4, var3, d, b("9\u00156y8\u001f\bu#"));
            if (!var6) {
               break;
            }
         case 5:
            if (var6) {
               throw new SnmpValueException(b("/\u00141r/\u0015\u001d-~6\u001f\u001ecX.\u0010\u001f cl\u000e\u00033rvZ") + var2.getTypeString());
            }
            break;
         default:
            throw new SnmpValueException(b("/\u00141r/\u0015\u001d-~6\u001f\u001ecX.\u0010\u001f cl\u000e\u00033rvZ") + var2.getTypeString());
      }

      if (var2.isRead() && var1.getReadMethod() == null) {
         throw new SnmpMibException(b("5\u0018)r/\u000eZd") + var5 + b("]Z*dl\b\u001f\"s-\u0018\u0016&7.\u000f\u000ecg>\u0015\n&e8\u0003Zd") + var4 + b("]Z+v?Z\u0014,7>\u001f\u001b'7d\u001d\u001f7>l\u0017\u001f7\u007f#\u001e"));
      } else if (var2.isWrite() && var1.getWriteMethod() == null) {
         throw new SnmpMibException(b("5\u0018)r/\u000eZd") + var5 + b("]Z*dl\r\b*c-\u0018\u0016&7.\u000f\u000ecg>\u0015\n&e8\u0003Zd") + var4 + b("]Z+v?Z\u0014,7;\b\u00137rlR\t&ceZ\u0017&c$\u0015\u001e"));
      }
   }

   void a(String var1, Class var2, Class[] var3, String var4) throws SnmpValueException {
      boolean var7 = A;
      int var5 = 0;

      while(var5 < var3.length) {
         if (var2 == var3[var5]) {
            return;
         }

         ++var5;
         if (var7) {
            break;
         }
      }

      StringBuffer var8 = new StringBuffer();
      var8.append(b("\u000e\u00033rl\u0017\u00130z-\u000e\u0019+7*\u0015\bc0") + var1 + b("]Tc?") + var4 + b("ZG~)l"));
      int var6 = 0;

      while(true) {
         if (var6 < var3.length) {
            var8.append(var3[var6].getName());
            var8.append("|");
            ++var6;
            if (var7) {
               break;
            }

            if (!var7) {
               continue;
            }
         }

         var8.append(")");
         break;
      }

      throw new SnmpValueException(var8.toString());
   }

   // $FF: synthetic method
   static Class a(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw (new NoClassDefFoundError()).initCause(var2);
      }
   }

   static {
      a = new Class[]{m == null ? (m = a(b("\u0017\u0015-q#\u0002T7x#\u0016\u0011*cb\t\u0014.gb)\u0014.g\u0005\n;'s>\u001f\t0"))) : m, n == null ? (n = a(b("\u0010\u001b5vb\u0016\u001b-pb)\u000e1~\"\u001d"))) : n};
      b = new Class[]{o == null ? (o = a(b("\u0017\u0015-q#\u0002T7x#\u0016\u0011*cb\t\u0014.gb)\u0014.g\u001f\u000e\b*y+"))) : o, n == null ? (n = a(b("\u0010\u001b5vb\u0016\u001b-pb)\u000e1~\"\u001d"))) : n};
      c = new Class[]{p == null ? (p = a(b("\u0017\u0015-q#\u0002T7x#\u0016\u0011*cb\t\u0014.gb)\u0014.g\u0003\n\u001b2b)"))) : p, n == null ? (n = a(b("\u0010\u001b5vb\u0016\u001b-pb)\u000e1~\"\u001d"))) : n};
      d = new Class[]{q == null ? (q = a(b("\u0017\u0015-q#\u0002T7x#\u0016\u0011*cb\t\u0014.gb)\u0014.g\u000f\u0015\u000f-c)\bLw"))) : q, r == null ? (r = a(b("\u0010\u001b5vb\u0016\u001b-pb6\u0015-p"))) : r, Long.TYPE};
      e = new Class[]{s == null ? (s = a(b("\u0017\u0015-q#\u0002T7x#\u0016\u0011*cb\t\u0014.gb)\u0014.g\u000b\u001b\u000f$r"))) : s, t == null ? (t = a(b("\u0010\u001b5vb\u0016\u001b-pb3\u00147r+\u001f\b"))) : t, r == null ? (r = a(b("\u0010\u001b5vb\u0016\u001b-pb6\u0015-p"))) : r, u == null ? (u = a(b("\u0010\u001b5vb\u0016\u001b-pb)\u0012,e8"))) : u, v == null ? (v = a(b("\u0010\u001b5vb\u0016\u001b-pb8\u00037r"))) : v, Byte.TYPE, Integer.TYPE, Long.TYPE, Short.TYPE};
      f = new Class[]{w == null ? (w = a(b("\u0017\u0015-q#\u0002T7x#\u0016\u0011*cb\t\u0014.gb)\u0014.g\u000f\u0015\u000f-c)\b"))) : w, t == null ? (t = a(b("\u0010\u001b5vb\u0016\u001b-pb3\u00147r+\u001f\b"))) : t, r == null ? (r = a(b("\u0010\u001b5vb\u0016\u001b-pb6\u0015-p"))) : r, u == null ? (u = a(b("\u0010\u001b5vb\u0016\u001b-pb)\u0012,e8"))) : u, v == null ? (v = a(b("\u0010\u001b5vb\u0016\u001b-pb8\u00037r"))) : v, Byte.TYPE, Integer.TYPE, Long.TYPE, Short.TYPE};
      g = new Class[]{x == null ? (x = a(b("\u0017\u0015-q#\u0002T7x#\u0016\u0011*cb\t\u0014.gb)\u0014.g\u0018\u0013\u0017&C%\u0019\u00110"))) : x, t == null ? (t = a(b("\u0010\u001b5vb\u0016\u001b-pb3\u00147r+\u001f\b"))) : t, r == null ? (r = a(b("\u0010\u001b5vb\u0016\u001b-pb6\u0015-p"))) : r, u == null ? (u = a(b("\u0010\u001b5vb\u0016\u001b-pb)\u0012,e8"))) : u, v == null ? (v = a(b("\u0010\u001b5vb\u0016\u001b-pb8\u00037r"))) : v, Byte.TYPE, Integer.TYPE, Long.TYPE, Short.TYPE};
      h = new Class[]{y == null ? (y = a(b("\u0017\u0015-q#\u0002T7x#\u0016\u0011*cb\t\u0014.gb)\u0014.g\u0005\u0014\u000e"))) : y, t == null ? (t = a(b("\u0010\u001b5vb\u0016\u001b-pb3\u00147r+\u001f\b"))) : t, r == null ? (r = a(b("\u0010\u001b5vb\u0016\u001b-pb6\u0015-p"))) : r, u == null ? (u = a(b("\u0010\u001b5vb\u0016\u001b-pb)\u0012,e8"))) : u, v == null ? (v = a(b("\u0010\u001b5vb\u0016\u001b-pb8\u00037r"))) : v, Byte.TYPE, Integer.TYPE, Long.TYPE, Short.TYPE};
      i = new Class[]{z == null ? (z = a(b("\u0017\u0015-q#\u0002T7x#\u0016\u0011*cb\t\u0014.gb)\u0014.g\u0003\u0013\u001e"))) : z, n == null ? (n = a(b("\u0010\u001b5vb\u0016\u001b-pb)\u000e1~\"\u001d"))) : n};
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 122;
               break;
            case 1:
               var10003 = 122;
               break;
            case 2:
               var10003 = 67;
               break;
            case 3:
               var10003 = 23;
               break;
            default:
               var10003 = 76;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
