package weblogic.descriptor.beangen;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JamClassLoader;
import com.bea.util.jam.JamServiceFactory;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;

public class JClasses {
   public static final JClass BYTE_ARRAY = load(byte[].class);
   public static final JClass BOOLEAN;
   public static final JClass CLASS;
   public static final JClass COLLECTION;
   public static final JClass DESCRIPTOR;
   public static final JClass DESCRIPTOR_BEAN;
   public static final JClass ILLEGAL_ARGUMENT_EXCEPTION;
   public static final JClass INVALID_ATTRIBUTE_VALUE_EXCEPTION;
   public static final JClass INT;
   public static final JClass LIST;
   public static final JClass MAP;
   public static final JClass OBJECT;
   public static final JClass STRING;
   public static final JClass STRING_ARRAY;
   public static final JClass VOID;
   private static final JamClassLoader JAM_CL = JamServiceFactory.getInstance().createJamClassLoader(JClasses.class.getClassLoader());

   static JClass load(Class cls) {
      return load(cls.getName());
   }

   static JClass load(String clsName) {
      return JAM_CL.loadClass(clsName);
   }

   static {
      BOOLEAN = load(Boolean.TYPE);
      CLASS = load(Class.class);
      COLLECTION = load(Collection.class);
      DESCRIPTOR = load(Descriptor.class);
      DESCRIPTOR_BEAN = load(DescriptorBean.class);
      ILLEGAL_ARGUMENT_EXCEPTION = load(IllegalArgumentException.class);
      INVALID_ATTRIBUTE_VALUE_EXCEPTION = load(InvalidAttributeValueException.class);
      INT = load(Integer.TYPE);
      LIST = load(List.class);
      MAP = load(Map.class);
      OBJECT = load(Object.class);
      STRING = load(String.class);
      STRING_ARRAY = load(String[].class);
      VOID = load(Void.TYPE);
   }
}
