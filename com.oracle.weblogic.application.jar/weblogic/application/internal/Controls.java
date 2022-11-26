package weblogic.application.internal;

public enum Controls {
   main((Controls)null, "weblogic.application.internal.controls", Boolean.TRUE),
   annoscancache(main, "annoscancache", Boolean.TRUE),
   deploymentfactorycache(main, "deploymentfactorycache", Boolean.TRUE),
   paralleltaskmanager(main, "paralleltaskmanager", Boolean.TRUE),
   paralleltaskmanagerparam(paralleltaskmanager, "paralleltaskmanagerparams", ""),
   pojoannotationprocessing(main, "pojoannotationprocessing", Boolean.TRUE),
   annoscanquery(main, "annoscanquery", Boolean.TRUE),
   keepannotatedclassesonly(annoscanquery, "keepannotatedclassesonly", Boolean.TRUE),
   loadannotationclassesonly(annoscanquery, "loadannotationclassesonly", Boolean.TRUE),
   exposedomainlibraryregistry((Controls)null, "weblogic.application.internal.library.LibraryRegistry.ExposeDomainLibrary", Boolean.FALSE);

   private final String propertyName;
   public final boolean enabled;
   public final boolean disabled;
   public final String strValue;

   private Controls(Controls parent, String name, Boolean defaultValue) {
      if (parent == null) {
         this.propertyName = name;
      } else {
         this.propertyName = parent.propertyName + '.' + name;
      }

      this.enabled = Boolean.valueOf(System.getProperty(this.propertyName, defaultValue.toString()));
      this.disabled = !this.enabled;
      this.strValue = null;
   }

   private Controls(Controls parent, String name, String defaultValue) {
      if (parent == null) {
         this.propertyName = name;
      } else {
         this.propertyName = parent.propertyName + '.' + name;
      }

      this.strValue = System.getProperty(this.propertyName, defaultValue);
      this.enabled = true;
      this.disabled = false;
   }
}
