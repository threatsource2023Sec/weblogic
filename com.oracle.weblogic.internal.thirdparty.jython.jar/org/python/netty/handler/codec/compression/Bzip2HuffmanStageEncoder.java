package org.python.netty.handler.codec.compression;

import java.util.Arrays;
import org.python.netty.buffer.ByteBuf;

final class Bzip2HuffmanStageEncoder {
   private static final int HUFFMAN_HIGH_SYMBOL_COST = 15;
   private final Bzip2BitWriter writer;
   private final char[] mtfBlock;
   private final int mtfLength;
   private final int mtfAlphabetSize;
   private final int[] mtfSymbolFrequencies;
   private final int[][] huffmanCodeLengths;
   private final int[][] huffmanMergedCodeSymbols;
   private final byte[] selectors;

   Bzip2HuffmanStageEncoder(Bzip2BitWriter writer, char[] mtfBlock, int mtfLength, int mtfAlphabetSize, int[] mtfSymbolFrequencies) {
      this.writer = writer;
      this.mtfBlock = mtfBlock;
      this.mtfLength = mtfLength;
      this.mtfAlphabetSize = mtfAlphabetSize;
      this.mtfSymbolFrequencies = mtfSymbolFrequencies;
      int totalTables = selectTableCount(mtfLength);
      this.huffmanCodeLengths = new int[totalTables][mtfAlphabetSize];
      this.huffmanMergedCodeSymbols = new int[totalTables][mtfAlphabetSize];
      this.selectors = new byte[(mtfLength + 50 - 1) / 50];
   }

   private static int selectTableCount(int mtfLength) {
      if (mtfLength >= 2400) {
         return 6;
      } else if (mtfLength >= 1200) {
         return 5;
      } else if (mtfLength >= 600) {
         return 4;
      } else {
         return mtfLength >= 200 ? 3 : 2;
      }
   }

   private static void generateHuffmanCodeLengths(int alphabetSize, int[] symbolFrequencies, int[] codeLengths) {
      int[] mergedFrequenciesAndIndices = new int[alphabetSize];
      int[] sortedFrequencies = new int[alphabetSize];

      int i;
      for(i = 0; i < alphabetSize; ++i) {
         mergedFrequenciesAndIndices[i] = symbolFrequencies[i] << 9 | i;
      }

      Arrays.sort(mergedFrequenciesAndIndices);

      for(i = 0; i < alphabetSize; ++i) {
         sortedFrequencies[i] = mergedFrequenciesAndIndices[i] >>> 9;
      }

      Bzip2HuffmanAllocator.allocateHuffmanCodeLengths(sortedFrequencies, 20);

      for(i = 0; i < alphabetSize; ++i) {
         codeLengths[mergedFrequenciesAndIndices[i] & 511] = sortedFrequencies[i];
      }

   }

   private void generateHuffmanOptimisationSeeds() {
      int[][] huffmanCodeLengths = this.huffmanCodeLengths;
      int[] mtfSymbolFrequencies = this.mtfSymbolFrequencies;
      int mtfAlphabetSize = this.mtfAlphabetSize;
      int totalTables = huffmanCodeLengths.length;
      int remainingLength = this.mtfLength;
      int lowCostEnd = -1;

      for(int i = 0; i < totalTables; ++i) {
         int targetCumulativeFrequency = remainingLength / (totalTables - i);
         int lowCostStart = lowCostEnd + 1;

         int actualCumulativeFrequency;
         for(actualCumulativeFrequency = 0; actualCumulativeFrequency < targetCumulativeFrequency && lowCostEnd < mtfAlphabetSize - 1; actualCumulativeFrequency += mtfSymbolFrequencies[lowCostEnd]) {
            ++lowCostEnd;
         }

         if (lowCostEnd > lowCostStart && i != 0 && i != totalTables - 1 && (totalTables - i & 1) == 0) {
            actualCumulativeFrequency -= mtfSymbolFrequencies[lowCostEnd--];
         }

         int[] tableCodeLengths = huffmanCodeLengths[i];

         for(int j = 0; j < mtfAlphabetSize; ++j) {
            if (j < lowCostStart || j > lowCostEnd) {
               tableCodeLengths[j] = 15;
            }
         }

         remainingLength -= actualCumulativeFrequency;
      }

   }

   private void optimiseSelectorsAndHuffmanTables(boolean storeSelectors) {
      char[] mtfBlock = this.mtfBlock;
      byte[] selectors = this.selectors;
      int[][] huffmanCodeLengths = this.huffmanCodeLengths;
      int mtfLength = this.mtfLength;
      int mtfAlphabetSize = this.mtfAlphabetSize;
      int totalTables = huffmanCodeLengths.length;
      int[][] tableFrequencies = new int[totalTables][mtfAlphabetSize];
      int selectorIndex = 0;

      int groupStart;
      int groupEnd;
      for(groupStart = 0; groupStart < mtfLength; groupStart = groupEnd + 1) {
         groupEnd = Math.min(groupStart + 50, mtfLength) - 1;
         short[] cost = new short[totalTables];

         short bestCost;
         for(int i = groupStart; i <= groupEnd; ++i) {
            bestCost = (short)mtfBlock[i];

            for(int j = 0; j < totalTables; ++j) {
               cost[j] = (short)(cost[j] + huffmanCodeLengths[j][bestCost]);
            }
         }

         byte bestTable = 0;
         bestCost = cost[0];

         int i;
         for(byte i = 1; i < totalTables; ++i) {
            i = cost[i];
            if (i < bestCost) {
               bestCost = (short)i;
               bestTable = i;
            }
         }

         int[] bestGroupFrequencies = tableFrequencies[bestTable];

         for(i = groupStart; i <= groupEnd; ++i) {
            ++bestGroupFrequencies[mtfBlock[i]];
         }

         if (storeSelectors) {
            selectors[selectorIndex++] = bestTable;
         }
      }

      for(groupStart = 0; groupStart < totalTables; ++groupStart) {
         generateHuffmanCodeLengths(mtfAlphabetSize, tableFrequencies[groupStart], huffmanCodeLengths[groupStart]);
      }

   }

   private void assignHuffmanCodeSymbols() {
      int[][] huffmanMergedCodeSymbols = this.huffmanMergedCodeSymbols;
      int[][] huffmanCodeLengths = this.huffmanCodeLengths;
      int mtfAlphabetSize = this.mtfAlphabetSize;
      int totalTables = huffmanCodeLengths.length;

      for(int i = 0; i < totalTables; ++i) {
         int[] tableLengths = huffmanCodeLengths[i];
         int minimumLength = 32;
         int maximumLength = 0;

         int code;
         int j;
         for(code = 0; code < mtfAlphabetSize; ++code) {
            j = tableLengths[code];
            if (j > maximumLength) {
               maximumLength = j;
            }

            if (j < minimumLength) {
               minimumLength = j;
            }
         }

         code = 0;

         for(j = minimumLength; j <= maximumLength; ++j) {
            for(int k = 0; k < mtfAlphabetSize; ++k) {
               if ((huffmanCodeLengths[i][k] & 255) == j) {
                  huffmanMergedCodeSymbols[i][k] = j << 24 | code;
                  ++code;
               }
            }

            code <<= 1;
         }
      }

   }

   private void writeSelectorsAndHuffmanTables(ByteBuf out) {
      Bzip2BitWriter writer = this.writer;
      byte[] selectors = this.selectors;
      int totalSelectors = selectors.length;
      int[][] huffmanCodeLengths = this.huffmanCodeLengths;
      int totalTables = huffmanCodeLengths.length;
      int mtfAlphabetSize = this.mtfAlphabetSize;
      writer.writeBits(out, 3, (long)totalTables);
      writer.writeBits(out, 15, (long)totalSelectors);
      Bzip2MoveToFrontTable selectorMTF = new Bzip2MoveToFrontTable();
      byte[] var9 = selectors;
      int var10 = selectors.length;

      int var11;
      for(var11 = 0; var11 < var10; ++var11) {
         byte selector = var9[var11];
         writer.writeUnary(out, selectorMTF.valueToFront(selector));
      }

      int[][] var18 = huffmanCodeLengths;
      var10 = huffmanCodeLengths.length;

      for(var11 = 0; var11 < var10; ++var11) {
         int[] tableLengths = var18[var11];
         int currentLength = tableLengths[0];
         writer.writeBits(out, 5, (long)currentLength);

         for(int j = 0; j < mtfAlphabetSize; ++j) {
            int codeLength = tableLengths[j];
            int value = currentLength < codeLength ? 2 : 3;
            int delta = Math.abs(codeLength - currentLength);

            while(delta-- > 0) {
               writer.writeBits(out, 2, (long)value);
            }

            writer.writeBoolean(out, false);
            currentLength = codeLength;
         }
      }

   }

   private void writeBlockData(ByteBuf out) {
      Bzip2BitWriter writer = this.writer;
      int[][] huffmanMergedCodeSymbols = this.huffmanMergedCodeSymbols;
      byte[] selectors = this.selectors;
      char[] mtf = this.mtfBlock;
      int mtfLength = this.mtfLength;
      int selectorIndex = 0;
      int mtfIndex = 0;

      while(mtfIndex < mtfLength) {
         int groupEnd = Math.min(mtfIndex + 50, mtfLength) - 1;
         int[] tableMergedCodeSymbols = huffmanMergedCodeSymbols[selectors[selectorIndex++]];

         while(mtfIndex <= groupEnd) {
            int mergedCodeSymbol = tableMergedCodeSymbols[mtf[mtfIndex++]];
            writer.writeBits(out, mergedCodeSymbol >>> 24, (long)mergedCodeSymbol);
         }
      }

   }

   void encode(ByteBuf out) {
      this.generateHuffmanOptimisationSeeds();

      for(int i = 3; i >= 0; --i) {
         this.optimiseSelectorsAndHuffmanTables(i == 0);
      }

      this.assignHuffmanCodeSymbols();
      this.writeSelectorsAndHuffmanTables(out);
      this.writeBlockData(out);
   }
}
