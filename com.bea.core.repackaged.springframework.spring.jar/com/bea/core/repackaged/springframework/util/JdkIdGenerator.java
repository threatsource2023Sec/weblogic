package com.bea.core.repackaged.springframework.util;

import java.util.UUID;

public class JdkIdGenerator implements IdGenerator {
   public UUID generateId() {
      return UUID.randomUUID();
   }
}
