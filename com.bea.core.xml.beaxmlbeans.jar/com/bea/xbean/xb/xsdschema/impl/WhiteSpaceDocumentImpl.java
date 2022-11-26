package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.JavaStringEnumerationHolderEx;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.WhiteSpaceDocument;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class WhiteSpaceDocumentImpl extends XmlComplexContentImpl implements WhiteSpaceDocument {
   private static final QName WHITESPACE$0 = new QName("http://www.w3.org/2001/XMLSchema", "whiteSpace");

   public WhiteSpaceDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WhiteSpaceDocument.WhiteSpace getWhiteSpace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WhiteSpaceDocument.WhiteSpace target = null;
         target = (WhiteSpaceDocument.WhiteSpace)this.get_store().find_element_user((QName)WHITESPACE$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWhiteSpace(WhiteSpaceDocument.WhiteSpace whiteSpace) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WhiteSpaceDocument.WhiteSpace target = null;
         target = (WhiteSpaceDocument.WhiteSpace)this.get_store().find_element_user((QName)WHITESPACE$0, 0);
         if (target == null) {
            target = (WhiteSpaceDocument.WhiteSpace)this.get_store().add_element_user(WHITESPACE$0);
         }

         target.set(whiteSpace);
      }
   }

   public WhiteSpaceDocument.WhiteSpace addNewWhiteSpace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WhiteSpaceDocument.WhiteSpace target = null;
         target = (WhiteSpaceDocument.WhiteSpace)this.get_store().add_element_user(WHITESPACE$0);
         return target;
      }
   }

   public static class WhiteSpaceImpl extends FacetImpl implements WhiteSpaceDocument.WhiteSpace {
      public WhiteSpaceImpl(SchemaType sType) {
         super(sType);
      }

      public static class ValueImpl extends JavaStringEnumerationHolderEx implements WhiteSpaceDocument.WhiteSpace.Value {
         public ValueImpl(SchemaType sType) {
            super(sType, false);
         }

         protected ValueImpl(SchemaType sType, boolean b) {
            super(sType, b);
         }
      }
   }
}
