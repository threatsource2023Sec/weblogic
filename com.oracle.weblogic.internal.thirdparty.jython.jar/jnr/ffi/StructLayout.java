package jnr.ffi;

import java.lang.reflect.Constructor;
import java.nio.charset.Charset;
import jnr.ffi.util.EnumMapper;

public class StructLayout extends Type {
   static final Charset ASCII = Charset.forName("ASCII");
   static final Charset UTF8 = Charset.forName("UTF-8");
   private final Runtime runtime;
   private final boolean isUnion = false;
   private boolean resetIndex = false;
   StructLayout enclosing = null;
   int offset = 0;
   int size = 0;
   int alignment = 1;
   int paddedSize = 0;

   protected StructLayout(Runtime runtime) {
      this.runtime = runtime;
   }

   protected StructLayout(Runtime runtime, int structSize) {
      this.runtime = runtime;
      this.size = this.paddedSize = structSize;
   }

   public final Runtime getRuntime() {
      return this.runtime;
   }

   public final int size() {
      return this.paddedSize;
   }

   public final int alignment() {
      return this.alignment;
   }

   public final int offset() {
      return this.offset;
   }

   public NativeType getNativeType() {
      return NativeType.STRUCT;
   }

   public java.lang.String toString() {
      StringBuilder sb = new StringBuilder();
      java.lang.reflect.Field[] fields = this.getClass().getDeclaredFields();
      sb.append(this.getClass().getSimpleName()).append(" { \n");
      java.lang.String fieldPrefix = "    ";
      java.lang.reflect.Field[] var4 = fields;
      int var5 = fields.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         java.lang.reflect.Field var10000 = var4[var6];

         try {
            sb.append("    ").append('\n');
         } catch (Throwable var9) {
            throw new RuntimeException(var9);
         }
      }

      sb.append("}\n");
      return sb.toString();
   }

   private static int align(int offset, int alignment) {
      return offset + alignment - 1 & ~(alignment - 1);
   }

   protected final int addField(int size, int align) {
      int off = this.resetIndex ? 0 : align(this.size, align);
      this.size = Math.max(this.size, off + size);
      this.alignment = Math.max(this.alignment, align);
      this.paddedSize = align(this.size, this.alignment);
      return off;
   }

   protected final int addField(int size, int align, Offset offset) {
      this.size = Math.max(this.size, offset.intValue() + size);
      this.alignment = Math.max(this.alignment, align);
      this.paddedSize = align(this.size, this.alignment);
      return offset.intValue();
   }

   protected final int addField(Type t) {
      return this.addField(t.size(), t.alignment());
   }

   protected final int addField(Type t, Offset offset) {
      return this.addField(t.size(), t.alignment(), offset);
   }

   protected final Offset at(int offset) {
      return new Offset(offset);
   }

   protected final void arrayBegin() {
      this.resetIndex = false;
   }

   protected final void arrayEnd() {
      this.resetIndex = false;
   }

   protected Field[] array(Field[] array) {
      this.arrayBegin();

      try {
         Class arrayClass = array.getClass().getComponentType();
         Constructor ctor = arrayClass.getDeclaredConstructor(arrayClass.getEnclosingClass());
         Object[] parameters = new Object[]{this};

         for(int i = 0; i < array.length; ++i) {
            array[i] = (Field)ctor.newInstance(parameters);
         }
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }

      this.arrayEnd();
      return array;
   }

   protected final StructLayout inner(StructLayout structLayout) {
      structLayout.enclosing = this;
      structLayout.offset = align(this.size, structLayout.alignment);
      this.size = structLayout.offset + structLayout.size;
      this.paddedSize = align(this.size, this.alignment());
      return structLayout;
   }

   protected final Function function(Class closureClass) {
      return new Function(closureClass);
   }

   protected final Function function(Class closureClass, Offset offset) {
      return new Function(closureClass, offset);
   }

   public final class rlim_t extends IntegerAlias {
      public rlim_t() {
         super(TypeAlias.rlim_t);
      }

      public rlim_t(Offset offset) {
         super(TypeAlias.rlim_t, offset);
      }
   }

   public final class socklen_t extends IntegerAlias {
      public socklen_t() {
         super(TypeAlias.socklen_t);
      }

      public socklen_t(Offset offset) {
         super(TypeAlias.socklen_t, offset);
      }
   }

   public final class sa_family_t extends IntegerAlias {
      public sa_family_t() {
         super(TypeAlias.sa_family_t);
      }

      public sa_family_t(Offset offset) {
         super(TypeAlias.sa_family_t, offset);
      }
   }

   public final class fsfilcnt_t extends IntegerAlias {
      public fsfilcnt_t() {
         super(TypeAlias.fsfilcnt_t);
      }

      public fsfilcnt_t(Offset offset) {
         super(TypeAlias.fsfilcnt_t, offset);
      }
   }

   public final class fsblkcnt_t extends IntegerAlias {
      public fsblkcnt_t() {
         super(TypeAlias.fsblkcnt_t);
      }

      public fsblkcnt_t(Offset offset) {
         super(TypeAlias.fsblkcnt_t, offset);
      }
   }

   public final class time_t extends IntegerAlias {
      public time_t() {
         super(TypeAlias.time_t);
      }

      public time_t(Offset offset) {
         super(TypeAlias.time_t, offset);
      }
   }

   public final class ssize_t extends IntegerAlias {
      public ssize_t() {
         super(TypeAlias.ssize_t);
      }

      public ssize_t(Offset offset) {
         super(TypeAlias.ssize_t, offset);
      }
   }

   public final class size_t extends IntegerAlias {
      public size_t() {
         super(TypeAlias.size_t);
      }

      public size_t(Offset offset) {
         super(TypeAlias.size_t, offset);
      }
   }

   public final class clock_t extends IntegerAlias {
      public clock_t() {
         super(TypeAlias.clock_t);
      }

      public clock_t(Offset offset) {
         super(TypeAlias.clock_t, offset);
      }
   }

   public final class uid_t extends IntegerAlias {
      public uid_t() {
         super(TypeAlias.uid_t);
      }

      public uid_t(Offset offset) {
         super(TypeAlias.uid_t, offset);
      }
   }

   public final class swblk_t extends IntegerAlias {
      public swblk_t() {
         super(TypeAlias.swblk_t);
      }

      public swblk_t(Offset offset) {
         super(TypeAlias.swblk_t, offset);
      }
   }

   public final class off_t extends IntegerAlias {
      public off_t() {
         super(TypeAlias.off_t);
      }

      public off_t(Offset offset) {
         super(TypeAlias.off_t, offset);
      }
   }

   public final class pid_t extends IntegerAlias {
      public pid_t() {
         super(TypeAlias.pid_t);
      }

      public pid_t(Offset offset) {
         super(TypeAlias.pid_t, offset);
      }
   }

   public final class id_t extends IntegerAlias {
      public id_t() {
         super(TypeAlias.id_t);
      }

      public id_t(Offset offset) {
         super(TypeAlias.id_t, offset);
      }
   }

   public final class nlink_t extends IntegerAlias {
      public nlink_t() {
         super(TypeAlias.nlink_t);
      }

      public nlink_t(Offset offset) {
         super(TypeAlias.nlink_t, offset);
      }
   }

   public final class mode_t extends IntegerAlias {
      public mode_t() {
         super(TypeAlias.mode_t);
      }

      public mode_t(Offset offset) {
         super(TypeAlias.mode_t, offset);
      }
   }

   public final class key_t extends IntegerAlias {
      public key_t() {
         super(TypeAlias.key_t);
      }

      public key_t(Offset offset) {
         super(TypeAlias.key_t, offset);
      }
   }

   public final class ino64_t extends IntegerAlias {
      public ino64_t() {
         super(TypeAlias.ino64_t);
      }

      public ino64_t(Offset offset) {
         super(TypeAlias.ino64_t, offset);
      }
   }

   public final class ino_t extends IntegerAlias {
      public ino_t() {
         super(TypeAlias.ino_t);
      }

      public ino_t(Offset offset) {
         super(TypeAlias.ino_t, offset);
      }
   }

   public final class in_port_t extends IntegerAlias {
      public in_port_t() {
         super(TypeAlias.in_port_t);
      }

      public in_port_t(Offset offset) {
         super(TypeAlias.in_port_t, offset);
      }
   }

   public final class in_addr_t extends IntegerAlias {
      public in_addr_t() {
         super(TypeAlias.in_addr_t);
      }

      public in_addr_t(Offset offset) {
         super(TypeAlias.in_addr_t, offset);
      }
   }

   public final class gid_t extends IntegerAlias {
      public gid_t() {
         super(TypeAlias.gid_t);
      }

      public gid_t(Offset offset) {
         super(TypeAlias.gid_t, offset);
      }
   }

   public final class blksize_t extends IntegerAlias {
      public blksize_t() {
         super(TypeAlias.blksize_t);
      }

      public blksize_t(Offset offset) {
         super(TypeAlias.blksize_t, offset);
      }
   }

   public final class blkcnt_t extends IntegerAlias {
      public blkcnt_t() {
         super(TypeAlias.blkcnt_t);
      }

      public blkcnt_t(Offset offset) {
         super(TypeAlias.blkcnt_t, offset);
      }
   }

   public final class dev_t extends IntegerAlias {
      public dev_t() {
         super(TypeAlias.dev_t);
      }

      public dev_t(Offset offset) {
         super(TypeAlias.dev_t, offset);
      }
   }

   public final class caddr_t extends IntegerAlias {
      public caddr_t() {
         super(TypeAlias.caddr_t);
      }

      public caddr_t(Offset offset) {
         super(TypeAlias.caddr_t, offset);
      }
   }

   public final class uintptr_t extends IntegerAlias {
      public uintptr_t() {
         super(TypeAlias.uintptr_t);
      }

      public uintptr_t(Offset offset) {
         super(TypeAlias.uintptr_t, offset);
      }
   }

   public final class intptr_t extends IntegerAlias {
      public intptr_t() {
         super(TypeAlias.intptr_t);
      }

      public intptr_t(Offset offset) {
         super(TypeAlias.intptr_t, offset);
      }
   }

   public final class u_int64_t extends IntegerAlias {
      public u_int64_t() {
         super(TypeAlias.u_int64_t);
      }

      public u_int64_t(Offset offset) {
         super(TypeAlias.u_int64_t, offset);
      }
   }

   public final class int64_t extends IntegerAlias {
      public int64_t() {
         super(TypeAlias.int64_t);
      }

      public int64_t(Offset offset) {
         super(TypeAlias.int64_t, offset);
      }
   }

   public final class u_int32_t extends IntegerAlias {
      public u_int32_t() {
         super(TypeAlias.u_int32_t);
      }

      public u_int32_t(Offset offset) {
         super(TypeAlias.u_int32_t, offset);
      }
   }

   public final class int32_t extends IntegerAlias {
      public int32_t() {
         super(TypeAlias.int32_t);
      }

      public int32_t(Offset offset) {
         super(TypeAlias.int32_t, offset);
      }
   }

   public final class u_int16_t extends IntegerAlias {
      public u_int16_t() {
         super(TypeAlias.u_int16_t);
      }

      public u_int16_t(Offset offset) {
         super(TypeAlias.u_int16_t, offset);
      }
   }

   public final class int16_t extends IntegerAlias {
      public int16_t() {
         super(TypeAlias.int16_t);
      }

      public int16_t(Offset offset) {
         super(TypeAlias.int16_t, offset);
      }
   }

   public final class u_int8_t extends IntegerAlias {
      public u_int8_t() {
         super(TypeAlias.u_int8_t);
      }

      public u_int8_t(Offset offset) {
         super(TypeAlias.u_int8_t, offset);
      }
   }

   public final class int8_t extends IntegerAlias {
      public int8_t() {
         super(TypeAlias.int8_t);
      }

      public int8_t(Offset offset) {
         super(TypeAlias.int8_t, offset);
      }
   }

   protected final class Function extends AbstractField {
      private final Class closureClass;
      private Object instance;

      public Function(Class closureClass) {
         super((NativeType)NativeType.ADDRESS);
         this.closureClass = closureClass;
      }

      public Function(Class closureClass, Offset offset) {
         super((NativeType)NativeType.ADDRESS, offset);
         this.closureClass = closureClass;
      }

      public final void set(jnr.ffi.Pointer ptr, Object value) {
         ptr.putPointer(this.offset(), StructLayout.this.getRuntime().getClosureManager().getClosurePointer(this.closureClass, this.instance = value));
      }
   }

   protected final class Padding extends AbstractField {
      public Padding(Type type, int length) {
         super(type.size() * length, type.alignment());
      }

      public Padding(Type type, int length, Offset offset) {
         super(type.size() * length, type.alignment(), offset);
      }

      public Padding(NativeType type, int length) {
         this((Type)StructLayout.this.getRuntime().findType(type), length);
      }

      public Padding(NativeType type, int length, Offset offset) {
         this((Type)StructLayout.this.getRuntime().findType(type), length);
      }
   }

   public class AsciiStringRef extends UTFStringRef {
      public AsciiStringRef(int size) {
         super(size, StructLayout.ASCII);
      }

      public AsciiStringRef(int size, Offset offset) {
         super(size, StructLayout.ASCII, offset);
      }

      public AsciiStringRef() {
         super(Integer.MAX_VALUE, StructLayout.ASCII);
      }
   }

   public class UTF8StringRef extends UTFStringRef {
      public UTF8StringRef(int size) {
         super(size, StructLayout.UTF8);
      }

      public UTF8StringRef(int size, Offset offset) {
         super(size, StructLayout.UTF8, offset);
      }

      public UTF8StringRef() {
         super(Integer.MAX_VALUE, StructLayout.UTF8);
      }
   }

   public class UTFStringRef extends String {
      private jnr.ffi.Pointer valueHolder;

      public UTFStringRef(int length, Charset cs) {
         super(StructLayout.this.getRuntime().findType(NativeType.ADDRESS).size(), StructLayout.this.getRuntime().findType(NativeType.ADDRESS).alignment(), length, cs);
      }

      public UTFStringRef(int length, Charset cs, Offset offset) {
         super(StructLayout.this.getRuntime().findType(NativeType.ADDRESS).size(), StructLayout.this.getRuntime().findType(NativeType.ADDRESS).alignment(), offset, length, cs);
      }

      public UTFStringRef(Charset cs) {
         this(Integer.MAX_VALUE, cs);
      }

      protected jnr.ffi.Pointer getStringMemory(jnr.ffi.Pointer ptr) {
         return ptr.getPointer(this.offset(), (long)this.length());
      }

      public final java.lang.String get(jnr.ffi.Pointer ptr) {
         jnr.ffi.Pointer memory = this.getStringMemory(ptr);
         return memory != null ? memory.getString(0L, this.length, this.charset) : null;
      }

      public final void set(jnr.ffi.Pointer ptr, java.lang.String value) {
         if (value != null) {
            this.valueHolder = StructLayout.this.getRuntime().getMemoryManager().allocateDirect(this.length() * 4);
            this.valueHolder.putString(0L, value, this.length() * 4, this.charset);
            ptr.putPointer(this.offset(), this.valueHolder);
         } else {
            this.valueHolder = null;
            ptr.putAddress(this.offset(), 0L);
         }

      }
   }

   public class AsciiString extends UTFString {
      public AsciiString(int size) {
         super(size, StructLayout.ASCII);
      }

      public AsciiString(int size, Offset offset) {
         super(size, StructLayout.ASCII, offset);
      }
   }

   public class UTF8String extends UTFString {
      public UTF8String(int size) {
         super(size, StructLayout.UTF8);
      }

      public UTF8String(int size, Offset offset) {
         super(size, StructLayout.UTF8, offset);
      }
   }

   public class UTFString extends String {
      public UTFString(int length, Charset cs) {
         super(length, 1, length, cs);
      }

      public UTFString(int length, Charset cs, Offset offset) {
         super(length, 1, offset, length, cs);
      }

      protected jnr.ffi.Pointer getStringMemory(jnr.ffi.Pointer ptr) {
         return ptr.slice(this.offset(), (long)this.length());
      }

      public final java.lang.String get(jnr.ffi.Pointer ptr) {
         return this.getStringMemory(ptr).getString(0L, this.length, this.charset);
      }

      public final void set(jnr.ffi.Pointer ptr, java.lang.String value) {
         this.getStringMemory(ptr).putString(0L, value, this.length, this.charset);
      }
   }

   public abstract class String extends AbstractField {
      protected final Charset charset;
      protected final int length;

      protected String(int size, int align, int length, Charset cs) {
         super(size, align);
         this.length = length;
         this.charset = cs;
      }

      protected String(int size, int align, Offset offset, int length, Charset cs) {
         super(size, align, offset);
         this.length = length;
         this.charset = cs;
      }

      public final int length() {
         return this.length;
      }

      protected abstract jnr.ffi.Pointer getStringMemory(jnr.ffi.Pointer var1);

      public abstract java.lang.String get(jnr.ffi.Pointer var1);

      public abstract void set(jnr.ffi.Pointer var1, java.lang.String var2);

      public final java.lang.String toString(jnr.ffi.Pointer ptr) {
         return this.get(ptr);
      }
   }

   public class Enum extends Enum32 {
      public Enum(Class enumClass) {
         super(enumClass);
      }

      public Enum(Class enumClass, Offset offset) {
         super(enumClass, offset);
      }
   }

   public class EnumLong extends EnumField {
      public EnumLong(Class enumClass) {
         super(NativeType.SLONG, enumClass);
      }

      public EnumLong(Class enumClass, Offset offset) {
         super(NativeType.SLONG, enumClass, offset);
      }

      public final void set(jnr.ffi.Pointer ptr, java.lang.Enum value) {
         ptr.putNativeLong(this.offset(), (long)this.enumMapper.intValue(value));
      }

      public void set(jnr.ffi.Pointer ptr, Number value) {
         ptr.putNativeLong(this.offset(), value.longValue());
      }

      public final int intValue(jnr.ffi.Pointer ptr) {
         return (int)this.longValue(ptr);
      }

      public final long longValue(jnr.ffi.Pointer ptr) {
         return ptr.getNativeLong(this.offset());
      }
   }

   public class Enum64 extends EnumField {
      public Enum64(Class enumClass) {
         super(NativeType.SLONGLONG, enumClass);
      }

      public Enum64(Class enumClass, Offset offset) {
         super(NativeType.SLONGLONG, enumClass, offset);
      }

      public final void set(jnr.ffi.Pointer ptr, java.lang.Enum value) {
         ptr.putLongLong(this.offset(), (long)this.enumMapper.intValue(value));
      }

      public void set(jnr.ffi.Pointer ptr, Number value) {
         ptr.putLongLong(this.offset(), value.longValue());
      }

      public final int intValue(jnr.ffi.Pointer ptr) {
         return (int)this.longValue(ptr);
      }

      public final long longValue(jnr.ffi.Pointer ptr) {
         return ptr.getLongLong(this.offset());
      }
   }

   public class Enum32 extends EnumField {
      public Enum32(Class enumClass) {
         super(NativeType.SINT, enumClass);
      }

      public Enum32(Class enumClass, Offset offset) {
         super(NativeType.SINT, enumClass, offset);
      }

      public void set(jnr.ffi.Pointer ptr, java.lang.Enum value) {
         ptr.putInt(this.offset(), this.enumMapper.intValue(value));
      }

      public void set(jnr.ffi.Pointer ptr, Number value) {
         ptr.putInt(this.offset(), value.intValue());
      }

      public final int intValue(jnr.ffi.Pointer ptr) {
         return ptr.getInt(this.offset());
      }
   }

   public class Enum16 extends EnumField {
      public Enum16(Class enumClass) {
         super(NativeType.SSHORT, enumClass);
      }

      public Enum16(Class enumClass, Offset offset) {
         super(NativeType.SSHORT, enumClass, offset);
      }

      public void set(jnr.ffi.Pointer ptr, java.lang.Enum value) {
         ptr.putShort(this.offset(), (short)this.enumMapper.intValue(value));
      }

      public void set(jnr.ffi.Pointer ptr, Number value) {
         ptr.putShort(this.offset(), value.shortValue());
      }

      public final int intValue(jnr.ffi.Pointer ptr) {
         return ptr.getShort(this.offset());
      }
   }

   public class Enum8 extends EnumField {
      public Enum8(Class enumClass) {
         super(NativeType.SCHAR, enumClass);
      }

      public Enum8(Class enumClass, Offset offset) {
         super(NativeType.SCHAR, enumClass, offset);
      }

      public final void set(jnr.ffi.Pointer ptr, java.lang.Enum value) {
         ptr.putByte(this.offset(), (byte)this.enumMapper.intValue(value));
      }

      public void set(jnr.ffi.Pointer ptr, Number value) {
         ptr.putByte(this.offset(), value.byteValue());
      }

      public final int intValue(jnr.ffi.Pointer ptr) {
         return ptr.getByte(this.offset());
      }
   }

   protected abstract class EnumField extends NumberField {
      protected final Class enumClass;
      protected final EnumMapper enumMapper;

      public EnumField(NativeType type, Class enumClass) {
         super((NativeType)type);
         this.enumClass = enumClass;
         this.enumMapper = EnumMapper.getInstance(enumClass);
      }

      public EnumField(NativeType type, Class enumClass, Offset offset) {
         super((NativeType)type, offset);
         this.enumClass = enumClass;
         this.enumMapper = EnumMapper.getInstance(enumClass);
      }

      public java.lang.Enum get(jnr.ffi.Pointer ptr) {
         return (java.lang.Enum)this.enumClass.cast(this.enumMapper.valueOf(this.intValue(ptr)));
      }

      public final java.lang.String toString(jnr.ffi.Pointer ptr) {
         return this.get(ptr).toString();
      }
   }

   public class Pointer extends NumberField {
      public Pointer() {
         super((NativeType)NativeType.ADDRESS);
      }

      public Pointer(Offset offset) {
         super((NativeType)NativeType.ADDRESS, offset);
      }

      public final jnr.ffi.Pointer get(jnr.ffi.Pointer ptr) {
         return ptr.getPointer(this.offset());
      }

      public final int size() {
         return StructLayout.this.getRuntime().findType(NativeType.ADDRESS).size();
      }

      public final void set(jnr.ffi.Pointer ptr, jnr.ffi.Pointer value) {
         ptr.putPointer(this.offset(), value);
      }

      public void set(jnr.ffi.Pointer ptr, Number value) {
         ptr.putAddress(this.offset(), value.longValue());
      }

      public final int intValue(jnr.ffi.Pointer ptr) {
         return (int)ptr.getAddress(this.offset());
      }

      public final long longValue(jnr.ffi.Pointer ptr) {
         return ptr.getAddress(this.offset());
      }

      public final java.lang.String toString(jnr.ffi.Pointer ptr) {
         return this.get(ptr).toString();
      }
   }

   public final class Double extends NumberField {
      public Double() {
         super((NativeType)NativeType.DOUBLE);
      }

      public Double(Offset offset) {
         super((NativeType)NativeType.DOUBLE, offset);
      }

      public final double get(jnr.ffi.Pointer ptr) {
         return ptr.getDouble(this.offset());
      }

      public final void set(jnr.ffi.Pointer ptr, double value) {
         ptr.putDouble(this.offset(), value);
      }

      public void set(jnr.ffi.Pointer ptr, Number value) {
         ptr.putDouble(this.offset(), value.doubleValue());
      }

      public final int intValue(jnr.ffi.Pointer ptr) {
         return (int)this.get(ptr);
      }

      public final long longValue(jnr.ffi.Pointer ptr) {
         return (long)this.get(ptr);
      }

      public final float floatValue(jnr.ffi.Pointer ptr) {
         return (float)this.get(ptr);
      }

      public final double doubleValue(jnr.ffi.Pointer ptr) {
         return this.get(ptr);
      }

      public final java.lang.String toString(jnr.ffi.Pointer ptr) {
         return java.lang.String.valueOf(this.get(ptr));
      }
   }

   public class Float extends NumberField {
      public Float() {
         super((NativeType)NativeType.FLOAT);
      }

      public Float(Offset offset) {
         super((NativeType)NativeType.FLOAT, offset);
      }

      public final float get(jnr.ffi.Pointer ptr) {
         return ptr.getFloat(this.offset());
      }

      public final void set(jnr.ffi.Pointer ptr, float value) {
         ptr.putFloat(this.offset(), value);
      }

      public void set(jnr.ffi.Pointer ptr, Number value) {
         ptr.putFloat(this.offset(), value.floatValue());
      }

      public final int intValue(jnr.ffi.Pointer ptr) {
         return (int)this.get(ptr);
      }

      public final double doubleValue(jnr.ffi.Pointer ptr) {
         return (double)this.get(ptr);
      }

      public final float floatValue(jnr.ffi.Pointer ptr) {
         return this.get(ptr);
      }

      public final long longValue(jnr.ffi.Pointer ptr) {
         return (long)this.get(ptr);
      }

      public final java.lang.String toString(jnr.ffi.Pointer ptr) {
         return java.lang.String.valueOf(this.get(ptr));
      }
   }

   public class UnsignedLong extends NumberField {
      public UnsignedLong() {
         super((NativeType)NativeType.ULONG);
      }

      public UnsignedLong(Offset offset) {
         super((NativeType)NativeType.ULONG, offset);
      }

      public final long get(jnr.ffi.Pointer ptr) {
         long value = ptr.getNativeLong(this.offset());
         long mask = StructLayout.this.getRuntime().findType(NativeType.SLONG).size() == 4 ? 4294967295L : -1L;
         return value < 0L ? (value & mask) + mask + 1L : value;
      }

      public final void set(jnr.ffi.Pointer ptr, long value) {
         ptr.putNativeLong(this.offset(), value);
      }

      public void set(jnr.ffi.Pointer ptr, Number value) {
         ptr.putNativeLong(this.offset(), value.longValue());
      }

      public final int intValue(jnr.ffi.Pointer ptr) {
         return (int)this.get(ptr);
      }

      public final long longValue(jnr.ffi.Pointer ptr) {
         return this.get(ptr);
      }

      public final java.lang.String toString(jnr.ffi.Pointer ptr) {
         return Long.toString(this.get(ptr));
      }
   }

   public class SignedLong extends NumberField {
      public SignedLong() {
         super((NativeType)NativeType.SLONG);
      }

      public SignedLong(Offset offset) {
         super((NativeType)NativeType.SLONG, offset);
      }

      public final long get(jnr.ffi.Pointer ptr) {
         return ptr.getNativeLong(this.offset());
      }

      public final void set(jnr.ffi.Pointer ptr, long value) {
         ptr.putNativeLong(this.offset(), value);
      }

      public void set(jnr.ffi.Pointer ptr, Number value) {
         ptr.putNativeLong(this.offset(), value.longValue());
      }

      public final int intValue(jnr.ffi.Pointer ptr) {
         return (int)this.get(ptr);
      }

      public final long longValue(jnr.ffi.Pointer ptr) {
         return this.get(ptr);
      }

      public final java.lang.String toString(jnr.ffi.Pointer ptr) {
         return Long.toString(this.get(ptr));
      }
   }

   public class Unsigned64 extends NumberField {
      public Unsigned64() {
         super((NativeType)NativeType.ULONGLONG);
      }

      public Unsigned64(Offset offset) {
         super((NativeType)NativeType.ULONGLONG, offset);
      }

      public final long get(jnr.ffi.Pointer ptr) {
         return ptr.getLongLong(this.offset());
      }

      public final void set(jnr.ffi.Pointer ptr, long value) {
         ptr.putLongLong(this.offset(), value);
      }

      public void set(jnr.ffi.Pointer ptr, Number value) {
         ptr.putLongLong(this.offset(), value.longValue());
      }

      public final int intValue(jnr.ffi.Pointer ptr) {
         return (int)this.get(ptr);
      }

      public final long longValue(jnr.ffi.Pointer ptr) {
         return this.get(ptr);
      }

      public final java.lang.String toString(jnr.ffi.Pointer ptr) {
         return Long.toString(this.get(ptr));
      }
   }

   public class Signed64 extends NumberField {
      public Signed64() {
         super((NativeType)NativeType.SLONGLONG);
      }

      public Signed64(Offset offset) {
         super((NativeType)NativeType.SLONGLONG, offset);
      }

      public final long get(jnr.ffi.Pointer ptr) {
         return ptr.getLongLong(this.offset());
      }

      public final void set(jnr.ffi.Pointer ptr, long value) {
         ptr.putLongLong(this.offset(), value);
      }

      public void set(jnr.ffi.Pointer ptr, Number value) {
         ptr.putLongLong(this.offset(), value.longValue());
      }

      public final int intValue(jnr.ffi.Pointer ptr) {
         return (int)this.get(ptr);
      }

      public final long longValue(jnr.ffi.Pointer ptr) {
         return this.get(ptr);
      }

      public final java.lang.String toString(jnr.ffi.Pointer ptr) {
         return Long.toString(this.get(ptr));
      }
   }

   public class Unsigned32 extends NumberField {
      public Unsigned32() {
         super((NativeType)NativeType.UINT);
      }

      public Unsigned32(Offset offset) {
         super((NativeType)NativeType.SINT, offset);
      }

      public final long get(jnr.ffi.Pointer ptr) {
         long value = (long)ptr.getInt(this.offset());
         return value < 0L ? (value & 2147483647L) + 2147483648L : value;
      }

      public final void set(jnr.ffi.Pointer ptr, long value) {
         ptr.putInt(this.offset(), (int)value);
      }

      public void set(jnr.ffi.Pointer ptr, Number value) {
         ptr.putInt(this.offset(), value.intValue());
      }

      public final int intValue(jnr.ffi.Pointer ptr) {
         return (int)this.get(ptr);
      }

      public final long longValue(jnr.ffi.Pointer ptr) {
         return this.get(ptr);
      }
   }

   public class Signed32 extends NumberField {
      public Signed32() {
         super((NativeType)NativeType.SINT);
      }

      public Signed32(Offset offset) {
         super((NativeType)NativeType.SINT, offset);
      }

      public final int get(jnr.ffi.Pointer ptr) {
         return ptr.getInt(this.offset());
      }

      public final void set(jnr.ffi.Pointer ptr, int value) {
         ptr.putInt(this.offset(), value);
      }

      public void set(jnr.ffi.Pointer ptr, Number value) {
         ptr.putInt(this.offset(), value.intValue());
      }

      public final int intValue(jnr.ffi.Pointer ptr) {
         return this.get(ptr);
      }
   }

   public class Unsigned16 extends NumberField {
      public Unsigned16() {
         super((NativeType)NativeType.USHORT);
      }

      public Unsigned16(Offset offset) {
         super((NativeType)NativeType.USHORT, offset);
      }

      public final int get(jnr.ffi.Pointer ptr) {
         int value = ptr.getShort(this.offset());
         return value < 0 ? (value & 32767) + '耀' : value;
      }

      public final void set(jnr.ffi.Pointer ptr, int value) {
         ptr.putShort(this.offset(), (short)value);
      }

      public void set(jnr.ffi.Pointer ptr, Number value) {
         ptr.putShort(this.offset(), value.shortValue());
      }

      public final int intValue(jnr.ffi.Pointer ptr) {
         return this.get(ptr);
      }
   }

   public class Signed16 extends NumberField {
      public Signed16() {
         super((NativeType)NativeType.SSHORT);
      }

      public Signed16(Offset offset) {
         super((NativeType)NativeType.SSHORT, offset);
      }

      public final short get(jnr.ffi.Pointer ptr) {
         return ptr.getShort(this.offset());
      }

      public final void set(jnr.ffi.Pointer ptr, short value) {
         ptr.putShort(this.offset(), value);
      }

      public void set(jnr.ffi.Pointer ptr, Number value) {
         ptr.putShort(this.offset(), value.shortValue());
      }

      public final short shortValue(jnr.ffi.Pointer ptr) {
         return this.get(ptr);
      }

      public final int intValue(jnr.ffi.Pointer ptr) {
         return this.get(ptr);
      }
   }

   public class Unsigned8 extends NumberField {
      public Unsigned8() {
         super((NativeType)NativeType.UCHAR);
      }

      public Unsigned8(Offset offset) {
         super((NativeType)NativeType.UCHAR, offset);
      }

      public final short get(jnr.ffi.Pointer ptr) {
         short value = (short)ptr.getByte(this.offset());
         return value < 0 ? (short)((value & 127) + 128) : value;
      }

      public final void set(jnr.ffi.Pointer ptr, short value) {
         ptr.putByte(this.offset(), (byte)value);
      }

      public void set(jnr.ffi.Pointer ptr, Number value) {
         ptr.putByte(this.offset(), value.byteValue());
      }

      public final short shortValue(jnr.ffi.Pointer ptr) {
         return this.get(ptr);
      }

      public final int intValue(jnr.ffi.Pointer ptr) {
         return this.get(ptr);
      }
   }

   public class Signed8 extends NumberField {
      public Signed8() {
         super((NativeType)NativeType.SCHAR);
      }

      public Signed8(Offset offset) {
         super((NativeType)NativeType.SCHAR, offset);
      }

      public final byte get(jnr.ffi.Pointer ptr) {
         return ptr.getByte(this.offset());
      }

      public final void set(jnr.ffi.Pointer ptr, byte value) {
         ptr.putByte(this.offset(), value);
      }

      public void set(jnr.ffi.Pointer ptr, Number value) {
         ptr.putByte(this.offset(), value.byteValue());
      }

      public final byte byteValue(jnr.ffi.Pointer ptr) {
         return this.get(ptr);
      }

      public final short shortValue(jnr.ffi.Pointer ptr) {
         return (short)this.get(ptr);
      }

      public final int intValue(jnr.ffi.Pointer ptr) {
         return this.get(ptr);
      }
   }

   public abstract class IntegerAlias extends NumberField {
      protected IntegerAlias(TypeAlias type) {
         super((Type)StructLayout.this.getRuntime().findType(type));
      }

      protected IntegerAlias(TypeAlias type, Offset offset) {
         super((Type)StructLayout.this.getRuntime().findType(type), offset);
      }

      public void set(jnr.ffi.Pointer ptr, Number value) {
         ptr.putInt(this.type, this.offset(), value.longValue());
      }

      public void set(jnr.ffi.Pointer ptr, long value) {
         ptr.putInt(this.type, this.offset(), value);
      }

      public final long get(jnr.ffi.Pointer ptr) {
         return ptr.getInt(this.type, this.offset());
      }

      public int intValue(jnr.ffi.Pointer ptr) {
         return (int)this.get(ptr);
      }

      public long longValue(jnr.ffi.Pointer ptr) {
         return this.get(ptr);
      }
   }

   protected abstract class NumberField extends Field {
      protected final Type type;

      protected NumberField(NativeType nativeType) {
         this((Type)StructLayout.this.getRuntime().findType(nativeType));
      }

      protected NumberField(Type type) {
         super(StructLayout.this.addField(type));
         this.type = type;
      }

      protected NumberField(NativeType nativeType, Offset offset) {
         this((Type)StructLayout.this.getRuntime().findType(nativeType), offset);
      }

      protected NumberField(Type type, Offset offset) {
         super(StructLayout.this.addField(type, offset));
         this.type = type;
      }

      public abstract void set(jnr.ffi.Pointer var1, Number var2);

      public double doubleValue(jnr.ffi.Pointer ptr) {
         return (double)this.longValue(ptr);
      }

      public float floatValue(jnr.ffi.Pointer ptr) {
         return (float)this.intValue(ptr);
      }

      public byte byteValue(jnr.ffi.Pointer ptr) {
         return (byte)this.intValue(ptr);
      }

      public short shortValue(jnr.ffi.Pointer ptr) {
         return (short)this.intValue(ptr);
      }

      public abstract int intValue(jnr.ffi.Pointer var1);

      public long longValue(jnr.ffi.Pointer ptr) {
         return (long)this.intValue(ptr);
      }

      public java.lang.String toString(jnr.ffi.Pointer ptr) {
         return Integer.toString(this.intValue(ptr), 10);
      }
   }

   protected final class WBOOL extends AbstractBoolean {
      protected WBOOL() {
         super(NativeType.SINT);
      }

      protected WBOOL(Offset offset) {
         super(NativeType.SINT, offset);
      }

      public final boolean get(jnr.ffi.Pointer ptr) {
         return (ptr.getInt(this.offset()) & 1) != 0;
      }

      public final void set(jnr.ffi.Pointer ptr, boolean value) {
         ptr.putInt(this.offset(), value ? 1 : 0);
      }
   }

   protected final class Boolean extends AbstractBoolean {
      protected Boolean() {
         super(NativeType.SCHAR);
      }

      protected Boolean(Offset offset) {
         super(NativeType.SCHAR, offset);
      }

      public final boolean get(jnr.ffi.Pointer ptr) {
         return (ptr.getByte(this.offset()) & 1) != 0;
      }

      public final void set(jnr.ffi.Pointer ptr, boolean value) {
         ptr.putByte(this.offset(), (byte)(value ? 1 : 0));
      }
   }

   protected abstract class AbstractBoolean extends AbstractField {
      protected AbstractBoolean(NativeType type) {
         super((NativeType)type);
      }

      protected AbstractBoolean(NativeType type, Offset offset) {
         super((NativeType)type, offset);
      }

      public abstract boolean get(jnr.ffi.Pointer var1);

      public abstract void set(jnr.ffi.Pointer var1, boolean var2);

      public java.lang.String toString(jnr.ffi.Pointer ptr) {
         return java.lang.Boolean.toString(this.get(ptr));
      }
   }

   protected abstract class AbstractField extends Field {
      protected AbstractField(int size, int align, Offset offset) {
         super(StructLayout.this.addField(size, align, offset));
      }

      protected AbstractField(int size, int align) {
         super(StructLayout.this.addField(size, align));
      }

      protected AbstractField(NativeType type) {
         super(StructLayout.this.addField(StructLayout.this.getRuntime().findType(type)));
      }

      protected AbstractField(Type type) {
         super(StructLayout.this.addField(type));
      }

      protected AbstractField(NativeType type, Offset offset) {
         super(StructLayout.this.addField(StructLayout.this.getRuntime().findType(type), offset));
      }

      protected AbstractField(Type type, Offset offset) {
         super(StructLayout.this.addField(type, offset));
      }
   }

   protected static final class Offset extends Number {
      private final int offset;

      public Offset(int offset) {
         this.offset = offset;
      }

      public int intValue() {
         return this.offset;
      }

      public long longValue() {
         return (long)this.offset;
      }

      public float floatValue() {
         return (float)this.offset;
      }

      public double doubleValue() {
         return (double)this.offset;
      }
   }

   protected abstract class Field {
      private final int offset;

      protected Field(int offset) {
         this.offset = offset;
      }

      public final StructLayout enclosing() {
         return StructLayout.this;
      }

      public final long offset() {
         return (long)(this.offset + StructLayout.this.offset);
      }
   }
}
