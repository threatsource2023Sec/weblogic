package com.bea.staxb.runtime.internal;

import com.bea.staxb.types.DotNetType;
import com.bea.staxb.types.IndigoChar;
import com.bea.staxb.types.IndigoDuration;
import com.bea.staxb.types.IndigoGuid;
import com.bea.staxb.types.UnsignedByte;
import com.bea.staxb.types.UnsignedInt;
import com.bea.staxb.types.UnsignedLong;
import com.bea.staxb.types.UnsignedShort;
import com.bea.xbean.common.InvalidLexicalValueException;
import com.bea.xbean.util.XsTypeConverter;
import com.bea.xml.XmlException;
import javax.xml.namespace.QName;

abstract class DotNetTypeConverter extends BaseSimpleTypeConverter {
   private QName schemaName;
   private Class klass;
   static final DotNetTypeConverter FOR_DOT_NET_LONG;
   static final DotNetTypeConverter FOR_DOT_NET_INT;
   static final DotNetTypeConverter FOR_DOT_NET_SHORT;
   static final DotNetTypeConverter FOR_DOT_NET_BYTE;
   static final DotNetTypeConverter FOR_INDIGO_CHAR;
   static final DotNetTypeConverter FOR_INDIGO_GUID;
   static final DotNetTypeConverter FOR_INDIGO_DURATION;

   protected DotNetTypeConverter(QName schemaName, Class klass) {
      this.schemaName = schemaName;
      this.klass = klass;
   }

   protected Object getObject(UnmarshalResult context) throws XmlException {
      return this.newInstance(context.getStringValue());
   }

   public Object unmarshalAttribute(UnmarshalResult context) throws XmlException {
      return this.newInstance(context.getAttributeStringValue());
   }

   public Object unmarshalAttribute(CharSequence lexical_value, UnmarshalResult result) throws XmlException {
      try {
         String f = XsTypeConverter.lexString(lexical_value);
         return this.newInstance(f);
      } catch (NumberFormatException var4) {
         throw new InvalidLexicalValueException(var4, result.getLocation());
      }
   }

   public CharSequence print(Object value, MarshalResult result) {
      DotNetType val = (DotNetType)value;
      return XsTypeConverter.printString(val.getStringValue());
   }

   protected abstract DotNetType newInstance(String var1);

   protected Class getJavaClass() {
      return this.klass;
   }

   public QName getXmlType() {
      return this.schemaName;
   }

   static {
      FOR_DOT_NET_LONG = new DotNetTypeConverter(UnsignedLong.QNAME, UnsignedLong.class) {
         protected DotNetType newInstance(String value) {
            return new UnsignedLong(value);
         }
      };
      FOR_DOT_NET_INT = new DotNetTypeConverter(UnsignedInt.QNAME, UnsignedInt.class) {
         protected DotNetType newInstance(String value) {
            return new UnsignedInt(value);
         }
      };
      FOR_DOT_NET_SHORT = new DotNetTypeConverter(UnsignedShort.QNAME, UnsignedShort.class) {
         protected DotNetType newInstance(String value) {
            return new UnsignedShort(value);
         }
      };
      FOR_DOT_NET_BYTE = new DotNetTypeConverter(UnsignedByte.QNAME, UnsignedByte.class) {
         protected DotNetType newInstance(String value) {
            return new UnsignedByte(value);
         }
      };
      FOR_INDIGO_CHAR = new DotNetTypeConverter(IndigoChar.QNAME, IndigoChar.class) {
         protected DotNetType newInstance(String value) {
            return new IndigoChar(value);
         }
      };
      FOR_INDIGO_GUID = new DotNetTypeConverter(IndigoGuid.QNAME, IndigoGuid.class) {
         protected DotNetType newInstance(String value) {
            return new IndigoGuid(value);
         }
      };
      FOR_INDIGO_DURATION = new DotNetTypeConverter(IndigoDuration.QNAME, IndigoDuration.class) {
         protected DotNetType newInstance(String value) {
            return new IndigoDuration(value);
         }
      };
   }
}
