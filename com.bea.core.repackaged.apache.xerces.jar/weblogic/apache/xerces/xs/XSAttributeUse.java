package weblogic.apache.xerces.xs;

public interface XSAttributeUse extends XSObject {
   boolean getRequired();

   XSAttributeDeclaration getAttrDeclaration();

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

   XSObjectList getAnnotations();
}
