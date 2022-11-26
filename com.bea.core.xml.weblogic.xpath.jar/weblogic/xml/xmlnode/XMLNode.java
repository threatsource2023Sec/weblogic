package weblogic.xml.xmlnode;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.xml.sax.InputSource;
import weblogic.utils.StackTraceUtils;
import weblogic.xml.babel.stream.XMLInputStreamBase;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.AttributeIterator;
import weblogic.xml.stream.CharacterData;
import weblogic.xml.stream.ElementFactory;
import weblogic.xml.stream.EndElement;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLName;
import weblogic.xml.stream.XMLOutputStream;
import weblogic.xml.stream.XMLOutputStreamFactory;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.stream.events.AttributeImpl;
import weblogic.xml.stream.events.CharacterDataEvent;
import weblogic.xml.stream.events.EndElementEvent;
import weblogic.xml.stream.events.Name;
import weblogic.xml.stream.events.SpaceEvent;
import weblogic.xml.stream.events.StartElementEvent;
import weblogic.xml.stream.util.TypeFilter;
import weblogic.xml.stream.util.XMLInputStreamFilterBase;
import weblogic.xml.util.LLNamespaceMap;

public class XMLNode implements Serializable {
   private static final EmptyIterator emptyIterator = new EmptyIterator();
   private static final boolean debug = false;
   protected XMLNode parent;
   protected XMLName name;
   protected String href;
   protected String id;
   protected boolean isSpace = false;
   private ArrayList children;
   private Map attributes;
   private Map globalNamespaceMap;
   private ReferenceMap hrefs;
   private String currentLocation = null;
   private static final String IGNORE_WHITE_SPACE_PROP = "weblogic.webservice.soap.ignoreWhiteSpace";
   private static final boolean IGNORE_WHITE_SPACE = getIgnoreWhiteSpaceProp();

   private static boolean getIgnoreWhiteSpaceProp() {
      try {
         return Boolean.getBoolean("weblogic.webservice.soap.ignoreWhiteSpace");
      } catch (SecurityException var1) {
         return false;
      }
   }

   public XMLNode() {
   }

   public XMLNode(String s) {
      this.currentLocation = s;
   }

   public XMLNode(XMLName name) {
      this.name = name;
   }

   public void setParent(XMLNode parent) {
      this.parent = parent;
   }

   public XMLNode setText(String text) {
      (new Error("This method is not supported")).printStackTrace();
      this.addText(text);
      return this;
   }

   public void setHref(String ref) {
      this.href = ref;
   }

   public void setId(String id) {
      this.id = id;
   }

   public void setReferenceMap(ReferenceMap map) {
      this.hrefs = map;
   }

   public void setGlobalNamespaceMap(Map map) {
      ((LLNamespaceMap)this.getGlobalNamespaceMap()).setPredecessor((LLNamespaceMap)map);
   }

   public void setName(XMLName name) {
      this.name = name;
   }

   public void setName(String name, String prefix, String namespace) {
      this.setName(this.createXMLName(name, prefix, namespace));
   }

   public XMLNode getParent() {
      return this.parent;
   }

   public XMLNode getChild(String name, String namespace) {
      Iterator it = this.getChildren();

      XMLNode child;
      XMLName xmlName;
      do {
         do {
            do {
               if (!it.hasNext()) {
                  return null;
               }

               child = (XMLNode)it.next();
            } while(child.isTextNode());

            xmlName = child.getName();
         } while(!name.equals(xmlName.getLocalName()));
      } while(namespace != null && !namespace.equals(xmlName.getNamespaceUri()));

      return child;
   }

   public boolean isTextNode() {
      return false;
   }

   public boolean isEndNode() {
      return false;
   }

   public String getHref() {
      return this.href;
   }

   public String getId() {
      return this.id;
   }

   public ReferenceMap getReferenceMap() {
      return this.hrefs();
   }

   public Map getGlobalNamespaceMap() {
      if (this.globalNamespaceMap == null) {
         HashMap map = new HashMap();
         map.put("xml", "http://www.w3.org/XML/1998/namespace");
         LLNamespaceMap head = new LLNamespaceMap(map, (LLNamespaceMap)null);
         this.globalNamespaceMap = new LLNamespaceMap(new HashMap(), head);
      }

      return this.globalNamespaceMap;
   }

   public String getAttribute(XMLName name) {
      return this.attributes == null ? null : (String)this.attributes.get(name);
   }

   public String getAttribute(String name, String namespace) {
      if (this.attributes == null) {
         return null;
      } else {
         Iterator it = this.getNodeAttributes();

         XMLName xmlName;
         do {
            do {
               if (!it.hasNext()) {
                  return null;
               }

               xmlName = (XMLName)it.next();
            } while(!name.equals(xmlName.getLocalName()));
         } while(namespace != null && !namespace.equals(xmlName.getNamespaceUri()));

         return this.getAttribute(xmlName);
      }
   }

   public Iterator getNodeAttributes() {
      return this.attributes().keySet().iterator();
   }

   public String getNamespaceURI(String prefix) {
      return (String)this.getGlobalNamespaceMap().get(prefix);
   }

   public Iterator getNamespacePrefixes() {
      return (Iterator)(this.namespaceMap() == null ? emptyIterator : this.namespaceMap().keySet().iterator());
   }

   public XMLName getName() {
      return this.name;
   }

   public Iterator getChildren() {
      return (Iterator)(this.children == null ? emptyIterator : this.children.iterator());
   }

   public Iterator getChildren(String name, String namespace) {
      ArrayList list = new ArrayList();
      Iterator it = this.getChildren();

      while(true) {
         XMLNode child;
         XMLName xmlName;
         do {
            do {
               do {
                  if (!it.hasNext()) {
                     return list.iterator();
                  }

                  child = (XMLNode)it.next();
               } while(child.isTextNode());

               xmlName = child.getName();
            } while(!name.equals(xmlName.getLocalName()));
         } while(namespace != null && !namespace.equals(xmlName.getNamespaceUri()));

         list.add(child);
      }
   }

   public Iterator getChildren(XMLName name) {
      ArrayList list = new ArrayList();
      Iterator it = this.getChildren();

      while(it.hasNext()) {
         XMLNode node = (XMLNode)it.next();
         if (name.equals(node.getName())) {
            list.add(node);
         }
      }

      return list.iterator();
   }

   public String getText() {
      String ret = "";
      Iterator it = this.getChildren();

      while(it.hasNext()) {
         XMLNode child = (XMLNode)it.next();
         if (child instanceof XMLTextNode) {
            ret = ret + child.getText();
         }
      }

      if (ret.equals("")) {
         return null;
      } else {
         return ret;
      }
   }

   public void detachNode() {
      this.parent.removeChild(this);
      this.parent = null;
   }

   public void recycleNode() {
   }

   public XMLInputStream stream() {
      XMLNodeInputStream stream = new XMLNodeInputStream(this);
      stream.setReferenceResolver(this.hrefs());
      return stream;
   }

   public void removeChild(XMLNode node) {
      if (this.children != null) {
         int location = this.children.indexOf(node);
         if (location != -1) {
            this.children.remove(location);
         }

      }
   }

   public XMLNode addChild(XMLName name) {
      XMLNode node = this.createChild(name);
      node.name = name;
      this.addChild(node);
      return node;
   }

   public XMLNode insertChild(XMLNode node, int index) {
      if (this.children == null) {
         this.children = new ArrayList();
      }

      if (node.parent != null) {
         node.inheritNamespace();
      }

      node.parent = this;
      this.children.add(index, node);
      return node;
   }

   public XMLNode addChild(XMLNode node) {
      if (this.children == null) {
         this.children = new ArrayList();
      }

      if (node.name != null) {
         node.setGlobalNamespaceMap(this.getGlobalNamespaceMap());
         node.setReferenceMap(this.getReferenceMap());
      }

      if (node.parent != null) {
         node.inheritNamespace();
      }

      node.parent = this;
      node.addNamespaceIfNeeded();
      this.children.add(node);
      return node;
   }

   public void inheritNamespace() {
      for(XMLNode parent = this.getParent(); parent != null; parent = parent.getParent()) {
         Iterator it = parent.namespaceMap().entrySet().iterator();

         while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String name = (String)entry.getKey();
            String value = (String)entry.getValue();
            if (!this.namespaceMap().containsKey(name)) {
               this.addNamespace(name, value);
            }
         }
      }

   }

   public XMLNode addChild(String localName) {
      return this.addChild(this.createXMLName(localName, (String)null, (String)null));
   }

   public XMLNode addChild(String localName, String prefix) {
      return this.addChild(this.createXMLName(localName, prefix, (String)null));
   }

   public XMLNode addChild(String localName, String prefix, String uri) {
      return this.addChild(this.createXMLName(localName, prefix, uri));
   }

   public XMLNode addText(String text) {
      XMLNode node = this.createTextChild(text);
      if (this.isSpace) {
         node.isSpace = true;
      }

      return this.addChild(node);
   }

   public XMLNode addAttribute(XMLName name, String value) {
      if (value == null) {
         throw new IllegalArgumentException("attribute can not have null value:" + name);
      } else if ("xmlns".equals(name.getPrefix())) {
         throw new IllegalArgumentException("This is not an attribute, it is a namespace:" + name);
      } else {
         this.attributes().put(name, value);
         return this;
      }
   }

   public XMLNode addAttribute(String localName, String prefix, String uri, String value) {
      return this.addAttribute(this.createXMLName(localName, prefix, uri), value);
   }

   public XMLNode addNamespace(String prefix, String uri) {
      this.getGlobalNamespaceMap().put(prefix, uri);
      return this;
   }

   public boolean removeAttribute(XMLName name) {
      return this.attributes().remove(name) != null;
   }

   public boolean removeNamespace(String prefix) {
      if (this.namespaceMap() == null) {
         return false;
      } else {
         return this.namespaceMap().remove(prefix) != null;
      }
   }

   public void removeTextNodes() {
      Iterator it = this.getChildren();

      while(it.hasNext()) {
         if (it.next() instanceof XMLTextNode) {
            it.remove();
         }
      }

   }

   public XMLNodeIterator iterator() {
      return new XMLNodeIterator(this);
   }

   public void write(XMLOutputStream xos) throws XMLStreamException {
      xos.add(this.createStartElement());
      this.writeChildren(xos);
      xos.add(ElementFactory.createEndElement(this.name.getNamespaceUri(), this.name.getLocalName(), this.name.getPrefix()));
   }

   public void read(InputStream in) throws IOException {
      this.read(in, IGNORE_WHITE_SPACE);
   }

   public void read(InputStream in, boolean ignoreSpace) throws IOException {
      try {
         XMLInputStreamBase stream = new XMLInputStreamBase();
         stream.open(new InputSource(in));
         this.readInternal(stream, ignoreSpace);
      } catch (XMLStreamException var4) {
         throw new IOException("failed to create xml input stream:" + var4);
      }
   }

   private void readInternal(XMLInputStream stream, boolean ignoreSpace) throws IOException {
      try {
         TypeFilter filter;
         if (ignoreSpace) {
            filter = new TypeFilter(22);
         } else {
            filter = new TypeFilter(86);
         }

         XMLInputStreamFilterBase filterStream = new XMLInputStreamFilterBase();
         stream.skip(2);
         filterStream.setParent(stream);
         filterStream.setFilter(filter);
         this.read((XMLInputStream)filterStream);
      } catch (XMLStreamException var5) {
         throw new IOException("failed to create xml input stream:" + var5);
      }
   }

   void readInternal(XMLInputStream stream) throws IOException {
      this.readInternal(stream, IGNORE_WHITE_SPACE);
   }

   public void read(XMLInputStream stream) throws IOException {
      try {
         if (!stream.hasNext()) {
            throw new IOException("start element not found");
         } else {
            XMLEvent element = stream.next();
            if (element.getType() == 2) {
               this.name = this.createXMLName(element.getName());
               this.readAttributes((StartElement)element);
               this.readNamespace((StartElement)element);
               this.readChildren(element, stream);
            } else {
               throw new IOException("is not a start element:" + element);
            }
         }
      } catch (XMLStreamException var3) {
         throw new IOException("error:" + var3);
      }
   }

   public StartElement createStartElement() {
      StartElementEvent startElement = null;
      if (this.name != null) {
         startElement = (StartElementEvent)ElementFactory.createStartElement(this.name.getNamespaceUri(), this.name.getLocalName(), this.name.getPrefix());
      } else {
         startElement = new StartElementEvent(this.name);
      }

      Iterator i = this.attributes().entrySet().iterator();

      Map.Entry entry;
      XMLName prefix;
      String uri;
      while(i.hasNext()) {
         entry = (Map.Entry)i.next();
         prefix = (XMLName)entry.getKey();
         uri = (String)entry.getValue();
         startElement.addAttribute(new AttributeImpl(prefix, uri, "CDATA"));
      }

      i = this.namespaceMap().entrySet().iterator();

      while(true) {
         while(i.hasNext()) {
            entry = (Map.Entry)i.next();
            String prefix = (String)entry.getKey();
            uri = (String)entry.getValue();
            if (prefix != null && !prefix.equals("")) {
               startElement.addNamespace(ElementFactory.createNamespaceAttribute(prefix, uri));
            } else {
               prefix = null;
               Attribute att = ElementFactory.createNamespaceAttribute((String)null, uri);
               startElement.addNamespace(att);
            }
         }

         startElement.setTransientNamespaceMap(this.globalNamespaceMap);
         return startElement;
      }
   }

   public EndElement createEndElement() {
      return new EndElementEvent(this.name);
   }

   public CharacterData createCharacterData() {
      return (CharacterData)(this.isSpace ? new SpaceEvent(this.getText()) : new CharacterDataEvent(this.getText()));
   }

   public String toString() {
      StringWriter sw = new StringWriter();
      XMLOutputStream xos = null;

      try {
         xos = XMLOutputStreamFactory.newInstance().newDebugOutputStream(sw);
         xos.add(this.stream());
         xos.flush();
      } catch (XMLStreamException var4) {
         return "Exception in: " + this.getClass().getName() + ".toString(): " + StackTraceUtils.throwable2StackTrace(var4);
      }

      return sw.getBuffer().toString();
   }

   protected void writeNamespace(XMLOutputStream xos) throws XMLStreamException {
      Iterator it = this.namespaceMap().keySet().iterator();

      while(it.hasNext()) {
         String prefix = (String)it.next();
         String url = (String)this.namespaceMap().get(prefix);
         xos.add(ElementFactory.createNamespaceAttribute("".equals(prefix) ? null : prefix, url));
      }

   }

   protected void writeAttributes(XMLOutputStream xos) throws XMLStreamException {
      if (this.attributes != null) {
         Iterator it = this.attributes.keySet().iterator();

         while(it.hasNext()) {
            XMLName name = (XMLName)it.next();
            String value = (String)this.attributes.get(name);
            xos.add(ElementFactory.createAttribute(name, value));
         }

      }
   }

   protected void writeText(XMLOutputStream xos) throws XMLStreamException {
      if (this.getText() != null) {
         xos.add(ElementFactory.createCharacterData(this.getText()));
      }

   }

   protected void writeChildren(XMLOutputStream xos) throws XMLStreamException {
      Iterator it = this.getChildren();

      while(it.hasNext()) {
         XMLNode node = (XMLNode)it.next();
         node.write(xos);
      }

   }

   protected XMLNode createChild(XMLName name) {
      return new XMLNode(name);
   }

   protected XMLNode createTextChild(String text) {
      return new XMLTextNode(text);
   }

   protected XMLName createXMLName(String name, String prefix, String namespace) {
      return new Name(namespace, name, prefix);
   }

   void readAttributes(StartElement element) throws IOException {
      Attribute attribute;
      XMLName xname;
      for(AttributeIterator it = element.getAttributes(); it.hasNext(); this.addAttribute(xname, attribute.getValue())) {
         attribute = it.next();
         xname = this.createXMLName(attribute.getName());
         String attributeName = attribute.getName().getLocalName();
         if (attributeName.equals("href")) {
            this.href = attribute.getValue();
            this.hrefs().addReference(this.href, this);
         } else if (attributeName.equals("id")) {
            this.id = attribute.getValue();
            this.hrefs().addTarget(this.id, this);
         }
      }

   }

   void readNamespace(StartElement element) throws IOException {
      AttributeIterator it = element.getNamespaces();
      if (it != null) {
         while(it.hasNext()) {
            Attribute attribute = it.next();
            XMLName xname = this.createXMLName(attribute.getName());
            if (xname.getLocalName().equals("xmlns")) {
               this.addNamespace("", attribute.getValue());
            } else {
               this.addNamespace(xname.getLocalName(), attribute.getValue());
            }
         }
      }

   }

   final ReferenceMap hrefs() {
      if (this.hrefs == null) {
         this.hrefs = new ReferenceMap();
      }

      return this.hrefs;
   }

   private final Map attributes() {
      if (this.attributes == null) {
         this.attributes = new HashMap();
      }

      return this.attributes;
   }

   private final Map namespaceMap() {
      return ((LLNamespaceMap)this.getGlobalNamespaceMap()).getNamespaceMap();
   }

   private void readChildren(XMLEvent element, XMLInputStream stream) throws XMLStreamException, IOException {
      while(stream.hasNext()) {
         XMLEvent child = stream.peek();
         this.isSpace = false;
         switch (child.getType()) {
            case 2:
               XMLName xmlName = child.getName();
               XMLNode node = this.addChild(this.createXMLName(xmlName));
               node.read(stream);
               if (this.currentLocation == null || !xmlName.getLocalName().equals("include")) {
                  break;
               }

               String schemaLocation = node.getAttribute("schemaLocation", (String)null);
               if (schemaLocation != null && !schemaLocation.startsWith("/") && schemaLocation.indexOf(58) < 0) {
                  node.addAttribute("schemaLocation", (String)null, (String)null, this.currentLocation + "/" + schemaLocation);
               }
               break;
            case 4:
               stream.next();
               return;
            case 16:
               this.addText(((CharacterData)child).getContent());
               stream.next();
               break;
            case 64:
               XMLNode textNode = this.addText(((CharacterData)child).getContent());
               textNode.isSpace = true;
               stream.next();
               break;
            default:
               stream.next();
         }
      }

   }

   private void addNamespaceIfNeeded() {
      if (this.name != null) {
         if (this.name.getNamespaceUri() != null) {
            String prefix = this.name.getPrefix();
            if (prefix == null) {
               prefix = "";
            }

            String oldNamespace = this.getNamespaceURI(prefix);
            if (!this.name.getNamespaceUri().equals(oldNamespace)) {
               this.addNamespace(prefix, this.name.getNamespaceUri());
            }
         }

      }
   }

   private XMLName createXMLName(XMLName name) {
      return this.createXMLName(name.getLocalName(), name.getPrefix(), name.getNamespaceUri());
   }

   public static void main(String[] args) throws Exception {
      XMLNode node = new XMLNode();
      node.read((InputStream)(new FileInputStream(args[0])));
      System.out.println("-------pretty output----------");
      System.out.println(node);
   }
}
