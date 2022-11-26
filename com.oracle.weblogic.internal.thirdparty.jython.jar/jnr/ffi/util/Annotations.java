package jnr.ffi.util;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public final class Annotations {
   public static final Collection EMPTY_ANNOTATIONS = Collections.emptyList();

   private Annotations() {
   }

   public static Collection sortedAnnotationCollection(Annotation[] annotations) {
      if (annotations.length > 1) {
         return sortedAnnotationCollection((Collection)Arrays.asList(annotations));
      } else {
         return annotations.length > 0 ? Collections.singletonList(annotations[0]) : Collections.emptyList();
      }
   }

   public static Collection sortedAnnotationCollection(Collection annotations) {
      if (annotations.size() >= 2 && (!(annotations instanceof SortedSet) || !(((SortedSet)annotations).comparator() instanceof AnnotationNameComparator))) {
         SortedSet sorted = new TreeSet(AnnotationNameComparator.getInstance());
         sorted.addAll(annotations);
         return Collections.unmodifiableSortedSet(sorted);
      } else {
         return annotations;
      }
   }

   public static final Collection mergeAnnotations(Collection a, Collection b) {
      if (a.isEmpty() && b.isEmpty()) {
         return EMPTY_ANNOTATIONS;
      } else if (!a.isEmpty() && b.isEmpty()) {
         return a;
      } else if (a.isEmpty() && !b.isEmpty()) {
         return b;
      } else {
         List all = new ArrayList(a);
         all.addAll(b);
         return sortedAnnotationCollection((Collection)all);
      }
   }

   public static final Collection mergeAnnotations(Collection... collections) {
      int totalLength = 0;
      Collection[] var2 = collections;
      int var3 = collections.length;

      int var4;
      for(var4 = 0; var4 < var3; ++var4) {
         Collection c = var2[var4];
         totalLength += c.size();
      }

      List all = new ArrayList(totalLength);
      Collection[] var8 = collections;
      var4 = collections.length;

      for(int var9 = 0; var9 < var4; ++var9) {
         Collection c = var8[var9];
         all.addAll(c);
      }

      return sortedAnnotationCollection((Collection)all);
   }
}
