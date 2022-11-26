package org.python.icu.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.python.icu.util.Output;

/** @deprecated */
@Deprecated
public class PluralSamples {
   private PluralRules pluralRules;
   private final Map _keySamplesMap;
   /** @deprecated */
   @Deprecated
   public final Map _keyLimitedMap;
   private final Map _keyFractionSamplesMap;
   private final Set _fractionSamples;
   private static final int[] TENS = new int[]{1, 10, 100, 1000, 10000, 100000, 1000000};
   private static final int LIMIT_FRACTION_SAMPLES = 3;

   /** @deprecated */
   @Deprecated
   public PluralSamples(PluralRules pluralRules) {
      this.pluralRules = pluralRules;
      Set keywords = pluralRules.getKeywords();
      int MAX_SAMPLES = true;
      Map temp = new HashMap();
      Iterator var5 = keywords.iterator();

      while(var5.hasNext()) {
         String k = (String)var5.next();
         temp.put(k, pluralRules.isLimited(k));
      }

      this._keyLimitedMap = temp;
      Map sampleMap = new HashMap();
      int keywordsRemaining = keywords.size();
      int limit = 128;

      for(int i = 0; keywordsRemaining > 0 && i < limit; ++i) {
         keywordsRemaining = this.addSimpleSamples(pluralRules, 3, sampleMap, keywordsRemaining, (double)i / 2.0);
      }

      keywordsRemaining = this.addSimpleSamples(pluralRules, 3, sampleMap, keywordsRemaining, 1000000.0);
      Map sampleFractionMap = new HashMap();
      Set mentioned = new TreeSet();
      Map foundKeywords = new HashMap();
      Iterator var11 = mentioned.iterator();

      PluralRules.FixedDecimal s;
      String keyword;
      while(var11.hasNext()) {
         s = (PluralRules.FixedDecimal)var11.next();
         keyword = pluralRules.select(s);
         this.addRelation(foundKeywords, keyword, s);
      }

      if (foundKeywords.size() != keywords.size()) {
         int i = 1;

         label88:
         while(true) {
            boolean done;
            if (i >= 1000) {
               for(i = 10; i < 1000; ++i) {
                  done = this.addIfNotPresent((double)i / 10.0, mentioned, foundKeywords);
                  if (done) {
                     break label88;
                  }
               }

               System.out.println("Failed to find sample for each keyword: " + foundKeywords + "\n\t" + pluralRules + "\n\t" + mentioned);
               break;
            }

            done = this.addIfNotPresent((double)i, mentioned, foundKeywords);
            if (done) {
               break;
            }

            ++i;
         }
      }

      mentioned.add(new PluralRules.FixedDecimal(0L));
      mentioned.add(new PluralRules.FixedDecimal(1L));
      mentioned.add(new PluralRules.FixedDecimal(2L));
      mentioned.add(new PluralRules.FixedDecimal(0.1, 1));
      mentioned.add(new PluralRules.FixedDecimal(1.99, 2));
      mentioned.addAll(this.fractions(mentioned));

      Object list;
      for(var11 = mentioned.iterator(); var11.hasNext(); ((Set)list).add(s)) {
         s = (PluralRules.FixedDecimal)var11.next();
         keyword = pluralRules.select(s);
         list = (Set)sampleFractionMap.get(keyword);
         if (list == null) {
            list = new LinkedHashSet();
            sampleFractionMap.put(keyword, list);
         }
      }

      if (keywordsRemaining > 0) {
         var11 = keywords.iterator();

         while(var11.hasNext()) {
            String k = (String)var11.next();
            if (!sampleMap.containsKey(k)) {
               sampleMap.put(k, Collections.emptyList());
            }

            if (!sampleFractionMap.containsKey(k)) {
               sampleFractionMap.put(k, Collections.emptySet());
            }
         }
      }

      var11 = sampleMap.entrySet().iterator();

      Map.Entry entry;
      while(var11.hasNext()) {
         entry = (Map.Entry)var11.next();
         sampleMap.put(entry.getKey(), Collections.unmodifiableList((List)entry.getValue()));
      }

      var11 = sampleFractionMap.entrySet().iterator();

      while(var11.hasNext()) {
         entry = (Map.Entry)var11.next();
         sampleFractionMap.put(entry.getKey(), Collections.unmodifiableSet((Set)entry.getValue()));
      }

      this._keySamplesMap = sampleMap;
      this._keyFractionSamplesMap = sampleFractionMap;
      this._fractionSamples = Collections.unmodifiableSet(mentioned);
   }

   private int addSimpleSamples(PluralRules pluralRules, int MAX_SAMPLES, Map sampleMap, int keywordsRemaining, double val) {
      String keyword = pluralRules.select(val);
      boolean keyIsLimited = (Boolean)this._keyLimitedMap.get(keyword);
      List list = (List)sampleMap.get(keyword);
      if (list == null) {
         list = new ArrayList(MAX_SAMPLES);
         sampleMap.put(keyword, list);
      } else if (!keyIsLimited && ((List)list).size() == MAX_SAMPLES) {
         return keywordsRemaining;
      }

      ((List)list).add(val);
      if (!keyIsLimited && ((List)list).size() == MAX_SAMPLES) {
         --keywordsRemaining;
      }

      return keywordsRemaining;
   }

   private void addRelation(Map foundKeywords, String keyword, PluralRules.FixedDecimal s) {
      Set set = (Set)foundKeywords.get(keyword);
      if (set == null) {
         foundKeywords.put(keyword, set = new HashSet());
      }

      ((Set)set).add(s);
   }

   private boolean addIfNotPresent(double d, Set mentioned, Map foundKeywords) {
      PluralRules.FixedDecimal numberInfo = new PluralRules.FixedDecimal(d);
      String keyword = this.pluralRules.select(numberInfo);
      if (!foundKeywords.containsKey(keyword) || keyword.equals("other")) {
         this.addRelation(foundKeywords, keyword, numberInfo);
         mentioned.add(numberInfo);
         if (keyword.equals("other") && ((Set)foundKeywords.get("other")).size() > 1) {
            return true;
         }
      }

      return false;
   }

   private Set fractions(Set original) {
      Set toAddTo = new HashSet();
      Set result = new HashSet();
      Iterator var4 = original.iterator();

      while(var4.hasNext()) {
         PluralRules.FixedDecimal base1 = (PluralRules.FixedDecimal)var4.next();
         result.add((int)base1.integerValue);
      }

      List ints = new ArrayList(result);
      Set keywords = new HashSet();

      for(int j = 0; j < ints.size(); ++j) {
         Integer base = (Integer)ints.get(j);
         String keyword = this.pluralRules.select((double)base);
         if (!keywords.contains(keyword)) {
            keywords.add(keyword);
            toAddTo.add(new PluralRules.FixedDecimal((double)base, 1));
            toAddTo.add(new PluralRules.FixedDecimal((double)base, 2));
            Integer fract = this.getDifferentCategory(ints, keyword);
            if (fract >= TENS[2]) {
               toAddTo.add(new PluralRules.FixedDecimal(base + "." + fract));
            } else {
               for(int visibleFractions = 1; visibleFractions < 3; ++visibleFractions) {
                  for(int i = 1; i <= visibleFractions; ++i) {
                     if (fract < TENS[i]) {
                        toAddTo.add(new PluralRules.FixedDecimal((double)base + (double)fract / (double)TENS[i], visibleFractions));
                     }
                  }
               }
            }
         }
      }

      return toAddTo;
   }

   private Integer getDifferentCategory(List ints, String keyword) {
      for(int i = ints.size() - 1; i >= 0; --i) {
         Integer other = (Integer)ints.get(i);
         String keywordOther = this.pluralRules.select((double)other);
         if (!keywordOther.equals(keyword)) {
            return other;
         }
      }

      return 37;
   }

   /** @deprecated */
   @Deprecated
   public PluralRules.KeywordStatus getStatus(String keyword, int offset, Set explicits, Output uniqueValue) {
      if (uniqueValue != null) {
         uniqueValue.value = null;
      }

      if (!this.pluralRules.getKeywords().contains(keyword)) {
         return PluralRules.KeywordStatus.INVALID;
      } else {
         Collection values = this.pluralRules.getAllKeywordValues(keyword);
         if (values == null) {
            return PluralRules.KeywordStatus.UNBOUNDED;
         } else {
            int originalSize = values.size();
            if (explicits == null) {
               explicits = Collections.emptySet();
            }

            if (originalSize > explicits.size()) {
               if (originalSize == 1) {
                  if (uniqueValue != null) {
                     uniqueValue.value = values.iterator().next();
                  }

                  return PluralRules.KeywordStatus.UNIQUE;
               } else {
                  return PluralRules.KeywordStatus.BOUNDED;
               }
            } else {
               HashSet subtractedSet = new HashSet(values);
               Iterator var8 = explicits.iterator();

               while(var8.hasNext()) {
                  Double explicit = (Double)var8.next();
                  subtractedSet.remove(explicit - (double)offset);
               }

               if (subtractedSet.size() == 0) {
                  return PluralRules.KeywordStatus.SUPPRESSED;
               } else {
                  if (uniqueValue != null && subtractedSet.size() == 1) {
                     uniqueValue.value = subtractedSet.iterator().next();
                  }

                  return originalSize == 1 ? PluralRules.KeywordStatus.UNIQUE : PluralRules.KeywordStatus.BOUNDED;
               }
            }
         }
      }
   }

   Map getKeySamplesMap() {
      return this._keySamplesMap;
   }

   Map getKeyFractionSamplesMap() {
      return this._keyFractionSamplesMap;
   }

   Set getFractionSamples() {
      return this._fractionSamples;
   }

   Collection getAllKeywordValues(String keyword) {
      if (!this.pluralRules.getKeywords().contains(keyword)) {
         return Collections.emptyList();
      } else {
         Collection result = (Collection)this.getKeySamplesMap().get(keyword);
         return result.size() > 2 && !(Boolean)this._keyLimitedMap.get(keyword) ? null : result;
      }
   }
}
