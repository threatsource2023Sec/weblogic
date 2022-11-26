# Caution: This file is part of the command scripting implementation. 
# Do not edit or move this file because this may cause commands and scripts to fail. 
# Do not try to reuse the logic in this file or keep copies of this file because this 
# could cause your scripts to fail when you upgrade to a different version.
# Copyright (c) 2004,2016, Oracle and/or its affiliates. All rights reserved.

"""
Online WLST Initialization script that sets up the runtime environment for WLST
This init script file defines all the WLST commands that can be run in online mode.
There are a few Un-documented functions which should not be used if you import this
script, the behavior of those functions might change in future releases.

"""

# Define Imports that will be used
import sys
import java
import jarray
import weblogic.management.scripting.WLScriptContext as wlctx
from jarray import array
from java import *
from weblogic import *
import weblogic.version
from java.lang import *
from javax.management import *
from weblogic.management.scripting import ScriptException
from org.python.core import PyArray
from org.python.core import PyClass
from org.python.core import PyComplex
from org.python.core import PyDictionary
from org.python.core import PyException
from org.python.core import PyFile
from org.python.core import PyFloat
from org.python.core import PyInteger
from org.python.core import PyList
from org.python.core import PyLong
from org.python.core import PyObject
from org.python.core import PyString
from weblogic.management.scripting.utils import WLSTUtil
from weblogic.management.scripting.utils import WLSTMsgTextFormatter
import wlstGlobals

# Define all the global variables here

global theInterpreter
if theInterpreter == None:
    theInterpreter=WLSTUtil.ensureInterpreter();

WLS_ON=WLSTUtil.ensureWLCtx(theInterpreter)
nmService=WLS_ON.getNodeManagerService();
fmtr=WLS_ON.getWLSTMsgFormatter()

home=None
adminHome=None
cmo=None
CMO=None
mbs=None
cmgr=None
domainRuntimeService=None
runtimeService=None
editService=None

### deprecated -- use wlstGlobals._editService instead
_editService=None

typeService=None
myps1="wls:/offline> "
sys.ps1=myps1
ncPrompt="wls:/offline> "
serverName = "";
domainName = "";
connected = "false"
lastPrompt = ""
domainType = ""
promptt = ""
hideProm = "false"
username = ""
wlsversion = weblogic.version.getVersions()
version = weblogic.version.getVersions()
isAdminServer = ""
recording = "false"
scriptMode = "false"
dcCalled = "false"
exitonerror = "true"
wlstPrompt = "true"
wlstVersion = "dev2dev version"
oldhook=sys.displayhook
true=1
false=0
LAST = None

# Un-documented
# @exclude
def init():
  'This function initializes all the global variables to their default values'
  global cmo,home,adminHome,dcCalled,lastPrompt,domainName,hideProm,domainType
  global showStack,wlsversion,mbs,connected,myps1,promptt,ncPrompt
  global serverName,cmgr, domainRuntimeService, runtimeService, editService, typeService, scriptMode, LAST
  cmo=None
  home=None
  adminHome=None
  cmgr=None
  dcCalled=None
  domainName=None
  domainType=None
  showStack=None
  wlsversion=weblogic.version.getVersions()
  version=weblogic.version.getVersions()
  mbs=None
  connected="false"
  serverName=None
  scriptMode="false"
  promptt=""
  myps1=ncPrompt
  LAST=None

"""
All Control Related Commands start here
-----------------------------------------------------------------
"""

def disconnect(force="false"):
  try:
    global connected,cmo,home,adminHome,mbs,promptt
    cmo=None
    mbs=None
    connected="false"
    adminHome=None
    home=None
    promptt=""
    myps1=ncPrompt
    WLS_ON.dc(force)
    updateGlobals()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
  WLSTUtil.setupOffline(theInterpreter)
  evaluatePrompt()

"""
End of all Control Commands
---------------------------------------------------------------------
"""

"""
Start Browse Commands
-----------------------------------------------------------------------
"""
def cd(mbeanName):
  """
  This function allows a user to navigate from one MBean instance
  to another instance and viceversa
  """
  try:
    global cmo, LAST
    WLS_ON.cd(mbeanName)
    WLS_ON.print("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return cmo
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def prompt(myPrompt="xx"):
  """
  Toggles the prompt. Useful when the prompt gets longer.
  """
  try:
    global hideProm,wlstPrompt,myps1
    if myPrompt=="off":
      wlstPrompt="false"
      sys.ps1=">>>"
      myps1=">>>"
      return
    elif myPrompt=="on":
      wlstPrompt="true"
      updateGlobals()
    else:
      wlstPrompt="true"
      if hideProm=="false":
        hideProm="true"
      elif hideProm == "true":
        hideProm = "false"
      updateGlobals()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def pwd():
  global LAST
  thePr = WLS_ON.getPrompt()
  if thePr == "":
    LAST = WLS_ON.getTree()+":/"
    return WLS_ON.getTree()+":/"
  LAST = WLS_ON.getTree()+":/"+thePr
  return WLS_ON.getTree()+":/"+thePr

"""
End of all Browse Commands
-------------------------------------------------------------------------
"""

"""
Start All Deployment Commands
-------------------------------------------------------------------------
"""
def deploy(appName,path,targets="",stageMode=None,planPath=None,**options):
  global LAST
  try:
    LAST = WLS_ON.deploy(appName,
                  path,
                  targets,
                  stageMode,
                  planPath,
                  options)
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def redeploy(appName, planPath=None, **options):
  global LAST
  try:
    LAST = WLS_ON.redeploy(appName, planPath, options)
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def undeploy(appName,targets=None, **options):
  global LAST
  try:
    LAST = WLS_ON.undeploy(appName, targets, options)
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

"""
End of All Deployment Commands
------------------------------------------------------------------------------
"""

"""
Start of All JSR-88 Deployment Commands
------------------------------------------------------------------------------
"""
      
def loadApplication(appPath, planPath=None, createPlan='true'):
  global LAST
  'This will load the application and return the WLSTPlan object' 
  hideDisplay()
  LAST = WLS_ON.loadApplication(appPath, planPath, createPlan)
  return LAST

def loadApp(appPath, planPath=None):
  global LAST
  LAST = loadApplication(appPath, planPath)
  return LAST
  
def distributeApplication(appPath, planPath=None, targets=None, **options ):
  global LAST
  hideDisplay()
  LAST = WLS_ON.distributeApplication(appPath, planPath, targets, options)
  return LAST

def distributeApp(appPath, planPath=None, targets=None, **options ):
  return distributeApplication(appPath, planPath, targets, options)

def appendToExtensionLoader(codeSource, targets=None, **options ):
  global LAST
  hideDisplay()
  LAST = WLS_ON.appendToExtensionLoader(codeSource, targets, options)
  return LAST

def listApplications():
  try:
    WLS_ON.listApplications()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def startApplication(appName, **options):
  global LAST
  hideDisplay()
  LAST = WLS_ON.startApplication(appName, options)
  return LAST

def startApp(appName, **options):
  return startApplication(appName, options)

def stopApplication(appName, **options):
  global LAST
  hideDisplay()
  LAST = WLS_ON.stopApplication(appName, options)
  return LAST

def stopApp(appName, block="true"):
  return stopApplication(appName, block)

def updateApplication(appName, planPath, **options):
  global LAST
  hideDisplay()
  LAST = WLS_ON.updateApplication(appName, planPath, options)
  return LAST

def updateApp(appName, planPath, **options):
  hideDisplay()
  return updateApplication(appName, planPath, options)

def getWLDM():
  global LAST
  hideDisplay()
  LAST = WLS_ON.getWLDM()
  return LAST

"""
End of All JSR-88 Deployment Commands
------------------------------------------------------------------------------
"""

"""
Start of All CAM Configuration pulling
------------------------------------------------------------------------------
"""

def resync(compName):
  global LAST
  try:
    res = wlstGlobals._editService.resync(compName)
    updateGlobals()
    hideDisplay()
    LAST = res
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def resyncAll():
  global LAST
  try:
    res = wlstGlobals._editService.resyncAll()
    updateGlobals()
    hideDisplay()
    LAST = res
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def pullComponentChanges(compName):
  global hideProm
  try:
    wlstGlobals._editService.pullComponentChanges(compName)
    updateGlobals()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def showComponentChanges(compName=None):
  global hideProm
  try:
    wlstGlobals._editService.showComponentChanges(compName)
    updateGlobals()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def enableOverwriteComponentChanges():
  global LAST
  try:
    res = wlstGlobals._editService.enableOverwriteComponentChanges()
    updateGlobals()
    hideDisplay()
    LAST = res
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

"""
End of All CAM Configuration pulling
------------------------------------------------------------------------------
"""

"""
Start of All Edit Commands
-----------------------------------------------------------------------------
"""

def createEditSession(name,
                      description=None):
  try:
    wlstGlobals._editService.createEditSession(name, description)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def destroyEditSession(name, force = false):
  try:
    wlstGlobals._editService.destroyEditSession(name, force)
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def showEditSession(name=None):
  try:
    wlstGlobals._editService.showEditSession(name)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def activate(timeout=300000,
             block='true'):
  global LAST
  try:
    res = wlstGlobals._editService.activate(timeout,
                                block)
    updateGlobals()
    hideDisplay()
    LAST = res
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def startEdit(waitTimeInMillis=0,
              timeOutInMillis=-1,
              exclusive="false"):
  global hideProm
  try:
    wlstGlobals._editService.startEdit(waitTimeInMillis,
                          timeOutInMillis,
                          exclusive)
    evaluatePrompt()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


def save():
  global hideProm
  try:
    wlstGlobals._editService.save()
    evaluatePrompt()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def resolve(stopOnConflict=false):
  global hideProm
  try:
    wlstGlobals._editService.resolve(stopOnConflict)
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def undo(unactivatedChanges='false',
         defaultAnswer='from_prompt'):
  global hideProm
  try:
    op='y'
    if defaultAnswer=='from_prompt':
      op=raw_input(fmtr.getUndoChanges())
    else:
      op=defaultAnswer
    if op=='y':
      wlstGlobals._editService.undo(unactivatedChanges)
    else:
      WLS_ON.println(fmtr.getUndoNotPerformed())
    evaluatePrompt()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


def stopEdit(defaultAnswer='from_prompt'):
  global hideProm
  try:
    op='y'
    if defaultAnswer=='from_prompt':
      op=raw_input(fmtr.getStopChanges())
    else:
      op=defaultAnswer
    if op=='y':
      wlstGlobals._editService.stopEdit()
      WLS_ON.println(fmtr.getEditStopped())
    else:
      WLS_ON.println(fmtr.getEditNotStopped())
    evaluatePrompt()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


def cancelEdit(defaultAnswer='from_prompt'):
  global hideProm
  try:
    op='y'
    if defaultAnswer=='from_prompt':
      op=raw_input(fmtr.getCancelChanges())
    else:
      op=defaultAnswer
    if op=='y':
      wlstGlobals._editService.cancelEdit()
      WLS_ON.println(fmtr.getEditCancelled())
    else:
      WLS_ON.println(fmtr.getEditNotStopped())
    evaluatePrompt()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def showChanges(onlyInMemory="false"):
  try:
    wlstGlobals._editService.showChanges(onlyInMemory)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())      

def isRestartRequired(attributeName=None):
  global LAST
  try:
    hideDisplay()
    LAST = wlstGlobals._editService.isRestartRequired(attributeName)
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())      

def validate():
  global LAST
  try:
    hideDisplay()
    LAST = wlstGlobals._editService.validate()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())    

def getActivationTask():
  global hideProm, LAST
  try:
    LAST = wlstGlobals._editService.getActivationTask()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def create(name, childMBeanType=None, baseProviderType=None):
  global LAST
  try:
    hideDisplay()
    LAST = WLS_ON.create(name, childMBeanType, baseProviderType)
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def destroy(name, childMBeanType=None):
  return delete(name, childMBeanType)

def delete(name, childMBeanType=None):
  global LAST
  try:
    hideDisplay()
    LAST = WLS_ON.delete(name,childMBeanType)
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      

def get(attrName):
  global LAST
  try:
    getObj = WLS_ON.get(attrName)
    updateGlobals()
    LAST = getObj
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def getMBean(mbeanPath=None):
  global LAST
  hideDisplay()
  LAST = WLS_ON.getMBean(mbeanPath)
  return LAST

def set(attrName, value):
  try:
    setObj = WLS_ON.set(attrName,value)
    updateGlobals()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def setEncrypted(attrName, propertyName, configFile=None, secretFile=None):
  try:
    WLS_ON.setEncrypted(attrName, propertyName, configFile, secretFile)
    updateGlobals()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def invoke(methodName, parameters, signatures):
  global LAST
  try:
    invokeObj = WLS_ON.invoke(methodName,
                              parameters,
                              signatures)
    updateGlobals()
    hideDisplay()
    LAST = invokeObj
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


# invoke clashes with java.lang.invoke  
# This function is available in case of problems with the namespace...
# it is a line-by-line exact copy of invoke()
# A caller, like wlstModule, has already overridden the invoke in this file with
# ane in wlstModule.  So wlstInvoke CAN NOT simply call invoke.  It will call back into
# wlstModule and then have a stack overflow with an infinite loop!

def wlstInvoke(methodName, parameters, signatures):
  global LAST
  try:
    invokeObj = WLS_ON.invoke(methodName,
                              parameters,
                              signatures)
    updateGlobals()
    hideDisplay()
    LAST = invokeObj
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


def encrypt(obj, domainDir=None):
  global LAST
  try:
    result = WLS_ON.encrypt(obj, domainDir)
    hideDisplay()
    LAST = result
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


"""
End of All Edit Functions
--------------------------------------------------------------------
"""

"""
Start of All Information Commands
----------------------------------------------------------------------
"""

import java.util.ArrayList
import java.util.HashMap
def _getList(children, attrType):
    if ( children == None):
        return
    # split into a list of children
    childList = children.split("\n");
    lsc_result = java.util.ArrayList()
    lsa_result = java.util.HashMap()
    lso_result = java.util.HashMap()
    result = java.util.ArrayList()
    if attrType == None:
      for child in childList:
        # get a child info into a list
        if(java.lang.String(child).startsWith("d")):
          childInfo = child.rstrip().split("   ");
          length = len(childInfo);          
          result.add(childInfo[1])
        elif (java.lang.String(child).startsWith("-")):
          childInfo = child.rstrip().split();
          # this will add the attribute names 
          result.add(childInfo[1])
      return result
      
    for child in childList:
        # get a child info into a list
        if(java.lang.String(child).startsWith("d")):
          #Parsing nodes here.  Node names may have spaces, so use split("   ") to separate names from permissions
          childInfo = child.rstrip().split("   ");         
          length = len(childInfo);
          lsc_result.add(childInfo[1])
        elif (java.lang.String(child).startsWith("-")):
          # Parsing attributes here.  Attributes names may not contain spaces, therefore it's OK to use no-arg split()
          childInfo = child.rstrip().split();         
          if attrType == "a":
            value = ""
            for x in range(2, (len(childInfo))):
              value = value + childInfo[x] + " "
            # this will add the attribute names and values
            lsa_result.put(childInfo[1], value)
          else:
            if (len(childInfo) == 4):
              lso_result.put(childInfo[1], childInfo[2])                    
            if (len(childInfo) == 5):
              lso_result.put(childInfo[1], childInfo[2]+"||"+childInfo[4])
                          
    if attrType == "a":
      return lsa_result
    if attrType == "c":
      return lsc_result
    if attrType == "o":
      return lso_result
    if lsc_result.size() > 0:
      return lsc_result
    return lsa_result

def ls(attrName=None, returnMap="false", returnType=None, inheritance="false"):
  global LAST
  try:
    obj = WLS_ON.ls(attrName, returnType, inheritance)
    WLS_ON.println("")
    hideDisplay()
    if returnType == None:
       returnType = attrName
    if returnMap == "true":
      LAST = _getList(obj, returnType)
      return LAST
    LAST = obj
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


def dumpVariables():
  try:
    WLS_ON.dumpVariables()
    WLS_ON.println('exitonerror'+WLS_ON.calculateTabSpace("exitonerror",30)+exitonerror)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


def man(attrName=None):
  global LAST
  'This will show all the help for commands ' 
  hideDisplay()
  LAST = WLS_ON.man(attrName)
  return LAST
  

def manual(attrName=None):
  global LAST
  'This will show all the help for commands ' 
  LAST = man(attrName)
  return LAST
  
def listChildTypes(parent=None):
  global LAST
  'This will show all the children types of cmo or parent' 
  hideDisplay()
  try:
      LAST = WLS_ON.listChildTypes(parent)
      return LAST
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage()) 
      
def getMBI(mbeanType=None):
  global LAST
  'This will return the model mbean info for the type specified' 
  hideDisplay()
  try:
      LAST = WLS_ON.getMBI(mbeanType)
      return LAST
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def getConfigManager():
  global LAST
  'This will return the configuration manager' 
  hideDisplay()
  try:
      LAST = WLS_ON.getConfigManager()
      return LAST
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
  

def find(name=None, type=None, searchInstancesOnly="true"):
  global LAST
  'This will show all the children types of cmo ' 
  hideDisplay()
  try:
      LAST = WLS_ON.find(name,type,searchInstancesOnly)
      return LAST
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def storeUserConfig(userConfigFile=None, userKeyFile=None, nm='false'):
  try:
    WLS_ON.storeUserConfig(userConfigFile, userKeyFile, nm)
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

from weblogic.management.scripting.utils import WLSTUtil

def state(name, type=None, returnMap="false"):
  global LAST
  try:
    LAST = WLS_ON.state(name,type)     
    if returnMap == "true":
      return LAST
    
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def getProgressAsXml(name):
  global LAST
  try:
    LAST = WLS_ON.progress(name)
    return LAST
    
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def threadDump(writeToFile="true", fileName=None, serverName = None):
      global LAST
      result = WLS_ON.threadDump(writeToFile, fileName, serverName)
      hideDisplay()
      LAST = result
      return LAST
      
def currentTree():
    global LAST
    if domainType == tree.DEPRECATED_ADMIN_TREE or wlstGlobals.domainType == tree.DEPRECATED_ADMIN_TREE:
      LAST = config
      return LAST
    elif domainType == tree.DEPRECATED_RUNTIME_TREE or wlstGlobals.domainType == tree.DEPRECATED_RUNTIME_TREE:
      LAST = runtime
      return LAST
    elif domainType == tree.CUSTOM_TREE or wlstGlobals.domainType == tree.CUSTOM_TREE:
      LAST = custom
      return LAST
    elif domainType == tree.DOMAIN_CUSTOM_TREE or wlstGlobals.domainType == tree.DOMAIN_CUSTOM_TREE:
      LAST = domainCustom
      return LAST
    elif domainType == tree.EDIT_CUSTOM_TREE or wlstGlobals.domainType == tree.EDIT_CUSTOM_TREE:
      LAST = editCustom
      return LAST
    elif domainType == tree.JNDI_TREE or wlstGlobals.domainType == tree.JNDI_TREE:
      LAST = jndi
      return LAST
    elif domainType == tree.CONFIG_RUNTIME_TREE or wlstGlobals.domainType == tree.CONFIG_RUNTIME_TREE:
      LAST = serverConfig
      return LAST
    elif domainType == tree.RUNTIME_RUNTIME_TREE or wlstGlobals.domainType == tree.RUNTIME_RUNTIME_TREE:
      LAST = serverRuntime
      return LAST
    elif domainType == tree.RUNTIME_DOMAINRUNTIME_TREE or wlstGlobals.domainType == tree.RUNTIME_DOMAINRUNTIME_TREE:
      LAST = domainRuntime
      return LAST
    elif domainType == tree.CONFIG_DOMAINRUNTIME_TREE or wlstGlobals.domainType == tree.CONFIG_DOMAINRUNTIME_TREE:
      LAST = domainConfig
      return LAST
    elif domainType == tree.EDIT_TREE or wlstGlobals.domainType == tree.EDIT_TREE:
      LAST = edit
      return LAST
    else:
      LAST = edit
      return LAST

def addListener(mbean, attributeNames=None, logFile=None, listenerName=None):
  try:
    WLS_ON.watch(mbean, attributeNames, logFile, listenerName)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def removeListener(mbean=None, listenerName=None):
  try:
    WLS_ON.removeWatch(mbean, listenerName)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def showListeners():
  try:
    WLS_ON.showWatches()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def lookup(name, childMBeanType=None):
  global LAST
  try:
    hideDisplay()
    LAST = WLS_ON.lookup(name,childMBeanType)
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
def viewMBean(mbean):
  try:
    LAST = WLS_ON.viewMBean(mbean)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      

def getPath(mbean):
  try:
    LAST = WLS_ON.getPath(mbean)
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

"""
End of All Information Commands
----------------------------------------------------------------------
"""

"""
Start of All Tree related commands
-----------------------------------------------------------------------
"""

def jndi(serverName=None):
  global LAST
  try:
    WLS_ON.jndi(serverName)
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def runtime():
  global LAST
  try:
    WLS_ON.cdToRuntime()
    WLS_ON.println("")
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
      WLS_ON.println(e.getMessage())

def config():
  global LAST
  try:
    WLS_ON.cdToConfig()
    WLS_ON.println("")
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
      WLS_ON.println(e.getMessage())

def custom(objectNamePattern=None):
  global LAST
  try:
    WLS_ON.cdToCustom(objectNamePattern)
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def domainCustom(objectNamePattern=None):
  global LAST
  try:
    WLS_ON.cdToDomainCustom(objectNamePattern)
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def editCustom(objectNamePattern=None):
  global LAST
  try:
    WLS_ON.cdToEditCustom(objectNamePattern)
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def adminConfig():
  global LAST
  try:
    WLS_ON.adminConfig()
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def configRuntime():
  global LAST
  try:
    WLS_ON.configRuntime()
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def serverConfig():
  global LAST
  try:
    WLS_ON.configRuntime()
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def serverRuntime():
  global LAST
  try:
    WLS_ON.runtimeRuntime()
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


def domainConfig():
  global LAST
  try:
    WLS_ON.configDomainRuntime()
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


def domainRuntime():
  global LAST
  try:
    WLS_ON.runtimeDomainRuntime()
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

# Un-documented
# @exclude
def configEdit(name=None):
  global LAST
  try:
    WLS_ON.configEdit(name)
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    LAST = cmo
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


def edit(name=None):
  configEdit(name)

def findService(serviceName, serviceType, location=None):
  global LAST
  try:
    LAST = WLS_ON.findService(serviceName,
                              serviceType,
                              location)
    WLS_ON.println("")
    updateGlobals()
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

"""
End of All Tree related Commands
----------------------------------------------------------------------
"""

"""
Start of All LifeCycle Related Commands
------------------------------------------------------------------------
"""
def shutdown(name=None,
             entityType=None,
             ignoreSessions='false',
             timeOut=0,
             force='false',
             block='true',
             props=None,
             waitForAllSessions='false'):
  try:
    global cmo,domainName,domainType,showStack,version,mbs,connected,myps1
    global ncPrompt,serverName
    shutdownSuccess = WLS_ON.isShutdownSuccessful()
    if name == None:
      sdCurrentServer = WLS_ON.shutdown(serverName,
                                        entityType,
                                        ignoreSessions,
                                        timeOut,
                                        force,
                                        block,
                                        props,
                                        waitForAllSessions)
    else:
      sdCurrentServer = WLS_ON.shutdown(name,
                                        entityType,
                                        ignoreSessions,
                                        timeOut,
                                        force,
                                        block,
                                        props,
                                        waitForAllSessions)
    if sdCurrentServer == 1:
      connected="false"
      mbs=None
      cmo=None
      domainName=None
      domainType=None
      showStack=None
      version=None
      myps1=ncPrompt
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def startServer(adminServerName="myserver",
                domainName="mydomain",
                url=None,
                username=None,
                password=None,
                domainDir=".",
                block='true',
                timeout=120000,
                useNM='true',
                overWriteRootDir='false',
                serverLog=None,
                systemProperties=None,
                jvmArgs = None,
                spaceAsJvmArgsDelimiter = 'false'):
  global LAST
  try:
    LAST = WLS_ON.startSvr(domainName,
                           adminServerName,
                           username,
                           password,
                           url,
                           domainDir,
                           "false",
                           overWriteRootDir,
                           block,
                           timeout,
                           useNM,
                           serverLog,
                           systemProperties,
                           jvmArgs,
                           spaceAsJvmArgsDelimiter)
    return LAST
    updateGlobals()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def start(name, 
      type=None,
      listenAddress=None,
      port=-1,
      block="true",
      props=None,
      disableMsiMode="false"):
  try:
    global LAST
    LAST = WLS_ON.start(name,
                 type,
                 listenAddress,
                 port,
                 block,
                 props,
                 disableMsiMode)
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def suspend(sname=None,
             ignoreSessions="false",
             timeOut=0,
             force="false",
             block = "true"):
  try:
    global LAST
    if sname == None:
      sname = serverName
    LAST = WLS_ON.suspend(sname,
                   ignoreSessions,
                   timeOut,
                   force,
                   block) 
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def resume(sname=None,
           block="true"):
  global LAST
  try:
    if sname == None:
      sname = serverName
    LAST = WLS_ON.resume(sname, block) 
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def migrate(sname,
            destinationName,
            sourceDown='true',
            destinationDown='false',
            migrationType='all'):
  try:
      if sname == None:
        sname = serverName
      WLS_ON.migrate(sname,
                 destinationName,
                 sourceDown,
                 destinationDown,
                 migrationType)
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def migrateServer(sname,
                  machineName,
                  sourceDown='false',
                  destinationDown='false'):
  try:
      WLS_ON.migrateServer(sname,
                       machineName,
                       sourceDown,
                       destinationDown)
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def migrateAll(sname,
                  destinationName,
                  sourceDown='false',
                  destinationDown='false'):
  try:
      WLS_ON.migrateAll(sname,
                       destinationName,
                       sourceDown,
                       destinationDown)
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def softRestart(name,
      block="true",
      props=None,
      type=None):
  try:
    global LAST
    LAST = WLS_ON.softRestart(name,
                 block,
                 props,
                 type)
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def scaleUp(clusterName,
      numServers=1,
      updateConfiguration=false,
      block= true,
      timeoutSeconds=600,
      type="DynamicCluster"):
  try:
    global LAST
    LAST = WLS_ON.scaleUp(clusterName,
                 numServers,
                 updateConfiguration,
                 block,
                 timeoutSeconds,
                 type)
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def scaleDown(clusterName,
            numServers=1,
            updateConfiguration=false,
            block=true,
            timeoutSeconds=300,
            type="DynamicCluster"):
    try:
        global LAST
        LAST = WLS_ON.scaleDown(clusterName,
                              numServers,
                              updateConfiguration,
                              block,
                              timeoutSeconds,
                              type)
        hideDisplay()
        return LAST
    except ScriptException,e:
        updateGlobals()
        if exitonerror=="true":
            raiseWLSTException(e)
        else:
            WLS_ON.println(e.getMessage())

"""
End of All LifeCycle related Commands
----------------------------------------------------------------------
"""

"""
Start All Rollout Commands
-------------------------------------------------------------------------
"""

"allows rollout of OracleHome, JavaHome, Applications, or any combination"
def rolloutUpdate(target, rolloutOracleHome="", backupOracleHome="", isRollback="false",
                   javaHome="", applicationProperties="", options=""):
  global LAST
  try:
    LAST = WLS_ON.rolloutUpdate(target,
                  rolloutOracleHome, 
                  backupOracleHome, 
                  isRollback, 
                  javaHome,
                  applicationProperties,
                  options)
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


"allows the rollout of a new OracleHome"
def rolloutOracleHome(target, rolloutOracleHome, backupOracleHome, isRollback="false", options=""):
  global LAST
  try:
    LAST = WLS_ON.rolloutOracleHome(target,
                  rolloutOracleHome, 
                  backupOracleHome, 
                  isRollback, 
                  options)
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
      
"allows the rollout of a new JavaHome"
def rolloutJavaHome(target, javaHome, options=""):
  global LAST
  try:
    LAST = WLS_ON.rolloutJavaHome(target,
                  javaHome, 
                  options)
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
      
"allows the rollout of new applications, the application properties is a path to a formatted text file containing the necessary information"
def rolloutApplications(target, applicationProperties, options=""):
  global LAST
  try:
    LAST = WLS_ON.rolloutApplications(target, 
                                      applicationProperties,
                                      options)
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
    
"will restart all of the target servers one at a time"
def rollingRestart(target, options=""):
  global LAST
  try:
    LAST = WLS_ON.rollingRestart(target, 
                                 options)
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      
"""
End of all Rollout Commands
-------------------------------------------------------------------------
"""

"""
Start of All NodeManager Related Commands
------------------------------------------------------------------------
"""

def nmEnroll(domainDir=None, nmHome = None):
  try:
    nmService.nmEnrollMachine(domainDir, nmHome)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())  

def nmGenBootStartupProps(serverName=None):
  try:
    nmService.nmGenBootStartupProps(serverName)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())  

from java.io import PrintStream
from java.io import File
from java.lang import System
from java.util import Properties
from weblogic.management.scripting.utils import ScriptCommands
myChild=None
dirToStartNM=None
varsToChangeMap=None
def startNodeManager(verbose='true', 
                     block='false',
                     timeout=120000,
                     username=None,
                     password=None,
                     host='localhost',
                     port=-1,
                     jvmArgs = None,
                     domainName=None,
                     domainDir=None,
                     nmType='ssl',
                     **nmProperties):
    try:
        global myChild
        global dirToStartNM
        global varsToChangeMap 
        global nmDir
        if myChild != None:
          try:
            myChild.exitValue()
          except Exception, e:
            WLS_ON.println(fmtr.getNodeManagerAlreadyStarted())
            return
        WLS_ON.setCommandType(ScriptCommands.START_NODE_MANAGER)

        javaVendor = System.getProperty("java.vendor")
        javaDataArch = System.getProperty("sun.arch.data.model")
        doNotUseD64 = System.getProperty("do.not.use.d64")
        osName = System.getProperty("os.name") 
        if doNotUseD64 is None or doNotUseD64=='false':
          if javaDataArch=='64' and (javaVendor=='Sun Microsystems Inc.' or javaVendor=='Oracle Corporation' or javaVendor=='Hewlett-Packard Co.' or javaVendor=='Hewlett Packard Enterprise') and (osName=='Solaris' or osName=='HP-UX' or osName=='SunOS'):
            WLSTUtil.addJvmArg("-d64")

        if jvmArgs != None:
          WLSTUtil.addJvmArgs(jvmArgs.replace("," , " "))

        keys = nmProperties.keys()
        keys.sort()
        systemProperties = Properties()
        systemProperties.setProperty("QuitEnabled","true")
        nmDir = "."
        rootDir = File(nmDir)
        nmProps="{";
        for kw in keys:
          if kw == "NodeManagerHome":
            nmDir = nmProperties[kw]
            rootDir = File(File(nmDir).getParent())
          systemProperties.setProperty(kw, nmProperties[kw])
          nmProps += kw+"="+nmProperties[kw]+","
        nmProps+="}"
        WLS_ON.println(fmtr.getLaunchingNodeManagerMessage())
        WLSTUtil.addSystemProps(systemProperties)
        #
        configDir = File(rootDir, "config")
        configFile = File(configDir, "config.xml")
        if configFile.exists():
          WLSTUtil.addSystemProp("weblogic.RootDirectory", rootDir.getAbsolutePath())
          # SubProcess is exec-ed here
          myChild = WLSTUtil.startNodeManagerScript(rootDir)
          dirToStartNM = rootDir.getAbsolutePath()

        else:
          # Can't find a valid domain. Trying to start NM from wlserver/server/bin
          # In this case, this single NM can manager more than one domain. 
          # Hence no weblogic.RootDirectory is set.
          WLS_ON.println(fmtr.getNotConfiguredWithDomain(nmDir))
          wlHomeDir = WLSTUtil.findWLHome()
          if wlHomeDir == None:
            WLS_ON.println(fmtr.getCannotFindWLHOME())
            return

          # If starting from <WL_HOME>/server/bin, WLST needs to set NODEMGR_HOME in to the environment
          # so the script can pick up this value.
          varsToChangeMap = java.util.HashMap()
          varsToChangeMap.put("NODEMGR_HOME", nmDir) 

          # SubProcess is exec-ed here
          myChild = WLSTUtil.startNodeManagerScript(wlHomeDir, varsToChangeMap)
          if myChild == None:
            WLS_ON.println(fmtr.getErrorStartingNodeManager())
            return

          dirToStartNM = wlHomeDir.getAbsolutePath()

        # NMProcess is a misnomer. The process is already started.
        # It is setting up the threads to read stdout and stderr
        WLSTUtil.startProcess(myChild, "NMProcess",1)
        java.lang.Thread.sleep(10000)
        WLS_ON.println(fmtr.getLaunchedNodeManager(File(nmDir).getAbsolutePath()))
    except Exception, e:
        e.printStackTrace()
        WLS_ON.println(fmtr.getErrorStartingNodeManager())
        # kill the process
        if not myChild==None:
          myChild.destroy()
        return
    # wait for node manager process to start if requested
    if block=='true':
        WLS_ON.println(fmtr.getWaitingForNodeManager())
        isConnected = 0
        quitTime = java.lang.System.currentTimeMillis() + float(timeout)
        finalException = None
        while not isConnected and not isTimeoutExpired(quitTime):
            try:
                nmConnect(username,password,host,port,domainName,domainDir,nmType)
                isConnected = 1
            except Exception, e:
                finalException = e
                java.lang.Thread.sleep(2000)
            if not isConnected and isTimeoutExpired(quitTime):
                WLS_ON.println(fmtr.getConnectionFailed())
                raise finalException
    else:
        WLS_ON.println(fmtr.getNodeManagerStarting())

# call the function here
def stopNodeManager():
    global dirToStartNM
    global varsToChangeMap
    if dirToStartNM!=None:
      try:
        WLS_ON.setCommandType(ScriptCommands.STOP_NODE_MANAGER)
        myStopChild=WLSTUtil.stopNodeManagerScript(File(dirToStartNM),varsToChangeMap)
        #
        # NMStopProcess is a misnomer. The process is already stopped.
        # It is setting up the threads to read stdout and stderr
        #
        WLSTUtil.startProcess(myStopChild, "NMStopProcess",1)
        java.lang.Thread.sleep(10000)
        WLS_ON.println(fmtr.getStopNodeManagerComplete(File(nmDir).getAbsolutePath()))
      except Exception, e:
        e.printStackTrace()
        WLS_ON.println(fmtr.getErrorStoppingNodeManager())
        if not myStopChild==None:
          myStopChild.destroy()
        dirToStartNM=None
        return
    else:
        nmService.nmQuit()
    updateGlobals()

"""
End of All NodeManager related Commands
----------------------------------------------------------------------
"""

"""
Start of All Documented Miscellaneous commands
----------------------------------------------------------------------
"""
def configToScript(configPath=None,
                   pyPath=None,
                   overWrite="true",
                   propertiesFile=None,
                   createDeploymentScript="false",
                   convertTheseResourcesOnly="",
                   debug = "false"
                   ):
  try:
    WLS_ON.config2Py(configPath,
                     pyPath,
                     overWrite,
                     propertiesFile,
                     createDeploymentScript,
                     convertTheseResourcesOnly,
                     debug)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def saveDomain():
  try:
    java.lang.Thread.sleep(4000)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

"""
End of All Documented Miscellaneous commands
----------------------------------------------------------------------
"""

"""
Start of all Un-Documented Commands
------------------------------------------------------------------------
"""
# Un-documented
# @exclude
def listCustomMBeans():
  try:
    retString = WLS_ON.listCustomMBeans()
    print ""
    updateGlobals()
    hideDisplay()
    return retString
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


# Un-documented
# @exclude
def reset():
  try:
    WLS_ON.reset()
    print ""
    updateGlobals()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


# Un-documented
# @exclude
def getTarget(path):
  try:
    hideDisplay()
    return WLS_ON.getTarget(path)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

# Un-documented
# @exclude
def getTargetArray(type, values):
  try:
    myArr = WLS_ON.getTargetArray(type, values)
    if type.endswith('Runtime'):
      type='weblogic.management.runtime.'+type+'MBean'
    else:
      type='weblogic.management.configuration.'+type+'MBean'
    myArr1 = jarray.array(myArr,Class.forName(type))
    hideDisplay()
    return myArr1
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

# Un-documented
# @exclude
def stopExecution(reason):
  raise "Error: "+reason



# Un-documented
# @exclude
def easeSyntax():
  WLS_ON.easeSyntax()


import weblogic.management.scripting.WLSTConstants as tree
# Un-documented
# @exclude
def evaluatePrompt():
  if WLSTUtil.runningWLSTAsModule():
    return
  global wlstPrompt,dcCalled,lastPrompt,domainName,hideProm
  global domainType,showStack,wlsversion,mbs,connected,myps1,promptt
  global ncPrompt,serverName
  if wlstPrompt=="false":
    myps1=sys.ps1
    sys.ps1=myps1
    return
  if hideProm == "false":
    if nmService.isConnectedToNM() == 1 and connected == "false":
      myps1="wls:/nm/"+str(nmService.getDomainName())+"> "
      sys.ps1 = myps1
      return
    if domainType == tree.DEPRECATED_ADMIN_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.DEPRECATED_CONFIG_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.DEPRECATED_RUNTIME_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.DEPRECATED_RUNTIME_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.CUSTOM_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.CUSTOM_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.DOMAIN_CUSTOM_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.DOMAIN_CUSTOM_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.EDIT_CUSTOM_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.EDIT_CUSTOM_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.JNDI_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.JNDI_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.CONFIG_RUNTIME_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.CONFIG_RUNTIME_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.RUNTIME_RUNTIME_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.RUNTIME_RUNTIME_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.RUNTIME_DOMAINRUNTIME_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.RUNTIME_DOMAINRUNTIME_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.CONFIG_DOMAINRUNTIME_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.CONFIG_DOMAINRUNTIME_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.EDIT_TREE:
      if WLS_ON.isEditSessionInProgress() == 1:
        myps1="wls:/"+str(domainName)+"/"+tree.EDIT_PROMPT+WLS_ON.getEditSessionNameForPrompt()+"/"+promptt+" !> "
      else:
        myps1="wls:/"+str(domainName)+"/"+tree.EDIT_PROMPT+WLS_ON.getEditSessionNameForPrompt()+"/"+promptt+"> "
      sys.ps1=myps1
    elif domainType == tree.DEPRECATED_CONFIG_TREE:
      myps1="wls:/"+str(domainName)+"/"+tree.DEPRECATED_CONFIG_PROMPT+"/"+promptt+"> "
      sys.ps1=myps1
  elif hideProm == "true":
    myps1="wls:/> "
    sys.ps1=myps1
  if connected=="false":
    # see if readTemplate or anything is intact
    offlinePrompt = java.lang.String(WLS.getAbsPwd())
    if offlinePrompt != None and offlinePrompt.length()!= 0:
      myps1 = "wls:/offline"+str(offlinePrompt)+"> "
    else:
      myps1=ncPrompt
    sys.ps1=myps1

# Un-documented
# @exclude
def updateGlobals():
  global isAdminServer,version,username,cmo,home,adminHome,wlstPrompt,dcCalled
  global lastPrompt,domainName,hideProm,domainType,showStack,wlsversion
  global mbs,connected,myps1,promptt,ncPrompt,serverName,cmgr,domainRuntimeService
  global hideProm,editService,runtimeService,typeService, scriptMode

  ## backwards compatability
  global _editService

  #####  Bug OWLS-39117  If this variable isn't set the prompt will look wrong when changing trees
  wlstGlobals.wlstPrompt = wlstPrompt

  cmo = WLS_ON.getCmo()
  wlstGlobals.cmo = cmo

  promptt = WLS_ON.getPrompt()
  wlstGlobals.promptt = promptt

  domainName = WLS_ON.getDomainName()
  wlstGlobals.domainName = domainName

  connected = WLS_ON.getConnected()
  wlstGlobals.connected = connected

  domainType = WLS_ON.getDomainType()
  wlstGlobals.domainType = domainType

  mbs = WLS_ON.getMBeanServer()
  wlstGlobals.mbs = mbs

  serverName = WLS_ON.getServerName()
  wlstGlobals.serverName = serverName

  wlsversion = WLS_ON.getVersion()
  wlstGlobals.wlsversion = wlsversion

  username = java.lang.String(WLS_ON.getUsername_bytes())
  wlstGlobals.username = username

  cmgr = WLS_ON.getConfigManager()
  wlstGlobals.cmgr = cmgr 

  version = wlsversion
  wlstGlobals.version = version

  isAdminServer = WLS_ON.isAdminServer()
  wlstGlobals.isAdminServer = isAdminServer

  domainRuntimeService = WLS_ON.getDomainRuntimeServiceMBean()
  wlstGlobals.domainRuntimeService = domainRuntimeService

  runtimeService = WLS_ON.getRuntimeServiceMBean()
  wlstGlobals.runtimeService = runtimeService

  editService = WLS_ON.getEditServiceMBean()
  wlstGlobals.editService = editService

  typeService = WLS_ON.getMBeanTypeService()
  wlstGlobals.typeService = typeService

  scriptMode = WLS_ON.getScriptMode()
  wlstGlobals.scriptMode = scriptMode

  wlstGlobals._editService = WLS_ON.getEditService()
  # For backwards compatability in case there are any scripts that use _editService.  Internally WLST ONLY uses
  # wlstGlobals._editService
  _editService = wlstGlobals._editService

  evaluatePrompt()
# Un-documented
# @exclude
def restoreDisplay():
  'This will restore the Display to Default'
  global oldhook,myps1
  sys.displayhook=oldhook
  myps1=sys.ps1

# Un-documented
# @exclude
def viewDebugScope():
  'This will bring the debug scope in a swing format' 
  weblogic.diagnostics.debug.DebugScopeViewer.main(None)
  

# Un-documented
# @exclude
def startSvr(domainName='mydomain', serverName='myserver', usrName='weblogic', passwrd='weblogic', url='t3://localhost:7001', generateDefaultConfig='true', rootDir='.'):
  'This will start a new server' 
  WLS_ON.startSvr(domainName, serverName, usrName, passwrd, url, generateDefaultConfig, rootDir, 'false',0)
  

# Un-documented
# @exclude
def jsr77():
  try:
    WLS_ON.jsr77()
    print ""
    updateGlobals()
    hideDisplay()
    return cmo
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())
      

# Determine if the connect retry count has reached the limit.
# Un-documented
# @exclude
def isTimeoutExpired(quitTime):
    return java.lang.System.currentTimeMillis() > quitTime
        
        
# Un-documented
# @exclude
def showExcluded(value="true"):
    WLS_ON.setShowExcluded(value)
    
# Un-documented
# @exclude
def dumpMBeans(value=None):
    WLS_ON.dumpMBeans(value)
    
# Un-documented
# @exclude
def skipSingletonCd(value="false"):
    WLS_ON.skipSingletonCd(value)

# Un-documented
# @exclude
def hideDumpStack(value="true"):
    WLS_ON.setHideDumpStack(value)
    
# call the function here
# Un-documented
# @exclude

def exportDiagnosticDataFromServer(**dict) :
    
    dict.setdefault('logicalName', 'ServerLog')
    dict.setdefault('query', '')
    dict.setdefault('beginTimestamp', 0L)
    dict.setdefault('endTimestamp', Long.MAX_VALUE)
    dict.setdefault('partition', '')
    dict.setdefault('server', '')
    dict.setdefault('format', 'xml')
    format = dict.get('format')
    dict.setdefault('exportFileName', 'export.'+format)
    dict.setdefault('last','')

    logicalName = dict.get('logicalName')
    query = dict.get('query')
    exportFileName = dict.get('exportFileName')
    beginTimestamp = dict.get('beginTimestamp')
    endTimestamp = dict.get('endTimestamp')    
    partition = dict.get('partition')
    server = dict.get('server')
    last = dict.get('last')

    WLS_ON.exportDiagnosticDataFromServer(logicalName, query, beginTimestamp, endTimestamp, exportFileName, partition, server, format, last)

    WLS_ON.println(fmtr.getExportedDiagnosticData(exportFileName))
  
def getAvailableCapturedImages(Server=None, partition=None) :
  result = WLS_ON.getAvailableCapturedImages(Server, partition)
  return result

def purgeCapturedImages(server=None, partition=None, age=None) :
  result = WLS_ON.purgeCapturedImages(server, partition, age)
  return result

def saveDiagnosticImageCaptureFile(imageName, outputFile=None, Server=None, partition=None) :
  destFile=outputFile
  if destFile == None:
    destFile=imageName
  WLS_ON.println(fmtr.getRetrievingImage(imageName, destFile))
  WLS_ON.saveDiagnosticImageCaptureFile(imageName, Server, destFile, partition)

def saveDiagnosticImageCaptureEntryFile(imageName, imageEntryName, outputFile=None, Server=None, partition=None) :
  destFile=outputFile
  if destFile == None:
    destFile=imageEntryName
  WLS_ON.println(fmtr.getRetrievingImageEntry(imageEntryName, imageName, destFile))
  WLS_ON.saveDiagnosticImageCaptureEntryFile(imageName, imageEntryName, Server, destFile, partition)

def listSystemResourceControls(Server=None, Target=None) :
  WLS_ON.listSystemResourceControls(Server,Target)

def enableSystemResource(resourceName, Server=None, Target=None) :
  WLS_ON.enableSystemResource(resourceName,Server,Target)

def disableSystemResource(resourceName,Server=None, Target=None) :
  WLS_ON.disableSystemResource(resourceName,Server,Target)

def createSystemResourceControl(resourceName, descriptorFile, Server=None, Target=None, enabled="false") :
  WLS_ON.createSystemResourceControl(resourceName,descriptorFile,Server,Target,enabled)

def destroySystemResourceControl(resourceName, Server=None, Target=None) :
  WLS_ON.destroySystemResourceControl(resourceName, Server,Target)

def dumpDiagnosticData(resourceName,file,frequency,duration,Server=None,dateFormat="EEE MM/dd/YY k:mm:ss:SSS z") :
  WLS_ON.dumpDiagnosticData(resourceName,Server,file,frequency,duration,dateFormat)

def captureAndSaveDiagnosticImage(Server=None,outputFile=None,outputDir=None,Target=None,partition=None) :
  WLS_ON.captureAndSaveDiagnosticImage(Server, Target, outputFile, outputDir, partition);

def captureAndSaveDiagnosticImage__(Server=None,outputFile=None, partition=None) :
  WLS_ON.println(fmtr.getCapturingImage());
  imageName = WLS_ON.captureDiagnosticImage(Server, partition)
  WLS_ON.println(fmtr.getCapturedImage());
  destFile=outputFile
  if destFile == None:
    destFile=imageName
  WLS_ON.println(fmtr.getRetrievingImage(imageName, destFile))
  WLS_ON.saveDiagnosticImageCaptureFile(imageName, destFile, partition)

def getAvailableDiagnosticDataAccessorNames(server=None, partition=None) :
  result = WLS_ON.getAvailableDiagnosticDataAccessorNames(server, partition)
  return result

def exportHarvestedTimeSeriesData(wldfSystemResource, server=None, partition=None, begin=-1L, end=-1L, exportFile='export.csv', dateFormat="EEE MM/dd/YY k:mm:ss:SSS z", last=None) :
  WLS_ON.exportHarvestedTimeSeriesData(wldfSystemResource, server, partition, begin, end, exportFile, dateFormat, last)

def listDebugPatches(target=None) :
  WLS_ON.listDebugPatches(target)

def showDebugPatchInfo(patch, target=None) :
  WLS_ON.showDebugPatchInfo(patch, target)

def activateDebugPatch(patch, app=None, module=None, partition=None, target=None) :
  return WLS_ON.activateDebugPatch(patch, app, module, partition, target)

def deactivateDebugPatches(patches, app=None, module=None, partition=None, target=None) :
  return WLS_ON.deactivateDebugPatches(patches, app, module, partition, target)

def listDebugPatchTasks(target=None) :
  return WLS_ON.listDebugPatchTasks(target);

def purgeDebugPatchTasks(target=None) :
  WLS_ON.purgeDebugPatchTasks(target)

def deactivateAllDebugPatches(target=None) :
  return WLS_ON.deactivateAllDebugPatches(target)

def migrateResourceGroup(target, currentTarget, newTarget, timeout=-1):
  try:
    return WLS_ON.getPortablePartitionManager().migrateResourceGroup(target, currentTarget, newTarget, timeout)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


def importPartition(archiveFileName, partitionName=None, createNew=None , keyFile=None):
  try:
    return WLS_ON.getPortablePartitionManager().importPartition(archiveFileName, partitionName, createNew, keyFile)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


def exportPartition(partitionName, expArchPath, includeAppsNLibs=true, keyFile=None):
  try:
    return WLS_ON.getPortablePartitionManager().exportPartition(partitionName, expArchPath, includeAppsNLibs, keyFile)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def startPartitionWait(partitionMBean, initialState='RUNNING', timeout=60*1000):
  try:
    if partitionMBean is None:
      raise ScriptException('PartitionMBean must not be null', 'startPartitionWait')
    pmb=WLS_ON.getDomainRuntimeServiceMBean().getDomainConfiguration().lookupPartition(partitionMBean.getName())
    if pmb is None:
      raise ScriptException('PartitionMBean not found in DomainRuntime', 'startPartitionWait')
    return WLS_ON.getDomainRuntimeServiceMBean().getDomainRuntime().startPartitionWait(pmb,initialState,timeout)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def forceShutdownPartitionWait(partitionMBean, timeout=60*1000):
  try:
    if partitionMBean is None:
      raise ScriptException('PartitionMBean must not be null', 'startPartitionWait')
    pmb=WLS_ON.getDomainRuntimeServiceMBean().getDomainConfiguration().lookupPartition(partitionMBean.getName())
    if pmb is None:
      raise ScriptException('PartitionMBean not found in DomainRuntime', 'startPartitionWait')
    return WLS_ON.getDomainRuntimeServiceMBean().getDomainRuntime().forceShutdownPartitionWait(pmb,timeout)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def uploadUserFileWait(uploadDir, filePath, partitionName=None, overwrite=false):
  global LAST
  try:
    LAST = WLS_ON.uploadUserFileWait(uploadDir,
                  filePath,
                  partitionName,
                  overwrite)
    hideDisplay()
    return LAST
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

"""
End of all Un-Documented Commands
"""
