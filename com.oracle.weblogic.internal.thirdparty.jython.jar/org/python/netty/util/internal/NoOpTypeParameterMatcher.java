package org.python.netty.util.internal;

public final class NoOpTypeParameterMatcher extends TypeParameterMatcher {
   public boolean match(Object msg) {
      return true;
   }
}
