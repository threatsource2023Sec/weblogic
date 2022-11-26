package weblogic.j2ee.descriptor;

public interface EjbCallbackBean extends InterceptorMethodsBean {
   AroundInvokeBean[] getAroundInvokes();

   AroundInvokeBean createAroundInvoke();

   void destroyAroundInvoke(AroundInvokeBean var1);

   AroundTimeoutBean[] getAroundTimeouts();

   AroundTimeoutBean createAroundTimeout();

   void destroyAroundTimeout(AroundTimeoutBean var1);

   LifecycleCallbackBean[] getPostActivates();

   LifecycleCallbackBean createPostActivate();

   void destroyPostActivate(LifecycleCallbackBean var1);

   LifecycleCallbackBean[] getPrePassivates();

   LifecycleCallbackBean createPrePassivate();

   void destroyPrePassivate(LifecycleCallbackBean var1);
}
