package weblogic.wtc.config;

import com.bea.core.jatmi.config.TuxedoConnectorLAP;
import weblogic.wtc.gwt.TDMLocalTDomain;
import weblogic.wtc.jatmi.TPException;

public class WTCLocalAccessPoint extends ConfigObjectBase implements TuxedoConnectorLAP {
   private String _ap;
   private String _apId;
   private String[] _addr;
   private String _sec;
   private long _blktime;
   private String _interop;
   private String[] _parsedEPG;

   public WTCLocalAccessPoint() {
      this.ctype = 1;
      this.mtype = 1;
   }

   public void setAccessPoint(String name) {
      this._ap = name;
   }

   public String getAccessPoint() {
      return this._ap;
   }

   public void setAccessPointId(String id) {
      this._apId = id;
   }

   public String getAccessPointId() {
      return this._apId;
   }

   public void setNetworkAddr(String[] addr) throws TPException {
      this._addr = addr;
   }

   public String[] getNetworkAddr() {
      return this._addr;
   }

   public void setSecurity(String sec) {
      this._sec = sec;
   }

   public String getSecurity() {
      return this._sec;
   }

   public void setBlockTime(long bt) {
      this._blktime = bt;
   }

   public long getBlockTime() {
      return this._blktime;
   }

   public void setInteroperate(String interop) {
      this._interop = interop;
   }

   public String getInteroperate() {
      return this._interop;
   }

   public void setEndPointGroup(String[] parsedEPG) {
      this._parsedEPG = parsedEPG;
   }

   public String[] getEndPointGroup() {
      return this._parsedEPG;
   }

   public void fillFromSource(TDMLocalTDomain lap) {
      this._ap = lap.getAccessPoint();
      this._apId = lap.getAccessPointId();
      this._sec = lap.getSecurity();
      this._blktime = lap.getBlockTime();
      this._interop = lap.getInteroperate();
      this.configSource = lap;
   }
}
