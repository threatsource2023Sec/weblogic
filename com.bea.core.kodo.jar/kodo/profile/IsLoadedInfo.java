package kodo.profile;

import com.solarmetric.profile.EventInfo;
import org.apache.openjpa.meta.ClassMetaData;

public class IsLoadedInfo implements EventInfo {
   private static final long serialVersionUID = 1L;
   private String _className;
   private String _category;
   private int _field;
   private boolean _isLoaded;

   public IsLoadedInfo(ClassMetaData meta, int field, boolean isLoaded) {
      this._className = meta.getDescribedType().getName();
      this._field = field;
      this._isLoaded = isLoaded;
   }

   public String getName() {
      return "IsLoaded";
   }

   public String getDescription() {
      return this._className + " field (" + this._field + ") required load: " + !this._isLoaded;
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

   public int getField() {
      return this._field;
   }

   public boolean getIsLoaded() {
      return this._isLoaded;
   }

   public String toString() {
      return this.getName() + ":" + this._field + ":" + this._isLoaded;
   }

   public String getViewerClassName() {
      return null;
   }
}
