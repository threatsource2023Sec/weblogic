package weblogic.management.configuration;

import javax.management.InvalidAttributeValueException;
import weblogic.diagnostics.descriptor.WLDFResourceBean;

public interface WLDFSystemResourceMBean extends SystemResourceMBean {
   String getName();

   WLDFResourceBean getWLDFResource();

   String getDescriptorFileName();

   String getDescription();

   void setDescription(String var1) throws InvalidAttributeValueException;
}
