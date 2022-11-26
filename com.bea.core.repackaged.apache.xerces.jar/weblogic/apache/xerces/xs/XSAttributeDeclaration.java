package weblogic.apache.xerces.xs;

public interface XSAttributeDeclaration extends XSObject {
   XSSimpleTypeDefinition getTypeDefinition();

   short getScope();

   XSComplexTypeDefinition getEnclosingCTDefinition();

   short getConstraintType();

   /** @deprecated */
   String getConstraintValue();

   /** @deprecated */
   Object getActualVC() throws XSException;

   /** @deprecated */
   short getActualVCType() throws XSException;

   /** @deprecated */
   ShortList getItemValueTypes() throws XSException;

   XSValue getValueConstraintValue();

   XSAnnotation getAnnotation();

   XSObjectList getAnnotations();
}
