package weblogic.application;

import org.jvnet.hk2.annotations.Contract;
import weblogic.j2ee.descriptor.J2eeEnvironmentBean;
import weblogic.utils.ErrorCollectionException;

@Contract
public interface PojoAnnotationProcessor {
   String[] getSupportedAnnotationNames();

   void processJ2eeAnnotations(Class var1, J2eeEnvironmentBean var2, boolean var3) throws ErrorCollectionException;
}
