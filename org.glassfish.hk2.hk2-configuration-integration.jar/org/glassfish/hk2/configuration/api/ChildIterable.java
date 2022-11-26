package org.glassfish.hk2.configuration.api;

public interface ChildIterable extends Iterable {
   Object byKey(String var1);

   Iterable handleIterator();
}
