package monfox.toolkit.snmp;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;
import monfox.toolkit.snmp.engine.SnmpBuffer;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpTableInfo;

public class SnmpVarBindList implements Serializable {
   static final long serialVersionUID = -4568664944447405334L;
   private boolean a;
   private Vector b;
   private transient SnmpBuffer c;
   private boolean d;
   private transient SnmpMetadata e;
   private static final String f = "$Id: SnmpVarBindList.java,v 1.37 2011/08/30 21:54:23 sking Exp $";

   public SnmpVarBindList() {
      this.a = false;
      this.b = new Vector();
      this.c = null;
      this.d = false;
      this.e = null;
      this.b = new Vector();
   }

   public SnmpVarBindList(String var1) throws SnmpException {
      this();
      this.add(var1);
   }

   public SnmpVarBindList(String[] var1) throws SnmpException {
      this();
      this.add(var1);
   }

   public SnmpVarBindList(SnmpMetadata var1) {
      this.a = false;
      this.b = new Vector();
      this.c = null;
      this.d = false;
      this.e = null;
      this.b = new Vector();
      this.e = var1;
   }

   public SnmpVarBindList(SnmpMetadata var1, String var2) throws SnmpException {
      this();
      this.e = var1;
      this.add(var2);
   }

   public SnmpVarBindList(SnmpMetadata var1, String[] var2) throws SnmpException {
      this();
      this.e = var1;
      this.add(var2);
   }

   public SnmpVarBindList(SnmpVarBindList var1) {
      this(var1, true, true);
   }

   public SnmpVarBindList(SnmpVarBindList var1, boolean var2, boolean var3) {
      boolean var7 = SnmpValue.b;
      super();
      this.a = false;
      this.b = new Vector();
      this.c = null;
      this.d = false;
      this.e = null;
      this.e = var1.e;
      int var4 = 0;

      while(var4 < var1.size()) {
         label19: {
            SnmpVarBind var5 = var1.get(var4);
            if (!var3 && var2) {
               this.add(var5);
               if (!var7) {
                  break label19;
               }
            }

            SnmpVarBind var6 = new SnmpVarBind(var5, var2, var3);
            this.add(var6);
         }

         ++var4;
         if (var7) {
            break;
         }
      }

   }

   public synchronized void addInstance(String var1) throws SnmpException {
      this.a();
      SnmpOid var2 = new SnmpOid(this.e, var1);
      this.addInstance(var2);
   }

   public synchronized void addInstance(SnmpOid var1) {
      this.a();
      int var2 = 0;

      while(var2 < this.size()) {
         SnmpVarBind var3 = (SnmpVarBind)this.b.elementAt(var2);
         var3.addInstance(var1);
         ++var2;
         if (SnmpValue.b) {
            break;
         }
      }

   }

   public synchronized void addInstance(long var1) {
      this.a();
      int var3 = 0;

      while(var3 < this.size()) {
         SnmpVarBind var4 = (SnmpVarBind)this.b.elementAt(var3);
         var4.addInstance(var1);
         ++var3;
         if (SnmpValue.b) {
            break;
         }
      }

   }

   public synchronized void addInstance(long[] var1) {
      this.a();
      int var2 = 0;

      while(var2 < this.size()) {
         SnmpVarBind var3 = (SnmpVarBind)this.b.elementAt(var2);
         var3.addInstance(var1);
         ++var2;
         if (SnmpValue.b) {
            break;
         }
      }

   }

   public synchronized void addInstance(String var1, SnmpValue[] var2) throws SnmpValueException {
      this.addInstance(new SnmpOid(this.e, var1), var2);
   }

   public synchronized void addInstance(SnmpOid var1, SnmpValue[] var2) throws SnmpValueException {
      boolean var9 = SnmpValue.b;
      this.a();
      SnmpTableInfo var3 = null;

      try {
         var3 = (SnmpTableInfo)var1.getOidInfo();
      } catch (Exception var11) {
         throw new SnmpValueException(a("|cL \u001cqdL'\bp`\tsN") + var1 + "'");
      }

      SnmpObjectInfo[] var4 = var3.getIndexes();
      if (var2.length != var4.length) {
         throw new SnmpValueException(a("\u007fe\u001f \u0000|kL:\u0007vi\u0014s\u001fs`\u00196Aa%Bs/}y\u00027I") + var2.length + a(">,\u001e6\u0018ge\u001e6I") + var4.length);
      } else {
         SnmpOid var5 = new SnmpOid(this.e);
         int var6 = 0;

         byte var10000;
         while(true) {
            if (var6 < var2.length) {
               var10000 = SnmpValue.validate(var4[var6].getType(), var2[var6]);
               if (var9) {
                  break;
               }

               if (var10000 == 0) {
                  throw new SnmpValueException(a("{b\u001a2\u0005{hL%\b~y\ts\u000f}~L'\u0010biL") + SnmpValue.typeToString(var4[var6].getType()) + a("(,") + var2[var6]);
               }

               boolean var7 = false;

               try {
                  var7 = SnmpFramework.isFixedStringEncodingEnabled() && var4[var6].getType() == 4 && var4[var6].getTypeInfo().isFixedSize();
               } catch (Exception var12) {
               }

               if (var7) {
               }

               var2[var6].appendIndexOid(var5, var3.isImplied() && var6 == var2.length - 1 || var7);
               ++var6;
               if (!var9) {
                  continue;
               }
            }

            var10000 = 0;
            break;
         }

         var6 = var10000;

         while(var6 < this.size()) {
            SnmpVarBind var13 = (SnmpVarBind)this.b.elementAt(var6);

            try {
               SnmpObjectInfo var8 = (SnmpObjectInfo)var13.getOid().getOidInfo();
               if (var8.getTableInfo() == var3 && var13.getOid().getLength() == var8.getOid().getLength()) {
                  var13.addInstance(var5);
               }
            } catch (Exception var10) {
            }

            ++var6;
            if (var9) {
               break;
            }
         }

      }
   }

   public synchronized void addAll(SnmpVarBindList var1) {
      int var2 = 0;

      while(var2 < var1.size()) {
         SnmpVarBind var3 = var1.get(var2);
         this.add(var3);
         ++var2;
         if (SnmpValue.b) {
            break;
         }
      }

   }

   public synchronized void add(SnmpVarBindList var1, int var2) {
      if (var2 > var1.size()) {
         var1.size();
      }

      int var4 = 0;

      while(var4 < var2) {
         SnmpVarBind var5 = var1.get(var4);
         this.add(var5);
         ++var4;
         if (SnmpValue.b) {
            break;
         }
      }

   }

   public synchronized void add(SnmpOid var1) {
      this.add(new SnmpVarBind(var1, (SnmpValue)null, true));
   }

   public synchronized void add(String var1) throws SnmpValueException {
      this.add(new SnmpVarBind(this.e, var1));
   }

   public synchronized void addDefault(SnmpOid var1) throws SnmpValueException {
      this.add(new SnmpVarBind(var1, (String)null));
   }

   public synchronized void addDefault(String var1) throws SnmpValueException {
      this.add(new SnmpVarBind(this.e, var1, (String)null));
   }

   public synchronized void add(SnmpVarBind var1) {
      this.a();
      this.b.addElement(var1);
   }

   public synchronized void add(String var1, SnmpValue var2) throws SnmpValueException {
      this.add(new SnmpVarBind(this.e, var1, var2));
   }

   public synchronized void add(String var1, String var2) throws SnmpValueException {
      this.add(new SnmpVarBind(this.e, var1, var2));
   }

   public synchronized void add(String var1, String var2, String var3) throws SnmpValueException {
      SnmpVarBind var4 = new SnmpVarBind(this.e, var1, (SnmpValue)null);
      var4.setFormattedValue(var3, var2);
      this.add(var4);
   }

   public synchronized void add(String var1, long var2) throws SnmpValueException {
      this.add(new SnmpVarBind(this.e, var1, var2));
   }

   public synchronized void add(String var1, String var2, boolean var3) throws SnmpValueException {
      this.add(new SnmpVarBind(this.e, var1, var2, var3));
   }

   public synchronized void add(SnmpOid var1, String var2) throws SnmpValueException {
      this.add(new SnmpVarBind(this.e, var1, var2));
   }

   public synchronized void add(SnmpOid var1, String var2, String var3) throws SnmpValueException {
      SnmpVarBind var4 = new SnmpVarBind(var1, (SnmpValue)null);
      var4.setFormattedValue(var3, var2);
      this.add(var4);
   }

   public synchronized void add(SnmpOid var1, SnmpValue var2) {
      this.add(new SnmpVarBind(var1, var2, true));
   }

   public synchronized void addString(String var1, String var2) throws SnmpValueException {
      this.addString(new SnmpOid(this.e, var1), var2);
   }

   public synchronized void addInteger(String var1, long var2) throws SnmpValueException {
      this.addInteger(new SnmpOid(this.e, var1), var2);
   }

   public synchronized void addCounter(String var1, long var2) throws SnmpValueException {
      this.addCounter(new SnmpOid(this.e, var1), var2);
   }

   public synchronized void addCounter64(String var1, long var2) throws SnmpValueException {
      this.addCounter64(new SnmpOid(this.e, var1), var2);
   }

   public synchronized void addNull(String var1) throws SnmpValueException {
      this.addNull(new SnmpOid(this.e, var1));
   }

   public synchronized void addOpaque(String var1, byte[] var2) throws SnmpValueException {
      this.addOpaque(new SnmpOid(this.e, var1), var2);
   }

   public synchronized void addOid(String var1, String var2) throws SnmpValueException {
      this.addOid(new SnmpOid(this.e, var1), var2);
   }

   public synchronized void addIpAddress(String var1, String var2) throws SnmpValueException {
      this.addIpAddress(new SnmpOid(this.e, var1), var2);
   }

   public synchronized void addTimeTicks(String var1, long var2) throws SnmpValueException {
      this.addTimeTicks(new SnmpOid(this.e, var1), var2);
   }

   public synchronized void addGauge(String var1, long var2) throws SnmpValueException {
      this.addGauge(new SnmpOid(this.e, var1), var2);
   }

   public synchronized void addString(SnmpOid var1, String var2) throws SnmpValueException {
      this.add(new SnmpVarBind(var1, new SnmpString(var2), false));
   }

   public synchronized void addInteger(SnmpOid var1, long var2) {
      this.add(new SnmpVarBind(var1, new SnmpInt(var2), false));
   }

   public synchronized void addCounter(SnmpOid var1, long var2) {
      this.add(new SnmpVarBind(var1, new SnmpCounter(var2), false));
   }

   public synchronized void addCounter64(SnmpOid var1, long var2) {
      this.add(new SnmpVarBind(var1, new SnmpCounter64(var2), false));
   }

   public synchronized void addNull(SnmpOid var1) {
      this.add(new SnmpVarBind(var1, new SnmpNull(), false));
   }

   public synchronized void addOpaque(SnmpOid var1, byte[] var2) {
      this.add(new SnmpVarBind(var1, new SnmpOpaque(var2), false));
   }

   public synchronized void addOid(SnmpOid var1, String var2) throws SnmpValueException {
      this.add(new SnmpVarBind(var1, new SnmpOid(this.e, var2), false));
   }

   public synchronized void addIpAddress(SnmpOid var1, String var2) throws SnmpValueException {
      this.add(new SnmpVarBind(var1, new SnmpIpAddress(var2), false));
   }

   public synchronized void addTimeTicks(SnmpOid var1, long var2) throws SnmpValueException {
      this.add(new SnmpVarBind(var1, new SnmpTimeTicks(var2), false));
   }

   public synchronized void addGauge(SnmpOid var1, long var2) throws SnmpValueException {
      this.add(new SnmpVarBind(var1, new SnmpGauge(var2), false));
   }

   public synchronized void add(String[] var1) throws SnmpException {
      int var2 = 0;

      while(var2 < var1.length) {
         this.add(var1[var2]);
         ++var2;
         if (SnmpValue.b) {
            break;
         }
      }

   }

   public final synchronized void addVariable(String[] var1, String var2) throws SnmpException {
      SnmpOid var3 = new SnmpOid(this.e, var2);
      int var4 = 0;

      while(var4 < var1.length) {
         SnmpVarBind var5 = new SnmpVarBind(this.e, var1[var4]);
         var5.addInstance(var3);
         this.add(var5);
         ++var4;
         if (SnmpValue.b) {
            break;
         }
      }

   }

   public synchronized Object clone() {
      return new SnmpVarBindList(this);
   }

   public synchronized SnmpVarBindList cloneWithValue() {
      return new SnmpVarBindList(this, true, true);
   }

   public synchronized SnmpVarBindList cloneWithoutValue() {
      return new SnmpVarBindList(this, false, true);
   }

   public void add(SnmpVarBindList var1) {
      this.add(var1, false);
   }

   public synchronized void add(SnmpVarBindList var1, boolean var2) {
      boolean var6 = SnmpValue.b;
      int var3 = 0;

      while(var3 < var1.size()) {
         label17: {
            SnmpVarBind var4 = var1.get(var3);
            if (!var2) {
               this.add(var4);
               if (!var6) {
                  break label17;
               }
            }

            SnmpVarBind var5 = new SnmpVarBind(var4, true, var2);
            this.add(var5);
         }

         ++var3;
         if (var6) {
            break;
         }
      }

   }

   public final synchronized SnmpVarBind get(int var1) {
      return (SnmpVarBind)this.b.elementAt(var1);
   }

   public final synchronized SnmpVarBind get(String var1) throws SnmpException {
      int var2 = this.indexOf(var1);
      return var2 == -1 ? null : (SnmpVarBind)this.b.elementAt(var2);
   }

   public final synchronized SnmpVarBind get(SnmpOid var1) {
      int var2 = this.indexOf(var1);
      return var2 == -1 ? null : (SnmpVarBind)this.b.elementAt(var2);
   }

   public synchronized Enumeration getVarBinds() {
      return this.b.elements();
   }

   public synchronized int size() {
      return this.b.size();
   }

   public synchronized int indexOf(SnmpOid var1) {
      boolean var4 = SnmpValue.b;
      int var2 = 0;

      byte var10000;
      while(true) {
         if (var2 < this.size()) {
            SnmpVarBind var3 = (SnmpVarBind)this.b.elementAt(var2);
            var10000 = var1.equals(var3.getOid());
            if (var4) {
               break;
            }

            if (var10000 != 0) {
               return var2;
            }

            ++var2;
            if (!var4) {
               continue;
            }
         }

         var10000 = -1;
         break;
      }

      return var10000;
   }

   public synchronized int indexOf(String var1) throws SnmpException {
      boolean var5 = SnmpValue.b;
      SnmpOid var2 = new SnmpOid(this.e, var1);
      int var3 = 0;

      byte var10000;
      while(true) {
         if (var3 < this.size()) {
            SnmpVarBind var4 = (SnmpVarBind)this.b.elementAt(var3);
            var10000 = var2.equals(var4.getOid());
            if (var5) {
               break;
            }

            if (var10000 != 0) {
               return var3;
            }

            ++var3;
            if (!var5) {
               continue;
            }
         }

         var10000 = -1;
         break;
      }

      return var10000;
   }

   public synchronized int indexOf(SnmpVarBind var1) {
      return this.indexOf(var1.getOid());
   }

   public synchronized boolean removeAll(SnmpVarBindList var1) {
      boolean var4 = SnmpValue.b;
      this.a();
      Enumeration var2 = var1.getVarBinds();
      boolean var3 = true;

      boolean var10000;
      while(true) {
         if (var2.hasMoreElements()) {
            var10000 = this.b.removeElement(var2.nextElement());
            if (var4) {
               break;
            }

            var3 = var10000 && var3;
            if (!var4) {
               continue;
            }
         }

         var10000 = var3;
         break;
      }

      return var10000;
   }

   public void remove(int var1) {
      this.b.removeElementAt(var1);
   }

   public synchronized boolean remove(String var1) throws SnmpException {
      this.a();
      int var2 = this.indexOf(var1);
      if (var2 >= 0) {
         this.b.removeElementAt(var2);
         return true;
      } else {
         return false;
      }
   }

   public synchronized boolean remove(SnmpVarBind var1) {
      this.a();
      int var2 = this.indexOf(var1);
      if (var2 >= 0) {
         this.b.removeElementAt(var2);
         return true;
      } else {
         return false;
      }
   }

   public synchronized boolean removeVariable(String[] var1) throws SnmpException {
      boolean var4 = SnmpValue.b;
      boolean var2 = true;
      int var3 = 0;

      boolean var10000;
      while(true) {
         if (var3 < var1.length) {
            var10000 = this.remove(var1[var3]);
            if (var4) {
               break;
            }

            var2 = var10000 && var2;
            ++var3;
            if (!var4) {
               continue;
            }
         }

         var10000 = var2;
         break;
      }

      return var10000;
   }

   public synchronized boolean remove(String[] var1, String var2) throws SnmpValueException {
      boolean var8 = SnmpValue.b;
      this.a();
      SnmpOid var3 = new SnmpOid(this.e, var2);
      byte var4 = 1;
      int var5 = 0;

      int var10000;
      while(true) {
         if (var5 < var1.length) {
            SnmpOid var6 = new SnmpOid(this.e, var1[var5]);
            var6.append(var3);
            int var7 = this.indexOf(var6);
            var10000 = var7;
            if (var8) {
               break;
            }

            label19: {
               if (var7 >= 0) {
                  this.b.removeElementAt(var7);
                  if (!var8) {
                     break label19;
                  }
               }

               var4 = 0;
            }

            ++var5;
            if (!var8) {
               continue;
            }
         }

         var10000 = var4;
         break;
      }

      return (boolean)var10000;
   }

   public final synchronized void set(int var1, SnmpVarBind var2) {
      boolean var3 = SnmpValue.b;
      this.a();

      while(true) {
         if (var1 >= this.b.size()) {
            this.b.addElement(new SnmpVarBind());
            if (var3) {
               break;
            }

            if (!var3) {
               continue;
            }
         }

         this.b.setElementAt(var2, var1);
         break;
      }

   }

   public final synchronized void insert(int var1, SnmpVarBind var2) {
      boolean var3 = SnmpValue.b;
      if (var1 >= 0) {
         if (var1 >= this.b.size()) {
            this.a();

            while(var1 > this.b.size()) {
               this.b.addElement(new SnmpVarBind());
               if (var3) {
                  return;
               }

               if (var3) {
                  break;
               }
            }

            this.b.addElement(var2);
            if (var3) {
            }
         } else {
            this.a();
            this.b.insertElementAt(var2, var1);
         }

      }
   }

   public synchronized SnmpVarBindList splitAt(int var1) {
      this.a();
      SnmpVarBindList var2 = new SnmpVarBindList(this.e);
      int var3 = 0;

      while(var3 < var1 && var3 < this.b.size()) {
         var2.add((SnmpVarBind)this.b.elementAt(0));
         this.b.removeElementAt(0);
         ++var3;
         if (SnmpValue.b) {
            break;
         }
      }

      return var2;
   }

   public synchronized String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public synchronized void toString(StringBuffer var1) {
      boolean var4 = SnmpValue.b;
      var1.append(a("i\u0006"));
      Enumeration var2 = this.getVarBinds();

      while(true) {
         if (var2.hasMoreElements()) {
            var1.append(a("2,L"));
            SnmpVarBind var3 = (SnmpVarBind)var2.nextElement();
            if (var4) {
               break;
            }

            label19: {
               if (var3 == null) {
                  var1.append(a(":b\u0019?\u0005?z\r!\u000b{b\bz"));
                  if (!var4) {
                     break label19;
                  }
               }

               var3.toString(var1);
            }

            var1.append("\n");
            if (!var4) {
               continue;
            }
         }

         var1.append("}");
         break;
      }

   }

   private void a() {
      this.c = null;
   }

   public void cacheEncoding(boolean var1) {
      this.d = var1;
   }

   public boolean cacheEncoding() {
      return this.d;
   }

   public synchronized boolean hasCachedEncoding() {
      return this.c != null;
   }

   public synchronized SnmpBuffer getCachedEncoding() {
      return this.c;
   }

   public synchronized void setCachedEncoding(SnmpBuffer var1) {
      this.c = var1;
   }

   public SnmpOid getOid(int var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      return var2.getOid();
   }

   public long getLongValue(String var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      this.a(var1, var2);
      return var2.getValue().longValue();
   }

   public boolean getBooleanValue(String var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      this.a(var1, var2);
      return var2.getValue().booleanValue();
   }

   public int getIntValue(String var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      this.a(var1, var2);
      return var2.getValue().intValue();
   }

   public byte getByteValue(String var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      this.a(var1, var2);
      return var2.getValue().byteValue();
   }

   public String getStringValue(String var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      this.a(var1, var2);
      return var2.getValue().getString();
   }

   public SnmpOid getOidValue(String var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      this.a(var1, var2);

      try {
         return (SnmpOid)((SnmpOid)var2.getValue());
      } catch (ClassCastException var4) {
         throw new SnmpValueException(a("dm\u0000&\f2+") + var2.getValue() + a("5,\u0005 I|c\u0018s\b|,?=\u0004bC\u00057"));
      }
   }

   private void a(SnmpOid var1, SnmpVarBind var2) throws NoSuchElementException, SnmpException {
      if (var2 == null) {
         throw new NoSuchElementException(var1.toString());
      } else if (var2.isError()) {
         throw new SnmpValueException(var1 + "=" + var2.getValue());
      } else if (var2.getValue() instanceof SnmpNull) {
         throw new SnmpValueException(a("\\y\u0000?Idm\u0000&\f(,D") + var2 + ")");
      }
   }

   private void a(String var1, SnmpVarBind var2) throws NoSuchElementException, SnmpException {
      if (var2 == null) {
         throw new NoSuchElementException(var1);
      } else if (var2.isError()) {
         throw new SnmpValueException(var1 + "=" + var2.getValue());
      } else if (var2.getValue() instanceof SnmpNull) {
         throw new SnmpValueException(a("\\y\u0000?Idm\u0000&\f(,D") + var2 + ")");
      }
   }

   private void a(int var1, SnmpVarBind var2) throws NoSuchElementException, SnmpException {
      if (var2 == null) {
         throw new NoSuchElementException(a("dm\u001e1\u0000|h7") + var1 + a("O,\u0001:\u001aae\u00024"));
      } else if (var2.isError()) {
         throw new SnmpValueException(a("2z\r!\u000b{b\b\b") + var1 + a("O1") + var2.getValue());
      } else if (var2.getValue() instanceof SnmpNull) {
         throw new SnmpValueException(a("\\y\u0000?Idm\u0000&\f(,\u001a2\u001bpe\u000272") + var1 + a("O,Qs") + var2 + ")");
      }
   }

   public long getLongValue(SnmpOid var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      this.a(var1, var2);
      return var2.getValue().longValue();
   }

   public boolean getBooleanValue(SnmpOid var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      this.a(var1, var2);
      return var2.getValue().booleanValue();
   }

   public int getIntValue(SnmpOid var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      this.a(var1, var2);
      return var2.getValue().intValue();
   }

   public byte getByteValue(SnmpOid var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      this.a(var1, var2);
      return var2.getValue().byteValue();
   }

   public String getStringValue(SnmpOid var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      this.a(var1, var2);
      return var2.getValue().getString();
   }

   public SnmpOid getOidValue(SnmpOid var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      this.a(var1, var2);

      try {
         return (SnmpOid)((SnmpOid)var2.getValue());
      } catch (ClassCastException var4) {
         throw new SnmpValueException(a("dm\u0000&\f2+") + var2.getValue() + a("5,\u0005 I|c\u0018s\b|,?=\u0004bC\u00057"));
      }
   }

   public long getLongValue(int var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      this.a(var1, var2);
      return var2.getValue().longValue();
   }

   public boolean getBooleanValue(int var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      this.a(var1, var2);
      return var2.getValue().booleanValue();
   }

   public int getIntValue(int var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      this.a(var1, var2);
      return var2.getValue().intValue();
   }

   public byte getByteValue(int var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      this.a(var1, var2);
      return var2.getValue().byteValue();
   }

   public String getStringValue(int var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      this.a(var1, var2);
      return var2.getValue().getString();
   }

   public SnmpOid getOidValue(int var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      this.a(var1, var2);

      try {
         return (SnmpOid)((SnmpOid)var2.getValue());
      } catch (ClassCastException var4) {
         throw new SnmpValueException(a("dm\u0000&\f2+") + var2.getValue() + a("5,\u0005 I|c\u0018s\b|,?=\u0004bC\u00057"));
      }
   }

   public SnmpVarBind find(SnmpOid var1) {
      if (var1 == null) {
         return null;
      } else {
         Iterator var2 = this.b.iterator();

         while(var2.hasNext()) {
            SnmpVarBind var3 = (SnmpVarBind)var2.next();
            if (var1.equals(var3.getOid())) {
               return var3;
            }

            if (SnmpValue.b) {
               break;
            }
         }

         return null;
      }
   }

   public void setEncodeGetValues(boolean var1) {
      this.a = var1;
   }

   public boolean getEncodeGetValues() {
      return this.a;
   }

   public SnmpValue getValue(int var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      return var2.getValue();
   }

   public SnmpValue getValue(String var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      if (var2 == null) {
         throw new NoSuchElementException(var1);
      } else {
         return var2.getValue();
      }
   }

   public SnmpValue getValue(SnmpOid var1) throws NoSuchElementException, SnmpException {
      SnmpVarBind var2 = this.get(var1);
      if (var2 == null) {
         throw new NoSuchElementException(var1.toString());
      } else {
         return var2.getValue();
      }
   }

   public SnmpMetadata getMetadata() {
      return this.e;
   }

   public synchronized boolean equals(Object var1) {
      boolean var7 = SnmpValue.b;
      if (this == var1) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (!(var1 instanceof SnmpVarBindList)) {
         return false;
      } else {
         SnmpVarBindList var2 = (SnmpVarBindList)var1;
         int var3 = this.b.size();
         if (var3 != var2.b.size()) {
            return false;
         } else {
            int var4 = 0;

            boolean var10000;
            while(true) {
               if (var4 < this.b.size()) {
                  SnmpVarBind var5 = (SnmpVarBind)this.b.get(var4);
                  SnmpVarBind var6 = (SnmpVarBind)var2.b.get(var4);
                  var10000 = var5.equals(var6);
                  if (var7) {
                     break;
                  }

                  if (!var10000) {
                     return false;
                  }

                  ++var4;
                  if (!var7) {
                     continue;
                  }
               }

               var10000 = true;
               break;
            }

            return var10000;
         }
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
               var10003 = 18;
               break;
            case 1:
               var10003 = 12;
               break;
            case 2:
               var10003 = 108;
               break;
            case 3:
               var10003 = 83;
               break;
            default:
               var10003 = 105;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
