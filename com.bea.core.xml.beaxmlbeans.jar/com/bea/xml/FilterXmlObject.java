package com.bea.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;
import weblogic.xml.stream.XMLInputStream;

public abstract class FilterXmlObject implements XmlObject, SimpleValue, DelegateXmlObject {
   public SchemaType schemaType() {
      return this.underlyingXmlObject().schemaType();
   }

   public boolean validate() {
      return this.underlyingXmlObject().validate();
   }

   public boolean validate(XmlOptions options) {
      return this.underlyingXmlObject().validate(options);
   }

   public XmlObject[] selectPath(String path) {
      return this.underlyingXmlObject().selectPath(path);
   }

   public XmlObject[] selectPath(String path, XmlOptions options) {
      return this.underlyingXmlObject().selectPath(path, options);
   }

   public XmlObject[] execQuery(String query) {
      return this.underlyingXmlObject().execQuery(query);
   }

   public XmlObject[] execQuery(String query, XmlOptions options) {
      return this.underlyingXmlObject().execQuery(query, options);
   }

   public XmlObject changeType(SchemaType newType) {
      return this.underlyingXmlObject().changeType(newType);
   }

   public boolean isNil() {
      return this.underlyingXmlObject().isNil();
   }

   public void setNil() {
      this.underlyingXmlObject().setNil();
   }

   public boolean isImmutable() {
      return this.underlyingXmlObject().isImmutable();
   }

   public XmlObject set(XmlObject srcObj) {
      return this.underlyingXmlObject().set(srcObj);
   }

   public XmlObject copy() {
      return this.underlyingXmlObject().copy();
   }

   public XmlObject copy(XmlOptions options) {
      return this.underlyingXmlObject().copy(options);
   }

   public boolean valueEquals(XmlObject obj) {
      return this.underlyingXmlObject().valueEquals(obj);
   }

   public int valueHashCode() {
      return this.underlyingXmlObject().valueHashCode();
   }

   public int compareTo(Object obj) {
      return this.underlyingXmlObject().compareTo(obj);
   }

   public int compareValue(XmlObject obj) {
      return this.underlyingXmlObject().compareValue(obj);
   }

   public Object monitor() {
      return this.underlyingXmlObject().monitor();
   }

   public XmlDocumentProperties documentProperties() {
      return this.underlyingXmlObject().documentProperties();
   }

   public XmlCursor newCursor() {
      return this.underlyingXmlObject().newCursor();
   }

   /** @deprecated */
   public XMLInputStream newXMLInputStream() {
      return this.underlyingXmlObject().newXMLInputStream();
   }

   public XMLStreamReader newXMLStreamReader() {
      return this.underlyingXmlObject().newXMLStreamReader();
   }

   public String xmlText() {
      return this.underlyingXmlObject().xmlText();
   }

   public InputStream newInputStream() {
      return this.underlyingXmlObject().newInputStream();
   }

   public Reader newReader() {
      return this.underlyingXmlObject().newReader();
   }

   public Node newDomNode() {
      return this.underlyingXmlObject().newDomNode();
   }

   public Node getDomNode() {
      return this.underlyingXmlObject().getDomNode();
   }

   public void save(ContentHandler ch, LexicalHandler lh) throws SAXException {
      this.underlyingXmlObject().save(ch, lh);
   }

   public void save(File file) throws IOException {
      this.underlyingXmlObject().save(file);
   }

   public void save(OutputStream os) throws IOException {
      this.underlyingXmlObject().save(os);
   }

   public void save(Writer w) throws IOException {
      this.underlyingXmlObject().save(w);
   }

   /** @deprecated */
   public XMLInputStream newXMLInputStream(XmlOptions options) {
      return this.underlyingXmlObject().newXMLInputStream(options);
   }

   public XMLStreamReader newXMLStreamReader(XmlOptions options) {
      return this.underlyingXmlObject().newXMLStreamReader(options);
   }

   public String xmlText(XmlOptions options) {
      return this.underlyingXmlObject().xmlText(options);
   }

   public InputStream newInputStream(XmlOptions options) {
      return this.underlyingXmlObject().newInputStream(options);
   }

   public Reader newReader(XmlOptions options) {
      return this.underlyingXmlObject().newReader(options);
   }

   public Node newDomNode(XmlOptions options) {
      return this.underlyingXmlObject().newDomNode(options);
   }

   public void save(ContentHandler ch, LexicalHandler lh, XmlOptions options) throws SAXException {
      this.underlyingXmlObject().save(ch, lh, options);
   }

   public void save(File file, XmlOptions options) throws IOException {
      this.underlyingXmlObject().save(file, options);
   }

   public void save(OutputStream os, XmlOptions options) throws IOException {
      this.underlyingXmlObject().save(os, options);
   }

   public void save(Writer w, XmlOptions options) throws IOException {
      this.underlyingXmlObject().save(w, options);
   }

   public SchemaType instanceType() {
      return ((SimpleValue)this.underlyingXmlObject()).instanceType();
   }

   /** @deprecated */
   public String stringValue() {
      return ((SimpleValue)this.underlyingXmlObject()).stringValue();
   }

   /** @deprecated */
   public boolean booleanValue() {
      return ((SimpleValue)this.underlyingXmlObject()).booleanValue();
   }

   /** @deprecated */
   public byte byteValue() {
      return ((SimpleValue)this.underlyingXmlObject()).byteValue();
   }

   /** @deprecated */
   public short shortValue() {
      return ((SimpleValue)this.underlyingXmlObject()).shortValue();
   }

   /** @deprecated */
   public int intValue() {
      return ((SimpleValue)this.underlyingXmlObject()).intValue();
   }

   /** @deprecated */
   public long longValue() {
      return ((SimpleValue)this.underlyingXmlObject()).longValue();
   }

   /** @deprecated */
   public BigInteger bigIntegerValue() {
      return ((SimpleValue)this.underlyingXmlObject()).bigIntegerValue();
   }

   /** @deprecated */
   public BigDecimal bigDecimalValue() {
      return ((SimpleValue)this.underlyingXmlObject()).bigDecimalValue();
   }

   /** @deprecated */
   public float floatValue() {
      return ((SimpleValue)this.underlyingXmlObject()).floatValue();
   }

   /** @deprecated */
   public double doubleValue() {
      return ((SimpleValue)this.underlyingXmlObject()).doubleValue();
   }

   /** @deprecated */
   public byte[] byteArrayValue() {
      return ((SimpleValue)this.underlyingXmlObject()).byteArrayValue();
   }

   /** @deprecated */
   public StringEnumAbstractBase enumValue() {
      return ((SimpleValue)this.underlyingXmlObject()).enumValue();
   }

   /** @deprecated */
   public Calendar calendarValue() {
      return ((SimpleValue)this.underlyingXmlObject()).calendarValue();
   }

   /** @deprecated */
   public Date dateValue() {
      return ((SimpleValue)this.underlyingXmlObject()).dateValue();
   }

   /** @deprecated */
   public GDate gDateValue() {
      return ((SimpleValue)this.underlyingXmlObject()).gDateValue();
   }

   /** @deprecated */
   public GDuration gDurationValue() {
      return ((SimpleValue)this.underlyingXmlObject()).gDurationValue();
   }

   /** @deprecated */
   public QName qNameValue() {
      return ((SimpleValue)this.underlyingXmlObject()).qNameValue();
   }

   /** @deprecated */
   public List listValue() {
      return ((SimpleValue)this.underlyingXmlObject()).listValue();
   }

   /** @deprecated */
   public List xlistValue() {
      return ((SimpleValue)this.underlyingXmlObject()).xlistValue();
   }

   /** @deprecated */
   public Object objectValue() {
      return ((SimpleValue)this.underlyingXmlObject()).objectValue();
   }

   /** @deprecated */
   public void set(String obj) {
      ((SimpleValue)this.underlyingXmlObject()).set(obj);
   }

   /** @deprecated */
   public void set(boolean v) {
      ((SimpleValue)this.underlyingXmlObject()).set(v);
   }

   /** @deprecated */
   public void set(byte v) {
      ((SimpleValue)this.underlyingXmlObject()).set(v);
   }

   /** @deprecated */
   public void set(short v) {
      ((SimpleValue)this.underlyingXmlObject()).set(v);
   }

   /** @deprecated */
   public void set(int v) {
      ((SimpleValue)this.underlyingXmlObject()).set(v);
   }

   /** @deprecated */
   public void set(long v) {
      ((SimpleValue)this.underlyingXmlObject()).set(v);
   }

   /** @deprecated */
   public void set(BigInteger obj) {
      ((SimpleValue)this.underlyingXmlObject()).set(obj);
   }

   /** @deprecated */
   public void set(BigDecimal obj) {
      ((SimpleValue)this.underlyingXmlObject()).set(obj);
   }

   /** @deprecated */
   public void set(float v) {
      ((SimpleValue)this.underlyingXmlObject()).set(v);
   }

   /** @deprecated */
   public void set(double v) {
      ((SimpleValue)this.underlyingXmlObject()).set(v);
   }

   /** @deprecated */
   public void set(byte[] obj) {
      ((SimpleValue)this.underlyingXmlObject()).set(obj);
   }

   /** @deprecated */
   public void set(StringEnumAbstractBase obj) {
      ((SimpleValue)this.underlyingXmlObject()).set(obj);
   }

   /** @deprecated */
   public void set(Calendar obj) {
      ((SimpleValue)this.underlyingXmlObject()).set(obj);
   }

   /** @deprecated */
   public void set(Date obj) {
      ((SimpleValue)this.underlyingXmlObject()).set(obj);
   }

   /** @deprecated */
   public void set(GDateSpecification obj) {
      ((SimpleValue)this.underlyingXmlObject()).set(obj);
   }

   /** @deprecated */
   public void set(GDurationSpecification obj) {
      ((SimpleValue)this.underlyingXmlObject()).set(obj);
   }

   /** @deprecated */
   public void set(QName obj) {
      ((SimpleValue)this.underlyingXmlObject()).set(obj);
   }

   /** @deprecated */
   public void set(List obj) {
      ((SimpleValue)this.underlyingXmlObject()).set(obj);
   }

   public String getStringValue() {
      return ((SimpleValue)this.underlyingXmlObject()).getStringValue();
   }

   public boolean getBooleanValue() {
      return ((SimpleValue)this.underlyingXmlObject()).getBooleanValue();
   }

   public byte getByteValue() {
      return ((SimpleValue)this.underlyingXmlObject()).getByteValue();
   }

   public short getShortValue() {
      return ((SimpleValue)this.underlyingXmlObject()).getShortValue();
   }

   public int getIntValue() {
      return ((SimpleValue)this.underlyingXmlObject()).getIntValue();
   }

   public long getLongValue() {
      return ((SimpleValue)this.underlyingXmlObject()).getLongValue();
   }

   public BigInteger getBigIntegerValue() {
      return ((SimpleValue)this.underlyingXmlObject()).getBigIntegerValue();
   }

   public BigDecimal getBigDecimalValue() {
      return ((SimpleValue)this.underlyingXmlObject()).getBigDecimalValue();
   }

   public float getFloatValue() {
      return ((SimpleValue)this.underlyingXmlObject()).getFloatValue();
   }

   public double getDoubleValue() {
      return ((SimpleValue)this.underlyingXmlObject()).getDoubleValue();
   }

   public byte[] getByteArrayValue() {
      return ((SimpleValue)this.underlyingXmlObject()).getByteArrayValue();
   }

   public StringEnumAbstractBase getEnumValue() {
      return ((SimpleValue)this.underlyingXmlObject()).getEnumValue();
   }

   public Calendar getCalendarValue() {
      return ((SimpleValue)this.underlyingXmlObject()).getCalendarValue();
   }

   public Date getDateValue() {
      return ((SimpleValue)this.underlyingXmlObject()).getDateValue();
   }

   public GDate getGDateValue() {
      return ((SimpleValue)this.underlyingXmlObject()).getGDateValue();
   }

   public GDuration getGDurationValue() {
      return ((SimpleValue)this.underlyingXmlObject()).getGDurationValue();
   }

   public QName getQNameValue() {
      return ((SimpleValue)this.underlyingXmlObject()).getQNameValue();
   }

   public List getListValue() {
      return ((SimpleValue)this.underlyingXmlObject()).getListValue();
   }

   public List xgetListValue() {
      return ((SimpleValue)this.underlyingXmlObject()).xgetListValue();
   }

   public Object getObjectValue() {
      return ((SimpleValue)this.underlyingXmlObject()).getObjectValue();
   }

   public void setStringValue(String obj) {
      ((SimpleValue)this.underlyingXmlObject()).setStringValue(obj);
   }

   public void setBooleanValue(boolean v) {
      ((SimpleValue)this.underlyingXmlObject()).setBooleanValue(v);
   }

   public void setByteValue(byte v) {
      ((SimpleValue)this.underlyingXmlObject()).setByteValue(v);
   }

   public void setShortValue(short v) {
      ((SimpleValue)this.underlyingXmlObject()).setShortValue(v);
   }

   public void setIntValue(int v) {
      ((SimpleValue)this.underlyingXmlObject()).setIntValue(v);
   }

   public void setLongValue(long v) {
      ((SimpleValue)this.underlyingXmlObject()).setLongValue(v);
   }

   public void setBigIntegerValue(BigInteger obj) {
      ((SimpleValue)this.underlyingXmlObject()).setBigIntegerValue(obj);
   }

   public void setBigDecimalValue(BigDecimal obj) {
      ((SimpleValue)this.underlyingXmlObject()).setBigDecimalValue(obj);
   }

   public void setFloatValue(float v) {
      ((SimpleValue)this.underlyingXmlObject()).setFloatValue(v);
   }

   public void setDoubleValue(double v) {
      ((SimpleValue)this.underlyingXmlObject()).setDoubleValue(v);
   }

   public void setByteArrayValue(byte[] obj) {
      ((SimpleValue)this.underlyingXmlObject()).setByteArrayValue(obj);
   }

   public void setEnumValue(StringEnumAbstractBase obj) {
      ((SimpleValue)this.underlyingXmlObject()).setEnumValue(obj);
   }

   public void setCalendarValue(Calendar obj) {
      ((SimpleValue)this.underlyingXmlObject()).setCalendarValue(obj);
   }

   public void setDateValue(Date obj) {
      ((SimpleValue)this.underlyingXmlObject()).setDateValue(obj);
   }

   public void setGDateValue(GDate obj) {
      ((SimpleValue)this.underlyingXmlObject()).setGDateValue(obj);
   }

   public void setGDurationValue(GDuration obj) {
      ((SimpleValue)this.underlyingXmlObject()).setGDurationValue(obj);
   }

   public void setQNameValue(QName obj) {
      ((SimpleValue)this.underlyingXmlObject()).setQNameValue(obj);
   }

   public void setListValue(List obj) {
      ((SimpleValue)this.underlyingXmlObject()).setListValue(obj);
   }

   public void setObjectValue(Object obj) {
      ((SimpleValue)this.underlyingXmlObject()).setObjectValue(obj);
   }

   /** @deprecated */
   public void objectSet(Object obj) {
      ((SimpleValue)this.underlyingXmlObject()).objectSet(obj);
   }

   public XmlObject[] selectChildren(QName elementName) {
      return this.underlyingXmlObject().selectChildren(elementName);
   }

   public XmlObject[] selectChildren(String elementUri, String elementLocalName) {
      return this.underlyingXmlObject().selectChildren(elementUri, elementLocalName);
   }

   public XmlObject[] selectChildren(QNameSet elementNameSet) {
      return this.underlyingXmlObject().selectChildren(elementNameSet);
   }

   public XmlObject selectAttribute(QName attributeName) {
      return this.underlyingXmlObject().selectAttribute(attributeName);
   }

   public XmlObject selectAttribute(String attributeUri, String attributeLocalName) {
      return this.underlyingXmlObject().selectAttribute(attributeUri, attributeLocalName);
   }

   public XmlObject[] selectAttributes(QNameSet attributeNameSet) {
      return this.underlyingXmlObject().selectAttributes(attributeNameSet);
   }
}
