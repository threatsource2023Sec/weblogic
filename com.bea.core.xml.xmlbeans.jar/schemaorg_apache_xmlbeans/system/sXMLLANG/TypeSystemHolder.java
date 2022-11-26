package schemaorg_apache_xmlbeans.system.sXMLLANG;

import org.apache.xmlbeans.SchemaTypeSystem;

public class TypeSystemHolder {
   public static final SchemaTypeSystem typeSystem = loadTypeSystem();

   private TypeSystemHolder() {
   }

   private static final SchemaTypeSystem loadTypeSystem() {
      try {
         return (SchemaTypeSystem)Class.forName("org.apache.xmlbeans.impl.schema.SchemaTypeSystemImpl", true, TypeSystemHolder.class.getClassLoader()).getConstructor(Class.class).newInstance(TypeSystemHolder.class);
      } catch (ClassNotFoundException var1) {
         throw new RuntimeException("Cannot load org.apache.xmlbeans.impl.SchemaTypeSystemImpl: make sure xbean.jar is on the classpath.", var1);
      } catch (Exception var2) {
         throw new RuntimeException("Could not instantiate SchemaTypeSystemImpl (" + var2.toString() + "): is the version of xbean.jar correct?", var2);
      }
   }
}
