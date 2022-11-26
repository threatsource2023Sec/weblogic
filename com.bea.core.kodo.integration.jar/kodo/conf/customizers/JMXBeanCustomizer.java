package kodo.conf.customizers;

import kodo.conf.descriptor.GUIJMXBean;
import kodo.conf.descriptor.JMX2JMXBean;
import kodo.conf.descriptor.JMXBean;
import kodo.conf.descriptor.LocalJMXBean;
import kodo.conf.descriptor.MX4J1JMXBean;
import kodo.conf.descriptor.NoneJMXBean;
import kodo.conf.descriptor.WLS81JMXBean;

public class JMXBeanCustomizer {
   private final JMXBean customized;

   public JMXBeanCustomizer(JMXBean custom) {
      this.customized = custom;
   }

   public Class[] getJMXTypes() {
      return new Class[]{NoneJMXBean.class, LocalJMXBean.class, GUIJMXBean.class, JMX2JMXBean.class, MX4J1JMXBean.class, WLS81JMXBean.class};
   }

   public JMXBean getJMX() {
      JMXBean provider = null;
      provider = this.customized.getNoneJMX();
      if (provider != null) {
         return provider;
      } else {
         JMXBean provider = this.customized.getLocalJMX();
         if (provider != null) {
            return provider;
         } else {
            JMXBean provider = this.customized.getGUIJMX();
            if (provider != null) {
               return provider;
            } else {
               JMXBean provider = this.customized.getJMX2JMX();
               if (provider != null) {
                  return provider;
               } else {
                  JMXBean provider = this.customized.getMX4J1JMX();
                  if (provider != null) {
                     return provider;
                  } else {
                     JMXBean provider = this.customized.getWLS81JMX();
                     return provider != null ? provider : null;
                  }
               }
            }
         }
      }
   }

   public JMXBean createJMX(Class type) {
      this.destroyJMX();
      JMXBean provider = null;
      if (NoneJMXBean.class.getName().equals(type.getName())) {
         provider = this.customized.createNoneJMX();
      } else if (LocalJMXBean.class.getName().equals(type.getName())) {
         provider = this.customized.createLocalJMX();
      } else if (GUIJMXBean.class.getName().equals(type.getName())) {
         provider = this.customized.createGUIJMX();
      } else if (JMX2JMXBean.class.getName().equals(type.getName())) {
         provider = this.customized.createJMX2JMX();
      } else if (MX4J1JMXBean.class.getName().equals(type.getName())) {
         provider = this.customized.createMX4J1JMX();
      } else if (WLS81JMXBean.class.getName().equals(type.getName())) {
         provider = this.customized.createWLS81JMX();
      }

      return (JMXBean)provider;
   }

   public void destroyJMX() {
      JMXBean provider = null;
      provider = this.customized.getNoneJMX();
      if (provider != null) {
         this.customized.destroyNoneJMX();
      } else {
         JMXBean provider = this.customized.getLocalJMX();
         if (provider != null) {
            this.customized.destroyLocalJMX();
         } else {
            JMXBean provider = this.customized.getGUIJMX();
            if (provider != null) {
               this.customized.destroyGUIJMX();
            } else {
               JMXBean provider = this.customized.getJMX2JMX();
               if (provider != null) {
                  this.customized.destroyJMX2JMX();
               } else {
                  JMXBean provider = this.customized.getMX4J1JMX();
                  if (provider != null) {
                     this.customized.destroyMX4J1JMX();
                  } else {
                     JMXBean provider = this.customized.getWLS81JMX();
                     if (provider != null) {
                        this.customized.destroyWLS81JMX();
                     }
                  }
               }
            }
         }
      }
   }
}
