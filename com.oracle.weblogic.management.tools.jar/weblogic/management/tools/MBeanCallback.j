@start rule: main
package weblogic.management.configuration.callback;

import java.lang.reflect.Constructor;

import java.util.Hashtable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.management.MBeanServer;

import weblogic.rjvm.JVMID;
import weblogic.t3.services.Config;
import weblogic.t3.services.PropertyDef;
import weblogic.t3.srvr.T3Srvr;

import weblogic.management.WebLogicMBean;
import weblogic.management.WebLogicMBeanServer;
import weblogic.management.configuration.ConfigurationError;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.internal.Helper;

/** 
 * @author Copyright (c) @year by BEA, Inc. All Rights Reserved.
 */
public interface @callbackInterfaceName @callbackSuperIntefaceName
{
@callbackOperations
}
@end rule: main

@start rule: interfaceOperation
  @operationReturnType @operationName(@operationParameters) @operationExceptions;
@end rule: interfaceOperation
