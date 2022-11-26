package kodo.profile;

import java.io.Serializable;

public class ResultListStats implements Serializable {
   private String _description;
   private int _samples = 0;
   private int _containsCalled = 0;
   private int _sizeCalled = 0;
   private int _indexOfCalled = 0;
   private int _sizeSamples = 0;
   private int _size = 0;
   private int _accessed = 0;
   private int _accessedKnownSize = 0;

   public ResultListStats(String description) {
      this._description = description;
   }

   public synchronized void record(ResultListSummaryInfo info) {
      ++this._samples;
      if (info.getContainsCalled()) {
         ++this._containsCalled;
      }

      if (info.getSizeCalled()) {
         ++this._sizeCalled;
      }

      if (info.getIndexOfCalled()) {
         ++this._indexOfCalled;
      }

      this._accessed += info.getAccessed();
      if (info.getSize() != -1) {
         ++this._sizeSamples;
         this._size += info.getSize();
         this._accessedKnownSize += info.getAccessed();
      }

   }

   public int getSamples() {
      return this._samples;
   }

   public int getContainsCalled() {
      return this._containsCalled;
   }

   public int getSizeCalled() {
      return this._sizeCalled;
   }

   public int getIndexOfCalled() {
      return this._indexOfCalled;
   }

   public int getSizeSamples() {
      return this._sizeSamples;
   }

   public int getSize() {
      return this._size;
   }

   public int getAccessed() {
      return this._accessed;
   }

   public int getAccessedKnownSize() {
      return this._accessedKnownSize;
   }

   public String toString() {
      return this._description;
   }
}
