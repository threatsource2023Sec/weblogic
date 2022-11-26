package org.python.icu.text;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.python.icu.lang.CharSequences;

class SourceTargetUtility {
   final Transform transform;
   final UnicodeSet sourceCache;
   final Set sourceStrings;
   static final UnicodeSet NON_STARTERS = (new UnicodeSet("[:^ccc=0:]")).freeze();
   static Normalizer2 NFC = Normalizer2.getNFCInstance();

   public SourceTargetUtility(Transform transform) {
      this(transform, (Normalizer2)null);
   }

   public SourceTargetUtility(Transform transform, Normalizer2 normalizer) {
      this.transform = transform;
      if (normalizer != null) {
         this.sourceCache = new UnicodeSet("[:^ccc=0:]");
      } else {
         this.sourceCache = new UnicodeSet();
      }

      this.sourceStrings = new HashSet();

      for(int i = 0; i <= 1114111; ++i) {
         String s = (String)transform.transform(UTF16.valueOf(i));
         boolean added = false;
         if (!CharSequences.equals(i, s)) {
            this.sourceCache.add(i);
            added = true;
         }

         if (normalizer != null) {
            String d = NFC.getDecomposition(i);
            if (d != null) {
               s = (String)transform.transform(d);
               if (!d.equals(s)) {
                  this.sourceStrings.add(d);
               }

               if (!added && !normalizer.isInert(i)) {
                  this.sourceCache.add(i);
               }
            }
         }
      }

      this.sourceCache.freeze();
   }

   public void addSourceTargetSet(Transliterator transliterator, UnicodeSet inputFilter, UnicodeSet sourceSet, UnicodeSet targetSet) {
      UnicodeSet myFilter = transliterator.getFilterAsUnicodeSet(inputFilter);
      UnicodeSet affectedCharacters = (new UnicodeSet(this.sourceCache)).retainAll(myFilter);
      sourceSet.addAll(affectedCharacters);
      Iterator var7 = affectedCharacters.iterator();

      String s;
      while(var7.hasNext()) {
         s = (String)var7.next();
         targetSet.addAll((CharSequence)this.transform.transform(s));
      }

      var7 = this.sourceStrings.iterator();

      while(var7.hasNext()) {
         s = (String)var7.next();
         if (myFilter.containsAll(s)) {
            String t = (String)this.transform.transform(s);
            if (!s.equals(t)) {
               targetSet.addAll((CharSequence)t);
               sourceSet.addAll((CharSequence)s);
            }
         }
      }

   }
}
