package com.oracle.weblogic.lifecycle.config.customizers;

import com.oracle.weblogic.lifecycle.config.PDB;
import com.oracle.weblogic.lifecycle.config.Service;
import javax.inject.Singleton;
import org.glassfish.hk2.xml.api.XmlHk2ConfigurationBean;

@Singleton
public class PDBCustomizer {
   public Service getService(PDB pdb) {
      if (pdb == null) {
         return null;
      } else {
         XmlHk2ConfigurationBean bean = (XmlHk2ConfigurationBean)pdb;
         return (Service)bean._getParent();
      }
   }
}
