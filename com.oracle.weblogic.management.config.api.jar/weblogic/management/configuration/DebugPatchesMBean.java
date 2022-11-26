package weblogic.management.configuration;

public interface DebugPatchesMBean extends ConfigurationMBean {
   String DEFAULT_DEBUG_PATCHES_DIRECTORY = "debug_patches";

   String getDebugPatchDirectory();

   void setDebugPatchDirectory(String var1);
}
