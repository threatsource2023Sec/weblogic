package org.python.icu.util;

public interface Freezable extends Cloneable {
   boolean isFrozen();

   Object freeze();

   Object cloneAsThawed();
}
