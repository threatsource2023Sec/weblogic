package org.apache.xmlbeans.impl.xb.xsdschema.impl;

import javax.xml.namespace.QName;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.impl.values.JavaStringEnumerationHolderEx;
import org.apache.xmlbeans.impl.xb.xsdschema.NamespaceList;
import org.apache.xmlbeans.impl.xb.xsdschema.Wildcard;

public class WildcardImpl extends AnnotatedImpl implements Wildcard {
   private static final QName NAMESPACE$0 = new QName("", "namespace");
   private static final QName PROCESSCONTENTS$2 = new QName("", "processContents");

   public WildcardImpl(SchemaType sType) {
      super(sType);
   }

   public Object getNamespace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAMESPACE$0);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(NAMESPACE$0);
         }

         return target == null ? null : target.getObjectValue();
      }
   }

   public NamespaceList xgetNamespace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamespaceList target = null;
         target = (NamespaceList)this.get_store().find_attribute_user(NAMESPACE$0);
         if (target == null) {
            target = (NamespaceList)this.get_default_attribute_value(NAMESPACE$0);
         }

         return target;
      }
   }

   public boolean isSetNamespace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(NAMESPACE$0) != null;
      }
   }

   public void setNamespace(Object namespace) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(NAMESPACE$0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(NAMESPACE$0);
         }

         target.setObjectValue(namespace);
      }
   }

   public void xsetNamespace(NamespaceList namespace) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         NamespaceList target = null;
         target = (NamespaceList)this.get_store().find_attribute_user(NAMESPACE$0);
         if (target == null) {
            target = (NamespaceList)this.get_store().add_attribute_user(NAMESPACE$0);
         }

         target.set(namespace);
      }
   }

   public void unsetNamespace() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(NAMESPACE$0);
      }
   }

   public Wildcard.ProcessContents.Enum getProcessContents() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(PROCESSCONTENTS$2);
         if (target == null) {
            target = (SimpleValue)this.get_default_attribute_value(PROCESSCONTENTS$2);
         }

         return target == null ? null : (Wildcard.ProcessContents.Enum)target.getEnumValue();
      }
   }

   public Wildcard.ProcessContents xgetProcessContents() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wildcard.ProcessContents target = null;
         target = (Wildcard.ProcessContents)this.get_store().find_attribute_user(PROCESSCONTENTS$2);
         if (target == null) {
            target = (Wildcard.ProcessContents)this.get_default_attribute_value(PROCESSCONTENTS$2);
         }

         return target;
      }
   }

   public boolean isSetProcessContents() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().find_attribute_user(PROCESSCONTENTS$2) != null;
      }
   }

   public void setProcessContents(Wildcard.ProcessContents.Enum processContents) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_attribute_user(PROCESSCONTENTS$2);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_attribute_user(PROCESSCONTENTS$2);
         }

         target.setEnumValue(processContents);
      }
   }

   public void xsetProcessContents(Wildcard.ProcessContents processContents) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         Wildcard.ProcessContents target = null;
         target = (Wildcard.ProcessContents)this.get_store().find_attribute_user(PROCESSCONTENTS$2);
         if (target == null) {
            target = (Wildcard.ProcessContents)this.get_store().add_attribute_user(PROCESSCONTENTS$2);
         }

         target.set(processContents);
      }
   }

   public void unsetProcessContents() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_attribute(PROCESSCONTENTS$2);
      }
   }

   public static class ProcessContentsImpl extends JavaStringEnumerationHolderEx implements Wildcard.ProcessContents {
      public ProcessContentsImpl(SchemaType sType) {
         super(sType, false);
      }

      protected ProcessContentsImpl(SchemaType sType, boolean b) {
         super(sType, b);
      }
   }
}
