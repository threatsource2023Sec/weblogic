package org.python.apache.xerces.impl.dv.xs;

import org.python.apache.xerces.impl.dv.DatatypeException;
import org.python.apache.xerces.impl.dv.InvalidDatatypeFacetException;
import org.python.apache.xerces.impl.dv.InvalidDatatypeValueException;
import org.python.apache.xerces.impl.dv.ValidatedInfo;
import org.python.apache.xerces.impl.dv.ValidationContext;
import org.python.apache.xerces.impl.dv.XSFacets;
import org.python.apache.xerces.impl.dv.XSSimpleType;
import org.python.apache.xerces.xs.StringList;
import org.python.apache.xerces.xs.XSNamespaceItem;
import org.python.apache.xerces.xs.XSObject;
import org.python.apache.xerces.xs.XSObjectList;
import org.python.apache.xerces.xs.XSSimpleTypeDefinition;
import org.python.apache.xerces.xs.XSTypeDefinition;

public class XSSimpleTypeDelegate implements XSSimpleType {
   protected final XSSimpleType type;

   public XSSimpleTypeDelegate(XSSimpleType var1) {
      if (var1 == null) {
         throw new NullPointerException();
      } else {
         this.type = var1;
      }
   }

   public XSSimpleType getWrappedXSSimpleType() {
      return this.type;
   }

   public XSObjectList getAnnotations() {
      return this.type.getAnnotations();
   }

   public boolean getBounded() {
      return this.type.getBounded();
   }

   public short getBuiltInKind() {
      return this.type.getBuiltInKind();
   }

   public short getDefinedFacets() {
      return this.type.getDefinedFacets();
   }

   public XSObjectList getFacets() {
      return this.type.getFacets();
   }

   public XSObject getFacet(int var1) {
      return this.type.getFacet(var1);
   }

   public boolean getFinite() {
      return this.type.getFinite();
   }

   public short getFixedFacets() {
      return this.type.getFixedFacets();
   }

   public XSSimpleTypeDefinition getItemType() {
      return this.type.getItemType();
   }

   public StringList getLexicalEnumeration() {
      return this.type.getLexicalEnumeration();
   }

   public String getLexicalFacetValue(short var1) {
      return this.type.getLexicalFacetValue(var1);
   }

   public StringList getLexicalPattern() {
      return this.type.getLexicalPattern();
   }

   public XSObjectList getMemberTypes() {
      return this.type.getMemberTypes();
   }

   public XSObjectList getMultiValueFacets() {
      return this.type.getMultiValueFacets();
   }

   public boolean getNumeric() {
      return this.type.getNumeric();
   }

   public short getOrdered() {
      return this.type.getOrdered();
   }

   public XSSimpleTypeDefinition getPrimitiveType() {
      return this.type.getPrimitiveType();
   }

   public short getVariety() {
      return this.type.getVariety();
   }

   public boolean isDefinedFacet(short var1) {
      return this.type.isDefinedFacet(var1);
   }

   public boolean isFixedFacet(short var1) {
      return this.type.isFixedFacet(var1);
   }

   public boolean derivedFrom(String var1, String var2, short var3) {
      return this.type.derivedFrom(var1, var2, var3);
   }

   public boolean derivedFromType(XSTypeDefinition var1, short var2) {
      return this.type.derivedFromType(var1, var2);
   }

   public boolean getAnonymous() {
      return this.type.getAnonymous();
   }

   public XSTypeDefinition getBaseType() {
      return this.type.getBaseType();
   }

   public short getFinal() {
      return this.type.getFinal();
   }

   public short getTypeCategory() {
      return this.type.getTypeCategory();
   }

   public boolean isFinal(short var1) {
      return this.type.isFinal(var1);
   }

   public String getName() {
      return this.type.getName();
   }

   public String getNamespace() {
      return this.type.getNamespace();
   }

   public XSNamespaceItem getNamespaceItem() {
      return this.type.getNamespaceItem();
   }

   public short getType() {
      return this.type.getType();
   }

   public void applyFacets(XSFacets var1, short var2, short var3, ValidationContext var4) throws InvalidDatatypeFacetException {
      this.type.applyFacets(var1, var2, var3, var4);
   }

   public short getPrimitiveKind() {
      return this.type.getPrimitiveKind();
   }

   public short getWhitespace() throws DatatypeException {
      return this.type.getWhitespace();
   }

   public boolean isEqual(Object var1, Object var2) {
      return this.type.isEqual(var1, var2);
   }

   public boolean isIDType() {
      return this.type.isIDType();
   }

   public void validate(ValidationContext var1, ValidatedInfo var2) throws InvalidDatatypeValueException {
      this.type.validate(var1, var2);
   }

   public Object validate(String var1, ValidationContext var2, ValidatedInfo var3) throws InvalidDatatypeValueException {
      return this.type.validate(var1, var2, var3);
   }

   public Object validate(Object var1, ValidationContext var2, ValidatedInfo var3) throws InvalidDatatypeValueException {
      return this.type.validate(var1, var2, var3);
   }

   public String toString() {
      return this.type.toString();
   }
}
