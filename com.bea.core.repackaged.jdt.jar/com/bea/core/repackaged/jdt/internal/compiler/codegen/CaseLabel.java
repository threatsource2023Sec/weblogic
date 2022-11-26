package com.bea.core.repackaged.jdt.internal.compiler.codegen;

public class CaseLabel extends BranchLabel {
   public int instructionPosition = -1;

   public CaseLabel(CodeStream codeStream) {
      super(codeStream);
   }

   void branch() {
      if (this.position == -1) {
         this.addForwardReference(this.codeStream.position);
         CodeStream var10000 = this.codeStream;
         var10000.position += 4;
         var10000 = this.codeStream;
         var10000.classFileOffset += 4;
      } else {
         this.codeStream.writeSignedWord(this.position - this.instructionPosition);
      }

   }

   void branchWide() {
      this.branch();
   }

   public boolean isCaseLabel() {
      return true;
   }

   public boolean isStandardLabel() {
      return false;
   }

   public void place() {
      if ((this.tagBits & 2) != 0) {
         this.position = this.codeStream.getPosition();
      } else {
         this.position = this.codeStream.position;
      }

      if (this.instructionPosition != -1) {
         int offset = this.position - this.instructionPosition;
         int[] forwardRefs = this.forwardReferences();
         int i = 0;

         for(int length = this.forwardReferenceCount(); i < length; ++i) {
            this.codeStream.writeSignedWord(forwardRefs[i], offset);
         }

         this.codeStream.addLabel(this);
      }

   }

   void placeInstruction() {
      if (this.instructionPosition == -1) {
         this.instructionPosition = this.codeStream.position;
      }

   }
}
