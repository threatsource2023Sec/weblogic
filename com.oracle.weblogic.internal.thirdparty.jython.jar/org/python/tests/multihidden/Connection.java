package org.python.tests.multihidden;

class Connection extends ConnectionWrapper implements SpecialConnection {
   public String close(int foo) {
      return "special close";
   }
}
