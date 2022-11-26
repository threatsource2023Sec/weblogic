@start rule: main
@genPackageDeclaration

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import weblogic.management.ManagementException;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

/** 
 * @genAuthor Copyright (c) @year by BEA. All Rights Reserved.
 *
 * This file was automatically generated, do not edit.
 *
 */
public class @genClassName extends XMLElementMBeanDelegate
  implements @genInterfaceName
{

  static final long serialVersionUID = 0000000000000001L;

/**
 * Attributes
 */
@genAttributes

/**
 * Constructor
 */
@constructor

/**
 * Getters\Setters
 */
@genAccessors

/**
 * XML generation code
 */
@toXML

}

 
@end rule: main

@start rule: constructor
  public @genClassName() {
    super();
  }
@end rule: constructor

@start rule: attributeFieldDeclaration
  private boolean @genAttributeFieldSetTesterName = false;
  private @genAttributeType @genAttributeFieldName @genDefaultValue;
@end rule: attributeFieldDeclaration

@start rule: attributeFieldArrayDeclaration
  private boolean @genAttributeFieldSetTesterName = false;
  private List @genAttributeFieldName;
@end rule: attributeFieldArrayDeclaration

@start rule: attributeArrayInitialization
  @genAttributeFieldName = Collections.synchronizedList(new ArrayList());

@end rule: attributeArrayInitialization

@start rule: getter
  /**
   * Getter for @genAttributeName
   */
  public @genAttributeType @genMethodName() {
    return @genAttributeFieldName;
  }
@end rule: getter

@start rule: setter
  /**
   * Setter for @genAttributeName
   */
  public void @genMethodName(@genAttributeType value) {
    @emptyStringCheck @genAttributeType old = @genAttributeFieldName;
    @genAttributeFieldName = value;
    @genAttributeFieldSetTesterName = @attributeIsSetExpression;
    checkChange("@genAttributeFieldName",old,@genAttributeFieldName);
  }
@end rule: setter

@start rule: arrayGetter
  /**
   * Getter for @genAttributeName
   */
  public @genAttributeType @genMethodName() {
    if(@genAttributeFieldName == null) {
      return new @genAttributeTypeMinusArrayBrackets[0];
    }
    @genAttributeType result = new @genAttributeTypeMinusArrayBrackets[@genAttributeFieldName.size()];
    result = (@genAttributeType)@genAttributeFieldName.toArray(result);
    return result;
  }
@end rule: arrayGetter

@start rule: arraySetter
  /**
   * Setter for @genAttributeName
   */
  public void @genMethodName(@genAttributeType value) {
    @genAttributeType _oldVal = null;
    if (changeSupport != null) {
      _oldVal = @getterMethodName();
    }
    @genAttributeFieldSetTesterName = true;
    if(@genAttributeFieldName == null) {
      @attributeArrayInitialization
    } else {
      @genAttributeFieldName.clear();
    }
    if (null != value) for (int i = 0; i < value.length; i++) @genAttributeFieldName.add(value[i]);
    if (changeSupport != null) {
      checkChange("@genAttributeName",_oldVal,@getterMethodName());
    }
  }
@end rule: arraySetter

@start rule: arrayAdder
  /**
   * Adder for @genAttributeName
   */
  public void @genMethodName(@genAttributeTypeMinusArrayBrackets value) {
    @genAttributeFieldSetTesterName = true;
    if(@genAttributeFieldName == null) {
      @attributeArrayInitialization
    }
    @genAttributeFieldName.add(value);
  }
@end rule: arrayAdder

@start rule: arrayRemover
  /**
   * Remover for @genAttributeName
   */
  public void @genMethodName(@genAttributeTypeMinusArrayBrackets value) {
    if(@genAttributeFieldName == null) {
      return;
    }
    @genAttributeFieldName.remove(value);
  }
@end rule: arrayRemover

@start rule: isGetter
  /**
   * Boolean getter for @genAttributeName
   */
  public boolean @genMethodName() {
    return @genAttributeFieldName;
  }
@end rule: isGetter

@start rule: toXML
  public String toXML() {
    return "NYI";
  }
@end rule: toXML
