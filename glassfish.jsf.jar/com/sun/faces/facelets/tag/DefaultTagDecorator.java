package com.sun.faces.facelets.tag;

import java.util.HashMap;
import java.util.Map;
import javax.faces.view.Location;
import javax.faces.view.facelets.FaceletException;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributes;
import javax.faces.view.facelets.TagDecorator;

class DefaultTagDecorator implements TagDecorator {
   private ElementConverter defaultElementConverter = new ElementConverter("jsf:element");

   public Tag decorate(Tag tag) {
      String ns = tag.getNamespace();
      if (!this.hasJsfAttribute(tag)) {
         return null;
      } else if (!"".equals(ns) && !"http://www.w3.org/1999/xhtml".equals(ns)) {
         throw new FaceletException("Elements with namespace " + ns + " may not have attributes in namespace " + DefaultTagDecorator.Namespace.jsf.uri + ". Namespace " + DefaultTagDecorator.Namespace.jsf.uri + " is intended for otherwise non-JSF-aware markup, such as <input type=\"text\" jsf:id > It is not valid to have <h:commandButton jsf:id=\"button\" />.");
      } else {
         Mapper[] var3 = DefaultTagDecorator.Mapper.values();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Mapper mapper = var3[var5];
            if (tag.getLocalName().equals(mapper.name())) {
               return mapper.elementConverter.decorate(tag);
            }
         }

         return this.defaultElementConverter.decorate(tag);
      }
   }

   private boolean hasJsfAttribute(Tag tag) {
      String[] var2 = tag.getAttributes().getNamespaces();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String ns = var2[var4];
         if (DefaultTagDecorator.Namespace.jsf.uri.equals(ns)) {
            return true;
         }
      }

      return false;
   }

   private static class ElementConverter implements TagDecorator {
      private String localName;
      private Namespace namespace;
      private String arbiterAttributeName;
      private String arbiterAttributeNamespace;
      private Map additionalMappings;
      private String otherHtmlIdAttribute;

      private ElementConverter() {
         this.arbiterAttributeNamespace = "";
         this.additionalMappings = new HashMap();
      }

      private ElementConverter(String faceletsTag) {
         this(faceletsTag, (String)null);
      }

      private ElementConverter(String faceletsTag, String arbiterAttributeName) {
         this.arbiterAttributeNamespace = "";
         this.additionalMappings = new HashMap();
         String[] strings = faceletsTag.split(":");
         this.namespace = DefaultTagDecorator.Namespace.valueOf(strings[0]);
         this.localName = strings[1];
         this.arbiterAttributeName = arbiterAttributeName;
         if (arbiterAttributeName != null && arbiterAttributeName.indexOf(58) > 0) {
            strings = arbiterAttributeName.split(":");
            this.arbiterAttributeNamespace = DefaultTagDecorator.Namespace.valueOf(strings[0]).uri;
            this.arbiterAttributeName = strings[1];
         }

      }

      private ElementConverter map(String arbiterAttributeValue, String faceletsTagLocalName) {
         this.additionalMappings.put(arbiterAttributeValue, faceletsTagLocalName);
         return this;
      }

      private ElementConverter id(String otherHtmlIdAttribute) {
         this.otherHtmlIdAttribute = otherHtmlIdAttribute;
         return this;
      }

      public Tag decorate(Tag tag) {
         if (this.arbiterAttributeName == null) {
            return this.convertTag(tag, this.namespace, this.localName);
         } else {
            TagAttribute arbiterAttribute = tag.getAttributes().get(this.arbiterAttributeNamespace, this.arbiterAttributeName);
            if (arbiterAttribute == null) {
               return null;
            } else {
               String myLocalName = (String)this.additionalMappings.get(arbiterAttribute.getValue());
               if (myLocalName == null) {
                  myLocalName = this.localName;
               }

               return this.convertTag(tag, this.namespace, myLocalName);
            }
         }
      }

      protected Tag convertTag(Tag tag, Namespace namespace, String localName) {
         Location location = tag.getLocation();
         String ns = namespace.uri;
         String qName = namespace.name() + ":" + localName;
         TagAttributes attributes = this.convertAttributes(tag.getAttributes());
         Tag converted = new Tag(location, ns, localName, qName, attributes);
         TagAttribute[] var9 = attributes.getAll();
         int var10 = var9.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            TagAttribute tagAttribute = var9[var11];
            tagAttribute.setTag(converted);
         }

         return converted;
      }

      protected TagAttributes convertAttributes(TagAttributes original) {
         Map attributes = new HashMap();
         TagAttribute elementName = this.createElementName(original.getTag());
         attributes.put(elementName.getQName(), elementName);
         TagAttribute[] var4 = original.getAll();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            TagAttribute attribute = var4[var6];
            TagAttribute converted = this.convertTagAttribute(attribute);
            attributes.put(converted.getQName(), converted);
         }

         return new TagAttributesImpl((TagAttribute[])attributes.values().toArray(new TagAttribute[attributes.size()]));
      }

      private TagAttribute createElementName(Tag tag) {
         Location location = tag.getLocation();
         String ns = DefaultTagDecorator.Namespace.p.uri;
         String myLocalName = "elementName";
         String qName = "p:" + myLocalName;
         String value = tag.getLocalName();
         return new TagAttributeImpl(location, ns, myLocalName, qName, value);
      }

      protected TagAttribute convertTagAttribute(TagAttribute attribute) {
         Location location = attribute.getLocation();
         String ns = attribute.getNamespace();
         String myLocalName = attribute.getLocalName();
         String value = attribute.getValue();
         String qName;
         if (DefaultTagDecorator.Namespace.jsf.uri.equals(attribute.getNamespace())) {
            qName = myLocalName;
            ns = "";
         } else {
            if (ns.length() != 0 && !ns.equals(attribute.getTag().getNamespace())) {
               return attribute;
            }

            if (attribute.getLocalName().equals(this.otherHtmlIdAttribute)) {
               qName = "id";
               myLocalName = "id";
            } else {
               qName = "p:" + myLocalName;
               ns = DefaultTagDecorator.Namespace.p.uri;
            }
         }

         return new TagAttributeImpl(location, ns, myLocalName, qName, value);
      }

      // $FF: synthetic method
      ElementConverter(String x0, String x1, Object x2) {
         this(x0, x1);
      }

      // $FF: synthetic method
      ElementConverter(String x0, Object x1) {
         this(x0);
      }

      // $FF: synthetic method
      ElementConverter(Object x0) {
         this();
      }
   }

   private static enum Namespace {
      p("http://xmlns.jcp.org/jsf/passthrough"),
      jsf("http://xmlns.jcp.org/jsf"),
      h("http://java.sun.com/jsf/html");

      private String uri;

      private Namespace(String uri) {
         this.uri = uri;
      }
   }

   private static enum Mapper {
      a(new ElementConverter[]{new ElementConverter("h:commandLink", "jsf:action"), new ElementConverter("h:commandLink", "jsf:actionListener"), new ElementConverter("h:outputLink", "jsf:value"), new ElementConverter("h:link", "jsf:outcome")}),
      img("h:graphicImage"),
      body("h:body"),
      head("h:head"),
      label("h:outputLabel"),
      script("h:outputScript"),
      link("h:outputStylesheet"),
      form("h:form"),
      textarea("h:inputTextarea"),
      button(new ElementConverter[]{new ElementConverter("h:button", "jsf:outcome"), new ElementConverter("h:commandButton")}),
      select(new ElementConverter[]{(new ElementConverter("h:selectManyListbox", "multiple")).id("name"), (new ElementConverter("h:selectOneListbox")).id("name")}),
      input(new ElementConverter[]{(new ElementConverter("h:inputText", "type")).id("name").map("hidden", "inputHidden").map("password", "inputSecret").map("number", "inputText").map("search", "inputText").map("email", "inputText").map("datetime", "inputText").map("date", "inputText").map("month", "inputText").map("week", "inputText").map("time", "inputText").map("datetime-local", "inputText").map("range", "inputText").map("color", "inputText").map("url", "inputText").map("checkbox", "selectBooleanCheckbox").map("file", "inputFile").map("submit", "commandButton").map("reset", "commandButton").map("button", "button")});

      private ElementConverter elementConverter;

      private Mapper(final ElementConverter... elementConverters) {
         if (elementConverters.length == 1) {
            this.elementConverter = elementConverters[0];
         } else {
            this.elementConverter = new ElementConverter() {
               public Tag decorate(Tag tag) {
                  ElementConverter[] var2 = elementConverters;
                  int var3 = var2.length;

                  for(int var4 = 0; var4 < var3; ++var4) {
                     ElementConverter converter = var2[var4];
                     Tag decorated = converter.decorate(tag);
                     if (decorated != null) {
                        return decorated;
                     }
                  }

                  return null;
               }
            };
         }

      }

      private Mapper(String faceletTag) {
         this.elementConverter = new ElementConverter(faceletTag);
      }
   }
}
