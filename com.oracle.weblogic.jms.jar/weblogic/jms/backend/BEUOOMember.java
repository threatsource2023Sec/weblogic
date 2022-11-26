package weblogic.jms.backend;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.CompletionRequest;
import weblogic.jms.JMSService;
import weblogic.jms.common.DistributedDestinationImpl;
import weblogic.jms.dd.DDManager;
import weblogic.management.ManagementException;
import weblogic.messaging.dispatcher.DispatcherException;
import weblogic.messaging.path.Key;
import weblogic.messaging.path.LegalMember;
import weblogic.messaging.path.UpdatableMember;
import weblogic.messaging.path.helper.KeyString;
import weblogic.messaging.path.helper.MemberString;
import weblogic.messaging.saf.utils.SAFClientUtil;
import weblogic.store.PersistentStoreTransaction;

public class BEUOOMember extends MemberString implements LegalMember, UpdatableMember {
   static final long serialVersionUID = -1978058611468713508L;
   private static int EXTVERSION = 1;
   private static int VERSION_MASK = 4095;
   private static int FLAG_DYNAMIC = 4096;
   protected boolean dynamic;

   public BEUOOMember(String memberId, String server, boolean dynamic) {
      super(memberId, server);
      this.dynamic = dynamic;
   }

   public BEUOOMember() {
   }

   public boolean getDynamic() {
      return this.dynamic;
   }

   public void setTimestamp(long ts) {
      this.timestamp = ts;
   }

   public void setGeneration(int gen) {
      this.generation = gen;
   }

   public void update(Key rawKey, UpdatableMember rawPrevious, CompletionRequest completionRequest) {
      BEOrderUpdateParentRequest orderUpdateParent;
      DistributedDestinationImpl memberDest;
      BEOrderUpdateRequest orderUpdate;
      try {
         KeyString keyString = (KeyString)rawKey;
         BEUOOMember previousMember = (BEUOOMember)rawPrevious;
         if (!DDManager.isMember(rawKey.getAssemblyId(), this.getStringId())) {
            completionRequest.setResult(new Exception("member " + this.getMemberId() + " unavailable within " + keyString.getAssemblyId()));
            return;
         }

         memberDest = DDManager.findDDImplByMemberName(this.getStringId());
         orderUpdate = new BEOrderUpdateRequest(memberDest.getId(), keyString, previousMember, this);
         orderUpdateParent = new BEOrderUpdateParentRequest(memberDest.getId(), orderUpdate, completionRequest);
      } catch (Error var20) {
         completionRequest.setResult(var20);
         throw var20;
      } catch (RuntimeException var21) {
         completionRequest.setResult(var21);
         throw var21;
      }

      try {
         JMSService jmsService = JMSService.getJMSServiceWithManagementException();
         orderUpdateParent.dispatchAsync(jmsService.dispatcherFindOrCreate(memberDest.getDispatcherId()), orderUpdate);
      } catch (Error var17) {
         synchronized(orderUpdateParent) {
            if (orderUpdateParent.getState() == Integer.MAX_VALUE) {
               return;
            }

            orderUpdateParent.setState(Integer.MAX_VALUE);
         }

         completionRequest.setResult(var17);
         throw var17;
      } catch (ManagementException | DispatcherException var18) {
         synchronized(orderUpdateParent) {
            if (orderUpdateParent.getState() == Integer.MAX_VALUE) {
               return;
            }

            orderUpdateParent.setState(Integer.MAX_VALUE);
         }

         completionRequest.setResult(var18);
      } catch (RuntimeException var19) {
         synchronized(orderUpdateParent) {
            if (orderUpdateParent.getState() == Integer.MAX_VALUE) {
               return;
            }

            orderUpdateParent.setState(Integer.MAX_VALUE);
         }

         completionRequest.setResult(var19);
         throw var19;
      }

   }

   public void isLegal(Key rawKey, LegalMember rawPrevious, CompletionRequest completionRequest) {
      Object result = null;
      RuntimeException runtimeException = null;
      Error error = null;

      try {
         completionRequest.runListenersInSetResult(true);
         if (this.dynamic) {
            if (DDManager.isMember(rawKey.getAssemblyId(), this.getStringId())) {
               result = Boolean.TRUE;
            } else {
               result = Boolean.FALSE;
            }

            return;
         }

         result = Boolean.TRUE;
      } catch (RuntimeException var12) {
         runtimeException = var12;
         result = var12;
         return;
      } catch (Error var13) {
         error = var13;
         result = var13;
         return;
      } finally {
         completionRequest.setResult(result);
         if (runtimeException != null) {
            throw runtimeException;
         }

         if (error != null) {
            throw error;
         }

      }

   }

   public boolean updateException(Throwable t, Key key, UpdatableMember member, PersistentStoreTransaction ptx, CompletionRequest cr) {
      cr.setResult(Boolean.FALSE);
      return false;
   }

   public boolean equals(Object o) {
      return this == o || o instanceof BEUOOMember && this.dynamic == ((BEUOOMember)o).dynamic && super.equals(o);
   }

   public int compareTo(Object arg) {
      int c = super.compareTo(arg);
      if (c != 0) {
         return c;
      } else if (this.dynamic == ((BEUOOMember)arg).dynamic) {
         return 0;
      } else {
         return this.dynamic ? 1 : -1;
      }
   }

   public String toString() {
      return this.dynamic ? super.toString() + "^dynamic" : super.toString();
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int flags = this.dynamic ? FLAG_DYNAMIC | EXTVERSION : EXTVERSION;
      out.writeInt(flags);
      super.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int flags = in.readInt();
      int version = flags & VERSION_MASK;
      if (version != EXTVERSION) {
         throw SAFClientUtil.versionIOException(version, EXTVERSION, EXTVERSION);
      } else {
         this.dynamic = (flags & FLAG_DYNAMIC) != 0;
         super.readExternal(in);
      }
   }
}
