package weblogic.eclipselink;

import weblogic.eclipselink.log.WLSDebugLevelHookImpl;
import weblogic.eclipselink.log.WLSLogAdapter;

public class WeblogicEclipseLinkLog extends WLSLogAdapter {
   public WeblogicEclipseLinkLog() {
      this.setDebugLevelHook(new WLSDebugLevelHookImpl());
   }
}
