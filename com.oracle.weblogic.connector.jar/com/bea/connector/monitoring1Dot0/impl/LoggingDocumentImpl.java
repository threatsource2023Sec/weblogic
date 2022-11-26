package com.bea.connector.monitoring1Dot0.impl;

import com.bea.connector.monitoring1Dot0.LoggingDocument;
import com.bea.connector.monitoring1Dot0.LoggingType;
import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import javax.xml.namespace.QName;

public class LoggingDocumentImpl extends XmlComplexContentImpl implements LoggingDocument {
   private static final long serialVersionUID = 1L;
   private static final QName LOGGING$0 = new QName("http://www.bea.com/connector/monitoring1dot0", "logging");

   public LoggingDocumentImpl(SchemaType sType) {
      super(sType);
   }

   public LoggingType getLogging() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoggingType target = null;
         target = (LoggingType)this.get_store().find_element_user(LOGGING$0, 0);
         return target == null ? null : target;
      }
   }

   public void setLogging(LoggingType logging) {
      this.generatedSetterHelperImpl(logging, LOGGING$0, 0, (short)1);
   }

   public LoggingType addNewLogging() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         LoggingType target = null;
         target = (LoggingType)this.get_store().add_element_user(LOGGING$0);
         return target;
      }
   }
}
