package org.python.icu.text;

import org.python.icu.impl.SimpleFilteredSentenceBreakIterator;
import org.python.icu.util.ULocale;

/** @deprecated */
@Deprecated
public abstract class FilteredBreakIteratorBuilder {
   /** @deprecated */
   @Deprecated
   public static FilteredBreakIteratorBuilder createInstance(ULocale where) {
      FilteredBreakIteratorBuilder ret = new SimpleFilteredSentenceBreakIterator.Builder(where);
      return ret;
   }

   /** @deprecated */
   @Deprecated
   public static FilteredBreakIteratorBuilder createInstance() {
      FilteredBreakIteratorBuilder ret = new SimpleFilteredSentenceBreakIterator.Builder();
      return ret;
   }

   /** @deprecated */
   @Deprecated
   public abstract boolean suppressBreakAfter(String var1);

   /** @deprecated */
   @Deprecated
   public abstract boolean unsuppressBreakAfter(String var1);

   /** @deprecated */
   @Deprecated
   public abstract BreakIterator build(BreakIterator var1);

   /** @deprecated */
   @Deprecated
   protected FilteredBreakIteratorBuilder() {
   }
}
