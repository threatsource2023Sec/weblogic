package org.python.tests.multihidden;

public class BaseConnection {
   public static Connection newConnection() {
      return new Connection();
   }

   public String close() {
      return "base close";
   }
}
