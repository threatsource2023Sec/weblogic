package weblogic.diagnostics.watch;

public interface WatchConstants {
   String WATCH_SOURCE_NAME = "WatchSource";
   int RULE_TYPE_LOG = 1;
   int RULE_TYPE_HARVESTER = 2;
   int RULE_TYPE_EVENT_DATA = 3;
   int RULE_TYPE_DOMAIN_LOG = 4;
   int ALARM_TYPE_NONE = 0;
   int ALARM_TYPE_MANUAL_RESET = 1;
   int ALARM_TYPE_AUTOMATIC_RESET = 2;
   String WATCH_RULE_TYPE_DOMAIN_LOG = "DomainLog";
   String WATCH_RULE_TYPE_LOG = "Log";
   String WATCH_RULE_TYPE_HARVESTER = "Harvester";
   String WATCH_RULE_TYPE_EVENT_DATA = "EventData";
   String WATCH_ALARM_TYPE_NONE = "None";
   String WATCH_ALARM_TYPE_MANUAL = "ManualReset";
   String WATCH_ALARM_TYPE_AUTOMATIC = "AutomaticReset";
}
