@start rule: main
@genPackageDeclaration;

import java.lang.reflect.Constructor;
import java.util.*;

import javax.management.MBeanServer;

import weblogic.management.ManagementError;
import weblogic.management.WebLogicMBean;
import weblogic.management.configuration.ConfigurationError;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.internal.Helper;
import weblogic.management.configuration.*;

/** 
 * @genAuthor Copyright (c) @year by BEA. All Rights Reserved.
 */
public class @genClassName
  extends @genSuperClassName 
  implements @genInterfaceName @genTargetInterface
// we should also implement @genConfigInterfaceName, 
// but comment it out for now
{
@constructor
@genTemplateDeclaration
@genAttributes
@genOperations
}
 
@end rule: main

@start rule: constructor
  public @genClassName() {
    super();
    try {
      // FIXME br-at-weblogic.com 21-Jun-00
      boolean isServer = true;
      // cover for the case where is not T3Srvr for testing.
      @genAttributeDefaults;
    } catch (Throwable t) {
      throw new ConfigurationError(t);
    }
  }
@end rule: constructor

@start rule: attributeField
  /*
   * Declaration of @genAttributeName
   */
  private @genAttributeType @genAttributeFieldName;
@end rule: attributeField

@start rule: attributeDefault
    if (isServer) {
      @genAttributeDefault;
    } else {
      @genAttributeClientDefault;
    }
@end rule: attributeDefault

@start rule: arrayFields
  /*
   * @genAttributeName
   */
  private Set @genAttributeFieldName;
@end rule: arrayField

@start rule: getter
  /**
   * Getter for @genAttributeName
   */
  public @genAttributeType get@genAttributeName() {
    return @genAttributeFieldName;
  }
@end rule: getter

@start rule: mbeanGetter
  /**
   * Getter for @genAttributeName
   */
  public @genAttributeType get@genAttributeName() {
    if (@genAttributeFieldName == null) {
      try {
        String attrType = Helper.mbeanType("@genAttributeType");
        @genAttributeType val = 
          (@genAttributeType)Helper.createMBean(
             "Default " + attrType + " for " + getName(),
             attrType, 
             getDomain().getName()
          );
	this.@genAttributeFieldName = val;
      } catch (Throwable t) { 
        throw new ConfigurationError(t);
      }
    }
    return @genAttributeFieldName;
  }
@end rule: mbeanGetter

@start rule: isgetter
  /**
   * Boolean getter for @genAttributeName
   */
  public boolean is@genAttributeName() {
    return @genAttributeFieldName;
  }
@end rule: isgetter

@start rule: arrayGetter
  /**
   * Getter for @genAttributeName
   */
  public @genAttributeType get@genAttributeName() {
    @genAttributeType result = new @genComponentType[@genAttributeFieldName.size()];
    result = (@genAttributeType)@genAttributeFieldName.toArray(result);
    return result;
  }
@end rule: arrayGetter

@start rule: setter
  /**
   * Setter for @genAttributeName
   */
  public void set@genAttributeName(@genAttributeType value)
  { 
    @genDynamicCheck
    if (!(@genLegalCheck)) {
      throw new ConfigurationError("Illegal value for @genAttributeName ," +
	" valid values are: @genLegalCheck");
    }
    set("@genAttributeName", @genGetOrIsAttribute, value);
    @genAttributeFieldName = value; 
  }
@end rule: setter

@start rule: arraySetter
  /**
   * Setter for @genAttributeName
   */
  public void set@genAttributeName(@genAttributeType value) {
    @genDynamicCheck
    if (!(@genLegalCheck)) {
      throw new ConfigurationError("Illegal value for @genAttributeName ," +
	" valid values are: @genLegalCheck");
    }
    set("@genAttributeName", @genGetOrIsAttribute, value);
    @genAttributeFieldName = new HashSet();
    for (int i = 0; i < value.length; i++) @genAttributeFieldName.add(value[i]);
  }
@end rule: arraySetter


@start rule: adder
  /**
   * Add method setter for @genAttributeName
   */
  public boolean @genOperationName(@genOperationParameters) 
    @genOperationExceptions
  {
    if (value == null) throw new NullPointerException("value is null");
    add("@genAttributeName", value);
    return @genAttributeFieldName.add(value);
  }
@end rule: adder

@start rule: remover
  /**
   * Remove method setter for @genAttributeName 
   */
  public boolean @genOperationName(@genOperationParameters) 
    @genOperationExceptions
  {
    if (value == null) throw new NullPointerException("value is null");
    remove("@genAttributeName", value);
    return @genAttributeFieldName.remove(value);
  }
@end rule: remover

@start rule: allremover 
  /**
   * All remove method for @genAttributeName
   */
  public boolean @genOperationName() @genOperationExceptions {
    removeAll("@genAttributeName");
    return @genAttributeFieldName.clear();
  }
@end rule: allremover

@start rule: operation
  public @genOperationReturnType @genOperationName(@genOperationParameters) 
    @genOperationExceptions
  {
    throw new InternalError("NYI");
  }
@end rule: operation

