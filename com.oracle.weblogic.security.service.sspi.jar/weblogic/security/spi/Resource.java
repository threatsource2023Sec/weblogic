package weblogic.security.spi;

import java.io.Serializable;

public interface Resource extends Serializable {
   boolean equals(Object var1);

   String toString();

   int hashCode();

   String getType();

   Resource getParentResource();

   long getID();

   String[] getKeys();

   String[] getValues();
}
