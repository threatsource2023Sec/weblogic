package weblogic.jms.module.observers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import weblogic.management.configuration.ForeignJMSConnectionFactoryMBean;
import weblogic.management.configuration.ForeignJMSDestinationMBean;
import weblogic.management.configuration.ForeignJMSServerMBean;
import weblogic.utils.ArrayUtils;

public class JMSForeignObserver implements PropertyChangeListener, ArrayUtils.DiffHandler {
   private static final String DESTINATION_STRING = "ForeignJMSDestinations";
   private static final String CONNECTION_FACTORY_STRING = "ForeignJMSConnectionFactories";
   private static final String[] handledProperties = new String[]{"ForeignJMSDestinations", "ForeignJMSConnectionFactories"};
   private static final int UNHANDLED = -1;
   private static final int DESTINATION = 0;
   private static final int CONNECTION_FACTORY = 1;
   private static final int MAX_PROPERTIES = 2;
   private JMSObserver domainObserver;
   private ForeignJMSServerMBean foreignJMSServer;
   private int currentType = -1;

   public JMSForeignObserver(JMSObserver paramDomainObserver, ForeignJMSServerMBean paramForeignJMSServer) {
      this.domainObserver = paramDomainObserver;
      this.foreignJMSServer = paramForeignJMSServer;
   }

   public synchronized void propertyChange(PropertyChangeEvent evt) {
      this.currentType = this.getType(evt.getPropertyName());
      if (this.currentType != -1) {
         Object[] originalObjects = (Object[])((Object[])evt.getOldValue());
         Object[] proposedObjects = (Object[])((Object[])evt.getNewValue());
         ArrayUtils.computeDiff(originalObjects, proposedObjects, this, this.domainObserver);
         this.currentType = -1;
      }
   }

   public ForeignJMSServerMBean getForeignJMSServer() {
      return this.foreignJMSServer;
   }

   private int getType(String propertyName) {
      if (propertyName == null) {
         return -1;
      } else {
         for(int lcv = 0; lcv < 2; ++lcv) {
            String property = handledProperties[lcv];
            if (property.equals(propertyName)) {
               return lcv;
            }
         }

         return -1;
      }
   }

   private void addDestination(ForeignJMSDestinationMBean foreignDestination) {
   }

   private void removeDestination(ForeignJMSDestinationMBean destination) {
   }

   private void addConnectionFactory(ForeignJMSConnectionFactoryMBean foreignConnectionFactory) {
   }

   private void removeConnectionFactory(ForeignJMSConnectionFactoryMBean connectionFactory) {
   }

   public void addObject(Object toAdd) {
      switch (this.currentType) {
         case 0:
            this.addDestination((ForeignJMSDestinationMBean)toAdd);
            break;
         case 1:
            this.addConnectionFactory((ForeignJMSConnectionFactoryMBean)toAdd);
            break;
         default:
            throw new AssertionError("ERROR: Unknown current type: " + this.currentType);
      }

   }

   public void removeObject(Object toRemove) {
      switch (this.currentType) {
         case 0:
            this.removeDestination((ForeignJMSDestinationMBean)toRemove);
            break;
         case 1:
            this.removeConnectionFactory((ForeignJMSConnectionFactoryMBean)toRemove);
            break;
         default:
            throw new AssertionError("ERROR: Unknown current type: " + this.currentType);
      }

   }

   public boolean equals(Object compareMe) {
      if (compareMe != null && compareMe instanceof JMSForeignObserver) {
         JMSForeignObserver toCompare = (JMSForeignObserver)compareMe;
         return this.foreignJMSServer == toCompare.foreignJMSServer;
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.foreignJMSServer.hashCode();
   }
}
