package com.bea.core.repackaged.jdt.internal.compiler.codegen;

import com.bea.core.repackaged.jdt.internal.compiler.lookup.LocalVariableBinding;
import java.util.Arrays;

public class BranchLabel extends Label {
   private int[] forwardReferences = new int[10];
   private int forwardReferenceCount = 0;
   BranchLabel delegate;
   public int tagBits;
   public static final int WIDE = 1;
   public static final int USED = 2;

   public BranchLabel() {
   }

   public BranchLabel(CodeStream codeStream) {
      super(codeStream);
   }

   void addForwardReference(int pos) {
      if (this.delegate != null) {
         this.delegate.addForwardReference(pos);
      } else {
         int count = this.forwardReferenceCount;
         int previousValue;
         if (count >= 1) {
            previousValue = this.forwardReferences[count - 1];
            if (previousValue < pos) {
               int length;
               if (count >= (length = this.forwardReferences.length)) {
                  System.arraycopy(this.forwardReferences, 0, this.forwardReferences = new int[2 * length], 0, length);
               }

               this.forwardReferences[this.forwardReferenceCount++] = pos;
            } else if (previousValue > pos) {
               int[] refs = this.forwardReferences;
               int length = 0;

               for(int max = this.forwardReferenceCount; length < max; ++length) {
                  if (refs[length] == pos) {
                     return;
                  }
               }

               if (count >= (length = refs.length)) {
                  System.arraycopy(refs, 0, this.forwardReferences = new int[2 * length], 0, length);
               }

               this.forwardReferences[this.forwardReferenceCount++] = pos;
               Arrays.sort(this.forwardReferences, 0, this.forwardReferenceCount);
            }
         } else {
            if (count >= (previousValue = this.forwardReferences.length)) {
               System.arraycopy(this.forwardReferences, 0, this.forwardReferences = new int[2 * previousValue], 0, previousValue);
            }

            this.forwardReferences[this.forwardReferenceCount++] = pos;
         }

      }
   }

   public void becomeDelegateFor(BranchLabel otherLabel) {
      otherLabel.delegate = this;
      int otherCount = otherLabel.forwardReferenceCount;
      if (otherCount != 0) {
         int[] mergedForwardReferences = new int[this.forwardReferenceCount + otherCount];
         int indexInMerge = 0;
         int j = 0;
         int i = 0;
         int max = this.forwardReferenceCount;

         int max2;
         label40:
         for(max2 = otherLabel.forwardReferenceCount; i < max; ++i) {
            int value1;
            for(value1 = this.forwardReferences[i]; j < max2; ++j) {
               int value2 = otherLabel.forwardReferences[j];
               if (value1 < value2) {
                  mergedForwardReferences[indexInMerge++] = value1;
                  continue label40;
               }

               if (value1 == value2) {
                  mergedForwardReferences[indexInMerge++] = value1;
                  ++j;
                  continue label40;
               }

               mergedForwardReferences[indexInMerge++] = value2;
            }

            mergedForwardReferences[indexInMerge++] = value1;
         }

         while(j < max2) {
            mergedForwardReferences[indexInMerge++] = otherLabel.forwardReferences[j];
            ++j;
         }

         this.forwardReferences = mergedForwardReferences;
         this.forwardReferenceCount = indexInMerge;
      }
   }

   void branch() {
      this.tagBits |= 2;
      if (this.delegate != null) {
         this.delegate.branch();
      } else {
         if (this.position == -1) {
            this.addForwardReference(this.codeStream.position);
            CodeStream var10000 = this.codeStream;
            var10000.position += 2;
            var10000 = this.codeStream;
            var10000.classFileOffset += 2;
         } else {
            this.codeStream.writePosition(this);
         }

      }
   }

   void branchWide() {
      this.tagBits |= 2;
      if (this.delegate != null) {
         this.delegate.branchWide();
      } else {
         if (this.position == -1) {
            this.addForwardReference(this.codeStream.position);
            this.tagBits |= 1;
            CodeStream var10000 = this.codeStream;
            var10000.position += 4;
            var10000 = this.codeStream;
            var10000.classFileOffset += 4;
         } else {
            this.codeStream.writeWidePosition(this);
         }

      }
   }

   public int forwardReferenceCount() {
      if (this.delegate != null) {
         this.delegate.forwardReferenceCount();
      }

      return this.forwardReferenceCount;
   }

   public int[] forwardReferences() {
      if (this.delegate != null) {
         this.delegate.forwardReferences();
      }

      return this.forwardReferences;
   }

   public void initialize(CodeStream stream) {
      this.codeStream = stream;
      this.position = -1;
      this.forwardReferenceCount = 0;
      this.delegate = null;
   }

   public boolean isCaseLabel() {
      return false;
   }

   public boolean isStandardLabel() {
      return true;
   }

   public void place() {
      if (this.position == -1) {
         this.position = this.codeStream.position;
         this.codeStream.addLabel(this);
         int oldPosition = this.position;
         boolean isOptimizedBranch = false;
         if (this.forwardReferenceCount != 0) {
            isOptimizedBranch = this.forwardReferences[this.forwardReferenceCount - 1] + 2 == this.position && this.codeStream.bCodeStream[this.codeStream.classFileOffset - 3] == -89;
            if (isOptimizedBranch) {
               if (this.codeStream.lastAbruptCompletion == this.position) {
                  this.codeStream.lastAbruptCompletion = -1;
               }

               this.codeStream.position = this.position -= 3;
               CodeStream var10000 = this.codeStream;
               var10000.classFileOffset -= 3;
               --this.forwardReferenceCount;
               if (this.codeStream.lastEntryPC == oldPosition) {
                  this.codeStream.lastEntryPC = this.position;
               }

               if ((this.codeStream.generateAttributes & 28) != 0) {
                  LocalVariableBinding[] locals = this.codeStream.locals;
                  int i = 0;

                  for(int max = locals.length; i < max; ++i) {
                     LocalVariableBinding local = locals[i];
                     if (local != null && local.initializationCount > 0) {
                        if (local.initializationPCs[(local.initializationCount - 1 << 1) + 1] == oldPosition) {
                           local.initializationPCs[(local.initializationCount - 1 << 1) + 1] = this.position;
                        }

                        if (local.initializationPCs[local.initializationCount - 1 << 1] == oldPosition) {
                           local.initializationPCs[local.initializationCount - 1 << 1] = this.position;
                        }
                     }
                  }
               }

               if ((this.codeStream.generateAttributes & 2) != 0) {
                  this.codeStream.removeUnusedPcToSourceMapEntries();
               }
            }
         }

         for(int i = 0; i < this.forwardReferenceCount; ++i) {
            this.codeStream.writePosition(this, this.forwardReferences[i]);
         }

         if (isOptimizedBranch) {
            this.codeStream.optimizeBranch(oldPosition, this);
         }
      }

   }

   public String toString() {
      String basic = this.getClass().getName();
      basic = basic.substring(basic.lastIndexOf(46) + 1);
      StringBuffer buffer = new StringBuffer(basic);
      buffer.append('@').append(Integer.toHexString(this.hashCode()));
      buffer.append("(position=").append(this.position);
      if (this.delegate != null) {
         buffer.append("delegate=").append(this.delegate);
      }

      buffer.append(", forwards = [");

      for(int i = 0; i < this.forwardReferenceCount - 1; ++i) {
         buffer.append(this.forwardReferences[i] + ", ");
      }

      if (this.forwardReferenceCount >= 1) {
         buffer.append(this.forwardReferences[this.forwardReferenceCount - 1]);
      }

      buffer.append("] )");
      return buffer.toString();
   }
}
