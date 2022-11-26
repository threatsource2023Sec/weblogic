package org.apache.xmlbeans.xml.stream;

public interface Location {
   int getColumnNumber();

   int getLineNumber();

   String getPublicId();

   String getSystemId();
}
