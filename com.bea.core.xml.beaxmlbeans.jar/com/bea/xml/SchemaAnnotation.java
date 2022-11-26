package com.bea.xml;

import javax.xml.namespace.QName;

public interface SchemaAnnotation extends SchemaComponent {
   XmlObject[] getApplicationInformation();

   XmlObject[] getUserInformation();

   Attribute[] getAttributes();

   public interface Attribute {
      QName getName();

      String getValue();

      String getValueUri();
   }
}
