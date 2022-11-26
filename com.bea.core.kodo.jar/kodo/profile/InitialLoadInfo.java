package kodo.profile;

import com.solarmetric.profile.EventInfo;
import java.util.BitSet;
import org.apache.openjpa.kernel.FetchConfiguration;
import org.apache.openjpa.kernel.OpenJPAStateManager;

public class InitialLoadInfo implements EventInfo {
   private static final long serialVersionUID = 1L;
   private String _className;
   private String _category;
   private BitSet _loaded;

   public InitialLoadInfo(OpenJPAStateManager sm, FetchConfiguration fetch) {
      this._className = sm.getMetaData().getDescribedType().getName();
      this._loaded = (BitSet)sm.getLoaded().clone();
   }

   public String getName() {
      return "InitialLoad";
   }

   public String getDescription() {
      return "Initial load of data";
   }

   public String getCategory() {
      return this._category;
   }

   public void setCategory(String catName) {
      this._category = catName;
   }

   public String getClassName() {
      return this._className;
   }

   public BitSet getLoaded() {
      return this._loaded;
   }

   public String toString() {
      return this.getName();
   }

   public String getViewerClassName() {
      return null;
   }
}
