package com.oracle.jrockit.jfr;

import java.lang.reflect.Field;
import oracle.jrockit.jfr.events.ValueDescriptor;

public class DynamicValue {
   private final ValueDescriptor descriptor;

   public DynamicValue(String id, String name, String description, ContentType contentType, Transition transition, Class valueType) throws InvalidValueException {
      this.descriptor = new ValueDescriptor(id, name, description, (String)null, contentType, transition, (String)null, (Field)null, valueType);
   }

   public DynamicValue(String id, String name, String description, ContentType contentType, Class valueType) throws InvalidValueException {
      this(id, name, description, contentType, Transition.None, valueType);
   }

   public DynamicValue(String id, String name, String description, Class valueType) throws InvalidValueException {
      this(id, name, description, ContentType.None, Transition.None, valueType);
   }

   public DynamicValue(String id, String name, String description, String relationKey, ContentType contentType, Transition transition, String constantPool, Class valueType) throws InvalidValueException {
      this.descriptor = new ValueDescriptor(id, name, description, relationKey, contentType, transition, constantPool, (Field)null, valueType);
   }

   public DynamicValue(String id, String name, String description, String relationKey, ContentType contentType, String constantPool, Class valueType) throws InvalidValueException {
      this(id, name, description, relationKey, contentType, Transition.None, constantPool, valueType);
   }

   public DynamicValue(String id, String name, String description, String relationKey, String constantPool, Class valueType) throws InvalidValueException {
      this(id, name, description, relationKey, ContentType.None, Transition.None, constantPool, valueType);
   }

   ValueDescriptor getDescriptor() {
      return this.descriptor;
   }
}
