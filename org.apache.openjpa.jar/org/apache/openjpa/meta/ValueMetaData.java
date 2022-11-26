package org.apache.openjpa.meta;

import java.io.Serializable;

public interface ValueMetaData extends MetaDataContext, MetaDataModes, Serializable {
   int CASCADE_NONE = 0;
   int CASCADE_IMMEDIATE = 1;
   int CASCADE_AUTO = 2;
   String MAPPED_BY_PK = "`pk`";

   FieldMetaData getFieldMetaData();

   Class getType();

   void setType(Class var1);

   int getTypeCode();

   void setTypeCode(int var1);

   boolean isTypePC();

   ClassMetaData getTypeMetaData();

   Class getDeclaredType();

   void setDeclaredType(Class var1);

   int getDeclaredTypeCode();

   void setDeclaredTypeCode(int var1);

   boolean isDeclaredTypePC();

   ClassMetaData getDeclaredTypeMetaData();

   boolean isEmbedded();

   void setEmbedded(boolean var1);

   boolean isEmbeddedPC();

   ClassMetaData getEmbeddedMetaData();

   ClassMetaData addEmbeddedMetaData();

   int getCascadeDelete();

   void setCascadeDelete(int var1);

   int getCascadePersist();

   void setCascadePersist(int var1);

   int getCascadeAttach();

   void setCascadeAttach(int var1);

   int getCascadeRefresh();

   void setCascadeRefresh(int var1);

   boolean isSerialized();

   void setSerialized(boolean var1);

   String getValueMappedBy();

   void setValueMappedBy(String var1);

   FieldMetaData getValueMappedByMetaData();

   Class getTypeOverride();

   void setTypeOverride(Class var1);

   int getResolve();

   void setResolve(int var1);

   void setResolve(int var1, boolean var2);

   boolean resolve(int var1);

   void copy(ValueMetaData var1);
}
