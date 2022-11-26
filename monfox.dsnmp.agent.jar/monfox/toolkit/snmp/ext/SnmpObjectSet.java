package monfox.toolkit.snmp.ext;

import java.io.Serializable;
import monfox.log.Logger;
import monfox.toolkit.snmp.NoSuchObjectException;
import monfox.toolkit.snmp.SnmpFramework;
import monfox.toolkit.snmp.SnmpOid;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.SnmpVarBind;
import monfox.toolkit.snmp.SnmpVarBindList;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.util.FormatUtil;

public abstract class SnmpObjectSet implements Serializable, Cloneable {
   public static final int NOTIFY_NONE = 0;
   public static final int NOTIFY_UPDATES = 1;
   public static final int NOTIFY_CHANGES = 2;
   public static final int NOTIFY_ALL = 3;
   public static final int VAR_MONITOR = 1;
   public static final int VAR_IGNORE = 2;
   static final int[] a = new int[0];
   private SnmpObjectSetListener b;
   private int[] c;
   private int d;
   private SnmpVarBindList e;
   private int f;
   private SnmpVarBindList g;
   private SnmpMetadata h;
   private int i;
   private long j;
   private SnmpOid k;
   private String l;
   static int m = 1;
   Logger n;
   private static final SnmpOid o = new SnmpOid(0L);
   private static final String p = "$Id: SnmpObjectSet.java,v 1.13 2009/08/17 19:36:54 sking Exp $";
   public static int q;

   SnmpObjectSet() {
      this.b = null;
      this.c = null;
      this.d = 0;
      this.e = null;
      this.f = 0;
      this.g = null;
      this.h = null;
      this.i = 3;
      this.j = -1L;
      this.k = o;
      this.l = "";
      this.n = Logger.getInstance(a("G1\u0004\u001aR"), a("F:\u001e"), a("P\f''Ma\b/4vP\u0007>"));
      this.n.debug(a("`\u0010/6vf\u0006j\u0004ln\u0012\u00055hf\u0001>\u0004gw"));
   }

   SnmpObjectSet(SnmpMetadata var1) {
      this();
      this.setMetadata(var1);
   }

   public void setMetadata(SnmpMetadata var1) {
      this.h = var1;
   }

   public SnmpMetadata getMetadata() {
      return this.h;
   }

   public int indexOf(String var1) throws SnmpValueException, NoSuchObjectException {
      SnmpOid var2 = new SnmpOid(this.h, var1);
      return this.indexOf(var2);
   }

   public int indexOf(SnmpOid var1) throws SnmpValueException, NoSuchObjectException {
      int var5 = q;
      SnmpObjectInfo[] var2 = this.infoSet();
      SnmpOidInfo var3 = var1.getOidInfo();
      int var4 = 0;

      while(true) {
         if (var4 < var2.length) {
            if (var5 != 0) {
               break;
            }

            if (var2[var4] == var3) {
               return var4;
            }

            ++var4;
            if (var5 == 0) {
               continue;
            }
         }

         this.n.error(a("j\f.2zL\u0004pw") + var1 + a("#\\j9m#\u0011?4j#\r(=g`\u0016"));
         break;
      }

      throw new NoSuchObjectException(a("M\rj$w`\nj8`i\u0007)#8#") + var1);
   }

   public SnmpValue getByName(String var1) throws SnmpValueException, NoSuchObjectException {
      int var6 = q;
      SnmpMetadata var2 = this.h;
      if (var2 == null) {
         var2 = SnmpFramework.getMetadata();
      }

      if (var2 == null) {
         throw new SnmpValueException(a("M\rj\u001agw\u0003.6vbB\u0019'g`\u000b,>gg"));
      } else {
         SnmpObjectInfo var3 = var2.getObject(var1);
         SnmpObjectInfo[] var4 = this.infoSet();
         int var5 = 0;

         while(true) {
            if (var5 < var4.length) {
               if (var6 != 0) {
                  break;
               }

               if (var4[var5] == var3) {
                  return this.getValue(var5);
               }

               ++var5;
               if (var6 == 0) {
                  continue;
               }
            }

            this.n.error(a("d\u0007>\u0015{M\u0003'28#") + var1 + a("#\\j9m#\u0011?4j#\r(=g`\u0016"));
            break;
         }

         throw new NoSuchObjectException(a("M\rj$w`\nj8`i\u0007)#8#") + var1);
      }
   }

   public SnmpValue getValue(String var1) throws SnmpValueException, NoSuchObjectException {
      return this.getValue(this.indexOf(var1));
   }

   public SnmpValue getValue(SnmpOid var1) throws SnmpValueException, NoSuchObjectException {
      return this.getValue(this.indexOf(var1));
   }

   public synchronized SnmpValue getValue(int var1) throws NoSuchObjectException {
      if (this.e == null) {
         this.n.error(a("d\u0007>\u0001co\u0017/m\"") + var1 + a("#\\j4c`\n/wkpB$\"no"));
         throw new NoSuchObjectException(a("L\u0000 2awB)6ak\u0007j9mwB#9kw\u000b+;ky\u0007."));
      } else {
         try {
            SnmpVarBindList var2 = this.e;
            SnmpVarBind var3 = var2.get(this.d + var1);
            return var3.getValue();
         } catch (ArrayIndexOutOfBoundsException var4) {
            this.n.error(a("d\u0007>\u0001co\u0017/wg{\u0001/'vj\r$"), var4);
            throw new NoSuchObjectException("[" + var1 + "]");
         }
      }
   }

   public SnmpVarBind getVarBind(int var1) throws NoSuchObjectException {
      if (this.e == null) {
         this.n.error(a("d\u0007>\u0001cq #9f9B") + var1 + a("#\\j4c`\n/wkpB$\"no"));
         throw new NoSuchObjectException(a("L\u0000 2awB)6ak\u0007j9mwB#9kw\u000b+;ky\u0007.y"));
      } else {
         try {
            SnmpVarBindList var2 = this.e;
            SnmpVarBind var3 = var2.get(this.d + var1);
            return var3;
         } catch (ArrayIndexOutOfBoundsException var4) {
            this.n.error(a("d\u0007>\u0001cq #9f#\u000724gs\u0016#8l"), var4);
            throw new NoSuchObjectException("[" + var1 + "]");
         }
      }
   }

   public SnmpValue getLast(int var1) throws NoSuchObjectException {
      SnmpVarBindList var2 = null;
      boolean var3 = true;
      int var8;
      synchronized(this) {
         var8 = this.f;
         var2 = this.g;
      }

      if (var2 == null) {
         this.n.error(a("d\u0007>\u001bcp\u0016pw") + var1 + a("#\\j;cp\u0016j\u0001@OB#$\"m\u0017&;"));
         throw new NoSuchObjectException(a("L\u0000 2awB)6ak\u0007j9mwB#9kw\u000b+;ky\u0007.y"));
      } else {
         try {
            SnmpVarBind var4 = var2.get(var8 + var1);
            return var4.getValue();
         } catch (ArrayIndexOutOfBoundsException var6) {
            this.n.error(a("d\u0007>\u001bcp\u0016j2z`\u0007:#kl\f"), var6);
            throw new NoSuchObjectException("[" + var1 + "]");
         }
      }
   }

   public int size() {
      return this.infoSet().length;
   }

   public SnmpObjectInfo getInfo(int var1) {
      return this.infoSet()[var1];
   }

   public String getName(int var1) {
      return this.infoSet()[var1].getName();
   }

   public SnmpOid getOid(int var1) {
      return this.infoSet()[var1].getOid();
   }

   public synchronized long getTimestamp() {
      return this.j;
   }

   public SnmpOid getRawIndex() {
      return this.k;
   }

   protected void setRawIndex(SnmpOid var1) {
      this.k = var1;
   }

   public String toString() {
      return this.toString(true);
   }

   public String toString(boolean var1) {
      StringBuffer var2 = new StringBuffer();
      this.toString(var2, var1);
      return var2.toString();
   }

   public void toString(StringBuffer var1) {
      this.toString(var1, true);
   }

   public void toString(StringBuffer var1, boolean var2) {
      int var10 = q;
      SnmpObjectInfo[] var3 = this.infoSet();
      int var4 = var3.length;
      Object[][] var5 = new Object[2][var4];
      String var6 = null;
      int var7;
      if (var2 && this.getName() != null) {
         var7 = this.getName().indexOf(a("W\u0003(;g"));
         if (var7 > 0) {
            var6 = this.getName().substring(0, var7);
         }
      }

      var7 = 0;

      while(true) {
         if (var7 < var3.length) {
            String var8 = var3[var7].getName();
            if (var10 != 0) {
               break;
            }

            label49: {
               if (var6 != null && var8.startsWith(var6)) {
                  String var9 = var8.substring(var6.length());
                  var5[0][var7] = var9.substring(0, 1).toLowerCase() + var9.substring(1);
                  if (var10 == 0) {
                     break label49;
                  }
               }

               var5[0][var7] = var8;
            }

            ++var7;
            if (var10 == 0) {
               continue;
            }
         }

         var7 = 0;
         break;
      }

      while(true) {
         if (var7 < var4) {
            label36: {
               try {
                  SnmpVarBind var12 = this.getVarBind(var7);
                  var5[1][var7] = var12.getValueString();
               } catch (NoSuchObjectException var11) {
                  var5[1][var7] = null;
                  break label36;
               }

               if (var10 != 0) {
                  break;
               }
            }

            ++var7;
            if (var10 == 0) {
               continue;
            }
         }

         FormatUtil.formatTable(var5, var1);
         break;
      }

   }

   protected void updateValues(SnmpVarBindList var1, int var2, long var3) {
      this.updateValues(var1, var2, var3, this.infoSet(), this.varMask());
   }

   protected void updateValues(SnmpVarBindList var1, int var2, long var3, SnmpObjectInfo[] var5, int[] var6) {
      int var17 = q;
      if (this.n.isDebugEnabled()) {
         this.n.debug(a("v\u0012.6vf4+;wf\u0011pwvj\u000f/j") + var3 + a("/\u0007#3z>") + var2 + a("/B<5n>") + var1);
      }

      if (this.c == null) {
         this.c = new int[var1.size()];
      }

      int var7 = 0;
      int[] var8 = null;
      synchronized(this) {
         label118: {
            this.j = var3;
            if (var1 == null) {
               return;
            }

            boolean var10;
            label101: {
               var10 = false;
               if (var5 != this.infoSet()) {
                  if (var6 == a) {
                     this.n.debug(a("j\f,8/p\u0007>wak\u0003$0ggNj9mwB?'fb\u0016#9e#\u0014+;wf\u0011"));
                     return;
                  }

                  this.updateInfoSet(var5, var6);
                  this.g = this.e;
                  this.f = this.d;
                  var10 = true;
                  if (var17 == 0) {
                     break label101;
                  }
               }

               this.g = this.e;
               this.f = this.d;
            }

            var6 = this.varMask();
            this.e = var1;
            this.d = var2;
            if (this.n.isDebugEnabled()) {
               this.n.debug(a("v\u0012.6vj\f-wav\u00108\u0001cq #9fO\u000b9#8#") + var1);
            }

            int[] var10000;
            label112: {
               if ((this.i & 2) != 0) {
                  int var11 = 0;

                  while(var11 < var5.length) {
                     var10000 = var6;
                     if (var17 != 0) {
                        break label112;
                     }

                     if (var6 == null || (var6[var11] & 2) == 0) {
                        SnmpVarBind var12 = this.e.get(var11 + this.d);
                        SnmpValue var13 = var12.getValue();
                        SnmpValue var14 = null;
                        if (this.g != null) {
                           if (var10) {
                              SnmpVarBind var15 = this.g.get(var12.getOid());
                              if (var15 != null) {
                                 var14 = var15.getValue();
                              }

                              if (this.n.isDebugEnabled()) {
                                 this.n.debug(a("`\r&\"om\u0011j4jb\f-2f/B82vq\u000b/!km\u0005j3cw\u0003j#jfB\"6pgB=6{9B") + var12.getOid() + a("#_j") + var14);
                              }
                           } else {
                              var14 = this.g.get(var11 + this.f).getValue();
                           }
                        }

                        if (var14 == null || var13 == null || !var14.equals(var13)) {
                           this.c[var7++] = var11;
                        }
                     }

                     ++var11;
                     if (var17 != 0) {
                        break;
                     }
                  }
               }

               if (var7 <= 0) {
                  break label118;
               }

               var10000 = new int[var7];
            }

            var8 = var10000;
            System.arraycopy(this.c, 0, var8, 0, var7);
         }
      }

      if (var7 > 0 || (this.i & 1) != 0) {
         this.notifyUpdated(var8);
      }

   }

   protected void notifyError(SnmpError var1) {
      if (this.b != null) {
         this.b.handleError(this, var1);
      }

   }

   protected void notifyUpdated(int[] var1) {
      if (this.b != null) {
         this.b.handleUpdated(this, var1);
      }

   }

   public void addObjectSetListener(SnmpObjectSetListener var1) {
      this.b = monfox.toolkit.snmp.ext.c.add(this.b, var1);
   }

   public void removeObjectSetListener(SnmpObjectSetListener var1) {
      this.b = monfox.toolkit.snmp.ext.c.remove(this.b, var1);
   }

   public int getNotificationMask() {
      return this.i;
   }

   public void setNotificationMask(int var1) {
      this.i = var1;
   }

   protected abstract SnmpObjectInfo[] infoSet();

   protected int[] varMask() {
      return null;
   }

   protected void updateInfoSet(SnmpObjectInfo[] var1, int[] var2) {
   }

   public String getName() {
      return this.l;
   }

   public void setName(String var1) {
      this.l = var1;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 3;
               break;
            case 1:
               var10003 = 98;
               break;
            case 2:
               var10003 = 74;
               break;
            case 3:
               var10003 = 87;
               break;
            default:
               var10003 = 2;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
