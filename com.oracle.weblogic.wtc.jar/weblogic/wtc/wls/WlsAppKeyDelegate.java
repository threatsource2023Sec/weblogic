package weblogic.wtc.wls;

import com.bea.core.jatmi.intf.TCAppKey;
import com.bea.core.jatmi.intf.TCAuthenticatedUser;
import weblogic.wtc.jatmi.AppKey;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.UserRec;

public final class WlsAppKeyDelegate implements TCAppKey {
   private AppKey _app_key;

   public WlsAppKeyDelegate(AppKey akg) {
      this._app_key = akg;
   }

   public void init(String param, boolean anonAllowed, int defaultAppKey) throws TPException {
      if (this._app_key != null) {
         this._app_key.init(param, anonAllowed, defaultAppKey);
      }

   }

   public void uninit() throws TPException {
      if (this._app_key != null) {
         this._app_key.uninit();
      }

   }

   public UserRec getTuxedoUserRecord(TCAuthenticatedUser subj) {
      UserRec ur = null;
      if (this._app_key != null) {
         WlsAuthenticatedUser prin = (WlsAuthenticatedUser)subj;
         ur = this._app_key.getTuxedoUserRecord(prin.getWlsSubject());
      }

      return ur;
   }

   public void doCache(boolean cached) {
   }

   public boolean isCached() {
      return false;
   }
}
