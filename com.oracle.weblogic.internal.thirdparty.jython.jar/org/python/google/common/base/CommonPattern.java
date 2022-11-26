package org.python.google.common.base;

import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
abstract class CommonPattern {
   abstract CommonMatcher matcher(CharSequence var1);

   abstract String pattern();

   abstract int flags();

   public abstract String toString();

   public abstract int hashCode();

   public abstract boolean equals(Object var1);
}
