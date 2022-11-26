@start rule: main
@textPackageDeclaration

import java.text.MessageFormat;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import weblogic.i18n.Localizer;
import weblogic.i18n.logging.BaseTextFormatter;
import weblogic.i18ntools.L10nLookup;
 
/** 
 * Copyright (c) 2003,2014, Oracle and/or its affiliates. All rights reserved.
 * @exclude
 */
public class @className extends BaseTextFormatter {

  private Localizer l10n;

  // Constructors
  public @className() {
    l10n = L10nLookup.getLocalizer(Locale.getDefault(), "@formatterClass", @className.class.getClassLoader());
  }

  public @className(Locale l) {
    l10n = L10nLookup.getLocalizer(l, "@formatterClass", @className.class.getClassLoader());
  }

  public static @className getInstance() {
    return new @className();
  }

  public static @className getInstance(Locale l) {
    return new @className(l);
  }

@textMessages
}
@end rule: main

@start rule: textMessage
  /**
   * @textMessageDescription
   */
  public String @textMethodName(@textArguments) @textExceptions {
    String id = "@textMessageId" ;
    String subsystem = "@messageCatalogSubsystem" ;
    Object [] args = { @textArgumentClasses };
    String output =  MessageFormat.format(l10n.get(id) , args);
    if (@isExtendedFormatEnabled && getExtendedFormat()) {
      DateFormat dformat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, 
                                             DateFormat.LONG);
      String fmt = "<"+dformat.format(new Date())+"><"+subsystem+"><"+id+"> ";
      return fmt+output;
    } else {
      return output;
    }
    
  }
@end rule: textMessage
