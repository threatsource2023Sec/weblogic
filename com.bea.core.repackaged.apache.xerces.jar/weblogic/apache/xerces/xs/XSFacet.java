package weblogic.apache.xerces.xs;

public interface XSFacet extends XSObject {
   short getFacetKind();

   String getLexicalFacetValue();

   int getIntFacetValue();

   Object getActualFacetValue();

   boolean getFixed();

   XSAnnotation getAnnotation();

   XSObjectList getAnnotations();
}
