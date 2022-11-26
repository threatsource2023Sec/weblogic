package kodo.jdbc.conf.descriptor;

import kodo.conf.descriptor.MetaDataRepositoryBean;

public interface KodoMappingRepositoryBean extends MetaDataRepositoryBean {
   int getResolve();

   void setResolve(int var1);

   int getValidate();

   void setValidate(int var1);

   int getSourceMode();

   void setSourceMode(int var1);
}
