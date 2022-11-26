package com.oracle.weblogic.lifecycle.properties;

public interface ConfidentialPropertyValue extends PropertyValue {
   String getEncryptedValue();

   String getValue();

   boolean isEmpty();
}
