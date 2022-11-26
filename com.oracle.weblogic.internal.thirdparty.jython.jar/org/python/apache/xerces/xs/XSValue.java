package org.python.apache.xerces.xs;

public interface XSValue {
   String getNormalizedValue();

   Object getActualValue();

   XSSimpleTypeDefinition getTypeDefinition();

   XSSimpleTypeDefinition getMemberTypeDefinition();

   XSObjectList getMemberTypeDefinitions();

   short getActualValueType();

   ShortList getListValueTypes();
}
