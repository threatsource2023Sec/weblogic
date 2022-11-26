package monfox.toolkit.snmp;

import java.io.Serializable;
import monfox.toolkit.snmp.metadata.SnmpMetadata;
import monfox.toolkit.snmp.metadata.SnmpObjectInfo;
import monfox.toolkit.snmp.metadata.SnmpOidInfo;
import monfox.toolkit.snmp.metadata.SnmpTypeInfo;

public class SnmpVarBind implements Serializable, Cloneable {
   static final long serialVersionUID = -3724546974755583102L;
   SnmpOid a;
   SnmpValue b;
   private static final String c = "$Id: SnmpVarBind.java,v 1.25 2011/08/30 21:54:23 sking Exp $";

   public SnmpVarBind() {
      this.a = null;
      this.b = null;
      this.a = new SnmpOid();
      this.b = null;
   }

   public SnmpVarBind(SnmpMetadata var1, String var2) throws SnmpValueException {
      this.a = null;
      this.b = null;
      this.a = new SnmpOid(var1, var2);
      this.b = null;
   }

   public SnmpVarBind(SnmpMetadata var1, String var2, SnmpValue var3) throws SnmpValueException {
      this.a = null;
      this.b = null;
      this.a = new SnmpOid(var1, var2);
      this.b = var3;
   }

   public SnmpVarBind(String var1) throws SnmpValueException {
      this.a = null;
      this.b = null;
      this.a = new SnmpOid(var1);
      this.b = null;
   }

   public SnmpVarBind(String var1, SnmpValue var2) throws SnmpValueException {
      this.a = null;
      this.b = null;
      this.a = new SnmpOid(var1);
      this.b = var2;
   }

   public SnmpVarBind(String var1, String var2) throws SnmpValueException {
      this.a = null;
      this.b = null;
      this.a = new SnmpOid(var1);
      this.setValue(var2);
   }

   public SnmpVarBind(String var1, long var2) throws SnmpValueException {
      this.a = null;
      this.b = null;
      this.a = new SnmpOid(var1);
      this.setValue(var2);
   }

   public SnmpVarBind(String var1, String var2, boolean var3) throws SnmpValueException {
      this.a = null;
      this.b = null;
      this.a = new SnmpOid(var1);
      this.setValue(var2, var3);
   }

   public SnmpVarBind(String var1, String var2, String var3) throws SnmpValueException {
      this.a = null;
      this.b = null;
      this.a = new SnmpOid(var1);
      this.setValue(var2, var3);
   }

   public SnmpVarBind(SnmpMetadata var1, String var2, String var3) throws SnmpValueException {
      this.a = null;
      this.b = null;
      this.a = new SnmpOid(var1, var2);
      this.setValue(var3);
   }

   public SnmpVarBind(SnmpMetadata var1, String var2, long var3) throws SnmpValueException {
      this.a = null;
      this.b = null;
      this.a = new SnmpOid(var1, var2);
      this.setValue(var3);
   }

   public SnmpVarBind(SnmpMetadata var1, String var2, String var3, boolean var4) throws SnmpValueException {
      this.a = null;
      this.b = null;
      this.a = new SnmpOid(var1, var2);
      this.setValue(var3, var4);
   }

   public SnmpVarBind(SnmpMetadata var1, SnmpOid var2, String var3) throws SnmpValueException {
      this.a = null;
      this.b = null;
      this.a = var2;
      this.setValue(var3);
   }

   public SnmpVarBind(SnmpMetadata var1, String var2, String var3, String var4) throws SnmpValueException {
      this.a = null;
      this.b = null;
      this.a = new SnmpOid(var1, var2);
      this.setValue(var3, var4);
   }

   public SnmpVarBind(SnmpOid var1, String var2, String var3) throws SnmpValueException {
      this.a = null;
      this.b = null;
      this.a = var1;
      this.setValue(var2, var3);
   }

   public SnmpVarBind(SnmpOid var1, String var2) throws SnmpValueException {
      this.a = null;
      this.b = null;
      this.a = var1;
      this.setValue(var2);
   }

   public SnmpVarBind(SnmpOid var1, SnmpValue var2) {
      this(var1, var2, false);
   }

   public SnmpVarBind(SnmpVarBind var1, boolean var2, boolean var3) {
      this.a = null;
      this.b = null;
      SnmpOid var4 = var1.a;
      SnmpValue var5 = var1.b;
      if (var3) {
         this.a = (SnmpOid)var4.cloneSnmpValue();
         this.b = var5 != null && var2 ? var5.cloneSnmpValue() : null;
         if (!SnmpValue.b) {
            return;
         }
      }

      this.a = var4;
      this.b = var2 ? var5 : null;
   }

   public SnmpVarBind(SnmpOid var1, SnmpValue var2, boolean var3) {
      boolean var4 = SnmpValue.b;
      super();
      this.a = null;
      this.b = null;
      if (var3) {
         this.a = (SnmpOid)var1.cloneSnmpValue();
         if (var2 != null) {
            this.b = var2.cloneSnmpValue();
            if (!var4) {
               return;
            }
         }

         this.b = null;
         if (!var4) {
            return;
         }
      }

      this.a = var1;
      this.b = var2;
   }

   public SnmpVarBind(SnmpMetadata var1, SnmpOid var2, SnmpValue var3, boolean var4) {
      boolean var5 = SnmpValue.b;
      super();
      this.a = null;
      this.b = null;
      if (var4) {
         this.a = (SnmpOid)var2.cloneSnmpValue();
         if (var3 != null) {
            this.b = var3.cloneSnmpValue();
            if (!var5) {
               return;
            }
         }

         this.b = null;
         if (!var5) {
            return;
         }
      }

      this.a = var2;
      this.b = var3;
   }

   public void addInstance(String var1) throws SnmpValueException {
      this.a.append(var1);
   }

   public void addInstance(SnmpOid var1) {
      this.a.append(var1);
   }

   public void addInstance(long var1) {
      this.a.append(var1);
   }

   public void addInstance(long[] var1) {
      this.a.append(var1);
   }

   public void addIndex(SnmpValue var1, boolean var2) {
      SnmpOid var3 = var1.toIndexOid(var2);
      this.addInstance(var3);
   }

   public void addIndex(SnmpValue var1) {
      this.addIndex(var1, false);
   }

   public boolean hasValue() {
      return this.b != null;
   }

   public void setOid(SnmpOid var1) {
      this.a = var1;
   }

   public SnmpOid getOid() {
      return this.a;
   }

   public void setValue(SnmpValue var1) {
      this.b = var1;
   }

   public SnmpValue getValue() {
      return this.b;
   }

   public final void setAsCounter64(long var1) {
      this.setValue((SnmpValue)(new SnmpCounter64(var1)));
   }

   public final void setAsCounter(long var1) {
      this.setValue((SnmpValue)(new SnmpCounter(var1)));
   }

   public final void setAsGauge(long var1) {
      this.setValue((SnmpValue)(new SnmpGauge(var1)));
   }

   public final void setAsInt(long var1) {
      this.setValue((SnmpValue)(new SnmpInt(var1)));
   }

   public final void setAsIpAddress(String var1) throws SnmpValueException {
      this.setValue((SnmpValue)(new SnmpIpAddress(var1)));
   }

   public final void setAsOid(String var1) throws SnmpValueException {
      this.setValue((SnmpValue)(new SnmpOid(var1)));
   }

   public final void setAsOpaque(byte[] var1) {
      this.setValue((SnmpValue)(new SnmpOpaque(var1)));
   }

   public final void setAsFixedString(String var1) throws SnmpValueException {
      this.setValue((SnmpValue)(new SnmpFixedString(var1)));
   }

   public final void setAsString(String var1) throws SnmpValueException {
      this.setValue((SnmpValue)(new SnmpString(var1)));
   }

   public final void setAsTimeTicks(long var1) {
      this.setValue((SnmpValue)(new SnmpTimeTicks(var1)));
   }

   public final void setToNoSuchObject() {
      this.setValue((SnmpValue)SnmpNull.noSuchObject);
   }

   public final void setToNoSuchInstance() {
      this.setValue((SnmpValue)SnmpNull.noSuchInstance);
   }

   public final void setToEndOfMibView() {
      this.setValue((SnmpValue)SnmpNull.endOfMibView);
   }

   public boolean isEndOfMibView() {
      return this.b != null && this.b.getTag() == 130;
   }

   public boolean isNoSuchInstance() {
      return this.b != null && this.b.getTag() == 129;
   }

   public boolean isNoSuchObject() {
      return this.b != null && this.b.getTag() == 128;
   }

   public boolean isError() {
      return this.b != null && (this.b.getTag() == 129 || this.b.getTag() == 130 || this.b.getTag() == 128);
   }

   public Object clone() {
      return new SnmpVarBind(this, true, true);
   }

   public void setValue(String var1, boolean var2) throws SnmpValueException {
      if (this.a == null) {
         throw new SnmpValueException(a("\u0011>\u0018B\u0001\u00120K^\u0001\u0018"));
      } else {
         this.b = SnmpValue.getInstance(this.a, var1, var2);
      }
   }

   public void setFormattedValue(String var1, String var2) throws SnmpValueException {
      if (this.a == null) {
         throw new SnmpValueException(a("\u0011>\u0018B\u0001\u00120K^\u0001\u0018"));
      } else if (this.a.getMetadata() == null) {
         throw new SnmpValueException(a("\u0013>\u000f\u0011\u0005\u0015$\u0018X\u0006\u001bw\u0006T\u001c\u001d3\nE\tFw") + this.a);
      } else {
         SnmpOidInfo var3 = this.a.getOidInfo();
         if (var3 == null) {
            throw new SnmpValueException(a("\u00128K^\u0001\u0018w\"_\u000e\u0013w\r^\u001aFw") + this.a);
         } else if (!(var3 instanceof SnmpObjectInfo)) {
            throw new SnmpValueException(a("\u00128\u001f\u0011\t\u0012w\u0004S\u0002\u00194\u001f\u0011\u000b\u00106\u0018BH\u0013>\u000f\u000bH") + this.a);
         } else {
            SnmpTypeInfo var4 = this.a.getMetadata().getType(var1);
            if (var4 == null) {
               throw new SnmpValueException(a("\u00128KB\u001d\u001f?KE\r\u0004#\u001eP\u0004Q4\u0004_\u001e\u00199\u001fX\u0007\u0012wL") + var1 + "'");
            } else {
               this.b = SnmpValue.getInstance(var4, var2, (SnmpObjectInfo)var3, true);
            }
         }
      }
   }

   public void setValue(String var1) throws SnmpValueException {
      if (this.a == null) {
         throw new SnmpValueException(a("\u0011>\u0018B\u0001\u00120K^\u0001\u0018"));
      } else {
         this.b = SnmpValue.getInstance(this.a, var1);
      }
   }

   public void setValue(long var1) throws SnmpValueException {
      if (this.a == null) {
         throw new SnmpValueException(a("\u0011>\u0018B\u0001\u00120K^\u0001\u0018"));
      } else {
         this.b = SnmpValue.getInstance(this.a, var1);
      }
   }

   public void setValue(String var1, String var2) throws SnmpValueException {
      this.b = SnmpValue.getInstance(var1, var2);
   }

   public String getValueString() {
      StringBuffer var1 = new StringBuffer();
      this.getValueString(var1, false);
      return var1.toString();
   }

   public String getValueString(boolean var1) {
      StringBuffer var2 = new StringBuffer();
      this.getValueString(var2, var1);
      return var2.toString();
   }

   public void getValueString(StringBuffer var1) {
      this.getValueString(var1, false);
   }

   public void getValueString(StringBuffer var1, boolean var2) {
      if (this.b == null) {
         var1.append("");
      } else {
         if (this.b instanceof SnmpInt && this.a != null && this.a.getOidInfo() != null && this.a.getOidInfo() instanceof SnmpObjectInfo) {
            long var3 = this.b.longValue();
            SnmpObjectInfo var5 = (SnmpObjectInfo)this.a.getOidInfo();
            if (var5.getTypeInfo() != null) {
               String var6 = var5.getTypeInfo().intToNamedNumber(var3);
               if (var6 != null) {
                  var1.append(var6);
                  if (!var2) {
                     var1.append("(").append(var3).append(")");
                  }

                  return;
               }
            }
         }

         if (this.b instanceof SnmpString) {
            ((SnmpString)this.b).a(var1, this.a);
            if (!SnmpValue.b) {
               return;
            }
         }

         this.b.toString(var1);
      }
   }

   public String getFormattedString(String var1) {
      StringBuffer var2 = new StringBuffer();
      this.getFormattedString(var2, var1);
      return var2.toString();
   }

   public void getFormattedString(StringBuffer var1, String var2) {
      try {
         if (this.a == null) {
            throw new SnmpValueException(a("\u0011>\u0018B\u0001\u00120K^\u0001\u0018"));
         } else if (this.a.getMetadata() == null) {
            throw new SnmpValueException(a("\u0013>\u000f\u0011\u0005\u0015$\u0018X\u0006\u001bw\u0006T\u001c\u001d3\nE\tFw") + this.a);
         } else {
            SnmpOidInfo var3 = this.a.getOidInfo();
            if (var3 == null) {
               throw new SnmpValueException(a("\u00128K^\u0001\u0018w\"_\u000e\u0013w\r^\u001aFw") + this.a);
            } else if (!(var3 instanceof SnmpObjectInfo)) {
               throw new SnmpValueException(a("\u00128\u001f\u0011\t\u0012w\u0004S\u0002\u00194\u001f\u0011\u000b\u00106\u0018BH\u0013>\u000f\u000bH") + this.a);
            } else {
               SnmpTypeInfo var4 = this.a.getMetadata().getType(var2);
               if (var4 == null) {
                  throw new SnmpValueException(a("\u00128KB\u001d\u001f?KE\r\u0004#\u001eP\u0004Q4\u0004_\u001e\u00199\u001fX\u0007\u0012wL") + var2 + "'");
               } else {
                  this.b.toString(var1, var4, true);
               }
            }
         }
      } catch (Exception var5) {
         this.getFormattedString(var1, false);
      }
   }

   public String getFormattedString() {
      StringBuffer var1 = new StringBuffer();
      this.getFormattedString(var1, false);
      return var1.toString();
   }

   public String getFormattedString(boolean var1) {
      StringBuffer var2 = new StringBuffer();
      this.getFormattedString(var2, var1);
      return var2.toString();
   }

   public void getFormattedString(StringBuffer var1) {
      this.getFormattedString(var1, false);
   }

   public void getFormattedString(StringBuffer var1, boolean var2) {
      if (this.b == null) {
         var1.append("");
      } else {
         this.b.toString(var1, this.a, var2);
      }
   }

   public String[] getNamedBits() throws SnmpValueException {
      if (this.a != null && this.a.getOidInfo() != null && this.a.getOidInfo() instanceof SnmpObjectInfo) {
         SnmpObjectInfo var1 = (SnmpObjectInfo)this.a.getOidInfo();
         SnmpTypeInfo var2 = var1.getTypeInfo();
         if (var2 != null && var2.getSmiType() == 14 && var2.getNameToNumberMap() != null) {
            return var2.bytesToNamedBits(this.b.getByteArray(), false);
         }
      }

      throw new SnmpValueException(a("28K_\t\u00112\u000f\u0011\n\u0015#\u0018\u0011\t\n6\u0002]\t\u001e;\u000e"));
   }

   public synchronized boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (var1 instanceof SnmpVarBind) {
         SnmpVarBind var2 = (SnmpVarBind)var1;
         if (this.a != null && var2.a != null) {
            if (!this.a.equals(var2.a)) {
               return false;
            } else if (this.b == var2.b) {
               return true;
            } else if (this.b != null && var2.b != null) {
               if (this.b.getTag() != var2.b.getTag()) {
                  return false;
               } else {
                  return this.b.equals(var2.b);
               }
            } else {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean isValidValue() {
      if (this.getOid() != null && this.getValue() != null) {
         try {
            SnmpOidInfo var1 = this.getOid().getOidInfo();
            if (var1 != null && var1 instanceof SnmpObjectInfo) {
               SnmpObjectInfo var2 = (SnmpObjectInfo)var1;
               SnmpTypeInfo var3 = var2.getTypeInfo();
               if (var3 != null) {
                  return SnmpValue.validate(var3, this.getValue()) == 0;
               } else {
                  return SnmpValue.validate(var2, this.getValue());
               }
            } else {
               return true;
            }
         } catch (Exception var4) {
            return true;
         }
      } else {
         return false;
      }
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(StringBuffer var1) {
      label11: {
         var1.append(a("\u0007w"));
         var1.append(this.a);
         var1.append("=");
         if (this.b != null) {
            this.getFormattedString(var1);
            if (!SnmpValue.b) {
               break label11;
            }
         }

         var1.append(a("@9\u0004\u001c\u001e\u001d;\u001eTV"));
      }

      var1.append(a("\\*"));
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 124;
               break;
            case 1:
               var10003 = 87;
               break;
            case 2:
               var10003 = 107;
               break;
            case 3:
               var10003 = 49;
               break;
            default:
               var10003 = 104;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
