package org.glassfish.hk2.xml.internal.alt.papi;

import org.glassfish.hk2.xml.internal.alt.AltEnum;

public class StringAltEnumImpl implements AltEnum {
   private final String declaringClass;
   private final String name;

   public StringAltEnumImpl(String declaringClass, String name) {
      this.declaringClass = declaringClass;
      this.name = name;
   }

   public String getDeclaringClass() {
      return this.declaringClass;
   }

   public String getName() {
      return this.name;
   }

   public String toString() {
      return "StringAltEnumImpl(" + this.declaringClass + "," + this.name + "," + System.identityHashCode(this) + ")";
   }
}
