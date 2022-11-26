package kodo.profile;

import com.solarmetric.profile.EventInfo;

public class ProxyUpdateInfo implements EventInfo {
   private static final long serialVersionUID = 1L;
   public static final int TYPE_INCREMENT_CONTAINS_CALLED = 0;
   public static final int TYPE_INCREMENT_SIZE_CALLED = 1;
   public static final int TYPE_INCREMENT_INDEXOF_CALLED = 2;
   public static final int TYPE_INCREMENT_CLEAR_CALLED = 3;
   public static final int TYPE_INCREMENT_RETAIN_CALLED = 4;
   public static final int TYPE_INCREMENT_ADD = 5;
   public static final int TYPE_INCREMENT_SET = 6;
   public static final int TYPE_INCREMENT_REMOVE = 7;
   public static final int TYPE_INCREMENT_ACCESS = 8;
   public static final int TYPE_INCREMENT_SIZE_RECORD = 9;
   private String _fullFieldName;
   private boolean _incrementSamples;
   private int _eventType;
   private int _count;
   private int _addToAccessedKnownSize;
   private int _addToSize;
   private String _category;

   public ProxyUpdateInfo(String fullFieldName, boolean incrementSamples, int type, int count, int addToSize, int addToAccessedKnownSize) {
      this._fullFieldName = fullFieldName;
      this._incrementSamples = incrementSamples;
      this._eventType = type;
      this._count = count;
      this._addToSize = addToSize;
      this._addToAccessedKnownSize = addToAccessedKnownSize;
   }

   public String getName() {
      return "ProxyUpdate";
   }

   public String getDescription() {
      return "Field: " + this._fullFieldName;
   }

   public String getCategory() {
      return this._category;
   }

   public void setCategory(String catName) {
      this._category = catName;
   }

   public String getFullFieldName() {
      return this._fullFieldName;
   }

   public int getAddToSize() {
      return this._addToSize;
   }

   public int getAddToAccessedKnownSize() {
      return this._addToAccessedKnownSize;
   }

   public int getCount() {
      return this._count;
   }

   public int getEventType() {
      return this._eventType;
   }

   public boolean getIncrementSamples() {
      return this._incrementSamples;
   }

   public String toString() {
      return this._fullFieldName;
   }

   public String getViewerClassName() {
      return null;
   }
}
