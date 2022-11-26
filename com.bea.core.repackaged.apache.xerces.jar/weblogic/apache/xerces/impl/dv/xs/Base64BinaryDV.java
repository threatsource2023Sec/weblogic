package weblogic.apache.xerces.impl.dv.xs;

import weblogic.apache.xerces.impl.dv.InvalidDatatypeValueException;
import weblogic.apache.xerces.impl.dv.ValidationContext;
import weblogic.apache.xerces.impl.dv.util.Base64;
import weblogic.apache.xerces.impl.dv.util.ByteListImpl;

public class Base64BinaryDV extends TypeValidator {
   public short getAllowedFacets() {
      return 2079;
   }

   public Object getActualValue(String var1, ValidationContext var2) throws InvalidDatatypeValueException {
      byte[] var3 = Base64.decode(var1);
      if (var3 == null) {
         throw new InvalidDatatypeValueException("cvc-datatype-valid.1.2.1", new Object[]{var1, "base64Binary"});
      } else {
         return new XBase64(var3);
      }
   }

   public int getDataLength(Object var1) {
      return ((XBase64)var1).getLength();
   }

   private static final class XBase64 extends ByteListImpl {
      public XBase64(byte[] var1) {
         super(var1);
      }

      public synchronized String toString() {
         if (this.canonical == null) {
            this.canonical = Base64.encode(this.data);
         }

         return this.canonical;
      }

      public boolean equals(Object var1) {
         if (!(var1 instanceof XBase64)) {
            return false;
         } else {
            byte[] var2 = ((XBase64)var1).data;
            int var3 = this.data.length;
            if (var3 != var2.length) {
               return false;
            } else {
               for(int var4 = 0; var4 < var3; ++var4) {
                  if (this.data[var4] != var2[var4]) {
                     return false;
                  }
               }

               return true;
            }
         }
      }

      public int hashCode() {
         int var1 = 0;

         for(int var2 = 0; var2 < this.data.length; ++var2) {
            var1 = var1 * 37 + (this.data[var2] & 255);
         }

         return var1;
      }
   }
}
