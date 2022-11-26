package com.bea.staxb.buildtime;

import java.util.HashMap;
import java.util.Map;

public class WildcardParticle {
   public static final WildcardParticle ANY = new WildcardParticle("any");
   public static final WildcardParticle ANYTYPE = new WildcardParticle("anyType");
   private static final Map PARTICLES = new HashMap();
   private final String type;

   private WildcardParticle(String type) {
      this.type = type;
   }

   public int hashCode() {
      return this.type.hashCode();
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof WildcardParticle)) {
         return false;
      } else {
         WildcardParticle that = (WildcardParticle)o;
         return this.type.equals(that.type);
      }
   }

   public String toString() {
      return this.type;
   }

   public static WildcardParticle valueOf(String type) {
      return type == null ? null : (WildcardParticle)PARTICLES.get(type.toLowerCase());
   }

   private static void register(WildcardParticle particle) {
      PARTICLES.put(particle.toString().toLowerCase(), particle);
   }

   static {
      register(ANY);
      register(ANYTYPE);
   }
}
