package org.apache.xmlbeans;

public interface SchemaLocalAttribute extends SchemaField, SchemaAnnotated {
   int PROHIBITED = 1;
   int OPTIONAL = 2;
   int REQUIRED = 3;

   int getUse();
}
