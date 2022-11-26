package com.bea.core.repackaged.jdt.internal.compiler.flow;

import com.bea.core.repackaged.jdt.internal.compiler.ast.IfStatement;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.FieldBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;

public abstract class FlowInfo {
   public int tagBits;
   public static final int REACHABLE = 0;
   public static final int UNREACHABLE_OR_DEAD = 1;
   public static final int UNREACHABLE_BY_NULLANALYSIS = 2;
   public static final int UNREACHABLE = 3;
   public static final int NULL_FLAG_MASK = 4;
   public static final int UNKNOWN = 1;
   public static final int NULL = 2;
   public static final int NON_NULL = 4;
   public static final int POTENTIALLY_UNKNOWN = 8;
   public static final int POTENTIALLY_NULL = 16;
   public static final int POTENTIALLY_NON_NULL = 32;
   public static final int UNROOTED = 64;
   public static final int FREE_TYPEVARIABLE = 48;
   public static final UnconditionalFlowInfo DEAD_END = new UnconditionalFlowInfo();

   static {
      DEAD_END.tagBits = 3;
   }

   public abstract FlowInfo addInitializationsFrom(FlowInfo var1);

   public abstract FlowInfo addNullInfoFrom(FlowInfo var1);

   public abstract FlowInfo addPotentialInitializationsFrom(FlowInfo var1);

   public FlowInfo asNegatedCondition() {
      return this;
   }

   public static FlowInfo conditional(FlowInfo initsWhenTrue, FlowInfo initsWhenFalse) {
      return (FlowInfo)(initsWhenTrue == initsWhenFalse ? initsWhenTrue : new ConditionalFlowInfo(initsWhenTrue, initsWhenFalse));
   }

   public boolean cannotBeDefinitelyNullOrNonNull(LocalVariableBinding local) {
      return this.isPotentiallyUnknown(local) || this.isPotentiallyNonNull(local) && this.isPotentiallyNull(local);
   }

   public boolean cannotBeNull(LocalVariableBinding local) {
      return this.isDefinitelyNonNull(local) || this.isProtectedNonNull(local);
   }

   public boolean canOnlyBeNull(LocalVariableBinding local) {
      return this.isDefinitelyNull(local) || this.isProtectedNull(local);
   }

   public abstract FlowInfo copy();

   public static UnconditionalFlowInfo initial(int maxFieldCount) {
      UnconditionalFlowInfo info = new UnconditionalFlowInfo();
      info.maxFieldCount = maxFieldCount;
      return info;
   }

   public abstract FlowInfo initsWhenFalse();

   public abstract FlowInfo initsWhenTrue();

   public abstract boolean isDefinitelyAssigned(FieldBinding var1);

   public abstract boolean isDefinitelyAssigned(LocalVariableBinding var1);

   public abstract boolean isDefinitelyNonNull(LocalVariableBinding var1);

   public abstract boolean isDefinitelyNull(LocalVariableBinding var1);

   public abstract boolean isDefinitelyUnknown(LocalVariableBinding var1);

   public abstract boolean hasNullInfoFor(LocalVariableBinding var1);

   public abstract boolean isPotentiallyAssigned(FieldBinding var1);

   public abstract boolean isPotentiallyAssigned(LocalVariableBinding var1);

   public abstract boolean isPotentiallyNonNull(LocalVariableBinding var1);

   public abstract boolean isPotentiallyNull(LocalVariableBinding var1);

   public abstract boolean isPotentiallyUnknown(LocalVariableBinding var1);

   public abstract boolean isProtectedNonNull(LocalVariableBinding var1);

   public abstract boolean isProtectedNull(LocalVariableBinding var1);

   public abstract void markAsComparedEqualToNonNull(LocalVariableBinding var1);

   public abstract void markAsComparedEqualToNull(LocalVariableBinding var1);

   public abstract void markAsDefinitelyAssigned(FieldBinding var1);

   public abstract void markAsDefinitelyNonNull(LocalVariableBinding var1);

   public abstract void markAsDefinitelyNull(LocalVariableBinding var1);

   public abstract void resetNullInfo(LocalVariableBinding var1);

   public abstract void markPotentiallyUnknownBit(LocalVariableBinding var1);

   public abstract void markPotentiallyNullBit(LocalVariableBinding var1);

   public abstract void markPotentiallyNonNullBit(LocalVariableBinding var1);

   public abstract void markAsDefinitelyAssigned(LocalVariableBinding var1);

   public abstract void markAsDefinitelyUnknown(LocalVariableBinding var1);

   public void markNullStatus(LocalVariableBinding local, int nullStatus) {
      switch (nullStatus) {
         case 1:
            this.markAsDefinitelyUnknown(local);
            break;
         case 2:
            this.markAsDefinitelyNull(local);
            break;
         case 3:
         default:
            this.resetNullInfo(local);
            if ((nullStatus & 8) != 0) {
               this.markPotentiallyUnknownBit(local);
            }

            if ((nullStatus & 16) != 0) {
               this.markPotentiallyNullBit(local);
            }

            if ((nullStatus & 32) != 0) {
               this.markPotentiallyNonNullBit(local);
            }

            if ((nullStatus & 56) == 0) {
               this.markAsDefinitelyUnknown(local);
            }
            break;
         case 4:
            this.markAsDefinitelyNonNull(local);
      }

   }

   public int nullStatus(LocalVariableBinding local) {
      if (this.isDefinitelyUnknown(local)) {
         return 1;
      } else if (this.isDefinitelyNull(local)) {
         return 2;
      } else if (this.isDefinitelyNonNull(local)) {
         return 4;
      } else {
         int status = 0;
         if (this.isPotentiallyUnknown(local)) {
            status |= 8;
         }

         if (this.isPotentiallyNull(local)) {
            status |= 16;
         }

         if (this.isPotentiallyNonNull(local)) {
            status |= 32;
         }

         return status > 0 ? status : 1;
      }
   }

   public static int mergeNullStatus(int nullStatus1, int nullStatus2) {
      boolean canBeNull = false;
      boolean canBeNonNull = false;
      switch (nullStatus1) {
         case 16:
            canBeNonNull = true;
         case 2:
            canBeNull = true;
            break;
         case 32:
            canBeNull = true;
         case 4:
            canBeNonNull = true;
      }

      switch (nullStatus2) {
         case 16:
            canBeNonNull = true;
         case 2:
            canBeNull = true;
            break;
         case 32:
            canBeNull = true;
         case 4:
            canBeNonNull = true;
      }

      if (canBeNull) {
         return canBeNonNull ? 16 : 2;
      } else {
         return canBeNonNull ? 4 : 1;
      }
   }

   public static UnconditionalFlowInfo mergedOptimizedBranches(FlowInfo initsWhenTrue, boolean isOptimizedTrue, FlowInfo initsWhenFalse, boolean isOptimizedFalse, boolean allowFakeDeadBranch) {
      UnconditionalFlowInfo mergedInfo;
      if (isOptimizedTrue) {
         if (initsWhenTrue == DEAD_END && allowFakeDeadBranch) {
            mergedInfo = initsWhenFalse.setReachMode(1).unconditionalInits();
         } else {
            mergedInfo = initsWhenTrue.addPotentialInitializationsFrom(initsWhenFalse.nullInfoLessUnconditionalCopy()).unconditionalInits();
         }
      } else if (isOptimizedFalse) {
         if (initsWhenFalse == DEAD_END && allowFakeDeadBranch) {
            mergedInfo = initsWhenTrue.setReachMode(1).unconditionalInits();
         } else {
            mergedInfo = initsWhenFalse.addPotentialInitializationsFrom(initsWhenTrue.nullInfoLessUnconditionalCopy()).unconditionalInits();
         }
      } else {
         mergedInfo = initsWhenTrue.mergedWith(initsWhenFalse.unconditionalInits());
      }

      return mergedInfo;
   }

   public static UnconditionalFlowInfo mergedOptimizedBranchesIfElse(FlowInfo initsWhenTrue, boolean isOptimizedTrue, FlowInfo initsWhenFalse, boolean isOptimizedFalse, boolean allowFakeDeadBranch, FlowInfo flowInfo, IfStatement ifStatement, boolean reportDeadCodeInKnownPattern) {
      UnconditionalFlowInfo mergedInfo;
      if (isOptimizedTrue) {
         if (initsWhenTrue == DEAD_END && allowFakeDeadBranch) {
            if (!reportDeadCodeInKnownPattern) {
               if (ifStatement.elseStatement == null) {
                  mergedInfo = flowInfo.unconditionalInits();
               } else {
                  mergedInfo = initsWhenFalse.unconditionalInits();
                  if (initsWhenFalse != DEAD_END) {
                     mergedInfo.setReachMode(flowInfo.reachMode());
                  }
               }
            } else {
               mergedInfo = initsWhenFalse.setReachMode(1).unconditionalInits();
            }
         } else {
            mergedInfo = initsWhenTrue.addPotentialInitializationsFrom(initsWhenFalse.nullInfoLessUnconditionalCopy()).unconditionalInits();
         }
      } else if (isOptimizedFalse) {
         if (initsWhenFalse == DEAD_END && allowFakeDeadBranch) {
            if (!reportDeadCodeInKnownPattern) {
               if (ifStatement.thenStatement == null) {
                  mergedInfo = flowInfo.unconditionalInits();
               } else {
                  mergedInfo = initsWhenTrue.unconditionalInits();
                  if (initsWhenTrue != DEAD_END) {
                     mergedInfo.setReachMode(flowInfo.reachMode());
                  }
               }
            } else {
               mergedInfo = initsWhenTrue.setReachMode(1).unconditionalInits();
            }
         } else {
            mergedInfo = initsWhenFalse.addPotentialInitializationsFrom(initsWhenTrue.nullInfoLessUnconditionalCopy()).unconditionalInits();
         }
      } else if ((flowInfo.tagBits & 3) == 0 && (ifStatement.bits & 128) != 0 && initsWhenTrue != DEAD_END && initsWhenFalse != DEAD_END) {
         mergedInfo = initsWhenTrue.addPotentialInitializationsFrom(initsWhenFalse.nullInfoLessUnconditionalCopy()).unconditionalInits();
         mergedInfo.mergeDefiniteInitsWith(initsWhenFalse.unconditionalCopy());
         if ((mergedInfo.tagBits & 1) != 0 && (initsWhenFalse.tagBits & 3) == 2) {
            mergedInfo.tagBits &= -2;
            mergedInfo.tagBits |= 2;
         }
      } else if ((flowInfo.tagBits & 3) == 0 && (ifStatement.bits & 256) != 0 && initsWhenTrue != DEAD_END && initsWhenFalse != DEAD_END) {
         mergedInfo = initsWhenFalse.addPotentialInitializationsFrom(initsWhenTrue.nullInfoLessUnconditionalCopy()).unconditionalInits();
         mergedInfo.mergeDefiniteInitsWith(initsWhenTrue.unconditionalCopy());
         if ((mergedInfo.tagBits & 1) != 0 && (initsWhenTrue.tagBits & 3) == 2) {
            mergedInfo.tagBits &= -2;
            mergedInfo.tagBits |= 2;
         }
      } else {
         mergedInfo = initsWhenTrue.mergedWith(initsWhenFalse.unconditionalInits());
      }

      return mergedInfo;
   }

   public int reachMode() {
      return this.tagBits & 3;
   }

   public abstract FlowInfo safeInitsWhenTrue();

   public abstract FlowInfo setReachMode(int var1);

   public abstract UnconditionalFlowInfo mergedWith(UnconditionalFlowInfo var1);

   public abstract UnconditionalFlowInfo mergeDefiniteInitsWith(UnconditionalFlowInfo var1);

   public abstract UnconditionalFlowInfo nullInfoLessUnconditionalCopy();

   public String toString() {
      return this == DEAD_END ? "FlowInfo.DEAD_END" : super.toString();
   }

   public abstract UnconditionalFlowInfo unconditionalCopy();

   public abstract UnconditionalFlowInfo unconditionalFieldLessCopy();

   public abstract UnconditionalFlowInfo unconditionalInits();

   public abstract UnconditionalFlowInfo unconditionalInitsWithoutSideEffect();

   public abstract void resetAssignmentInfo(LocalVariableBinding var1);

   public static int tagBitsToNullStatus(long tagBits) {
      if ((tagBits & 72057594037927936L) != 0L) {
         return 4;
      } else {
         return (tagBits & 36028797018963968L) != 0L ? 48 : 1;
      }
   }
}
