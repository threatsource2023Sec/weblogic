package weblogic.j2ee.descriptor;

public interface LifecycleCallbackBean extends SourceTrackerBean {
   String getLifecycleCallbackClass();

   void setLifecycleCallbackClass(String var1);

   String getLifecycleCallbackMethod();

   void setLifecycleCallbackMethod(String var1);
}
