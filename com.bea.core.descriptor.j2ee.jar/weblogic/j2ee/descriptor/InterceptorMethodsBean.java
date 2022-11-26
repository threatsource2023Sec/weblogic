package weblogic.j2ee.descriptor;

public interface InterceptorMethodsBean {
   AroundInvokeBean[] getAroundInvokes();

   AroundInvokeBean createAroundInvoke();

   void destroyAroundInvoke(AroundInvokeBean var1);

   AroundTimeoutBean[] getAroundTimeouts();

   AroundTimeoutBean createAroundTimeout();

   void destroyAroundTimeout(AroundTimeoutBean var1);
}
