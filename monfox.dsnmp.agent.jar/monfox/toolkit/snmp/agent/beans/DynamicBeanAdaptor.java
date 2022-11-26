package monfox.toolkit.snmp.agent.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Properties;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpModule;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;

public class DynamicBeanAdaptor extends StandardBeanAdaptor {
   private static Logger g = null;
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
   // $FF: synthetic field
   static Class a;
   // $FF: synthetic field
   static Class b;

   public DynamicBeanAdaptor(SnmpMib var1, Object var2) throws SnmpValueException {
      this(var1, var2, (Class)null);
   }

   public DynamicBeanAdaptor(SnmpMib var1, Object var2, Class var3) throws SnmpValueException {
      super(var1, var2, var3);
      if (g == null) {
         g = Logger.getInstance(b("\u000e;~F\u0017#!RB\u001b$\u0003tF\n>-b"));
      }

   }

   public SnmpModule generateMetadata(String var1, String var2, String var3, String var4) throws SnmpValueException, IntrospectionException {
      boolean var23 = SnmpBeanAdaptor.A;
      SnmpMetadata var5 = this.getMib().getMetadata();
      this.setBaseName(var3);
      SnmpObjectInfo var6 = null;

      try {
         var6 = var5.getObject(var2);
      } catch (SnmpValueException var26) {
      }

      if (var6 != null) {
         throw new SnmpValueException(b("\u0005+t\u0007\u0014+/u\u001dZm") + var2 + b("mbqK\b/#t^Z/:yT\u000e9l"));
      } else {
         var5.addOid(var1, var4, var2);
         Class var7 = this.getObject().getClass();
         if (this.getCoreInterface() != null) {
            label266: {
               if (this.getCoreInterface().isAssignableFrom(var7)) {
                  var7 = this.getCoreInterface();
                  if (!var23) {
                     break label266;
                  }
               }

               throw new IntrospectionException(b("\t-bBZ#,dB\b,#sB@je") + this.getCoreInterface().getName() + b("mbyTZ$-d\u0007\u001b$byI\u000e/0vF\u0019/b\u007fAZ\b'qIZ).qT\tje") + var7.getName() + "'");
            }
         }

         BeanInfo var8 = Introspector.getBeanInfo(var7);
         PropertyDescriptor[] var9 = var8.getPropertyDescriptors();
         new Properties();
         Vector var11 = new Vector();
         int var12 = 1;
         int var13 = 0;

         while(var13 < var9.length) {
            label261: {
               PropertyDescriptor var14;
               Class var15;
               String var16;
               label262: {
                  var14 = var9[var13];
                  var15 = var14.getPropertyType();
                  var16 = "s";
                  if (var15.isAssignableFrom(m == null ? (m = a(b(" #fFT&#~@T\u00196bN\u0014-"))) : m)) {
                     var16 = "s";
                     if (!var23) {
                        break label262;
                     }
                  }

                  if (var15.isAssignableFrom(n == null ? (n = a(b(" #fFT&#~@T\u00047}E\u001f8"))) : n)) {
                     var16 = "i";
                     if (!var23) {
                        break label262;
                     }
                  }

                  if (var15 == Integer.TYPE) {
                     var16 = "i";
                     var15 = o == null ? (o = a(b(" #fFT&#~@T\u0003,dB\u001d/0"))) : o;
                     if (!var23) {
                        break label262;
                     }
                  }

                  if (var15 == Short.TYPE) {
                     var16 = "i";
                     var15 = p == null ? (p = a(b(" #fFT&#~@T\u0019*\u007fU\u000e"))) : p;
                     if (!var23) {
                        break label262;
                     }
                  }

                  if (var15 == Byte.TYPE) {
                     var16 = "i";
                     var15 = q == null ? (q = a(b(" #fFT&#~@T\b;dB"))) : q;
                     if (!var23) {
                        break label262;
                     }
                  }

                  if (var15 == Long.TYPE) {
                     var16 = "i";
                     var15 = r == null ? (r = a(b(" #fFT&#~@T\u0006-~@"))) : r;
                     if (!var23) {
                        break label262;
                     }
                  }

                  if (var15 == Float.TYPE) {
                     var16 = "s";
                     var15 = s == null ? (s = a(b(" #fFT&#~@T\f.\u007fF\u000e"))) : s;
                     if (!var23) {
                        break label262;
                     }
                  }

                  if (var15 == Double.TYPE) {
                     var16 = "s";
                     var15 = t == null ? (t = a(b(" #fFT&#~@T\u000e-eE\u0016/"))) : t;
                     if (!var23) {
                        break label262;
                     }
                  }

                  if (var15 == (u == null ? (u = a(b("'-~A\u00152ldH\u0015&)yST9,}WT\u0019,}W.#/us\u0013))c"))) : u)) {
                     var16 = "t";
                     if (!var23) {
                        break label262;
                     }
                  }

                  if (var15 == (v == null ? (v = a(b("'-~A\u00152ldH\u0015&)yST9,}WT\u0019,}W)>0yI\u001d"))) : v)) {
                     var16 = "s";
                     if (!var23) {
                        break label262;
                     }
                  }

                  if (var15 == (w == null ? (w = a(b("'-~A\u00152ldH\u0015&)yST9,}WT\u0019,}W9%7~S\u001f8"))) : w)) {
                     var16 = "c";
                     if (!var23) {
                        break label262;
                     }
                  }

                  if (var15 == (x == null ? (x = a(b("'-~A\u00152ldH\u0015&)yST9,}WT\u0019,}W9%7~S\u001f8t$"))) : x)) {
                     var16 = b(")t$");
                     if (!var23) {
                        break label262;
                     }
                  }

                  if (var15 == (y == null ? (y = a(b("'-~A\u00152ldH\u0015&)yST9,}WT\u0019,}W3$6"))) : y)) {
                     var16 = "i";
                     if (!var23) {
                        break label262;
                     }
                  }

                  if (var15 == (z == null ? (z = a(b("'-~A\u00152ldH\u0015&)yST9,}WT\u0019,}W=+7wB"))) : z)) {
                     var16 = "g";
                     if (!var23) {
                        break label262;
                     }
                  }

                  if (var15 == (a == null ? (a = a(b("'-~A\u00152ldH\u0015&)yST9,}WT\u0019,}W5#&"))) : a)) {
                     var16 = "o";
                     if (!var23) {
                        break label262;
                     }
                  }

                  g.debug(b("\u0003%~H\b#,w\u0007\n8-`B\b>;0\u0000") + var14.getName() + b("ml0i\u0015j/qW\n#,w\u0007\u001c%00W\b%2uU\u000e3bd^\n/b7") + var15.getName() + "'");
                  if (!var23) {
                     break label261;
                  }
               }

               String var17;
               label263: {
                  var17 = b("85");
                  if (var14.getWriteMethod() == null) {
                     var17 = b("8-");
                     if (!var23) {
                        break label263;
                     }
                  }

                  if (!(b == null ? (b = a(b("'-~A\u00152ldH\u0015&)yST9,}WT\u0019,}W,+.eB"))) : b).isAssignableFrom(var15)) {
                     try {
                        var15.getConstructor(m == null ? (m = a(b(" #fFT&#~@T\u00196bN\u0014-"))) : m);
                     } catch (NoSuchMethodException var25) {
                        System.err.println(b("\t#~I\u0015>bcB\u000ej4qK\u000f/x0") + var14.getName());
                        g.debug(b("\t#~I\u0015>bcB\u000ej4qK\u000f/bvH\bjx0\u0000") + var14.getName() + b("ml0i\u0015j\u0011dU\u0013$%0D\u0015$1dU\u000f)6\u007fUZ,-b\u0007\u000e32u\t"));
                        var17 = b("8-");
                     }
                  }
               }

               var17 = this.getNameProperty(var14.getName() + b("d#sD\u001f91"), var17);
               if (!var17.startsWith("n")) {
                  var16 = this.getNameProperty(var14.getName() + b("d6iW\u001f"), var16);
                  String var18 = var14.getName();
                  if (var3 != null) {
                     var18 = var3 + var14.getName().substring(0, 1).toUpperCase() + var14.getName().substring(1);
                  }

                  var18 = this.getNameProperty(var14.getName(), var18);
                  String var19 = this.getNameProperty(var14.getName() + b("d+~C\u001f2"), "" + var12);
                  String var20 = var4 + "." + var19;
                  SnmpObjectInfo var21 = null;

                  try {
                     var21 = var5.getObject(var18);
                  } catch (SnmpValueException var24) {
                  }

                  if (var21 != null) {
                     throw new SnmpValueException(b("\u0005 zB\u0019>bQK\b/#t^Z\u0018'wN\t>'bB\u001epb") + var18);
                  }

                  SnmpObjectInfo var22 = var5.addObject(var1, var20, var18, var16, var17);
                  var11.addElement(var22);
                  if (g.isDebugEnabled()) {
                     g.debug(b("\u000b&tB\u001ej\rRm?\t\u00160\u001d") + var22);
                  }

                  ++var12;
               }
            }

            ++var13;
            if (var23) {
               break;
            }
         }

         return var5.getModule(var1);
      }
   }

   // $FF: synthetic method
   static Class a(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         throw (new NoClassDefFoundError()).initCause(var2);
      }
   }

   private static String b(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 74;
               break;
            case 1:
               var10003 = 66;
               break;
            case 2:
               var10003 = 16;
               break;
            case 3:
               var10003 = 39;
               break;
            default:
               var10003 = 122;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
