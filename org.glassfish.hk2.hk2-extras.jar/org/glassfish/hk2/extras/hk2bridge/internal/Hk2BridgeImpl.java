package org.glassfish.hk2.extras.hk2bridge.internal;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.Descriptor;
import org.glassfish.hk2.api.DescriptorVisibility;
import org.glassfish.hk2.api.DynamicConfiguration;
import org.glassfish.hk2.api.DynamicConfigurationListener;
import org.glassfish.hk2.api.DynamicConfigurationService;
import org.glassfish.hk2.api.Filter;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.api.Visibility;

@Singleton
@Visibility(DescriptorVisibility.LOCAL)
public class Hk2BridgeImpl implements DynamicConfigurationListener {
   private final ServiceLocator local;
   private ServiceLocator remote;
   private Filter filter;
   private List mirroredDescriptors = new ArrayList();
   private static final List EMPTY_LIST = Collections.emptyList();

   @Inject
   private Hk2BridgeImpl(ServiceLocator local) {
      this.local = local;
   }

   public synchronized void setRemote(ServiceLocator remote) {
      this.remote = remote;
      this.filter = new NoLocalNoRemoteFilter(remote.getLocatorId());
      List newDescriptors = this.local.getDescriptors(this.filter);
      this.handleChange(newDescriptors);
   }

   private synchronized void handleChange(List newDescriptors) {
      if (this.remote != null) {
         HashSet toRemove = new HashSet(this.mirroredDescriptors);
         toRemove.removeAll(newDescriptors);
         HashSet toAdd = new HashSet(newDescriptors);
         toAdd.removeAll(this.mirroredDescriptors);
         DynamicConfigurationService remoteDCS = (DynamicConfigurationService)this.remote.getService(DynamicConfigurationService.class, new Annotation[0]);
         DynamicConfiguration config = remoteDCS.createDynamicConfiguration();
         boolean dirty = false;

         Iterator var7;
         ActiveDescriptor addMe;
         for(var7 = toRemove.iterator(); var7.hasNext(); dirty = true) {
            addMe = (ActiveDescriptor)var7.next();
            Filter removeFilter = new RemoveFilter(addMe.getLocatorId(), addMe.getServiceId());
            config.addUnbindFilter(removeFilter);
         }

         for(var7 = toAdd.iterator(); var7.hasNext(); dirty = true) {
            addMe = (ActiveDescriptor)var7.next();
            CrossOverDescriptor cod = new CrossOverDescriptor(this.local, addMe);
            config.addActiveDescriptor(cod);
         }

         if (dirty) {
            config.commit();
         }

         this.mirroredDescriptors = newDescriptors;
      }
   }

   public void configurationChanged() {
      List newDescriptors = this.local.getDescriptors(this.filter);
      this.handleChange(newDescriptors);
   }

   @PreDestroy
   private void preDestroy() {
      this.handleChange(Collections.emptyList());
   }

   private static Set getMetadataLongsSet(Descriptor d, String field) {
      Set retVal = new HashSet();
      List metadataValues = (List)d.getMetadata().get(field);
      if (metadataValues == null) {
         return retVal;
      } else {
         Iterator var4 = metadataValues.iterator();

         while(var4.hasNext()) {
            String metadataValue = (String)var4.next();

            try {
               Long val = new Long(metadataValue);
               retVal.add(val);
            } catch (NumberFormatException var7) {
            }
         }

         return retVal;
      }
   }

   private static List getMetadataLongsList(Descriptor d, String field) {
      List metadataValues = (List)d.getMetadata().get(field);
      if (metadataValues == null) {
         return EMPTY_LIST;
      } else {
         List retVal = new ArrayList(metadataValues.size());
         Iterator var4 = metadataValues.iterator();

         while(var4.hasNext()) {
            String metadataValue = (String)var4.next();

            try {
               Long val = new Long(metadataValue);
               retVal.add(val);
            } catch (NumberFormatException var7) {
            }
         }

         return retVal;
      }
   }

   private static class RemoveFilter implements Filter {
      private final long localLocatorId;
      private final long localServiceId;

      private RemoveFilter(long localLocatorId, long localServiceId) {
         this.localLocatorId = localLocatorId;
         this.localServiceId = localServiceId;
      }

      public boolean matches(Descriptor d) {
         List locatorIds = Hk2BridgeImpl.getMetadataLongsList(d, "org.jvnet.hk2.hk2bridge.locator.id");
         int index = -1;
         int lcv = 0;

         Long serviceId;
         for(Iterator var5 = locatorIds.iterator(); var5.hasNext(); ++lcv) {
            serviceId = (Long)var5.next();
            if (this.localLocatorId == serviceId) {
               index = lcv;
               break;
            }
         }

         if (index == -1) {
            return false;
         } else {
            List serviceIds = Hk2BridgeImpl.getMetadataLongsList(d, "org.jvnet.hk2.hk2bridge.service.id");
            serviceId = (Long)serviceIds.get(index);
            return serviceId == this.localServiceId;
         }
      }

      // $FF: synthetic method
      RemoveFilter(long x0, long x1, Object x2) {
         this(x0, x1);
      }
   }

   private static class NoLocalNoRemoteFilter implements Filter {
      private final long remoteLocatorId;

      private NoLocalNoRemoteFilter(long remoteId) {
         this.remoteLocatorId = remoteId;
      }

      public boolean matches(Descriptor d) {
         if (DescriptorVisibility.LOCAL.equals(d.getDescriptorVisibility())) {
            return false;
         } else {
            Set previousVisits = Hk2BridgeImpl.getMetadataLongsSet(d, "org.jvnet.hk2.hk2bridge.locator.id");
            return !previousVisits.contains(new Long(this.remoteLocatorId));
         }
      }

      // $FF: synthetic method
      NoLocalNoRemoteFilter(long x0, Object x1) {
         this(x0);
      }
   }
}
