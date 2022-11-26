package kodo.profile;

import com.solarmetric.profile.EventInfo;

public class ResultListSummaryInfo implements EventInfo {
   private static final long serialVersionUID = 1L;
   private String _candidateClassName;
   private String _filter;
   private boolean _containsCalled = false;
   private boolean _sizeCalled = false;
   private boolean _indexOfCalled = false;
   private int _size = -1;
   private int _accessed = 0;
   private String _category;

   public ResultListSummaryInfo(String candidateClassName, String filter, boolean containsCalled, boolean sizeCalled, boolean indexOfCalled, int size, int accessed) {
      this._candidateClassName = candidateClassName;
      this._filter = filter;
      this._containsCalled = containsCalled;
      this._sizeCalled = sizeCalled;
      this._indexOfCalled = indexOfCalled;
      this._size = size;
      this._accessed = accessed;
   }

   public String getName() {
      return "ResultListSummary";
   }

   public String getDescription() {
      return "Class: " + this._candidateClassName + "\nFilter: " + this._filter;
   }

   public String getCategory() {
      return this._category;
   }

   public void setCategory(String catName) {
      this._category = catName;
   }

   public String getCandidateClassName() {
      return this._candidateClassName;
   }

   public String getFilter() {
      return this._filter;
   }

   public int getSize() {
      return this._size;
   }

   public int getAccessed() {
      return this._accessed;
   }

   public boolean getContainsCalled() {
      return this._containsCalled;
   }

   public boolean getSizeCalled() {
      return this._sizeCalled;
   }

   public boolean getIndexOfCalled() {
      return this._indexOfCalled;
   }

   public String toString() {
      return this._candidateClassName + ":" + this._filter;
   }

   public String getViewerClassName() {
      return null;
   }
}
