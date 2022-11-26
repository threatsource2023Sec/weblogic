package org.opensaml.soap.wssecurity.impl;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.soap.wssecurity.AttributedDateTime;
import org.opensaml.soap.wssecurity.IdBearing;

public class AttributedDateTimeImpl extends AbstractWSSecurityObject implements AttributedDateTime {
   private DateTimeFormatter formatter = ISODateTimeFormat.dateTime().withChronology(ISOChronology.getInstanceUTC());
   private DateTime dateTimeValue;
   private String stringValue;
   private String id;
   private AttributeMap unknownAttributes = new AttributeMap(this);

   public AttributedDateTimeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
      super(namespaceURI, elementLocalName, namespacePrefix);
   }

   public DateTime getDateTime() {
      return this.dateTimeValue;
   }

   public void setDateTime(DateTime newDateTime) {
      this.dateTimeValue = newDateTime;
      String formattedDateTime = this.formatter.print(this.dateTimeValue);
      this.stringValue = this.prepareForAssignment(this.stringValue, formattedDateTime);
   }

   public String getValue() {
      return this.stringValue;
   }

   public void setValue(String newValue) {
      this.dateTimeValue = (new DateTime(newValue)).withChronology(ISOChronology.getInstanceUTC());
      this.stringValue = this.prepareForAssignment(this.stringValue, newValue);
   }

   public String getWSUId() {
      return this.id;
   }

   public void setWSUId(String newId) {
      String oldID = this.id;
      this.id = this.prepareForAssignment(this.id, newId);
      this.registerOwnID(oldID, this.id);
      this.manageQualifiedAttributeNamespace(IdBearing.WSU_ID_ATTR_NAME, this.id != null);
   }

   public AttributeMap getUnknownAttributes() {
      return this.unknownAttributes;
   }

   public DateTimeFormatter getDateTimeFormatter() {
      return this.formatter;
   }

   public void setDateTimeFormatter(DateTimeFormatter newFormatter) {
      if (newFormatter == null) {
         throw new IllegalArgumentException("The specified DateTimeFormatter may not be null");
      } else {
         this.formatter = newFormatter;
         this.setDateTime(this.getDateTime());
      }
   }
}
