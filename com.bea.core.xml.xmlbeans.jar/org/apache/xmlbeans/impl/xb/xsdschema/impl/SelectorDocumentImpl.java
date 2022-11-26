package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.impl.values.JavaStringHolderEx;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.SelectorDocument;

public class SelectorDocumentImpl extends XmlComplexContentImpl implements SelectorDocument {
   private static final QName SELECTOR$0 = new QName("http://www.w3.org/2001/XMLSchema", "selector");

   public SelectorDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public SelectorDocument.Selector getSelector() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SelectorDocument.Selector target = null;
         target = (SelectorDocument.Selector)this.get_store().find_element_user((QName)SELECTOR$0, 0);
         return target == null ? null : target;
      }
   }

   public void setSelector(SelectorDocument.Selector selector) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SelectorDocument.Selector target = null;
         target = (SelectorDocument.Selector)this.get_store().find_element_user((QName)SELECTOR$0, 0);
         if (target == null) {
            target = (SelectorDocument.Selector)this.get_store().add_element_user(SELECTOR$0);
         }

         target.set(selector);
      }
   }

   public SelectorDocument.Selector addNewSelector() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SelectorDocument.Selector target = null;
         target = (SelectorDocument.Selector)this.get_store().add_element_user(SELECTOR$0);
         return target;
      }
   }

   public static class SelectorImpl extends AnnotatedImpl implements SelectorDocument.Selector {
      private static final QName XPATH$0 = new QName("", "xpath");

      public SelectorImpl(SchemaType sType) {
         super(sType);
      }

      public String getXpath() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(XPATH$0);
            return target == null ? null : target.getStringValue();
         }
      }

      public SelectorDocument.Selector.Xpath xgetXpath() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SelectorDocument.Selector.Xpath target = null;
            target = (SelectorDocument.Selector.Xpath)this.get_store().find_attribute_user(XPATH$0);
            return target;
         }
      }

      public void setXpath(String xpath) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(XPATH$0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(XPATH$0);
            }

            target.setStringValue(xpath);
         }
      }

      public void xsetXpath(SelectorDocument.Selector.Xpath xpath) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SelectorDocument.Selector.Xpath target = null;
            target = (SelectorDocument.Selector.Xpath)this.get_store().find_attribute_user(XPATH$0);
            if (target == null) {
               target = (SelectorDocument.Selector.Xpath)this.get_store().add_attribute_user(XPATH$0);
            }

            target.set(xpath);
         }
      }

      public static class XpathImpl extends JavaStringHolderEx implements SelectorDocument.Selector.Xpath {
         public XpathImpl(SchemaType sType) {
            super(sType, false);
         }

         protected XpathImpl(SchemaType sType, boolean b) {
            super(sType, b);
         }
      }
   }
}
