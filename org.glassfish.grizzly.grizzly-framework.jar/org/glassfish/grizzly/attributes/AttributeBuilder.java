package org.glassfish.grizzly.attributes;

public interface AttributeBuilder {
   AttributeBuilder DEFAULT_ATTRIBUTE_BUILDER = AttributeBuilderInitializer.initBuilder();

   Attribute createAttribute(String var1);

   Attribute createAttribute(String var1, Object var2);

   Attribute createAttribute(String var1, org.glassfish.grizzly.utils.NullaryFunction var2);

   /** @deprecated */
   Attribute createAttribute(String var1, NullaryFunction var2);

   AttributeHolder createSafeAttributeHolder();

   AttributeHolder createUnsafeAttributeHolder();
}
