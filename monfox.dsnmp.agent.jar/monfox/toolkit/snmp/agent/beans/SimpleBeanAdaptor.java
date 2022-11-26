package monfox.toolkit.snmp.agent.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibNode;
import monfox.toolkit.snmp.agent.SnmpRuntimeErrorException;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;

public class SimpleBeanAdaptor extends SnmpBeanAdaptor {
   private static Logger a = Logger.getInstance(b("*c@21"), b(",uO1L/tO/5!b]"), b("=Yc\u000f\r\u000brk\u001e\u000f/To\u000f\u0015\u0001B"));
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

   public SimpleBeanAdaptor(SnmpMib var1) {
      super(var1);
   }

   public synchronized SnmpMibLeaf add(String var1, Object var2, String var3) throws SnmpValueException, SnmpMibException, IntrospectionException {
      SnmpMetadata var4 = this.getMib().getMetadata();
      SnmpOid var5 = new SnmpOid(var4, var1);
      SnmpOidInfo var6 = var5.getOidInfo();
      if (var6 != null && var6 instanceof SnmpObjectInfo) {
         SnmpObjectInfo var7 = (SnmpObjectInfo)var6;
         BeanInfo var8 = Introspector.getBeanInfo(var2.getClass());
         PropertyDescriptor[] var9 = var8.getPropertyDescriptors();
         PropertyDescriptor var10 = null;
         boolean var11 = true;

         for(int var12 = 0; var12 < var9.length; ++var12) {
            PropertyDescriptor var13 = var9[var12];
            if (var3.equals(var13.getName())) {
               var10 = var13;
               break;
            }
         }

         if (var10 == null) {
            throw new IntrospectionException(b(" _.,\u0014\rX./\u0013\u0001@k\r\u0015\u0017\n.X") + var3);
         } else {
            this.a(var10, var7);
            BeanMibLeaf var14 = new BeanMibLeaf(var4, var7.getOid(), var5, var2, var10);
            this.getMib().add(var14);
            return var14;
         }
      } else {
         throw new SnmpValueException(b(" _.0#$uM+A\nUh\u0016\u000f\u000bT.\u0019\u000e\u001c\u0010a\u0016\u0005N\u0017") + var1 + "'");
      }
   }

   public synchronized void remove(SnmpMibLeaf var1) {
      this.getMib().remove((SnmpMibNode)var1);
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
               var10003 = 110;
               break;
            case 1:
               var10003 = 48;
               break;
            case 2:
               var10003 = 14;
               break;
            case 3:
               var10003 = 127;
               break;
            default:
               var10003 = 97;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   static class BeanMibLeaf extends SnmpMibLeaf {
      private Object a;
      private PropertyDescriptor b;

      BeanMibLeaf(SnmpMetadata var1, SnmpOid var2, SnmpOid var3, Object var4, PropertyDescriptor var5) throws SnmpValueException, SnmpMibException {
         super(var2, var3);
         this.a = var4;
         this.b = var5;
      }

      public SnmpValue getValue() {
         try {
            try {
               Method var1 = this.b.getReadMethod();
               Object var2 = var1.invoke(this.a);
               if (var2 == null) {
                  var2 = "";
               }

               return var2 instanceof SnmpValue ? (SnmpValue)var2 : SnmpValue.getInstance(this._objectInfo, var2.toString());
            } catch (InvocationTargetException var3) {
               throw var3.getTargetException();
            }
         } catch (SnmpRuntimeErrorException var4) {
            throw var4;
         } catch (Throwable var5) {
            if (SimpleBeanAdaptor.a.isDebugEnabled()) {
               SimpleBeanAdaptor.a.debug(a("@'%\\jQ6)W:C-)T:B:2M\u007fWef") + this.b.getReadMethod().getName(), var5);
            }

            return null;
         }
      }

      public void setValue(SnmpValue var1) throws SnmpValueException {
         boolean var6 = SnmpBeanAdaptor.A;

         try {
            try {
               Class var2 = this.b.getPropertyType();
               if (var2.isInstance(var1)) {
                  Method var3 = this.b.getWriteMethod();
                  if (var3 != null) {
                     var3.invoke(this.a, var1);
                  }

                  if (!var6) {
                     return;
                  }
               }

               label104: {
                  if (var2 == Integer.TYPE) {
                     var2 = SimpleBeanAdaptor.m == null ? (SimpleBeanAdaptor.m = SimpleBeanAdaptor.a(a("O>0X4I>(^4l12\\}@-"))) : SimpleBeanAdaptor.m;
                     if (!var6) {
                        break label104;
                     }
                  }

                  if (var2 == Long.TYPE) {
                     var2 = SimpleBeanAdaptor.n == null ? (SimpleBeanAdaptor.n = SimpleBeanAdaptor.a(a("O>0X4I>(^4i0(^"))) : SimpleBeanAdaptor.n;
                     if (!var6) {
                        break label104;
                     }
                  }

                  if (var2 == Double.TYPE) {
                     var2 = SimpleBeanAdaptor.o == null ? (SimpleBeanAdaptor.o = SimpleBeanAdaptor.a(a("O>0X4I>(^4a03[v@"))) : SimpleBeanAdaptor.o;
                     if (!var6) {
                        break label104;
                     }
                  }

                  if (var2 == Float.TYPE) {
                     var2 = SimpleBeanAdaptor.p == null ? (SimpleBeanAdaptor.p = SimpleBeanAdaptor.a(a("O>0X4I>(^4c3)Xn"))) : SimpleBeanAdaptor.p;
                     if (!var6) {
                        break label104;
                     }
                  }

                  if (var2 == Byte.TYPE) {
                     var2 = SimpleBeanAdaptor.q == null ? (SimpleBeanAdaptor.q = SimpleBeanAdaptor.a(a("O>0X4I>(^4g&2\\"))) : SimpleBeanAdaptor.q;
                     if (!var6) {
                        break label104;
                     }
                  }

                  if (var2 == Short.TYPE) {
                     var2 = SimpleBeanAdaptor.r == null ? (SimpleBeanAdaptor.r = SimpleBeanAdaptor.a(a("O>0X4I>(^4v7)Kn"))) : SimpleBeanAdaptor.r;
                  }
               }

               Constructor var10 = var2.getConstructor(SimpleBeanAdaptor.s == null ? (SimpleBeanAdaptor.s = SimpleBeanAdaptor.a(a("O>0X4I>(^4v+4PtB"))) : SimpleBeanAdaptor.s);
               Object var4 = var10.newInstance(var1.toString());
               Method var5 = this.b.getWriteMethod();
               if (var5 != null) {
                  var5.invoke(this.a, var4);
               }

            } catch (InvocationTargetException var7) {
               throw var7.getTargetException();
            }
         } catch (SnmpRuntimeErrorException var8) {
            throw var8;
         } catch (Throwable var9) {
            if (SimpleBeanAdaptor.a.isDebugEnabled()) {
               SimpleBeanAdaptor.a.debug(a("@'%\\jQ6)W:C-)T:V:2M\u007fWef") + this.b.getWriteMethod().getName(), var9);
            }

            throw new SnmpValueException(a("V:2\u0019\u007fW-)K \u0005") + var9.getMessage());
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
                  var10003 = 37;
                  break;
               case 1:
                  var10003 = 95;
                  break;
               case 2:
                  var10003 = 70;
                  break;
               case 3:
                  var10003 = 57;
                  break;
               default:
                  var10003 = 26;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
