package com.bea.core.repackaged.jdt.internal.compiler.codegen;

import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import java.text.MessageFormat;

public class StackMapFrame {
   public static final int USED = 1;
   public static final int SAME_FRAME = 0;
   public static final int CHOP_FRAME = 1;
   public static final int APPEND_FRAME = 2;
   public static final int SAME_FRAME_EXTENDED = 3;
   public static final int FULL_FRAME = 4;
   public static final int SAME_LOCALS_1_STACK_ITEMS = 5;
   public static final int SAME_LOCALS_1_STACK_ITEMS_EXTENDED = 6;
   public int pc;
   public int numberOfStackItems;
   private int numberOfLocals;
   public int localIndex;
   public VerificationTypeInfo[] locals;
   public VerificationTypeInfo[] stackItems;
   private int numberOfDifferentLocals = -1;
   public int tagBits;

   public StackMapFrame(int initialLocalSize) {
      this.locals = new VerificationTypeInfo[initialLocalSize];
      this.numberOfLocals = -1;
      this.numberOfDifferentLocals = -1;
   }

   public int getFrameType(StackMapFrame prevFrame) {
      int offsetDelta = this.getOffsetDelta(prevFrame);
      switch (this.numberOfStackItems) {
         case 0:
            switch (this.numberOfDifferentLocals(prevFrame)) {
               case -3:
               case -2:
               case -1:
                  return 1;
               case 0:
                  return offsetDelta <= 63 ? 0 : 3;
               case 1:
               case 2:
               case 3:
                  return 2;
               default:
                  return 4;
            }
         case 1:
            switch (this.numberOfDifferentLocals(prevFrame)) {
               case 0:
                  return offsetDelta <= 63 ? 5 : 6;
            }
      }

      return 4;
   }

   public void addLocal(int resolvedPosition, VerificationTypeInfo info) {
      if (this.locals == null) {
         this.locals = new VerificationTypeInfo[resolvedPosition + 1];
         this.locals[resolvedPosition] = info;
      } else {
         int length = this.locals.length;
         if (resolvedPosition >= length) {
            System.arraycopy(this.locals, 0, this.locals = new VerificationTypeInfo[resolvedPosition + 1], 0, length);
         }

         this.locals[resolvedPosition] = info;
      }

   }

   public void addStackItem(VerificationTypeInfo info) {
      if (info == null) {
         throw new IllegalArgumentException("info cannot be null");
      } else {
         if (this.stackItems == null) {
            this.stackItems = new VerificationTypeInfo[1];
            this.stackItems[0] = info;
            this.numberOfStackItems = 1;
         } else {
            int length = this.stackItems.length;
            if (this.numberOfStackItems == length) {
               System.arraycopy(this.stackItems, 0, this.stackItems = new VerificationTypeInfo[length + 1], 0, length);
            }

            this.stackItems[this.numberOfStackItems++] = info;
         }

      }
   }

   public void addStackItem(TypeBinding binding) {
      if (this.stackItems == null) {
         this.stackItems = new VerificationTypeInfo[1];
         this.stackItems[0] = new VerificationTypeInfo(binding);
         this.numberOfStackItems = 1;
      } else {
         int length = this.stackItems.length;
         if (this.numberOfStackItems == length) {
            System.arraycopy(this.stackItems, 0, this.stackItems = new VerificationTypeInfo[length + 1], 0, length);
         }

         this.stackItems[this.numberOfStackItems++] = new VerificationTypeInfo(binding);
      }

   }

   public StackMapFrame duplicate() {
      int length = this.locals.length;
      StackMapFrame result = new StackMapFrame(length);
      result.numberOfLocals = -1;
      result.numberOfDifferentLocals = -1;
      result.pc = this.pc;
      result.numberOfStackItems = this.numberOfStackItems;
      int i;
      if (length != 0) {
         result.locals = new VerificationTypeInfo[length];

         for(i = 0; i < length; ++i) {
            VerificationTypeInfo verificationTypeInfo = this.locals[i];
            if (verificationTypeInfo != null) {
               result.locals[i] = verificationTypeInfo.duplicate();
            }
         }
      }

      length = this.numberOfStackItems;
      if (length != 0) {
         result.stackItems = new VerificationTypeInfo[length];

         for(i = 0; i < length; ++i) {
            result.stackItems[i] = this.stackItems[i].duplicate();
         }
      }

      return result;
   }

   public int numberOfDifferentLocals(StackMapFrame prevFrame) {
      if (this.numberOfDifferentLocals != -1) {
         return this.numberOfDifferentLocals;
      } else if (prevFrame == null) {
         this.numberOfDifferentLocals = 0;
         return 0;
      } else {
         VerificationTypeInfo[] prevLocals = prevFrame.locals;
         VerificationTypeInfo[] currentLocals = this.locals;
         int prevLocalsLength = prevLocals == null ? 0 : prevLocals.length;
         int currentLocalsLength = currentLocals == null ? 0 : currentLocals.length;
         int prevNumberOfLocals = prevFrame.getNumberOfLocals();
         int currentNumberOfLocals = this.getNumberOfLocals();
         int result = 0;
         int indexInPrevLocals;
         int indexInCurrentLocals;
         if (prevNumberOfLocals == 0) {
            if (currentNumberOfLocals != 0) {
               result = currentNumberOfLocals;
               indexInPrevLocals = 0;
               indexInCurrentLocals = 0;

               while(indexInCurrentLocals < currentLocalsLength && indexInPrevLocals < currentNumberOfLocals) {
                  if (currentLocals[indexInCurrentLocals] == null) {
                     result = Integer.MAX_VALUE;
                     this.numberOfDifferentLocals = result;
                     return result;
                  }

                  switch (currentLocals[indexInCurrentLocals].id()) {
                     case 7:
                     case 8:
                        ++indexInCurrentLocals;
                     default:
                        ++indexInPrevLocals;
                        ++indexInCurrentLocals;
                  }
               }
            }
         } else if (currentNumberOfLocals == 0) {
            indexInPrevLocals = 0;
            result = -prevNumberOfLocals;
            indexInCurrentLocals = 0;

            while(indexInCurrentLocals < prevLocalsLength && indexInPrevLocals < prevNumberOfLocals) {
               if (prevLocals[indexInCurrentLocals] == null) {
                  result = Integer.MAX_VALUE;
                  this.numberOfDifferentLocals = result;
                  return result;
               }

               switch (prevLocals[indexInCurrentLocals].id()) {
                  case 7:
                  case 8:
                     ++indexInCurrentLocals;
                  default:
                     ++indexInPrevLocals;
                     ++indexInCurrentLocals;
               }
            }
         } else {
            indexInPrevLocals = 0;
            indexInCurrentLocals = 0;
            int currentLocalsCounter = 0;
            int prevLocalsCounter = 0;

            label156:
            while(true) {
               VerificationTypeInfo prevLocal;
               if (indexInCurrentLocals < currentLocalsLength && currentLocalsCounter < currentNumberOfLocals) {
                  prevLocal = currentLocals[indexInCurrentLocals];
                  if (prevLocal != null) {
                     ++currentLocalsCounter;
                     switch (prevLocal.id()) {
                        case 7:
                        case 8:
                           ++indexInCurrentLocals;
                     }
                  }

                  if (indexInPrevLocals < prevLocalsLength && prevLocalsCounter < prevNumberOfLocals) {
                     VerificationTypeInfo prevLocal = prevLocals[indexInPrevLocals];
                     if (prevLocal != null) {
                        ++prevLocalsCounter;
                        switch (prevLocal.id()) {
                           case 7:
                           case 8:
                              ++indexInPrevLocals;
                        }
                     }

                     if (this.equals(prevLocal, prevLocal) && indexInPrevLocals == indexInCurrentLocals) {
                        if (result != 0) {
                           result = Integer.MAX_VALUE;
                           this.numberOfDifferentLocals = result;
                           return result;
                        }

                        ++indexInPrevLocals;
                        ++indexInCurrentLocals;
                        continue;
                     }

                     result = Integer.MAX_VALUE;
                     this.numberOfDifferentLocals = result;
                     return result;
                  }

                  if (prevLocal == null) {
                     result = Integer.MAX_VALUE;
                     this.numberOfDifferentLocals = result;
                     return result;
                  }

                  ++result;
                  ++indexInCurrentLocals;
               }

               if (currentLocalsCounter < currentNumberOfLocals) {
                  while(true) {
                     if (indexInCurrentLocals >= currentLocalsLength || currentLocalsCounter >= currentNumberOfLocals) {
                        break label156;
                     }

                     prevLocal = currentLocals[indexInCurrentLocals];
                     if (prevLocal == null) {
                        result = Integer.MAX_VALUE;
                        this.numberOfDifferentLocals = result;
                        return result;
                     }

                     ++result;
                     ++currentLocalsCounter;
                     switch (prevLocal.id()) {
                        case 7:
                        case 8:
                           ++indexInCurrentLocals;
                        default:
                           ++indexInCurrentLocals;
                     }
                  }
               }

               if (prevLocalsCounter < prevNumberOfLocals) {
                  result = -result;

                  while(indexInPrevLocals < prevLocalsLength && prevLocalsCounter < prevNumberOfLocals) {
                     prevLocal = prevLocals[indexInPrevLocals];
                     if (prevLocal == null) {
                        result = Integer.MAX_VALUE;
                        this.numberOfDifferentLocals = result;
                        return result;
                     }

                     --result;
                     ++prevLocalsCounter;
                     switch (prevLocal.id()) {
                        case 7:
                        case 8:
                           ++indexInPrevLocals;
                        default:
                           ++indexInPrevLocals;
                     }
                  }
               }
               break;
            }
         }

         this.numberOfDifferentLocals = result;
         return result;
      }
   }

   public int getNumberOfLocals() {
      if (this.numberOfLocals != -1) {
         return this.numberOfLocals;
      } else {
         int result = 0;
         int length = this.locals == null ? 0 : this.locals.length;

         for(int i = 0; i < length; ++i) {
            if (this.locals[i] != null) {
               switch (this.locals[i].id()) {
                  case 7:
                  case 8:
                     ++i;
                  default:
                     ++result;
               }
            }
         }

         this.numberOfLocals = result;
         return result;
      }
   }

   public int getOffsetDelta(StackMapFrame prevFrame) {
      if (prevFrame == null) {
         return this.pc;
      } else {
         return prevFrame.pc == -1 ? this.pc : this.pc - prevFrame.pc - 1;
      }
   }

   public String toString() {
      StringBuffer buffer = new StringBuffer();
      this.printFrame(buffer, this);
      return String.valueOf(buffer);
   }

   private void printFrame(StringBuffer buffer, StackMapFrame frame) {
      String pattern = "[pc : {0} locals: {1} stack items: {2}\nlocals: {3}\nstack: {4}\n]";
      int localsLength = frame.locals == null ? 0 : frame.locals.length;
      buffer.append(MessageFormat.format(pattern, Integer.toString(frame.pc), Integer.toString(frame.getNumberOfLocals()), Integer.toString(frame.numberOfStackItems), this.print(frame.locals, localsLength), this.print(frame.stackItems, frame.numberOfStackItems)));
   }

   private String print(VerificationTypeInfo[] infos, int length) {
      StringBuffer buffer = new StringBuffer();
      buffer.append('[');
      if (infos != null) {
         for(int i = 0; i < length; ++i) {
            if (i != 0) {
               buffer.append(',');
            }

            VerificationTypeInfo verificationTypeInfo = infos[i];
            if (verificationTypeInfo == null) {
               buffer.append("top");
            } else {
               buffer.append(verificationTypeInfo);
            }
         }
      }

      buffer.append(']');
      return String.valueOf(buffer);
   }

   public void putLocal(int resolvedPosition, VerificationTypeInfo info) {
      if (this.locals == null) {
         this.locals = new VerificationTypeInfo[resolvedPosition + 1];
         this.locals[resolvedPosition] = info;
      } else {
         int length = this.locals.length;
         if (resolvedPosition >= length) {
            System.arraycopy(this.locals, 0, this.locals = new VerificationTypeInfo[resolvedPosition + 1], 0, length);
         }

         this.locals[resolvedPosition] = info;
      }

   }

   public void replaceWithElementType() {
      VerificationTypeInfo info = this.stackItems[this.numberOfStackItems - 1];
      VerificationTypeInfo info2 = info.duplicate();
      info2.replaceWithElementType();
      this.stackItems[this.numberOfStackItems - 1] = info2;
   }

   public int getIndexOfDifferentLocals(int differentLocalsCount) {
      for(int i = this.locals.length - 1; i >= 0; --i) {
         VerificationTypeInfo currentLocal = this.locals[i];
         if (currentLocal != null) {
            --differentLocalsCount;
            if (differentLocalsCount == 0) {
               return i;
            }
         }
      }

      return 0;
   }

   private boolean equals(VerificationTypeInfo info, VerificationTypeInfo info2) {
      if (info == null) {
         return info2 == null;
      } else {
         return info2 == null ? false : info.equals(info2);
      }
   }
}
