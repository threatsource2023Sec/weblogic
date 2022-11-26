package org.opensaml.core.xml.schema.impl;

import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.BaseXMLObjectUnmarshaller;
import org.opensaml.core.xml.schema.XSDateTime;

public class XSDateTimeUnmarshaller extends BaseXMLObjectUnmarshaller {
   protected void processElementContent(XMLObject xmlObject, String elementContent) {
      XSDateTime xsDateTime = (XSDateTime)xmlObject;
      xsDateTime.setValue((new DateTime(elementContent)).withChronology(ISOChronology.getInstanceUTC()));
   }
}
