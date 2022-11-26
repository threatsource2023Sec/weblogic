package weblogic.application.naming;

import java.util.Collection;
import javax.persistence.spi.PersistenceUnitInfo;

public interface PersistenceUnitRegistry {
   Collection getPersistenceUnitNames();

   PersistenceUnitInfo getPersistenceUnit(String var1) throws IllegalArgumentException;

   void close();
}
