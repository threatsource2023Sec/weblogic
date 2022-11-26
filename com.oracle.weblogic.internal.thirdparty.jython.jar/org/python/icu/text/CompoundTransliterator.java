package org.python.icu.text;

import java.util.List;

class CompoundTransliterator extends Transliterator {
   private Transliterator[] trans;
   private int numAnonymousRBTs;

   CompoundTransliterator(List list) {
      this(list, 0);
   }

   CompoundTransliterator(List list, int numAnonymousRBTs) {
      super("", (UnicodeFilter)null);
      this.numAnonymousRBTs = 0;
      this.trans = null;
      this.init(list, 0, false);
      this.numAnonymousRBTs = numAnonymousRBTs;
   }

   CompoundTransliterator(String id, UnicodeFilter filter2, Transliterator[] trans2, int numAnonymousRBTs2) {
      super(id, filter2);
      this.numAnonymousRBTs = 0;
      this.trans = trans2;
      this.numAnonymousRBTs = numAnonymousRBTs2;
   }

   private void init(List list, int direction, boolean fixReverseID) {
      int count = list.size();
      this.trans = new Transliterator[count];

      int i;
      for(i = 0; i < count; ++i) {
         int j = direction == 0 ? i : count - 1 - i;
         this.trans[i] = (Transliterator)list.get(j);
      }

      if (direction == 1 && fixReverseID) {
         StringBuilder newID = new StringBuilder();

         for(i = 0; i < count; ++i) {
            if (i > 0) {
               newID.append(';');
            }

            newID.append(this.trans[i].getID());
         }

         this.setID(newID.toString());
      }

      this.computeMaximumContextLength();
   }

   public int getCount() {
      return this.trans.length;
   }

   public Transliterator getTransliterator(int index) {
      return this.trans[index];
   }

   private static void _smartAppend(StringBuilder buf, char c) {
      if (buf.length() != 0 && buf.charAt(buf.length() - 1) != c) {
         buf.append(c);
      }

   }

   public String toRules(boolean escapeUnprintable) {
      StringBuilder rulesSource = new StringBuilder();
      if (this.numAnonymousRBTs >= 1 && this.getFilter() != null) {
         rulesSource.append("::").append(this.getFilter().toPattern(escapeUnprintable)).append(';');
      }

      for(int i = 0; i < this.trans.length; ++i) {
         String rule;
         if (this.trans[i].getID().startsWith("%Pass")) {
            rule = this.trans[i].toRules(escapeUnprintable);
            if (this.numAnonymousRBTs > 1 && i > 0 && this.trans[i - 1].getID().startsWith("%Pass")) {
               rule = "::Null;" + rule;
            }
         } else if (this.trans[i].getID().indexOf(59) >= 0) {
            rule = this.trans[i].toRules(escapeUnprintable);
         } else {
            rule = this.trans[i].baseToRules(escapeUnprintable);
         }

         _smartAppend(rulesSource, '\n');
         rulesSource.append(rule);
         _smartAppend(rulesSource, ';');
      }

      return rulesSource.toString();
   }

   public void addSourceTargetSet(UnicodeSet filter, UnicodeSet sourceSet, UnicodeSet targetSet) {
      UnicodeSet myFilter = new UnicodeSet(this.getFilterAsUnicodeSet(filter));
      UnicodeSet tempTargetSet = new UnicodeSet();

      for(int i = 0; i < this.trans.length; ++i) {
         tempTargetSet.clear();
         this.trans[i].addSourceTargetSet(myFilter, sourceSet, tempTargetSet);
         targetSet.addAll(tempTargetSet);
         myFilter.addAll(tempTargetSet);
      }

   }

   protected void handleTransliterate(Replaceable text, Transliterator.Position index, boolean incremental) {
      if (this.trans.length < 1) {
         index.start = index.limit;
      } else {
         int compoundLimit = index.limit;
         int compoundStart = index.start;
         int delta = 0;
         StringBuffer log = null;

         for(int i = 0; i < this.trans.length; ++i) {
            index.start = compoundStart;
            int limit = index.limit;
            if (index.start == index.limit) {
               break;
            }

            this.trans[i].filteredTransliterate(text, index, incremental);
            if (!incremental && index.start != index.limit) {
               throw new RuntimeException("ERROR: Incomplete non-incremental transliteration by " + this.trans[i].getID());
            }

            delta += index.limit - limit;
            if (incremental) {
               index.limit = index.start;
            }
         }

         compoundLimit += delta;
         index.limit = compoundLimit;
      }
   }

   private void computeMaximumContextLength() {
      int max = 0;

      for(int i = 0; i < this.trans.length; ++i) {
         int len = this.trans[i].getMaximumContextLength();
         if (len > max) {
            max = len;
         }
      }

      this.setMaximumContextLength(max);
   }

   public Transliterator safeClone() {
      UnicodeFilter filter = this.getFilter();
      if (filter != null && filter instanceof UnicodeSet) {
         filter = new UnicodeSet((UnicodeSet)filter);
      }

      return new CompoundTransliterator(this.getID(), (UnicodeFilter)filter, this.trans, this.numAnonymousRBTs);
   }
}
