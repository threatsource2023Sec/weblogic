package weblogic.management.security.profiles;

import javax.management.MBeanException;
import javax.management.modelmbean.ModelMBean;
import javax.management.modelmbean.RequiredModelMBean;
import weblogic.management.security.ProviderImpl;

public class ProfilerImpl extends ProviderImpl {
   protected static final int PROPERTY_FIRST = 0;
   public static final int PROPERTY_BOOLEAN = 0;
   public static final int PROPERTY_DATE = 1;
   public static final int PROPERTY_FLOAT = 2;
   public static final int PROPERTY_LONG = 3;
   public static final int PROPERTY_TEXT = 4;
   public static final int PROPERTY_SUBPROFILE = 5;
   protected static final int PROPERTY_LAST = 5;

   public ProfilerImpl(ModelMBean base) throws MBeanException {
      super(base);
   }

   protected ProfilerImpl(RequiredModelMBean base) throws MBeanException {
      super(base);
   }
}
