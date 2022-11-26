package weblogic.messaging.path;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import weblogic.management.ManagementException;
import weblogic.management.runtime.PSAssemblyRuntimeMBean;
import weblogic.messaging.path.helper.KeySerializable;
import weblogic.messaging.path.helper.KeyString;
import weblogic.store.PersistentMapAsyncTX;
import weblogic.store.PersistentStoreException;

public class PSAssemblyRuntimeDelegate extends PSEntryCursorRuntimeDelegate implements PSAssemblyRuntimeMBean {
   private static transient long counter;
   private transient Key sampleKey;
   private transient PathServiceMap pathService;

   public PSAssemblyRuntimeDelegate(Key sample, PathServiceRuntimeDelegate pathServiceRuntime, PathServiceMap pathService) throws ManagementException {
      super(sample.getAssemblyId() + "." + getNewCounter(), pathServiceRuntime);
      this.pathService = pathService;
      this.sampleKey = sample;
   }

   private static synchronized long getNewCounter() {
      return (long)(counter++);
   }

   public String getMapEntries() throws ManagementException {
      return this.getMapEntries(0);
   }

   public String getMapEntries(int timeout) throws ManagementException {
      Set keyValues;
      try {
         PersistentMapAsyncTX assemblyMap = this.pathService.mapByKey(this.sampleKey);
         keyValues = assemblyMap.keySet();
      } catch (PersistentStoreException var7) {
         throw new ManagementException(var7.getMessage());
      }

      Key[] keys = new Key[keyValues.size()];
      Iterator iterator = keyValues.iterator();
      int i = 0;

      while(iterator.hasNext()) {
         Object o = iterator.next();
         if (o instanceof String) {
            keys[i++] = new KeyString(this.sampleKey.getSubsystem(), this.sampleKey.getAssemblyId(), (String)o);
         } else {
            keys[i++] = new KeySerializable(this.sampleKey.getSubsystem(), this.sampleKey.getAssemblyId(), (Serializable)o);
         }
      }

      PSEntryCursorDelegate delegate = new PSEntryCursorDelegate(this, new PSEntryOpenDataHelper(), timeout, keys, this.pathService);
      this.addCursorDelegate(delegate);
      return delegate.getHandle();
   }
}
