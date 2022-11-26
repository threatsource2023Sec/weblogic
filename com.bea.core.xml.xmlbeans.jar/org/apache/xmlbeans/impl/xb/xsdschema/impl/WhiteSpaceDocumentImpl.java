package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.JavaStringEnumerationHolderEx;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.WhiteSpaceDocument;

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
