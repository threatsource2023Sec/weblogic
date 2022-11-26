package org.glassfish.hk2.xml.internal;

import org.glassfish.hk2.xml.internal.alt.AdapterInformation;
import org.glassfish.hk2.xml.internal.alt.AltClass;

public class AdapterInformationImpl implements AdapterInformation {
   private final boolean isChild;
   private final AltClass valueType;
   private final AltClass boundType;
   private final AltClass adapter;

   public AdapterInformationImpl(AltClass adapter, AltClass valueType, AltClass boundType) {
      this.adapter = adapter;
      this.valueType = valueType;
      this.boundType = boundType;
      this.isChild = valueType.isInterface();
   }

   public boolean isChild() {
      return this.isChild;
   }

   public AltClass getValueType() {
      return this.valueType;
   }

   public AltClass getBoundType() {
      return this.boundType;
   }

   public AltClass getAdapter() {
      return this.adapter;
   }

   public String toString() {
      return "AdapterInformationImpl(" + this.adapter + "," + this.valueType + "," + this.boundType + "," + this.isChild + "," + System.identityHashCode(this) + ")";
   }
}
