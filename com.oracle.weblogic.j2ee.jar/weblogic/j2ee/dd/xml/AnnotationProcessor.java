package weblogic.j2ee.dd.xml;

import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.J2eeClientEnvironmentBean;
import weblogic.utils.ErrorCollectionException;

public interface AnnotationProcessor {
   void processJ2eeAnnotations(Class var1, J2eeClientEnvironmentBean var2, boolean var3) throws ErrorCollectionException;

   void validate(ClassLoader var1, DescriptorBean var2, boolean var3) throws ErrorCollectionException;
}
