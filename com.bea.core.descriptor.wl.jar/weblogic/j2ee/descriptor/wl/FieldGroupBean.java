package weblogic.j2ee.descriptor.wl;

public interface FieldGroupBean {
   String getGroupName();

   void setGroupName(String var1);

   String[] getCmpFields();

   void addCmpField(String var1);

   void removeCmpField(String var1);

   void setCmpFields(String[] var1);

   String[] getCmrFields();

   void addCmrField(String var1);

   void removeCmrField(String var1);

   void setCmrFields(String[] var1);

   String getId();

   void setId(String var1);
}
