package weblogic.j2ee.descriptor.wl;

public interface DestinationKeyBean extends NamedEntityBean {
   String getProperty();

   void setProperty(String var1) throws IllegalArgumentException;

   String getKeyType();

   void setKeyType(String var1) throws IllegalArgumentException;

   String getSortOrder();

   void setSortOrder(String var1) throws IllegalArgumentException;
}
