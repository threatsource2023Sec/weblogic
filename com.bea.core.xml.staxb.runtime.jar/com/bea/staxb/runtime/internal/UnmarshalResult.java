package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingType;
import com.bea.staxb.buildtime.internal.bts.BindingTypeName;
import com.bea.staxb.buildtime.internal.bts.JavaTypeName;
import com.bea.staxb.buildtime.internal.bts.SimpleDocumentBinding;
import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.staxb.runtime.NodeFromStreamReader;
import com.bea.staxb.runtime.ObjectFactory;
import com.bea.staxb.runtime.UnmarshalOptions;
import com.bea.staxb.runtime.internal.util.XmlStreamUtils;
import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.richParser.XMLStreamReaderExt;
import com.bea.xbean.richParser.XMLStreamReaderExtImpl;
import com.bea.xml.GDate;
import com.bea.xml.GDuration;
import com.bea.xml.XmlCalendar;
import com.bea.xml.XmlException;
import com.bea.xml.soap.SOAPArrayType;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.BitSet;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;

abstract class UnmarshalResult {
   private final BindingLoader bindingLoader;
   private final RuntimeBindingTypeTable typeTable;
   private final boolean forceDotNetUnmarshal;
   private XMLStreamReader originalReader;
   protected XMLStreamReaderExt baseReader;
   protected final UnmarshalOptions options;
   protected final Collection errors;
   private final XsiAttributeHolder xsiAttributeHolder = new XsiAttributeHolder();
   private boolean gotXsiAttributes;
   private BitSet defaultAttributeBits;
   private int currentAttributeIndex = -1;
   private int currentAttributeCount = -1;
   private static final int INVALID = -1;

   UnmarshalResult(BindingLoader bindingLoader, RuntimeBindingTypeTable typeTable, UnmarshalOptions options) {
      this.bindingLoader = bindingLoader;
      this.typeTable = typeTable;
      this.options = options;
      this.errors = BindingContextImpl.extractErrorHandler(options);
      if (options != null) {
         this.forceDotNetUnmarshal = options.isForceDotNetCompatibleUnmarshal();
      } else {
         this.forceDotNetUnmarshal = false;
      }

   }

   protected RuntimeBindingType getRuntimeType(BindingType type) throws XmlException {
      return this.typeTable.createRuntimeType(type, this.bindingLoader);
   }

   private void enrichXmlStream(XMLStreamReader reader) {
      assert reader != null;

      this.baseReader = createExtendedReader(reader);
      this.updateAttributeState();
   }

   private static XMLStreamReaderExt createExtendedReader(XMLStreamReader reader) {
      return (XMLStreamReaderExt)(reader instanceof XMLStreamReaderExt ? (XMLStreamReaderExt)reader : new XMLStreamReaderExtImpl(reader));
   }

   NamespaceContext getNamespaceContext() {
      return this.baseReader.getNamespaceContext();
   }

   Map getNamespaceMapping() {
      return this.options != null ? this.options.getNamespaceMapping() : null;
   }

   private void addError(String msg) {
      this.addError(msg, this.baseReader.getLocation());
   }

   protected void addWarning(String msg) {
      Location location = this.baseReader.getLocation();

      assert location != null;

      MarshalStreamUtils.addError(this.errors, msg, 1, location);
   }

   final void addError(String msg, Location location) {
      assert location != null;

      MarshalStreamUtils.addError(this.errors, msg, location);
   }

   Collection getErrors() {
      return this.errors;
   }

   final Object unmarshalDocument(XMLStreamReader reader) throws XmlException {
      this.enrichXmlStream(this.getValidatingStream(reader));
      this.advanceToFirstItemOfInterest();
      BindingType bindingType = this.determineRootType();
      return this.unmarshalBindingType(this.getRuntimeType(bindingType));
   }

   protected Object unmarshalBindingType(RuntimeBindingType actual_rtt) throws XmlException {
      this.updateAttributeState();
      ObjectFactory of = this.extractObjectFactory();

      try {
         if (this.hasXsiNil()) {
            return NullUnmarshaller.getInstance().unmarshal(this);
         } else {
            TypeUnmarshaller um;
            if (of == null) {
               um = actual_rtt.getUnmarshaller();
               return um.unmarshal(this);
            } else {
               Object initial_obj = of.createObject(actual_rtt.getJavaType());
               if (initial_obj == null) {
                  String msg = "factory: " + of + " returned null for type " + actual_rtt.getJavaType();
                  throw new IllegalStateException(msg);
               } else {
                  um = actual_rtt.getUnmarshaller();
                  Object inter = actual_rtt.createIntermediary(this, initial_obj);
                  um.unmarshalIntoIntermediary(inter, this);
                  return actual_rtt.getFinalObjectFromIntermediary(inter, this);
               }
            }
         }
      } catch (InvalidLexicalValueException var6) {
         assert !this.errors.isEmpty();

         return null;
      }
   }

   protected ObjectFactory extractObjectFactory() {
      return this.options == null ? null : this.options.getInitialObjectFactory();
   }

   final Object unmarshalType(XMLStreamReader reader, XmlTypeName schemaType, String javaType) throws XmlException {
      this.doctorStream(schemaType, reader);
      BindingType expected_btype = this.determineBindingType(schemaType, javaType);
      RuntimeBindingType expected_rtt = this.getRuntimeType(expected_btype);
      RuntimeBindingType actual_rtt = expected_rtt.determineActualRuntimeType(this);
      return this.unmarshalBindingType(actual_rtt);
   }

   final Object unmarshalElement(XMLStreamReader reader, XmlTypeName globalElement, String javaType) throws XmlException {
      BindingType binding_type = this.determineTypeForGlobalElement(globalElement);
      XmlTypeName type_name = binding_type.getName().getXmlName();

      assert type_name.isGlobal();

      assert type_name.isSchemaType();

      return this.unmarshalType(reader, type_name, javaType);
   }

   private void doctorStream(XmlTypeName schemaType, XMLStreamReader reader) throws XmlException {
      this.originalReader = reader;
      this.enrichXmlStream(this.getValidatingStream(schemaType, reader));
   }

   public XMLStreamReader getOriginalReader() {
      return this.originalReader;
   }

   protected abstract XMLStreamReader getValidatingStream(XMLStreamReader var1) throws XmlException;

   protected abstract XMLStreamReader getValidatingStream(XmlTypeName var1, XMLStreamReader var2) throws XmlException;

   private BindingType determineBindingType(XmlTypeName xname, String javaType) throws XmlException {
      JavaTypeName jname = JavaTypeName.forClassName(javaType);
      BindingTypeName btname = BindingTypeName.forPair(jname, xname);
      BindingType prioritizedBType = this.getPrioritizedBindingType(btname);
      if (prioritizedBType != null) {
         return prioritizedBType;
      } else {
         BindingType btype = this.bindingLoader.getBindingType(btname);
         if (btype == null) {
            String msg = "unable to find binding type for " + xname + " : " + javaType;
            throw new XmlException(msg);
         } else {
            return btype;
         }
      }
   }

   protected BindingType getPrioritizedBindingType(BindingTypeName btname) throws XmlException {
      if (this.forceDotNetUnmarshal) {
         BindingTypeName newbtname = BindingTypeNameRegistry.getInstance().getBTNForTyped(btname);
         if (newbtname != null) {
            return this.bindingLoader.getBindingType(newbtname);
         }
      }

      return null;
   }

   private BindingType determineRootType() throws XmlException {
      QName xsi_type = this.getXsiType();
      BindingType retval = null;
      if (xsi_type != null) {
         retval = this.getPojoTypeFromXsiType(xsi_type);
      }

      if (retval == null) {
         String nameSpace = this.getNamespaceURI();
         String localName = this.getLocalName();
         QName root_elem_qname = new QName(nameSpace != null ? nameSpace.intern() : null, localName != null ? localName.intern() : null);
         retval = this.determineTypeForGlobalElement(root_elem_qname);
      }

      return retval;
   }

   private BindingType determineTypeForGlobalElement(XmlTypeName elem) throws XmlException {
      BindingType doc_binding_type = this.getPojoBindingType(elem, true);
      SimpleDocumentBinding sd = (SimpleDocumentBinding)doc_binding_type;
      return this.getPojoBindingType(sd.getTypeOfElement(), true);
   }

   private BindingType determineTypeForGlobalElement(QName elem) throws XmlException {
      XmlTypeName el = XmlTypeName.forGlobalName('e', elem);
      return this.determineTypeForGlobalElement(el);
   }

   protected BindingType getPojoTypeFromXsiType(QName xsi_type) throws XmlException {
      BindingType pbt = this.getPrioritizedBindinggTypeForXsiType(xsi_type);
      if (pbt != null) {
         return pbt;
      } else {
         XmlTypeName type_name = XmlTypeName.forTypeNamed(xsi_type);
         BindingType pojoBindingType = this.getPojoBindingType(type_name, false);

         assert !(pojoBindingType instanceof SimpleDocumentBinding);

         return pojoBindingType;
      }
   }

   protected BindingType getPrioritizedBindinggTypeForXsiType(QName xsi_type) {
      if (this.forceDotNetUnmarshal) {
         BindingTypeName bindingTypeNameForAnyType = BindingTypeNameRegistry.getInstance().getBTNForUnTyped(xsi_type);
         if (bindingTypeNameForAnyType != null) {
            return this.bindingLoader.getBindingType(bindingTypeNameForAnyType);
         }
      }

      return null;
   }

   private BindingType getPojoBindingType(XmlTypeName type_name, boolean fail_fast) throws XmlException {
      BindingTypeName btName = this.bindingLoader.lookupPojoFor(type_name);
      if (btName == null) {
         String msg = "failed to load java type corresponding to " + type_name;
         if (fail_fast) {
            throw new XmlException(msg);
         } else {
            this.addError(msg);
            return null;
         }
      } else {
         BindingType bt = this.bindingLoader.getBindingType(btName);
         if (bt == null) {
            String msg = "failed to load binding type for " + btName;
            if (fail_fast) {
               throw new XmlException(msg);
            } else {
               this.addError(msg);
               return null;
            }
         } else {
            return bt;
         }
      }
   }

   Location getLocation() {
      return this.baseReader.getLocation();
   }

   String getStringValue() throws XmlException {
      try {
         return this.baseReader.getStringValue();
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   String getStringValue(int ws) throws XmlException {
      try {
         return this.baseReader.getStringValue(ws);
      } catch (XMLStreamException var3) {
         throw new XmlException(var3);
      }
   }

   boolean getBooleanValue() throws XmlException {
      try {
         return this.baseReader.getBooleanValue();
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   byte getByteValue() throws XmlException {
      try {
         return this.baseReader.getByteValue();
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   short getShortValue() throws XmlException {
      try {
         return this.baseReader.getShortValue();
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   int getIntValue() throws XmlException {
      try {
         return this.baseReader.getIntValue();
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   long getLongValue() throws XmlException {
      try {
         return this.baseReader.getLongValue();
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   BigInteger getBigIntegerValue() throws XmlException {
      try {
         return this.baseReader.getBigIntegerValue();
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   BigDecimal getBigDecimalValue() throws XmlException {
      try {
         return this.baseReader.getBigDecimalValue();
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   float getFloatValue() throws XmlException {
      try {
         return this.baseReader.getFloatValue();
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   double getDoubleValue() throws XmlException {
      try {
         return this.baseReader.getDoubleValue();
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   InputStream getHexBinaryValue() throws XmlException {
      try {
         return this.baseReader.getHexBinaryValue();
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   InputStream getBase64Value() throws XmlException {
      try {
         return this.baseReader.getBase64Value();
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   XmlCalendar getCalendarValue() throws XmlException {
      try {
         return this.baseReader.getCalendarValue();
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   String getAnyUriValue() throws XmlException {
      try {
         return this.baseReader.getStringValue(3);
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   Date getDateValue() throws XmlException {
      try {
         GDate val = this.baseReader.getGDateValue();
         return val == null ? null : val.getDate();
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   GDate getGDateValue() throws XmlException {
      try {
         return this.baseReader.getGDateValue();
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   GDuration getGDurationValue() throws XmlException {
      try {
         return this.baseReader.getGDurationValue();
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   QName getQNameValue() throws XmlException {
      try {
         return this.baseReader.getQNameValue();
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   String getAttributeStringValue() throws XmlException {
      try {
         return this.baseReader.getAttributeStringValue(this.currentAttributeIndex);
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   String getAttributeStringValue(int whitespace_style) throws XmlException {
      try {
         return this.baseReader.getAttributeStringValue(this.currentAttributeIndex, whitespace_style);
      } catch (XMLStreamException var3) {
         throw new XmlException(var3);
      }
   }

   boolean getAttributeBooleanValue() throws XmlException {
      try {
         return this.baseReader.getAttributeBooleanValue(this.currentAttributeIndex);
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   byte getAttributeByteValue() throws XmlException {
      try {
         return this.baseReader.getAttributeByteValue(this.currentAttributeIndex);
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   short getAttributeShortValue() throws XmlException {
      try {
         return this.baseReader.getAttributeShortValue(this.currentAttributeIndex);
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   int getAttributeIntValue() throws XmlException {
      try {
         return this.baseReader.getAttributeIntValue(this.currentAttributeIndex);
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   long getAttributeLongValue() throws XmlException {
      try {
         return this.baseReader.getAttributeLongValue(this.currentAttributeIndex);
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   BigInteger getAttributeBigIntegerValue() throws XmlException {
      try {
         return this.baseReader.getAttributeBigIntegerValue(this.currentAttributeIndex);
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   BigDecimal getAttributeBigDecimalValue() throws XmlException {
      try {
         return this.baseReader.getAttributeBigDecimalValue(this.currentAttributeIndex);
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   float getAttributeFloatValue() throws XmlException {
      try {
         return this.baseReader.getAttributeFloatValue(this.currentAttributeIndex);
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   double getAttributeDoubleValue() throws XmlException {
      try {
         return this.baseReader.getAttributeDoubleValue(this.currentAttributeIndex);
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   String getAttributeAnyUriValue() throws XmlException {
      try {
         return this.baseReader.getAttributeStringValue(this.currentAttributeIndex, 3);
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   InputStream getAttributeHexBinaryValue() throws XmlException {
      try {
         return this.baseReader.getAttributeHexBinaryValue(this.currentAttributeIndex);
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   InputStream getAttributeBase64Value() throws XmlException {
      try {
         return this.baseReader.getAttributeBase64Value(this.currentAttributeIndex);
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   XmlCalendar getAttributeCalendarValue() throws XmlException {
      try {
         return this.baseReader.getAttributeCalendarValue(this.currentAttributeIndex);
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   Date getAttributeDateValue() throws XmlException {
      try {
         GDate val = this.baseReader.getAttributeGDateValue(this.currentAttributeIndex);
         return val == null ? null : val.getDate();
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   GDate getAttributeGDateValue() throws XmlException {
      try {
         return this.baseReader.getAttributeGDateValue(this.currentAttributeIndex);
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   GDuration getAttributeGDurationValue() throws XmlException {
      try {
         return this.baseReader.getAttributeGDurationValue(this.currentAttributeIndex);
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   QName getAttributeQNameValue() throws XmlException {
      try {
         return this.baseReader.getAttributeQNameValue(this.currentAttributeIndex);
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   protected QName getXsiType() throws XmlException {
      if (!this.gotXsiAttributes) {
         this.getXsiAttributes();
      }

      assert this.gotXsiAttributes;

      return this.xsiAttributeHolder.xsiType;
   }

   protected final boolean hasXsiNil() throws XmlException {
      if (!this.gotXsiAttributes) {
         this.getXsiAttributes();
      }

      assert this.gotXsiAttributes;

      return this.xsiAttributeHolder.hasXsiNil;
   }

   private void getXsiAttributes() throws XmlException {
      try {
         MarshalStreamUtils.getXsiAttributes(this.xsiAttributeHolder, this.baseReader, this.errors);
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }

      this.gotXsiAttributes = true;
   }

   final boolean advanceToNextStartElement() throws XmlException {
      boolean ret = MarshalStreamUtils.advanceToNextStartElement(this.baseReader);
      this.updateAttributeState();
      return ret;
   }

   private void advanceToFirstItemOfInterest() throws XmlException {
      assert this.baseReader != null;

      MarshalStreamUtils.advanceToFirstItemOfInterest(this.baseReader);
   }

   final int next() throws XmlException {
      try {
         int new_state = this.baseReader.next();
         if (new_state == 1) {
            this.updateAttributeState();
         }

         return new_state;
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   final boolean hasNext() throws XmlException {
      try {
         return this.baseReader.hasNext();
      } catch (XMLStreamException var2) {
         throw new XmlException(var2);
      }
   }

   protected final void updateAttributeState() {
      this.xsiAttributeHolder.reset();
      this.gotXsiAttributes = false;
      if (this.defaultAttributeBits != null) {
         this.defaultAttributeBits.clear();
      }

      if (this.baseReader.isStartElement()) {
         this.currentAttributeCount = this.baseReader.getAttributeCount();
         this.currentAttributeIndex = 0;
      } else {
         this.currentAttributeIndex = -1;
         this.currentAttributeCount = -1;
      }

   }

   boolean isStartElement() {
      return this.baseReader.isStartElement();
   }

   boolean isEndElement() {
      return this.baseReader.isEndElement();
   }

   private int getAttributeCount() {
      assert this.baseReader.isStartElement();

      return this.baseReader.getAttributeCount();
   }

   String getLocalName() {
      return this.baseReader.getLocalName();
   }

   String getNamespaceURI() {
      return this.baseReader.getNamespaceURI();
   }

   final void skipElement() throws XmlException {
      MarshalStreamUtils.skipElement(this.baseReader);
      this.updateAttributeState();
   }

   final void advanceAttribute() {
      assert this.hasMoreAttributes();

      assert this.currentAttributeCount != -1;

      assert this.currentAttributeIndex != -1;

      ++this.currentAttributeIndex;

      assert this.currentAttributeIndex <= this.currentAttributeCount;

   }

   boolean hasMoreAttributes() {
      assert this.baseReader.isStartElement();

      assert this.currentAttributeCount != -1;

      assert this.currentAttributeIndex != -1;

      return this.currentAttributeIndex < this.currentAttributeCount;
   }

   String getCurrentAttributeNamespaceURI() {
      assert this.currentAttributeCount != -1;

      assert this.currentAttributeIndex != -1;

      return this.baseReader.getAttributeNamespace(this.currentAttributeIndex);
   }

   String getCurrentAttributeLocalName() {
      assert this.currentAttributeCount != -1;

      assert this.currentAttributeIndex != -1;

      return this.baseReader.getAttributeLocalName(this.currentAttributeIndex);
   }

   final void attributePresent(int att_idx) {
      if (this.defaultAttributeBits == null) {
         int bits_size = this.getAttributeCount();
         this.defaultAttributeBits = new BitSet(bits_size);
      }

      this.defaultAttributeBits.set(att_idx);
   }

   boolean isAttributePresent(int att_idx) {
      return this.defaultAttributeBits == null ? false : this.defaultAttributeBits.get(att_idx);
   }

   void setNextElementDefault(String lexical_default) throws XmlException {
      try {
         this.baseReader.setDefaultValue(lexical_default);
      } catch (XMLStreamException var3) {
         throw new XmlException(var3);
      }
   }

   static boolean doesElementMatch(QName qn, String localname, String uri, Map namespaceMapping) {
      if (qn.getLocalPart().equals(localname)) {
         boolean matched = qn.getNamespaceURI().equals(uri == null ? "" : uri);
         if (matched) {
            return true;
         }

         if (namespaceMapping != null) {
            String mappedName = (String)namespaceMapping.get(qn.getNamespaceURI());
            if (mappedName != null) {
               return mappedName.equals(uri == null ? "" : uri);
            }
         }
      }

      return false;
   }

   boolean doesByNameElementMatch(QName expected_name, String localname, String uri, Map namespaceMapping) {
      return doesElementMatch(expected_name, localname, uri, namespaceMapping);
   }

   abstract void extractAndFillElementProp(RuntimeBindingProperty var1, Object var2) throws XmlException;

   protected TypeUnmarshaller getUnmarshaller(RuntimeBindingType actual_rtt) throws XmlException {
      TypeUnmarshaller um;
      if (this.hasXsiNil()) {
         um = NullUnmarshaller.getInstance();
      } else {
         um = actual_rtt.getUnmarshaller();
      }

      return um;
   }

   public abstract SOAPArrayType extractSoapArrayType() throws XmlException;

   SOAPElement getCurrentSOAPElementNode() throws XmlException {
      assert this.originalReader != null;

      NodeFromStreamReader nodeFromStreamReader = this.options == null ? null : this.options.getNodeFromStreamReader();
      if (nodeFromStreamReader == null) {
         String e = "SOAPElement processing requires a nodeFromStreamReader in the unmarshalOptions";
         throw new IllegalStateException("SOAPElement processing requires a nodeFromStreamReader in the unmarshalOptions");
      } else {
         Node node = nodeFromStreamReader.getCurrentNode(this.originalReader);
         if (node == null) {
            String msg = nodeFromStreamReader + "failed to get current dom node from " + this.originalReader.getClass() + " at " + XmlStreamUtils.printEvent(this.originalReader) + ".  Returning null";
            this.addError(msg);
            return null;
         } else {
            SOAPElement se = (SOAPElement)node;
            return se;
         }
      }
   }
}
