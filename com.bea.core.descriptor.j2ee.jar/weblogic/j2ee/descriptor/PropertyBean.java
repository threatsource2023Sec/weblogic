package weblogic.j2ee.descriptor;

public interface PropertyBean {
   JavaEEPropertyBean[] getProperties();

   JavaEEPropertyBean lookupProperty(String var1);

   JavaEEPropertyBean createProperty();

   void destroyProperty(JavaEEPropertyBean var1);
}
