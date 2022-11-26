package weblogic.xml.babel.adapters;

import java.util.List;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.EntityReference;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.ProcessingInstruction;
import weblogic.xml.babel.baseparser.PrefixMapping;
import weblogic.xml.babel.baseparser.SymbolTable;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.events.AttributeImpl;
import weblogic.xml.stream.events.ChangePrefixMappingEvent;
import weblogic.xml.stream.events.CharacterDataEvent;
import weblogic.xml.stream.events.CommentEvent;
import weblogic.xml.stream.events.EndDocumentEvent;
import weblogic.xml.stream.events.EndElementEvent;
import weblogic.xml.stream.events.EndPrefixMappingEvent;
import weblogic.xml.stream.events.Name;
import weblogic.xml.stream.events.ProcessingInstructionEvent;
import weblogic.xml.stream.events.SpaceEvent;
import weblogic.xml.stream.events.StartDocumentEvent;
import weblogic.xml.stream.events.StartElementEvent;
import weblogic.xml.stream.events.StartPrefixMappingEvent;

public class DOMAdapter {
   private boolean debug;
   protected ElementConsumer consumer;
   protected SymbolTable nameSpaceTable;

   public DOMAdapter() {
      this.debug = false;
      this.nameSpaceTable = new SymbolTable();
      this.nameSpaceTable.put("", (String)null);
      this.nameSpaceTable.put("xml", "http://www.w3.org/XML/1998/namespace");
   }

   public DOMAdapter(ElementConsumer consumer) {
      this();
      this.consumer = consumer;
   }

   public void setElementConsumer(ElementConsumer consumer) {
      this.consumer = consumer;
   }

   public ElementConsumer getElementConsumer() {
      return this.consumer;
   }

   public void adapt(Node node) throws XMLStreamException {
      switch (node.getNodeType()) {
         case 1:
            this.adaptElement(node);
            break;
         case 2:
         case 6:
         default:
            throw new XMLStreamException("Unknown DOM Node Type");
         case 3:
         case 4:
            this.adaptText(node);
            break;
         case 5:
            this.adaptReference((EntityReference)node);
            break;
         case 7:
            this.adaptProcessingInstruction((ProcessingInstruction)node);
            break;
         case 8:
            this.adaptComment((Comment)node);
            break;
         case 9:
            Document doc = (Document)node;
            this.adaptDocument(doc);
         case 10:
            break;
         case 11:
            DocumentFragment fragment = (DocumentFragment)node;
            this.adaptDocumentFragment(fragment);
      }

   }

   public void adaptDocument(Document doc) throws XMLStreamException {
      this.consumer.add(new StartDocumentEvent());
      this.adapt(doc.getDocumentElement());
      this.consumer.add(new EndDocumentEvent());
   }

   public void adaptNamespace(Node node, StartElementEvent event, String prefix, String value) {
      Attribute att = weblogic.xml.stream.ElementFactory.createNamespaceAttribute(prefix, value);
      event.addNamespace(att);
      if (prefix == null) {
         prefix = "";
      }

      this.nameSpaceTable.put(prefix, value);
      this.consumer.add(new StartPrefixMappingEvent(prefix, value));
   }

   public void adaptAttribute(Node node, StartElementEvent event) throws XMLStreamException {
      String name = node.getLocalName();
      if (name == null) {
         name = node.getNodeName();
      }

      if (name == null) {
         throw new XMLStreamException("The name of an attribute cannot be null");
      } else if (name.equals("xmlns")) {
         this.adaptNamespace(node, event, (String)null, node.getNodeValue());
      } else {
         String prefix = node.getPrefix();
         if (prefix != null && prefix.equals("xmlns")) {
            this.adaptNamespace(node, event, name, node.getNodeValue());
         } else {
            if ((prefix == null || prefix.equals("")) && "http://www.w3.org/XML/1998/namespace".equals(node.getNamespaceURI())) {
               prefix = "xml";
            }

            event.addAttribute(new AttributeImpl(new Name(node.getNamespaceURI(), name, prefix), node.getNodeValue(), "CDATA"));
         }
      }
   }

   public void adaptElement(Node node) throws XMLStreamException {
      this.nameSpaceTable.openScope();
      String localName = node.getLocalName();
      if (localName == null) {
         localName = node.getNodeName();
      }

      StartElementEvent event = new StartElementEvent(new Name(node.getNamespaceURI(), localName, node.getPrefix()));
      NamedNodeMap a = node.getAttributes();

      for(int i = 0; i < a.getLength(); ++i) {
         Node current = a.item(i);
         this.adaptAttribute(current, event);
      }

      this.consumer.add(event);
      this.recurseChildren(node);
      this.consumer.add(new EndElementEvent(new Name(node.getNamespaceURI(), localName, node.getPrefix())));
      List keys = this.nameSpaceTable.closeScope();
      int i = 0;

      for(int len = keys.size(); i < len; ++i) {
         PrefixMapping prefixMapping = (PrefixMapping)keys.get(i);
         if (prefixMapping.getUri() == null) {
            this.consumer.add(new EndPrefixMappingEvent(prefixMapping.getPrefix()));
         } else {
            this.consumer.add(new ChangePrefixMappingEvent(prefixMapping.getOldUri(), prefixMapping.getUri(), prefixMapping.getPrefix()));
         }
      }

   }

   public void adaptDocumentFragment(DocumentFragment fragment) throws XMLStreamException {
      this.recurseChildren(fragment);
   }

   protected void recurseChildren(Node node) throws XMLStreamException {
      NodeList children = node.getChildNodes();
      if (children != null) {
         for(int i = 0; i < children.getLength(); ++i) {
            this.adapt(children.item(i));
         }
      }

   }

   protected boolean isSpace(char c) {
      return c == ' ' || c == '\t' || c == '\r' || c == '\n';
   }

   public void adaptComment(Comment comment) {
      this.consumer.add(new CommentEvent(comment.getData()));
   }

   public void adaptText(Node node) {
      boolean space = true;
      String value = node.getNodeValue();

      for(int i = 0; i < value.length(); ++i) {
         if (!this.isSpace(value.charAt(i))) {
            space = false;
            break;
         }
      }

      if (space) {
         this.consumer.add(new SpaceEvent(value));
      } else {
         this.consumer.add(new CharacterDataEvent(value));
      }

   }

   public void adaptProcessingInstruction(ProcessingInstruction processingInstruction) {
      this.consumer.add(new ProcessingInstructionEvent(new Name(processingInstruction.getNodeName()), processingInstruction.getData()));
   }

   public void adaptReference(EntityReference reference) throws XMLStreamException {
      this.recurseChildren(reference);
   }
}
