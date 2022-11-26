package weblogic.wtc.gwt;

import com.bea.core.jatmi.common.ntrace;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import weblogic.wtc.jatmi.Conversation;
import weblogic.wtc.jatmi.Reply;
import weblogic.wtc.jatmi.TPException;
import weblogic.wtc.jatmi.TPReplyException;
import weblogic.wtc.jatmi.TypedBuffer;

public class TuxedoConversation implements Conversation {
   private TuxedoConnection myConnection;
   private Conversation myInternalConversation;
   private Transaction myTransaction;
   private int tpNotran;

   public TuxedoConversation(TuxedoConnection connection, Conversation internalConversation, Transaction associatedTransaction, int tpNotran) {
      this.myConnection = connection;
      this.myInternalConversation = internalConversation;
      this.myTransaction = associatedTransaction;
      this.tpNotran = tpNotran;
   }

   public void tpsend(TypedBuffer data, int flags) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoConversation/tpsend/" + flags);
      }

      if (this.myConnection == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConversation/tpsend/5/");
         }

         throw new TPException(9, "Missing valid connection");
      } else if (this.myConnection.isTerminated()) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConversation/tpsend/10/");
         }

         throw new TPException(9, "Tuxedo session terminated");
      } else if ((flags & -4130) == 0) {
         if (this.myTransaction != null && this.myConnection.getRollbackOnly()) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TuxedoConversation/tpsend/30/TPEPROTO");
            }

            throw new TPException(9, "Transaction rolled back, no send attempted");
         } else {
            try {
               this.myInternalConversation.tpsend(data, flags);
            } catch (TPException var7) {
               if (this.myTransaction != null && this.tpNotran == 0) {
                  try {
                     this.myTransaction.setRollbackOnly();
                  } catch (SystemException var6) {
                     if (traceEnabled) {
                        ntrace.doTrace("/TuxedoConversation/tpsend/SystemException:" + var6);
                     }
                  }
               }

               if (traceEnabled) {
                  ntrace.doTrace("*]/TuxedoConversation/tpsend/40/" + var7);
               }

               throw var7;
            }

            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoConversation/tpsend/50/");
            }

         }
      } else {
         if (this.myTransaction != null && this.tpNotran == 0) {
            try {
               this.myTransaction.setRollbackOnly();
            } catch (SystemException var8) {
               if (traceEnabled) {
                  ntrace.doTrace("/TuxedoConversation/tpsend/SystemException:" + var8);
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConversation/tpsend/20/TPEINVAL");
         }

         throw new TPException(4);
      }
   }

   public Reply tprecv(int flags) throws TPReplyException, TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoConversation/tprecv/" + flags);
      }

      if (this.myConnection == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConversation/tprecv/5/");
         }

         throw new TPException(9, "Missing valid connection");
      } else if (this.myConnection.isTerminated()) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConversation/tprecv/10/");
         }

         throw new TPException(9, "Tuxedo session terminated");
      } else if ((flags & -34) == 0) {
         if (this.myTransaction != null && this.myConnection.getRollbackOnly()) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TuxedoConversation/tprecv/30/TPEPROTO");
            }

            throw new TPException(9, "Transaction rolled back, no receive attempted");
         } else {
            Reply ret;
            try {
               ret = this.myInternalConversation.tprecv(flags);
            } catch (TPReplyException var10) {
               int errno = var10.gettperrno();
               int treevent = var10.getrevent();
               if (errno == 22 && treevent == 8) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConversation/tprecv/40/" + var10);
                  }

                  throw var10;
               }

               if (errno == 22 && treevent == 32) {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/TuxedoConversation/tprecv/45/" + var10);
                  }

                  throw var10;
               }

               if (this.myTransaction != null && this.tpNotran == 0) {
                  try {
                     this.myTransaction.setRollbackOnly();
                  } catch (SystemException var8) {
                     if (traceEnabled) {
                        ntrace.doTrace("/TuxedoConversation/tprecv/SystemException:" + var8);
                     }
                  }
               }

               if (traceEnabled) {
                  ntrace.doTrace("*]/TuxedoConversation/tprecv/50/" + var10);
               }

               throw var10;
            } catch (TPException var11) {
               if (this.myTransaction != null && this.tpNotran == 0) {
                  try {
                     this.myTransaction.setRollbackOnly();
                  } catch (SystemException var9) {
                     if (traceEnabled) {
                        ntrace.doTrace("/TuxedoConversation/tprecv/SystemException:" + var9);
                     }
                  }
               }

               if (traceEnabled) {
                  ntrace.doTrace("*]/TuxedoConversation/tprecv/60/" + var11);
               }

               throw var11;
            }

            if (traceEnabled) {
               ntrace.doTrace("]/TuxedoConversation/tprecv/70/" + ret);
            }

            return ret;
         }
      } else {
         if (this.myTransaction != null && this.tpNotran == 0) {
            try {
               this.myTransaction.setRollbackOnly();
            } catch (SystemException var12) {
               if (traceEnabled) {
                  ntrace.doTrace("/TuxedoConversation/tpsend/SystemException:" + var12);
               }
            }
         }

         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConversation/tprecv/20/TPEINVAL");
         }

         throw new TPException(4);
      }
   }

   public void tpdiscon() throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(2);
      if (traceEnabled) {
         ntrace.doTrace("[/TuxedoConversation/tpdiscon/");
      }

      if (this.myConnection.isTerminated()) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TuxedoConversation/tpdiscon/10/");
         }

         throw new TPException(9, "Tuxedo session terminated");
      } else {
         if (this.myTransaction != null && this.tpNotran == 0) {
            try {
               this.myTransaction.setRollbackOnly();
            } catch (SystemException var4) {
               if (traceEnabled) {
                  ntrace.doTrace("/TuxedoConversation/tprecv/SystemException:" + var4);
               }
            }
         }

         try {
            this.myInternalConversation.tpdiscon();
         } catch (TPException var3) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TuxedoConversation/tpdiscon/20/" + var3);
            }

            throw var3;
         }

         if (traceEnabled) {
            ntrace.doTrace("]/TuxedoConversation/tpdiscon/30/");
         }

      }
   }
}
