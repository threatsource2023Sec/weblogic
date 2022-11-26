package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingType;
import com.bea.staxb.buildtime.internal.bts.BindingTypeName;
import com.bea.staxb.buildtime.internal.bts.JavaTypeName;
import com.bea.staxb.buildtime.internal.bts.SimpleDocumentBinding;
import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.staxb.runtime.MarshalOptions;
import com.bea.staxb.runtime.Marshaller;
import com.bea.staxb.runtime.StaxWriterToNode;
import com.bea.staxb.runtime.internal.util.PrettyXMLStreamWriter;
import com.bea.xml.SchemaTypeLoader;
import com.bea.xml.XmlException;
import java.io.OutputStream;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import org.w3c.dom.Node;
import weblogic.xml.stax.XMLStreamOutputFactory;

final class MarshallerImpl implements Marshaller {
   private final BindingLoader loader;
   private final RuntimeBindingTypeTable typeTable;
   private final SchemaTypeLoaderProvider schemaTypeLoaderProvider;
   private static final XMLOutputFactory XML_OUTPUT_FACTORY = new XMLStreamOutputFactory();
   private static final String XML_VERSION = "1.0";

   public MarshallerImpl(BindingLoader loader, RuntimeBindingTypeTable typeTable, SchemaTypeLoaderProvider schemaTypeLoaderProvider) {
      this.loader = loader;
      this.typeTable = typeTable;
      this.schemaTypeLoaderProvider = schemaTypeLoaderProvider;
   }

   private XmlTypeName lookupRootElementName(JavaTypeName jname, MarshalOptions options) throws XmlException {
      BindingTypeName root_elem_btype = options.isRootObj() ? this.loader.lookupElementFor(jname) : this.loader.lookupTypeFor(jname);
      if (root_elem_btype == null) {
         String msg = "failed to find root element corresponding to " + jname;
         throw new XmlException(msg);
      } else {
         XmlTypeName elem = root_elem_btype.getXmlName();

         assert elem.getComponentType() == 101;

         return elem;
      }
   }

   private PullMarshalResult createMarshalResult(BindingType btype, QName elem_qn, Object obj, MarshalOptions options) throws XmlException {
      assert btype != null;

      RuntimeBindingType runtime_type = this.typeTable.createRuntimeType(btype, this.loader);
      runtime_type.checkInstance(obj);
      RuntimeGlobalProperty prop = new RuntimeGlobalProperty(elem_qn, runtime_type);
      return new LiteralMarshalResult(this.loader, this.typeTable, prop, obj, options);
   }

   public XMLStreamReader marshal(Object obj, MarshalOptions options) throws XmlException {
      options = maskNull(options);
      JavaTypeName jname = determineJavaType(obj);
      XmlTypeName elem = this.lookupRootElementName(jname, options);
      BindingType btype = this.loadBindingTypeForGlobalElem(elem, jname, obj, options);
      return this.createMarshalResult(btype, elem.getQName(), obj, options);
   }

   private static JavaTypeName determineJavaType(Object obj) {
      return determineJavaType(obj.getClass());
   }

   private static JavaTypeName determineJavaType(Class clazz) {
      return JavaTypeName.forClassName(clazz.getName());
   }

   public void marshal(XMLStreamWriter writer, Object obj) throws XmlException {
      this.marshal(writer, obj, MarshalOptions.getDefaults());
   }

   public void marshal(XMLStreamWriter writer, Object obj, MarshalOptions options) throws XmlException {
      options = maskNull(options);
      writer = makeStreamPretty(options, writer);
      JavaTypeName jname = determineJavaType(obj);
      XmlTypeName elem = this.lookupRootElementName(jname, options);
      BindingType btype = this.loadBindingTypeForGlobalElem(elem, jname, obj, options);
      String el = options.isRootObj() ? null : elem.getQName().getLocalPart().replace("Type", "");
      String encoding = getEncoding(options);

      try {
         if (encoding != null) {
            writer.writeStartDocument(encoding, "1.0");
         }

         if (el != null) {
            this.marshalBindingType(writer, btype, obj, new QName(el), options);
         } else {
            this.marshalBindingType(writer, btype, obj, elem.getQName(), options);
         }

         writer.writeEndDocument();
      } catch (XMLStreamException var10) {
         throw new XmlException(var10);
      }
   }

   private BindingType loadBindingTypeForGlobalElem(XmlTypeName elem, JavaTypeName jname, Object obj, MarshalOptions options) throws XmlException {
      XmlTypeName elem_type = options.isRootObj() ? this.determineDocumentType(elem).getTypeOfElement() : elem;
      BindingType btype = loadBindingType(elem_type, jname, this.loader);
      if (btype == null) {
         String msg = "failed to find a suitable binding type for use in marshalling object \"" + obj + "\".  using schema type: " + elem_type + " java type: " + jname;
         throw new XmlException(msg);
      } else {
         return btype;
      }
   }

   private static String getEncoding(MarshalOptions options) {
      return options == null ? null : options.getCharacterEncoding();
   }

   public void marshal(OutputStream out, Object obj) throws XmlException {
      this.marshal(out, obj, MarshalOptions.getDefaults());
   }

   public void marshal(OutputStream out, Object obj, MarshalOptions options) throws XmlException {
      options = maskNull(options);
      String encoding = getEncoding(options);

      try {
         XMLStreamWriter writer = createXmlStreamWriter(out, encoding);
         this.marshal(writer, obj, options);
         writer.close();
      } catch (XMLStreamException var6) {
         throw new XmlException(var6);
      }
   }

   private static XMLStreamWriter makeStreamPretty(MarshalOptions options, XMLStreamWriter writer) {
      if (options.isPrettyPrint()) {
         writer = new PrettyXMLStreamWriter((XMLStreamWriter)writer, options.getPrettyPrintIndent());
      }

      return (XMLStreamWriter)writer;
   }

   public XMLStreamReader marshalType(Object obj, QName elementName, QName schemaType, String javaType, MarshalOptions options) throws XmlException {
      options = maskNull(options);
      BindingType type = lookupBindingType(schemaType, javaType, elementName, obj, this.loader);
      return this.createMarshalResult(type, elementName, obj, options);
   }

   private static XMLStreamWriter createXmlStreamWriter(OutputStream out, String encoding) throws XMLStreamException {
      XMLStreamWriter writer;
      if (encoding != null) {
         writer = XML_OUTPUT_FACTORY.createXMLStreamWriter(out, encoding);
      } else {
         writer = XML_OUTPUT_FACTORY.createXMLStreamWriter(out);
      }

      return writer;
   }

   private static BindingType lookupBindingType(QName schemaType, String javaType, QName elementName, Object obj, BindingLoader loader) throws XmlException {
      return lookupBindingType(XmlTypeName.forTypeNamed(schemaType), javaType, elementName, obj, loader);
   }

   static BindingType lookupBindingType(XmlTypeName schema_type, String javaType, QName elementName, Object obj, BindingLoader loader) throws XmlException {
      BindingType type = loadBindingType(schema_type, JavaTypeName.forClassName(javaType), loader);
      if (type == null) {
         String msg = "failed to find a suitable binding type for use in marshalling \"" + elementName + "\".  using java type: " + javaType + " schema type: " + schema_type + " instance type: " + (obj == null ? "null" : obj.getClass().getName());
         throw new XmlException(msg);
      } else {
         return type;
      }
   }

   private void marshalBindingType(XMLStreamWriter writer, BindingType btype, Object obj, QName elementName, MarshalOptions options) throws XmlException {
      RuntimeBindingType runtime_type = this.typeTable.createRuntimeType(btype, this.loader);
      SchemaTypeLoader schemaTypeLoader = this.getSchemaTypeLoader();
      StaxWriterToNode swtn = options.getStaxWriterToNode();
      Node parent = null;
      if (swtn != null) {
         parent = swtn.getCurrentNode(writer);
      }

      runtime_type.checkInstance(obj);
      RuntimeGlobalProperty prop = new RuntimeGlobalProperty(elementName, runtime_type, parent, schemaTypeLoader);
      PushMarshalResult pmr = new LiteralPushMarshalResult(this.loader, this.typeTable, writer, getDefaultNamespaceUri(options, elementName), options);
      RuntimeBindingType actual_rtt = prop.getActualRuntimeType(obj, pmr);
      pmr.marshalTopType(obj, prop, actual_rtt, options);
   }

   private SchemaTypeLoader getSchemaTypeLoader() {
      SchemaTypeLoader schemaTypeLoader = null;

      try {
         schemaTypeLoader = this.schemaTypeLoaderProvider.getSchemaTypeLoader();
      } catch (XmlException var3) {
      }

      return schemaTypeLoader;
   }

   private static String getDefaultNamespaceUri(MarshalOptions options, QName elementName) {
      String default_ns;
      if (options.isUseDefaultNamespaceForRootElement()) {
         String uri = elementName.getNamespaceURI();
         if ("".equals(uri)) {
            default_ns = null;
         } else {
            default_ns = uri;
         }
      } else {
         default_ns = null;
      }

      return default_ns;
   }

   public void marshalElement(XMLStreamWriter writer, Object obj, QName elementName, String javaType, MarshalOptions options) throws XmlException {
      options = maskNull(options);
      if (writer == null) {
         throw new IllegalArgumentException("null writer");
      } else {
         writer = makeStreamPretty(options, writer);
         XmlTypeName elem_name = XmlTypeName.forGlobalName('e', elementName);
         BindingType btype = this.loadBindingTypeForGlobalElem(elem_name, JavaTypeName.forClassName(javaType), obj, options);
         this.marshalBindingType(writer, btype, obj, elementName, options);
      }
   }

   public void marshalElement(XMLStreamWriter writer, Object obj, XmlTypeName elementName, String javaType, MarshalOptions options) throws XmlException {
      options = maskNull(options);
      if (writer == null) {
         throw new IllegalArgumentException("null writer");
      } else {
         writer = makeStreamPretty(options, writer);
         QName elem_qname = elementName.getQName();
         if (elem_qname == null) {
            throw new IllegalArgumentException("invalid element name (must have a qname): " + elementName);
         } else {
            BindingType btype = this.loadBindingTypeForGlobalElem(elementName, JavaTypeName.forClassName(javaType), obj, options);
            this.marshalBindingType(writer, btype, obj, elem_qname, options);
         }
      }
   }

   public void marshalType(XMLStreamWriter writer, Object obj, QName elementName, QName schemaType, String javaType) throws XmlException {
      this.marshalType(writer, obj, elementName, schemaType, javaType, MarshalOptions.getDefaults());
   }

   public void marshalType(XMLStreamWriter writer, Object obj, QName elementName, QName schemaType, String javaType, MarshalOptions options) throws XmlException {
      options = maskNull(options);
      writer = makeStreamPretty(options, writer);
      BindingType btype = lookupBindingType(schemaType, javaType, elementName, obj, this.loader);

      assert btype != null;

      this.marshalBindingType(writer, btype, obj, elementName, options);
   }

   public void marshalType(XMLStreamWriter writer, Object obj, QName elementName, XmlTypeName schemaType, String javaType, MarshalOptions options) throws XmlException {
      options = maskNull(options);
      writer = makeStreamPretty(options, writer);
      BindingType btype = lookupBindingType(schemaType, javaType, elementName, obj, this.loader);

      assert btype != null;

      this.marshalBindingType(writer, btype, obj, elementName, options);
   }

   public void marshalType(XMLStreamWriter writer, Object obj, XmlTypeName elementName, XmlTypeName schemaType, String javaType, MarshalOptions options) throws XmlException {
      options = maskNull(options);
      writer = makeStreamPretty(options, writer);
      QName elem_qname = elementName.getQName();
      if (elem_qname == null) {
         throw new IllegalArgumentException("invalid element name (must have a qname): " + elementName);
      } else {
         BindingType btype = lookupBindingType(schemaType, javaType, elem_qname, obj, this.loader);

         assert btype != null;

         this.marshalBindingType(writer, btype, obj, elem_qname, options);
      }
   }

   static MarshalOptions maskNull(MarshalOptions options) {
      return options == null ? MarshalOptions.getDefaults() : options;
   }

   public XMLStreamReader marshalElement(Object obj, QName elementName, String javaType, MarshalOptions options) throws XmlException {
      options = maskNull(options);
      if (elementName == null) {
         throw new IllegalArgumentException("null elementName");
      } else if (javaType == null) {
         throw new IllegalArgumentException("null javaType");
      } else {
         SimpleDocumentBinding doc_binding = this.determineDocumentType(elementName);
         XmlTypeName elem_type = doc_binding.getTypeOfElement();
         BindingType type = lookupBindingType(elem_type, javaType, elementName, obj, this.loader);
         return this.createMarshalResult(type, elementName, obj, options);
      }
   }

   static NamespaceContext getNamespaceContextFromOptions(MarshalOptions options) {
      if (options != null) {
         NamespaceContext ctx = options.getNamespaceContext();
         if (ctx != null) {
            return ctx;
         }
      }

      return EmptyNamespaceContext.getInstance();
   }

   private SimpleDocumentBinding determineDocumentType(QName global_element) throws XmlException {
      XmlTypeName type_name = XmlTypeName.forGlobalName('e', global_element);
      return this.determineDocumentType(type_name);
   }

   private SimpleDocumentBinding determineDocumentType(XmlTypeName global_element) throws XmlException {
      BindingType doc_binding_type = this.getPojoBindingType(global_element);

      assert doc_binding_type != null;

      return (SimpleDocumentBinding)doc_binding_type;
   }

   private BindingType getPojoBindingType(XmlTypeName type_name) throws XmlException {
      BindingTypeName btName = this.loader.lookupPojoFor(type_name);
      if (btName == null) {
         String msg = "failed to load java type corresponding to " + type_name;
         throw new XmlException(msg);
      } else {
         BindingType bt = this.loader.getBindingType(btName);
         if (bt == null) {
            String msg = "failed to load BindingType for " + btName;
            throw new XmlException(msg);
         } else {
            return bt;
         }
      }
   }

   static BindingType lookupBindingType(Class instance_type, JavaTypeName java_type, XmlTypeName xml_type, BindingLoader loader, boolean check_supertypes) throws XmlException {
      JavaTypeName binding_type = determineJavaType(instance_type);
      BindingType bt = loadBindingType(xml_type, binding_type, loader);
      if (bt != null) {
         return bt;
      } else {
         binding_type = null;
         Class curr_class = instance_type;

         do {
            JavaTypeName jname = determineJavaType(curr_class);
            BindingTypeName btype_name = loader.lookupTypeFor(jname);
            if (btype_name != null) {
               BindingType binding_type = loader.getBindingType(btype_name);
               if (binding_type == null) {
                  String e = "binding configuration inconsistency: found " + btype_name + " defined for " + jname + " but failed to load the type";
                  throw new XmlException(e);
               }

               return binding_type;
            }

            Class super_type = curr_class.getSuperclass();
            if (super_type == null || super_type.getSuperclass() == null) {
               break;
            }

            curr_class = super_type;
         } while(check_supertypes);

         assert binding_type == null;

         return loadBindingType(xml_type, java_type, loader);
      }
   }

   private static BindingType loadBindingType(XmlTypeName xname, JavaTypeName jname, BindingLoader loader) {
      BindingTypeName btname = BindingTypeName.forPair(jname, xname);
      return loader.getBindingType(btname);
   }
}
