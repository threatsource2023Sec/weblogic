package weblogic.application.utils.annotation;

import java.util.List;

public interface Info {
   void addAnnotation(String var1, String var2);

   List getAnnotations();

   int getAccess();

   String getDesc();

   String getName();
}
