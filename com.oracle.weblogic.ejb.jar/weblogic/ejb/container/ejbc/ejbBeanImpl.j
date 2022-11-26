@start rule: main
/**
 * This code was automatically generated at @time on @date
 * by @generator -- do not edit.
 *
 * @@version @buildString
 * @@author Copyright (c) @year by BEA Systems, Inc. All Rights Reserved.
 */

@packageStatement

import weblogic.persistence.ExtendedPersistenceContextWrapper;
import java.io.IOException;

public final class @simple_beanimpl_class_name
    extends @simple_bean_class_name
  implements weblogic.ejb.container.interfaces.WLEntityBean,
             @simple_beanimpl_interface_name
{

  @declareBeanStateVar

  private int __WL_method_state;

  private boolean __WL_busy = false;

  private boolean __WL_isLocal = false;

  private boolean __WL_needsRemove;

  private boolean __WL_creatorOfTx;

  private javax.ejb.EJBContext __WL_EJBContext;

  private Object __WL_loadUser;

  public @simple_beanimpl_class_name() @ctor_throws_clause {}

  public boolean __WL_isBusy() { return __WL_busy; }
  public void __WL_setBusy(boolean b) { __WL_busy = b; }

  public boolean __WL_getIsLocal() { return __WL_isLocal; }
  public void __WL_setIsLocal(boolean b) { __WL_isLocal = b; }

  public javax.ejb.EJBContext __WL_getEJBContext() { return __WL_EJBContext; }
  public void __WL_setEJBContext(javax.ejb.EJBContext ctx) {
    __WL_EJBContext = ctx;
  }

  public int __WL_getMethodState() { return __WL_method_state; }
  public void __WL_setMethodState(int state) { __WL_method_state = state; }

  public boolean __WL_needsRemove() { return __WL_needsRemove; }
  public void __WL_setNeedsRemove(boolean b) { __WL_needsRemove = b; }

  public void __WL_setLoadUser(Object o) { __WL_loadUser = o;}
  public Object __WL_getLoadUser() { return __WL_loadUser; }

  public void __WL_setCreatorOfTx (boolean b) { __WL_creatorOfTx = b; }
  public boolean __WL_isCreatorOfTx() { return __WL_creatorOfTx; }

  @perhapsDeclareBeanStateValidAccessors

  @wl_entitybean_fields

  @ejb_callbacks

  @bean_postcreate_methods

  // create, find, remove, EJB 2.0 home methods
  @bean_home_methods

  @wl_entitybean_methods

}

@end rule: main


@start rule: common_ejb_callbacks
  public void ejbActivate()
    @activate_throws_clause
  {
    int oldState = __WL_method_state;
    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_EJB_ACTIVATE;
      super.ejbActivate();
    } finally {
      __WL_method_state = oldState;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }

  public void ejbPassivate()
    @passivate_throws_clause
  {
    int oldState = __WL_method_state;
    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_EJB_PASSIVATE;
      super.ejbPassivate();
    } finally {
      __WL_method_state = oldState;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }

  public void ejbRemove()
    @remove_throws_clause
  {
    int oldState = __WL_method_state;
    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_EJB_REMOVE;
      super.ejbRemove();
    } finally {
      __WL_method_state = oldState;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }
@end rule: common_ejb_callbacks

@start rule: entity_callbacks

  public void setEntityContext(javax.ejb.EntityContext ctx)
    @setentitycontext_throws_clause
  {
    int oldState = __WL_method_state;
    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_SET_CONTEXT;
      super.setEntityContext(ctx);
    } finally {
      __WL_method_state = oldState;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }

  public void unsetEntityContext()
    @unsetentitycontext_throws_clause
  {
    int oldState = __WL_method_state;
    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_UNSET_CONTEXT;
      super.unsetEntityContext();
    } finally {
      __WL_method_state = oldState;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }

  public void ejbLoad()
    @ejbload_throws_clause
  {
    int oldState = __WL_method_state;
    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_EJBLOAD;
      super.ejbLoad();
      @perhapsSetLastLoadTime
    } finally {
      __WL_method_state = oldState;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }

  public void ejbStore()
    @ejbstore_throws_clause
  {
    int oldState = __WL_method_state;
    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = STATE_EJBSTORE;
      super.ejbStore();
    } finally {
      __WL_method_state = oldState;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }

@end rule: entity_callbacks


@start rule: home_method

  @method_signature_no_throws
    @beanmethod_throws_clause
  {
    int oldState = __WL_method_state;
    try {
      weblogic.ejb.container.internal.AllowedMethodsHelper.pushBean(this);
      __WL_method_state = @method_state;
      @perhaps_return super.@method_name(@method_parameters_without_types);
    } finally {
      __WL_method_state = oldState;
      weblogic.ejb.container.internal.AllowedMethodsHelper.popBean();
    }
  }

@end rule: home_method


@start rule: wl_entitybean_fields_code

private boolean __WL_operationsComplete = false;

@end rule: wl_entitybean_fields_code


@start rule: wl_entitybean_methods_code

  public void __WL_setOperationsComplete(boolean b) {
    __WL_operationsComplete = b;
  }

  public boolean __WL_getOperationsComplete() {
    return __WL_operationsComplete;
  }

@end rule: wl_entitybean_methods_code

@start rule: bean_state_valid_accessors

 public void __WL_setBeanStateValid(boolean valid) {
   @setBeanStateValidMethodBody
 }

 public boolean __WL_isBeanStateValid() {
   @isBeanStateValidMethodBody
 }

 public void __WL_setLastLoadTime(long time) {
   @perhapsSetLastLoadTimeMethodBody
 }

 public long __WL_getLastLoadTime() {
   @perhapsGetLastLoadTimeMethodBody
 }

@end rule: bean_state_valid_accessors

@start rule: bean_state_timeout_check
   long lastLoadTime = __WL_lastLoadTime.get();
   if(lastLoadTime != 0) {
     if(__WL_readTimeoutMS == 0 ||
        System.currentTimeMillis() - lastLoadTime < __WL_readTimeoutMS)
      return true;
   }
   return false;
@end rule: bean_state_timeout_check

