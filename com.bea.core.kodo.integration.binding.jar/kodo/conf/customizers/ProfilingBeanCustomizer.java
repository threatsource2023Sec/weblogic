package kodo.conf.customizers;

import kodo.conf.descriptor.ExportProfilingBean;
import kodo.conf.descriptor.GUIProfilingBean;
import kodo.conf.descriptor.LocalProfilingBean;
import kodo.conf.descriptor.NoneProfilingBean;
import kodo.conf.descriptor.ProfilingBean;

public class ProfilingBeanCustomizer {
   private final ProfilingBean customized;

   public ProfilingBeanCustomizer(ProfilingBean custom) {
      this.customized = custom;
   }

   public Class[] getProfilingTypes() {
      return new Class[]{NoneProfilingBean.class, LocalProfilingBean.class, ExportProfilingBean.class, GUIProfilingBean.class};
   }

   public ProfilingBean getProfiling() {
      ProfilingBean provider = null;
      provider = this.customized.getNoneProfiling();
      if (provider != null) {
         return provider;
      } else {
         ProfilingBean provider = this.customized.getLocalProfiling();
         if (provider != null) {
            return provider;
         } else {
            ProfilingBean provider = this.customized.getExportProfiling();
            if (provider != null) {
               return provider;
            } else {
               ProfilingBean provider = this.customized.getGUIProfiling();
               return provider != null ? provider : null;
            }
         }
      }
   }

   public ProfilingBean createProfiling(Class type) {
      this.destroyProfiling();
      ProfilingBean provider = null;
      if (NoneProfilingBean.class.getName().equals(type.getName())) {
         provider = this.customized.createNoneProfiling();
      } else if (LocalProfilingBean.class.getName().equals(type.getName())) {
         provider = this.customized.createLocalProfiling();
      } else if (ExportProfilingBean.class.getName().equals(type.getName())) {
         provider = this.customized.createExportProfiling();
      } else if (GUIProfilingBean.class.getName().equals(type.getName())) {
         provider = this.customized.createGUIProfiling();
      }

      return (ProfilingBean)provider;
   }

   public void destroyProfiling() {
      ProfilingBean provider = null;
      provider = this.customized.getNoneProfiling();
      if (provider != null) {
         this.customized.destroyNoneProfiling();
      } else {
         ProfilingBean provider = this.customized.getLocalProfiling();
         if (provider != null) {
            this.customized.destroyLocalProfiling();
         } else {
            ProfilingBean provider = this.customized.getExportProfiling();
            if (provider != null) {
               this.customized.destroyExportProfiling();
            } else {
               ProfilingBean provider = this.customized.getGUIProfiling();
               if (provider != null) {
                  this.customized.destroyGUIProfiling();
               }
            }
         }
      }
   }
}
