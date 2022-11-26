package weblogic.diagnostics.harvester.internal;

import weblogic.utils.PropertyHelper;

public interface HarvesterInternalConstants {
   boolean TEST_NO_ARCHIVE = PropertyHelper.getBoolean("weblogic.diagnostics.module.harvester.DBG_NoArchive");
   boolean DBG_USE_LONG_DATA_FORM = PropertyHelper.getBoolean("weblogic.diagnostics.module.harvester.DBG_UseLongDataForm");
   long MINIMUM_SAMPLE_INTERVAL_AS_PERCENT_OF_SAMPLE_PERIOD = 50L;
   String HVST_TIMER_MANAGER = "HarvesterTimerManager";
   long NANOS_PER_MILLI = 1000000L;
}
