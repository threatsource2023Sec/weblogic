package monfox.toolkit.snmp.metadata;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import monfox.toolkit.snmp.Snmp;
import monfox.toolkit.snmp.SnmpValue;
import monfox.toolkit.snmp.SnmpValueException;
import monfox.toolkit.snmp.util.TextBuffer;

public class SnmpTypeInfo implements Serializable {
   static final long serialVersionUID = -766138160407080196L;
   private int _smiType = -1;
   private String _description = null;
   private SnmpModule _module = null;
   private RangeItem[] _rangeSpec = null;
   private int _type = -1;
   private String _definedType = null;
   private String _displayHint = null;
   private String _status = a("k\u001c'u\u0010f\u001d");
   private String _name = null;
   private SnmpTypeInfo _typeRef = null;
   private String _typeRefName = null;
   private SnmpTypeInfo _baseType = null;
   private int _fixedSize = -1;
   private Hashtable _nameToNumberMap = null;
   private Hashtable _numberToNameMap = null;
   private static char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
   private transient SnmpDisplayHint a = null;

   public SnmpTypeInfo(SnmpTypeInfo var1) {
      this._name = var1._name;
      this._definedType = var1._definedType;
      this._type = var1._type;
      this._displayHint = var1._displayHint;
      this._rangeSpec = var1._rangeSpec;
      this._nameToNumberMap = var1._nameToNumberMap;
      this._numberToNameMap = var1._numberToNameMap;
   }

   public SnmpTypeInfo(String var1, String var2, String var3, String var4) throws SnmpValueException {
      this._name = var1;
      this._definedType = var2;
      this._type = SnmpValue.stringToType(var3);
      if (this._type == -1) {
         throw new SnmpValueException(a("}\u0007>i\u001a\u007f\u0007us\fx\fo'") + var3);
      } else {
         this._displayHint = var4;
      }
   }

   public String getName() {
      return this._name;
   }

   public String getDefinedType() {
      return this._definedType;
   }

   public int getType() {
      return this._type;
   }

   public String getTypeString() {
      return SnmpValue.typeToString(this._type);
   }

   public String getTypeShortString() {
      return SnmpValue.typeToShortString(this._type);
   }

   public String getDisplayHint() {
      return this._displayHint;
   }

   public SnmpDisplayHint getSnmpDisplayHint() {
      if (this.a == null) {
         if (this._displayHint != null) {
            this.a = new SnmpDisplayHint(this._displayHint);
            if (!SnmpOidInfo.a) {
               return this.a;
            }
         }

         if (this._baseType != null) {
            this.a = this._baseType.getSnmpDisplayHint();
         }
      }

      return this.a;
   }

   public void setDisplayHint(String var1) {
      this._displayHint = var1;
      this.a = null;
   }

   public boolean isFixedSize() {
      return this._rangeSpec != null && this._rangeSpec.length == 1 && this._rangeSpec[0].isSingle();
   }

   public int getFixedSize() {
      return this._rangeSpec != null && this._rangeSpec.length == 1 ? this._rangeSpec[0].getSingleValue().intValue() : -1;
   }

   public SnmpTypeInfo(Hashtable var1) {
      this.setNameToNumberMap(var1);
   }

   public void setNameToNumberMap(Hashtable var1) {
      if (var1 != null) {
         this._nameToNumberMap = var1;
         if (this._nameToNumberMap != null) {
            this._numberToNameMap = new Hashtable();
            Enumeration var2 = this._nameToNumberMap.keys();

            while(var2.hasMoreElements()) {
               String var3 = (String)var2.nextElement();
               Long var4 = (Long)this._nameToNumberMap.get(var3);
               this._numberToNameMap.put(var4, var3);
               if (SnmpOidInfo.a) {
                  break;
               }
            }
         }
      }

   }

   public Hashtable getNameToNumberMap() {
      return this._nameToNumberMap;
   }

   public Hashtable getNumberToNameMap() {
      return this._numberToNameMap;
   }

   public String intToNamedNumber(long var1) {
      if (this._numberToNameMap != null) {
         String var3 = (String)this._numberToNameMap.get(new Long(var1));
         return var3;
      } else {
         return null;
      }
   }

   public int namedNumberToInt(String var1) throws NumberFormatException {
      long var2 = this.namedNumberToLong(var1);
      return (int)var2;
   }

   public long namedNumberToLong(String var1) throws NumberFormatException {
      if (this._nameToNumberMap != null) {
         Long var2 = (Long)this._nameToNumberMap.get(var1);
         if (var2 != null) {
            return var2;
         }
      }

      return (long)Integer.parseInt(var1);
   }

   public byte[] namedBitsStringToBytes(String var1) throws SnmpValueException {
      StringTokenizer var2 = new StringTokenizer(var1, a("s\u0014u+[."), false);
      String[] var3 = new String[var2.countTokens()];
      int var4 = 0;

      while(var2.hasMoreTokens()) {
         var3[var4++] = var2.nextToken();
         if (SnmpOidInfo.a) {
            break;
         }
      }

      return this.namedBitsToBytes(var3);
   }

   public byte[] namedBitsToBytes(String[] var1) throws SnmpValueException {
      boolean var9 = SnmpOidInfo.a;
      int[] var2 = new int[var1.length];
      int var3 = 0;
      int var4 = 0;

      int var6;
      int var10000;
      int var10001;
      while(true) {
         if (var4 < var1.length) {
            String var5 = var1[var4];

            try {
               var6 = (int)this.namedNumberToLong(var5);
               var10000 = var6;
               var10001 = var3;
               if (var9) {
                  break;
               }

               if (var6 > var3) {
                  var3 = var6;
               }

               var2[var4] = var6;
            } catch (NumberFormatException var10) {
               throw new SnmpValueException(a("]\u0007>i\u001a\u007f\u0007ue\u001c|I;f\u0018mIr") + var5 + "'");
            }

            ++var4;
            if (!var9) {
               continue;
            }
         }

         var10000 = var3 / 8;
         var10001 = 1;
         break;
      }

      var4 = var10000 + var10001;
      byte[] var11 = new byte[var4];
      var6 = 0;

      byte[] var12;
      while(true) {
         if (var6 < var2.length) {
            int var7 = var2[var6];
            int var8 = var7 / 8;
            var12 = var11;
            if (var9) {
               break;
            }

            var11[var8] |= (byte)(128 >> var7 % 8);
            ++var6;
            if (!var9) {
               continue;
            }
         }

         var12 = var11;
         break;
      }

      return var12;
   }

   public String bytesToNamedBitsString(byte[] var1) {
      StringBuffer var2 = new StringBuffer();
      this.bytesToNamedBitsString(var2, var1);
      return var2.toString();
   }

   public void bytesToNamedBitsString(StringBuffer var1, byte[] var2) {
      boolean var5 = SnmpOidInfo.a;
      String[] var3 = this.bytesToNamedBits(var2, true);
      int var4;
      if (var3 != null) {
         var1.append(a("sI"));
         var4 = 0;

         while(var4 < var3.length) {
            if (var5) {
               return;
            }

            if (var4 > 0) {
               var1.append(a("$I"));
            }

            var1.append(var3[var4]);
            ++var4;
            if (var5) {
               break;
            }
         }

         var1.append(a("(\u0014"));
         if (var5) {
         }
      } else {
         var1.append("'");
         var4 = 0;

         while(var4 < var2.length) {
            var1.append(hexDigits[var2[var4] >> 4 & 15]);
            var1.append(hexDigits[var2[var4] & 15]);
            ++var4;
            if (var5) {
               return;
            }

            if (var5) {
               break;
            }
         }

         var1.append(a("/!"));
      }

   }

   public String[] bytesToNamedBits(byte[] var1, boolean var2) {
      boolean var10 = SnmpOidInfo.a;
      if (this._nameToNumberMap != null && this._nameToNumberMap.size() != 0) {
         Vector var3 = new Vector();
         boolean var4 = true;
         int var5 = 0;

         int var6;
         int var10000;
         label65:
         while(true) {
            var10000 = var5;

            label62:
            while(var10000 < var1.length) {
               var6 = var1[var5];
               var10000 = 0;
               if (var10) {
                  break label65;
               }

               int var7 = 0;

               while(var7 < 8) {
                  var10000 = var6 & (byte)(128 >> var7);
                  if (var10) {
                     continue label62;
                  }

                  if (var10000 != 0) {
                     label55: {
                        int var8 = 8 * var5 + var7;
                        String var9 = this.intToNamedNumber((long)var8);
                        if (var9 == null) {
                           if (var2) {
                              return null;
                           }

                           var3.add(String.valueOf(var8));
                           if (!var10) {
                              break label55;
                           }
                        }

                        var3.add(var9);
                     }
                  }

                  ++var7;
                  if (var10) {
                     break;
                  }
               }

               ++var5;
               if (!var10) {
                  continue label65;
               }
               break;
            }

            var10000 = var3.size();
            break;
         }

         String[] var11 = new String[var10000];
         var6 = 0;

         String[] var12;
         while(true) {
            if (var6 < var3.size()) {
               var12 = var11;
               if (var10) {
                  break;
               }

               var11[var6] = (String)var3.get(var6);
               ++var6;
               if (!var10) {
                  continue;
               }
            }

            var12 = var11;
            break;
         }

         return var12;
      } else {
         return null;
      }
   }

   public boolean hasRangeSpec() {
      return this._rangeSpec != null;
   }

   public RangeItem[] getRangeSpec() {
      return this._rangeSpec;
   }

   public void setRangeSpec(RangeItem[] var1) {
      this._rangeSpec = var1;
   }

   public SnmpModule getModule() {
      return this._module;
   }

   void a(SnmpModule var1) {
      this._module = var1;
   }

   public String toString() {
      TextBuffer var1 = new TextBuffer();
      this.toString(var1);
      return var1.toString();
   }

   public void toString(TextBuffer var1) {
      boolean var5;
      label93: {
         var5 = SnmpOidInfo.a;
         if (this._module != null) {
            var1.append((Object)this._name).append((Object)a("(=\fW0(Tu"));
            if (!var5) {
               break label93;
            }
         }

         var1.append((Object)a("|\u0010%b<f\u000f:'H("));
      }

      label88: {
         if (this._definedType != null) {
            var1.append((Object)this._definedType);
            if (!var5) {
               break label88;
            }
         }

         var1.append((Object)this.getSmiTypeString());
      }

      label82: {
         label81: {
            label96: {
               int var10000;
               label97: {
                  if (this._nameToNumberMap != null) {
                     var1.append((Object)a("sI"));
                     Enumeration var2 = this._nameToNumberMap.keys();

                     while(var2.hasMoreElements()) {
                        String var3 = (String)var2.nextElement();
                        Long var4 = (Long)this._nameToNumberMap.get(var3);
                        var1.append((Object)(var3 + "(" + var4 + ")"));
                        var10000 = var2.hasMoreElements();
                        if (var5) {
                           break label97;
                        }

                        if (var10000 != 0) {
                           var1.append((Object)a("$I"));
                        }

                        if (var5) {
                           break;
                        }
                     }

                     var1.append((Object)a("(\u0014"));
                  }

                  if (this._displayHint == null && this._rangeSpec == null) {
                     break label82;
                  }

                  var1.pushIndent();
                  if (this._displayHint != null) {
                     var1.append((Object)a("l\u0000&w\u0019i\u0010\u001dn\u001b|Ih'")).append((Object)this._displayHint).append((Object)"\n");
                  }

                  if (this._rangeSpec == null) {
                     break label96;
                  }

                  var10000 = this._type;
               }

               label59: {
                  if (var10000 == 4) {
                     var1.append((Object)a("{\u0000/b"));
                     if (!var5) {
                        break label59;
                     }
                  }

                  var1.append((Object)a("z\b;`\u0010"));
               }

               var1.append((Object)"(");
               int var6 = 0;

               while(var6 < this._rangeSpec.length) {
                  this._rangeSpec[var6].toString(var1);
                  if (var5) {
                     break label81;
                  }

                  if (var6 + 1 < this._rangeSpec.length) {
                     var1.append((Object)"|");
                  }

                  ++var6;
                  if (var5) {
                     break;
                  }
               }

               var1.append((Object)a("!c"));
            }

            var1.popIndent();
         }

         if (!var5) {
            return;
         }
      }

      var1.append((Object)"\n");
   }

   public void setBaseType(SnmpTypeInfo var1) {
      this._baseType = var1;
   }

   public SnmpTypeInfo getBaseType() {
      return this._baseType;
   }

   public void setDescription(String var1) {
      this._description = var1;
   }

   public String getDescription() {
      return this._description;
   }

   public void setStatus(String var1) {
      this._status = var1;
   }

   public String getStatus() {
      return this._status;
   }

   public int getSmiType() {
      return this._smiType;
   }

   public void setSmiType(int var1) {
      this._smiType = var1;
   }

   public void setSmiType(String var1) {
      this._smiType = Snmp.stringToSmiType(var1);
   }

   public String getSmiTypeString() {
      return Snmp.smiTypeToString(this._smiType);
   }

   public String getSmiTypeShortString() {
      return Snmp.smiTypeToShortString(this._smiType);
   }

   public SnmpTypeInfo getTypeRef() {
      return this._typeRef;
   }

   public void setTypeRef(SnmpTypeInfo var1) {
      this._typeRef = var1;
   }

   public String getTypeRefName() {
      return this._typeRefName;
   }

   public void setTypeRefName(String var1) {
      this._typeRefName = var1;
   }

   private static String a(String var0) {
      char[] var1 = var0.toCharArray();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         char var10002 = var1[var3];
         byte var10003;
         switch (var3 % 5) {
            case 0:
               var10003 = 8;
               break;
            case 1:
               var10003 = 105;
               break;
            case 2:
               var10003 = 85;
               break;
            case 3:
               var10003 = 7;
               break;
            default:
               var10003 = 117;
         }

         var1[var3] = (char)(var10002 ^ var10003);
      }

      return new String(var1);
   }
}
