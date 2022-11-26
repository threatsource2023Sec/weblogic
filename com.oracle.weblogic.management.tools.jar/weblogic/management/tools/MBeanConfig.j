@start rule: main
package weblogic.management.configuration;

import javax.management.ObjectName;

/** 
 * @genAuthor Copyright (c) @year by BEA, Inc. All Rights Reserved.
 */
public interface @genConfigInterfaceName 
  extends @genConfigSuperInterfaceName
{
@configGetters
}
@end rule: main

@start rule: configGetter
  @genAttributeType @genGetOrIsAttribute;
@end rule: configGetter
