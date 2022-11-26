package weblogic.utils.classes;

public class TypeUtils {
   public static String classToJavaSourceType(Class type) {
      int arrayDim;
      for(arrayDim = 0; type.isArray(); type = type.getComponentType()) {
         ++arrayDim;
      }

      StringBuffer sb = new StringBuffer(type.getName());

      for(int i = 0; i < arrayDim; ++i) {
         sb.append("[]");
      }

      return sb.toString();
   }

   public static String[] classesToJavaSourceTypes(Class[] types) {
      String[] typeNames = new String[types.length];

      for(int iType = 0; iType < types.length; ++iType) {
         typeNames[iType] = classToJavaSourceType(types[iType]);
      }

      return typeNames;
   }
}
