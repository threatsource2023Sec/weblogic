package org.glassfish.grizzly.compression.lzma.impl;

import java.io.IOException;
import org.glassfish.grizzly.Buffer;
import org.glassfish.grizzly.compression.lzma.LZMAEncoder;
import org.glassfish.grizzly.compression.lzma.impl.lz.BinTree;
import org.glassfish.grizzly.compression.lzma.impl.rangecoder.BitTreeEncoder;
import org.glassfish.grizzly.compression.lzma.impl.rangecoder.RangeEncoder;
import org.glassfish.grizzly.memory.MemoryManager;

public class Encoder {
   public static final int EMatchFinderTypeBT2 = 0;
   public static final int EMatchFinderTypeBT4 = 1;
   static final int kIfinityPrice = 268435455;
   static final byte[] g_FastPos = new byte[2048];
   int _state = Base.stateInit();
   byte _previousByte;
   final int[] _repDistances = new int[4];
   static final int kDefaultDictionaryLogSize = 22;
   static final int kNumFastBytesDefault = 32;
   public static final int kNumLenSpecSymbols = 16;
   static final int kNumOpts = 4096;
   final Optimal[] _optimum = new Optimal[4096];
   BinTree _matchFinder = null;
   final RangeEncoder _rangeEncoder = new RangeEncoder();
   final short[] _isMatch = new short[192];
   final short[] _isRep = new short[12];
   final short[] _isRepG0 = new short[12];
   final short[] _isRepG1 = new short[12];
   final short[] _isRepG2 = new short[12];
   final short[] _isRep0Long = new short[192];
   final BitTreeEncoder[] _posSlotEncoder = new BitTreeEncoder[4];
   final short[] _posEncoders = new short[114];
   final BitTreeEncoder _posAlignEncoder = new BitTreeEncoder(4);
   final LenPriceTableEncoder _lenEncoder = new LenPriceTableEncoder();
   final LenPriceTableEncoder _repMatchLenEncoder = new LenPriceTableEncoder();
   final LiteralEncoder _literalEncoder = new LiteralEncoder();
   final int[] _matchDistances = new int[548];
   int _numFastBytes = 32;
   int _longestMatchLength;
   int _numDistancePairs;
   int _additionalOffset;
   int _optimumEndIndex;
   int _optimumCurrentIndex;
   boolean _longestMatchWasFound;
   final int[] _posSlotPrices = new int[256];
   final int[] _distancesPrices = new int[512];
   final int[] _alignPrices = new int[16];
   int _alignPriceCount;
   int _distTableSize = 44;
   int _posStateBits = 2;
   int _posStateMask = 3;
   int _numLiteralPosStateBits = 0;
   int _numLiteralContextBits = 3;
   int _dictionarySize = 4194304;
   int _dictionarySizePrev = -1;
   int _numFastBytesPrev = -1;
   long nowPos64;
   boolean _finished;
   Buffer _src;
   int _matchFinderType = 1;
   boolean _writeEndMark = false;
   boolean _needReleaseMFStream = false;
   final int[] reps = new int[4];
   final int[] repLens = new int[4];
   int backRes;
   final long[] processedInSize = new long[1];
   final long[] processedOutSize = new long[1];
   final boolean[] finished = new boolean[1];
   final int[] tempPrices = new int[128];
   int _matchPriceCount;

   static int getPosSlot(int pos) {
      if (pos < 2048) {
         return g_FastPos[pos];
      } else {
         return pos < 2097152 ? g_FastPos[pos >> 10] + 20 : g_FastPos[pos >> 20] + 40;
      }
   }

   static int getPosSlot2(int pos) {
      if (pos < 131072) {
         return g_FastPos[pos >> 6] + 12;
      } else {
         return pos < 134217728 ? g_FastPos[pos >> 16] + 32 : g_FastPos[pos >> 26] + 52;
      }
   }

   void baseInit() {
      this._state = Base.stateInit();
      this._previousByte = 0;

      for(int i = 0; i < 4; ++i) {
         this._repDistances[i] = 0;
      }

   }

   void create() {
      if (this._matchFinder == null) {
         BinTree bt = new BinTree();
         int numHashBytes = 4;
         if (this._matchFinderType == 0) {
            numHashBytes = 2;
         }

         bt.setType(numHashBytes);
         this._matchFinder = bt;
      }

      this._literalEncoder.create(this._numLiteralPosStateBits, this._numLiteralContextBits);
      if (this._dictionarySize != this._dictionarySizePrev || this._numFastBytesPrev != this._numFastBytes) {
         this._matchFinder.create(this._dictionarySize, 4096, this._numFastBytes, 274);
         this._dictionarySizePrev = this._dictionarySize;
         this._numFastBytesPrev = this._numFastBytes;
      }
   }

   public Encoder() {
      int i;
      for(i = 0; i < 4096; ++i) {
         this._optimum[i] = new Optimal();
      }

      for(i = 0; i < 4; ++i) {
         this._posSlotEncoder[i] = new BitTreeEncoder(6);
      }

   }

   void setWriteEndMarkerMode(boolean writeEndMarker) {
      this._writeEndMark = writeEndMarker;
   }

   void init() {
      this.baseInit();
      this._rangeEncoder.init();
      RangeEncoder.initBitModels(this._isMatch);
      RangeEncoder.initBitModels(this._isRep0Long);
      RangeEncoder.initBitModels(this._isRep);
      RangeEncoder.initBitModels(this._isRepG0);
      RangeEncoder.initBitModels(this._isRepG1);
      RangeEncoder.initBitModels(this._isRepG2);
      RangeEncoder.initBitModels(this._posEncoders);
      this._literalEncoder.init();

      for(int i = 0; i < 4; ++i) {
         this._posSlotEncoder[i].init();
      }

      this._lenEncoder.init(1 << this._posStateBits);
      this._repMatchLenEncoder.init(1 << this._posStateBits);
      this._posAlignEncoder.init();
      this._longestMatchWasFound = false;
      this._optimumEndIndex = 0;
      this._optimumCurrentIndex = 0;
      this._additionalOffset = 0;
   }

   int readMatchDistances() throws IOException {
      int lenRes = 0;
      this._numDistancePairs = this._matchFinder.getMatches(this._matchDistances);
      if (this._numDistancePairs > 0) {
         lenRes = this._matchDistances[this._numDistancePairs - 2];
         if (lenRes == this._numFastBytes) {
            lenRes += this._matchFinder.getMatchLen(lenRes - 1, this._matchDistances[this._numDistancePairs - 1], 273 - lenRes);
         }
      }

      ++this._additionalOffset;
      return lenRes;
   }

   void movePos(int num) throws IOException {
      if (num > 0) {
         this._matchFinder.skip(num);
         this._additionalOffset += num;
      }

   }

   int getRepLen1Price(int state, int posState) {
      return RangeEncoder.getPrice0(this._isRepG0[state]) + RangeEncoder.getPrice0(this._isRep0Long[(state << 4) + posState]);
   }

   int getPureRepPrice(int repIndex, int state, int posState) {
      int price;
      if (repIndex == 0) {
         price = RangeEncoder.getPrice0(this._isRepG0[state]);
         price += RangeEncoder.getPrice1(this._isRep0Long[(state << 4) + posState]);
      } else {
         price = RangeEncoder.getPrice1(this._isRepG0[state]);
         if (repIndex == 1) {
            price += RangeEncoder.getPrice0(this._isRepG1[state]);
         } else {
            price += RangeEncoder.getPrice1(this._isRepG1[state]);
            price += RangeEncoder.getPrice(this._isRepG2[state], repIndex - 2);
         }
      }

      return price;
   }

   int getRepPrice(int repIndex, int len, int state, int posState) {
      int price = this._repMatchLenEncoder.getPrice(len - 2, posState);
      return price + this.getPureRepPrice(repIndex, state, posState);
   }

   int getPosLenPrice(int pos, int len, int posState) {
      int lenToPosState = Base.getLenToPosState(len);
      int price;
      if (pos < 128) {
         price = this._distancesPrices[lenToPosState * 128 + pos];
      } else {
         price = this._posSlotPrices[(lenToPosState << 6) + getPosSlot2(pos)] + this._alignPrices[pos & 15];
      }

      return price + this._lenEncoder.getPrice(len - 2, posState);
   }

   int backward(int cur) {
      this._optimumEndIndex = cur;
      int posMem = this._optimum[cur].PosPrev;
      int backMem = this._optimum[cur].BackPrev;

      int posPrev;
      do {
         if (this._optimum[cur].Prev1IsChar) {
            this._optimum[posMem].makeAsChar();
            this._optimum[posMem].PosPrev = posMem - 1;
            if (this._optimum[cur].Prev2) {
               this._optimum[posMem - 1].Prev1IsChar = false;
               this._optimum[posMem - 1].PosPrev = this._optimum[cur].PosPrev2;
               this._optimum[posMem - 1].BackPrev = this._optimum[cur].BackPrev2;
            }
         }

         posPrev = posMem;
         int backCur = backMem;
         backMem = this._optimum[posMem].BackPrev;
         posMem = this._optimum[posMem].PosPrev;
         this._optimum[posPrev].BackPrev = backCur;
         this._optimum[posPrev].PosPrev = cur;
         cur = posPrev;
      } while(posPrev > 0);

      this.backRes = this._optimum[0].BackPrev;
      this._optimumCurrentIndex = this._optimum[0].PosPrev;
      return this._optimumCurrentIndex;
   }

   int getOptimum(int position) throws IOException {
      int lenMain;
      if (this._optimumEndIndex != this._optimumCurrentIndex) {
         lenMain = this._optimum[this._optimumCurrentIndex].PosPrev - this._optimumCurrentIndex;
         this.backRes = this._optimum[this._optimumCurrentIndex].BackPrev;
         this._optimumCurrentIndex = this._optimum[this._optimumCurrentIndex].PosPrev;
         return lenMain;
      } else {
         this._optimumCurrentIndex = this._optimumEndIndex = 0;
         if (!this._longestMatchWasFound) {
            lenMain = this.readMatchDistances();
         } else {
            lenMain = this._longestMatchLength;
            this._longestMatchWasFound = false;
         }

         int numDistancePairs = this._numDistancePairs;
         int numAvailableBytes = this._matchFinder.getNumAvailableBytes() + 1;
         if (numAvailableBytes < 2) {
            this.backRes = -1;
            return 1;
         } else {
            if (numAvailableBytes > 273) {
               int numAvailableBytes = true;
            }

            int repMaxIndex = 0;

            int i;
            for(i = 0; i < 4; ++i) {
               this.reps[i] = this._repDistances[i];
               this.repLens[i] = this._matchFinder.getMatchLen(-1, this.reps[i], 273);
               if (this.repLens[i] > this.repLens[repMaxIndex]) {
                  repMaxIndex = i;
               }
            }

            int currentByte;
            if (this.repLens[repMaxIndex] >= this._numFastBytes) {
               this.backRes = repMaxIndex;
               currentByte = this.repLens[repMaxIndex];
               this.movePos(currentByte - 1);
               return currentByte;
            } else if (lenMain >= this._numFastBytes) {
               this.backRes = this._matchDistances[numDistancePairs - 1] + 4;
               this.movePos(lenMain - 1);
               return lenMain;
            } else {
               currentByte = this._matchFinder.getIndexByte(-1);
               byte matchByte = this._matchFinder.getIndexByte(0 - this._repDistances[0] - 1 - 1);
               if (lenMain < 2 && currentByte != matchByte && this.repLens[repMaxIndex] < 2) {
                  this.backRes = -1;
                  return 1;
               } else {
                  this._optimum[0].State = this._state;
                  int posState = position & this._posStateMask;
                  this._optimum[1].Price = RangeEncoder.getPrice0(this._isMatch[(this._state << 4) + posState]) + this._literalEncoder.getSubCoder(position, this._previousByte).getPrice(!Base.stateIsCharState(this._state), matchByte, (byte)currentByte);
                  this._optimum[1].makeAsChar();
                  int matchPrice = RangeEncoder.getPrice1(this._isMatch[(this._state << 4) + posState]);
                  int repMatchPrice = matchPrice + RangeEncoder.getPrice1(this._isRep[this._state]);
                  int lenEnd;
                  if (matchByte == currentByte) {
                     lenEnd = repMatchPrice + this.getRepLen1Price(this._state, posState);
                     if (lenEnd < this._optimum[1].Price) {
                        this._optimum[1].Price = lenEnd;
                        this._optimum[1].makeAsShortRep();
                     }
                  }

                  lenEnd = lenMain >= this.repLens[repMaxIndex] ? lenMain : this.repLens[repMaxIndex];
                  if (lenEnd < 2) {
                     this.backRes = this._optimum[1].BackPrev;
                     return 1;
                  } else {
                     this._optimum[1].PosPrev = 0;
                     this._optimum[0].Backs0 = this.reps[0];
                     this._optimum[0].Backs1 = this.reps[1];
                     this._optimum[0].Backs2 = this.reps[2];
                     this._optimum[0].Backs3 = this.reps[3];
                     int len = lenEnd;

                     do {
                        this._optimum[len--].Price = 268435455;
                     } while(len >= 2);

                     int normalMatchPrice;
                     int cur;
                     int newLen;
                     for(i = 0; i < 4; ++i) {
                        normalMatchPrice = this.repLens[i];
                        if (normalMatchPrice >= 2) {
                           cur = repMatchPrice + this.getPureRepPrice(i, this._state, posState);

                           do {
                              newLen = cur + this._repMatchLenEncoder.getPrice(normalMatchPrice - 2, posState);
                              Optimal optimum = this._optimum[normalMatchPrice];
                              if (newLen < optimum.Price) {
                                 optimum.Price = newLen;
                                 optimum.PosPrev = 0;
                                 optimum.BackPrev = i;
                                 optimum.Prev1IsChar = false;
                              }

                              --normalMatchPrice;
                           } while(normalMatchPrice >= 2);
                        }
                     }

                     normalMatchPrice = matchPrice + RangeEncoder.getPrice0(this._isRep[this._state]);
                     len = this.repLens[0] >= 2 ? this.repLens[0] + 1 : 2;
                     int posPrev;
                     if (len <= lenMain) {
                        for(cur = 0; len > this._matchDistances[cur]; cur += 2) {
                        }

                        while(true) {
                           newLen = this._matchDistances[cur + 1];
                           posPrev = normalMatchPrice + this.getPosLenPrice(newLen, len, posState);
                           Optimal optimum = this._optimum[len];
                           if (posPrev < optimum.Price) {
                              optimum.Price = posPrev;
                              optimum.PosPrev = 0;
                              optimum.BackPrev = newLen + 4;
                              optimum.Prev1IsChar = false;
                           }

                           if (len == this._matchDistances[cur]) {
                              cur += 2;
                              if (cur == numDistancePairs) {
                                 break;
                              }
                           }

                           ++len;
                        }
                     }

                     cur = 0;

                     while(true) {
                        ++cur;
                        if (cur == lenEnd) {
                           return this.backward(cur);
                        }

                        newLen = this.readMatchDistances();
                        numDistancePairs = this._numDistancePairs;
                        if (newLen >= this._numFastBytes) {
                           this._longestMatchLength = newLen;
                           this._longestMatchWasFound = true;
                           return this.backward(cur);
                        }

                        ++position;
                        posPrev = this._optimum[cur].PosPrev;
                        int state;
                        if (this._optimum[cur].Prev1IsChar) {
                           --posPrev;
                           if (this._optimum[cur].Prev2) {
                              state = this._optimum[this._optimum[cur].PosPrev2].State;
                              if (this._optimum[cur].BackPrev2 < 4) {
                                 state = Base.stateUpdateRep(state);
                              } else {
                                 state = Base.stateUpdateMatch(state);
                              }
                           } else {
                              state = this._optimum[posPrev].State;
                           }

                           state = Base.stateUpdateChar(state);
                        } else {
                           state = this._optimum[posPrev].State;
                        }

                        int pos;
                        if (posPrev == cur - 1) {
                           if (this._optimum[cur].isShortRep()) {
                              state = Base.stateUpdateShortRep(state);
                           } else {
                              state = Base.stateUpdateChar(state);
                           }
                        } else {
                           if (this._optimum[cur].Prev1IsChar && this._optimum[cur].Prev2) {
                              posPrev = this._optimum[cur].PosPrev2;
                              pos = this._optimum[cur].BackPrev2;
                              state = Base.stateUpdateRep(state);
                           } else {
                              pos = this._optimum[cur].BackPrev;
                              if (pos < 4) {
                                 state = Base.stateUpdateRep(state);
                              } else {
                                 state = Base.stateUpdateMatch(state);
                              }
                           }

                           Optimal opt = this._optimum[posPrev];
                           if (pos < 4) {
                              if (pos == 0) {
                                 this.reps[0] = opt.Backs0;
                                 this.reps[1] = opt.Backs1;
                                 this.reps[2] = opt.Backs2;
                                 this.reps[3] = opt.Backs3;
                              } else if (pos == 1) {
                                 this.reps[0] = opt.Backs1;
                                 this.reps[1] = opt.Backs0;
                                 this.reps[2] = opt.Backs2;
                                 this.reps[3] = opt.Backs3;
                              } else if (pos == 2) {
                                 this.reps[0] = opt.Backs2;
                                 this.reps[1] = opt.Backs0;
                                 this.reps[2] = opt.Backs1;
                                 this.reps[3] = opt.Backs3;
                              } else {
                                 this.reps[0] = opt.Backs3;
                                 this.reps[1] = opt.Backs0;
                                 this.reps[2] = opt.Backs1;
                                 this.reps[3] = opt.Backs2;
                              }
                           } else {
                              this.reps[0] = pos - 4;
                              this.reps[1] = opt.Backs0;
                              this.reps[2] = opt.Backs1;
                              this.reps[3] = opt.Backs2;
                           }
                        }

                        this._optimum[cur].State = state;
                        this._optimum[cur].Backs0 = this.reps[0];
                        this._optimum[cur].Backs1 = this.reps[1];
                        this._optimum[cur].Backs2 = this.reps[2];
                        this._optimum[cur].Backs3 = this.reps[3];
                        pos = this._optimum[cur].Price;
                        currentByte = this._matchFinder.getIndexByte(-1);
                        matchByte = this._matchFinder.getIndexByte(0 - this.reps[0] - 1 - 1);
                        posState = position & this._posStateMask;
                        int curAnd1Price = pos + RangeEncoder.getPrice0(this._isMatch[(state << 4) + posState]) + this._literalEncoder.getSubCoder(position, this._matchFinder.getIndexByte(-2)).getPrice(!Base.stateIsCharState(state), matchByte, (byte)currentByte);
                        Optimal nextOptimum = this._optimum[cur + 1];
                        boolean nextIsChar = false;
                        if (curAnd1Price < nextOptimum.Price) {
                           nextOptimum.Price = curAnd1Price;
                           nextOptimum.PosPrev = cur;
                           nextOptimum.makeAsChar();
                           nextIsChar = true;
                        }

                        matchPrice = pos + RangeEncoder.getPrice1(this._isMatch[(state << 4) + posState]);
                        repMatchPrice = matchPrice + RangeEncoder.getPrice1(this._isRep[state]);
                        int numAvailableBytesFull;
                        if (matchByte == currentByte && (nextOptimum.PosPrev >= cur || nextOptimum.BackPrev != 0)) {
                           numAvailableBytesFull = repMatchPrice + this.getRepLen1Price(state, posState);
                           if (numAvailableBytesFull <= nextOptimum.Price) {
                              nextOptimum.Price = numAvailableBytesFull;
                              nextOptimum.PosPrev = cur;
                              nextOptimum.makeAsShortRep();
                              nextIsChar = true;
                           }
                        }

                        numAvailableBytesFull = this._matchFinder.getNumAvailableBytes() + 1;
                        numAvailableBytesFull = Math.min(4095 - cur, numAvailableBytesFull);
                        numAvailableBytes = numAvailableBytesFull;
                        if (numAvailableBytesFull >= 2) {
                           if (numAvailableBytesFull > this._numFastBytes) {
                              numAvailableBytes = this._numFastBytes;
                           }

                           int startLen;
                           int repIndex;
                           int lenTest;
                           int curBack;
                           int curAndLenPrice;
                           int lenTest2;
                           int state2;
                           if (!nextIsChar && matchByte != currentByte) {
                              startLen = Math.min(numAvailableBytesFull - 1, this._numFastBytes);
                              repIndex = this._matchFinder.getMatchLen(0, this.reps[0], startLen);
                              if (repIndex >= 2) {
                                 lenTest = Base.stateUpdateChar(state);
                                 curBack = position + 1 & this._posStateMask;
                                 curAndLenPrice = curAnd1Price + RangeEncoder.getPrice1(this._isMatch[(lenTest << 4) + curBack]) + RangeEncoder.getPrice1(this._isRep[lenTest]);

                                 for(lenTest2 = cur + 1 + repIndex; lenEnd < lenTest2; this._optimum[lenEnd].Price = 268435455) {
                                    ++lenEnd;
                                 }

                                 state2 = curAndLenPrice + this.getRepPrice(0, repIndex, lenTest, curBack);
                                 Optimal optimum = this._optimum[lenTest2];
                                 if (state2 < optimum.Price) {
                                    optimum.Price = state2;
                                    optimum.PosPrev = cur + 1;
                                    optimum.BackPrev = 0;
                                    optimum.Prev1IsChar = true;
                                    optimum.Prev2 = false;
                                 }
                              }
                           }

                           startLen = 2;

                           int state2;
                           int posStateNext;
                           int curAndLenCharPrice;
                           int nextMatchPrice;
                           int nextRepMatchPrice;
                           Optimal optimum;
                           int lenTest2;
                           label284:
                           for(repIndex = 0; repIndex < 4; ++repIndex) {
                              lenTest = this._matchFinder.getMatchLen(-1, this.reps[repIndex], numAvailableBytes);
                              if (lenTest >= 2) {
                                 curBack = lenTest;

                                 while(true) {
                                    while(lenEnd >= cur + lenTest) {
                                       curAndLenPrice = repMatchPrice + this.getRepPrice(repIndex, lenTest, state, posState);
                                       optimum = this._optimum[cur + lenTest];
                                       if (curAndLenPrice < optimum.Price) {
                                          optimum.Price = curAndLenPrice;
                                          optimum.PosPrev = cur;
                                          optimum.BackPrev = repIndex;
                                          optimum.Prev1IsChar = false;
                                       }

                                       --lenTest;
                                       if (lenTest < 2) {
                                          if (repIndex == 0) {
                                             startLen = curBack + 1;
                                          }

                                          if (curBack >= numAvailableBytesFull) {
                                             continue label284;
                                          }

                                          curAndLenPrice = Math.min(numAvailableBytesFull - 1 - curBack, this._numFastBytes);
                                          lenTest2 = this._matchFinder.getMatchLen(curBack, this.reps[repIndex], curAndLenPrice);
                                          if (lenTest2 < 2) {
                                             continue label284;
                                          }

                                          state2 = Base.stateUpdateRep(state);
                                          lenTest2 = position + curBack & this._posStateMask;
                                          state2 = repMatchPrice + this.getRepPrice(repIndex, curBack, state, posState) + RangeEncoder.getPrice0(this._isMatch[(state2 << 4) + lenTest2]) + this._literalEncoder.getSubCoder(position + curBack, this._matchFinder.getIndexByte(curBack - 1 - 1)).getPrice(true, this._matchFinder.getIndexByte(curBack - 1 - (this.reps[repIndex] + 1)), this._matchFinder.getIndexByte(curBack - 1));
                                          state2 = Base.stateUpdateChar(state2);
                                          lenTest2 = position + curBack + 1 & this._posStateMask;
                                          posStateNext = state2 + RangeEncoder.getPrice1(this._isMatch[(state2 << 4) + lenTest2]);
                                          curAndLenCharPrice = posStateNext + RangeEncoder.getPrice1(this._isRep[state2]);

                                          for(nextMatchPrice = curBack + 1 + lenTest2; lenEnd < cur + nextMatchPrice; this._optimum[lenEnd].Price = 268435455) {
                                             ++lenEnd;
                                          }

                                          nextRepMatchPrice = curAndLenCharPrice + this.getRepPrice(0, lenTest2, state2, lenTest2);
                                          Optimal optimum = this._optimum[cur + nextMatchPrice];
                                          if (nextRepMatchPrice < optimum.Price) {
                                             optimum.Price = nextRepMatchPrice;
                                             optimum.PosPrev = cur + curBack + 1;
                                             optimum.BackPrev = 0;
                                             optimum.Prev1IsChar = true;
                                             optimum.Prev2 = true;
                                             optimum.PosPrev2 = cur;
                                             optimum.BackPrev2 = repIndex;
                                          }
                                          continue label284;
                                       }
                                    }

                                    ++lenEnd;
                                    this._optimum[lenEnd].Price = 268435455;
                                 }
                              }
                           }

                           if (newLen > numAvailableBytes) {
                              newLen = numAvailableBytes;

                              for(numDistancePairs = 0; newLen > this._matchDistances[numDistancePairs]; numDistancePairs += 2) {
                              }

                              this._matchDistances[numDistancePairs] = newLen;
                              numDistancePairs += 2;
                           }

                           if (newLen >= startLen) {
                              for(normalMatchPrice = matchPrice + RangeEncoder.getPrice0(this._isRep[state]); lenEnd < cur + newLen; this._optimum[lenEnd].Price = 268435455) {
                                 ++lenEnd;
                              }

                              for(repIndex = 0; startLen > this._matchDistances[repIndex]; repIndex += 2) {
                              }

                              lenTest = startLen;

                              while(true) {
                                 curBack = this._matchDistances[repIndex + 1];
                                 curAndLenPrice = normalMatchPrice + this.getPosLenPrice(curBack, lenTest, posState);
                                 optimum = this._optimum[cur + lenTest];
                                 if (curAndLenPrice < optimum.Price) {
                                    optimum.Price = curAndLenPrice;
                                    optimum.PosPrev = cur;
                                    optimum.BackPrev = curBack + 4;
                                    optimum.Prev1IsChar = false;
                                 }

                                 if (lenTest == this._matchDistances[repIndex]) {
                                    if (lenTest < numAvailableBytesFull) {
                                       state2 = Math.min(numAvailableBytesFull - 1 - lenTest, this._numFastBytes);
                                       lenTest2 = this._matchFinder.getMatchLen(lenTest, curBack, state2);
                                       if (lenTest2 >= 2) {
                                          state2 = Base.stateUpdateMatch(state);
                                          posStateNext = position + lenTest & this._posStateMask;
                                          curAndLenCharPrice = curAndLenPrice + RangeEncoder.getPrice0(this._isMatch[(state2 << 4) + posStateNext]) + this._literalEncoder.getSubCoder(position + lenTest, this._matchFinder.getIndexByte(lenTest - 1 - 1)).getPrice(true, this._matchFinder.getIndexByte(lenTest - (curBack + 1) - 1), this._matchFinder.getIndexByte(lenTest - 1));
                                          state2 = Base.stateUpdateChar(state2);
                                          posStateNext = position + lenTest + 1 & this._posStateMask;
                                          nextMatchPrice = curAndLenCharPrice + RangeEncoder.getPrice1(this._isMatch[(state2 << 4) + posStateNext]);
                                          nextRepMatchPrice = nextMatchPrice + RangeEncoder.getPrice1(this._isRep[state2]);

                                          int offset;
                                          for(offset = lenTest + 1 + lenTest2; lenEnd < cur + offset; this._optimum[lenEnd].Price = 268435455) {
                                             ++lenEnd;
                                          }

                                          curAndLenPrice = nextRepMatchPrice + this.getRepPrice(0, lenTest2, state2, posStateNext);
                                          optimum = this._optimum[cur + offset];
                                          if (curAndLenPrice < optimum.Price) {
                                             optimum.Price = curAndLenPrice;
                                             optimum.PosPrev = cur + lenTest + 1;
                                             optimum.BackPrev = 0;
                                             optimum.Prev1IsChar = true;
                                             optimum.Prev2 = true;
                                             optimum.PosPrev2 = cur;
                                             optimum.BackPrev2 = curBack + 4;
                                          }
                                       }
                                    }

                                    repIndex += 2;
                                    if (repIndex == numDistancePairs) {
                                       break;
                                    }
                                 }

                                 ++lenTest;
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   boolean changePair(int smallDist, int bigDist) {
      int kDif = 7;
      return smallDist < 1 << 32 - kDif && bigDist >= smallDist << kDif;
   }

   void writeEndMarker(int posState) throws IOException {
      if (this._writeEndMark) {
         this._rangeEncoder.encode(this._isMatch, (this._state << 4) + posState, 1);
         this._rangeEncoder.encode(this._isRep, this._state, 0);
         this._state = Base.stateUpdateMatch(this._state);
         int len = 2;
         this._lenEncoder.encode(this._rangeEncoder, len - 2, posState);
         int posSlot = 63;
         int lenToPosState = Base.getLenToPosState(len);
         this._posSlotEncoder[lenToPosState].encode(this._rangeEncoder, posSlot);
         int footerBits = 30;
         int posReduced = (1 << footerBits) - 1;
         this._rangeEncoder.encodeDirectBits(posReduced >> 4, footerBits - 4);
         this._posAlignEncoder.reverseEncode(this._rangeEncoder, posReduced & 15);
      }
   }

   void flush(int nowPos) throws IOException {
      this.releaseMFBuffer();
      this.writeEndMarker(nowPos & this._posStateMask);
      this._rangeEncoder.flushData();
   }

   public void codeOneBlock(long[] inSize, long[] outSize, boolean[] finished) throws IOException {
      inSize[0] = 0L;
      outSize[0] = 0L;
      finished[0] = true;
      if (this._src != null) {
         this._matchFinder.setBuffer(this._src);
         this._matchFinder.init();
         this._needReleaseMFStream = true;
         this._src = null;
      }

      if (!this._finished) {
         this._finished = true;
         long progressPosValuePrev = this.nowPos64;
         int len;
         int pos;
         if (this.nowPos64 == 0L) {
            if (this._matchFinder.getNumAvailableBytes() == 0) {
               this.flush((int)this.nowPos64);
               return;
            }

            this.readMatchDistances();
            len = (int)this.nowPos64 & this._posStateMask;
            this._rangeEncoder.encode(this._isMatch, (this._state << 4) + len, 0);
            this._state = Base.stateUpdateChar(this._state);
            pos = this._matchFinder.getIndexByte(0 - this._additionalOffset);
            this._literalEncoder.getSubCoder((int)this.nowPos64, this._previousByte).encode(this._rangeEncoder, (byte)pos);
            this._previousByte = (byte)pos;
            --this._additionalOffset;
            ++this.nowPos64;
         }

         if (this._matchFinder.getNumAvailableBytes() == 0) {
            this.flush((int)this.nowPos64);
         } else {
            while(true) {
               len = this.getOptimum((int)this.nowPos64);
               pos = this.backRes;
               int posState = (int)this.nowPos64 & this._posStateMask;
               int complexState = (this._state << 4) + posState;
               if (len == 1 && pos == -1) {
                  this._rangeEncoder.encode(this._isMatch, complexState, 0);
                  byte curByte = this._matchFinder.getIndexByte(0 - this._additionalOffset);
                  LiteralEncoder.Encoder2 subCoder = this._literalEncoder.getSubCoder((int)this.nowPos64, this._previousByte);
                  if (!Base.stateIsCharState(this._state)) {
                     byte matchByte = this._matchFinder.getIndexByte(0 - this._repDistances[0] - 1 - this._additionalOffset);
                     subCoder.encodeMatched(this._rangeEncoder, matchByte, curByte);
                  } else {
                     subCoder.encode(this._rangeEncoder, curByte);
                  }

                  this._previousByte = curByte;
                  this._state = Base.stateUpdateChar(this._state);
               } else {
                  this._rangeEncoder.encode(this._isMatch, complexState, 1);
                  int distance;
                  if (pos < 4) {
                     this._rangeEncoder.encode(this._isRep, this._state, 1);
                     if (pos == 0) {
                        this._rangeEncoder.encode(this._isRepG0, this._state, 0);
                        if (len == 1) {
                           this._rangeEncoder.encode(this._isRep0Long, complexState, 0);
                        } else {
                           this._rangeEncoder.encode(this._isRep0Long, complexState, 1);
                        }
                     } else {
                        this._rangeEncoder.encode(this._isRepG0, this._state, 1);
                        if (pos == 1) {
                           this._rangeEncoder.encode(this._isRepG1, this._state, 0);
                        } else {
                           this._rangeEncoder.encode(this._isRepG1, this._state, 1);
                           this._rangeEncoder.encode(this._isRepG2, this._state, pos - 2);
                        }
                     }

                     if (len == 1) {
                        this._state = Base.stateUpdateShortRep(this._state);
                     } else {
                        this._repMatchLenEncoder.encode(this._rangeEncoder, len - 2, posState);
                        this._state = Base.stateUpdateRep(this._state);
                     }

                     distance = this._repDistances[pos];
                     if (pos != 0) {
                        System.arraycopy(this._repDistances, 0, this._repDistances, 1, pos);
                        this._repDistances[0] = distance;
                     }
                  } else {
                     this._rangeEncoder.encode(this._isRep, this._state, 0);
                     this._state = Base.stateUpdateMatch(this._state);
                     this._lenEncoder.encode(this._rangeEncoder, len - 2, posState);
                     pos -= 4;
                     distance = getPosSlot(pos);
                     int lenToPosState = Base.getLenToPosState(len);
                     this._posSlotEncoder[lenToPosState].encode(this._rangeEncoder, distance);
                     if (distance >= 4) {
                        int footerBits = (distance >> 1) - 1;
                        int baseVal = (2 | distance & 1) << footerBits;
                        int posReduced = pos - baseVal;
                        if (distance < 14) {
                           BitTreeEncoder.reverseEncode(this._posEncoders, baseVal - distance - 1, this._rangeEncoder, footerBits, posReduced);
                        } else {
                           this._rangeEncoder.encodeDirectBits(posReduced >> 4, footerBits - 4);
                           this._posAlignEncoder.reverseEncode(this._rangeEncoder, posReduced & 15);
                           ++this._alignPriceCount;
                        }
                     }

                     System.arraycopy(this._repDistances, 0, this._repDistances, 1, 3);
                     this._repDistances[0] = pos;
                     ++this._matchPriceCount;
                  }

                  this._previousByte = this._matchFinder.getIndexByte(len - 1 - this._additionalOffset);
               }

               this._additionalOffset -= len;
               this.nowPos64 += (long)len;
               if (this._additionalOffset == 0) {
                  if (this._matchPriceCount >= 128) {
                     this.fillDistancesPrices();
                  }

                  if (this._alignPriceCount >= 16) {
                     this.fillAlignPrices();
                  }

                  inSize[0] = this.nowPos64;
                  outSize[0] = this._rangeEncoder.getProcessedSizeAdd();
                  if (this._matchFinder.getNumAvailableBytes() == 0) {
                     this.flush((int)this.nowPos64);
                     return;
                  }

                  if (this.nowPos64 - progressPosValuePrev >= 4096L) {
                     this._finished = false;
                     finished[0] = false;
                     return;
                  }
               }
            }
         }
      }
   }

   void releaseMFBuffer() {
      if (this._matchFinder != null && this._needReleaseMFStream) {
         this._matchFinder.releaseBuffer();
         this._needReleaseMFStream = false;
      }

   }

   void setDstBuffer(Buffer dst, MemoryManager mm) {
      this._rangeEncoder.setBuffer(dst, mm);
   }

   Buffer releaseDstBuffer() {
      return this._rangeEncoder.releaseBuffer();
   }

   void releaseBuffers(LZMAEncoder.LZMAOutputState state) {
      this.releaseMFBuffer();
      state.setDst(this.releaseDstBuffer());
   }

   void setStreams(Buffer src, Buffer dst, MemoryManager mm, long inSize, long outSize) {
      this._src = src;
      this._finished = false;
      this.create();
      this.setDstBuffer(dst, mm);
      this.init();
      this.fillDistancesPrices();
      this.fillAlignPrices();
      this._lenEncoder.setTableSize(this._numFastBytes + 1 - 2);
      this._lenEncoder.updateTables(1 << this._posStateBits);
      this._repMatchLenEncoder.setTableSize(this._numFastBytes + 1 - 2);
      this._repMatchLenEncoder.updateTables(1 << this._posStateBits);
      this.nowPos64 = 0L;
   }

   public void code(LZMAEncoder.LZMAOutputState state, long inSize, long outSize) throws IOException {
      this._needReleaseMFStream = false;

      try {
         this.setStreams(state.getSrc(), state.getDst(), state.getMemoryManager(), inSize, outSize);

         do {
            this.codeOneBlock(this.processedInSize, this.processedOutSize, this.finished);
         } while(!this.finished[0]);
      } finally {
         this.releaseBuffers(state);
      }

   }

   public void writeCoderProperties(Buffer dst) throws IOException {
      dst.put((byte)((this._posStateBits * 5 + this._numLiteralPosStateBits) * 9 + this._numLiteralContextBits));

      for(int i = 0; i < 4; ++i) {
         dst.put((byte)(this._dictionarySize >> 8 * i));
      }

   }

   void fillDistancesPrices() {
      int lenToPosState;
      int posSlot;
      int st;
      for(lenToPosState = 4; lenToPosState < 128; ++lenToPosState) {
         posSlot = getPosSlot(lenToPosState);
         int footerBits = (posSlot >> 1) - 1;
         st = (2 | posSlot & 1) << footerBits;
         this.tempPrices[lenToPosState] = BitTreeEncoder.reverseGetPrice(this._posEncoders, st - posSlot - 1, footerBits, lenToPosState - st);
      }

      for(lenToPosState = 0; lenToPosState < 4; ++lenToPosState) {
         BitTreeEncoder encoder = this._posSlotEncoder[lenToPosState];
         st = lenToPosState << 6;

         for(posSlot = 0; posSlot < this._distTableSize; ++posSlot) {
            this._posSlotPrices[st + posSlot] = encoder.getPrice(posSlot);
         }

         for(posSlot = 14; posSlot < this._distTableSize; ++posSlot) {
            int[] var10000 = this._posSlotPrices;
            var10000[st + posSlot] += (posSlot >> 1) - 1 - 4 << 6;
         }

         int st2 = lenToPosState * 128;

         int i;
         for(i = 0; i < 4; ++i) {
            this._distancesPrices[st2 + i] = this._posSlotPrices[st + i];
         }

         while(i < 128) {
            this._distancesPrices[st2 + i] = this._posSlotPrices[st + getPosSlot(i)] + this.tempPrices[i];
            ++i;
         }
      }

      this._matchPriceCount = 0;
   }

   void fillAlignPrices() {
      for(int i = 0; i < 16; ++i) {
         this._alignPrices[i] = this._posAlignEncoder.reverseGetPrice(i);
      }

      this._alignPriceCount = 0;
   }

   public boolean setAlgorithm(int algorithm) {
      return true;
   }

   public boolean setDictionarySize(int dictionarySize) {
      int kDicLogSizeMaxCompress = 29;
      if (dictionarySize >= 1 && dictionarySize <= 1 << kDicLogSizeMaxCompress) {
         this._dictionarySize = dictionarySize;

         int dicLogSize;
         for(dicLogSize = 0; dictionarySize > 1 << dicLogSize; ++dicLogSize) {
         }

         this._distTableSize = dicLogSize * 2;
         return true;
      } else {
         return false;
      }
   }

   public boolean setNumFastBytes(int numFastBytes) {
      if (numFastBytes >= 5 && numFastBytes <= 273) {
         this._numFastBytes = numFastBytes;
         return true;
      } else {
         return false;
      }
   }

   public boolean setMatchFinder(int matchFinderIndex) {
      if (matchFinderIndex >= 0 && matchFinderIndex <= 2) {
         int matchFinderIndexPrev = this._matchFinderType;
         this._matchFinderType = matchFinderIndex;
         if (this._matchFinder != null && matchFinderIndexPrev != this._matchFinderType) {
            this._dictionarySizePrev = -1;
            this._matchFinder = null;
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean setLcLpPb(int lc, int lp, int pb) {
      if (lp >= 0 && lp <= 4 && lc >= 0 && lc <= 8 && pb >= 0 && pb <= 4) {
         this._numLiteralPosStateBits = lp;
         this._numLiteralContextBits = lc;
         this._posStateBits = pb;
         this._posStateMask = (1 << this._posStateBits) - 1;
         return true;
      } else {
         return false;
      }
   }

   public void setEndMarkerMode(boolean endMarkerMode) {
      this._writeEndMark = endMarkerMode;
   }

   static {
      int kFastSlots = 22;
      int c = 2;
      g_FastPos[0] = 0;
      g_FastPos[1] = 1;

      for(int slotFast = 2; slotFast < kFastSlots; ++slotFast) {
         int k = 1 << (slotFast >> 1) - 1;

         for(int j = 0; j < k; ++c) {
            g_FastPos[c] = (byte)slotFast;
            ++j;
         }
      }

   }

   static class Optimal {
      public int State;
      public boolean Prev1IsChar;
      public boolean Prev2;
      public int PosPrev2;
      public int BackPrev2;
      public int Price;
      public int PosPrev;
      public int BackPrev;
      public int Backs0;
      public int Backs1;
      public int Backs2;
      public int Backs3;

      public void makeAsChar() {
         this.BackPrev = -1;
         this.Prev1IsChar = false;
      }

      public void makeAsShortRep() {
         this.BackPrev = 0;
         this.Prev1IsChar = false;
      }

      public boolean isShortRep() {
         return this.BackPrev == 0;
      }
   }

   static class LenPriceTableEncoder extends LenEncoder {
      final int[] _prices = new int[4352];
      int _tableSize;
      final int[] _counters = new int[16];

      public void setTableSize(int tableSize) {
         this._tableSize = tableSize;
      }

      public int getPrice(int symbol, int posState) {
         return this._prices[posState * 272 + symbol];
      }

      void updateTable(int posState) {
         this.setPrices(posState, this._tableSize, this._prices, posState * 272);
         this._counters[posState] = this._tableSize;
      }

      public void updateTables(int numPosStates) {
         for(int posState = 0; posState < numPosStates; ++posState) {
            this.updateTable(posState);
         }

      }

      public void encode(RangeEncoder rangeEncoder, int symbol, int posState) throws IOException {
         super.encode(rangeEncoder, symbol, posState);
         if (--this._counters[posState] == 0) {
            this.updateTable(posState);
         }

      }
   }

   static class LenEncoder {
      final short[] _choice = new short[2];
      final BitTreeEncoder[] _lowCoder = new BitTreeEncoder[16];
      final BitTreeEncoder[] _midCoder = new BitTreeEncoder[16];
      final BitTreeEncoder _highCoder = new BitTreeEncoder(8);

      public LenEncoder() {
         for(int posState = 0; posState < 16; ++posState) {
            this._lowCoder[posState] = new BitTreeEncoder(3);
            this._midCoder[posState] = new BitTreeEncoder(3);
         }

      }

      public void init(int numPosStates) {
         RangeEncoder.initBitModels(this._choice);

         for(int posState = 0; posState < numPosStates; ++posState) {
            this._lowCoder[posState].init();
            this._midCoder[posState].init();
         }

         this._highCoder.init();
      }

      public void encode(RangeEncoder rangeEncoder, int symbol, int posState) throws IOException {
         if (symbol < 8) {
            rangeEncoder.encode(this._choice, 0, 0);
            this._lowCoder[posState].encode(rangeEncoder, symbol);
         } else {
            symbol -= 8;
            rangeEncoder.encode(this._choice, 0, 1);
            if (symbol < 8) {
               rangeEncoder.encode(this._choice, 1, 0);
               this._midCoder[posState].encode(rangeEncoder, symbol);
            } else {
               rangeEncoder.encode(this._choice, 1, 1);
               this._highCoder.encode(rangeEncoder, symbol - 8);
            }
         }

      }

      public void setPrices(int posState, int numSymbols, int[] prices, int st) {
         int a0 = RangeEncoder.getPrice0(this._choice[0]);
         int a1 = RangeEncoder.getPrice1(this._choice[0]);
         int b0 = a1 + RangeEncoder.getPrice0(this._choice[1]);
         int b1 = a1 + RangeEncoder.getPrice1(this._choice[1]);

         int i;
         for(i = 0; i < 8; ++i) {
            if (i >= numSymbols) {
               return;
            }

            prices[st + i] = a0 + this._lowCoder[posState].getPrice(i);
         }

         while(i < 16) {
            if (i >= numSymbols) {
               return;
            }

            prices[st + i] = b0 + this._midCoder[posState].getPrice(i - 8);
            ++i;
         }

         while(i < numSymbols) {
            prices[st + i] = b1 + this._highCoder.getPrice(i - 8 - 8);
            ++i;
         }

      }
   }

   static class LiteralEncoder {
      Encoder2[] m_Coders;
      int m_NumPrevBits;
      int m_NumPosBits;
      int m_PosMask;

      public void create(int numPosBits, int numPrevBits) {
         if (this.m_Coders == null || this.m_NumPrevBits != numPrevBits || this.m_NumPosBits != numPosBits) {
            this.m_NumPosBits = numPosBits;
            this.m_PosMask = (1 << numPosBits) - 1;
            this.m_NumPrevBits = numPrevBits;
            int numStates = 1 << this.m_NumPrevBits + this.m_NumPosBits;
            this.m_Coders = new Encoder2[numStates];

            for(int i = 0; i < numStates; ++i) {
               this.m_Coders[i] = new Encoder2();
            }

         }
      }

      public void init() {
         int numStates = 1 << this.m_NumPrevBits + this.m_NumPosBits;

         for(int i = 0; i < numStates; ++i) {
            this.m_Coders[i].init();
         }

      }

      public Encoder2 getSubCoder(int pos, byte prevByte) {
         return this.m_Coders[((pos & this.m_PosMask) << this.m_NumPrevBits) + ((prevByte & 255) >>> 8 - this.m_NumPrevBits)];
      }

      static class Encoder2 {
         final short[] m_Encoders = new short[768];

         public void init() {
            RangeEncoder.initBitModels(this.m_Encoders);
         }

         public void encode(RangeEncoder rangeEncoder, byte symbol) throws IOException {
            int context = 1;

            for(int i = 7; i >= 0; --i) {
               int bit = symbol >> i & 1;
               rangeEncoder.encode(this.m_Encoders, context, bit);
               context = context << 1 | bit;
            }

         }

         public void encodeMatched(RangeEncoder rangeEncoder, byte matchByte, byte symbol) throws IOException {
            int context = 1;
            boolean same = true;

            for(int i = 7; i >= 0; --i) {
               int bit = symbol >> i & 1;
               int state = context;
               if (same) {
                  int matchBit = matchByte >> i & 1;
                  state = context + (1 + matchBit << 8);
                  same = matchBit == bit;
               }

               rangeEncoder.encode(this.m_Encoders, state, bit);
               context = context << 1 | bit;
            }

         }

         public int getPrice(boolean matchMode, byte matchByte, byte symbol) {
            int price = 0;
            int context = 1;
            int i = 7;
            int matchBit;
            if (matchMode) {
               while(i >= 0) {
                  matchBit = matchByte >> i & 1;
                  int bit = symbol >> i & 1;
                  price += RangeEncoder.getPrice(this.m_Encoders[(1 + matchBit << 8) + context], bit);
                  context = context << 1 | bit;
                  if (matchBit != bit) {
                     --i;
                     break;
                  }

                  --i;
               }
            }

            while(i >= 0) {
               matchBit = symbol >> i & 1;
               price += RangeEncoder.getPrice(this.m_Encoders[context], matchBit);
               context = context << 1 | matchBit;
               --i;
            }

            return price;
         }
      }
   }
}
