package org.jboss.weld.logging;

@FunctionalInterface
public interface MessageCallback {
   Object construct(Object... var1);
}
