package weblogic.security.service.internal;

import com.bea.common.engine.ServiceInitializationException;
import com.bea.common.engine.ServiceLifecycleSpi;
import com.bea.common.engine.Services;
import com.bea.common.logger.service.LoggerService;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.service.AuditService;
import com.bea.common.security.utils.UsernameUtils;
import java.io.IOException;
import java.security.AccessController;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import weblogic.cluster.ClusterServices;
import weblogic.cluster.MulticastSession;
import weblogic.cluster.RecoverListener;
import weblogic.cluster.ClusterServicesActivator.Locator;
import weblogic.security.SecurityLogger;
import weblogic.security.SubjectUtils;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.auth.callback.IdentityDomainNames;
import weblogic.security.service.AdminResource;
import weblogic.security.service.AuthorizationManager;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.security.spi.AuditSeverity;
import weblogic.security.spi.AuditAtnEventV2.AtnEventTypeV2;
import weblogic.security.utils.PartitionUtils;
import weblogic.security.utils.ResourceIDDContextWrapper;
import weblogic.work.WorkManager;
import weblogic.work.WorkManagerFactory;

public class UserLockoutServiceImpl implements ServiceLifecycleSpi {
   private LoggerSpi logger;
   private WorkManager USERLOCKOUTWM = null;
   private long lastGCms = 0L;
   private static long minGCInterval = Long.getLong("weblogic.security.userlockout.minGCIntervalMs", 0L);
   private volatile boolean skipGC = false;

   public Object init(Object config, Services dependentServices) throws ServiceInitializationException {
      this.logger = ((LoggerService)dependentServices.getService(LoggerService.SERVICE_NAME)).getLogger("SecurityUserLockout");
      if (this.logger == null) {
         throw new UnsupportedOperationException(SecurityLogger.getServiceNotFound("Logger", "SecurityUserLockout"));
      } else {
         if (this.logger.isDebugEnabled()) {
            this.logger.debug(this.getClass().getName() + ".init()");
         }

         return new ServiceImpl((UserLockoutServiceConfig)config, dependentServices);
      }
   }

   public void shutdown() {
   }

   private class ServiceImpl implements UserLockoutService, UserLockoutRuntimeService, UserLockoutAdministrationService, UserLockoutCoordinationService {
      private long cum_user_lockout_count;
      private long cum_invalid_login_count;
      private long cum_locked_attempts_count;
      private long high_invalid_login_users;
      private long cum_user_unlock_count;
      private long current_lock_count;
      private Hashtable master_invalid_login;
      private Vector unused_cache;
      private long unused_cache_size;
      private volatile long timestamp_of_current_check;
      private boolean lockout_enabled;
      private long lockout_threshold;
      private long lockout_duration;
      private long lockout_duration_min;
      private long lockout_reset_duration;
      private long lockout_gc_threshold;
      private volatile int sequence_number;
      private volatile int failure_sequence_number;
      private volatile int unlock_sequence_number;
      private String this_server_name;
      private ClusterServices clusterServices;
      private MulticastSession multicastSession;
      private String realmName;
      private String realmManagementIDD;
      private boolean caseSensitiveUserNames;
      private AuditService auditService;

      private ServiceImpl(UserLockoutServiceConfig myconfig, Services dependentServices) throws ServiceInitializationException {
         this.master_invalid_login = new Hashtable();
         this.unused_cache = new Vector();
         this.lockout_enabled = false;
         this.clusterServices = null;
         this.multicastSession = null;
         this.lockout_enabled = myconfig.isLockoutEnabled();
         this.lockout_threshold = myconfig.getLockoutThreshold();
         this.lockout_duration_min = myconfig.getLockoutDuration();
         this.lockout_duration = this.lockout_duration_min * 60L * 1000L;
         this.lockout_reset_duration = myconfig.getLockoutResetDuration() * 60L * 1000L;
         this.lockout_gc_threshold = myconfig.getLockoutGCThreshold();
         this.unused_cache_size = myconfig.getLockoutCacheSize();
         this.this_server_name = myconfig.getServerName();
         this.realmName = myconfig.getRealmName();
         this.realmManagementIDD = myconfig.getManagementIDD();
         this.createMulticastSession();
         this.caseSensitiveUserNames = SecurityServiceManager.isCaseSensitiveUserNames();
         this.auditService = (AuditService)dependentServices.getService(myconfig.getAuditServiceName());
         if (this.auditService == null) {
            throw new AssertionError(this.getClass().getName() + ".  could not get AuditService " + myconfig.getAuditServiceName());
         } else {
            if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
               String method = this.getClass().getName() + ".constructor";
               UserLockoutServiceImpl.this.logger.debug(method + " RealmName            = " + myconfig.getRealmName());
               UserLockoutServiceImpl.this.logger.debug(method + " Realm Management IDD = " + myconfig.getManagementIDD());
               UserLockoutServiceImpl.this.logger.debug(method + " ServerName           = " + myconfig.getServerName());
               UserLockoutServiceImpl.this.logger.debug(method + " LockoutEnabled       = " + myconfig.isLockoutEnabled());
               UserLockoutServiceImpl.this.logger.debug(method + " LockoutThreshold     = " + myconfig.getLockoutThreshold());
               UserLockoutServiceImpl.this.logger.debug(method + " LockoutDuration      = " + myconfig.getLockoutDuration());
               UserLockoutServiceImpl.this.logger.debug(method + " LockoutResetDuration = " + myconfig.getLockoutResetDuration());
               UserLockoutServiceImpl.this.logger.debug(method + " LockoutGCThreshold   = " + myconfig.getLockoutGCThreshold());
               UserLockoutServiceImpl.this.logger.debug(method + " LockoutCacheSize     = " + myconfig.getLockoutCacheSize());
            }

         }
      }

      private boolean createMulticastSession() {
         if (this.multicastSession != null) {
            return true;
         } else {
            this.clusterServices = Locator.locateClusterServices();
            if (this.clusterServices != null) {
               this.multicastSession = this.clusterServices.createMulticastSession((RecoverListener)null, -1);
               if (this.multicastSession != null) {
                  return true;
               } else {
                  if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                     UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + "UserLockout Can't create multicastSession even though ClusterServices are available");
                  }

                  return false;
               }
            } else {
               if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                  UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + "UserLockout Can't create multicastSession because ClusterServices are unavailable");
               }

               return false;
            }
         }
      }

      public UserLockoutCoordinationService getCoordinationService() {
         if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
            UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".getCoordinationService()");
         }

         return this;
      }

      public void processSecurityMessage(int nextSeqNo, final SecurityMulticastRecord record) {
         Runnable runnable = new Runnable() {
            public void run() {
               ServiceImpl.this.processSecurityMessage(record);
            }
         };
         this.getUserLockoutWM().schedule(runnable);
      }

      private void processSecurityMessage(SecurityMulticastRecord record) {
         if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
            UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".processSecurityMessage()");
         }

         if (!(record instanceof LoginFailureRecord) && !(record instanceof UnlockUserRecord)) {
            if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
               UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".processSecurityMessage() discarding unknown SecurityMulticaseRecord");
            }

         } else if (record.eventOrigin().equals(this.this_server_name)) {
            if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
               UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".processSecurityMessage() discarding SecurityMulticaseRecord sent by this server");
            }

         } else {
            if (record instanceof LoginFailureRecord) {
               LoginFailureRecord lf = (LoginFailureRecord)record;
               if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                  UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".processSecurityMessage() Received a LoginFailureRecord: " + lf.toString());
               }

               this.logFailure(lf.eventOrigin(), lf.eventSequenceNumber(), lf.eventTime(), lf.userName(), lf.identityDomain(), true);
            } else if (record instanceof UnlockUserRecord) {
               UnlockUserRecord uu = (UnlockUserRecord)record;
               if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                  UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".processSecurityMessage() Received an UnlockUserRecord: " + uu.toString());
               }

               if (this.unlockLocal(uu.userName(), uu.identityDomain(), true) && UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                  UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".processSecurityMessage() Locked user has now been unlocked locally");
               }
            }

         }
      }

      public UserLockoutRuntimeService getRuntimeService() {
         if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
            UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".getRuntimeService()");
         }

         return this;
      }

      public boolean isLocked(String userName, String identityDomain) {
         if (userName == null) {
            throw new IllegalArgumentException(SecurityLogger.getNullParameterSupplied("isLocked"));
         } else {
            if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
               UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".isLocked(" + userName + "," + identityDomain + ")");
            }

            if (!this.caseSensitiveUserNames) {
               userName = userName.toLowerCase();
            }

            if (this.lockout_enabled && this.master_invalid_login.size() != 0) {
               this.setTimestampOfCurrentCheck();
               IdentityDomainNames userKey = new IdentityDomainNames(userName, identityDomain);
               if (this.master_invalid_login.containsKey(userKey)) {
                  InvalidLogin user_invalid_login = (InvalidLogin)this.master_invalid_login.get(userKey);
                  long when_locked = user_invalid_login.getLockedTimestamp();
                  if (when_locked == 0L) {
                     if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                        UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".isLocked(" + userName + "," + identityDomain + ") in realm " + this.realmName + " is not yet locked");
                     }

                     return false;
                  } else {
                     synchronized(this) {
                        ++this.cum_locked_attempts_count;
                        ++this.cum_invalid_login_count;
                     }

                     long unlock_time = when_locked + this.lockout_duration;
                     if (this.getTimestampOfCurrentCheck() < unlock_time) {
                        if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                           UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".isLocked(" + userName + "," + identityDomain + ") UserLockout User " + UsernameUtils.formatUserName(userName, identityDomain) + " in realm " + this.realmName + " is still locked");
                        }

                        return true;
                     } else {
                        this.clearInvalidLoginRecord(user_invalid_login);
                        synchronized(this) {
                           ++this.cum_user_unlock_count;
                           --this.current_lock_count;
                        }

                        final String user = UsernameUtils.getTruncatedFormattedName(userName, identityDomain);
                        this.getUserLockoutWM().schedule(new Runnable() {
                           public void run() {
                              SecurityLogger.logRealmLockoutExpiredInfo(user, ServiceImpl.this.realmName);
                              if (ServiceImpl.this.auditService != null) {
                                 AuditAtnEventImpl atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.SUCCESS, user, (ContextHandler)null, AtnEventTypeV2.USERLOCKOUTEXPIRED, (Exception)null);
                                 ServiceImpl.this.auditService.writeEvent(atnAuditEvent);
                              }

                           }
                        });
                        return false;
                     }
                  }
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      }

      public boolean isLocked(String userName) {
         return this.isLocked(userName, (String)null);
      }

      public void logFailure(String userName, String identityDomain) {
         if (userName == null) {
            throw new IllegalArgumentException(SecurityLogger.getNullParameterSupplied("logFailure"));
         } else {
            if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
               UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".logFailure(" + userName + "," + identityDomain + ")");
            }

            ++this.failure_sequence_number;
            LoginFailureRecord failure = this.logFailure(this.this_server_name, this.failure_sequence_number, this.getTimestampOfCurrentCheck(), userName, identityDomain, false);
            if (failure != null) {
               ++this.sequence_number;
               SecurityMessage sm = new SecurityMessage(this.realmName, this.sequence_number, failure);
               if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                  UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".logFailure(" + userName + "," + identityDomain + ") UserLockout About to multicast login failure for user " + sm.toString());
               }

               try {
                  if (this.createMulticastSession()) {
                     this.multicastSession.send(sm);
                     if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                        UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".logFailure(" + userName + "," + identityDomain + ") UserLockout Sent multicast for login failure");
                     }
                  }
               } catch (IOException var6) {
                  SecurityLogger.logRealmSendingLoginFailure(this.realmName, var6.toString());
               }
            }

         }
      }

      public void logFailure(String userName) {
         this.logFailure(userName, (String)null);
      }

      protected LoginFailureRecord logFailure(String server, int sequenceNumber, long timestamp, String userName, String identityDomain, boolean isMulticast) {
         if (!this.caseSensitiveUserNames) {
            userName = userName.toLowerCase();
         }

         LoginFailureRecord failure = null;
         if (!this.lockout_enabled) {
            return failure;
         } else {
            if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
               UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".logFailure(" + userName + "," + identityDomain + ") UserLockout Login failure for user in realm " + this.realmName);
            }

            if (server.equals(this.this_server_name)) {
               synchronized(this) {
                  ++this.cum_invalid_login_count;
               }
            }

            final InvalidLogin user_invalid_login = null;
            IdentityDomainNames userKey = new IdentityDomainNames(userName, identityDomain);
            synchronized(this) {
               user_invalid_login = (InvalidLogin)this.master_invalid_login.get(userKey);
               if (user_invalid_login == null) {
                  if (this.unused_cache.size() > 0) {
                     if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                        UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".logFailure(" + userName + "," + identityDomain + ") UserLockout Retrieving unused invalid login from the cache");
                     }

                     user_invalid_login = (InvalidLogin)this.unused_cache.elementAt(0);
                     this.unused_cache.removeElementAt(0);
                     user_invalid_login.setUser(userKey);
                  } else {
                     user_invalid_login = new InvalidLogin(userName, identityDomain);
                  }

                  this.master_invalid_login.put(userKey, user_invalid_login);
               }

               if ((long)this.master_invalid_login.size() > this.getInvalidLoginUsersHighCount()) {
                  this.high_invalid_login_users = (long)this.master_invalid_login.size();
               }
            }

            failure = new LoginFailureRecord(server, this.realmName, sequenceNumber, timestamp, userName, identityDomain);
            user_invalid_login.addFailure(failure);
            this.cleanOutStaleFailureRecords(user_invalid_login);
            if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
               UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".logFailure(" + userName + "," + identityDomain + ") User " + UsernameUtils.formatUserName(userName, identityDomain) + " has " + user_invalid_login.getFailureCount() + " failures in realm " + this.realmName);
            }

            if ((long)user_invalid_login.getFailureCount() >= this.lockout_threshold) {
               if (this.runtimeIsLocked(userName, identityDomain)) {
                  synchronized(this) {
                     ++this.cum_locked_attempts_count;
                  }
               } else {
                  SecurityLogger.logRealmLockingUser(UsernameUtils.getTruncatedFormattedName(userName, identityDomain), this.realmName, (long)user_invalid_login.getFailureCount(), this.lockout_duration_min);
                  synchronized(this) {
                     ++this.cum_user_lockout_count;
                     ++this.current_lock_count;
                  }

                  user_invalid_login.setLockedTimestamp(timestamp);
                  if (!isMulticast) {
                     final String user = UsernameUtils.getTruncatedFormattedName(userName, identityDomain);
                     this.getUserLockoutWM().schedule(new Runnable() {
                        public void run() {
                           SecurityLogger.logRealmLockingUser(user, ServiceImpl.this.realmName, (long)user_invalid_login.getFailureCount(), ServiceImpl.this.lockout_duration_min);
                           if (ServiceImpl.this.auditService != null) {
                              AuditAtnEventImpl atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.WARNING, user, (ContextHandler)null, AtnEventTypeV2.USERLOCKED, (Exception)null);
                              ServiceImpl.this.auditService.writeEvent(atnAuditEvent);
                           }

                        }
                     });
                  } else {
                     SecurityLogger.logRealmLockingUser(UsernameUtils.getTruncatedFormattedName(userName, identityDomain), this.realmName, (long)user_invalid_login.getFailureCount(), this.lockout_duration_min);
                     if (this.auditService != null) {
                        AuditAtnEventImpl atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.WARNING, UsernameUtils.getTruncatedFormattedName(userName, identityDomain), (ContextHandler)null, AtnEventTypeV2.USERLOCKED, (Exception)null);
                        this.auditService.writeEvent(atnAuditEvent);
                     }
                  }
               }
            }

            UserLockoutServiceImpl.this.skipGC = UserLockoutServiceImpl.minGCInterval > 0L && System.currentTimeMillis() - UserLockoutServiceImpl.this.lastGCms < UserLockoutServiceImpl.minGCInterval;
            if (UserLockoutServiceImpl.this.skipGC) {
               return failure;
            } else {
               this.garbageCollectInvalidLoginRecords();
               UserLockoutServiceImpl.this.lastGCms = System.currentTimeMillis();
               return failure;
            }
         }
      }

      public void logSuccess(String userName, String identityDomain) {
         if (userName == null) {
            throw new IllegalArgumentException(SecurityLogger.getNullParameterSupplied("logSucces"));
         } else {
            if (this.unlockLocal(userName, identityDomain, false)) {
               ++this.unlock_sequence_number;
               UnlockUserRecord uu = new UnlockUserRecord(this.this_server_name, this.realmName, this.unlock_sequence_number, this.getTimestampOfCurrentCheck(), userName, identityDomain);
               ++this.sequence_number;
               SecurityMessage sm = new SecurityMessage(this.realmName, this.sequence_number, uu);
               if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                  UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".logSuccess(" + userName + "," + identityDomain + ") unlocking user: " + sm.toString());
               }

               try {
                  if (this.createMulticastSession()) {
                     this.multicastSession.send(sm);
                     if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                        UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".logSuccess(" + userName + "," + identityDomain + ") Sent multicast for unlock user in realm " + this.realmName);
                     }
                  }
               } catch (IOException var6) {
                  SecurityLogger.logRealmBroadcastUnlockUserFailure(UsernameUtils.getTruncatedFormattedName(userName, identityDomain), this.realmName, var6.toString());
               }
            }

         }
      }

      public void logSuccess(String userName) {
         this.logSuccess(userName, (String)null);
      }

      private boolean unlockLocal(final String userName, String identityDomain, boolean isMulticast) {
         if (userName == null) {
            throw new IllegalArgumentException(SecurityLogger.getNullParameterSupplied("unlockLocal"));
         } else {
            if (!this.caseSensitiveUserNames) {
               userName = userName.toLowerCase();
            }

            if (this.lockout_enabled && this.master_invalid_login.size() != 0) {
               if (userName == null) {
                  throw new AssertionError(SecurityLogger.getReceivedANullUserName());
               } else {
                  IdentityDomainNames userKey = new IdentityDomainNames(userName, identityDomain);
                  InvalidLogin user_invalid_login = (InvalidLogin)this.master_invalid_login.get(userKey);
                  if (user_invalid_login == null) {
                     return false;
                  } else {
                     long when_locked = user_invalid_login.getLockedTimestamp();
                     if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                        UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".unlockLocal(" + userName + "," + identityDomain + ") Unlocked user or successful login in realm " + this.realmName + " cleaning out old invalid login record");
                     }

                     InvalidLogin unused = null;
                     synchronized(this) {
                        unused = (InvalidLogin)this.master_invalid_login.remove(userKey);
                     }

                     if (unused != null) {
                        synchronized(unused) {
                           unused.erase();
                           if ((long)this.unused_cache.size() < this.unused_cache_size) {
                              if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                                 UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".unlockLocal(" + userName + "," + identityDomain + ") UserLockout Putting unused invalid login record in cache");
                              }

                              this.unused_cache.addElement(unused);
                           }
                        }
                     }

                     if (when_locked != 0L) {
                        synchronized(this) {
                           ++this.cum_user_unlock_count;
                           --this.current_lock_count;
                        }
                     }

                     if (this.auditService != null) {
                        if (!isMulticast) {
                           this.getUserLockoutWM().schedule(new Runnable() {
                              public void run() {
                                 AuditAtnEventImpl atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.SUCCESS, userName, (ContextHandler)null, AtnEventTypeV2.USERUNLOCKED, (Exception)null);
                                 ServiceImpl.this.auditService.writeEvent(atnAuditEvent);
                              }
                           });
                        } else {
                           AuditAtnEventImpl atnAuditEvent = new AuditAtnEventImpl(AuditSeverity.SUCCESS, userName, (ContextHandler)null, AtnEventTypeV2.USERUNLOCKED, (Exception)null);
                           this.auditService.writeEvent(atnAuditEvent);
                        }
                     }

                     return true;
                  }
               }
            } else {
               return false;
            }
         }
      }

      private void setTimestampOfCurrentCheck() {
         this.timestamp_of_current_check = System.currentTimeMillis();
      }

      private long getTimestampOfCurrentCheck() {
         if (this.timestamp_of_current_check == 0L) {
            this.setTimestampOfCurrentCheck();
         }

         return this.timestamp_of_current_check;
      }

      private void cleanOutStaleFailureRecords(InvalidLogin invalidLogin) {
         if (invalidLogin != null) {
            Vector failure_records = invalidLogin.getFailures();
            if (failure_records == null) {
               throw new AssertionError(SecurityLogger.getInconsistentInvalidLoginRecord());
            } else if (failure_records.size() != 0) {
               for(int i = 0; i < failure_records.size(); ++i) {
                  LoginFailureRecord checkfailure = (LoginFailureRecord)failure_records.elementAt(i);
                  if (this.getTimestampOfCurrentCheck() - checkfailure.timestamp <= this.lockout_reset_duration) {
                     break;
                  }

                  if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                     UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + "UserLockout Discarding stale login failure record");
                  }

                  failure_records.removeElementAt(i);
                  checkfailure = null;
               }

            }
         }
      }

      private synchronized void clearInvalidLoginRecord(InvalidLogin user_invalid_login) {
         InvalidLogin unused;
         synchronized(user_invalid_login) {
            String user_name = user_invalid_login.getName();
            if (user_name == null) {
               return;
            }

            IdentityDomainNames userKey = new IdentityDomainNames(user_name, user_invalid_login.getIdentityDomain());
            unused = (InvalidLogin)this.master_invalid_login.remove(userKey);
            if (unused == null) {
               return;
            }

            unused.erase();
         }

         if ((long)this.unused_cache.size() < this.unused_cache_size) {
            if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
               UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + "UserLockout Putting unused invalid login record in cache");
            }

            this.unused_cache.addElement(unused);
         } else {
            unused = null;
         }

      }

      private synchronized void garbageCollectInvalidLoginRecords() {
         if (!UserLockoutServiceImpl.this.skipGC) {
            UserLockoutServiceImpl.this.skipGC = UserLockoutServiceImpl.minGCInterval > 0L;
            Thread.yield();
            long current_time = System.currentTimeMillis();
            InvalidLogin user_invalid_login = null;
            LoginFailureRecord latest_failure = null;
            int num_records = this.master_invalid_login.size();
            if (num_records != 0 && (long)num_records >= this.lockout_gc_threshold) {
               Enumeration enumerator = this.master_invalid_login.elements();

               while(enumerator.hasMoreElements()) {
                  user_invalid_login = (InvalidLogin)enumerator.nextElement();
                  if (user_invalid_login == null) {
                     throw new AssertionError(SecurityLogger.getEnumeratorReturnedNullElement());
                  }

                  synchronized(user_invalid_login) {
                     long when_locked = user_invalid_login.getLockedTimestamp();
                     if (when_locked == 0L) {
                        latest_failure = (LoginFailureRecord)user_invalid_login.getLatestFailure();
                        if (latest_failure != null) {
                           if (latest_failure.eventTime() < current_time - this.lockout_reset_duration) {
                              if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                                 UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + "UserLockout Garbage collecting InvalidLogin record for user: " + UsernameUtils.formatUserName(user_invalid_login.getName(), user_invalid_login.getIdentityDomain()));
                              }

                              this.clearInvalidLoginRecord(user_invalid_login);
                           }
                        }
                     }
                  }
               }

               if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                  UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + "UserLockout InvalidLogin Record GC done: " + (num_records - this.master_invalid_login.size()) + " records garbage collected");
               }

            } else {
               if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                  UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + "UserLockout InvalidLogin Record GC not needed");
               }

            }
         }
      }

      public UserLockoutAdministrationService getAdministrationService() {
         if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
            UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".getAdministrationService()");
         }

         return this;
      }

      public long getLastLoginFailure(String userName, String identityDomain) {
         if (userName == null) {
            throw new IllegalArgumentException(SecurityLogger.getNullParameterSupplied("getLastLoginFailure"));
         } else {
            if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
               UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".getLastLoginFailure(" + userName + "," + identityDomain + ")");
            }

            if (!this.caseSensitiveUserNames) {
               userName = userName.toLowerCase();
            }

            if (!this.lockout_enabled) {
               return 0L;
            } else if (userName == null) {
               throw new AssertionError(SecurityLogger.getReceivedANullUserName());
            } else if (userName.equals("")) {
               if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                  UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".getLastLoginFailure(" + userName + "," + identityDomain + ") UserLockout getLastLoginFailure was passed an empty user name, ignoring");
               }

               return 0L;
            } else {
               IdentityDomainNames userKey = new IdentityDomainNames(userName, identityDomain);
               InvalidLogin user_invalid_login = null;
               if (!this.master_invalid_login.containsKey(userKey)) {
                  return 0L;
               } else {
                  user_invalid_login = (InvalidLogin)this.master_invalid_login.get(userKey);
                  if (user_invalid_login == null) {
                     throw new AssertionError(SecurityLogger.getInconsistentHashTableKeyExists());
                  } else {
                     Vector failure_records = user_invalid_login.getFailures();
                     if (failure_records == null) {
                        throw new AssertionError(SecurityLogger.getInconsistentInvalidLoginRecord());
                     } else if (failure_records.size() == 0) {
                        return 0L;
                     } else {
                        LoginFailureRecord last_failure = (LoginFailureRecord)failure_records.lastElement();
                        return last_failure != null ? last_failure.timestamp : 0L;
                     }
                  }
               }
            }
         }
      }

      public long getLastLoginFailure(String userName) {
         return this.getLastLoginFailure(userName, (String)null);
      }

      public long getLoginFailureCount(String userName, String identityDomain) {
         if (userName == null) {
            throw new IllegalArgumentException(SecurityLogger.getNullParameterSupplied("getLoginFailureCount"));
         } else {
            if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
               UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".getLoginFailureCount(" + userName + "," + identityDomain + ")");
            }

            if (!this.caseSensitiveUserNames) {
               userName = userName.toLowerCase();
            }

            if (!this.lockout_enabled) {
               return 0L;
            } else if (userName == null) {
               throw new AssertionError(SecurityLogger.getReceivedANullUserName());
            } else if (userName.equals("")) {
               if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                  UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".getLoginFailureCount(" + userName + "," + identityDomain + ") UserLockout getLoginFailureCount was passed an empty user name, ignoring");
               }

               return 0L;
            } else {
               IdentityDomainNames userKey = new IdentityDomainNames(userName, identityDomain);
               InvalidLogin user_invalid_login = null;
               if (!this.master_invalid_login.containsKey(userKey)) {
                  return 0L;
               } else {
                  user_invalid_login = (InvalidLogin)this.master_invalid_login.get(userKey);
                  if (user_invalid_login == null) {
                     throw new AssertionError(SecurityLogger.getInconsistentHashTableKeyExists());
                  } else {
                     Vector failure_records = user_invalid_login.getFailures();
                     return failure_records == null ? 0L : (long)failure_records.size();
                  }
               }
            }
         }
      }

      public long getLoginFailureCount(String userName) {
         return this.getLoginFailureCount(userName, (String)null);
      }

      public boolean isLockedOut(String userName, String identityDomain) {
         return this.runtimeIsLocked(userName, identityDomain);
      }

      public boolean isLockedOut(String userName) {
         return this.isLockedOut(userName, (String)null);
      }

      public void clearLockout(String userName, String identityDomain) {
         this.runtimeClearLockout(userName, identityDomain);
      }

      public void clearLockout(String userName) {
         this.clearLockout(userName, (String)null);
      }

      public boolean runtimeIsLocked(String userName, String identityDomain) {
         if (userName == null) {
            throw new IllegalArgumentException(SecurityLogger.getNullParameterSupplied("runtimeIsLocked"));
         } else {
            if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
               UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".runtimeIsLockedOut(" + userName + "," + identityDomain + ")");
            }

            if (!this.caseSensitiveUserNames) {
               userName = userName.toLowerCase();
            }

            if (this.lockout_enabled && this.master_invalid_login.size() != 0) {
               this.setTimestampOfCurrentCheck();
               IdentityDomainNames userKey = new IdentityDomainNames(userName, identityDomain);
               if (this.master_invalid_login.containsKey(userKey)) {
                  InvalidLogin user_invalid_login = (InvalidLogin)this.master_invalid_login.get(userKey);
                  long when_locked = user_invalid_login.getLockedTimestamp();
                  if (when_locked != 0L) {
                     if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                        UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".runtimeIsLockedOut(" + userName + "," + identityDomain + ") true");
                     }

                     return true;
                  }
               }

               if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                  UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".runtimeIsLockedOut(" + userName + "," + identityDomain + ") false");
               }

               return false;
            } else {
               if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                  UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".runtimeIsLockedOut(" + userName + "," + identityDomain + ") false");
               }

               return false;
            }
         }
      }

      public boolean runtimeIsLocked(String userName) {
         return this.runtimeIsLocked(userName, (String)null);
      }

      public void runtimeClearLockout(String userName, String identityDomain) {
         if (userName == null) {
            throw new IllegalArgumentException(SecurityLogger.getNullParameterSupplied("runtimeClearLockout"));
         } else {
            if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
               UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".runtimeClearClockout(" + userName + "," + identityDomain + ")");
            }

            if (userName.equals("")) {
               if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                  UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".runtimeClearClockout(" + userName + "," + identityDomain + ") UserLockout clearLockout was passed an empty user name, ignoring");
               }

            } else if (!this.lockout_enabled) {
               if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                  UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".runtimeClearClockout(" + userName + "," + identityDomain + ") UserLockout clearLockout lockout not enabled, ignoring");
               }

            } else {
               AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
               AuthorizationManager authManager = SecurityServiceManager.getAuthorizationManager(kernelId, SecurityServiceManager.getAdministrativeRealmName());
               if (authManager != null) {
                  AuthenticatedSubject currSubject = SecurityServiceManager.getCurrentSubject(kernelId);
                  AdminResource res = new AdminResource("UserLockout", this.realmName, "unlockuser");
                  if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                     UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".runtimeClearClockout(" + userName + "," + identityDomain + ")  isAccessAllowed:  checking Permission for: '" + res + "', currentSubject: '" + SubjectUtils.displaySubject(currSubject) + "'");
                  }

                  if (!authManager.isAccessAllowed(currSubject, res, this.getResourceIDDContextWrapper())) {
                     if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
                        UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".runtimeClearClockout(" + userName + "," + identityDomain + ")  isAccessAllowed:  currentSubject: " + currSubject + " does not have permission to unlock user " + UsernameUtils.formatUserName(userName, identityDomain) + " in realm " + this.realmName);
                     }

                     throw new SecurityException(SecurityLogger.getSubjectDoesNotHavePermissionToUnlock(SubjectUtils.displaySubject(currSubject), userName, this.realmName));
                  }

                  this.logSuccess(userName, identityDomain);
                  SecurityLogger.logExplicitUserUnlockInfo(UsernameUtils.getTruncatedFormattedName(userName, identityDomain));
               }

            }
         }
      }

      public void runtimeClearLockout(String userName) {
         this.runtimeClearLockout(userName, (String)null);
      }

      public long getUserLockoutTotalCount() {
         if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
            UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".getUserLockoutTotalCount()");
         }

         return this.cum_user_lockout_count;
      }

      public long getInvalidLoginAttemptsTotalCount() {
         if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
            UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".getInvalidLoginAttemptsTotalCount()");
         }

         return this.cum_invalid_login_count;
      }

      public long getLoginAttemptsWhileLockedTotalCount() {
         if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
            UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".getLoginAttemptsWhileLockedTotalCount()");
         }

         return this.cum_locked_attempts_count;
      }

      public long getInvalidLoginUsersHighCount() {
         if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
            UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".getInvaliLoginUsersHighCount()");
         }

         return this.high_invalid_login_users;
      }

      public long getUnlockedUsersTotalCount() {
         if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
            UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".getUnlockedUsersTotalCount()");
         }

         return this.cum_user_unlock_count;
      }

      public long getLockedUsersCurrentCount() {
         if (UserLockoutServiceImpl.this.logger.isDebugEnabled()) {
            UserLockoutServiceImpl.this.logger.debug(this.getClass().getName() + ".getLockedUsersCurrentCount()");
         }

         return this.current_lock_count;
      }

      private synchronized WorkManager getUserLockoutWM() {
         if (UserLockoutServiceImpl.this.USERLOCKOUTWM == null) {
            UserLockoutServiceImpl.this.USERLOCKOUTWM = WorkManagerFactory.getInstance().findOrCreate("UserLockout", -1, 5);
         }

         return UserLockoutServiceImpl.this.USERLOCKOUTWM;
      }

      private ResourceIDDContextWrapper getResourceIDDContextWrapper() {
         if (this.realmManagementIDD != null && !this.realmManagementIDD.isEmpty() && PartitionUtils.getPartitionName() != null && !PartitionUtils.getPartitionName().isEmpty()) {
            ResourceIDDContextWrapper wrapper = new ResourceIDDContextWrapper((ContextHandler)null, false);
            wrapper.setResourceIdentityDomain(this.realmManagementIDD);
            return wrapper;
         } else {
            return new ResourceIDDContextWrapper(true);
         }
      }

      // $FF: synthetic method
      ServiceImpl(UserLockoutServiceConfig x1, Services x2, Object x3) throws ServiceInitializationException {
         this(x1, x2);
      }
   }
}
