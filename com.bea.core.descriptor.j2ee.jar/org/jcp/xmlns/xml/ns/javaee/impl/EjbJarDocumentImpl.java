package org.jcp.xmlns.xml.ns.javaee.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;
import org.jcp.xmlns.xml.ns.javaee.EjbJarDocument;
import org.jcp.xmlns.xml.ns.javaee.EjbJarType;

public class EjbJarDocumentImpl extends XmlComplexContentImpl implements EjbJarDocument {
   private static final long serialVersionUID = 1L;
   private static final QName EJBJAR$0 = new QName("http://xmlns.jcp.org/xml/ns/javaee", "ejb-jar");

   public EjbJarDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public EjbJarType getEjbJar() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbJarType target = null;
         target = (EjbJarType)this.get_store().find_element_user(EJBJAR$0, 0);
         return target == null ? null : target;
      }
   }

   public void setEjbJar(EjbJarType ejbJar) {
      this.generatedSetterHelperImpl(ejbJar, EJBJAR$0, 0, (short)1);
   }

   public EjbJarType addNewEjbJar() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         EjbJarType target = null;
         target = (EjbJarType)this.get_store().add_element_user(EJBJAR$0);
         return target;
      }
   }
}
