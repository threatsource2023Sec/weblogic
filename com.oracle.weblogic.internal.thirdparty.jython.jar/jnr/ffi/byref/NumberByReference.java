package jnr.ffi.byref;

import jnr.ffi.Pointer;
import jnr.ffi.Runtime;
import jnr.ffi.TypeAlias;

public class NumberByReference extends AbstractNumberReference {
   private final TypeAlias typeAlias;

   public NumberByReference(TypeAlias typeAlias, Number value) {
      super(checkNull(value));
      this.typeAlias = typeAlias;
   }

   public NumberByReference(TypeAlias typeAlias) {
      super(0);
      this.typeAlias = typeAlias;
   }

   public int nativeSize(Runtime runtime) {
      return runtime.findType(this.typeAlias).size();
   }

   public void fromNative(Runtime runtime, Pointer memory, long offset) {
      switch (runtime.findType(this.typeAlias).getNativeType()) {
         case SCHAR:
         case UCHAR:
            this.value = memory.getByte(offset);
            break;
         case SSHORT:
         case USHORT:
            this.value = memory.getShort(offset);
            break;
         case SINT:
         case UINT:
            this.value = memory.getInt(offset);
            break;
         case SLONG:
         case ULONG:
            this.value = memory.getLong(offset);
            break;
         case SLONGLONG:
         case ULONGLONG:
            this.value = memory.getLongLong(offset);
            break;
         case ADDRESS:
            this.value = memory.getAddress(offset);
            break;
         case FLOAT:
            this.value = memory.getFloat(offset);
            break;
         case DOUBLE:
            this.value = memory.getDouble(offset);
            break;
         default:
            throw new UnsupportedOperationException("unsupported type: " + this.typeAlias);
      }

   }

   public void toNative(Runtime runtime, Pointer memory, long offset) {
      switch (runtime.findType(this.typeAlias).getNativeType()) {
         case SCHAR:
         case UCHAR:
            memory.putByte(offset, this.value.byteValue());
            break;
         case SSHORT:
         case USHORT:
            memory.putShort(offset, this.value.shortValue());
            break;
         case SINT:
         case UINT:
            memory.putInt(offset, this.value.intValue());
            break;
         case SLONG:
         case ULONG:
            memory.putLong(offset, this.value.longValue());
            break;
         case SLONGLONG:
         case ULONGLONG:
            memory.putLongLong(offset, this.value.longValue());
            break;
         case ADDRESS:
            memory.putAddress(offset, this.value.longValue());
            break;
         case FLOAT:
            memory.putFloat(offset, this.value.floatValue());
            break;
         case DOUBLE:
            memory.putDouble(offset, this.value.doubleValue());
            break;
         default:
            throw new UnsupportedOperationException("unsupported type: " + this.typeAlias);
      }

   }
}
