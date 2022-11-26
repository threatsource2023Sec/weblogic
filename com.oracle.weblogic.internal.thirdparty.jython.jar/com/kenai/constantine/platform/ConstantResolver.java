package com.kenai.constantine.platform;

import com.kenai.constantine.Constant;
import com.kenai.constantine.ConstantSet;
import java.lang.reflect.Array;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/** @deprecated */
@Deprecated
class ConstantResolver {
   public static final String __UNKNOWN_CONSTANT__ = "__UNKNOWN_CONSTANT__";
   private final Object modLock;
   private final Class enumType;
   private final Map reverseLookupMap;
   private final AtomicInteger nextUnknown;
   private final int lastUnknown;
   private final boolean bitmask;
   private Constant[] cache;
   private volatile Enum[] valueCache;
   private volatile int cacheGuard;
   private volatile ConstantSet constants;

   private ConstantResolver(Class enumType) {
      this(enumType, Integer.MIN_VALUE, -2147482648, false);
   }

   private ConstantResolver(Class enumType, int firstUnknown, int lastUnknown, boolean bitmask) {
      this.modLock = new Object();
      this.reverseLookupMap = new ConcurrentHashMap();
      this.cache = null;
      this.valueCache = null;
      this.cacheGuard = 0;
      this.enumType = enumType;
      this.nextUnknown = new AtomicInteger(firstUnknown);
      this.lastUnknown = lastUnknown;
      this.bitmask = bitmask;
   }

   static final ConstantResolver getResolver(Class enumType) {
      return new ConstantResolver(enumType);
   }

   static final ConstantResolver getResolver(Class enumType, int first, int last) {
      return new ConstantResolver(enumType, first, last, false);
   }

   static final ConstantResolver getBitmaskResolver(Class enumType) {
      return new ConstantResolver(enumType, 0, Integer.MIN_VALUE, true);
   }

   private Constant getConstant(Enum e) {
      Constant c;
      return this.cacheGuard != 0 && (c = this.cache[e.ordinal()]) != null ? c : this.lookupAndCacheConstant(e);
   }

   private Constant lookupAndCacheConstant(Enum e) {
      synchronized(this.modLock) {
         Constant c;
         if (this.cacheGuard != 0 && (c = this.cache[e.ordinal()]) != null) {
            return c;
         } else {
            EnumSet enums = EnumSet.allOf(this.enumType);
            ConstantSet cset = this.getConstants();
            if (this.cache == null) {
               this.cache = new Constant[enums.size()];
            }

            long known = 0L;
            long unknown = 0L;

            Enum v;
            Object c;
            for(Iterator var10 = enums.iterator(); var10.hasNext(); this.cache[v.ordinal()] = (Constant)c) {
               v = (Enum)var10.next();
               c = cset.getConstant(v.name());
               if (c == null) {
                  if (this.bitmask) {
                     unknown |= 1L << v.ordinal();
                     c = new UnknownConstant(0, v.name());
                  } else {
                     c = new UnknownConstant(this.nextUnknown.getAndAdd(1), v.name());
                  }
               } else if (this.bitmask) {
                  known |= (long)((Constant)c).value();
               }
            }

            int index;
            if (this.bitmask) {
               for(long mask = 0L; (mask = Long.lowestOneBit(unknown)) != 0L; unknown &= ~(1L << index)) {
                  index = Long.numberOfTrailingZeros(mask);
                  int sparebit = Long.numberOfTrailingZeros(Long.lowestOneBit(~known));
                  int value = 1 << sparebit;
                  this.cache[index] = new UnknownConstant(value, this.cache[index].name());
                  known |= (long)value;
               }
            }

            this.cacheGuard = 1;
            return this.cache[e.ordinal()];
         }
      }
   }

   final int intValue(Enum e) {
      return this.getConstant(e).value();
   }

   final long longValue(Enum e) {
      return this.getConstant(e).longValue();
   }

   final String description(Enum e) {
      return this.getConstant(e).toString();
   }

   final Enum valueOf(int value) {
      Enum e;
      if (value >= 0 && value < 256 && this.valueCache != null && (e = this.valueCache[value]) != null) {
         return e;
      } else {
         e = (Enum)this.reverseLookupMap.get(value);
         if (e != null) {
            return e;
         } else {
            Constant c = this.getConstants().getConstant(value);
            if (c != null) {
               try {
                  e = Enum.valueOf(this.enumType, c.name());
                  this.reverseLookupMap.put(value, e);
                  if (c.value() >= 0 && c.value() < 256) {
                     Enum[] values = this.valueCache;
                     if (values == null) {
                        values = (Enum[])((Enum[])Array.newInstance(this.enumType, 256));
                     }

                     values[c.value()] = e;
                     this.valueCache = values;
                  }

                  return e;
               } catch (IllegalArgumentException var5) {
               }
            }

            System.out.println("failed to reverse lookup value " + value);
            return Enum.valueOf(this.enumType, "__UNKNOWN_CONSTANT__");
         }
      }
   }

   private final ConstantSet getConstants() {
      if (this.constants == null) {
         this.constants = ConstantSet.getConstantSet(this.enumType.getSimpleName());
         if (this.constants == null) {
            throw new RuntimeException("Could not load platform constants for " + this.enumType.getSimpleName());
         }
      }

      return this.constants;
   }

   private static final class UnknownConstant implements Constant {
      private final int value;
      private final String name;

      UnknownConstant(int value, String name) {
         this.value = value;
         this.name = name;
      }

      public int value() {
         return this.value;
      }

      public int intValue() {
         return this.value;
      }

      public long longValue() {
         return (long)this.value;
      }

      public String name() {
         return this.name;
      }

      public boolean defined() {
         return false;
      }

      public String toString() {
         return this.name;
      }
   }
}
