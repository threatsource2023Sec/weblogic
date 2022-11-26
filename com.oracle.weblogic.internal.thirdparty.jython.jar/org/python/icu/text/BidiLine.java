package org.python.icu.text;

import java.util.Arrays;

final class BidiLine {
   static void setTrailingWSStart(Bidi bidi) {
      byte[] dirProps = bidi.dirProps;
      byte[] levels = bidi.levels;
      int start = bidi.length;
      byte paraLevel = bidi.paraLevel;
      if (dirProps[start - 1] == 7) {
         bidi.trailingWSStart = start;
      } else {
         while(start > 0 && (Bidi.DirPropFlag(dirProps[start - 1]) & Bidi.MASK_WS) != 0) {
            --start;
         }

         while(start > 0 && levels[start - 1] == paraLevel) {
            --start;
         }

         bidi.trailingWSStart = start;
      }
   }

   static Bidi setLine(Bidi paraBidi, int start, int limit) {
      Bidi lineBidi = new Bidi();
      int length = lineBidi.length = lineBidi.originalLength = lineBidi.resultLength = limit - start;
      lineBidi.text = new char[length];
      System.arraycopy(paraBidi.text, start, lineBidi.text, 0, length);
      lineBidi.paraLevel = paraBidi.GetParaLevelAt(start);
      lineBidi.paraCount = paraBidi.paraCount;
      lineBidi.runs = new BidiRun[0];
      lineBidi.reorderingMode = paraBidi.reorderingMode;
      lineBidi.reorderingOptions = paraBidi.reorderingOptions;
      if (paraBidi.controlCount > 0) {
         for(int j = start; j < limit; ++j) {
            if (Bidi.IsBidiControlChar(paraBidi.text[j])) {
               ++lineBidi.controlCount;
            }
         }

         lineBidi.resultLength -= lineBidi.controlCount;
      }

      lineBidi.getDirPropsMemory(length);
      lineBidi.dirProps = lineBidi.dirPropsMemory;
      System.arraycopy(paraBidi.dirProps, start, lineBidi.dirProps, 0, length);
      lineBidi.getLevelsMemory(length);
      lineBidi.levels = lineBidi.levelsMemory;
      System.arraycopy(paraBidi.levels, start, lineBidi.levels, 0, length);
      lineBidi.runCount = -1;
      if (paraBidi.direction != 2) {
         lineBidi.direction = paraBidi.direction;
         if (paraBidi.trailingWSStart <= start) {
            lineBidi.trailingWSStart = 0;
         } else if (paraBidi.trailingWSStart < limit) {
            lineBidi.trailingWSStart = paraBidi.trailingWSStart - start;
         } else {
            lineBidi.trailingWSStart = length;
         }
      } else {
         byte[] levels = lineBidi.levels;
         setTrailingWSStart(lineBidi);
         int trailingWSStart = lineBidi.trailingWSStart;
         if (trailingWSStart == 0) {
            lineBidi.direction = (byte)(lineBidi.paraLevel & 1);
         } else {
            byte level = (byte)(levels[0] & 1);
            if (trailingWSStart < length && (lineBidi.paraLevel & 1) != level) {
               lineBidi.direction = 2;
            } else {
               label71: {
                  for(int i = 1; i != trailingWSStart; ++i) {
                     if ((levels[i] & 1) != level) {
                        lineBidi.direction = 2;
                        break label71;
                     }
                  }

                  lineBidi.direction = level;
               }
            }
         }

         switch (lineBidi.direction) {
            case 0:
               lineBidi.paraLevel = (byte)(lineBidi.paraLevel + 1 & -2);
               lineBidi.trailingWSStart = 0;
               break;
            case 1:
               lineBidi.paraLevel = (byte)(lineBidi.paraLevel | 1);
               lineBidi.trailingWSStart = 0;
         }
      }

      lineBidi.paraBidi = paraBidi;
      return lineBidi;
   }

   static byte getLevelAt(Bidi bidi, int charIndex) {
      return bidi.direction == 2 && charIndex < bidi.trailingWSStart ? bidi.levels[charIndex] : bidi.GetParaLevelAt(charIndex);
   }

   static byte[] getLevels(Bidi bidi) {
      int start = bidi.trailingWSStart;
      int length = bidi.length;
      if (start != length) {
         Arrays.fill(bidi.levels, start, length, bidi.paraLevel);
         bidi.trailingWSStart = length;
      }

      if (length < bidi.levels.length) {
         byte[] levels = new byte[length];
         System.arraycopy(bidi.levels, 0, levels, 0, length);
         return levels;
      } else {
         return bidi.levels;
      }
   }

   static BidiRun getLogicalRun(Bidi bidi, int logicalPosition) {
      BidiRun newRun = new BidiRun();
      getRuns(bidi);
      int runCount = bidi.runCount;
      int visualStart = 0;
      int logicalLimit = 0;
      BidiRun iRun = bidi.runs[0];

      for(int i = 0; i < runCount; ++i) {
         iRun = bidi.runs[i];
         logicalLimit = iRun.start + iRun.limit - visualStart;
         if (logicalPosition >= iRun.start && logicalPosition < logicalLimit) {
            break;
         }

         visualStart = iRun.limit;
      }

      newRun.start = iRun.start;
      newRun.limit = logicalLimit;
      newRun.level = iRun.level;
      return newRun;
   }

   static BidiRun getVisualRun(Bidi bidi, int runIndex) {
      int start = bidi.runs[runIndex].start;
      byte level = bidi.runs[runIndex].level;
      int limit;
      if (runIndex > 0) {
         limit = start + bidi.runs[runIndex].limit - bidi.runs[runIndex - 1].limit;
      } else {
         limit = start + bidi.runs[0].limit;
      }

      return new BidiRun(start, limit, level);
   }

   static void getSingleRun(Bidi bidi, byte level) {
      bidi.runs = bidi.simpleRuns;
      bidi.runCount = 1;
      bidi.runs[0] = new BidiRun(0, bidi.length, level);
   }

   private static void reorderLine(Bidi bidi, byte minLevel, byte maxLevel) {
      if (maxLevel > (minLevel | 1)) {
         ++minLevel;
         BidiRun[] runs = bidi.runs;
         byte[] levels = bidi.levels;
         int runCount = bidi.runCount;
         if (bidi.trailingWSStart < bidi.length) {
            --runCount;
         }

         label70:
         while(true) {
            --maxLevel;
            int firstRun;
            BidiRun tempRun;
            if (maxLevel < minLevel) {
               if ((minLevel & 1) == 0) {
                  firstRun = 0;
                  if (bidi.trailingWSStart == bidi.length) {
                     --runCount;
                  }

                  while(firstRun < runCount) {
                     tempRun = runs[firstRun];
                     runs[firstRun] = runs[runCount];
                     runs[runCount] = tempRun;
                     ++firstRun;
                     --runCount;
                  }
               }

               return;
            }

            firstRun = 0;

            while(true) {
               while(firstRun >= runCount || levels[runs[firstRun].start] >= maxLevel) {
                  if (firstRun >= runCount) {
                     continue label70;
                  }

                  int limitRun = firstRun;

                  do {
                     ++limitRun;
                  } while(limitRun < runCount && levels[runs[limitRun].start] >= maxLevel);

                  for(int endRun = limitRun - 1; firstRun < endRun; --endRun) {
                     tempRun = runs[firstRun];
                     runs[firstRun] = runs[endRun];
                     runs[endRun] = tempRun;
                     ++firstRun;
                  }

                  if (limitRun == runCount) {
                     continue label70;
                  }

                  firstRun = limitRun + 1;
               }

               ++firstRun;
            }
         }
      }
   }

   static int getRunFromLogicalIndex(Bidi bidi, int logicalIndex) {
      BidiRun[] runs = bidi.runs;
      int runCount = bidi.runCount;
      int visualStart = 0;

      for(int i = 0; i < runCount; ++i) {
         int length = runs[i].limit - visualStart;
         int logicalStart = runs[i].start;
         if (logicalIndex >= logicalStart && logicalIndex < logicalStart + length) {
            return i;
         }

         visualStart += length;
      }

      throw new IllegalStateException("Internal ICU error in getRunFromLogicalIndex");
   }

   static void getRuns(Bidi bidi) {
      if (bidi.runCount < 0) {
         int length;
         int limit;
         if (bidi.direction != 2) {
            getSingleRun(bidi, bidi.paraLevel);
         } else {
            length = bidi.length;
            byte[] levels = bidi.levels;
            byte level = -1;
            limit = bidi.trailingWSStart;
            int runCount = 0;

            int i;
            for(i = 0; i < limit; ++i) {
               if (levels[i] != level) {
                  ++runCount;
                  level = levels[i];
               }
            }

            if (runCount == 1 && limit == length) {
               getSingleRun(bidi, levels[0]);
            } else {
               byte minLevel = 126;
               byte maxLevel = 0;
               if (limit < length) {
                  ++runCount;
               }

               bidi.getRunsMemory(runCount);
               BidiRun[] runs = bidi.runsMemory;
               int runIndex = 0;
               i = 0;

               do {
                  int start = i;
                  level = levels[i];
                  if (level < minLevel) {
                     minLevel = level;
                  }

                  if (level > maxLevel) {
                     maxLevel = level;
                  }

                  do {
                     ++i;
                  } while(i < limit && levels[i] == level);

                  runs[runIndex] = new BidiRun(start, i - start, level);
                  ++runIndex;
               } while(i < limit);

               if (limit < length) {
                  runs[runIndex] = new BidiRun(limit, length - limit, bidi.paraLevel);
                  if (bidi.paraLevel < minLevel) {
                     minLevel = bidi.paraLevel;
                  }
               }

               bidi.runs = runs;
               bidi.runCount = runCount;
               reorderLine(bidi, minLevel, maxLevel);
               limit = 0;

               for(i = 0; i < runCount; ++i) {
                  runs[i].level = levels[runs[i].start];
                  limit = runs[i].limit += limit;
               }

               if (runIndex < runCount) {
                  int trailingRun = (bidi.paraLevel & 1) != 0 ? 0 : runIndex;
                  runs[trailingRun].level = bidi.paraLevel;
               }
            }
         }

         if (bidi.insertPoints.size > 0) {
            for(int ip = 0; ip < bidi.insertPoints.size; ++ip) {
               Bidi.Point point = bidi.insertPoints.points[ip];
               limit = getRunFromLogicalIndex(bidi, point.pos);
               BidiRun var10000 = bidi.runs[limit];
               var10000.insertRemove |= point.flag;
            }
         }

         if (bidi.controlCount > 0) {
            for(limit = 0; limit < bidi.length; ++limit) {
               char c = bidi.text[limit];
               if (Bidi.IsBidiControlChar(c)) {
                  length = getRunFromLogicalIndex(bidi, limit);
                  --bidi.runs[length].insertRemove;
               }
            }
         }

      }
   }

   static int[] prepareReorder(byte[] levels, byte[] pMinLevel, byte[] pMaxLevel) {
      if (levels != null && levels.length > 0) {
         byte minLevel = 126;
         byte maxLevel = 0;
         int start = levels.length;

         while(start > 0) {
            --start;
            byte level = levels[start];
            if (level < 0) {
               return null;
            }

            if (level > 126) {
               return null;
            }

            if (level < minLevel) {
               minLevel = level;
            }

            if (level > maxLevel) {
               maxLevel = level;
            }
         }

         pMinLevel[0] = minLevel;
         pMaxLevel[0] = maxLevel;
         int[] indexMap = new int[levels.length];

         for(start = levels.length; start > 0; indexMap[start] = start) {
            --start;
         }

         return indexMap;
      } else {
         return null;
      }
   }

   static int[] reorderLogical(byte[] levels) {
      byte[] aMinLevel = new byte[1];
      byte[] aMaxLevel = new byte[1];
      int[] indexMap = prepareReorder(levels, aMinLevel, aMaxLevel);
      if (indexMap == null) {
         return null;
      } else {
         byte minLevel = aMinLevel[0];
         byte maxLevel = aMaxLevel[0];
         if (minLevel == maxLevel && (minLevel & 1) == 0) {
            return indexMap;
         } else {
            minLevel = (byte)(minLevel | 1);

            do {
               int start = 0;

               label56:
               while(true) {
                  while(start >= levels.length || levels[start] >= maxLevel) {
                     if (start >= levels.length) {
                        break label56;
                     }

                     int limit = start;

                     do {
                        ++limit;
                     } while(limit < levels.length && levels[limit] >= maxLevel);

                     int sumOfSosEos = start + limit - 1;

                     do {
                        indexMap[start] = sumOfSosEos - indexMap[start];
                        ++start;
                     } while(start < limit);

                     if (limit == levels.length) {
                        break label56;
                     }

                     start = limit + 1;
                  }

                  ++start;
               }

               --maxLevel;
            } while(maxLevel >= minLevel);

            return indexMap;
         }
      }
   }

   static int[] reorderVisual(byte[] levels) {
      byte[] aMinLevel = new byte[1];
      byte[] aMaxLevel = new byte[1];
      int[] indexMap = prepareReorder(levels, aMinLevel, aMaxLevel);
      if (indexMap == null) {
         return null;
      } else {
         byte minLevel = aMinLevel[0];
         byte maxLevel = aMaxLevel[0];
         if (minLevel == maxLevel && (minLevel & 1) == 0) {
            return indexMap;
         } else {
            minLevel = (byte)(minLevel | 1);

            do {
               int start = 0;

               label58:
               while(true) {
                  while(start >= levels.length || levels[start] >= maxLevel) {
                     if (start >= levels.length) {
                        break label58;
                     }

                     int limit = start;

                     do {
                        ++limit;
                     } while(limit < levels.length && levels[limit] >= maxLevel);

                     for(int end = limit - 1; start < end; --end) {
                        int temp = indexMap[start];
                        indexMap[start] = indexMap[end];
                        indexMap[end] = temp;
                        ++start;
                     }

                     if (limit == levels.length) {
                        break label58;
                     }

                     start = limit + 1;
                  }

                  ++start;
               }

               --maxLevel;
            } while(maxLevel >= minLevel);

            return indexMap;
         }
      }
   }

   static int getVisualIndex(Bidi bidi, int logicalIndex) {
      int visualIndex = -1;
      BidiRun[] runs;
      int j;
      int i;
      int limit;
      int start;
      switch (bidi.direction) {
         case 0:
            visualIndex = logicalIndex;
            break;
         case 1:
            visualIndex = bidi.length - logicalIndex - 1;
            break;
         default:
            getRuns(bidi);
            runs = bidi.runs;
            j = 0;

            for(i = 0; i < bidi.runCount; ++i) {
               limit = runs[i].limit - j;
               start = logicalIndex - runs[i].start;
               if (start >= 0 && start < limit) {
                  if (runs[i].isEvenRun()) {
                     visualIndex = j + start;
                  } else {
                     visualIndex = j + limit - start - 1;
                  }
                  break;
               }

               j += limit;
            }

            if (i >= bidi.runCount) {
               return -1;
            }
      }

      int length;
      if (bidi.insertPoints.size > 0) {
         runs = bidi.runs;
         limit = 0;
         length = 0;
         i = 0;

         while(true) {
            j = runs[i].limit - limit;
            start = runs[i].insertRemove;
            if ((start & 5) > 0) {
               ++length;
            }

            if (visualIndex < runs[i].limit) {
               return visualIndex + length;
            }

            if ((start & 10) > 0) {
               ++length;
            }

            ++i;
            limit += j;
         }
      } else if (bidi.controlCount <= 0) {
         return visualIndex;
      } else {
         runs = bidi.runs;
         int visualStart = 0;
         int controlFound = 0;
         char uchar = bidi.text[logicalIndex];
         if (Bidi.IsBidiControlChar(uchar)) {
            return -1;
         } else {
            i = 0;

            while(true) {
               length = runs[i].limit - visualStart;
               int insertRemove = runs[i].insertRemove;
               if (visualIndex < runs[i].limit) {
                  if (insertRemove == 0) {
                     return visualIndex - controlFound;
                  } else {
                     if (runs[i].isEvenRun()) {
                        start = runs[i].start;
                        limit = logicalIndex;
                     } else {
                        start = logicalIndex + 1;
                        limit = runs[i].start + length;
                     }

                     for(j = start; j < limit; ++j) {
                        uchar = bidi.text[j];
                        if (Bidi.IsBidiControlChar(uchar)) {
                           ++controlFound;
                        }
                     }

                     return visualIndex - controlFound;
                  }
               }

               controlFound -= insertRemove;
               ++i;
               visualStart += length;
            }
         }
      }
   }

   static int getLogicalIndex(Bidi bidi, int visualIndex) {
      BidiRun[] runs = bidi.runs;
      int runCount = bidi.runCount;
      int controlFound;
      int length;
      int i;
      int logicalStart;
      int limit;
      if (bidi.insertPoints.size > 0) {
         controlFound = 0;
         length = 0;
         i = 0;

         while(true) {
            logicalStart = runs[i].limit - length;
            limit = runs[i].insertRemove;
            if ((limit & 5) > 0) {
               if (visualIndex <= length + controlFound) {
                  return -1;
               }

               ++controlFound;
            }

            if (visualIndex < runs[i].limit + controlFound) {
               visualIndex -= controlFound;
               break;
            }

            if ((limit & 10) > 0) {
               if (visualIndex == length + logicalStart + controlFound) {
                  return -1;
               }

               ++controlFound;
            }

            ++i;
            length += logicalStart;
         }
      } else if (bidi.controlCount > 0) {
         controlFound = 0;
         int visualStart = 0;
         i = 0;

         while(true) {
            length = runs[i].limit - visualStart;
            limit = runs[i].insertRemove;
            if (visualIndex < runs[i].limit - controlFound + limit) {
               if (limit == 0) {
                  visualIndex += controlFound;
               } else {
                  logicalStart = runs[i].start;
                  boolean evenRun = runs[i].isEvenRun();
                  int logicalEnd = logicalStart + length - 1;

                  for(int j = 0; j < length; ++j) {
                     int k = evenRun ? logicalStart + j : logicalEnd - j;
                     char uchar = bidi.text[k];
                     if (Bidi.IsBidiControlChar(uchar)) {
                        ++controlFound;
                     }

                     if (visualIndex + controlFound == visualStart + j) {
                        break;
                     }
                  }

                  visualIndex += controlFound;
               }
               break;
            }

            controlFound -= limit;
            ++i;
            visualStart += length;
         }
      }

      if (runCount <= 10) {
         for(i = 0; visualIndex >= runs[i].limit; ++i) {
         }
      } else {
         controlFound = 0;
         limit = runCount;

         label76:
         while(true) {
            while(true) {
               i = controlFound + limit >>> 1;
               if (visualIndex >= runs[i].limit) {
                  controlFound = i + 1;
               } else {
                  if (i == 0 || visualIndex >= runs[i - 1].limit) {
                     break label76;
                  }

                  limit = i;
               }
            }
         }
      }

      int start = runs[i].start;
      if (runs[i].isEvenRun()) {
         if (i > 0) {
            visualIndex -= runs[i - 1].limit;
         }

         return start + visualIndex;
      } else {
         return start + runs[i].limit - visualIndex - 1;
      }
   }

   static int[] getLogicalMap(Bidi bidi) {
      BidiRun[] runs = bidi.runs;
      int[] indexMap = new int[bidi.length];
      if (bidi.length > bidi.resultLength) {
         Arrays.fill(indexMap, -1);
      }

      int visualStart = 0;

      int controlFound;
      int logicalStart;
      for(controlFound = 0; controlFound < bidi.runCount; ++controlFound) {
         logicalStart = runs[controlFound].start;
         int visualLimit = runs[controlFound].limit;
         if (runs[controlFound].isEvenRun()) {
            do {
               indexMap[logicalStart++] = visualStart++;
            } while(visualStart < visualLimit);
         } else {
            logicalStart += visualLimit - visualStart;

            do {
               --logicalStart;
               indexMap[logicalStart] = visualStart++;
            } while(visualStart < visualLimit);
         }
      }

      int runCount;
      int i;
      int length;
      int insertRemove;
      int logicalLimit;
      int j;
      if (bidi.insertPoints.size > 0) {
         controlFound = 0;
         runCount = bidi.runCount;
         runs = bidi.runs;
         visualStart = 0;

         for(i = 0; i < runCount; visualStart += length) {
            length = runs[i].limit - visualStart;
            insertRemove = runs[i].insertRemove;
            if ((insertRemove & 5) > 0) {
               ++controlFound;
            }

            if (controlFound > 0) {
               logicalStart = runs[i].start;
               logicalLimit = logicalStart + length;

               for(j = logicalStart; j < logicalLimit; ++j) {
                  indexMap[j] += controlFound;
               }
            }

            if ((insertRemove & 10) > 0) {
               ++controlFound;
            }

            ++i;
         }
      } else if (bidi.controlCount > 0) {
         controlFound = 0;
         runCount = bidi.runCount;
         runs = bidi.runs;
         visualStart = 0;

         for(i = 0; i < runCount; visualStart += length) {
            length = runs[i].limit - visualStart;
            insertRemove = runs[i].insertRemove;
            if (controlFound - insertRemove != 0) {
               logicalStart = runs[i].start;
               boolean evenRun = runs[i].isEvenRun();
               logicalLimit = logicalStart + length;
               if (insertRemove == 0) {
                  for(j = logicalStart; j < logicalLimit; ++j) {
                     indexMap[j] -= controlFound;
                  }
               } else {
                  for(j = 0; j < length; ++j) {
                     int k = evenRun ? logicalStart + j : logicalLimit - j - 1;
                     char uchar = bidi.text[k];
                     if (Bidi.IsBidiControlChar(uchar)) {
                        ++controlFound;
                        indexMap[k] = -1;
                     } else {
                        indexMap[k] -= controlFound;
                     }
                  }
               }
            }

            ++i;
         }
      }

      return indexMap;
   }

   static int[] getVisualMap(Bidi bidi) {
      BidiRun[] runs = bidi.runs;
      int allocLength = bidi.length > bidi.resultLength ? bidi.length : bidi.resultLength;
      int[] indexMap = new int[allocLength];
      int visualStart = 0;
      int idx = 0;

      int markFound;
      int logicalStart;
      int visualLimit;
      for(markFound = 0; markFound < bidi.runCount; ++markFound) {
         logicalStart = runs[markFound].start;
         visualLimit = runs[markFound].limit;
         if (runs[markFound].isEvenRun()) {
            do {
               indexMap[idx++] = logicalStart++;
               ++visualStart;
            } while(visualStart < visualLimit);
         } else {
            logicalStart += visualLimit - visualStart;

            do {
               int var10001 = idx++;
               --logicalStart;
               indexMap[var10001] = logicalStart;
               ++visualStart;
            } while(visualStart < visualLimit);
         }
      }

      int logicalEnd;
      int length;
      int insertRemove;
      int k;
      int j;
      if (bidi.insertPoints.size > 0) {
         markFound = 0;
         logicalEnd = bidi.runCount;
         runs = bidi.runs;

         for(length = 0; length < logicalEnd; ++length) {
            insertRemove = runs[length].insertRemove;
            if ((insertRemove & 5) > 0) {
               ++markFound;
            }

            if ((insertRemove & 10) > 0) {
               ++markFound;
            }
         }

         k = bidi.resultLength;

         for(length = logicalEnd - 1; length >= 0 && markFound > 0; --length) {
            insertRemove = runs[length].insertRemove;
            if ((insertRemove & 10) > 0) {
               --k;
               indexMap[k] = -1;
               --markFound;
            }

            visualStart = length > 0 ? runs[length - 1].limit : 0;

            for(j = runs[length].limit - 1; j >= visualStart && markFound > 0; --j) {
               --k;
               indexMap[k] = indexMap[j];
            }

            if ((insertRemove & 5) > 0) {
               --k;
               indexMap[k] = -1;
               --markFound;
            }
         }
      } else if (bidi.controlCount > 0) {
         markFound = bidi.runCount;
         runs = bidi.runs;
         visualStart = 0;
         int k = 0;

         for(j = 0; j < markFound; visualStart += length) {
            length = runs[j].limit - visualStart;
            insertRemove = runs[j].insertRemove;
            if (insertRemove == 0 && k == visualStart) {
               k += length;
            } else if (insertRemove == 0) {
               visualLimit = runs[j].limit;

               for(k = visualStart; k < visualLimit; ++k) {
                  indexMap[k++] = indexMap[k];
               }
            } else {
               logicalStart = runs[j].start;
               boolean evenRun = runs[j].isEvenRun();
               logicalEnd = logicalStart + length - 1;

               for(k = 0; k < length; ++k) {
                  int m = evenRun ? logicalStart + k : logicalEnd - k;
                  char uchar = bidi.text[m];
                  if (!Bidi.IsBidiControlChar(uchar)) {
                     indexMap[k++] = m;
                  }
               }
            }

            ++j;
         }
      }

      if (allocLength == bidi.resultLength) {
         return indexMap;
      } else {
         int[] newMap = new int[bidi.resultLength];
         System.arraycopy(indexMap, 0, newMap, 0, bidi.resultLength);
         return newMap;
      }
   }

   static int[] invertMap(int[] srcMap) {
      int srcLength = srcMap.length;
      int destLength = -1;
      int count = 0;

      int i;
      int srcEntry;
      for(i = 0; i < srcLength; ++i) {
         srcEntry = srcMap[i];
         if (srcEntry > destLength) {
            destLength = srcEntry;
         }

         if (srcEntry >= 0) {
            ++count;
         }
      }

      ++destLength;
      int[] destMap = new int[destLength];
      if (count < destLength) {
         Arrays.fill(destMap, -1);
      }

      for(i = 0; i < srcLength; ++i) {
         srcEntry = srcMap[i];
         if (srcEntry >= 0) {
            destMap[srcEntry] = i;
         }
      }

      return destMap;
   }
}
