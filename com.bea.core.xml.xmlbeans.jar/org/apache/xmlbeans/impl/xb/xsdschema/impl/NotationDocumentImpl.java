package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.XmlAnyURI;
import org.apache.xmlbeans.XmlNCName;
import org.apache.xmlbeans.impl.values.XmlComplexContentImpl;
import org.apache.xmlbeans.impl.xb.xsdschema.NotationDocument;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

public class NotationDocumentImpl extends XmlComplexContentImpl implements NotationDocument {
   private static final QName NOTATION$0 = new QName("http://www.w3.org/2001/XMLSchema", "notation");

   public NotationDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public NotationDocument.Notation getNotation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NotationDocument.Notation target = null;
         target = (NotationDocument.Notation)this.get_store().find_element_user((QName)NOTATION$0, 0);
         return target == null ? null : target;
      }
   }

   public void setNotation(NotationDocument.Notation notation) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NotationDocument.Notation target = null;
         target = (NotationDocument.Notation)this.get_store().find_element_user((QName)NOTATION$0, 0);
         if (target == null) {
            target = (NotationDocument.Notation)this.get_store().add_element_user(NOTATION$0);
         }

         target.set(notation);
      }
   }

   public NotationDocument.Notation addNewNotation() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NotationDocument.Notation target = null;
         target = (NotationDocument.Notation)this.get_store().add_element_user(NOTATION$0);
         return target;
      }
   }

   public static class NotationImpl extends AnnotatedImpl implements NotationDocument.Notation {
      private static final QName NAME$0 = new QName("", "name");
      private static final QName PUBLIC$2 = new QName("", "public");
      private static final QName SYSTEM$4 = new QName("", "system");

      public NotationImpl(SchemaType sType) {
         super(sType);
      }

      public String getName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(NAME$0);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlNCName xgetName() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlNCName target = null;
            target = (XmlNCName)this.get_store().find_attribute_user(NAME$0);
            return target;
         }
      }

      public void setName(String name) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(NAME$0);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(NAME$0);
            }

            target.setStringValue(name);
         }
      }

      public void xsetName(XmlNCName name) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlNCName target = null;
            target = (XmlNCName)this.get_store().find_attribute_user(NAME$0);
            if (target == null) {
               target = (XmlNCName)this.get_store().add_attribute_user(NAME$0);
            }

            target.set(name);
         }
      }

      public String getPublic() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(PUBLIC$2);
            return target == null ? null : target.getStringValue();
         }
      }

      public Public xgetPublic() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Public target = null;
            target = (Public)this.get_store().find_attribute_user(PUBLIC$2);
            return target;
         }
      }

      public boolean isSetPublic() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(PUBLIC$2) != null;
         }
      }

      public void setPublic(String xpublic) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(PUBLIC$2);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(PUBLIC$2);
            }

            target.setStringValue(xpublic);
         }
      }

      public void xsetPublic(Public xpublic) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            Public target = null;
            target = (Public)this.get_store().find_attribute_user(PUBLIC$2);
            if (target == null) {
               target = (Public)this.get_store().add_attribute_user(PUBLIC$2);
            }

            target.set(xpublic);
         }
      }

      public void unsetPublic() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(PUBLIC$2);
         }
      }

      public String getSystem() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(SYSTEM$4);
            return target == null ? null : target.getStringValue();
         }
      }

      public XmlAnyURI xgetSystem() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlAnyURI target = null;
            target = (XmlAnyURI)this.get_store().find_attribute_user(SYSTEM$4);
            return target;
         }
      }

      public boolean isSetSystem() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return this.get_store().find_attribute_user(SYSTEM$4) != null;
         }
      }

      public void setSystem(String system) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            SimpleValue target = null;
            target = (SimpleValue)this.get_store().find_attribute_user(SYSTEM$4);
            if (target == null) {
               target = (SimpleValue)this.get_store().add_attribute_user(SYSTEM$4);
            }

            target.setStringValue(system);
         }
      }

      public void xsetSystem(XmlAnyURI system) {
         synchronized(this.monitor()) {
            this.check_orphaned();
            XmlAnyURI target = null;
            target = (XmlAnyURI)this.get_store().find_attribute_user(SYSTEM$4);
            if (target == null) {
               target = (XmlAnyURI)this.get_store().add_attribute_user(SYSTEM$4);
            }

            target.set(system);
         }
      }

      public void unsetSystem() {
         synchronized(this.monitor()) {
            this.check_orphaned();
            this.get_store().remove_attribute(SYSTEM$4);
         }
      }
   }
}
