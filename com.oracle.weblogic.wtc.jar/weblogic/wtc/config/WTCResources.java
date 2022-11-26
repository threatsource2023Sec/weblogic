package weblogic.wtc.config;

import com.bea.core.jatmi.config.TuxedoConnectorResources;
import weblogic.wtc.gwt.WTCService;

public class WTCResources extends ConfigObjectBase implements TuxedoConnectorResources {
   private String _name;
   private String[] _fldtbl16;
   private String[] _fldtbl32;
   private String[] _viewtbl16;
   private String[] _viewtbl32;
   private String _pwd;
   private String _tpusrfile;
   private String _encoding;
   private String _mapfile;

   public WTCResources() {
      this.ctype = 0;
      this.mtype = 0;
      this._name = WTCService.getWTCServerName();
   }

   public void setTuxedoConnectorName(String name) {
   }

   public String getTuxedoConnectorName() {
      return this._name;
   }

   public void setFldTbl16Classes(String[] fldtbl) {
      this._fldtbl16 = fldtbl;
   }

   public String[] getFldTbl16Classes() {
      return this._fldtbl16;
   }

   public void setFldTbl32Classes(String[] fldtbl) {
      this._fldtbl32 = fldtbl;
   }

   public String[] getFldTbl32Classes() {
      return this._fldtbl32;
   }

   public void setViewTbl16Classes(String[] vtbl) {
      this._viewtbl16 = vtbl;
   }

   public String[] getViewTbl16Classes() {
      return this._viewtbl16;
   }

   public void setViewTbl32Classes(String[] vtbl) {
      this._viewtbl32 = vtbl;
   }

   public String[] getViewTbl32Classes() {
      return this._viewtbl32;
   }

   public void setAppPassword(String apwd) {
      this._pwd = apwd;
   }

   public String getAppPassword() {
      return this._pwd;
   }

   public void setTpUsrFile(String fname) {
      this._tpusrfile = fname;
   }

   public String getTpUsrFile() {
      return this._tpusrfile;
   }

   public void setRemoteMBEncoding(String enc) {
      this._encoding = enc;
   }

   public String getRemoteMBEncoding() {
      return this._encoding;
   }

   public void setMBEncodingMapFile(String fname) {
      this._mapfile = fname;
   }

   public String getMBEncodingMapFile() {
      return this._mapfile;
   }

   public void copyResources(WTCResources copyTo) {
      copyTo.setFldTbl16Classes(this._fldtbl16);
      copyTo.setFldTbl32Classes(this._fldtbl32);
      copyTo.setViewTbl16Classes(this._viewtbl16);
      copyTo.setViewTbl32Classes(this._viewtbl32);
      copyTo.setAppPassword(this._pwd);
      copyTo.setTpUsrFile(this._tpusrfile);
      copyTo.setRemoteMBEncoding(this._encoding);
      copyTo.setMBEncodingMapFile(this._mapfile);
      copyTo.setConfigSource(this.configSource);
      this.configSource.changeConfigObj(this, copyTo);
   }
}
