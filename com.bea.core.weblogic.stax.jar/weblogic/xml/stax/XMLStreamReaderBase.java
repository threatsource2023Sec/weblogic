package weblogic.xml.stax;

import java.io.FileReader;
import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.xml.babel.baseparser.Attribute;
import weblogic.xml.babel.baseparser.BaseParser;
import weblogic.xml.babel.baseparser.CharDataElement;
import weblogic.xml.babel.baseparser.Element;
import weblogic.xml.babel.baseparser.ParserConstraintException;
import weblogic.xml.babel.baseparser.PrefixMapping;
import weblogic.xml.babel.baseparser.ProcessingInstruction;
import weblogic.xml.babel.baseparser.StartElement;
import weblogic.xml.stax.util.TypeNames;

public class XMLStreamReaderBase implements XMLStreamReader, Location {
   public static final String UTF8_BOM = "\ufeff";
   private Element current;
   private BaseParser baseparser;
   protected int eventType;
   private boolean pushBack = false;
   private final ArrayList attributes = new ArrayList();
   private final ArrayList namespaces = new ArrayList();
   private List outofscope = new ArrayList(0);
   private Map namespacemap;
   private ReadOnlyNamespaceContext nscontext;
   private String textCache;
   private char[] arrayCache;
   private ProcessingInstruction xmldecl;
   private boolean open = true;
   private final Attribute tempAttribute = new Attribute();
   private ConfigurationContextBase configContext;
   private boolean processedDTD = false;
   private String systemId = null;
   private Map initialNamespaces;
   private RecyclingFactory factory = null;
   private long characterLimit;
   private long elementCountLimit;
   private long totalElements;
   private int attrCountLimit;
   private int elementNestingLimit;
   private int nesting;
   private int childElementLimit;
   private Stack eltStack;
   private boolean limitChecksEnabled;
   private int elementStarted = 0;
   private boolean checkStartElementEnabled = true;

   public XMLStreamReaderBase() {
   }

   private void prime(Reader r) throws XMLStreamException {
      this.totalElements = 0L;
      this.nesting = 0;
      if (this.childElementLimit != -1) {
         if (this.eltStack == null) {
            this.eltStack = new Stack();
         } else {
            this.eltStack.clear();
         }
      }

      try {
         if (this.baseparser == null) {
            this.baseparser = new BaseParser(r);
         }

         if (this.limitChecksEnabled) {
            this.baseparser.setMaxAttrsPerElement(this.attrCountLimit);
         }

         if (this.initialNamespaces != null) {
            this.baseparser.setFragmentParser(true);
            this.baseparser.addNamespaceDeclarations(this.initialNamespaces);
         }

         this.open = true;
         this.tempAttribute.init();
         this.namespacemap = this.baseparser.getNameSpaceMap();
         this.nscontext = new ReadOnlyNamespaceContext(this.namespacemap);
         this.eventType = 7;
         this.pushBack = true;

         try {
            this.current = this.baseparser.parseSome();
         } catch (ParserConstraintException var4) {
            Throwable t = var4.getCause();
            if (t instanceof XMLStreamException) {
               throw (XMLStreamException)t;
            }

            throw new XMLStreamException(var4.getLocalizedMessage());
         }

         if (this.current == null) {
            throw new XMLStreamException("Premature end of file encountered");
         } else {
            if (this.current.type == 4) {
               ProcessingInstruction pi = (ProcessingInstruction)this.current;
               if (pi.isXMLDecl()) {
                  this.xmldecl = pi;
                  this.pushBack = false;
               } else {
                  this.xmldecl = null;
               }
            }

         }
      } catch (Exception var5) {
         if (var5 instanceof XMLStreamException) {
            throw (XMLStreamException)var5;
         } else if (var5 instanceof ParserConstraintException) {
            throw new XMLStreamException(var5.getLocalizedMessage());
         } else if (var5.getCause() instanceof XMLStreamException) {
            throw (XMLStreamException)var5.getCause();
         } else {
            throw new XMLStreamException(var5);
         }
      }
   }

   public XMLStreamReaderBase(Reader r) throws XMLStreamException {
      this.prime(r);
   }

   public void setInput(Reader r) throws XMLStreamException {
      Reader cr = this.limitChecksEnabled && this.characterLimit != -1L ? new CountingReader(r, this.characterLimit) : r;

      try {
         if (this.baseparser != null) {
            this.baseparser = this.baseparser.recycle((Reader)cr);
         }
      } catch (Exception var4) {
         throw new XMLStreamException(var4);
      }

      this.prime((Reader)cr);
   }

   public void setInitialNamespaces(Map namespaces) {
      this.initialNamespaces = namespaces;
   }

   public void setConfigurationContext(ConfigurationContextBase base) {
      this.configContext = base;
      this.attrCountLimit = asInt(base.getProperty("weblogic.xml.stax.MaxAttrsPerElement"));
      this.childElementLimit = asInt(base.getProperty("weblogic.xml.stax.MaxChildElements"));
      this.elementCountLimit = asLong(base.getProperty("weblogic.xml.stax.MaxTotalElements"));
      this.characterLimit = asLong(base.getProperty("weblogic.xml.stax.MaxInputSize"));
      this.elementNestingLimit = asInt(base.getProperty("weblogic.xml.stax.MaxElementDepth"));
      this.limitChecksEnabled = asBoolean(base.getProperty("weblogic.xml.stax.EnableAllLimitChecks"));
      this.checkStartElementEnabled = asBoolean(base.getProperty("weblogic.xml.stax.EnableStartElementChecks"));
   }

   private static boolean asBoolean(Object obj) {
      if (obj instanceof Boolean) {
         return (Boolean)obj;
      } else if (obj instanceof String) {
         return ((String)obj).equalsIgnoreCase("true");
      } else {
         throw new IllegalArgumentException("cannot convert " + obj + " to boolean.");
      }
   }

   private static int asInt(Object obj) {
      int res;
      if (obj instanceof Number) {
         res = ((Number)obj).intValue();
      } else {
         if (!(obj instanceof String)) {
            throw new IllegalArgumentException("cannot convert " + obj + " to int.");
         }

         res = Integer.parseInt((String)obj);
      }

      if (res < 0 && res != -1) {
         throw new IllegalArgumentException("invalid int property value: " + res);
      } else {
         return res;
      }
   }

   private static long asLong(Object obj) {
      long res;
      if (obj instanceof Number) {
         res = ((Number)obj).longValue();
      } else {
         if (!(obj instanceof String)) {
            throw new IllegalArgumentException("cannot convert " + obj + " to long.");
         }

         res = Long.parseLong((String)obj);
      }

      if (res < 0L && res != -1L) {
         throw new IllegalArgumentException("invalid long property value: " + res);
      } else {
         return res;
      }
   }

   void setFactory(RecyclingFactory f) {
      this.factory = f;
   }

   public Object getProperty(String name) throws IllegalArgumentException {
      return this.configContext.getProperty(name);
   }

   private boolean atStart() {
      return 7 == this.eventType;
   }

   protected boolean atEnd() {
      return 8 == this.eventType;
   }

   protected int convert(int type) {
      switch (type) {
         case 0:
            return 1;
         case 1:
         case 2:
         default:
            return -1;
         case 3:
            return 2;
         case 4:
            return 3;
         case 5:
            return 4;
         case 6:
            return 5;
         case 7:
            return this.baseparser.inDocument() ? 4 : 6;
      }
   }

   public int getEventType() {
      return this.eventType;
   }

   protected void setTextCache(String val) {
      this.textCache = val;
   }

   protected void setArrayCache(char[] val) {
      this.arrayCache = val;
   }

   protected String getTextCache() {
      return this.textCache;
   }

   protected char[] getArrayCache() {
      return this.arrayCache;
   }

   protected void setEventType(int type) {
      this.eventType = type;
   }

   protected void advance() throws XMLStreamException {
      try {
         this.textCache = null;
         this.arrayCache = null;
         if (this.pushBack) {
            int previousEventType = this.eventType;
            this.eventType = this.convert(this.current.type);
            this.pushBack = false;
            if (this.isStartElement()) {
               if (this.limitChecksEnabled) {
                  this.checkCurrentConstraints(this.current);
               }

               this.initializeAttributesAndNamespaces();
               ++this.elementStarted;
            }

            if (this.isEndElement()) {
               if (this.limitChecksEnabled) {
                  this.endCheckCurrentConstraints();
               }

               this.initializeOutOfScopeNamespaces();
               --this.elementStarted;
            }

            if (this.checkStartElementEnabled && previousEventType == 7 && this.isCharacters() && this.elementStarted <= 0) {
               CharDataElement cde = (CharDataElement)this.current;
               String text = cde.getContent();
               if (text != null) {
                  if (text.startsWith("\ufeff")) {
                     text = text.substring(1);
                  }

                  text = text.trim();
                  text = text.replaceAll("(\\r|\\n|\\t)", "");
                  if (text.length() > 0) {
                     throw new XMLStreamException("Start element expected.");
                  }
               }
            }

         } else if (!this.processedDTD && this.baseparser.getDTDStringValue() != null) {
            this.textCache = this.baseparser.getDTDStringValue();
            this.eventType = 11;
            this.processedDTD = true;
         } else if (this.baseparser.hasNext()) {
            this.current = this.baseparser.parseSome();
            this.eventType = this.convert(this.current.type);
            if (this.isStartElement()) {
               if (this.limitChecksEnabled) {
                  this.checkCurrentConstraints(this.current);
               }

               this.initializeAttributesAndNamespaces();
               ++this.elementStarted;
            }

            if (this.isEndElement()) {
               if (this.limitChecksEnabled) {
                  this.endCheckCurrentConstraints();
               }

               this.initializeOutOfScopeNamespaces();
               --this.elementStarted;
            }

            if (this.checkStartElementEnabled && this.isCharacters() && this.elementStarted <= 0) {
               CharDataElement cde = (CharDataElement)this.current;
               String text = cde.getContent();
               if (text != null) {
                  if (text.startsWith("\ufeff")) {
                     text = text.substring(1);
                  }

                  text = text.trim();
                  text = text.replaceAll("(\\r|\\n|\\t)", "");
                  if (text.length() > 0) {
                     throw new XMLStreamException("Start element expected.");
                  }
               }
            }

         } else {
            this.eventType = 8;
         }
      } catch (ParserConstraintException var4) {
         Throwable cause = var4.getCause();
         if (cause instanceof XMLStreamException) {
            throw (XMLStreamException)cause;
         } else {
            throw new XMLStreamException(var4.getLocalizedMessage());
         }
      } catch (Exception var5) {
         if (var5 instanceof XMLStreamException) {
            throw (XMLStreamException)var5;
         } else {
            throw new XMLStreamException(var5);
         }
      }
   }

   private void checkCurrentConstraints(Element current) throws XMLStreamException {
      if (this.totalElements == this.elementCountLimit) {
         throw new XMLStreamException("Total element limit of " + this.elementCountLimit + " elements exceeded at element '" + current.getName() + "'");
      } else {
         ++this.totalElements;
         if (this.nesting == this.elementNestingLimit) {
            throw new XMLStreamException("Element nesting limit of " + this.elementNestingLimit + " elements exceeded at element '" + current.getName() + "'");
         } else {
            ++this.nesting;
            if (this.childElementLimit != -1) {
               if (!this.eltStack.empty()) {
                  ElementInfo ei = (ElementInfo)this.eltStack.peek();
                  if (ei.childCnt == this.childElementLimit) {
                     throw new XMLStreamException("Child element count of " + this.childElementLimit + " exceeded for node '" + ei.name + "'");
                  }

                  ++ei.childCnt;
               }

               this.eltStack.push(new ElementInfo(current.getName()));
            }

         }
      }
   }

   private void endCheckCurrentConstraints() {
      --this.nesting;
      if (this.nesting < 0) {
         throw new IllegalStateException("nesting stack underflow");
      } else {
         if (this.childElementLimit != -1) {
            if (this.eltStack.empty()) {
               throw new IllegalStateException("breadth stack underflow");
            }

            this.eltStack.pop();
         }

      }
   }

   protected void initializeAttributesAndNamespaces() {
      this.namespaces.clear();
      this.attributes.clear();
      List atts = ((StartElement)this.current).getAttributes();
      int i = 0;

      for(int len = atts.size(); i < len; ++i) {
         Attribute a = (Attribute)atts.get(i);
         if (!a.isNameSpaceDeclaration() && !a.declaresDefaultNameSpace()) {
            this.attributes.add(a);
         } else {
            this.namespaces.add(a);
         }
      }

   }

   protected void initializeOutOfScopeNamespaces() {
      this.outofscope = this.baseparser.getOutOfScopeNamespaces();
   }

   public int next() throws XMLStreamException {
      if (!this.open && this.atEnd()) {
         throw new XMLStreamException("next() may not be called when XMLStreamReader is at END_DOCUMENT");
      } else {
         if (this.atStart()) {
            this.elementStarted = 0;
         }

         this.advance();
         if (this.atEnd()) {
            this.open = false;
         }

         return this.eventType;
      }
   }

   public boolean hasNext() throws XMLStreamException {
      return this.open;
   }

   public void require(int type, String namespaceURI, String localName) throws XMLStreamException {
      boolean typeMatch = false;
      boolean uriMatch = false;
      boolean localNameMatch = false;
      if (namespaceURI == null || namespaceURI.equals(this.getNamespaceURI())) {
         uriMatch = true;
      }

      if (type == this.eventType) {
         typeMatch = true;
      }

      if (localName == null || localName.equals(this.getLocalName())) {
         localNameMatch = true;
      }

      if (!uriMatch || !typeMatch || !localNameMatch) {
         if (type != this.eventType) {
            throw new XMLStreamException("Event types did not match");
         } else if (!namespaceURI.equals(this.getNamespaceURI())) {
            throw new XMLStreamException("The supplied namespace URI " + namespaceURI + " did not match the current namespace URI  " + this.getNamespaceURI());
         } else {
            throw new XMLStreamException("The supplied local name " + localName + " did not match the current local name " + this.getLocalName());
         }
      }
   }

   public int nextTag() throws XMLStreamException {
      do {
         if (this.next() == 8) {
            throw new XMLStreamException("Unexpected end of Document");
         }

         if (this.isCharacters() && !this.isWhiteSpace()) {
            throw new XMLStreamException("Unexpected text");
         }
      } while(!this.isStartElement() && !this.isEndElement());

      return this.getEventType();
   }

   public String getElementText() throws XMLStreamException {
      StringBuffer buf = new StringBuffer();
      if (this.getEventType() != 1) {
         throw new XMLStreamException("Precondition for readText is getEventType() == START_ELEMENT");
      } else {
         while(this.next() != 8) {
            if (this.isStartElement()) {
               throw new XMLStreamException("Unexpected Element start");
            }

            if (this.isCharacters() || this.getEventType() == 9) {
               buf.append(this.getText());
            }

            if (this.isEndElement()) {
               return buf.toString();
            }
         }

         throw new XMLStreamException("Unexpected end of Document");
      }
   }

   public void close() throws XMLStreamException {
      if (this.factory != null) {
         this.baseparser.clear();
         this.factory.add(this);
      } else {
         this.baseparser = null;
      }

   }

   protected boolean checkStartOrEnd() {
      if (!this.isStartElement() && !this.isEndElement()) {
         throw new IllegalStateException("Attempted operation is valid only on a START_ELEMENT or END_ELEMENT the current state is [" + TypeNames.getName(this.getEventType()) + "]");
      } else {
         return true;
      }
   }

   protected boolean checkStartElement() {
      if (!this.isStartElement()) {
         throw new IllegalStateException("Attempted operation is only valid on an START_ELEMENT the current state is [" + TypeNames.getName(this.getEventType()) + "]");
      } else {
         return true;
      }
   }

   protected boolean checkEndElement() {
      if (!this.isEndElement()) {
         throw new IllegalStateException("Attempted operation is only valid on an END_ELEMENT the current state is [" + TypeNames.getName(this.getEventType()) + "]");
      } else {
         return true;
      }
   }

   public QName getAttributeName(int index) {
      return this.createQName(this.getAttributeNamespace(index), this.getAttributeLocalName(index), this.getAttributePrefix(index));
   }

   private QName createQName(String uri, String localName, String prefix) {
      return prefix == null ? new QName(uri, localName) : new QName(uri, localName, prefix);
   }

   public int getAttributeCount() {
      if (this.isStartElement()) {
         return this.attributes.size();
      } else {
         throw new IllegalStateException("Unable to access attributes on a non START_ELEMENT the current state is [" + TypeNames.getName(this.getEventType()) + "]");
      }
   }

   public String getAttributeValue(String namespaceUri, String localName) {
      for(int i = 0; i < this.getAttributeCount(); ++i) {
         Attribute a = this.getAttributeInternal(i);
         if (localName.equals(a.getLocalName())) {
            if (namespaceUri == null) {
               return a.getValue();
            }

            if (namespaceUri.equals(a.getURI())) {
               return a.getValue();
            }
         }
      }

      return null;
   }

   private Attribute getAttributeInternal(int index) {
      return (Attribute)this.attributes.get(index);
   }

   private Attribute getNamespaceInternal(int index) {
      if (this.isStartElement()) {
         return (Attribute)this.namespaces.get(index);
      } else {
         PrefixMapping m = (PrefixMapping)this.outofscope.get(index);
         this.tempAttribute.setLocalName(m.getPrefix());
         this.tempAttribute.setValue(m.getOldUri());
         return this.tempAttribute;
      }
   }

   public String getAttributeNamespace(int index) {
      this.checkStartElement();
      Attribute a = this.getAttributeInternal(index);
      return a == null ? null : a.getURI();
   }

   public String getAttributeLocalName(int index) {
      this.checkStartElement();
      Attribute a = this.getAttributeInternal(index);
      return a == null ? null : a.getLocalName();
   }

   public String getAttributePrefix(int index) {
      this.checkStartElement();
      Attribute a = this.getAttributeInternal(index);
      return a == null ? null : a.getPrefix();
   }

   public String getAttributeType(int index) {
      this.checkStartElement();
      return "CDATA";
   }

   public String getAttributeValue(int index) {
      this.checkStartElement();
      Attribute a = this.getAttributeInternal(index);
      return a == null ? null : a.getValue();
   }

   public boolean isAttributeSpecified(int index) {
      this.checkStartElement();
      return false;
   }

   public String getNamespaceURI(String prefix) {
      if (this.namespacemap == null) {
         return null;
      } else {
         String uri = (String)this.namespacemap.get(prefix);
         if (uri == null && this.eventType == 2 && !this.outofscope.isEmpty()) {
            Iterator i = this.outofscope.iterator();

            while(i.hasNext()) {
               PrefixMapping pm = (PrefixMapping)i.next();
               String oosPrefix = pm.getPrefix();
               if (prefix.equals(oosPrefix)) {
                  uri = pm.getOldUri();
               }
            }
         }

         return uri;
      }
   }

   public int getNamespaceCount() {
      this.checkStartOrEnd();
      return this.isStartElement() ? this.namespaces.size() : this.outofscope.size();
   }

   public String getNamespacePrefix(int index) {
      this.checkStartOrEnd();
      Attribute a = this.getNamespaceInternal(index);
      if (a == null) {
         return null;
      } else {
         return a.declaresDefaultNameSpace() ? null : a.getLocalName();
      }
   }

   protected static String checkNull(String s) {
      return s == null ? "" : s;
   }

   public String getNamespaceURI(int index) {
      this.checkStartOrEnd();
      Attribute a = this.getNamespaceInternal(index);
      return a == null ? null : checkNull(a.getValue());
   }

   public NamespaceContext getNamespaceContext() {
      if (this.eventType == 2 && !this.outofscope.isEmpty()) {
         Map ns = new HashMap();
         ns.putAll(this.nscontext.internal);

         String prefix;
         String oldUri;
         for(Iterator i = this.outofscope.iterator(); i.hasNext(); ns.put(prefix, oldUri)) {
            PrefixMapping pm = (PrefixMapping)i.next();
            prefix = pm.getPrefix();
            oldUri = pm.getOldUri();
            if (pm.getUri() != null) {
               ns.remove(pm.getUri());
            }
         }

         return new ReadOnlyNamespaceContext(ns);
      } else {
         return this.nscontext;
      }
   }

   public XMLStreamReader subReader() throws XMLStreamException {
      return null;
   }

   public String getText() {
      if (this.textCache == null) {
         if (this.current.type != 5 && this.current.type != 6 && this.current.type != 7) {
            throw new IllegalStateException("Attempt to access text from an illegal state, the current state is [" + TypeNames.getName(this.getEventType()) + "]");
         }

         CharDataElement cde = (CharDataElement)this.current;
         this.textCache = cde.getContent();
      }

      return this.textCache;
   }

   public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
      if (target == null) {
         throw new NullPointerException("Target must be non-null value");
      } else if (targetStart >= 0 && length >= 0 && targetStart + length <= target.length) {
         char[] source = this.getTextCharacters();

         assert source != null;

         int max = source.length - sourceStart;
         if (max < 0) {
            return 0;
         } else {
            if (max < length) {
               length = max;
            }

            System.arraycopy(source, sourceStart, target, targetStart, length);
            return length;
         }
      } else {
         throw new IndexOutOfBoundsException("Illegal arguments were specified");
      }
   }

   public char[] getTextCharacters() {
      if (this.arrayCache == null) {
         if (this.current.type != 5 && this.current.type != 6 && this.current.type != 7) {
            throw new IllegalStateException("Attempt to access text from an illegal state, the current the current state is [" + TypeNames.getName(this.getEventType()) + "]");
         }

         CharDataElement cde = (CharDataElement)this.current;
         this.arrayCache = cde.getArray();
      }

      return this.arrayCache;
   }

   public int getTextStart() {
      return 0;
   }

   public int getTextLength() {
      return this.getTextCharacters().length;
   }

   public boolean hasText() {
      return this.eventType == 4 || this.eventType == 11 || this.eventType == 5 || this.eventType == 9;
   }

   public String getLocalName() {
      if (!this.hasName()) {
         throw new IllegalStateException("getLocalName() may only be called on a START_ELEMENT, END_ELEMENT or ENTITY_REFERENCE event, the current state is [" + TypeNames.getName(this.getEventType()) + "]");
      } else {
         return this.current.getLocalName();
      }
   }

   public boolean hasName() {
      return this.eventType == 1 || this.eventType == 2 || this.eventType == 9;
   }

   public QName getName() {
      return this.createQName(this.getNamespaceURI(), this.getLocalName(), this.getPrefix());
   }

   public String getNamespaceURI() {
      return checkNull(this.current.getURI());
   }

   public String getPrefix() {
      return this.current.getPrefix();
   }

   public String getVersion() {
      return this.xmldecl == null ? "1.0" : this.xmldecl.getVersion();
   }

   public boolean isStandalone() {
      return this.xmldecl == null ? false : "yes".equals(this.xmldecl.getStandalone());
   }

   public boolean standaloneSet() {
      return this.xmldecl != null && this.xmldecl.getStandalone() != null;
   }

   public String getCharacterEncodingScheme() {
      return this.xmldecl == null ? "utf-8" : this.xmldecl.getEncoding();
   }

   public String getPITarget() {
      if (this.current instanceof ProcessingInstruction) {
         ProcessingInstruction pi = (ProcessingInstruction)this.current;
         return pi.getTarget();
      } else {
         return null;
      }
   }

   public String getPIData() {
      if (this.current instanceof ProcessingInstruction) {
         ProcessingInstruction pi = (ProcessingInstruction)this.current;
         return pi.getData();
      } else {
         return null;
      }
   }

   public String getEncoding() {
      return null;
   }

   public Location getLocation() {
      return this;
   }

   public int getLineNumber() {
      return this.current.getLine();
   }

   public int getColumnNumber() {
      return this.current.getColumn();
   }

   public int getCharacterOffset() {
      return -1;
   }

   public String getLocationURI() {
      return null;
   }

   public boolean isStartElement() {
      return this.eventType == 1;
   }

   public boolean isEndElement() {
      return this.eventType == 2;
   }

   public boolean isCharacters() {
      return this.eventType == 4;
   }

   public boolean isWhiteSpace() {
      return this.current instanceof CharDataElement ? ((CharDataElement)this.current).isSpace() : false;
   }

   public String getPublicId() {
      return null;
   }

   public String getSystemId() {
      return this.systemId;
   }

   public void setSystemId(String id) {
      this.systemId = id;
   }

   private static void printName(String prefix, String uri, String localName, StringBuffer b) {
      if (uri != null && !"".equals(uri)) {
         b.append("['" + uri + "']:");
      }

      if (prefix != null) {
         b.append(prefix + ":");
      }

      if (localName != null) {
         b.append(localName);
      }

   }

   private static void printName(XMLStreamReader xmlr, StringBuffer b) {
      if (xmlr.hasName()) {
         String prefix = xmlr.getPrefix();
         String uri = xmlr.getNamespaceURI();
         String localName = xmlr.getLocalName();
         printName(prefix, uri, localName, b);
      }

   }

   public static String printEvent(XMLStreamReader xmlr) {
      StringBuffer b = new StringBuffer();
      b.append("EVENT:[" + xmlr.getLocation().getLineNumber() + "][" + xmlr.getLocation().getColumnNumber() + "] ");
      b.append(TypeNames.getName(xmlr.getEventType()));
      b.append(" [");
      int i;
      String n;
      switch (xmlr.getEventType()) {
         case 1:
            b.append("<");
            printName(xmlr, b);

            for(i = 0; i < xmlr.getNamespaceCount(); ++i) {
               b.append(" ");
               n = xmlr.getNamespacePrefix(i);
               if ("xmlns".equals(n)) {
                  b.append("xmlns=\"" + xmlr.getNamespaceURI(i) + "\"");
               } else {
                  b.append("xmlns:" + n);
                  b.append("=\"");
                  b.append(xmlr.getNamespaceURI(i));
                  b.append("\"");
               }
            }

            for(i = 0; i < xmlr.getAttributeCount(); ++i) {
               b.append(" ");
               printName(xmlr.getAttributePrefix(i), xmlr.getAttributeNamespace(i), xmlr.getAttributeLocalName(i), b);
               b.append("=\"");
               b.append(xmlr.getAttributeValue(i));
               b.append("\"");
            }

            b.append(">");
            break;
         case 2:
            b.append("</");
            printName(xmlr, b);

            for(i = 0; i < xmlr.getNamespaceCount(); ++i) {
               b.append(" ");
               n = xmlr.getNamespacePrefix(i);
               if ("xmlns".equals(n)) {
                  b.append("xmlns=\"" + xmlr.getNamespaceURI(i) + "\"");
               } else {
                  b.append("xmlns:" + n);
                  b.append("=\"");
                  b.append(xmlr.getNamespaceURI(i));
                  b.append("\"");
               }
            }

            b.append(">");
            break;
         case 3:
            String target = xmlr.getPITarget();
            if (target == null) {
               target = "";
            }

            String data = xmlr.getPIData();
            if (data == null) {
               data = "";
            }

            b.append("<?");
            b.append(target + " " + data);
            b.append("?>");
            break;
         case 4:
         case 6:
            i = xmlr.getTextStart();
            int length = xmlr.getTextLength();
            b.append(new String(xmlr.getTextCharacters(), i, length));
            break;
         case 5:
            b.append("<!--");
            if (xmlr.hasText()) {
               b.append(xmlr.getText());
            }

            b.append("-->");
            break;
         case 7:
            b.append("<?xml");
            b.append(" version='" + xmlr.getVersion() + "'");
            b.append(" encoding='" + xmlr.getCharacterEncodingScheme() + "'");
            if (xmlr.isStandalone()) {
               b.append(" standalone='yes'");
            } else {
               b.append(" standalone='no'");
            }

            b.append("?>");
         case 8:
         case 10:
         default:
            break;
         case 9:
            b.append(xmlr.getLocalName() + "=");
            if (xmlr.hasText()) {
               b.append("[" + xmlr.getText() + "]");
            }
            break;
         case 11:
            b.append(xmlr.getText());
            break;
         case 12:
            b.append("<![CDATA[");
            if (xmlr.hasText()) {
               b.append(xmlr.getText());
            }

            b.append("]]>");
      }

      b.append("]");
      return b.toString();
   }

   public String toString() {
      return printEvent(this);
   }

   public static void main(String[] args) throws Exception {
      Reader in = new FileReader(args[0]);
      XMLStreamReader r = new XMLStreamReaderBase(in);

      while(r.hasNext()) {
         System.out.println(r.toString());
         r.next();
      }

   }

   private static final class ElementInfo {
      public String name;
      public int childCnt;

      public ElementInfo(String name) {
         this.name = name;
      }
   }

   private static final class CountingReader extends FilterReader {
      private long limit;
      private long count;
      private long curMark = 0L;

      public CountingReader(Reader r, long limit) {
         super(r);
         this.limit = limit;
         this.count = 0L;
         this.curMark = 0L;
      }

      public int read() throws IOException {
         int n = super.read();
         if (n != -1) {
            if (this.count == this.limit) {
               this.throwLimitException();
            }

            ++this.count;
         }

         return n;
      }

      public int read(char[] cbuf, int off, int len) throws IOException {
         int n = super.read(cbuf, off, len);
         if (n > 0) {
            if ((long)n > this.limit - this.count) {
               this.throwLimitException();
            }

            this.count += (long)n;
         }

         return n;
      }

      public void mark(int readAheadLimit) throws IOException {
         super.mark(readAheadLimit);
         this.curMark = this.count;
      }

      public void reset() throws IOException {
         super.reset();
         this.count = this.curMark;
         this.curMark = 0L;
      }

      public long skip(long n) throws IOException {
         long res = super.skip(n);
         if (res > this.limit - this.count) {
            this.throwLimitException();
         }

         this.count += res;
         return res;
      }

      private void throwLimitException() throws IOException {
         throw new ParserConstraintException("Character limit of " + this.limit + " characters exceeded");
      }
   }
}
