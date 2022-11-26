package jnr.ffi.provider;

import java.nio.ByteOrder;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;
import jnr.ffi.NativeType;
import jnr.ffi.Runtime;
import jnr.ffi.Type;

public abstract class AbstractRuntime extends Runtime {
   private final Type[] types;
   private final long addressMask;
   private final int addressSize;
   private final int longSize;
   private final ByteOrder byteOrder;

   public AbstractRuntime(ByteOrder byteOrder, EnumMap typeMap) {
      this.byteOrder = byteOrder;
      EnumSet nativeTypes = EnumSet.allOf(NativeType.class);
      this.types = new Type[nativeTypes.size()];

      NativeType t;
      for(Iterator var4 = nativeTypes.iterator(); var4.hasNext(); this.types[t.ordinal()] = (Type)(typeMap.containsKey(t) ? (Type)typeMap.get(t) : new BadType(t.toString()))) {
         t = (NativeType)var4.next();
      }

      this.addressSize = this.types[NativeType.ADDRESS.ordinal()].size();
      this.longSize = this.types[NativeType.SLONG.ordinal()].size();
      this.addressMask = this.addressSize == 4 ? 4294967295L : -1L;
   }

   public final Type findType(NativeType type) {
      return this.types[type.ordinal()];
   }

   public abstract MemoryManager getMemoryManager();

   public abstract int getLastError();

   public abstract void setLastError(int var1);

   public final long addressMask() {
      return this.addressMask;
   }

   public final int addressSize() {
      return this.addressSize;
   }

   public final int longSize() {
      return this.longSize;
   }

   public final ByteOrder byteOrder() {
      return this.byteOrder;
   }
}
