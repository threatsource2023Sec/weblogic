package org.python.tests;

public class BadStaticInitializer {
   static {
      throw new RuntimeException();
   }
}
