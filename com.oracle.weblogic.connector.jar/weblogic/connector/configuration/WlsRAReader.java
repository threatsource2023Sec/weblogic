package weblogic.connector.configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.descriptor.AbstractDescriptorLoader;
import weblogic.application.descriptor.BasicMunger;
import weblogic.application.descriptor.ReaderEvent;
import weblogic.connector.common.Debug;
import weblogic.j2ee.descriptor.ConnectionDefinitionBean;
import weblogic.j2ee.descriptor.ConnectorBean;
import weblogic.j2ee.descriptor.OutboundResourceAdapterBean;
import weblogic.j2ee.descriptor.ResourceAdapterBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;

public class WlsRAReader extends BasicMunger {
   private boolean createExtensionBean = true;
   private static final Map wlraNameChanges = new HashMap();
   ConnectorBean connBean;
   ArrayList linkRefQueue = null;
   ArrayList proxyQueue = null;
   ArrayList loggingEnableQueue = null;
   ArrayList loggingFileNameQueue = null;
   ArrayList nativeLibdirQueue = null;
   ArrayList descriptionQueue = null;
   ArrayList jndiNameQueue = null;
   ArrayList configPropQueue = null;
   ArrayList poolParamsQueue = null;
   ArrayList unknownQueue = null;
   ArrayList currentQueue = null;
   boolean inConfigProps = false;
   boolean ignore = false;
   char[] initial_capacity = null;
   char[] max_capacity = null;
   char[] capacity_increment = null;
   char[] shrinking_enabled = null;
   char[] shrink_frequency_seconds = null;
   char[] highest_num_waiters = null;
   char[] highest_num_unavailable = null;
   char[] connection_creation_retry_frequency_seconds = null;
   char[] connection_reserve_timeout_seconds = null;
   char[] test_frequency_seconds = null;
   char[] match_connections_supported = null;
   char[] inactive_connection_timeout_seconds = null;
   String lastLocalElement;
   boolean debug = false;

   public WlsRAReader(ConnectorBean connBean, XMLStreamReader delegate, AbstractDescriptorLoader loader, DeploymentPlanBean plan, String moduleName, String uri) {
      super(delegate, loader, plan, moduleName, "rar", uri);
      this.connBean = connBean;
      this.createExtensionBean = true;
   }

   public WlsRAReader(ConnectorBean connBean, XMLStreamReader delegate, AbstractDescriptorLoader loader, DeploymentPlanBean plan, String moduleName, String uri, boolean createExtensionBean) {
      super(delegate, loader, plan, moduleName, "rar", uri);
      this.connBean = connBean;
      this.createExtensionBean = createExtensionBean;
   }

   public String getDtdNamespaceURI() {
      return "http://xmlns.oracle.com/weblogic/weblogic-connector";
   }

   public Map getLocalNameMap() {
      return wlraNameChanges;
   }

   public int next() throws XMLStreamException {
      int next = super.next();
      if (!this.playback && this.usingDTD()) {
         switch (next) {
            case 1:
               this.lastLocalElement = this.getLocalName();
               if (this.lastLocalElement.equals("native-libdir")) {
                  this.currentQueue = this.getNativeLibdirQueue();
                  this.currentQueue.add(this.getQueuedEvent(1, this.lastLocalElement));
               } else if (!this.lastLocalElement.equals("use-connection-proxies") && !this.lastLocalElement.equals("connection-profiling-enabled")) {
                  if (!this.lastLocalElement.equals("connection-factory-name") && !this.lastLocalElement.equals("ra-link-ref")) {
                     if (this.lastLocalElement.equals("description")) {
                        this.currentQueue = this.getDescriptionQueue();
                        this.currentQueue.add(this.getQueuedEvent(1, this.lastLocalElement));
                     } else if (this.lastLocalElement.equals("jndi-name")) {
                        this.currentQueue = this.getJNDINameQueue();
                        this.currentQueue.add(this.getQueuedEvent(1, this.lastLocalElement));
                     } else if (this.lastLocalElement.equals("logging-enabled")) {
                        this.currentQueue = this.getLoggingEnableQueue();
                        this.currentQueue.add(this.getQueuedEvent(1, this.lastLocalElement));
                     } else if (this.lastLocalElement.equals("log-filename")) {
                        this.currentQueue = this.getLoggingFileNameQueue();
                        this.currentQueue.add(this.getQueuedEvent(1, this.lastLocalElement));
                     } else if (this.lastLocalElement.equals("property")) {
                        this.inConfigProps = true;
                        this.currentQueue = this.getConfigPropQueue();
                        this.currentQueue.add(this.getQueuedEvent(1, this.lastLocalElement));
                     } else if (!this.lastLocalElement.equals("name") && !this.lastLocalElement.equals("value")) {
                        if (this.lastLocalElement.equals("security-principal-map")) {
                           Debug.logSecurityPrincipalMapNotAllowed();
                           this.currentQueue = this.getUknownQueue();
                           this.currentQueue.add(this.getQueuedEvent(1, this.lastLocalElement));
                        } else if (!this.lastLocalElement.equals("initial-capacity") && !this.lastLocalElement.equals("capacity-increment") && !this.lastLocalElement.equals("connection-cleanup-frequency") && !this.lastLocalElement.equals("connection-creation-retry-frequency-seconds") && !this.lastLocalElement.equals("connection-duration-time") && !this.lastLocalElement.equals("connection-maxidle-time") && !this.lastLocalElement.equals("connection-reserve-timeout-seconds") && !this.lastLocalElement.equals("highest-num-unavailable") && !this.lastLocalElement.equals("highest-num-waiters") && !this.lastLocalElement.equals("inactive-connection-timeout-seconds") && !this.lastLocalElement.equals("map-config-property") && !this.lastLocalElement.equals("match-connections-supported") && !this.lastLocalElement.equals("max-capacity") && !this.lastLocalElement.equals("pool-params") && !this.lastLocalElement.equals("shrink-frequency-seconds") && !this.lastLocalElement.equals("shrink-period-minutes") && !this.lastLocalElement.equals("shrinking-enabled") && !this.lastLocalElement.equals("test-frequency-seconds") && !this.lastLocalElement.equals("weblogic-connection-factory") && !this.ignore) {
                           this.currentQueue = this.getUknownQueue();
                           this.currentQueue.add(this.getQueuedEvent(1, this.lastLocalElement));
                        }
                     } else if (this.currentQueue != null) {
                        this.currentQueue.add(this.getQueuedEvent(1, this.lastLocalElement));
                     }
                  } else {
                     this.currentQueue = this.getLinkRefQueue();
                     this.currentQueue.add(this.getQueuedEvent(1, this.lastLocalElement));
                  }
               } else {
                  this.currentQueue = this.getProxyQueue();
                  this.currentQueue.add(this.getQueuedEvent(1, this.lastLocalElement));
               }

               return this.skip(next);
            case 2:
               if (this.getLocalName().equals("weblogic-connection-factory")) {
                  if (this.queuedEvents != null && this.queuedEvents.size() == 0) {
                     return next;
                  }

                  this.buildQueuedEvents();
                  if (this.debug) {
                     this.toXML();
                  }

                  this.setPlayback(true);
                  return this.next();
               }

               if (this.currentQueue != null) {
                  if (this.getLocalName().equals("property")) {
                     this.inConfigProps = false;
                  }

                  this.currentQueue.add(this.getQueuedEvent(2, this.getLocalName()));
                  if (!this.inConfigProps) {
                     this.currentQueue = null;
                  }
               }

               return this.skip(next);
            case 3:
            default:
               return next;
            case 4:
               if (!this.isWhiteSpace() && !this.ignore) {
                  if (this.currentQueue == null) {
                     if (this.lastLocalElement.equals("inactive-connection-timeout-seconds")) {
                        this.inactive_connection_timeout_seconds = this.getTextCharacters();
                     } else if (this.lastLocalElement.equals("connection-maxidle-time")) {
                        if (this.inactive_connection_timeout_seconds == null) {
                           this.inactive_connection_timeout_seconds = this.getTextCharacters();
                        }
                     } else if (this.lastLocalElement.equals("initial-capacity")) {
                        this.initial_capacity = this.getTextCharacters();
                     } else if (this.lastLocalElement.equals("max-capacity")) {
                        this.max_capacity = this.getTextCharacters();
                     } else if (this.lastLocalElement.equals("capacity-increment")) {
                        this.capacity_increment = this.getTextCharacters();
                     } else if (this.lastLocalElement.equals("shrinking-enabled")) {
                        this.shrinking_enabled = this.getTextCharacters();
                     } else if (this.lastLocalElement.equals("shrink-period-minutes")) {
                        if (this.shrink_frequency_seconds == null) {
                           try {
                              String shrinkPeriodMins = new String(this.getTextCharacters());
                              if (shrinkPeriodMins != null && shrinkPeriodMins.length() > 0) {
                                 int min = Integer.parseInt(shrinkPeriodMins);
                                 int sec = min * 60;
                                 String shrinkPeriodSeconds = Integer.toString(sec);
                                 this.shrink_frequency_seconds = shrinkPeriodSeconds.toCharArray();
                              }
                           } catch (Exception var6) {
                              this.shrink_frequency_seconds = this.getTextCharacters();
                           }
                        }
                     } else if (this.lastLocalElement.equals("shrink-frequency-seconds")) {
                        this.shrink_frequency_seconds = this.getTextCharacters();
                     } else if (this.lastLocalElement.equals("highest-num-waiters")) {
                        this.highest_num_waiters = this.getTextCharacters();
                     } else if (this.lastLocalElement.equals("highest-num-unavailable")) {
                        this.highest_num_unavailable = this.getTextCharacters();
                     } else if (this.lastLocalElement.equals("connection-creation-retry-frequency-seconds")) {
                        this.connection_creation_retry_frequency_seconds = this.getTextCharacters();
                     } else if (this.lastLocalElement.equals("connection-reserve-timeout-seconds")) {
                        this.connection_reserve_timeout_seconds = this.getTextCharacters();
                     } else if (this.lastLocalElement.equals("test-frequency-seconds")) {
                        this.test_frequency_seconds = this.getTextCharacters();
                     } else if (this.lastLocalElement.equals("match-connections-supported")) {
                        this.match_connections_supported = this.getTextCharacters();
                     }
                  } else {
                     this.currentQueue.add(this.getQueuedEvent(4, this.getTextCharacters()));
                  }
               }

               return this.skip(next);
         }
      } else {
         return next;
      }
   }

   private void buildQueuedEvents() {
      if (this.createExtensionBean) {
         this.getQueuedEvents().add(this.getQueuedEvent(1, "weblogic-connector-extension"));
      } else {
         this.getQueuedEvents().add(this.getQueuedEvent(1, "weblogic-connector"));
      }

      if (this.nativeLibdirQueue != null) {
         this.getQueuedEvents().addAll(this.getNativeLibdirQueue());
      }

      this.getQueuedEvents().add(this.getQueuedEvent(1, "enable-access-outside-app"));
      this.getQueuedEvents().add(this.getQueuedEvent(4, "true".toCharArray()));
      this.getQueuedEvents().add(this.getQueuedEvent(2, "enable-access-outside-app"));
      this.buildOutboundResourceAdapter();
      if (this.createExtensionBean && this.linkRefQueue != null) {
         this.getQueuedEvents().add(this.getQueuedEvent(1, "link-ref"));
         this.getQueuedEvents().addAll(this.getLinkRefQueue());
         this.getQueuedEvents().add(this.getQueuedEvent(2, "link-ref"));
      }

      if (this.createExtensionBean && (this.proxyQueue != null || this.inactive_connection_timeout_seconds != null)) {
         this.getQueuedEvents().add(this.getQueuedEvent(1, "proxy"));
         if (this.inactive_connection_timeout_seconds != null) {
            this.getQueuedEvents().add(this.getQueuedEvent(1, "inactive-connection-timeout-seconds"));
            this.getQueuedEvents().add(this.getQueuedEvent(4, this.inactive_connection_timeout_seconds));
            this.getQueuedEvents().add(this.getQueuedEvent(2, "inactive-connection-timeout-seconds"));
         }

         if (this.proxyQueue != null) {
            this.getQueuedEvents().addAll(this.getProxyQueue());
         }

         this.getQueuedEvents().add(this.getQueuedEvent(2, "proxy"));
      }

      if (this.unknownQueue != null) {
         this.getQueuedEvents().addAll(this.getUknownQueue());
      }

      if (this.createExtensionBean) {
         this.getQueuedEvents().add(this.getQueuedEvent(2, "weblogic-connector-extension"));
      } else {
         this.getQueuedEvents().add(this.getQueuedEvent(2, "weblogic-connector"));
      }

   }

   private void buildOutboundResourceAdapter() {
      ResourceAdapterBean raBean = null;
      OutboundResourceAdapterBean outBean = null;
      ConnectionDefinitionBean[] connDefnBeans = null;
      char[] connFactoryName = null;
      String errXPath = "";
      this.getQueuedEvents().add(this.getQueuedEvent(1, "outbound-resource-adapter"));
      this.getQueuedEvents().add(this.getQueuedEvent(1, "connection-definition-group"));
      errXPath = errXPath + "/connector";
      if (this.connBean != null) {
         errXPath = errXPath + "/resourceadapter";
         raBean = this.connBean.getResourceAdapter();
         if (raBean != null) {
            errXPath = errXPath + "/outbound-resourceadapter";
            outBean = raBean.getOutboundResourceAdapter();
            if (outBean != null) {
               errXPath = errXPath + "/connection-definition";
               connDefnBeans = outBean.getConnectionDefinitions();
               if (connDefnBeans != null && connDefnBeans.length > 0) {
                  errXPath = errXPath + "/connectionfactory-interface";
                  String cfi = connDefnBeans[0].getConnectionFactoryInterface();
                  if (cfi != null) {
                     connFactoryName = cfi.toCharArray();
                  }
               }
            }
         }

         if (connFactoryName == null) {
            connFactoryName = new char[]{' '};
            Debug.logBuildOutboundFailed(errXPath);
         }
      } else {
         connFactoryName = "LinkRef".toCharArray();
      }

      this.getQueuedEvents().add(this.getQueuedEvent(1, "connection-factory-interface"));
      this.getQueuedEvents().add(this.getQueuedEvent(4, connFactoryName));
      this.getQueuedEvents().add(this.getQueuedEvent(2, "connection-factory-interface"));
      this.getQueuedEvents().add(this.getQueuedEvent(1, "connection-instance"));
      if (this.descriptionQueue != null) {
         this.getQueuedEvents().addAll(this.getDescriptionQueue());
      }

      if (this.jndiNameQueue != null) {
         this.getQueuedEvents().addAll(this.getJNDINameQueue());
      }

      this.getQueuedEvents().add(this.getQueuedEvent(1, "connection-properties"));
      this.buildPoolParams();
      ArrayList loggingQueue = this.getLoggingQueue();
      if (!loggingQueue.isEmpty()) {
         this.getQueuedEvents().add(this.getQueuedEvent(1, "logging"));
         this.getQueuedEvents().addAll(loggingQueue);
         this.getQueuedEvents().add(this.getQueuedEvent(2, "logging"));
      }

      if (this.configPropQueue != null) {
         this.getQueuedEvents().add(this.getQueuedEvent(1, "properties"));
         this.getQueuedEvents().addAll(this.getConfigPropQueue());
         this.getQueuedEvents().add(this.getQueuedEvent(2, "properties"));
      }

      this.getQueuedEvents().add(this.getQueuedEvent(2, "connection-properties"));
      this.getQueuedEvents().add(this.getQueuedEvent(2, "connection-instance"));
      this.getQueuedEvents().add(this.getQueuedEvent(2, "connection-definition-group"));
      this.getQueuedEvents().add(this.getQueuedEvent(2, "outbound-resource-adapter"));
   }

   private void buildPoolParams() {
      if (this.initial_capacity != null || this.max_capacity != null || this.capacity_increment != null || this.shrinking_enabled != null || this.shrink_frequency_seconds != null || this.highest_num_waiters != null || this.highest_num_unavailable != null || this.connection_creation_retry_frequency_seconds != null || this.connection_reserve_timeout_seconds != null || this.test_frequency_seconds != null || this.match_connections_supported != null) {
         this.getQueuedEvents().add(this.getQueuedEvent(1, "pool-params"));
         if (this.initial_capacity != null) {
            this.getQueuedEvents().add(this.getQueuedEvent(1, "initial-capacity"));
            this.getQueuedEvents().add(this.getQueuedEvent(4, this.initial_capacity));
            this.getQueuedEvents().add(this.getQueuedEvent(2, "initial-capacity"));
         }

         if (this.max_capacity != null) {
            this.getQueuedEvents().add(this.getQueuedEvent(1, "max-capacity"));
            this.getQueuedEvents().add(this.getQueuedEvent(4, this.max_capacity));
            this.getQueuedEvents().add(this.getQueuedEvent(2, "max-capacity"));
         }

         if (this.capacity_increment != null) {
            this.getQueuedEvents().add(this.getQueuedEvent(1, "capacity-increment"));
            this.getQueuedEvents().add(this.getQueuedEvent(4, this.capacity_increment));
            this.getQueuedEvents().add(this.getQueuedEvent(2, "capacity-increment"));
         }

         if (this.shrinking_enabled != null) {
            this.getQueuedEvents().add(this.getQueuedEvent(1, "shrinking-enabled"));
            this.getQueuedEvents().add(this.getQueuedEvent(4, this.shrinking_enabled));
            this.getQueuedEvents().add(this.getQueuedEvent(2, "shrinking-enabled"));
         }

         if (this.shrink_frequency_seconds != null) {
            this.getQueuedEvents().add(this.getQueuedEvent(1, "shrink-frequency-seconds"));
            this.getQueuedEvents().add(this.getQueuedEvent(4, this.shrink_frequency_seconds));
            this.getQueuedEvents().add(this.getQueuedEvent(2, "shrink-frequency-seconds"));
         }

         if (this.highest_num_waiters != null) {
            this.getQueuedEvents().add(this.getQueuedEvent(1, "highest-num-waiters"));
            this.getQueuedEvents().add(this.getQueuedEvent(4, this.highest_num_waiters));
            this.getQueuedEvents().add(this.getQueuedEvent(2, "highest-num-waiters"));
         }

         if (this.highest_num_unavailable != null) {
            this.getQueuedEvents().add(this.getQueuedEvent(1, "highest-num-unavailable"));
            this.getQueuedEvents().add(this.getQueuedEvent(4, this.highest_num_unavailable));
            this.getQueuedEvents().add(this.getQueuedEvent(2, "highest-num-unavailable"));
         }

         if (this.connection_creation_retry_frequency_seconds != null) {
            this.getQueuedEvents().add(this.getQueuedEvent(1, "connection-creation-retry-frequency-seconds"));
            this.getQueuedEvents().add(this.getQueuedEvent(4, this.connection_creation_retry_frequency_seconds));
            this.getQueuedEvents().add(this.getQueuedEvent(2, "connection-creation-retry-frequency-seconds"));
         }

         if (this.connection_reserve_timeout_seconds != null) {
            this.getQueuedEvents().add(this.getQueuedEvent(1, "connection-reserve-timeout-seconds"));
            this.getQueuedEvents().add(this.getQueuedEvent(4, this.connection_reserve_timeout_seconds));
            this.getQueuedEvents().add(this.getQueuedEvent(2, "connection-reserve-timeout-seconds"));
         }

         if (this.test_frequency_seconds != null) {
            this.getQueuedEvents().add(this.getQueuedEvent(1, "test-frequency-seconds"));
            this.getQueuedEvents().add(this.getQueuedEvent(4, this.test_frequency_seconds));
            this.getQueuedEvents().add(this.getQueuedEvent(2, "test-frequency-seconds"));
         }

         if (this.match_connections_supported != null) {
            this.getQueuedEvents().add(this.getQueuedEvent(1, "match-connections-supported"));
            this.getQueuedEvents().add(this.getQueuedEvent(4, this.match_connections_supported));
            this.getQueuedEvents().add(this.getQueuedEvent(2, "match-connections-supported"));
         }

         this.getQueuedEvents().add(this.getQueuedEvent(2, "pool-params"));
      }

   }

   private void toXML() {
      int indentLevel = 0;
      ReaderEvent tempQueuedEvent = null;
      String output = "";

      for(int i = 0; i < this.getQueuedEvents().size(); ++i) {
         tempQueuedEvent = (ReaderEvent)this.getQueuedEvents().get(i);
         if (tempQueuedEvent != null) {
            switch (tempQueuedEvent.getEventType()) {
               case 1:
                  ++indentLevel;
                  output = this.indent(indentLevel) + "<" + tempQueuedEvent.getLocalName() + ">";
                  break;
               case 2:
                  output = this.indent(indentLevel) + "</" + tempQueuedEvent.getLocalName() + ">";
                  --indentLevel;
               case 3:
               default:
                  break;
               case 4:
                  if (tempQueuedEvent.getCharacters() != null) {
                     output = this.indent(indentLevel) + new String((char[])tempQueuedEvent.getCharacters());
                  } else {
                     output = "";
                  }
            }

            System.out.println(output);
         }
      }

   }

   private String indent(int level) {
      String indent = "";
      String indentTab = "  ";

      for(int i = 0; i < level; ++i) {
         indent = indent + indentTab;
      }

      return indent;
   }

   private ArrayList getQueuedEvents() {
      if (this.queuedEvents == null) {
         this.queuedEvents = new ArrayList();
      }

      return this.queuedEvents;
   }

   private ArrayList getUknownQueue() {
      if (this.unknownQueue == null) {
         this.unknownQueue = new ArrayList();
      }

      return this.unknownQueue;
   }

   private ArrayList getConfigPropQueue() {
      if (this.configPropQueue == null) {
         this.configPropQueue = new ArrayList();
      }

      return this.configPropQueue;
   }

   private ArrayList getDescriptionQueue() {
      if (this.descriptionQueue == null) {
         this.descriptionQueue = new ArrayList();
      }

      return this.descriptionQueue;
   }

   private ArrayList getJNDINameQueue() {
      if (this.jndiNameQueue == null) {
         this.jndiNameQueue = new ArrayList();
      }

      return this.jndiNameQueue;
   }

   private ArrayList getLinkRefQueue() {
      if (this.linkRefQueue == null) {
         this.linkRefQueue = new ArrayList();
      }

      return this.linkRefQueue;
   }

   private ArrayList getLoggingEnableQueue() {
      if (this.loggingEnableQueue == null) {
         this.loggingEnableQueue = new ArrayList();
      }

      return this.loggingEnableQueue;
   }

   private ArrayList getLoggingFileNameQueue() {
      if (this.loggingFileNameQueue == null) {
         this.loggingFileNameQueue = new ArrayList();
      }

      return this.loggingFileNameQueue;
   }

   private ArrayList getLoggingQueue() {
      ArrayList loggingQueue = new ArrayList();
      if (this.loggingFileNameQueue != null) {
         loggingQueue.addAll(this.loggingFileNameQueue);
      }

      if (this.loggingEnableQueue != null) {
         loggingQueue.addAll(this.loggingEnableQueue);
      }

      return loggingQueue;
   }

   private ArrayList getProxyQueue() {
      if (this.proxyQueue == null) {
         this.proxyQueue = new ArrayList();
      }

      return this.proxyQueue;
   }

   private ArrayList getNativeLibdirQueue() {
      if (this.nativeLibdirQueue == null) {
         this.nativeLibdirQueue = new ArrayList();
      }

      return this.nativeLibdirQueue;
   }

   public boolean supportsValidation() {
      return true;
   }

   static {
      wlraNameChanges.put("weblogic-connection-factory-dd", "weblogic-connection-factory");
      wlraNameChanges.put("map-config-property", "property");
      wlraNameChanges.put("map-config-property-name", "name");
      wlraNameChanges.put("map-config-property-value", "value");
   }
}
