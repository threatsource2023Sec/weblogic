package weblogic.apache.xerces.impl.xs.util;

import weblogic.apache.xerces.util.SymbolHash;
import weblogic.apache.xerces.xs.XSObject;
import weblogic.apache.xerces.xs.XSTypeDefinition;

public final class XSNamedMap4Types extends XSNamedMapImpl {
   private final short fType;

   public XSNamedMap4Types(String var1, SymbolHash var2, short var3) {
      super(var1, var2);
      this.fType = var3;
   }

   public XSNamedMap4Types(String[] var1, SymbolHash[] var2, int var3, short var4) {
      super(var1, var2, var3);
      this.fType = var4;
   }

   public synchronized int getLength() {
      if (this.fLength == -1) {
         int var1 = 0;

         for(int var2 = 0; var2 < this.fNSNum; ++var2) {
            var1 += this.fMaps[var2].getLength();
         }

         int var3 = 0;
         XSObject[] var4 = new XSObject[var1];

         for(int var5 = 0; var5 < this.fNSNum; ++var5) {
            var3 += this.fMaps[var5].getValues(var4, var3);
         }

         this.fLength = 0;
         this.fArray = new XSObject[var1];

         for(int var7 = 0; var7 < var1; ++var7) {
            XSTypeDefinition var6 = (XSTypeDefinition)var4[var7];
            if (var6.getTypeCategory() == this.fType) {
               this.fArray[this.fLength++] = var6;
            }
         }
      }

      return this.fLength;
   }

   public XSObject itemByName(String var1, String var2) {
      for(int var3 = 0; var3 < this.fNSNum; ++var3) {
         if (XSNamedMapImpl.isEqual(var1, this.fNamespaces[var3])) {
            XSTypeDefinition var4 = (XSTypeDefinition)this.fMaps[var3].get(var2);
            if (var4 != null && var4.getTypeCategory() == this.fType) {
               return var4;
            }

            return null;
         }
      }

      return null;
   }

   public synchronized XSObject item(int var1) {
      if (this.fArray == null) {
         this.getLength();
      }

      return var1 >= 0 && var1 < this.fLength ? this.fArray[var1] : null;
   }
}
