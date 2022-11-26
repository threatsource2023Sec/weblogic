package weblogic.jndi;

import java.io.IOException;

public interface ObjectCopier {
   int PROXY_COPIER_PRIORITY = 100;
   int IIOP_COPIER_PRIORITY = 80;
   int T3_COPIER_PRIORITY = 60;
   int SERIALIZABLE_COPIER_PRIORITY = 40;
   int DEFAULT_COPIER_PRIORITY = 0;

   boolean mayCopy(Object var1);

   Object copyObject(Object var1) throws IOException, ClassNotFoundException;

   int getPriority();
}
