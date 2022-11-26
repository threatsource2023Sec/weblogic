package weblogic.management.jmx;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.management.ObjectName;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;

public class PrimitiveMapper {
   private static final Map primitiveTypes = new HashMap(9);
   private static final Map wrapperTypes;
   private static final Map openTypes;

   public static Class lookupClass(String primitiveName) {
      return (Class)primitiveTypes.get(primitiveName);
   }

   public static String lookupClassName(String primitiveName) {
      Class primitiveClass = (Class)primitiveTypes.get(primitiveName);
      return primitiveClass != null ? primitiveClass.getName() : null;
   }

   public static Class lookupWrapperClass(String primitiveName) {
      return (Class)wrapperTypes.get(primitiveName);
   }

   public static String lookupWrapperClassName(Class primitiveClass) {
      Class wrapperClass = (Class)wrapperTypes.get(primitiveClass.getName());
      return wrapperClass != null ? wrapperClass.getName() : null;
   }

   public static OpenType lookupOpenType(String className) {
      return (OpenType)openTypes.get(className);
   }

   static {
      primitiveTypes.put(Boolean.TYPE.getName(), Boolean.TYPE);
      primitiveTypes.put(Byte.TYPE.getName(), Byte.TYPE);
      primitiveTypes.put(Character.TYPE.getName(), Character.TYPE);
      primitiveTypes.put(Short.TYPE.getName(), Short.TYPE);
      primitiveTypes.put(Integer.TYPE.getName(), Integer.TYPE);
      primitiveTypes.put(Long.TYPE.getName(), Long.TYPE);
      primitiveTypes.put(Float.TYPE.getName(), Float.TYPE);
      primitiveTypes.put(Double.TYPE.getName(), Double.TYPE);
      primitiveTypes.put(Void.TYPE.getName(), Void.TYPE);
      wrapperTypes = new HashMap(9);
      wrapperTypes.put(Boolean.TYPE.getName(), Boolean.class);
      wrapperTypes.put(Byte.TYPE.getName(), Byte.class);
      wrapperTypes.put(Character.TYPE.getName(), Character.class);
      wrapperTypes.put(Short.TYPE.getName(), Short.class);
      wrapperTypes.put(Integer.TYPE.getName(), Integer.class);
      wrapperTypes.put(Long.TYPE.getName(), Long.class);
      wrapperTypes.put(Float.TYPE.getName(), Float.class);
      wrapperTypes.put(Double.TYPE.getName(), Double.class);
      wrapperTypes.put(Void.TYPE.getName(), Void.class);
      openTypes = new HashMap(9);
      openTypes.put(Boolean.TYPE.getName(), SimpleType.BOOLEAN);
      openTypes.put(Byte.TYPE.getName(), SimpleType.BYTE);
      openTypes.put(Character.TYPE.getName(), SimpleType.CHARACTER);
      openTypes.put(Short.TYPE.getName(), SimpleType.SHORT);
      openTypes.put(Integer.TYPE.getName(), SimpleType.INTEGER);
      openTypes.put(Long.TYPE.getName(), SimpleType.LONG);
      openTypes.put(Float.TYPE.getName(), SimpleType.FLOAT);
      openTypes.put(Double.TYPE.getName(), SimpleType.DOUBLE);
      openTypes.put(Void.TYPE.getName(), SimpleType.VOID);
      openTypes.put(BigDecimal.class.getName(), SimpleType.BIGDECIMAL);
      openTypes.put(BigInteger.class.getName(), SimpleType.BIGINTEGER);
      openTypes.put(Date.class.getName(), SimpleType.DATE);
      openTypes.put(ObjectName.class.getName(), SimpleType.OBJECTNAME);
      openTypes.put(Boolean.class.getName(), SimpleType.BOOLEAN);
      openTypes.put(Byte.class.getName(), SimpleType.BYTE);
      openTypes.put(Character.class.getName(), SimpleType.CHARACTER);
      openTypes.put(Short.class.getName(), SimpleType.SHORT);
      openTypes.put(Integer.class.getName(), SimpleType.INTEGER);
      openTypes.put(Long.class.getName(), SimpleType.LONG);
      openTypes.put(Float.class.getName(), SimpleType.FLOAT);
      openTypes.put(Double.class.getName(), SimpleType.DOUBLE);
      openTypes.put(Void.class.getName(), SimpleType.VOID);
      openTypes.put(ObjectName.class.getName(), SimpleType.OBJECTNAME);
      openTypes.put(String.class.getName(), SimpleType.STRING);
      openTypes.put(Object.class.getName(), CompositeTypeAny.ANY);
      openTypes.put(Throwable.class.getName(), CompositeTypeThrowable.THROWABLE);
      openTypes.put(Properties.class.getName(), CompositeTypeProperties.PROPERTIES);
   }
}
