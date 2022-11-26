package weblogic.application.naming;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.MessageDestinationBean;
import weblogic.j2ee.descriptor.wl.MessageDestinationDescriptorBean;
import weblogic.logging.Loggable;

public class MessageDestinationInfoRegistryImpl implements MessageDestinationInfoRegistry {
   private final Map messageDestInfoMappings = new ConcurrentHashMap();

   public MessageDestinationInfoRegistry.MessageDestinationInfo get(String messageDestinationLinkName) {
      return (MessageDestinationInfoRegistry.MessageDestinationInfo)this.messageDestInfoMappings.get(messageDestinationLinkName);
   }

   public void register(MessageDestinationBean[] mdbs, MessageDestinationDescriptorBean[] mdds) throws EnvironmentException {
      if (mdds != null || mdbs != null) {
         if (mdds == null) {
            mdds = new MessageDestinationDescriptorBean[0];
         }

         if (mdbs == null) {
            mdbs = new MessageDestinationBean[0];
         }

         Map mdescs = new HashMap(mdds.length);
         MessageDestinationDescriptorBean[] var4 = mdds;
         int var5 = mdds.length;

         int var6;
         MessageDestinationDescriptorBean mdd;
         for(var6 = 0; var6 < var5; ++var6) {
            mdd = var4[var6];
            mdescs.put(mdd.getMessageDestinationName(), mdd);
         }

         this.validate(mdbs, mdescs);
         MessageDestinationBean[] var9 = mdbs;
         var5 = mdbs.length;

         MessageDestinationInfoImpl mdi;
         for(var6 = 0; var6 < var5; ++var6) {
            MessageDestinationBean mdb = var9[var6];
            mdi = new MessageDestinationInfoImpl((MessageDestinationDescriptorBean)mdescs.remove(mdb.getMessageDestinationName()), mdb);
            this.messageDestInfoMappings.put(mdb.getMessageDestinationName(), mdi);
         }

         var4 = mdds;
         var5 = mdds.length;

         for(var6 = 0; var6 < var5; ++var6) {
            mdd = var4[var6];
            if (!this.messageDestInfoMappings.containsKey(mdd.getMessageDestinationName())) {
               mdi = new MessageDestinationInfoImpl(mdd, (MessageDestinationBean)null);
               this.messageDestInfoMappings.put(mdd.getMessageDestinationName(), mdi);
            }
         }

      }
   }

   private void validate(MessageDestinationBean[] mds, Map mdescs) throws EnvironmentException {
      if (mds.length != 0) {
         MessageDestinationBean[] var3 = mds;
         int var4 = mds.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            MessageDestinationBean mdb = var3[var5];
            MessageDestinationDescriptorBean mdd = (MessageDestinationDescriptorBean)mdescs.get(mdb.getMessageDestinationName());
            if (mdd != null && mdd.getDestinationResourceLink() == null && mdd.getDestinationJNDIName() == null || mdd == null && mdb.getLookupName() == null && mdb.getMappedName() == null) {
               Loggable l = J2EELogger.logMissingMessageDestinationDescriptorLoggable(mdb.getMessageDestinationName());
               throw new EnvironmentException(l.getMessage());
            }
         }

      }
   }

   public Collection getAll() {
      return this.messageDestInfoMappings.values();
   }

   private static class MessageDestinationInfoImpl implements MessageDestinationInfoRegistry.MessageDestinationInfo {
      private final MessageDestinationDescriptorBean mdd;
      private final MessageDestinationBean mdb;
      private String destinationName;

      public MessageDestinationInfoImpl(MessageDestinationDescriptorBean mdd, MessageDestinationBean mdb) {
         this.mdd = mdd;
         this.mdb = mdb;
         if (this.mdd != null) {
            this.destinationName = this.mdd.getMessageDestinationName();
         } else if (this.mdb != null) {
            this.destinationName = this.mdb.getMessageDestinationName();
         }

      }

      public MessageDestinationBean getMessageDestination() {
         return this.mdb;
      }

      public MessageDestinationDescriptorBean getMessageDestinationDescriptor() {
         return this.mdd;
      }

      public String getMessageDestinationName() {
         return this.destinationName;
      }
   }
}
