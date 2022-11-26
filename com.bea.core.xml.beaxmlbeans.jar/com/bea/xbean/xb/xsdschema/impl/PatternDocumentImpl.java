package com.bea.xbean.xb.xsdschema.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xbean.xb.xsdschema.PatternDocument;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

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
