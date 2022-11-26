package org.python.apache.xerces.xs;

public interface XSNotationDeclaration extends XSObject {
   String getSystemId();

   String getPublicId();

   XSAnnotation getAnnotation();

   XSObjectList getAnnotations();
}
