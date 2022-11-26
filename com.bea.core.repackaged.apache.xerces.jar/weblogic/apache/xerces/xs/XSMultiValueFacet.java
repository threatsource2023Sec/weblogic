package weblogic.apache.xerces.xs;

import weblogic.apache.xerces.xs.datatypes.ObjectList;

public interface XSMultiValueFacet extends XSObject {
   short getFacetKind();

   StringList getLexicalFacetValues();

   ObjectList getEnumerationValues();

   XSObjectList getAnnotations();
}
