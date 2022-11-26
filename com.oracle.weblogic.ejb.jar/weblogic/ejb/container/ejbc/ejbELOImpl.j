@start rule: main
/**
 * This code was automatically generated at @time on @date
 * by @generator -- do not edit.
 *
 * @@version @buildString
 * @@author Copyright (c) @year by BEA Systems, Inc. All Rights Reserved.
 */

@packageStatement

import javax.ejb.EJBException;
import weblogic.ejb20.interfaces.LocalHandle;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;


public final class @simple_eloimpl_class_name
    extends weblogic.ejb.container.internal.EntityEJBLocalObject
  implements @local_interface_name, weblogic.utils.PlatformConstants, java.io.Serializable,
    weblogic.ejb.EJBLocalObject
{

  @declare_static_elo_method_descriptors

  public static weblogic.ejb.container.internal.MethodDescriptor md_eo_remove;
  public static weblogic.ejb.container.internal.MethodDescriptor md_eo_getEJBLocalHome;
  public static weblogic.ejb.container.internal.MethodDescriptor md_eo_getLocalHandle;
  public static weblogic.ejb.container.internal.MethodDescriptor md_eo_getPrimaryKey;
  public static weblogic.ejb.container.internal.MethodDescriptor md_eo_isIdentical_javax_ejb_EJBLocalObject;

  public @simple_eloimpl_class_name() {}

  @local_interface_methods

  public void remove() throws javax.ejb.RemoveException {
    super.remove(md_eo_remove);
  }

  public javax.ejb.EJBLocalHome getEJBLocalHome() throws EJBException {
    return super.getEJBLocalHome(md_eo_getEJBLocalHome);
  }

  public LocalHandle getLocalHandle() throws EJBException {
    return super.getLocalHandle(md_eo_getLocalHandle);
  }

  public Object getPrimaryKey() throws EJBException {
    return super.getPrimaryKey(md_eo_getPrimaryKey);
  }

  public boolean isIdentical(javax.ejb.EJBLocalObject o) throws EJBException {
    return super.isIdentical(md_eo_isIdentical_javax_ejb_EJBLocalObject, o);
  }

  private void writeObject(java.io.ObjectOutputStream out)
    throws java.io.IOException
  {
    throw new javax.ejb.EJBException(
      "Attempt to pass a reference to an EJBLocalObject to a remote " +
      "client.  A local EJB component may only be accessed by clients " +
      "co-located in the same ear or standalone jar file.");
  }

  private void readObject(java.io.ObjectInputStream in)
    throws java.io.IOException, java.lang.ClassNotFoundException
  {
    // this method is never called

    throw new javax.ejb.EJBException(
      "Attempt to pass a reference to an EJBLocalObject to a remote " +
      "client.  A local EJB component may only be accessed by clients " +
      "co-located in the same ear or standalone jar file.");
  }

  public void operationsComplete() {
    super.operationsComplete();
  }
}

@end rule: main

@start rule: local_interface_method
  @method_signature
  {
    java.lang.Throwable __ee = null;
    weblogic.ejb.container.internal.MethodDescriptor __md = md_eo_@method_sig;
    weblogic.ejb.container.internal.InvocationWrapper __wrap = 
      weblogic.ejb.container.internal.InvocationWrapper.newInstance(__md);

    super.__WL_preInvoke@perhapsLite(
        __wrap, new weblogic.ejb.container.internal.EJBContextHandler(
          __md, @method_parameters_in_array));

    @declare_result

    boolean doTxRetry = false;
    do {
      @simple_beanimpl_interface_name __bean = (@simple_beanimpl_interface_name) __wrap.getBean();

      int __oldState = __bean.__WL_getMethodState();

      try {
        weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(__bean);
        __bean.__WL_setMethodState(WLEnterpriseBean.STATE_BUSINESS_METHOD);

        @result __bean.@method_name(@method_parameters_without_types);

        @checkExistsOnMethod
      } catch (java.lang.Throwable t) {
        __ee = t;
      } finally {
        __bean.__WL_setMethodState(__oldState);
        weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
      }

      try {
        // Here is where we try to commit if it's our TX
        doTxRetry = super.__WL_postInvokeTxRetry(__wrap, __ee);
      } catch (java.lang.Throwable t) {
        __ee = t;
        doTxRetry = false;    // do not attempt any Tx Retry if we encountered
                              // an exception while attempting __WL_postInvokeTxRetry
                              // instead go directly to __WL_postInvokeCleanup
      }
    } while (doTxRetry);

    // __WL_postInvokeCleanup must *always* be done if __WL_preInvoke succeeded
    try {
      super.__WL_postInvokeCleanup(__wrap, __ee);
    } catch (java.lang.Exception e) {
      if (e instanceof javax.ejb.EJBException) {
        throw (javax.ejb.EJBException) e;
      }
      @enum_exceptions
      else {
        throw new javax.ejb.EJBException("Unexpected exception in " +
          "@ejb_class_name.@method_name():" + EOL +
          weblogic.utils.StackTraceUtils.throwable2StackTrace(e), e);
      }
    }
    @return_result
  }

@end rule: local_interface_method
