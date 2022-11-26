package org.glassfish.admin.rest.composite;

import javax.validation.constraints.NotNull;

public interface Property extends RestModel {
   @NotNull String getName();

   void setName(String var1);

   String getValue();

   void setValue(String var1);
}
