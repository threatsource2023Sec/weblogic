package org.glassfish.hk2.xml.internal.alt;

public interface AdapterInformation {
   boolean isChild();

   AltClass getValueType();

   AltClass getBoundType();

   AltClass getAdapter();
}
