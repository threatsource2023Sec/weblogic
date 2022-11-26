package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.generic.Instruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionConstants;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionHandle;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionTargeter;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import java.util.Iterator;

abstract class Range implements InstructionTargeter {
   protected InstructionList body;
   protected InstructionHandle start;
   protected InstructionHandle end;
   static final Where InsideBefore = new Where("insideBefore");
   static final Where InsideAfter = new Where("insideAfter");
   static final Where OutsideBefore = new Where("outsideBefore");
   static final Where OutsideAfter = new Where("outsideAfter");
   public static final Instruction RANGEINSTRUCTION;

   protected Range(InstructionList il) {
      this.body = il;
   }

   final InstructionList getBody() {
      return this.body;
   }

   final InstructionHandle getStart() {
      return this.start;
   }

   final InstructionHandle getEnd() {
      return this.end;
   }

   boolean isEmpty() {
      for(InstructionHandle ih = this.start; ih != this.end; ih = ih.getNext()) {
         if (!isRangeHandle(ih)) {
            return false;
         }
      }

      return true;
   }

   static InstructionHandle getRealStart(InstructionHandle ih) {
      while(isRangeHandle(ih)) {
         ih = ih.getNext();
      }

      return ih;
   }

   InstructionHandle getRealStart() {
      return getRealStart(this.start);
   }

   static InstructionHandle getRealEnd(InstructionHandle ih) {
      while(isRangeHandle(ih)) {
         ih = ih.getPrev();
      }

      return ih;
   }

   InstructionHandle getRealEnd() {
      return getRealEnd(this.end);
   }

   InstructionHandle getRealNext() {
      return getRealStart(this.end);
   }

   InstructionHandle insert(Instruction i, Where where) {
      InstructionList il = new InstructionList();
      InstructionHandle ret = il.insert(i);
      this.insert(il, where);
      return ret;
   }

   void insert(InstructionList freshIl, Where where) {
      InstructionHandle h;
      if (where != InsideBefore && where != OutsideBefore) {
         h = this.getEnd();
      } else {
         h = this.getStart();
      }

      if (where != InsideBefore && where != OutsideAfter) {
         InstructionHandle newStart = this.body.insert(h, freshIl);
         if (where == OutsideBefore) {
            BcelShadow.retargetAllBranches(h, newStart);
         }
      } else {
         this.body.append(h, freshIl);
      }

   }

   InstructionHandle append(Instruction i) {
      return this.insert(i, InsideAfter);
   }

   void append(InstructionList i) {
      this.insert(i, InsideAfter);
   }

   private static void setLineNumberFromNext(InstructionHandle ih) {
      int lineNumber = Utility.getSourceLine(ih.getNext());
      if (lineNumber != -1) {
         Utility.setSourceLine(ih, lineNumber);
      }

   }

   static InstructionHandle genStart(InstructionList body) {
      InstructionHandle ih = body.insert(RANGEINSTRUCTION);
      setLineNumberFromNext(ih);
      return ih;
   }

   static InstructionHandle genEnd(InstructionList body) {
      return body.append(RANGEINSTRUCTION);
   }

   static InstructionHandle genStart(InstructionList body, InstructionHandle ih) {
      if (ih == null) {
         return genStart(body);
      } else {
         InstructionHandle freshIh = body.insert(ih, RANGEINSTRUCTION);
         setLineNumberFromNext(freshIh);
         return freshIh;
      }
   }

   static InstructionHandle genEnd(InstructionList body, InstructionHandle ih) {
      return ih == null ? genEnd(body) : body.append(ih, RANGEINSTRUCTION);
   }

   public boolean containsTarget(InstructionHandle ih) {
      return false;
   }

   public final void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih) {
      throw new RuntimeException("Ranges must be updated with an enclosing instructionList");
   }

   protected void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih, InstructionList new_il) {
      old_ih.removeTargeter(this);
      if (new_ih != null) {
         new_ih.addTargeter(this);
      }

      this.body = new_il;
      if (old_ih == this.start) {
         this.start = new_ih;
      }

      if (old_ih == this.end) {
         this.end = new_ih;
      }

   }

   public static final boolean isRangeHandle(InstructionHandle ih) {
      if (ih == null) {
         return false;
      } else {
         return ih.getInstruction() == RANGEINSTRUCTION;
      }
   }

   protected static final Range getRange(InstructionHandle ih) {
      Range ret = null;
      Iterator tIter = ih.getTargeters().iterator();

      while(true) {
         InstructionTargeter targeter;
         Range r;
         do {
            do {
               if (!tIter.hasNext()) {
                  if (ret == null) {
                     throw new BCException("shouldn't happen");
                  }

                  return ret;
               }

               targeter = (InstructionTargeter)tIter.next();
            } while(!(targeter instanceof Range));

            r = (Range)targeter;
         } while(r.getStart() != ih && r.getEnd() != ih);

         if (ret != null) {
            throw new BCException("multiple ranges on same range handle: " + ret + ",  " + targeter);
         }

         ret = r;
      }
   }

   static {
      RANGEINSTRUCTION = InstructionConstants.IMPDEP1;
   }

   static class Where {
      private String name;

      public Where(String name) {
         this.name = name;
      }

      public String toString() {
         return this.name;
      }
   }
}
