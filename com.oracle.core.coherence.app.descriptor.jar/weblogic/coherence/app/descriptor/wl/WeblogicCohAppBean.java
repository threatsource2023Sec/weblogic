package weblogic.coherence.app.descriptor.wl;

import weblogic.descriptor.SettableBean;
import weblogic.j2ee.descriptor.wl.LibraryRefBean;

public interface WeblogicCohAppBean extends SettableBean {
   LibraryRefBean[] getLibraryRefs();
}
