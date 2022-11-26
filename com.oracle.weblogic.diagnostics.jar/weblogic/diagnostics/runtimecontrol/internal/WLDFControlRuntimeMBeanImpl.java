package weblogic.diagnostics.runtimecontrol.internal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.descriptor.util.WLDFDescriptorLoader;
import weblogic.diagnostics.i18n.DiagnosticsTextTextFormatter;
import weblogic.diagnostics.runtimecontrol.RuntimeProfileDriver;
import weblogic.management.ManagementException;
import weblogic.management.ManagementRuntimeException;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.WLDFControlRuntimeMBean;
import weblogic.management.runtime.WLDFSystemResourceControlRuntimeMBean;
import weblogic.utils.collections.ConcurrentHashMap;

public class WLDFControlRuntimeMBeanImpl extends RuntimeMBeanDelegate implements WLDFControlRuntimeMBean {
   private static DiagnosticsTextTextFormatter textFormatter = DiagnosticsTextTextFormatter.getInstance();
   private Map profiles = new ConcurrentHashMap();
   private List systemProfiles = new ArrayList();

   public WLDFSystemResourceControlRuntimeMBean createSystemResourceControl(String name, String descriptor) {
      if (this.profiles.containsKey(name)) {
         throw new IllegalArgumentException(textFormatter.getRuntimeControlAlreadyExists(name));
      } else {
         WLDFResourceBean resource = null;

         try {
            InputStream fis = new ByteArrayInputStream(descriptor.getBytes());
            WLDFDescriptorLoader loader = new WLDFDescriptorLoader(fis);
            resource = (WLDFResourceBean)loader.loadDescriptorBean();
            resource.setName(name);
            resource.getHarvester().setName(name);
            resource.getWatchNotification().setName(name);
            resource.getInstrumentation().setName(name);
         } catch (IOException var9) {
            throw new IllegalArgumentException(var9);
         } catch (XMLStreamException var10) {
            throw new IllegalArgumentException(var10);
         }

         try {
            WLDFSystemResourceControlRuntimeMBean profile = new WLDFSystemResourceControlRuntimeMBeanImpl(resource, name, this, true);
            synchronized(this.profiles) {
               this.profiles.put(name, profile);
            }

            RuntimeProfileDriver.getInstance().deploy(resource);
            return profile;
         } catch (ManagementException var8) {
            throw new IllegalArgumentException(var8);
         }
      }
   }

   public void destroySystemResourceControl(WLDFSystemResourceControlRuntimeMBean control) {
      WLDFSystemResourceControlRuntimeMBeanImpl profile;
      synchronized(this.profiles) {
         if (this.systemProfiles.contains(control.getName())) {
            throw new IllegalArgumentException(textFormatter.getAttemptedToDeleteDomainConfiguredResource(control.getName()));
         }

         profile = (WLDFSystemResourceControlRuntimeMBeanImpl)this.profiles.remove(control.getName());
      }

      if (profile != null) {
         try {
            profile.unregister();
         } catch (ManagementException var5) {
            throw new ManagementRuntimeException(var5);
         }
      }

   }

   public WLDFControlRuntimeMBeanImpl(String name, RuntimeMBean parent) throws ManagementException {
      super(name, parent);
   }

   public WLDFSystemResourceControlRuntimeMBean lookupSystemResourceControl(String name) {
      return (WLDFSystemResourceControlRuntimeMBean)this.profiles.get(name);
   }

   public WLDFSystemResourceControlRuntimeMBean[] getSystemResourceControls() {
      synchronized(this.profiles) {
         WLDFSystemResourceControlRuntimeMBean[] returnArray = (WLDFSystemResourceControlRuntimeMBean[])this.profiles.values().toArray(new WLDFSystemResourceControlRuntimeMBean[this.profiles.values().size()]);
         return returnArray;
      }
   }

   public void addSystemWLDFProfileControl(WLDFSystemResourceControlRuntimeMBean fromSystem) {
      String resName = fromSystem.getName();
      synchronized(this.profiles) {
         this.profiles.put(resName, fromSystem);
         this.systemProfiles.add(resName);
      }
   }

   public void removeSystemWLDFProfileControl(String name) throws ManagementException {
      WLDFSystemResourceControlRuntimeMBean profile = this.lookupSystemResourceControl(name);
      if (profile != null) {
         ((WLDFSystemResourceControlRuntimeMBeanImpl)profile).unregister();
         synchronized(this.profiles) {
            this.profiles.remove(name);
            this.systemProfiles.remove(name);
         }
      }

   }

   public void redeploy(WLDFResourceBean proposedBean) throws ManagementException {
      WLDFSystemResourceControlRuntimeMBean profileControl = (WLDFSystemResourceControlRuntimeMBean)this.profiles.get(proposedBean.getName());
      if (profileControl != null) {
         ((WLDFSystemResourceControlRuntimeMBeanImpl)profileControl).redeploy(proposedBean);
      }

   }
}
