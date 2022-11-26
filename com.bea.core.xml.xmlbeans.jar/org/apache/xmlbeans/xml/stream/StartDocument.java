package org.apache.xmlbeans.xml.stream;

public interface StartDocument extends XMLEvent {
   String getSystemId();

   String getCharacterEncodingScheme();

   boolean isStandalone();

   String getVersion();
}
