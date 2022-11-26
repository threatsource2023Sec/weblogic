package weblogic.ejb.container.interfaces;

import java.util.List;

public interface CMPCompiler {
   List generatePersistenceSources(EntityBeanInfo var1) throws Exception;

   void postCompilation() throws Exception;
}
