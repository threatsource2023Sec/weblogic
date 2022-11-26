package jnr.ffi;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import jnr.ffi.provider.ParameterFlags;
import jnr.ffi.provider.jffi.ArrayMemoryIO;
import jnr.ffi.util.EnumMapper;

public abstract class Struct {
   static final Charset ASCII = Charset.forName("ASCII");
   static final Charset UTF8 = Charset.forName("UTF-8");
   final Info __info;

   protected Struct(Runtime runtime) {
      this.__info = new Info(runtime);
   }

   protected Struct(Runtime runtime, Alignment alignment) {
      this(runtime);
      this.__info.alignment = alignment;
   }

   protected Struct(Runtime runtime, Struct enclosing) {
      this(runtime);
      this.__info.alignment = enclosing.__info.alignment;
   }

   Struct(Runtime runtime, boolean isUnion) {
      this(runtime);
      this.__info.resetIndex = isUnion;
      this.__info.isUnion = isUnion;
   }

   public final Runtime getRuntime() {
      return this.__info.runtime;
   }

   public final void useMemory(jnr.ffi.Pointer address) {
      this.__info.useMemory(address);
   }

   public static jnr.ffi.Pointer getMemory(Struct struct) {
      return struct.__info.getMemory(0);
   }

   public static jnr.ffi.Pointer getMemory(Struct struct, int flags) {
      return struct.__info.getMemory(flags);
   }

   public static int size(Struct struct) {
      return struct.__info.size();
   }

   public static int alignment(Struct struct) {
      return struct.__info.getMinimumAlignment();
   }

   public static boolean isDirect(Struct struct) {
      return struct.__info.isDirect();
   }

   private static int align(int offset, int align) {
      return offset + align - 1 & ~(align - 1);
   }

   public static Struct[] arrayOf(Runtime runtime, Class type, int length) {
      try {
         Struct[] array = (Struct[])((Struct[])Array.newInstance(type, length));
         Constructor c = type.getConstructor(Runtime.class);

         int structSize;
         for(structSize = 0; structSize < length; ++structSize) {
            array[structSize] = (Struct)c.newInstance(runtime);
         }

         if (array.length > 0) {
            structSize = align(size(array[0]), alignment(array[0]));
            jnr.ffi.Pointer memory = runtime.getMemoryManager().allocateDirect(structSize * length);

            for(int i = 0; i < array.length; ++i) {
               array[i].useMemory(memory.slice((long)(structSize * i), (long)structSize));
            }
         }

         return array;
      } catch (RuntimeException var8) {
         throw var8;
      } catch (Exception var9) {
         throw new RuntimeException(var9);
      }
   }

   public java.lang.String toString() {
      StringBuilder sb = new StringBuilder();
      Field[] fields = this.getClass().getDeclaredFields();
      sb.append(this.getClass().getSimpleName()).append(" { \n");
      java.lang.String fieldPrefix = "    ";
      Field[] var4 = fields;
      int var5 = fields.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Field field = var4[var6];

         try {
            sb.append("    ");
            sb.append(field.getName()).append(" = ");
            sb.append(field.get(this).toString());
            sb.append("\n");
         } catch (Throwable var9) {
            throw new RuntimeException(var9);
         }
      }

      sb.append("}\n");
      return sb.toString();
   }

   protected final void arrayBegin() {
      this.__info.resetIndex = false;
   }

   protected final void arrayEnd() {
      this.__info.resetIndex = this.__info.isUnion;
   }

   protected Member[] array(Member[] array) {
      this.arrayBegin();

      try {
         Class arrayClass = array.getClass().getComponentType();
         Constructor ctor = arrayClass.getDeclaredConstructor(arrayClass.getEnclosingClass());
         Object[] parameters = new Object[]{this};

         for(int i = 0; i < array.length; ++i) {
            array[i] = (Member)ctor.newInstance(parameters);
         }
      } catch (Exception var6) {
         throw new RuntimeException(var6);
      }

      this.arrayEnd();
      return array;
   }

   protected Enum8[] array(Enum8[] array, Class enumClass) {
      this.arrayBegin();

      for(int i = 0; i < array.length; ++i) {
         array[i] = new Enum8(enumClass);
      }

      this.arrayEnd();
      return array;
   }

   protected Enum16[] array(Enum16[] array, Class enumClass) {
      this.arrayBegin();

      for(int i = 0; i < array.length; ++i) {
         array[i] = new Enum16(enumClass);
      }

      this.arrayEnd();
      return array;
   }

   protected Enum32[] array(Enum32[] array, Class enumClass) {
      this.arrayBegin();

      for(int i = 0; i < array.length; ++i) {
         array[i] = new Enum32(enumClass);
      }

      this.arrayEnd();
      return array;
   }

   protected Enum64[] array(Enum64[] array, Class enumClass) {
      this.arrayBegin();

      for(int i = 0; i < array.length; ++i) {
         array[i] = new Enum64(enumClass);
      }

      this.arrayEnd();
      return array;
   }

   protected Enum[] array(Enum[] array, Class enumClass) {
      this.arrayBegin();

      for(int i = 0; i < array.length; ++i) {
         array[i] = new Enum(enumClass);
      }

      this.arrayEnd();
      return array;
   }

   protected Struct[] array(Struct[] array) {
      this.arrayBegin();

      try {
         Class type = array.getClass().getComponentType();
         Constructor c = type.getConstructor(Runtime.class);

         for(int i = 0; i < array.length; ++i) {
            array[i] = this.inner((Struct)c.newInstance(this.getRuntime()));
         }
      } catch (Exception var5) {
         throw new RuntimeException(var5);
      }

      this.arrayEnd();
      return array;
   }

   protected final Signed8[] array(Signed8[] array) {
      this.arrayBegin();

      for(int i = 0; i < array.length; ++i) {
         array[i] = new Signed8();
      }

      this.arrayEnd();
      return array;
   }

   protected final Unsigned8[] array(Unsigned8[] array) {
      this.arrayBegin();

      for(int i = 0; i < array.length; ++i) {
         array[i] = new Unsigned8();
      }

      this.arrayEnd();
      return array;
   }

   protected final Signed16[] array(Signed16[] array) {
      this.arrayBegin();

      for(int i = 0; i < array.length; ++i) {
         array[i] = new Signed16();
      }

      this.arrayEnd();
      return array;
   }

   protected final Unsigned16[] array(Unsigned16[] array) {
      this.arrayBegin();

      for(int i = 0; i < array.length; ++i) {
         array[i] = new Unsigned16();
      }

      this.arrayEnd();
      return array;
   }

   protected final Signed32[] array(Signed32[] array) {
      this.arrayBegin();

      for(int i = 0; i < array.length; ++i) {
         array[i] = new Signed32();
      }

      this.arrayEnd();
      return array;
   }

   protected final Unsigned32[] array(Unsigned32[] array) {
      this.arrayBegin();

      for(int i = 0; i < array.length; ++i) {
         array[i] = new Unsigned32();
      }

      this.arrayEnd();
      return array;
   }

   protected final Signed64[] array(Signed64[] array) {
      this.arrayBegin();

      for(int i = 0; i < array.length; ++i) {
         array[i] = new Signed64();
      }

      this.arrayEnd();
      return array;
   }

   protected final Unsigned64[] array(Unsigned64[] array) {
      this.arrayBegin();

      for(int i = 0; i < array.length; ++i) {
         array[i] = new Unsigned64();
      }

      this.arrayEnd();
      return array;
   }

   protected final SignedLong[] array(SignedLong[] array) {
      this.arrayBegin();

      for(int i = 0; i < array.length; ++i) {
         array[i] = new SignedLong();
      }

      this.arrayEnd();
      return array;
   }

   protected final UnsignedLong[] array(UnsignedLong[] array) {
      this.arrayBegin();

      for(int i = 0; i < array.length; ++i) {
         array[i] = new UnsignedLong();
      }

      this.arrayEnd();
      return array;
   }

   protected final Float[] array(Float[] array) {
      this.arrayBegin();

      for(int i = 0; i < array.length; ++i) {
         array[i] = new Float();
      }

      this.arrayEnd();
      return array;
   }

   protected final Double[] array(Double[] array) {
      this.arrayBegin();

      for(int i = 0; i < array.length; ++i) {
         array[i] = new Double();
      }

      this.arrayEnd();
      return array;
   }

   protected final Address[] array(Address[] array) {
      this.arrayBegin();

      for(int i = 0; i < array.length; ++i) {
         array[i] = new Address();
      }

      this.arrayEnd();
      return array;
   }

   protected final Pointer[] array(Pointer[] array) {
      this.arrayBegin();

      for(int i = 0; i < array.length; ++i) {
         array[i] = new Pointer();
      }

      this.arrayEnd();
      return array;
   }

   protected UTF8String[] array(UTF8String[] array, int stringLength) {
      this.arrayBegin();

      for(int i = 0; i < array.length; ++i) {
         array[i] = new UTF8String(stringLength);
      }

      this.arrayEnd();
      return array;
   }

   protected final Struct inner(Struct struct) {
      int alignment = this.__info.alignment.intValue() > 0 ? Math.min(this.__info.alignment.intValue(), struct.__info.getMinimumAlignment()) : struct.__info.getMinimumAlignment();
      int offset = this.__info.resetIndex ? 0 : align(this.__info.size, alignment);
      struct.__info.enclosing = this;
      struct.__info.offset = offset;
      this.__info.size = Math.max(this.__info.size, offset + struct.__info.size);
      return struct;
   }

   protected final Function function(Class closureClass) {
      return new Function(closureClass);
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

   public final class Function extends AbstractMember {
      private final Class closureClass;
      private Object instance;

      public Function(Class closureClass) {
         super(NativeType.ADDRESS);
         this.closureClass = closureClass;
      }

      public final void set(Object value) {
         this.getMemory().putPointer(this.offset(), Struct.this.getRuntime().getClosureManager().getClosurePointer(this.closureClass, this.instance = value));
      }
   }

   protected final class Padding extends AbstractMember {
      public Padding(Type type, int length) {
         super(type.size() * 8 * length, type.alignment() * 8);
      }

      public Padding(NativeType type, int length) {
         super(Struct.this.getRuntime().findType(type).size() * 8 * length, Struct.this.getRuntime().findType(type).alignment() * 8);
      }
   }

   public class AsciiStringRef extends UTFStringRef {
      public AsciiStringRef(int size) {
         super(size, Struct.ASCII);
      }

      public AsciiStringRef() {
         super(Integer.MAX_VALUE, Struct.ASCII);
      }
   }

   public class UTF8StringRef extends UTFStringRef {
      public UTF8StringRef(int size) {
         super(size, Struct.UTF8);
      }

      public UTF8StringRef() {
         super(Integer.MAX_VALUE, Struct.UTF8);
      }
   }

   public class UTFStringRef extends String {
      private jnr.ffi.Pointer valueHolder;

      public UTFStringRef(int length, Charset cs) {
         super(Struct.this.getRuntime().findType(NativeType.ADDRESS).size() * 8, Struct.this.getRuntime().findType(NativeType.ADDRESS).alignment() * 8, length, cs);
      }

      public UTFStringRef(Charset cs) {
         this(Integer.MAX_VALUE, cs);
      }

      protected jnr.ffi.Pointer getStringMemory() {
         return this.getMemory().getPointer(this.offset(), (long)this.length());
      }

      public final java.lang.String get() {
         jnr.ffi.Pointer ptr = this.getStringMemory();
         return ptr != null ? ptr.getString(0L, this.length, this.charset) : null;
      }

      public final void set(java.lang.String value) {
         if (value != null) {
            this.valueHolder = Struct.this.getRuntime().getMemoryManager().allocateDirect(this.length() * 4);
            this.valueHolder.putString(0L, value, this.length() * 4, this.charset);
            this.getMemory().putPointer(this.offset(), this.valueHolder);
         } else {
            this.valueHolder = null;
            this.getMemory().putAddress(this.offset(), 0L);
         }

      }
   }

   public class AsciiString extends UTFString {
      public AsciiString(int size) {
         super(size, Struct.ASCII);
      }
   }

   public class UTF8String extends UTFString {
      public UTF8String(int size) {
         super(size, Struct.UTF8);
      }
   }

   public class UTFString extends String {
      public UTFString(int length, Charset cs) {
         super(length * 8, 8, length, cs);
      }

      protected jnr.ffi.Pointer getStringMemory() {
         return this.getMemory().slice(this.offset(), (long)this.length());
      }

      public final java.lang.String get() {
         return this.getStringMemory().getString(0L, this.length, this.charset);
      }

      public final void set(java.lang.String value) {
         this.getStringMemory().putString(0L, value, this.length, this.charset);
      }
   }

   public abstract class String extends AbstractMember {
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

      protected abstract jnr.ffi.Pointer getStringMemory();

      public abstract java.lang.String get();

      public abstract void set(java.lang.String var1);

      public final java.lang.String toString() {
         return this.get();
      }
   }

   public class Enum extends Enum32 {
      public Enum(Class enumClass) {
         super(enumClass);
      }
   }

   public class EnumLong extends EnumField {
      public EnumLong(Class enumClass) {
         super(NativeType.SLONG, enumClass);
      }

      public final java.lang.Enum get() {
         return (java.lang.Enum)this.enumClass.cast(EnumMapper.getInstance(this.enumClass).valueOf(this.intValue()));
      }

      public final void set(java.lang.Enum value) {
         this.getMemory().putNativeLong(this.offset(), (long)EnumMapper.getInstance(this.enumClass).intValue(value));
      }

      public void set(Number value) {
         this.getMemory().putNativeLong(this.offset(), value.longValue());
      }

      public final int intValue() {
         return (int)this.longValue();
      }

      public final long longValue() {
         return this.getMemory().getNativeLong(this.offset());
      }
   }

   public class Enum64 extends EnumField {
      public Enum64(Class enumClass) {
         super(NativeType.SLONGLONG, enumClass);
      }

      public final java.lang.Enum get() {
         return (java.lang.Enum)this.enumClass.cast(EnumMapper.getInstance(this.enumClass).valueOf(this.intValue()));
      }

      public final void set(java.lang.Enum value) {
         this.getMemory().putLongLong(this.offset(), (long)EnumMapper.getInstance(this.enumClass).intValue(value));
      }

      public void set(Number value) {
         this.getMemory().putLongLong(this.offset(), value.longValue());
      }

      public final int intValue() {
         return (int)this.longValue();
      }

      public final long longValue() {
         return this.getMemory().getLongLong(this.offset());
      }
   }

   public class Enum32 extends EnumField {
      public Enum32(Class enumClass) {
         super(NativeType.SINT, enumClass);
      }

      public final java.lang.Enum get() {
         return (java.lang.Enum)this.enumClass.cast(EnumMapper.getInstance(this.enumClass).valueOf(this.intValue()));
      }

      public final void set(java.lang.Enum value) {
         this.getMemory().putInt(this.offset(), EnumMapper.getInstance(this.enumClass).intValue(value));
      }

      public void set(Number value) {
         this.getMemory().putInt(this.offset(), value.intValue());
      }

      public final int intValue() {
         return this.getMemory().getInt(this.offset());
      }
   }

   public class Enum16 extends EnumField {
      public Enum16(Class enumClass) {
         super(NativeType.SSHORT, enumClass);
      }

      public final java.lang.Enum get() {
         return (java.lang.Enum)this.enumClass.cast(EnumMapper.getInstance(this.enumClass).valueOf(this.intValue()));
      }

      public final void set(java.lang.Enum value) {
         this.getMemory().putShort(this.offset(), (short)EnumMapper.getInstance(this.enumClass).intValue(value));
      }

      public void set(Number value) {
         this.getMemory().putShort(this.offset(), value.shortValue());
      }

      public final int intValue() {
         return this.getMemory().getShort(this.offset());
      }
   }

   public class Enum8 extends EnumField {
      public Enum8(Class enumClass) {
         super(NativeType.SCHAR, enumClass);
      }

      public final java.lang.Enum get() {
         return (java.lang.Enum)this.enumClass.cast(EnumMapper.getInstance(this.enumClass).valueOf(this.intValue()));
      }

      public final void set(java.lang.Enum value) {
         this.getMemory().putByte(this.offset(), (byte)EnumMapper.getInstance(this.enumClass).intValue(value));
      }

      public void set(Number value) {
         this.getMemory().putByte(this.offset(), value.byteValue());
      }

      public final int intValue() {
         return this.getMemory().getByte(this.offset());
      }
   }

   protected abstract class EnumField extends NumberField {
      protected final Class enumClass;

      public EnumField(NativeType type, Class enumClass) {
         super((NativeType)type);
         this.enumClass = enumClass;
      }

      public abstract Object get();

      public final java.lang.String toString() {
         return this.get().toString();
      }
   }

   public class StructRef extends PointerField {
      private final Constructor structConstructor;
      private final Class structType;
      private final int size;

      public StructRef(Class structType) {
         super();
         this.structType = structType;

         try {
            this.structConstructor = structType.getDeclaredConstructor(Runtime.class);
            this.size = Struct.size((Struct)this.structConstructor.newInstance(Struct.this.getRuntime()));
         } catch (Exception var4) {
            throw new RuntimeException(var4);
         }
      }

      public StructRef(Class structType, int initialStructCount) {
         this(structType);
         this.set((jnr.ffi.Pointer)Memory.allocateDirect(Struct.this.getRuntime(), this.size * initialStructCount));
      }

      public StructRef(Offset offset, Class structType) {
         super(offset);
         this.structType = structType;

         try {
            this.structConstructor = structType.getDeclaredConstructor(Runtime.class);
            this.size = Struct.size((Struct)this.structConstructor.newInstance(Struct.this.getRuntime()));
         } catch (Exception var5) {
            throw new RuntimeException(var5);
         }
      }

      public StructRef(Offset offset, Class structType, int initialStructCount) {
         this(offset, structType);
         this.set((jnr.ffi.Pointer)Memory.allocateDirect(Struct.this.getRuntime(), this.size * initialStructCount));
      }

      public final void set(Struct struct) {
         jnr.ffi.Pointer structMemory = Struct.getMemory(struct);
         this.set((jnr.ffi.Pointer)structMemory);
      }

      public final void set(Struct[] structs) {
         if (structs.length == 0) {
            this.set((jnr.ffi.Pointer)Memory.allocateDirect(Struct.this.getRuntime(), 0));
         } else {
            jnr.ffi.Pointer value = Memory.allocateDirect(Struct.this.getRuntime(), this.size * structs.length);
            byte[] data = new byte[this.size];

            for(int i = 0; i < structs.length; ++i) {
               Struct.getMemory(structs[i]).get(0L, (byte[])data, 0, this.size);
               value.put((long)(this.size * i), (byte[])data, 0, this.size);
            }

            this.set((jnr.ffi.Pointer)value);
         }
      }

      public final Struct get() {
         Struct struct;
         try {
            struct = (Struct)this.structConstructor.newInstance(Struct.this.getRuntime());
         } catch (Exception var3) {
            throw new RuntimeException(var3);
         }

         struct.useMemory(this.getPointer());
         return struct;
      }

      public final Struct[] get(int length) {
         try {
            Struct[] array = (Struct[])((Struct[])Array.newInstance(this.structType, length));

            for(int i = 0; i < length; ++i) {
               array[i] = (Struct)this.structConstructor.newInstance(Struct.this.getRuntime());
               array[i].useMemory(this.getPointer().slice((long)(Struct.size(array[i]) * i)));
            }

            return array;
         } catch (Exception var4) {
            throw new RuntimeException(var4);
         }
      }

      public java.lang.String toString() {
         return "struct @ " + super.toString() + '\n' + this.get();
      }
   }

   public class Pointer extends PointerField {
      public Pointer() {
         super();
      }

      public Pointer(Offset offset) {
         super(offset);
      }

      public final jnr.ffi.Pointer get() {
         return this.getPointer();
      }

      public final int intValue() {
         return super.intValue();
      }

      public final long longValue() {
         return super.longValue();
      }

      public final java.lang.String toString() {
         return super.toString();
      }
   }

   public abstract class PointerField extends NumberField {
      public PointerField() {
         super((NativeType)NativeType.ADDRESS);
      }

      public PointerField(Offset offset) {
         super((NativeType)NativeType.ADDRESS, offset);
      }

      protected final jnr.ffi.Pointer getPointer() {
         return this.getMemory().getPointer(this.offset());
      }

      public final int size() {
         return Struct.this.getRuntime().findType(NativeType.ADDRESS).size() * 8;
      }

      public final void set(jnr.ffi.Pointer value) {
         jnr.ffi.Pointer finalPointer = value;
         if (value instanceof ArrayMemoryIO) {
            ArrayMemoryIO arrayMemory = (ArrayMemoryIO)value;
            byte[] valueArray = arrayMemory.array();
            finalPointer = Memory.allocateDirect(Struct.this.getRuntime(), valueArray.length);
            finalPointer.put(0L, (byte[])valueArray, 0, valueArray.length);
         }

         this.getMemory().putPointer(this.offset(), finalPointer);
      }

      public void set(Number value) {
         this.getMemory().putAddress(this.offset(), value.longValue());
      }

      public int intValue() {
         return (int)this.getMemory().getAddress(this.offset());
      }

      public long longValue() {
         return this.getMemory().getAddress(this.offset());
      }

      public java.lang.String toString() {
         return this.getPointer().toString();
      }
   }

   public class Address extends NumberField {
      public Address() {
         super((NativeType)NativeType.ADDRESS);
      }

      public Address(Offset offset) {
         super((NativeType)NativeType.ADDRESS, offset);
      }

      public final jnr.ffi.Address get() {
         return jnr.ffi.Address.valueOf(this.getMemory().getAddress(this.offset()));
      }

      public final void set(jnr.ffi.Address value) {
         this.getMemory().putAddress(this.offset(), value != null ? value.nativeAddress() : 0L);
      }

      public void set(Number value) {
         this.getMemory().putAddress(this.offset(), value.longValue());
      }

      public final int intValue() {
         return this.get().intValue();
      }

      public final long longValue() {
         return this.get().longValue();
      }

      public final java.lang.String toString() {
         return this.get().toString();
      }
   }

   public final class Double extends NumberField {
      public Double() {
         super((NativeType)NativeType.DOUBLE);
      }

      public Double(Offset offset) {
         super((NativeType)NativeType.DOUBLE, offset);
      }

      public final double get() {
         return this.getMemory().getDouble(this.offset());
      }

      public final void set(double value) {
         this.getMemory().putDouble(this.offset(), value);
      }

      public void set(Number value) {
         this.getMemory().putDouble(this.offset(), value.doubleValue());
      }

      public final int intValue() {
         return (int)this.get();
      }

      public final long longValue() {
         return (long)this.get();
      }

      public final float floatValue() {
         return (float)this.get();
      }

      public final double doubleValue() {
         return this.get();
      }

      public final java.lang.String toString() {
         return java.lang.String.valueOf(this.get());
      }
   }

   public class Float extends NumberField {
      public Float() {
         super((NativeType)NativeType.FLOAT);
      }

      public Float(Offset offset) {
         super((NativeType)NativeType.FLOAT, offset);
      }

      public final float get() {
         return this.getMemory().getFloat(this.offset());
      }

      public final void set(float value) {
         this.getMemory().putFloat(this.offset(), value);
      }

      public void set(Number value) {
         this.getMemory().putFloat(this.offset(), value.floatValue());
      }

      public final int intValue() {
         return (int)this.get();
      }

      public final double doubleValue() {
         return (double)this.get();
      }

      public final float floatValue() {
         return this.get();
      }

      public final long longValue() {
         return (long)this.get();
      }

      public final java.lang.String toString() {
         return java.lang.String.valueOf(this.get());
      }
   }

   public class UnsignedLong extends NumberField {
      public UnsignedLong() {
         super((NativeType)NativeType.ULONG);
      }

      public UnsignedLong(Offset offset) {
         super((NativeType)NativeType.ULONG, offset);
      }

      public final long get() {
         long value = this.getMemory().getNativeLong(this.offset());
         long mask = Struct.this.getRuntime().findType(NativeType.SLONG).size() == 32 ? 4294967295L : -1L;
         return value < 0L ? (value & mask) + mask + 1L : value;
      }

      public final void set(long value) {
         this.getMemory().putNativeLong(this.offset(), value);
      }

      public void set(Number value) {
         this.getMemory().putNativeLong(this.offset(), value.longValue());
      }

      public final int intValue() {
         return (int)this.get();
      }

      public final long longValue() {
         return this.get();
      }

      public final java.lang.String toString() {
         return Long.toString(this.get());
      }
   }

   public class SignedLong extends NumberField {
      public SignedLong() {
         super((NativeType)NativeType.SLONG);
      }

      public SignedLong(Offset offset) {
         super((NativeType)NativeType.SLONG, offset);
      }

      public final long get() {
         return this.getMemory().getNativeLong(this.offset());
      }

      public final void set(long value) {
         this.getMemory().putNativeLong(this.offset(), value);
      }

      public void set(Number value) {
         this.getMemory().putNativeLong(this.offset(), value.longValue());
      }

      public final int intValue() {
         return (int)this.get();
      }

      public final long longValue() {
         return this.get();
      }

      public final java.lang.String toString() {
         return Long.toString(this.get());
      }
   }

   public class Unsigned64 extends NumberField {
      public Unsigned64() {
         super((NativeType)NativeType.ULONGLONG);
      }

      public Unsigned64(Offset offset) {
         super((NativeType)NativeType.ULONGLONG, offset);
      }

      public final long get() {
         return this.getMemory().getLongLong(this.offset());
      }

      public final void set(long value) {
         this.getMemory().putLongLong(this.offset(), value);
      }

      public void set(Number value) {
         this.getMemory().putLongLong(this.offset(), value.longValue());
      }

      public final int intValue() {
         return (int)this.get();
      }

      public final long longValue() {
         return this.get();
      }

      public final java.lang.String toString() {
         return Long.toString(this.get());
      }
   }

   public class Signed64 extends NumberField {
      public Signed64() {
         super((NativeType)NativeType.SLONGLONG);
      }

      public Signed64(Offset offset) {
         super((NativeType)NativeType.SLONGLONG, offset);
      }

      public final long get() {
         return this.getMemory().getLongLong(this.offset());
      }

      public final void set(long value) {
         this.getMemory().putLongLong(this.offset(), value);
      }

      public void set(Number value) {
         this.getMemory().putLongLong(this.offset(), value.longValue());
      }

      public final int intValue() {
         return (int)this.get();
      }

      public final long longValue() {
         return this.get();
      }

      public final java.lang.String toString() {
         return Long.toString(this.get());
      }
   }

   public class Unsigned32 extends NumberField {
      public Unsigned32() {
         super((NativeType)NativeType.UINT);
      }

      public Unsigned32(Offset offset) {
         super((NativeType)NativeType.UINT, offset);
      }

      public final long get() {
         long value = (long)this.getMemory().getInt(this.offset());
         return value < 0L ? (value & 2147483647L) + 2147483648L : value;
      }

      public final void set(long value) {
         this.getMemory().putInt(this.offset(), (int)value);
      }

      public void set(Number value) {
         this.getMemory().putInt(this.offset(), value.intValue());
      }

      public final int intValue() {
         return (int)this.get();
      }

      public final long longValue() {
         return this.get();
      }

      public final java.lang.String toString() {
         return Long.toString(this.get());
      }
   }

   public class Signed32 extends NumberField {
      public Signed32() {
         super((NativeType)NativeType.SINT);
      }

      public Signed32(Offset offset) {
         super((NativeType)NativeType.SINT, offset);
      }

      public final int get() {
         return this.getMemory().getInt(this.offset());
      }

      public final void set(int value) {
         this.getMemory().putInt(this.offset(), value);
      }

      public void set(Number value) {
         this.getMemory().putInt(this.offset(), value.intValue());
      }

      public final int intValue() {
         return this.get();
      }
   }

   public class Unsigned16 extends NumberField {
      public Unsigned16() {
         super((NativeType)NativeType.USHORT);
      }

      public Unsigned16(Offset offset) {
         super((NativeType)NativeType.USHORT, offset);
      }

      public final int get() {
         int value = this.getMemory().getShort(this.offset());
         return value < 0 ? (value & 32767) + '耀' : value;
      }

      public final void set(int value) {
         this.getMemory().putShort(this.offset(), (short)value);
      }

      public void set(Number value) {
         this.getMemory().putShort(this.offset(), value.shortValue());
      }

      public final int intValue() {
         return this.get();
      }
   }

   public class Signed16 extends NumberField {
      public Signed16() {
         super((NativeType)NativeType.SSHORT);
      }

      public Signed16(Offset offset) {
         super((NativeType)NativeType.SSHORT, offset);
      }

      public final short get() {
         return this.getMemory().getShort(this.offset());
      }

      public final void set(short value) {
         this.getMemory().putShort(this.offset(), value);
      }

      public void set(Number value) {
         this.getMemory().putShort(this.offset(), value.shortValue());
      }

      public final short shortValue() {
         return this.get();
      }

      public final int intValue() {
         return this.get();
      }
   }

   public class Unsigned8 extends NumberField {
      public Unsigned8() {
         super((NativeType)NativeType.UCHAR);
      }

      public Unsigned8(Offset offset) {
         super((NativeType)NativeType.UCHAR, offset);
      }

      public final short get() {
         short value = (short)this.getMemory().getByte(this.offset());
         return value < 0 ? (short)((value & 127) + 128) : value;
      }

      public final void set(short value) {
         this.getMemory().putByte(this.offset(), (byte)value);
      }

      public void set(Number value) {
         this.getMemory().putByte(this.offset(), value.byteValue());
      }

      public final short shortValue() {
         return this.get();
      }

      public final int intValue() {
         return this.get();
      }
   }

   public class Signed8 extends NumberField {
      public Signed8() {
         super((NativeType)NativeType.SCHAR);
      }

      public Signed8(Offset offset) {
         super((NativeType)NativeType.SCHAR, offset);
      }

      public final byte get() {
         return this.getMemory().getByte(this.offset());
      }

      public final void set(byte value) {
         this.getMemory().putByte(this.offset(), value);
      }

      public void set(Number value) {
         this.getMemory().putByte(this.offset(), value.byteValue());
      }

      public final byte byteValue() {
         return this.get();
      }

      public final short shortValue() {
         return (short)this.get();
      }

      public final int intValue() {
         return this.get();
      }
   }

   public abstract class IntegerAlias extends NumberField {
      IntegerAlias(TypeAlias type) {
         super((TypeAlias)type);
      }

      IntegerAlias(TypeAlias type, Offset offset) {
         super((TypeAlias)type, offset);
      }

      public void set(Number value) {
         this.getMemory().putInt(this.type, this.offset(), value.longValue());
      }

      public void set(long value) {
         this.getMemory().putInt(this.type, this.offset(), value);
      }

      public final long get() {
         return this.getMemory().getInt(this.type, this.offset());
      }

      public int intValue() {
         return (int)this.get();
      }

      public long longValue() {
         return this.get();
      }

      public final java.lang.String toString() {
         return Long.toString(this.get());
      }
   }

   public abstract class NumberField extends Member {
      private final int offset;
      protected final Type type;

      protected NumberField(NativeType type) {
         super();
         Type t = this.type = Struct.this.getRuntime().findType(type);
         this.offset = Struct.this.__info.addField(t.size() * 8, t.alignment() * 8);
      }

      protected NumberField(NativeType type, Offset offset) {
         super();
         Type t = this.type = Struct.this.getRuntime().findType(type);
         this.offset = Struct.this.__info.addField(t.size() * 8, t.alignment() * 8, offset);
      }

      protected NumberField(TypeAlias type) {
         super();
         Type t = this.type = Struct.this.getRuntime().findType(type);
         this.offset = Struct.this.__info.addField(t.size() * 8, t.alignment() * 8);
      }

      protected NumberField(TypeAlias type, Offset offset) {
         super();
         Type t = this.type = Struct.this.getRuntime().findType(type);
         this.offset = Struct.this.__info.addField(t.size() * 8, t.alignment() * 8, offset);
      }

      public final jnr.ffi.Pointer getMemory() {
         return Struct.this.__info.getMemory();
      }

      public final Struct struct() {
         return Struct.this;
      }

      public final long offset() {
         return (long)(this.offset + Struct.this.__info.getOffset());
      }

      public abstract void set(Number var1);

      public double doubleValue() {
         return (double)this.longValue();
      }

      public float floatValue() {
         return (float)this.intValue();
      }

      public byte byteValue() {
         return (byte)this.intValue();
      }

      public short shortValue() {
         return (short)this.intValue();
      }

      public abstract int intValue();

      public long longValue() {
         return (long)this.intValue();
      }

      public java.lang.String toString() {
         return Integer.toString(this.intValue(), 10);
      }
   }

   public final class LONG extends Signed32 {
      public LONG() {
         super();
      }

      public LONG(Offset offset) {
         super(offset);
      }
   }

   public final class DWORD extends Unsigned32 {
      public DWORD() {
         super();
      }

      public DWORD(Offset offset) {
         super(offset);
      }
   }

   public final class WORD extends Unsigned16 {
      public WORD() {
         super();
      }

      public WORD(Offset offset) {
         super(offset);
      }
   }

   public final class BYTE extends Unsigned8 {
      public BYTE() {
         super();
      }

      public BYTE(Offset offset) {
         super(offset);
      }
   }

   public final class BOOL16 extends AbstractBoolean {
      public BOOL16() {
         super(NativeType.SSHORT);
      }

      public final boolean get() {
         return (this.getMemory().getShort(this.offset()) & 1) != 0;
      }

      public final void set(boolean value) {
         this.getMemory().putShort(this.offset(), (short)(value ? 1 : 0));
      }
   }

   public final class WBOOL extends AbstractBoolean {
      public WBOOL() {
         super(NativeType.SINT);
      }

      public final boolean get() {
         return (this.getMemory().getInt(this.offset()) & 1) != 0;
      }

      public final void set(boolean value) {
         this.getMemory().putInt(this.offset(), value ? 1 : 0);
      }
   }

   public final class Boolean extends AbstractBoolean {
      public Boolean() {
         super(NativeType.SCHAR);
      }

      public final boolean get() {
         return (this.getMemory().getByte(this.offset()) & 1) != 0;
      }

      public final void set(boolean value) {
         this.getMemory().putByte(this.offset(), (byte)(value ? 1 : 0));
      }
   }

   protected abstract class AbstractBoolean extends AbstractMember {
      protected AbstractBoolean(NativeType type) {
         super(type);
      }

      protected AbstractBoolean(NativeType type, Offset offset) {
         super(type, offset);
      }

      public abstract boolean get();

      public abstract void set(boolean var1);

      public java.lang.String toString() {
         return java.lang.Boolean.toString(this.get());
      }
   }

   protected abstract class AbstractMember extends Member {
      private final int offset;

      protected AbstractMember(int size) {
         this(size, size);
      }

      protected AbstractMember(int size, int align, Offset offset) {
         super();
         this.offset = Struct.this.__info.addField(size, align, offset);
      }

      protected AbstractMember(int size, int align) {
         super();
         this.offset = Struct.this.__info.addField(size, align);
      }

      protected AbstractMember(NativeType type) {
         super();
         Type t = Struct.this.getRuntime().findType(type);
         this.offset = Struct.this.__info.addField(t.size() * 8, t.alignment() * 8);
      }

      protected AbstractMember(NativeType type, Offset offset) {
         super();
         Type t = Struct.this.getRuntime().findType(type);
         this.offset = Struct.this.__info.addField(t.size() * 8, t.alignment() * 8, offset);
      }

      public final jnr.ffi.Pointer getMemory() {
         return Struct.this.__info.getMemory();
      }

      public final Struct struct() {
         return Struct.this;
      }

      public final long offset() {
         return (long)(this.offset + Struct.this.__info.getOffset());
      }
   }

   protected abstract class Member {
      abstract Struct struct();

      abstract jnr.ffi.Pointer getMemory();

      abstract long offset();
   }

   public static final class Alignment extends Number {
      private final int alignment;

      public Alignment(int alignment) {
         this.alignment = alignment;
      }

      public int intValue() {
         return this.alignment;
      }

      public long longValue() {
         return (long)this.alignment;
      }

      public float floatValue() {
         return (float)this.alignment;
      }

      public double doubleValue() {
         return (double)this.alignment;
      }
   }

   public static final class Offset extends Number {
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

   static final class Info {
      private final Runtime runtime;
      private jnr.ffi.Pointer memory = null;
      Struct enclosing = null;
      int offset = 0;
      int size = 0;
      int minAlign = 1;
      boolean isUnion = false;
      boolean resetIndex = false;
      Alignment alignment = new Alignment(0);

      public Info(Runtime runtime) {
         this.runtime = runtime;
      }

      public final int getOffset() {
         return this.enclosing == null ? 0 : this.offset + this.enclosing.__info.getOffset();
      }

      public final jnr.ffi.Pointer getMemory(int flags) {
         return this.enclosing != null ? this.enclosing.__info.getMemory(flags) : (this.memory != null ? this.memory : (this.memory = this.allocateMemory(flags)));
      }

      public final jnr.ffi.Pointer getMemory() {
         return this.getMemory(16);
      }

      final boolean isDirect() {
         return this.enclosing != null && this.enclosing.__info.isDirect() || this.memory != null && this.memory.isDirect();
      }

      final int size() {
         return this.alignment.intValue() > 0 ? this.size + (-this.size & this.minAlign - 1) : this.size;
      }

      final int getMinimumAlignment() {
         return this.minAlign;
      }

      private jnr.ffi.Pointer allocateMemory(int flags) {
         return ParameterFlags.isDirect(flags) ? this.runtime.getMemoryManager().allocateDirect(this.size(), true) : this.runtime.getMemoryManager().allocate(this.size());
      }

      public final void useMemory(jnr.ffi.Pointer io) {
         this.memory = io;
      }

      protected final int addField(int sizeBits, int alignBits, Offset offset) {
         this.size = Math.max(this.size, offset.intValue() + (sizeBits >> 3));
         this.minAlign = Math.max(this.minAlign, alignBits >> 3);
         return offset.intValue();
      }

      protected final int addField(int sizeBits, int alignBits) {
         int alignment = this.alignment.intValue() > 0 ? Math.min(this.alignment.intValue(), alignBits >> 3) : alignBits >> 3;
         int offset = this.resetIndex ? 0 : Struct.align(this.size, alignment);
         this.size = Math.max(this.size, offset + (sizeBits >> 3));
         this.minAlign = Math.max(this.minAlign, alignment);
         return offset;
      }
   }
}
