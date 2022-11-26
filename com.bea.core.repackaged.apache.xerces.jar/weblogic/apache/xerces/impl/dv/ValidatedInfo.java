package weblogic.apache.xerces.impl.dv;

import weblogic.apache.xerces.impl.xs.util.ShortListImpl;
import weblogic.apache.xerces.impl.xs.util.XSObjectListImpl;
import weblogic.apache.xerces.xs.ShortList;
import weblogic.apache.xerces.xs.XSObjectList;
import weblogic.apache.xerces.xs.XSSimpleTypeDefinition;
import weblogic.apache.xerces.xs.XSValue;

public class ValidatedInfo implements XSValue {
   public String normalizedValue;
   public Object actualValue;
   public short actualValueType;
   public XSSimpleType actualType;
   public XSSimpleType memberType;
   public XSSimpleType[] memberTypes;
   public ShortList itemValueTypes;

   public void reset() {
      this.normalizedValue = null;
      this.actualValue = null;
      this.actualValueType = 45;
      this.actualType = null;
      this.memberType = null;
      this.memberTypes = null;
      this.itemValueTypes = null;
   }

   public String stringValue() {
      return this.actualValue == null ? this.normalizedValue : this.actualValue.toString();
   }

   public static boolean isComparable(ValidatedInfo var0, ValidatedInfo var1) {
      short var2 = convertToPrimitiveKind(var0.actualValueType);
      short var3 = convertToPrimitiveKind(var1.actualValueType);
      if (var2 != var3) {
         return var2 == 1 && var3 == 2 || var2 == 2 && var3 == 1;
      } else {
         if (var2 == 44 || var2 == 43) {
            ShortList var4 = var0.itemValueTypes;
            ShortList var5 = var1.itemValueTypes;
            int var6 = var4 != null ? var4.getLength() : 0;
            int var7 = var5 != null ? var5.getLength() : 0;
            if (var6 != var7) {
               return false;
            }

            for(int var8 = 0; var8 < var6; ++var8) {
               short var9 = convertToPrimitiveKind(var4.item(var8));
               short var10 = convertToPrimitiveKind(var5.item(var8));
               if (var9 != var10 && (var9 != 1 || var10 != 2) && (var9 != 2 || var10 != 1)) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   private static short convertToPrimitiveKind(short var0) {
      if (var0 <= 20) {
         return var0;
      } else if (var0 <= 29) {
         return 2;
      } else {
         return var0 <= 42 ? 4 : var0;
      }
   }

   public Object getActualValue() {
      return this.actualValue;
   }

   public short getActualValueType() {
      return this.actualValueType;
   }

   public ShortList getListValueTypes() {
      return (ShortList)(this.itemValueTypes == null ? ShortListImpl.EMPTY_LIST : this.itemValueTypes);
   }

   public XSObjectList getMemberTypeDefinitions() {
      return this.memberTypes == null ? XSObjectListImpl.EMPTY_LIST : new XSObjectListImpl(this.memberTypes, this.memberTypes.length);
   }

   public String getNormalizedValue() {
      return this.normalizedValue;
   }

   public XSSimpleTypeDefinition getTypeDefinition() {
      return this.actualType;
   }

   public XSSimpleTypeDefinition getMemberTypeDefinition() {
      return this.memberType;
   }

   public void copyFrom(XSValue var1) {
      if (var1 == null) {
         this.reset();
      } else if (var1 instanceof ValidatedInfo) {
         ValidatedInfo var2 = (ValidatedInfo)var1;
         this.normalizedValue = var2.normalizedValue;
         this.actualValue = var2.actualValue;
         this.actualValueType = var2.actualValueType;
         this.actualType = var2.actualType;
         this.memberType = var2.memberType;
         this.memberTypes = var2.memberTypes;
         this.itemValueTypes = var2.itemValueTypes;
      } else {
         this.normalizedValue = var1.getNormalizedValue();
         this.actualValue = var1.getActualValue();
         this.actualValueType = var1.getActualValueType();
         this.actualType = (XSSimpleType)var1.getTypeDefinition();
         this.memberType = (XSSimpleType)var1.getMemberTypeDefinition();
         XSSimpleType var5 = this.memberType == null ? this.actualType : this.memberType;
         if (var5 != null && var5.getBuiltInKind() == 43) {
            XSObjectList var3 = var1.getMemberTypeDefinitions();
            this.memberTypes = new XSSimpleType[var3.getLength()];

            for(int var4 = 0; var4 < var3.getLength(); ++var4) {
               this.memberTypes[var4] = (XSSimpleType)var3.get(var4);
            }
         } else {
            this.memberTypes = null;
         }

         this.itemValueTypes = var1.getListValueTypes();
      }

   }
}
