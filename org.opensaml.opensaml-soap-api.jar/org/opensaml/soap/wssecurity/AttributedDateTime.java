package org.opensaml.soap.wssecurity;

import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.schema.XSString;

public interface AttributedDateTime extends XSString, IdBearing, AttributeExtensibleXMLObject, WSSecurityObject {
   String TYPE_LOCAL_NAME = "AttributedDateTime";
   QName TYPE_NAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "AttributedDateTime", "wsu");
   /** @deprecated */
   String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

   DateTime getDateTime();

   void setDateTime(DateTime var1);

   DateTimeFormatter getDateTimeFormatter();

   void setDateTimeFormatter(DateTimeFormatter var1);
}
