package org.python.icu.text;

public final class UnicodeCompressor implements SCSU {
   private static boolean[] sSingleTagTable = new boolean[]{false, true, true, true, true, true, true, true, true, false, false, true, true, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false};
   private static boolean[] sUnicodeTagTable = new boolean[]{false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false};
   private int fCurrentWindow = 0;
   private int[] fOffsets = new int[8];
   private int fMode = 0;
   private int[] fIndexCount = new int[256];
   private int[] fTimeStamps = new int[8];
   private int fTimeStamp = 0;

   public UnicodeCompressor() {
      this.reset();
   }

   public static byte[] compress(String buffer) {
      return compress(buffer.toCharArray(), 0, buffer.length());
   }

   public static byte[] compress(char[] buffer, int start, int limit) {
      UnicodeCompressor comp = new UnicodeCompressor();
      int len = Math.max(4, 3 * (limit - start) + 1);
      byte[] temp = new byte[len];
      int byteCount = comp.compress(buffer, start, limit, (int[])null, temp, 0, len);
      byte[] result = new byte[byteCount];
      System.arraycopy(temp, 0, result, 0, byteCount);
      return result;
   }

   public int compress(char[] charBuffer, int charBufferStart, int charBufferLimit, int[] charsRead, byte[] byteBuffer, int byteBufferStart, int byteBufferLimit) {
      int bytePos = byteBufferStart;
      int ucPos = charBufferStart;
      int curUC = true;
      int curIndex = true;
      int nextUC = true;
      int forwardUC = true;
      int whichWindow = false;
      int hiByte = false;
      int loByte = false;
      if (byteBuffer.length >= 4 && byteBufferLimit - byteBufferStart >= 4) {
         label254:
         while(ucPos < charBufferLimit && bytePos < byteBufferLimit) {
            char curUC;
            int curIndex;
            int var10002;
            int nextUC;
            int forwardUC;
            int whichWindow;
            int hiByte;
            int loByte;
            switch (this.fMode) {
               case 0:
                  while(true) {
                     while(true) {
                        while(true) {
                           if (ucPos >= charBufferLimit || bytePos >= byteBufferLimit) {
                              continue label254;
                           }

                           curUC = charBuffer[ucPos++];
                           if (ucPos < charBufferLimit) {
                              nextUC = charBuffer[ucPos];
                           } else {
                              nextUC = -1;
                           }

                           if (curUC >= 128) {
                              if (!this.inDynamicWindow(curUC, this.fCurrentWindow)) {
                                 if (!isCompressible(curUC)) {
                                    if (nextUC == -1 || !isCompressible(nextUC)) {
                                       if (bytePos + 3 >= byteBufferLimit) {
                                          --ucPos;
                                          break label254;
                                       }

                                       byteBuffer[bytePos++] = 15;
                                       hiByte = curUC >>> 8;
                                       loByte = curUC & 255;
                                       if (sUnicodeTagTable[hiByte]) {
                                          byteBuffer[bytePos++] = -16;
                                       }

                                       byteBuffer[bytePos++] = (byte)hiByte;
                                       byteBuffer[bytePos++] = (byte)loByte;
                                       this.fMode = 1;
                                       continue label254;
                                    }

                                    if (bytePos + 2 >= byteBufferLimit) {
                                       --ucPos;
                                       break label254;
                                    }

                                    byteBuffer[bytePos++] = 14;
                                    byteBuffer[bytePos++] = (byte)(curUC >>> 8);
                                    byteBuffer[bytePos++] = (byte)(curUC & 255);
                                 } else if ((whichWindow = this.findDynamicWindow(curUC)) != -1) {
                                    if (ucPos + 1 < charBufferLimit) {
                                       forwardUC = charBuffer[ucPos + 1];
                                    } else {
                                       forwardUC = -1;
                                    }

                                    if (this.inDynamicWindow(nextUC, whichWindow) && this.inDynamicWindow(forwardUC, whichWindow)) {
                                       if (bytePos + 1 >= byteBufferLimit) {
                                          --ucPos;
                                          break label254;
                                       }

                                       byteBuffer[bytePos++] = (byte)(16 + whichWindow);
                                       byteBuffer[bytePos++] = (byte)(curUC - this.fOffsets[whichWindow] + 128);
                                       this.fTimeStamps[whichWindow] = ++this.fTimeStamp;
                                       this.fCurrentWindow = whichWindow;
                                    } else {
                                       if (bytePos + 1 >= byteBufferLimit) {
                                          --ucPos;
                                          break label254;
                                       }

                                       byteBuffer[bytePos++] = (byte)(1 + whichWindow);
                                       byteBuffer[bytePos++] = (byte)(curUC - this.fOffsets[whichWindow] + 128);
                                    }
                                 } else if ((whichWindow = findStaticWindow(curUC)) != -1 && !inStaticWindow(nextUC, whichWindow)) {
                                    if (bytePos + 1 >= byteBufferLimit) {
                                       --ucPos;
                                       break label254;
                                    }

                                    byteBuffer[bytePos++] = (byte)(1 + whichWindow);
                                    byteBuffer[bytePos++] = (byte)(curUC - sOffsets[whichWindow]);
                                 } else {
                                    curIndex = makeIndex(curUC);
                                    var10002 = this.fIndexCount[curIndex]++;
                                    if (ucPos + 1 < charBufferLimit) {
                                       forwardUC = charBuffer[ucPos + 1];
                                    } else {
                                       forwardUC = -1;
                                    }

                                    if (this.fIndexCount[curIndex] <= 1 && (curIndex != makeIndex(nextUC) || curIndex != makeIndex(forwardUC))) {
                                       if (bytePos + 3 >= byteBufferLimit) {
                                          --ucPos;
                                          break label254;
                                       }

                                       byteBuffer[bytePos++] = 15;
                                       hiByte = curUC >>> 8;
                                       loByte = curUC & 255;
                                       if (sUnicodeTagTable[hiByte]) {
                                          byteBuffer[bytePos++] = -16;
                                       }

                                       byteBuffer[bytePos++] = (byte)hiByte;
                                       byteBuffer[bytePos++] = (byte)loByte;
                                       this.fMode = 1;
                                       continue label254;
                                    }

                                    if (bytePos + 2 >= byteBufferLimit) {
                                       --ucPos;
                                       break label254;
                                    }

                                    whichWindow = this.getLRDefinedWindow();
                                    byteBuffer[bytePos++] = (byte)(24 + whichWindow);
                                    byteBuffer[bytePos++] = (byte)curIndex;
                                    byteBuffer[bytePos++] = (byte)(curUC - sOffsetTable[curIndex] + 128);
                                    this.fOffsets[whichWindow] = sOffsetTable[curIndex];
                                    this.fCurrentWindow = whichWindow;
                                    this.fTimeStamps[whichWindow] = ++this.fTimeStamp;
                                 }
                              } else {
                                 byteBuffer[bytePos++] = (byte)(curUC - this.fOffsets[this.fCurrentWindow] + 128);
                              }
                           } else {
                              loByte = curUC & 255;
                              if (sSingleTagTable[loByte]) {
                                 if (bytePos + 1 >= byteBufferLimit) {
                                    --ucPos;
                                    break label254;
                                 }

                                 byteBuffer[bytePos++] = 1;
                              }

                              byteBuffer[bytePos++] = (byte)loByte;
                           }
                        }
                     }
                  }
               case 1:
                  while(true) {
                     while(true) {
                        while(true) {
                           while(true) {
                              if (ucPos >= charBufferLimit || bytePos >= byteBufferLimit) {
                                 continue label254;
                              }

                              curUC = charBuffer[ucPos++];
                              if (ucPos < charBufferLimit) {
                                 nextUC = charBuffer[ucPos];
                              } else {
                                 nextUC = -1;
                              }

                              if (isCompressible(curUC) && (nextUC == -1 || isCompressible(nextUC))) {
                                 if (curUC >= 128) {
                                    if ((whichWindow = this.findDynamicWindow(curUC)) == -1) {
                                       curIndex = makeIndex(curUC);
                                       var10002 = this.fIndexCount[curIndex]++;
                                       if (ucPos + 1 < charBufferLimit) {
                                          forwardUC = charBuffer[ucPos + 1];
                                       } else {
                                          forwardUC = -1;
                                       }

                                       if (this.fIndexCount[curIndex] > 1 || curIndex == makeIndex(nextUC) && curIndex == makeIndex(forwardUC)) {
                                          if (bytePos + 2 >= byteBufferLimit) {
                                             --ucPos;
                                             break label254;
                                          }

                                          whichWindow = this.getLRDefinedWindow();
                                          byteBuffer[bytePos++] = (byte)(232 + whichWindow);
                                          byteBuffer[bytePos++] = (byte)curIndex;
                                          byteBuffer[bytePos++] = (byte)(curUC - sOffsetTable[curIndex] + 128);
                                          this.fOffsets[whichWindow] = sOffsetTable[curIndex];
                                          this.fCurrentWindow = whichWindow;
                                          this.fTimeStamps[whichWindow] = ++this.fTimeStamp;
                                          this.fMode = 0;
                                          continue label254;
                                       }

                                       if (bytePos + 2 >= byteBufferLimit) {
                                          --ucPos;
                                          break label254;
                                       }

                                       hiByte = curUC >>> 8;
                                       loByte = curUC & 255;
                                       if (sUnicodeTagTable[hiByte]) {
                                          byteBuffer[bytePos++] = -16;
                                       }

                                       byteBuffer[bytePos++] = (byte)hiByte;
                                       byteBuffer[bytePos++] = (byte)loByte;
                                    } else {
                                       if (this.inDynamicWindow(nextUC, whichWindow)) {
                                          if (bytePos + 1 >= byteBufferLimit) {
                                             --ucPos;
                                             break label254;
                                          }

                                          byteBuffer[bytePos++] = (byte)(224 + whichWindow);
                                          byteBuffer[bytePos++] = (byte)(curUC - this.fOffsets[whichWindow] + 128);
                                          this.fTimeStamps[whichWindow] = ++this.fTimeStamp;
                                          this.fCurrentWindow = whichWindow;
                                          this.fMode = 0;
                                          continue label254;
                                       }

                                       if (bytePos + 2 >= byteBufferLimit) {
                                          --ucPos;
                                          break label254;
                                       }

                                       hiByte = curUC >>> 8;
                                       loByte = curUC & 255;
                                       if (sUnicodeTagTable[hiByte]) {
                                          byteBuffer[bytePos++] = -16;
                                       }

                                       byteBuffer[bytePos++] = (byte)hiByte;
                                       byteBuffer[bytePos++] = (byte)loByte;
                                    }
                                 } else {
                                    loByte = curUC & 255;
                                    if (nextUC != -1 && nextUC < 128 && !sSingleTagTable[loByte]) {
                                       if (bytePos + 1 >= byteBufferLimit) {
                                          --ucPos;
                                          break label254;
                                       }

                                       whichWindow = this.fCurrentWindow;
                                       byteBuffer[bytePos++] = (byte)(224 + whichWindow);
                                       byteBuffer[bytePos++] = (byte)loByte;
                                       this.fTimeStamps[whichWindow] = ++this.fTimeStamp;
                                       this.fMode = 0;
                                       continue label254;
                                    }

                                    if (bytePos + 1 >= byteBufferLimit) {
                                       --ucPos;
                                       break label254;
                                    }

                                    byteBuffer[bytePos++] = 0;
                                    byteBuffer[bytePos++] = (byte)loByte;
                                 }
                              } else {
                                 if (bytePos + 2 >= byteBufferLimit) {
                                    --ucPos;
                                    break label254;
                                 }

                                 hiByte = curUC >>> 8;
                                 loByte = curUC & 255;
                                 if (sUnicodeTagTable[hiByte]) {
                                    byteBuffer[bytePos++] = -16;
                                 }

                                 byteBuffer[bytePos++] = (byte)hiByte;
                                 byteBuffer[bytePos++] = (byte)loByte;
                              }
                           }
                        }
                     }
                  }
            }
         }

         if (charsRead != null) {
            charsRead[0] = ucPos - charBufferStart;
         }

         return bytePos - byteBufferStart;
      } else {
         throw new IllegalArgumentException("byteBuffer.length < 4");
      }
   }

   public void reset() {
      this.fOffsets[0] = 128;
      this.fOffsets[1] = 192;
      this.fOffsets[2] = 1024;
      this.fOffsets[3] = 1536;
      this.fOffsets[4] = 2304;
      this.fOffsets[5] = 12352;
      this.fOffsets[6] = 12448;
      this.fOffsets[7] = 65280;

      int i;
      for(i = 0; i < 8; ++i) {
         this.fTimeStamps[i] = 0;
      }

      for(i = 0; i <= 255; ++i) {
         this.fIndexCount[i] = 0;
      }

      this.fTimeStamp = 0;
      this.fCurrentWindow = 0;
      this.fMode = 0;
   }

   private static int makeIndex(int c) {
      if (c >= 192 && c < 320) {
         return 249;
      } else if (c >= 592 && c < 720) {
         return 250;
      } else if (c >= 880 && c < 1008) {
         return 251;
      } else if (c >= 1328 && c < 1424) {
         return 252;
      } else if (c >= 12352 && c < 12448) {
         return 253;
      } else if (c >= 12448 && c < 12576) {
         return 254;
      } else if (c >= 65376 && c < 65439) {
         return 255;
      } else if (c >= 128 && c < 13312) {
         return c / 128 & 255;
      } else {
         return c >= 57344 && c <= 65535 ? (c - '가') / 128 & 255 : 0;
      }
   }

   private boolean inDynamicWindow(int c, int whichWindow) {
      return c >= this.fOffsets[whichWindow] && c < this.fOffsets[whichWindow] + 128;
   }

   private static boolean inStaticWindow(int c, int whichWindow) {
      return c >= sOffsets[whichWindow] && c < sOffsets[whichWindow] + 128;
   }

   private static boolean isCompressible(int c) {
      return c < 13312 || c >= 57344;
   }

   private int findDynamicWindow(int c) {
      for(int i = 7; i >= 0; --i) {
         if (this.inDynamicWindow(c, i)) {
            int var10002 = this.fTimeStamps[i]++;
            return i;
         }
      }

      return -1;
   }

   private static int findStaticWindow(int c) {
      for(int i = 7; i >= 0; --i) {
         if (inStaticWindow(c, i)) {
            return i;
         }
      }

      return -1;
   }

   private int getLRDefinedWindow() {
      int leastRU = Integer.MAX_VALUE;
      int whichWindow = -1;

      for(int i = 7; i >= 0; --i) {
         if (this.fTimeStamps[i] < leastRU) {
            leastRU = this.fTimeStamps[i];
            whichWindow = i;
         }
      }

      return whichWindow;
   }
}
