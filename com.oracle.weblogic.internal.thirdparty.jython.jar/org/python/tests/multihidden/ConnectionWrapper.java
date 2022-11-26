package org.python.tests.multihidden;

class ConnectionWrapper extends BaseConnection {
   public String close() {
      return "wrapper close";
   }
}
