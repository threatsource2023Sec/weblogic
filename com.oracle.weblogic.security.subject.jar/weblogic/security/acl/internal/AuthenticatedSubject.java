package weblogic.security.acl.internal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Set;
import javax.security.auth.Subject;
import weblogic.common.internal.InteropWriteReplaceable;
import weblogic.common.internal.PeerInfo;
import weblogic.security.SecurityLogger;
import weblogic.security.principal.WLSAbstractPrincipal;
import weblogic.security.service.SecurityManager;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;
import weblogic.utils.AssertionError;

public final class AuthenticatedSubject extends AuthenticatedUser implements InteropWriteReplaceable, AbstractSubject {
   static final long serialVersionUID = -5562362296231458788L;
   public static final AuthenticatedSubject ANON = new AuthenticatedSubject(true);
   private final SealableSet principals;
   private transient SealableSet pubCredentials;
   private transient SealableSet privCredentials;
   private transient Subject subject;
   private transient boolean sealed;
   private static final byte[] IDD_SERIALIZATION_MARKER = new byte[]{-66, -17, 0, 1};

   public AuthenticatedSubject() {
      this.sealed = false;
      this.principals = new SealableSet(true);
      this.pubCredentials = new SealableSet();
      this.privCredentials = new SealableSet();
   }

   public AuthenticatedSubject(boolean readOnly, Set principals) {
      this.sealed = false;
      this.principals = new SealableSet(principals, true);
      this.pubCredentials = new SealableSet();
      this.privCredentials = new SealableSet();
      if (readOnly) {
         this.sealInternal();
      }

   }

   public AuthenticatedSubject(AuthenticatedUser au) {
      super(au);
      this.sealed = false;
      this.principals = new SealableSet(true);
      this.pubCredentials = new SealableSet();
      this.privCredentials = new SealableSet();
   }

   private AuthenticatedSubject(boolean ignore) {
      this();
      this.sealInternal();
      this.subject = new Subject(true, this.principals, this.pubCredentials, this.privCredentials);
   }

   public AuthenticatedSubject(Subject subject) {
      this.sealed = false;
      this.principals = new SealableSet(subject.getPrincipals(), true);
      this.pubCredentials = new SealableSet(subject.getPublicCredentials());
      this.privCredentials = new SealableSet(subject.getPrivateCredentials());
      this.subject = subject;
   }

   public static AuthenticatedSubject getFromSubject(final Subject subject) {
      AuthenticatedSubject AS = (AuthenticatedSubject)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            try {
               Iterator i = subject.getPrivateCredentials().iterator();

               Object objx;
               do {
                  if (!i.hasNext()) {
                     return new AuthenticatedSubject(subject);
                  }

                  objx = i.next();
               } while(!(objx instanceof AuthenticatedSubject));

               return objx;
            } catch (ConcurrentModificationException var7) {
               Object[] lprivCredentials = subject.getPrivateCredentials().toArray();
               Object[] var3 = lprivCredentials;
               int var4 = lprivCredentials.length;

               for(int var5 = 0; var5 < var4; ++var5) {
                  Object obj = var3[var5];
                  if (obj instanceof AuthenticatedSubject) {
                     lprivCredentials = null;
                     return obj;
                  }
               }

               lprivCredentials = null;
               return new AuthenticatedSubject(subject);
            }
         }
      });
      return AS;
   }

   public Set getPrincipals() {
      return this.principals;
   }

   public Set getPrincipals(Class c) {
      return this.getClassSubset(this.principals, c);
   }

   public Set getPublicCredentials() {
      return this.pubCredentials;
   }

   public Set getPublicCredentials(Class c) {
      return this.getClassSubset(this.pubCredentials, c);
   }

   public Set getPrivateCredentials(AbstractSubject kernelID) {
      SecurityManager.checkKernelIdentity((AuthenticatedSubject)kernelID);
      return this.privCredentials;
   }

   public Set getPrivateCredentials(AbstractSubject kernelID, Class c) {
      SecurityManager.checkKernelIdentity((AuthenticatedSubject)kernelID);
      return this.getClassSubset(this.privCredentials, c);
   }

   private Set getClassSubset(Set principals, Class c) {
      if (c == null) {
         throw new NullPointerException(SecurityLogger.getNullClass());
      } else {
         HashSet ret = new HashSet();
         Iterator i = principals.iterator();

         while(i.hasNext()) {
            Object principal = i.next();
            if (c.isAssignableFrom(principal.getClass())) {
               ret.add(principal);
            }
         }

         return ret;
      }
   }

   public synchronized Subject getSubject() {
      if (this.subject != null) {
         return this.subject;
      } else {
         Subject tmp = new Subject(false, this.principals, this.pubCredentials, this.privCredentials);
         synchronized(tmp) {
            this.subject = tmp;
         }

         AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               AuthenticatedSubject.this.subject.getPrivateCredentials().add(AuthenticatedSubject.this);
               return null;
            }
         });
         return this.subject;
      }
   }

   public void seal(AbstractSubject kernelID) {
      SecurityManager.checkKernelIdentity((AuthenticatedSubject)kernelID);
      this.sealInternal();
   }

   public void setReadOnly(AbstractSubject kernelID) {
      this.seal(kernelID);
   }

   public boolean isSealed() {
      return this.sealed;
   }

   public boolean isReadOnly() {
      return this.sealed;
   }

   public Object doAs(AbstractSubject kernelId, PrivilegedAction action) {
      if (action == null) {
         throw new SecurityException(SecurityLogger.getNullAction());
      } else {
         int sizeBeforePush = SubjectManager.getSubjectManager().getSize();
         SubjectManager.getSubjectManager().pushSubject(kernelId, this);
         Object actionResult = null;
         boolean var9 = false;

         try {
            var9 = true;
            actionResult = action.run();
            var9 = false;
         } finally {
            if (var9) {
               int sizeBeforePop = SubjectManager.getSubjectManager().getSize();

               while(sizeBeforePop-- > sizeBeforePush) {
                  SubjectManager.getSubjectManager().popSubject(kernelId);
               }

            }
         }

         int sizeBeforePop = SubjectManager.getSubjectManager().getSize();

         while(sizeBeforePop-- > sizeBeforePush) {
            SubjectManager.getSubjectManager().popSubject(kernelId);
         }

         return actionResult;
      }
   }

   public Object doAs(AbstractSubject kernelId, PrivilegedExceptionAction action) throws PrivilegedActionException {
      if (action == null) {
         throw new SecurityException(SecurityLogger.getNullAction());
      } else {
         int sizeBeforePush = SubjectManager.getSubjectManager().getSize();
         SubjectManager.getSubjectManager().pushSubject(kernelId, this);
         Object actionResult = null;
         boolean var11 = false;

         try {
            var11 = true;
            actionResult = action.run();
            var11 = false;
         } catch (RuntimeException var12) {
            throw var12;
         } catch (Exception var13) {
            throw new PrivilegedActionException(var13);
         } finally {
            if (var11) {
               int sizeBeforePop = SubjectManager.getSubjectManager().getSize();

               while(sizeBeforePop-- > sizeBeforePush) {
                  SubjectManager.getSubjectManager().popSubject(kernelId);
               }

            }
         }

         int sizeBeforePop = SubjectManager.getSubjectManager().getSize();

         while(sizeBeforePop-- > sizeBeforePush) {
            SubjectManager.getSubjectManager().popSubject(kernelId);
         }

         return actionResult;
      }
   }

   private void sealInternal() {
      this.sealed = true;
      this.principals.seal();
      this.pubCredentials.seal();
      this.privCredentials.seal();
   }

   private void readObject(ObjectInputStream stream) throws IOException, ClassNotFoundException {
      stream.defaultReadObject();
      this.pubCredentials = new SealableSet();
      this.privCredentials = new SealableSet();
   }

   public int hashCode() {
      return this.principals == null ? 0 : this.principals.hashCode();
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (o == null) {
         return false;
      } else if (!(o instanceof AuthenticatedSubject)) {
         return false;
      } else {
         AuthenticatedSubject other = (AuthenticatedSubject)o;
         return this.principals.equals(other.principals);
      }
   }

   public Object interopWriteReplace(PeerInfo peerInfo) throws IOException {
      if (peerInfo.compareTo(PeerInfo.VERSION_1221) >= 0) {
         return this;
      } else if (peerInfo.getMajor() < 7) {
         return this.convertToAuthenticatedUser(this);
      } else {
         Set principals = this.getPrincipals();
         boolean isFirst = true;
         boolean isSameIdd = true;
         String idd = null;
         Iterator var6 = principals.iterator();

         while(var6.hasNext()) {
            Principal principal = (Principal)var6.next();
            if (principal instanceof WLSAbstractPrincipal) {
               String currentIdd = ((WLSAbstractPrincipal)principal).getIdentityDomain();
               if (currentIdd != null && !currentIdd.isEmpty()) {
                  if (isFirst) {
                     isFirst = false;
                     idd = currentIdd;
                     continue;
                  }

                  if (currentIdd.equals(idd)) {
                     continue;
                  }

                  isSameIdd = false;
                  break;
               }

               idd = null;
               isSameIdd = false;
               break;
            }
         }

         if (idd != null && isSameIdd) {
            this.setName(idd);
            this.setSignature(IDD_SERIALIZATION_MARKER);
         }

         return this;
      }
   }

   private Object convertToAuthenticatedUser(AuthenticatedSubject as) {
      try {
         Class clzSSM = Class.forName("weblogic.security.service.SecurityServiceManager");
         Method mtdConvert = clzSSM.getMethod("convertToAuthenticatedUser", AuthenticatedUser.class);
         return mtdConvert.invoke((Object)null, as);
      } catch (Exception var4) {
         throw new AssertionError(SecurityLogger.getCouldNotConvertASToAU("" + as));
      }
   }

   public String toString() {
      return "principals=" + this.principals;
   }

   protected boolean hasSerializedIdentityDomain() {
      byte[] signature = this.getSignature();
      return signature != null && signature.length == 4 ? Arrays.equals(signature, IDD_SERIALIZATION_MARKER) : false;
   }

   public String getSerializedIdentityDomain() {
      return this.hasSerializedIdentityDomain() ? this.getName() : null;
   }

   private static final class SealableSet extends AbstractSet implements Set, Serializable {
      static final long serialVersionUID = -6020057914807495674L;
      final LinkedList elements;
      transient LinkedHashSet elementsToCompare;
      private boolean sealed;
      private boolean hashCodeValid;
      private int hashCode;
      private final boolean isPrincipalSet;

      SealableSet() {
         this(false);
      }

      SealableSet(boolean isPrincipalSet) {
         this.elementsToCompare = null;
         this.sealed = false;
         this.hashCodeValid = false;
         this.elements = new LinkedList();
         this.isPrincipalSet = isPrincipalSet;
      }

      SealableSet(Set s) {
         this(s, false);
      }

      SealableSet(final Set s, boolean isPrincipalSet) {
         this.elementsToCompare = null;
         this.sealed = false;
         this.hashCodeValid = false;
         this.elements = (LinkedList)AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
               return new LinkedList(s);
            }
         });
         this.isPrincipalSet = isPrincipalSet;
      }

      public boolean add(Object o) {
         if (this.sealed) {
            throw new SecurityException(SecurityLogger.getAttemptingToModifySealedSubject());
         } else if (o == null) {
            throw new NullPointerException();
         } else if (this.isPrincipalSet && !(o instanceof Principal)) {
            throw new SecurityException(SecurityLogger.getNotAPrincipal(o.getClass().getName()));
         } else if (!this.elements.contains(o)) {
            boolean bOk = this.elements.add(o);
            if (this.elementsToCompare != null) {
               this.elementsToCompare.add(o);
            }

            return bOk;
         } else {
            return false;
         }
      }

      public int size() {
         return this.elements.size();
      }

      public Iterator iterator() {
         return new Iterator() {
            ListIterator i;

            {
               this.i = SealableSet.this.elements.listIterator(0);
            }

            public boolean hasNext() {
               return this.i.hasNext();
            }

            public Object next() {
               return this.i.next();
            }

            public void remove() {
               if (SealableSet.this.sealed) {
                  throw new SecurityException(SecurityLogger.getAttemptingToModifySealedSubject());
               } else {
                  SealableSet.this.hashCodeValid = false;
                  this.i.remove();
               }
            }
         };
      }

      public void clear() {
         if (this.sealed) {
            throw new SecurityException(SecurityLogger.getAttemptingToModifySealedSubject());
         } else {
            this.hashCodeValid = false;
            this.elements.clear();
         }
      }

      void seal() {
         this.sealed = true;
      }

      public int hashCode() {
         if (!this.hashCodeValid) {
            this.hashCode = super.hashCode();
            this.hashCodeValid = true;
         }

         return this.hashCode;
      }

      public boolean equals(Object o) {
         if (o == this) {
            return true;
         } else if (!(o instanceof Set)) {
            return false;
         } else {
            Collection c = (Collection)o;
            if (c.size() != this.size()) {
               return false;
            } else {
               try {
                  if (this.elementsToCompare == null) {
                     this.elementsToCompare = new LinkedHashSet(this.elements);
                  }

                  return this.elementsToCompare.containsAll(c);
               } catch (ClassCastException var4) {
                  return false;
               } catch (NullPointerException var5) {
                  return false;
               }
            }
         }
      }
   }
}
