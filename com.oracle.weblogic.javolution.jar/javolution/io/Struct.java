package javolution.io;

import com.rc.retroweaver.runtime.Enum_;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import javolution.JavolutionError;
import javolution.lang.Reflection;

public class Struct {
   private Struct _outer;
   private ByteBuffer _byteBuffer;
   private int _outerOffset;
   private int _bitsUsed;
   private int _alignment = 1;
   private int _bitIndex;
   private boolean _resetIndex = this.isUnion();
   private byte[] _bytes;
   private static final Reflection.Method ADDRESS_METHOD = Reflection.getMethod("sun.nio.ch.DirectBuffer.address()");
   private static final char[] HEXA = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
   private static final Class BOOL = (new Bool[0]).getClass();
   private static final Class SIGNED_8 = (new Signed8[0]).getClass();
   private static final Class UNSIGNED_8 = (new Unsigned8[0]).getClass();
   private static final Class SIGNED_16 = (new Signed16[0]).getClass();
   private static final Class UNSIGNED_16 = (new Unsigned16[0]).getClass();
   private static final Class SIGNED_32 = (new Signed32[0]).getClass();
   private static final Class UNSIGNED_32 = (new Unsigned32[0]).getClass();
   private static final Class SIGNED_64 = (new Signed64[0]).getClass();
   private static final Class FLOAT_32 = (new Float32[0]).getClass();
   private static final Class FLOAT_64 = (new Float64[0]).getClass();

   public final int size() {
      int var1 = this._bitsUsed + 7 >> 3;
      return var1 % this._alignment == 0 ? var1 : var1 + this._alignment - var1 % this._alignment;
   }

   public final ByteBuffer getByteBuffer() {
      if (this._outer != null) {
         return this._outer.getByteBuffer();
      } else {
         return this._byteBuffer != null ? this._byteBuffer : this.newBuffer();
      }
   }

   private synchronized ByteBuffer newBuffer() {
      if (this._byteBuffer != null) {
         return this._byteBuffer;
      } else {
         int var1 = this.size();
         int var2 = this.isPacked() ? ((var1 & 7) == 0 ? var1 : var1 + 8 - (var1 & 7)) : var1;
         ByteBuffer var3 = ByteBuffer.allocateDirect(var2);
         var3.order(this.byteOrder());
         this.setByteBuffer(var3, 0);
         return this._byteBuffer;
      }
   }

   public final Struct setByteBuffer(ByteBuffer var1, int var2) {
      if (var1.order() != this.byteOrder()) {
         throw new IllegalArgumentException("The byte order of the specified byte buffer is different from this struct byte order");
      } else if (this._outer != null) {
         throw new UnsupportedOperationException("Inner struct byte buffer is inherited from outer");
      } else {
         this._byteBuffer = var1;
         this._outerOffset = var2;
         return this;
      }
   }

   public final Struct setByteBufferPosition(int var1) {
      return this.setByteBuffer(this.getByteBuffer(), var1);
   }

   public final int getByteBufferPosition() {
      return this._outer != null ? this._outer.getByteBufferPosition() + this._outerOffset : this._outerOffset;
   }

   public int read(InputStream var1) throws IOException {
      ByteBuffer var2 = this.getByteBuffer();
      if (var2.hasArray()) {
         int var3 = var2.arrayOffset() + this.getByteBufferPosition();
         return var1.read(var2.array(), var3, this.size());
      } else {
         synchronized(var2) {
            if (this._bytes == null) {
               this._bytes = new byte[this.size()];
            }

            int var4 = var1.read(this._bytes);
            var2.position(this.getByteBufferPosition());
            var2.put(this._bytes);
            return var4;
         }
      }
   }

   public void write(OutputStream var1) throws IOException {
      ByteBuffer var2 = this.getByteBuffer();
      if (var2.hasArray()) {
         int var3 = var2.arrayOffset() + this.getByteBufferPosition();
         var1.write(var2.array(), var3, this.size());
      } else {
         synchronized(var2) {
            if (this._bytes == null) {
               this._bytes = new byte[this.size()];
            }

            var2.position(this.getByteBufferPosition());
            var2.get(this._bytes);
            var1.write(this._bytes);
         }
      }

   }

   public final long address() {
      ByteBuffer var1 = this.getByteBuffer();
      if (ADDRESS_METHOD != null) {
         Long var2 = (Long)ADDRESS_METHOD.invoke(var1);
         return var2 + (long)this.getByteBufferPosition();
      } else {
         throw new UnsupportedOperationException("Operation not supported for " + var1.getClass());
      }
   }

   public String toString() {
      int var1 = this.size();
      StringBuffer var2 = new StringBuffer(var1 * 3);
      ByteBuffer var3 = this.getByteBuffer();
      int var4 = this.getByteBufferPosition();

      for(int var5 = 0; var5 < var1; ++var5) {
         int var6 = var3.get(var4 + var5) & 255;
         var2.append(HEXA[var6 >> 4]);
         var2.append(HEXA[var6 & 15]);
         var2.append((char)((var5 & 15) == 15 ? '\n' : ' '));
      }

      return var2.toString();
   }

   public boolean isUnion() {
      return false;
   }

   public ByteOrder byteOrder() {
      return this._outer != null ? this._outer.byteOrder() : ByteOrder.BIG_ENDIAN;
   }

   public boolean isPacked() {
      return this._outer != null ? this._outer.isPacked() : false;
   }

   protected Struct inner(Struct var1) {
      if (var1._outer != null) {
         throw new IllegalArgumentException("struct: Already an inner struct");
      } else {
         var1._outer = this;
         int var2 = var1.size() << 3;
         this.updateIndexes(var1._alignment, var2, var2);
         var1._outerOffset = this._bitIndex - var2 >> 3;
         return var1;
      }
   }

   protected Struct[] array(Struct[] var1) {
      Class var2 = null;
      boolean var3 = this._resetIndex;
      if (this._resetIndex) {
         this._bitIndex = 0;
         this._resetIndex = false;
      }

      Struct var5;
      for(int var4 = 0; var4 < var1.length; var1[var4++] = this.inner(var5)) {
         var5 = var1[var4];
         if (var5 == null) {
            try {
               if (var2 == null) {
                  String var6 = var1.getClass().getName();
                  String var7 = var6.substring(2, var6.length() - 1);
                  var2 = Reflection.getClass(var7);
                  if (var2 == null) {
                     throw new JavolutionError("Struct class: " + var7 + " not found");
                  }
               }

               var5 = (Struct)var2.newInstance();
            } catch (Exception var8) {
               throw new JavolutionError(var8);
            }
         }
      }

      this._resetIndex = var3;
      return (Struct[])var1;
   }

   protected Struct[][] array(Struct[][] var1) {
      boolean var2 = this._resetIndex;
      if (this._resetIndex) {
         this._bitIndex = 0;
         this._resetIndex = false;
      }

      for(int var3 = 0; var3 < var1.length; ++var3) {
         this.array(var1[var3]);
      }

      this._resetIndex = var2;
      return (Struct[][])var1;
   }

   protected Struct[][][] array(Struct[][][] var1) {
      boolean var2 = this._resetIndex;
      if (this._resetIndex) {
         this._bitIndex = 0;
         this._resetIndex = false;
      }

      for(int var3 = 0; var3 < var1.length; ++var3) {
         this.array(var1[var3]);
      }

      this._resetIndex = var2;
      return (Struct[][][])var1;
   }

   protected Member[] array(Member[] var1) {
      boolean var2 = this._resetIndex;
      if (this._resetIndex) {
         this._bitIndex = 0;
         this._resetIndex = false;
      }

      int var3;
      if (BOOL.isInstance(var1)) {
         for(var3 = 0; var3 < var1.length; var1[var3++] = new Bool()) {
         }
      } else if (SIGNED_8.isInstance(var1)) {
         for(var3 = 0; var3 < var1.length; var1[var3++] = new Signed8()) {
         }
      } else if (UNSIGNED_8.isInstance(var1)) {
         for(var3 = 0; var3 < var1.length; var1[var3++] = new Unsigned8()) {
         }
      } else if (SIGNED_16.isInstance(var1)) {
         for(var3 = 0; var3 < var1.length; var1[var3++] = new Signed16()) {
         }
      } else if (UNSIGNED_16.isInstance(var1)) {
         for(var3 = 0; var3 < var1.length; var1[var3++] = new Unsigned16()) {
         }
      } else if (SIGNED_32.isInstance(var1)) {
         for(var3 = 0; var3 < var1.length; var1[var3++] = new Signed32()) {
         }
      } else if (UNSIGNED_32.isInstance(var1)) {
         for(var3 = 0; var3 < var1.length; var1[var3++] = new Unsigned32()) {
         }
      } else if (SIGNED_64.isInstance(var1)) {
         for(var3 = 0; var3 < var1.length; var1[var3++] = new Signed64()) {
         }
      } else if (FLOAT_32.isInstance(var1)) {
         for(var3 = 0; var3 < var1.length; var1[var3++] = new Float32()) {
         }
      } else {
         if (!FLOAT_64.isInstance(var1)) {
            throw new UnsupportedOperationException("Cannot create member elements, the arrayMember should contain the member instances instead of null");
         }

         for(var3 = 0; var3 < var1.length; var1[var3++] = new Float64()) {
         }
      }

      this._resetIndex = var2;
      return (Member[])var1;
   }

   protected Member[][] array(Member[][] var1) {
      boolean var2 = this._resetIndex;
      if (this._resetIndex) {
         this._bitIndex = 0;
         this._resetIndex = false;
      }

      for(int var3 = 0; var3 < var1.length; ++var3) {
         this.array(var1[var3]);
      }

      this._resetIndex = var2;
      return (Member[][])var1;
   }

   protected Member[][][] array(Member[][][] var1) {
      boolean var2 = this._resetIndex;
      if (this._resetIndex) {
         this._bitIndex = 0;
         this._resetIndex = false;
      }

      for(int var3 = 0; var3 < var1.length; ++var3) {
         this.array(var1[var3]);
      }

      this._resetIndex = var2;
      return (Member[][][])var1;
   }

   protected Utf8String[] array(Utf8String[] var1, int var2) {
      for(int var3 = 0; var3 < var1.length; ++var3) {
         var1[var3] = new Utf8String(var2);
      }

      return var1;
   }

   private int updateIndexes(int var1, int var2, int var3) {
      if (var2 > var3) {
         throw new IllegalArgumentException("nbrOfBits: " + var2 + " exceeds capacity: " + var3);
      } else {
         if (this._resetIndex) {
            this._bitIndex = 0;
         }

         var1 = this.isPacked() ? 1 : var1;
         int var4 = this._bitIndex / (var1 << 3) * var1;
         int var5 = this._bitIndex - (var4 << 3);
         if (var3 >= var5 + var2 && (var2 != 0 || var5 == 0)) {
            this._bitIndex += var2;
         } else {
            var4 += var1;
            this._bitIndex = (var4 << 3) + var2;
         }

         if (this._bitsUsed < this._bitIndex) {
            this._bitsUsed = this._bitIndex;
         }

         if (this._alignment < var1) {
            this._alignment = var1;
         }

         return var4;
      }
   }

   static int access$000(Struct var0, int var1, int var2, int var3) {
      return var0.updateIndexes(var1, var2, var3);
   }

   static int access$100(Struct var0) {
      return var0._bitIndex;
   }

   public class Enum64 extends Member {
      private final long _mask;
      private final int _shift;
      private final int _signShift;
      private final List _enumValues;

      public Enum64(List var2) {
         this(var2, 64);
      }

      public Enum64(List var2, int var3) {
         super(8, var3, 64);
         this._enumValues = var2;
         int var4 = this.offset() << 3;
         this._shift = Struct.this.byteOrder() == ByteOrder.BIG_ENDIAN ? 64 - Struct.access$100(Struct.this) + var4 : Struct.access$100(Struct.this) - var4 - var3;
         this._mask = var3 == 64 ? -1L : (1L << var3) - 1L << this._shift;
         this._signShift = 64 - this._shift - var3;
      }

      public Enum_ get() {
         if (this._mask == -1L) {
            return (Enum_)this._enumValues.get((int)Struct.this.getByteBuffer().getLong(this.position()));
         } else {
            long var1 = Struct.this.getByteBuffer().getLong(this.position());
            var1 &= this._mask;
            var1 <<= this._signShift;
            var1 >>= this._signShift + this._shift;
            return (Enum_)this._enumValues.get((int)var1);
         }
      }

      public void set(Enum_ var1) {
         int var2 = var1.ordinal();
         if (this._enumValues.get(var2) != var1) {
            throw new IllegalArgumentException("enum: " + var1 + ", ordinal value does not reflect enum values position");
         } else {
            long var3 = (long)var2;
            if (this._mask == -1L) {
               Struct.this.getByteBuffer().putLong(this.position(), var3);
            } else {
               var3 <<= this._shift;
               var3 &= this._mask;
               long var5 = Struct.this.getByteBuffer().getLong(this.position()) & ~this._mask;
               Struct.this.getByteBuffer().putLong(this.position(), var5 | var3);
            }

         }
      }
   }

   public class Enum32 extends Member {
      private final int _mask;
      private final int _shift;
      private final int _signShift;
      private final List _enumValues;

      public Enum32(List var2) {
         this(var2, 32);
      }

      public Enum32(List var2, int var3) {
         super(4, var3, 32);
         this._enumValues = var2;
         int var4 = this.offset() << 3;
         this._shift = Struct.this.byteOrder() == ByteOrder.BIG_ENDIAN ? 32 - Struct.access$100(Struct.this) + var4 : Struct.access$100(Struct.this) - var4 - var3;
         this._mask = var3 == 32 ? -1 : (1 << var3) - 1 << this._shift;
         this._signShift = 32 - this._shift - var3;
      }

      public Enum_ get() {
         if (this._mask == -1) {
            return (Enum_)this._enumValues.get(Struct.this.getByteBuffer().getInt(this.position()));
         } else {
            int var1 = Struct.this.getByteBuffer().getInt(this.position());
            var1 &= this._mask;
            var1 <<= this._signShift;
            var1 >>= this._signShift + this._shift;
            return (Enum_)this._enumValues.get(var1);
         }
      }

      public void set(Enum_ var1) {
         int var2 = var1.ordinal();
         if (this._enumValues.get(var2) != var1) {
            throw new IllegalArgumentException("enum: " + var1 + ", ordinal value does not reflect enum values position");
         } else {
            if (this._mask == -1) {
               Struct.this.getByteBuffer().putInt(this.position(), var2);
            } else {
               int var3 = var2 << this._shift;
               var3 &= this._mask;
               int var4 = Struct.this.getByteBuffer().getInt(this.position()) & ~this._mask;
               Struct.this.getByteBuffer().putInt(this.position(), var4 | var3);
            }

         }
      }
   }

   public class Enum16 extends Member {
      private final int _mask;
      private final int _shift;
      private final int _signShift;
      private final List _enumValues;

      public Enum16(List var2) {
         this(var2, 16);
      }

      public Enum16(List var2, int var3) {
         super(2, var3, 16);
         this._enumValues = var2;
         int var4 = this.offset() << 3;
         this._shift = Struct.this.byteOrder() == ByteOrder.BIG_ENDIAN ? 16 - Struct.access$100(Struct.this) + var4 : Struct.access$100(Struct.this) - var4 - var3;
         this._mask = (1 << var3) - 1 << this._shift;
         this._signShift = 32 - this._shift - var3;
      }

      public Enum_ get() {
         if (this._mask == 65535) {
            return (Enum_)this._enumValues.get(Struct.this.getByteBuffer().getShort(this.position()));
         } else {
            int var1 = Struct.this.getByteBuffer().getShort(this.position());
            var1 &= this._mask;
            var1 <<= this._signShift;
            var1 >>= this._signShift + this._shift;
            return (Enum_)this._enumValues.get(var1);
         }
      }

      public void set(Enum_ var1) {
         int var2 = var1.ordinal();
         if (this._enumValues.get(var2) != var1) {
            throw new IllegalArgumentException("enum: " + var1 + ", ordinal value does not reflect enum values position");
         } else {
            short var3 = (short)var2;
            if (this._mask == 65535) {
               Struct.this.getByteBuffer().putShort(this.position(), var3);
            } else {
               var3 = (short)(var3 << this._shift);
               var3 = (short)(var3 & this._mask);
               int var4 = Struct.this.getByteBuffer().getShort(this.position()) & ~this._mask;
               Struct.this.getByteBuffer().putShort(this.position(), (short)(var4 | var3));
            }

         }
      }
   }

   public class Enum8 extends Member {
      private final int _mask;
      private final int _shift;
      private final int _signShift;
      private final List _enumValues;

      public Enum8(List var2) {
         this(var2, 8);
      }

      public Enum8(List var2, int var3) {
         super(1, var3, 8);
         this._enumValues = var2;
         int var4 = this.offset() << 3;
         this._shift = Struct.this.byteOrder() == ByteOrder.BIG_ENDIAN ? 8 - Struct.access$100(Struct.this) + var4 : Struct.access$100(Struct.this) - var4 - var3;
         this._mask = (1 << var3) - 1 << this._shift;
         this._signShift = 32 - this._shift - var3;
      }

      public Enum_ get() {
         if (this._mask == 255) {
            return (Enum_)this._enumValues.get(Struct.this.getByteBuffer().get(this.position()));
         } else {
            int var1 = Struct.this.getByteBuffer().get(this.position());
            var1 &= this._mask;
            var1 <<= this._signShift;
            var1 >>= this._signShift + this._shift;
            return (Enum_)this._enumValues.get(var1);
         }
      }

      public void set(Enum_ var1) {
         int var2 = var1.ordinal();
         if (this._enumValues.get(var2) != var1) {
            throw new IllegalArgumentException("enum: " + var1 + ", ordinal value does not reflect enum values position");
         } else {
            byte var3 = (byte)var2;
            if (this._mask == 255) {
               Struct.this.getByteBuffer().put(this.position(), var3);
            } else {
               var3 = (byte)(var3 << this._shift);
               var3 = (byte)(var3 & this._mask);
               int var4 = Struct.this.getByteBuffer().get(this.position()) & ~this._mask;
               Struct.this.getByteBuffer().put(this.position(), (byte)(var4 | var3));
            }

         }
      }
   }

   public class Reference64 extends Member {
      private Struct _struct;

      public Reference64() {
         super(8, 8);
      }

      public void set(Struct var1) {
         if (var1 != null) {
            Struct.this.getByteBuffer().putLong(this.position(), var1.address());
         } else if (var1 == null) {
            Struct.this.getByteBuffer().putLong(this.position(), 0L);
         }

         this._struct = var1;
      }

      public Struct get() {
         return this._struct;
      }

      public long value() {
         return Struct.this.getByteBuffer().getLong(this.position());
      }

      public boolean isUpToDate() {
         if (this._struct != null) {
            return Struct.this.getByteBuffer().getLong(this.position()) == this._struct.address();
         } else {
            return Struct.this.getByteBuffer().getLong(this.position()) == 0L;
         }
      }
   }

   public class Reference32 extends Member {
      private Struct _struct;

      public Reference32() {
         super(4, 4);
      }

      public void set(Struct var1) {
         if (var1 != null) {
            Struct.this.getByteBuffer().putInt(this.position(), (int)var1.address());
         } else {
            Struct.this.getByteBuffer().putInt(this.position(), 0);
         }

         this._struct = var1;
      }

      public Struct get() {
         return this._struct;
      }

      public int value() {
         return Struct.this.getByteBuffer().getInt(this.position());
      }

      public boolean isUpToDate() {
         if (this._struct != null) {
            return Struct.this.getByteBuffer().getInt(this.position()) == (int)this._struct.address();
         } else {
            return Struct.this.getByteBuffer().getInt(this.position()) == 0;
         }
      }
   }

   public class Float64 extends Member {
      public Float64() {
         super(8, 8);
      }

      public void set(double var1) {
         Struct.this.getByteBuffer().putDouble(this.position(), var1);
      }

      public double get() {
         return Struct.this.getByteBuffer().getDouble(this.position());
      }
   }

   public class Float32 extends Member {
      public Float32() {
         super(4, 4);
      }

      public void set(float var1) {
         Struct.this.getByteBuffer().putFloat(this.position(), var1);
      }

      public float get() {
         return Struct.this.getByteBuffer().getFloat(this.position());
      }
   }

   public class Signed64 extends Member {
      private final long _mask;
      private final int _shift;
      private final int _signShift;

      public Signed64() {
         this(64);
      }

      public Signed64(int var2) {
         super(8, var2, 64);
         int var3 = this.offset() << 3;
         this._shift = Struct.this.byteOrder() == ByteOrder.BIG_ENDIAN ? 64 - Struct.access$100(Struct.this) + var3 : Struct.access$100(Struct.this) - var3 - var2;
         this._mask = var2 == 64 ? -1L : (1L << var2) - 1L << this._shift;
         this._signShift = 64 - this._shift - var2;
      }

      public long get() {
         if (this._mask == -1L) {
            return Struct.this.getByteBuffer().getLong(this.position());
         } else {
            long var1 = Struct.this.getByteBuffer().getLong(this.position());
            var1 &= this._mask;
            var1 <<= this._signShift;
            var1 >>= this._signShift + this._shift;
            return var1;
         }
      }

      public void set(long var1) {
         if (this._mask == -1L) {
            Struct.this.getByteBuffer().putLong(this.position(), var1);
         } else {
            var1 <<= this._shift;
            var1 &= this._mask;
            long var3 = Struct.this.getByteBuffer().getLong(this.position()) & ~this._mask;
            Struct.this.getByteBuffer().putLong(this.position(), var3 | var1);
         }

      }
   }

   public class Unsigned32 extends Member {
      private final int _shift;
      private final long _mask;

      public Unsigned32() {
         this(32);
      }

      public Unsigned32(int var2) {
         super(4, var2, 32);
         int var3 = this.offset() << 3;
         this._shift = Struct.this.byteOrder() == ByteOrder.BIG_ENDIAN ? 32 - Struct.access$100(Struct.this) + var3 : Struct.access$100(Struct.this) - var3 - var2;
         this._mask = var2 == 32 ? 4294967295L : (1L << var2) - 1L << this._shift;
      }

      public long get() {
         int var1 = Struct.this.getByteBuffer().getInt(this.position());
         return ((long)var1 & this._mask) >>> this._shift;
      }

      public void set(long var1) {
         if (this._mask == -1L) {
            Struct.this.getByteBuffer().putInt(this.position(), (int)var1);
         } else {
            var1 <<= this._shift;
            var1 &= this._mask;
            int var3 = Struct.this.getByteBuffer().getInt(this.position()) & ~((int)this._mask);
            Struct.this.getByteBuffer().putInt(this.position(), (int)((long)var3 | var1));
         }

      }
   }

   public class Signed32 extends Member {
      private final int _mask;
      private final int _shift;
      private final int _signShift;

      public Signed32() {
         this(32);
      }

      public Signed32(int var2) {
         super(4, var2, 32);
         int var3 = this.offset() << 3;
         this._shift = Struct.this.byteOrder() == ByteOrder.BIG_ENDIAN ? 32 - Struct.access$100(Struct.this) + var3 : Struct.access$100(Struct.this) - var3 - var2;
         this._mask = var2 == 32 ? -1 : (1 << var2) - 1 << this._shift;
         this._signShift = 32 - this._shift - var2;
      }

      public int get() {
         if (this._mask == -1) {
            return Struct.this.getByteBuffer().getInt(this.position());
         } else {
            int var1 = Struct.this.getByteBuffer().getInt(this.position());
            var1 &= this._mask;
            var1 <<= this._signShift;
            var1 >>= this._signShift + this._shift;
            return var1;
         }
      }

      public void set(int var1) {
         if (this._mask == -1) {
            Struct.this.getByteBuffer().putInt(this.position(), var1);
         } else {
            var1 <<= this._shift;
            var1 &= this._mask;
            int var2 = Struct.this.getByteBuffer().getInt(this.position()) & ~this._mask;
            Struct.this.getByteBuffer().putInt(this.position(), var2 | var1);
         }

      }
   }

   public class Unsigned16 extends Member {
      private final int _shift;
      private final int _mask;

      public Unsigned16() {
         this(16);
      }

      public Unsigned16(int var2) {
         super(2, var2, 16);
         int var3 = this.offset() << 3;
         this._shift = Struct.this.byteOrder() == ByteOrder.BIG_ENDIAN ? 16 - Struct.access$100(Struct.this) + var3 : Struct.access$100(Struct.this) - var3 - var2;
         this._mask = (1 << var2) - 1 << this._shift;
      }

      public int get() {
         short var1 = Struct.this.getByteBuffer().getShort(this.position());
         return (var1 & this._mask) >>> this._shift;
      }

      public void set(int var1) {
         if (this._mask == 65535) {
            Struct.this.getByteBuffer().putShort(this.position(), (short)var1);
         } else {
            var1 <<= this._shift;
            var1 &= this._mask;
            int var2 = Struct.this.getByteBuffer().getShort(this.position()) & ~this._mask;
            Struct.this.getByteBuffer().putShort(this.position(), (short)(var2 | var1));
         }

      }
   }

   public class Signed16 extends Member {
      private final int _mask;
      private final int _shift;
      private final int _signShift;

      public Signed16() {
         this(16);
      }

      public Signed16(int var2) {
         super(2, var2, 16);
         int var3 = this.offset() << 3;
         this._shift = Struct.this.byteOrder() == ByteOrder.BIG_ENDIAN ? 16 - Struct.access$100(Struct.this) + var3 : Struct.access$100(Struct.this) - var3 - var2;
         this._mask = (1 << var2) - 1 << this._shift;
         this._signShift = 32 - this._shift - var2;
      }

      public short get() {
         if (this._mask == 65535) {
            return Struct.this.getByteBuffer().getShort(this.position());
         } else {
            int var1 = Struct.this.getByteBuffer().getShort(this.position());
            var1 &= this._mask;
            var1 <<= this._signShift;
            var1 >>= this._signShift + this._shift;
            return (short)var1;
         }
      }

      public void set(short var1) {
         if (this._mask == 65535) {
            Struct.this.getByteBuffer().putShort(this.position(), var1);
         } else {
            var1 = (short)(var1 << this._shift);
            var1 = (short)(var1 & this._mask);
            int var2 = Struct.this.getByteBuffer().getShort(this.position()) & ~this._mask;
            Struct.this.getByteBuffer().putShort(this.position(), (short)(var2 | var1));
         }

      }
   }

   public class Unsigned8 extends Member {
      private final int _shift;
      private final int _mask;

      public Unsigned8() {
         this(8);
      }

      public Unsigned8(int var2) {
         super(1, var2, 8);
         int var3 = this.offset() << 3;
         this._shift = Struct.this.byteOrder() == ByteOrder.BIG_ENDIAN ? 8 - Struct.access$100(Struct.this) + var3 : Struct.access$100(Struct.this) - var3 - var2;
         this._mask = (1 << var2) - 1 << this._shift;
      }

      public short get() {
         byte var1 = Struct.this.getByteBuffer().get(this.position());
         return (short)((var1 & this._mask) >>> this._shift);
      }

      public void set(short var1) {
         if (this._mask == 255) {
            Struct.this.getByteBuffer().put(this.position(), (byte)var1);
         } else {
            var1 = (short)(var1 << this._shift);
            var1 = (short)(var1 & this._mask);
            int var2 = Struct.this.getByteBuffer().get(this.position()) & ~this._mask;
            Struct.this.getByteBuffer().put(this.position(), (byte)(var2 | var1));
         }

      }
   }

   public class Signed8 extends Member {
      private final int _mask;
      private final int _shift;
      private final int _signShift;

      public Signed8() {
         this(8);
      }

      public Signed8(int var2) {
         super(1, var2, 8);
         int var3 = this.offset() << 3;
         this._shift = Struct.this.byteOrder() == ByteOrder.BIG_ENDIAN ? 8 - Struct.access$100(Struct.this) + var3 : Struct.access$100(Struct.this) - var3 - var2;
         this._mask = (1 << var2) - 1 << this._shift;
         this._signShift = 32 - this._shift - var2;
      }

      public byte get() {
         if (this._mask == 255) {
            return Struct.this.getByteBuffer().get(this.position());
         } else {
            int var1 = Struct.this.getByteBuffer().get(this.position());
            var1 &= this._mask;
            var1 <<= this._signShift;
            var1 >>= this._signShift + this._shift;
            return (byte)var1;
         }
      }

      public void set(byte var1) {
         if (this._mask == 255) {
            Struct.this.getByteBuffer().put(this.position(), var1);
         } else {
            var1 = (byte)(var1 << this._shift);
            var1 = (byte)(var1 & this._mask);
            int var2 = Struct.this.getByteBuffer().get(this.position()) & ~this._mask;
            Struct.this.getByteBuffer().put(this.position(), (byte)(var2 | var1));
         }

      }
   }

   public class Bool extends Member {
      private final int _mask;
      private final int _shift;

      public Bool() {
         this(8);
      }

      public Bool(int var2) {
         super(1, var2, 8);
         int var3 = this.offset() << 3;
         this._shift = Struct.this.byteOrder() == ByteOrder.BIG_ENDIAN ? 8 - Struct.access$100(Struct.this) + var3 : Struct.access$100(Struct.this) - var3 - var2;
         this._mask = (1 << var2) - 1 << this._shift;
      }

      public boolean get() {
         return (Struct.this.getByteBuffer().get(this.position()) & this._mask) != 0;
      }

      public void set(boolean var1) {
         if (this._mask == 255) {
            Struct.this.getByteBuffer().put(this.position(), (byte)(var1 ? 1 : 0));
         } else {
            int var2 = Struct.this.getByteBuffer().get(this.position()) & ~this._mask;
            if (var1) {
               Struct.this.getByteBuffer().put(this.position(), (byte)(var2 | 1 << this._shift));
            } else {
               Struct.this.getByteBuffer().put(this.position(), (byte)var2);
            }
         }

      }
   }

   public class Utf8String extends Member {
      private final Utf8ByteBufferWriter _writer = new Utf8ByteBufferWriter();
      private final Utf8ByteBufferReader _reader = new Utf8ByteBufferReader();
      private final char[] _chars;
      private final int _length;

      public Utf8String(int var2) {
         super(1, var2);
         this._length = var2;
         this._chars = new char[var2];
      }

      public void set(String var1) {
         ByteBuffer var2 = Struct.this.getByteBuffer();
         synchronized(var2) {
            try {
               var2.position(this.position());
               this._writer.setByteBuffer(var2);
               if (var1.length() < this._length) {
                  this._writer.write(var1);
                  this._writer.write((int)0);
               } else if (var1.length() > this._length) {
                  this._writer.write(var1.substring(0, this._length));
               } else {
                  this._writer.write(var1);
               }
            } catch (IOException var10) {
               throw new JavolutionError(var10);
            } finally {
               this._writer.reset();
            }

         }
      }

      public String get() {
         ByteBuffer var1 = Struct.this.getByteBuffer();
         synchronized(var1) {
            try {
               var1.position(this.position());
               this._reader.setByteBuffer(var1);

               char var4;
               for(int var3 = 0; var3 < this._length; this._chars[var3++] = var4) {
                  var4 = (char)this._reader.read();
                  if (var4 == 0) {
                     String var5 = new String(this._chars, 0, var3);
                     return var5;
                  }
               }

               String var14 = new String(this._chars, 0, this._length);
               return var14;
            } catch (IOException var11) {
               throw new JavolutionError(var11);
            } finally {
               this._reader.reset();
            }
         }
      }
   }

   protected class Member {
      private final int _offset;

      protected Member(int var2, int var3) {
         int var4 = var3 << 3;
         this._offset = Struct.access$000(Struct.this, var2, var4, var4);
      }

      Member(int var2, int var3, int var4) {
         this._offset = Struct.access$000(Struct.this, var2, var3, var4);
      }

      public final Struct struct() {
         return Struct.this;
      }

      public final int offset() {
         return this._offset;
      }

      public final int position() {
         return Struct.this.getByteBufferPosition() + this._offset;
      }
   }
}
