package com.bea.xml.stream.filters;

import javax.xml.namespace.QName;
import javax.xml.stream.EventFilter;
import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class NameFilter implements EventFilter, StreamFilter {
   private QName name;

   public NameFilter(QName name) {
      this.name = name;
   }

   public boolean accept(XMLEvent e) {
      if (!e.isStartElement() && !e.isEndElement()) {
         return false;
      } else {
         QName eName = null;
         if (e.isStartElement()) {
            eName = ((StartElement)e).getName();
         } else {
            eName = ((EndElement)e).getName();
         }

         return this.name.equals(eName);
      }
   }

   public boolean accept(XMLStreamReader r) {
      if (!r.isStartElement() && !r.isEndElement()) {
         return false;
      } else {
         QName eName = new QName(r.getNamespaceURI(), r.getLocalName());
         return this.name.equals(eName);
      }
   }
}
