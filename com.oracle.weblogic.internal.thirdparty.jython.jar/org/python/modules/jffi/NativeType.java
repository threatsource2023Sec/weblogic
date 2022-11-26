package org.python.modules.jffi;

import com.kenai.jffi.Type;

public enum NativeType {
   VOID,
   BOOL,
   BYTE,
   UBYTE,
   SHORT,
   USHORT,
   INT,
   UINT,
   LONGLONG,
   ULONGLONG,
   LONG,
   ULONG,
   FLOAT,
   DOUBLE,
   POINTER,
   BUFFER_IN,
   BUFFER_OUT,
   BUFFER_INOUT,
   STRING,
   ARRAY,
   STRUCT;

   static final Type jffiType(NativeType type) {
      switch (type) {
         case VOID:
            return Type.VOID;
         case BYTE:
            return Type.SINT8;
         case UBYTE:
            return Type.UINT8;
         case SHORT:
            return Type.SINT16;
         case USHORT:
            return Type.UINT16;
         case INT:
         case BOOL:
            return Type.SINT32;
         case UINT:
            return Type.UINT32;
         case LONGLONG:
            return Type.SINT64;
         case ULONGLONG:
            return Type.UINT64;
         case LONG:
            return Type.SLONG;
         case ULONG:
            return Type.ULONG;
         case FLOAT:
            return Type.FLOAT;
         case DOUBLE:
            return Type.DOUBLE;
         case POINTER:
         case STRING:
            return Type.POINTER;
         default:
            throw new RuntimeException("Unknown type " + type);
      }
   }
}
