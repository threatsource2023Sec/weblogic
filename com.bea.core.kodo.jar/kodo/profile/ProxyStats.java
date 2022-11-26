package kodo.profile;

import java.io.Serializable;

public class ProxyStats implements Serializable {
   private String _description;
   private int _samples = 0;
   private int _containsCalled = 0;
   private int _sizeCalled = 0;
   private int _indexOfCalled = 0;
   private int _clearCalled = 0;
   private int _retainCalled = 0;
   private int _addCalled = 0;
   private int _setCalled = 0;
   private int _removeCalled = 0;
   private int _sizeSamples = 0;
   private int _size = 0;
   private int _accessed = 0;
   private int _accessedKnownSize = 0;

   public ProxyStats(String description) {
      this._description = description;
   }

   public synchronized void record(ProxyUpdateInfo info) {
      if (info.getIncrementSamples()) {
         ++this._samples;
      }

      this._accessedKnownSize += info.getAddToAccessedKnownSize();
      this._size += info.getAddToSize();
      switch (info.getEventType()) {
         case 0:
            ++this._containsCalled;
            break;
         case 1:
            ++this._sizeCalled;
            break;
         case 2:
            ++this._indexOfCalled;
            break;
         case 3:
            ++this._clearCalled;
            break;
         case 4:
            ++this._retainCalled;
            break;
         case 5:
            this._addCalled += info.getCount();
            break;
         case 6:
            this._setCalled += info.getCount();
            break;
         case 7:
            this._removeCalled += info.getCount();
            break;
         case 8:
            this._accessed += info.getCount();
            break;
         case 9:
            ++this._sizeSamples;
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

   public int getClearCalled() {
      return this._clearCalled;
   }

   public int getRetainCalled() {
      return this._retainCalled;
   }

   public int getAddCalled() {
      return this._addCalled;
   }

   public int getSetCalled() {
      return this._setCalled;
   }

   public int getRemoveCalled() {
      return this._removeCalled;
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
