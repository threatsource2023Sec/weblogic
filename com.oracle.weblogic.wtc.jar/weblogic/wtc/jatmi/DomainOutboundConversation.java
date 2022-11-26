package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;

public class DomainOutboundConversation implements Conversation {
   private boolean isDisconnected = false;
   private dsession myDomainSession;
   private int mySendSequenceNumber = 1;
   private int myRecvSequenceNumber = 1;
   private int myConversationIdentifier;
   private boolean mySendOnly;
   private ConversationReply myRplyObj;
   private SessionAcallDescriptor myCallDescriptor;
   private boolean myInTransaction;
   private rdsession myRecieveSession;

   public DomainOutboundConversation(dsession domainSession, ConversationReply replyObj, int conversationIdentifier, boolean sendOnly, SessionAcallDescriptor callDescriptor, boolean inTransaction) {
      this.myDomainSession = domainSession;
      this.myRplyObj = replyObj;
      this.myConversationIdentifier = conversationIdentifier;
      this.mySendOnly = sendOnly;
      this.myCallDescriptor = callDescriptor;
      this.myInTransaction = inTransaction;
      this.myRecieveSession = domainSession.get_rcv_place();
   }

   private void internalDisconnect() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/DomainOutboundConversation/internalDisconnect/");
      }

      if (this.isDisconnected) {
         if (traceEnabled) {
            ntrace.doTrace("]/DomainOutboundConversation/internalDisconnect/10");
         }

      } else {
         this.myRecieveSession.remove_rplyObj(this.myCallDescriptor);
         this.myDomainSession = null;
         this.myRplyObj = null;
         this.myCallDescriptor = null;
         this.myRecieveSession = null;
         this.isDisconnected = true;
         if (traceEnabled) {
            ntrace.doTrace("]/DomainOutboundConversation/internalDisconnect/20");
         }

      }
   }

   public synchronized void tpsend(TypedBuffer data, int flags) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/DomainOutboundConversation/tpsend/" + data + "/" + flags);
      }

      if (!this.mySendOnly) {
         if (traceEnabled) {
            ntrace.doTrace("*]/DomainOutboundConversation/tpsend/10/TPEPROTO");
         }

         throw new TPException(9, "ERROR: Attempting to send but direction is receive");
      } else if (this.myDomainSession.getIsTerminated()) {
         this.internalDisconnect();
         if (traceEnabled) {
            ntrace.doTrace("*]/DomainOutboundConversation/tpsend/20/TPESYSTEM");
         }

         throw new TPException(12, "WARN: The domain link was removed underneath the conversation");
      } else if ((flags & -4130) != 0) {
         if (traceEnabled) {
            ntrace.doTrace("*]/DomainOutboundConversation/tpsend/30/TPEINVAL");
         }

         throw new TPException(4);
      } else {
         flags &= -34;
         boolean switchDirection = (flags & 4096) != 0;
         tfmh tmmsg;
         if (data == null) {
            tmmsg = new tfmh(1);
         } else {
            tcm user = new tcm((short)0, new UserTcb(data));
            tmmsg = new tfmh(data.getHintIndex(), user, 1);
         }

         try {
            this.myDomainSession._tpsend_internal(tmmsg, this.mySendSequenceNumber, this.myConversationIdentifier, true, switchDirection, false);
         } catch (TPException var7) {
            this.internalDisconnect();
            if (traceEnabled) {
               ntrace.doTrace("*]/DomainOutboundConversation/tpsend/40/" + var7);
            }

            throw var7;
         }

         ++this.mySendSequenceNumber;
         if (switchDirection) {
            this.mySendOnly = false;
         }

         if (traceEnabled) {
            ntrace.doTrace("]/DomainOutboundConversation/tpsend/50/");
         }

      }
   }

   public synchronized Reply tprecv(int flags) throws TPException, TPReplyException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/DomainOutboundConversation/tprecv/" + flags);
      }

      TuxedoReply theReply = null;
      if (this.mySendOnly) {
         if (traceEnabled) {
            ntrace.doTrace("*]/DomainOutboundConversation/tprecv/10/TPEPROTO");
         }

         throw new TPException(9, "ERROR: Attempting to receive but direction is send");
      } else if (this.myDomainSession.getIsTerminated()) {
         this.internalDisconnect();
         if (traceEnabled) {
            ntrace.doTrace("*]/DomainOutboundConversation/tprecv/20/TPESYSTEM");
         }

         throw new TPException(12, "WARN: The domain link was removed underneath the conversation");
      } else if ((flags & -34) != 0) {
         if (traceEnabled) {
            ntrace.doTrace("*]/DomainOutboundConversation/tprecv/30/TPEINVAL");
         }

         throw new TPException(4);
      } else {
         boolean block = (flags & 1) == 0;
         if (!this.myInTransaction && (flags & 32) == 0 && !this.myRecieveSession.addRplyObjTimeout(this.myCallDescriptor, 0)) {
            this.internalDisconnect();
            if (traceEnabled) {
               ntrace.doTrace("*]/DomainOutboundConversation/tprecv/40/TPESYSTEM");
            }

            throw new TPException(12, "ERROR: Unable to set a timeout for tprecv");
         } else {
            ReqMsg reply;
            if ((reply = this.myRplyObj.get_reply(block)) == null) {
               if (block) {
                  this.internalDisconnect();
                  if (traceEnabled) {
                     ntrace.doTrace("*]/DomainOutboundConversation/tprecv/50/TPESYSTEM");
                  }

                  throw new TPException(12, "ERROR: Conversation in invalid state");
               } else {
                  if (traceEnabled) {
                     ntrace.doTrace("*]/DomainOutboundConversation/tprecv/60/TPEBLOCK");
                  }

                  throw new TPException(3);
               }
            } else {
               tfmh tmmsg;
               if ((tmmsg = reply.getReply()) == null) {
                  this.internalDisconnect();
                  if (traceEnabled) {
                     ntrace.doTrace("*]/DomainOutboundConversation/tprecv/70/TPESYSTEM");
                  }

                  throw new TPException(12, "ERROR: Invalid tprecv message");
               } else {
                  TdomTcb tdom = (TdomTcb)tmmsg.tdom.body;
                  int myTPException = tdom.get_diagnostic();
                  int myflag = tdom.get_flag();
                  int mytpurcode = tdom.getTpurcode();
                  int mytperrordetail = tdom.get_errdetail();
                  int opcode = tdom.get_opcode();
                  int mytpevent = tdom.get_tpevent();
                  int theSequenceNumber = tdom.get_seqnum();
                  if ((myflag & 4096) != 0 && mytpevent == 0) {
                     mytpevent = 32;
                  }

                  TypedBuffer tb;
                  if (tmmsg.user == null) {
                     tb = null;
                  } else {
                     tb = ((UserTcb)tmmsg.user.body).user_data;
                  }

                  TPReplyException replyException;
                  if (theSequenceNumber != this.myRecvSequenceNumber) {
                     replyException = new TPReplyException(22, 0, mytpurcode, mytperrordetail, 0, 1, new TuxedoReply(tb, mytpurcode, this.myCallDescriptor));
                     this.internalDisconnect();
                     if (traceEnabled) {
                        ntrace.doTrace("*]/DomainOutboundConversation/tprecv/80/" + replyException + "/" + theSequenceNumber + "/" + this.myRecvSequenceNumber);
                     }

                     throw replyException;
                  } else {
                     ++this.myRecvSequenceNumber;
                     if (traceEnabled) {
                        ntrace.doTrace("*]/DomainOutboundConversation/tprecv/85/" + myTPException + "/" + myflag + "/" + mytpurcode + "/" + mytperrordetail + "/" + opcode + "/" + mytpevent + "/" + theSequenceNumber);
                     }

                     switch (opcode) {
                        case 2:
                           replyException = new TPReplyException(22, 0, mytpurcode, mytperrordetail, 0, 8, new TuxedoReply(tb, mytpurcode, this.myCallDescriptor));
                           this.internalDisconnect();
                           if (traceEnabled) {
                              ntrace.doTrace("*]/DomainOutboundConversation/tprecv/120/" + replyException);
                           }

                           throw replyException;
                        case 3:
                           this.internalDisconnect();
                           if (myTPException == 22) {
                              switch (mytpevent) {
                                 case 1:
                                 case 2:
                                 case 4:
                                    replyException = new TPReplyException(22, 0, mytpurcode, mytperrordetail, 0, mytpevent, new TuxedoReply(tb, mytpurcode, this.myCallDescriptor));
                                    if (traceEnabled) {
                                       ntrace.doTrace("*]/DomainOutboundConversation/tprecv/140/" + replyException);
                                    }

                                    throw replyException;
                                 case 3:
                                 default:
                                    if (traceEnabled) {
                                       ntrace.doTrace("*]/DomainOutboundConversation/tprecv/145/TPESYSTEM");
                                    }

                                    throw new TPException(12, "ERROR: Invalid tpevent detected " + mytpevent);
                              }
                           }

                           if (traceEnabled) {
                              ntrace.doTrace("*]/DomainOutboundConversation/tprecv/146/TPESYSTEM");
                           }

                           throw new TPException(12, "ERROR: Unexpected exception:" + myTPException);
                        case 4:
                        default:
                           this.internalDisconnect();
                           if (traceEnabled) {
                              ntrace.doTrace("*]/DomainOutboundConversation/tprecv/150/TPESYSTEM" + opcode);
                           }

                           throw new TPException(12, "ERROR: Got an invalid conversational response");
                        case 5:
                           if (myTPException == 22) {
                              switch (mytpevent) {
                                 case 1:
                                 case 2:
                                 case 4:
                                 case 8:
                                 default:
                                    this.internalDisconnect();
                                    if (traceEnabled) {
                                       ntrace.doTrace("*]/DomainOutboundConversation/tprecv/100/TPESYSTEM");
                                    }

                                    throw new TPException(12, "ERROR: Invalid tpevent detected " + mytpevent);
                                 case 32:
                                    this.mySendOnly = true;
                                    replyException = new TPReplyException(myTPException, 0, mytpurcode, mytperrordetail, 0, mytpevent, new TuxedoReply(tb, mytpurcode, this.myCallDescriptor));
                                    if (traceEnabled) {
                                       ntrace.doTrace("*]/DomainOutboundConversation/tprecv/90/" + replyException);
                                    }

                                    throw replyException;
                              }
                           } else {
                              if (myTPException != 0) {
                                 if (traceEnabled) {
                                    ntrace.doTrace("*]/DomainOutboundConversation/tprecv/110/TPESYSTEM/invalid diagnostic:" + myTPException);
                                 }

                                 throw new TPException(12, "ERROR: Invalid diagnostic:" + myTPException);
                              }

                              theReply = new TuxedoReply(tb, 0, this.myCallDescriptor);
                              if (traceEnabled) {
                                 ntrace.doTrace("]/DomainOutboundConversation/tprecv/160/" + theReply);
                              }

                              return theReply;
                           }
                        case 6:
                           replyException = new TPReplyException(22, 0, mytpurcode, mytperrordetail, 0, 1, new TuxedoReply(tb, mytpurcode, this.myCallDescriptor));
                           this.internalDisconnect();
                           if (traceEnabled) {
                              ntrace.doTrace("*]/DomainOutboundConversation/tprecv/130/" + replyException);
                           }

                           throw replyException;
                     }
                  }
               }
            }
         }
      }
   }

   public synchronized void tpdiscon() throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/DomainOutboundConversation/tpdiscon/");
      }

      if (this.myDomainSession == null) {
         if (traceEnabled) {
            ntrace.doTrace("*]/DomainOutboundConversation/tpdiscon/10/TPEPROTO");
         }

         throw new TPException(9, "ERROR: Attempting tpdiscon in an improper context");
      } else {
         tfmh tmmsg = new tfmh(1);

         try {
            this.myDomainSession._tpsend_internal(tmmsg, this.mySendSequenceNumber, this.myConversationIdentifier, true, false, true);
         } catch (TPException var4) {
            if (traceEnabled) {
               ntrace.doTrace("/DomainOutboundConversation/tpdiscon/internal send failure: " + var4);
            }

            throw var4;
         }

         ++this.mySendSequenceNumber;
         this.internalDisconnect();
         if (traceEnabled) {
            ntrace.doTrace("]/DomainOutboundConversation/tpdiscon/20");
         }

      }
   }

   public String toString() {
      return new String(this.isDisconnected + ":" + this.mySendSequenceNumber + ":" + this.myConversationIdentifier + ":" + this.mySendOnly + ":" + this.myCallDescriptor + ":" + this.myInTransaction);
   }
}
