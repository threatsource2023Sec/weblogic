package weblogic.ejb.container.persistence.spi;

import java.util.Set;

public interface Dependent {
   String getDescription();

   String getDependentClassName();

   String getName();

   Set getCMFieldNames();

   Set getPrimaryKeyFieldNames();
}
