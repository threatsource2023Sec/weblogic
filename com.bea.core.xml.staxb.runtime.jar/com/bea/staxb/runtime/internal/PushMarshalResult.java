package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.runtime.MarshalOptions;
import com.bea.staxb.runtime.StaxSerializer;
import com.bea.staxb.runtime.StaxWriterToNode;
import com.bea.staxb.runtime.internal.util.ArrayUtils;
import com.bea.staxb.runtime.internal.util.PrettyXMLStreamWriter;
import com.bea.xml.XmlException;
import com.bea.xml.XmlRuntimeException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

abstract class PushMarshalResult extends MarshalResult implements RuntimeTypeVisitor {
   private final XMLStreamWriter writer;
   private final String defaultNamespaceUri;
   private Object currObject;
   private RuntimeBindingProperty currProp;
   private final StaxWriterToNode staxWriterToNode;
   private final XOPMarshaller xopMarshaller;
   private final MarshalOptions marshalOptions;
   private static final int UNSET = -1;

   PushMarshalResult(BindingLoader bindingLoader, RuntimeBindingTypeTable typeTable, XMLStreamWriter writer, String default_namespace_uri, MarshalOptions options) throws XmlException {
      super(bindingLoader, typeTable, options);
      this.writer = writer;
      this.marshalOptions = options;

      assert !"".equals(default_namespace_uri);

      this.defaultNamespaceUri = default_namespace_uri;
      this.staxWriterToNode = options.getStaxWriterToNode();
      this.xopMarshaller = options.getMarshaller();
   }

   final void marshalTopType(Object obj, RuntimeBindingProperty prop, RuntimeBindingType actual_rtt, MarshalOptions options) throws XmlException {
      assert prop.getName() != null : "null prop name from " + prop;

      QName name = prop.getName();

      assert name != null;

      if (obj != null || !prop.isOptional() || prop.isNillable()) {
         try {
            boolean needs_wrapped = !actual_rtt.containsOwnContainingElement(obj);
            if (needs_wrapped) {
               this.writeStartElement(name, actual_rtt.canUseDefaultNamespace(obj), options.getInitialNamespaces());
               this.addSchemaLocationAttributes(options);
            }

            this.updateState(obj, prop);
            this.writeContents(actual_rtt);
            if (needs_wrapped) {
               this.writeEndElement();
            }

         } catch (XMLStreamException var7) {
            throw new XmlException(var7);
         }
      }
   }

   protected boolean needTobindDefaultNamespace() throws XmlException {
      if (this.defaultNamespaceUri != null) {
         String default_ns_uri = this.getNamespaceContext().getNamespaceURI("");
         return !this.defaultNamespaceUri.equals(default_ns_uri);
      } else {
         return false;
      }
   }

   protected void bindDefaultNamespace() throws XmlException {
      this.bindNamespace("", this.defaultNamespaceUri);
   }

   protected void unbindDefaultNamespace() throws XmlException {
      String default_ns_uri = this.getNamespaceContext().getNamespaceURI("");
      if (default_ns_uri != null && !"".equals(default_ns_uri)) {
         this.bindNamespace("", "");
      }

   }

   private void marshalType(Object obj, RuntimeBindingProperty prop, RuntimeBindingType actual_rtt) throws XmlException {
      assert prop.getName() != null : "null prop name from " + prop;

      try {
         boolean needs_wrapped = !actual_rtt.containsOwnContainingElement(obj);
         if (needs_wrapped) {
            this.writeStartElement(prop.getName(), actual_rtt.canUseDefaultNamespace(obj));
         }

         this.updateState(obj, prop);
         this.writeContents(actual_rtt);
         if (needs_wrapped) {
            this.writeEndElement();
         }

      } catch (XMLStreamException var5) {
         throw new XmlException(var5);
      }
   }

   protected final void writeEndElement() throws XMLStreamException {
      this.writer.writeEndElement();
   }

   protected final void updateState(Object obj, RuntimeBindingProperty prop) {
      this.currObject = obj;
      this.currProp = prop;
   }

   protected final void writeXsiAttributes(RuntimeBindingType actual_rtt) throws XmlException {
      RuntimeBindingType rtt;
      if (this.currObject == null) {
         this.addXsiNilAttribute();
         rtt = this.currProp.getRuntimeBindingType();
         if (actual_rtt.needsXsiType(rtt, this)) {
            this.addXsiTypeAttribute(actual_rtt);
         }
      } else {
         rtt = this.currProp.getRuntimeBindingType();
         if (actual_rtt.needsXsiType(rtt, this)) {
            this.addXsiTypeAttribute(actual_rtt);
         }
      }

   }

   protected void writeContents(RuntimeBindingType actual_rtt) throws XmlException {
      this.writeXsiAttributes(actual_rtt);
      if (this.currObject != null) {
         actual_rtt.predefineNamespaces(this.currObject, this);
         actual_rtt.accept(this);
      }

   }

   protected final Object getCurrObject() {
      return this.currObject;
   }

   protected final void writeStartElement(QName name, boolean can_use_default_ns, Map namespaces) throws XmlException, XMLStreamException {
      assert name != null;

      String uri = name.getNamespaceURI();

      assert uri != null;

      if (namespaces != null && !namespaces.isEmpty() && !"".equals(uri)) {
         Set ns_entries = namespaces.entrySet();
         Iterator itr = ns_entries.iterator();

         while(itr.hasNext()) {
            Map.Entry e = (Map.Entry)itr.next();
            String ns = (String)e.getValue();
            if (uri.equals(ns)) {
               String pfx = (String)e.getKey();
               this.writeStartElement(name, can_use_default_ns, pfx);
               this.bindNamespaces(namespaces);
               return;
            }
         }
      }

      this.writeStartElement(name, can_use_default_ns);
      this.bindNamespaces(namespaces);
   }

   protected final void writeStartElement(QName name, boolean can_use_default_ns) throws XmlException, XMLStreamException {
      this.writeStartElement(name, can_use_default_ns, (String)null);
   }

   protected final void writeStartElement(QName name, boolean can_use_default_ns, String forced_prefix) throws XMLStreamException, XmlException {
      assert name != null;

      String uri = name.getNamespaceURI();
      boolean has_uri = !"".equals(uri);
      if (!has_uri) {
         can_use_default_ns = false;
      }

      boolean use_default_ns = can_use_default_ns && uri.equals(this.defaultNamespaceUri);
      if (has_uri && this.marshalOptions.isRootObj()) {
         String prefix;
         boolean need_default_ns;
         if (use_default_ns) {
            prefix = "";
            need_default_ns = this.needTobindDefaultNamespace();
         } else {
            prefix = forced_prefix != null ? forced_prefix : this.getNamespaceContext().getPrefix(uri);
            need_default_ns = false;
         }

         String new_prefix;
         if (prefix == null) {
            new_prefix = this.findNextPrefix(uri);
            prefix = new_prefix;
         } else {
            new_prefix = null;
         }

         assert prefix != null;

         this.writer.writeStartElement(prefix, name.getLocalPart(), name.getNamespaceURI());
         if (need_default_ns) {
            this.bindDefaultNamespace();
         }

         if (new_prefix != null) {
            this.bindNamespace(new_prefix, uri);
         }
      } else {
         this.writer.writeStartElement(name.getLocalPart());
      }

      if (!can_use_default_ns) {
         this.unbindDefaultNamespace();
      }

   }

   public NamespaceContext getNamespaceContext() {
      return this.writer.getNamespaceContext();
   }

   protected void bindNamespace(String prefix, String uri) throws XmlException {
      try {
         this.writer.writeNamespace(prefix, uri);
      } catch (XMLStreamException var4) {
         throw new XmlException(var4);
      }
   }

   protected void addAttribute(String lname, String value) throws XmlException {
      try {
         this.writer.writeAttribute(lname, value);
      } catch (XMLStreamException var4) {
         throw new XmlException(var4);
      }
   }

   protected void addAttribute(String uri, String lname, String prefix, String value) throws XmlException {
      assert uri != null;

      assert lname != null;

      assert prefix != null;

      assert value != null;

      assert checkDefaultNS(uri, prefix);

      try {
         this.writer.writeAttribute(prefix, uri, lname, value);
      } catch (XMLStreamException var6) {
         throw new XmlException(var6);
      }
   }

   public void visit(BuiltinRuntimeBindingType builtinRuntimeBindingType) throws XmlException {
      if (this.xopMarshaller != null && this.xopMarshaller.canMarshalXOP(builtinRuntimeBindingType.getBindingType().getName())) {
         this.xopMarshaller.marshalXOP(this.currObject, this.staxWriterToNode.getCurrentNode(this.writer));
      } else if (this.xopMarshaller != null && this.xopMarshaller.isMtomOverSecurity(builtinRuntimeBindingType.getBindingType().getName())) {
         this.xopMarshaller.recordBase64Element(this.staxWriterToNode.getCurrentNode(this.writer));
         this.writeCharData();
      } else {
         this.writeCharData();
      }

   }

   public void visit(AnyTypeRuntimeBindingType anyTypeRuntimeBindingType) throws XmlException {
      Object curr_obj = this.currObject;
      if (curr_obj != null) {
         anyTypeRuntimeBindingType.marshalGenericXml(this, curr_obj);
      }
   }

   public void visit(ByNameRuntimeBindingType byNameRuntimeBindingType) throws XmlException {
      Object curr_obj = this.currObject;
      this.writeAttributes(byNameRuntimeBindingType);
      int elem_cnt = byNameRuntimeBindingType.getElementPropertyCount();

      for(int i = 0; i < elem_cnt; ++i) {
         RuntimeBindingProperty prop = byNameRuntimeBindingType.getElementProperty(i);
         if (prop.isSet(curr_obj)) {
            Object prop_val = prop.getValue(curr_obj);
            if (prop.isMultiple()) {
               Iterator itr = ArrayUtils.getCollectionIterator(prop_val);

               while(itr.hasNext()) {
                  Object nextElem = itr.next();
                  if (!prop.isTransient(nextElem)) {
                     this.visitProp(nextElem, prop);
                  }
               }
            } else {
               this.visitProp(prop_val, prop);
            }
         }
      }

      if (elem_cnt == 0 && this.currObject instanceof StaxSerializer) {
         try {
            ((StaxSerializer)this.currObject).writeTo(this.writer);
         } catch (XMLStreamException var9) {
            throw new XmlException(var9);
         }
      }

   }

   public void visit(SimpleContentRuntimeBindingType simpleContentRuntimeBindingType) throws XmlException {
      this.writeAttributes(simpleContentRuntimeBindingType);
      this.writeCharData();
   }

   public void visit(SimpleRuntimeBindingType simpleRuntimeBindingType) throws XmlException {
      this.writeCharData();
   }

   public void visit(JaxrpcEnumRuntimeBindingType jaxrpcEnumRuntimeBindingType) throws XmlException {
      this.writeCharData();
   }

   public void visit(WrappedArrayRuntimeBindingType wrappedArrayRuntimeBindingType) throws XmlException {
      RuntimeBindingProperty elem_prop = wrappedArrayRuntimeBindingType.getElementProperty();
      Iterator itr = ArrayUtils.getCollectionIterator(this.currObject);

      while(itr.hasNext()) {
         Object item = itr.next();
         RuntimeBindingType actual_rtt = elem_prop.getActualRuntimeType(item, this);
         this.marshalType(item, elem_prop, actual_rtt);
      }

   }

   public void visit(SoapArrayRuntimeBindingType soapArrayRuntimeBindingType) throws XmlException {
      RuntimeBindingProperty elem_prop = soapArrayRuntimeBindingType.getElementProperty();
      this.writeSoapArrayAttributes(soapArrayRuntimeBindingType);
      this.walkSoapArray(soapArrayRuntimeBindingType.getRanks(), this.currObject, elem_prop);
   }

   private void writeSoapArrayAttributes(SoapArrayRuntimeBindingType soapArrayRuntimeBindingType) throws XmlException {
      int[] array_dims = this.getSoapArrayDims(soapArrayRuntimeBindingType, this.currObject);
      this.writeSoapArrayAttributes(soapArrayRuntimeBindingType, array_dims);
   }

   protected abstract void writeSoapArrayAttributes(SoapArrayRuntimeBindingType var1, int[] var2) throws XmlException;

   private int[] getSoapArrayDims(SoapArrayRuntimeBindingType type, Object array) throws XmlException {
      int ranks = type.getRanks();

      assert ranks > 0 : "ranks=" + ranks + " for " + type;

      int[] dims;
      if (type.isJavaCollection()) {
         Collection c = (Collection)array;
         dims = new int[]{c.size()};
      } else {
         dims = new int[ranks];
         Arrays.fill(dims, -1);
         this.fillDims(dims, ranks, array);
      }

      return dims;
   }

   private void fillDims(int[] dims, int curr_rank, Object array) throws XmlException {
      if (array != null) {
         assert array.getClass().isArray();

         assert curr_rank > 0;

         int len;
         if (curr_rank <= 1 && !(array instanceof Object[])) {
            len = Array.getLength(array);
         } else {
            Object[] a = (Object[])((Object[])array);
            len = a.length;
         }

         int dims_idx = dims.length - curr_rank;
         int curr_dim_val = dims[dims_idx];
         if (curr_dim_val == -1) {
            dims[dims_idx] = len;
         } else if (len != curr_dim_val) {
            throw new XmlException("jagged array not allowed for soap multidimensional array");
         }

         if (curr_rank > 1) {
            Object[] a = (Object[])((Object[])array);
            int array_len = a.length;
            int new_rank = curr_rank - 1;

            for(int i = 0; i < array_len; ++i) {
               Object sub_arr = a[i];
               this.fillDims(dims, new_rank, sub_arr);
            }
         }

      }
   }

   private void walkSoapArray(int ranks, Object array, RuntimeBindingProperty elem_prop) throws XmlException {
      if (array != null) {
         assert array.getClass().isArray();

         assert ranks > 0;

         if (ranks == 1) {
            this.walkRankOneSoapArray(array, elem_prop);
         } else {
            int new_rank = ranks - 1;
            Object[] a = (Object[])((Object[])array);
            int array_len = a.length;
            int first_subarray_len = array_len > 0 ? Array.getLength(a[0]) : -1;

            for(int i = 0; i < array_len; ++i) {
               Object sub_arr = a[i];
               if (Array.getLength(sub_arr) != first_subarray_len) {
                  throw new XmlException("jagged java array not allowed for multidimensional soap array for type " + elem_prop.getContainingType());
               }

               this.walkSoapArray(new_rank, sub_arr, elem_prop);
            }
         }

      }
   }

   private void walkRankOneSoapArray(Object array, RuntimeBindingProperty elem_prop) throws XmlException {
      int alen;
      if (array instanceof Object[]) {
         Object[] a = (Object[])((Object[])array);
         alen = 0;

         for(int alen = a.length; alen < alen; ++alen) {
            Object item = a[alen];
            RuntimeBindingType actual_rtt = elem_prop.getActualRuntimeType(item, this);
            this.marshalType(item, elem_prop, actual_rtt);
         }
      } else {
         Object item;
         RuntimeBindingType actual_rtt;
         if (array instanceof Collection) {
            Collection coll = (Collection)array;
            Iterator itr = coll.iterator();

            while(itr.hasNext()) {
               item = itr.next();
               actual_rtt = elem_prop.getActualRuntimeType(item, this);
               this.marshalType(item, elem_prop, actual_rtt);
            }
         } else {
            int i = 0;

            for(alen = Array.getLength(array); i < alen; ++i) {
               item = Array.get(array, i);
               actual_rtt = elem_prop.getActualRuntimeType(item, this);
               this.marshalType(item, elem_prop, actual_rtt);
            }
         }
      }

   }

   public void visit(ListArrayRuntimeBindingType listArrayRuntimeBindingType) throws XmlException {
      this.writeCharData();
   }

   private void writeAttributes(AttributeRuntimeBindingType att_rtt) throws XmlException {
      Object curr_obj = this.currObject;
      int att_cnt = att_rtt.getAttributePropertyCount();

      for(int i = 0; i < att_cnt; ++i) {
         RuntimeBindingProperty prop = att_rtt.getAttributeProperty(i);
         if (prop.isSet(curr_obj)) {
            Object prop_val = prop.getValue(curr_obj);
            CharSequence att_val = prop.getLexical(prop_val, this);
            if (att_val != null) {
               this.fillAndAddAttribute(prop.getName(), att_val.toString());
            }
         }
      }

   }

   private void visitProp(Object prop_val, RuntimeBindingProperty prop) throws XmlException {
      RuntimeBindingType rtt = prop.getActualRuntimeType(prop_val, this);
      this.marshalType(prop_val, prop, rtt);
   }

   private void writeCharData() throws XmlException {
      if (this.currObject != null) {
         CharSequence lexical = this.currProp.getLexical(this.currObject, this);

         try {
            if (lexical != null) {
               this.writer.writeCharacters(lexical.toString());
            }

         } catch (XMLStreamException var3) {
            throw new XmlException(var3);
         }
      }
   }

   void appendDomNode(Node el) {
      if (this.staxWriterToNode == null) {
         String m = "caller must supply a StaxWriterToNode instance in MarshalOptions in order to support marshalling DOM nodes";
         throw new XmlRuntimeException("caller must supply a StaxWriterToNode instance in MarshalOptions in order to support marshalling DOM nodes");
      } else {
         XMLStreamWriter w = this.getUnderlyingWriter();
         Node currentNode = this.staxWriterToNode.getCurrentNode(w);
         Document doc = currentNode.getOwnerDocument();
         Node imported_node = doc.importNode(el, true);
         currentNode.appendChild(imported_node);
      }
   }

   private XMLStreamWriter getUnderlyingWriter() {
      XMLStreamWriter w = this.writer;
      if (w instanceof PrettyXMLStreamWriter) {
         PrettyXMLStreamWriter psw = (PrettyXMLStreamWriter)w;
         w = psw.getDelegate();
      }

      return w;
   }
}
