package org.apache.openjpa.xmlstore;

import org.apache.openjpa.conf.OpenJPAConfigurationImpl;
import org.apache.openjpa.lib.conf.ProductDerivations;

public class XMLConfiguration extends OpenJPAConfigurationImpl {
   private XMLStore _store;
   private XMLFileHandler _handler;

   public XMLConfiguration() {
      super(false, false);
      this.lockManagerPlugin.setDefault("version");
      this.lockManagerPlugin.setString("version");
      ProductDerivations.beforeConfigurationLoad(this);
      this.loadGlobals();
   }

   public synchronized XMLFileHandler getFileHandler() {
      if (this._handler == null) {
         this._handler = new XMLFileHandler(this);
      }

      return this._handler;
   }

   public synchronized XMLStore getStore() {
      if (this._store == null) {
         this._store = new XMLStore(this);
      }

      return this._store;
   }
}
