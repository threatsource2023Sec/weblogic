package org.python.icu.text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.python.icu.lang.UCharacter;
import org.python.icu.util.LocaleData;
import org.python.icu.util.ULocale;

public final class AlphabeticIndex implements Iterable {
   private static final String BASE = "\ufdd0";
   private static final char CGJ = '͏';
   private static final Comparator binaryCmp = new UTF16.StringComparator(true, false, 0);
   private final RuleBasedCollator collatorOriginal;
   private final RuleBasedCollator collatorPrimaryOnly;
   private RuleBasedCollator collatorExternal;
   private final Comparator recordComparator;
   private final List firstCharsInScripts;
   private final UnicodeSet initialLabels;
   private List inputList;
   private BucketList buckets;
   private String overflowLabel;
   private String underflowLabel;
   private String inflowLabel;
   private int maxLabelCount;
   private static final int GC_LU_MASK = 2;
   private static final int GC_LL_MASK = 4;
   private static final int GC_LT_MASK = 8;
   private static final int GC_LM_MASK = 16;
   private static final int GC_LO_MASK = 32;
   private static final int GC_L_MASK = 62;
   private static final int GC_CN_MASK = 1;

   public AlphabeticIndex(ULocale locale) {
      this(locale, (RuleBasedCollator)null);
   }

   public AlphabeticIndex(Locale locale) {
      this(ULocale.forLocale(locale), (RuleBasedCollator)null);
   }

   public AlphabeticIndex(RuleBasedCollator collator) {
      this((ULocale)null, collator);
   }

   private AlphabeticIndex(ULocale locale, RuleBasedCollator collator) {
      this.recordComparator = new Comparator() {
         public int compare(Record o1, Record o2) {
            return AlphabeticIndex.this.collatorOriginal.compare(o1.name, o2.name);
         }
      };
      this.initialLabels = new UnicodeSet();
      this.overflowLabel = "…";
      this.underflowLabel = "…";
      this.inflowLabel = "…";
      this.maxLabelCount = 99;
      this.collatorOriginal = collator != null ? collator : (RuleBasedCollator)Collator.getInstance(locale);

      try {
         this.collatorPrimaryOnly = this.collatorOriginal.cloneAsThawed();
      } catch (Exception var4) {
         throw new IllegalStateException("Collator cannot be cloned", var4);
      }

      this.collatorPrimaryOnly.setStrength(0);
      this.collatorPrimaryOnly.freeze();
      this.firstCharsInScripts = this.getFirstCharactersInScripts();
      Collections.sort(this.firstCharsInScripts, this.collatorPrimaryOnly);

      while(!this.firstCharsInScripts.isEmpty()) {
         if (this.collatorPrimaryOnly.compare((String)this.firstCharsInScripts.get(0), "") != 0) {
            if (!this.addChineseIndexCharacters() && locale != null) {
               this.addIndexExemplars(locale);
            }

            return;
         }

         this.firstCharsInScripts.remove(0);
      }

      throw new IllegalArgumentException("AlphabeticIndex requires some non-ignorable script boundary strings");
   }

   public AlphabeticIndex addLabels(UnicodeSet additions) {
      this.initialLabels.addAll(additions);
      this.buckets = null;
      return this;
   }

   public AlphabeticIndex addLabels(ULocale... additions) {
      ULocale[] var2 = additions;
      int var3 = additions.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ULocale addition = var2[var4];
         this.addIndexExemplars(addition);
      }

      this.buckets = null;
      return this;
   }

   public AlphabeticIndex addLabels(Locale... additions) {
      Locale[] var2 = additions;
      int var3 = additions.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Locale addition = var2[var4];
         this.addIndexExemplars(ULocale.forLocale(addition));
      }

      this.buckets = null;
      return this;
   }

   public AlphabeticIndex setOverflowLabel(String overflowLabel) {
      this.overflowLabel = overflowLabel;
      this.buckets = null;
      return this;
   }

   public String getUnderflowLabel() {
      return this.underflowLabel;
   }

   public AlphabeticIndex setUnderflowLabel(String underflowLabel) {
      this.underflowLabel = underflowLabel;
      this.buckets = null;
      return this;
   }

   public String getOverflowLabel() {
      return this.overflowLabel;
   }

   public AlphabeticIndex setInflowLabel(String inflowLabel) {
      this.inflowLabel = inflowLabel;
      this.buckets = null;
      return this;
   }

   public String getInflowLabel() {
      return this.inflowLabel;
   }

   public int getMaxLabelCount() {
      return this.maxLabelCount;
   }

   public AlphabeticIndex setMaxLabelCount(int maxLabelCount) {
      this.maxLabelCount = maxLabelCount;
      this.buckets = null;
      return this;
   }

   private List initLabels() {
      Normalizer2 nfkdNormalizer = Normalizer2.getNFKDInstance();
      List indexCharacters = new ArrayList();
      String firstScriptBoundary = (String)this.firstCharsInScripts.get(0);
      String overflowBoundary = (String)this.firstCharsInScripts.get(this.firstCharsInScripts.size() - 1);
      Iterator var5 = this.initialLabels.iterator();

      while(true) {
         String item;
         boolean checkDistinct;
         do {
            do {
               do {
                  if (!var5.hasNext()) {
                     int size = indexCharacters.size() - 1;
                     if (size > this.maxLabelCount) {
                        int count = 0;
                        int old = -1;
                        Iterator it = indexCharacters.iterator();

                        while(it.hasNext()) {
                           ++count;
                           it.next();
                           int bump = count * this.maxLabelCount / size;
                           if (bump == old) {
                              it.remove();
                           } else {
                              old = bump;
                           }
                        }
                     }

                     return indexCharacters;
                  }

                  item = (String)var5.next();
                  if (!UTF16.hasMoreCodePointsThan((String)item, 1)) {
                     checkDistinct = false;
                  } else if (item.charAt(item.length() - 1) == '*' && item.charAt(item.length() - 2) != '*') {
                     item = item.substring(0, item.length() - 1);
                     checkDistinct = false;
                  } else {
                     checkDistinct = true;
                  }
               } while(this.collatorPrimaryOnly.compare(item, firstScriptBoundary) < 0);
            } while(this.collatorPrimaryOnly.compare(item, overflowBoundary) >= 0);
         } while(checkDistinct && this.collatorPrimaryOnly.compare(item, this.separated(item)) == 0);

         int insertionPoint = Collections.binarySearch(indexCharacters, item, this.collatorPrimaryOnly);
         if (insertionPoint < 0) {
            indexCharacters.add(~insertionPoint, item);
         } else {
            String itemAlreadyIn = (String)indexCharacters.get(insertionPoint);
            if (isOneLabelBetterThanOther(nfkdNormalizer, item, itemAlreadyIn)) {
               indexCharacters.set(insertionPoint, item);
            }
         }
      }
   }

   private static String fixLabel(String current) {
      if (!current.startsWith("\ufdd0")) {
         return current;
      } else {
         int rest = current.charAt("\ufdd0".length());
         return 10240 < rest && rest <= 10495 ? rest - 10240 + "劃" : current.substring("\ufdd0".length());
      }
   }

   private void addIndexExemplars(ULocale locale) {
      UnicodeSet exemplars = LocaleData.getExemplarSet(locale, 0, 2);
      if (exemplars != null) {
         this.initialLabels.addAll(exemplars);
      } else {
         exemplars = LocaleData.getExemplarSet(locale, 0, 0);
         exemplars = exemplars.cloneAsThawed();
         if (exemplars.containsSome(97, 122) || exemplars.size() == 0) {
            exemplars.addAll(97, 122);
         }

         if (exemplars.containsSome(44032, 55203)) {
            exemplars.remove(44032, 55203).add(44032).add(45208).add(45796).add(46972).add(47560).add(48148).add(49324).add(50500).add(51088).add(52264).add(52852).add(53440).add(54028).add(54616);
         }

         if (exemplars.containsSome(4608, 4991)) {
            UnicodeSet ethiopic = new UnicodeSet("[[:Block=Ethiopic:]&[:Script=Ethiopic:]]");
            UnicodeSetIterator it = new UnicodeSetIterator(ethiopic);

            while(it.next() && it.codepoint != UnicodeSetIterator.IS_STRING) {
               if ((it.codepoint & 7) != 0) {
                  exemplars.remove(it.codepoint);
               }
            }
         }

         Iterator var5 = exemplars.iterator();

         while(var5.hasNext()) {
            String item = (String)var5.next();
            this.initialLabels.add((CharSequence)UCharacter.toUpperCase(locale, item));
         }

      }
   }

   private boolean addChineseIndexCharacters() {
      UnicodeSet contractions = new UnicodeSet();

      try {
         this.collatorPrimaryOnly.internalAddContractions("\ufdd0".charAt(0), contractions);
      } catch (Exception var5) {
         return false;
      }

      if (contractions.isEmpty()) {
         return false;
      } else {
         this.initialLabels.addAll(contractions);
         Iterator var2 = contractions.iterator();

         while(var2.hasNext()) {
            String s = (String)var2.next();

            assert s.startsWith("\ufdd0");

            char c = s.charAt(s.length() - 1);
            if ('A' <= c && c <= 'Z') {
               this.initialLabels.add(65, 90);
               break;
            }
         }

         return true;
      }
   }

   private String separated(String item) {
      StringBuilder result = new StringBuilder();
      char last = item.charAt(0);
      result.append(last);

      for(int i = 1; i < item.length(); ++i) {
         char ch = item.charAt(i);
         if (!UCharacter.isHighSurrogate(last) || !UCharacter.isLowSurrogate(ch)) {
            result.append('͏');
         }

         result.append(ch);
         last = ch;
      }

      return result.toString();
   }

   public ImmutableIndex buildImmutableIndex() {
      BucketList immutableBucketList;
      if (this.inputList != null && !this.inputList.isEmpty()) {
         immutableBucketList = this.createBucketList();
      } else {
         if (this.buckets == null) {
            this.buckets = this.createBucketList();
         }

         immutableBucketList = this.buckets;
      }

      return new ImmutableIndex(immutableBucketList, this.collatorPrimaryOnly);
   }

   public List getBucketLabels() {
      this.initBuckets();
      ArrayList result = new ArrayList();
      Iterator var2 = this.buckets.iterator();

      while(var2.hasNext()) {
         Bucket bucket = (Bucket)var2.next();
         result.add(bucket.getLabel());
      }

      return result;
   }

   public RuleBasedCollator getCollator() {
      if (this.collatorExternal == null) {
         try {
            this.collatorExternal = (RuleBasedCollator)((RuleBasedCollator)this.collatorOriginal.clone());
         } catch (Exception var2) {
            throw new IllegalStateException("Collator cannot be cloned", var2);
         }
      }

      return this.collatorExternal;
   }

   public AlphabeticIndex addRecord(CharSequence name, Object data) {
      this.buckets = null;
      if (this.inputList == null) {
         this.inputList = new ArrayList();
      }

      this.inputList.add(new Record(name, data));
      return this;
   }

   public int getBucketIndex(CharSequence name) {
      this.initBuckets();
      return this.buckets.getBucketIndex(name, this.collatorPrimaryOnly);
   }

   public AlphabeticIndex clearRecords() {
      if (this.inputList != null && !this.inputList.isEmpty()) {
         this.inputList.clear();
         this.buckets = null;
      }

      return this;
   }

   public int getBucketCount() {
      this.initBuckets();
      return this.buckets.getBucketCount();
   }

   public int getRecordCount() {
      return this.inputList != null ? this.inputList.size() : 0;
   }

   public Iterator iterator() {
      this.initBuckets();
      return this.buckets.iterator();
   }

   private void initBuckets() {
      if (this.buckets == null) {
         this.buckets = this.createBucketList();
         if (this.inputList != null && !this.inputList.isEmpty()) {
            Collections.sort(this.inputList, this.recordComparator);
            Iterator bucketIterator = this.buckets.fullIterator();
            Bucket currentBucket = (Bucket)bucketIterator.next();
            Bucket nextBucket;
            String upperBoundary;
            if (bucketIterator.hasNext()) {
               nextBucket = (Bucket)bucketIterator.next();
               upperBoundary = nextBucket.lowerBoundary;
            } else {
               nextBucket = null;
               upperBoundary = null;
            }

            Record r;
            Bucket bucket;
            for(Iterator var5 = this.inputList.iterator(); var5.hasNext(); bucket.records.add(r)) {
               r = (Record)var5.next();

               while(upperBoundary != null && this.collatorPrimaryOnly.compare(r.name, upperBoundary) >= 0) {
                  currentBucket = nextBucket;
                  if (bucketIterator.hasNext()) {
                     nextBucket = (Bucket)bucketIterator.next();
                     upperBoundary = nextBucket.lowerBoundary;
                  } else {
                     upperBoundary = null;
                  }
               }

               bucket = currentBucket;
               if (currentBucket.displayBucket != null) {
                  bucket = currentBucket.displayBucket;
               }

               if (bucket.records == null) {
                  bucket.records = new ArrayList();
               }
            }

         }
      }
   }

   private static boolean isOneLabelBetterThanOther(Normalizer2 nfkdNormalizer, String one, String other) {
      String n1 = nfkdNormalizer.normalize(one);
      String n2 = nfkdNormalizer.normalize(other);
      int result = n1.codePointCount(0, n1.length()) - n2.codePointCount(0, n2.length());
      if (result != 0) {
         return result < 0;
      } else {
         result = binaryCmp.compare(n1, n2);
         if (result != 0) {
            return result < 0;
         } else {
            return binaryCmp.compare(one, other) < 0;
         }
      }
   }

   private BucketList createBucketList() {
      List indexCharacters = this.initLabels();
      long variableTop;
      if (this.collatorPrimaryOnly.isAlternateHandlingShifted()) {
         variableTop = (long)this.collatorPrimaryOnly.getVariableTop() & 4294967295L;
      } else {
         variableTop = 0L;
      }

      boolean hasInvisibleBuckets = false;
      Bucket[] asciiBuckets = new Bucket[26];
      Bucket[] pinyinBuckets = new Bucket[26];
      boolean hasPinyin = false;
      ArrayList bucketList = new ArrayList();
      bucketList.add(new Bucket(this.getUnderflowLabel(), "", AlphabeticIndex.Bucket.LabelType.UNDERFLOW));
      int scriptIndex = -1;
      String scriptUpperBoundary = "";
      Iterator var11 = indexCharacters.iterator();

      while(true) {
         while(true) {
            String current;
            Bucket bucket;
            do {
               do {
                  do {
                     if (!var11.hasNext()) {
                        if (bucketList.size() == 1) {
                           return new BucketList(bucketList, bucketList);
                        }

                        bucketList.add(new Bucket(this.getOverflowLabel(), scriptUpperBoundary, AlphabeticIndex.Bucket.LabelType.OVERFLOW));
                        if (hasPinyin) {
                           Bucket asciiBucket = null;

                           for(int i = 0; i < 26; ++i) {
                              if (asciiBuckets[i] != null) {
                                 asciiBucket = asciiBuckets[i];
                              }

                              if (pinyinBuckets[i] != null && asciiBucket != null) {
                                 pinyinBuckets[i].displayBucket = asciiBucket;
                                 hasInvisibleBuckets = true;
                              }
                           }
                        }

                        if (!hasInvisibleBuckets) {
                           return new BucketList(bucketList, bucketList);
                        }

                        int i = bucketList.size() - 1;
                        Bucket nextBucket = (Bucket)bucketList.get(i);

                        while(true) {
                           while(true) {
                              do {
                                 --i;
                                 if (i <= 0) {
                                    ArrayList publicBucketList = new ArrayList();
                                    Iterator var24 = bucketList.iterator();

                                    while(var24.hasNext()) {
                                       Bucket bucket = (Bucket)var24.next();
                                       if (bucket.displayBucket == null) {
                                          publicBucketList.add(bucket);
                                       }
                                    }

                                    return new BucketList(bucketList, publicBucketList);
                                 }

                                 bucket = (Bucket)bucketList.get(i);
                              } while(bucket.displayBucket != null);

                              if (bucket.labelType == AlphabeticIndex.Bucket.LabelType.INFLOW && nextBucket.labelType != AlphabeticIndex.Bucket.LabelType.NORMAL) {
                                 bucket.displayBucket = nextBucket;
                              } else {
                                 nextBucket = bucket;
                              }
                           }
                        }
                     }

                     current = (String)var11.next();
                     if (this.collatorPrimaryOnly.compare(current, scriptUpperBoundary) >= 0) {
                        String inflowBoundary = scriptUpperBoundary;
                        boolean skippedScript = false;

                        while(true) {
                           ++scriptIndex;
                           scriptUpperBoundary = (String)this.firstCharsInScripts.get(scriptIndex);
                           if (this.collatorPrimaryOnly.compare(current, scriptUpperBoundary) < 0) {
                              if (skippedScript && bucketList.size() > 1) {
                                 bucketList.add(new Bucket(this.getInflowLabel(), inflowBoundary, AlphabeticIndex.Bucket.LabelType.INFLOW));
                              }
                              break;
                           }

                           skippedScript = true;
                        }
                     }

                     bucket = new Bucket(fixLabel(current), current, AlphabeticIndex.Bucket.LabelType.NORMAL);
                     bucketList.add(bucket);
                     char c;
                     if (current.length() == 1 && 'A' <= (c = current.charAt(0)) && c <= 'Z') {
                        asciiBuckets[c - 65] = bucket;
                     } else if (current.length() == "\ufdd0".length() + 1 && current.startsWith("\ufdd0") && 'A' <= (c = current.charAt("\ufdd0".length())) && c <= 'Z') {
                        pinyinBuckets[c - 65] = bucket;
                        hasPinyin = true;
                     }
                  } while(current.startsWith("\ufdd0"));
               } while(!hasMultiplePrimaryWeights(this.collatorPrimaryOnly, variableTop, current));
            } while(current.endsWith("\uffff"));

            int i = bucketList.size() - 2;

            while(true) {
               Bucket singleBucket = (Bucket)bucketList.get(i);
               if (singleBucket.labelType != AlphabeticIndex.Bucket.LabelType.NORMAL) {
                  break;
               }

               if (singleBucket.displayBucket == null && !hasMultiplePrimaryWeights(this.collatorPrimaryOnly, variableTop, singleBucket.lowerBoundary)) {
                  bucket = new Bucket("", current + "\uffff", AlphabeticIndex.Bucket.LabelType.NORMAL);
                  bucket.displayBucket = singleBucket;
                  bucketList.add(bucket);
                  hasInvisibleBuckets = true;
                  break;
               }

               --i;
            }
         }
      }
   }

   private static boolean hasMultiplePrimaryWeights(RuleBasedCollator coll, long variableTop, String s) {
      long[] ces = coll.internalGetCEs(s);
      boolean seenPrimary = false;

      for(int i = 0; i < ces.length; ++i) {
         long ce = ces[i];
         long p = ce >>> 32;
         if (p > variableTop) {
            if (seenPrimary) {
               return true;
            }

            seenPrimary = true;
         }
      }

      return false;
   }

   /** @deprecated */
   @Deprecated
   public List getFirstCharactersInScripts() {
      List dest = new ArrayList(200);
      UnicodeSet set = new UnicodeSet();
      this.collatorPrimaryOnly.internalAddContractions(64977, set);
      if (set.isEmpty()) {
         throw new UnsupportedOperationException("AlphabeticIndex requires script-first-primary contractions");
      } else {
         Iterator var3 = set.iterator();

         while(var3.hasNext()) {
            String boundary = (String)var3.next();
            int gcMask = 1 << UCharacter.getType(boundary.codePointAt(1));
            if ((gcMask & 63) != 0) {
               dest.add(boundary);
            }
         }

         return dest;
      }
   }

   private static class BucketList implements Iterable {
      private final ArrayList bucketList;
      private final List immutableVisibleList;

      private BucketList(ArrayList bucketList, ArrayList publicBucketList) {
         this.bucketList = bucketList;
         int displayIndex = 0;
         Iterator var4 = publicBucketList.iterator();

         while(var4.hasNext()) {
            Bucket bucket = (Bucket)var4.next();
            bucket.displayIndex = displayIndex++;
         }

         this.immutableVisibleList = Collections.unmodifiableList(publicBucketList);
      }

      private int getBucketCount() {
         return this.immutableVisibleList.size();
      }

      private int getBucketIndex(CharSequence name, Collator collatorPrimaryOnly) {
         int start = 0;
         int limit = this.bucketList.size();

         while(start + 1 < limit) {
            int i = (start + limit) / 2;
            Bucket bucket = (Bucket)this.bucketList.get(i);
            int nameVsBucket = collatorPrimaryOnly.compare((Object)name, (Object)bucket.lowerBoundary);
            if (nameVsBucket < 0) {
               limit = i;
            } else {
               start = i;
            }
         }

         Bucket bucket = (Bucket)this.bucketList.get(start);
         if (bucket.displayBucket != null) {
            bucket = bucket.displayBucket;
         }

         return bucket.displayIndex;
      }

      private Iterator fullIterator() {
         return this.bucketList.iterator();
      }

      public Iterator iterator() {
         return this.immutableVisibleList.iterator();
      }

      // $FF: synthetic method
      BucketList(ArrayList x0, ArrayList x1, Object x2) {
         this(x0, x1);
      }
   }

   public static class Bucket implements Iterable {
      private final String label;
      private final String lowerBoundary;
      private final LabelType labelType;
      private Bucket displayBucket;
      private int displayIndex;
      private List records;

      private Bucket(String label, String lowerBoundary, LabelType labelType) {
         this.label = label;
         this.lowerBoundary = lowerBoundary;
         this.labelType = labelType;
      }

      public String getLabel() {
         return this.label;
      }

      public LabelType getLabelType() {
         return this.labelType;
      }

      public int size() {
         return this.records == null ? 0 : this.records.size();
      }

      public Iterator iterator() {
         return this.records == null ? Collections.emptyList().iterator() : this.records.iterator();
      }

      public String toString() {
         return "{labelType=" + this.labelType + ", lowerBoundary=" + this.lowerBoundary + ", label=" + this.label + "}";
      }

      // $FF: synthetic method
      Bucket(String x0, String x1, LabelType x2, Object x3) {
         this(x0, x1, x2);
      }

      public static enum LabelType {
         NORMAL,
         UNDERFLOW,
         INFLOW,
         OVERFLOW;
      }
   }

   public static class Record {
      private final CharSequence name;
      private final Object data;

      private Record(CharSequence name, Object data) {
         this.name = name;
         this.data = data;
      }

      public CharSequence getName() {
         return this.name;
      }

      public Object getData() {
         return this.data;
      }

      public String toString() {
         return this.name + "=" + this.data;
      }

      // $FF: synthetic method
      Record(CharSequence x0, Object x1, Object x2) {
         this(x0, x1);
      }
   }

   public static final class ImmutableIndex implements Iterable {
      private final BucketList buckets;
      private final Collator collatorPrimaryOnly;

      private ImmutableIndex(BucketList bucketList, Collator collatorPrimaryOnly) {
         this.buckets = bucketList;
         this.collatorPrimaryOnly = collatorPrimaryOnly;
      }

      public int getBucketCount() {
         return this.buckets.getBucketCount();
      }

      public int getBucketIndex(CharSequence name) {
         return this.buckets.getBucketIndex(name, this.collatorPrimaryOnly);
      }

      public Bucket getBucket(int index) {
         return 0 <= index && index < this.buckets.getBucketCount() ? (Bucket)this.buckets.immutableVisibleList.get(index) : null;
      }

      public Iterator iterator() {
         return this.buckets.iterator();
      }

      // $FF: synthetic method
      ImmutableIndex(BucketList x0, Collator x1, Object x2) {
         this(x0, x1);
      }
   }
}
