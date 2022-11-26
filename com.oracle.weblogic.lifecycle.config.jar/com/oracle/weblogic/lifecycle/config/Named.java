package com.oracle.weblogic.lifecycle.config;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlID;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
public interface Named {
   @XmlAttribute(
      required = true
   )
   @XmlID
   @NotNull String getName();

   void setName(String var1);
}
