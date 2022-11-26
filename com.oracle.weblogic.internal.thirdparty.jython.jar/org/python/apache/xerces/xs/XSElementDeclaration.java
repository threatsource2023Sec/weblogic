package org.python.apache.xerces.xs;

public interface XSElementDeclaration extends XSTerm {
   XSTypeDefinition getTypeDefinition();

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

   boolean getNillable();

   XSNamedMap getIdentityConstraints();

   XSElementDeclaration getSubstitutionGroupAffiliation();

   boolean isSubstitutionGroupExclusion(short var1);

   short getSubstitutionGroupExclusions();

   boolean isDisallowedSubstitution(short var1);

   short getDisallowedSubstitutions();

   boolean getAbstract();

   XSAnnotation getAnnotation();

   XSObjectList getAnnotations();
}
