package weblogic.management.provider.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.provider.ClientAccess;

public class ClientAccessImpl implements ClientAccess {
   ClientAccessImpl() {
   }

   public DomainMBean getDomain(boolean editable) throws IOException, XMLStreamException {
      return this.getDomain("config/config.xml", editable);
   }

   public DomainMBean getDomain(String fileName, boolean editable) throws IOException, XMLStreamException {
      return (DomainMBean)DescriptorManagerHelper.loadDescriptor(fileName, editable, false, (List)null).getRootBean();
   }

   public void saveDomain(DomainMBean domain, OutputStream output) throws IOException {
      AbstractDescriptorBean descriptorBean = (AbstractDescriptorBean)domain;
      DescriptorManagerHelper.saveDescriptor(descriptorBean.getDescriptor(), output);
   }

   public void saveDomainDirectory(DomainMBean domain, String domainConfigDirectory) throws IOException {
      AbstractDescriptorBean descriptorBean = (AbstractDescriptorBean)domain;
      DescriptorHelper.saveDescriptorTree(descriptorBean.getDescriptor(), false, domainConfigDirectory, "UTF-8");
   }
}
