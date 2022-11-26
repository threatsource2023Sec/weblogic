package com.octetstring.vde.schema;

import com.octetstring.nls.Messages;
import com.octetstring.vde.syntax.DirectoryString;
import com.octetstring.vde.util.Logger;
import com.octetstring.vde.util.ServerConfig;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class SchemaXMLWriter {
   public Element findAttributeType(Document document, String attrname) {
      NodeList nl = document.getElementsByTagName("dsml:attribute-type");
      if (nl != null && nl.getLength() != 0) {
         int max = nl.getLength();

         for(int i = 0; i < max; ++i) {
            Element el = (Element)nl.item(i);
            if (attrname.equalsIgnoreCase(el.getAttribute("id"))) {
               return el;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public Element findObjectClass(Document document, String ocname) {
      NodeList nl = document.getElementsByTagName("dsml:class");
      if (nl != null && nl.getLength() != 0) {
         int max = nl.getLength();

         for(int i = 0; i < max; ++i) {
            Element el = (Element)nl.item(i);
            if (ocname.equalsIgnoreCase(el.getAttribute("id"))) {
               return el;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public void deleteAttributeType(Document document, Element ds, String name) {
      Element orig = this.findAttributeType(document, name);
      if (orig != null) {
         ds.removeChild(orig);
      }

   }

   public void deleteObjectClass(Document document, Element ds, String name) {
      Element orig = this.findObjectClass(document, name);
      if (orig != null) {
         ds.removeChild(orig);
      }

   }

   public void setAttributeType(Document document, Element ds, AttributeType at) {
      Element attr = document.createElement("dsml:attribute-type");
      Element orig = this.findAttributeType(document, at.getName().toString());
      if (orig == null) {
         ds.appendChild(attr);
      } else {
         ds.replaceChild(attr, orig);
      }

      attr.setAttribute("id", at.getName().toString());
      if (at.getSuperior() != null) {
         attr.setAttribute("superior", at.getSuperior().toString());
      }

      if (at.isSingleValue()) {
         attr.setAttribute("single-value", "true");
      }

      if (at.isNoUserModification()) {
         attr.setAttribute("user-modification", "false");
      }

      attr.appendChild(document.createTextNode("\n"));
      Element nameel = document.createElement("dsml:name");
      attr.appendChild(nameel);
      nameel.appendChild(document.createTextNode(at.getName().toString()));
      attr.appendChild(document.createTextNode("\n"));
      Element oid;
      if (at.getDescription() != null) {
         oid = document.createElement("dsml:description");
         oid.appendChild(document.createTextNode(at.getDescription()));
         attr.appendChild(oid);
         attr.appendChild(document.createTextNode("\n"));
      }

      oid = document.createElement("dsml:object-identifier");
      oid.appendChild(document.createTextNode(at.getOid()));
      attr.appendChild(oid);
      attr.appendChild(document.createTextNode("\n"));
      Element substring;
      if (at.getSyntax() != null) {
         substring = document.createElement("dsml:syntax");
         if (at.getBound() != 0) {
            substring.setAttribute("bound", (new Integer(at.getBound())).toString());
         }

         substring.appendChild(document.createTextNode(at.getSyntax()));
         attr.appendChild(substring);
         attr.appendChild(document.createTextNode("\n"));
      }

      if (at.getEqualityMatch() != null) {
         substring = document.createElement("dsml:equality");
         substring.appendChild(document.createTextNode(at.getEqualityMatch()));
         attr.appendChild(substring);
         attr.appendChild(document.createTextNode("\n"));
      }

      if (at.getOrderingMatch() != null) {
         substring = document.createElement("dsml:ordering");
         substring.appendChild(document.createTextNode(at.getOrderingMatch()));
         attr.appendChild(substring);
         attr.appendChild(document.createTextNode("\n"));
      }

      if (at.getSubstrMatch() != null) {
         substring = document.createElement("dsml:substring");
         substring.appendChild(document.createTextNode(at.getSubstrMatch()));
         attr.appendChild(substring);
         attr.appendChild(document.createTextNode("\n"));
      }

   }

   public void setObjectClass(Document document, Element ds, ObjectClass myoc) {
      Element oneoc = document.createElement("dsml:class");
      Element orig = this.findObjectClass(document, myoc.getName().toString());
      if (orig == null) {
         ds.appendChild(oneoc);
      } else {
         ds.replaceChild(oneoc, orig);
      }

      oneoc.setAttribute("id", myoc.getName().toString());
      if (myoc.getSuperior() != null) {
         oneoc.setAttribute("superior", "#" + myoc.getSuperior().toString());
      }

      if (myoc.getType() == 1) {
         oneoc.setAttribute("type", "structural");
      } else if (myoc.getType() == 0) {
         oneoc.setAttribute("type", "abstract");
      } else if (myoc.getType() == 2) {
         oneoc.setAttribute("type", "auxiliary");
      }

      oneoc.appendChild(document.createTextNode("\n"));
      Element name = document.createElement("dsml:name");
      oneoc.appendChild(name);
      name.appendChild(document.createTextNode(myoc.getName().toString()));
      Element oid;
      if (myoc.getDescription() != null) {
         oid = document.createElement("dsml:description");
         oneoc.appendChild(document.createTextNode("\n"));
         oneoc.appendChild(oid);
         oid.appendChild(document.createTextNode(myoc.getDescription()));
      }

      oid = document.createElement("dsml:object-identifier");
      oneoc.appendChild(document.createTextNode("\n"));
      oneoc.appendChild(oid);
      oid.appendChild(document.createTextNode(myoc.getOid()));
      Vector must = myoc.getMust();
      Enumeration musten = must.elements();

      while(musten.hasMoreElements()) {
         DirectoryString attr = (DirectoryString)musten.nextElement();
         Element elat = document.createElement("dsml:attribute");
         oneoc.appendChild(document.createTextNode("\n"));
         oneoc.appendChild(elat);
         elat.setAttribute("ref", "#" + attr.toString());
         elat.setAttribute("required", "true");
      }

      Vector may = myoc.getMay();
      Enumeration mayen = may.elements();

      while(mayen.hasMoreElements()) {
         DirectoryString attr = (DirectoryString)mayen.nextElement();
         Element elat = document.createElement("dsml:attribute");
         oneoc.appendChild(document.createTextNode("\n"));
         oneoc.appendChild(elat);
         elat.setAttribute("ref", "#" + attr.toString());
         elat.setAttribute("required", "false");
      }

      oneoc.appendChild(document.createTextNode("\n"));
   }

   public Document load(String filename) {
      DocumentBuilderFactory factory = FactoryInstanceHelper.getDocumentBuilderInstance();
      Document document = null;

      try {
         factory.setNamespaceAware(true);
         factory.setValidating(false);
         DocumentBuilder builder = factory.newDocumentBuilder();
         FileInputStream fis = new FileInputStream(filename);
         document = builder.parse(fis, filename);
         return document;
      } catch (Exception var6) {
         Logger.getInstance().log(0, this, Messages.getString("Error_parsing_XML_file_57"));
         return null;
      }
   }

   public Element getSchemaElement(Document document) {
      NodeList nl = document.getElementsByTagName("dsml:directory-schema");
      return nl != null && nl.getLength() != 0 ? (Element)nl.item(0) : null;
   }

   public synchronized void write(String filename, Document doc) {
   }

   public static void main(String[] args) {
      try {
         ServerConfig.getInstance().init();
      } catch (IOException var5) {
         System.err.println("Error initializing " + var5);
         System.exit(-1);
      }

      (new InitSchema()).init();
      ObjectClass oc = SchemaChecker.getInstance().getObjectClass(new DirectoryString("person"));
      oc.addMay(new DirectoryString("blah"));
      SchemaXMLWriter sxw = new SchemaXMLWriter();
      Document mydoc = sxw.load("d:/schematest.xml");
      if (mydoc == null) {
         System.out.println("Document is Null!");
      }

      Element ds = sxw.getSchemaElement(mydoc);
      sxw.setObjectClass(mydoc, ds, oc);
      sxw.write("d:/schematest.xml", mydoc);
   }
}
