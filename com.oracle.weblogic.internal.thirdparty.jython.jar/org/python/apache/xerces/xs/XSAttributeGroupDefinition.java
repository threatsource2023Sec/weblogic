package org.python.apache.xerces.xs;

public interface XSAttributeGroupDefinition extends XSObject {
   XSObjectList getAttributeUses();

   XSWildcard getAttributeWildcard();

   XSAnnotation getAnnotation();

   XSObjectList getAnnotations();
}
