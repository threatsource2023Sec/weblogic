package weblogic.j2ee.descriptor.wl;

import weblogic.descriptor.SettableBean;

public interface NamedEntityBean extends SettableBean {
   String getNotes();

   void setNotes(String var1);

   String getName();

   void setName(String var1);

   long getId();
}
