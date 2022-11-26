package com.bea.core.repackaged.springframework.aop.config;

import com.bea.core.repackaged.springframework.beans.factory.parsing.ParseState;
import com.bea.core.repackaged.springframework.util.StringUtils;

public class AspectEntry implements ParseState.Entry {
   private final String id;
   private final String ref;

   public AspectEntry(String id, String ref) {
      this.id = id;
      this.ref = ref;
   }

   public String toString() {
      return "Aspect: " + (StringUtils.hasLength(this.id) ? "id='" + this.id + "'" : "ref='" + this.ref + "'");
   }
}
