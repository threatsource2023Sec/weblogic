package weblogic.xml.babel.stream;

import java.util.Stack;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import weblogic.utils.Debug;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.AttributeIterator;
import weblogic.xml.stream.ChangePrefixMapping;
import weblogic.xml.stream.CharacterData;
import weblogic.xml.stream.Comment;
import weblogic.xml.stream.EndDocument;
import weblogic.xml.stream.EndElement;
import weblogic.xml.stream.EndPrefixMapping;
import weblogic.xml.stream.EntityReference;
import weblogic.xml.stream.ProcessingInstruction;
import weblogic.xml.stream.Space;
import weblogic.xml.stream.StartDocument;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.StartPrefixMapping;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLStreamException;

public class DOMNodeWriter extends XMLWriter {
   protected Stack nodeParentStack;
   protected Document document;
   protected Node root;
   protected int level;
   private static final String XMLNS_URI = "http://www.w3.org/2000/xmlns/";

   public DOMNodeWriter(Document document) {
      this.document = document;
      this.root = this.document;
      this.init();
   }

   public DOMNodeWriter(Document document, DocumentFragment documentFragment) {
      this.document = document;
      this.root = documentFragment;
      this.init();
   }

   protected void init() {
      this.setWriteHeader(true);
      this.setWriteElementNameSpaces(true);
      this.setWriteAll(false);
      this.setShowNamespaceBindings(false);
      this.setNormalizeWhiteSpace(false);
      this.reset();
   }

   public void reset() {
      this.level = 0;
      this.nodeParentStack = new Stack();
   }

   protected Node getCurrentParent() throws XMLStreamException {
      if (this.nodeParentStack.empty()) {
         throw new XMLStreamException("Null parent");
      } else {
         return (Node)this.nodeParentStack.peek();
      }
   }

   public void addAttributes(AttributeIterator attributes, Element e) throws XMLStreamException {
      while(attributes.hasNext()) {
         Attribute a = attributes.next();

         try {
            e.setAttributeNS(a.getName().getNamespaceUri(), a.getName().getQualifiedName(), a.getValue());
         } catch (DOMException var5) {
            Debug.say("(manoj):e" + e);
            Debug.say("(manoj):a" + a);
            Debug.say("(manoj):domException" + var5);
            throw new XMLStreamException(var5);
         }
      }

   }

   public void addNamespaces(AttributeIterator attributes, Element e) throws XMLStreamException {
      while(attributes.hasNext()) {
         Attribute a = attributes.next();

         try {
            e.setAttributeNS("http://www.w3.org/2000/xmlns/", a.getName().getQualifiedName(), a.getValue());
         } catch (DOMException var5) {
            throw new XMLStreamException(var5);
         }
      }

   }

   public void write(StartElement element) throws XMLStreamException {
      Element newElement = this.document.createElementNS(element.getName().getNamespaceUri(), element.getName().getQualifiedName());
      if (this.writeElementNameSpaces) {
         this.addNamespaces(element.getNamespaces(), newElement);
      }

      this.addAttributes(element.getAttributes(), newElement);
      if (this.nodeParentStack.empty()) {
         DocumentFragment fragment = this.document.createDocumentFragment();
         fragment.appendChild(newElement);
         this.root.appendChild(fragment);
         this.nodeParentStack.push(newElement);
         ++this.level;
      } else {
         this.getCurrentParent().appendChild(newElement);
         this.nodeParentStack.push(newElement);
         ++this.level;
      }

   }

   public void write(EndElement element) throws XMLStreamException {
      --this.level;
      this.nodeParentStack.pop();
   }

   public void write(ProcessingInstruction event) throws XMLStreamException {
      this.getCurrentParent().appendChild(this.document.createProcessingInstruction(event.getTarget(), event.getData()));
   }

   public void write(CharacterData event) throws XMLStreamException {
      if (this.nodeParentStack.empty()) {
         DocumentFragment fragment = this.document.createDocumentFragment();
         fragment.appendChild(this.document.createTextNode(event.getContent()));
         this.root.appendChild(fragment);
      } else {
         this.getCurrentParent().appendChild(this.document.createTextNode(event.getContent()));
      }

   }

   public void write(StartDocument event) throws XMLStreamException {
      this.reset();
      this.nodeParentStack.push(this.document);
   }

   public void write(EndDocument event) throws XMLStreamException {
   }

   public void write(Comment event) throws XMLStreamException {
      this.getCurrentParent().appendChild(this.document.createComment(event.getContent()));
   }

   public void write(StartPrefixMapping mapping) throws XMLStreamException {
   }

   public void write(ChangePrefixMapping mapping) throws XMLStreamException {
   }

   public void write(EndPrefixMapping mapping) throws XMLStreamException {
   }

   public void write(EntityReference reference) throws XMLStreamException {
      this.document.createEntityReference(reference.getName().getQualifiedName());
   }

   public void write(Space space) throws XMLStreamException {
      if (this.level > 0) {
         this.getCurrentParent().appendChild(this.document.createTextNode(space.getContent()));
      }

   }

   public void flush() throws XMLStreamException {
   }

   public Node getDocument() throws XMLStreamException {
      return this.getCurrentParent();
   }

   public boolean write(XMLEvent e) throws XMLStreamException {
      switch (e.getType()) {
         case 2:
            this.write((StartElement)e);
            break;
         case 4:
            this.write((EndElement)e);
            break;
         case 8:
            this.write((ProcessingInstruction)e);
            break;
         case 16:
            this.write((CharacterData)e);
            break;
         case 32:
            this.write((Comment)e);
            break;
         case 64:
            this.write((Space)e);
            break;
         case 128:
            throw new XMLStreamException("Attempt to write a null element.");
         case 256:
            this.write((StartDocument)e);
            break;
         case 512:
            this.write((EndDocument)e);
            break;
         case 1024:
            this.write((StartPrefixMapping)e);
            break;
         case 2048:
            this.write((EndPrefixMapping)e);
            break;
         case 4096:
            this.write((ChangePrefixMapping)e);
            break;
         case 8192:
            this.write((EntityReference)e);
            break;
         default:
            throw new XMLStreamException("Attempt to write unknown element [" + e.getType() + "]");
      }

      return true;
   }
}
