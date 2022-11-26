package weblogic.j2ee.descriptor;

public interface CmrFieldBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String getCmrFieldName();

   void setCmrFieldName(String var1);

   String getCmrFieldType();

   void setCmrFieldType(String var1);

   String getId();

   void setId(String var1);
}
