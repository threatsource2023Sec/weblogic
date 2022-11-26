@start rule: main
@textPackageDeclaration

import java.text.MessageFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
 
/** 
 * Copyright (c) 2012,2013, Oracle and/or its affiliates. All rights reserved.
 * @exclude
 */
public class @className {

  // FIXME - The resource bundle name will need to change when the class is moved to Glassfish src line
  private static final String RESOURCE_BUNDLE_NAME = "@formatterClass";

  private ResourceBundle resourceBundle;
  private boolean format=false;
  
  // Constructors

  public @className() {
    resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, Locale.getDefault(), @className.class.getClassLoader());
  }

  public @className(Locale loc) {
    resourceBundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_NAME, loc, @className.class.getClassLoader());
  }

  public static @className getInstance() {
    return new @className();
  }

  public static @className getInstance(Locale l) {
    return new @className(l);
  }

  public void setExtendedFormat(boolean fmt) {
    format = fmt;
  }

  public boolean getExtendedFormat() { return format; }


@textMessages
}
@end rule: main

@start rule: textMessage
  /**
   * @textMessageDescription
   */
  public String @textMethodName(@textArguments) @textExceptions {
    String fmt  = "";
    String id = "@textMessageId" ;
    String subsystem = "@messageCatalogSubsystem" ;
    Object [] args = { @textArgumentClasses };
    String output =  MessageFormat.format(resourceBundle.getString(id) , args);
    if (getExtendedFormat()) {
      DateFormat dformat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, 
                                             DateFormat.LONG);
      fmt = "<"+dformat.format(new Date())+"><"+subsystem+"><"+id+"> ";
    }
    return fmt+output;
  }
@end rule: textMessage
