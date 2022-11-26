package com.bea.staxb.buildtime.internal.facade;

import com.bea.staxb.buildtime.internal.bts.QNameProperty;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JField;
import com.bea.util.jam.JMethod;

public interface PropgenFacade {
   QNameProperty getBtsProperty();

   JClass getType();

   void finish();

   void setSchemaName(String var1);

   void setType(JClass var1);

   void setGetter(JMethod var1);

   void setSetter(JMethod var1);

   void setField(JField var1);

   void setIssetter(JMethod var1);

   void setFactory(JMethod var1);

   void setNillable(boolean var1);

   void setRequired(boolean var1);

   void setDefault(String var1);

   void addFacet(int var1, String var2);

   void setCtorParamIndex(int var1);

   void setForm(String var1);

   void setDocumentation(String var1);
}
