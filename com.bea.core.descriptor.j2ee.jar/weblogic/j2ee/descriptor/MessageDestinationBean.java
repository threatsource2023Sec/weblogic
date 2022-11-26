package weblogic.j2ee.descriptor;

public interface MessageDestinationBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String[] getDisplayNames();

   void addDisplayName(String var1);

   void removeDisplayName(String var1);

   void setDisplayNames(String[] var1);

   IconBean[] getIcons();

   IconBean createIcon();

   void destroyIcon(IconBean var1);

   String getMessageDestinationName();

   void setMessageDestinationName(String var1);

   String getMappedName();

   void setMappedName(String var1);

   String getLookupName();

   void setLookupName(String var1);

   String getId();

   void setId(String var1);
}
