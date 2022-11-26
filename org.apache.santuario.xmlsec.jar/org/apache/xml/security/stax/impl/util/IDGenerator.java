package org.apache.xml.security.stax.impl.util;

import java.util.UUID;

public class IDGenerator {
   public static String generateID(String prefix) {
      String id = UUID.randomUUID().toString();
      return prefix != null ? prefix + id : "G" + id;
   }
}
