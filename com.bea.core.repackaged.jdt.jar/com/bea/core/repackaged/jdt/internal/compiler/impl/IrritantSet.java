package com.bea.core.repackaged.jdt.internal.compiler.impl;

public class IrritantSet {
   public static final int GROUP_MASK = -536870912;
   public static final int GROUP_SHIFT = 29;
   public static final int GROUP_MAX = 3;
   public static final int GROUP0 = 0;
   public static final int GROUP1 = 536870912;
   public static final int GROUP2 = 1073741824;
   public static final IrritantSet ALL = new IrritantSet(536870911);
   public static final IrritantSet BOXING = new IrritantSet(536871168);
   public static final IrritantSet CAST = new IrritantSet(67108864);
   public static final IrritantSet DEPRECATION = new IrritantSet(4);
   public static final IrritantSet TERMINAL_DEPRECATION = new IrritantSet(1082130432);
   public static final IrritantSet DEP_ANN = new IrritantSet(536879104);
   public static final IrritantSet FALLTHROUGH = new IrritantSet(537395200);
   public static final IrritantSet FINALLY = new IrritantSet(16777216);
   public static final IrritantSet HIDING = new IrritantSet(8);
   public static final IrritantSet INCOMPLETE_SWITCH = new IrritantSet(536875008);
   public static final IrritantSet NLS = new IrritantSet(256);
   public static final IrritantSet NULL = new IrritantSet(536871040);
   public static final IrritantSet RAW = new IrritantSet(536936448);
   public static final IrritantSet RESTRICTION = new IrritantSet(536870944);
   public static final IrritantSet SERIAL = new IrritantSet(536870920);
   public static final IrritantSet STATIC_ACCESS = new IrritantSet(268435456);
   public static final IrritantSet STATIC_METHOD = new IrritantSet(1073741840);
   public static final IrritantSet SYNTHETIC_ACCESS = new IrritantSet(128);
   public static final IrritantSet SYNCHRONIZED = new IrritantSet(805306368);
   public static final IrritantSet SUPER = new IrritantSet(537919488);
   public static final IrritantSet UNUSED = new IrritantSet(16);
   public static final IrritantSet UNCHECKED = new IrritantSet(536870914);
   public static final IrritantSet UNQUALIFIED_FIELD_ACCESS = new IrritantSet(4194304);
   public static final IrritantSet RESOURCE = new IrritantSet(1073741952);
   public static final IrritantSet UNLIKELY_ARGUMENT_TYPE = new IrritantSet(1075838976);
   public static final IrritantSet API_LEAK = new IrritantSet(1090519040);
   public static final IrritantSet MODULE = new IrritantSet(1107296256);
   public static final IrritantSet JAVADOC = new IrritantSet(33554432);
   public static final IrritantSet PREVIEW = new IrritantSet(1140850688);
   public static final IrritantSet COMPILER_DEFAULT_ERRORS = new IrritantSet(0);
   public static final IrritantSet COMPILER_DEFAULT_WARNINGS = new IrritantSet(0);
   public static final IrritantSet COMPILER_DEFAULT_INFOS = new IrritantSet(0);
   private int[] bits = new int[3];

   static {
      COMPILER_DEFAULT_INFOS.set(1077936128);
      COMPILER_DEFAULT_WARNINGS.set(16838239).set(721671934).set(1203384454);
      COMPILER_DEFAULT_ERRORS.set(1073744896);
      ALL.setAll();
      HIDING.set(131072).set(65536).set(536871936);
      NULL.set(538968064).set(541065216).set(1073742848).set(1073743872).set(1073745920).set(1073750016).set(1073872896).set(1073758208).set(1074266112).set(1074790400);
      RESTRICTION.set(536887296);
      STATIC_ACCESS.set(2048);
      UNUSED.set(32).set(1074003968).set(32768).set(8388608).set(537001984).set(1024).set(553648128).set(603979776).set(1073741826).set(1073741832).set(1073807360).set(1073741888);
      STATIC_METHOD.set(1073741856);
      RESOURCE.set(1073742080).set(1073742336);
      INCOMPLETE_SWITCH.set(1073774592);
      String suppressRawWhenUnchecked = System.getProperty("suppressRawWhenUnchecked");
      if (suppressRawWhenUnchecked != null && "true".equalsIgnoreCase(suppressRawWhenUnchecked)) {
         UNCHECKED.set(536936448);
      }

      JAVADOC.set(1048576).set(2097152);
      UNLIKELY_ARGUMENT_TYPE.set(1077936128);
   }

   public IrritantSet(int singleGroupIrritants) {
      this.initialize(singleGroupIrritants);
   }

   public IrritantSet(IrritantSet other) {
      this.initialize(other);
   }

   public boolean areAllSet() {
      for(int i = 0; i < 3; ++i) {
         if (this.bits[i] != 536870911) {
            return false;
         }
      }

      return true;
   }

   public IrritantSet clear(int singleGroupIrritants) {
      int group = (singleGroupIrritants & -536870912) >> 29;
      int[] var10000 = this.bits;
      var10000[group] &= ~singleGroupIrritants;
      return this;
   }

   public IrritantSet clearAll() {
      for(int i = 0; i < 3; ++i) {
         this.bits[i] = 0;
      }

      return this;
   }

   public void initialize(int singleGroupIrritants) {
      if (singleGroupIrritants != 0) {
         int group = (singleGroupIrritants & -536870912) >> 29;
         this.bits[group] = singleGroupIrritants & 536870911;
      }
   }

   public void initialize(IrritantSet other) {
      if (other != null) {
         System.arraycopy(other.bits, 0, this.bits = new int[3], 0, 3);
      }
   }

   public boolean isAnySet(IrritantSet other) {
      if (other == null) {
         return false;
      } else {
         for(int i = 0; i < 3; ++i) {
            if ((this.bits[i] & other.bits[i]) != 0) {
               return true;
            }
         }

         return false;
      }
   }

   public boolean hasSameIrritants(IrritantSet irritantSet) {
      if (irritantSet == null) {
         return false;
      } else {
         for(int i = 0; i < 3; ++i) {
            if (this.bits[i] != irritantSet.bits[i]) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean isSet(int singleGroupIrritants) {
      int group = (singleGroupIrritants & -536870912) >> 29;
      return (this.bits[group] & singleGroupIrritants) != 0;
   }

   public int[] getBits() {
      return this.bits;
   }

   public IrritantSet set(int singleGroupIrritants) {
      int group = (singleGroupIrritants & -536870912) >> 29;
      int[] var10000 = this.bits;
      var10000[group] |= singleGroupIrritants & 536870911;
      return this;
   }

   public IrritantSet set(IrritantSet other) {
      if (other == null) {
         return this;
      } else {
         boolean wasNoOp = true;

         for(int i = 0; i < 3; ++i) {
            int otherIrritant = other.bits[i] & 536870911;
            if ((this.bits[i] & otherIrritant) != otherIrritant) {
               wasNoOp = false;
               int[] var10000 = this.bits;
               var10000[i] |= otherIrritant;
            }
         }

         return wasNoOp ? null : this;
      }
   }

   public IrritantSet setAll() {
      for(int i = 0; i < 3; ++i) {
         int[] var10000 = this.bits;
         var10000[i] |= 536870911;
      }

      return this;
   }
}
