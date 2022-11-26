package org.opensaml.core.xml.schema.impl;

import java.util.Collections;
import java.util.List;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.schema.XSDateTime;

public class XSDateTimeImpl extends AbstractXMLObject implements XSDateTime {
   private DateTime value;
   private DateTimeFormatter formatter = ISODateTimeFormat.dateTime().withChronology(ISOChronology.getInstanceUTC());

   protected XSDateTimeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public DateTime getValue() {
      return this.value;
   }

   public void setValue(DateTime newValue) {
      this.value = (DateTime)this.prepareForAssignment(this.value, newValue);
   }

   public List getOrderedChildren() {
      return Collections.emptyList();
   }

   public DateTimeFormatter getDateTimeFormatter() {
      return this.formatter;
   }

   public void setDateTimeFormatter(DateTimeFormatter newFormatter) {
      this.formatter = (DateTimeFormatter)Constraint.isNotNull(newFormatter, "The formatter cannot be null");
   }
}
