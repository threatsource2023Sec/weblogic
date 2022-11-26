@start rule: main
/**
 * This code was automatically generated at @time on @date
 * by @generator -- do not edit.
 *
 * @@version @buildString
 * @@author Copyright (c) @year by BEA Systems, Inc. All Rights Reserved.
 */

@packageStatement

import javax.ejb.EJBMetaData;
import weblogic.ejb.container.interfaces.WLEnterpriseBean;

public final class @simple_home_class_name
     extends    weblogic.ejb.container.internal.EntityEJBHome
     implements @home_interface_name, weblogic.utils.PlatformConstants
                @invalidation_interface_name @dynamic_query_interface_name
{

  @declare_create_method_descriptors

  @declare_find_method_descriptors

  @declare_create_methods

  @declare_home_method_descriptors

  public weblogic.ejb.container.internal.MethodDescriptor md_ejbRemove_javax_ejb_Handle;
  public weblogic.ejb.container.internal.MethodDescriptor md_ejbRemove_O;
  public weblogic.ejb.container.internal.MethodDescriptor md_getEJBMetaData;
  public weblogic.ejb.container.internal.MethodDescriptor md_getHomeHandle;

  @perhaps_initialize_create_methods

  public @simple_home_class_name() {
    super(@simple_eoimpl_class_name.class);
  }

  @create_methods

  @find_methods

  @home_methods

  public EJBMetaData getEJBMetaData()
    throws java.rmi.RemoteException
  {
    return super.getEJBMetaData(md_getEJBMetaData);
  }

  public javax.ejb.HomeHandle getHomeHandle()
    throws java.rmi.RemoteException
  {
    return super.getHomeHandle(md_getHomeHandle);
  }

  public void remove(java.lang.Object pk)
    throws java.rmi.RemoteException, javax.ejb.RemoveException
  {
    super.remove(md_ejbRemove_O, pk);
  }

  public void remove(javax.ejb.Handle h)
    throws java.rmi.RemoteException, javax.ejb.RemoveException
  {
    super.remove(md_ejbRemove_javax_ejb_Handle, h);
  }
}

@end rule: main

@start rule: declare_create_method
  private static final java.lang.reflect.Method mth_@method_sig;
@end rule: declare_create_method


@start rule: declare_postCreate_method
  private static final java.lang.reflect.Method mth_post@method_sig;
@end rule: declare_postCreate_method

@start rule: declare_home_method_descriptor
  public weblogic.ejb.container.internal.MethodDescriptor md_@method_sig;
@end rule: declare_home_method_descriptor


@start rule: initialize_create_method
  mth_@method_sig = @simple_beanimpl_interface_name.class.getMethod(
    "@create_method_name", @method_types_as_array);
@end rule: initialize_create_method


@start rule: initialize_postCreate_method
  mth_post@method_sig = @simple_beanimpl_interface_name.class.getMethod(
      "@postCreate_method_name", @method_types_as_array);
@end rule: initialize_postCreate_method

@start rule: static_block_to_init_create_methods
  static {
    try {
      @initialize_create_methods
    } catch (Exception e) {
      throw new AssertionError("Unable to find expected "+
        "methods.  Please check your classpath for stale versions of "+
        "your ejb classes and re-run weblogic.ejbc.\n"+
        "If this is a java.io.FilePermission exception and you are "+
        "running under JACC security, then check your security policy file.\n"+
        "  Exception: '"+
        e.getMessage()+"'");
    }
  }
@end rule: static_block_to_init_create_methods

@start rule: home_method_body
    java.lang.Throwable ee = null;

    weblogic.ejb.container.internal.InvocationWrapper wrap =
      super.preEntityHomeInvoke(md_@method_sig);

    @declare_result

    int nextTxRetryCount = super.getRetryOnRollbackCount(wrap);

    do {

      @simple_beanimpl_interface_name bean = (@simple_beanimpl_interface_name)wrap.getBean();
      int oldState = bean.__WL_getMethodState();

      try {
        bean.__WL_setMethodState(WLEnterpriseBean.STATE_EJBHOME);
        @result bean.ejbHome@capitalized_method_name(@method_parameters_without_types);
      } catch (java.lang.Throwable t) {
        ee = t;
      }
      finally {
        bean.__WL_setMethodState(oldState);
      }

      try {
        // Here is where we try to commit if it's our TX

        nextTxRetryCount = super.postEntityHomeInvokeTxRetry(wrap, ee, nextTxRetryCount);
      } catch (java.lang.Throwable t) {
        ee = t;

        // do not attempt any Tx Retry if we encountered
        // an exception while attempting postInvokeTxRetry
        // instead go directly to postInvokeCleanup

        nextTxRetryCount = -1;
      }
    } while (nextTxRetryCount >= 0);

    try {
      super.postEntityHomeInvokeCleanup(wrap, ee);
    } catch (java.lang.Exception e) {
      if (e instanceof java.rmi.RemoteException) {
        throw (java.rmi.RemoteException)e;
      }
      @enum_exceptions
      else {
        throw new java.rmi.UnexpectedException("Unexpected exception in " +
          "@home_class_name.@method_name():" + EOL +
          weblogic.utils.StackTraceUtils.throwable2StackTrace(e), e);
      }
    }
    @return_result

@end rule: home_method_body

@start rule: create_method_en

public @remote_interface_name @method_name (@method_parameters)
   @method_throws_clause
{
  try {
    return (@remote_interface_name) super.create(md_@method_sig,
      mth_@method_sig, mth_post@method_sig,
      @method_parameters_in_array);

 } catch (java.lang.Exception e) {
    if (e instanceof java.rmi.RemoteException) {
      throw (java.rmi.RemoteException)e;
    }
    @enum_exceptions
       else {
         throw new javax.ejb.CreateException ("Error while creating bean: " +
           e.toString());
       }
  }
}

@end rule: create_method_en

@start rule: finder

public @method_return @method_name(@method_parameters)
   @method_throws_clause
{
  try {
    return (@method_return)
      super.@finder_name(md_@method_sig, @finder_parameters @optional_finder_type);
  } catch (java.lang.Exception e) {
    if (e instanceof java.rmi.RemoteException) {
      throw (java.rmi.RemoteException)e;
    }
    @enum_exceptions
       else {
         throw new javax.ejb.FinderException ("Error while finding bean: " +
           e.toString());
       }
  }
}

@end rule: finder
