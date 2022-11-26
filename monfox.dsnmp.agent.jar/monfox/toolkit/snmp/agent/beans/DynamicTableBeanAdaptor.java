package monfox.toolkit.snmp.agent.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Properties;
import java.util.Vector;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.metadata.SnmpTableEntryInfo;
import monfox.toolkit.snmp.metadata.SnmpTableInfo;

public class DynamicTableBeanAdaptor extends StandardTableBeanAdaptor {
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

   public DynamicTableBeanAdaptor(SnmpMib var1, Class var2) throws SnmpValueException {
      super(var1, var2, (String)null);
   }

   public SnmpTableInfo generateMetadata(String var1, String var2, String var3, String[] var4) throws SnmpValueException, IntrospectionException {
      boolean var24 = SnmpBeanAdaptor.A;
      SnmpMetadata var5 = this.getMib().getMetadata();
      this.setBaseName(var2);
      String var6 = var2 + b("F`V\u001e\u0013");
      SnmpObjectInfo var7 = null;

      try {
         var7 = var5.getObject(var6);
      } catch (SnmpValueException var27) {
      }

      if (var7 != null) {
         throw new SnmpValueException(b("]hPR\u0018slQHV5") + var6 + b("5!U\u001e\u0004w`P\u000bVwy]\u0001\u0002a/"));
      } else {
         Properties var8 = new Properties();
         BeanInfo var9 = Introspector.getBeanInfo(this.getBeanClass());
         PropertyDescriptor[] var10 = var9.getPropertyDescriptors();
         Vector var11 = new Vector();
         Vector var12 = new Vector();
         int var13 = 1;
         int var14 = 0;

         while(var14 < var10.length) {
            label282: {
               PropertyDescriptor var15;
               Class var16;
               String var17;
               label283: {
                  var15 = var10[var14];
                  var16 = var15.getPropertyType();
                  var17 = "s";
                  if (var16.isAssignableFrom(m == null ? (m = a(b("x`B\u0013X~`Z\u0015XAuF\u001b\u0018u"))) : m)) {
                     var17 = "s";
                     if (!var24) {
                        break label283;
                     }

                     SnmpException.b = !SnmpException.b;
                  }

                  if (var16.isAssignableFrom(n == null ? (n = a(b("x`B\u0013X~`Z\u0015X\\tY\u0010\u0013`"))) : n)) {
                     var17 = "i";
                     if (!var24) {
                        break label283;
                     }
                  }

                  if (var16 == Integer.TYPE) {
                     var17 = "i";
                     var16 = o == null ? (o = a(b("x`B\u0013X~`Z\u0015X[o@\u0017\u0011ws"))) : o;
                     if (!var24) {
                        break label283;
                     }
                  }

                  if (var16 == Short.TYPE) {
                     var17 = "i";
                     var16 = p == null ? (p = a(b("x`B\u0013X~`Z\u0015XAi[\u0000\u0002"))) : p;
                     if (!var24) {
                        break label283;
                     }
                  }

                  if (var16 == Byte.TYPE) {
                     var17 = "i";
                     var16 = q == null ? (q = a(b("x`B\u0013X~`Z\u0015XPx@\u0017"))) : q;
                     if (!var24) {
                        break label283;
                     }
                  }

                  if (var16 == Long.TYPE) {
                     var17 = "i";
                     var16 = r == null ? (r = a(b("x`B\u0013X~`Z\u0015X^nZ\u0015"))) : r;
                     if (!var24) {
                        break label283;
                     }
                  }

                  if (var16 == Float.TYPE) {
                     var17 = "s";
                     var16 = s == null ? (s = a(b("x`B\u0013X~`Z\u0015XTm[\u0013\u0002"))) : s;
                     if (!var24) {
                        break label283;
                     }
                  }

                  if (var16 == Double.TYPE) {
                     var17 = "s";
                     var16 = t == null ? (t = a(b("x`B\u0013X~`Z\u0015XVnA\u0010\u001aw"))) : t;
                     if (!var24) {
                        break label283;
                     }
                  }

                  if (var16 == (u == null ? (u = a(b("\u007fnZ\u0014\u0019j/@\u001d\u0019~j]\u0006XaoY\u0002XAoY\u0002\"{lQ&\u001fqjG"))) : u)) {
                     var17 = "t";
                     if (!var24) {
                        break label283;
                     }
                  }

                  if (var16 == (v == null ? (v = a(b("\u007fnZ\u0014\u0019j/@\u001d\u0019~j]\u0006XaoY\u0002XAoY\u0002%fs]\u001c\u0011"))) : v)) {
                     var17 = "s";
                     if (!var24) {
                        break label283;
                     }
                  }

                  if (var16 == (w == null ? (w = a(b("\u007fnZ\u0014\u0019j/@\u001d\u0019~j]\u0006XaoY\u0002XAoY\u00025}tZ\u0006\u0013`"))) : w)) {
                     var17 = "c";
                     if (!var24) {
                        break label283;
                     }
                  }

                  if (var16 == (x == null ? (x = a(b("\u007fnZ\u0014\u0019j/@\u001d\u0019~j]\u0006XaoY\u0002XAoY\u00025}tZ\u0006\u0013`7\u0000"))) : x)) {
                     var17 = b("q7\u0000");
                     if (!var24) {
                        break label283;
                     }
                  }

                  if (var16 == (y == null ? (y = a(b("\u007fnZ\u0014\u0019j/@\u001d\u0019~j]\u0006XaoY\u0002XAoY\u0002?|u"))) : y)) {
                     var17 = "i";
                     if (!var24) {
                        break label283;
                     }
                  }

                  if (var16 == (z == null ? (z = a(b("\u007fnZ\u0014\u0019j/@\u001d\u0019~j]\u0006XaoY\u0002XAoY\u00021stS\u0017"))) : z)) {
                     var17 = "g";
                     if (!var24) {
                        break label283;
                     }
                  }

                  if (var16 != (a == null ? (a = a(b("\u007fnZ\u0014\u0019j/@\u001d\u0019~j]\u0006XaoY\u0002XAoY\u00029{e"))) : a)) {
                     break label282;
                  }

                  var17 = "o";
               }

               String var18;
               label198: {
                  var18 = b("`v");
                  if (var15.getWriteMethod() == null) {
                     var18 = b("`n");
                     if (!var24) {
                        break label198;
                     }
                  }

                  try {
                     var16.getConstructor(m == null ? (m = a(b("x`B\u0013X~`Z\u0015XAuF\u001b\u0018u"))) : m);
                  } catch (NoSuchMethodException var26) {
                     System.err.println(b("Q`Z\u001c\u0019f!G\u0017\u00022wU\u001e\u0003w;\u0014") + var15.getName());
                     var18 = b("`n");
                  }
               }

               var18 = this.getNameProperty(var15.getName() + b("<`W\u0011\u0013ar"), var18);
               if (!var18.startsWith("n")) {
                  var17 = this.getNameProperty(var15.getName() + b("<uM\u0002\u0013"), var17);
                  String var19 = var2 + var15.getName().substring(0, 1).toUpperCase() + var15.getName().substring(1);
                  var19 = this.getNameProperty(var15.getName(), var19);
                  String var20 = this.getNameProperty(var15.getName() + b("<hZ\u0016\u0013j"), "" + var13);
                  String var21 = var3 + b("<0\u001a") + var20;
                  SnmpObjectInfo var22 = null;

                  try {
                     var22 = var5.getObject(var19);
                  } catch (SnmpValueException var25) {
                  }

                  if (var22 != null) {
                     throw new SnmpValueException(b("]c^\u0017\u0015f!u\u001e\u0004w`P\u000bV@dS\u001b\u0005fdF\u0017\u0012(!") + var19);
                  }

                  var8.put(var15.getName(), var19);
                  var11.addElement(var5.addObject(var1, var21, var19, var17, var18));
                  var12.addElement(var15);
                  ++var13;
               }
            }

            ++var14;
            if (var24) {
               break;
            }
         }

         SnmpObjectInfo[] var29 = new SnmpObjectInfo[var4.length];
         int var30 = 0;

         SnmpObjectInfo[] var10000;
         while(true) {
            if (var30 < var4.length) {
               String var33 = var8.getProperty(var4[var30], var4[var30]);

               try {
                  var10000 = var29;
                  if (var24) {
                     break;
                  }

                  var29[var30] = var5.getObject(var33);
               } catch (SnmpValueException var28) {
                  throw new SnmpValueException(b("[oB\u0013\u001a{e\u00141\u0019~tY\u001cV\\`Y\u0017L2") + var33);
               }

               ++var30;
               if (!var24) {
                  continue;
               }
            }

            var10000 = new SnmpObjectInfo[var11.size()];
            break;
         }

         SnmpObjectInfo[] var31 = var10000;
         int var34 = 0;

         while(var34 < var31.length) {
            var31[var34] = (SnmpObjectInfo)var11.elementAt(var34);
            var31[var34].setColumnar(true);
            ++var34;
            if (var24) {
               break;
            }
         }

         SnmpTableEntryInfo var35 = new SnmpTableEntryInfo(var31, var29);
         var5.add(var1, var3 + b("<0"), var2 + b("Wo@\u0000\u000f"), (SnmpOidInfo)var35);
         SnmpTableInfo var36 = new SnmpTableInfo(var31, var29, var35);
         var5.add(var1, var3, var2 + b("F`V\u001e\u0013"), var36);
         int var32 = 0;

         while(true) {
            if (var32 < var31.length) {
               var31[var32].setTableInfo(var36);
               ++var32;
               if (var24) {
                  break;
               }

               if (!var24) {
                  continue;
               }
            }

            this.a(var36);
            break;
         }

         return var36;
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
               var10003 = 18;
               break;
            case 1:
               var10003 = 1;
               break;
            case 2:
               var10003 = 52;
               break;
            case 3:
               var10003 = 114;
               break;
            default:
               var10003 = 118;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
