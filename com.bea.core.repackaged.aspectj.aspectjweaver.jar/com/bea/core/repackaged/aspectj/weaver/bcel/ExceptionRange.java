package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionHandle;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;

public final class ExceptionRange extends Range {
   private InstructionHandle handler;
   private final UnresolvedType exceptionType;
   private final int priority;
   private volatile int hashCode;

   public ExceptionRange(InstructionList body, UnresolvedType exceptionType, int priority) {
      super(body);
      this.hashCode = 0;
      this.exceptionType = exceptionType;
      this.priority = priority;
   }

   public ExceptionRange(InstructionList body, UnresolvedType exceptionType, boolean insideExisting) {
      this(body, exceptionType, insideExisting ? Integer.MAX_VALUE : -1);
   }

   public void associateWithTargets(InstructionHandle start, InstructionHandle end, InstructionHandle handler) {
      this.start = start;
      this.end = end;
      this.handler = handler;
      start.addTargeter(this);
      end.addTargeter(this);
      handler.addTargeter(this);
   }

   public InstructionHandle getHandler() {
      return this.handler;
   }

   public UnresolvedType getCatchType() {
      return this.exceptionType;
   }

   public int getPriority() {
      return this.priority;
   }

   public String toString() {
      String str;
      if (this.exceptionType == null) {
         str = "finally";
      } else {
         str = "catch " + this.exceptionType;
      }

      return str;
   }

   public boolean equals(Object other) {
      if (!(other instanceof ExceptionRange)) {
         return false;
      } else {
         ExceptionRange o = (ExceptionRange)other;
         boolean var10000;
         if (o.getStart() == this.getStart() && o.getEnd() == this.getEnd() && o.handler == this.handler) {
            label24: {
               if (o.exceptionType == null) {
                  if (this.exceptionType != null) {
                     break label24;
                  }
               } else if (!o.exceptionType.equals(this.exceptionType)) {
                  break label24;
               }

               if (o.priority == this.priority) {
                  var10000 = true;
                  return var10000;
               }
            }
         }

         var10000 = false;
         return var10000;
      }
   }

   public int hashCode() {
      if (this.hashCode == 0) {
         int ret = 17;
         ret = 37 * ret + this.getStart().hashCode();
         ret = 37 * ret + this.getEnd().hashCode();
         ret = 37 * ret + this.handler.hashCode();
         ret = 37 * ret + (this.exceptionType == null ? 0 : this.exceptionType.hashCode());
         ret = 37 * ret + this.priority;
         this.hashCode = ret;
      }

      return this.hashCode;
   }

   public void updateTarget(InstructionHandle oldIh, InstructionHandle newIh, InstructionList newBody) {
      super.updateTarget(oldIh, newIh, newBody);
      if (oldIh == this.handler) {
         this.handler = newIh;
      }

   }

   public static boolean isExceptionStart(InstructionHandle ih) {
      if (!isRangeHandle(ih)) {
         return false;
      } else {
         Range r = getRange(ih);
         if (!(r instanceof ExceptionRange)) {
            return false;
         } else {
            ExceptionRange er = (ExceptionRange)r;
            return er.getStart() == ih;
         }
      }
   }

   public static boolean isExceptionEnd(InstructionHandle ih) {
      if (!isRangeHandle(ih)) {
         return false;
      } else {
         Range r = getRange(ih);
         if (!(r instanceof ExceptionRange)) {
            return false;
         } else {
            ExceptionRange er = (ExceptionRange)r;
            return er.getEnd() == ih;
         }
      }
   }
}
