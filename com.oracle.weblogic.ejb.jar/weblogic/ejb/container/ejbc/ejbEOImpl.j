@start rule: main
/**
 * This code was automatically generated at @time on @date
 * by @generator -- do not edit.
 *
 * @@version @buildString
 * @@author Copyright (c) @year by BEA Systems, Inc. All Rights Reserved.
 */

@packageStatement

import weblogic.ejb.container.interfaces.WLEnterpriseBean;

public final class @simple_eoimpl_class_name
    extends weblogic.ejb.container.internal.EntityEJBObject_Activatable
  implements @remote_interface_name,
             weblogic.utils.PlatformConstants, weblogic.ejb.EJBObject
{

  @declare_static_eo_method_descriptors

  public static weblogic.ejb.container.internal.MethodDescriptor md_eo_remove;
  public static weblogic.ejb.container.internal.MethodDescriptor md_eo_getEJBHome;
  public static weblogic.ejb.container.internal.MethodDescriptor md_eo_getHandle;
  public static weblogic.ejb.container.internal.MethodDescriptor md_eo_getPrimaryKey;
  public static weblogic.ejb.container.internal.MethodDescriptor md_eo_isIdentical_javax_ejb_EJBObject;

  public @simple_eoimpl_class_name() {}

  @remote_interface_methods

  public void remove()
     throws javax.ejb.RemoveException, java.rmi.RemoteException
  {
    super.remove(md_eo_remove);
  }

  public javax.ejb.EJBHome getEJBHome()
    throws java.rmi.RemoteException
  {
    return super.getEJBHome(md_eo_getEJBHome);
  }

  public javax.ejb.Handle getHandle()
    throws java.rmi.RemoteException
  {
    return super.getHandle(md_eo_getHandle);
  }

  public Object getPrimaryKey()
    throws java.rmi.RemoteException
  {
    return super.getPrimaryKey(md_eo_getPrimaryKey);
  }

  public boolean isIdentical(javax.ejb.EJBObject o)
    throws java.rmi.RemoteException
  {
    return super.isIdentical(md_eo_isIdentical_javax_ejb_EJBObject, o);
  }

  public void operationsComplete() {
    super.operationsComplete();
  }
}

@end rule: main
 
@start rule: remote_interface_method
  @method_signature
  {
    java.lang.Throwable __ee = null;

    weblogic.ejb.container.internal.MethodDescriptor __md = 
      md_eo_@method_sig;
    weblogic.ejb.container.internal.InvocationWrapper __wrap =
      weblogic.ejb.container.internal.InvocationWrapper.newInstance(__md);

    super.__WL_preInvoke(
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
      }
      finally {
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


    // __WL_postInvokeCleanup must *always* be done
    // if __WL_preInvoke succeeded

    try {
      super.__WL_postInvokeCleanup(__wrap, __ee);
    } catch (java.lang.Exception e) {
      if (e instanceof java.rmi.RemoteException) {
        throw (java.rmi.RemoteException)e;
      } 
      @enum_exceptions
      else {
        throw new java.rmi.UnexpectedException("Unexpected exception in " +
          "@ejb_class_name.@method_name():" + EOL +	
          weblogic.utils.StackTraceUtils.throwable2StackTrace(e), e);
      } 
    }
    @return_result
  }

@end rule: remote_interface_method
