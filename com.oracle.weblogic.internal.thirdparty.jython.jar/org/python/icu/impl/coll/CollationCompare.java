package org.python.icu.impl.coll;

public final class CollationCompare {
   public static int compareUpToQuaternary(CollationIterator left, CollationIterator right, CollationSettings settings) {
      int options = settings.options;
      long variableTop;
      if ((options & 12) == 0) {
         variableTop = 0L;
      } else {
         variableTop = settings.variableTop + 1L;
      }

      boolean anyVariable = false;

      long p;
      long leftPrimary;
      long leftQuaternary;
      do {
         do {
            p = left.nextCE();
            leftPrimary = p >>> 32;
            if (leftPrimary < variableTop && leftPrimary > 33554432L) {
               anyVariable = true;

               do {
                  left.setCurrentCE(p & -4294967296L);

                  while(true) {
                     p = left.nextCE();
                     leftPrimary = p >>> 32;
                     if (leftPrimary != 0L) {
                        break;
                     }

                     left.setCurrentCE(0L);
                  }
               } while(leftPrimary < variableTop && leftPrimary > 33554432L);
            }
         } while(leftPrimary == 0L);

         do {
            leftQuaternary = right.nextCE();
            p = leftQuaternary >>> 32;
            if (p < variableTop && p > 33554432L) {
               anyVariable = true;

               do {
                  right.setCurrentCE(leftQuaternary & -4294967296L);

                  while(true) {
                     leftQuaternary = right.nextCE();
                     p = leftQuaternary >>> 32;
                     if (p != 0L) {
                        break;
                     }

                     right.setCurrentCE(0L);
                  }
               } while(p < variableTop && p > 33554432L);
            }
         } while(p == 0L);

         if (leftPrimary != p) {
            if (settings.hasReordering()) {
               leftPrimary = settings.reorder(leftPrimary);
               p = settings.reorder(p);
            }

            return leftPrimary < p ? -1 : 1;
         }
      } while(leftPrimary != 1L);

      int leftStart;
      int leftIndex;
      int rightIndex;
      int anyQuaternaries;
      int leftLower32;
      int leftTertiary;
      int rightLower32;
      int rightTertiary;
      if (CollationSettings.getStrength(options) >= 1) {
         if ((options & 2048) == 0) {
            leftStart = 0;
            leftIndex = 0;

            do {
               do {
                  rightIndex = (int)left.getCE(leftStart++) >>> 16;
               } while(rightIndex == 0);

               do {
                  anyQuaternaries = (int)right.getCE(leftIndex++) >>> 16;
               } while(anyQuaternaries == 0);

               if (rightIndex != anyQuaternaries) {
                  return rightIndex < anyQuaternaries ? -1 : 1;
               }
            } while(rightIndex != 256);
         } else {
            leftStart = 0;
            leftIndex = 0;

            while(true) {
               for(leftLower32 = leftStart; (p = left.getCE(leftLower32) >>> 32) > 33554432L || p == 0L; ++leftLower32) {
               }

               for(leftTertiary = leftIndex; (p = right.getCE(leftTertiary) >>> 32) > 33554432L || p == 0L; ++leftTertiary) {
               }

               rightLower32 = leftLower32;
               rightTertiary = leftTertiary;

               int leftSecondary;
               do {
                  for(leftSecondary = 0; leftSecondary == 0 && rightLower32 > leftStart; leftSecondary = (int)left.getCE(rightLower32) >>> 16) {
                     --rightLower32;
                  }

                  int rightSecondary;
                  for(rightSecondary = 0; rightSecondary == 0 && rightTertiary > leftIndex; rightSecondary = (int)right.getCE(rightTertiary) >>> 16) {
                     --rightTertiary;
                  }

                  if (leftSecondary != rightSecondary) {
                     return leftSecondary < rightSecondary ? -1 : 1;
                  }
               } while(leftSecondary != 0);

               assert left.getCE(leftLower32) == right.getCE(leftTertiary);

               if (p == 1L) {
                  break;
               }

               leftStart = leftLower32 + 1;
               leftIndex = leftTertiary + 1;
            }
         }
      }

      long rightQuaternary;
      if ((options & 1024) != 0) {
         leftStart = CollationSettings.getStrength(options);
         leftIndex = 0;
         rightIndex = 0;

         do {
            if (leftStart == 0) {
               while(true) {
                  do {
                     rightQuaternary = left.getCE(leftIndex++);
                     anyQuaternaries = (int)rightQuaternary;
                  } while(rightQuaternary >>> 32 == 0L);

                  if (anyQuaternaries != 0) {
                     leftLower32 = anyQuaternaries;
                     anyQuaternaries &= 49152;

                     do {
                        do {
                           rightQuaternary = right.getCE(rightIndex++);
                           leftTertiary = (int)rightQuaternary;
                        } while(rightQuaternary >>> 32 == 0L);
                     } while(leftTertiary == 0);

                     leftTertiary &= 49152;
                     break;
                  }
               }
            } else {
               do {
                  anyQuaternaries = (int)left.getCE(leftIndex++);
               } while((anyQuaternaries & -65536) == 0);

               leftLower32 = anyQuaternaries;
               anyQuaternaries &= 49152;

               do {
                  leftTertiary = (int)right.getCE(rightIndex++);
               } while((leftTertiary & -65536) == 0);

               leftTertiary &= 49152;
            }

            if (anyQuaternaries != leftTertiary) {
               if ((options & 256) == 0) {
                  return anyQuaternaries < leftTertiary ? -1 : 1;
               }

               return anyQuaternaries < leftTertiary ? 1 : -1;
            }
         } while(leftLower32 >>> 16 != 256);
      }

      if (CollationSettings.getStrength(options) <= 1) {
         return 0;
      } else {
         leftStart = CollationSettings.getTertiaryMask(options);
         leftIndex = 0;
         rightIndex = 0;
         anyQuaternaries = 0;

         do {
            do {
               leftLower32 = (int)left.getCE(leftIndex++);
               anyQuaternaries |= leftLower32;

               assert (leftLower32 & 16191) != 0 || (leftLower32 & '샀') == 0;

               leftTertiary = leftLower32 & leftStart;
            } while(leftTertiary == 0);

            do {
               rightLower32 = (int)right.getCE(rightIndex++);
               anyQuaternaries |= rightLower32;

               assert (rightLower32 & 16191) != 0 || (rightLower32 & '샀') == 0;

               rightTertiary = rightLower32 & leftStart;
            } while(rightTertiary == 0);

            if (leftTertiary != rightTertiary) {
               if (CollationSettings.sortsTertiaryUpperCaseFirst(options)) {
                  if (leftTertiary > 256) {
                     if ((leftLower32 & -65536) != 0) {
                        leftTertiary ^= 49152;
                     } else {
                        leftTertiary += 16384;
                     }
                  }

                  if (rightTertiary > 256) {
                     if ((rightLower32 & -65536) != 0) {
                        rightTertiary ^= 49152;
                     } else {
                        rightTertiary += 16384;
                     }
                  }
               }

               return leftTertiary < rightTertiary ? -1 : 1;
            }
         } while(leftTertiary != 256);

         if (CollationSettings.getStrength(options) <= 2) {
            return 0;
         } else if (!anyVariable && (anyQuaternaries & 192) == 0) {
            return 0;
         } else {
            leftIndex = 0;
            rightIndex = 0;

            do {
               do {
                  rightQuaternary = left.getCE(leftIndex++);
                  leftQuaternary = rightQuaternary & 65535L;
                  if (leftQuaternary <= 256L) {
                     leftQuaternary = rightQuaternary >>> 32;
                  } else {
                     leftQuaternary |= 4294967103L;
                  }
               } while(leftQuaternary == 0L);

               do {
                  long ce = right.getCE(rightIndex++);
                  rightQuaternary = ce & 65535L;
                  if (rightQuaternary <= 256L) {
                     rightQuaternary = ce >>> 32;
                  } else {
                     rightQuaternary |= 4294967103L;
                  }
               } while(rightQuaternary == 0L);

               if (leftQuaternary != rightQuaternary) {
                  if (settings.hasReordering()) {
                     leftQuaternary = settings.reorder(leftQuaternary);
                     rightQuaternary = settings.reorder(rightQuaternary);
                  }

                  return leftQuaternary < rightQuaternary ? -1 : 1;
               }
            } while(leftQuaternary != 1L);

            return 0;
         }
      }
   }
}
