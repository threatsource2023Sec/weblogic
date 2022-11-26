package org.apache.xmlbeans.xml.stream;

public interface ProcessingInstruction extends XMLEvent {
   String getTarget();

   String getData();
}
