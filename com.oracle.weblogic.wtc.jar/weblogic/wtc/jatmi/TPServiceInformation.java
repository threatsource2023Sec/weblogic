package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TPServiceInformation extends TuxedoReply implements Conversation {
   private static final long serialVersionUID = -2256092221608261884L;
   private String service_name;
   private TypedBuffer service_data;
   private int service_flags;
   private String service_data_key;
   private int mySessionIdentifier = -1;
   private int myConversationIdentifier;
   private transient boolean isConversationInitialized = false;
   private transient boolean conversationInitializationStatus;
   private transient boolean isDisconnected = false;
   private transient dsession myDomainSession;
   private transient int mySendSequenceNumber;
   private transient int myRecvSequenceNumber;
   private transient boolean mySendOnly;
   private transient ConversationReply myRplyObj;
   private transient SessionAcallDescriptor myCallDescriptor;
   private transient rdsession myRecieveSession;

   private void writeObject(ObjectOutputStream out) throws IOException {
      boolean traceEnabled = ntrace.isTraceEnabled(50000);
      if (traceEnabled) {
         ntrace.doTrace("[/TPServiceInformation/writeObject/");
      }

      if (null != TypedBufferFactory.getBufferPool() && null != this.service_data) {
         this.service_data_key = this.service_data.toString();
         TypedBufferFactory.getBufferPool().put(this.service_data_key, this.service_data);
         TypedBuffer temp = this.service_data;
         this.service_data = null;
         out.defaultWriteObject();
         this.service_data = temp;
         this.service_data_key = null;
         if (traceEnabled) {
            ntrace.doTrace("]/TPServiceInformation/writeObject/20/true");
         }

      } else {
         this.service_data_key = null;
         out.defaultWriteObject();
         if (traceEnabled) {
            ntrace.doTrace("]/TPServiceInformation/writeObject/10/false");
         }

      }
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      boolean traceEnabled = ntrace.isTraceEnabled(50000);
      if (traceEnabled) {
         ntrace.doTrace("[/TPServiceInformation/readObject/");
      }

      this.service_data_key = null;
      in.defaultReadObject();
      if (null != TypedBufferFactory.getBufferPool() && null != this.service_data_key) {
         this.service_data = TypedBufferFactory.getBufferPool().get(this.service_data_key);
         this.service_data_key = null;
         if (traceEnabled) {
            ntrace.doTrace("]/TPServiceInformation/readObject/20/true");
         }

      } else {
         if (traceEnabled) {
            ntrace.doTrace("]/TPServiceInformation/readObject/10/false");
         }

      }
   }

   public TPServiceInformation() {
   }

   public TPServiceInformation(String sn, TypedBuffer sd, int f, int sessionIdentifier, int conversationIdentifier) {
      this.service_name = sn;
      this.service_data = sd;
      this.service_flags = f;
      this.mySessionIdentifier = sessionIdentifier;
      this.myConversationIdentifier = conversationIdentifier;
   }

   public String getServiceName() {
      return this.service_name;
   }

   public TypedBuffer getServiceData() {
      return this.service_data;
   }

   public int getServiceFlags() {
      return this.service_flags;
   }

   private void initializeConversation() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TPServiceInformation/initializeConversation/");
      }

      if (this.isConversationInitialized) {
         if (traceEnabled) {
            ntrace.doTrace("]/TPServiceInformation/initializeConversation/10/false");
         }

      } else {
         this.conversationInitializationStatus = false;
         this.isConversationInitialized = true;
         gwatmi aSession;
         if ((aSession = DomainRegistry.getDomainSession(this.mySessionIdentifier)) == null) {
            if (traceEnabled) {
               ntrace.doTrace("]/TPServiceInformation/initializeConversation/20/false");
            }

         } else if (!(aSession instanceof dsession)) {
            if (traceEnabled) {
               ntrace.doTrace("]/TPServiceInformation/initializeConversation/30/false");
            }

         } else {
            this.myDomainSession = (dsession)aSession;
            this.mySendOnly = (this.service_flags & 2048) != 0;
            this.myCallDescriptor = new SessionAcallDescriptor(this.myConversationIdentifier, true);
            if ((this.myRecieveSession = this.myDomainSession.get_rcv_place()) == null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/TPServiceInformation/initializeConversation/40/false");
               }

            } else if ((this.myRplyObj = this.myRecieveSession.getConversationReply(this.myConversationIdentifier)) == null) {
               if (traceEnabled) {
                  ntrace.doTrace("]/TPServiceInformation/initializeConversation/50/false");
               }

            } else {
               this.mySendSequenceNumber = 1;
               this.myRecvSequenceNumber = 1;
               if (traceEnabled) {
                  ntrace.doTrace("]/TPServiceInformation/initializeConversation/60/true");
               }

               this.conversationInitializationStatus = true;
            }
         }
      }
   }

   private void internalDisconnect() {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TPServiceInformation/internalDisconnect/");
      }

      if (this.isDisconnected) {
         if (traceEnabled) {
            ntrace.doTrace("]/TPServiceInformation/internalDisconnect/10");
         }

      } else {
         this.myRecieveSession.remove_rplyObj(this.myCallDescriptor);
         this.myDomainSession = null;
         this.myRplyObj = null;
         this.myCallDescriptor = null;
         this.myRecieveSession = null;
         this.isDisconnected = true;
         if (traceEnabled) {
            ntrace.doTrace("]/TPServiceInformation/internalDisconnect/20");
         }

      }
   }

   public void tpsend(TypedBuffer data, int flags) throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TPServiceInformation/tpsend/" + data + "/" + flags);
      }

      if ((this.service_flags & 1024) == 0) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TPServiceInformation/tpsend/10/TPEINVAL");
         }

         throw new TPException(4, "ERROR: This service is not conversational");
      } else {
         if (!this.isConversationInitialized) {
            this.initializeConversation();
         }

         if (!this.conversationInitializationStatus) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TPServiceInformation/tpsend/20/TPEINVAL");
            }

            throw new TPException(12, "ERROR: Conversation initialization failed");
         } else if (this.isDisconnected) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TPServiceInformation/tpsend/25/TPEPROTO");
            }

            throw new TPException(9, "ERROR: Conversation has been disconnected");
         } else if (!this.mySendOnly) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TPServiceInformation/tpsend/30/TPEPROTO");
            }

            throw new TPException(9, "ERROR: Attempting to send but direction is receive");
         } else if (this.myDomainSession.getIsTerminated()) {
            this.internalDisconnect();
            if (traceEnabled) {
               ntrace.doTrace("*]/TPServiceInformation/tpsend/40/TPESYSTEM");
            }

            throw new TPException(12, "WARN: The domain link was removed underneath the conversation");
         } else if ((flags & -4130) != 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TPServiceInformation/tpsend/50/TPEINVAL");
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
               this.myDomainSession._tpsend_internal(tmmsg, this.mySendSequenceNumber, this.myConversationIdentifier, false, switchDirection, false);
            } catch (TPException var7) {
               this.internalDisconnect();
               if (traceEnabled) {
                  ntrace.doTrace("*]/TPServiceInformation/tpsend/60/" + var7);
               }

               throw var7;
            }

            ++this.mySendSequenceNumber;
            if (switchDirection) {
               this.mySendOnly = false;
            }

            if (traceEnabled) {
               ntrace.doTrace("]/TPServiceInformation/tpsend/70/");
            }

         }
      }
   }

   public Reply tprecv(int flags) throws TPException, TPReplyException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TPServiceInformation/tprecv/" + flags);
      }

      TuxedoReply theReply = null;
      if ((this.service_flags & 1024) == 0) {
         if (traceEnabled) {
            ntrace.doTrace("*]/TPServiceInformation/tprecv/10/TPEINVAL");
         }

         throw new TPException(4, "ERROR: This service is not conversational");
      } else {
         if (!this.isConversationInitialized) {
            this.initializeConversation();
         }

         if (!this.conversationInitializationStatus) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TPServiceInformation/tprecv/20/TPESYSTEM");
            }

            throw new TPException(12, "ERROR: Conversation initialization failed");
         } else if (this.isDisconnected) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TPServiceInformation/tprecv/25/TPEPROTO");
            }

            throw new TPException(9, "ERROR: Conversation has been disconnected");
         } else if (this.mySendOnly) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TPServiceInformation/tprecv/30/TPEPROTO");
            }

            throw new TPException(9, "ERROR: Attempting to receive but direction is send");
         } else if (this.myDomainSession.getIsTerminated()) {
            this.internalDisconnect();
            if (traceEnabled) {
               ntrace.doTrace("*]/TPServiceInformation/tprecv/40/TPESYSTEM");
            }

            throw new TPException(12, "WARN: The domain link was removed underneath the conversation");
         } else if ((flags & -34) != 0) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TPServiceInformation/tprecv/50/TPEINVAL");
            }

            throw new TPException(4);
         } else {
            boolean block = (flags & 1) == 0;
            if ((flags & 32) == 0 && !this.myRecieveSession.addRplyObjTimeout(this.myCallDescriptor, 0)) {
               this.internalDisconnect();
               if (traceEnabled) {
                  ntrace.doTrace("*]/TPServiceInformation/tprecv/60/TPESYSTEM");
               }

               throw new TPException(12, "ERROR: Unable to set a timeout for tprecv");
            } else {
               ReqMsg reply;
               if ((reply = this.myRplyObj.get_reply(block)) == null) {
                  if (block) {
                     this.internalDisconnect();
                     if (traceEnabled) {
                        ntrace.doTrace("*]/TPServiceInformation/tprecv/70/TPESYSTEM");
                     }

                     throw new TPException(12, "ERROR: Conversation in invalid state");
                  } else {
                     if (traceEnabled) {
                        ntrace.doTrace("*]/TPServiceInformation/tprecv/80/TPEBLOCK");
                     }

                     throw new TPException(3);
                  }
               } else {
                  tfmh tmmsg;
                  if ((tmmsg = reply.getReply()) == null) {
                     this.internalDisconnect();
                     if (traceEnabled) {
                        ntrace.doTrace("*]/TPServiceInformation/tprecv/90/TPESYSTEM");
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
                           ntrace.doTrace("*]/TPServiceInformation/tprecv/100/" + replyException + "/" + theSequenceNumber + "/" + this.myRecvSequenceNumber);
                        }

                        throw replyException;
                     } else {
                        ++this.myRecvSequenceNumber;
                        switch (opcode) {
                           case 2:
                              replyException = new TPReplyException(22, 0, mytpurcode, mytperrordetail, 0, 8, new TuxedoReply(tb, mytpurcode, this.myCallDescriptor));
                              this.internalDisconnect();
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/TPServiceInformation/tprecv/140/" + replyException);
                              }

                              throw replyException;
                           case 3:
                              if (myTPException == 11) {
                                 replyException = new TPReplyException(22, 0, mytpurcode, mytperrordetail, 0, 4, new TuxedoReply(tb, mytpurcode, this.myCallDescriptor));
                              } else if (myTPException == 10) {
                                 replyException = new TPReplyException(22, 0, mytpurcode, mytperrordetail, 0, 2, new TuxedoReply(tb, mytpurcode, this.myCallDescriptor));
                              } else {
                                 replyException = new TPReplyException(22, 0, mytpurcode, mytperrordetail, 0, 1, new TuxedoReply(tb, mytpurcode, this.myCallDescriptor));
                              }

                              this.internalDisconnect();
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/TPServiceInformation/tprecv/160/" + replyException);
                              }

                              throw replyException;
                           case 4:
                           default:
                              this.internalDisconnect();
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/TPServiceInformation/tprecv/170/TPESYSTEM" + opcode);
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
                                          ntrace.doTrace("*]/TPServiceInformation/tprecv/120/TPESYSTEM");
                                       }

                                       throw new TPException(12, "ERROR: Invalid tpevent detected " + mytpevent);
                                    case 32:
                                       this.mySendOnly = true;
                                       replyException = new TPReplyException(myTPException, 0, mytpurcode, mytperrordetail, 0, mytpevent, new TuxedoReply(tb, mytpurcode, this.myCallDescriptor));
                                       if (traceEnabled) {
                                          ntrace.doTrace("*]/TPServiceInformation/tprecv/110/" + replyException);
                                       }

                                       throw replyException;
                                 }
                              } else {
                                 if (myTPException != 0) {
                                    if (traceEnabled) {
                                       ntrace.doTrace("*]/TPServiceInformation/tprecv/130/TPESYSTEM/invalid diagnostic:" + myTPException);
                                    }

                                    throw new TPException(12, "ERROR: Invalid diagnostic:" + myTPException);
                                 }

                                 theReply = new TuxedoReply(tb, 0, this.myCallDescriptor);
                                 if (traceEnabled) {
                                    ntrace.doTrace("]/TPServiceInformation/tprecv/180/" + theReply);
                                 }

                                 return theReply;
                              }
                           case 6:
                              replyException = new TPReplyException(22, 0, mytpurcode, mytperrordetail, 0, 1, new TuxedoReply(tb, mytpurcode, this.myCallDescriptor));
                              this.internalDisconnect();
                              if (traceEnabled) {
                                 ntrace.doTrace("*]/TPServiceInformation/tprecv/150/" + replyException);
                              }

                              throw replyException;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void tpdiscon() throws TPException {
      boolean traceEnabled = ntrace.isTraceEnabled(4);
      if (traceEnabled) {
         ntrace.doTrace("[/TPServiceInformation/tpdiscon/");
      }

      tfmh tmmsg = new tfmh(1);
      if ((this.service_flags & 1024) == 0) {
         if (traceEnabled) {
            ntrace.doTrace("]/TPServiceInformation/tpdiscon/10/TPEINVAL");
         }

         throw new TPException(4, "ERROR: This service is not conversational");
      } else {
         if (!this.isConversationInitialized) {
            this.initializeConversation();
         }

         if (!this.conversationInitializationStatus) {
            if (traceEnabled) {
               ntrace.doTrace("]/TPServiceInformation/tpdiscon/20/TPESYSTEM");
            }

            throw new TPException(12, "ERROR: Conversation initialization failed");
         } else if (this.isDisconnected) {
            if (traceEnabled) {
               ntrace.doTrace("*]/TPServiceInformation/tpdiscon/25/TPEPROTO");
            }

            throw new TPException(9, "ERROR: Conversation has been disconnected");
         } else {
            try {
               this.myDomainSession._tpsend_internal(tmmsg, this.mySendSequenceNumber, this.myConversationIdentifier, true, false, true);
            } catch (TPException var4) {
               if (traceEnabled) {
                  ntrace.doTrace("/TPServiceInformation/tpdiscon/internal send failure: " + var4);
               }
            }

            ++this.mySendSequenceNumber;
            this.internalDisconnect();
            if (traceEnabled) {
               ntrace.doTrace("]/TPServiceInformation/tpdiscon/30");
            }

         }
      }
   }

   public String toString() {
      return new String(super.toString() + ":" + this.service_name + ":" + this.service_data + ":" + this.service_flags + ":" + this.mySessionIdentifier + ":" + this.myConversationIdentifier);
   }
}
