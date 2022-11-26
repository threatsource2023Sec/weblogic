package org.glassfish.hk2.xml.internal;

import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.configuration.hub.api.WriteableBeanDatabase;

public class XmlDynamicChange {
   public static final XmlDynamicChange EMPTY = new XmlDynamicChange((WriteableBeanDatabase)null, (DynamicConfiguration)null, (DynamicConfiguration)null);
   private final WriteableBeanDatabase userDatabase;
   private final DynamicConfiguration userDynamicConfiguration;
   private final DynamicConfiguration systemDynamicConfiguration;

   public XmlDynamicChange(WriteableBeanDatabase userDatabase, DynamicConfiguration userDynamicConfiguration, DynamicConfiguration systemDynamicConfiguration) {
      this.userDatabase = userDatabase;
      this.userDynamicConfiguration = userDynamicConfiguration;
      this.systemDynamicConfiguration = systemDynamicConfiguration;
   }

   public WriteableBeanDatabase getBeanDatabase() {
      return this.userDatabase;
   }

   public DynamicConfiguration getDynamicConfiguration() {
      return this.userDynamicConfiguration;
   }

   public DynamicConfiguration getSystemDynamicConfiguration() {
      return this.systemDynamicConfiguration;
   }

   public String toString() {
      return "XmlDynamicChange(" + this.userDatabase + "," + this.userDynamicConfiguration + "," + this.systemDynamicConfiguration + "," + System.identityHashCode(this) + ")";
   }
}
