package com.oracle.weblogic.lifecycle.config;

import com.oracle.weblogic.lifecycle.config.customizers.EditSessionsCustomizer;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import org.glassfish.hk2.api.Customize;
import org.glassfish.hk2.api.Customizer;
import org.glassfish.hk2.xml.api.annotations.Hk2XmlPreGenerate;
import org.jvnet.hk2.annotations.Contract;

@Contract
@Hk2XmlPreGenerate
@Customizer({EditSessionsCustomizer.class})
public interface EditSessions {
   @XmlElement(
      name = "edit-session"
   )
   @Valid List getEditSessions();

   void setEditSessions(List var1);

   EditSession lookupEditSession(String var1);

   EditSession addEditSession(EditSession var1);

   EditSession removeEditSession(EditSession var1);

   @Customize
   EditSession getOrCreateEditSession(String var1);

   @Customize
   EditSession createEditSession(String var1);

   @Customize
   EditSession createEditSession(String var1, Map var2);

   @Customize
   EditSession getEditSessionByName(String var1);

   @Customize
   EditSession deleteEditSession(EditSession var1);
}
