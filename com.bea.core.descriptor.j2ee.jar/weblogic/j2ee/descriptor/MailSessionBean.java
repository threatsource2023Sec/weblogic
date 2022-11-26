package weblogic.j2ee.descriptor;

public interface MailSessionBean extends PropertyBean {
   String getDescription();

   void setDescription(String var1);

   String getName();

   void setName(String var1);

   String getStoreProtocol();

   void setStoreProtocol(String var1);

   String getStoreProtocolClass();

   void setStoreProtocolClass(String var1);

   String getTransportProtocol();

   void setTransportProtocol(String var1);

   String getTransportProtocolClass();

   void setTransportProtocolClass(String var1);

   String getHost();

   void setHost(String var1);

   String getUser();

   void setUser(String var1);

   String getPassword();

   void setPassword(String var1);

   String getFrom();

   void setFrom(String var1);

   JavaEEPropertyBean[] getProperties();

   JavaEEPropertyBean lookupProperty(String var1);

   JavaEEPropertyBean createProperty();

   void destroyProperty(JavaEEPropertyBean var1);

   String getId();

   void setId(String var1);
}
