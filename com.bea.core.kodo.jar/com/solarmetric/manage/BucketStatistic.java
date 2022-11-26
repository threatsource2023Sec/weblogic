package com.solarmetric.manage;

public class BucketStatistic extends SimpleStatistic {
   public static final int TYPE_SAMPLES = 0;
   public static final int TYPE_TIME = 1;
   public static final int MODE_MIN = 0;
   public static final int MODE_MAX = 1;
   public static final int MODE_TIMEAVE = 2;
   public static final int MODE_SAMPLEAVE = 3;
   public static final int MODE_SAMPLESUM = 4;
   public static final int MODE_COUNT = 5;
   private double _bucketValue;
   private double _curValue;
   private long _startTime;
   private long _lastSetTime;
   private int _numSamples;
   private int _bucketType;
   private int _bucketMode;
   private long _numTypeUnits;

   public BucketStatistic(String name, String description, String ordinateDescription, int bucketType, int bucketMode, long numTypeUnits) {
      this(name, description, ordinateDescription, 0, bucketType, bucketMode, numTypeUnits);
   }

   public BucketStatistic(String name, String description, String ordinateDescription, int drawStyle, int bucketType, int bucketMode, long numTypeUnits) {
      super(name, description, ordinateDescription, drawStyle, false);
      this._bucketValue = Double.NaN;
      this._curValue = Double.NaN;
      this._startTime = System.currentTimeMillis();
      this._lastSetTime = System.currentTimeMillis();
      this._numSamples = 0;
      this._bucketType = bucketType;
      this._bucketMode = bucketMode;
      this._numTypeUnits = numTypeUnits;
      this._numSamples = 0;
      this._startTime = System.currentTimeMillis();
      this._bucketValue = Double.NaN;
      this._lastSetTime = System.currentTimeMillis();
   }

   void setStartTime(long time) {
      this._startTime = time;
      this._lastSetTime = time;
   }

   public double getValue() {
      return this._curValue;
   }

   private void updateBucketVal(long time, double value) {
      switch (this._bucketMode) {
         case 0:
            if (Double.isNaN(this._bucketValue) || this._bucketValue > value) {
               this._bucketValue = value;
            }
            break;
         case 1:
            if (Double.isNaN(this._bucketValue) || this._bucketValue < value) {
               this._bucketValue = value;
            }
            break;
         case 2:
            if (Double.isNaN(this._bucketValue)) {
               this._bucketValue = 0.0;
            }

            if (Double.isNaN(this._curValue)) {
               this._startTime += time - this._lastSetTime;
            } else {
               this._bucketValue += (double)(time - this._lastSetTime) * this._curValue;
            }

            this._lastSetTime = time;
            break;
         case 3:
            if (Double.isNaN(this._bucketValue)) {
               this._bucketValue = 0.0;
            }

            this._bucketValue += value;
            break;
         case 4:
            if (Double.isNaN(this._bucketValue)) {
               this._bucketValue = 0.0;
            }

            this._bucketValue += value;
            break;
         case 5:
            if (Double.isNaN(this._bucketValue)) {
               this._bucketValue = 0.0;
            }

            ++this._bucketValue;
      }

      ++this._numSamples;
   }

   public void setValue(long time, double value) {
      if (this._bucketType == 0) {
         this.updateBucketVal(time, value);
         if ((long)this._numSamples >= this._numTypeUnits) {
            this.writeAndReset(time, value);
         }
      } else {
         while(true) {
            if (time < this._startTime + this._numTypeUnits) {
               this.updateBucketVal(time, value);
               break;
            }

            this.writeAndReset(this._startTime + this._numTypeUnits, this._curValue);
         }
      }

      this._curValue = value;
   }

   private void writeAndReset(long time, double value) {
      if (!Double.isNaN(this._bucketValue)) {
         switch (this._bucketMode) {
            case 0:
            case 1:
            case 4:
            case 5:
               super.setValue(time, this._bucketValue);
               break;
            case 2:
               if (time > this._lastSetTime) {
                  if (Double.isNaN(this._bucketValue)) {
                     this._bucketValue = 0.0;
                  }

                  if (Double.isNaN(this._curValue)) {
                     this._startTime += time - this._lastSetTime;
                  } else {
                     this._bucketValue += (double)(time - this._lastSetTime) * this._curValue;
                  }

                  this._lastSetTime = time;
               }

               double timediff = (double)(time - this._startTime);
               if (timediff == 0.0) {
                  super.setValue(time, value);
               } else {
                  super.setValue(time, this._bucketValue / timediff);
               }
               break;
            case 3:
               super.setValue(time, this._bucketValue / (double)this._numSamples);
         }
      }

      this._numSamples = 0;
      this._startTime = time;
      this._bucketValue = Double.NaN;
   }

   public void setValue(double value) {
      long curtime = System.currentTimeMillis();
      this.setValue(curtime, value);
   }
}
