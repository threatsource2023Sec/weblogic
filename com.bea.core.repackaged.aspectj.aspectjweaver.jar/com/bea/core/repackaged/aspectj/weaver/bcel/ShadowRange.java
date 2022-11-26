package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.generic.Instruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionBranch;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionFactory;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionHandle;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionLV;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionSelect;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionTargeter;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.LocalVariableTag;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.RET;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.TargetLostException;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import java.util.Iterator;

final class ShadowRange extends Range {
   private BcelShadow shadow;

   public ShadowRange(InstructionList body) {
      super(body);
   }

   protected void associateWithTargets(InstructionHandle start, InstructionHandle end) {
      this.start = start;
      this.end = end;
      start.addTargeter(this);
      end.addTargeter(this);
   }

   public void associateWithShadow(BcelShadow shadow) {
      this.shadow = shadow;
      shadow.setRange(this);
   }

   public Shadow.Kind getKind() {
      return this.shadow.getKind();
   }

   public String toString() {
      return this.shadow.toString();
   }

   void extractInstructionsInto(LazyMethodGen freshMethod, IntMap remap, boolean addReturn) {
      LazyMethodGen.assertGoodBody(this.getBody(), this.toString());
      freshMethod.assertGoodBody();
      InstructionList freshBody = freshMethod.getBody();
      InstructionHandle ret = this.start.getNext();

      label146:
      while(ret != this.end) {
         Instruction oldI = ret.getInstruction();
         Instruction freshI = oldI == RANGEINSTRUCTION ? oldI : Utility.copyInstruction(oldI);
         Object freshIh;
         if (freshI instanceof InstructionBranch) {
            InstructionBranch oldBranch = (InstructionBranch)oldI;
            InstructionBranch freshBranch = (InstructionBranch)freshI;
            InstructionHandle oldTarget = oldBranch.getTarget();
            oldTarget.removeTargeter(oldBranch);
            oldTarget.addTargeter(freshBranch);
            if (freshBranch instanceof InstructionSelect) {
               InstructionSelect oldSelect = (InstructionSelect)oldI;
               InstructionSelect freshSelect = (InstructionSelect)freshI;
               InstructionHandle[] oldTargets = freshSelect.getTargets();

               for(int k = oldTargets.length - 1; k >= 0; --k) {
                  oldTargets[k].removeTargeter(oldSelect);
                  oldTargets[k].addTargeter(freshSelect);
               }
            }

            freshIh = freshBody.append(freshBranch);
         } else {
            freshIh = freshBody.append(freshI);
         }

         Iterator i$ = ret.getTargetersCopy().iterator();

         while(true) {
            while(true) {
               while(i$.hasNext()) {
                  InstructionTargeter source = (InstructionTargeter)i$.next();
                  if (source instanceof LocalVariableTag) {
                     Shadow.Kind kind = this.getKind();
                     if (kind != Shadow.AdviceExecution && kind != Shadow.ConstructorExecution && kind != Shadow.MethodExecution && kind != Shadow.PreInitialization && kind != Shadow.Initialization && kind != Shadow.StaticInitialization) {
                        source.updateTarget(ret, (InstructionHandle)null);
                     } else {
                        LocalVariableTag sourceLocalVariableTag = (LocalVariableTag)source;
                        if (sourceLocalVariableTag.getSlot() == 0 && sourceLocalVariableTag.getName().equals("this")) {
                           sourceLocalVariableTag.setName("ajc$this");
                        }

                        source.updateTarget(ret, (InstructionHandle)freshIh);
                     }
                  } else if (source instanceof Range) {
                     ((Range)source).updateTarget(ret, (InstructionHandle)freshIh, freshBody);
                  } else {
                     source.updateTarget(ret, (InstructionHandle)freshIh);
                  }
               }

               if (freshI.isLocalVariableInstruction() || freshI instanceof RET) {
                  int oldIndex = freshI.getIndex();
                  int freshIndex;
                  if (!remap.hasKey(oldIndex)) {
                     freshIndex = freshMethod.allocateLocal(2);
                     remap.put(oldIndex, freshIndex);
                  } else {
                     freshIndex = remap.get(oldIndex);
                  }

                  if (freshI instanceof RET) {
                     freshI.setIndex(freshIndex);
                  } else {
                     Instruction freshI = ((InstructionLV)freshI).setIndexAndCopyIfNecessary(freshIndex);
                     ((InstructionHandle)freshIh).setInstruction(freshI);
                  }
               }

               ret = ret.getNext();
               continue label146;
            }
         }
      }

      Iterator i$;
      InstructionTargeter t;
      for(ret = freshBody.getStart(); ret != freshBody.getEnd(); ret = ret.getNext()) {
         i$ = ret.getTargeters().iterator();

         while(i$.hasNext()) {
            t = (InstructionTargeter)i$.next();
            if (t instanceof LocalVariableTag) {
               LocalVariableTag lvt = (LocalVariableTag)t;
               if (!lvt.isRemapped() && remap.hasKey(lvt.getSlot())) {
                  lvt.updateSlot(remap.get(lvt.getSlot()));
               }
            }
         }
      }

      InstructionHandle next;
      try {
         for(ret = this.start.getNext(); ret != this.end; ret = next) {
            next = ret.getNext();
            this.body.delete(ret);
         }
      } catch (TargetLostException var16) {
         throw new BCException("shouldn't have gotten a target lost");
      }

      ret = null;
      if (addReturn) {
         ret = freshBody.append(InstructionFactory.createReturn(freshMethod.getReturnType()));
      }

      i$ = this.end.getTargetersCopy().iterator();

      while(i$.hasNext()) {
         t = (InstructionTargeter)i$.next();
         if (t != this) {
            if (!addReturn) {
               throw new BCException("range has target, but we aren't adding a return");
            }

            t.updateTarget(this.end, ret);
         }
      }

      LazyMethodGen.assertGoodBody(this.getBody(), this.toString());
      freshMethod.assertGoodBody();
   }

   public BcelShadow getShadow() {
      return this.shadow;
   }
}
