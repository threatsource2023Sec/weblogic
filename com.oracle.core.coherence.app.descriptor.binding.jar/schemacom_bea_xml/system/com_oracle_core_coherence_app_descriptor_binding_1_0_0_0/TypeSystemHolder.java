package schemacom_bea_xml.system.com_oracle_core_coherence_app_descriptor_binding_1_0_0_0;

import com.bea.xml.SchemaTypeSystem;

public class TypeSystemHolder {
   public static final SchemaTypeSystem typeSystem = loadTypeSystem();

   private TypeSystemHolder() {
   }

   private static final SchemaTypeSystem loadTypeSystem() {
      try {
         return (SchemaTypeSystem)Class.forName("com.bea.xbean.schema.SchemaTypeSystemImpl", true, TypeSystemHolder.class.getClassLoader()).getConstructor(Class.class).newInstance(TypeSystemHolder.class);
      } catch (ClassNotFoundException var1) {
         throw new RuntimeException("Cannot load com.bea.xbean.SchemaTypeSystemImpl: make sure xbean.jar is on the classpath.", var1);
      } catch (Exception var2) {
         throw new RuntimeException("Could not instantiate SchemaTypeSystemImpl (" + var2.toString() + "): is the version of xbean.jar correct?", var2);
      }
   }
}
