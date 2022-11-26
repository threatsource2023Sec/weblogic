package weblogic.diagnostics.instrumentation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DelegatingMonitorControl extends StandardMonitorControl implements DelegatingMonitor {
   static final long serialVersionUID = -3716624702862137600L;
   private List actionList;
   private int locationType;
   private String[] compatibleActionTypes;
   private static final DiagnosticAction[] EMPTY_ACTIONS_ARR = new DiagnosticAction[0];
   private volatile DiagnosticAction[] cachedActions;

   public DelegatingMonitorControl(DelegatingMonitorControl mon) {
      super((StandardMonitorControl)mon);
      this.setLocationType(mon.getLocationType());
      this.setCompatibleActionTypes(mon.getCompatibleActionTypes());
   }

   public DelegatingMonitorControl(String type) {
      this(type, type);
   }

   public DelegatingMonitorControl(String name, String type) {
      super(name, type);
      this.actionList = null;
      this.cachedActions = EMPTY_ACTIONS_ARR;
   }

   public DiagnosticAction[] getActions() {
      return this.cachedActions;
   }

   private synchronized void computeCachedActions() {
      DiagnosticAction[] tmp = EMPTY_ACTIONS_ARR;
      int cnt = this.actionList != null ? this.actionList.size() : 0;
      if (cnt > 0) {
         tmp = new DiagnosticAction[cnt];
         tmp = (DiagnosticAction[])((DiagnosticAction[])this.actionList.toArray(tmp));
      }

      this.cachedActions = tmp;
   }

   public String[] getCompatibleActionTypes() {
      return this.compatibleActionTypes;
   }

   public void setCompatibleActionTypes(String[] compatibleActionTypes) {
      this.compatibleActionTypes = compatibleActionTypes;
   }

   public int getLocationType() {
      return this.locationType;
   }

   public void setLocationType(int locationType) {
      this.locationType = locationType;
   }

   public synchronized void addAction(DiagnosticAction action) throws DuplicateActionException, IncompatibleActionException {
      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("Adding action of type " + action.getType() + " to " + this.getName());
      }

      if (this.actionList == null) {
         this.actionList = new ArrayList();
      }

      if (this.actionList.contains(action)) {
         throw new DuplicateActionException("Action " + action + " already attached to " + this.getType());
      } else {
         String[] compatibleActionTypes = this.getCompatibleActionTypes();
         int len = compatibleActionTypes != null ? compatibleActionTypes.length : 0;
         boolean found = false;
         String actionType = action.getType();

         for(int i = 0; !found && i < len; ++i) {
            if (actionType.equals(compatibleActionTypes[i])) {
               found = true;
            }
         }

         if (!found) {
            throw new IncompatibleActionException("Attempt to use incompatible action type " + actionType + " with delegating monitor type " + this.getType());
         } else {
            this.actionList.add(action);
            if (action.requiresArgumentsCapture()) {
               if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
                  InstrumentationDebug.DEBUG_CONFIG.debug("setting arguments capture flag to true");
               }

               this.argumentsCaptureNeeded = true;
            }

            action.setDiagnosticMonitor(this);
            this.computeCachedActions();
         }
      }
   }

   public synchronized void removeAllActions() {
      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("Removing all actions from " + this.getName());
      }

      if (this.actionList != null) {
         this.actionList.clear();
         this.actionList = null;
      }

      this.argumentsCaptureNeeded = false;
      this.computeCachedActions();
   }

   public synchronized void removeAction(DiagnosticAction action) throws ActionNotFoundException {
      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("Removing action of type " + action.getType() + " from " + this.getName());
      }

      if (this.actionList != null && this.actionList.contains(action)) {
         this.actionList.remove(action);
         if (this.argumentsCaptureNeeded) {
            this.checkArgsCaptureNeeded();
         }

         this.computeCachedActions();
      } else {
         throw new ActionNotFoundException("Attempt to remove non-existent action " + action + " from " + this.getType());
      }
   }

   public synchronized boolean merge(DiagnosticMonitorControl other) {
      boolean retVal = false;
      if (super.merge(other) && other instanceof DelegatingMonitorControl) {
         DelegatingMonitorControl oM = (DelegatingMonitorControl)other;
         this.actionList = oM.actionList;
         if (this.actionList != null) {
            Iterator it = this.actionList.iterator();

            while(it.hasNext()) {
               DiagnosticAction action = (DiagnosticAction)it.next();
               action.setDiagnosticMonitor(this);
            }
         }

         this.locationType = oM.locationType;
         this.checkArgsCaptureNeeded();
         retVal = true;
         this.computeCachedActions();
      }

      return retVal;
   }

   public synchronized void unionOf(DiagnosticMonitorControl other) {
      if (other != null && other instanceof DelegatingMonitorControl) {
         super.unionOf(other);

         try {
            DelegatingMonitorControl dmc = (DelegatingMonitorControl)other;
            Iterator var3 = dmc.actionList.iterator();

            while(var3.hasNext()) {
               DiagnosticAction action = (DiagnosticAction)var3.next();
               if (!this.actionList.contains(action)) {
                  this.addAction(action);
               }
            }
         } catch (Exception var5) {
            throw new RuntimeException(var5);
         }
      }

   }

   private void checkArgsCaptureNeeded() {
      if (this.actionList != null) {
         Iterator actionIt = this.actionList.iterator();

         while(actionIt.hasNext()) {
            if (((DiagnosticAction)actionIt.next()).requiresArgumentsCapture()) {
               this.argumentsCaptureNeeded = true;
               return;
            }
         }
      }

      if (InstrumentationDebug.DEBUG_CONFIG.isDebugEnabled()) {
         InstrumentationDebug.DEBUG_CONFIG.debug("setting arguments capture flag to false");
      }

      this.argumentsCaptureNeeded = false;
   }

   void subvertArgumentsCaptureNeededCheck() {
      this.argumentsCaptureNeeded = true;
   }
}
