package com.oracle.weblogic.lifecycle.provisioning.core;

import java.util.NoSuchElementException;

public class NoSuchConfigurableAttributeValueException extends NoSuchElementException {
   private static final long serialVersionUID = 1L;
   private final String attributeNamespace;
   private final String attributeName;

   public NoSuchConfigurableAttributeValueException(String namespace, String name) {
      this(namespace, name, (Throwable)null);
   }

   public NoSuchConfigurableAttributeValueException(String namespace, String name, Throwable cause) {
      super(namespace + ":" + name);
      this.initCause(cause);
      this.attributeNamespace = namespace;
      this.attributeName = name;
   }

   public final String getAttributeNamespace() {
      return this.attributeNamespace;
   }

   public final String getAttributeName() {
      return this.attributeName;
   }
}
