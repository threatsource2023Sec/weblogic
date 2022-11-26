package com.bea.staxb.buildtime.internal;

import com.bea.staxb.buildtime.internal.bts.JavaTypeName;
import com.bea.staxb.buildtime.internal.joust.Expression;
import com.bea.staxb.buildtime.internal.joust.ExpressionFactory;
import com.bea.staxb.buildtime.internal.joust.Variable;
import com.bea.xbean.values.XmlListImpl;
import com.bea.xml.SchemaType;
import com.bea.xml.XmlAnySimpleType;
import com.bea.xml.XmlBase64Binary;
import com.bea.xml.XmlBoolean;
import com.bea.xml.XmlByte;
import com.bea.xml.XmlDouble;
import com.bea.xml.XmlFloat;
import com.bea.xml.XmlHexBinary;
import com.bea.xml.XmlInt;
import com.bea.xml.XmlLong;
import com.bea.xml.XmlQName;
import com.bea.xml.XmlShort;
import com.bea.xml.XmlUnsignedByte;
import com.bea.xml.XmlUnsignedInt;
import com.bea.xml.XmlUnsignedShort;
import java.util.Iterator;
import javax.xml.namespace.QName;

public class EnumerationPrintHelper {
   private boolean mArray;
   private ExpressionFactory mExprFactory;
   private int mTypeCode;
   private int mSchemaTypeCode;
   private boolean mPrimitive;
   private static final int T_STRING = 1;
   private static final int T_GDURATION = 2;
   private static final int T_CALENDAR = 3;
   private static final int T_BOOLEAN = 4;
   private static final int T_FLOAT = 5;
   private static final int T_DOUBLE = 6;
   private static final int T_BIGDECIMAL = 7;
   private static final int T_BIGINTEGER = 8;
   private static final int T_LONG = 9;
   private static final int T_INT = 10;
   private static final int T_SHORT = 11;
   private static final int T_BYTE = 12;
   private static final int T_QNAME = 13;
   private static final int T_FLOAT_CLASS = 14;
   private static final int T_DOUBLE_CLASS = 15;
   private static final int T_LONG_CLASS = 16;
   private static final int T_INT_CLASS = 17;
   private static final int T_SHORT_CLASS = 18;
   private static final int T_BYTE_CLASS = 19;
   private static final int T_BOOLEAN_CLASS = 20;
   private static final int T_URI = 21;
   private static final int T_BYTE_ARRAY = 22;
   private static final int T_GREGORIANCALENDAR = 23;

   public EnumerationPrintHelper(JavaTypeName typeName, ExpressionFactory exprFactory, SchemaType schemaType) {
      this.mSchemaTypeCode = this.extractTypeCode(schemaType);
      if (this.mSchemaTypeCode != 4 && this.mSchemaTypeCode != 5) {
         this.mArray = typeName.getArrayDepth() > 0;
      } else {
         this.mArray = typeName.getArrayDepth() > 1;
      }

      this.mExprFactory = exprFactory;
      String t;
      if (this.mArray) {
         t = typeName.getArrayItemType(1).toString();
      } else {
         t = typeName.toString();
      }

      switch (t.charAt(0)) {
         case 'b':
            if (t.equals("boolean")) {
               this.mTypeCode = 4;
            } else if (t.equals("byte")) {
               this.mTypeCode = 12;
            } else {
               if (!t.equals("byte[]")) {
                  throw new IllegalArgumentException("Unknown simple type: " + t);
               }

               this.mTypeCode = 22;
            }
            break;
         case 'c':
            if (t.equals("com.bea.xml.GDuration")) {
               this.mTypeCode = 2;
            }
            break;
         case 'd':
            if (!t.equals("double")) {
               throw new IllegalArgumentException("Unknown simple type: " + t);
            }

            this.mTypeCode = 6;
            break;
         case 'e':
         case 'g':
         case 'h':
         case 'k':
         case 'm':
         case 'n':
         case 'o':
         case 'p':
         case 'q':
         case 'r':
         default:
            throw new IllegalArgumentException("Unknown simple type: " + t);
         case 'f':
            if (!t.equals("float")) {
               throw new IllegalArgumentException("Unknown simple type: " + t);
            }

            this.mTypeCode = 5;
            break;
         case 'i':
            if (!t.equals("int")) {
               throw new IllegalArgumentException("Unknown simple type: " + t);
            }

            this.mTypeCode = 10;
            break;
         case 'j':
            if (t.equals("java.lang.String")) {
               this.mTypeCode = 1;
            } else if (t.equals("java.util.Calendar")) {
               this.mTypeCode = 3;
            } else if (t.equals("java.util.GregorianCalendar")) {
               this.mTypeCode = 23;
            } else if (t.equals("java.math.BigDecimal")) {
               this.mTypeCode = 7;
            } else if (t.equals("java.math.BigInteger")) {
               this.mTypeCode = 8;
            } else if (t.equals("javax.xml.namespace.QName")) {
               this.mTypeCode = 13;
            } else if (t.equals("java.net.URI")) {
               this.mTypeCode = 21;
            } else if (t.equals(Float.class.getName())) {
               this.mTypeCode = 14;
            } else if (t.equals(Double.class.getName())) {
               this.mTypeCode = 15;
            } else if (t.equals(Long.class.getName())) {
               this.mTypeCode = 16;
            } else if (t.equals(Integer.class.getName())) {
               this.mTypeCode = 17;
            } else if (t.equals(Short.class.getName())) {
               this.mTypeCode = 18;
            } else if (t.equals(Byte.class.getName())) {
               this.mTypeCode = 19;
            } else {
               if (!t.equals(Boolean.class.getName())) {
                  throw new IllegalArgumentException("Unknown simple type: " + t);
               }

               this.mTypeCode = 20;
            }
            break;
         case 'l':
            if (!t.equals("long")) {
               throw new IllegalArgumentException("Unknown simple type: " + t);
            }

            this.mTypeCode = 9;
            break;
         case 's':
            if (!t.equals("short")) {
               throw new IllegalArgumentException("Unknown simple type: " + t);
            }

            this.mTypeCode = 11;
      }

      switch (this.mTypeCode) {
         case 4:
         case 5:
         case 6:
         case 9:
         case 10:
         case 11:
         case 12:
            this.mPrimitive = true;
         case 7:
         case 8:
         default:
      }
   }

   public Expression getInitExpr(XmlAnySimpleType value) {
      if (!this.mArray) {
         return this.getInitExprHelper(value);
      } else {
         StringBuffer arrayInit = new StringBuffer();
         arrayInit.append('{');
         Iterator it = ((XmlListImpl)value).xlistValue().iterator();

         while(it.hasNext()) {
            Expression exprInit = this.getInitExprHelper((XmlAnySimpleType)it.next());
            arrayInit.append(exprInit.getMemento().toString()).append(',').append(' ');
         }

         arrayInit.append('}');
         return this.mExprFactory.createVerbatim(arrayInit.toString());
      }
   }

   private Expression getInitExprHelper(XmlAnySimpleType value) {
      Expression result = null;
      switch (this.mTypeCode) {
         case 1:
            result = this.mExprFactory.createVerbatim("\"" + value.getStringValue() + "\"");
            break;
         case 2:
            result = this.mExprFactory.createVerbatim("new com.bea.xml.GDuration(\"" + value.getStringValue() + "\")");
            break;
         case 3:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.lexDateTime(\"" + value.getStringValue() + "\")");
            break;
         case 4:
            result = this.mExprFactory.createVerbatim(String.valueOf(((XmlBoolean)value).getBooleanValue()));
            break;
         case 5:
            float floatValue = ((XmlFloat)value).getFloatValue();
            if (floatValue == Float.POSITIVE_INFINITY) {
               result = this.mExprFactory.createVerbatim("Float.POSITIVE_INFINITY");
            } else if (floatValue == Float.NEGATIVE_INFINITY) {
               result = this.mExprFactory.createVerbatim("Float.NEGATIVE_INFINITY");
            } else if (Float.isNaN(floatValue)) {
               result = this.mExprFactory.createVerbatim("Float.NaN");
            } else {
               result = this.mExprFactory.createVerbatim("(float)" + String.valueOf(floatValue));
            }
            break;
         case 6:
            double doubleValue = ((XmlDouble)value).getDoubleValue();
            if (doubleValue == Double.POSITIVE_INFINITY) {
               result = this.mExprFactory.createVerbatim("Double.POSITIVE_INFINITY");
            } else if (doubleValue == Double.NEGATIVE_INFINITY) {
               result = this.mExprFactory.createVerbatim("Double.NEGATIVE_INFINITY");
            } else if (Double.isNaN(doubleValue)) {
               result = this.mExprFactory.createVerbatim("Double.NaN");
            } else {
               result = this.mExprFactory.createVerbatim(String.valueOf(doubleValue));
            }
            break;
         case 7:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.lexDecimal(\"" + value.getStringValue() + "\")");
            break;
         case 8:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.lexInteger(\"" + value.getStringValue() + "\")");
            break;
         case 9:
            if (value instanceof XmlUnsignedInt) {
               result = this.mExprFactory.createVerbatim(String.valueOf(((XmlUnsignedInt)value).getLongValue()));
            } else {
               result = this.mExprFactory.createVerbatim(String.valueOf(((XmlLong)value).getLongValue()));
            }
            break;
         case 10:
            if (value instanceof XmlUnsignedShort) {
               result = this.mExprFactory.createVerbatim(String.valueOf(((XmlUnsignedShort)value).getIntValue()));
            } else {
               result = this.mExprFactory.createVerbatim(String.valueOf(((XmlInt)value).getIntValue()));
            }
            break;
         case 11:
            if (value instanceof XmlUnsignedByte) {
               result = this.mExprFactory.createVerbatim(String.valueOf(((XmlUnsignedByte)value).getShortValue()));
            } else {
               result = this.mExprFactory.createVerbatim(String.valueOf(((XmlShort)value).getShortValue()));
            }
            break;
         case 12:
            result = this.mExprFactory.createVerbatim(String.valueOf(((XmlByte)value).getByteValue()));
            break;
         case 13:
            QName qName = ((XmlQName)value).getQNameValue();
            result = this.mExprFactory.createVerbatim("new javax.xml.namespace.QName(\"" + qName.getNamespaceURI() + "\", \"" + qName.getLocalPart() + "\", \"" + qName.getPrefix() + "\")");
            break;
         case 14:
            result = this.mExprFactory.createVerbatim("new Float(" + ((XmlFloat)value).getFloatValue() + ")");
            break;
         case 15:
            result = this.mExprFactory.createVerbatim("new Double(" + ((XmlDouble)value).getDoubleValue() + ")");
            break;
         case 16:
            result = this.mExprFactory.createVerbatim("new Long(" + ((XmlLong)value).getLongValue() + ")");
            break;
         case 17:
            result = this.mExprFactory.createVerbatim("new Integer(" + ((XmlInt)value).getIntValue() + ")");
            break;
         case 18:
            result = this.mExprFactory.createVerbatim("new Short(" + ((XmlShort)value).getShortValue() + ")");
            break;
         case 19:
            result = this.mExprFactory.createVerbatim("new Byte(" + ((XmlByte)value).getByteValue() + ")");
            break;
         case 20:
            result = this.mExprFactory.createVerbatim("new Boolean(" + ((XmlBoolean)value).getBooleanValue() + ")");
            break;
         case 21:
            result = this.mExprFactory.createVerbatim("java.net.URI.create(\"" + value.getStringValue() + "\")");
            break;
         case 22:
            StringBuffer arrayInit = new StringBuffer();
            arrayInit.append('{');
            byte[] bytes = null;
            if (this.mSchemaTypeCode == 4) {
               bytes = ((XmlBase64Binary)value).getByteArrayValue();
            } else if (this.mSchemaTypeCode == 5) {
               bytes = ((XmlHexBinary)value).getByteArrayValue();
            }

            if (bytes != null) {
               for(int i = 0; i < bytes.length; ++i) {
                  arrayInit.append(bytes[i]).append(',').append(' ');
               }

               arrayInit.append('}');
               result = this.mExprFactory.createVerbatim(arrayInit.toString());
            }
            break;
         case 23:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.lexDateTime(\"" + value.getStringValue() + "\")");
      }

      if (result == null) {
         throw new IllegalStateException();
      } else {
         return result;
      }
   }

   public Expression getFromStringExpr(Expression param) {
      Expression result = null;
      String s = param.getMemento().toString();
      switch (this.mTypeCode) {
         case 1:
            result = param;
            break;
         case 2:
            result = this.mExprFactory.createVerbatim("new com.bea.xml.GDuration(" + s + ")");
         case 22:
            if (this.mSchemaTypeCode == 4) {
               result = this.mExprFactory.createVerbatim("com.bea.xbean.util.Base64.encode(" + s + ".getBytes())");
            } else if (this.mSchemaTypeCode == 5) {
               result = this.mExprFactory.createVerbatim("com.bea.xbean.util.HexBin.stringToBytes(" + s + ")");
            }
            break;
         case 3:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.lexDateTime(" + s + ")");
            break;
         case 4:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.lexBoolean(" + s + ")");
            break;
         case 5:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.lexFloat(" + s + ")");
            break;
         case 6:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.lexDouble(" + s + ")");
            break;
         case 7:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.lexDecimal(" + s + ")");
            break;
         case 8:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.lexInteger(" + s + ")");
            break;
         case 9:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.lexLong(" + s + ")");
            break;
         case 10:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.lexInt(" + s + ")");
            break;
         case 11:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.lexShort(" + s + ")");
            break;
         case 12:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.lexByte(" + s + ")");
            break;
         case 13:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.lexQName(" + s + ", new javax.xml.namespace.NamespaceContext() {public String getNamespaceURI(String p) {return \"\";} public String getPrefix(String u) {return null;} public java.util.Iterator getPrefixes(String s) {return null;}})");
            break;
         case 14:
            result = this.mExprFactory.createVerbatim("new Float(XsTypeConverter.lexFloat(" + s + "))");
            break;
         case 15:
            result = this.mExprFactory.createVerbatim("new Double(XsTypeConverter.lexDouble(" + s + "))");
            break;
         case 16:
            result = this.mExprFactory.createVerbatim("new Long(XsTypeConverter.lexLong(" + s + "))");
            break;
         case 17:
            result = this.mExprFactory.createVerbatim("new Integer(XsTypeConverter.lexInt(" + s + "))");
            break;
         case 18:
            result = this.mExprFactory.createVerbatim("new Short(XsTypeConverter.lexShort(" + s + "))");
            break;
         case 19:
            result = this.mExprFactory.createVerbatim("new Byte(XsTypeConverter.lexByte(" + s + "))");
            break;
         case 20:
            result = this.mExprFactory.createVerbatim("new Boolean(XsTypeConverter.lexBoolean(" + s + "))");
            break;
         case 21:
            result = this.mExprFactory.createVerbatim("new java.net.URI(" + s + ")");
            break;
         case 23:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.lexDateTime(" + s + ")");
      }

      if (result == null) {
         throw new IllegalStateException();
      } else {
         return result;
      }
   }

   public String getToXmlString(Variable var, String index) {
      assert this.mArray : "This method can only be called for a list enumeration";

      return this.getToXmlExpr(this.mExprFactory.createVerbatim(var.getMemento().toString() + "[" + index + "]")).getMemento().toString();
   }

   public Expression getToXmlExpr(Expression var) {
      Expression result = null;
      String s = var.getMemento().toString();
      switch (this.mTypeCode) {
         case 1:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.printString(" + s + ").toString()");
            break;
         case 2:
            result = this.mExprFactory.createVerbatim(s + ".toString()");
            break;
         case 3:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.printDateTime(" + s + ", " + this.mSchemaTypeCode + ").toString()");
            break;
         case 4:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.printBoolean(" + s + ").toString()");
            break;
         case 5:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.printFloat(" + s + ").toString()");
            break;
         case 6:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.printDouble(" + s + ").toString()");
            break;
         case 7:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.printDecimal(" + s + ").toString()");
            break;
         case 8:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.printInteger(" + s + ").toString()");
            break;
         case 9:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.printLong(" + s + ").toString()");
            break;
         case 10:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.printInt(" + s + ").toString()");
            break;
         case 11:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.printShort(" + s + ").toString()");
            break;
         case 12:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.printByte(" + s + ").toString()");
            break;
         case 13:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.getQNameString(" + s + ".getNamespaceURI(), " + s + ".getLocalPart(), " + s + ".getPrefix())");
            break;
         case 14:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.printFloat(" + s + ".floatValue()).toString()");
            break;
         case 15:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.printDouble(" + s + ".doubleValue()).toString()");
            break;
         case 16:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.printLong(" + s + ".longValue()).toString()");
            break;
         case 17:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.printInt(" + s + ".intValue()).toString()");
            break;
         case 18:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.printShort(" + s + ".shortValue()).toString()");
            break;
         case 19:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.printByte(" + s + ".byteValue()).toString()");
            break;
         case 20:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.printBoolean(" + s + ".booleanValue()).toString()");
            break;
         case 21:
            result = this.mExprFactory.createVerbatim(s + ".toString()");
            break;
         case 22:
            if (this.mSchemaTypeCode == 4) {
               result = this.mExprFactory.createVerbatim("XsTypeConverter.printBase64Binary(" + s + ").toString()");
            } else if (this.mSchemaTypeCode == 5) {
               result = this.mExprFactory.createVerbatim("XsTypeConverter.printHexBinary(" + s + ").toString()");
            }
            break;
         case 23:
            result = this.mExprFactory.createVerbatim("XsTypeConverter.printDateTime(" + s + ", " + this.mSchemaTypeCode + ").toString()");
      }

      if (result == null) {
         throw new IllegalStateException();
      } else {
         return result;
      }
   }

   public String getObjectVersion(String var) {
      String result = null;
      if (this.mPrimitive) {
         switch (this.mTypeCode) {
            case 4:
               result = "new Boolean(" + var + ")";
               break;
            case 5:
               result = "new Float(" + var + ")";
               break;
            case 6:
               result = "new Double(" + var + ")";
            case 7:
            case 8:
            default:
               break;
            case 9:
               result = "new Long(" + var + ")";
               break;
            case 10:
               result = "new Integer(" + var + ")";
               break;
            case 11:
               result = "new Short(" + var + ")";
               break;
            case 12:
               result = "new Byte(" + var + ")";
         }
      } else {
         result = var;
      }

      if (result == null) {
         throw new IllegalStateException();
      } else {
         return result;
      }
   }

   public String getEquals(String var1, String var2) {
      String result = null;
      if (this.mTypeCode == 22) {
         result = "java.util.Arrays.equals(" + var1 + ", " + var2 + ")";
      } else if (this.mPrimitive) {
         result = var1 + " == " + var2;
      } else {
         result = var1 + ".equals(" + var2 + ")";
      }

      return result;
   }

   public Expression getHashCode(String var) {
      Expression result = null;
      if (this.mPrimitive) {
         switch (this.mTypeCode) {
            case 4:
               result = this.mExprFactory.createVerbatim(var + " ? 1 : 0");
               break;
            case 5:
               result = this.mExprFactory.createVerbatim("Float.floatToIntBits(" + var + ")");
               break;
            case 6:
               result = this.mExprFactory.createVerbatim("(int) (Double.doubleToLongBits(" + var + ") ^ (Double.doubleToLongBits(" + var + ") >>> 32))");
            case 7:
            case 8:
            default:
               break;
            case 9:
               result = this.mExprFactory.createVerbatim("(int) (" + var + " ^ (" + var + " >>> 32))");
               break;
            case 10:
               result = this.mExprFactory.createVerbatim(var);
               break;
            case 11:
               result = this.mExprFactory.createVerbatim("(int) " + var);
               break;
            case 12:
               result = this.mExprFactory.createVerbatim("(int) " + var);
         }
      } else if (this.mTypeCode == 22) {
         result = this.mExprFactory.createVerbatim(var + ".length > 0 ? (int)" + var + "[0] : 0");
      } else {
         result = this.mExprFactory.createVerbatim(var + ".hashCode()");
      }

      if (result == null) {
         throw new IllegalStateException();
      } else {
         return result;
      }
   }

   public boolean isQName() {
      return this.mTypeCode == 13;
   }

   public boolean isArray() {
      return this.mArray;
   }

   public boolean isBinary() {
      return this.mSchemaTypeCode == 4 || this.mSchemaTypeCode == 5;
   }

   private int extractTypeCode(SchemaType sType) {
      boolean done = false;

      while(!done) {
         switch (sType.getSimpleVariety()) {
            case 1:
               done = true;
               break;
            case 2:
               throw new IllegalArgumentException("Unions are not currently supported");
            case 3:
               sType = sType.getListItemType();
               break;
            default:
               throw new IllegalStateException();
         }
      }

      return sType.getPrimitiveType().getBuiltinTypeCode();
   }
}
