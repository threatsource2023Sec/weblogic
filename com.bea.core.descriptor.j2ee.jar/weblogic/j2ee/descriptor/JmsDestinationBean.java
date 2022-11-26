package weblogic.j2ee.descriptor;

public interface JmsDestinationBean extends PropertyBean {
   String getDescription();

   void setDescription(String var1);

   String getName();

   void setName(String var1);

   String getInterfaceName();

   void setInterfaceName(String var1);

   String getClassName();

   void setClassName(String var1);

   String getResourceAdapter();

   void setResourceAdapter(String var1);

   String getDestinationName();

   void setDestinationName(String var1);

   JavaEEPropertyBean[] getProperties();

   JavaEEPropertyBean lookupProperty(String var1);

   JavaEEPropertyBean createProperty();

   void destroyProperty(JavaEEPropertyBean var1);

   String getId();

   void setId(String var1);
}
