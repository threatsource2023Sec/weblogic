package weblogic.xml.babel.stream;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.xml.babel.baseparser.SAXElementFactory;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.AttributeIterator;
import weblogic.xml.stream.ChangePrefixMapping;
import weblogic.xml.stream.Comment;
import weblogic.xml.stream.ElementFactory;
import weblogic.xml.stream.EndElement;
import weblogic.xml.stream.EndPrefixMapping;
import weblogic.xml.stream.ProcessingInstruction;
import weblogic.xml.stream.Space;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.StartPrefixMapping;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.events.StartElementEvent;

public class CanonicalWriter extends XMLWriter {
   private boolean writeComments;
   protected boolean writeAugmented;
   protected Set augmentedElementTracks;
   private ScopeManager namespaces;
   private boolean beforeDocumentElement;
   private Map externalNS;
   public static final String XML_PREFIX_NS = "http://www.w3.org/XML/1998/namespace";

   public CanonicalWriter(Writer writer) {
      this.writeComments = true;
      this.writeAugmented = false;
      this.augmentedElementTracks = null;
      this.namespaces = new ScopeManager();
      this.externalNS = null;
      this.writeAll = false;
      this.writeHeader = false;
      this.writer = writer;
      this.beforeDocumentElement = true;
   }

   public CanonicalWriter(Writer writer, Map externalNamespaces) {
      this(writer);
      this.externalNS = externalNamespaces;
   }

   public void setWriteComments(boolean b) {
      this.writeComments = b;
   }

   public void setWriteAugmented(boolean b) {
      this.writeAugmented = b;
   }

   public void setAugmentedElementTracks(Set augmentedElementTracks) {
      this.augmentedElementTracks = augmentedElementTracks;
   }

   private void checkLevel() throws XMLStreamException {
      if (this.elementLevel == 0) {
         this.EOL();
      }

   }

   private void EOL() throws XMLStreamException {
      this.write('\n');
   }

   protected void writeCharacters(String characters, boolean isAttributeValue) throws XMLStreamException {
      String result = CanonicalUtils.normalizeCharacters(characters, isAttributeValue);
      if (result != null) {
         this.write((String)result);
      }

   }

   public void write(StartPrefixMapping mapping) throws XMLStreamException {
   }

   public void write(ChangePrefixMapping mapping) throws XMLStreamException {
   }

   private String getPrefix(String prefix) {
      return prefix.equals("") ? "xmlns" : prefix;
   }

   public void write(EndPrefixMapping mapping) throws XMLStreamException {
   }

   public ScopeManager getScopedNamespaces() {
      return this.namespaces;
   }

   public Map getExternalNamespaces() {
      return this.externalNS != null ? this.externalNS : Collections.EMPTY_MAP;
   }

   public void writeNamespaces(AttributeIterator nameSpaces) throws XMLStreamException {
      while(nameSpaces.hasNext()) {
         Attribute a = nameSpaces.next();
         this.namespaces.checkPrefixMap(a.getName().getLocalName(), a.getValue());
         if (this.namespaces.needToWriteNS(a.getName().getLocalName())) {
            this.write((String)" ");
            this.write((Attribute)a);
            this.namespaces.wroteNS(a.getName().getLocalName());
         }
      }

   }

   public void incrementLevel() {
      this.beforeDocumentElement = false;
      ++this.elementLevel;
   }

   public void write(StartElement element) throws XMLStreamException {
      this.incrementLevel();
      this.namespaces.openScope();
      this.write('<');
      this.write((XMLName)element.getName());
      if (this.externalNS != null) {
         element = this.declareParentNamespaces(element, this.externalNS);
         this.externalNS = null;
      }

      if (this.writeAugmented && this.augmentedElementTracks.contains(element.getName().getQualifiedName())) {
         this.writeEmptyDefaultNS(element.getNamespaces());
      }

      this.writeNamespaces(CanonicalUtils.sortNamespaces(element.getNamespaces()));
      this.write((AttributeIterator)CanonicalUtils.sortAttributes(element.getAttributes()));
      this.write('>');
   }

   public void write(EndElement element) throws XMLStreamException {
      this.namespaces.closeScope();
      super.write(element);
   }

   public void write(Comment event) throws XMLStreamException {
      if (this.writeComments) {
         if (!this.beforeDocumentElement) {
            this.checkLevel();
            super.write(event);
         } else {
            super.write(event);
            this.checkLevel();
         }

      }
   }

   public void write(ProcessingInstruction event) throws XMLStreamException {
      if (!this.beforeDocumentElement) {
         this.checkLevel();
         super.write(event);
      } else {
         super.write(event);
         this.checkLevel();
      }

   }

   public void setWriteHeader(boolean val) {
      this.writeHeader = false;
   }

   public void write(Space space) throws XMLStreamException {
      if (this.elementLevel > 0 && space.hasContent()) {
         this.write((String)space.getContent());
      }

   }

   public StartElement declareParentNamespaces(StartElement e, Map namespaces) {
      StartElementEvent sev = new StartElementEvent(e);
      HashSet declared = new HashSet();
      AttributeIterator ai = sev.getNamespaces();

      while(ai.hasNext()) {
         Attribute ns = ai.next();
         declared.add(ns.getValue());
      }

      if (namespaces != null) {
         Iterator iter = namespaces.entrySet().iterator();

         while(iter.hasNext()) {
            Map.Entry entry = (Map.Entry)iter.next();
            String uri = (String)entry.getValue();
            if (!declared.contains(uri) && !uri.equals("http://www.w3.org/XML/1998/namespace")) {
               String prefix = (String)entry.getKey();
               Attribute newNS;
               if (prefix.startsWith("xml:")) {
                  newNS = ElementFactory.createAttribute(prefix, uri);
                  sev.addAttribute(newNS);
               } else {
                  if (prefix.equals("")) {
                     prefix = null;
                  }

                  newNS = ElementFactory.createNamespaceAttribute(prefix, uri);
                  declared.add(uri);
                  sev.addNamespace(newNS);
               }
            }
         }
      }

      return sev;
   }

   public void writeEmptyDefaultNS(AttributeIterator attrs) throws XMLStreamException {
      boolean writeEmptyDefaultNS = true;

      while(attrs.hasNext()) {
         Attribute attr = attrs.next();
         if (attr.getName().getPrefix() == null && attr.getValue() != null && !attr.getValue().equals("")) {
            writeEmptyDefaultNS = false;
            break;
         }
      }

      String defaultNamespaceURIInOrUnderScope = this.namespaces.getNamespaceURI("");
      if (writeEmptyDefaultNS && (defaultNamespaceURIInOrUnderScope == null || defaultNamespaceURIInOrUnderScope.equals(""))) {
         this.writeEmptyDefaultNS();
      }

   }

   public void writeEmptyDefaultNS() throws XMLStreamException {
      this.write((String)" ");
      this.write((Attribute)ElementFactory.createNamespaceAttribute((String)null, ""));
   }

   public static void main(String[] args) throws Exception {
      XMLWriter writer = new CanonicalWriter(new OutputStreamWriter(new FileOutputStream("out.xml"), "utf-8"));
      XMLInputStreamBase root = new XMLInputStreamBase();
      root.openValidating(SAXElementFactory.createInputSource(args[0]));
      XMLOutputStreamBase output = new XMLOutputStreamBase(writer);
      output.add((XMLInputStream)root);
      output.flush();
   }
}
