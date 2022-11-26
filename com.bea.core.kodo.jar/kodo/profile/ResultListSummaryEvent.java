package kodo.profile;

import com.solarmetric.profile.ProfilingEnvironment;
import com.solarmetric.profile.ProfilingEvent;

public class ResultListSummaryEvent extends ProfilingEvent {
   private ResultListSummaryInfo _info;

   public ResultListSummaryEvent(ProfilingEnvironment pe, ResultListSummaryInfo info) {
      super(pe);
      this._info = info;
   }

   public ResultListSummaryInfo getResultListSummaryInfo() {
      return this._info;
   }

   public String toString() {
      return "ResultListSummary: " + this._info.toString();
   }
}
