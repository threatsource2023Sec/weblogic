package weblogic.xml.registry;

import java.beans.PropertyChangeListener;
import java.io.Serializable;

public abstract class XMLAbstractRegistryEntry implements Serializable {
   private String publicId = null;
   private String systemId = null;
   private PropertyChangeListener listener;
   private ConfigAbstraction.EntryConfig mbean = null;
   private boolean isPrivate = false;

   public XMLAbstractRegistryEntry(String pubId, String sysId, ConfigAbstraction.EntryConfig mbean) {
      this.publicId = pubId;
      this.systemId = sysId;
      this.mbean = mbean;
   }

   public String getPublicId() {
      return this.publicId;
   }

   public String getSystemId() {
      return this.systemId;
   }

   public String getRootElementTag() {
      return null;
   }

   public PropertyChangeListener getListener() {
      return this.listener;
   }

   public ConfigAbstraction.EntryConfig getMBean() {
      return this.mbean;
   }

   public void setPrivate(boolean val) {
      this.isPrivate = val;
   }

   public void setListener(PropertyChangeListener listener) {
      this.listener = listener;
   }

   public boolean isPrivate() {
      return this.isPrivate;
   }
}
