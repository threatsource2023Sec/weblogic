package com.bea.xml;

public interface SchemaLocalElement extends SchemaField, SchemaAnnotated {
   boolean blockExtension();

   boolean blockRestriction();

   boolean blockSubstitution();

   boolean isAbstract();

   SchemaIdentityConstraint[] getIdentityConstraints();
}
