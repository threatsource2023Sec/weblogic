package org.python.apache.xerces.xs;

public interface XSModelGroupDefinition extends XSObject {
   XSModelGroup getModelGroup();

   XSAnnotation getAnnotation();

   XSObjectList getAnnotations();
}
