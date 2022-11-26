package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.NativeLibdirDocument;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.SimpleValue;
import com.bea.xml.XmlString;
import javax.xml.namespace.QName;

public class NativeLibdirDocumentImpl extends XmlComplexContentImpl implements NativeLibdirDocument {
   private static final long serialVersionUID = 1L;
   private static final QName NATIVELIBDIR$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "native-libdir");

   public NativeLibdirDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public String getNativeLibdir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NATIVELIBDIR$0, 0);
         return target == null ? null : target.getStringValue();
      }
   }

   public XmlString xgetNativeLibdir() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NATIVELIBDIR$0, 0);
         return target;
      }
   }

   public void setNativeLibdir(String nativeLibdir) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         SimpleValue target = null;
         target = (SimpleValue)this.get_store().find_element_user(NATIVELIBDIR$0, 0);
         if (target == null) {
            target = (SimpleValue)this.get_store().add_element_user(NATIVELIBDIR$0);
         }

         target.setStringValue(nativeLibdir);
      }
   }

   public void xsetNativeLibdir(XmlString nativeLibdir) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         XmlString target = null;
         target = (XmlString)this.get_store().find_element_user(NATIVELIBDIR$0, 0);
         if (target == null) {
            target = (XmlString)this.get_store().add_element_user(NATIVELIBDIR$0);
         }

         target.set(nativeLibdir);
      }
   }
}
