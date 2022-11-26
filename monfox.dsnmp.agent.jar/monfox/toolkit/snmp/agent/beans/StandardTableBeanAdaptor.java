package monfox.toolkit.snmp.agent.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.SnmpException;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.agent.SnmpMib;
import monfox.toolkit.snmp.agent.SnmpMibException;
import monfox.toolkit.snmp.agent.SnmpMibLeaf;
import monfox.toolkit.snmp.agent.SnmpMibLeafFactory;
import monfox.toolkit.snmp.agent.SnmpMibTable;
import monfox.toolkit.snmp.agent.SnmpRuntimeErrorException;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpTableInfo;

public class StandardTableBeanAdaptor extends SnmpBeanAdaptor {
   private static Logger a = Logger.getInstance(b("\r#S<s"), b("\u000b5\\?\u000e\b4\\!w\u0006\"N"), b("\u001a\u0004|\u001fG(\u0002y%B+\u001cx3F(\u001e\\\u0015B9\u0004r\u0003"));
   private Class b = null;
   private SnmpMibTable c = null;
   private Vector d = new Vector();
   private boolean e = false;
   private SnmpTableInfo f = null;
   private SnmpObjectInfo[] g = null;
   private PropertyDescriptor[] h = null;
   private SnmpObjectInfo[] i = null;
   private PropertyDescriptor[] j = null;
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

   public StandardTableBeanAdaptor(SnmpMib var1, Class var2, String var3) throws SnmpValueException {
      super(var1);
      this.b = var2;
      if (var3 != null) {
         this.f = var1.getMetadata().getTable(var3);
      }

   }

   public void attach() throws SnmpValueException, SnmpMibException, IntrospectionException {
      boolean var9 = SnmpBeanAdaptor.A;
      SnmpMetadata var1 = this.getMib().getMetadata();
      BeanInfo var2 = Introspector.getBeanInfo(this.b);
      PropertyDescriptor[] var3 = var2.getPropertyDescriptors();
      Hashtable var4 = new Hashtable();
      int var5 = 0;

      StandardTableBeanAdaptor var10000;
      while(true) {
         if (var5 < var3.length) {
            PropertyDescriptor var6 = var3[var5];
            String var7 = var6.getName();
            String var8 = var7;
            var10000 = this;
            if (var9) {
               break;
            }

            label61: {
               if (this.getNameMap() != null && this.getNameMap().containsKey(var7)) {
                  var8 = (String)this.getNameMap().get(var7);
                  if (!var9) {
                     break label61;
                  }
               }

               if (this.getBaseName() != null) {
                  var8 = this.getBaseName() + var7.substring(0, 1).toUpperCase() + var7.substring(1);
               }
            }

            var4.put(var8, var6);
            ++var5;
            if (!var9) {
               continue;
            }
         }

         this.g = this.f.getColumns();
         var10000 = this;
         break;
      }

      Vector var10 = var10000.a(var4, this.g);
      this.h = new PropertyDescriptor[var10.size()];
      int var11 = 0;

      while(true) {
         if (var11 < this.h.length) {
            this.h[var11] = (PropertyDescriptor)var10.elementAt(var11);
            ++var11;
            if (!var9 || !var9) {
               continue;
            }
            break;
         }

         this.i = this.f.getIndexes();
         break;
      }

      Vector var12 = this.a(var4, this.i);
      this.j = new PropertyDescriptor[var12.size()];
      int var13 = 0;

      while(true) {
         if (var13 < this.j.length) {
            this.j[var13] = (PropertyDescriptor)var12.elementAt(var13);
            ++var13;
            if (var9) {
               break;
            }

            if (!var9) {
               continue;
            }
         }

         this.c = new SnmpMibTable(this.f.getOid());
         this.getMib().add(this.c);
         this.e = true;
         break;
      }

      if (SnmpException.b) {
         SnmpBeanAdaptor.A = !var9;
      }

   }

   private Vector a(Hashtable var1, SnmpObjectInfo[] var2) throws SnmpMibException, SnmpValueException, IntrospectionException {
      Vector var3 = new Vector();
      int var4 = 0;

      while(var4 < var2.length) {
         SnmpObjectInfo var5 = var2[var4];
         PropertyDescriptor var6 = (PropertyDescriptor)var1.get(var5.getName());
         if (var6 == null) {
            throw new SnmpMibException(b("\u0004\u0019n\u0002J'\u0017=\u0001Q&\u0000x\u0003W0P{\u001eQi\u0013r\u001dV$\u001e=V") + var5.getName() + "'");
         }

         this.a(var6, var5);
         var3.addElement(var6);
         ++var4;
         if (SnmpBeanAdaptor.A) {
            break;
         }
      }

      return var3;
   }

   public synchronized void removeAll() throws SnmpMibException, SnmpValueException {
      if (!this.e) {
         throw new SnmpMibException(b("=\u0011\u007f\u001dFiW") + this.f.getName() + b("nPs\u001eWi\u0011i\u0005B*\u0018x\u0015\ri=h\u0002Wi\u0011i\u0005B*\u0018=\u0005B+\u001cxQW&PN\u001fN9=t\u0013\u00039\u0002t\u001eQi\u0004rQQ,\u001dr\u0007J'\u0017=\u0003L>\u00033"));
      } else {
         while(this.d.size() > 0) {
            this.removeRow(this.d.lastElement());
            if (SnmpBeanAdaptor.A) {
               break;
            }
         }

      }
   }

   public synchronized void removeRow(Object var1) throws SnmpMibException, SnmpValueException {
      boolean var4 = SnmpBeanAdaptor.A;
      if (!this.e) {
         throw new SnmpMibException(b("=\u0011\u007f\u001dFiW") + this.f.getName() + b("nPs\u001eWi\u0011i\u0005B*\u0018x\u0015\ri=h\u0002Wi\u0011i\u0005B*\u0018=\u0005B+\u001cxQW&PN\u001fN9=t\u0013\u00039\u0002t\u001eQi\u0004rQQ,\u001dr\u0007J'\u0017=\u0003L>\u00033"));
      } else {
         SnmpValue[] var2 = new SnmpValue[this.i.length];
         int var3 = 0;

         while(true) {
            if (var3 < this.i.length) {
               var2[var3] = this.a(this.j[var3], this.i[var3], var1);
               ++var3;
               if (var4) {
                  break;
               }

               if (!var4) {
                  continue;
               }
            }

            this.c.removeRow(var2);
            this.d.removeElement(var1);
            break;
         }

      }
   }

   public synchronized void addRow(Object var1) throws SnmpMibException, SnmpValueException {
      if (!this.e) {
         throw new SnmpMibException(b("=\u0011\u007f\u001dFiW") + this.f.getName() + b("nPs\u001eWi\u0011i\u0005B*\u0018x\u0015\ri=h\u0002Wi\u0011i\u0005B*\u0018=\u0005B+\u001cxQW&PN\u001fN9=t\u0013\u00039\u0002t\u001eQi\u0004rQB-\u0014t\u001fDi\u0002r\u0006Pg"));
      } else {
         SnmpValue[] var2 = new SnmpValue[this.i.length];
         int var3 = 0;

         while(var3 < this.i.length) {
            var2[var3] = this.a(this.j[var3], this.i[var3], var1);
            ++var3;
            if (SnmpBeanAdaptor.A) {
               break;
            }
         }

         BeanMibLeafFactory var5 = new BeanMibLeafFactory(var1);
         this.c.setDefaultFactory(var5);
         this.c.addRow(var2);
         this.c.setDefaultFactory((SnmpMibLeafFactory)null);
         this.d.addElement(var1);
      }
   }

   public SnmpMibTable getMibTable() {
      return this.c;
   }

   private SnmpValue a(PropertyDescriptor var1, SnmpObjectInfo var2, Object var3) throws SnmpValueException {
      try {
         Method var4 = var1.getReadMethod();
         Object var5 = var4.invoke(var3);
         if (var5 instanceof SnmpValue) {
            return (SnmpValue)var5;
         } else {
            if (var5 == null) {
               var5 = "";
            }

            return SnmpValue.getInstance(var2, var5.toString());
         }
      } catch (Exception var6) {
         var6.printStackTrace();
         throw new SnmpValueException(b("9\u0002r\u0001F;\u0004dQF;\u0002r\u0003\u0019i") + var1.getName());
      }
   }

   public Class getBeanClass() {
      return this.b;
   }

   void a(SnmpTableInfo var1) {
      this.f = var1;
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
               var10003 = 73;
               break;
            case 1:
               var10003 = 112;
               break;
            case 2:
               var10003 = 29;
               break;
            case 3:
               var10003 = 113;
               break;
            default:
               var10003 = 35;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   static class BeanMibLeaf extends SnmpMibLeaf {
      private Object a;
      private PropertyDescriptor b;

      BeanMibLeaf(SnmpOid var1, SnmpOid var2, Object var3, PropertyDescriptor var4, SnmpMetadata var5) throws SnmpValueException, SnmpMibException {
         super(var1, var2);
         this.a = var3;
         this.b = var4;
      }

      public SnmpValue getValue() {
         try {
            try {
               Method var1 = this.b.getReadMethod();
               Object var2 = var1.invoke(this.a);
               if (var2 instanceof SnmpValue) {
                  return (SnmpValue)var2;
               } else {
                  if (var2 == null) {
                     var2 = "";
                  }

                  return SnmpValue.getInstance(this._objectInfo, var2.toString());
               }
            } catch (InvocationTargetException var3) {
               throw var3.getTargetException();
            }
         } catch (SnmpRuntimeErrorException var4) {
            throw var4;
         } catch (Throwable var5) {
            StandardTableBeanAdaptor.a.debug(a("=$X:Q,5T1\u0001>.T2\u0001?9O+D*f\u001b") + this.b.getReadMethod().getName());
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
                     var2 = StandardTableBeanAdaptor.m == null ? (StandardTableBeanAdaptor.m = StandardTableBeanAdaptor.a(a("2=M>\u000f4=U8\u000f\u00112O:F=."))) : StandardTableBeanAdaptor.m;
                     if (!var6) {
                        break label104;
                     }
                  }

                  if (var2 == Long.TYPE) {
                     var2 = StandardTableBeanAdaptor.n == null ? (StandardTableBeanAdaptor.n = StandardTableBeanAdaptor.a(a("2=M>\u000f4=U8\u000f\u00143U8"))) : StandardTableBeanAdaptor.n;
                     if (!var6) {
                        break label104;
                     }
                  }

                  if (var2 == Double.TYPE) {
                     var2 = StandardTableBeanAdaptor.o == null ? (StandardTableBeanAdaptor.o = StandardTableBeanAdaptor.a(a("2=M>\u000f4=U8\u000f\u001c3N=M="))) : StandardTableBeanAdaptor.o;
                     if (!var6) {
                        break label104;
                     }
                  }

                  if (var2 == Float.TYPE) {
                     var2 = StandardTableBeanAdaptor.p == null ? (StandardTableBeanAdaptor.p = StandardTableBeanAdaptor.a(a("2=M>\u000f4=U8\u000f\u001e0T>U"))) : StandardTableBeanAdaptor.p;
                     if (!var6) {
                        break label104;
                     }
                  }

                  if (var2 == Byte.TYPE) {
                     var2 = StandardTableBeanAdaptor.q == null ? (StandardTableBeanAdaptor.q = StandardTableBeanAdaptor.a(a("2=M>\u000f4=U8\u000f\u001a%O:"))) : StandardTableBeanAdaptor.q;
                     if (!var6) {
                        break label104;
                     }
                  }

                  if (var2 == Short.TYPE) {
                     var2 = StandardTableBeanAdaptor.r == null ? (StandardTableBeanAdaptor.r = StandardTableBeanAdaptor.a(a("2=M>\u000f4=U8\u000f\u000b4T-U"))) : StandardTableBeanAdaptor.r;
                  }
               }

               Constructor var10 = var2.getConstructor(StandardTableBeanAdaptor.s == null ? (StandardTableBeanAdaptor.s = StandardTableBeanAdaptor.a(a("2=M>\u000f4=U8\u000f\u000b(I6O?"))) : StandardTableBeanAdaptor.s);
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
            if (StandardTableBeanAdaptor.a.isDebugEnabled()) {
               StandardTableBeanAdaptor.a.debug(a("=$X:Q,5T1\u0001>.T2\u0001+9O+D*f\u001b") + this.b.getWriteMethod().getName(), var9);
            }

            throw new SnmpValueException(a("+9O\u007fD*.T-\u001bx") + var9.getMessage());
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
                  var10003 = 88;
                  break;
               case 1:
                  var10003 = 92;
                  break;
               case 2:
                  var10003 = 59;
                  break;
               case 3:
                  var10003 = 95;
                  break;
               default:
                  var10003 = 33;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }

   class BeanMibLeafFactory implements SnmpMibLeafFactory {
      private Object a = null;

      BeanMibLeafFactory(Object var2) {
         this.a = var2;
      }

      public SnmpMibLeaf getInstance(SnmpMibTable var1, SnmpOid var2, SnmpOid var3) throws SnmpMibException, SnmpValueException {
         int var4 = var2.intValue() - 1;
         return new BeanMibLeaf(var2, var3, this.a, StandardTableBeanAdaptor.this.h[var4], StandardTableBeanAdaptor.this.getMib().getMetadata());
      }
   }
}
