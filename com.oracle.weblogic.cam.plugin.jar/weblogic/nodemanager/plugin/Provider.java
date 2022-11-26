package weblogic.nodemanager.plugin;

import java.io.File;

public interface Provider {
   File getDomainDirectory();

   NMProcessFactory getNMProcessFactory();

   String getDomainName();
}
