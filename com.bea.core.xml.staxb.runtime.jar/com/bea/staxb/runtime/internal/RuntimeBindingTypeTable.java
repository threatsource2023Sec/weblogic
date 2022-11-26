package com.bea.staxb.runtime.internal;

import com.bea.staxb.buildtime.internal.bts.BindingLoader;
import com.bea.staxb.buildtime.internal.bts.BindingType;
import com.bea.staxb.buildtime.internal.bts.BindingTypeName;
import com.bea.staxb.buildtime.internal.bts.BindingTypeVisitor;
import com.bea.staxb.buildtime.internal.bts.BuiltinBindingLoader;
import com.bea.staxb.buildtime.internal.bts.BuiltinBindingType;
import com.bea.staxb.buildtime.internal.bts.ByNameBean;
import com.bea.staxb.buildtime.internal.bts.JavaTypeName;
import com.bea.staxb.buildtime.internal.bts.JaxrpcEnumType;
import com.bea.staxb.buildtime.internal.bts.ListArrayType;
import com.bea.staxb.buildtime.internal.bts.SimpleBindingType;
import com.bea.staxb.buildtime.internal.bts.SimpleContentBean;
import com.bea.staxb.buildtime.internal.bts.SimpleDocumentBinding;
import com.bea.staxb.buildtime.internal.bts.SoapArrayType;
import com.bea.staxb.buildtime.internal.bts.WrappedArrayType;
import com.bea.staxb.buildtime.internal.bts.XmlTypeName;
import com.bea.staxb.types.XMLGregorianCalendar;
import com.bea.xml.GDuration;
import com.bea.xml.XmlCalendar;
import com.bea.xml.XmlException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;

final class RuntimeBindingTypeTable {
   private final Map initedTypeMap;
   private final Map tempTypeMap;
   private final FactoryTypeVisitor typeVisitor;
   static final String XSD_NS = "http://www.w3.org/2001/XMLSchema";
   private static final String SOAPENC_NS = "http://schemas.xmlsoap.org/soap/encoding/";
   private static final ConcurrentHashMap BUILTIN_TYPE_MAP;

   static RuntimeBindingTypeTable createTable() {
      RuntimeBindingTypeTable tbl = new RuntimeBindingTypeTable(new ConcurrentHashMap(BUILTIN_TYPE_MAP));
      return tbl;
   }

   private RuntimeBindingTypeTable(Map typeMap) {
      this.tempTypeMap = new HashMap();
      this.typeVisitor = new FactoryTypeVisitor();
      this.initedTypeMap = typeMap;
   }

   private RuntimeBindingTypeTable() {
      this(new ConcurrentHashMap());
   }

   RuntimeBindingType createRuntimeType(BindingType type, BindingLoader binding_loader) throws XmlException {
      assert type != null;

      RuntimeBindingType rtype = (RuntimeBindingType)this.initedTypeMap.get(type);
      if (rtype != null) {
         return rtype;
      } else {
         synchronized(this) {
            rtype = (RuntimeBindingType)this.tempTypeMap.get(type);
            if (rtype == null) {
               rtype = this.allocateType(type);
               this.tempTypeMap.put(type, rtype);
               rtype.external_initialize(this, binding_loader);
               this.initedTypeMap.put(type, rtype);
               this.tempTypeMap.remove(type);
            }
         }

         assert rtype != null;

         return rtype;
      }
   }

   private RuntimeBindingType allocateType(BindingType type) throws XmlException {
      type.accept(this.typeVisitor);
      return this.typeVisitor.getRuntimeBindingType();
   }

   private WrappedArrayRuntimeBindingType createRuntimeType(WrappedArrayType type, BindingLoader binding_loader) throws XmlException {
      RuntimeBindingType rtt = this.createRuntimeTypeInternal(type, binding_loader);
      return (WrappedArrayRuntimeBindingType)rtt;
   }

   private SoapArrayRuntimeBindingType createRuntimeType(SoapArrayType type, BindingLoader binding_loader) throws XmlException {
      RuntimeBindingType rtt = this.createRuntimeTypeInternal(type, binding_loader);
      return (SoapArrayRuntimeBindingType)rtt;
   }

   private ListArrayRuntimeBindingType createRuntimeType(ListArrayType type, BindingLoader binding_loader) throws XmlException {
      RuntimeBindingType rtt = this.createRuntimeTypeInternal(type, binding_loader);
      return (ListArrayRuntimeBindingType)rtt;
   }

   private ByNameRuntimeBindingType createRuntimeType(ByNameBean type, BindingLoader binding_loader) throws XmlException {
      RuntimeBindingType rtt = this.createRuntimeTypeInternal(type, binding_loader);
      return (ByNameRuntimeBindingType)rtt;
   }

   private SimpleContentRuntimeBindingType createRuntimeType(SimpleContentBean type, BindingLoader binding_loader) throws XmlException {
      RuntimeBindingType rtt = this.createRuntimeTypeInternal(type, binding_loader);
      return (SimpleContentRuntimeBindingType)rtt;
   }

   private JaxrpcEnumRuntimeBindingType createRuntimeType(JaxrpcEnumType type, BindingLoader binding_loader) throws XmlException {
      RuntimeBindingType rtt = this.createRuntimeTypeInternal(type, binding_loader);
      return (JaxrpcEnumRuntimeBindingType)rtt;
   }

   private RuntimeBindingType createRuntimeTypeInternal(BindingType type, BindingLoader loader) throws XmlException {
      return this.createRuntimeType(type, loader);
   }

   private void addBuiltinType(String xsdType, JavaTypeName jName, TypeConverter converter) {
      QName xml_type = new QName("http://www.w3.org/2001/XMLSchema", xsdType);
      this.addBuiltinType(xml_type, jName, converter);
   }

   private void addBuiltinSoapType(String xsdType, JavaTypeName jName, TypeConverter converter) {
      QName xml_type = new QName("http://schemas.xmlsoap.org/soap/encoding/", xsdType);
      this.addBuiltinType(xml_type, jName, converter);
   }

   private void addBuiltinType(QName xml_type, JavaTypeName jName, TypeConverter converter) {
      BindingLoader default_builtin_loader = BuiltinBindingLoader.getBuiltinBindingLoader(false);
      XmlTypeName xName = XmlTypeName.forTypeNamed(xml_type);
      BindingTypeName btname = BindingTypeName.forPair(jName, xName);
      BindingType btype = default_builtin_loader.getBindingType(btname);
      if (btype == null) {
         BindingLoader jaxrpc_builtin_loader = BuiltinBindingLoader.getBuiltinBindingLoader(true);
         btype = jaxrpc_builtin_loader.getBindingType(btname);
      }

      if (btype == null) {
         throw new AssertionError("failed to find builtin for java:" + jName + " - xsd:" + xName);
      } else {
         assert btype instanceof BuiltinBindingType : "unexpected type: " + btype;

         Object builtin;
         try {
            if (xml_type.equals(AnyTypeRuntimeBindingType.ANY_TYPE_NAME)) {
               builtin = new AnyTypeRuntimeBindingType((BuiltinBindingType)btype, converter);
            } else if (xml_type.equals(AnyRuntimeBindingType.ANY_NAME)) {
               builtin = new AnyRuntimeBindingType((BuiltinBindingType)btype, converter);
            } else if (xml_type.equals(QNameRuntimeBindingType.QNAME_TYPE_NAME)) {
               builtin = new QNameRuntimeBindingType((BuiltinBindingType)btype, converter);
            } else {
               builtin = new BuiltinRuntimeBindingType((BuiltinBindingType)btype, converter);
            }
         } catch (XmlException var10) {
            throw new AssertionError(var10);
         }

         this.initedTypeMap.put(btype, builtin);
      }
   }

   private void addBuiltinSoapType(String xsdType, Class javaClass, TypeConverter converter) {
      JavaTypeName jName = JavaTypeName.forClassName(javaClass.getName());
      this.addBuiltinSoapType(xsdType, jName, converter);
   }

   private void addBuiltinType(String xsdType, Class javaClass, TypeConverter converter) {
      JavaTypeName jName = JavaTypeName.forClassName(javaClass.getName());
      this.addBuiltinType(xsdType, jName, converter);
   }

   private void addDotNetCompatibleType(DotNetTypeConverter unsign_conv) {
      JavaTypeName jName = JavaTypeName.forClassName(unsign_conv.getJavaClass().getName());
      this.addBuiltinType((QName)unsign_conv.getXmlType(), (JavaTypeName)jName, unsign_conv);
   }

   private void addBuiltinTypes() {
      this.addBuiltinType((String)"anyType", (Class)Object.class, new ObjectAnyTypeConverter());
      this.addBuiltinType((String)"anyType", (Class)SOAPElement.class, new SOAPElementAnyTypeConverter());
      this.addBuiltinType((String)"any", (Class)SOAPElement.class, new SOAPElementAnyTypeConverter());
      FloatTypeConverter float_conv = new FloatTypeConverter();
      this.addBuiltinType((String)"float", (Class)Float.TYPE, float_conv);
      this.addBuiltinType((String)"float", (Class)Float.class, float_conv);
      DoubleTypeConverter double_conv = new DoubleTypeConverter();
      this.addBuiltinType((String)"double", (Class)Double.TYPE, double_conv);
      this.addBuiltinType((String)"double", (Class)Double.class, double_conv);
      IntegerTypeConverter integer_conv = new IntegerTypeConverter();
      Class bigint = BigInteger.class;
      this.addBuiltinType((String)"integer", (Class)bigint, integer_conv);
      this.addBuiltinType((String)"nonPositiveInteger", (Class)bigint, integer_conv);
      this.addBuiltinType((String)"negativeInteger", (Class)bigint, integer_conv);
      this.addBuiltinType((String)"nonNegativeInteger", (Class)bigint, integer_conv);
      this.addBuiltinType((String)"positiveInteger", (Class)bigint, integer_conv);
      this.addBuiltinType((String)"unsignedLong", (Class)bigint, integer_conv);
      TypeConverter i2iconv = new IntegerToIntTypeConverter();
      this.addBuiltinType((String)"integer", (Class)Integer.TYPE, i2iconv);
      this.addBuiltinType((String)"nonPositiveInteger", (Class)Integer.TYPE, i2iconv);
      this.addBuiltinType((String)"negativeInteger", (Class)Integer.TYPE, i2iconv);
      this.addBuiltinType((String)"nonNegativeInteger", (Class)Integer.TYPE, i2iconv);
      this.addBuiltinType((String)"positiveInteger", (Class)Integer.TYPE, i2iconv);
      this.addBuiltinType((String)"unsignedLong", (Class)Integer.TYPE, i2iconv);
      DecimalTypeConverter decimal_conv = new DecimalTypeConverter();
      this.addBuiltinType((String)"decimal", (Class)BigDecimal.class, decimal_conv);
      LongTypeConverter long_conv = new LongTypeConverter();
      this.addBuiltinType((String)"long", (Class)Long.TYPE, long_conv);
      this.addBuiltinType((String)"long", (Class)Long.class, long_conv);
      this.addBuiltinType((String)"unsignedInt", (Class)Long.TYPE, long_conv);
      this.addBuiltinType((String)"unsignedInt", (Class)Long.class, long_conv);
      IntTypeConverter int_conv = new IntTypeConverter();
      this.addBuiltinType((String)"int", (Class)Integer.TYPE, int_conv);
      this.addBuiltinType((String)"int", (Class)Integer.class, int_conv);
      this.addBuiltinType((String)"unsignedShort", (Class)Integer.TYPE, int_conv);
      this.addBuiltinType((String)"unsignedShort", (Class)Integer.class, int_conv);
      ShortTypeConverter short_conv = new ShortTypeConverter();
      this.addBuiltinType((String)"short", (Class)Short.TYPE, short_conv);
      this.addBuiltinType((String)"short", (Class)Short.class, short_conv);
      this.addBuiltinType((String)"unsignedByte", (Class)Short.TYPE, short_conv);
      this.addBuiltinType((String)"unsignedByte", (Class)Short.class, short_conv);
      ByteTypeConverter byte_conv = new ByteTypeConverter();
      this.addBuiltinType((String)"byte", (Class)Byte.TYPE, byte_conv);
      this.addBuiltinType((String)"byte", (Class)Byte.class, byte_conv);
      this.addDotNetCompatibleType(DotNetTypeConverter.FOR_INDIGO_CHAR);
      this.addDotNetCompatibleType(DotNetTypeConverter.FOR_DOT_NET_BYTE);
      this.addDotNetCompatibleType(DotNetTypeConverter.FOR_DOT_NET_INT);
      this.addDotNetCompatibleType(DotNetTypeConverter.FOR_DOT_NET_LONG);
      this.addDotNetCompatibleType(DotNetTypeConverter.FOR_DOT_NET_SHORT);
      this.addDotNetCompatibleType(DotNetTypeConverter.FOR_INDIGO_DURATION);
      this.addDotNetCompatibleType(DotNetTypeConverter.FOR_INDIGO_GUID);
      this.addBuiltinType((String)"dateTime", (Class)XMLGregorianCalendar.class, new XMLCalendarTypeConverter(14));
      BooleanTypeConverter boolean_conv = new BooleanTypeConverter();
      this.addBuiltinType((String)"boolean", (Class)Boolean.TYPE, boolean_conv);
      this.addBuiltinType((String)"boolean", (Class)Boolean.class, boolean_conv);
      AnyUriToUriTypeConverter uri_uri_conv = new AnyUriToUriTypeConverter();
      this.addBuiltinType((String)"anyURI", (Class)URI.class, uri_uri_conv);
      Class str = String.class;
      this.addBuiltinType((String)"anySimpleType", (Class)str, new AnySimpleTypeConverter());
      StringTypeConverter string_conv = new StringTypeConverter();
      TypeConverter collapsing_string_conv = CollapseStringTypeConverter.getInstance();
      this.addBuiltinType((String)"string", (Class)str, string_conv);
      this.addBuiltinType("normalizedString", str, ReplaceStringTypeConverter.getInstance());
      this.addBuiltinType("token", str, collapsing_string_conv);
      this.addBuiltinType("language", str, collapsing_string_conv);
      this.addBuiltinType("Name", str, collapsing_string_conv);
      this.addBuiltinType("NCName", str, collapsing_string_conv);
      this.addBuiltinType("NMTOKEN", str, collapsing_string_conv);
      this.addBuiltinType("ID", str, collapsing_string_conv);
      this.addBuiltinType("IDREF", str, collapsing_string_conv);
      this.addBuiltinType("ENTITY", str, collapsing_string_conv);
      this.addBuiltinType("NOTATION", str, collapsing_string_conv);
      this.addBuiltinType("duration", str, collapsing_string_conv);
      this.addBuiltinType("gDay", str, collapsing_string_conv);
      this.addBuiltinType("gMonth", str, collapsing_string_conv);
      this.addBuiltinType("gMonthDay", str, collapsing_string_conv);
      this.addBuiltinType("gYear", str, collapsing_string_conv);
      this.addBuiltinType("gYearMonth", str, collapsing_string_conv);
      AnyUriToStringTypeConverter uri_conv = new AnyUriToStringTypeConverter();
      this.addBuiltinType((String)"anyURI", (Class)str, uri_conv);
      Class str_array = (new String[0]).getClass();
      StringListArrayConverter string_list_array_conv = new StringListArrayConverter();
      this.addBuiltinType((String)"ENTITIES", (Class)str_array, string_list_array_conv);
      this.addBuiltinType((String)"IDREFS", (Class)str_array, string_list_array_conv);
      this.addBuiltinType((String)"NMTOKENS", (Class)str_array, string_list_array_conv);
      DurationTypeConverter gduration_conv = new DurationTypeConverter();
      this.addBuiltinType((String)"duration", (Class)GDuration.class, gduration_conv);
      Class calendar_class = Calendar.class;
      JavaCalendarTypeConverter date_time_conv = new JavaCalendarTypeConverter(14);
      this.addBuiltinType((String)"dateTime", (Class)calendar_class, date_time_conv);
      Class gregorian_calendar_class = GregorianCalendar.class;
      JavaGregorianCalendarTypeConverter gregorian_calendar_date_time_conv = new JavaGregorianCalendarTypeConverter(14);
      this.addBuiltinType((String)"dateTime", (Class)gregorian_calendar_class, gregorian_calendar_date_time_conv);
      Class bea_calendar_class = XmlCalendar.class;
      JavaGregorianCalendarTypeConverter bea_calendar_date_time_conv = new JavaGregorianCalendarTypeConverter(14);
      this.addBuiltinType((String)"dateTime", (Class)bea_calendar_class, bea_calendar_date_time_conv);
      JavaDateTypeConverter date_datetime_conv = new JavaDateTypeConverter(14);
      this.addBuiltinType((String)"dateTime", (Class)Date.class, date_datetime_conv);
      JavaCalendarTypeConverter time_conv = new JavaCalendarTypeConverter(15);
      this.addBuiltinType((String)"time", (Class)calendar_class, time_conv);
      JavaCalendarTypeConverter date_conv = new JavaCalendarTypeConverter(16);
      this.addBuiltinType((String)"date", (Class)calendar_class, date_conv);
      JavaDateTypeConverter date_date_conv = new JavaDateTypeConverter(16);
      this.addBuiltinType((String)"date", (Class)Date.class, date_date_conv);
      JavaCalendarTypeConverter gday_conv = new JavaCalendarTypeConverter(20);
      this.addBuiltinType((String)"gDay", (Class)calendar_class, gday_conv);
      JavaCalendarTypeConverter gmonth_conv = new JavaCalendarTypeConverter(21);
      this.addBuiltinType((String)"gMonth", (Class)calendar_class, gmonth_conv);
      JavaCalendarTypeConverter gmonth_day_conv = new JavaCalendarTypeConverter(19);
      this.addBuiltinType((String)"gMonthDay", (Class)calendar_class, gmonth_day_conv);
      JavaCalendarTypeConverter gyear_conv = new JavaCalendarTypeConverter(18);
      this.addBuiltinType((String)"gYear", (Class)calendar_class, gyear_conv);
      JavaCalendarTypeConverter gyearmonth_conv = new JavaCalendarTypeConverter(17);
      this.addBuiltinType((String)"gYearMonth", (Class)calendar_class, gyearmonth_conv);
      this.addBuiltinType((String)"gDay", (Class)Integer.TYPE, new IntDateTypeConverter(20));
      this.addBuiltinType((String)"gMonth", (Class)Integer.TYPE, new IntDateTypeConverter(21));
      this.addBuiltinType((String)"gYear", (Class)Integer.TYPE, new IntDateTypeConverter(18));
      QNameTypeConverter qname_conv = new QNameTypeConverter();
      this.addBuiltinType((String)"QName", (Class)QName.class, qname_conv);
      JavaTypeName byte_array_jname = JavaTypeName.forArray(JavaTypeName.forString("byte"), 1);
      Base64BinaryTypeConverter base64_conv = new Base64BinaryTypeConverter();
      this.addBuiltinType((String)"base64Binary", (JavaTypeName)byte_array_jname, base64_conv);
      HexBinaryTypeConverter hexbin_conv = new HexBinaryTypeConverter();
      this.addBuiltinType((String)"hexBinary", (JavaTypeName)byte_array_jname, hexbin_conv);
      JavaCharTypeConverter char_conv = new JavaCharTypeConverter();
      this.addBuiltinType((String)"string", (Class)Character.TYPE, char_conv);
      this.addBuiltinType((String)"string", (Class)Character.class, char_conv);
      this.addBuiltinSoapType("float", (Class)Float.class, float_conv);
      this.addBuiltinSoapType("double", (Class)Double.class, double_conv);
      this.addBuiltinSoapType("integer", (Class)bigint, integer_conv);
      this.addBuiltinSoapType("nonPositiveInteger", (Class)bigint, integer_conv);
      this.addBuiltinSoapType("negativeInteger", (Class)bigint, integer_conv);
      this.addBuiltinSoapType("nonNegativeInteger", (Class)bigint, integer_conv);
      this.addBuiltinSoapType("positiveInteger", (Class)bigint, integer_conv);
      this.addBuiltinSoapType("unsignedLong", (Class)bigint, integer_conv);
      this.addBuiltinSoapType("decimal", (Class)BigDecimal.class, decimal_conv);
      this.addBuiltinSoapType("long", (Class)Long.class, long_conv);
      this.addBuiltinSoapType("unsignedInt", (Class)Long.class, long_conv);
      this.addBuiltinSoapType("int", (Class)Integer.class, int_conv);
      this.addBuiltinSoapType("unsignedShort", (Class)Integer.class, int_conv);
      this.addBuiltinSoapType("short", (Class)Short.class, short_conv);
      this.addBuiltinSoapType("unsignedByte", (Class)Short.class, short_conv);
      this.addBuiltinSoapType("byte", (Class)Byte.class, byte_conv);
      this.addBuiltinSoapType("boolean", (Class)Boolean.class, boolean_conv);
      this.addBuiltinSoapType("anyURI", (Class)URI.class, uri_uri_conv);
      this.addBuiltinSoapType("string", (Class)str, string_conv);
      this.addBuiltinSoapType("normalizedString", (Class)str, string_conv);
      this.addBuiltinSoapType("token", (Class)str, string_conv);
      this.addBuiltinSoapType("language", (Class)str, string_conv);
      this.addBuiltinSoapType("Name", (Class)str, string_conv);
      this.addBuiltinSoapType("NCName", (Class)str, string_conv);
      this.addBuiltinSoapType("NMTOKEN", (Class)str, string_conv);
      this.addBuiltinSoapType("ID", (Class)str, string_conv);
      this.addBuiltinSoapType("IDREF", (Class)str, string_conv);
      this.addBuiltinSoapType("ENTITY", (Class)str, string_conv);
      this.addBuiltinSoapType("NOTATION", str, collapsing_string_conv);
      this.addBuiltinSoapType("duration", str, collapsing_string_conv);
      this.addBuiltinSoapType("gDay", str, collapsing_string_conv);
      this.addBuiltinSoapType("gMonth", str, collapsing_string_conv);
      this.addBuiltinSoapType("gMonthDay", str, collapsing_string_conv);
      this.addBuiltinSoapType("gYear", str, collapsing_string_conv);
      this.addBuiltinSoapType("gYearMonth", str, collapsing_string_conv);
      this.addBuiltinSoapType("anyURI", (Class)str, uri_conv);
      this.addBuiltinSoapType("ENTITIES", (Class)str_array, string_list_array_conv);
      this.addBuiltinSoapType("IDREFS", (Class)str_array, string_list_array_conv);
      this.addBuiltinSoapType("NMTOKENS", (Class)str_array, string_list_array_conv);
      this.addBuiltinSoapType("duration", (Class)GDuration.class, gduration_conv);
      this.addBuiltinSoapType("dateTime", (Class)calendar_class, date_time_conv);
      this.addBuiltinSoapType("dateTime", (Class)gregorian_calendar_class, gregorian_calendar_date_time_conv);
      this.addBuiltinSoapType("dateTime", (Class)Date.class, date_datetime_conv);
      this.addBuiltinSoapType("time", (Class)calendar_class, time_conv);
      this.addBuiltinSoapType("date", (Class)calendar_class, date_conv);
      this.addBuiltinSoapType("date", (Class)Date.class, date_date_conv);
      this.addBuiltinSoapType("gDay", (Class)calendar_class, gday_conv);
      this.addBuiltinSoapType("gMonth", (Class)calendar_class, gmonth_conv);
      this.addBuiltinSoapType("gMonthDay", (Class)calendar_class, gmonth_day_conv);
      this.addBuiltinSoapType("gYear", (Class)calendar_class, gyear_conv);
      this.addBuiltinSoapType("gYearMonth", (Class)calendar_class, gyearmonth_conv);
      this.addBuiltinSoapType("QName", (Class)QName.class, qname_conv);
      this.addBuiltinSoapType("base64Binary", (JavaTypeName)byte_array_jname, base64_conv);
      this.addBuiltinSoapType("base64", (JavaTypeName)byte_array_jname, base64_conv);
      this.addBuiltinSoapType("hexBinary", (JavaTypeName)byte_array_jname, hexbin_conv);
   }

   private static TypeUnmarshaller createSimpleTypeUnmarshaller(SimpleBindingType stype, BindingLoader loader, RuntimeBindingTypeTable table) throws XmlException {
      int curr_ws = 0;
      SimpleBindingType curr = stype;

      while(true) {
         if (curr_ws == 0) {
            curr_ws = curr.getWhitespace();
         }

         BindingTypeName asif_name = curr.getAsIfBindingTypeName();
         if (asif_name == null) {
            throw new XmlException("missing as-xml type on " + curr.getName());
         }

         BindingType asif_new = loader.getBindingType(asif_name);
         if (asif_new instanceof BuiltinBindingType) {
            BuiltinBindingType resolved = (BuiltinBindingType)asif_new;

            assert resolved != null;

            switch (curr_ws) {
               case 0:
                  TypeUnmarshaller um = table.createRuntimeType((BindingType)resolved, loader).getUnmarshaller();
                  if (um != null) {
                     return um;
                  }

                  String msg = "unable to get simple type unmarshaller for " + stype + " resolved to " + resolved;
                  throw new AssertionError(msg);
               case 1:
                  return PreserveStringTypeConverter.getInstance();
               case 2:
                  return ReplaceStringTypeConverter.getInstance();
               case 3:
                  return CollapseStringTypeConverter.getInstance();
               default:
                  throw new AssertionError("invalid whitespace: " + curr_ws);
            }
         }

         if (!(asif_new instanceof SimpleBindingType)) {
            String msg = "invalid as-xml type: " + asif_name + " on type: " + curr.getName();
            throw new XmlException(msg);
         }

         curr = (SimpleBindingType)asif_new;
      }
   }

   TypeUnmarshaller createUnmarshaller(BindingType binding_type, BindingLoader loader) throws XmlException {
      TypeVisitor type_visitor = new TypeVisitor(this, loader);
      binding_type.accept(type_visitor);
      TypeUnmarshaller type_um = type_visitor.getUnmarshaller();
      type_um.initialize(this, loader);
      return type_um;
   }

   private TypeMarshaller createMarshaller(BindingTypeName type_name, BindingLoader loader) throws XmlException {
      BindingType binding_type = loader.getBindingType(type_name);
      if (binding_type == null) {
         String msg = "unable to load type for " + type_name;
         throw new XmlException(msg);
      } else {
         return this.createMarshaller(binding_type, loader);
      }
   }

   TypeMarshaller createMarshaller(BindingType binding_type, BindingLoader loader) throws XmlException {
      Object m;
      if (binding_type instanceof SimpleContentBean) {
         SimpleContentBean scb = (SimpleContentBean)binding_type;
         SimpleContentRuntimeBindingType rtt = this.createRuntimeType(scb, loader);
         m = new SimpleContentBeanMarshaller(rtt);
      } else if (binding_type instanceof SimpleBindingType) {
         SimpleBindingType stype = (SimpleBindingType)binding_type;
         BindingTypeName asif_name = stype.getAsIfBindingTypeName();
         if (asif_name == null) {
            throw new XmlException("no asif for " + stype);
         }

         m = this.createMarshaller(asif_name, loader);
      } else if (binding_type instanceof JaxrpcEnumType) {
         JaxrpcEnumType enum_type = (JaxrpcEnumType)binding_type;
         JaxrpcEnumRuntimeBindingType rtt = this.createRuntimeType(enum_type, loader);
         m = new JaxrpcEnumMarsahller(rtt);
      } else if (binding_type instanceof ListArrayType) {
         ListArrayType la_type = (ListArrayType)binding_type;
         ListArrayRuntimeBindingType rtt = this.createRuntimeType(la_type, loader);
         m = new ListArrayConverter(rtt);
      } else if (binding_type instanceof BuiltinBindingType) {
         RuntimeBindingType rtt = this.createRuntimeType(binding_type, loader);
         m = rtt.getMarshaller();

         assert m != null;
      } else {
         m = null;
      }

      return (TypeMarshaller)m;
   }

   static {
      RuntimeBindingTypeTable tbl = new RuntimeBindingTypeTable();
      tbl.addBuiltinTypes();
      BUILTIN_TYPE_MAP = (ConcurrentHashMap)tbl.initedTypeMap;
   }

   private static final class FactoryTypeVisitor implements BindingTypeVisitor {
      private RuntimeBindingType runtimeBindingType;

      private FactoryTypeVisitor() {
      }

      public RuntimeBindingType getRuntimeBindingType() {
         return this.runtimeBindingType;
      }

      public void visit(BuiltinBindingType builtinBindingType) throws XmlException {
         String s = "internal error: no builtin runtime type for " + builtinBindingType;
         throw new AssertionError(s);
      }

      public void visit(ByNameBean byNameBean) throws XmlException {
         this.runtimeBindingType = new ByNameRuntimeBindingType(byNameBean);
      }

      public void visit(SimpleContentBean simpleContentBean) throws XmlException {
         this.runtimeBindingType = new SimpleContentRuntimeBindingType(simpleContentBean);
      }

      public void visit(SimpleBindingType simpleBindingType) throws XmlException {
         this.runtimeBindingType = new SimpleRuntimeBindingType(simpleBindingType);
      }

      public void visit(JaxrpcEnumType jaxrpcEnumType) throws XmlException {
         this.runtimeBindingType = new JaxrpcEnumRuntimeBindingType(jaxrpcEnumType);
      }

      public void visit(SimpleDocumentBinding simpleDocumentBinding) throws XmlException {
         throw new AssertionError("not valid here: " + simpleDocumentBinding);
      }

      public void visit(WrappedArrayType wrappedArrayType) throws XmlException {
         this.runtimeBindingType = new WrappedArrayRuntimeBindingType(wrappedArrayType);
      }

      public void visit(SoapArrayType soapArrayType) throws XmlException {
         this.runtimeBindingType = new SoapArrayRuntimeBindingType(soapArrayType);
      }

      public void visit(ListArrayType listArrayType) throws XmlException {
         this.runtimeBindingType = new ListArrayRuntimeBindingType(listArrayType);
      }

      // $FF: synthetic method
      FactoryTypeVisitor(Object x0) {
         this();
      }
   }

   private static final class TypeVisitor implements BindingTypeVisitor {
      private final BindingLoader loader;
      private final RuntimeBindingTypeTable typeTable;
      private TypeUnmarshaller typeUnmarshaller;

      public TypeVisitor(RuntimeBindingTypeTable runtimeBindingTypeTable, BindingLoader loader) {
         this.typeTable = runtimeBindingTypeTable;
         this.loader = loader;
      }

      public void visit(BuiltinBindingType builtinBindingType) throws XmlException {
         throw new AssertionError("internal error: no builtin unmarshaller for " + builtinBindingType);
      }

      public void visit(ByNameBean byNameBean) throws XmlException {
         ByNameRuntimeBindingType rtt = this.typeTable.createRuntimeType(byNameBean, this.loader);
         this.typeUnmarshaller = new ByNameUnmarshaller(rtt);
      }

      public void visit(SimpleContentBean simpleContentBean) throws XmlException {
         SimpleContentRuntimeBindingType rtt = this.typeTable.createRuntimeType(simpleContentBean, this.loader);
         this.typeUnmarshaller = new SimpleContentUnmarshaller(rtt);
      }

      public void visit(SimpleBindingType simpleBindingType) throws XmlException {
         RuntimeBindingTypeTable var10001 = this.typeTable;
         this.typeUnmarshaller = RuntimeBindingTypeTable.createSimpleTypeUnmarshaller(simpleBindingType, this.loader, this.typeTable);
      }

      public void visit(JaxrpcEnumType jaxrpcEnumType) throws XmlException {
         JaxrpcEnumRuntimeBindingType rtt = this.typeTable.createRuntimeType(jaxrpcEnumType, this.loader);
         this.typeUnmarshaller = new JaxrpcEnumUnmarshaller(rtt);
      }

      public void visit(SimpleDocumentBinding simpleDocumentBinding) throws XmlException {
         throw new AssertionError("type not allowed here" + simpleDocumentBinding);
      }

      public void visit(WrappedArrayType wrappedArrayType) throws XmlException {
         WrappedArrayRuntimeBindingType rtt = this.typeTable.createRuntimeType(wrappedArrayType, this.loader);
         this.typeUnmarshaller = new WrappedArrayUnmarshaller(rtt);
      }

      public void visit(SoapArrayType soapArrayType) throws XmlException {
         SoapArrayRuntimeBindingType rtt = this.typeTable.createRuntimeType(soapArrayType, this.loader);
         this.typeUnmarshaller = new SoapArrayUnmarshaller(rtt);
      }

      public void visit(ListArrayType listArrayType) throws XmlException {
         ListArrayRuntimeBindingType rtt = this.typeTable.createRuntimeType(listArrayType, this.loader);
         this.typeUnmarshaller = new ListArrayConverter(rtt);
      }

      public TypeUnmarshaller getUnmarshaller() {
         assert this.typeUnmarshaller != null;

         return this.typeUnmarshaller;
      }
   }
}
