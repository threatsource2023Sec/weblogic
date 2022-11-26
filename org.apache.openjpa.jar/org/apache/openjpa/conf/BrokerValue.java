package org.apache.openjpa.conf;

import org.apache.openjpa.kernel.BrokerImpl;
import org.apache.openjpa.kernel.FinalizingBrokerImpl;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.PluginValue;
import org.apache.openjpa.util.InternalException;

public class BrokerValue extends PluginValue {
   public static final String KEY = "BrokerImpl";
   public static final String NON_FINALIZING_ALIAS = "non-finalizing";
   public static final String DEFAULT_ALIAS = "default";
   private BrokerImpl _templateBroker;

   public BrokerValue() {
      super("BrokerImpl", false);
      String[] aliases = new String[]{"default", FinalizingBrokerImpl.class.getName(), "non-finalizing", BrokerImpl.class.getName()};
      this.setAliases(aliases);
      this.setDefault(aliases[0]);
      this.setString(aliases[0]);
   }

   public Object newInstance(String clsName, Class type, Configuration conf, boolean fatal) {
      this.getTemplateBroker(clsName, type, conf, fatal);

      try {
         return this._templateBroker.clone();
      } catch (CloneNotSupportedException var6) {
         throw new InternalException(var6);
      }
   }

   public Class getTemplateBrokerType(Configuration c) {
      return this.getTemplateBroker(this.getClassName(), BrokerImpl.class, c, true).getClass();
   }

   private BrokerImpl getTemplateBroker(String clsName, Class type, Configuration conf, boolean fatal) {
      if (clsName != null && clsName.equals(this.getClassName())) {
         if (this._templateBroker == null) {
            this._templateBroker = (BrokerImpl)super.newInstance(clsName, type, conf, fatal);
         }

         return this._templateBroker;
      } else {
         throw new IllegalArgumentException("clsName != configured value '" + this.getClassName() + "'");
      }
   }
}
