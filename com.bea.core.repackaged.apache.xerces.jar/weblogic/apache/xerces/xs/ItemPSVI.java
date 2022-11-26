package weblogic.apache.xerces.xs;

public interface ItemPSVI {
   short VALIDITY_NOTKNOWN = 0;
   short VALIDITY_INVALID = 1;
   short VALIDITY_VALID = 2;
   short VALIDATION_NONE = 0;
   short VALIDATION_PARTIAL = 1;
   short VALIDATION_FULL = 2;

   ItemPSVI constant();

   boolean isConstant();

   String getValidationContext();

   short getValidity();

   short getValidationAttempted();

   StringList getErrorCodes();

   StringList getErrorMessages();

   /** @deprecated */
   String getSchemaNormalizedValue();

   /** @deprecated */
   Object getActualNormalizedValue() throws XSException;

   /** @deprecated */
   short getActualNormalizedValueType() throws XSException;

   /** @deprecated */
   ShortList getItemValueTypes() throws XSException;

   XSValue getSchemaValue();

   XSTypeDefinition getTypeDefinition();

   XSSimpleTypeDefinition getMemberTypeDefinition();

   String getSchemaDefault();

   boolean getIsSchemaSpecified();
}
