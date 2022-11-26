package com.kenai.jffi;

import java.util.List;

public abstract class Type {
   public static final Type VOID;
   public static final Type FLOAT;
   public static final Type DOUBLE;
   public static final Type LONGDOUBLE;
   public static final Type UINT8;
   public static final Type SINT8;
   public static final Type UINT16;
   public static final Type SINT16;
   public static final Type UINT32;
   public static final Type SINT32;
   public static final Type UINT64;
   public static final Type SINT64;
   public static final Type POINTER;
   public static final Type UCHAR;
   public static final Type SCHAR;
   public static final Type USHORT;
   public static final Type SSHORT;
   public static final Type UINT;
   public static final Type SINT;
   public static final Type ULONG;
   public static final Type SLONG;
   public static final Type ULONG_LONG;
   public static final Type SLONG_LONG;
   private int type = 0;
   private int size = 0;
   private int alignment = 0;
   private volatile long handle = 0L;

   public final int type() {
      return this.type != 0 ? this.type : this.resolveType();
   }

   final long handle() {
      return this.handle != 0L ? this.handle : this.resolveHandle();
   }

   public final int size() {
      return this.size != 0 ? this.size : this.resolveSize();
   }

   public final int alignment() {
      return this.alignment != 0 ? this.alignment : this.resolveAlignment();
   }

   private int resolveType() {
      return this.type = this.getTypeInfo().type;
   }

   private int resolveSize() {
      return this.size = this.getTypeInfo().size;
   }

   private int resolveAlignment() {
      return this.alignment = this.getTypeInfo().alignment;
   }

   private long resolveHandle() {
      return this.handle = this.getTypeInfo().handle;
   }

   abstract TypeInfo getTypeInfo();

   public boolean equals(Object obj) {
      return obj instanceof Type && ((Type)obj).handle() == this.handle();
   }

   public int hashCode() {
      int hash = 3;
      hash = 67 * hash + (int)(this.handle() ^ this.handle() >>> 32);
      return hash;
   }

   static long[] nativeHandles(Type[] types) {
      long[] nativeTypes = new long[types.length];

      for(int i = 0; i < types.length; ++i) {
         nativeTypes[i] = types[i].handle();
      }

      return nativeTypes;
   }

   static long[] nativeHandles(List types) {
      long[] nativeTypes = new long[types.size()];

      for(int i = 0; i < nativeTypes.length; ++i) {
         nativeTypes[i] = ((Type)types.get(i)).handle();
      }

      return nativeTypes;
   }

   private static Type builtin(NativeType nativeType) {
      return new Builtin(nativeType);
   }

   static {
      VOID = builtin(NativeType.VOID);
      FLOAT = builtin(NativeType.FLOAT);
      DOUBLE = builtin(NativeType.DOUBLE);
      LONGDOUBLE = builtin(NativeType.LONGDOUBLE);
      UINT8 = builtin(NativeType.UINT8);
      SINT8 = builtin(NativeType.SINT8);
      UINT16 = builtin(NativeType.UINT16);
      SINT16 = builtin(NativeType.SINT16);
      UINT32 = builtin(NativeType.UINT32);
      SINT32 = builtin(NativeType.SINT32);
      UINT64 = builtin(NativeType.UINT64);
      SINT64 = builtin(NativeType.SINT64);
      POINTER = builtin(NativeType.POINTER);
      UCHAR = UINT8;
      SCHAR = SINT8;
      USHORT = UINT16;
      SSHORT = SINT16;
      UINT = UINT32;
      SINT = SINT32;
      ULONG = builtin(NativeType.ULONG);
      SLONG = builtin(NativeType.SLONG);
      ULONG_LONG = UINT64;
      SLONG_LONG = SINT64;
   }

   static final class TypeInfo {
      final int type;
      final int size;
      final int alignment;
      final long handle;

      TypeInfo(long handle, int type, int size, int alignment) {
         this.handle = handle;
         this.type = type;
         this.size = size;
         this.alignment = alignment;
      }
   }

   static final class Builtin extends Type {
      private final NativeType nativeType;
      private TypeInfo typeInfo;

      private Builtin(NativeType nativeType) {
         this.nativeType = nativeType;
      }

      TypeInfo getTypeInfo() {
         return this.typeInfo != null ? this.typeInfo : this.lookupTypeInfo();
      }

      private TypeInfo lookupTypeInfo() {
         try {
            Foreign foreign = Foreign.getInstance();
            long handle = foreign.lookupBuiltinType(this.nativeType.ffiType);
            if (handle == 0L) {
               throw new NullPointerException("invalid handle for native type " + this.nativeType);
            } else {
               return this.typeInfo = new TypeInfo(handle, foreign.getTypeType(handle), foreign.getTypeSize(handle), foreign.getTypeAlign(handle));
            }
         } catch (Throwable var5) {
            UnsatisfiedLinkError ule = new UnsatisfiedLinkError("could not get native definition for type: " + this.nativeType);
            var5.initCause(var5);
            throw ule;
         }
      }

      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            if (!super.equals(o)) {
               return false;
            } else {
               Builtin builtin = (Builtin)o;
               return this.nativeType == builtin.nativeType;
            }
         } else {
            return false;
         }
      }

      public int hashCode() {
         int result = super.hashCode();
         result = 31 * result + this.nativeType.hashCode();
         return result;
      }

      // $FF: synthetic method
      Builtin(NativeType x0, Object x1) {
         this(x0);
      }
   }
}
