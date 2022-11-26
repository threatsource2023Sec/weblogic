package weblogic.iiop.contexts;

import weblogic.iiop.protocol.CorbaInputStream;
import weblogic.iiop.protocol.CorbaOutputStream;

public final class ServiceContextList {
   private static final int BASE_LIST_SIZE = 4;
   private static final ServiceContext NULL_CONTEXT = new ServiceContext(-1);
   private long omgBitmask = 0L;
   private long beaBitmask = 0L;
   private int beaElems = 0;
   private int omgElems = 0;
   private int foreignElems = 0;
   private int size = 0;
   private ServiceContext[] beaContexts = new ServiceContext[4];
   private ServiceContext[] omgContexts = new ServiceContext[4];
   private ServiceContext[] foreignContexts;

   public void write(CorbaOutputStream os) {
      os.write_ulong(this.size);

      int i;
      for(i = 0; i < this.omgElems; ++i) {
         if (this.omgContexts[i] != NULL_CONTEXT) {
            this.omgContexts[i].write(os);
         }
      }

      for(i = 0; i < this.beaElems; ++i) {
         if (this.beaContexts[i] != NULL_CONTEXT) {
            this.beaContexts[i].write(os);
         }
      }

      for(i = 0; i < this.foreignElems; ++i) {
         if (this.foreignContexts[i] != NULL_CONTEXT) {
            this.foreignContexts[i].write(os);
         }
      }

   }

   public final void read(CorbaInputStream is) {
      int sz = is.read_ulong();

      for(int i = 0; i < sz; ++i) {
         ServiceContext sc = ServiceContext.readServiceContext(is);
         this.addServiceContext(sc);
      }

   }

   public final ServiceContext getServiceContext(int ctxid) {
      if (this.size == 0) {
         return null;
      } else {
         int i;
         if (ctxid < 64) {
            if (this.omgElems == 0 || (this.omgBitmask & (long)(1 << ctxid)) == 0L) {
               return null;
            }

            for(i = 0; i < this.omgElems; ++i) {
               if (this.omgContexts[i].getContextId() == ctxid) {
                  return this.omgContexts[i];
               }
            }
         } else if (ctxid >= 1111834880 && ctxid < 1111834944) {
            if (this.beaElems == 0 || (this.beaBitmask & (long)(1 << ctxid - 1111834880)) == 0L) {
               return null;
            }

            for(i = 0; i < this.beaElems; ++i) {
               if (this.beaContexts[i].getContextId() == ctxid) {
                  return this.beaContexts[i];
               }
            }
         } else {
            if (this.foreignElems == 0) {
               return null;
            }

            for(i = 0; i < this.foreignElems; ++i) {
               if (this.foreignContexts[i].getContextId() == ctxid) {
                  return this.foreignContexts[i];
               }
            }
         }

         return null;
      }
   }

   public final void removeServiceContext(int ctxid) {
      int i;
      if (ctxid < 64) {
         if (this.omgElems != 0 && (this.omgBitmask & (long)(1 << ctxid)) != 0L) {
            for(i = 0; i < this.omgElems; ++i) {
               if (this.omgContexts[i].getContextId() == ctxid) {
                  this.omgContexts[i] = NULL_CONTEXT;
                  --this.size;
               }
            }
         }
      } else if (ctxid >= 1111834880 && ctxid < 1111834944) {
         if (this.beaElems != 0 && (this.beaBitmask & (long)(1 << ctxid - 1111834880)) != 0L) {
            for(i = 0; i < this.beaElems; ++i) {
               if (this.beaContexts[i].getContextId() == ctxid) {
                  this.beaContexts[i] = NULL_CONTEXT;
                  --this.size;
               }
            }
         }
      } else {
         if (this.foreignElems == 0) {
            return;
         }

         for(i = 0; i < this.foreignElems; ++i) {
            if (this.foreignContexts[i].getContextId() == ctxid) {
               this.foreignContexts[i] = NULL_CONTEXT;
               --this.size;
            }
         }
      }

   }

   public final void addServiceContext(ServiceContext sc) {
      int ctxid = sc.getContextId();
      ++this.size;
      if (ctxid < 64) {
         if (this.omgElems == this.omgContexts.length) {
            this.omgContexts = this.growList(this.omgContexts, this.omgElems);
         }

         this.omgBitmask |= (long)(1 << ctxid);
         this.omgContexts[this.omgElems++] = sc;
      } else if (ctxid >= 1111834880 && ctxid < 1111834944) {
         if (this.beaElems == this.beaContexts.length) {
            this.beaContexts = this.growList(this.beaContexts, this.beaElems);
         }

         this.beaBitmask |= (long)(1 << ctxid - 1111834880);
         this.beaContexts[this.beaElems++] = sc;
      } else {
         if (this.foreignContexts == null) {
            this.foreignContexts = new ServiceContext[4];
         } else if (this.foreignElems == this.foreignContexts.length) {
            this.foreignContexts = this.growList(this.foreignContexts, this.foreignElems);
         }

         this.foreignContexts[this.foreignElems++] = sc;
      }

   }

   private ServiceContext[] growList(ServiceContext[] curr, int elems) {
      ServiceContext[] newList = new ServiceContext[curr.length * 2];
      System.arraycopy(curr, 0, newList, 0, elems);
      return newList;
   }

   protected static void p(String s) {
      System.err.println("<ServiceContextList> " + s);
   }

   public final void reset() {
      this.beaElems = 0;
      this.omgElems = 0;
      this.foreignElems = 0;
      this.size = 0;
      this.omgBitmask = 0L;
      this.beaBitmask = 0L;
   }

   public String toString() {
      return "ServiceContextList:" + this.toListString();
   }

   public String toListString() {
      StringBuilder sb = new StringBuilder("[ ");

      int i;
      for(i = 0; i < this.omgElems; ++i) {
         sb.append(this.omgContexts[i]).append(' ');
      }

      for(i = 0; i < this.beaElems; ++i) {
         sb.append(this.beaContexts[i]).append(' ');
      }

      sb.append(']');
      return sb.toString();
   }
}
