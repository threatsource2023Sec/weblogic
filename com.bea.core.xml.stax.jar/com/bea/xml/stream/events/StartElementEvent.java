package com.bea.xml.stream.events;

import com.bea.xml.stream.util.EmptyIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.StartElement;

public class StartElementEvent extends NamedEvent implements StartElement {
   private List attributes;
   private List namespaces;
   private NamespaceContext context;

   public StartElementEvent() {
   }

   public StartElementEvent(QName name) {
      super(name);
      this.init();
   }

   public void reset() {
      if (this.attributes != null) {
         this.attributes.clear();
      }

      if (this.namespaces != null) {
         this.namespaces.clear();
      }

      if (this.context != null) {
         this.context = null;
      }

   }

   public StartElementEvent(StartElement element) {
      super(element.getName());
      this.init();
      this.setName(element.getName());
      Iterator ai = element.getAttributes();

      while(ai.hasNext()) {
         this.addAttribute((Attribute)ai.next());
      }

      Iterator ni = element.getNamespaces();
      ni = element.getNamespaces();

      while(ni.hasNext()) {
         this.addNamespace((Namespace)ni.next());
      }

   }

   protected void init() {
      this.setEventType(1);
   }

   public Iterator getAttributes() {
      return (Iterator)(this.attributes == null ? EmptyIterator.emptyIterator : this.attributes.iterator());
   }

   public Iterator getNamespaces() {
      return (Iterator)(this.namespaces == null ? EmptyIterator.emptyIterator : this.namespaces.iterator());
   }

   public Attribute getAttributeByName(QName name) {
      if (name == null) {
         return null;
      } else {
         Iterator i = this.getAttributes();

         while(i.hasNext()) {
            Attribute a = (Attribute)i.next();
            if (a.getName().equals(name)) {
               return a;
            }
         }

         return null;
      }
   }

   public void setAttributes(List attributes) {
      this.attributes = attributes;
   }

   public void addAttribute(Attribute attribute) {
      if (this.attributes == null) {
         this.attributes = new ArrayList();
      }

      this.attributes.add(attribute);
   }

   public void addNamespace(Namespace attribute) {
      if (this.namespaces == null) {
         this.namespaces = new ArrayList();
      }

      this.namespaces.add(attribute);
   }

   public String getNamespaceURI(String prefix) {
      return this.context == null ? null : this.context.getNamespaceURI(prefix);
   }

   public void setNamespaceContext(NamespaceContext c) {
      this.context = c;
   }

   public NamespaceContext getNamespaceContext() {
      return this.context;
   }

   public String toString() {
      String value = "<" + this.nameAsString();

      for(Iterator ai = this.getAttributes(); ai.hasNext(); value = value + " " + ai.next().toString()) {
      }

      for(Iterator ni = this.getNamespaces(); ni.hasNext(); value = value + " " + ni.next().toString()) {
      }

      value = value + ">";
      return value;
   }
}
