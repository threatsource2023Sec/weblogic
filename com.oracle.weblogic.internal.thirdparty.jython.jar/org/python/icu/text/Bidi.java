package org.python.icu.text;

import java.awt.font.NumericShaper;
import java.awt.font.TextAttribute;
import java.lang.reflect.Array;
import java.text.AttributedCharacterIterator;
import java.util.Arrays;
import org.python.icu.impl.UBiDiProps;
import org.python.icu.lang.UCharacter;

public class Bidi {
   public static final byte LEVEL_DEFAULT_LTR = 126;
   public static final byte LEVEL_DEFAULT_RTL = 127;
   public static final byte MAX_EXPLICIT_LEVEL = 125;
   public static final byte LEVEL_OVERRIDE = -128;
   public static final int MAP_NOWHERE = -1;
   public static final byte LTR = 0;
   public static final byte RTL = 1;
   public static final byte MIXED = 2;
   public static final byte NEUTRAL = 3;
   public static final short KEEP_BASE_COMBINING = 1;
   public static final short DO_MIRRORING = 2;
   public static final short INSERT_LRM_FOR_NUMERIC = 4;
   public static final short REMOVE_BIDI_CONTROLS = 8;
   public static final short OUTPUT_REVERSE = 16;
   public static final short REORDER_DEFAULT = 0;
   public static final short REORDER_NUMBERS_SPECIAL = 1;
   public static final short REORDER_GROUP_NUMBERS_WITH_R = 2;
   public static final short REORDER_RUNS_ONLY = 3;
   public static final short REORDER_INVERSE_NUMBERS_AS_L = 4;
   public static final short REORDER_INVERSE_LIKE_DIRECT = 5;
   public static final short REORDER_INVERSE_FOR_NUMBERS_SPECIAL = 6;
   static final short REORDER_COUNT = 7;
   static final short REORDER_LAST_LOGICAL_TO_VISUAL = 1;
   public static final int OPTION_DEFAULT = 0;
   public static final int OPTION_INSERT_MARKS = 1;
   public static final int OPTION_REMOVE_CONTROLS = 2;
   public static final int OPTION_STREAMING = 4;
   static final byte L = 0;
   static final byte R = 1;
   static final byte EN = 2;
   static final byte ES = 3;
   static final byte ET = 4;
   static final byte AN = 5;
   static final byte CS = 6;
   static final byte B = 7;
   static final byte S = 8;
   static final byte WS = 9;
   static final byte ON = 10;
   static final byte LRE = 11;
   static final byte LRO = 12;
   static final byte AL = 13;
   static final byte RLE = 14;
   static final byte RLO = 15;
   static final byte PDF = 16;
   static final byte NSM = 17;
   static final byte BN = 18;
   static final byte FSI = 19;
   static final byte LRI = 20;
   static final byte RLI = 21;
   static final byte PDI = 22;
   static final byte ENL = 23;
   static final byte ENR = 24;
   /** @deprecated */
   @Deprecated
   public static final int CLASS_DEFAULT = 23;
   static final int SIMPLE_PARAS_COUNT = 10;
   static final int SIMPLE_OPENINGS_COUNT = 20;
   private static final char CR = '\r';
   private static final char LF = '\n';
   static final int LRM_BEFORE = 1;
   static final int LRM_AFTER = 2;
   static final int RLM_BEFORE = 4;
   static final int RLM_AFTER = 8;
   static final byte FOUND_L = (byte)DirPropFlag((byte)0);
   static final byte FOUND_R = (byte)DirPropFlag((byte)1);
   static final int ISOLATE = 256;
   Bidi paraBidi;
   final UBiDiProps bdp;
   char[] text;
   int originalLength;
   int length;
   int resultLength;
   boolean mayAllocateText;
   boolean mayAllocateRuns;
   byte[] dirPropsMemory;
   byte[] levelsMemory;
   byte[] dirProps;
   byte[] levels;
   boolean isInverse;
   int reorderingMode;
   int reorderingOptions;
   boolean orderParagraphsLTR;
   byte paraLevel;
   byte defaultParaLevel;
   String prologue;
   String epilogue;
   ImpTabPair impTabPair;
   byte direction;
   int flags;
   int lastArabicPos;
   int trailingWSStart;
   int paraCount;
   int[] paras_limit;
   byte[] paras_level;
   int runCount;
   BidiRun[] runsMemory;
   BidiRun[] runs;
   BidiRun[] simpleRuns;
   Isolate[] isolates;
   int isolateCount;
   int[] logicalToVisualRunsMap;
   boolean isGoodLogicalToVisualRunsMap;
   BidiClassifier customClassifier;
   InsertPoints insertPoints;
   int controlCount;
   static final int DirPropFlagMultiRuns = DirPropFlag((byte)31);
   static final int[] DirPropFlagLR = new int[]{DirPropFlag((byte)0), DirPropFlag((byte)1)};
   static final int[] DirPropFlagE = new int[]{DirPropFlag((byte)11), DirPropFlag((byte)14)};
   static final int[] DirPropFlagO = new int[]{DirPropFlag((byte)12), DirPropFlag((byte)15)};
   static final int MASK_LTR = DirPropFlag((byte)0) | DirPropFlag((byte)2) | DirPropFlag((byte)23) | DirPropFlag((byte)24) | DirPropFlag((byte)5) | DirPropFlag((byte)11) | DirPropFlag((byte)12) | DirPropFlag((byte)20);
   static final int MASK_RTL = DirPropFlag((byte)1) | DirPropFlag((byte)13) | DirPropFlag((byte)14) | DirPropFlag((byte)15) | DirPropFlag((byte)21);
   static final int MASK_R_AL = DirPropFlag((byte)1) | DirPropFlag((byte)13);
   static final int MASK_STRONG_EN_AN = DirPropFlag((byte)0) | DirPropFlag((byte)1) | DirPropFlag((byte)13) | DirPropFlag((byte)2) | DirPropFlag((byte)5);
   static final int MASK_EXPLICIT = DirPropFlag((byte)11) | DirPropFlag((byte)12) | DirPropFlag((byte)14) | DirPropFlag((byte)15) | DirPropFlag((byte)16);
   static final int MASK_BN_EXPLICIT;
   static final int MASK_ISO;
   static final int MASK_B_S;
   static final int MASK_WS;
   static final int MASK_POSSIBLE_N;
   static final int MASK_EMBEDDING;
   static final int NOT_SEEKING_STRONG = 0;
   static final int SEEKING_STRONG_FOR_PARA = 1;
   static final int SEEKING_STRONG_FOR_FSI = 2;
   static final int LOOKING_FOR_PDI = 3;
   private static final int IMPTABPROPS_COLUMNS = 16;
   private static final int IMPTABPROPS_RES = 15;
   private static final short[] groupProp;
   private static final short _L = 0;
   private static final short _R = 1;
   private static final short _EN = 2;
   private static final short _AN = 3;
   private static final short _ON = 4;
   private static final short _S = 5;
   private static final short _B = 6;
   private static final short[][] impTabProps;
   private static final int IMPTABLEVELS_COLUMNS = 8;
   private static final int IMPTABLEVELS_RES = 7;
   private static final byte[][] impTabL_DEFAULT;
   private static final byte[][] impTabR_DEFAULT;
   private static final short[] impAct0;
   private static final ImpTabPair impTab_DEFAULT;
   private static final byte[][] impTabL_NUMBERS_SPECIAL;
   private static final ImpTabPair impTab_NUMBERS_SPECIAL;
   private static final byte[][] impTabL_GROUP_NUMBERS_WITH_R;
   private static final byte[][] impTabR_GROUP_NUMBERS_WITH_R;
   private static final ImpTabPair impTab_GROUP_NUMBERS_WITH_R;
   private static final byte[][] impTabL_INVERSE_NUMBERS_AS_L;
   private static final byte[][] impTabR_INVERSE_NUMBERS_AS_L;
   private static final ImpTabPair impTab_INVERSE_NUMBERS_AS_L;
   private static final byte[][] impTabR_INVERSE_LIKE_DIRECT;
   private static final short[] impAct1;
   private static final ImpTabPair impTab_INVERSE_LIKE_DIRECT;
   private static final byte[][] impTabL_INVERSE_LIKE_DIRECT_WITH_MARKS;
   private static final byte[][] impTabR_INVERSE_LIKE_DIRECT_WITH_MARKS;
   private static final short[] impAct2;
   private static final short[] impAct3;
   private static final ImpTabPair impTab_INVERSE_LIKE_DIRECT_WITH_MARKS;
   private static final ImpTabPair impTab_INVERSE_FOR_NUMBERS_SPECIAL;
   private static final byte[][] impTabL_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS;
   private static final ImpTabPair impTab_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS;
   static final int FIRSTALLOC = 10;
   public static final int DIRECTION_LEFT_TO_RIGHT = 0;
   public static final int DIRECTION_RIGHT_TO_LEFT = 1;
   public static final int DIRECTION_DEFAULT_LEFT_TO_RIGHT = 126;
   public static final int DIRECTION_DEFAULT_RIGHT_TO_LEFT = 127;

   static int DirPropFlag(byte dir) {
      return 1 << dir;
   }

   boolean testDirPropFlagAt(int flag, int index) {
      return (DirPropFlag(this.dirProps[index]) & flag) != 0;
   }

   static final int DirPropFlagLR(byte level) {
      return DirPropFlagLR[level & 1];
   }

   static final int DirPropFlagE(byte level) {
      return DirPropFlagE[level & 1];
   }

   static final int DirPropFlagO(byte level) {
      return DirPropFlagO[level & 1];
   }

   static final byte DirFromStrong(byte strong) {
      return (byte)(strong == 0 ? 0 : 1);
   }

   static final byte NoOverride(byte level) {
      return (byte)(level & 127);
   }

   static byte GetLRFromLevel(byte level) {
      return (byte)(level & 1);
   }

   static boolean IsDefaultLevel(byte level) {
      return (level & 126) == 126;
   }

   static boolean IsBidiControlChar(int c) {
      return (c & -4) == 8204 || c >= 8234 && c <= 8238 || c >= 8294 && c <= 8297;
   }

   void verifyValidPara() {
      if (this != this.paraBidi) {
         throw new IllegalStateException();
      }
   }

   void verifyValidParaOrLine() {
      Bidi para = this.paraBidi;
      if (this != para) {
         if (para == null || para != para.paraBidi) {
            throw new IllegalStateException();
         }
      }
   }

   void verifyRange(int index, int start, int limit) {
      if (index < start || index >= limit) {
         throw new IllegalArgumentException("Value " + index + " is out of range " + start + " to " + limit);
      }
   }

   public Bidi() {
      this(0, 0);
   }

   public Bidi(int maxLength, int maxRunCount) {
      this.dirPropsMemory = new byte[1];
      this.levelsMemory = new byte[1];
      this.paras_limit = new int[10];
      this.paras_level = new byte[10];
      this.runsMemory = new BidiRun[0];
      this.simpleRuns = new BidiRun[]{new BidiRun()};
      this.customClassifier = null;
      this.insertPoints = new InsertPoints();
      if (maxLength >= 0 && maxRunCount >= 0) {
         this.bdp = UBiDiProps.INSTANCE;
         if (maxLength > 0) {
            this.getInitialDirPropsMemory(maxLength);
            this.getInitialLevelsMemory(maxLength);
         } else {
            this.mayAllocateText = true;
         }

         if (maxRunCount > 0) {
            if (maxRunCount > 1) {
               this.getInitialRunsMemory(maxRunCount);
            }
         } else {
            this.mayAllocateRuns = true;
         }

      } else {
         throw new IllegalArgumentException();
      }
   }

   private Object getMemory(String label, Object array, Class arrayClass, boolean mayAllocate, int sizeNeeded) {
      int len = Array.getLength(array);
      if (sizeNeeded == len) {
         return array;
      } else if (!mayAllocate) {
         if (sizeNeeded <= len) {
            return array;
         } else {
            throw new OutOfMemoryError("Failed to allocate memory for " + label);
         }
      } else {
         try {
            return Array.newInstance(arrayClass, sizeNeeded);
         } catch (Exception var8) {
            throw new OutOfMemoryError("Failed to allocate memory for " + label);
         }
      }
   }

   private void getDirPropsMemory(boolean mayAllocate, int len) {
      Object array = this.getMemory("DirProps", this.dirPropsMemory, Byte.TYPE, mayAllocate, len);
      this.dirPropsMemory = (byte[])((byte[])array);
   }

   void getDirPropsMemory(int len) {
      this.getDirPropsMemory(this.mayAllocateText, len);
   }

   private void getLevelsMemory(boolean mayAllocate, int len) {
      Object array = this.getMemory("Levels", this.levelsMemory, Byte.TYPE, mayAllocate, len);
      this.levelsMemory = (byte[])((byte[])array);
   }

   void getLevelsMemory(int len) {
      this.getLevelsMemory(this.mayAllocateText, len);
   }

   private void getRunsMemory(boolean mayAllocate, int len) {
      Object array = this.getMemory("Runs", this.runsMemory, BidiRun.class, mayAllocate, len);
      this.runsMemory = (BidiRun[])((BidiRun[])array);
   }

   void getRunsMemory(int len) {
      this.getRunsMemory(this.mayAllocateRuns, len);
   }

   private void getInitialDirPropsMemory(int len) {
      this.getDirPropsMemory(true, len);
   }

   private void getInitialLevelsMemory(int len) {
      this.getLevelsMemory(true, len);
   }

   private void getInitialRunsMemory(int len) {
      this.getRunsMemory(true, len);
   }

   public void setInverse(boolean isInverse) {
      this.isInverse = isInverse;
      this.reorderingMode = isInverse ? 4 : 0;
   }

   public boolean isInverse() {
      return this.isInverse;
   }

   public void setReorderingMode(int reorderingMode) {
      if (reorderingMode >= 0 && reorderingMode < 7) {
         this.reorderingMode = reorderingMode;
         this.isInverse = reorderingMode == 4;
      }
   }

   public int getReorderingMode() {
      return this.reorderingMode;
   }

   public void setReorderingOptions(int options) {
      if ((options & 2) != 0) {
         this.reorderingOptions = options & -2;
      } else {
         this.reorderingOptions = options;
      }

   }

   public int getReorderingOptions() {
      return this.reorderingOptions;
   }

   public static byte getBaseDirection(CharSequence paragraph) {
      if (paragraph != null && paragraph.length() != 0) {
         int length = paragraph.length();

         for(int i = 0; i < length; i = UCharacter.offsetByCodePoints(paragraph, i, 1)) {
            int c = UCharacter.codePointAt(paragraph, i);
            byte direction = UCharacter.getDirectionality(c);
            if (direction == 0) {
               return 0;
            }

            if (direction == 1 || direction == 13) {
               return 1;
            }
         }

         return 3;
      } else {
         return 3;
      }
   }

   private byte firstL_R_AL() {
      byte result = 10;
      int i = 0;

      while(true) {
         byte dirProp;
         label29:
         do {
            while(i < this.prologue.length()) {
               int uchar = this.prologue.codePointAt(i);
               i += Character.charCount(uchar);
               dirProp = (byte)this.getCustomizedClass(uchar);
               if (result == 10) {
                  continue label29;
               }

               if (dirProp == 7) {
                  result = 10;
               }
            }

            return result;
         } while(dirProp != 0 && dirProp != 1 && dirProp != 13);

         result = dirProp;
      }
   }

   private void checkParaCount() {
      int count = this.paraCount;
      if (count > this.paras_level.length) {
         int oldLength = this.paras_level.length;
         int[] saveLimits = this.paras_limit;
         byte[] saveLevels = this.paras_level;

         try {
            this.paras_limit = new int[count * 2];
            this.paras_level = new byte[count * 2];
         } catch (Exception var6) {
            throw new OutOfMemoryError("Failed to allocate memory for paras");
         }

         System.arraycopy(saveLimits, 0, this.paras_limit, 0, oldLength);
         System.arraycopy(saveLevels, 0, this.paras_level, 0, oldLength);
      }
   }

   private void getDirProps() {
      int i = false;
      this.flags = 0;
      byte defaultParaLevel = false;
      boolean isDefaultLevel = IsDefaultLevel(this.paraLevel);
      boolean isDefaultLevelInverse = isDefaultLevel && (this.reorderingMode == 5 || this.reorderingMode == 6);
      this.lastArabicPos = -1;
      int controlCount = 0;
      boolean removeBidiControls = (this.reorderingOptions & 2) != 0;
      byte lastStrong = 10;
      int[] isolateStartStack = new int[126];
      byte[] previousStateStack = new byte[126];
      int stackLast = -1;
      if ((this.reorderingOptions & 4) != 0) {
         this.length = 0;
      }

      byte defaultParaLevel = (byte)(this.paraLevel & 1);
      byte dirProp;
      byte state;
      if (isDefaultLevel) {
         this.paras_level[0] = defaultParaLevel;
         lastStrong = defaultParaLevel;
         if (this.prologue != null && (dirProp = this.firstL_R_AL()) != 10) {
            if (dirProp == 0) {
               this.paras_level[0] = 0;
            } else {
               this.paras_level[0] = 1;
            }

            state = 0;
         } else {
            state = 1;
         }
      } else {
         this.paras_level[0] = this.paraLevel;
         state = 0;
      }

      int i = 0;

      while(true) {
         while(i < this.originalLength) {
            int i0 = i;
            int uchar = UTF16.charAt(this.text, 0, this.originalLength, i);
            i += UTF16.getCharCount(uchar);
            int i1 = i - 1;
            dirProp = (byte)this.getCustomizedClass(uchar);
            this.flags |= DirPropFlag(dirProp);
            this.dirProps[i1] = dirProp;
            if (i1 > i0) {
               this.flags |= DirPropFlag((byte)18);

               do {
                  --i1;
                  this.dirProps[i1] = 18;
               } while(i1 > i0);
            }

            if (removeBidiControls && IsBidiControlChar(uchar)) {
               ++controlCount;
            }

            if (dirProp == 0) {
               if (state == 1) {
                  this.paras_level[this.paraCount - 1] = 0;
                  state = 0;
               } else if (state == 2) {
                  if (stackLast <= 125) {
                     this.flags |= DirPropFlag((byte)20);
                  }

                  state = 3;
               }

               lastStrong = 0;
            } else if (dirProp != 1 && dirProp != 13) {
               if (dirProp >= 19 && dirProp <= 21) {
                  ++stackLast;
                  if (stackLast <= 125) {
                     isolateStartStack[stackLast] = i - 1;
                     previousStateStack[stackLast] = state;
                  }

                  if (dirProp == 19) {
                     this.dirProps[i - 1] = 20;
                     state = 2;
                  } else {
                     state = 3;
                  }
               } else if (dirProp == 22) {
                  if (state == 2 && stackLast <= 125) {
                     this.flags |= DirPropFlag((byte)20);
                  }

                  if (stackLast >= 0) {
                     if (stackLast <= 125) {
                        state = previousStateStack[stackLast];
                     }

                     --stackLast;
                  }
               } else if (dirProp == 7 && (i >= this.originalLength || uchar != 13 || this.text[i] != '\n')) {
                  this.paras_limit[this.paraCount - 1] = i;
                  if (isDefaultLevelInverse && lastStrong == 1) {
                     this.paras_level[this.paraCount - 1] = 1;
                  }

                  if ((this.reorderingOptions & 4) != 0) {
                     this.length = i;
                     this.controlCount = controlCount;
                  }

                  if (i < this.originalLength) {
                     ++this.paraCount;
                     this.checkParaCount();
                     if (isDefaultLevel) {
                        this.paras_level[this.paraCount - 1] = defaultParaLevel;
                        state = 1;
                        lastStrong = defaultParaLevel;
                     } else {
                        this.paras_level[this.paraCount - 1] = this.paraLevel;
                        state = 0;
                     }

                     stackLast = -1;
                  }
               }
            } else {
               if (state == 1) {
                  this.paras_level[this.paraCount - 1] = 1;
                  state = 0;
               } else if (state == 2) {
                  if (stackLast <= 125) {
                     this.dirProps[isolateStartStack[stackLast]] = 21;
                     this.flags |= DirPropFlag((byte)21);
                  }

                  state = 3;
               }

               lastStrong = 1;
               if (dirProp == 13) {
                  this.lastArabicPos = i - 1;
               }
            }
         }

         if (stackLast > 125) {
            stackLast = 125;
            state = 2;
         }

         while(stackLast >= 0) {
            if (state == 2) {
               this.flags |= DirPropFlag((byte)20);
               break;
            }

            state = previousStateStack[stackLast];
            --stackLast;
         }

         if ((this.reorderingOptions & 4) != 0) {
            if (this.length < this.originalLength) {
               --this.paraCount;
            }
         } else {
            this.paras_limit[this.paraCount - 1] = this.originalLength;
            this.controlCount = controlCount;
         }

         if (isDefaultLevelInverse && lastStrong == 1) {
            this.paras_level[this.paraCount - 1] = 1;
         }

         if (isDefaultLevel) {
            this.paraLevel = this.paras_level[0];
         }

         for(i = 0; i < this.paraCount; ++i) {
            this.flags |= DirPropFlagLR(this.paras_level[i]);
         }

         if (this.orderParagraphsLTR && (this.flags & DirPropFlag((byte)7)) != 0) {
            this.flags |= DirPropFlag((byte)0);
         }

         return;
      }
   }

   byte GetParaLevelAt(int pindex) {
      if (this.defaultParaLevel != 0 && pindex >= this.paras_limit[0]) {
         int i;
         for(i = 1; i < this.paraCount && pindex >= this.paras_limit[i]; ++i) {
         }

         if (i >= this.paraCount) {
            i = this.paraCount - 1;
         }

         return this.paras_level[i];
      } else {
         return this.paraLevel;
      }
   }

   private void bracketInit(BracketData bd) {
      bd.isoRunLast = 0;
      bd.isoRuns[0] = new IsoRun();
      bd.isoRuns[0].start = 0;
      bd.isoRuns[0].limit = 0;
      bd.isoRuns[0].level = this.GetParaLevelAt(0);
      bd.isoRuns[0].lastStrong = bd.isoRuns[0].lastBase = bd.isoRuns[0].contextDir = (byte)(this.GetParaLevelAt(0) & 1);
      bd.isoRuns[0].contextPos = 0;
      bd.openings = new Opening[20];
      bd.isNumbersSpecial = this.reorderingMode == 1 || this.reorderingMode == 6;
   }

   private void bracketProcessB(BracketData bd, byte level) {
      bd.isoRunLast = 0;
      bd.isoRuns[0].limit = 0;
      bd.isoRuns[0].level = level;
      bd.isoRuns[0].lastStrong = bd.isoRuns[0].lastBase = bd.isoRuns[0].contextDir = (byte)(level & 1);
      bd.isoRuns[0].contextPos = 0;
   }

   private void bracketProcessBoundary(BracketData bd, int lastCcPos, byte contextLevel, byte embeddingLevel) {
      IsoRun pLastIsoRun = bd.isoRuns[bd.isoRunLast];
      if ((DirPropFlag(this.dirProps[lastCcPos]) & MASK_ISO) == 0) {
         if (NoOverride(embeddingLevel) > NoOverride(contextLevel)) {
            contextLevel = embeddingLevel;
         }

         pLastIsoRun.limit = pLastIsoRun.start;
         pLastIsoRun.level = embeddingLevel;
         pLastIsoRun.lastStrong = pLastIsoRun.lastBase = pLastIsoRun.contextDir = (byte)(contextLevel & 1);
         pLastIsoRun.contextPos = lastCcPos;
      }
   }

   private void bracketProcessLRI_RLI(BracketData bd, byte level) {
      IsoRun pLastIsoRun = bd.isoRuns[bd.isoRunLast];
      pLastIsoRun.lastBase = 10;
      short lastLimit = pLastIsoRun.limit;
      ++bd.isoRunLast;
      pLastIsoRun = bd.isoRuns[bd.isoRunLast];
      if (pLastIsoRun == null) {
         pLastIsoRun = bd.isoRuns[bd.isoRunLast] = new IsoRun();
      }

      pLastIsoRun.start = pLastIsoRun.limit = lastLimit;
      pLastIsoRun.level = level;
      pLastIsoRun.lastStrong = pLastIsoRun.lastBase = pLastIsoRun.contextDir = (byte)(level & 1);
      pLastIsoRun.contextPos = 0;
   }

   private void bracketProcessPDI(BracketData bd) {
      --bd.isoRunLast;
      IsoRun pLastIsoRun = bd.isoRuns[bd.isoRunLast];
      pLastIsoRun.lastBase = 10;
   }

   private void bracketAddOpening(BracketData bd, char match, int position) {
      IsoRun pLastIsoRun = bd.isoRuns[bd.isoRunLast];
      if (pLastIsoRun.limit >= bd.openings.length) {
         Opening[] saveOpenings = bd.openings;

         int count;
         try {
            count = bd.openings.length;
            bd.openings = new Opening[count * 2];
         } catch (Exception var9) {
            throw new OutOfMemoryError("Failed to allocate memory for openings");
         }

         System.arraycopy(saveOpenings, 0, bd.openings, 0, count);
      }

      Opening pOpening = bd.openings[pLastIsoRun.limit];
      if (pOpening == null) {
         pOpening = bd.openings[pLastIsoRun.limit] = new Opening();
      }

      pOpening.position = position;
      pOpening.match = match;
      pOpening.contextDir = pLastIsoRun.contextDir;
      pOpening.contextPos = pLastIsoRun.contextPos;
      pOpening.flags = 0;
      ++pLastIsoRun.limit;
   }

   private void fixN0c(BracketData bd, int openingIndex, int newPropPosition, byte newProp) {
      IsoRun pLastIsoRun = bd.isoRuns[bd.isoRunLast];

      for(int k = openingIndex + 1; k < pLastIsoRun.limit; ++k) {
         Opening qOpening = bd.openings[k];
         if (qOpening.match < 0) {
            if (newPropPosition < qOpening.contextPos) {
               break;
            }

            if (newPropPosition < qOpening.position) {
               if (newProp == qOpening.contextDir) {
                  break;
               }

               int openingPosition = qOpening.position;
               this.dirProps[openingPosition] = newProp;
               int closingPosition = -qOpening.match;
               this.dirProps[closingPosition] = newProp;
               qOpening.match = 0;
               this.fixN0c(bd, k, openingPosition, newProp);
               this.fixN0c(bd, k, closingPosition, newProp);
            }
         }
      }

   }

   private byte bracketProcessClosing(BracketData bd, int openIdx, int position) {
      IsoRun pLastIsoRun = bd.isoRuns[bd.isoRunLast];
      Opening pOpening = bd.openings[openIdx];
      byte direction = (byte)(pLastIsoRun.level & 1);
      boolean stable = true;
      byte newProp;
      if ((direction != 0 || (pOpening.flags & FOUND_L) <= 0) && (direction != 1 || (pOpening.flags & FOUND_R) <= 0)) {
         if ((pOpening.flags & (FOUND_L | FOUND_R)) == 0) {
            pLastIsoRun.limit = (short)openIdx;
            return 10;
         }

         stable = openIdx == pLastIsoRun.start;
         if (direction != pOpening.contextDir) {
            newProp = pOpening.contextDir;
         } else {
            newProp = direction;
         }
      } else {
         newProp = direction;
      }

      this.dirProps[pOpening.position] = newProp;
      this.dirProps[position] = newProp;
      this.fixN0c(bd, openIdx, pOpening.position, newProp);
      if (stable) {
         for(pLastIsoRun.limit = (short)openIdx; pLastIsoRun.limit > pLastIsoRun.start && bd.openings[pLastIsoRun.limit - 1].position == pOpening.position; --pLastIsoRun.limit) {
         }
      } else {
         pOpening.match = -position;

         int k;
         for(k = openIdx - 1; k >= pLastIsoRun.start && bd.openings[k].position == pOpening.position; bd.openings[k--].match = 0) {
         }

         for(k = openIdx + 1; k < pLastIsoRun.limit; ++k) {
            Opening qOpening = bd.openings[k];
            if (qOpening.position >= position) {
               break;
            }

            if (qOpening.match > 0) {
               qOpening.match = 0;
            }
         }
      }

      return newProp;
   }

   private void bracketProcessChar(BracketData bd, int position) {
      IsoRun pLastIsoRun = bd.isoRuns[bd.isoRunLast];
      byte dirProp = this.dirProps[position];
      int i;
      byte newProp;
      byte level;
      short flag;
      Opening var10000;
      if (dirProp == 10) {
         i = this.text[position];

         for(int idx = pLastIsoRun.limit - 1; idx >= pLastIsoRun.start; --idx) {
            if (bd.openings[idx].match == i) {
               newProp = this.bracketProcessClosing(bd, idx, position);
               if (newProp != 10) {
                  pLastIsoRun.lastBase = 10;
                  pLastIsoRun.contextDir = newProp;
                  pLastIsoRun.contextPos = position;
                  level = this.levels[position];
                  byte[] var12;
                  if ((level & -128) != 0) {
                     newProp = (byte)(level & 1);
                     pLastIsoRun.lastStrong = newProp;
                     short flag = (short)DirPropFlag(newProp);

                     for(int i = pLastIsoRun.start; i < idx; ++i) {
                        var10000 = bd.openings[i];
                        var10000.flags |= flag;
                     }

                     var12 = this.levels;
                     var12[position] = (byte)(var12[position] & 127);
                  }

                  var12 = this.levels;
                  int var10001 = bd.openings[idx].position;
                  var12[var10001] = (byte)(var12[var10001] & 127);
                  return;
               }

               i = 0;
               break;
            }
         }

         if (i != 0) {
            flag = (short)((char)UCharacter.getBidiPairedBracket(i));
         } else {
            flag = 0;
         }

         if (flag != i && UCharacter.getIntPropertyValue(i, 4117) == 1) {
            if (flag == 9002) {
               this.bracketAddOpening(bd, '〉', position);
            } else if (flag == 12297) {
               this.bracketAddOpening(bd, '〉', position);
            }

            this.bracketAddOpening(bd, (char)flag, position);
         }
      }

      level = this.levels[position];
      if ((level & -128) != 0) {
         newProp = (byte)(level & 1);
         if (dirProp != 8 && dirProp != 9 && dirProp != 10) {
            this.dirProps[position] = newProp;
         }

         pLastIsoRun.lastBase = newProp;
         pLastIsoRun.lastStrong = newProp;
         pLastIsoRun.contextDir = newProp;
         pLastIsoRun.contextPos = position;
      } else if (dirProp > 1 && dirProp != 13) {
         if (dirProp == 2) {
            pLastIsoRun.lastBase = 2;
            if (pLastIsoRun.lastStrong == 0) {
               newProp = 0;
               if (!bd.isNumbersSpecial) {
                  this.dirProps[position] = 23;
               }

               pLastIsoRun.contextDir = 0;
               pLastIsoRun.contextPos = position;
            } else {
               newProp = 1;
               if (pLastIsoRun.lastStrong == 13) {
                  this.dirProps[position] = 5;
               } else {
                  this.dirProps[position] = 24;
               }

               pLastIsoRun.contextDir = 1;
               pLastIsoRun.contextPos = position;
            }
         } else if (dirProp == 5) {
            newProp = 1;
            pLastIsoRun.lastBase = 5;
            pLastIsoRun.contextDir = 1;
            pLastIsoRun.contextPos = position;
         } else if (dirProp == 17) {
            newProp = pLastIsoRun.lastBase;
            if (newProp == 10) {
               this.dirProps[position] = newProp;
            }
         } else {
            newProp = dirProp;
            pLastIsoRun.lastBase = dirProp;
         }
      } else {
         newProp = DirFromStrong(dirProp);
         pLastIsoRun.lastBase = dirProp;
         pLastIsoRun.lastStrong = dirProp;
         pLastIsoRun.contextDir = newProp;
         pLastIsoRun.contextPos = position;
      }

      if (newProp <= 1 || newProp == 13) {
         flag = (short)DirPropFlag(DirFromStrong(newProp));

         for(i = pLastIsoRun.start; i < pLastIsoRun.limit; ++i) {
            if (position > bd.openings[i].position) {
               var10000 = bd.openings[i];
               var10000.flags |= flag;
            }
         }
      }

   }

   private byte directionFromFlags() {
      if ((this.flags & MASK_RTL) != 0 || (this.flags & DirPropFlag((byte)5)) != 0 && (this.flags & MASK_POSSIBLE_N) != 0) {
         return (byte)((this.flags & MASK_LTR) == 0 ? 1 : 2);
      } else {
         return 0;
      }
   }

   private byte resolveExplicitLevels() {
      int i = false;
      byte level = this.GetParaLevelAt(0);
      this.isolateCount = 0;
      byte dirct = this.directionFromFlags();
      if (dirct != 2) {
         return dirct;
      } else {
         int embeddingLevel;
         int newLevel;
         int previousLevel;
         int i;
         if (this.reorderingMode > 1) {
            for(embeddingLevel = 0; embeddingLevel < this.paraCount; ++embeddingLevel) {
               if (embeddingLevel == 0) {
                  newLevel = 0;
               } else {
                  newLevel = this.paras_limit[embeddingLevel - 1];
               }

               previousLevel = this.paras_limit[embeddingLevel];
               level = this.paras_level[embeddingLevel];

               for(i = newLevel; i < previousLevel; ++i) {
                  this.levels[i] = level;
               }
            }

            return dirct;
         } else {
            byte dirProp;
            if ((this.flags & (MASK_EXPLICIT | MASK_ISO)) == 0) {
               BracketData bracketData = new BracketData();
               this.bracketInit(bracketData);

               for(embeddingLevel = 0; embeddingLevel < this.paraCount; ++embeddingLevel) {
                  if (embeddingLevel == 0) {
                     newLevel = 0;
                  } else {
                     newLevel = this.paras_limit[embeddingLevel - 1];
                  }

                  previousLevel = this.paras_limit[embeddingLevel];
                  level = this.paras_level[embeddingLevel];

                  for(i = newLevel; i < previousLevel; ++i) {
                     this.levels[i] = level;
                     dirProp = this.dirProps[i];
                     if (dirProp != 18) {
                        if (dirProp == 7) {
                           if (i + 1 < this.length && (this.text[i] != '\r' || this.text[i + 1] != '\n')) {
                              this.bracketProcessB(bracketData, level);
                           }
                        } else {
                           this.bracketProcessChar(bracketData, i);
                        }
                     }
                  }
               }

               return dirct;
            } else {
               embeddingLevel = level;
               previousLevel = level;
               int lastCcPos = 0;
               short[] stack = new short[127];
               int stackLast = 0;
               int overflowIsolateCount = 0;
               int overflowEmbeddingCount = 0;
               int validIsolateCount = 0;
               BracketData bracketData = new BracketData();
               this.bracketInit(bracketData);
               stack[0] = (short)level;
               this.flags = 0;

               for(i = 0; i < this.length; ++i) {
                  dirProp = this.dirProps[i];
                  switch (dirProp) {
                     case 7:
                        this.flags |= DirPropFlag((byte)7);
                        this.levels[i] = this.GetParaLevelAt(i);
                        if (i + 1 < this.length && (this.text[i] != '\r' || this.text[i + 1] != '\n')) {
                           overflowIsolateCount = 0;
                           overflowEmbeddingCount = 0;
                           validIsolateCount = 0;
                           stackLast = 0;
                           previousLevel = embeddingLevel = this.GetParaLevelAt(i + 1);
                           stack[0] = (short)embeddingLevel;
                           this.bracketProcessB(bracketData, (byte)embeddingLevel);
                        }
                        break;
                     case 8:
                     case 9:
                     case 10:
                     case 13:
                     case 17:
                     case 19:
                     default:
                        if (NoOverride((byte)embeddingLevel) != NoOverride((byte)previousLevel)) {
                           this.bracketProcessBoundary(bracketData, lastCcPos, (byte)previousLevel, (byte)embeddingLevel);
                           this.flags |= DirPropFlagMultiRuns;
                           if ((embeddingLevel & -128) != 0) {
                              this.flags |= DirPropFlagO((byte)embeddingLevel);
                           } else {
                              this.flags |= DirPropFlagE((byte)embeddingLevel);
                           }
                        }

                        previousLevel = embeddingLevel;
                        this.levels[i] = (byte)embeddingLevel;
                        this.bracketProcessChar(bracketData, i);
                        this.flags |= DirPropFlag(this.dirProps[i]);
                        break;
                     case 11:
                     case 12:
                     case 14:
                     case 15:
                        this.flags |= DirPropFlag((byte)18);
                        this.levels[i] = (byte)previousLevel;
                        if (dirProp != 11 && dirProp != 12) {
                           newLevel = (byte)(NoOverride((byte)embeddingLevel) + 1 | 1);
                        } else {
                           newLevel = (byte)(embeddingLevel + 2 & 126);
                        }

                        if (newLevel <= 125 && overflowIsolateCount == 0 && overflowEmbeddingCount == 0) {
                           lastCcPos = i;
                           embeddingLevel = newLevel;
                           if (dirProp == 12 || dirProp == 15) {
                              embeddingLevel = (byte)(newLevel | -128);
                           }

                           ++stackLast;
                           stack[stackLast] = (short)embeddingLevel;
                        } else if (overflowIsolateCount == 0) {
                           ++overflowEmbeddingCount;
                        }
                        break;
                     case 16:
                        this.flags |= DirPropFlag((byte)18);
                        this.levels[i] = (byte)previousLevel;
                        if (overflowIsolateCount <= 0) {
                           if (overflowEmbeddingCount > 0) {
                              --overflowEmbeddingCount;
                           } else if (stackLast > 0 && stack[stackLast] < 256) {
                              lastCcPos = i;
                              --stackLast;
                              embeddingLevel = (byte)stack[stackLast];
                           }
                        }
                        break;
                     case 18:
                        this.levels[i] = (byte)previousLevel;
                        this.flags |= DirPropFlag((byte)18);
                        break;
                     case 20:
                     case 21:
                        this.flags |= DirPropFlag((byte)10) | DirPropFlagLR((byte)embeddingLevel);
                        this.levels[i] = NoOverride((byte)embeddingLevel);
                        if (NoOverride((byte)embeddingLevel) != NoOverride((byte)previousLevel)) {
                           this.bracketProcessBoundary(bracketData, lastCcPos, (byte)previousLevel, (byte)embeddingLevel);
                           this.flags |= DirPropFlagMultiRuns;
                        }

                        previousLevel = embeddingLevel;
                        if (dirProp == 20) {
                           newLevel = (byte)(embeddingLevel + 2 & 126);
                        } else {
                           newLevel = (byte)(NoOverride((byte)embeddingLevel) + 1 | 1);
                        }

                        if (newLevel <= 125 && overflowIsolateCount == 0 && overflowEmbeddingCount == 0) {
                           this.flags |= DirPropFlag(dirProp);
                           lastCcPos = i;
                           ++validIsolateCount;
                           if (validIsolateCount > this.isolateCount) {
                              this.isolateCount = validIsolateCount;
                           }

                           embeddingLevel = newLevel;
                           ++stackLast;
                           stack[stackLast] = (short)(newLevel + 256);
                           this.bracketProcessLRI_RLI(bracketData, (byte)newLevel);
                           break;
                        }

                        this.dirProps[i] = 9;
                        ++overflowIsolateCount;
                        break;
                     case 22:
                        if (NoOverride((byte)embeddingLevel) != NoOverride((byte)previousLevel)) {
                           this.bracketProcessBoundary(bracketData, lastCcPos, (byte)previousLevel, (byte)embeddingLevel);
                           this.flags |= DirPropFlagMultiRuns;
                        }

                        if (overflowIsolateCount > 0) {
                           --overflowIsolateCount;
                           this.dirProps[i] = 9;
                        } else if (validIsolateCount > 0) {
                           this.flags |= DirPropFlag((byte)22);
                           lastCcPos = i;

                           for(overflowEmbeddingCount = 0; stack[stackLast] < 256; --stackLast) {
                           }

                           --stackLast;
                           --validIsolateCount;
                           this.bracketProcessPDI(bracketData);
                        } else {
                           this.dirProps[i] = 9;
                        }

                        embeddingLevel = (byte)(stack[stackLast] & -257);
                        this.flags |= DirPropFlag((byte)10) | DirPropFlagLR((byte)embeddingLevel);
                        previousLevel = embeddingLevel;
                        this.levels[i] = NoOverride((byte)embeddingLevel);
                  }
               }

               if ((this.flags & MASK_EMBEDDING) != 0) {
                  this.flags |= DirPropFlagLR(this.paraLevel);
               }

               if (this.orderParagraphsLTR && (this.flags & DirPropFlag((byte)7)) != 0) {
                  this.flags |= DirPropFlag((byte)0);
               }

               dirct = this.directionFromFlags();
               return dirct;
            }
         }
      }
   }

   private byte checkExplicitLevels() {
      int isolateCount = 0;
      this.flags = 0;
      this.isolateCount = 0;
      int currentParaIndex = 0;
      int currentParaLimit = this.paras_limit[0];
      byte currentParaLevel = this.paraLevel;

      for(int i = 0; i < this.length; ++i) {
         byte level = this.levels[i];
         byte dirProp = this.dirProps[i];
         if (dirProp != 20 && dirProp != 21) {
            if (dirProp == 22) {
               --isolateCount;
            } else if (dirProp == 7) {
               isolateCount = 0;
            }
         } else {
            ++isolateCount;
            if (isolateCount > this.isolateCount) {
               this.isolateCount = isolateCount;
            }
         }

         if (this.defaultParaLevel != 0 && i == currentParaLimit && currentParaIndex + 1 < this.paraCount) {
            ++currentParaIndex;
            currentParaLevel = this.paras_level[currentParaIndex];
            currentParaLimit = this.paras_limit[currentParaIndex];
         }

         int overrideFlag = level & -128;
         level = (byte)(level & 127);
         if (level < currentParaLevel || 125 < level) {
            if (level != 0) {
               throw new IllegalArgumentException("level " + level + " out of bounds at " + i);
            }

            if (dirProp != 7) {
               level = currentParaLevel;
               this.levels[i] = (byte)(currentParaLevel | overrideFlag);
            }
         }

         if (overrideFlag != 0) {
            this.flags |= DirPropFlagO(level);
         } else {
            this.flags |= DirPropFlagE(level) | DirPropFlag(dirProp);
         }
      }

      if ((this.flags & MASK_EMBEDDING) != 0) {
         this.flags |= DirPropFlagLR(this.paraLevel);
      }

      return this.directionFromFlags();
   }

   private static short GetStateProps(short cell) {
      return (short)(cell & 31);
   }

   private static short GetActionProps(short cell) {
      return (short)(cell >> 5);
   }

   private static short GetState(byte cell) {
      return (short)(cell & 15);
   }

   private static short GetAction(byte cell) {
      return (short)(cell >> 4);
   }

   private void addPoint(int pos, int flag) {
      Point point = new Point();
      int len = this.insertPoints.points.length;
      if (len == 0) {
         this.insertPoints.points = new Point[10];
         len = 10;
      }

      if (this.insertPoints.size >= len) {
         Point[] savePoints = this.insertPoints.points;
         this.insertPoints.points = new Point[len * 2];
         System.arraycopy(savePoints, 0, this.insertPoints.points, 0, len);
      }

      point.pos = pos;
      point.flag = flag;
      this.insertPoints.points[this.insertPoints.size] = point;
      ++this.insertPoints.size;
   }

   private void setLevelsOutsideIsolates(int start, int limit, byte level) {
      int isolateCount = 0;

      for(int k = start; k < limit; ++k) {
         byte dirProp = this.dirProps[k];
         if (dirProp == 22) {
            --isolateCount;
         }

         if (isolateCount == 0) {
            this.levels[k] = level;
         }

         if (dirProp == 20 || dirProp == 21) {
            ++isolateCount;
         }
      }

   }

   private void processPropertySeq(LevState levState, short _prop, int start, int limit) {
      byte[][] impTab = levState.impTab;
      short[] impAct = levState.impAct;
      int start0 = start;
      short oldStateSeq = levState.state;
      byte cell = impTab[oldStateSeq][_prop];
      levState.state = GetState(cell);
      short actionSeq = impAct[GetAction(cell)];
      byte addLevel = impTab[levState.state][7];
      byte level;
      int k;
      if (actionSeq != 0) {
         byte[] var10000;
         label163:
         switch (actionSeq) {
            case 1:
               levState.startON = start;
               break;
            case 2:
               start = levState.startON;
               break;
            case 3:
               level = (byte)(levState.runLevel + 1);
               this.setLevelsOutsideIsolates(levState.startON, start, level);
               break;
            case 4:
               level = (byte)(levState.runLevel + 2);
               this.setLevelsOutsideIsolates(levState.startON, start, level);
               break;
            case 5:
               if (levState.startL2EN >= 0) {
                  this.addPoint(levState.startL2EN, 1);
               }

               levState.startL2EN = -1;
               if (this.insertPoints.points.length != 0 && this.insertPoints.size > this.insertPoints.confirmed) {
                  for(k = levState.lastStrongRTL + 1; k < start0; ++k) {
                     this.levels[k] = (byte)(this.levels[k] - 2 & -2);
                  }

                  this.insertPoints.confirmed = this.insertPoints.size;
                  levState.lastStrongRTL = -1;
                  if (_prop == 5) {
                     this.addPoint(start0, 1);
                     this.insertPoints.confirmed = this.insertPoints.size;
                  }
               } else {
                  levState.lastStrongRTL = -1;
                  level = impTab[oldStateSeq][7];
                  if ((level & 1) != 0 && levState.startON > 0) {
                     start = levState.startON;
                  }

                  if (_prop == 5) {
                     this.addPoint(start, 1);
                     this.insertPoints.confirmed = this.insertPoints.size;
                  }
               }
               break;
            case 6:
               if (this.insertPoints.points.length > 0) {
                  this.insertPoints.size = this.insertPoints.confirmed;
               }

               levState.startON = -1;
               levState.startL2EN = -1;
               levState.lastStrongRTL = limit - 1;
               break;
            case 7:
               if (_prop == 3 && this.dirProps[start] == 5 && this.reorderingMode != 6) {
                  if (levState.startL2EN == -1) {
                     levState.lastStrongRTL = limit - 1;
                  } else {
                     if (levState.startL2EN >= 0) {
                        this.addPoint(levState.startL2EN, 1);
                        levState.startL2EN = -2;
                     }

                     this.addPoint(start, 1);
                  }
               } else if (levState.startL2EN == -1) {
                  levState.startL2EN = start;
               }
               break;
            case 8:
               levState.lastStrongRTL = limit - 1;
               levState.startON = -1;
               break;
            case 9:
               for(k = start - 1; k >= 0 && (this.levels[k] & 1) == 0; --k) {
               }

               if (k >= 0) {
                  this.addPoint(k, 4);
                  this.insertPoints.confirmed = this.insertPoints.size;
               }

               levState.startON = start;
               break;
            case 10:
               this.addPoint(start, 1);
               this.addPoint(start, 2);
               break;
            case 11:
               this.insertPoints.size = this.insertPoints.confirmed;
               if (_prop == 5) {
                  this.addPoint(start, 4);
                  this.insertPoints.confirmed = this.insertPoints.size;
               }
               break;
            case 12:
               level = (byte)(levState.runLevel + addLevel);

               for(k = levState.startON; k < start0; ++k) {
                  if (this.levels[k] < level) {
                     this.levels[k] = level;
                  }
               }

               this.insertPoints.confirmed = this.insertPoints.size;
               levState.startON = start0;
               break;
            case 13:
               level = levState.runLevel;
               k = start - 1;

               while(true) {
                  if (k < levState.startON) {
                     break label163;
                  }

                  if (this.levels[k] == level + 3) {
                     while(this.levels[k] == level + 3) {
                        var10000 = this.levels;
                        int var10001 = k--;
                        var10000[var10001] = (byte)(var10000[var10001] - 2);
                     }

                     while(this.levels[k] == level) {
                        --k;
                     }
                  }

                  if (this.levels[k] == level + 2) {
                     this.levels[k] = level;
                  } else {
                     this.levels[k] = (byte)(level + 1);
                  }

                  --k;
               }
            case 14:
               level = (byte)(levState.runLevel + 1);
               k = start - 1;

               while(true) {
                  if (k < levState.startON) {
                     break label163;
                  }

                  if (this.levels[k] > level) {
                     var10000 = this.levels;
                     var10000[k] = (byte)(var10000[k] - 2);
                  }

                  --k;
               }
            default:
               throw new IllegalStateException("Internal ICU error in processPropertySeq");
         }
      }

      if (addLevel != 0 || start < start0) {
         level = (byte)(levState.runLevel + addLevel);
         if (start >= levState.runStart) {
            for(k = start; k < limit; ++k) {
               this.levels[k] = level;
            }
         } else {
            this.setLevelsOutsideIsolates(start, limit, level);
         }
      }

   }

   private byte lastL_R_AL() {
      int i = this.prologue.length();

      byte dirProp;
      do {
         if (i <= 0) {
            return 4;
         }

         int uchar = this.prologue.codePointBefore(i);
         i -= Character.charCount(uchar);
         dirProp = (byte)this.getCustomizedClass(uchar);
         if (dirProp == 0) {
            return 0;
         }

         if (dirProp == 1 || dirProp == 13) {
            return 1;
         }
      } while(dirProp != 7);

      return 4;
   }

   private byte firstL_R_AL_EN_AN() {
      int i = 0;

      byte dirProp;
      do {
         if (i >= this.epilogue.length()) {
            return 4;
         }

         int uchar = this.epilogue.codePointAt(i);
         i += Character.charCount(uchar);
         dirProp = (byte)this.getCustomizedClass(uchar);
         if (dirProp == 0) {
            return 0;
         }

         if (dirProp == 1 || dirProp == 13) {
            return 1;
         }

         if (dirProp == 2) {
            return 2;
         }
      } while(dirProp != 5);

      return 3;
   }

   private void resolveImplicitLevels(int start, int limit, short sor, short eor) {
      LevState levState = new LevState();
      short nextStrongProp = 1;
      int nextStrongPos = -1;
      boolean inverseRTL = start < this.lastArabicPos && (this.GetParaLevelAt(start) & 1) > 0 && (this.reorderingMode == 5 || this.reorderingMode == 6);
      levState.startL2EN = -1;
      levState.lastStrongRTL = -1;
      levState.runStart = start;
      levState.runLevel = this.levels[start];
      levState.impTab = this.impTabPair.imptab[levState.runLevel & 1];
      levState.impAct = this.impTabPair.impact[levState.runLevel & 1];
      int k;
      if (start == 0 && this.prologue != null) {
         k = this.lastL_R_AL();
         if (k != 4) {
            sor = (short)k;
         }
      }

      int start1;
      short stateImp;
      if (this.dirProps[start] == 22) {
         levState.startON = this.isolates[this.isolateCount].startON;
         start1 = this.isolates[this.isolateCount].start1;
         stateImp = this.isolates[this.isolateCount].stateImp;
         levState.state = this.isolates[this.isolateCount].state;
         --this.isolateCount;
      } else {
         levState.startON = -1;
         start1 = start;
         if (this.dirProps[start] == 17) {
            stateImp = (short)(1 + sor);
         } else {
            stateImp = 0;
         }

         levState.state = 0;
         this.processPropertySeq(levState, sor, start, start);
      }

      int start2 = start;

      int i;
      byte dirProp;
      byte prop;
      for(i = start; i <= limit; ++i) {
         short gprop;
         if (i >= limit) {
            for(k = limit - 1; k > start && (DirPropFlag(this.dirProps[k]) & MASK_BN_EXPLICIT) != 0; --k) {
            }

            dirProp = this.dirProps[k];
            if (dirProp == 20 || dirProp == 21) {
               break;
            }

            gprop = eor;
         } else {
            prop = this.dirProps[i];
            if (prop == 7) {
               this.isolateCount = -1;
            }

            if (inverseRTL) {
               if (prop == 13) {
                  prop = 1;
               } else if (prop == 2) {
                  if (nextStrongPos <= i) {
                     nextStrongProp = 1;
                     nextStrongPos = limit;

                     for(int j = i + 1; j < limit; ++j) {
                        byte prop1 = this.dirProps[j];
                        if (prop1 == 0 || prop1 == 1 || prop1 == 13) {
                           nextStrongProp = (short)prop1;
                           nextStrongPos = j;
                           break;
                        }
                     }
                  }

                  if (nextStrongProp == 13) {
                     prop = 5;
                  }
               }
            }

            gprop = groupProp[prop];
         }

         short oldStateImp = stateImp;
         short cell = impTabProps[stateImp][gprop];
         stateImp = GetStateProps(cell);
         short actionImp = GetActionProps(cell);
         if (i == limit && actionImp == 0) {
            actionImp = 1;
         }

         if (actionImp != 0) {
            short resProp = impTabProps[oldStateImp][15];
            switch (actionImp) {
               case 1:
                  this.processPropertySeq(levState, resProp, start1, i);
                  start1 = i;
                  break;
               case 2:
                  start2 = i;
                  break;
               case 3:
                  this.processPropertySeq(levState, resProp, start1, start2);
                  this.processPropertySeq(levState, (short)4, start2, i);
                  start1 = i;
                  break;
               case 4:
                  this.processPropertySeq(levState, resProp, start1, start2);
                  start1 = start2;
                  start2 = i;
                  break;
               default:
                  throw new IllegalStateException("Internal ICU error in resolveImplicitLevels");
            }
         }
      }

      if (limit == this.length && this.epilogue != null) {
         prop = this.firstL_R_AL_EN_AN();
         if (prop != 4) {
            eor = (short)prop;
         }
      }

      for(i = limit - 1; i > start && (DirPropFlag(this.dirProps[i]) & MASK_BN_EXPLICIT) != 0; --i) {
      }

      dirProp = this.dirProps[i];
      if ((dirProp == 20 || dirProp == 21) && limit < this.length) {
         ++this.isolateCount;
         if (this.isolates[this.isolateCount] == null) {
            this.isolates[this.isolateCount] = new Isolate();
         }

         this.isolates[this.isolateCount].stateImp = stateImp;
         this.isolates[this.isolateCount].state = levState.state;
         this.isolates[this.isolateCount].start1 = start1;
         this.isolates[this.isolateCount].startON = levState.startON;
      } else {
         this.processPropertySeq(levState, eor, limit, limit);
      }

   }

   private void adjustWSLevels() {
      if ((this.flags & MASK_WS) != 0) {
         int i = this.trailingWSStart;

         while(true) {
            while(i > 0) {
               int flag;
               label35:
               while(true) {
                  while(true) {
                     if (i <= 0) {
                        break label35;
                     }

                     --i;
                     if (((flag = DirPropFlag(this.dirProps[i])) & MASK_WS) == 0) {
                        break label35;
                     }

                     if (this.orderParagraphsLTR && (flag & DirPropFlag((byte)7)) != 0) {
                        this.levels[i] = 0;
                     } else {
                        this.levels[i] = this.GetParaLevelAt(i);
                     }
                  }
               }

               while(i > 0) {
                  --i;
                  flag = DirPropFlag(this.dirProps[i]);
                  if ((flag & MASK_BN_EXPLICIT) != 0) {
                     this.levels[i] = this.levels[i + 1];
                  } else {
                     if (this.orderParagraphsLTR && (flag & DirPropFlag((byte)7)) != 0) {
                        this.levels[i] = 0;
                        break;
                     }

                     if ((flag & MASK_B_S) != 0) {
                        this.levels[i] = this.GetParaLevelAt(i);
                        break;
                     }
                  }
               }
            }

            return;
         }
      }
   }

   public void setContext(String prologue, String epilogue) {
      this.prologue = prologue != null && prologue.length() > 0 ? prologue : null;
      this.epilogue = epilogue != null && epilogue.length() > 0 ? epilogue : null;
   }

   private void setParaSuccess() {
      this.prologue = null;
      this.epilogue = null;
      this.paraBidi = this;
   }

   int Bidi_Min(int x, int y) {
      return x < y ? x : y;
   }

   int Bidi_Abs(int x) {
      return x >= 0 ? x : -x;
   }

   void setParaRunsOnly(char[] parmText, byte parmParaLevel) {
      this.reorderingMode = 0;
      int parmLength = parmText.length;
      if (parmLength == 0) {
         this.setPara((char[])parmText, parmParaLevel, (byte[])null);
         this.reorderingMode = 3;
      } else {
         int saveOptions = this.reorderingOptions;
         if ((saveOptions & 1) > 0) {
            this.reorderingOptions &= -2;
            this.reorderingOptions |= 2;
         }

         parmParaLevel = (byte)(parmParaLevel & 1);
         this.setPara((char[])parmText, parmParaLevel, (byte[])null);
         byte[] saveLevels = new byte[this.length];
         System.arraycopy(this.getLevels(), 0, saveLevels, 0, this.length);
         int saveTrailingWSStart = this.trailingWSStart;
         String visualText = this.writeReordered(2);
         int[] visualMap = this.getVisualMap();
         this.reorderingOptions = saveOptions;
         int saveLength = this.length;
         byte saveDirection = this.direction;
         this.reorderingMode = 5;
         parmParaLevel = (byte)(parmParaLevel ^ 1);
         this.setPara((String)visualText, parmParaLevel, (byte[])null);
         BidiLine.getRuns(this);
         int addedRuns = 0;
         int oldRunCount = this.runCount;
         int visualStart = 0;

         int i;
         int runLength;
         int logicalStart;
         int j;
         int index;
         int index1;
         for(i = 0; i < oldRunCount; visualStart += runLength) {
            runLength = this.runs[i].limit - visualStart;
            if (runLength >= 2) {
               logicalStart = this.runs[i].start;

               for(j = logicalStart + 1; j < logicalStart + runLength; ++j) {
                  index = visualMap[j];
                  index1 = visualMap[j - 1];
                  if (this.Bidi_Abs(index - index1) != 1 || saveLevels[index] != saveLevels[index1]) {
                     ++addedRuns;
                  }
               }
            }

            ++i;
         }

         if (addedRuns > 0) {
            this.getRunsMemory(oldRunCount + addedRuns);
            if (this.runCount == 1) {
               this.runsMemory[0] = this.runs[0];
            } else {
               System.arraycopy(this.runs, 0, this.runsMemory, 0, this.runCount);
            }

            this.runs = this.runsMemory;
            this.runCount += addedRuns;

            for(i = oldRunCount; i < this.runCount; ++i) {
               if (this.runs[i] == null) {
                  this.runs[i] = new BidiRun(0, 0, (byte)0);
               }
            }
         }

         for(i = oldRunCount - 1; i >= 0; --i) {
            int newI = i + addedRuns;
            runLength = i == 0 ? this.runs[0].limit : this.runs[i].limit - this.runs[i - 1].limit;
            logicalStart = this.runs[i].start;
            int indexOddBit = this.runs[i].level & 1;
            int logicalPos;
            if (runLength < 2) {
               if (addedRuns > 0) {
                  this.runs[newI].copyFrom(this.runs[i]);
               }

               logicalPos = visualMap[logicalStart];
               this.runs[newI].start = logicalPos;
               this.runs[newI].level = (byte)(saveLevels[logicalPos] ^ indexOddBit);
            } else {
               int start;
               int limit;
               byte step;
               if (indexOddBit > 0) {
                  start = logicalStart;
                  limit = logicalStart + runLength - 1;
                  step = 1;
               } else {
                  start = logicalStart + runLength - 1;
                  limit = logicalStart;
                  step = -1;
               }

               for(j = start; j != limit; j += step) {
                  index = visualMap[j];
                  index1 = visualMap[j + step];
                  if (this.Bidi_Abs(index - index1) != 1 || saveLevels[index] != saveLevels[index1]) {
                     logicalPos = this.Bidi_Min(visualMap[start], index);
                     this.runs[newI].start = logicalPos;
                     this.runs[newI].level = (byte)(saveLevels[logicalPos] ^ indexOddBit);
                     this.runs[newI].limit = this.runs[i].limit;
                     BidiRun var10000 = this.runs[i];
                     var10000.limit -= this.Bidi_Abs(j - start) + 1;
                     int insertRemove = this.runs[i].insertRemove & 10;
                     this.runs[newI].insertRemove = insertRemove;
                     var10000 = this.runs[i];
                     var10000.insertRemove &= ~insertRemove;
                     start = j + step;
                     --addedRuns;
                     --newI;
                  }
               }

               if (addedRuns > 0) {
                  this.runs[newI].copyFrom(this.runs[i]);
               }

               logicalPos = this.Bidi_Min(visualMap[start], visualMap[limit]);
               this.runs[newI].start = logicalPos;
               this.runs[newI].level = (byte)(saveLevels[logicalPos] ^ indexOddBit);
            }
         }

         this.paraLevel = (byte)(this.paraLevel ^ 1);
         this.text = parmText;
         this.length = saveLength;
         this.originalLength = parmLength;
         this.direction = saveDirection;
         this.levels = saveLevels;
         this.trailingWSStart = saveTrailingWSStart;
         if (this.runCount > 1) {
            this.direction = 2;
         }

         this.reorderingMode = 3;
      }
   }

   public void setPara(String text, byte paraLevel, byte[] embeddingLevels) {
      if (text == null) {
         this.setPara(new char[0], paraLevel, embeddingLevels);
      } else {
         this.setPara(text.toCharArray(), paraLevel, embeddingLevels);
      }

   }

   public void setPara(char[] chars, byte paraLevel, byte[] embeddingLevels) {
      if (paraLevel < 126) {
         this.verifyRange(paraLevel, 0, 126);
      }

      if (chars == null) {
         chars = new char[0];
      }

      if (this.reorderingMode == 3) {
         this.setParaRunsOnly(chars, paraLevel);
      } else {
         this.paraBidi = null;
         this.text = chars;
         this.length = this.originalLength = this.resultLength = this.text.length;
         this.paraLevel = paraLevel;
         this.direction = (byte)(paraLevel & 1);
         this.paraCount = 1;
         this.dirProps = new byte[0];
         this.levels = new byte[0];
         this.runs = new BidiRun[0];
         this.isGoodLogicalToVisualRunsMap = false;
         this.insertPoints.size = 0;
         this.insertPoints.confirmed = 0;
         this.defaultParaLevel = IsDefaultLevel(paraLevel) ? paraLevel : 0;
         if (this.length == 0) {
            if (IsDefaultLevel(paraLevel)) {
               this.paraLevel = (byte)(this.paraLevel & 1);
               this.defaultParaLevel = 0;
            }

            this.flags = DirPropFlagLR(paraLevel);
            this.runCount = 0;
            this.paraCount = 0;
            this.setParaSuccess();
         } else {
            this.runCount = -1;
            this.getDirPropsMemory(this.length);
            this.dirProps = this.dirPropsMemory;
            this.getDirProps();
            this.trailingWSStart = this.length;
            if (embeddingLevels == null) {
               this.getLevelsMemory(this.length);
               this.levels = this.levelsMemory;
               this.direction = this.resolveExplicitLevels();
            } else {
               this.levels = embeddingLevels;
               this.direction = this.checkExplicitLevels();
            }

            if (this.isolateCount > 0 && (this.isolates == null || this.isolates.length < this.isolateCount)) {
               this.isolates = new Isolate[this.isolateCount + 3];
            }

            this.isolateCount = -1;
            int limit;
            byte level;
            byte nextLevel;
            int eor;
            int start;
            int sor;
            switch (this.direction) {
               case 0:
                  this.trailingWSStart = 0;
                  break;
               case 1:
                  this.trailingWSStart = 0;
                  break;
               default:
                  switch (this.reorderingMode) {
                     case 0:
                        this.impTabPair = impTab_DEFAULT;
                        break;
                     case 1:
                        this.impTabPair = impTab_NUMBERS_SPECIAL;
                        break;
                     case 2:
                        this.impTabPair = impTab_GROUP_NUMBERS_WITH_R;
                        break;
                     case 3:
                        throw new InternalError("Internal ICU error in setPara");
                     case 4:
                        this.impTabPair = impTab_INVERSE_NUMBERS_AS_L;
                        break;
                     case 5:
                        if ((this.reorderingOptions & 1) != 0) {
                           this.impTabPair = impTab_INVERSE_LIKE_DIRECT_WITH_MARKS;
                        } else {
                           this.impTabPair = impTab_INVERSE_LIKE_DIRECT;
                        }
                        break;
                     case 6:
                        if ((this.reorderingOptions & 1) != 0) {
                           this.impTabPair = impTab_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS;
                        } else {
                           this.impTabPair = impTab_INVERSE_FOR_NUMBERS_SPECIAL;
                        }
                  }

                  if (embeddingLevels == null && this.paraCount <= 1 && (this.flags & DirPropFlagMultiRuns) == 0) {
                     this.resolveImplicitLevels(0, this.length, (short)GetLRFromLevel(this.GetParaLevelAt(0)), (short)GetLRFromLevel(this.GetParaLevelAt(this.length - 1)));
                  } else {
                     limit = 0;
                     level = this.GetParaLevelAt(0);
                     nextLevel = this.levels[0];
                     if (level < nextLevel) {
                        eor = (short)GetLRFromLevel(nextLevel);
                     } else {
                        eor = (short)GetLRFromLevel(level);
                     }

                     do {
                        start = limit;
                        level = nextLevel;
                        if (limit > 0 && this.dirProps[limit - 1] == 7) {
                           sor = (short)GetLRFromLevel(this.GetParaLevelAt(limit));
                        } else {
                           sor = eor;
                        }

                        do {
                           ++limit;
                        } while(limit < this.length && (this.levels[limit] == level || (DirPropFlag(this.dirProps[limit]) & MASK_BN_EXPLICIT) != 0));

                        if (limit < this.length) {
                           nextLevel = this.levels[limit];
                        } else {
                           nextLevel = this.GetParaLevelAt(this.length - 1);
                        }

                        if (NoOverride(level) < NoOverride(nextLevel)) {
                           eor = (short)GetLRFromLevel(nextLevel);
                        } else {
                           eor = (short)GetLRFromLevel(level);
                        }

                        if ((level & -128) == 0) {
                           this.resolveImplicitLevels(start, limit, (short)sor, (short)eor);
                        } else {
                           do {
                              byte[] var10000 = this.levels;
                              int var10001 = start++;
                              var10000[var10001] = (byte)(var10000[var10001] & 127);
                           } while(start < limit);
                        }
                     } while(limit < this.length);
                  }

                  this.adjustWSLevels();
            }

            if (this.defaultParaLevel > 0 && (this.reorderingOptions & 1) != 0 && (this.reorderingMode == 5 || this.reorderingMode == 6)) {
               for(sor = 0; sor < this.paraCount; ++sor) {
                  limit = this.paras_limit[sor] - 1;
                  level = this.paras_level[sor];
                  if (level != 0) {
                     start = sor == 0 ? 0 : this.paras_limit[sor - 1];

                     for(eor = limit; eor >= start; --eor) {
                        nextLevel = this.dirProps[eor];
                        if (nextLevel == 0) {
                           if (eor < limit) {
                              while(this.dirProps[limit] == 7) {
                                 --limit;
                              }
                           }

                           this.addPoint(limit, 4);
                           break;
                        }

                        if ((DirPropFlag(nextLevel) & MASK_R_AL) != 0) {
                           break;
                        }
                     }
                  }
               }
            }

            if ((this.reorderingOptions & 2) != 0) {
               this.resultLength -= this.controlCount;
            } else {
               this.resultLength += this.insertPoints.size;
            }

            this.setParaSuccess();
         }
      }
   }

   public void setPara(AttributedCharacterIterator paragraph) {
      Boolean runDirection = (Boolean)paragraph.getAttribute(TextAttribute.RUN_DIRECTION);
      int paraLvl;
      if (runDirection == null) {
         paraLvl = 126;
      } else {
         paraLvl = runDirection.equals(TextAttribute.RUN_DIRECTION_LTR) ? 0 : 1;
      }

      byte[] lvls = null;
      int len = paragraph.getEndIndex() - paragraph.getBeginIndex();
      byte[] embeddingLevels = new byte[len];
      char[] txt = new char[len];
      int i = 0;

      for(char ch = paragraph.first(); ch != '\uffff'; ++i) {
         txt[i] = ch;
         Integer embedding = (Integer)paragraph.getAttribute(TextAttribute.BIDI_EMBEDDING);
         if (embedding != null) {
            byte level = embedding.byteValue();
            if (level != 0) {
               if (level < 0) {
                  lvls = embeddingLevels;
                  embeddingLevels[i] = (byte)(0 - level | -128);
               } else {
                  lvls = embeddingLevels;
                  embeddingLevels[i] = level;
               }
            }
         }

         ch = paragraph.next();
      }

      NumericShaper shaper = (NumericShaper)paragraph.getAttribute(TextAttribute.NUMERIC_SHAPING);
      if (shaper != null) {
         shaper.shape(txt, 0, len);
      }

      this.setPara((char[])txt, (byte)paraLvl, lvls);
   }

   public void orderParagraphsLTR(boolean ordarParaLTR) {
      this.orderParagraphsLTR = ordarParaLTR;
   }

   public boolean isOrderParagraphsLTR() {
      return this.orderParagraphsLTR;
   }

   public byte getDirection() {
      this.verifyValidParaOrLine();
      return this.direction;
   }

   public String getTextAsString() {
      this.verifyValidParaOrLine();
      return new String(this.text);
   }

   public char[] getText() {
      this.verifyValidParaOrLine();
      return this.text;
   }

   public int getLength() {
      this.verifyValidParaOrLine();
      return this.originalLength;
   }

   public int getProcessedLength() {
      this.verifyValidParaOrLine();
      return this.length;
   }

   public int getResultLength() {
      this.verifyValidParaOrLine();
      return this.resultLength;
   }

   public byte getParaLevel() {
      this.verifyValidParaOrLine();
      return this.paraLevel;
   }

   public int countParagraphs() {
      this.verifyValidParaOrLine();
      return this.paraCount;
   }

   public BidiRun getParagraphByIndex(int paraIndex) {
      this.verifyValidParaOrLine();
      this.verifyRange(paraIndex, 0, this.paraCount);
      Bidi bidi = this.paraBidi;
      int paraStart;
      if (paraIndex == 0) {
         paraStart = 0;
      } else {
         paraStart = bidi.paras_limit[paraIndex - 1];
      }

      BidiRun bidiRun = new BidiRun();
      bidiRun.start = paraStart;
      bidiRun.limit = bidi.paras_limit[paraIndex];
      bidiRun.level = this.GetParaLevelAt(paraStart);
      return bidiRun;
   }

   public BidiRun getParagraph(int charIndex) {
      this.verifyValidParaOrLine();
      Bidi bidi = this.paraBidi;
      this.verifyRange(charIndex, 0, bidi.length);

      int paraIndex;
      for(paraIndex = 0; charIndex >= bidi.paras_limit[paraIndex]; ++paraIndex) {
      }

      return this.getParagraphByIndex(paraIndex);
   }

   public int getParagraphIndex(int charIndex) {
      this.verifyValidParaOrLine();
      Bidi bidi = this.paraBidi;
      this.verifyRange(charIndex, 0, bidi.length);

      int paraIndex;
      for(paraIndex = 0; charIndex >= bidi.paras_limit[paraIndex]; ++paraIndex) {
      }

      return paraIndex;
   }

   public void setCustomClassifier(BidiClassifier classifier) {
      this.customClassifier = classifier;
   }

   public BidiClassifier getCustomClassifier() {
      return this.customClassifier;
   }

   public int getCustomizedClass(int c) {
      int dir;
      if (this.customClassifier == null || (dir = this.customClassifier.classify(c)) == 23) {
         dir = this.bdp.getClass(c);
      }

      if (dir >= 23) {
         dir = 10;
      }

      return dir;
   }

   public Bidi setLine(int start, int limit) {
      this.verifyValidPara();
      this.verifyRange(start, 0, limit);
      this.verifyRange(limit, 0, this.length + 1);
      if (this.getParagraphIndex(start) != this.getParagraphIndex(limit - 1)) {
         throw new IllegalArgumentException();
      } else {
         return BidiLine.setLine(this, start, limit);
      }
   }

   public byte getLevelAt(int charIndex) {
      this.verifyValidParaOrLine();
      this.verifyRange(charIndex, 0, this.length);
      return BidiLine.getLevelAt(this, charIndex);
   }

   public byte[] getLevels() {
      this.verifyValidParaOrLine();
      return this.length <= 0 ? new byte[0] : BidiLine.getLevels(this);
   }

   public BidiRun getLogicalRun(int logicalPosition) {
      this.verifyValidParaOrLine();
      this.verifyRange(logicalPosition, 0, this.length);
      return BidiLine.getLogicalRun(this, logicalPosition);
   }

   public int countRuns() {
      this.verifyValidParaOrLine();
      BidiLine.getRuns(this);
      return this.runCount;
   }

   public BidiRun getVisualRun(int runIndex) {
      this.verifyValidParaOrLine();
      BidiLine.getRuns(this);
      this.verifyRange(runIndex, 0, this.runCount);
      return BidiLine.getVisualRun(this, runIndex);
   }

   public int getVisualIndex(int logicalIndex) {
      this.verifyValidParaOrLine();
      this.verifyRange(logicalIndex, 0, this.length);
      return BidiLine.getVisualIndex(this, logicalIndex);
   }

   public int getLogicalIndex(int visualIndex) {
      this.verifyValidParaOrLine();
      this.verifyRange(visualIndex, 0, this.resultLength);
      if (this.insertPoints.size == 0 && this.controlCount == 0) {
         if (this.direction == 0) {
            return visualIndex;
         }

         if (this.direction == 1) {
            return this.length - visualIndex - 1;
         }
      }

      BidiLine.getRuns(this);
      return BidiLine.getLogicalIndex(this, visualIndex);
   }

   public int[] getLogicalMap() {
      this.countRuns();
      return this.length <= 0 ? new int[0] : BidiLine.getLogicalMap(this);
   }

   public int[] getVisualMap() {
      this.countRuns();
      return this.resultLength <= 0 ? new int[0] : BidiLine.getVisualMap(this);
   }

   public static int[] reorderLogical(byte[] levels) {
      return BidiLine.reorderLogical(levels);
   }

   public static int[] reorderVisual(byte[] levels) {
      return BidiLine.reorderVisual(levels);
   }

   public static int[] invertMap(int[] srcMap) {
      return srcMap == null ? null : BidiLine.invertMap(srcMap);
   }

   public Bidi(String paragraph, int flags) {
      this(paragraph.toCharArray(), 0, (byte[])null, 0, paragraph.length(), flags);
   }

   public Bidi(AttributedCharacterIterator paragraph) {
      this();
      this.setPara(paragraph);
   }

   public Bidi(char[] text, int textStart, byte[] embeddings, int embStart, int paragraphLength, int flags) {
      this();
      byte paraLvl;
      switch (flags) {
         case 0:
         default:
            paraLvl = 0;
            break;
         case 1:
            paraLvl = 1;
            break;
         case 126:
            paraLvl = 126;
            break;
         case 127:
            paraLvl = 127;
      }

      byte[] paraEmbeddings;
      if (embeddings == null) {
         paraEmbeddings = null;
      } else {
         paraEmbeddings = new byte[paragraphLength];

         for(int i = 0; i < paragraphLength; ++i) {
            byte lev = embeddings[i + embStart];
            if (lev < 0) {
               lev = (byte)(-lev | -128);
            }

            paraEmbeddings[i] = lev;
         }
      }

      if (textStart == 0 && paragraphLength == text.length) {
         this.setPara(text, paraLvl, paraEmbeddings);
      } else {
         char[] paraText = new char[paragraphLength];
         System.arraycopy(text, textStart, paraText, 0, paragraphLength);
         this.setPara(paraText, paraLvl, paraEmbeddings);
      }

   }

   public Bidi createLineBidi(int lineStart, int lineLimit) {
      return this.setLine(lineStart, lineLimit);
   }

   public boolean isMixed() {
      return !this.isLeftToRight() && !this.isRightToLeft();
   }

   public boolean isLeftToRight() {
      return this.getDirection() == 0 && (this.paraLevel & 1) == 0;
   }

   public boolean isRightToLeft() {
      return this.getDirection() == 1 && (this.paraLevel & 1) == 1;
   }

   public boolean baseIsLeftToRight() {
      return this.getParaLevel() == 0;
   }

   public int getBaseLevel() {
      return this.getParaLevel();
   }

   public int getRunCount() {
      return this.countRuns();
   }

   void getLogicalToVisualRunsMap() {
      if (!this.isGoodLogicalToVisualRunsMap) {
         int count = this.countRuns();
         if (this.logicalToVisualRunsMap == null || this.logicalToVisualRunsMap.length < count) {
            this.logicalToVisualRunsMap = new int[count];
         }

         long[] keys = new long[count];

         int i;
         for(i = 0; i < count; ++i) {
            keys[i] = ((long)this.runs[i].start << 32) + (long)i;
         }

         Arrays.sort(keys);

         for(i = 0; i < count; ++i) {
            this.logicalToVisualRunsMap[i] = (int)(keys[i] & -1L);
         }

         this.isGoodLogicalToVisualRunsMap = true;
      }
   }

   public int getRunLevel(int run) {
      this.verifyValidParaOrLine();
      BidiLine.getRuns(this);
      this.verifyRange(run, 0, this.runCount);
      this.getLogicalToVisualRunsMap();
      return this.runs[this.logicalToVisualRunsMap[run]].level;
   }

   public int getRunStart(int run) {
      this.verifyValidParaOrLine();
      BidiLine.getRuns(this);
      this.verifyRange(run, 0, this.runCount);
      this.getLogicalToVisualRunsMap();
      return this.runs[this.logicalToVisualRunsMap[run]].start;
   }

   public int getRunLimit(int run) {
      this.verifyValidParaOrLine();
      BidiLine.getRuns(this);
      this.verifyRange(run, 0, this.runCount);
      this.getLogicalToVisualRunsMap();
      int idx = this.logicalToVisualRunsMap[run];
      int len = idx == 0 ? this.runs[idx].limit : this.runs[idx].limit - this.runs[idx - 1].limit;
      return this.runs[idx].start + len;
   }

   public static boolean requiresBidi(char[] text, int start, int limit) {
      int RTLMask = '\ue022';

      for(int i = start; i < limit; ++i) {
         if ((1 << UCharacter.getDirection(text[i]) & '\ue022') != 0) {
            return true;
         }
      }

      return false;
   }

   public static void reorderVisually(byte[] levels, int levelStart, Object[] objects, int objectStart, int count) {
      byte[] reorderLevels = new byte[count];
      System.arraycopy(levels, levelStart, reorderLevels, 0, count);
      int[] indexMap = reorderVisual(reorderLevels);
      Object[] temp = new Object[count];
      System.arraycopy(objects, objectStart, temp, 0, count);

      for(int i = 0; i < count; ++i) {
         objects[objectStart + i] = temp[indexMap[i]];
      }

   }

   public String writeReordered(int options) {
      this.verifyValidParaOrLine();
      return this.length == 0 ? "" : BidiWriter.writeReordered(this, options);
   }

   public static String writeReverse(String src, int options) {
      if (src == null) {
         throw new IllegalArgumentException();
      } else {
         return src.length() > 0 ? BidiWriter.writeReverse(src, options) : "";
      }
   }

   static {
      MASK_BN_EXPLICIT = DirPropFlag((byte)18) | MASK_EXPLICIT;
      MASK_ISO = DirPropFlag((byte)20) | DirPropFlag((byte)21) | DirPropFlag((byte)19) | DirPropFlag((byte)22);
      MASK_B_S = DirPropFlag((byte)7) | DirPropFlag((byte)8);
      MASK_WS = MASK_B_S | DirPropFlag((byte)9) | MASK_BN_EXPLICIT | MASK_ISO;
      MASK_POSSIBLE_N = DirPropFlag((byte)10) | DirPropFlag((byte)6) | DirPropFlag((byte)3) | DirPropFlag((byte)4) | MASK_WS;
      MASK_EMBEDDING = DirPropFlag((byte)17) | MASK_POSSIBLE_N;
      groupProp = new short[]{0, 1, 2, 7, 8, 3, 9, 6, 5, 4, 4, 10, 10, 12, 10, 10, 10, 11, 10, 4, 4, 4, 4, 13, 14};
      impTabProps = new short[][]{{1, 2, 4, 5, 7, 15, 17, 7, 9, 7, 0, 7, 3, 18, 21, 4}, {1, 34, 36, 37, 39, 47, 49, 39, 41, 39, 1, 1, 35, 50, 53, 0}, {33, 2, 36, 37, 39, 47, 49, 39, 41, 39, 2, 2, 35, 50, 53, 1}, {33, 34, 38, 38, 40, 48, 49, 40, 40, 40, 3, 3, 3, 50, 53, 1}, {33, 34, 4, 37, 39, 47, 49, 74, 11, 74, 4, 4, 35, 18, 21, 2}, {33, 34, 36, 5, 39, 47, 49, 39, 41, 76, 5, 5, 35, 50, 53, 3}, {33, 34, 6, 6, 40, 48, 49, 40, 40, 77, 6, 6, 35, 18, 21, 3}, {33, 34, 36, 37, 7, 47, 49, 7, 78, 7, 7, 7, 35, 50, 53, 4}, {33, 34, 38, 38, 8, 48, 49, 8, 8, 8, 8, 8, 35, 50, 53, 4}, {33, 34, 4, 37, 7, 47, 49, 7, 9, 7, 9, 9, 35, 18, 21, 4}, {97, 98, 4, 101, 135, 111, 113, 135, 142, 135, 10, 135, 99, 18, 21, 2}, {33, 34, 4, 37, 39, 47, 49, 39, 11, 39, 11, 11, 35, 18, 21, 2}, {97, 98, 100, 5, 135, 111, 113, 135, 142, 135, 12, 135, 99, 114, 117, 3}, {97, 98, 6, 6, 136, 112, 113, 136, 136, 136, 13, 136, 99, 18, 21, 3}, {33, 34, 132, 37, 7, 47, 49, 7, 14, 7, 14, 14, 35, 146, 149, 4}, {33, 34, 36, 37, 39, 15, 49, 39, 41, 39, 15, 39, 35, 50, 53, 5}, {33, 34, 38, 38, 40, 16, 49, 40, 40, 40, 16, 40, 35, 50, 53, 5}, {33, 34, 36, 37, 39, 47, 17, 39, 41, 39, 17, 39, 35, 50, 53, 6}, {33, 34, 18, 37, 39, 47, 49, 83, 20, 83, 18, 18, 35, 18, 21, 0}, {97, 98, 18, 101, 135, 111, 113, 135, 142, 135, 19, 135, 99, 18, 21, 0}, {33, 34, 18, 37, 39, 47, 49, 39, 20, 39, 20, 20, 35, 18, 21, 0}, {33, 34, 21, 37, 39, 47, 49, 86, 23, 86, 21, 21, 35, 18, 21, 3}, {97, 98, 21, 101, 135, 111, 113, 135, 142, 135, 22, 135, 99, 18, 21, 3}, {33, 34, 21, 37, 39, 47, 49, 39, 23, 39, 23, 23, 35, 18, 21, 3}};
      impTabL_DEFAULT = new byte[][]{{0, 1, 0, 2, 0, 0, 0, 0}, {0, 1, 3, 3, 20, 20, 0, 1}, {0, 1, 0, 2, 21, 21, 0, 2}, {0, 1, 3, 3, 20, 20, 0, 2}, {0, 33, 51, 51, 4, 4, 0, 0}, {0, 33, 0, 50, 5, 5, 0, 0}};
      impTabR_DEFAULT = new byte[][]{{1, 0, 2, 2, 0, 0, 0, 0}, {1, 0, 1, 3, 20, 20, 0, 1}, {1, 0, 2, 2, 0, 0, 0, 1}, {1, 0, 1, 3, 5, 5, 0, 1}, {33, 0, 33, 3, 4, 4, 0, 0}, {1, 0, 1, 3, 5, 5, 0, 0}};
      impAct0 = new short[]{0, 1, 2, 3, 4};
      impTab_DEFAULT = new ImpTabPair(impTabL_DEFAULT, impTabR_DEFAULT, impAct0, impAct0);
      impTabL_NUMBERS_SPECIAL = new byte[][]{{0, 2, 17, 17, 0, 0, 0, 0}, {0, 66, 1, 1, 0, 0, 0, 0}, {0, 2, 4, 4, 19, 19, 0, 1}, {0, 34, 52, 52, 3, 3, 0, 0}, {0, 2, 4, 4, 19, 19, 0, 2}};
      impTab_NUMBERS_SPECIAL = new ImpTabPair(impTabL_NUMBERS_SPECIAL, impTabR_DEFAULT, impAct0, impAct0);
      impTabL_GROUP_NUMBERS_WITH_R = new byte[][]{{0, 3, 17, 17, 0, 0, 0, 0}, {32, 3, 1, 1, 2, 32, 32, 2}, {32, 3, 1, 1, 2, 32, 32, 1}, {0, 3, 5, 5, 20, 0, 0, 1}, {32, 3, 5, 5, 4, 32, 32, 1}, {0, 3, 5, 5, 20, 0, 0, 2}};
      impTabR_GROUP_NUMBERS_WITH_R = new byte[][]{{2, 0, 1, 1, 0, 0, 0, 0}, {2, 0, 1, 1, 0, 0, 0, 1}, {2, 0, 20, 20, 19, 0, 0, 1}, {34, 0, 4, 4, 3, 0, 0, 0}, {34, 0, 4, 4, 3, 0, 0, 1}};
      impTab_GROUP_NUMBERS_WITH_R = new ImpTabPair(impTabL_GROUP_NUMBERS_WITH_R, impTabR_GROUP_NUMBERS_WITH_R, impAct0, impAct0);
      impTabL_INVERSE_NUMBERS_AS_L = new byte[][]{{0, 1, 0, 0, 0, 0, 0, 0}, {0, 1, 0, 0, 20, 20, 0, 1}, {0, 1, 0, 0, 21, 21, 0, 2}, {0, 1, 0, 0, 20, 20, 0, 2}, {32, 1, 32, 32, 4, 4, 32, 1}, {32, 1, 32, 32, 5, 5, 32, 1}};
      impTabR_INVERSE_NUMBERS_AS_L = new byte[][]{{1, 0, 1, 1, 0, 0, 0, 0}, {1, 0, 1, 1, 20, 20, 0, 1}, {1, 0, 1, 1, 0, 0, 0, 1}, {1, 0, 1, 1, 5, 5, 0, 1}, {33, 0, 33, 33, 4, 4, 0, 0}, {1, 0, 1, 1, 5, 5, 0, 0}};
      impTab_INVERSE_NUMBERS_AS_L = new ImpTabPair(impTabL_INVERSE_NUMBERS_AS_L, impTabR_INVERSE_NUMBERS_AS_L, impAct0, impAct0);
      impTabR_INVERSE_LIKE_DIRECT = new byte[][]{{1, 0, 2, 2, 0, 0, 0, 0}, {1, 0, 1, 2, 19, 19, 0, 1}, {1, 0, 2, 2, 0, 0, 0, 1}, {33, 48, 6, 4, 3, 3, 48, 0}, {33, 48, 6, 4, 5, 5, 48, 3}, {33, 48, 6, 4, 5, 5, 48, 2}, {33, 48, 6, 4, 3, 3, 48, 1}};
      impAct1 = new short[]{0, 1, 13, 14};
      impTab_INVERSE_LIKE_DIRECT = new ImpTabPair(impTabL_DEFAULT, impTabR_INVERSE_LIKE_DIRECT, impAct0, impAct1);
      impTabL_INVERSE_LIKE_DIRECT_WITH_MARKS = new byte[][]{{0, 99, 0, 1, 0, 0, 0, 0}, {0, 99, 0, 1, 18, 48, 0, 4}, {32, 99, 32, 1, 2, 48, 32, 3}, {0, 99, 85, 86, 20, 48, 0, 3}, {48, 67, 85, 86, 4, 48, 48, 3}, {48, 67, 5, 86, 20, 48, 48, 4}, {48, 67, 85, 6, 20, 48, 48, 4}};
      impTabR_INVERSE_LIKE_DIRECT_WITH_MARKS = new byte[][]{{19, 0, 1, 1, 0, 0, 0, 0}, {35, 0, 1, 1, 2, 64, 0, 1}, {35, 0, 1, 1, 2, 64, 0, 0}, {3, 0, 3, 54, 20, 64, 0, 1}, {83, 64, 5, 54, 4, 64, 64, 0}, {83, 64, 5, 54, 4, 64, 64, 1}, {83, 64, 6, 6, 4, 64, 64, 3}};
      impAct2 = new short[]{0, 1, 2, 5, 6, 7, 8};
      impAct3 = new short[]{0, 1, 9, 10, 11, 12};
      impTab_INVERSE_LIKE_DIRECT_WITH_MARKS = new ImpTabPair(impTabL_INVERSE_LIKE_DIRECT_WITH_MARKS, impTabR_INVERSE_LIKE_DIRECT_WITH_MARKS, impAct2, impAct3);
      impTab_INVERSE_FOR_NUMBERS_SPECIAL = new ImpTabPair(impTabL_NUMBERS_SPECIAL, impTabR_INVERSE_LIKE_DIRECT, impAct0, impAct1);
      impTabL_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS = new byte[][]{{0, 98, 1, 1, 0, 0, 0, 0}, {0, 98, 1, 1, 0, 48, 0, 4}, {0, 98, 84, 84, 19, 48, 0, 3}, {48, 66, 84, 84, 3, 48, 48, 3}, {48, 66, 4, 4, 19, 48, 48, 4}};
      impTab_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS = new ImpTabPair(impTabL_INVERSE_FOR_NUMBERS_SPECIAL_WITH_MARKS, impTabR_INVERSE_LIKE_DIRECT_WITH_MARKS, impAct2, impAct3);
   }

   private static class LevState {
      byte[][] impTab;
      short[] impAct;
      int startON;
      int startL2EN;
      int lastStrongRTL;
      int runStart;
      short state;
      byte runLevel;

      private LevState() {
      }

      // $FF: synthetic method
      LevState(Object x0) {
         this();
      }
   }

   private static class ImpTabPair {
      byte[][][] imptab;
      short[][] impact;

      ImpTabPair(byte[][] table1, byte[][] table2, short[] act1, short[] act2) {
         this.imptab = new byte[][][]{table1, table2};
         this.impact = new short[][]{act1, act2};
      }
   }

   static class Isolate {
      int startON;
      int start1;
      short stateImp;
      short state;
   }

   static class BracketData {
      Opening[] openings = new Opening[20];
      int isoRunLast;
      IsoRun[] isoRuns = new IsoRun[127];
      boolean isNumbersSpecial;
   }

   static class IsoRun {
      int contextPos;
      short start;
      short limit;
      byte level;
      byte lastStrong;
      byte lastBase;
      byte contextDir;
   }

   static class Opening {
      int position;
      int match;
      int contextPos;
      short flags;
      byte contextDir;
   }

   static class InsertPoints {
      int size;
      int confirmed;
      Point[] points = new Point[0];
   }

   static class Point {
      int pos;
      int flag;
   }
}
