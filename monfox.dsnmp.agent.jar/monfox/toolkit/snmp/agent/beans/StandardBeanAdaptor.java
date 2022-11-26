package monfox.toolkit.snmp.agent.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Vector;
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
import monfox.toolkit.snmp.metadata.SnmpObjectGroupInfo;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;

public class StandardBeanAdaptor extends SnmpBeanAdaptor {
   private Object a;
   private Class b;
   private String l;
   private Vector c;
   private SnmpOid d;
   private SnmpObjectGroupInfo e;
   private boolean f;
   private static Logger g = Logger.getInstance(b("N8H#<"), b("H.G AK/G>8E9U"), b("Y\u001fg\u0000\bk\u0019b,\tk\u0005G\n\rz\u001fi\u001c"));
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

   public StandardBeanAdaptor(SnmpMib var1, Object var2) {
      this(var1, var2, (Class)null);
   }

   public StandardBeanAdaptor(SnmpMib var1, Object var2, Class var3) {
      super(var1);
      this.a = null;
      this.b = null;
      this.l = null;
      this.c = null;
      this.d = null;
      this.e = null;
      this.f = false;
      this.a = var2;
      this.b = var3;
   }

   public void setGroupName(String var1) throws SnmpValueException {
      this.e = null;
      this.d = null;
      if (var1 != null) {
         try {
            this.e = this.getMib().getMetadata().getObjectGroup(var1);
         } catch (SnmpValueException var3) {
            this.d = new SnmpOid(this.getMib().getMetadata(), var1);
         }
      }

      this.l = var1;
   }

   public String getGroupName() {
      return this.l;
   }

   public synchronized String[] attach() throws SnmpValueException, SnmpMibException, IntrospectionException {
      boolean var15 = SnmpBeanAdaptor.A;
      if (this.f) {
         throw new SnmpMibException(b("K\u0007t\u000b\rn\u0012&\u000f\u0018~\ne\u0006\tn"));
      } else {
         this.c = new Vector();
         SnmpMetadata var1 = this.getMib().getMetadata();
         Vector var2 = new Vector();
         Class var3 = this.a.getClass();
         if (this.b != null) {
            if (!this.b.isAssignableFrom(var3)) {
               throw new IntrospectionException(b("I\u0004t\u000bLc\u0005r\u000b\u001el\ne\u000bV*L") + this.b.getName() + b("-Ko\u001dLd\u0004rN\rdKo\u0000\u0018o\u0019`\u000f\u000foKi\bLH\u000eg\u0000Li\u0007g\u001d\u001f*L") + var3.getName() + "'");
            }

            var3 = this.b;
            if (var15) {
               throw new IntrospectionException(b("I\u0004t\u000bLc\u0005r\u000b\u001el\ne\u000bV*L") + this.b.getName() + b("-Ko\u001dLd\u0004rN\rdKo\u0000\u0018o\u0019`\u000f\u000foKi\bLH\u000eg\u0000Li\u0007g\u001d\u001f*L") + var3.getName() + "'");
            }
         }

         BeanInfo var4 = Introspector.getBeanInfo(var3);
         PropertyDescriptor[] var5 = var4.getPropertyDescriptors();
         int var6 = 1;
         int var7 = 0;

         while(var7 < var5.length) {
            label275: {
               PropertyDescriptor var8;
               String var9;
               SnmpObjectInfo var11;
               label293: {
                  boolean var12;
                  label273: {
                     var8 = var5[var7];
                     var9 = var8.getName();
                     String var10 = var9;
                     SnmpObjectInfo[] var13;
                     int var14;
                     if (this.getNameMap() != null) {
                        if (this.getNameMap().containsKey(var9)) {
                           var10 = (String)this.getNameMap().get(var9);
                           if (var15) {
                              if (this.getBaseName() != null) {
                                 var10 = this.getBaseName() + var9.substring(0, 1).toUpperCase() + var9.substring(1);
                              }

                              var11 = null;

                              try {
                                 var11 = var1.getObject(var10);
                              } catch (SnmpValueException var20) {
                                 if (!var15) {
                                    break label275;
                                 }
                              }

                              if (this.e == null) {
                                 if (this.d != null && !this.d.contains(var11.getOid()) && !var15) {
                                    break label275;
                                 }
                                 break label293;
                              }

                              var12 = false;
                              var13 = this.e.getObjects();
                              var14 = 0;
                              if (var14 >= var13.length) {
                                 break label273;
                              }
                           } else {
                              var11 = null;

                              try {
                                 var11 = var1.getObject(var10);
                              } catch (SnmpValueException var21) {
                                 if (!var15) {
                                    break label275;
                                 }
                              }

                              if (this.e == null) {
                                 if (this.d != null && !this.d.contains(var11.getOid()) && !var15) {
                                    break label275;
                                 }
                                 break label293;
                              }

                              var12 = false;
                              var13 = this.e.getObjects();
                              var14 = 0;
                              if (var14 >= var13.length) {
                                 break label273;
                              }
                           }
                        } else {
                           if (this.getBaseName() != null) {
                              var10 = this.getBaseName() + var9.substring(0, 1).toUpperCase() + var9.substring(1);
                           }

                           var11 = null;

                           try {
                              var11 = var1.getObject(var10);
                           } catch (SnmpValueException var19) {
                              if (!var15) {
                                 break label275;
                              }
                           }

                           if (this.e == null) {
                              if (this.d != null && !this.d.contains(var11.getOid()) && !var15) {
                                 break label275;
                              }
                              break label293;
                           }

                           var12 = false;
                           var13 = this.e.getObjects();
                           var14 = 0;
                           if (var14 >= var13.length) {
                              break label273;
                           }
                        }
                     } else {
                        if (this.getBaseName() != null) {
                           var10 = this.getBaseName() + var9.substring(0, 1).toUpperCase() + var9.substring(1);
                        }

                        var11 = null;

                        try {
                           var11 = var1.getObject(var10);
                        } catch (SnmpValueException var18) {
                           if (!var15) {
                              break label275;
                           }
                        }

                        if (this.e == null) {
                           if (this.d != null && !this.d.contains(var11.getOid()) && !var15) {
                              break label275;
                           }
                           break label293;
                        }

                        var12 = false;
                        var13 = this.e.getObjects();
                        var14 = 0;
                        if (var14 >= var13.length) {
                           break label273;
                        }
                     }

                     label272:
                     do {
                        while(true) {
                           SnmpObjectInfo var10000 = var13[var14];
                           if (!var15) {
                              if (var10000 == var11) {
                                 var12 = true;
                                 if (!var15) {
                                    break label272;
                                 }
                              }

                              ++var14;
                              break;
                           }

                           var10 = (String)var10000;
                           if (var15) {
                              if (this.getBaseName() != null) {
                                 var10 = this.getBaseName() + var9.substring(0, 1).toUpperCase() + var9.substring(1);
                              }

                              var11 = null;

                              try {
                                 var11 = var1.getObject(var10);
                              } catch (SnmpValueException var16) {
                                 if (!var15) {
                                    break label275;
                                 }
                              }

                              if (this.e == null) {
                                 if (this.d != null && !this.d.contains(var11.getOid()) && !var15) {
                                    break label275;
                                 }
                                 break label293;
                              }

                              var12 = false;
                              var13 = this.e.getObjects();
                              var14 = 0;
                              if (var14 >= var13.length) {
                                 break label272;
                              }
                           } else {
                              var11 = null;

                              try {
                                 var11 = var1.getObject(var10);
                              } catch (SnmpValueException var17) {
                                 if (!var15) {
                                    break label275;
                                 }
                              }

                              if (this.e == null) {
                                 if (this.d != null && !this.d.contains(var11.getOid()) && !var15) {
                                    break label275;
                                 }
                                 break label293;
                              }

                              var12 = false;
                              var13 = this.e.getObjects();
                              var14 = 0;
                              if (var14 >= var13.length) {
                                 break label272;
                              }
                           }
                        }
                     } while(!var15 && var14 < var13.length);
                  }

                  if (!var12 && !var15 || var15 && this.d != null && !this.d.contains(var11.getOid()) && !var15) {
                     break label275;
                  }
               }

               this.a(var8, var11);
               var2.addElement(var9);
               BeanMibLeaf var24 = new BeanMibLeaf(var1, var11, this.a, var8);
               this.c.addElement(var24);
               this.getMib().add(var24);
               ++var6;
            }

            ++var7;
            if (var15) {
               break;
            }
         }

         String[] var22 = new String[var2.size()];
         int var23 = 0;

         while(true) {
            if (var23 < var22.length) {
               var22[var23] = (String)var2.elementAt(var23);
               ++var23;
               if (!var15 || !var15) {
                  continue;
               }
               break;
            }

            this.f = true;
            break;
         }

         return var22;
      }
   }

   public synchronized void detach() {
      boolean var3 = SnmpBeanAdaptor.A;
      if (this.f) {
         Enumeration var1 = this.c.elements();

         while(true) {
            if (var1.hasMoreElements()) {
               SnmpMibLeaf var2 = (SnmpMibLeaf)var1.nextElement();
               this.getMib().remove((SnmpMibNode)var2);
               if (var3) {
                  break;
               }

               if (!var3) {
                  continue;
               }
            }

            this.f = false;
            break;
         }

      }
   }

   public Class getCoreInterface() {
      return this.b;
   }

   public Object getObject() {
      return this.a;
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
               var10003 = 10;
               break;
            case 1:
               var10003 = 107;
               break;
            case 2:
               var10003 = 6;
               break;
            case 3:
               var10003 = 110;
               break;
            default:
               var10003 = 108;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }

   static class BeanMibLeaf extends SnmpMibLeaf {
      private Object a;
      private PropertyDescriptor b;

      BeanMibLeaf(SnmpMetadata var1, SnmpObjectInfo var2, Object var3, PropertyDescriptor var4) throws SnmpValueException, SnmpMibException {
         super(var2.getOid());
         this.a = var3;
         this.b = var4;
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
            if (StandardBeanAdaptor.g.isDebugEnabled()) {
               StandardBeanAdaptor.g.debug(a("\u001a.\u0003\u0000,\u000b?\u000f\u000b|\u0019$\u000f\b|\u00183\u0014\u00119\rl@") + this.b.getReadMethod().getName(), var5);
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
                     var2 = StandardBeanAdaptor.m == null ? (StandardBeanAdaptor.m = StandardBeanAdaptor.a(a("\u00157\u0016\u0004r\u00137\u000e\u0002r68\u0014\u0000;\u001a$"))) : StandardBeanAdaptor.m;
                     if (!var6) {
                        break label104;
                     }
                  }

                  if (var2 == Long.TYPE) {
                     var2 = StandardBeanAdaptor.n == null ? (StandardBeanAdaptor.n = StandardBeanAdaptor.a(a("\u00157\u0016\u0004r\u00137\u000e\u0002r39\u000e\u0002"))) : StandardBeanAdaptor.n;
                     if (!var6) {
                        break label104;
                     }
                  }

                  if (var2 == Double.TYPE) {
                     var2 = StandardBeanAdaptor.o == null ? (StandardBeanAdaptor.o = StandardBeanAdaptor.a(a("\u00157\u0016\u0004r\u00137\u000e\u0002r;9\u0015\u00070\u001a"))) : StandardBeanAdaptor.o;
                     if (!var6) {
                        break label104;
                     }
                  }

                  if (var2 == Float.TYPE) {
                     var2 = StandardBeanAdaptor.p == null ? (StandardBeanAdaptor.p = StandardBeanAdaptor.a(a("\u00157\u0016\u0004r\u00137\u000e\u0002r9:\u000f\u0004("))) : StandardBeanAdaptor.p;
                     if (!var6) {
                        break label104;
                     }
                  }

                  if (var2 == Byte.TYPE) {
                     var2 = StandardBeanAdaptor.q == null ? (StandardBeanAdaptor.q = StandardBeanAdaptor.a(a("\u00157\u0016\u0004r\u00137\u000e\u0002r=/\u0014\u0000"))) : StandardBeanAdaptor.q;
                     if (!var6) {
                        break label104;
                     }
                  }

                  if (var2 == Short.TYPE) {
                     var2 = StandardBeanAdaptor.r == null ? (StandardBeanAdaptor.r = StandardBeanAdaptor.a(a("\u00157\u0016\u0004r\u00137\u000e\u0002r,>\u000f\u0017("))) : StandardBeanAdaptor.r;
                  }
               }

               Constructor var10 = var2.getConstructor(StandardBeanAdaptor.s == null ? (StandardBeanAdaptor.s = StandardBeanAdaptor.a(a("\u00157\u0016\u0004r\u00137\u000e\u0002r,\"\u0012\f2\u0018"))) : StandardBeanAdaptor.s);
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
            if (StandardBeanAdaptor.g.isDebugEnabled()) {
               StandardBeanAdaptor.g.debug(a("\u001a.\u0003\u0000,\u000b?\u000f\u000b|\u0019$\u000f\b|\f3\u0014\u00119\rl@") + this.b.getWriteMethod().getName(), var9);
            }

            throw new SnmpValueException(a("\f3\u0014E9\r$\u000f\u0017f_") + var9.getMessage());
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
                  var10003 = 127;
                  break;
               case 1:
                  var10003 = 86;
                  break;
               case 2:
                  var10003 = 96;
                  break;
               case 3:
                  var10003 = 101;
                  break;
               default:
                  var10003 = 92;
            }

            var1[var3] = (char)(var10002 ^ var10003);
         }

         return new String(var1);
      }
   }
}
