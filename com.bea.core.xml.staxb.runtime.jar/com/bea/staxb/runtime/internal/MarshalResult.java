package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingType;
import com.bea.staxb.buildtime.internal.bts.BindingTypeName;
import com.bea.staxb.buildtime.internal.bts.DefaultBuiltinBindingLoader;
import com.bea.staxb.buildtime.internal.bts.SoapArrayType;
import com.bea.staxb.runtime.MarshalOptions;
import com.bea.staxb.types.XMLGregorianCalendar;
import com.bea.xbean.common.QNameHelper;
import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.XmlError;
import com.bea.xml.XmlException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import org.w3c.dom.Node;

abstract class MarshalResult {
   private final BindingLoader bindingLoader;
   private final RuntimeBindingTypeTable typeTable;
   private final boolean walkTypes;
   private final boolean forceXsiType;
   private final boolean forceOracle1012Compatible;
   private final Collection errors;
   private int prefixCnt = 0;
   private static final String NSPREFIX = "n";
   private static final boolean PRETTY_PREFIX = true;
   private final boolean forceDotNetCompatibleMarshal;

   MarshalResult(BindingLoader loader, RuntimeBindingTypeTable tbl, MarshalOptions options) throws XmlException {
      assert options != null;

      this.bindingLoader = loader;
      this.typeTable = tbl;
      this.walkTypes = options.isCheckSupertypes();
      this.forceXsiType = options.isForceIncludeXsiType();
      this.forceOracle1012Compatible = options.isForceOracle1012Compatible();
      this.errors = BindingContextImpl.extractErrorHandler(options);
      this.forceDotNetCompatibleMarshal = options.isForceDotNetCompatibleMarshal();
   }

   protected final void resetPrefixCount() {
      this.prefixCnt = 0;
   }

   public abstract NamespaceContext getNamespaceContext();

   protected final String ensureElementPrefix(String uri) throws XmlException {
      assert uri != null;

      assert uri.length() > 0;

      String prefix = this.getNamespaceContext().getPrefix(uri);
      if (prefix == null) {
         prefix = this.bindNextPrefix(uri);
      }

      assert prefix != null;

      return prefix;
   }

   private String ensureAttributePrefix(String uri) throws XmlException {
      assert uri != null;

      assert uri.length() > 0;

      String prefix = this.getNamespaceContext().getPrefix(uri);
      if ("".equals(prefix)) {
         prefix = null;
         Iterator prefixes = this.getNamespaceContext().getPrefixes(uri);

         while(prefixes.hasNext()) {
            String candidate = (String)prefixes.next();
            if (!"".equals(candidate)) {
               prefix = candidate;
               break;
            }
         }
      }

      if (prefix == null) {
         prefix = this.bindNextPrefix(uri);
      }

      assert prefix != null;

      return prefix;
   }

   private String bindNextPrefix(String uri) throws XmlException {
      String prefix = this.findNextPrefix(uri);
      this.bindNamespace(prefix, uri);
      return prefix;
   }

   protected final String findNextPrefix(String uri) {
      assert uri != null;

      String prefix = QNameHelper.suggestPrefix(uri);

      while(true) {
         String testuri = this.getNamespaceContext().getNamespaceURI(prefix);
         if (testuri == null) {
            assert prefix != null;

            return prefix;
         }

         prefix = this.nextNumberedPrefix();
      }
   }

   private String nextNumberedPrefix() {
      return "n" + ++this.prefixCnt;
   }

   protected abstract void bindNamespace(String var1, String var2) throws XmlException;

   final void addXsiNilAttribute() throws XmlException {
      this.addAttribute("http://www.w3.org/2001/XMLSchema-instance", "nil", this.ensureAttributePrefix("http://www.w3.org/2001/XMLSchema-instance"), NamedXmlTypeVisitor.TRUE_LEX);
   }

   final void addXsiTypeAttribute(RuntimeBindingType rtt) throws XmlException {
      QName schema_type = rtt.getXsiTypeName();
      String type_uri = schema_type.getNamespaceURI();

      assert type_uri != null;

      assert type_uri.length() > 0;

      String aval = XsTypeConverter.getQNameString(type_uri, schema_type.getLocalPart(), this.ensureElementPrefix(type_uri));
      this.addAttribute("http://www.w3.org/2001/XMLSchema-instance", "type", this.ensureAttributePrefix("http://www.w3.org/2001/XMLSchema-instance"), aval);
      if (this.forceOracle1012Compatible && Soap11Constants.ARRAY_NAME.equals(schema_type)) {
         SoapArrayType bindingType = (SoapArrayType)rtt.getBindingType();
         QName itemTypeName = bindingType.getItemType().getXmlName().getQName();
         String aval1 = XsTypeConverter.getQNameString(itemTypeName.getNamespaceURI(), itemTypeName.getLocalPart(), this.ensureElementPrefix(itemTypeName.getNamespaceURI()));
         this.addAttribute(Soap11Constants.ARRAY_TYPE_NAME.getNamespaceURI(), Soap11Constants.ARRAY_TYPE_NAME.getLocalPart(), this.ensureAttributePrefix(Soap11Constants.ARRAY_TYPE_NAME.getNamespaceURI()), aval1 + "[]");
      }

   }

   final void fillAndAddAttribute(QName qname_without_prefix, String value) throws XmlException {
      String uri = qname_without_prefix.getNamespaceURI();
      if (uri.length() == 0) {
         this.addAttribute(qname_without_prefix.getLocalPart(), value);
      } else {
         this.addAttribute(uri, qname_without_prefix.getLocalPart(), this.ensureAttributePrefix(uri), value);
      }

   }

   protected abstract void addAttribute(String var1, String var2) throws XmlException;

   protected abstract void addAttribute(String var1, String var2, String var3, String var4) throws XmlException;

   static boolean checkDefaultNS(String uri, String prefix) {
      if (!"".equals(prefix)) {
         return true;
      } else {
         return uri == null || "".equals(uri);
      }
   }

   final RuntimeBindingType determineDefaultRuntimeBindingType(RuntimeBindingType expected, Object instance) throws XmlException {
      return this.determineRuntimeBindingTypeWithLoader(expected, instance, DefaultBuiltinBindingLoader.getInstance());
   }

   final RuntimeBindingType determineRuntimeBindingType(RuntimeBindingType expected, Object instance) throws XmlException {
      return this.determineRuntimeBindingTypeWithLoader(expected, instance, this.bindingLoader);
   }

   private final RuntimeBindingType determineRuntimeBindingTypeWithLoader(RuntimeBindingType expected, Object instance, BindingLoader loader) throws XmlException {
      BindingTypeName type_name;
      if (this.forceDotNetCompatibleMarshal) {
         BindingTypeName name = expected.getBindingType().getName();
         type_name = BindingTypeNameRegistry.getInstance().getBTNForTyped(name);
         if (type_name != null) {
            return this.createRuntimeBindingType(type_name);
         }
      }

      if (instance != null && expected.canHaveSubtype()) {
         Class instance_class = instance.getClass();
         if (instance_class.equals(expected.getJavaType()) || instance instanceof Calendar && !(instance instanceof XMLGregorianCalendar) && !expected.getJavaType().equals(Object.class)) {
            return expected;
         } else {
            type_name = expected.getBindingType().getName();
            if (!type_name.getJavaName().isNameForClass(instance_class)) {
               BindingType actual_type = MarshallerImpl.lookupBindingType(instance_class, type_name.getJavaName(), type_name.getXmlName(), loader, this.walkTypes);
               if (actual_type != null) {
                  return this.typeTable.createRuntimeType(actual_type, loader);
               }
            }

            return expected;
         }
      } else {
         return expected;
      }
   }

   public final boolean isForceXsiType() {
      return this.forceXsiType;
   }

   public final boolean isForceOracle1012Compatible() {
      return this.forceOracle1012Compatible;
   }

   final Collection getErrorCollection() {
      return this.errors;
   }

   void addError(String msg) {
      XmlError err = XmlError.forMessage(msg);
      this.getErrorCollection().add(err);
   }

   RuntimeBindingType createRuntimeBindingType(BindingTypeName type_name) throws XmlException {
      BindingType btype = this.bindingLoader.getBindingType(type_name);
      if (btype == null) {
         throw new XmlException("unable to load type " + type_name);
      } else {
         return this.typeTable.createRuntimeType(btype, this.bindingLoader);
      }
   }

   abstract void appendDomNode(Node var1);

   protected final void bindDefaultNS(MarshalOptions options, RuntimeBindingProperty property) throws XmlException {
      if (options.isUseDefaultNamespaceForRootElement()) {
         String uri = property.getName().getNamespaceURI();
         if (uri.length() > 0) {
            this.bindNamespace("", uri);
         }
      }

   }

   protected void addSchemaLocationAttributes(MarshalOptions options) throws XmlException {
      String nnsl = options.getSchemaLocation();
      if (nnsl != null) {
         this.addAttribute("http://www.w3.org/2001/XMLSchema-instance", "schemaLocation", this.ensureAttributePrefix("http://www.w3.org/2001/XMLSchema-instance"), nnsl);
      }

      nnsl = options.getNoNamespaceschemaLocation();
      if (nnsl != null) {
         this.addAttribute("http://www.w3.org/2001/XMLSchema-instance", "noNamespaceSchemaLocation", this.ensureAttributePrefix("http://www.w3.org/2001/XMLSchema-instance"), nnsl);
      }

   }

   protected final void bindNamespaces(Map namespaces) throws XmlException {
      if (namespaces != null) {
         if (!namespaces.isEmpty()) {
            Set ns_entries = namespaces.entrySet();
            Iterator itr = ns_entries.iterator();

            while(itr.hasNext()) {
               Map.Entry e = (Map.Entry)itr.next();
               String pfx = (String)e.getKey();
               String ns = (String)e.getValue();
               this.bindNamespace(pfx, ns);
            }

         }
      }
   }
}
