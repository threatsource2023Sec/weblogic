package com.sun.java.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import com.sun.java.xml.ns.javaee.WebFragmentDocument;
import com.sun.java.xml.ns.javaee.WebFragmentType;
import javax.xml.namespace.QName;

public class WebFragmentDocumentImpl extends XmlComplexContentImpl implements WebFragmentDocument {
   private static final long serialVersionUID = 1L;
   private static final QName WEBFRAGMENT$0 = new QName("http://java.sun.com/xml/ns/javaee", "web-fragment");

   public WebFragmentDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public WebFragmentType getWebFragment() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebFragmentType target = null;
         target = (WebFragmentType)this.get_store().find_element_user(WEBFRAGMENT$0, 0);
         return target == null ? null : target;
      }
   }

   public void setWebFragment(WebFragmentType webFragment) {
      this.generatedSetterHelperImpl(webFragment, WEBFRAGMENT$0, 0, (short)1);
   }

   public WebFragmentType addNewWebFragment() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WebFragmentType target = null;
         target = (WebFragmentType)this.get_store().add_element_user(WEBFRAGMENT$0);
         return target;
      }
   }
}
