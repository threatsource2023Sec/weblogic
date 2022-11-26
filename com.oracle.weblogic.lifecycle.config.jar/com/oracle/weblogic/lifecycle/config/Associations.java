package com.oracle.weblogic.lifecycle.config;

import java.util.List;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
public interface Associations {
   @XmlElement(
      name = "association"
   )
   @Valid List getAssociations();

   void setAssociations(List var1);

   Association addAssociation(Association var1);

   Association removeAssociation(Association var1);
}
