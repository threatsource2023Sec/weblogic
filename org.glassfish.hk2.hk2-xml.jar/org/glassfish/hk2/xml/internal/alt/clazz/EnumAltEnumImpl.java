package org.glassfish.hk2.xml.internal.alt.clazz;

import org.glassfish.hk2.xml.internal.alt.AltEnum;

public class EnumAltEnumImpl implements AltEnum {
   private final Enum eValue;

   public EnumAltEnumImpl(Enum eValue) {
      this.eValue = eValue;
   }

   public String getDeclaringClass() {
      return this.eValue.getDeclaringClass().getName();
   }

   public String getName() {
      return this.eValue.name();
   }

   public String toString() {
      return "EnumAltEnumImpl(" + this.eValue + "," + System.identityHashCode(this) + ")";
   }
}
