package org.python.icu.impl.coll;

import java.text.ParseException;
import org.python.icu.impl.Norm2AllModes;
import org.python.icu.impl.Normalizer2Impl;
import org.python.icu.text.CanonicalIterator;
import org.python.icu.text.Normalizer2;
import org.python.icu.text.UnicodeSet;
import org.python.icu.text.UnicodeSetIterator;
import org.python.icu.util.ULocale;

public final class CollationBuilder extends CollationRuleParser.Sink {
   private static final boolean DEBUG = false;
   private static final UnicodeSet COMPOSITES = new UnicodeSet("[:NFD_QC=N:]");
   private static final int MAX_INDEX = 1048575;
   private static final int HAS_BEFORE2 = 64;
   private static final int HAS_BEFORE3 = 32;
   private static final int IS_TAILORED = 8;
   private Normalizer2 nfd = Normalizer2.getNFDInstance();
   private Normalizer2 fcd = Norm2AllModes.getFCDNormalizer2();
   private Normalizer2Impl nfcImpl;
   private CollationTailoring base;
   private CollationData baseData;
   private CollationRootElements rootElements;
   private long variableTop;
   private CollationDataBuilder dataBuilder;
   private boolean fastLatinEnabled;
   private UnicodeSet optimizeSet = new UnicodeSet();
   private long[] ces = new long[31];
   private int cesLength;
   private UVector32 rootPrimaryIndexes;
   private UVector64 nodes;

   public CollationBuilder(CollationTailoring b) {
      this.nfcImpl = Norm2AllModes.getNFCInstance().impl;
      this.base = b;
      this.baseData = b.data;
      this.rootElements = new CollationRootElements(b.data.rootElements);
      this.variableTop = 0L;
      this.dataBuilder = new CollationDataBuilder();
      this.fastLatinEnabled = true;
      this.cesLength = 0;
      this.rootPrimaryIndexes = new UVector32();
      this.nodes = new UVector64();
      this.nfcImpl.ensureCanonIterData();
      this.dataBuilder.initForTailoring(this.baseData);
   }

   public CollationTailoring parseAndBuild(String ruleString) throws ParseException {
      if (this.baseData.rootElements == null) {
         throw new UnsupportedOperationException("missing root elements data, tailoring not supported");
      } else {
         CollationTailoring tailoring = new CollationTailoring(this.base.settings);
         CollationRuleParser parser = new CollationRuleParser(this.baseData);
         this.variableTop = ((CollationSettings)this.base.settings.readOnly()).variableTop;
         parser.setSink(this);
         parser.setImporter(new BundleImporter());
         CollationSettings ownedSettings = (CollationSettings)tailoring.settings.copyOnWrite();
         parser.parse(ruleString, ownedSettings);
         if (this.dataBuilder.hasMappings()) {
            this.makeTailoredCEs();
            this.closeOverComposites();
            this.finalizeCEs();
            this.optimizeSet.add(0, 127);
            this.optimizeSet.add(192, 255);
            this.optimizeSet.remove(44032, 55203);
            this.dataBuilder.optimize(this.optimizeSet);
            tailoring.ensureOwnedData();
            if (this.fastLatinEnabled) {
               this.dataBuilder.enableFastLatin();
            }

            this.dataBuilder.build(tailoring.ownedData);
            this.dataBuilder = null;
         } else {
            tailoring.data = this.baseData;
         }

         ownedSettings.fastLatinOptions = CollationFastLatin.getOptions(tailoring.data, ownedSettings, ownedSettings.fastLatinPrimaries);
         tailoring.setRules(ruleString);
         tailoring.setVersion(this.base.version, 0);
         return tailoring;
      }
   }

   void addReset(int strength, CharSequence str) {
      assert str.length() != 0;

      if (str.charAt(0) == '\ufffe') {
         this.ces[0] = this.getSpecialResetPosition(str);
         this.cesLength = 1;

         assert (this.ces[0] & 49344L) == 0L;
      } else {
         String nfdString = this.nfd.normalize(str);
         this.cesLength = this.dataBuilder.getCEs(nfdString, this.ces, 0);
         if (this.cesLength > 31) {
            throw new IllegalArgumentException("reset position maps to too many collation elements (more than 31)");
         }
      }

      if (strength != 15) {
         assert 0 <= strength && strength <= 2;

         int index = this.findOrInsertNodeForCEs(strength);

         long node;
         for(node = this.nodes.elementAti(index); strengthFromNode(node) > strength; node = this.nodes.elementAti(index)) {
            index = previousIndexFromNode(node);
         }

         if (strengthFromNode(node) == strength && isTailoredNode(node)) {
            index = previousIndexFromNode(node);
         } else {
            int previousIndex;
            if (strength == 0) {
               long p = weight32FromNode(node);
               if (p == 0L) {
                  throw new UnsupportedOperationException("reset primary-before ignorable not possible");
               }

               if (p <= this.rootElements.getFirstPrimary()) {
                  throw new UnsupportedOperationException("reset primary-before first non-ignorable not supported");
               }

               if (p == 4278321664L) {
                  throw new UnsupportedOperationException("reset primary-before [first trailing] not supported");
               }

               p = this.rootElements.getPrimaryBefore(p, this.baseData.isCompressiblePrimary(p));
               index = this.findOrInsertNodeForPrimary(p);

               while(true) {
                  node = this.nodes.elementAti(index);
                  previousIndex = nextIndexFromNode(node);
                  if (previousIndex == 0) {
                     break;
                  }

                  index = previousIndex;
               }
            } else {
               index = this.findCommonNode(index, 1);
               if (strength >= 2) {
                  index = this.findCommonNode(index, 2);
               }

               node = this.nodes.elementAti(index);
               int weight16;
               if (strengthFromNode(node) != strength) {
                  weight16 = this.getWeight16Before(index, node, strength);
                  index = this.findOrInsertWeakNode(index, weight16, strength);
               } else {
                  weight16 = weight16FromNode(node);
                  if (weight16 == 0) {
                     throw new UnsupportedOperationException(strength == 1 ? "reset secondary-before secondary ignorable not possible" : "reset tertiary-before completely ignorable not possible");
                  }

                  assert weight16 > 256;

                  weight16 = this.getWeight16Before(index, node, strength);
                  previousIndex = previousIndexFromNode(node);
                  int i = previousIndex;

                  int previousWeight16;
                  while(true) {
                     node = this.nodes.elementAti(i);
                     int previousStrength = strengthFromNode(node);
                     if (previousStrength < strength) {
                        assert weight16 >= 1280 || i == previousIndex;

                        previousWeight16 = 1280;
                        break;
                     }

                     if (previousStrength == strength && !isTailoredNode(node)) {
                        previousWeight16 = weight16FromNode(node);
                        break;
                     }

                     i = previousIndexFromNode(node);
                  }

                  if (previousWeight16 == weight16) {
                     index = previousIndex;
                  } else {
                     node = nodeFromWeight16(weight16) | nodeFromStrength(strength);
                     index = this.insertNodeBetween(previousIndex, index, node);
                  }
               }

               strength = ceStrength(this.ces[this.cesLength - 1]);
            }
         }

         this.ces[this.cesLength - 1] = tempCEFromIndexAndStrength(index, strength);
      }
   }

   private int getWeight16Before(int index, long node, int level) {
      assert strengthFromNode(node) < level || !isTailoredNode(node);

      int t;
      if (strengthFromNode(node) == 2) {
         t = weight16FromNode(node);
      } else {
         t = 1280;
      }

      while(strengthFromNode(node) > 1) {
         index = previousIndexFromNode(node);
         node = this.nodes.elementAti(index);
      }

      if (isTailoredNode(node)) {
         return 256;
      } else {
         int s;
         if (strengthFromNode(node) == 1) {
            s = weight16FromNode(node);
         } else {
            s = 1280;
         }

         while(strengthFromNode(node) > 0) {
            index = previousIndexFromNode(node);
            node = this.nodes.elementAti(index);
         }

         if (isTailoredNode(node)) {
            return 256;
         } else {
            long p = weight32FromNode(node);
            int weight16;
            if (level == 1) {
               weight16 = this.rootElements.getSecondaryBefore(p, s);
            } else {
               weight16 = this.rootElements.getTertiaryBefore(p, s, t);

               assert (weight16 & -16192) == 0;
            }

            return weight16;
         }
      }
   }

   private long getSpecialResetPosition(CharSequence str) {
      assert str.length() == 2;

      int strength = 0;
      boolean isBoundary = false;
      CollationRuleParser.Position pos = CollationRuleParser.POSITION_VALUES[str.charAt(1) - 10240];
      int index;
      long node;
      long ce;
      switch (pos) {
         case FIRST_TERTIARY_IGNORABLE:
            return 0L;
         case LAST_TERTIARY_IGNORABLE:
            return 0L;
         case FIRST_SECONDARY_IGNORABLE:
            index = this.findOrInsertNodeForRootCE(0L, 2);
            node = this.nodes.elementAti(index);
            if ((index = nextIndexFromNode(node)) != 0) {
               node = this.nodes.elementAti(index);

               assert strengthFromNode(node) <= 2;

               if (isTailoredNode(node) && strengthFromNode(node) == 2) {
                  return tempCEFromIndexAndStrength(index, 2);
               }
            }

            return this.rootElements.getFirstTertiaryCE();
         case LAST_SECONDARY_IGNORABLE:
            ce = this.rootElements.getLastTertiaryCE();
            strength = 2;
            break;
         case FIRST_PRIMARY_IGNORABLE:
            index = this.findOrInsertNodeForRootCE(0L, 1);
            node = this.nodes.elementAti(index);

            while((index = nextIndexFromNode(node)) != 0) {
               node = this.nodes.elementAti(index);
               strength = strengthFromNode(node);
               if (strength < 1) {
                  break;
               }

               if (strength == 1) {
                  if (isTailoredNode(node)) {
                     if (nodeHasBefore3(node)) {
                        index = nextIndexFromNode(this.nodes.elementAti(nextIndexFromNode(node)));

                        assert isTailoredNode(this.nodes.elementAti(index));
                     }

                     return tempCEFromIndexAndStrength(index, 1);
                  }
                  break;
               }
            }

            ce = this.rootElements.getFirstSecondaryCE();
            strength = 1;
            break;
         case LAST_PRIMARY_IGNORABLE:
            ce = this.rootElements.getLastSecondaryCE();
            strength = 1;
            break;
         case FIRST_VARIABLE:
            ce = this.rootElements.getFirstPrimaryCE();
            isBoundary = true;
            break;
         case LAST_VARIABLE:
            ce = this.rootElements.lastCEWithPrimaryBefore(this.variableTop + 1L);
            break;
         case FIRST_REGULAR:
            ce = this.rootElements.firstCEWithPrimaryAtLeast(this.variableTop + 1L);
            isBoundary = true;
            break;
         case LAST_REGULAR:
            ce = this.rootElements.firstCEWithPrimaryAtLeast(this.baseData.getFirstPrimaryForGroup(17));
            break;
         case FIRST_IMPLICIT:
            ce = this.baseData.getSingleCE(19968);
            break;
         case LAST_IMPLICIT:
            throw new UnsupportedOperationException("reset to [last implicit] not supported");
         case FIRST_TRAILING:
            ce = Collation.makeCE(4278321664L);
            isBoundary = true;
            break;
         case LAST_TRAILING:
            throw new IllegalArgumentException("LDML forbids tailoring to U+FFFF");
         default:
            assert false;

            return 0L;
      }

      index = this.findOrInsertNodeForRootCE(ce, strength);
      node = this.nodes.elementAti(index);
      if ((pos.ordinal() & 1) == 0) {
         if (!nodeHasAnyBefore(node) && isBoundary) {
            if ((index = nextIndexFromNode(node)) != 0) {
               node = this.nodes.elementAti(index);

               assert isTailoredNode(node);

               ce = tempCEFromIndexAndStrength(index, strength);
            } else {
               assert strength == 0;

               long p = ce >>> 32;
               int pIndex = this.rootElements.findPrimary(p);
               boolean isCompressible = this.baseData.isCompressiblePrimary(p);
               p = this.rootElements.getPrimaryAfter(p, pIndex, isCompressible);
               ce = Collation.makeCE(p);
               index = this.findOrInsertNodeForRootCE(ce, 0);
               node = this.nodes.elementAti(index);
            }
         }

         if (nodeHasAnyBefore(node)) {
            if (nodeHasBefore2(node)) {
               index = nextIndexFromNode(this.nodes.elementAti(nextIndexFromNode(node)));
               node = this.nodes.elementAti(index);
            }

            if (nodeHasBefore3(node)) {
               index = nextIndexFromNode(this.nodes.elementAti(nextIndexFromNode(node)));
            }

            assert isTailoredNode(this.nodes.elementAti(index));

            ce = tempCEFromIndexAndStrength(index, strength);
         }
      } else {
         while(true) {
            int nextIndex = nextIndexFromNode(node);
            if (nextIndex == 0) {
               break;
            }

            long nextNode = this.nodes.elementAti(nextIndex);
            if (strengthFromNode(nextNode) < strength) {
               break;
            }

            index = nextIndex;
            node = nextNode;
         }

         if (isTailoredNode(node)) {
            ce = tempCEFromIndexAndStrength(index, strength);
         }
      }

      return ce;
   }

   void addRelation(int strength, CharSequence prefix, CharSequence str, CharSequence extension) {
      String nfdPrefix;
      if (prefix.length() == 0) {
         nfdPrefix = "";
      } else {
         nfdPrefix = this.nfd.normalize(prefix);
      }

      String nfdString = this.nfd.normalize(str);
      int nfdLength = nfdString.length();
      int index;
      if (nfdLength >= 2) {
         index = nfdString.charAt(0);
         if (Normalizer2Impl.Hangul.isJamoL(index) || Normalizer2Impl.Hangul.isJamoV(index)) {
            throw new UnsupportedOperationException("contractions starting with conjoining Jamo L or V not supported");
         }

         index = nfdString.charAt(nfdLength - 1);
         if (Normalizer2Impl.Hangul.isJamoL(index) || Normalizer2Impl.Hangul.isJamoV(index) && Normalizer2Impl.Hangul.isJamoL(nfdString.charAt(nfdLength - 2))) {
            throw new UnsupportedOperationException("contractions ending with conjoining Jamo L or L+V not supported");
         }
      }

      if (strength != 15) {
         index = this.findOrInsertNodeForCEs(strength);

         assert this.cesLength > 0;

         long ce = this.ces[this.cesLength - 1];
         if (strength == 0 && !isTempCE(ce) && ce >>> 32 == 0L) {
            throw new UnsupportedOperationException("tailoring primary after ignorables not supported");
         }

         if (strength == 3 && ce == 0L) {
            throw new UnsupportedOperationException("tailoring quaternary after tertiary ignorables not supported");
         }

         index = this.insertTailoredNodeAfter(index, strength);
         int tempStrength = ceStrength(ce);
         if (strength < tempStrength) {
            tempStrength = strength;
         }

         this.ces[this.cesLength - 1] = tempCEFromIndexAndStrength(index, tempStrength);
      }

      this.setCaseBits(nfdString);
      index = this.cesLength;
      if (extension.length() != 0) {
         String nfdExtension = this.nfd.normalize(extension);
         this.cesLength = this.dataBuilder.getCEs(nfdExtension, this.ces, this.cesLength);
         if (this.cesLength > 31) {
            throw new IllegalArgumentException("extension string adds too many collation elements (more than 31 total)");
         }
      }

      int ce32 = -1;
      if ((!nfdPrefix.contentEquals(prefix) || !nfdString.contentEquals(str)) && !this.ignorePrefix(prefix) && !this.ignoreString(str)) {
         ce32 = this.addIfDifferent(prefix, str, this.ces, this.cesLength, ce32);
      }

      this.addWithClosure(nfdPrefix, nfdString, this.ces, this.cesLength, ce32);
      this.cesLength = index;
   }

   private int findOrInsertNodeForCEs(int strength) {
      assert 0 <= strength && strength <= 3;

      long ce;
      while(true) {
         if (this.cesLength == 0) {
            ce = this.ces[0] = 0L;
            this.cesLength = 1;
            break;
         }

         ce = this.ces[this.cesLength - 1];
         if (ceStrength(ce) <= strength) {
            break;
         }

         --this.cesLength;
      }

      if (isTempCE(ce)) {
         return indexFromTempCE(ce);
      } else if ((int)(ce >>> 56) == 254) {
         throw new UnsupportedOperationException("tailoring relative to an unassigned code point not supported");
      } else {
         return this.findOrInsertNodeForRootCE(ce, strength);
      }
   }

   private int findOrInsertNodeForRootCE(long ce, int strength) {
      assert (int)(ce >>> 56) != 254;

      assert (ce & 192L) == 0L;

      int index = this.findOrInsertNodeForPrimary(ce >>> 32);
      if (strength >= 1) {
         int lower32 = (int)ce;
         index = this.findOrInsertWeakNode(index, lower32 >>> 16, 1);
         if (strength >= 2) {
            index = this.findOrInsertWeakNode(index, lower32 & 16191, 2);
         }
      }

      return index;
   }

   private static final int binarySearchForRootPrimaryNode(int[] rootPrimaryIndexes, int length, long[] nodes, long p) {
      if (length == 0) {
         return -1;
      } else {
         int start = 0;
         int limit = length;

         while(true) {
            int i = (int)(((long)start + (long)limit) / 2L);
            long node = nodes[rootPrimaryIndexes[i]];
            long nodePrimary = node >>> 32;
            if (p == nodePrimary) {
               return i;
            }

            if (p < nodePrimary) {
               if (i == start) {
                  return ~start;
               }

               limit = i;
            } else {
               if (i == start) {
                  return ~(start + 1);
               }

               start = i;
            }
         }
      }
   }

   private int findOrInsertNodeForPrimary(long p) {
      int rootIndex = binarySearchForRootPrimaryNode(this.rootPrimaryIndexes.getBuffer(), this.rootPrimaryIndexes.size(), this.nodes.getBuffer(), p);
      if (rootIndex >= 0) {
         return this.rootPrimaryIndexes.elementAti(rootIndex);
      } else {
         int index = this.nodes.size();
         this.nodes.addElement(nodeFromWeight32(p));
         this.rootPrimaryIndexes.insertElementAt(index, ~rootIndex);
         return index;
      }
   }

   private int findOrInsertWeakNode(int index, int weight16, int level) {
      assert 0 <= index && index < this.nodes.size();

      assert 1 <= level && level <= 2;

      if (weight16 == 1280) {
         return this.findCommonNode(index, level);
      } else {
         long node = this.nodes.elementAti(index);

         assert strengthFromNode(node) < level;

         int nextIndex;
         if (weight16 != 0 && weight16 < 1280) {
            nextIndex = level == 1 ? 64 : 32;
            if ((node & (long)nextIndex) == 0L) {
               long commonNode = nodeFromWeight16(1280) | nodeFromStrength(level);
               if (level == 1) {
                  commonNode |= node & 32L;
                  node &= -33L;
               }

               this.nodes.setElementAt(node | (long)nextIndex, index);
               int nextIndex = nextIndexFromNode(node);
               node = nodeFromWeight16(weight16) | nodeFromStrength(level);
               index = this.insertNodeBetween(index, nextIndex, node);
               this.insertNodeBetween(index, nextIndex, commonNode);
               return index;
            }
         }

         for(; (nextIndex = nextIndexFromNode(node)) != 0; index = nextIndex) {
            node = this.nodes.elementAti(nextIndex);
            int nextStrength = strengthFromNode(node);
            if (nextStrength <= level) {
               if (nextStrength < level) {
                  break;
               }

               if (!isTailoredNode(node)) {
                  int nextWeight16 = weight16FromNode(node);
                  if (nextWeight16 == weight16) {
                     return nextIndex;
                  }

                  if (nextWeight16 > weight16) {
                     break;
                  }
               }
            }
         }

         node = nodeFromWeight16(weight16) | nodeFromStrength(level);
         return this.insertNodeBetween(index, nextIndex, node);
      }
   }

   private int insertTailoredNodeAfter(int index, int strength) {
      assert 0 <= index && index < this.nodes.size();

      if (strength >= 1) {
         index = this.findCommonNode(index, 1);
         if (strength >= 2) {
            index = this.findCommonNode(index, 2);
         }
      }

      long node;
      int nextIndex;
      for(node = this.nodes.elementAti(index); (nextIndex = nextIndexFromNode(node)) != 0; index = nextIndex) {
         node = this.nodes.elementAti(nextIndex);
         if (strengthFromNode(node) <= strength) {
            break;
         }
      }

      node = 8L | nodeFromStrength(strength);
      return this.insertNodeBetween(index, nextIndex, node);
   }

   private int insertNodeBetween(int index, int nextIndex, long node) {
      assert previousIndexFromNode(node) == 0;

      assert nextIndexFromNode(node) == 0;

      assert nextIndexFromNode(this.nodes.elementAti(index)) == nextIndex;

      int newIndex = this.nodes.size();
      node |= nodeFromPreviousIndex(index) | nodeFromNextIndex(nextIndex);
      this.nodes.addElement(node);
      node = this.nodes.elementAti(index);
      this.nodes.setElementAt(changeNodeNextIndex(node, newIndex), index);
      if (nextIndex != 0) {
         node = this.nodes.elementAti(nextIndex);
         this.nodes.setElementAt(changeNodePreviousIndex(node, newIndex), nextIndex);
      }

      return newIndex;
   }

   private int findCommonNode(int index, int strength) {
      assert 1 <= strength && strength <= 2;

      long node = this.nodes.elementAti(index);
      if (strengthFromNode(node) >= strength) {
         return index;
      } else {
         label56: {
            if (strength == 1) {
               if (nodeHasBefore2(node)) {
                  break label56;
               }
            } else if (nodeHasBefore3(node)) {
               break label56;
            }

            return index;
         }

         index = nextIndexFromNode(node);
         node = this.nodes.elementAti(index);

         assert !isTailoredNode(node) && strengthFromNode(node) == strength && weight16FromNode(node) < 1280;

         do {
            index = nextIndexFromNode(node);
            node = this.nodes.elementAti(index);

            assert strengthFromNode(node) >= strength;
         } while(isTailoredNode(node) || strengthFromNode(node) > strength || weight16FromNode(node) < 1280);

         assert weight16FromNode(node) == 1280;

         return index;
      }
   }

   private void setCaseBits(CharSequence nfdString) {
      int numTailoredPrimaries = 0;

      for(int i = 0; i < this.cesLength; ++i) {
         if (ceStrength(this.ces[i]) == 0) {
            ++numTailoredPrimaries;
         }
      }

      assert numTailoredPrimaries <= 31;

      long cases = 0L;
      int lastCase;
      if (numTailoredPrimaries > 0) {
         UTF16CollationIterator baseCEs = new UTF16CollationIterator(this.baseData, false, nfdString, 0);
         int baseCEsLength = baseCEs.fetchCEs() - 1;

         assert baseCEsLength >= 0 && baseCEs.getCE(baseCEsLength) == 4311744768L;

         lastCase = 0;
         int numBasePrimaries = 0;

         for(int i = 0; i < baseCEsLength; ++i) {
            long ce = baseCEs.getCE(i);
            if (ce >>> 32 != 0L) {
               ++numBasePrimaries;
               int c = (int)ce >> 14 & 3;

               assert c == 0 || c == 2;

               if (numBasePrimaries < numTailoredPrimaries) {
                  cases |= (long)c << (numBasePrimaries - 1) * 2;
               } else if (numBasePrimaries == numTailoredPrimaries) {
                  lastCase = c;
               } else if (c != lastCase) {
                  lastCase = 1;
                  break;
               }
            }
         }

         if (numBasePrimaries >= numTailoredPrimaries) {
            cases |= (long)lastCase << (numTailoredPrimaries - 1) * 2;
         }
      }

      for(int i = 0; i < this.cesLength; ++i) {
         long ce = this.ces[i] & -49153L;
         lastCase = ceStrength(ce);
         if (lastCase == 0) {
            ce |= (cases & 3L) << 14;
            cases >>>= 2;
         } else if (lastCase == 2) {
            ce |= 32768L;
         }

         this.ces[i] = ce;
      }

   }

   void suppressContractions(UnicodeSet set) {
      this.dataBuilder.suppressContractions(set);
   }

   void optimize(UnicodeSet set) {
      this.optimizeSet.addAll(set);
   }

   private int addWithClosure(CharSequence nfdPrefix, CharSequence nfdString, long[] newCEs, int newCEsLength, int ce32) {
      ce32 = this.addIfDifferent(nfdPrefix, nfdString, newCEs, newCEsLength, ce32);
      ce32 = this.addOnlyClosure(nfdPrefix, nfdString, newCEs, newCEsLength, ce32);
      this.addTailComposites(nfdPrefix, nfdString);
      return ce32;
   }

   private int addOnlyClosure(CharSequence nfdPrefix, CharSequence nfdString, long[] newCEs, int newCEsLength, int ce32) {
      CanonicalIterator stringIter;
      String prefix;
      if (nfdPrefix.length() == 0) {
         stringIter = new CanonicalIterator(nfdString.toString());
         String prefix = "";

         while(true) {
            prefix = stringIter.next();
            if (prefix == null) {
               return ce32;
            }

            if (!this.ignoreString(prefix) && !prefix.contentEquals(nfdString)) {
               ce32 = this.addIfDifferent(prefix, prefix, newCEs, newCEsLength, ce32);
            }
         }
      } else {
         stringIter = new CanonicalIterator(nfdPrefix.toString());
         CanonicalIterator stringIter = new CanonicalIterator(nfdString.toString());

         label53:
         while(true) {
            do {
               prefix = stringIter.next();
               if (prefix == null) {
                  return ce32;
               }
            } while(this.ignorePrefix(prefix));

            boolean samePrefix = prefix.contentEquals(nfdPrefix);

            while(true) {
               String str;
               do {
                  do {
                     str = stringIter.next();
                     if (str == null) {
                        stringIter.reset();
                        continue label53;
                     }
                  } while(this.ignoreString(str));
               } while(samePrefix && str.contentEquals(nfdString));

               ce32 = this.addIfDifferent(prefix, str, newCEs, newCEsLength, ce32);
            }
         }
      }
   }

   private void addTailComposites(CharSequence nfdPrefix, CharSequence nfdString) {
      int lastStarter;
      for(int indexAfterLastStarter = nfdString.length(); indexAfterLastStarter != 0; indexAfterLastStarter -= Character.charCount(lastStarter)) {
         lastStarter = Character.codePointBefore(nfdString, indexAfterLastStarter);
         if (this.nfd.getCombiningClass(lastStarter) == 0) {
            if (Normalizer2Impl.Hangul.isJamoL(lastStarter)) {
               return;
            }

            UnicodeSet composites = new UnicodeSet();
            if (!this.nfcImpl.getCanonStartSet(lastStarter, composites)) {
               return;
            }

            StringBuilder newNFDString = new StringBuilder();
            StringBuilder newString = new StringBuilder();
            long[] newCEs = new long[31];
            UnicodeSetIterator iter = new UnicodeSetIterator(composites);

            while(iter.next()) {
               assert iter.codepoint != UnicodeSetIterator.IS_STRING;

               int composite = iter.codepoint;
               String decomp = this.nfd.getDecomposition(composite);
               if (this.mergeCompositeIntoString(nfdString, indexAfterLastStarter, composite, decomp, newNFDString, newString)) {
                  int newCEsLength = this.dataBuilder.getCEs(nfdPrefix, newNFDString, newCEs, 0);
                  if (newCEsLength <= 31) {
                     int ce32 = this.addIfDifferent(nfdPrefix, newString, newCEs, newCEsLength, -1);
                     if (ce32 != -1) {
                        this.addOnlyClosure(nfdPrefix, newNFDString, newCEs, newCEsLength, ce32);
                     }
                  }
               }
            }

            return;
         }
      }

   }

   private boolean mergeCompositeIntoString(CharSequence nfdString, int indexAfterLastStarter, int composite, CharSequence decomp, StringBuilder newNFDString, StringBuilder newString) {
      assert Character.codePointBefore(nfdString, indexAfterLastStarter) == Character.codePointAt(decomp, 0);

      int lastStarterLength = Character.offsetByCodePoints(decomp, 0, 1);
      if (lastStarterLength == decomp.length()) {
         return false;
      } else if (this.equalSubSequences(nfdString, indexAfterLastStarter, decomp, lastStarterLength)) {
         return false;
      } else {
         newNFDString.setLength(0);
         newNFDString.append(nfdString, 0, indexAfterLastStarter);
         newString.setLength(0);
         newString.append(nfdString, 0, indexAfterLastStarter - lastStarterLength).appendCodePoint(composite);
         int sourceIndex = indexAfterLastStarter;
         int decompIndex = lastStarterLength;
         int sourceChar = -1;
         int sourceCC = 0;
         int decompCC = 0;

         while(true) {
            if (sourceChar < 0) {
               if (sourceIndex >= nfdString.length()) {
                  break;
               }

               sourceChar = Character.codePointAt(nfdString, sourceIndex);
               sourceCC = this.nfd.getCombiningClass(sourceChar);

               assert sourceCC != 0;
            }

            if (decompIndex >= decomp.length()) {
               break;
            }

            int decompChar = Character.codePointAt(decomp, decompIndex);
            decompCC = this.nfd.getCombiningClass(decompChar);
            if (decompCC == 0) {
               return false;
            }

            if (sourceCC < decompCC) {
               return false;
            }

            if (decompCC < sourceCC) {
               newNFDString.appendCodePoint(decompChar);
               decompIndex += Character.charCount(decompChar);
            } else {
               if (decompChar != sourceChar) {
                  return false;
               }

               newNFDString.appendCodePoint(decompChar);
               decompIndex += Character.charCount(decompChar);
               sourceIndex += Character.charCount(decompChar);
               sourceChar = -1;
            }
         }

         if (sourceChar >= 0) {
            if (sourceCC < decompCC) {
               return false;
            }

            newNFDString.append(nfdString, sourceIndex, nfdString.length());
            newString.append(nfdString, sourceIndex, nfdString.length());
         } else if (decompIndex < decomp.length()) {
            newNFDString.append(decomp, decompIndex, decomp.length());
         }

         assert this.nfd.isNormalized(newNFDString);

         assert this.fcd.isNormalized(newString);

         assert this.nfd.normalize(newString).equals(newNFDString.toString());

         return true;
      }
   }

   private boolean equalSubSequences(CharSequence left, int leftStart, CharSequence right, int rightStart) {
      int leftLength = left.length();
      if (leftLength - leftStart != right.length() - rightStart) {
         return false;
      } else {
         do {
            if (leftStart >= leftLength) {
               return true;
            }
         } while(left.charAt(leftStart++) == right.charAt(rightStart++));

         return false;
      }
   }

   private boolean ignorePrefix(CharSequence s) {
      return !this.isFCD(s);
   }

   private boolean ignoreString(CharSequence s) {
      return !this.isFCD(s) || Normalizer2Impl.Hangul.isHangul(s.charAt(0));
   }

   private boolean isFCD(CharSequence s) {
      return this.fcd.isNormalized(s);
   }

   private void closeOverComposites() {
      String prefix = "";
      UnicodeSetIterator iter = new UnicodeSetIterator(COMPOSITES);

      while(iter.next()) {
         assert iter.codepoint != UnicodeSetIterator.IS_STRING;

         String nfdString = this.nfd.getDecomposition(iter.codepoint);
         this.cesLength = this.dataBuilder.getCEs(nfdString, this.ces, 0);
         if (this.cesLength <= 31) {
            String composite = iter.getString();
            this.addIfDifferent(prefix, composite, this.ces, this.cesLength, -1);
         }
      }

   }

   private int addIfDifferent(CharSequence prefix, CharSequence str, long[] newCEs, int newCEsLength, int ce32) {
      long[] oldCEs = new long[31];
      int oldCEsLength = this.dataBuilder.getCEs(prefix, str, oldCEs, 0);
      if (!sameCEs(newCEs, newCEsLength, oldCEs, oldCEsLength)) {
         if (ce32 == -1) {
            ce32 = this.dataBuilder.encodeCEs(newCEs, newCEsLength);
         }

         this.dataBuilder.addCE32(prefix, str, ce32);
      }

      return ce32;
   }

   private static boolean sameCEs(long[] ces1, int ces1Length, long[] ces2, int ces2Length) {
      if (ces1Length != ces2Length) {
         return false;
      } else {
         assert ces1Length <= 31;

         for(int i = 0; i < ces1Length; ++i) {
            if (ces1[i] != ces2[i]) {
               return false;
            }
         }

         return true;
      }
   }

   private static final int alignWeightRight(int w) {
      if (w != 0) {
         while((w & 255) == 0) {
            w >>>= 8;
         }
      }

      return w;
   }

   private void makeTailoredCEs() {
      CollationWeights primaries = new CollationWeights();
      CollationWeights secondaries = new CollationWeights();
      CollationWeights tertiaries = new CollationWeights();
      long[] nodesArray = this.nodes.getBuffer();

      for(int rpi = 0; rpi < this.rootPrimaryIndexes.size(); ++rpi) {
         int i = this.rootPrimaryIndexes.elementAti(rpi);
         long node = nodesArray[i];
         long p = weight32FromNode(node);
         int s = p == 0L ? 0 : 1280;
         int t = s;
         int q = 0;
         boolean pIsTailored = false;
         boolean sIsTailored = false;
         boolean tIsTailored = false;
         int pIndex = p == 0L ? 0 : this.rootElements.findPrimary(p);
         int nextIndex = nextIndexFromNode(node);

         while(nextIndex != 0) {
            i = nextIndex;
            node = nodesArray[nextIndex];
            nextIndex = nextIndexFromNode(node);
            int strength = strengthFromNode(node);
            if (strength == 3) {
               assert isTailoredNode(node);

               if (q == 3) {
                  throw new UnsupportedOperationException("quaternary tailoring gap too small");
               }

               ++q;
            } else {
               int sCount;
               int sLimit;
               if (strength == 2) {
                  if (isTailoredNode(node)) {
                     if (!tIsTailored) {
                        sCount = countTailoredNodes(nodesArray, nextIndex, 2) + 1;
                        if (t == 0) {
                           t = this.rootElements.getTertiaryBoundary() - 256;
                           sLimit = (int)this.rootElements.getFirstTertiaryCE() & 16191;
                        } else if (!pIsTailored && !sIsTailored) {
                           sLimit = this.rootElements.getTertiaryAfter(pIndex, s, t);
                        } else if (t == 256) {
                           sLimit = 1280;
                        } else {
                           assert t == 1280;

                           sLimit = this.rootElements.getTertiaryBoundary();
                        }

                        assert sLimit == 16384 || (sLimit & -16192) == 0;

                        tertiaries.initForTertiary();
                        if (!tertiaries.allocWeights((long)t, (long)sLimit, sCount)) {
                           throw new UnsupportedOperationException("tertiary tailoring gap too small");
                        }

                        tIsTailored = true;
                     }

                     t = (int)tertiaries.nextWeight();

                     assert t != -1;
                  } else {
                     t = weight16FromNode(node);
                     tIsTailored = false;
                  }
               } else {
                  if (strength == 1) {
                     if (isTailoredNode(node)) {
                        if (!sIsTailored) {
                           sCount = countTailoredNodes(nodesArray, nextIndex, 1) + 1;
                           if (s == 0) {
                              s = this.rootElements.getSecondaryBoundary() - 256;
                              sLimit = (int)(this.rootElements.getFirstSecondaryCE() >> 16);
                           } else if (!pIsTailored) {
                              sLimit = this.rootElements.getSecondaryAfter(pIndex, s);
                           } else if (s == 256) {
                              sLimit = 1280;
                           } else {
                              assert s == 1280;

                              sLimit = this.rootElements.getSecondaryBoundary();
                           }

                           if (s == 1280) {
                              s = this.rootElements.getLastCommonSecondary();
                           }

                           secondaries.initForSecondary();
                           if (!secondaries.allocWeights((long)s, (long)sLimit, sCount)) {
                              throw new UnsupportedOperationException("secondary tailoring gap too small");
                           }

                           sIsTailored = true;
                        }

                        s = (int)secondaries.nextWeight();

                        assert s != -1;
                     } else {
                        s = weight16FromNode(node);
                        sIsTailored = false;
                     }
                  } else {
                     assert isTailoredNode(node);

                     if (!pIsTailored) {
                        sCount = countTailoredNodes(nodesArray, nextIndex, 0) + 1;
                        boolean isCompressible = this.baseData.isCompressiblePrimary(p);
                        long pLimit = this.rootElements.getPrimaryAfter(p, pIndex, isCompressible);
                        primaries.initForPrimary(isCompressible);
                        if (!primaries.allocWeights(p, pLimit, sCount)) {
                           throw new UnsupportedOperationException("primary tailoring gap too small");
                        }

                        pIsTailored = true;
                     }

                     p = primaries.nextWeight();

                     assert p != 4294967295L;

                     s = 1280;
                     sIsTailored = false;
                  }

                  t = s == 0 ? 0 : 1280;
                  tIsTailored = false;
               }

               q = 0;
            }

            if (isTailoredNode(node)) {
               nodesArray[i] = Collation.makeCE(p, s, t, q);
            }
         }
      }

   }

   private static int countTailoredNodes(long[] nodesArray, int i, int strength) {
      int count;
      long node;
      for(count = 0; i != 0; i = nextIndexFromNode(node)) {
         node = nodesArray[i];
         if (strengthFromNode(node) < strength) {
            break;
         }

         if (strengthFromNode(node) == strength) {
            if (!isTailoredNode(node)) {
               break;
            }

            ++count;
         }
      }

      return count;
   }

   private void finalizeCEs() {
      CollationDataBuilder newBuilder = new CollationDataBuilder();
      newBuilder.initForTailoring(this.baseData);
      CEFinalizer finalizer = new CEFinalizer(this.nodes.getBuffer());
      newBuilder.copyFrom(this.dataBuilder, finalizer);
      this.dataBuilder = newBuilder;
   }

   private static long tempCEFromIndexAndStrength(int index, int strength) {
      return 4629700417037541376L + ((long)(index & 1040384) << 43) + ((long)(index & 8128) << 42) + (long)((index & 63) << 24) + (long)(strength << 8);
   }

   private static int indexFromTempCE(long tempCE) {
      tempCE -= 4629700417037541376L;
      return (int)(tempCE >> 43) & 1040384 | (int)(tempCE >> 42) & 8128 | (int)(tempCE >> 24) & 63;
   }

   private static int strengthFromTempCE(long tempCE) {
      return (int)tempCE >> 8 & 3;
   }

   private static boolean isTempCE(long ce) {
      int sec = (int)ce >>> 24;
      return 6 <= sec && sec <= 69;
   }

   private static int indexFromTempCE32(int tempCE32) {
      tempCE32 -= 1077937696;
      return tempCE32 >> 11 & 1040384 | tempCE32 >> 10 & 8128 | tempCE32 >> 8 & 63;
   }

   private static boolean isTempCE32(int ce32) {
      return (ce32 & 255) >= 2 && 6 <= (ce32 >> 8 & 255) && (ce32 >> 8 & 255) <= 69;
   }

   private static int ceStrength(long ce) {
      return isTempCE(ce) ? strengthFromTempCE(ce) : ((ce & -72057594037927936L) != 0L ? 0 : (((int)ce & -16777216) != 0 ? 1 : (ce != 0L ? 2 : 15)));
   }

   private static long nodeFromWeight32(long weight32) {
      return weight32 << 32;
   }

   private static long nodeFromWeight16(int weight16) {
      return (long)weight16 << 48;
   }

   private static long nodeFromPreviousIndex(int previous) {
      return (long)previous << 28;
   }

   private static long nodeFromNextIndex(int next) {
      return (long)(next << 8);
   }

   private static long nodeFromStrength(int strength) {
      return (long)strength;
   }

   private static long weight32FromNode(long node) {
      return node >>> 32;
   }

   private static int weight16FromNode(long node) {
      return (int)(node >> 48) & '\uffff';
   }

   private static int previousIndexFromNode(long node) {
      return (int)(node >> 28) & 1048575;
   }

   private static int nextIndexFromNode(long node) {
      return (int)node >> 8 & 1048575;
   }

   private static int strengthFromNode(long node) {
      return (int)node & 3;
   }

   private static boolean nodeHasBefore2(long node) {
      return (node & 64L) != 0L;
   }

   private static boolean nodeHasBefore3(long node) {
      return (node & 32L) != 0L;
   }

   private static boolean nodeHasAnyBefore(long node) {
      return (node & 96L) != 0L;
   }

   private static boolean isTailoredNode(long node) {
      return (node & 8L) != 0L;
   }

   private static long changeNodePreviousIndex(long node, int previous) {
      return node & -281474708275201L | nodeFromPreviousIndex(previous);
   }

   private static long changeNodeNextIndex(long node, int next) {
      return node & -268435201L | nodeFromNextIndex(next);
   }

   static {
      COMPOSITES.remove(44032, 55203);
   }

   private static final class CEFinalizer implements CollationDataBuilder.CEModifier {
      private long[] finalCEs;

      CEFinalizer(long[] ces) {
         this.finalCEs = ces;
      }

      public long modifyCE32(int ce32) {
         assert !Collation.isSpecialCE32(ce32);

         return CollationBuilder.isTempCE32(ce32) ? this.finalCEs[CollationBuilder.indexFromTempCE32(ce32)] | (long)((ce32 & 192) << 8) : 4311744768L;
      }

      public long modifyCE(long ce) {
         return CollationBuilder.isTempCE(ce) ? this.finalCEs[CollationBuilder.indexFromTempCE(ce)] | ce & 49152L : 4311744768L;
      }
   }

   private static final class BundleImporter implements CollationRuleParser.Importer {
      BundleImporter() {
      }

      public String getRules(String localeID, String collationType) {
         return CollationLoader.loadRules(new ULocale(localeID), collationType);
      }
   }
}
