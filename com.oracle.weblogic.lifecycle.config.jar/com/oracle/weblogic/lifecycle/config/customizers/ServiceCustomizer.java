package com.oracle.weblogic.lifecycle.config.customizers;

import com.oracle.weblogic.lifecycle.config.Environment;
import com.oracle.weblogic.lifecycle.config.PDB;
import com.oracle.weblogic.lifecycle.config.Resources;
import com.oracle.weblogic.lifecycle.config.Service;
import com.oracle.weblogic.lifecycle.config.Tenant;
import java.lang.annotation.Annotation;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.xml.api.XmlHk2ConfigurationBean;
import org.glassfish.hk2.xml.api.XmlService;

@Singleton
public class ServiceCustomizer {
   @Inject
   private XmlService xmlService;
   @Inject
   private ServiceLocator locator;

   public PDB createPDB(Service service, String name, String id, String status) {
      PDB pdb = (PDB)this.xmlService.createBean(PDB.class);
      pdb.setName(name);
      pdb.setId(id);
      pdb.setPdbStatus(status);
      if (service.getPdb() != null) {
         service.setPdb((PDB)null);
      }

      service.setPdb(pdb);
      return service.getPdb();
   }

   public PDB deletePDB(Service service, PDB pdb) {
      if (pdb == null) {
         return null;
      } else {
         PDB retVal = service.getPdb();
         service.setPdb((PDB)null);
         return retVal;
      }
   }

   public PDB deletePDB(Service service, String pdbName) {
      PDB killMe = service.getPdb();
      if (killMe == null) {
         return null;
      } else {
         service.setPdb((PDB)null);
         return killMe;
      }
   }

   public Resources createResourcesIfNotFound(Service service) {
      Resources resources = service.getResources();
      if (resources != null) {
         return resources;
      } else {
         resources = (Resources)this.xmlService.createBean(Resources.class);
         service.setResources(resources);
         return service.getResources();
      }
   }

   public Environment getEnvironment(Service service) {
      return service == null ? null : (Environment)this.locator.getService(Environment.class, service.getEnvironmentRef(), new Annotation[0]);
   }

   public Tenant getTenant(Service service) {
      if (service == null) {
         return null;
      } else {
         XmlHk2ConfigurationBean configBean = (XmlHk2ConfigurationBean)service;
         return (Tenant)configBean._getParent();
      }
   }

   public void addTopLevelDir(Service service, String topLevelDir) {
      service.setTopLevelDir(topLevelDir);
   }

   public void addTwoTask(Service service, String twoTask) {
      service.setTwoTask(twoTask);
   }
}
