package com.sun.faces.facelets.tag.jsf.html;

import com.sun.faces.facelets.tag.TagAttributesImpl;
import javax.faces.view.facelets.Tag;
import javax.faces.view.facelets.TagAttribute;
import javax.faces.view.facelets.TagAttributes;
import javax.faces.view.facelets.TagDecorator;

public final class HtmlDecorator implements TagDecorator {
   public static final String XhtmlNamespace = "http://www.w3.org/1999/xhtml";
   public static final HtmlDecorator Instance = new HtmlDecorator();

   public Tag decorate(Tag tag) {
      if ("http://www.w3.org/1999/xhtml".equals(tag.getNamespace())) {
         String n = tag.getLocalName();
         if ("a".equals(n)) {
            return new Tag(tag.getLocation(), "http://java.sun.com/jsf/html", "commandLink", tag.getQName(), tag.getAttributes());
         }

         if ("form".equals(n)) {
            return new Tag(tag.getLocation(), "http://java.sun.com/jsf/html", "form", tag.getQName(), tag.getAttributes());
         }

         if ("input".equals(n)) {
            TagAttribute attr = tag.getAttributes().get("type");
            if (attr != null) {
               String t = attr.getValue();
               TagAttributes na = this.removeType(tag.getAttributes());
               if ("text".equals(t)) {
                  return new Tag(tag.getLocation(), "http://java.sun.com/jsf/html", "inputText", tag.getQName(), na);
               }

               if ("password".equals(t)) {
                  return new Tag(tag.getLocation(), "http://java.sun.com/jsf/html", "inputSecret", tag.getQName(), na);
               }

               if ("hidden".equals(t)) {
                  return new Tag(tag.getLocation(), "http://java.sun.com/jsf/html", "inputHidden", tag.getQName(), na);
               }

               if ("submit".equals(t)) {
                  return new Tag(tag.getLocation(), "http://java.sun.com/jsf/html", "commandButton", tag.getQName(), na);
               }

               if ("file".equals(t)) {
                  return new Tag(tag.getLocation(), "http://java.sun.com/jsf/html", "inputFile", tag.getQName(), na);
               }
            }
         }
      }

      return null;
   }

   private TagAttributes removeType(TagAttributes attrs) {
      TagAttribute[] o = attrs.getAll();
      TagAttribute[] a = new TagAttribute[o.length - 1];
      int p = 0;

      for(int i = 0; i < o.length; ++i) {
         if (!"type".equals(o[i].getLocalName())) {
            a[p++] = o[i];
         }
      }

      return new TagAttributesImpl(a);
   }
}
