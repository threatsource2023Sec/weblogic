package monfox.toolkit.snmp;

import java.math.BigInteger;
import java.util.Enumeration;
import java.util.StringTokenizer;
import java.util.Vector;
import monfox.log.Logger;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpMetadataException;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.metadata.SnmpTableInfo;

public final class SnmpOid extends SnmpValue {
   static final long serialVersionUID = -2312612984187828966L;
   private static final int INITIAL_SIZE = 16;
   private transient SnmpOidInfo a;
   private static Logger _log = Logger.getInstance(a("tUqqy"), a("sImy"), a("chRLfYb"));
   private static SnmpVarBind[] EMPTY_VB_LIST;
   private transient int b;
   long[] _components;
   int _componentCount;
   private boolean _isStatic;
   private transient SnmpMetadata c;
   private static final String _ident = "$Id: SnmpOid.java,v 1.33 2008/11/25 13:31:51 sking Exp $";

   public SnmpOid() {
      this.a = null;
      this.b = 0;
      this._components = null;
      this._componentCount = 0;
      this._isStatic = false;
      this.c = null;
      this._components = new long[16];
      this._componentCount = 0;
   }

   public SnmpOid(SnmpMetadata var1) {
      this.a = null;
      this.b = 0;
      this._components = null;
      this._componentCount = 0;
      this._isStatic = false;
      this.c = null;
      this._components = new long[16];
      this._componentCount = 0;
      this.c = var1;
   }

   public SnmpOid(long var1) {
      this.a = null;
      this.b = 0;
      this._components = null;
      this._componentCount = 0;
      this._isStatic = false;
      this.c = null;
      this._components = new long[16];
      this._components[0] = var1;
      this._componentCount = 1;
   }

   public SnmpOid(long var1, long var3, long var5, long var7) {
      boolean var9 = SnmpValue.b;
      super();
      this.a = null;
      this.b = 0;
      this._components = null;
      this._componentCount = 0;
      this._isStatic = false;
      this.c = null;
      this._components = new long[]{var1, var3, var5, var7};
      this._componentCount = 4;
      if (SnmpException.b) {
         SnmpValue.b = !var9;
      }

   }

   public SnmpOid(String var1) throws SnmpValueException {
      this((SnmpMetadata)null, (String)var1);
   }

   public SnmpOid(SnmpMetadata var1, String var2) throws SnmpValueException {
      this.a = null;
      this.b = 0;
      this._components = null;
      this._componentCount = 0;
      this._isStatic = false;
      this.c = null;
      this.c = var1;
      this.fromString(var2);
   }

   public void fromString(String var1) throws SnmpValueException {
      boolean var11 = SnmpValue.b;
      Vector var2 = b(var1);
      Enumeration var3 = var2.elements();
      int var4 = var2.size();
      if (var4 == 0) {
         this._components = new long[0];
         this._componentCount = 0;
      } else {
         int var5;
         String var6;
         label66: {
            this._components = null;
            var5 = 0;
            var6 = (String)var3.nextElement();
            if (Character.isDigit(var6.charAt(0))) {
               this._components = new long[var4];
               this._components[var5++] = Long.parseLong(var6);
               if (!var11) {
                  break label66;
               }
            }

            SnmpMetadata var7 = this.b();
            if (var7 != null) {
               SnmpOidInfo var8 = var7.resolveName(var6);
               SnmpOid var9 = var8.getOid();
               this._components = new long[var4 - 1 + var9.getLength()];
               long[] var10 = var9.toLongArray(false);
               System.arraycopy(var10, 0, this._components, 0, var9.getLength());
               var5 = var9.getLength();
               if (!var11) {
                  break label66;
               }
            }

            throw new SnmpValueException(a("sgQRFD&OY[ViMQ\t\\iPW\\@&PR\u0013\u0010") + var1 + "[" + var6 + a("m(") + a("\u0010HP\u001cz^kOqLDg[]]Q&sSHTc[\u0012"));
         }

         label54:
         while(true) {
            if (var3.hasMoreElements()) {
               var6 = (String)var3.nextElement();
               if (!var11 && !var11) {
                  if (Character.isDigit(var6.charAt(0))) {
                     this._components[var5++] = Long.parseLong(var6);
                     if (!var11) {
                        continue;
                     }
                  }

                  if (var6.charAt(0) == '"' || var6.charAt(0) == '\'') {
                     if (var6.charAt(var6.length() - 1) != var6.charAt(0)) {
                        throw new SnmpValueException(a("rg[\u001cXEiKYM\u0010Ivx\tDiTYG\n&\u001d") + var6 + a("\u0012(\u001frFD&KY[]oQ]]Ub\u0011"));
                     }

                     var6 = var6.substring(1, var6.length() - 1);
                     int var12 = var6.length();
                     long[] var13 = new long[this._components.length + var12];
                     System.arraycopy(this._components, 0, var13, 0, this._components.length);
                     this._components = var13;
                     this._components[var5++] = (long)var12;
                     int var14 = 0;

                     while(var14 < var12) {
                        this._components[var5++] = (long)(var6.charAt(var14) & 255);
                        ++var14;
                        if (var11 && var11) {
                           continue label54;
                        }
                     }

                     if (!var11) {
                        continue;
                     }
                  }

                  throw new SnmpValueException(a("bcR]@^oQ[\tFgSILC&VR\t\u0017") + var1 + a("\u0017&RIZD&]Y\tYhKO") + a("\u0010iM\u001cXEiKYM\u0010uKN@^aL\u001cr") + var6 + "]");
               }
            } else {
               this._componentCount = var5;
            }

            return;
         }
      }
   }

   private static Vector b(String var0) {
      boolean var8 = SnmpValue.b;
      if (var0.startsWith("{") && var0.endsWith("}")) {
         var0 = var0.substring(1, var0.length() - 1).trim();
      }

      Vector var1 = new Vector();
      int var2 = var0.length();
      int var3 = -1;
      int var4 = 0;
      int var5 = 0;

      String var10000;
      int var10001;
      while(true) {
         if (var5 < var2) {
            var10000 = var0;
            var10001 = var5;
            if (var8) {
               break;
            }

            label63: {
               char var6 = var0.charAt(var5);
               if (var6 == '.' || var6 == ' ') {
                  if (var3 != -1) {
                     break label63;
                  }

                  if (var5 > var4) {
                     String var7 = var0.substring(var4, var5);
                     var1.addElement(var7);
                  }

                  var4 = var5 + 1;
                  if (!var8) {
                     break label63;
                  }
               }

               if (var6 == var3) {
                  var3 = -1;
                  if (!var8) {
                     break label63;
                  }
               }

               if (var6 == '\'' || var6 == '"') {
                  var3 = var6;
               }
            }

            ++var5;
            if (!var8) {
               continue;
            }
         }

         var10000 = var0;
         var10001 = var4;
         break;
      }

      String var9 = var10000.substring(var10001);
      if (var9.length() > 0) {
         var1.addElement(var9);
      }

      return var1;
   }

   public static SnmpOid getStatic(long[] var0) {
      SnmpOid var1 = new SnmpOid(var0);
      var1._isStatic = true;
      return var1;
   }

   public SnmpOid(long[] var1) {
      this(var1.length, var1, false);
   }

   public SnmpOid(SnmpOid var1) {
      this(var1._componentCount, var1._components, true);
      this.setMetadata(var1.c);
   }

   public SnmpOid(SnmpMetadata var1, SnmpOid var2) {
      this(var2._componentCount, var2._components, true);
      this.setMetadata(var1);
   }

   public SnmpOid(int var1, long[] var2, boolean var3) {
      this.a = null;
      this.b = 0;
      this._components = null;
      this._componentCount = 0;
      this._isStatic = false;
      this.c = null;
      this._componentCount = var1;
      if (var3) {
         this._components = new long[var2.length];
         System.arraycopy(var2, 0, this._components, 0, var2.length);
         if (!SnmpValue.b) {
            return;
         }
      }

      this._components = var2;
   }

   public void append(String var1) throws SnmpValueException {
      StringTokenizer var2 = new StringTokenizer(var1, a("\u001e<"), false);

      while(var2.hasMoreTokens()) {
         String var3 = var2.nextToken();

         try {
            this.append(Long.parseLong(var3));
         } catch (NumberFormatException var5) {
            throw new SnmpValueException(a("yhI]EYb\u001fs@T&\\SD@iQYGD&\u0005\u001cr") + var3 + a("m&VR\t\u0018") + var1 + ")");
         }

         if (SnmpValue.b) {
            break;
         }
      }

   }

   public synchronized void append(long[] var1) {
      this.append(var1, var1.length);
   }

   public synchronized void append(byte[] var1, int var2) {
      boolean var5 = SnmpValue.b;
      int var3 = this._componentCount + var2;
      this.a(var3);
      int var4 = 0;

      while(true) {
         if (var4 < var2) {
            this._components[this._componentCount + var4] = (long)var1[var4];
            ++var4;
            if (var5) {
               break;
            }

            if (!var5) {
               continue;
            }
         }

         this._componentCount = var3;
         this.b = 0;
         break;
      }

   }

   public synchronized void append(long[] var1, int var2) {
      boolean var5 = SnmpValue.b;
      int var3 = this._componentCount + var2;
      this.a(var3);
      int var4 = 0;

      while(true) {
         if (var4 < var2) {
            this._components[this._componentCount + var4] = var1[var4];
            ++var4;
            if (var5) {
               break;
            }

            if (!var5) {
               continue;
            }
         }

         this._componentCount = var3;
         this.b = 0;
         break;
      }

   }

   public synchronized void append(long var1) {
      synchronized(this._components) {
         this.a(this._componentCount + 1);
         this._components[this._componentCount] = var1;
         ++this._componentCount;
         this.b = 0;
      }
   }

   public SnmpOid getParent() {
      try {
         return this.suboid(0, this._componentCount - 1);
      } catch (SnmpValueException var2) {
         return new SnmpOid();
      }
   }

   public SnmpOid getParent(int var1) {
      try {
         return this.suboid(0, this._componentCount - var1);
      } catch (SnmpValueException var3) {
         return new SnmpOid();
      }
   }

   public SnmpOid suboid(int var1, int var2) throws SnmpValueException {
      if (var2 > this._componentCount) {
         throw new SnmpValueException(a("yhI]EYb\u001fYGT&OS@^r\u0005") + var2 + ">" + this._componentCount);
      } else if (var1 > this._componentCount - 1) {
         throw new SnmpValueException(a("yhI]EYb\u001fO]QtK\u001cY_oQH\u0013") + var1 + ">" + (this._componentCount - 1));
      } else {
         long[] var3 = new long[var2 - var1];
         System.arraycopy(this._components, var1, var3, 0, var2 - var1);
         SnmpOid var4 = new SnmpOid(var3);
         var4.setMetadata(this.c);
         return var4;
      }
   }

   public SnmpOid suboid(int var1) throws SnmpValueException {
      if (var1 >= this._componentCount) {
         throw new SnmpValueException(a("yhI]EYb\u001fO]QtK\u001cY_oQH\u0013") + var1 + a("\u000e;") + this._componentCount);
      } else {
         long[] var2 = new long[this._componentCount - var1];
         System.arraycopy(this._components, var1, var2, 0, this._componentCount - var1);
         SnmpOid var3 = new SnmpOid(var2);
         var3.setMetadata(this.c);
         return var3;
      }
   }

   private synchronized void a(int var1) {
      if (var1 > this._components.length) {
         long[] var2 = new long[var1];
         System.arraycopy(this._components, 0, var2, 0, this._componentCount);
         this._components = var2;
      }

   }

   public synchronized void append(SnmpOid var1) {
      int var2 = var1.getLength();
      int var3 = this._componentCount + var2;
      this.a(var3);
      long[] var4 = var1.toLongArray(false);
      System.arraycopy(var4, 0, this._components, this._componentCount, var2);
      this._componentCount = var3;
      this.b = 0;
   }

   public Object clone() {
      SnmpOid var1 = new SnmpOid(this._componentCount, this._components, true);
      var1.setMetadata(this.c);
      return var1;
   }

   public boolean contains(SnmpOid var1) {
      boolean var3 = SnmpValue.b;
      if (this.getLength() > var1.getLength()) {
         return false;
      } else {
         int var2 = this.getLength() - 1;

         int var10000;
         while(true) {
            if (var2 >= 0) {
               long var4;
               var10000 = (var4 = this._components[var2] - var1._components[var2]) == 0L ? 0 : (var4 < 0L ? -1 : 1);
               if (var3) {
                  break;
               }

               if (var10000 != 0) {
                  return false;
               }

               --var2;
               if (!var3) {
                  continue;
               }
            }

            var10000 = 1;
            break;
         }

         return (boolean)var10000;
      }
   }

   public synchronized int compareTo(SnmpOid var1) {
      boolean var6 = SnmpValue.b;
      if (var1 == null) {
         return 1;
      } else if (this == var1) {
         return 0;
      } else {
         int var2 = this._componentCount;
         int var3 = var1._componentCount;
         int var4 = var2 < var3 ? var2 : var3;
         int var5 = 0;

         int var10000;
         while(true) {
            if (var5 < var4) {
               long var7;
               var10000 = (var7 = this._components[var5] - var1._components[var5]) == 0L ? 0 : (var7 < 0L ? -1 : 1);
               if (var6) {
                  break;
               }

               if (var10000 < 0) {
                  return -1;
               }

               if (this._components[var5] > var1._components[var5]) {
                  return 1;
               }

               ++var5;
               if (!var6) {
                  continue;
               }
            }

            var10000 = var2;
            break;
         }

         if (var10000 == var3) {
            return 0;
         } else {
            return var2 < var3 ? -1 : 1;
         }
      }
   }

   public synchronized boolean equals(Object var1) {
      boolean var4 = SnmpValue.b;
      if (this == var1) {
         return true;
      } else {
         SnmpOid var2;
         if (!(var1 instanceof SnmpOid)) {
            if (var1 instanceof String) {
               try {
                  var2 = new SnmpOid((String)var1);
                  var2.setMetadata(this.c);
                  return this.equals(var2);
               } catch (SnmpValueException var5) {
                  return false;
               }
            } else {
               return false;
            }
         } else {
            var2 = (SnmpOid)var1;
            if (this._componentCount != var2._componentCount) {
               return false;
            } else {
               int var3 = this._componentCount - 1;

               int var10000;
               while(true) {
                  if (var3 >= 0) {
                     long var6;
                     var10000 = (var6 = this._components[var3] - var2._components[var3]) == 0L ? 0 : (var6 < 0L ? -1 : 1);
                     if (var4) {
                        break;
                     }

                     if (var10000 != 0) {
                        return false;
                     }

                     --var3;
                     if (!var4) {
                        continue;
                     }
                  }

                  var10000 = 1;
                  break;
               }

               return (boolean)var10000;
            }
         }
      }
   }

   public int getLength() {
      return this._componentCount;
   }

   public final long get(int var1) throws SnmpValueException {
      if (var1 >= this._componentCount) {
         throw new SnmpValueException(a("YhI]EYb\u001fLFC<") + var1);
      } else {
         return this._components[var1];
      }
   }

   public void set(int var1, long var2) throws SnmpValueException {
      if (var1 >= this._componentCount) {
         throw new SnmpValueException(a("YhI]EYb\u001fLFC<") + var1);
      } else {
         this._components[var1] = var2;
      }
   }

   public String getTypeName() {
      return a("\u007fDuyjd&vxl~Rvz`uT");
   }

   public int hashCode() {
      boolean var3 = SnmpValue.b;
      if (this.b == 0) {
         int var1 = 0;
         int var2 = this._componentCount - 1;

         while(var2 >= 0) {
            var1 = var1 * 37 + (int)this._components[var2];
            --var2;
            if (var3) {
               return this.b;
            }

            if (var3) {
               break;
            }
         }

         this.b = var1;
      }

      return this.b;
   }

   public synchronized void insert(int var1) {
      this.insert(var1);
   }

   public synchronized void insert(long var1) {
      boolean var6 = SnmpValue.b;
      this.a(this._componentCount + 1);
      synchronized(this._components) {
         int var4 = this._componentCount - 1;

         while(true) {
            if (var4 >= 0) {
               this._components[var4 + 1] = this._components[var4];
               --var4;
               if (var6) {
                  break;
               }

               if (!var6) {
                  continue;
               }
            }

            this._components[0] = var1;
            ++this._componentCount;
            break;
         }
      }

      this.b = 0;
   }

   public synchronized boolean isValid() {
      return this._componentCount >= 2 && this._components[0] >= 0L && this._components[0] <= 2L && this._components[1] >= 0L && this._components[1] < 40L;
   }

   public long[] toLongArray() {
      return this.toLongArray(true);
   }

   public final long[] toLongArray(boolean var1) {
      if (var1) {
         long[] var2 = new long[this._componentCount];
         System.arraycopy(this._components, 0, var2, 0, var2.length);
         return var2;
      } else {
         return this._components;
      }
   }

   public static int nextOid(long[] var0, int var1) throws SnmpValueException {
      int var2 = (int)var0[var1];
      if (var2 + var1 >= var0.length) {
         throw new SnmpValueException(a("yhI]EYb\u001fUGTcG\u001cfyB\u001fPL^aKT\u0013\u0010") + var2 + a("\u001e&qS]\u0010cQS\\Wn\u001fYEUkZR]C("));
      } else {
         return var1 + var2 + 1;
      }
   }

   public long getLast() {
      return this._componentCount > 0 ? this._components[this._componentCount - 1] : -1L;
   }

   public boolean booleanValue() {
      return this.longValue() > 0L;
   }

   public int intValue() {
      return (int)this.longValue();
   }

   public byte byteValue() {
      return (byte)((int)this.longValue());
   }

   public long longValue() {
      return this._componentCount > 0 ? this._components[this._componentCount - 1] : -1L;
   }

   void a(StringBuffer var1) {
      if (this._componentCount > 1 && SnmpFramework.resolveOidNames()) {
         SnmpOidInfo var2 = this.getOidInfo();
         if (var2 != null) {
            var1.append(var2.getName());
            SnmpOid var3 = var2.getOid();
            int var4 = var3.getLength();

            while(var4 < this.getLength()) {
               var1.append('.');
               this.a(var1, this._components[var4]);
               ++var4;
               if (SnmpValue.b) {
                  break;
               }
            }

            return;
         }
      }

      this.toNumericString(var1);
   }

   public String toFormattedString() {
      StringBuffer var1 = new StringBuffer();
      this.toFormattedString(var1);
      return var1.toString();
   }

   public void toFormattedString(StringBuffer var1) {
      boolean var8 = SnmpValue.b;
      if (this._componentCount > 1 && SnmpFramework.resolveOidNames()) {
         SnmpOidInfo var2 = this.getOidInfo();
         if (var2 != null) {
            var1.append(var2.getName());
            if (var2 instanceof SnmpObjectInfo) {
               SnmpObjectInfo var3 = (SnmpObjectInfo)var2;
               if (var3.isColumnar()) {
                  try {
                     SnmpVarBind[] var4 = this.getIndexVarBinds();
                     if (var4 != null && var4.length > 0) {
                        StringBuffer var5 = new StringBuffer();
                        int var6 = 0;

                        while(true) {
                           if (var6 < var4.length) {
                              var5.append('.');
                              SnmpVarBind var7 = var4[var6];
                              if (var8) {
                                 break;
                              }

                              switch (var7.getValue().getType()) {
                                 case 4:
                                 case 5:
                                 case 6:
                                 case 64:
                                 case 68:
                                    var5.append("'");
                                    var5.append(var7.getFormattedString());
                                    var5.append("'");
                                    if (!var8) {
                                       break;
                                    }
                                 default:
                                    var5.append(var7.getFormattedString());
                              }

                              ++var6;
                              if (!var8) {
                                 continue;
                              }
                           }

                           var1.append(var5.toString());
                           break;
                        }

                        return;
                     }
                  } catch (Exception var9) {
                  }
               }
            }

            SnmpOid var10 = var2.getOid();
            int var11 = var10.getLength();

            while(var11 < this.getLength()) {
               var1.append('.');
               this.a(var1, this._components[var11]);
               ++var11;
               if (var8) {
                  break;
               }
            }

            return;
         }
      }

      this.toNumericString(var1);
   }

   public String getString() {
      return this.toString();
   }

   public String toNumericString() {
      StringBuffer var1 = new StringBuffer();
      this.toNumericString(var1);
      return var1.toString();
   }

   public void toNumericString(StringBuffer var1) {
      int var2 = 0;

      while(var2 < this._componentCount) {
         this.a(var1, this._components[var2]);
         if (var2 + 1 < this._componentCount) {
            var1.append('.');
         }

         ++var2;
         if (SnmpValue.b) {
            break;
         }
      }

   }

   private void a(StringBuffer var1, long var2) {
      if (var2 < 0L) {
         BigInteger var4 = new BigInteger(new byte[]{0, -1, -1, -1, -1, -1, -1, -1, -1});
         var4 = var4.add(BigInteger.valueOf(var2 + 1L));
         var1.append(var4);
         if (!SnmpValue.b) {
            return;
         }
      }

      var1.append(var2);
   }

   public void toString(StringBuffer var1) {
      // $FF: Couldn't be decompiled
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public SnmpOid toIndexOid(boolean var1) {
      int var2;
      long[] var3;
      if (var1) {
         var2 = this._componentCount;
         var3 = new long[var2];
         System.arraycopy(this._components, 0, var3, 0, var2);
         SnmpOid var6 = new SnmpOid(var3);
         var6.setMetadata(this.getMetadata());
         return var6;
      } else {
         var2 = this._componentCount;
         var3 = this._components;
         long[] var4 = new long[var2 + 1];
         var4[0] = (long)var2;
         System.arraycopy(this._components, 0, var4, 1, var2);
         SnmpOid var5 = new SnmpOid(var4);
         var5.setMetadata(this.getMetadata());
         return var5;
      }
   }

   public void appendIndexOid(SnmpOid var1, boolean var2) {
      int var3 = this._componentCount;
      long[] var4 = this._components;
      if (!var2) {
         var1.append((long)var3);
      }

      var1.append(var4, var3);
   }

   public int fromIndexOid(SnmpOid var1, int var2, boolean var3, int var4) throws SnmpValueException {
      int var6;
      boolean var9;
      int var10;
      label39: {
         var9 = SnmpValue.b;
         boolean var5 = true;
         if (var4 > 0) {
            var10 = var4;
            var6 = var2;
            if (!var9) {
               break label39;
            }
         }

         if (!var3) {
            var10 = (int)var1.get(var2);
            var6 = var2 + 1;
            if (!var9) {
               break label39;
            }
         }

         var10 = var1.getLength() - var2;
         var6 = var2;
      }

      if (var6 + var10 > var1.getLength()) {
         throw new SnmpValueException(a("yhI]EYb\u001fUGTcG\u001cfyB\u001fPL^aKT\u0013\u0010") + var10 + a("\u001e&qS]\u0010cQS\\Wn\u001fYEUkZR]C("));
      } else {
         long[] var7 = new long[var10];
         int var8 = 0;

         while(true) {
            if (var8 < var10) {
               var7[var8] = var1.get(var8 + var6);
               ++var8;
               if (!var9 || !var9) {
                  continue;
               }
               break;
            }

            this._components = var7;
            this._componentCount = var10;
            break;
         }

         return var6 + var10;
      }
   }

   public int getType() {
      return 6;
   }

   public int getTag() {
      return 6;
   }

   public int getCoder() {
      return 2;
   }

   public SnmpOidInfo getOidInfo() {
      if (this.a == null) {
         SnmpMetadata var1 = this.b();
         if (var1 != null) {
            try {
               SnmpOidInfo var2 = var1.resolveBaseOid(this);
               if (SnmpFramework.isCachingOidInfo() || !this._isStatic) {
                  this.a = var2;
               }

               return var2;
            } catch (SnmpValueException var3) {
            }
         }
      }

      return this.a;
   }

   private SnmpObjectInfo a() throws SnmpMetadataException {
      SnmpOidInfo var1 = this.getOidInfo();
      if (var1 == null) {
         throw new SnmpMetadataException(a("^i\u001fQLDg[]]Q&YS[\u0010iVX\u0013\u0010") + this.toNumericString());
      } else if (!(var1 instanceof SnmpObjectInfo)) {
         throw new SnmpMetadataException(a("_o[\u001c\u000e") + this.toNumericString() + a("\u0017&QS]\u0010gQ\u001cfrLz\u007f}\u001dRfll\u001e"));
      } else {
         SnmpObjectInfo var2 = (SnmpObjectInfo)var1;
         return var2;
      }
   }

   public SnmpOid getBaseOid() throws SnmpMetadataException {
      return this.a().getOid();
   }

   public SnmpOid getIndexOid() throws SnmpMetadataException, SnmpValueException {
      SnmpObjectInfo var1 = this.a();
      SnmpOid var2 = this.suboid(var1.getOid().getLength());
      return var2;
   }

   public SnmpValue[] getIndexValues() throws SnmpMetadataException, SnmpValueException {
      boolean var11 = SnmpValue.b;
      SnmpObjectInfo var1 = this.a();
      SnmpOid var2 = this.suboid(var1.getOid().getLength());
      SnmpTableInfo var3 = var1.getTableInfo();
      SnmpValue[] var10000;
      if (var3 != null) {
         SnmpObjectInfo[] var13 = var3.getIndexes();
         SnmpValue[] var14 = new SnmpValue[var13.length];
         int var6 = 0;
         int var7 = 0;

         while(true) {
            if (var7 < var13.length) {
               int var8 = var13[var7].getType();
               var10000 = var14;
               if (var11) {
                  break;
               }

               var14[var7] = SnmpValue.getInstance(var8, (String)null);
               int var9 = -1;

               try {
                  if (SnmpFramework.isFixedStringEncodingEnabled() && (var13[var7].getType() == 4 || var13[var7].getType() == 6) && var13[var7].getTypeInfo().isFixedSize()) {
                     var9 = var13[var7].getTypeInfo().getFixedSize();
                  }

                  if (_log.isDebugEnabled() && var9 > 0) {
                     _log.debug(a("Yu\u0012Z@Hc[\u0011ZY|Zg") + var13[var7].getOid() + a("m<\u001f") + var9);
                  }
               } catch (Exception var12) {
               }

               var6 = var14[var7].fromIndexOid(var2, var6, var3.isImplied() && var7 == var14.length - 1, var9);
               ++var7;
               if (!var11) {
                  continue;
               }
            }

            var10000 = var14;
            break;
         }

         return var10000;
      } else {
         SnmpValue[] var4 = new SnmpValue[var2.getLength()];
         int var5 = 0;

         while(true) {
            if (var5 < var4.length) {
               var10000 = var4;
               if (var11) {
                  break;
               }

               var4[var5] = new SnmpInt(var2.get(var5));
               ++var5;
               if (!var11) {
                  continue;
               }
            }

            var10000 = var4;
            break;
         }

         return var10000;
      }
   }

   public SnmpVarBind[] getIndexVarBinds() throws SnmpMetadataException, SnmpValueException {
      SnmpObjectInfo var1 = this.a();
      SnmpOid var2 = this.suboid(var1.getOid().getLength());
      SnmpTableInfo var3 = var1.getTableInfo();
      if (var3 == null) {
         return EMPTY_VB_LIST;
      } else {
         SnmpObjectInfo[] var4 = var3.getIndexes();
         SnmpVarBind[] var5 = new SnmpVarBind[var4.length];
         int var6 = 0;
         int var7 = 0;

         while(var7 < var4.length) {
            SnmpValue var8 = null;
            int var9 = var4[var7].getType();
            var8 = SnmpValue.getInstance(var9, (String)null);
            int var10 = -1;

            try {
               if (SnmpFramework.isFixedStringEncodingEnabled() && (var4[var7].getType() == 4 || var4[var7].getType() == 6) && var4[var7].getTypeInfo().isFixedSize()) {
                  var10 = var4[var7].getTypeInfo().getFixedSize();
               }

               if (_log.isDebugEnabled() && var10 > 0) {
                  _log.debug(a("Yu\u0012Z@Hc[\u0011ZY|Zg") + var4[var7].getOid() + a("m<\u001f") + var10);
               }
            } catch (Exception var12) {
            }

            var6 = var8.fromIndexOid(var2, var6, var3.isImplied() && var7 == var4.length - 1, var10);
            var5[var7] = new SnmpVarBind(var4[var7].getOid(), var8, true);
            ++var7;
            if (SnmpValue.b) {
               break;
            }
         }

         return var5;
      }
   }

   public void setMetadata(SnmpMetadata var1) {
      this.c = var1;
   }

   public SnmpMetadata getMetadata() {
      return this.c;
   }

   private SnmpMetadata b() {
      if (this.c != null) {
         return this.c;
      } else {
         if (SnmpFramework.isCachingOidInfo() || !this._isStatic) {
            this.c = SnmpFramework.getMetadata();
         }

         return SnmpFramework.getMetadata();
      }
   }

   public long[] getLongArray() {
      return this._components;
   }

   static {
      f.getInstance();
      EMPTY_VB_LIST = new SnmpVarBind[0];
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 48;
               break;
            case 1:
               var10003 = 6;
               break;
            case 2:
               var10003 = 63;
               break;
            case 3:
               var10003 = 60;
               break;
            default:
               var10003 = 41;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
