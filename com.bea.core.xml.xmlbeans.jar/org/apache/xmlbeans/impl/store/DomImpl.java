package org.apache.xmlbeans.impl.store;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.Source;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlRuntimeException;
import org.apache.xmlbeans.impl.common.XMLChar;
import org.apache.xmlbeans.impl.soap.Detail;
import org.apache.xmlbeans.impl.soap.DetailEntry;
import org.apache.xmlbeans.impl.soap.Name;
import org.apache.xmlbeans.impl.soap.SOAPBody;
import org.apache.xmlbeans.impl.soap.SOAPBodyElement;
import org.apache.xmlbeans.impl.soap.SOAPElement;
import org.apache.xmlbeans.impl.soap.SOAPEnvelope;
import org.apache.xmlbeans.impl.soap.SOAPException;
import org.apache.xmlbeans.impl.soap.SOAPFault;
import org.apache.xmlbeans.impl.soap.SOAPHeader;
import org.apache.xmlbeans.impl.soap.SOAPHeaderElement;
import org.apache.xmlbeans.impl.soap.SOAPPart;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Comment;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;
import org.w3c.dom.UserDataHandler;

final class DomImpl {
   static final int ELEMENT = 1;
   static final int ATTR = 2;
   static final int TEXT = 3;
   static final int CDATA = 4;
   static final int ENTITYREF = 5;
   static final int ENTITY = 6;
   static final int PROCINST = 7;
   static final int COMMENT = 8;
   static final int DOCUMENT = 9;
   static final int DOCTYPE = 10;
   static final int DOCFRAG = 11;
   static final int NOTATION = 12;
   public static NodeList _emptyNodeList = new EmptyNodeList();

   static Dom parent(Dom d) {
      return node_getParentNode(d);
   }

   static Dom firstChild(Dom d) {
      return node_getFirstChild(d);
   }

   static Dom nextSibling(Dom d) {
      return node_getNextSibling(d);
   }

   static Dom prevSibling(Dom d) {
      return node_getPreviousSibling(d);
   }

   public static Dom append(Dom n, Dom p) {
      return node_insertBefore(p, n, (Dom)null);
   }

   public static Dom insert(Dom n, Dom b) {
      assert b != null;

      return node_insertBefore(parent(b), n, b);
   }

   public static Dom remove(Dom n) {
      Dom p = parent(n);
      if (p != null) {
         node_removeChild(p, n);
      }

      return n;
   }

   static String nodeKindName(int t) {
      switch (t) {
         case 1:
            return "element";
         case 2:
            return "attribute";
         case 3:
            return "text";
         case 4:
            return "cdata section";
         case 5:
            return "entity reference";
         case 6:
            return "entity";
         case 7:
            return "processing instruction";
         case 8:
            return "comment";
         case 9:
            return "document";
         case 10:
            return "document type";
         case 11:
            return "document fragment";
         case 12:
            return "notation";
         default:
            throw new RuntimeException("Unknown node type");
      }
   }

   private static String isValidChild(Dom parent, Dom child) {
      int pk = parent.nodeType();
      int ck = child.nodeType();
      switch (pk) {
         case 1:
         case 5:
         case 6:
         case 11:
            switch (ck) {
               case 1:
               case 3:
               case 4:
               case 5:
               case 7:
               case 8:
                  return null;
               case 2:
               case 6:
               default:
                  return nodeKindName(pk) + " nodes may not have " + nodeKindName(ck) + " nodes as children";
            }
         case 2:
            if (ck != 3 && ck != 5) {
               break;
            }

            return null;
         case 3:
         case 4:
         case 7:
         case 8:
         case 10:
         case 12:
            return nodeKindName(pk) + " nodes may not have any children";
         case 9:
            switch (ck) {
               case 1:
                  if (document_getDocumentElement(parent) != null) {
                     return "Documents may only have a maximum of one document element";
                  }

                  return null;
               case 2:
               case 3:
               case 4:
               case 5:
               case 6:
               case 9:
               default:
                  break;
               case 7:
               case 8:
                  return null;
               case 10:
                  if (document_getDoctype(parent) != null) {
                     return "Documents may only have a maximum of one document type node";
                  }

                  return null;
            }
      }

      return nodeKindName(pk) + " nodes may not have " + nodeKindName(ck) + " nodes as children";
   }

   private static void validateNewChild(Dom parent, Dom child) {
      String msg = isValidChild(parent, child);
      if (msg != null) {
         throw new HierarchyRequestErr(msg);
      } else if (parent == child) {
         throw new HierarchyRequestErr("New child and parent are the same node");
      } else {
         do {
            if ((parent = parent(parent)) == null) {
               return;
            }

            if (child.nodeType() == 5) {
               throw new NoModificationAllowedErr("Entity reference trees may not be modified");
            }
         } while(child != parent);

         throw new HierarchyRequestErr("New child is an ancestor node of the parent node");
      }
   }

   private static String validatePrefix(String prefix, String uri, String local, boolean isAttr) {
      validateNcName(prefix);
      if (prefix == null) {
         prefix = "";
      }

      if (uri == null) {
         uri = "";
      }

      if (prefix.length() > 0 && uri.length() == 0) {
         throw new NamespaceErr("Attempt to give a prefix for no namespace");
      } else if (prefix.equals("xml") && !uri.equals("http://www.w3.org/XML/1998/namespace")) {
         throw new NamespaceErr("Invalid prefix - begins with 'xml'");
      } else {
         if (isAttr) {
            if (prefix.length() > 0) {
               if (local.equals("xmlns")) {
                  throw new NamespaceErr("Invalid namespace - attr is default namespace already");
               }

               if (Locale.beginsWithXml(local)) {
                  throw new NamespaceErr("Invalid namespace - attr prefix begins with 'xml'");
               }

               if (prefix.equals("xmlns") && !uri.equals("http://www.w3.org/2000/xmlns/")) {
                  throw new NamespaceErr("Invalid namespace - uri is not 'http://www.w3.org/2000/xmlns/;");
               }
            } else if (local.equals("xmlns") && !uri.equals("http://www.w3.org/2000/xmlns/")) {
               throw new NamespaceErr("Invalid namespace - uri is not 'http://www.w3.org/2000/xmlns/;");
            }
         } else if (Locale.beginsWithXml(prefix)) {
            throw new NamespaceErr("Invalid prefix - begins with 'xml'");
         }

         return prefix;
      }
   }

   private static void validateName(String name) {
      if (name == null) {
         throw new IllegalArgumentException("Name is null");
      } else if (name.length() == 0) {
         throw new IllegalArgumentException("Name is empty");
      } else if (!XMLChar.isValidName(name)) {
         throw new InvalidCharacterError("Name has an invalid character");
      }
   }

   private static void validateNcName(String name) {
      if (name != null && name.length() > 0 && !XMLChar.isValidNCName(name)) {
         throw new InvalidCharacterError();
      }
   }

   private static void validateQualifiedName(String name, String uri, boolean isAttr) {
      assert name != null;

      if (uri == null) {
         uri = "";
      }

      int i = name.indexOf(58);
      String local;
      if (i < 0) {
         local = name;
         validateNcName(name);
         if (isAttr && name.equals("xmlns") && !uri.equals("http://www.w3.org/2000/xmlns/")) {
            throw new NamespaceErr("Default xmlns attribute does not have namespace: http://www.w3.org/2000/xmlns/");
         }
      } else {
         if (i == 0) {
            throw new NamespaceErr("Invalid qualified name, no prefix specified");
         }

         String prefix = name.substring(0, i);
         validateNcName(prefix);
         if (uri.length() == 0) {
            throw new NamespaceErr("Attempt to give a prefix for no namespace");
         }

         local = name.substring(i + 1);
         if (local.indexOf(58) >= 0) {
            throw new NamespaceErr("Invalid qualified name, more than one colon");
         }

         validateNcName(local);
         if (prefix.equals("xml") && !uri.equals("http://www.w3.org/XML/1998/namespace")) {
            throw new NamespaceErr("Invalid prefix - begins with 'xml'");
         }
      }

      if (local.length() == 0) {
         throw new NamespaceErr("Invalid qualified name, no local part specified");
      }
   }

   private static void removeNode(Dom n) {
      assert n.nodeType() != 3 && n.nodeType() != 4;

      Cur cFrom = n.tempCur();
      cFrom.toEnd();
      if (cFrom.next()) {
         CharNode fromNodes = cFrom.getCharNodes();
         if (fromNodes != null) {
            cFrom.setCharNodes((CharNode)null);
            Cur cTo = n.tempCur();
            cTo.setCharNodes(DomImpl.CharNode.appendNodes(cTo.getCharNodes(), fromNodes));
            cTo.release();
         }
      }

      cFrom.release();
      Cur.moveNode((Xobj)n, (Cur)null);
   }

   public static Document _domImplementation_createDocument(Locale l, String u, String n, DocumentType t) {
      if (l.noSync()) {
         l.enter();

         Document var5;
         try {
            var5 = domImplementation_createDocument(l, u, n, t);
         } finally {
            l.exit();
         }

         return var5;
      } else {
         synchronized(l) {
            l.enter();

            Document var6;
            try {
               var6 = domImplementation_createDocument(l, u, n, t);
            } finally {
               l.exit();
            }

            return var6;
         }
      }
   }

   public static Document domImplementation_createDocument(Locale l, String namespaceURI, String qualifiedName, DocumentType doctype) {
      validateQualifiedName(qualifiedName, namespaceURI, false);
      Cur c = l.tempCur();
      c.createDomDocumentRoot();
      Document doc = (Document)c.getDom();
      c.next();
      c.createElement(l.makeQualifiedQName(namespaceURI, qualifiedName));
      if (doctype != null) {
         throw new RuntimeException("Not impl");
      } else {
         c.toParent();

         try {
            Locale.autoTypeDocument(c, (SchemaType)null, (XmlOptions)null);
         } catch (XmlException var7) {
            throw new XmlRuntimeException(var7);
         }

         c.release();
         return doc;
      }
   }

   public static boolean _domImplementation_hasFeature(Locale l, String feature, String version) {
      if (feature == null) {
         return false;
      } else if (version != null && version.length() > 0 && !version.equals("1.0") && !version.equals("2.0")) {
         return false;
      } else if (feature.equalsIgnoreCase("core")) {
         return true;
      } else {
         return feature.equalsIgnoreCase("xml");
      }
   }

   public static Element _document_getDocumentElement(Dom d) {
      Locale l = d.locale();
      Dom e;
      if (l.noSync()) {
         l.enter();

         try {
            e = document_getDocumentElement(d);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               e = document_getDocumentElement(d);
            } finally {
               l.exit();
            }
         }
      }

      return (Element)e;
   }

   public static Dom document_getDocumentElement(Dom d) {
      for(d = firstChild(d); d != null; d = nextSibling(d)) {
         if (d.nodeType() == 1) {
            return d;
         }
      }

      return null;
   }

   public static DocumentFragment _document_createDocumentFragment(Dom d) {
      Locale l = d.locale();
      Dom f;
      if (l.noSync()) {
         l.enter();

         try {
            f = document_createDocumentFragment(d);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               f = document_createDocumentFragment(d);
            } finally {
               l.exit();
            }
         }
      }

      return (DocumentFragment)f;
   }

   public static Dom document_createDocumentFragment(Dom d) {
      Cur c = d.locale().tempCur();
      c.createDomDocFragRoot();
      Dom f = c.getDom();
      c.release();
      return f;
   }

   public static Element _document_createElement(Dom d, String name) {
      Locale l = d.locale();
      Dom e;
      if (l.noSync()) {
         l.enter();

         try {
            e = document_createElement(d, name);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               e = document_createElement(d, name);
            } finally {
               l.exit();
            }
         }
      }

      return (Element)e;
   }

   public static Dom document_createElement(Dom d, String name) {
      validateName(name);
      Locale l = d.locale();
      Cur c = l.tempCur();
      c.createElement(l.makeQualifiedQName("", name));
      Dom e = c.getDom();
      c.release();
      ((Xobj.ElementXobj)e)._canHavePrefixUri = false;
      return e;
   }

   public static Element _document_createElementNS(Dom d, String uri, String qname) {
      Locale l = d.locale();
      Dom e;
      if (l.noSync()) {
         l.enter();

         try {
            e = document_createElementNS(d, uri, qname);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               e = document_createElementNS(d, uri, qname);
            } finally {
               l.exit();
            }
         }
      }

      return (Element)e;
   }

   public static Dom document_createElementNS(Dom d, String uri, String qname) {
      validateQualifiedName(qname, uri, false);
      Locale l = d.locale();
      Cur c = l.tempCur();
      c.createElement(l.makeQualifiedQName(uri, qname));
      Dom e = c.getDom();
      c.release();
      return e;
   }

   public static Attr _document_createAttribute(Dom d, String name) {
      Locale l = d.locale();
      Dom a;
      if (l.noSync()) {
         l.enter();

         try {
            a = document_createAttribute(d, name);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               a = document_createAttribute(d, name);
            } finally {
               l.exit();
            }
         }
      }

      return (Attr)a;
   }

   public static Dom document_createAttribute(Dom d, String name) {
      validateName(name);
      Locale l = d.locale();
      Cur c = l.tempCur();
      c.createAttr(l.makeQualifiedQName("", name));
      Dom e = c.getDom();
      c.release();
      ((Xobj.AttrXobj)e)._canHavePrefixUri = false;
      return e;
   }

   public static Attr _document_createAttributeNS(Dom d, String uri, String qname) {
      Locale l = d.locale();
      Dom a;
      if (l.noSync()) {
         l.enter();

         try {
            a = document_createAttributeNS(d, uri, qname);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               a = document_createAttributeNS(d, uri, qname);
            } finally {
               l.exit();
            }
         }
      }

      return (Attr)a;
   }

   public static Dom document_createAttributeNS(Dom d, String uri, String qname) {
      validateQualifiedName(qname, uri, true);
      Locale l = d.locale();
      Cur c = l.tempCur();
      c.createAttr(l.makeQualifiedQName(uri, qname));
      Dom e = c.getDom();
      c.release();
      return e;
   }

   public static Comment _document_createComment(Dom d, String data) {
      Locale l = d.locale();
      Dom c;
      if (l.noSync()) {
         l.enter();

         try {
            c = document_createComment(d, data);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               c = document_createComment(d, data);
            } finally {
               l.exit();
            }
         }
      }

      return (Comment)c;
   }

   public static Dom document_createComment(Dom d, String data) {
      Locale l = d.locale();
      Cur c = l.tempCur();
      c.createComment();
      Dom comment = c.getDom();
      if (data != null) {
         c.next();
         c.insertString(data);
      }

      c.release();
      return comment;
   }

   public static ProcessingInstruction _document_createProcessingInstruction(Dom d, String target, String data) {
      Locale l = d.locale();
      Dom pi;
      if (l.noSync()) {
         l.enter();

         try {
            pi = document_createProcessingInstruction(d, target, data);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               pi = document_createProcessingInstruction(d, target, data);
            } finally {
               l.exit();
            }
         }
      }

      return (ProcessingInstruction)pi;
   }

   public static Dom document_createProcessingInstruction(Dom d, String target, String data) {
      if (target == null) {
         throw new IllegalArgumentException("Target is null");
      } else if (target.length() == 0) {
         throw new IllegalArgumentException("Target is empty");
      } else if (!XMLChar.isValidName(target)) {
         throw new InvalidCharacterError("Target has an invalid character");
      } else if (Locale.beginsWithXml(target) && target.length() == 3) {
         throw new InvalidCharacterError("Invalid target - is 'xml'");
      } else {
         Locale l = d.locale();
         Cur c = l.tempCur();
         c.createProcinst(target);
         Dom pi = c.getDom();
         if (data != null) {
            c.next();
            c.insertString(data);
         }

         c.release();
         return pi;
      }
   }

   public static CDATASection _document_createCDATASection(Dom d, String data) {
      return (CDATASection)document_createCDATASection(d, data);
   }

   public static Dom document_createCDATASection(Dom d, String data) {
      TextNode t = d.locale().createCdataNode();
      if (data == null) {
         data = "";
      }

      t.setChars(data, 0, data.length());
      return t;
   }

   public static Text _document_createTextNode(Dom d, String data) {
      return (Text)document_createTextNode(d, data);
   }

   public static CharNode document_createTextNode(Dom d, String data) {
      TextNode t = d.locale().createTextNode();
      if (data == null) {
         data = "";
      }

      t.setChars(data, 0, data.length());
      return t;
   }

   public static EntityReference _document_createEntityReference(Dom d, String name) {
      throw new RuntimeException("Not implemented");
   }

   public static Element _document_getElementById(Dom d, String elementId) {
      throw new RuntimeException("Not implemented");
   }

   public static NodeList _document_getElementsByTagName(Dom d, String name) {
      Locale l = d.locale();
      if (l.noSync()) {
         l.enter();

         NodeList var3;
         try {
            var3 = document_getElementsByTagName(d, name);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            NodeList var4;
            try {
               var4 = document_getElementsByTagName(d, name);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static NodeList document_getElementsByTagName(Dom d, String name) {
      return new ElementsByTagNameNodeList(d, name);
   }

   public static NodeList _document_getElementsByTagNameNS(Dom d, String uri, String local) {
      Locale l = d.locale();
      if (l.noSync()) {
         l.enter();

         NodeList var4;
         try {
            var4 = document_getElementsByTagNameNS(d, uri, local);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            NodeList var5;
            try {
               var5 = document_getElementsByTagNameNS(d, uri, local);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static NodeList document_getElementsByTagNameNS(Dom d, String uri, String local) {
      return new ElementsByTagNameNSNodeList(d, uri, local);
   }

   public static DOMImplementation _document_getImplementation(Dom d) {
      return d.locale();
   }

   public static Node _document_importNode(Dom d, Node n, boolean deep) {
      Locale l = d.locale();
      Dom i;
      if (l.noSync()) {
         l.enter();

         try {
            i = document_importNode(d, n, deep);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               i = document_importNode(d, n, deep);
            } finally {
               l.exit();
            }
         }
      }

      return (Node)i;
   }

   public static Dom document_importNode(Dom d, Node n, boolean deep) {
      if (n == null) {
         return null;
      } else {
         boolean copyChildren = false;
         Object i;
         String local;
         String prefix;
         String name;
         String uri;
         switch (n.getNodeType()) {
            case 1:
               local = n.getLocalName();
               if (local != null && local.length() != 0) {
                  prefix = n.getPrefix();
                  name = prefix != null && prefix.length() != 0 ? prefix + ":" + local : local;
                  uri = n.getNamespaceURI();
                  if (uri != null && uri.length() != 0) {
                     i = document_createElementNS(d, uri, name);
                  } else {
                     i = document_createElement(d, name);
                  }
               } else {
                  i = document_createElement(d, n.getNodeName());
               }

               NamedNodeMap attrs = n.getAttributes();

               for(int a = 0; a < attrs.getLength(); ++a) {
                  attributes_setNamedItem((Dom)i, document_importNode(d, attrs.item(a), true));
               }

               copyChildren = deep;
               break;
            case 2:
               local = n.getLocalName();
               if (local != null && local.length() != 0) {
                  prefix = n.getPrefix();
                  name = prefix != null && prefix.length() != 0 ? prefix + ":" + local : local;
                  uri = n.getNamespaceURI();
                  if (uri != null && uri.length() != 0) {
                     i = document_createAttributeNS(d, uri, name);
                  } else {
                     i = document_createAttribute(d, name);
                  }
               } else {
                  i = document_createAttribute(d, n.getNodeName());
               }

               copyChildren = true;
               break;
            case 3:
               i = document_createTextNode(d, n.getNodeValue());
               break;
            case 4:
               i = document_createCDATASection(d, n.getNodeValue());
               break;
            case 5:
            case 6:
            case 12:
               throw new RuntimeException("Not impl");
            case 7:
               i = document_createProcessingInstruction(d, n.getNodeName(), n.getNodeValue());
               break;
            case 8:
               i = document_createComment(d, n.getNodeValue());
               break;
            case 9:
               throw new NotSupportedError("Document nodes may not be imported");
            case 10:
               throw new NotSupportedError("Document type nodes may not be imported");
            case 11:
               i = document_createDocumentFragment(d);
               copyChildren = deep;
               break;
            default:
               throw new RuntimeException("Unknown kind");
         }

         if (copyChildren) {
            NodeList children = n.getChildNodes();

            for(int c = 0; c < children.getLength(); ++c) {
               node_insertBefore((Dom)i, document_importNode(d, children.item(c), true), (Dom)null);
            }
         }

         return (Dom)i;
      }
   }

   public static DocumentType _document_getDoctype(Dom d) {
      Locale l = d.locale();
      Dom dt;
      if (l.noSync()) {
         l.enter();

         try {
            dt = document_getDoctype(d);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               dt = document_getDoctype(d);
            } finally {
               l.exit();
            }
         }
      }

      return (DocumentType)dt;
   }

   public static Dom document_getDoctype(Dom d) {
      return null;
   }

   public static Document _node_getOwnerDocument(Dom n) {
      Locale l = n.locale();
      Dom d;
      if (l.noSync()) {
         l.enter();

         try {
            d = node_getOwnerDocument(n);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               d = node_getOwnerDocument(n);
            } finally {
               l.exit();
            }
         }
      }

      return (Document)d;
   }

   public static Dom node_getOwnerDocument(Dom n) {
      if (n.nodeType() == 9) {
         return null;
      } else {
         Locale l = n.locale();
         if (l._ownerDoc == null) {
            Cur c = l.tempCur();
            c.createDomDocumentRoot();
            l._ownerDoc = c.getDom();
            c.release();
         }

         return l._ownerDoc;
      }
   }

   public static Node _node_getParentNode(Dom n) {
      Locale l = n.locale();
      Dom p;
      if (l.noSync()) {
         l.enter();

         try {
            p = node_getParentNode(n);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               p = node_getParentNode(n);
            } finally {
               l.exit();
            }
         }
      }

      return (Node)p;
   }

   public static Dom node_getParentNode(Dom n) {
      Cur c = null;
      switch (n.nodeType()) {
         case 1:
         case 7:
         case 8:
            if (!(c = n.tempCur()).toParentRaw()) {
               c.release();
               c = null;
            }
         case 2:
         case 9:
         case 11:
            break;
         case 3:
         case 4:
            if ((c = n.tempCur()) != null) {
               c.toParent();
            }
            break;
         case 5:
            throw new RuntimeException("Not impl");
         case 6:
         case 10:
         case 12:
            throw new RuntimeException("Not impl");
         default:
            throw new RuntimeException("Unknown kind");
      }

      if (c == null) {
         return null;
      } else {
         Dom d = c.getDom();
         c.release();
         return d;
      }
   }

   public static Node _node_getFirstChild(Dom n) {
      Locale l = n.locale();

      assert n instanceof Xobj;

      Xobj node = (Xobj)n;
      if (!node.isVacant()) {
         if (node.isFirstChildPtrDomUsable()) {
            return (Node)node._firstChild;
         }

         Xobj lastAttr = node.lastAttr();
         if (lastAttr != null && lastAttr.isNextSiblingPtrDomUsable()) {
            return (Xobj.NodeXobj)lastAttr._nextSibling;
         }

         if (node.isExistingCharNodesValueUsable()) {
            return node._charNodesValue;
         }
      }

      Dom fc;
      if (l.noSync()) {
         fc = node_getFirstChild(n);
      } else {
         synchronized(l) {
            fc = node_getFirstChild(n);
         }
      }

      return (Node)fc;
   }

   public static Dom node_getFirstChild(Dom n) {
      Dom fc = null;
      switch (n.nodeType()) {
         case 1:
         case 2:
         case 9:
         case 11:
            Xobj node = (Xobj)n;
            node.ensureOccupancy();
            if (node.isFirstChildPtrDomUsable()) {
               return (Xobj.NodeXobj)node._firstChild;
            } else {
               Xobj lastAttr = node.lastAttr();
               if (lastAttr != null) {
                  if (lastAttr.isNextSiblingPtrDomUsable()) {
                     return (Xobj.NodeXobj)lastAttr._nextSibling;
                  }

                  if (lastAttr.updateCharNodesAfter()) {
                     return lastAttr._charNodesAfter;
                  }
               }

               if (node.updateCharNodesValue()) {
                  return node._charNodesValue;
               }
            }
         case 3:
         case 4:
         case 7:
         case 8:
         default:
            return (Dom)fc;
         case 5:
            throw new RuntimeException("Not impl");
         case 6:
         case 10:
         case 12:
            throw new RuntimeException("Not impl");
      }
   }

   public static Node _node_getLastChild(Dom n) {
      Locale l = n.locale();
      Dom lc;
      if (l.noSync()) {
         l.enter();

         try {
            lc = node_getLastChild(n);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               lc = node_getLastChild(n);
            } finally {
               l.exit();
            }
         }
      }

      return (Node)lc;
   }

   public static Dom node_getLastChild(Dom n) {
      switch (n.nodeType()) {
         case 1:
         case 2:
         case 9:
         case 11:
         default:
            Dom lc = null;
            Cur c = n.tempCur();
            CharNode nodes;
            if (c.toLastChild()) {
               lc = c.getDom();
               c.skip();
               if ((nodes = c.getCharNodes()) != null) {
                  lc = null;
               }
            } else {
               c.next();
               nodes = c.getCharNodes();
            }

            if (lc == null && nodes != null) {
               while(nodes._next != null) {
                  nodes = nodes._next;
               }

               lc = nodes;
            }

            c.release();
            return (Dom)lc;
         case 3:
         case 4:
         case 7:
         case 8:
            return null;
         case 5:
            throw new RuntimeException("Not impl");
         case 6:
         case 10:
         case 12:
            throw new RuntimeException("Not impl");
      }
   }

   public static Node _node_getNextSibling(Dom n) {
      Locale l = n.locale();
      Dom ns;
      if (l.noSync()) {
         ns = node_getNextSibling(n);
      } else {
         synchronized(l) {
            ns = node_getNextSibling(n);
         }
      }

      return (Node)ns;
   }

   public static Dom node_getNextSibling(Dom n) {
      Dom ns = null;
      switch (n.nodeType()) {
         case 1:
         case 7:
         case 8:
            assert n instanceof Xobj : "PI, Comments and Elements always backed up by Xobj";

            Xobj node = (Xobj)n;
            node.ensureOccupancy();
            if (node.isNextSiblingPtrDomUsable()) {
               return (Xobj.NodeXobj)node._nextSibling;
            }

            if (node.updateCharNodesAfter()) {
               return node._charNodesAfter;
            }
         case 2:
         case 9:
         case 11:
         default:
            break;
         case 3:
         case 4:
            CharNode cn = (CharNode)n;
            if (!(cn._src instanceof Xobj)) {
               return null;
            }

            Xobj src = (Xobj)cn._src;
            src._charNodesAfter = Cur.updateCharNodes(src._locale, src, src._charNodesAfter, src._cchAfter);
            src._charNodesValue = Cur.updateCharNodes(src._locale, src, src._charNodesValue, src._cchValue);
            if (cn._next != null) {
               ns = cn._next;
            } else {
               boolean isThisNodeAfterText = cn.isNodeAftertext();
               if (isThisNodeAfterText) {
                  ns = (Xobj.NodeXobj)src._nextSibling;
               } else {
                  ns = (Xobj.NodeXobj)src._firstChild;
               }
            }
            break;
         case 5:
         case 6:
         case 10:
         case 12:
            throw new RuntimeException("Not implemented");
      }

      return (Dom)ns;
   }

   public static Node _node_getPreviousSibling(Dom n) {
      Locale l = n.locale();
      Dom ps;
      if (l.noSync()) {
         ps = node_getPreviousSibling(n);
      } else {
         synchronized(l) {
            ps = node_getPreviousSibling(n);
         }
      }

      return (Node)ps;
   }

   public static Dom node_getPreviousSibling(Dom n) {
      Dom prev = null;
      switch (n.nodeType()) {
         case 3:
         case 4:
            assert n instanceof CharNode : "Text/CData should be a CharNode";

            CharNode node = (CharNode)n;
            if (!(node._src instanceof Xobj)) {
               return null;
            }

            Xobj src = (Xobj)node._src;
            src.ensureOccupancy();
            boolean isThisNodeAfterText = node.isNodeAftertext();
            prev = node._prev;
            if (prev == null) {
               prev = isThisNodeAfterText ? (Dom)src : src._charNodesValue;
            }
            break;
         default:
            assert n instanceof Xobj;

            Xobj node = (Xobj)n;
            prev = (Dom)node._prevSibling;
            if (prev == null && node._parent != null) {
               prev = node_getFirstChild((Dom)node._parent);
            }
      }

      for(Dom temp = prev; temp != null && (temp = node_getNextSibling((Dom)temp)) != n; prev = temp) {
      }

      return (Dom)prev;
   }

   public static boolean _node_hasAttributes(Dom n) {
      Locale l = n.locale();
      if (l.noSync()) {
         l.enter();

         boolean var2;
         try {
            var2 = node_hasAttributes(n);
         } finally {
            l.exit();
         }

         return var2;
      } else {
         synchronized(l) {
            l.enter();

            boolean var3;
            try {
               var3 = node_hasAttributes(n);
            } finally {
               l.exit();
            }

            return var3;
         }
      }
   }

   public static boolean node_hasAttributes(Dom n) {
      boolean hasAttrs = false;
      if (n.nodeType() == 1) {
         Cur c = n.tempCur();
         hasAttrs = c.hasAttrs();
         c.release();
      }

      return hasAttrs;
   }

   public static boolean _node_isSupported(Dom n, String feature, String version) {
      return _domImplementation_hasFeature(n.locale(), feature, version);
   }

   public static void _node_normalize(Dom n) {
      Locale l = n.locale();
      if (l.noSync()) {
         l.enter();

         try {
            node_normalize(n);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               node_normalize(n);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static void node_normalize(Dom n) {
      switch (n.nodeType()) {
         case 1:
         case 2:
         case 9:
         case 11:
         default:
            Cur c = n.tempCur();
            c.push();

            do {
               c.nextWithAttrs();
               CharNode cn = c.getCharNodes();
               if (cn != null) {
                  if (!c.isText()) {
                     while(cn != null) {
                        cn.setChars((Object)null, 0, 0);
                        cn = DomImpl.CharNode.remove(cn, cn);
                     }
                  } else if (cn._next != null) {
                     while(cn._next != null) {
                        cn.setChars((Object)null, 0, 0);
                        cn = DomImpl.CharNode.remove(cn, cn._next);
                     }

                     cn._cch = Integer.MAX_VALUE;
                  }

                  c.setCharNodes(cn);
               }
            } while(!c.isAtEndOfLastPush());

            c.release();
            n.locale().invalidateDomCaches(n);
            return;
         case 3:
         case 4:
         case 7:
         case 8:
            return;
         case 5:
            throw new RuntimeException("Not impl");
         case 6:
         case 10:
         case 12:
            throw new RuntimeException("Not impl");
      }
   }

   public static boolean _node_hasChildNodes(Dom n) {
      return _node_getFirstChild(n) != null;
   }

   public static Node _node_appendChild(Dom p, Node newChild) {
      return _node_insertBefore(p, newChild, (Node)null);
   }

   public static Node _node_replaceChild(Dom p, Node newChild, Node oldChild) {
      Locale l = p.locale();
      if (newChild == null) {
         throw new IllegalArgumentException("Child to add is null");
      } else if (oldChild == null) {
         throw new NotFoundErr("Child to replace is null");
      } else {
         Dom nc;
         if (newChild instanceof Dom && (nc = (Dom)newChild).locale() == l) {
            Dom oc = null;
            if (oldChild instanceof Dom && (oc = (Dom)oldChild).locale() == l) {
               Dom d;
               if (l.noSync()) {
                  l.enter();

                  try {
                     d = node_replaceChild(p, nc, oc);
                  } finally {
                     l.exit();
                  }
               } else {
                  synchronized(l) {
                     l.enter();

                     try {
                        d = node_replaceChild(p, nc, oc);
                     } finally {
                        l.exit();
                     }
                  }
               }

               return (Node)d;
            } else {
               throw new WrongDocumentErr("Child to replace is from another document");
            }
         } else {
            throw new WrongDocumentErr("Child to add is from another document");
         }
      }
   }

   public static Dom node_replaceChild(Dom p, Dom newChild, Dom oldChild) {
      Dom nextNode = node_getNextSibling(oldChild);
      node_removeChild(p, oldChild);

      try {
         node_insertBefore(p, newChild, nextNode);
         return oldChild;
      } catch (DOMException var5) {
         node_insertBefore(p, oldChild, nextNode);
         throw var5;
      }
   }

   public static Node _node_insertBefore(Dom p, Node newChild, Node refChild) {
      Locale l = p.locale();
      if (newChild == null) {
         throw new IllegalArgumentException("Child to add is null");
      } else {
         Dom nc;
         if (newChild instanceof Dom && (nc = (Dom)newChild).locale() == l) {
            Dom rc = null;
            if (refChild != null && (!(refChild instanceof Dom) || (rc = (Dom)refChild).locale() != l)) {
               throw new WrongDocumentErr("Reference child is from another document");
            } else {
               Dom d;
               if (l.noSync()) {
                  l.enter();

                  try {
                     d = node_insertBefore(p, nc, rc);
                  } finally {
                     l.exit();
                  }
               } else {
                  synchronized(l) {
                     l.enter();

                     try {
                        d = node_insertBefore(p, nc, rc);
                     } finally {
                        l.exit();
                     }
                  }
               }

               return (Node)d;
            }
         } else {
            throw new WrongDocumentErr("Child to add is from another document");
         }
      }
   }

   public static Dom node_insertBefore(Dom p, Dom nc, Dom rc) {
      assert nc != null;

      if (nc == rc) {
         return nc;
      } else if (rc != null && parent(rc) != p) {
         throw new NotFoundErr("RefChild is not a child of this node");
      } else {
         int nck = nc.nodeType();
         if (nck == 11) {
            Dom c;
            for(c = firstChild(nc); c != null; c = nextSibling(c)) {
               validateNewChild(p, c);
            }

            Dom n;
            for(c = firstChild(nc); c != null; c = n) {
               n = nextSibling(c);
               if (rc == null) {
                  append(c, p);
               } else {
                  insert(c, rc);
               }
            }

            return nc;
         } else {
            validateNewChild(p, nc);
            remove(nc);
            int pk = p.nodeType();

            assert pk == 2 || pk == 11 || pk == 9 || pk == 1;

            switch (nck) {
               case 1:
               case 7:
               case 8:
                  if (rc == null) {
                     Cur cTo = p.tempCur();
                     cTo.toEnd();
                     Cur.moveNode((Xobj)nc, cTo);
                     cTo.release();
                  } else {
                     int rck = rc.nodeType();
                     if (rck == 3 || rck == 4) {
                        ArrayList charNodes;
                        Dom next;
                        for(charNodes = new ArrayList(); rc != null && (rc.nodeType() == 3 || rc.nodeType() == 4); rc = next) {
                           next = nextSibling(rc);
                           charNodes.add(remove(rc));
                        }

                        if (rc == null) {
                           append(nc, p);
                        } else {
                           insert(nc, rc);
                        }

                        rc = nextSibling(nc);

                        for(int i = 0; i < charNodes.size(); ++i) {
                           Dom n = (Dom)charNodes.get(i);
                           if (rc == null) {
                              append(n, p);
                           } else {
                              insert(n, rc);
                           }
                        }

                        return nc;
                     }

                     if (rck == 5) {
                        throw new RuntimeException("Not implemented");
                     }

                     assert rck == 1 || rck == 7 || rck == 8;

                     Cur cTo = rc.tempCur();
                     Cur.moveNode((Xobj)nc, cTo);
                     cTo.release();
                  }
                  break;
               case 2:
               case 6:
               case 9:
               default:
                  throw new RuntimeException("Unexpected child node type");
               case 3:
               case 4:
                  CharNode n = (CharNode)nc;

                  assert n._prev == null && n._next == null;

                  CharNode refCharNode = null;
                  Cur c = p.tempCur();
                  if (rc == null) {
                     c.toEnd();
                  } else {
                     int rck = rc.nodeType();
                     if (rck != 3 && rck != 4) {
                        if (rck == 5) {
                           throw new RuntimeException("Not implemented");
                        }

                        c.moveToDom(rc);
                     } else {
                        c.moveToCharNode(refCharNode = (CharNode)rc);
                     }
                  }

                  CharNode nodes = c.getCharNodes();
                  nodes = DomImpl.CharNode.insertNode(nodes, n, refCharNode);
                  c.insertChars(n._src, n._off, n._cch);
                  c.setCharNodes(nodes);
                  c.release();
                  break;
               case 5:
                  throw new RuntimeException("Not implemented");
               case 10:
                  throw new RuntimeException("Not implemented");
            }

            return nc;
         }
      }
   }

   public static Node _node_removeChild(Dom p, Node child) {
      Locale l = p.locale();
      if (child == null) {
         throw new NotFoundErr("Child to remove is null");
      } else {
         Dom c;
         if (child instanceof Dom && (c = (Dom)child).locale() == l) {
            Dom d;
            if (l.noSync()) {
               l.enter();

               try {
                  d = node_removeChild(p, c);
               } finally {
                  l.exit();
               }
            } else {
               synchronized(l) {
                  l.enter();

                  try {
                     d = node_removeChild(p, c);
                  } finally {
                     l.exit();
                  }
               }
            }

            return (Node)d;
         } else {
            throw new WrongDocumentErr("Child to remove is from another document");
         }
      }
   }

   public static Dom node_removeChild(Dom parent, Dom child) {
      if (parent(child) != parent) {
         throw new NotFoundErr("Child to remove is not a child of given parent");
      } else {
         switch (child.nodeType()) {
            case 1:
            case 7:
            case 8:
               removeNode(child);
               break;
            case 2:
            case 9:
            case 11:
               throw new IllegalStateException();
            case 3:
            case 4:
               Cur c = child.tempCur();
               CharNode nodes = c.getCharNodes();
               CharNode cn = (CharNode)child;

               assert cn._src instanceof Dom;

               cn.setChars(c.moveChars((Cur)null, cn._cch), c._offSrc, c._cchSrc);
               c.setCharNodes(DomImpl.CharNode.remove(nodes, cn));
               c.release();
               break;
            case 5:
               throw new RuntimeException("Not impl");
            case 6:
            case 10:
            case 12:
               throw new RuntimeException("Not impl");
            default:
               throw new RuntimeException("Unknown kind");
         }

         return child;
      }
   }

   public static Node _node_cloneNode(Dom n, boolean deep) {
      Locale l = n.locale();
      Dom c;
      if (l.noSync()) {
         l.enter();

         try {
            c = node_cloneNode(n, deep);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               c = node_cloneNode(n, deep);
            } finally {
               l.exit();
            }
         }
      }

      return (Node)c;
   }

   public static Dom node_cloneNode(Dom n, boolean deep) {
      Locale l = n.locale();
      Dom clone = null;
      Cur shallow;
      if (!deep) {
         shallow = null;
         label36:
         switch (n.nodeType()) {
            case 1:
               shallow = l.tempCur();
               shallow.createElement(n.getQName());
               Element elem = (Element)shallow.getDom();
               NamedNodeMap attrs = ((Element)n).getAttributes();
               int i = 0;

               while(true) {
                  if (i >= attrs.getLength()) {
                     break label36;
                  }

                  elem.setAttributeNodeNS((Attr)attrs.item(i).cloneNode(true));
                  ++i;
               }
            case 2:
               shallow = l.tempCur();
               shallow.createAttr(n.getQName());
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 12:
            default:
               break;
            case 9:
               shallow = l.tempCur();
               shallow.createDomDocumentRoot();
               break;
            case 11:
               shallow = l.tempCur();
               shallow.createDomDocFragRoot();
         }

         if (shallow != null) {
            clone = shallow.getDom();
            shallow.release();
         }
      }

      if (clone == null) {
         switch (n.nodeType()) {
            case 1:
            case 2:
            case 7:
            case 8:
            case 9:
            case 11:
               shallow = l.tempCur();
               Cur cSrc = n.tempCur();
               cSrc.copyNode(shallow);
               clone = shallow.getDom();
               shallow.release();
               cSrc.release();
               break;
            case 3:
            case 4:
               shallow = n.tempCur();
               CharNode cn = n.nodeType() == 3 ? l.createTextNode() : l.createCdataNode();
               ((CharNode)cn).setChars(shallow.getChars(((CharNode)n)._cch), shallow._offSrc, shallow._cchSrc);
               clone = cn;
               shallow.release();
               break;
            case 5:
            case 6:
            case 10:
            case 12:
               throw new RuntimeException("Not impl");
            default:
               throw new RuntimeException("Unknown kind");
         }
      }

      return (Dom)clone;
   }

   public static String _node_getLocalName(Dom n) {
      if (!n.nodeCanHavePrefixUri()) {
         return null;
      } else {
         QName name = n.getQName();
         return name == null ? "" : name.getLocalPart();
      }
   }

   public static String _node_getNamespaceURI(Dom n) {
      if (!n.nodeCanHavePrefixUri()) {
         return null;
      } else {
         QName name = n.getQName();
         return name == null ? "" : name.getNamespaceURI();
      }
   }

   public static void _node_setPrefix(Dom n, String prefix) {
      Locale l = n.locale();
      if (l.noSync()) {
         l.enter();

         try {
            node_setPrefix(n, prefix);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               node_setPrefix(n, prefix);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static void node_setPrefix(Dom n, String prefix) {
      if (n.nodeType() != 1 && n.nodeType() != 2) {
         validatePrefix(prefix, "", "", false);
      } else {
         Cur c = n.tempCur();
         QName name = c.getName();
         String uri = name.getNamespaceURI();
         String local = name.getLocalPart();
         prefix = validatePrefix(prefix, uri, local, n.nodeType() == 2);
         c.setName(n.locale().makeQName(uri, local, prefix));
         c.release();
      }

   }

   public static String _node_getPrefix(Dom n) {
      if (!n.nodeCanHavePrefixUri()) {
         return null;
      } else {
         QName name = n.getQName();
         return name == null ? "" : name.getPrefix();
      }
   }

   public static String _node_getNodeName(Dom n) {
      switch (n.nodeType()) {
         case 1:
         case 2:
            QName name = n.getQName();
            String prefix = name.getPrefix();
            return prefix.length() == 0 ? name.getLocalPart() : prefix + ":" + name.getLocalPart();
         case 3:
            return "#text";
         case 4:
            return "#cdata-section";
         case 5:
         case 6:
         case 10:
         case 12:
            throw new RuntimeException("Not impl");
         case 7:
            return n.getQName().getLocalPart();
         case 8:
            return "#comment";
         case 9:
            return "#document";
         case 11:
            return "#document-fragment";
         default:
            throw new RuntimeException("Unknown node type");
      }
   }

   public static short _node_getNodeType(Dom n) {
      return (short)n.nodeType();
   }

   public static void _node_setNodeValue(Dom n, String nodeValue) {
      Locale l = n.locale();
      if (l.noSync()) {
         l.enter();

         try {
            node_setNodeValue(n, nodeValue);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               node_setNodeValue(n, nodeValue);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static void node_setNodeValue(Dom n, String nodeValue) {
      if (nodeValue == null) {
         nodeValue = "";
      }

      switch (n.nodeType()) {
         case 2:
            NodeList children = ((Node)n).getChildNodes();

            while(children.getLength() > 1) {
               node_removeChild(n, (Dom)children.item(1));
            }

            if (children.getLength() == 0) {
               TextNode tn = n.locale().createTextNode();
               tn.setChars(nodeValue, 0, nodeValue.length());
               node_insertBefore(n, tn, (Dom)null);
            } else {
               assert children.getLength() == 1;

               children.item(0).setNodeValue(nodeValue);
            }

            if (((Xobj.AttrXobj)n).isId()) {
               Dom d = node_getOwnerDocument(n);
               String val = node_getNodeValue(n);
               if (d instanceof Xobj.DocumentXobj) {
                  ((Xobj.DocumentXobj)d).removeIdElement(val);
                  ((Xobj.DocumentXobj)d).addIdElement(nodeValue, attr_getOwnerElement(n));
               }
            }
            break;
         case 3:
         case 4:
            CharNode cn = (CharNode)n;
            Cur c;
            if ((c = cn.tempCur()) != null) {
               c.moveChars((Cur)null, cn._cch);
               cn._cch = nodeValue.length();
               c.insertString(nodeValue);
               c.release();
            } else {
               cn.setChars(nodeValue, 0, nodeValue.length());
            }
         case 5:
         case 6:
         default:
            break;
         case 7:
         case 8:
            Cur c = n.tempCur();
            c.next();
            c.getChars(-1);
            c.moveChars((Cur)null, c._cchSrc);
            c.insertString(nodeValue);
            c.release();
      }

   }

   public static String _node_getNodeValue(Dom n) {
      Locale l = n.locale();
      if (l.noSync()) {
         return node_getNodeValue(n);
      } else {
         synchronized(l) {
            return node_getNodeValue(n);
         }
      }
   }

   public static String node_getNodeValue(Dom n) {
      String s = null;
      switch (n.nodeType()) {
         case 2:
         case 7:
         case 8:
            s = ((Xobj)n).getValueAsString();
            break;
         case 3:
         case 4:
            assert n instanceof CharNode : "Text/CData should be a CharNode";

            CharNode node = (CharNode)n;
            if (!(node._src instanceof Xobj)) {
               s = CharUtil.getString(node._src, node._off, node._cch);
            } else {
               Xobj src = (Xobj)node._src;
               src.ensureOccupancy();
               boolean isThisNodeAfterText = node.isNodeAftertext();
               if (isThisNodeAfterText) {
                  src._charNodesAfter = Cur.updateCharNodes(src._locale, src, src._charNodesAfter, src._cchAfter);
                  s = src.getCharsAfterAsString(node._off, node._cch);
               } else {
                  src._charNodesValue = Cur.updateCharNodes(src._locale, src, src._charNodesValue, src._cchValue);
                  s = src.getCharsValueAsString(node._off, node._cch);
               }
            }
         case 5:
         case 6:
      }

      return s;
   }

   public static Object _node_getUserData(Dom n, String key) {
      throw new RuntimeException("DOM Level 3 Not implemented");
   }

   public static Object _node_setUserData(Dom n, String key, Object data, UserDataHandler handler) {
      throw new RuntimeException("DOM Level 3 Not implemented");
   }

   public static Object _node_getFeature(Dom n, String feature, String version) {
      throw new RuntimeException("DOM Level 3 Not implemented");
   }

   public static boolean _node_isEqualNode(Dom n, Node arg) {
      throw new RuntimeException("DOM Level 3 Not implemented");
   }

   public static boolean _node_isSameNode(Dom n, Node arg) {
      throw new RuntimeException("DOM Level 3 Not implemented");
   }

   public static String _node_lookupNamespaceURI(Dom n, String prefix) {
      throw new RuntimeException("DOM Level 3 Not implemented");
   }

   public static boolean _node_isDefaultNamespace(Dom n, String namespaceURI) {
      throw new RuntimeException("DOM Level 3 Not implemented");
   }

   public static String _node_lookupPrefix(Dom n, String namespaceURI) {
      throw new RuntimeException("DOM Level 3 Not implemented");
   }

   public static void _node_setTextContent(Dom n, String textContent) {
      throw new RuntimeException("DOM Level 3 Not implemented");
   }

   public static String _node_getTextContent(Dom n) {
      throw new RuntimeException("DOM Level 3 Not implemented");
   }

   public static short _node_compareDocumentPosition(Dom n, Node other) {
      throw new RuntimeException("DOM Level 3 Not implemented");
   }

   public static String _node_getBaseURI(Dom n) {
      throw new RuntimeException("DOM Level 3 Not implemented");
   }

   public static Node _childNodes_item(Dom n, int i) {
      Locale l = n.locale();
      if (i == 0) {
         return _node_getFirstChild(n);
      } else {
         Dom d;
         if (l.noSync()) {
            d = childNodes_item(n, i);
         } else {
            synchronized(l) {
               d = childNodes_item(n, i);
            }
         }

         return (Node)d;
      }
   }

   public static Dom childNodes_item(Dom n, int i) {
      if (i < 0) {
         return null;
      } else {
         switch (n.nodeType()) {
            case 1:
            case 2:
            case 9:
            case 11:
            default:
               if (i == 0) {
                  return node_getFirstChild(n);
               }

               return n.locale().findDomNthChild(n, i);
            case 3:
            case 4:
            case 7:
            case 8:
               return null;
            case 5:
               throw new RuntimeException("Not impl");
            case 6:
            case 10:
            case 12:
               throw new RuntimeException("Not impl");
         }
      }
   }

   public static int _childNodes_getLength(Dom n) {
      Locale l = n.locale();

      assert n instanceof Xobj;

      Xobj node = (Xobj)n;
      int count;
      if (!node.isVacant() && (count = node.getDomZeroOneChildren()) < 2) {
         return count;
      } else if (l.noSync()) {
         return childNodes_getLength(n);
      } else {
         synchronized(l) {
            return childNodes_getLength(n);
         }
      }
   }

   public static int childNodes_getLength(Dom n) {
      switch (n.nodeType()) {
         case 1:
         case 2:
         case 9:
         case 11:
         default:
            assert n instanceof Xobj;

            Xobj node = (Xobj)n;
            node.ensureOccupancy();
            int count;
            if ((count = node.getDomZeroOneChildren()) < 2) {
               return count;
            }

            return n.locale().domLength(n);
         case 3:
         case 4:
         case 7:
         case 8:
            return 0;
         case 5:
            throw new RuntimeException("Not impl");
         case 6:
         case 10:
         case 12:
            throw new RuntimeException("Not impl");
      }
   }

   public static String _element_getTagName(Dom e) {
      return _node_getNodeName(e);
   }

   public static Attr _element_getAttributeNode(Dom e, String name) {
      return (Attr)_attributes_getNamedItem(e, name);
   }

   public static Attr _element_getAttributeNodeNS(Dom e, String uri, String local) {
      return (Attr)_attributes_getNamedItemNS(e, uri, local);
   }

   public static Attr _element_setAttributeNode(Dom e, Attr newAttr) {
      return (Attr)_attributes_setNamedItem(e, newAttr);
   }

   public static Attr _element_setAttributeNodeNS(Dom e, Attr newAttr) {
      return (Attr)_attributes_setNamedItemNS(e, newAttr);
   }

   public static String _element_getAttribute(Dom e, String name) {
      Node a = _attributes_getNamedItem(e, name);
      return a == null ? "" : a.getNodeValue();
   }

   public static String _element_getAttributeNS(Dom e, String uri, String local) {
      Node a = _attributes_getNamedItemNS(e, uri, local);
      return a == null ? "" : a.getNodeValue();
   }

   public static boolean _element_hasAttribute(Dom e, String name) {
      return _attributes_getNamedItem(e, name) != null;
   }

   public static boolean _element_hasAttributeNS(Dom e, String uri, String local) {
      return _attributes_getNamedItemNS(e, uri, local) != null;
   }

   public static void _element_removeAttribute(Dom e, String name) {
      try {
         _attributes_removeNamedItem(e, name);
      } catch (NotFoundErr var3) {
      }

   }

   public static void _element_removeAttributeNS(Dom e, String uri, String local) {
      try {
         _attributes_removeNamedItemNS(e, uri, local);
      } catch (NotFoundErr var4) {
      }

   }

   public static Attr _element_removeAttributeNode(Dom e, Attr oldAttr) {
      if (oldAttr == null) {
         throw new NotFoundErr("Attribute to remove is null");
      } else if (oldAttr.getOwnerElement() != e) {
         throw new NotFoundErr("Attribute to remove does not belong to this element");
      } else {
         return (Attr)_attributes_removeNamedItem(e, oldAttr.getNodeName());
      }
   }

   public static void _element_setAttribute(Dom e, String name, String value) {
      Locale l = e.locale();
      if (l.noSync()) {
         l.enter();

         try {
            element_setAttribute(e, name, value);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               element_setAttribute(e, name, value);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static void element_setAttribute(Dom e, String name, String value) {
      Dom a = attributes_getNamedItem(e, name);
      if (a == null) {
         a = document_createAttribute(node_getOwnerDocument(e), name);
         attributes_setNamedItem(e, a);
      }

      node_setNodeValue(a, value);
   }

   public static void _element_setAttributeNS(Dom e, String uri, String qname, String value) {
      Locale l = e.locale();
      if (l.noSync()) {
         l.enter();

         try {
            element_setAttributeNS(e, uri, qname, value);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               element_setAttributeNS(e, uri, qname, value);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static void element_setAttributeNS(Dom e, String uri, String qname, String value) {
      validateQualifiedName(qname, uri, true);
      QName name = e.locale().makeQualifiedQName(uri, qname);
      String local = name.getLocalPart();
      String prefix = validatePrefix(name.getPrefix(), uri, local, true);
      Dom a = attributes_getNamedItemNS(e, uri, local);
      if (a == null) {
         a = document_createAttributeNS(node_getOwnerDocument(e), uri, local);
         attributes_setNamedItemNS(e, a);
      }

      node_setPrefix(a, prefix);
      node_setNodeValue(a, value);
   }

   public static NodeList _element_getElementsByTagName(Dom e, String name) {
      Locale l = e.locale();
      if (l.noSync()) {
         l.enter();

         NodeList var3;
         try {
            var3 = element_getElementsByTagName(e, name);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            NodeList var4;
            try {
               var4 = element_getElementsByTagName(e, name);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static NodeList element_getElementsByTagName(Dom e, String name) {
      return new ElementsByTagNameNodeList(e, name);
   }

   public static NodeList _element_getElementsByTagNameNS(Dom e, String uri, String local) {
      Locale l = e.locale();
      if (l.noSync()) {
         l.enter();

         NodeList var4;
         try {
            var4 = element_getElementsByTagNameNS(e, uri, local);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            NodeList var5;
            try {
               var5 = element_getElementsByTagNameNS(e, uri, local);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static NodeList element_getElementsByTagNameNS(Dom e, String uri, String local) {
      return new ElementsByTagNameNSNodeList(e, uri, local);
   }

   public static int _attributes_getLength(Dom e) {
      Locale l = e.locale();
      if (l.noSync()) {
         l.enter();

         int var2;
         try {
            var2 = attributes_getLength(e);
         } finally {
            l.exit();
         }

         return var2;
      } else {
         synchronized(l) {
            l.enter();

            int var3;
            try {
               var3 = attributes_getLength(e);
            } finally {
               l.exit();
            }

            return var3;
         }
      }
   }

   public static int attributes_getLength(Dom e) {
      int n = 0;

      Cur c;
      for(c = e.tempCur(); c.toNextAttr(); ++n) {
      }

      c.release();
      return n;
   }

   public static Node _attributes_setNamedItem(Dom e, Node attr) {
      Locale l = e.locale();
      if (attr == null) {
         throw new IllegalArgumentException("Attr to set is null");
      } else {
         Dom a;
         if (attr instanceof Dom && (a = (Dom)attr).locale() == l) {
            Dom oldA;
            if (l.noSync()) {
               l.enter();

               try {
                  oldA = attributes_setNamedItem(e, a);
               } finally {
                  l.exit();
               }
            } else {
               synchronized(l) {
                  l.enter();

                  try {
                     oldA = attributes_setNamedItem(e, a);
                  } finally {
                     l.exit();
                  }
               }
            }

            return (Node)oldA;
         } else {
            throw new WrongDocumentErr("Attr to set is from another document");
         }
      }
   }

   public static Dom attributes_setNamedItem(Dom e, Dom a) {
      if (attr_getOwnerElement(a) != null) {
         throw new InuseAttributeError();
      } else if (a.nodeType() != 2) {
         throw new HierarchyRequestErr("Node is not an attribute");
      } else {
         String name = _node_getNodeName(a);
         Dom oldAttr = null;
         Cur c = e.tempCur();

         while(c.toNextAttr()) {
            Dom aa = c.getDom();
            if (_node_getNodeName(aa).equals(name)) {
               if (oldAttr == null) {
                  oldAttr = aa;
               } else {
                  removeNode(aa);
                  c.toPrevAttr();
               }
            }
         }

         if (oldAttr == null) {
            c.moveToDom(e);
            c.next();
            Cur.moveNode((Xobj)a, c);
         } else {
            c.moveToDom(oldAttr);
            Cur.moveNode((Xobj)a, c);
            removeNode(oldAttr);
         }

         c.release();
         return oldAttr;
      }
   }

   public static Node _attributes_getNamedItem(Dom e, String name) {
      Locale l = e.locale();
      Dom n;
      if (l.noSync()) {
         l.enter();

         try {
            n = attributes_getNamedItem(e, name);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               n = attributes_getNamedItem(e, name);
            } finally {
               l.exit();
            }
         }
      }

      return (Node)n;
   }

   public static Dom attributes_getNamedItem(Dom e, String name) {
      Dom a = null;
      Cur c = e.tempCur();

      while(c.toNextAttr()) {
         Dom d = c.getDom();
         if (_node_getNodeName(d).equals(name)) {
            a = d;
            break;
         }
      }

      c.release();
      return a;
   }

   public static Node _attributes_getNamedItemNS(Dom e, String uri, String local) {
      Locale l = e.locale();
      Dom n;
      if (l.noSync()) {
         l.enter();

         try {
            n = attributes_getNamedItemNS(e, uri, local);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               n = attributes_getNamedItemNS(e, uri, local);
            } finally {
               l.exit();
            }
         }
      }

      return (Node)n;
   }

   public static Dom attributes_getNamedItemNS(Dom e, String uri, String local) {
      if (uri == null) {
         uri = "";
      }

      Dom a = null;
      Cur c = e.tempCur();

      while(c.toNextAttr()) {
         Dom d = c.getDom();
         QName n = d.getQName();
         if (n.getNamespaceURI().equals(uri) && n.getLocalPart().equals(local)) {
            a = d;
            break;
         }
      }

      c.release();
      return a;
   }

   public static Node _attributes_removeNamedItem(Dom e, String name) {
      Locale l = e.locale();
      Dom n;
      if (l.noSync()) {
         l.enter();

         try {
            n = attributes_removeNamedItem(e, name);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               n = attributes_removeNamedItem(e, name);
            } finally {
               l.exit();
            }
         }
      }

      return (Node)n;
   }

   public static Dom attributes_removeNamedItem(Dom e, String name) {
      Dom oldAttr = null;
      Cur c = e.tempCur();

      while(c.toNextAttr()) {
         Dom aa = c.getDom();
         if (_node_getNodeName(aa).equals(name)) {
            if (oldAttr == null) {
               oldAttr = aa;
            }

            if (((Xobj.AttrXobj)aa).isId()) {
               Dom d = node_getOwnerDocument(aa);
               String val = node_getNodeValue(aa);
               if (d instanceof Xobj.DocumentXobj) {
                  ((Xobj.DocumentXobj)d).removeIdElement(val);
               }
            }

            removeNode(aa);
            c.toPrevAttr();
         }
      }

      c.release();
      if (oldAttr == null) {
         throw new NotFoundErr("Named item not found: " + name);
      } else {
         return oldAttr;
      }
   }

   public static Node _attributes_removeNamedItemNS(Dom e, String uri, String local) {
      Locale l = e.locale();
      Dom n;
      if (l.noSync()) {
         l.enter();

         try {
            n = attributes_removeNamedItemNS(e, uri, local);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               n = attributes_removeNamedItemNS(e, uri, local);
            } finally {
               l.exit();
            }
         }
      }

      return (Node)n;
   }

   public static Dom attributes_removeNamedItemNS(Dom e, String uri, String local) {
      if (uri == null) {
         uri = "";
      }

      Dom oldAttr = null;
      Cur c = e.tempCur();

      while(c.toNextAttr()) {
         Dom aa = c.getDom();
         QName qn = aa.getQName();
         if (qn.getNamespaceURI().equals(uri) && qn.getLocalPart().equals(local)) {
            if (oldAttr == null) {
               oldAttr = aa;
            }

            if (((Xobj.AttrXobj)aa).isId()) {
               Dom d = node_getOwnerDocument(aa);
               String val = node_getNodeValue(aa);
               if (d instanceof Xobj.DocumentXobj) {
                  ((Xobj.DocumentXobj)d).removeIdElement(val);
               }
            }

            removeNode(aa);
            c.toPrevAttr();
         }
      }

      c.release();
      if (oldAttr == null) {
         throw new NotFoundErr("Named item not found: uri=" + uri + ", local=" + local);
      } else {
         return oldAttr;
      }
   }

   public static Node _attributes_setNamedItemNS(Dom e, Node attr) {
      Locale l = e.locale();
      if (attr == null) {
         throw new IllegalArgumentException("Attr to set is null");
      } else {
         Dom a;
         if (attr instanceof Dom && (a = (Dom)attr).locale() == l) {
            Dom oldA;
            if (l.noSync()) {
               l.enter();

               try {
                  oldA = attributes_setNamedItemNS(e, a);
               } finally {
                  l.exit();
               }
            } else {
               synchronized(l) {
                  l.enter();

                  try {
                     oldA = attributes_setNamedItemNS(e, a);
                  } finally {
                     l.exit();
                  }
               }
            }

            return (Node)oldA;
         } else {
            throw new WrongDocumentErr("Attr to set is from another document");
         }
      }
   }

   public static Dom attributes_setNamedItemNS(Dom e, Dom a) {
      Dom owner = attr_getOwnerElement(a);
      if (owner == e) {
         return a;
      } else if (owner != null) {
         throw new InuseAttributeError();
      } else if (a.nodeType() != 2) {
         throw new HierarchyRequestErr("Node is not an attribute");
      } else {
         QName name = a.getQName();
         Dom oldAttr = null;
         Cur c = e.tempCur();

         while(c.toNextAttr()) {
            Dom aa = c.getDom();
            if (aa.getQName().equals(name)) {
               if (oldAttr == null) {
                  oldAttr = aa;
               } else {
                  removeNode(aa);
                  c.toPrevAttr();
               }
            }
         }

         if (oldAttr == null) {
            c.moveToDom(e);
            c.next();
            Cur.moveNode((Xobj)a, c);
         } else {
            c.moveToDom(oldAttr);
            Cur.moveNode((Xobj)a, c);
            removeNode(oldAttr);
         }

         c.release();
         return oldAttr;
      }
   }

   public static Node _attributes_item(Dom e, int index) {
      Locale l = e.locale();
      Dom a;
      if (l.noSync()) {
         l.enter();

         try {
            a = attributes_item(e, index);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               a = attributes_item(e, index);
            } finally {
               l.exit();
            }
         }
      }

      return (Node)a;
   }

   public static Dom attributes_item(Dom e, int index) {
      if (index < 0) {
         return null;
      } else {
         Cur c = e.tempCur();
         Dom a = null;

         while(c.toNextAttr()) {
            if (index-- == 0) {
               a = c.getDom();
               break;
            }
         }

         c.release();
         return a;
      }
   }

   public static String _processingInstruction_getData(Dom p) {
      return _node_getNodeValue(p);
   }

   public static String _processingInstruction_getTarget(Dom p) {
      return _node_getNodeName(p);
   }

   public static void _processingInstruction_setData(Dom p, String data) {
      _node_setNodeValue(p, data);
   }

   public static boolean _attr_getSpecified(Dom a) {
      return true;
   }

   public static Element _attr_getOwnerElement(Dom a) {
      Locale l = a.locale();
      Dom e;
      if (l.noSync()) {
         l.enter();

         try {
            e = attr_getOwnerElement(a);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               e = attr_getOwnerElement(a);
            } finally {
               l.exit();
            }
         }
      }

      return (Element)e;
   }

   public static Dom attr_getOwnerElement(Dom n) {
      Cur c = n.tempCur();
      if (!c.toParentRaw()) {
         c.release();
         return null;
      } else {
         Dom p = c.getDom();
         c.release();
         return p;
      }
   }

   public static void _characterData_appendData(Dom cd, String arg) {
      if (arg != null && arg.length() != 0) {
         _node_setNodeValue(cd, _node_getNodeValue(cd) + arg);
      }

   }

   public static void _characterData_deleteData(Dom c, int offset, int count) {
      String s = _characterData_getData(c);
      if (offset >= 0 && offset <= s.length() && count >= 0) {
         if (offset + count > s.length()) {
            count = s.length() - offset;
         }

         if (count > 0) {
            _characterData_setData(c, s.substring(0, offset) + s.substring(offset + count));
         }

      } else {
         throw new IndexSizeError();
      }
   }

   public static String _characterData_getData(Dom c) {
      return _node_getNodeValue(c);
   }

   public static int _characterData_getLength(Dom c) {
      return _characterData_getData(c).length();
   }

   public static void _characterData_insertData(Dom c, int offset, String arg) {
      String s = _characterData_getData(c);
      if (offset >= 0 && offset <= s.length()) {
         if (arg != null && arg.length() > 0) {
            _characterData_setData(c, s.substring(0, offset) + arg + s.substring(offset));
         }

      } else {
         throw new IndexSizeError();
      }
   }

   public static void _characterData_replaceData(Dom c, int offset, int count, String arg) {
      String s = _characterData_getData(c);
      if (offset >= 0 && offset <= s.length() && count >= 0) {
         if (offset + count > s.length()) {
            count = s.length() - offset;
         }

         if (count > 0) {
            _characterData_setData(c, s.substring(0, offset) + (arg == null ? "" : arg) + s.substring(offset + count));
         }

      } else {
         throw new IndexSizeError();
      }
   }

   public static void _characterData_setData(Dom c, String data) {
      _node_setNodeValue(c, data);
   }

   public static String _characterData_substringData(Dom c, int offset, int count) {
      String s = _characterData_getData(c);
      if (offset >= 0 && offset <= s.length() && count >= 0) {
         if (offset + count > s.length()) {
            count = s.length() - offset;
         }

         return s.substring(offset, offset + count);
      } else {
         throw new IndexSizeError();
      }
   }

   public static Text _text_splitText(Dom t, int offset) {
      assert t.nodeType() == 3;

      String s = _characterData_getData(t);
      if (offset >= 0 && offset <= s.length()) {
         _characterData_deleteData(t, offset, s.length() - offset);
         Dom t2 = (Dom)_document_createTextNode(t, s.substring(offset));
         Dom p = (Dom)_node_getParentNode(t);
         if (p != null) {
            _node_insertBefore(p, (Text)t2, _node_getNextSibling(t));
            t.locale().invalidateDomCaches(p);
         }

         return (Text)t2;
      } else {
         throw new IndexSizeError();
      }
   }

   public static String _text_getWholeText(Dom t) {
      throw new RuntimeException("DOM Level 3 Not implemented");
   }

   public static boolean _text_isElementContentWhitespace(Dom t) {
      throw new RuntimeException("DOM Level 3 Not implemented");
   }

   public static Text _text_replaceWholeText(Dom t, String content) {
      throw new RuntimeException("DOM Level 3 Not implemented");
   }

   public static XMLStreamReader _getXmlStreamReader(Dom n) {
      Locale l = n.locale();
      if (l.noSync()) {
         l.enter();

         XMLStreamReader var2;
         try {
            var2 = getXmlStreamReader(n);
         } finally {
            l.exit();
         }

         return var2;
      } else {
         synchronized(l) {
            l.enter();

            XMLStreamReader var3;
            try {
               var3 = getXmlStreamReader(n);
            } finally {
               l.exit();
            }

            return var3;
         }
      }
   }

   public static XMLStreamReader getXmlStreamReader(Dom n) {
      XMLStreamReader xs;
      switch (n.nodeType()) {
         case 1:
         case 2:
         case 7:
         case 8:
         case 9:
         case 11:
            Cur c = n.tempCur();
            xs = Jsr173.newXmlStreamReader(c, (XmlOptions)null);
            c.release();
            break;
         case 3:
         case 4:
            CharNode cn = (CharNode)n;
            Cur c;
            if ((c = cn.tempCur()) == null) {
               c = n.locale().tempCur();
               xs = Jsr173.newXmlStreamReader(c, cn._src, cn._off, cn._cch);
            } else {
               xs = Jsr173.newXmlStreamReader(c, c.getChars(cn._cch), c._offSrc, c._cchSrc);
            }

            c.release();
            break;
         case 5:
         case 6:
         case 10:
         case 12:
            throw new RuntimeException("Not impl");
         default:
            throw new RuntimeException("Unknown kind");
      }

      return xs;
   }

   public static XmlCursor _getXmlCursor(Dom n) {
      Locale l = n.locale();
      if (l.noSync()) {
         l.enter();

         XmlCursor var2;
         try {
            var2 = getXmlCursor(n);
         } finally {
            l.exit();
         }

         return var2;
      } else {
         synchronized(l) {
            l.enter();

            XmlCursor var3;
            try {
               var3 = getXmlCursor(n);
            } finally {
               l.exit();
            }

            return var3;
         }
      }
   }

   public static XmlCursor getXmlCursor(Dom n) {
      Cur c = n.tempCur();
      Cursor xc = new Cursor(c);
      c.release();
      return xc;
   }

   public static XmlObject _getXmlObject(Dom n) {
      Locale l = n.locale();
      if (l.noSync()) {
         l.enter();

         XmlObject var2;
         try {
            var2 = getXmlObject(n);
         } finally {
            l.exit();
         }

         return var2;
      } else {
         synchronized(l) {
            l.enter();

            XmlObject var3;
            try {
               var3 = getXmlObject(n);
            } finally {
               l.exit();
            }

            return var3;
         }
      }
   }

   public static XmlObject getXmlObject(Dom n) {
      Cur c = n.tempCur();
      XmlObject x = c.getObject();
      c.release();
      return x;
   }

   public static boolean _soapText_isComment(Dom n) {
      Locale l = n.locale();
      org.apache.xmlbeans.impl.soap.Text text = (org.apache.xmlbeans.impl.soap.Text)n;
      if (l.noSync()) {
         l.enter();

         boolean var3;
         try {
            var3 = l._saaj.soapText_isComment(text);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            boolean var4;
            try {
               var4 = l._saaj.soapText_isComment(text);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static void _soapNode_detachNode(Dom n) {
      Locale l = n.locale();
      org.apache.xmlbeans.impl.soap.Node node = (org.apache.xmlbeans.impl.soap.Node)n;
      if (l.noSync()) {
         l.enter();

         try {
            l._saaj.soapNode_detachNode(node);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               l._saaj.soapNode_detachNode(node);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static void _soapNode_recycleNode(Dom n) {
      Locale l = n.locale();
      org.apache.xmlbeans.impl.soap.Node node = (org.apache.xmlbeans.impl.soap.Node)n;
      if (l.noSync()) {
         l.enter();

         try {
            l._saaj.soapNode_recycleNode(node);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               l._saaj.soapNode_recycleNode(node);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static String _soapNode_getValue(Dom n) {
      Locale l = n.locale();
      org.apache.xmlbeans.impl.soap.Node node = (org.apache.xmlbeans.impl.soap.Node)n;
      if (l.noSync()) {
         l.enter();

         String var3;
         try {
            var3 = l._saaj.soapNode_getValue(node);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            String var4;
            try {
               var4 = l._saaj.soapNode_getValue(node);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static void _soapNode_setValue(Dom n, String value) {
      Locale l = n.locale();
      org.apache.xmlbeans.impl.soap.Node node = (org.apache.xmlbeans.impl.soap.Node)n;
      if (l.noSync()) {
         l.enter();

         try {
            l._saaj.soapNode_setValue(node, value);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               l._saaj.soapNode_setValue(node, value);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static SOAPElement _soapNode_getParentElement(Dom n) {
      Locale l = n.locale();
      org.apache.xmlbeans.impl.soap.Node node = (org.apache.xmlbeans.impl.soap.Node)n;
      if (l.noSync()) {
         l.enter();

         SOAPElement var3;
         try {
            var3 = l._saaj.soapNode_getParentElement(node);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            SOAPElement var4;
            try {
               var4 = l._saaj.soapNode_getParentElement(node);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static void _soapNode_setParentElement(Dom n, SOAPElement p) {
      Locale l = n.locale();
      org.apache.xmlbeans.impl.soap.Node node = (org.apache.xmlbeans.impl.soap.Node)n;
      if (l.noSync()) {
         l.enter();

         try {
            l._saaj.soapNode_setParentElement(node, p);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               l._saaj.soapNode_setParentElement(node, p);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static void _soapElement_removeContents(Dom d) {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         try {
            l._saaj.soapElement_removeContents(se);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               l._saaj.soapElement_removeContents(se);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static String _soapElement_getEncodingStyle(Dom d) {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         String var3;
         try {
            var3 = l._saaj.soapElement_getEncodingStyle(se);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            String var4;
            try {
               var4 = l._saaj.soapElement_getEncodingStyle(se);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static void _soapElement_setEncodingStyle(Dom d, String encodingStyle) {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         try {
            l._saaj.soapElement_setEncodingStyle(se, encodingStyle);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               l._saaj.soapElement_setEncodingStyle(se, encodingStyle);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static boolean _soapElement_removeNamespaceDeclaration(Dom d, String prefix) {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         boolean var4;
         try {
            var4 = l._saaj.soapElement_removeNamespaceDeclaration(se, prefix);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            boolean var5;
            try {
               var5 = l._saaj.soapElement_removeNamespaceDeclaration(se, prefix);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static Iterator _soapElement_getAllAttributes(Dom d) {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         Iterator var3;
         try {
            var3 = l._saaj.soapElement_getAllAttributes(se);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            Iterator var4;
            try {
               var4 = l._saaj.soapElement_getAllAttributes(se);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static Iterator _soapElement_getChildElements(Dom d) {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         Iterator var3;
         try {
            var3 = l._saaj.soapElement_getChildElements(se);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            Iterator var4;
            try {
               var4 = l._saaj.soapElement_getChildElements(se);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static Iterator _soapElement_getNamespacePrefixes(Dom d) {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         Iterator var3;
         try {
            var3 = l._saaj.soapElement_getNamespacePrefixes(se);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            Iterator var4;
            try {
               var4 = l._saaj.soapElement_getNamespacePrefixes(se);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static SOAPElement _soapElement_addAttribute(Dom d, Name name, String value) throws SOAPException {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         SOAPElement var5;
         try {
            var5 = l._saaj.soapElement_addAttribute(se, name, value);
         } finally {
            l.exit();
         }

         return var5;
      } else {
         synchronized(l) {
            l.enter();

            SOAPElement var6;
            try {
               var6 = l._saaj.soapElement_addAttribute(se, name, value);
            } finally {
               l.exit();
            }

            return var6;
         }
      }
   }

   public static SOAPElement _soapElement_addChildElement(Dom d, SOAPElement oldChild) throws SOAPException {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         SOAPElement var4;
         try {
            var4 = l._saaj.soapElement_addChildElement(se, oldChild);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            SOAPElement var5;
            try {
               var5 = l._saaj.soapElement_addChildElement(se, oldChild);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static SOAPElement _soapElement_addChildElement(Dom d, Name name) throws SOAPException {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         SOAPElement var4;
         try {
            var4 = l._saaj.soapElement_addChildElement(se, name);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            SOAPElement var5;
            try {
               var5 = l._saaj.soapElement_addChildElement(se, name);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static SOAPElement _soapElement_addChildElement(Dom d, String localName) throws SOAPException {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         SOAPElement var4;
         try {
            var4 = l._saaj.soapElement_addChildElement(se, localName);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            SOAPElement var5;
            try {
               var5 = l._saaj.soapElement_addChildElement(se, localName);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static SOAPElement _soapElement_addChildElement(Dom d, String localName, String prefix) throws SOAPException {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         SOAPElement var5;
         try {
            var5 = l._saaj.soapElement_addChildElement(se, localName, prefix);
         } finally {
            l.exit();
         }

         return var5;
      } else {
         synchronized(l) {
            l.enter();

            SOAPElement var6;
            try {
               var6 = l._saaj.soapElement_addChildElement(se, localName, prefix);
            } finally {
               l.exit();
            }

            return var6;
         }
      }
   }

   public static SOAPElement _soapElement_addChildElement(Dom d, String localName, String prefix, String uri) throws SOAPException {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         SOAPElement var6;
         try {
            var6 = l._saaj.soapElement_addChildElement(se, localName, prefix, uri);
         } finally {
            l.exit();
         }

         return var6;
      } else {
         synchronized(l) {
            l.enter();

            SOAPElement var7;
            try {
               var7 = l._saaj.soapElement_addChildElement(se, localName, prefix, uri);
            } finally {
               l.exit();
            }

            return var7;
         }
      }
   }

   public static SOAPElement _soapElement_addNamespaceDeclaration(Dom d, String prefix, String uri) {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         SOAPElement var5;
         try {
            var5 = l._saaj.soapElement_addNamespaceDeclaration(se, prefix, uri);
         } finally {
            l.exit();
         }

         return var5;
      } else {
         synchronized(l) {
            l.enter();

            SOAPElement var6;
            try {
               var6 = l._saaj.soapElement_addNamespaceDeclaration(se, prefix, uri);
            } finally {
               l.exit();
            }

            return var6;
         }
      }
   }

   public static SOAPElement _soapElement_addTextNode(Dom d, String data) {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         SOAPElement var4;
         try {
            var4 = l._saaj.soapElement_addTextNode(se, data);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            SOAPElement var5;
            try {
               var5 = l._saaj.soapElement_addTextNode(se, data);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static String _soapElement_getAttributeValue(Dom d, Name name) {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         String var4;
         try {
            var4 = l._saaj.soapElement_getAttributeValue(se, name);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            String var5;
            try {
               var5 = l._saaj.soapElement_getAttributeValue(se, name);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static Iterator _soapElement_getChildElements(Dom d, Name name) {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         Iterator var4;
         try {
            var4 = l._saaj.soapElement_getChildElements(se, name);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            Iterator var5;
            try {
               var5 = l._saaj.soapElement_getChildElements(se, name);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static Name _soapElement_getElementName(Dom d) {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         Name var3;
         try {
            var3 = l._saaj.soapElement_getElementName(se);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            Name var4;
            try {
               var4 = l._saaj.soapElement_getElementName(se);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static String _soapElement_getNamespaceURI(Dom d, String prefix) {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         String var4;
         try {
            var4 = l._saaj.soapElement_getNamespaceURI(se, prefix);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            String var5;
            try {
               var5 = l._saaj.soapElement_getNamespaceURI(se, prefix);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static Iterator _soapElement_getVisibleNamespacePrefixes(Dom d) {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         Iterator var3;
         try {
            var3 = l._saaj.soapElement_getVisibleNamespacePrefixes(se);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            Iterator var4;
            try {
               var4 = l._saaj.soapElement_getVisibleNamespacePrefixes(se);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static boolean _soapElement_removeAttribute(Dom d, Name name) {
      Locale l = d.locale();
      SOAPElement se = (SOAPElement)d;
      if (l.noSync()) {
         l.enter();

         boolean var4;
         try {
            var4 = l._saaj.soapElement_removeAttribute(se, name);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            boolean var5;
            try {
               var5 = l._saaj.soapElement_removeAttribute(se, name);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static SOAPBody _soapEnvelope_addBody(Dom d) throws SOAPException {
      Locale l = d.locale();
      SOAPEnvelope se = (SOAPEnvelope)d;
      if (l.noSync()) {
         l.enter();

         SOAPBody var3;
         try {
            var3 = l._saaj.soapEnvelope_addBody(se);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            SOAPBody var4;
            try {
               var4 = l._saaj.soapEnvelope_addBody(se);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static SOAPBody _soapEnvelope_getBody(Dom d) throws SOAPException {
      Locale l = d.locale();
      SOAPEnvelope se = (SOAPEnvelope)d;
      if (l.noSync()) {
         l.enter();

         SOAPBody var3;
         try {
            var3 = l._saaj.soapEnvelope_getBody(se);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            SOAPBody var4;
            try {
               var4 = l._saaj.soapEnvelope_getBody(se);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static SOAPHeader _soapEnvelope_getHeader(Dom d) throws SOAPException {
      Locale l = d.locale();
      SOAPEnvelope se = (SOAPEnvelope)d;
      if (l.noSync()) {
         l.enter();

         SOAPHeader var3;
         try {
            var3 = l._saaj.soapEnvelope_getHeader(se);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            SOAPHeader var4;
            try {
               var4 = l._saaj.soapEnvelope_getHeader(se);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static SOAPHeader _soapEnvelope_addHeader(Dom d) throws SOAPException {
      Locale l = d.locale();
      SOAPEnvelope se = (SOAPEnvelope)d;
      if (l.noSync()) {
         l.enter();

         SOAPHeader var3;
         try {
            var3 = l._saaj.soapEnvelope_addHeader(se);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            SOAPHeader var4;
            try {
               var4 = l._saaj.soapEnvelope_addHeader(se);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static Name _soapEnvelope_createName(Dom d, String localName) {
      Locale l = d.locale();
      SOAPEnvelope se = (SOAPEnvelope)d;
      if (l.noSync()) {
         l.enter();

         Name var4;
         try {
            var4 = l._saaj.soapEnvelope_createName(se, localName);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            Name var5;
            try {
               var5 = l._saaj.soapEnvelope_createName(se, localName);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static Name _soapEnvelope_createName(Dom d, String localName, String prefix, String namespaceURI) {
      Locale l = d.locale();
      SOAPEnvelope se = (SOAPEnvelope)d;
      if (l.noSync()) {
         l.enter();

         Name var6;
         try {
            var6 = l._saaj.soapEnvelope_createName(se, localName, prefix, namespaceURI);
         } finally {
            l.exit();
         }

         return var6;
      } else {
         synchronized(l) {
            l.enter();

            Name var7;
            try {
               var7 = l._saaj.soapEnvelope_createName(se, localName, prefix, namespaceURI);
            } finally {
               l.exit();
            }

            return var7;
         }
      }
   }

   public static Iterator soapHeader_examineAllHeaderElements(Dom d) {
      Locale l = d.locale();
      SOAPHeader sh = (SOAPHeader)d;
      if (l.noSync()) {
         l.enter();

         Iterator var3;
         try {
            var3 = l._saaj.soapHeader_examineAllHeaderElements(sh);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            Iterator var4;
            try {
               var4 = l._saaj.soapHeader_examineAllHeaderElements(sh);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static Iterator soapHeader_extractAllHeaderElements(Dom d) {
      Locale l = d.locale();
      SOAPHeader sh = (SOAPHeader)d;
      if (l.noSync()) {
         l.enter();

         Iterator var3;
         try {
            var3 = l._saaj.soapHeader_extractAllHeaderElements(sh);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            Iterator var4;
            try {
               var4 = l._saaj.soapHeader_extractAllHeaderElements(sh);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static Iterator soapHeader_examineHeaderElements(Dom d, String actor) {
      Locale l = d.locale();
      SOAPHeader sh = (SOAPHeader)d;
      if (l.noSync()) {
         l.enter();

         Iterator var4;
         try {
            var4 = l._saaj.soapHeader_examineHeaderElements(sh, actor);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            Iterator var5;
            try {
               var5 = l._saaj.soapHeader_examineHeaderElements(sh, actor);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static Iterator soapHeader_examineMustUnderstandHeaderElements(Dom d, String mustUnderstandString) {
      Locale l = d.locale();
      SOAPHeader sh = (SOAPHeader)d;
      if (l.noSync()) {
         l.enter();

         Iterator var4;
         try {
            var4 = l._saaj.soapHeader_examineMustUnderstandHeaderElements(sh, mustUnderstandString);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            Iterator var5;
            try {
               var5 = l._saaj.soapHeader_examineMustUnderstandHeaderElements(sh, mustUnderstandString);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static Iterator soapHeader_extractHeaderElements(Dom d, String actor) {
      Locale l = d.locale();
      SOAPHeader sh = (SOAPHeader)d;
      if (l.noSync()) {
         l.enter();

         Iterator var4;
         try {
            var4 = l._saaj.soapHeader_extractHeaderElements(sh, actor);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            Iterator var5;
            try {
               var5 = l._saaj.soapHeader_extractHeaderElements(sh, actor);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static SOAPHeaderElement soapHeader_addHeaderElement(Dom d, Name name) {
      Locale l = d.locale();
      SOAPHeader sh = (SOAPHeader)d;
      if (l.noSync()) {
         l.enter();

         SOAPHeaderElement var4;
         try {
            var4 = l._saaj.soapHeader_addHeaderElement(sh, name);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            SOAPHeaderElement var5;
            try {
               var5 = l._saaj.soapHeader_addHeaderElement(sh, name);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static boolean soapBody_hasFault(Dom d) {
      Locale l = d.locale();
      SOAPBody sb = (SOAPBody)d;
      if (l.noSync()) {
         l.enter();

         boolean var3;
         try {
            var3 = l._saaj.soapBody_hasFault(sb);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            boolean var4;
            try {
               var4 = l._saaj.soapBody_hasFault(sb);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static SOAPFault soapBody_addFault(Dom d) throws SOAPException {
      Locale l = d.locale();
      SOAPBody sb = (SOAPBody)d;
      if (l.noSync()) {
         l.enter();

         SOAPFault var3;
         try {
            var3 = l._saaj.soapBody_addFault(sb);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            SOAPFault var4;
            try {
               var4 = l._saaj.soapBody_addFault(sb);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static SOAPFault soapBody_getFault(Dom d) {
      Locale l = d.locale();
      SOAPBody sb = (SOAPBody)d;
      if (l.noSync()) {
         l.enter();

         SOAPFault var3;
         try {
            var3 = l._saaj.soapBody_getFault(sb);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            SOAPFault var4;
            try {
               var4 = l._saaj.soapBody_getFault(sb);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static SOAPBodyElement soapBody_addBodyElement(Dom d, Name name) {
      Locale l = d.locale();
      SOAPBody sb = (SOAPBody)d;
      if (l.noSync()) {
         l.enter();

         SOAPBodyElement var4;
         try {
            var4 = l._saaj.soapBody_addBodyElement(sb, name);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            SOAPBodyElement var5;
            try {
               var5 = l._saaj.soapBody_addBodyElement(sb, name);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static SOAPBodyElement soapBody_addDocument(Dom d, Document document) {
      Locale l = d.locale();
      SOAPBody sb = (SOAPBody)d;
      if (l.noSync()) {
         l.enter();

         SOAPBodyElement var4;
         try {
            var4 = l._saaj.soapBody_addDocument(sb, document);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            SOAPBodyElement var5;
            try {
               var5 = l._saaj.soapBody_addDocument(sb, document);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static SOAPFault soapBody_addFault(Dom d, Name name, String s) throws SOAPException {
      Locale l = d.locale();
      SOAPBody sb = (SOAPBody)d;
      if (l.noSync()) {
         l.enter();

         SOAPFault var5;
         try {
            var5 = l._saaj.soapBody_addFault(sb, name, s);
         } finally {
            l.exit();
         }

         return var5;
      } else {
         synchronized(l) {
            l.enter();

            SOAPFault var6;
            try {
               var6 = l._saaj.soapBody_addFault(sb, name, s);
            } finally {
               l.exit();
            }

            return var6;
         }
      }
   }

   public static SOAPFault soapBody_addFault(Dom d, Name faultCode, String faultString, java.util.Locale locale) throws SOAPException {
      Locale l = d.locale();
      SOAPBody sb = (SOAPBody)d;
      if (l.noSync()) {
         l.enter();

         SOAPFault var6;
         try {
            var6 = l._saaj.soapBody_addFault(sb, faultCode, faultString, locale);
         } finally {
            l.exit();
         }

         return var6;
      } else {
         synchronized(l) {
            l.enter();

            SOAPFault var7;
            try {
               var7 = l._saaj.soapBody_addFault(sb, faultCode, faultString, locale);
            } finally {
               l.exit();
            }

            return var7;
         }
      }
   }

   public static void soapFault_setFaultString(Dom d, String faultString) {
      Locale l = d.locale();
      SOAPFault sf = (SOAPFault)d;
      if (l.noSync()) {
         l.enter();

         try {
            l._saaj.soapFault_setFaultString(sf, faultString);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               l._saaj.soapFault_setFaultString(sf, faultString);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static void soapFault_setFaultString(Dom d, String faultString, java.util.Locale locale) {
      Locale l = d.locale();
      SOAPFault sf = (SOAPFault)d;
      if (l.noSync()) {
         l.enter();

         try {
            l._saaj.soapFault_setFaultString(sf, faultString, locale);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               l._saaj.soapFault_setFaultString(sf, faultString, locale);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static void soapFault_setFaultCode(Dom d, Name faultCodeName) throws SOAPException {
      Locale l = d.locale();
      SOAPFault sf = (SOAPFault)d;
      if (l.noSync()) {
         l.enter();

         try {
            l._saaj.soapFault_setFaultCode(sf, faultCodeName);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               l._saaj.soapFault_setFaultCode(sf, faultCodeName);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static void soapFault_setFaultActor(Dom d, String faultActorString) {
      Locale l = d.locale();
      SOAPFault sf = (SOAPFault)d;
      if (l.noSync()) {
         l.enter();

         try {
            l._saaj.soapFault_setFaultActor(sf, faultActorString);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               l._saaj.soapFault_setFaultActor(sf, faultActorString);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static String soapFault_getFaultActor(Dom d) {
      Locale l = d.locale();
      SOAPFault sf = (SOAPFault)d;
      if (l.noSync()) {
         l.enter();

         String var3;
         try {
            var3 = l._saaj.soapFault_getFaultActor(sf);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            String var4;
            try {
               var4 = l._saaj.soapFault_getFaultActor(sf);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static String soapFault_getFaultCode(Dom d) {
      Locale l = d.locale();
      SOAPFault sf = (SOAPFault)d;
      if (l.noSync()) {
         l.enter();

         String var3;
         try {
            var3 = l._saaj.soapFault_getFaultCode(sf);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            String var4;
            try {
               var4 = l._saaj.soapFault_getFaultCode(sf);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static void soapFault_setFaultCode(Dom d, String faultCode) throws SOAPException {
      Locale l = d.locale();
      SOAPFault sf = (SOAPFault)d;
      if (l.noSync()) {
         l.enter();

         try {
            l._saaj.soapFault_setFaultCode(sf, faultCode);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               l._saaj.soapFault_setFaultCode(sf, faultCode);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static java.util.Locale soapFault_getFaultStringLocale(Dom d) {
      Locale l = d.locale();
      SOAPFault sf = (SOAPFault)d;
      if (l.noSync()) {
         l.enter();

         java.util.Locale var3;
         try {
            var3 = l._saaj.soapFault_getFaultStringLocale(sf);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            java.util.Locale var4;
            try {
               var4 = l._saaj.soapFault_getFaultStringLocale(sf);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static Name soapFault_getFaultCodeAsName(Dom d) {
      Locale l = d.locale();
      SOAPFault sf = (SOAPFault)d;
      if (l.noSync()) {
         l.enter();

         Name var3;
         try {
            var3 = l._saaj.soapFault_getFaultCodeAsName(sf);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            Name var4;
            try {
               var4 = l._saaj.soapFault_getFaultCodeAsName(sf);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static String soapFault_getFaultString(Dom d) {
      Locale l = d.locale();
      SOAPFault sf = (SOAPFault)d;
      if (l.noSync()) {
         l.enter();

         String var3;
         try {
            var3 = l._saaj.soapFault_getFaultString(sf);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            String var4;
            try {
               var4 = l._saaj.soapFault_getFaultString(sf);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static Detail soapFault_addDetail(Dom d) throws SOAPException {
      Locale l = d.locale();
      SOAPFault sf = (SOAPFault)d;
      if (l.noSync()) {
         l.enter();

         Detail var3;
         try {
            var3 = l._saaj.soapFault_addDetail(sf);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            Detail var4;
            try {
               var4 = l._saaj.soapFault_addDetail(sf);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static Detail soapFault_getDetail(Dom d) {
      Locale l = d.locale();
      SOAPFault sf = (SOAPFault)d;
      if (l.noSync()) {
         l.enter();

         Detail var3;
         try {
            var3 = l._saaj.soapFault_getDetail(sf);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            Detail var4;
            try {
               var4 = l._saaj.soapFault_getDetail(sf);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static void soapHeaderElement_setMustUnderstand(Dom d, boolean mustUnderstand) {
      Locale l = d.locale();
      SOAPHeaderElement she = (SOAPHeaderElement)d;
      if (l.noSync()) {
         l.enter();

         try {
            l._saaj.soapHeaderElement_setMustUnderstand(she, mustUnderstand);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               l._saaj.soapHeaderElement_setMustUnderstand(she, mustUnderstand);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static boolean soapHeaderElement_getMustUnderstand(Dom d) {
      Locale l = d.locale();
      SOAPHeaderElement she = (SOAPHeaderElement)d;
      if (l.noSync()) {
         l.enter();

         boolean var3;
         try {
            var3 = l._saaj.soapHeaderElement_getMustUnderstand(she);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            boolean var4;
            try {
               var4 = l._saaj.soapHeaderElement_getMustUnderstand(she);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static void soapHeaderElement_setActor(Dom d, String actor) {
      Locale l = d.locale();
      SOAPHeaderElement she = (SOAPHeaderElement)d;
      if (l.noSync()) {
         l.enter();

         try {
            l._saaj.soapHeaderElement_setActor(she, actor);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               l._saaj.soapHeaderElement_setActor(she, actor);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static String soapHeaderElement_getActor(Dom d) {
      Locale l = d.locale();
      SOAPHeaderElement she = (SOAPHeaderElement)d;
      if (l.noSync()) {
         l.enter();

         String var3;
         try {
            var3 = l._saaj.soapHeaderElement_getActor(she);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            String var4;
            try {
               var4 = l._saaj.soapHeaderElement_getActor(she);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static DetailEntry detail_addDetailEntry(Dom d, Name name) {
      Locale l = d.locale();
      Detail detail = (Detail)d;
      if (l.noSync()) {
         l.enter();

         DetailEntry var4;
         try {
            var4 = l._saaj.detail_addDetailEntry(detail, name);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            DetailEntry var5;
            try {
               var5 = l._saaj.detail_addDetailEntry(detail, name);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static Iterator detail_getDetailEntries(Dom d) {
      Locale l = d.locale();
      Detail detail = (Detail)d;
      if (l.noSync()) {
         l.enter();

         Iterator var3;
         try {
            var3 = l._saaj.detail_getDetailEntries(detail);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            Iterator var4;
            try {
               var4 = l._saaj.detail_getDetailEntries(detail);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static void _soapPart_removeAllMimeHeaders(Dom d) {
      Locale l = d.locale();
      SOAPPart sp = (SOAPPart)d;
      if (l.noSync()) {
         l.enter();

         try {
            l._saaj.soapPart_removeAllMimeHeaders(sp);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               l._saaj.soapPart_removeAllMimeHeaders(sp);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static void _soapPart_removeMimeHeader(Dom d, String name) {
      Locale l = d.locale();
      SOAPPart sp = (SOAPPart)d;
      if (l.noSync()) {
         l.enter();

         try {
            l._saaj.soapPart_removeMimeHeader(sp, name);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               l._saaj.soapPart_removeMimeHeader(sp, name);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static Iterator _soapPart_getAllMimeHeaders(Dom d) {
      Locale l = d.locale();
      SOAPPart sp = (SOAPPart)d;
      if (l.noSync()) {
         l.enter();

         Iterator var3;
         try {
            var3 = l._saaj.soapPart_getAllMimeHeaders(sp);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            Iterator var4;
            try {
               var4 = l._saaj.soapPart_getAllMimeHeaders(sp);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static SOAPEnvelope _soapPart_getEnvelope(Dom d) {
      Locale l = d.locale();
      SOAPPart sp = (SOAPPart)d;
      if (l.noSync()) {
         l.enter();

         SOAPEnvelope var3;
         try {
            var3 = l._saaj.soapPart_getEnvelope(sp);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            SOAPEnvelope var4;
            try {
               var4 = l._saaj.soapPart_getEnvelope(sp);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static Source _soapPart_getContent(Dom d) {
      Locale l = d.locale();
      SOAPPart sp = (SOAPPart)d;
      if (l.noSync()) {
         l.enter();

         Source var3;
         try {
            var3 = l._saaj.soapPart_getContent(sp);
         } finally {
            l.exit();
         }

         return var3;
      } else {
         synchronized(l) {
            l.enter();

            Source var4;
            try {
               var4 = l._saaj.soapPart_getContent(sp);
            } finally {
               l.exit();
            }

            return var4;
         }
      }
   }

   public static void _soapPart_setContent(Dom d, Source source) {
      Locale l = d.locale();
      SOAPPart sp = (SOAPPart)d;
      if (l.noSync()) {
         l.enter();

         try {
            l._saaj.soapPart_setContent(sp, source);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               l._saaj.soapPart_setContent(sp, source);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static String[] _soapPart_getMimeHeader(Dom d, String name) {
      Locale l = d.locale();
      SOAPPart sp = (SOAPPart)d;
      if (l.noSync()) {
         l.enter();

         String[] var4;
         try {
            var4 = l._saaj.soapPart_getMimeHeader(sp, name);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            String[] var5;
            try {
               var5 = l._saaj.soapPart_getMimeHeader(sp, name);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static void _soapPart_addMimeHeader(Dom d, String name, String value) {
      Locale l = d.locale();
      SOAPPart sp = (SOAPPart)d;
      if (l.noSync()) {
         l.enter();

         try {
            l._saaj.soapPart_addMimeHeader(sp, name, value);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               l._saaj.soapPart_addMimeHeader(sp, name, value);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static void _soapPart_setMimeHeader(Dom d, String name, String value) {
      Locale l = d.locale();
      SOAPPart sp = (SOAPPart)d;
      if (l.noSync()) {
         l.enter();

         try {
            l._saaj.soapPart_setMimeHeader(sp, name, value);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               l._saaj.soapPart_setMimeHeader(sp, name, value);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static Iterator _soapPart_getMatchingMimeHeaders(Dom d, String[] names) {
      Locale l = d.locale();
      SOAPPart sp = (SOAPPart)d;
      if (l.noSync()) {
         l.enter();

         Iterator var4;
         try {
            var4 = l._saaj.soapPart_getMatchingMimeHeaders(sp, names);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            Iterator var5;
            try {
               var5 = l._saaj.soapPart_getMatchingMimeHeaders(sp, names);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static Iterator _soapPart_getNonMatchingMimeHeaders(Dom d, String[] names) {
      Locale l = d.locale();
      SOAPPart sp = (SOAPPart)d;
      if (l.noSync()) {
         l.enter();

         Iterator var4;
         try {
            var4 = l._saaj.soapPart_getNonMatchingMimeHeaders(sp, names);
         } finally {
            l.exit();
         }

         return var4;
      } else {
         synchronized(l) {
            l.enter();

            Iterator var5;
            try {
               var5 = l._saaj.soapPart_getNonMatchingMimeHeaders(sp, names);
            } finally {
               l.exit();
            }

            return var5;
         }
      }
   }

   public static void saajCallback_setSaajData(Dom d, Object o) {
      Locale l = d.locale();
      if (l.noSync()) {
         l.enter();

         try {
            impl_saajCallback_setSaajData(d, o);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               impl_saajCallback_setSaajData(d, o);
            } finally {
               l.exit();
            }
         }
      }

   }

   public static void impl_saajCallback_setSaajData(Dom d, Object o) {
      Locale l = d.locale();
      Cur c = l.tempCur();
      c.moveToDom(d);
      SaajData sd = null;
      if (o != null) {
         sd = (SaajData)c.getBookmark(SaajData.class);
         if (sd == null) {
            sd = new SaajData();
         }

         sd._obj = o;
      }

      c.setBookmark(SaajData.class, sd);
      c.release();
   }

   public static Object saajCallback_getSaajData(Dom d) {
      Locale l = d.locale();
      if (l.noSync()) {
         l.enter();

         Object var2;
         try {
            var2 = impl_saajCallback_getSaajData(d);
         } finally {
            l.exit();
         }

         return var2;
      } else {
         synchronized(l) {
            l.enter();

            Object var3;
            try {
               var3 = impl_saajCallback_getSaajData(d);
            } finally {
               l.exit();
            }

            return var3;
         }
      }
   }

   public static Object impl_saajCallback_getSaajData(Dom d) {
      Locale l = d.locale();
      Cur c = l.tempCur();
      c.moveToDom(d);
      SaajData sd = (SaajData)c.getBookmark(SaajData.class);
      Object o = sd == null ? null : sd._obj;
      c.release();
      return o;
   }

   public static Element saajCallback_createSoapElement(Dom d, QName name, QName parentName) {
      Locale l = d.locale();
      Dom e;
      if (l.noSync()) {
         l.enter();

         try {
            e = impl_saajCallback_createSoapElement(d, name, parentName);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               e = impl_saajCallback_createSoapElement(d, name, parentName);
            } finally {
               l.exit();
            }
         }
      }

      return (Element)e;
   }

   public static Dom impl_saajCallback_createSoapElement(Dom d, QName name, QName parentName) {
      Cur c = d.locale().tempCur();
      c.createElement(name, parentName);
      Dom e = c.getDom();
      c.release();
      return e;
   }

   public static Element saajCallback_importSoapElement(Dom d, Element elem, boolean deep, QName parentName) {
      Locale l = d.locale();
      Dom e;
      if (l.noSync()) {
         l.enter();

         try {
            e = impl_saajCallback_importSoapElement(d, elem, deep, parentName);
         } finally {
            l.exit();
         }
      } else {
         synchronized(l) {
            l.enter();

            try {
               e = impl_saajCallback_importSoapElement(d, elem, deep, parentName);
            } finally {
               l.exit();
            }
         }
      }

      return (Element)e;
   }

   public static Dom impl_saajCallback_importSoapElement(Dom d, Element elem, boolean deep, QName parentName) {
      throw new RuntimeException("Not impl");
   }

   public static Text saajCallback_ensureSoapTextNode(Dom d) {
      Locale l = d.locale();
      if (l.noSync()) {
         l.enter();

         Text var2;
         try {
            var2 = impl_saajCallback_ensureSoapTextNode(d);
         } finally {
            l.exit();
         }

         return var2;
      } else {
         synchronized(l) {
            l.enter();

            Text var3;
            try {
               var3 = impl_saajCallback_ensureSoapTextNode(d);
            } finally {
               l.exit();
            }

            return var3;
         }
      }
   }

   public static Text impl_saajCallback_ensureSoapTextNode(Dom d) {
      return null;
   }

   private static class SaajData {
      Object _obj;

      private SaajData() {
      }

      // $FF: synthetic method
      SaajData(Object x0) {
         this();
      }
   }

   static class SaajCdataNode extends CdataNode implements org.apache.xmlbeans.impl.soap.Text {
      public SaajCdataNode(Locale l) {
         super(l);
      }

      public boolean isComment() {
         return DomImpl._soapText_isComment(this);
      }

      public void detachNode() {
         DomImpl._soapNode_detachNode(this);
      }

      public void recycleNode() {
         DomImpl._soapNode_recycleNode(this);
      }

      public String getValue() {
         return DomImpl._soapNode_getValue(this);
      }

      public void setValue(String value) {
         DomImpl._soapNode_setValue(this, value);
      }

      public SOAPElement getParentElement() {
         return DomImpl._soapNode_getParentElement(this);
      }

      public void setParentElement(SOAPElement p) {
         DomImpl._soapNode_setParentElement(this, p);
      }
   }

   static class SaajTextNode extends TextNode implements org.apache.xmlbeans.impl.soap.Text {
      SaajTextNode(Locale l) {
         super(l);
      }

      public boolean isComment() {
         return DomImpl._soapText_isComment(this);
      }

      public void detachNode() {
         DomImpl._soapNode_detachNode(this);
      }

      public void recycleNode() {
         DomImpl._soapNode_recycleNode(this);
      }

      public String getValue() {
         return DomImpl._soapNode_getValue(this);
      }

      public void setValue(String value) {
         DomImpl._soapNode_setValue(this, value);
      }

      public SOAPElement getParentElement() {
         return DomImpl._soapNode_getParentElement(this);
      }

      public void setParentElement(SOAPElement p) {
         DomImpl._soapNode_setParentElement(this, p);
      }
   }

   static class CdataNode extends TextNode implements CDATASection {
      CdataNode(Locale l) {
         super(l);
      }

      public int nodeType() {
         return 4;
      }

      public String name() {
         return "#cdata-section";
      }
   }

   static class TextNode extends CharNode implements Text {
      TextNode(Locale l) {
         super(l);
      }

      public int nodeType() {
         return 3;
      }

      public String name() {
         return "#text";
      }

      public Text splitText(int offset) {
         return DomImpl._text_splitText(this, offset);
      }

      public String getWholeText() {
         return DomImpl._text_getWholeText(this);
      }

      public boolean isElementContentWhitespace() {
         return DomImpl._text_isElementContentWhitespace(this);
      }

      public Text replaceWholeText(String content) {
         return DomImpl._text_replaceWholeText(this, content);
      }
   }

   abstract static class CharNode implements Dom, Node, CharacterData {
      private Locale _locale;
      CharNode _next;
      CharNode _prev;
      private Object _src;
      int _off;
      int _cch;

      public CharNode(Locale l) {
         assert l != null;

         this._locale = l;
      }

      public QName getQName() {
         return null;
      }

      public Locale locale() {
         assert this.isValid();

         return this._locale == null ? ((Dom)this._src).locale() : this._locale;
      }

      public void setChars(Object src, int off, int cch) {
         assert CharUtil.isValid(src, off, cch);

         assert this._locale != null || this._src instanceof Dom;

         if (this._locale == null) {
            this._locale = ((Dom)this._src).locale();
         }

         this._src = src;
         this._off = off;
         this._cch = cch;
      }

      public Dom getDom() {
         assert this.isValid();

         return this._src instanceof Dom ? (Dom)this._src : null;
      }

      public void setDom(Dom d) {
         assert d != null;

         this._src = d;
         this._locale = null;
      }

      public Cur tempCur() {
         assert this.isValid();

         if (!(this._src instanceof Dom)) {
            return null;
         } else {
            Cur c = this.locale().tempCur();
            c.moveToCharNode(this);
            return c;
         }
      }

      private boolean isValid() {
         if (this._src instanceof Dom) {
            return this._locale == null;
         } else {
            return this._locale != null;
         }
      }

      public static boolean isOnList(CharNode nodes, CharNode node) {
         assert node != null;

         for(CharNode cn = nodes; cn != null; cn = cn._next) {
            if (cn == node) {
               return true;
            }
         }

         return false;
      }

      public static CharNode remove(CharNode nodes, CharNode node) {
         assert isOnList(nodes, node);

         if (nodes == node) {
            nodes = node._next;
         } else {
            node._prev._next = node._next;
         }

         if (node._next != null) {
            node._next._prev = node._prev;
         }

         node._prev = node._next = null;
         return nodes;
      }

      public static CharNode insertNode(CharNode nodes, CharNode newNode, CharNode before) {
         assert !isOnList(nodes, newNode);

         assert before == null || isOnList(nodes, before);

         assert newNode != null;

         assert newNode._prev == null && newNode._next == null;

         if (nodes == null) {
            assert before == null;

            nodes = newNode;
         } else if (nodes == before) {
            nodes._prev = newNode;
            newNode._next = nodes;
            nodes = newNode;
         } else {
            CharNode n;
            for(n = nodes; n._next != before; n = n._next) {
            }

            if ((newNode._next = n._next) != null) {
               n._next._prev = newNode;
            }

            newNode._prev = n;
            n._next = newNode;
         }

         return nodes;
      }

      public static CharNode appendNode(CharNode nodes, CharNode newNode) {
         return insertNode(nodes, newNode, (CharNode)null);
      }

      public static CharNode appendNodes(CharNode nodes, CharNode newNodes) {
         assert newNodes != null;

         assert newNodes._prev == null;

         if (nodes == null) {
            return newNodes;
         } else {
            CharNode n;
            for(n = nodes; n._next != null; n = n._next) {
            }

            n._next = newNodes;
            newNodes._prev = n;
            return nodes;
         }
      }

      public static CharNode copyNodes(CharNode nodes, Object newSrc) {
         CharNode newNodes = null;

         for(CharNode n = null; nodes != null; nodes = nodes._next) {
            Object newNode;
            if (nodes instanceof TextNode) {
               newNode = nodes.locale().createTextNode();
            } else {
               newNode = nodes.locale().createCdataNode();
            }

            ((CharNode)newNode).setChars(newSrc, nodes._off, nodes._cch);
            if (newNodes == null) {
               newNodes = newNode;
            }

            if (n != null) {
               ((CharNode)n)._next = (CharNode)newNode;
               ((CharNode)newNode)._prev = (CharNode)n;
            }

            n = newNode;
         }

         return (CharNode)newNodes;
      }

      public boolean nodeCanHavePrefixUri() {
         return false;
      }

      public boolean isNodeAftertext() {
         assert this._src instanceof Xobj : "this method is to only be used for nodes backed up by Xobjs";

         Xobj src = (Xobj)this._src;
         return src._charNodesValue == null ? true : (src._charNodesAfter == null ? false : isOnList(src._charNodesAfter, this));
      }

      public void dump(PrintStream o, Object ref) {
         if (this._src instanceof Dom) {
            ((Dom)this._src).dump(o, ref);
         } else {
            o.println("Lonely CharNode: \"" + CharUtil.getString(this._src, this._off, this._cch) + "\"");
         }

      }

      public void dump(PrintStream o) {
         this.dump(o, this);
      }

      public void dump() {
         this.dump(System.out);
      }

      public Node appendChild(Node newChild) {
         return DomImpl._node_appendChild(this, newChild);
      }

      public Node cloneNode(boolean deep) {
         return DomImpl._node_cloneNode(this, deep);
      }

      public NamedNodeMap getAttributes() {
         return null;
      }

      public NodeList getChildNodes() {
         return DomImpl._emptyNodeList;
      }

      public Node getParentNode() {
         return DomImpl._node_getParentNode(this);
      }

      public Node removeChild(Node oldChild) {
         return DomImpl._node_removeChild(this, oldChild);
      }

      public Node getFirstChild() {
         return null;
      }

      public Node getLastChild() {
         return null;
      }

      public String getLocalName() {
         return DomImpl._node_getLocalName(this);
      }

      public String getNamespaceURI() {
         return DomImpl._node_getNamespaceURI(this);
      }

      public Node getNextSibling() {
         return DomImpl._node_getNextSibling(this);
      }

      public String getNodeName() {
         return DomImpl._node_getNodeName(this);
      }

      public short getNodeType() {
         return DomImpl._node_getNodeType(this);
      }

      public String getNodeValue() {
         return DomImpl._node_getNodeValue(this);
      }

      public Document getOwnerDocument() {
         return DomImpl._node_getOwnerDocument(this);
      }

      public String getPrefix() {
         return DomImpl._node_getPrefix(this);
      }

      public Node getPreviousSibling() {
         return DomImpl._node_getPreviousSibling(this);
      }

      public boolean hasAttributes() {
         return false;
      }

      public boolean hasChildNodes() {
         return false;
      }

      public Node insertBefore(Node newChild, Node refChild) {
         return DomImpl._node_insertBefore(this, newChild, refChild);
      }

      public boolean isSupported(String feature, String version) {
         return DomImpl._node_isSupported(this, feature, version);
      }

      public void normalize() {
         DomImpl._node_normalize(this);
      }

      public Node replaceChild(Node newChild, Node oldChild) {
         return DomImpl._node_replaceChild(this, newChild, oldChild);
      }

      public void setNodeValue(String nodeValue) {
         DomImpl._node_setNodeValue(this, nodeValue);
      }

      public void setPrefix(String prefix) {
         DomImpl._node_setPrefix(this, prefix);
      }

      public Object getUserData(String key) {
         return DomImpl._node_getUserData(this, key);
      }

      public Object setUserData(String key, Object data, UserDataHandler handler) {
         return DomImpl._node_setUserData(this, key, data, handler);
      }

      public Object getFeature(String feature, String version) {
         return DomImpl._node_getFeature(this, feature, version);
      }

      public boolean isEqualNode(Node arg) {
         return DomImpl._node_isEqualNode(this, arg);
      }

      public boolean isSameNode(Node arg) {
         return DomImpl._node_isSameNode(this, arg);
      }

      public String lookupNamespaceURI(String prefix) {
         return DomImpl._node_lookupNamespaceURI(this, prefix);
      }

      public String lookupPrefix(String namespaceURI) {
         return DomImpl._node_lookupPrefix(this, namespaceURI);
      }

      public boolean isDefaultNamespace(String namespaceURI) {
         return DomImpl._node_isDefaultNamespace(this, namespaceURI);
      }

      public void setTextContent(String textContent) {
         DomImpl._node_setTextContent(this, textContent);
      }

      public String getTextContent() {
         return DomImpl._node_getTextContent(this);
      }

      public short compareDocumentPosition(Node other) {
         return DomImpl._node_compareDocumentPosition(this, other);
      }

      public String getBaseURI() {
         return DomImpl._node_getBaseURI(this);
      }

      public void appendData(String arg) {
         DomImpl._characterData_appendData(this, arg);
      }

      public void deleteData(int offset, int count) {
         DomImpl._characterData_deleteData(this, offset, count);
      }

      public String getData() {
         return DomImpl._characterData_getData(this);
      }

      public int getLength() {
         return DomImpl._characterData_getLength(this);
      }

      public void insertData(int offset, String arg) {
         DomImpl._characterData_insertData(this, offset, arg);
      }

      public void replaceData(int offset, int count, String arg) {
         DomImpl._characterData_replaceData(this, offset, count, arg);
      }

      public void setData(String data) {
         DomImpl._characterData_setData(this, data);
      }

      public String substringData(int offset, int count) {
         return DomImpl._characterData_substringData(this, offset, count);
      }
   }

   private static class ElementsByTagNameNSNodeList extends ElementsNodeList {
      private String _uri;
      private String _local;

      ElementsByTagNameNSNodeList(Dom root, String uri, String local) {
         super(root);
         this._uri = uri == null ? "" : uri;
         this._local = local;
      }

      protected boolean match(Dom element) {
         if (!this._uri.equals("*") && !DomImpl._node_getNamespaceURI(element).equals(this._uri)) {
            return false;
         } else {
            return this._local.equals("*") ? true : DomImpl._node_getLocalName(element).equals(this._local);
         }
      }
   }

   private static class ElementsByTagNameNodeList extends ElementsNodeList {
      private String _name;

      ElementsByTagNameNodeList(Dom root, String name) {
         super(root);
         this._name = name;
      }

      protected boolean match(Dom element) {
         return this._name.equals("*") ? true : DomImpl._node_getNodeName(element).equals(this._name);
      }
   }

   private abstract static class ElementsNodeList implements NodeList {
      private Dom _root;
      private Locale _locale;
      private long _version;
      private ArrayList _elements;

      ElementsNodeList(Dom root) {
         assert root.nodeType() == 9 || root.nodeType() == 1;

         this._root = root;
         this._locale = this._root.locale();
         this._version = 0L;
      }

      public int getLength() {
         this.ensureElements();
         return this._elements.size();
      }

      public Node item(int i) {
         this.ensureElements();
         return i >= 0 && i < this._elements.size() ? (Node)this._elements.get(i) : (Node)null;
      }

      private void ensureElements() {
         if (this._version != this._locale.version()) {
            this._version = this._locale.version();
            this._elements = new ArrayList();
            Locale l = this._locale;
            if (l.noSync()) {
               l.enter();

               try {
                  this.addElements(this._root);
               } finally {
                  l.exit();
               }
            } else {
               synchronized(l) {
                  l.enter();

                  try {
                     this.addElements(this._root);
                  } finally {
                     l.exit();
                  }
               }
            }

         }
      }

      private void addElements(Dom node) {
         for(Dom c = DomImpl.firstChild(node); c != null; c = DomImpl.nextSibling(c)) {
            if (c.nodeType() == 1) {
               if (this.match(c)) {
                  this._elements.add(c);
               }

               this.addElements(c);
            }
         }

      }

      protected abstract boolean match(Dom var1);
   }

   private static final class EmptyNodeList implements NodeList {
      private EmptyNodeList() {
      }

      public int getLength() {
         return 0;
      }

      public Node item(int i) {
         return null;
      }

      // $FF: synthetic method
      EmptyNodeList(Object x0) {
         this();
      }
   }

   static class InvalidCharacterError extends DOMException {
      InvalidCharacterError() {
         this("The name contains an invalid character");
      }

      InvalidCharacterError(String message) {
         super((short)5, message);
      }
   }

   static class NotSupportedError extends DOMException {
      NotSupportedError() {
         this("This operation is not supported");
      }

      NotSupportedError(String message) {
         super((short)9, message);
      }
   }

   static class IndexSizeError extends DOMException {
      IndexSizeError() {
         this("Index Size Error");
      }

      IndexSizeError(String message) {
         super((short)1, message);
      }
   }

   static class InuseAttributeError extends DOMException {
      InuseAttributeError() {
         this("Attribute currently in use error");
      }

      InuseAttributeError(String message) {
         super((short)10, message);
      }
   }

   static class NoModificationAllowedErr extends DOMException {
      NoModificationAllowedErr() {
         this("No modification allowed error");
      }

      NoModificationAllowedErr(String message) {
         super((short)7, message);
      }
   }

   static class NamespaceErr extends DOMException {
      NamespaceErr() {
         this("Namespace error");
      }

      NamespaceErr(String message) {
         super((short)14, message);
      }
   }

   static class NotFoundErr extends DOMException {
      NotFoundErr() {
         this("Node not found");
      }

      NotFoundErr(String message) {
         super((short)8, message);
      }
   }

   static class WrongDocumentErr extends DOMException {
      WrongDocumentErr() {
         this("Nodes do not belong to the same document");
      }

      WrongDocumentErr(String message) {
         super((short)4, message);
      }
   }

   static class HierarchyRequestErr extends DOMException {
      HierarchyRequestErr() {
         this("This node isn't allowed there");
      }

      HierarchyRequestErr(String message) {
         super((short)3, message);
      }
   }

   interface Dom {
      Locale locale();

      int nodeType();

      Cur tempCur();

      QName getQName();

      boolean nodeCanHavePrefixUri();

      void dump();

      void dump(PrintStream var1);

      void dump(PrintStream var1, Object var2);
   }
}
