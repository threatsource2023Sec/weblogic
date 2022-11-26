package com.solarmetric.remote;

import java.io.ObjectInput;
import java.io.ObjectOutput;

public abstract class Command {
   private long _id = -1L;
   private boolean _response = true;

   public void setClientId(long id) {
      this._id = id;
   }

   public long getClientId() {
      return this._id;
   }

   public boolean hasResponse() {
      return this._response;
   }

   public void setHasResponse(boolean response) {
      this._response = response;
   }

   public abstract void execute(Object var1) throws Exception;

   protected void read(ObjectInput in) throws Exception {
   }

   protected void write(ObjectOutput out) throws Exception {
   }

   protected void readResponse(ObjectInput in) throws Exception {
   }

   protected void writeResponse(ObjectOutput out) throws Exception {
   }

   public String toString() {
      return super.toString() + " [client: " + this._id + "]";
   }
}
