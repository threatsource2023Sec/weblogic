package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.PatternDocument;

public class PatternDocumentImpl extends XmlComplexContentImpl implements PatternDocument {
   private static final QName PATTERN$0 = new QName("http://www.w3.org/2001/XMLSchema", "pattern");

   public PatternDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public PatternDocument.Pattern getPattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PatternDocument.Pattern target = null;
         target = (PatternDocument.Pattern)this.get_store().find_element_user((QName)PATTERN$0, 0);
         return target == null ? null : target;
      }
   }

   public void setPattern(PatternDocument.Pattern pattern) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PatternDocument.Pattern target = null;
         target = (PatternDocument.Pattern)this.get_store().find_element_user((QName)PATTERN$0, 0);
         if (target == null) {
            target = (PatternDocument.Pattern)this.get_store().add_element_user(PATTERN$0);
         }

         target.set(pattern);
      }
   }

   public PatternDocument.Pattern addNewPattern() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         PatternDocument.Pattern target = null;
         target = (PatternDocument.Pattern)this.get_store().add_element_user(PATTERN$0);
         return target;
      }
   }

   public static class PatternImpl extends NoFixedFacetImpl implements PatternDocument.Pattern {
      public PatternImpl(SchemaType sType) {
         super(sType);
      }
   }
}
