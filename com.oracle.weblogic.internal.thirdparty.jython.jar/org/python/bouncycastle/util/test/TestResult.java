package org.python.bouncycastle.util.test;

public interface TestResult {
   boolean isSuccessful();

   Throwable getException();

   String toString();
}
