package org.python.icu.text;

import org.python.icu.lang.UCharacter;

final class BidiWriter {
   static final char LRM_CHAR = '\u200e';
   static final char RLM_CHAR = '\u200f';
   static final int MASK_R_AL = 8194;

   private static boolean IsCombining(int type) {
      return (1 << type & 448) != 0;
   }

   private static String doWriteForward(String src, int options) {
      int i;
      int c;
      StringBuffer dest;
      switch (options & 10) {
         case 0:
            return src;
         case 2:
            dest = new StringBuffer(src.length());
            i = 0;

            do {
               c = UTF16.charAt(src, i);
               i += UTF16.getCharCount(c);
               UTF16.append(dest, UCharacter.getMirror(c));
            } while(i < src.length());

            return dest.toString();
         case 8:
            StringBuilder dest = new StringBuilder(src.length());
            i = 0;

            do {
               c = src.charAt(i++);
               if (!Bidi.IsBidiControlChar(c)) {
                  dest.append((char)c);
               }
            } while(i < src.length());

            return dest.toString();
         default:
            dest = new StringBuffer(src.length());
            i = 0;

            do {
               c = UTF16.charAt(src, i);
               i += UTF16.getCharCount(c);
               if (!Bidi.IsBidiControlChar(c)) {
                  UTF16.append(dest, UCharacter.getMirror(c));
               }
            } while(i < src.length());

            return dest.toString();
      }
   }

   private static String doWriteForward(char[] text, int start, int limit, int options) {
      return doWriteForward(new String(text, start, limit - start), options);
   }

   static String writeReverse(String src, int options) {
      StringBuffer dest = new StringBuffer(src.length());
      int srcLength;
      int i;
      int c;
      switch (options & 11) {
         case 0:
            srcLength = src.length();

            do {
               i = srcLength;
               srcLength -= UTF16.getCharCount(UTF16.charAt(src, srcLength - 1));
               dest.append(src.substring(srcLength, i));
            } while(srcLength > 0);

            return dest.toString();
         case 1:
            srcLength = src.length();

            do {
               c = srcLength;

               do {
                  i = UTF16.charAt(src, srcLength - 1);
                  srcLength -= UTF16.getCharCount(i);
               } while(srcLength > 0 && IsCombining(UCharacter.getType(i)));

               dest.append(src.substring(srcLength, c));
            } while(srcLength > 0);

            return dest.toString();
         default:
            srcLength = src.length();

            do {
               i = srcLength;
               c = UTF16.charAt(src, srcLength - 1);
               srcLength -= UTF16.getCharCount(c);
               if ((options & 1) != 0) {
                  while(srcLength > 0 && IsCombining(UCharacter.getType(c))) {
                     c = UTF16.charAt(src, srcLength - 1);
                     srcLength -= UTF16.getCharCount(c);
                  }
               }

               if ((options & 8) == 0 || !Bidi.IsBidiControlChar(c)) {
                  int j = srcLength;
                  if ((options & 2) != 0) {
                     c = UCharacter.getMirror(c);
                     UTF16.append(dest, c);
                     j = srcLength + UTF16.getCharCount(c);
                  }

                  dest.append(src.substring(j, i));
               }
            } while(srcLength > 0);

            return dest.toString();
      }
   }

   static String doWriteReverse(char[] text, int start, int limit, int options) {
      return writeReverse(new String(text, start, limit - start), options);
   }

   static String writeReordered(Bidi bidi, int options) {
      char[] text = bidi.text;
      int runCount = bidi.countRuns();
      if ((bidi.reorderingOptions & 1) != 0) {
         options |= 4;
         options &= -9;
      }

      if ((bidi.reorderingOptions & 2) != 0) {
         options |= 8;
         options &= -5;
      }

      if (bidi.reorderingMode != 4 && bidi.reorderingMode != 5 && bidi.reorderingMode != 6 && bidi.reorderingMode != 3) {
         options &= -5;
      }

      StringBuilder dest = new StringBuilder((options & 4) != 0 ? bidi.length * 2 : bidi.length);
      int run;
      BidiRun bidiRun;
      byte[] dirProps;
      if ((options & 16) == 0) {
         if ((options & 4) == 0) {
            for(run = 0; run < runCount; ++run) {
               bidiRun = bidi.getVisualRun(run);
               if (bidiRun.isEvenRun()) {
                  dest.append(doWriteForward(text, bidiRun.start, bidiRun.limit, options & -3));
               } else {
                  dest.append(doWriteReverse(text, bidiRun.start, bidiRun.limit, options));
               }
            }
         } else {
            dirProps = bidi.dirProps;

            for(run = 0; run < runCount; ++run) {
               BidiRun bidiRun = bidi.getVisualRun(run);
               int markFlag = false;
               int markFlag = bidi.runs[run].insertRemove;
               if (markFlag < 0) {
                  markFlag = 0;
               }

               short uc;
               if (bidiRun.isEvenRun()) {
                  if (bidi.isInverse() && dirProps[bidiRun.start] != 0) {
                     markFlag |= 1;
                  }

                  if ((markFlag & 1) != 0) {
                     uc = 8206;
                  } else if ((markFlag & 4) != 0) {
                     uc = 8207;
                  } else {
                     uc = 0;
                  }

                  if (uc != 0) {
                     dest.append((char)uc);
                  }

                  dest.append(doWriteForward(text, bidiRun.start, bidiRun.limit, options & -3));
                  if (bidi.isInverse() && dirProps[bidiRun.limit - 1] != 0) {
                     markFlag |= 2;
                  }

                  if ((markFlag & 2) != 0) {
                     uc = 8206;
                  } else if ((markFlag & 8) != 0) {
                     uc = 8207;
                  } else {
                     uc = 0;
                  }

                  if (uc != 0) {
                     dest.append((char)uc);
                  }
               } else {
                  if (bidi.isInverse() && !bidi.testDirPropFlagAt(8194, bidiRun.limit - 1)) {
                     markFlag |= 4;
                  }

                  if ((markFlag & 1) != 0) {
                     uc = 8206;
                  } else if ((markFlag & 4) != 0) {
                     uc = 8207;
                  } else {
                     uc = 0;
                  }

                  if (uc != 0) {
                     dest.append((char)uc);
                  }

                  dest.append(doWriteReverse(text, bidiRun.start, bidiRun.limit, options));
                  if (bidi.isInverse() && (8194 & Bidi.DirPropFlag(dirProps[bidiRun.start])) == 0) {
                     markFlag |= 8;
                  }

                  if ((markFlag & 2) != 0) {
                     uc = 8206;
                  } else if ((markFlag & 8) != 0) {
                     uc = 8207;
                  } else {
                     uc = 0;
                  }

                  if (uc != 0) {
                     dest.append((char)uc);
                  }
               }
            }
         }
      } else if ((options & 4) == 0) {
         run = runCount;

         while(true) {
            --run;
            if (run < 0) {
               break;
            }

            bidiRun = bidi.getVisualRun(run);
            if (bidiRun.isEvenRun()) {
               dest.append(doWriteReverse(text, bidiRun.start, bidiRun.limit, options & -3));
            } else {
               dest.append(doWriteForward(text, bidiRun.start, bidiRun.limit, options));
            }
         }
      } else {
         dirProps = bidi.dirProps;
         run = runCount;

         while(true) {
            --run;
            if (run < 0) {
               break;
            }

            BidiRun bidiRun = bidi.getVisualRun(run);
            if (bidiRun.isEvenRun()) {
               if (dirProps[bidiRun.limit - 1] != 0) {
                  dest.append('\u200e');
               }

               dest.append(doWriteReverse(text, bidiRun.start, bidiRun.limit, options & -3));
               if (dirProps[bidiRun.start] != 0) {
                  dest.append('\u200e');
               }
            } else {
               if ((8194 & Bidi.DirPropFlag(dirProps[bidiRun.start])) == 0) {
                  dest.append('\u200f');
               }

               dest.append(doWriteForward(text, bidiRun.start, bidiRun.limit, options));
               if ((8194 & Bidi.DirPropFlag(dirProps[bidiRun.limit - 1])) == 0) {
                  dest.append('\u200f');
               }
            }
         }
      }

      return dest.toString();
   }
}
