package org.apache.xmlbeans.impl.values;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamReader;
import org.apache.xmlbeans.DelegateXmlObject;
import org.apache.xmlbeans.GDate;
import org.apache.xmlbeans.GDateSpecification;
import org.apache.xmlbeans.GDuration;
import org.apache.xmlbeans.GDurationSpecification;
import org.apache.xmlbeans.QNameSet;
import org.apache.xmlbeans.SchemaAttributeModel;
import org.apache.xmlbeans.SchemaField;
import org.apache.xmlbeans.SchemaLocalAttribute;
import org.apache.xmlbeans.SchemaProperty;
import org.apache.xmlbeans.SchemaType;
import org.apache.xmlbeans.SchemaTypeLoader;
import org.apache.xmlbeans.SimpleValue;
import org.apache.xmlbeans.StringEnumAbstractBase;
import org.apache.xmlbeans.XmlAnySimpleType;
import org.apache.xmlbeans.XmlBeans;
import org.apache.xmlbeans.XmlCursor;
import org.apache.xmlbeans.XmlDocumentProperties;
import org.apache.xmlbeans.XmlError;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.XmlOptions;
import org.apache.xmlbeans.XmlRuntimeException;
import org.apache.xmlbeans.impl.common.GlobalLock;
import org.apache.xmlbeans.impl.common.QNameHelper;
import org.apache.xmlbeans.impl.common.ValidationContext;
import org.apache.xmlbeans.impl.common.XmlErrorWatcher;
import org.apache.xmlbeans.impl.common.XmlLocale;
import org.apache.xmlbeans.impl.common.XmlWhitespace;
import org.apache.xmlbeans.impl.schema.SchemaTypeImpl;
import org.apache.xmlbeans.impl.schema.SchemaTypeVisitorImpl;
import org.apache.xmlbeans.impl.validator.Validator;
import org.apache.xmlbeans.xml.stream.XMLInputStream;
import org.w3c.dom.Node;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public abstract class XmlObjectBase implements TypeStoreUser, Serializable, XmlObject, SimpleValue {
   static final boolean isRepackagedXmlBeans = XmlObjectBase.class.getPackage().getName().startsWith("com.bea");
   public static final short MAJOR_VERSION_NUMBER = 1;
   public static final short MINOR_VERSION_NUMBER = 1;
   public static final short KIND_SETTERHELPER_SINGLETON = 1;
   public static final short KIND_SETTERHELPER_ARRAYITEM = 2;
   public static final ValidationContext _voorVc = new ValueOutOfRangeValidationContext();
   private int _flags = 65;
   private Object _textsource;
   private static final int FLAG_NILLABLE = 1;
   private static final int FLAG_HASDEFAULT = 2;
   private static final int FLAG_FIXED = 4;
   private static final int FLAG_ATTRIBUTE = 8;
   private static final int FLAG_STORE = 16;
   private static final int FLAG_VALUE_DATED = 32;
   private static final int FLAG_NIL = 64;
   private static final int FLAG_NIL_DATED = 128;
   private static final int FLAG_ISDEFAULT = 256;
   private static final int FLAG_ELEMENT_DATED = 512;
   private static final int FLAG_SETTINGDEFAULT = 1024;
   private static final int FLAG_ORPHANED = 2048;
   private static final int FLAG_IMMUTABLE = 4096;
   private static final int FLAG_COMPLEXTYPE = 8192;
   private static final int FLAG_COMPLEXCONTENT = 16384;
   private static final int FLAG_NOT_VARIABLE = 32768;
   private static final int FLAG_VALIDATE_ON_SET = 65536;
   private static final int FLAGS_DATED = 672;
   private static final int FLAGS_ELEMENT = 7;
   private static final BigInteger _max = BigInteger.valueOf(Long.MAX_VALUE);
   private static final BigInteger _min = BigInteger.valueOf(Long.MIN_VALUE);
   private static final XmlOptions _toStringOptions = buildInnerPrettyOptions();
   private static final XmlObject[] EMPTY_RESULT = new XmlObject[0];

   public final Object monitor() {
      return this.has_store() ? this.get_store().get_locale() : this;
   }

   private static XmlObjectBase underlying(XmlObject obj) {
      if (obj == null) {
         return null;
      } else if (obj instanceof XmlObjectBase) {
         return (XmlObjectBase)obj;
      } else {
         while(obj instanceof DelegateXmlObject) {
            obj = ((DelegateXmlObject)obj).underlyingXmlObject();
         }

         if (obj instanceof XmlObjectBase) {
            return (XmlObjectBase)obj;
         } else {
            throw new IllegalStateException("Non-native implementations of XmlObject should extend FilterXmlObject or implement DelegateXmlObject");
         }
      }
   }

   public final XmlObject copy() {
      if (this.preCheck()) {
         return this._copy();
      } else {
         synchronized(this.monitor()) {
            return this._copy();
         }
      }
   }

   public final XmlObject copy(XmlOptions options) {
      if (this.preCheck()) {
         return this._copy(options);
      } else {
         synchronized(this.monitor()) {
            return this._copy(options);
         }
      }
   }

   private boolean preCheck() {
      return this.has_store() ? this.get_store().get_locale().noSync() : false;
   }

   public final XmlObject _copy() {
      return this._copy((XmlOptions)null);
   }

   public final XmlObject _copy(XmlOptions xmlOptions) {
      if (this.isImmutable()) {
         return this;
      } else {
         this.check_orphaned();
         SchemaTypeLoader stl = this.get_store().get_schematypeloader();
         XmlObject result = (XmlObject)this.get_store().copy(stl, this.schemaType(), xmlOptions);
         return result;
      }
   }

   public XmlDocumentProperties documentProperties() {
      XmlCursor cur = this.newCursorForce();

      XmlDocumentProperties var2;
      try {
         var2 = cur.documentProperties();
      } finally {
         cur.dispose();
      }

      return var2;
   }

   /** @deprecated */
   public XMLInputStream newXMLInputStream() {
      return this.newXMLInputStream((XmlOptions)null);
   }

   /** @deprecated */
   public XMLInputStream newXMLInputStream(XmlOptions options) {
      XmlCursor cur = this.newCursorForce();

      XMLInputStream var3;
      try {
         var3 = cur.newXMLInputStream(makeInnerOptions(options));
      } finally {
         cur.dispose();
      }

      return var3;
   }

   public XMLStreamReader newXMLStreamReader() {
      return this.newXMLStreamReader((XmlOptions)null);
   }

   public XMLStreamReader newXMLStreamReader(XmlOptions options) {
      XmlCursor cur = this.newCursorForce();

      XMLStreamReader var3;
      try {
         var3 = cur.newXMLStreamReader(makeInnerOptions(options));
      } finally {
         cur.dispose();
      }

      return var3;
   }

   public InputStream newInputStream() {
      return this.newInputStream((XmlOptions)null);
   }

   public InputStream newInputStream(XmlOptions options) {
      XmlCursor cur = this.newCursorForce();

      InputStream var3;
      try {
         var3 = cur.newInputStream(makeInnerOptions(options));
      } finally {
         cur.dispose();
      }

      return var3;
   }

   public Reader newReader() {
      return this.newReader((XmlOptions)null);
   }

   public Reader newReader(XmlOptions options) {
      XmlCursor cur = this.newCursorForce();

      Reader var3;
      try {
         var3 = cur.newReader(makeInnerOptions(options));
      } finally {
         cur.dispose();
      }

      return var3;
   }

   public Node getDomNode() {
      XmlCursor cur = this.newCursorForce();

      Node var2;
      try {
         var2 = cur.getDomNode();
      } finally {
         cur.dispose();
      }

      return var2;
   }

   public Node newDomNode() {
      return this.newDomNode((XmlOptions)null);
   }

   public Node newDomNode(XmlOptions options) {
      XmlCursor cur = this.newCursorForce();

      Node var3;
      try {
         var3 = cur.newDomNode(makeInnerOptions(options));
      } finally {
         cur.dispose();
      }

      return var3;
   }

   public void save(ContentHandler ch, LexicalHandler lh, XmlOptions options) throws SAXException {
      XmlCursor cur = this.newCursorForce();

      try {
         cur.save(ch, lh, makeInnerOptions(options));
      } finally {
         cur.dispose();
      }

   }

   public void save(File file, XmlOptions options) throws IOException {
      XmlCursor cur = this.newCursorForce();

      try {
         cur.save(file, makeInnerOptions(options));
      } finally {
         cur.dispose();
      }

   }

   public void save(OutputStream os, XmlOptions options) throws IOException {
      XmlCursor cur = this.newCursorForce();

      try {
         cur.save(os, makeInnerOptions(options));
      } finally {
         cur.dispose();
      }

   }

   public void save(Writer w, XmlOptions options) throws IOException {
      XmlCursor cur = this.newCursorForce();

      try {
         cur.save(w, makeInnerOptions(options));
      } finally {
         cur.dispose();
      }

   }

   public void save(ContentHandler ch, LexicalHandler lh) throws SAXException {
      this.save(ch, lh, (XmlOptions)null);
   }

   public void save(File file) throws IOException {
      this.save((File)file, (XmlOptions)null);
   }

   public void save(OutputStream os) throws IOException {
      this.save((OutputStream)os, (XmlOptions)null);
   }

   public void save(Writer w) throws IOException {
      this.save((Writer)w, (XmlOptions)null);
   }

   public void dump() {
      XmlCursor cur = this.newCursorForce();

      try {
         cur.dump();
      } finally {
         cur.dispose();
      }

   }

   public XmlCursor newCursorForce() {
      synchronized(this.monitor()) {
         return this.ensureStore().newCursor();
      }
   }

   private XmlObject ensureStore() {
      if ((this._flags & 16) != 0) {
         return this;
      } else {
         this.check_dated();
         String value = (this._flags & 64) != 0 ? "" : this.compute_text(this.has_store() ? this.get_store() : null);
         XmlOptions options = (new XmlOptions()).setDocumentType(this.schemaType());
         XmlObject x = XmlObject.Factory.newInstance(options);
         XmlCursor c = x.newCursor();
         c.toNextToken();
         c.insertChars(value);
         return x;
      }
   }

   private static XmlOptions makeInnerOptions(XmlOptions options) {
      XmlOptions innerOptions = new XmlOptions(options);
      innerOptions.put("SAVE_INNER");
      return innerOptions;
   }

   public XmlCursor newCursor() {
      if ((this._flags & 16) == 0) {
         throw new IllegalStateException("XML Value Objects cannot create cursors");
      } else {
         this.check_orphaned();
         XmlLocale l = this.getXmlLocale();
         if (l.noSync()) {
            l.enter();

            XmlCursor var2;
            try {
               var2 = this.get_store().new_cursor();
            } finally {
               l.exit();
            }

            return var2;
         } else {
            synchronized(l) {
               l.enter();

               XmlCursor var3;
               try {
                  var3 = this.get_store().new_cursor();
               } finally {
                  l.exit();
               }

               return var3;
            }
         }
      }
   }

   public abstract SchemaType schemaType();

   public SchemaType instanceType() {
      synchronized(this.monitor()) {
         return this.isNil() ? null : this.schemaType();
      }
   }

   private SchemaField schemaField() {
      SchemaType st = this.schemaType();
      SchemaField field = st.getContainerField();
      if (field == null) {
         field = this.get_store().get_schema_field();
      }

      return field;
   }

   public boolean validate() {
      return this.validate((XmlOptions)null);
   }

   public boolean validate(XmlOptions options) {
      if ((this._flags & 16) == 0) {
         if ((this._flags & 4096) != 0) {
            return this.validate_immutable(options);
         } else {
            throw new IllegalStateException("XML objects with no underlying store cannot be validated");
         }
      } else {
         synchronized(this.monitor()) {
            if ((this._flags & 2048) != 0) {
               throw new XmlValueDisconnectedException();
            } else {
               SchemaField field = this.schemaField();
               SchemaType type = this.schemaType();
               TypeStore typeStore = this.get_store();
               Validator validator = new Validator(type, field, typeStore.get_schematypeloader(), options, (Collection)null);
               typeStore.validate(validator);
               return validator.isValid();
            }
         }
      }
   }

   private boolean validate_immutable(XmlOptions options) {
      Collection errorListener = options == null ? null : (Collection)options.get("ERROR_LISTENER");
      XmlErrorWatcher watcher = new XmlErrorWatcher(errorListener);
      if (!this.schemaType().isSimpleType() && (options == null || !options.hasOption("VALIDATE_TEXT_ONLY"))) {
         SchemaProperty[] properties = this.schemaType().getProperties();

         for(int i = 0; i < properties.length; ++i) {
            if (properties[i].getMinOccurs().signum() > 0) {
               if (properties[i].isAttribute()) {
                  watcher.add(XmlError.forObject("cvc-complex-type.4", new Object[]{QNameHelper.pretty(properties[i].getName())}, this));
               } else {
                  watcher.add(XmlError.forObject("cvc-complex-type.2.4c", new Object[]{properties[i].getMinOccurs(), QNameHelper.pretty(properties[i].getName())}, this));
               }
            }
         }

         if (this.schemaType().getContentType() != 2) {
            return !watcher.hasError();
         }
      }

      String text = (String)this._textsource;
      if (text == null) {
         text = "";
      }

      this.validate_simpleval(text, new ImmutableValueValidationContext(watcher, this));
      return !watcher.hasError();
   }

   protected void validate_simpleval(String lexical, ValidationContext ctx) {
   }

   private static XmlObject[] _typedArray(XmlObject[] input) {
      if (input.length == 0) {
         return input;
      } else {
         SchemaType commonType = input[0].schemaType();
         if (!commonType.equals(XmlObject.type) && !commonType.isNoType()) {
            for(int i = 1; i < input.length; ++i) {
               if (input[i].schemaType().isNoType()) {
                  return input;
               }

               commonType = commonType.getCommonBaseType(input[i].schemaType());
               if (commonType.equals(XmlObject.type)) {
                  return input;
               }
            }

            Class desiredClass;
            for(desiredClass = commonType.getJavaClass(); desiredClass == null; desiredClass = commonType.getJavaClass()) {
               commonType = commonType.getBaseType();
               if (XmlObject.type.equals(commonType)) {
                  return input;
               }
            }

            XmlObject[] result = (XmlObject[])((XmlObject[])Array.newInstance(desiredClass, input.length));
            System.arraycopy(input, 0, result, 0, input.length);
            return result;
         } else {
            return input;
         }
      }
   }

   public XmlObject[] selectPath(String path) {
      return this.selectPath(path, (XmlOptions)null);
   }

   public XmlObject[] selectPath(String path, XmlOptions options) {
      XmlCursor c = this.newCursor();
      if (c == null) {
         throw new XmlValueDisconnectedException();
      } else {
         XmlObject[] selections;
         try {
            c.selectPath(path, options);
            if (!c.hasNextSelection()) {
               selections = EMPTY_RESULT;
            } else {
               selections = new XmlObject[c.getSelectionCount()];

               for(int i = 0; c.toNextSelection(); ++i) {
                  if ((selections[i] = c.getObject()) == null && (!c.toParent() || (selections[i] = c.getObject()) == null)) {
                     throw new XmlRuntimeException("Path must select only elements and attributes");
                  }
               }
            }
         } finally {
            c.dispose();
         }

         return _typedArray(selections);
      }
   }

   public XmlObject[] execQuery(String path) {
      return this.execQuery(path, (XmlOptions)null);
   }

   public XmlObject[] execQuery(String queryExpr, XmlOptions options) {
      synchronized(this.monitor()) {
         TypeStore typeStore = this.get_store();
         if (typeStore == null) {
            throw new XmlRuntimeException("Cannot do XQuery on XML Value Objects");
         } else {
            XmlObject[] var10000;
            try {
               var10000 = _typedArray(typeStore.exec_query(queryExpr, options));
            } catch (XmlException var7) {
               throw new XmlRuntimeException(var7);
            }

            return var10000;
         }
      }
   }

   public XmlObject changeType(SchemaType type) {
      if (type == null) {
         throw new IllegalArgumentException("Invalid type (null)");
      } else if ((this._flags & 16) == 0) {
         throw new IllegalStateException("XML Value Objects cannot have thier type changed");
      } else {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return (XmlObject)this.get_store().change_type(type);
         }
      }
   }

   public XmlObject substitute(QName name, SchemaType type) {
      if (name == null) {
         throw new IllegalArgumentException("Invalid name (null)");
      } else if (type == null) {
         throw new IllegalArgumentException("Invalid type (null)");
      } else if ((this._flags & 16) == 0) {
         throw new IllegalStateException("XML Value Objects cannot be used with substitution");
      } else {
         synchronized(this.monitor()) {
            this.check_orphaned();
            return (XmlObject)this.get_store().substitute(name, type);
         }
      }
   }

   protected XmlObjectBase() {
   }

   public void init_flags(SchemaProperty prop) {
      if (prop != null) {
         if (prop.hasDefault() != 1 && prop.hasFixed() != 1 && prop.hasNillable() != 1) {
            this._flags &= -8;
            this._flags |= (prop.hasDefault() == 0 ? 0 : 2) | (prop.hasFixed() == 0 ? 0 : 4) | (prop.hasNillable() == 0 ? 0 : 1) | '耀';
         }
      }
   }

   protected void initComplexType(boolean complexType, boolean complexContent) {
      this._flags |= (complexType ? 8192 : 0) | (complexContent ? 16384 : 0);
   }

   protected boolean _isComplexType() {
      return (this._flags & 8192) != 0;
   }

   protected boolean _isComplexContent() {
      return (this._flags & 16384) != 0;
   }

   public void setValidateOnSet() {
      this._flags |= 65536;
   }

   protected boolean _validateOnSet() {
      return (this._flags & 65536) != 0;
   }

   public final boolean isNil() {
      synchronized(this.monitor()) {
         this.check_dated();
         return (this._flags & 64) != 0;
      }
   }

   public final boolean isFixed() {
      this.check_element_dated();
      return (this._flags & 4) != 0;
   }

   public final boolean isNillable() {
      this.check_element_dated();
      return (this._flags & 1) != 0;
   }

   public final boolean isDefaultable() {
      this.check_element_dated();
      return (this._flags & 2) != 0;
   }

   public final boolean isDefault() {
      this.check_dated();
      return (this._flags & 256) != 0;
   }

   public final void setNil() {
      synchronized(this.monitor()) {
         this.set_prepare();
         if ((this._flags & 1) == 0 && (this._flags & 65536) != 0) {
            throw new XmlValueNotNillableException();
         } else {
            this.set_nil();
            this._flags |= 64;
            if ((this._flags & 16) != 0) {
               this.get_store().invalidate_text();
               this._flags &= -673;
               this.get_store().invalidate_nil();
            } else {
               this._textsource = null;
            }

         }
      }
   }

   protected int elementFlags() {
      this.check_element_dated();
      return this._flags & 7;
   }

   public void setImmutable() {
      if ((this._flags & 4112) != 0) {
         throw new IllegalStateException();
      } else {
         this._flags |= 4096;
      }
   }

   public boolean isImmutable() {
      return (this._flags & 4096) != 0;
   }

   public final void attach_store(TypeStore store) {
      this._textsource = store;
      if ((this._flags & 4096) != 0) {
         throw new IllegalStateException();
      } else {
         this._flags |= 688;
         if (store.is_attribute()) {
            this._flags |= 8;
         }

         if (store.validate_on_set()) {
            this._flags |= 65536;
         }

      }
   }

   public final void invalidate_value() {
      assert (this._flags & 16) != 0;

      this._flags |= 32;
   }

   public final boolean uses_invalidate_value() {
      SchemaType type = this.schemaType();
      return type.isSimpleType() || type.getContentType() == 2;
   }

   public final void invalidate_nilvalue() {
      assert (this._flags & 16) != 0;

      this._flags |= 160;
   }

   public final void invalidate_element_order() {
      assert (this._flags & 16) != 0;

      this._flags |= 672;
   }

   public final TypeStore get_store() {
      assert (this._flags & 16) != 0;

      return (TypeStore)this._textsource;
   }

   public final XmlLocale getXmlLocale() {
      return this.get_store().get_locale();
   }

   protected final boolean has_store() {
      return (this._flags & 16) != 0;
   }

   public final String build_text(NamespaceManager nsm) {
      assert (this._flags & 16) != 0;

      assert (this._flags & 32) == 0;

      return (this._flags & 320) != 0 ? "" : this.compute_text((NamespaceManager)(nsm == null ? (this.has_store() ? this.get_store() : null) : nsm));
   }

   public boolean build_nil() {
      assert (this._flags & 16) != 0;

      assert (this._flags & 32) == 0;

      return (this._flags & 64) != 0;
   }

   public void validate_now() {
      this.check_dated();
   }

   public void disconnect_store() {
      assert (this._flags & 16) != 0;

      this._flags |= 2720;
   }

   public TypeStoreUser create_element_user(QName eltName, QName xsiType) {
      return (TypeStoreUser)((SchemaTypeImpl)this.schemaType()).createElementType(eltName, xsiType, this.get_store().get_schematypeloader());
   }

   public TypeStoreUser create_attribute_user(QName attrName) {
      return (TypeStoreUser)((SchemaTypeImpl)this.schemaType()).createAttributeType(attrName, this.get_store().get_schematypeloader());
   }

   public SchemaType get_schema_type() {
      return this.schemaType();
   }

   public SchemaType get_element_type(QName eltName, QName xsiType) {
      return this.schemaType().getElementType(eltName, xsiType, this.get_store().get_schematypeloader());
   }

   public SchemaType get_attribute_type(QName attrName) {
      return this.schemaType().getAttributeType(attrName, this.get_store().get_schematypeloader());
   }

   public String get_default_element_text(QName eltName) {
      assert this._isComplexContent();

      if (!this._isComplexContent()) {
         throw new IllegalStateException();
      } else {
         SchemaProperty prop = this.schemaType().getElementProperty(eltName);
         return prop == null ? "" : prop.getDefaultText();
      }
   }

   public String get_default_attribute_text(QName attrName) {
      assert this._isComplexType();

      if (!this._isComplexType()) {
         throw new IllegalStateException();
      } else {
         SchemaProperty prop = this.schemaType().getAttributeProperty(attrName);
         return prop == null ? "" : prop.getDefaultText();
      }
   }

   public int get_elementflags(QName eltName) {
      if (!this._isComplexContent()) {
         return 0;
      } else {
         SchemaProperty prop = this.schemaType().getElementProperty(eltName);
         if (prop == null) {
            return 0;
         } else {
            return prop.hasDefault() != 1 && prop.hasFixed() != 1 && prop.hasNillable() != 1 ? (prop.hasDefault() == 0 ? 0 : 2) | (prop.hasFixed() == 0 ? 0 : 4) | (prop.hasNillable() == 0 ? 0 : 1) : -1;
         }
      }
   }

   public int get_attributeflags(QName attrName) {
      if (!this._isComplexType()) {
         return 0;
      } else {
         SchemaProperty prop = this.schemaType().getAttributeProperty(attrName);
         return prop == null ? 0 : (prop.hasDefault() == 0 ? 0 : 2) | (prop.hasFixed() == 0 ? 0 : 4);
      }
   }

   public boolean is_child_element_order_sensitive() {
      return !this._isComplexType() ? false : this.schemaType().isOrderSensitive();
   }

   public final QNameSet get_element_ending_delimiters(QName eltname) {
      SchemaProperty prop = this.schemaType().getElementProperty(eltname);
      return prop == null ? null : prop.getJavaSetterDelimiter();
   }

   public TypeStoreVisitor new_visitor() {
      return !this._isComplexContent() ? null : new SchemaTypeVisitorImpl(this.schemaType().getContentModel());
   }

   public SchemaField get_attribute_field(QName attrName) {
      SchemaAttributeModel model = this.schemaType().getAttributeModel();
      return model == null ? null : model.getAttribute(attrName);
   }

   protected void set_String(String v) {
      if ((this._flags & 4096) != 0) {
         throw new IllegalStateException();
      } else {
         boolean wasNilled = (this._flags & 64) != 0;
         String wscanon = this.apply_wscanon(v);
         this.update_from_wscanon_text(wscanon);
         if ((this._flags & 16) != 0) {
            this._flags &= -33;
            if ((this._flags & 1024) == 0) {
               this.get_store().store_text(v);
            }

            if (wasNilled) {
               this.get_store().invalidate_nil();
            }
         } else {
            this._textsource = v;
         }

      }
   }

   protected void update_from_complex_content() {
      throw new XmlValueNotSupportedException("Complex content");
   }

   private final void update_from_wscanon_text(String v) {
      if ((this._flags & 2) != 0 && (this._flags & 1024) == 0 && (this._flags & 8) == 0 && v.equals("")) {
         String def = this.get_store().compute_default_text();
         if (def == null) {
            throw new XmlValueOutOfRangeException();
         } else {
            this._flags |= 1024;

            try {
               this.setStringValue(def);
            } finally {
               this._flags &= -1025;
            }

            this._flags &= -65;
            this._flags |= 256;
         }
      } else {
         this.set_text(v);
         this._flags &= -321;
      }
   }

   protected boolean is_defaultable_ws(String v) {
      return true;
   }

   protected int get_wscanon_rule() {
      return 3;
   }

   private final String apply_wscanon(String v) {
      return XmlWhitespace.collapse(v, this.get_wscanon_rule());
   }

   private final void check_element_dated() {
      if ((this._flags & 512) != 0 && (this._flags & '耀') == 0) {
         if ((this._flags & 2048) != 0) {
            throw new XmlValueDisconnectedException();
         }

         int eltflags = this.get_store().compute_flags();
         this._flags &= -520;
         this._flags |= eltflags;
      }

      if ((this._flags & '耀') != 0) {
         this._flags &= -513;
      }

   }

   protected final boolean is_orphaned() {
      return (this._flags & 2048) != 0;
   }

   protected final void check_orphaned() {
      if (this.is_orphaned()) {
         throw new XmlValueDisconnectedException();
      }
   }

   public final void check_dated() {
      if ((this._flags & 672) != 0) {
         if ((this._flags & 2048) != 0) {
            throw new XmlValueDisconnectedException();
         }

         assert (this._flags & 16) != 0;

         if (this.preCheck()) {
            this._check_dated();
         } else {
            synchronized(this.monitor()) {
               this._check_dated();
            }
         }
      }

   }

   private final void _check_dated() {
      this.check_element_dated();
      if ((this._flags & 512) != 0) {
         int eltflags = this.get_store().compute_flags();
         this._flags &= -520;
         this._flags |= eltflags;
      }

      boolean nilled = false;
      if ((this._flags & 128) != 0) {
         if (this.get_store().find_nil()) {
            if ((this._flags & 1) == 0 && (this._flags & 65536) != 0) {
               throw new XmlValueOutOfRangeException();
            }

            this.set_nil();
            this._flags |= 64;
            nilled = true;
         } else {
            this._flags &= -65;
         }

         this._flags &= -129;
      }

      if (!nilled) {
         String text;
         if ((this._flags & 16384) == 0 && (text = this.get_wscanon_text()) != null) {
            NamespaceContext.push(new NamespaceContext(this.get_store()));

            try {
               this.update_from_wscanon_text(text);
            } finally {
               NamespaceContext.pop();
            }
         } else {
            this.update_from_complex_content();
         }
      }

      this._flags &= -33;
   }

   private final void set_prepare() {
      this.check_element_dated();
      if ((this._flags & 4096) != 0) {
         throw new IllegalStateException();
      }
   }

   private final void set_commit() {
      boolean wasNilled = (this._flags & 64) != 0;
      this._flags &= -321;
      if ((this._flags & 16) != 0) {
         this._flags &= -673;
         this.get_store().invalidate_text();
         if (wasNilled) {
            this.get_store().invalidate_nil();
         }
      } else {
         this._textsource = null;
      }

   }

   public final String get_wscanon_text() {
      return (this._flags & 16) == 0 ? this.apply_wscanon((String)this._textsource) : this.get_store().fetch_text(this.get_wscanon_rule());
   }

   protected abstract void set_text(String var1);

   protected abstract void set_nil();

   protected abstract String compute_text(NamespaceManager var1);

   public float getFloatValue() {
      BigDecimal bd = this.getBigDecimalValue();
      return bd == null ? 0.0F : bd.floatValue();
   }

   public double getDoubleValue() {
      BigDecimal bd = this.getBigDecimalValue();
      return bd == null ? 0.0 : bd.doubleValue();
   }

   public BigDecimal getBigDecimalValue() {
      throw new XmlValueNotSupportedException("exception.value.not.supported.s2j", new Object[]{this.getPrimitiveTypeName(), "numeric"});
   }

   public BigInteger getBigIntegerValue() {
      BigDecimal bd = this.bigDecimalValue();
      return bd == null ? null : bd.toBigInteger();
   }

   public byte getByteValue() {
      long l = (long)this.getIntValue();
      if (l > 127L) {
         throw new XmlValueOutOfRangeException();
      } else if (l < -128L) {
         throw new XmlValueOutOfRangeException();
      } else {
         return (byte)((int)l);
      }
   }

   public short getShortValue() {
      long l = (long)this.getIntValue();
      if (l > 32767L) {
         throw new XmlValueOutOfRangeException();
      } else if (l < -32768L) {
         throw new XmlValueOutOfRangeException();
      } else {
         return (short)((int)l);
      }
   }

   public int getIntValue() {
      long l = this.getLongValue();
      if (l > 2147483647L) {
         throw new XmlValueOutOfRangeException();
      } else if (l < -2147483648L) {
         throw new XmlValueOutOfRangeException();
      } else {
         return (int)l;
      }
   }

   public long getLongValue() {
      BigInteger b = this.getBigIntegerValue();
      if (b == null) {
         return 0L;
      } else if (b.compareTo(_max) >= 0) {
         throw new XmlValueOutOfRangeException();
      } else if (b.compareTo(_min) <= 0) {
         throw new XmlValueOutOfRangeException();
      } else {
         return b.longValue();
      }
   }

   static final XmlOptions buildInnerPrettyOptions() {
      XmlOptions options = new XmlOptions();
      options.put("SAVE_INNER");
      options.put("SAVE_PRETTY_PRINT");
      options.put("SAVE_AGGRESSIVE_NAMESPACES");
      options.put("SAVE_USE_DEFAULT_NAMESPACE");
      return options;
   }

   public final String toString() {
      synchronized(this.monitor()) {
         return this.ensureStore().xmlText(_toStringOptions);
      }
   }

   public String xmlText() {
      return this.xmlText((XmlOptions)null);
   }

   public String xmlText(XmlOptions options) {
      XmlCursor cur = this.newCursorForce();

      String var3;
      try {
         var3 = cur.xmlText(makeInnerOptions(options));
      } finally {
         cur.dispose();
      }

      return var3;
   }

   public StringEnumAbstractBase getEnumValue() {
      throw new XmlValueNotSupportedException("exception.value.not.supported.s2j", new Object[]{this.getPrimitiveTypeName(), "enum"});
   }

   public String getStringValue() {
      if (this.isImmutable()) {
         return (this._flags & 64) != 0 ? null : this.compute_text((NamespaceManager)null);
      } else {
         synchronized(this.monitor()) {
            if (this._isComplexContent()) {
               return this.get_store().fetch_text(1);
            } else {
               this.check_dated();
               return (this._flags & 64) != 0 ? null : this.compute_text(this.has_store() ? this.get_store() : null);
            }
         }
      }
   }

   /** @deprecated */
   public String stringValue() {
      return this.getStringValue();
   }

   /** @deprecated */
   public boolean booleanValue() {
      return this.getBooleanValue();
   }

   /** @deprecated */
   public byte byteValue() {
      return this.getByteValue();
   }

   /** @deprecated */
   public short shortValue() {
      return this.getShortValue();
   }

   /** @deprecated */
   public int intValue() {
      return this.getIntValue();
   }

   /** @deprecated */
   public long longValue() {
      return this.getLongValue();
   }

   /** @deprecated */
   public BigInteger bigIntegerValue() {
      return this.getBigIntegerValue();
   }

   /** @deprecated */
   public BigDecimal bigDecimalValue() {
      return this.getBigDecimalValue();
   }

   /** @deprecated */
   public float floatValue() {
      return this.getFloatValue();
   }

   /** @deprecated */
   public double doubleValue() {
      return this.getDoubleValue();
   }

   /** @deprecated */
   public byte[] byteArrayValue() {
      return this.getByteArrayValue();
   }

   /** @deprecated */
   public StringEnumAbstractBase enumValue() {
      return this.getEnumValue();
   }

   /** @deprecated */
   public Calendar calendarValue() {
      return this.getCalendarValue();
   }

   /** @deprecated */
   public Date dateValue() {
      return this.getDateValue();
   }

   /** @deprecated */
   public GDate gDateValue() {
      return this.getGDateValue();
   }

   /** @deprecated */
   public GDuration gDurationValue() {
      return this.getGDurationValue();
   }

   /** @deprecated */
   public QName qNameValue() {
      return this.getQNameValue();
   }

   /** @deprecated */
   public List xlistValue() {
      return this.xgetListValue();
   }

   /** @deprecated */
   public List listValue() {
      return this.getListValue();
   }

   /** @deprecated */
   public Object objectValue() {
      return this.getObjectValue();
   }

   /** @deprecated */
   public void set(String obj) {
      this.setStringValue(obj);
   }

   /** @deprecated */
   public void set(boolean v) {
      this.setBooleanValue(v);
   }

   /** @deprecated */
   public void set(byte v) {
      this.setByteValue(v);
   }

   /** @deprecated */
   public void set(short v) {
      this.setShortValue(v);
   }

   /** @deprecated */
   public void set(int v) {
      this.setIntValue(v);
   }

   /** @deprecated */
   public void set(long v) {
      this.setLongValue(v);
   }

   /** @deprecated */
   public void set(BigInteger obj) {
      this.setBigIntegerValue(obj);
   }

   /** @deprecated */
   public void set(BigDecimal obj) {
      this.setBigDecimalValue(obj);
   }

   /** @deprecated */
   public void set(float v) {
      this.setFloatValue(v);
   }

   /** @deprecated */
   public void set(double v) {
      this.setDoubleValue(v);
   }

   /** @deprecated */
   public void set(byte[] obj) {
      this.setByteArrayValue(obj);
   }

   /** @deprecated */
   public void set(StringEnumAbstractBase obj) {
      this.setEnumValue(obj);
   }

   /** @deprecated */
   public void set(Calendar obj) {
      this.setCalendarValue(obj);
   }

   /** @deprecated */
   public void set(Date obj) {
      this.setDateValue(obj);
   }

   /** @deprecated */
   public void set(GDateSpecification obj) {
      this.setGDateValue(obj);
   }

   /** @deprecated */
   public void set(GDurationSpecification obj) {
      this.setGDurationValue(obj);
   }

   /** @deprecated */
   public void set(QName obj) {
      this.setQNameValue(obj);
   }

   /** @deprecated */
   public void set(List obj) {
      this.setListValue(obj);
   }

   /** @deprecated */
   public void objectSet(Object obj) {
      this.setObjectValue(obj);
   }

   public byte[] getByteArrayValue() {
      throw new XmlValueNotSupportedException("exception.value.not.supported.s2j", new Object[]{this.getPrimitiveTypeName(), "byte[]"});
   }

   public boolean getBooleanValue() {
      throw new XmlValueNotSupportedException("exception.value.not.supported.s2j", new Object[]{this.getPrimitiveTypeName(), "boolean"});
   }

   public GDate getGDateValue() {
      throw new XmlValueNotSupportedException("exception.value.not.supported.s2j", new Object[]{this.getPrimitiveTypeName(), "Date"});
   }

   public Date getDateValue() {
      throw new XmlValueNotSupportedException("exception.value.not.supported.s2j", new Object[]{this.getPrimitiveTypeName(), "Date"});
   }

   public Calendar getCalendarValue() {
      throw new XmlValueNotSupportedException("exception.value.not.supported.s2j", new Object[]{this.getPrimitiveTypeName(), "Calendar"});
   }

   public GDuration getGDurationValue() {
      throw new XmlValueNotSupportedException("exception.value.not.supported.s2j", new Object[]{this.getPrimitiveTypeName(), "Duration"});
   }

   public QName getQNameValue() {
      throw new XmlValueNotSupportedException("exception.value.not.supported.s2j", new Object[]{this.getPrimitiveTypeName(), "QName"});
   }

   public List getListValue() {
      throw new XmlValueNotSupportedException("exception.value.not.supported.s2j", new Object[]{this.getPrimitiveTypeName(), "List"});
   }

   public List xgetListValue() {
      throw new XmlValueNotSupportedException("exception.value.not.supported.s2j", new Object[]{this.getPrimitiveTypeName(), "List"});
   }

   public Object getObjectValue() {
      return java_value(this);
   }

   public final void setBooleanValue(boolean v) {
      synchronized(this.monitor()) {
         this.set_prepare();
         this.set_boolean(v);
         this.set_commit();
      }
   }

   public final void setByteValue(byte v) {
      synchronized(this.monitor()) {
         this.set_prepare();
         this.set_byte(v);
         this.set_commit();
      }
   }

   public final void setShortValue(short v) {
      synchronized(this.monitor()) {
         this.set_prepare();
         this.set_short(v);
         this.set_commit();
      }
   }

   public final void setIntValue(int v) {
      synchronized(this.monitor()) {
         this.set_prepare();
         this.set_int(v);
         this.set_commit();
      }
   }

   public final void setLongValue(long v) {
      synchronized(this.monitor()) {
         this.set_prepare();
         this.set_long(v);
         this.set_commit();
      }
   }

   public final void setFloatValue(float v) {
      synchronized(this.monitor()) {
         this.set_prepare();
         this.set_float(v);
         this.set_commit();
      }
   }

   public final void setDoubleValue(double v) {
      synchronized(this.monitor()) {
         this.set_prepare();
         this.set_double(v);
         this.set_commit();
      }
   }

   public final void setByteArrayValue(byte[] obj) {
      if (obj == null) {
         this.setNil();
      } else {
         synchronized(this.monitor()) {
            this.set_prepare();
            this.set_ByteArray(obj);
            this.set_commit();
         }
      }

   }

   public final void setEnumValue(StringEnumAbstractBase obj) {
      if (obj == null) {
         this.setNil();
      } else {
         synchronized(this.monitor()) {
            this.set_prepare();
            this.set_enum(obj);
            this.set_commit();
         }
      }

   }

   public final void setBigIntegerValue(BigInteger obj) {
      if (obj == null) {
         this.setNil();
      } else {
         synchronized(this.monitor()) {
            this.set_prepare();
            this.set_BigInteger(obj);
            this.set_commit();
         }
      }

   }

   public final void setBigDecimalValue(BigDecimal obj) {
      if (obj == null) {
         this.setNil();
      } else {
         synchronized(this.monitor()) {
            this.set_prepare();
            this.set_BigDecimal(obj);
            this.set_commit();
         }
      }

   }

   public final void setCalendarValue(Calendar obj) {
      if (obj == null) {
         this.setNil();
      } else {
         synchronized(this.monitor()) {
            this.set_prepare();
            this.set_Calendar(obj);
            this.set_commit();
         }
      }

   }

   public final void setDateValue(Date obj) {
      if (obj == null) {
         this.setNil();
      } else {
         synchronized(this.monitor()) {
            this.set_prepare();
            this.set_Date(obj);
            this.set_commit();
         }
      }

   }

   public final void setGDateValue(GDate obj) {
      if (obj == null) {
         this.setNil();
      } else {
         synchronized(this.monitor()) {
            this.set_prepare();
            this.set_GDate(obj);
            this.set_commit();
         }
      }

   }

   public final void setGDateValue(GDateSpecification obj) {
      if (obj == null) {
         this.setNil();
      } else {
         synchronized(this.monitor()) {
            this.set_prepare();
            this.set_GDate(obj);
            this.set_commit();
         }
      }

   }

   public final void setGDurationValue(GDuration obj) {
      if (obj == null) {
         this.setNil();
      } else {
         synchronized(this.monitor()) {
            this.set_prepare();
            this.set_GDuration(obj);
            this.set_commit();
         }
      }

   }

   public final void setGDurationValue(GDurationSpecification obj) {
      if (obj == null) {
         this.setNil();
      } else {
         synchronized(this.monitor()) {
            this.set_prepare();
            this.set_GDuration(obj);
            this.set_commit();
         }
      }

   }

   public final void setQNameValue(QName obj) {
      if (obj == null) {
         this.setNil();
      } else {
         synchronized(this.monitor()) {
            this.set_prepare();
            this.set_QName(obj);
            this.set_commit();
         }
      }

   }

   public final void setListValue(List obj) {
      if (obj == null) {
         this.setNil();
      } else {
         synchronized(this.monitor()) {
            this.set_prepare();
            this.set_list(obj);
            this.set_commit();
         }
      }

   }

   public final void setStringValue(String obj) {
      if (obj == null) {
         this.setNil();
      } else {
         synchronized(this.monitor()) {
            this.set_prepare();
            this.set_String(obj);
         }
      }

   }

   public void setObjectValue(Object o) {
      if (o == null) {
         this.setNil();
      } else {
         if (o instanceof XmlObject) {
            this.set((XmlObject)o);
         } else if (o instanceof String) {
            this.setStringValue((String)o);
         } else if (o instanceof StringEnumAbstractBase) {
            this.setEnumValue((StringEnumAbstractBase)o);
         } else if (o instanceof BigInteger) {
            this.setBigIntegerValue((BigInteger)o);
         } else if (o instanceof BigDecimal) {
            this.setBigDecimalValue((BigDecimal)o);
         } else if (o instanceof Byte) {
            this.setByteValue((Byte)o);
         } else if (o instanceof Short) {
            this.setShortValue((Short)o);
         } else if (o instanceof Integer) {
            this.setIntValue((Integer)o);
         } else if (o instanceof Long) {
            this.setLongValue((Long)o);
         } else if (o instanceof Boolean) {
            this.setBooleanValue((Boolean)o);
         } else if (o instanceof Float) {
            this.setFloatValue((Float)o);
         } else if (o instanceof Double) {
            this.setDoubleValue((Double)o);
         } else if (o instanceof Calendar) {
            this.setCalendarValue((Calendar)o);
         } else if (o instanceof Date) {
            this.setDateValue((Date)o);
         } else if (o instanceof GDateSpecification) {
            this.setGDateValue((GDateSpecification)o);
         } else if (o instanceof GDurationSpecification) {
            this.setGDurationValue((GDurationSpecification)o);
         } else if (o instanceof QName) {
            this.setQNameValue((QName)o);
         } else if (o instanceof List) {
            this.setListValue((List)o);
         } else {
            if (!(o instanceof byte[])) {
               throw new XmlValueNotSupportedException("Can't set union object of class : " + o.getClass().getName());
            }

            this.setByteArrayValue((byte[])((byte[])o));
         }

      }
   }

   public final void set_newValue(XmlObject obj) {
      if (obj != null && !obj.isNil()) {
         if (obj instanceof XmlAnySimpleType) {
            XmlAnySimpleType v = (XmlAnySimpleType)obj;
            SchemaType instanceType = ((SimpleValue)v).instanceType();

            assert instanceType != null : "Nil case should have been handled already";

            if (instanceType.getSimpleVariety() == 3) {
               synchronized(this.monitor()) {
                  this.set_prepare();
                  this.set_list(((SimpleValue)v).xgetListValue());
                  this.set_commit();
               }
            } else {
               synchronized(this.monitor()) {
                  assert instanceType.getSimpleVariety() == 1;

                  String s;
                  byte[] byteArr;
                  boolean pushed;
                  label182:
                  switch (instanceType.getPrimitiveType().getBuiltinTypeCode()) {
                     case 2:
                        pushed = false;
                        if (!v.isImmutable()) {
                           pushed = true;
                           NamespaceContext.push(new NamespaceContext(v));
                        }

                        try {
                           this.set_prepare();
                           this.set_xmlanysimple(v);
                           break;
                        } finally {
                           if (pushed) {
                              NamespaceContext.pop();
                           }

                        }
                     case 3:
                        pushed = ((SimpleValue)v).getBooleanValue();
                        this.set_prepare();
                        this.set_boolean(pushed);
                        break;
                     case 4:
                        byteArr = ((SimpleValue)v).getByteArrayValue();
                        this.set_prepare();
                        this.set_b64(byteArr);
                        break;
                     case 5:
                        byteArr = ((SimpleValue)v).getByteArrayValue();
                        this.set_prepare();
                        this.set_hex(byteArr);
                        break;
                     case 6:
                        s = v.getStringValue();
                        this.set_prepare();
                        this.set_text(s);
                        break;
                     case 7:
                        QName name = ((SimpleValue)v).getQNameValue();
                        this.set_prepare();
                        this.set_QName(name);
                        break;
                     case 8:
                        s = v.getStringValue();
                        this.set_prepare();
                        this.set_notation(s);
                        break;
                     case 9:
                        float f = ((SimpleValue)v).getFloatValue();
                        this.set_prepare();
                        this.set_float(f);
                        break;
                     case 10:
                        double d = ((SimpleValue)v).getDoubleValue();
                        this.set_prepare();
                        this.set_double(d);
                        break;
                     case 11:
                        switch (instanceType.getDecimalSize()) {
                           case 8:
                              byte b = ((SimpleValue)v).getByteValue();
                              this.set_prepare();
                              this.set_byte(b);
                              break label182;
                           case 16:
                              short s = ((SimpleValue)v).getShortValue();
                              this.set_prepare();
                              this.set_short(s);
                              break label182;
                           case 32:
                              int i = ((SimpleValue)v).getIntValue();
                              this.set_prepare();
                              this.set_int(i);
                              break label182;
                           case 64:
                              long l = ((SimpleValue)v).getLongValue();
                              this.set_prepare();
                              this.set_long(l);
                              break label182;
                           case 1000000:
                              BigInteger bi = ((SimpleValue)v).getBigIntegerValue();
                              this.set_prepare();
                              this.set_BigInteger(bi);
                              break label182;
                           default:
                              assert false : "invalid numeric bit count";
                           case 1000001:
                              BigDecimal bd = ((SimpleValue)v).getBigDecimalValue();
                              this.set_prepare();
                              this.set_BigDecimal(bd);
                              break label182;
                        }
                     case 12:
                        s = v.getStringValue();
                        this.set_prepare();
                        this.set_String(s);
                        break;
                     case 13:
                        GDuration gd = ((SimpleValue)v).getGDurationValue();
                        this.set_prepare();
                        this.set_GDuration(gd);
                        break;
                     case 14:
                     case 15:
                     case 16:
                     case 17:
                     case 18:
                     case 19:
                     case 20:
                     case 21:
                        GDate gd = ((SimpleValue)v).getGDateValue();
                        this.set_prepare();
                        this.set_GDate(gd);
                        break;
                     default:
                        assert false : "encountered nonprimitive type.";

                        throw new IllegalStateException("Complex type unexpected");
                  }

                  this.set_commit();
               }
            }
         } else {
            throw new IllegalStateException("Complex type unexpected");
         }
      } else {
         this.setNil();
      }
   }

   private TypeStoreUser setterHelper(XmlObjectBase src) {
      this.check_orphaned();
      src.check_orphaned();
      return this.get_store().copy_contents_from(src.get_store()).get_store().change_type(src.schemaType());
   }

   public final XmlObject set(XmlObject src) {
      if (this.isImmutable()) {
         throw new IllegalStateException("Cannot set the value of an immutable XmlObject");
      } else {
         XmlObjectBase obj = underlying(src);
         TypeStoreUser newObj = this;
         if (obj == null) {
            this.setNil();
            return this;
         } else {
            if (obj.isImmutable()) {
               this.setStringValue(obj.getStringValue());
            } else {
               boolean noSyncThis = this.preCheck();
               boolean noSyncObj = obj.preCheck();
               if (this.monitor() == obj.monitor()) {
                  if (noSyncThis) {
                     newObj = this.setterHelper(obj);
                  } else {
                     synchronized(this.monitor()) {
                        newObj = this.setterHelper(obj);
                     }
                  }
               } else if (noSyncThis) {
                  if (noSyncObj) {
                     newObj = this.setterHelper(obj);
                  } else {
                     synchronized(obj.monitor()) {
                        newObj = this.setterHelper(obj);
                     }
                  }
               } else if (noSyncObj) {
                  synchronized(this.monitor()) {
                     newObj = this.setterHelper(obj);
                  }
               } else {
                  boolean acquired = false;

                  try {
                     GlobalLock.acquire();
                     acquired = true;
                     synchronized(this.monitor()) {
                        synchronized(obj.monitor()) {
                           GlobalLock.release();
                           acquired = false;
                           newObj = this.setterHelper(obj);
                        }
                     }
                  } catch (InterruptedException var22) {
                     throw new XmlRuntimeException(var22);
                  } finally {
                     if (acquired) {
                        GlobalLock.release();
                     }

                  }
               }
            }

            return (XmlObject)newObj;
         }
      }
   }

   public final XmlObject generatedSetterHelperImpl(XmlObject src, QName propName, int index, short kindSetterHelper) {
      XmlObjectBase srcObj = underlying(src);
      XmlObjectBase target;
      if (srcObj == null) {
         synchronized(this.monitor()) {
            target = this.getTargetForSetter(propName, index, kindSetterHelper);
            target.setNil();
            return target;
         }
      } else if (srcObj.isImmutable()) {
         synchronized(this.monitor()) {
            target = this.getTargetForSetter(propName, index, kindSetterHelper);
            target.setStringValue(srcObj.getStringValue());
            return target;
         }
      } else {
         boolean noSyncThis = this.preCheck();
         boolean noSyncObj = srcObj.preCheck();
         if (this.monitor() == srcObj.monitor()) {
            if (noSyncThis) {
               return (XmlObject)this.objSetterHelper(srcObj, propName, index, kindSetterHelper);
            } else {
               synchronized(this.monitor()) {
                  return (XmlObject)this.objSetterHelper(srcObj, propName, index, kindSetterHelper);
               }
            }
         } else if (noSyncThis) {
            if (noSyncObj) {
               return (XmlObject)this.objSetterHelper(srcObj, propName, index, kindSetterHelper);
            } else {
               synchronized(srcObj.monitor()) {
                  return (XmlObject)this.objSetterHelper(srcObj, propName, index, kindSetterHelper);
               }
            }
         } else if (noSyncObj) {
            synchronized(this.monitor()) {
               return (XmlObject)this.objSetterHelper(srcObj, propName, index, kindSetterHelper);
            }
         } else {
            boolean acquired = false;

            XmlObject var11;
            try {
               GlobalLock.acquire();
               acquired = true;
               synchronized(this.monitor()) {
                  synchronized(srcObj.monitor()) {
                     GlobalLock.release();
                     acquired = false;
                     var11 = (XmlObject)this.objSetterHelper(srcObj, propName, index, kindSetterHelper);
                  }
               }
            } catch (InterruptedException var32) {
               throw new XmlRuntimeException(var32);
            } finally {
               if (acquired) {
                  GlobalLock.release();
               }

            }

            return var11;
         }
      }
   }

   private TypeStoreUser objSetterHelper(XmlObjectBase srcObj, QName propName, int index, short kindSetterHelper) {
      XmlObjectBase target = this.getTargetForSetter(propName, index, kindSetterHelper);
      target.check_orphaned();
      srcObj.check_orphaned();
      return target.get_store().copy_contents_from(srcObj.get_store()).get_store().change_type(srcObj.schemaType());
   }

   private XmlObjectBase getTargetForSetter(QName propName, int index, short kindSetterHelper) {
      XmlObjectBase target;
      switch (kindSetterHelper) {
         case 1:
            this.check_orphaned();
            target = null;
            target = (XmlObjectBase)this.get_store().find_element_user(propName, index);
            if (target == null) {
               target = (XmlObjectBase)this.get_store().add_element_user(propName);
            }

            if (target.isImmutable()) {
               throw new IllegalStateException("Cannot set the value of an immutable XmlObject");
            }

            return target;
         case 2:
            this.check_orphaned();
            target = null;
            target = (XmlObjectBase)this.get_store().find_element_user(propName, index);
            if (target == null) {
               throw new IndexOutOfBoundsException();
            } else {
               if (target.isImmutable()) {
                  throw new IllegalStateException("Cannot set the value of an immutable XmlObject");
               }

               return target;
            }
         default:
            throw new IllegalArgumentException("Unknown kindSetterHelper: " + kindSetterHelper);
      }
   }

   public final XmlObject _set(XmlObject src) {
      if (this.isImmutable()) {
         throw new IllegalStateException("Cannot set the value of an immutable XmlObject");
      } else {
         XmlObjectBase obj = underlying(src);
         TypeStoreUser newObj = this;
         if (obj == null) {
            this.setNil();
            return this;
         } else {
            if (obj.isImmutable()) {
               this.set(obj.stringValue());
            } else {
               this.check_orphaned();
               obj.check_orphaned();
               newObj = this.get_store().copy_contents_from(obj.get_store()).get_store().change_type(obj.schemaType());
            }

            return (XmlObject)newObj;
         }
      }
   }

   protected void set_list(List list) {
      throw new XmlValueNotSupportedException("exception.value.not.supported.j2s", new Object[]{"List", this.getPrimitiveTypeName()});
   }

   protected void set_boolean(boolean v) {
      throw new XmlValueNotSupportedException("exception.value.not.supported.j2s", new Object[]{"boolean", this.getPrimitiveTypeName()});
   }

   protected void set_byte(byte v) {
      this.set_int(v);
   }

   protected void set_short(short v) {
      this.set_int(v);
   }

   protected void set_int(int v) {
      this.set_long((long)v);
   }

   protected void set_long(long v) {
      this.set_BigInteger(BigInteger.valueOf(v));
   }

   protected void set_char(char v) {
      this.set_String(Character.toString(v));
   }

   protected void set_float(float v) {
      this.set_BigDecimal(new BigDecimal((double)v));
   }

   protected void set_double(double v) {
      this.set_BigDecimal(new BigDecimal(v));
   }

   protected void set_enum(StringEnumAbstractBase e) {
      throw new XmlValueNotSupportedException("exception.value.not.supported.j2s", new Object[]{"enum", this.getPrimitiveTypeName()});
   }

   protected void set_ByteArray(byte[] b) {
      throw new XmlValueNotSupportedException("exception.value.not.supported.j2s", new Object[]{"byte[]", this.getPrimitiveTypeName()});
   }

   protected void set_b64(byte[] b) {
      this.set_ByteArray(b);
   }

   protected void set_hex(byte[] b) {
      this.set_ByteArray(b);
   }

   protected void set_BigInteger(BigInteger v) {
      this.set_BigDecimal(new BigDecimal(v));
   }

   protected void set_BigDecimal(BigDecimal v) {
      throw new XmlValueNotSupportedException("exception.value.not.supported.j2s", new Object[]{"numeric", this.getPrimitiveTypeName()});
   }

   protected void set_Date(Date v) {
      throw new XmlValueNotSupportedException("exception.value.not.supported.j2s", new Object[]{"Date", this.getPrimitiveTypeName()});
   }

   protected void set_Calendar(Calendar v) {
      throw new XmlValueNotSupportedException("exception.value.not.supported.j2s", new Object[]{"Calendar", this.getPrimitiveTypeName()});
   }

   protected void set_GDate(GDateSpecification v) {
      throw new XmlValueNotSupportedException("exception.value.not.supported.j2s", new Object[]{"Date", this.getPrimitiveTypeName()});
   }

   protected void set_GDuration(GDurationSpecification v) {
      throw new XmlValueNotSupportedException("exception.value.not.supported.j2s", new Object[]{"Duration", this.getPrimitiveTypeName()});
   }

   protected void set_ComplexXml(XmlObject v) {
      throw new XmlValueNotSupportedException("exception.value.not.supported.j2s", new Object[]{"complex content", this.getPrimitiveTypeName()});
   }

   protected void set_QName(QName v) {
      throw new XmlValueNotSupportedException("exception.value.not.supported.j2s", new Object[]{"QName", this.getPrimitiveTypeName()});
   }

   protected void set_notation(String v) {
      throw new XmlValueNotSupportedException();
   }

   protected void set_xmlanysimple(XmlAnySimpleType v) {
      this.set_String(v.getStringValue());
   }

   private final String getPrimitiveTypeName() {
      SchemaType type = this.schemaType();
      if (type.isNoType()) {
         return "unknown";
      } else {
         SchemaType t = type.getPrimitiveType();
         return t == null ? "complex" : t.getName().getLocalPart();
      }
   }

   private final boolean comparable_value_spaces(SchemaType t1, SchemaType t2) {
      assert t1.getSimpleVariety() != 2 && t2.getSimpleVariety() != 2;

      if (!t1.isSimpleType() && !t2.isSimpleType()) {
         return t1.getContentType() == t2.getContentType();
      } else if (t1.isSimpleType() && t2.isSimpleType()) {
         if (t1.getSimpleVariety() == 3 && t2.getSimpleVariety() == 3) {
            return true;
         } else {
            return t1.getSimpleVariety() != 3 && t2.getSimpleVariety() != 3 ? t1.getPrimitiveType().equals(t2.getPrimitiveType()) : false;
         }
      } else {
         return false;
      }
   }

   private final boolean valueEqualsImpl(XmlObject xmlobj) {
      this.check_dated();
      SchemaType typethis = this.instanceType();
      SchemaType typeother = ((SimpleValue)xmlobj).instanceType();
      if (typethis == null && typeother == null) {
         return true;
      } else if (typethis != null && typeother != null) {
         if (!this.comparable_value_spaces(typethis, typeother)) {
            return false;
         } else {
            return xmlobj.schemaType().getSimpleVariety() == 2 ? underlying(xmlobj).equal_to(this) : this.equal_to(xmlobj);
         }
      } else {
         return false;
      }
   }

   public final boolean valueEquals(XmlObject xmlobj) {
      boolean acquired = false;

      boolean var4;
      try {
         if (this.isImmutable()) {
            if (xmlobj.isImmutable()) {
               boolean var3 = this.valueEqualsImpl(xmlobj);
               return var3;
            }

            synchronized(xmlobj.monitor()) {
               var4 = this.valueEqualsImpl(xmlobj);
               return var4;
            }
         }

         if (!xmlobj.isImmutable() && this.monitor() != xmlobj.monitor()) {
            GlobalLock.acquire();
            acquired = true;
            synchronized(this.monitor()) {
               synchronized(xmlobj.monitor()) {
                  GlobalLock.release();
                  acquired = false;
                  boolean var5 = this.valueEqualsImpl(xmlobj);
                  return var5;
               }
            }
         }

         synchronized(this.monitor()) {
            var4 = this.valueEqualsImpl(xmlobj);
         }
      } catch (InterruptedException var20) {
         throw new XmlRuntimeException(var20);
      } finally {
         if (acquired) {
            GlobalLock.release();
         }

      }

      return var4;
   }

   public final int compareTo(Object obj) {
      int result = this.compareValue((XmlObject)obj);
      if (result == 2) {
         throw new ClassCastException();
      } else {
         return result;
      }
   }

   private final int compareValueImpl(XmlObject xmlobj) {
      SchemaType type1;
      SchemaType type2;
      try {
         type1 = this.instanceType();
         type2 = ((SimpleValue)xmlobj).instanceType();
      } catch (XmlValueOutOfRangeException var5) {
         return 2;
      }

      if (type1 == null && type2 == null) {
         return 0;
      } else if (type1 != null && type2 != null) {
         if (type1.isSimpleType() && !type1.isURType()) {
            if (type2.isSimpleType() && !type2.isURType()) {
               type1 = type1.getPrimitiveType();
               type2 = type2.getPrimitiveType();
               return type1.getBuiltinTypeCode() != type2.getBuiltinTypeCode() ? 2 : this.compare_to(xmlobj);
            } else {
               return 2;
            }
         } else {
            return 2;
         }
      } else {
         return 2;
      }
   }

   public final int compareValue(XmlObject xmlobj) {
      if (xmlobj == null) {
         return 2;
      } else {
         boolean acquired = false;

         int var4;
         try {
            if (!this.isImmutable()) {
               if (!xmlobj.isImmutable() && this.monitor() != xmlobj.monitor()) {
                  GlobalLock.acquire();
                  acquired = true;
                  synchronized(this.monitor()) {
                     synchronized(xmlobj.monitor()) {
                        GlobalLock.release();
                        acquired = false;
                        int var5 = this.compareValueImpl(xmlobj);
                        return var5;
                     }
                  }
               }

               synchronized(this.monitor()) {
                  var4 = this.compareValueImpl(xmlobj);
                  return var4;
               }
            }

            if (xmlobj.isImmutable()) {
               int var3 = this.compareValueImpl(xmlobj);
               return var3;
            }

            synchronized(xmlobj.monitor()) {
               var4 = this.compareValueImpl(xmlobj);
            }
         } catch (InterruptedException var20) {
            throw new XmlRuntimeException(var20);
         } finally {
            if (acquired) {
               GlobalLock.release();
            }

         }

         return var4;
      }
   }

   protected int compare_to(XmlObject xmlobj) {
      return this.equal_to(xmlobj) ? 0 : 2;
   }

   protected abstract boolean equal_to(XmlObject var1);

   protected abstract int value_hash_code();

   public int valueHashCode() {
      synchronized(this.monitor()) {
         return this.value_hash_code();
      }
   }

   public boolean isInstanceOf(SchemaType type) {
      SchemaType myType;
      if (type.getSimpleVariety() != 2) {
         for(myType = this.instanceType(); myType != null; myType = myType.getBaseType()) {
            if (type == myType) {
               return true;
            }
         }

         return false;
      } else {
         Set ctypes = new HashSet(Arrays.asList(type.getUnionConstituentTypes()));

         for(myType = this.instanceType(); myType != null; myType = myType.getBaseType()) {
            if (ctypes.contains(myType)) {
               return true;
            }
         }

         return false;
      }
   }

   public final boolean equals(Object obj) {
      if (!this.isImmutable()) {
         return super.equals(obj);
      } else if (!(obj instanceof XmlObject)) {
         return false;
      } else {
         XmlObject xmlobj = (XmlObject)obj;
         return !xmlobj.isImmutable() ? false : this.valueEquals(xmlobj);
      }
   }

   public final int hashCode() {
      if (!this.isImmutable()) {
         return super.hashCode();
      } else {
         synchronized(this.monitor()) {
            return this.isNil() ? 0 : this.value_hash_code();
         }
      }
   }

   public XmlObject[] selectChildren(QName elementName) {
      XmlCursor xc = this.newCursor();

      XmlObject[] var3;
      try {
         if (xc.isContainer()) {
            List result = new ArrayList();
            if (xc.toChild(elementName)) {
               do {
                  result.add(xc.getObject());
               } while(xc.toNextSibling(elementName));
            }

            XmlObject[] var4;
            if (result.size() == 0) {
               var4 = EMPTY_RESULT;
               return var4;
            }

            var4 = (XmlObject[])((XmlObject[])result.toArray(EMPTY_RESULT));
            return var4;
         }

         var3 = EMPTY_RESULT;
      } finally {
         xc.dispose();
      }

      return var3;
   }

   public XmlObject[] selectChildren(String elementUri, String elementLocalName) {
      return this.selectChildren(new QName(elementUri, elementLocalName));
   }

   public XmlObject[] selectChildren(QNameSet elementNameSet) {
      if (elementNameSet == null) {
         throw new IllegalArgumentException();
      } else {
         XmlCursor xc = this.newCursor();

         XmlObject[] var4;
         try {
            if (!xc.isContainer()) {
               XmlObject[] var8 = EMPTY_RESULT;
               return var8;
            }

            List result = new ArrayList();
            if (xc.toFirstChild()) {
               do {
                  assert xc.isContainer();

                  if (elementNameSet.contains(xc.getName())) {
                     result.add(xc.getObject());
                  }
               } while(xc.toNextSibling());
            }

            if (result.size() != 0) {
               var4 = (XmlObject[])((XmlObject[])result.toArray(EMPTY_RESULT));
               return var4;
            }

            var4 = EMPTY_RESULT;
         } finally {
            xc.dispose();
         }

         return var4;
      }
   }

   public XmlObject selectAttribute(QName attributeName) {
      XmlCursor xc = this.newCursor();

      XmlObject var3;
      try {
         if (xc.isContainer()) {
            if (xc.toFirstAttribute()) {
               do {
                  if (xc.getName().equals(attributeName)) {
                     var3 = xc.getObject();
                     return var3;
                  }
               } while(xc.toNextAttribute());
            }

            var3 = null;
            return var3;
         }

         var3 = null;
      } finally {
         xc.dispose();
      }

      return var3;
   }

   public XmlObject selectAttribute(String attributeUri, String attributeLocalName) {
      return this.selectAttribute(new QName(attributeUri, attributeLocalName));
   }

   public XmlObject[] selectAttributes(QNameSet attributeNameSet) {
      if (attributeNameSet == null) {
         throw new IllegalArgumentException();
      } else {
         XmlCursor xc = this.newCursor();

         XmlObject[] var4;
         try {
            if (!xc.isContainer()) {
               XmlObject[] var8 = EMPTY_RESULT;
               return var8;
            }

            List result = new ArrayList();
            if (xc.toFirstAttribute()) {
               do {
                  if (attributeNameSet.contains(xc.getName())) {
                     result.add(xc.getObject());
                  }
               } while(xc.toNextAttribute());
            }

            if (result.size() == 0) {
               var4 = EMPTY_RESULT;
               return var4;
            }

            var4 = (XmlObject[])((XmlObject[])result.toArray(EMPTY_RESULT));
         } finally {
            xc.dispose();
         }

         return var4;
      }
   }

   public Object writeReplace() {
      synchronized(this.monitor()) {
         return this.isRootXmlObject() ? new SerializedRootObject(this) : new SerializedInteriorObject(this, this.getRootXmlObject());
      }
   }

   private boolean isRootXmlObject() {
      XmlCursor cur = this.newCursor();
      if (cur == null) {
         return false;
      } else {
         boolean result = !cur.toParent();
         cur.dispose();
         return result;
      }
   }

   private XmlObject getRootXmlObject() {
      XmlCursor cur = this.newCursor();
      if (cur == null) {
         return this;
      } else {
         cur.toStartDoc();
         XmlObject result = cur.getObject();
         cur.dispose();
         return result;
      }
   }

   protected static Object java_value(XmlObject obj) {
      if (obj.isNil()) {
         return null;
      } else if (!(obj instanceof XmlAnySimpleType)) {
         return obj;
      } else {
         SchemaType instanceType = ((SimpleValue)obj).instanceType();

         assert instanceType != null : "Nil case should have been handled above";

         if (instanceType.getSimpleVariety() == 3) {
            return ((SimpleValue)obj).getListValue();
         } else {
            SimpleValue base = (SimpleValue)obj;
            switch (instanceType.getPrimitiveType().getBuiltinTypeCode()) {
               case 3:
                  return base.getBooleanValue() ? Boolean.TRUE : Boolean.FALSE;
               case 4:
               case 5:
                  return base.getByteArrayValue();
               case 6:
                  return base.getStringValue();
               case 7:
                  return base.getQNameValue();
               case 9:
                  return new Float(base.getFloatValue());
               case 10:
                  return new Double(base.getDoubleValue());
               case 11:
                  switch (instanceType.getDecimalSize()) {
                     case 8:
                        return new Byte(base.getByteValue());
                     case 16:
                        return new Short(base.getShortValue());
                     case 32:
                        return new Integer(base.getIntValue());
                     case 64:
                        return new Long(base.getLongValue());
                     case 1000000:
                        return base.getBigIntegerValue();
                     default:
                        assert false : "invalid numeric bit count";
                     case 1000001:
                        return base.getBigDecimalValue();
                  }
               case 13:
                  return base.getGDurationValue();
               case 14:
               case 15:
               case 16:
               case 17:
               case 18:
               case 19:
               case 20:
               case 21:
                  return base.getCalendarValue();
               default:
                  assert false : "encountered nonprimitive type.";
               case 2:
               case 8:
               case 12:
                  return base.getStringValue();
            }
         }
      }
   }

   protected XmlAnySimpleType get_default_attribute_value(QName name) {
      SchemaType sType = this.schemaType();
      SchemaAttributeModel aModel = sType.getAttributeModel();
      if (aModel == null) {
         return null;
      } else {
         SchemaLocalAttribute sAttr = aModel.getAttribute(name);
         return sAttr == null ? null : sAttr.getDefaultValue();
      }
   }

   private static class SerializedInteriorObject implements Serializable {
      private static final long serialVersionUID = 1L;
      transient XmlObject _impl;
      transient XmlObject _root;

      private SerializedInteriorObject() {
      }

      private SerializedInteriorObject(XmlObject impl, XmlObject root) {
         this._impl = impl;
         this._root = root;
      }

      private void writeObject(ObjectOutputStream out) throws IOException {
         out.writeObject(this._root);
         out.writeBoolean(false);
         out.writeInt(this.distanceToRoot());
      }

      private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
         this._root = (XmlObject)in.readObject();
         in.readBoolean();
         this._impl = this.objectAtDistance(in.readInt());
      }

      private Object readResolve() throws ObjectStreamException {
         return this._impl;
      }

      private int distanceToRoot() {
         XmlCursor cur = this._impl.newCursor();
         int count = 0;

         while(!cur.toPrevToken().isNone()) {
            if (!cur.currentTokenType().isNamespace()) {
               ++count;
            }
         }

         cur.dispose();
         return count;
      }

      private XmlObject objectAtDistance(int count) {
         XmlCursor cur = this._root.newCursor();

         while(count > 0) {
            cur.toNextToken();
            if (!cur.currentTokenType().isNamespace()) {
               --count;
            }
         }

         XmlObject result = cur.getObject();
         cur.dispose();
         return result;
      }

      // $FF: synthetic method
      SerializedInteriorObject(XmlObject x0, XmlObject x1, Object x2) {
         this(x0, x1);
      }
   }

   private static class SerializedRootObject implements Serializable {
      private static final long serialVersionUID = 1L;
      transient Class _xbeanClass;
      transient XmlObject _impl;

      private SerializedRootObject() {
      }

      private SerializedRootObject(XmlObject impl) {
         this._xbeanClass = impl.schemaType().getJavaClass();
         this._impl = impl;
      }

      private void writeObject(ObjectOutputStream out) throws IOException {
         out.writeObject(this._xbeanClass);
         out.writeShort(0);
         out.writeShort(1);
         out.writeShort(1);
         String xmlText = this._impl.xmlText();
         out.writeObject(xmlText);
         out.writeBoolean(false);
      }

      private void readObject(ObjectInputStream in) throws IOException {
         try {
            this._xbeanClass = (Class)in.readObject();
            int utfBytes = in.readUnsignedShort();
            int majorVersionNum = 0;
            int minorVersionNum = 0;
            if (utfBytes == 0) {
               majorVersionNum = in.readUnsignedShort();
               minorVersionNum = in.readUnsignedShort();
            }

            String xmlText;
            xmlText = null;
            label24:
            switch (majorVersionNum) {
               case 0:
                  xmlText = this.readObjectV0(in, utfBytes);
                  in.readBoolean();
                  break;
               case 1:
                  switch (minorVersionNum) {
                     case 1:
                        xmlText = (String)in.readObject();
                        in.readBoolean();
                        break label24;
                     default:
                        throw new IOException("Deserialization error: version number " + majorVersionNum + "." + minorVersionNum + " not supported.");
                  }
               default:
                  throw new IOException("Deserialization error: version number " + majorVersionNum + "." + minorVersionNum + " not supported.");
            }

            XmlOptions opts;
            if (XmlObjectBase.isRepackagedXmlBeans) {
               opts = new XmlOptions();
            } else {
               opts = new XmlOptions(XmlOptions.getDeserializeXmlOptions());
            }

            opts.setDocumentType(XmlBeans.typeForClass(this._xbeanClass));
            this._impl = XmlBeans.getContextTypeLoader().parse((String)xmlText, (SchemaType)null, opts);
         } catch (Exception var7) {
            throw (IOException)((IOException)(new IOException(var7.getMessage())).initCause(var7));
         }
      }

      private String readObjectV0(ObjectInputStream in, int utfBytes) throws IOException {
         byte[] bArray = new byte[utfBytes + 2];
         bArray[0] = (byte)(255 & utfBytes >> 8);
         bArray[1] = (byte)(255 & utfBytes);

         int totalBytesRead;
         int numRead;
         for(totalBytesRead = 0; totalBytesRead < utfBytes; totalBytesRead += numRead) {
            numRead = in.read(bArray, 2 + totalBytesRead, utfBytes - totalBytesRead);
            if (numRead == -1) {
               break;
            }
         }

         if (totalBytesRead != utfBytes) {
            throw new IOException("Error reading backwards compatible XmlObject: number of bytes read (" + totalBytesRead + ") != number expected (" + utfBytes + ")");
         } else {
            DataInputStream dis = null;
            String str = null;

            try {
               dis = new DataInputStream(new ByteArrayInputStream(bArray));
               str = dis.readUTF();
            } finally {
               if (dis != null) {
                  dis.close();
               }

            }

            return str;
         }
      }

      private Object readResolve() throws ObjectStreamException {
         return this._impl;
      }

      // $FF: synthetic method
      SerializedRootObject(XmlObject x0, Object x1) {
         this(x0);
      }
   }

   private static final class ImmutableValueValidationContext implements ValidationContext {
      private XmlObject _loc;
      private Collection _coll;

      ImmutableValueValidationContext(Collection coll, XmlObject loc) {
         this._coll = coll;
         this._loc = loc;
      }

      public void invalid(String message) {
         this._coll.add(XmlError.forObject(message, this._loc));
      }

      public void invalid(String code, Object[] args) {
         this._coll.add(XmlError.forObject(code, args, this._loc));
      }
   }

   private static final class ValueOutOfRangeValidationContext implements ValidationContext {
      private ValueOutOfRangeValidationContext() {
      }

      public void invalid(String message) {
         throw new XmlValueOutOfRangeException(message);
      }

      public void invalid(String code, Object[] args) {
         throw new XmlValueOutOfRangeException(code, args);
      }

      // $FF: synthetic method
      ValueOutOfRangeValidationContext(Object x0) {
         this();
      }
   }
}
