package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingType;
import com.bea.staxb.buildtime.internal.bts.BindingTypeVisitor;
import com.bea.staxb.buildtime.internal.bts.BuiltinBindingType;
import com.bea.staxb.buildtime.internal.bts.ByNameBean;
import com.bea.staxb.buildtime.internal.bts.JaxrpcEnumType;
import com.bea.staxb.buildtime.internal.bts.ListArrayType;
import com.bea.staxb.buildtime.internal.bts.SimpleBindingType;
import com.bea.staxb.buildtime.internal.bts.SimpleContentBean;
import com.bea.staxb.buildtime.internal.bts.SimpleDocumentBinding;
import com.bea.staxb.buildtime.internal.bts.SoapArrayType;
import com.bea.staxb.buildtime.internal.bts.WrappedArrayType;
import com.bea.staxb.runtime.MarshalOptions;
import com.bea.staxb.runtime.internal.util.AttributeHolder;
import com.bea.xbean.common.XmlStreamUtils;
import com.bea.xbean.common.XmlWhitespace;
import com.bea.xml.XmlException;
import com.bea.xml.XmlRuntimeException;
import java.util.Stack;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.w3c.dom.Node;

abstract class PullMarshalResult extends MarshalResult implements XMLStreamReader {
   private final PullBindingTypeVisitor bindingTypeVisitor = new PullBindingTypeVisitor(this);
   private final ScopedNamespaceContext namespaceContext;
   private final Stack visitorStack = new Stack();
   private XmlTypeVisitor currVisitor;
   private int currentEventType = 1;
   private boolean initedAttributes = false;
   private AttributeHolder attributeHolder;
   private static final String ATTRIBUTE_XML_TYPE = "CDATA";

   PullMarshalResult(BindingLoader loader, RuntimeBindingTypeTable tbl, RuntimeBindingProperty property, Object obj, MarshalOptions options) throws XmlException {
      super(loader, tbl, options);
      NamespaceContext initial_ctx = MarshallerImpl.getNamespaceContextFromOptions(options);
      this.namespaceContext = new ScopedNamespaceContext(initial_ctx);
      this.namespaceContext.openScope();
      this.bindDefaultNS(options, property);
      this.bindNamespaces(options.getInitialNamespaces());
      this.currVisitor = this.createInitialVisitor(property, obj);
      this.initAttributes();
      this.addSchemaLocationAttributes(options);
   }

   protected void reset(RuntimeBindingProperty property, Object obj) throws XmlException {
      this.resetPrefixCount();
      this.namespaceContext.clear();
      this.namespaceContext.openScope();
      this.visitorStack.clear();
      this.currVisitor = this.createInitialVisitor(property, obj);
      this.currentEventType = 1;
      this.initedAttributes = false;
      if (this.attributeHolder != null) {
         this.attributeHolder.clear();
      }

   }

   protected XmlTypeVisitor createInitialVisitor(RuntimeBindingProperty property, Object obj) throws XmlException {
      return this.createVisitor(property, obj);
   }

   protected XmlTypeVisitor createVisitor(RuntimeBindingProperty property, Object obj) throws XmlException {
      assert property != null;

      BindingType btype = property.getRuntimeBindingType().getBindingType();
      PullBindingTypeVisitor type_visitor = this.bindingTypeVisitor;
      type_visitor.setParentObject(obj);
      type_visitor.setRuntimeBindingProperty(property);
      btype.accept(type_visitor);
      return type_visitor.getXmlTypeVisitor();
   }

   public Object getProperty(String s) throws IllegalArgumentException {
      throw new UnsupportedOperationException("UNIMPLEMENTED");
   }

   public int next() throws XMLStreamException {
      switch (this.currVisitor.getState()) {
         case 2:
         default:
            throw new AssertionError("invalid: " + this.currVisitor.getState());
         case 3:
         case 4:
            this.currVisitor = this.popVisitor();
         case 1:
            try {
               return this.currentEventType = this.advanceToNext();
            } catch (XmlException var3) {
               XMLStreamException xse = new XMLStreamException(var3);
               throw xse;
            }
      }
   }

   private int advanceToNext() throws XmlException {
      int next_state = this.currVisitor.advance();
      switch (next_state) {
         case 2:
            this.pushVisitor(this.currVisitor);
            this.currVisitor = this.currVisitor.getCurrentChild();
            return 1;
         case 3:
            this.pushVisitor(this.currVisitor);
            this.currVisitor = this.currVisitor.getCurrentChild();
            return 4;
         case 4:
            return 2;
         default:
            throw new AssertionError("bad state: " + next_state);
      }
   }

   private void pushVisitor(XmlTypeVisitor v) {
      this.visitorStack.push(v);
      this.namespaceContext.openScope();
      this.initedAttributes = false;
   }

   private XmlTypeVisitor popVisitor() {
      this.namespaceContext.closeScope();
      XmlTypeVisitor tv = (XmlTypeVisitor)this.visitorStack.pop();
      return tv;
   }

   public void require(int i, String s, String s1) throws XMLStreamException {
      throw new UnsupportedOperationException("UNIMPLEMENTED");
   }

   public String getElementText() throws XMLStreamException {
      throw new UnsupportedOperationException("UNIMPLEMENTED");
   }

   public int nextTag() throws XMLStreamException {
      throw new UnsupportedOperationException("UNIMPLEMENTED");
   }

   public boolean hasNext() throws XMLStreamException {
      if (this.visitorStack.isEmpty()) {
         return this.currVisitor.getState() != 4;
      } else {
         return true;
      }
   }

   public void close() throws XMLStreamException {
   }

   public String getNamespaceURI(String s) {
      if (s == null) {
         throw new IllegalArgumentException("prefix cannot be null");
      } else {
         return this.getNamespaceContext().getNamespaceURI(s);
      }
   }

   public boolean isStartElement() {
      return this.currentEventType == 1;
   }

   public boolean isEndElement() {
      return this.currentEventType == 2;
   }

   public boolean isCharacters() {
      return this.currentEventType == 4;
   }

   public boolean isWhiteSpace() {
      if (!this.isCharacters()) {
         return false;
      } else {
         CharSequence seq = this.currVisitor.getCharData();
         return XmlWhitespace.isAllSpace(seq);
      }
   }

   public String getAttributeValue(String uri, String lname) {
      this.initAttributes();
      int i = 0;

      for(int len = this.getAttributeCount(); i < len; ++i) {
         QName aname = this.getAttributeName(i);
         if (lname.equals(aname.getLocalPart()) && (uri == null || uri.equals(aname.getNamespaceURI()))) {
            return this.getAttributeValue(i);
         }
      }

      return null;
   }

   public int getAttributeCount() {
      this.initAttributes();
      return this.attributeHolder == null ? 0 : this.attributeHolder.getAttributeCount();
   }

   public QName getAttributeName(int i) {
      this.initAttributes();

      assert this.attributeHolder != null;

      return this.attributeHolder.getAttributeName(i);
   }

   public String getAttributeNamespace(int i) {
      this.initAttributes();

      assert this.attributeHolder != null;

      return this.attributeHolder.getAttributeNamespace(i);
   }

   public String getAttributeLocalName(int i) {
      this.initAttributes();

      assert this.attributeHolder != null;

      return this.attributeHolder.getAttributeLocalName(i);
   }

   public String getAttributePrefix(int i) {
      this.initAttributes();

      assert this.attributeHolder != null;

      return this.attributeHolder.getAttributePrefix(i);
   }

   public String getAttributeType(int i) {
      this.attributeRangeCheck(i);
      return "CDATA";
   }

   public String getAttributeValue(int i) {
      this.initAttributes();

      assert this.attributeHolder != null;

      return this.attributeHolder.getAttributeValue(i);
   }

   public boolean isAttributeSpecified(int i) {
      this.initAttributes();

      assert this.attributeHolder != null;

      return this.attributeHolder.isAttributeSpecified(i);
   }

   public int getNamespaceCount() {
      this.initAttributes();
      return this.namespaceContext.getCurrentScopeNamespaceCount();
   }

   public String getNamespacePrefix(int i) {
      this.initAttributes();
      return this.namespaceContext.getCurrentScopeNamespacePrefix(i);
   }

   public String getNamespaceURI(int i) {
      this.initAttributes();
      return this.namespaceContext.getCurrentScopeNamespaceURI(i);
   }

   public NamespaceContext getNamespaceContext() {
      return this.namespaceContext;
   }

   protected void bindNamespace(String prefix, String uri) throws XmlException {
      this.namespaceContext.bindNamespace(prefix, uri);
   }

   protected void addAttribute(String lname, String value) throws XmlException {
      this.addAttribute((String)null, lname, (String)null, value);
   }

   public int getEventType() {
      return this.currentEventType;
   }

   public String getText() {
      CharSequence seq = this.currVisitor.getCharData();
      return seq.toString();
   }

   public char[] getTextCharacters() {
      CharSequence seq = this.currVisitor.getCharData();
      if (seq instanceof String) {
         return seq.toString().toCharArray();
      } else {
         int len = seq.length();
         char[] val = new char[len];

         for(int i = 0; i < len; ++i) {
            val[i] = seq.charAt(i);
         }

         return val;
      }
   }

   public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
      if (length < 0) {
         throw new IndexOutOfBoundsException("negative length: " + length);
      } else if (targetStart < 0) {
         throw new IndexOutOfBoundsException("negative targetStart: " + targetStart);
      } else {
         int target_length = target.length;
         if (targetStart >= target_length) {
            throw new IndexOutOfBoundsException("targetStart(" + targetStart + ") past end of target(" + target_length + ")");
         } else if (targetStart + length > target_length) {
            throw new IndexOutOfBoundsException("insufficient data in target(length is " + target_length + ")");
         } else {
            CharSequence seq = this.currVisitor.getCharData();
            if (seq instanceof String) {
               String s = seq.toString();
               s.getChars(sourceStart, sourceStart + length, target, targetStart);
               return length;
            } else {
               int cnt = 0;
               int src_idx = sourceStart;

               for(int dest_idx = targetStart; cnt < length; ++cnt) {
                  target[dest_idx++] = seq.charAt(src_idx++);
               }

               return cnt;
            }
         }
      }
   }

   public int getTextStart() {
      return 0;
   }

   public int getTextLength() {
      return this.currVisitor.getCharData().length();
   }

   public String getEncoding() {
      return null;
   }

   public boolean hasText() {
      switch (this.currentEventType) {
         case 4:
         case 5:
         case 6:
         case 9:
         case 11:
            return true;
         case 7:
         case 8:
         case 10:
         default:
            return false;
      }
   }

   public Location getLocation() {
      return EmptyLocation.getInstance();
   }

   public QName getName() {
      return this.currVisitor.getName();
   }

   public String getLocalName() {
      return this.currVisitor.getLocalPart();
   }

   public boolean hasName() {
      return this.currentEventType == 1 || this.currentEventType == 2;
   }

   public String getNamespaceURI() {
      return this.currVisitor.getNamespaceURI();
   }

   public String getPrefix() {
      return this.currVisitor.getPrefix();
   }

   public String getVersion() {
      return null;
   }

   public boolean isStandalone() {
      return false;
   }

   public boolean standaloneSet() {
      return false;
   }

   public String getCharacterEncodingScheme() {
      return null;
   }

   public String getPITarget() {
      throw new IllegalStateException();
   }

   public String getPIData() {
      throw new IllegalStateException();
   }

   protected void initAttributes() {
      if (!this.initedAttributes) {
         try {
            if (this.attributeHolder != null) {
               this.attributeHolder.clear();
            }

            this.currVisitor.initAttributes();
            this.currVisitor.predefineNamespaces();
         } catch (XmlException var2) {
            throw new XmlRuntimeException(var2);
         }

         this.initedAttributes = true;
      }

   }

   private void attributeRangeCheck(int i) {
      int att_cnt = this.getAttributeCount();
      if (i >= att_cnt) {
         String msg = "index" + i + " invalid.  attribute count is " + att_cnt;
         throw new IndexOutOfBoundsException(msg);
      }
   }

   public String toString() {
      return "com.bea.staxb.runtime.internal.MarshalResult{currentEvent=" + XmlStreamUtils.printEvent(this) + ", visitorStack=" + (this.visitorStack == null ? null : "size:" + this.visitorStack.size() + this.visitorStack) + ", currVisitor=" + this.currVisitor + "}";
   }

   protected void addAttribute(String namespaceURI, String localPart, String prefix, String value) {
      assert checkDefaultNS(namespaceURI, prefix);

      if (this.attributeHolder == null) {
         this.attributeHolder = new AttributeHolder();
      }

      this.attributeHolder.add(namespaceURI, localPart, prefix, value);
   }

   void appendDomNode(Node el) {
      throw new AssertionError("UNIMP");
   }

   private static final class PullBindingTypeVisitor implements BindingTypeVisitor {
      private final PullMarshalResult marshalResult;
      private Object parentObject;
      private RuntimeBindingProperty runtimeBindingProperty;
      private XmlTypeVisitor xmlTypeVisitor;

      public PullBindingTypeVisitor(PullMarshalResult marshalResult) {
         this.marshalResult = marshalResult;
      }

      public void setParentObject(Object parentObject) {
         this.parentObject = parentObject;
      }

      public void setRuntimeBindingProperty(RuntimeBindingProperty runtimeBindingProperty) {
         this.runtimeBindingProperty = runtimeBindingProperty;
      }

      public XmlTypeVisitor getXmlTypeVisitor() {
         return this.xmlTypeVisitor;
      }

      public void visit(BuiltinBindingType builtinBindingType) throws XmlException {
         this.xmlTypeVisitor = new SimpleTypeVisitor(this.runtimeBindingProperty, this.parentObject, this.marshalResult);
      }

      public void visit(ByNameBean byNameBean) throws XmlException {
         this.xmlTypeVisitor = new ByNameTypeVisitor(this.runtimeBindingProperty, this.parentObject, this.marshalResult);
      }

      public void visit(SimpleContentBean simpleContentBean) throws XmlException {
         this.xmlTypeVisitor = new SimpleContentTypeVisitor(this.runtimeBindingProperty, this.parentObject, this.marshalResult);
      }

      public void visit(SimpleBindingType simpleBindingType) throws XmlException {
         this.xmlTypeVisitor = new SimpleTypeVisitor(this.runtimeBindingProperty, this.parentObject, this.marshalResult);
      }

      public void visit(JaxrpcEnumType jaxrpcEnumType) throws XmlException {
         this.xmlTypeVisitor = new SimpleTypeVisitor(this.runtimeBindingProperty, this.parentObject, this.marshalResult);
      }

      public void visit(SimpleDocumentBinding simpleDocumentBinding) throws XmlException {
         throw new AssertionError("unexpected type: " + simpleDocumentBinding);
      }

      public void visit(WrappedArrayType wrappedArrayType) throws XmlException {
         this.xmlTypeVisitor = new WrappedArrayTypeVisitor(this.runtimeBindingProperty, this.parentObject, this.marshalResult);
      }

      public void visit(SoapArrayType soapArrayType) throws XmlException {
         this.xmlTypeVisitor = new SoapArrayTypeVisitor(this.runtimeBindingProperty, this.parentObject, this.marshalResult);
      }

      public void visit(ListArrayType listArrayType) throws XmlException {
         this.xmlTypeVisitor = new SimpleTypeVisitor(this.runtimeBindingProperty, this.parentObject, this.marshalResult);
      }
   }
}
