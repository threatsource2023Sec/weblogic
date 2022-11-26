package weblogic.j2ee.descriptor;

public interface CmpFieldBean {
   String[] getDescriptions();

   void addDescription(String var1);

   void removeDescription(String var1);

   void setDescriptions(String[] var1);

   String getFieldName();

   void setFieldName(String var1);

   String getId();

   void setId(String var1);
}
