package weblogic.ejb.container.cmp.rdbms;

import java.util.ArrayList;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import weblogic.application.descriptor.AbstractDescriptorLoader;
import weblogic.application.descriptor.BasicMunger;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.WeblogicRdbmsJarBean;
import weblogic.utils.Debug;

public class WlsCmpRdbmsReader extends BasicMunger {
   private static boolean useMunger = Debug.getCategory("weblogic.use.munger").isEnabled();
   private boolean consume = false;
   private boolean is510 = false;
   private boolean is600_11 = false;
   private boolean is600 = false;
   private boolean is700 = false;
   private boolean is810 = false;
   private boolean inEjbQlQuery = false;
   private boolean inTableMap = false;
   private boolean inEjbName = false;
   WeblogicRdbmsJarBean cmp20Bean;
   weblogic.j2ee.descriptor.wl60.WeblogicRdbmsJarBean cmp11Bean;
   ArrayList ejbQlQueryQueue;
   ArrayList tableMapQueue;

   public WlsCmpRdbmsReader(XMLStreamReader delegate, AbstractDescriptorLoader loader, DeploymentPlanBean plan, String moduleName, String uri) {
      super(delegate, loader, plan, moduleName, "ejb", uri);
   }

   public String getDtdNamespaceURI() {
      return "http://xmlns.oracle.com/weblogic/weblogic-rdbms-jar";
   }

   public void initDtdText(String dtdText) {
      if (dtdText.lastIndexOf("5.1.0") > 0) {
         this.is510 = true;
      } else if (dtdText.lastIndexOf("6.0.0") > 0) {
         if (dtdText.lastIndexOf("1.1") > 0) {
            this.is600_11 = true;
         } else {
            this.is600 = true;
         }
      } else if (dtdText.lastIndexOf("7.0.0") > 0) {
         this.is700 = true;
      } else if (dtdText.lastIndexOf("8.1.0") > 0) {
         this.is810 = true;
      }

      if (this.debug) {
         if (this.is810) {
            System.out.println("is810");
         } else if (this.is700) {
            System.out.println("is700");
         } else if (this.is600) {
            System.out.println("is600");
         } else if (this.is510) {
            System.out.println("is510");
         }
      }

   }

   private ArrayList getQueuedEvents() {
      if (this.queuedEvents == null) {
         this.queuedEvents = new ArrayList();
      }

      return this.queuedEvents;
   }

   private ArrayList getEjbQlQueryQueue() {
      if (this.ejbQlQueryQueue == null) {
         this.ejbQlQueryQueue = new ArrayList();
         this.ejbQlQueryQueue.add(this.getQueuedEvent(1, "ejb-ql-query"));
      }

      return this.ejbQlQueryQueue;
   }

   private ArrayList getTableMapQueue() {
      if (this.tableMapQueue == null) {
         this.tableMapQueue = new ArrayList();
         this.tableMapQueue.add(this.getQueuedEvent(1, "table-map"));
      }

      return this.tableMapQueue;
   }

   public int next() throws XMLStreamException {
      int next = super.next();
      if (!this.playback && this.usingDTD()) {
         if (!useMunger) {
            return next;
         } else {
            if (this.debug) {
               System.out.println("next = " + this.type2Str(next));
            }

            switch (next) {
               case 1:
                  if (!this.getLocalName().equals("weblogic-ql") && !this.getLocalName().equals("group-name") && !this.getLocalName().equals("caching-name")) {
                     if (this.is600) {
                        if (this.getLocalName().equals("table-name") || this.getLocalName().equals("field-map")) {
                           this.inTableMap = true;
                           this.getTableMapQueue().add(this.getQueuedEvent(1, this.getLocalName()));
                           return this.skip(next);
                        }

                        if (this.inTableMap) {
                           this.getTableMapQueue().add(this.getQueuedEvent(1, this.getLocalName()));
                           return this.skip(next);
                        }
                     }

                     if (this.getLocalName().equals("ejb-name")) {
                        this.inEjbName = true;
                        this.getQueuedEvents().add(this.getQueuedEvent(1, this.getLocalName()));
                        return this.skip(next);
                     }

                     this.getQueuedEvents().add(this.getQueuedEvent(1, this.getLocalName()));
                     return this.skip(next);
                  } else {
                     this.inEjbQlQuery = true;
                     this.getEjbQlQueryQueue().add(this.getQueuedEvent(1, this.getLocalName()));
                     return this.skip(next);
                  }
               case 2:
                  if (!this.getLocalName().equals("weblogic-ql") && !this.getLocalName().equals("group-name") && !this.getLocalName().equals("caching-name")) {
                     if (this.ejbQlQueryQueue != null) {
                        this.getQueuedEvents().addAll(this.getEjbQlQueryQueue());
                        this.ejbQlQueryQueue = null;
                        this.queuedEvents.add(this.getQueuedEvent(2, "ejb-ql-query"));
                     }

                     if (this.is600) {
                        if (this.getLocalName().equals("table-name") || this.getLocalName().equals("field-map")) {
                           this.inTableMap = false;
                           this.getTableMapQueue().add(this.getQueuedEvent(2, this.getLocalName()));
                           return this.skip(next);
                        }

                        if (this.inTableMap) {
                           this.getTableMapQueue().add(this.getQueuedEvent(2, this.getLocalName()));
                           return this.skip(next);
                        }

                        if (this.tableMapQueue != null) {
                           this.getQueuedEvents().addAll(this.getTableMapQueue());
                           this.tableMapQueue = null;
                           this.queuedEvents.add(this.getQueuedEvent(2, "table-map"));
                        }
                     }

                     if (this.getLocalName().equals("weblogic-rdbms-jar")) {
                        if (this.queuedEvents != null && this.queuedEvents.size() == 0) {
                           return next;
                        }

                        this.queuedEvents.add(this.getQueuedEvent(2, "weblogic-rdbms-jar"));
                        this.setPlayback(true);
                        return this.next();
                     }

                     if (this.getLocalName().equals("ejb-name")) {
                        this.inEjbName = false;
                        this.getQueuedEvents().add(this.getQueuedEvent(2, this.getLocalName()));
                        return this.skip(next);
                     }

                     this.getQueuedEvents().add(this.getQueuedEvent(2, this.getLocalName()));
                     return this.skip(next);
                  }

                  this.inEjbQlQuery = false;
                  this.getEjbQlQueryQueue().add(this.getQueuedEvent(2, this.getLocalName()));
                  return this.skip(next);
               case 3:
               default:
                  return next;
               case 4:
                  if (this.consume) {
                     return this.skip(next);
                  } else if (this.inEjbQlQuery) {
                     this.getEjbQlQueryQueue().add(this.getQueuedEvent(4, this.getTextCharacters()));
                     return this.skip(next);
                  } else if (this.is600 && this.inTableMap) {
                     this.getTableMapQueue().add(this.getQueuedEvent(4, this.getTextCharacters()));
                     return this.skip(next);
                  } else if (this.inEjbName) {
                     this.getQueuedEvents().add(this.getQueuedEvent(4, this.replaceSlashWithPeriod(this.getTextCharacters())));
                     return this.skip(next);
                  } else {
                     this.getQueuedEvents().add(this.getQueuedEvent(4, this.getTextCharacters()));
                     return this.skip(next);
                  }
            }
         }
      } else {
         return next;
      }
   }

   private char[] replaceSlashWithPeriod(char[] nameChars) {
      for(int i = 0; nameChars != null && i < nameChars.length; ++i) {
         if (nameChars[i] == '/') {
            nameChars[i] = '.';
         }
      }

      return nameChars;
   }
}
