package org.jboss.weld.util;

public interface ValueHolder {
   Object get();

   default Object getIfPresent() {
      throw new UnsupportedOperationException();
   }
}
