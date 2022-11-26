# Caution: This file is part of the command scripting implementation. 
# Do not edit or move this file because this may cause commands and scripts to fail. 
# Do not try to reuse the logic in this file or keep copies of this file because this 
# could cause your scripts to fail when you upgrade to a different version.
# Copyright (c) 2011,2016, Oracle and/or its affiliates. All rights reserved.

"""
This script defines WLST commands can be run in both online and offline modes.
These commands don't need be redefined when WLST user switches between online
and offline mode.

"""

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

### Define all the global variables here

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

### JSR88

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

from java.io import File
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


# Determine if the connect retry count has reached the limit.
# Un-documented
# @exclude
def isTimeoutExpired(quitTime):
    return java.lang.System.currentTimeMillis() > quitTime

### utilities

def loadProperties(fileName):
  # verify theInterpreter is set
  if WLSTUtil.runningWLSTAsModule():
    WLS_ON.println(fmtr.getCanNotLoadProperties())
    return
  global theInterpreter
  WLS_ON.loadProperties(fileName, theInterpreter)

def storeUserConfig(userConfigFile=None, userKeyFile=None, nm='false'):
  try:
    WLS_ON.storeUserConfig(userConfigFile, userKeyFile, nm)
  except ScriptException,e:
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

# lifecycle
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
                spaceAsJvmArgsDelimiter = 'false',
	        generateDefaultConfig = 'false'):
  global LAST
  try:
    LAST = WLS_ON.startSvr(domainName,
                           adminServerName,
                           username,
                           password,
                           url,
                           domainDir,
                           generateDefaultConfig,
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

### Custom help commands

def addHelpCommandGroup(groupName, resourceBundleName):
  try:
    WLS_ON.addHelpCommandGroup(groupName, resourceBundleName)
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def addHelpCommand(commandName, groupName, offline='false', online='false'):
  try:
    WLS_ON.addHelpCommand(commandName, groupName, offline, online)
  except ScriptException,e:
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


### Miscellaneous commands (debug, command syntax, prompt, etc.)

# Un-documented
# @exclude
def debug(val=None):
  try:
    WLS_ON.debug(val)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())


# Un-documented
# @exclude
def easeSyntax():
  WLS_ON.easeSyntax()

# Un-documented
# @exclude
def eatdisplay(dummy):
  pass

# Un-documented
# @exclude
def hideDisplay():
  sys.displayhook=eatdisplay

# Un-documented
# @exclude
def restoreDisplay():
  'This will restore the Display to Default'
  global oldhook,myps1
  sys.displayhook=oldhook
  myps1=sys.ps1

# @exclude
def hideDumpStack(value="true"):
    WLS_ON.setHideDumpStack(value)

# Un-documented
# @exclude
def setDumpStackThrowable(value=None):
    WLS_ON.setDumpStackThrowable(value)
    
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
        myps1="wls:/"+str(domainName)+"/"+tree.EDIT_PROMPT+"/"+promptt+" !> "
      else:
        myps1="wls:/"+str(domainName)+"/"+tree.EDIT_PROMPT+"/"+promptt+"> "
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
  global hideProm,editService,runtimeService,typeService,scriptMode
  cmo = WLS_ON.getCmo()
  promptt = WLS_ON.getPrompt()
  domainName = WLS_ON.getDomainName()
  connected = WLS_ON.getConnected()
  domainType = WLS_ON.getDomainType()
  mbs = WLS_ON.getMBeanServer()
  serverName = WLS_ON.getServerName()
  wlsversion = WLS_ON.getVersion()
  username = java.lang.String(WLS_ON.getUsername_bytes())
  cmgr = WLS_ON.getConfigManager()
  version = wlsversion
  isAdminServer = WLS_ON.isAdminServer()
  domainRuntimeService = WLS_ON.getDomainRuntimeServiceMBean()
  runtimeService = WLS_ON.getRuntimeServiceMBean()
  editService = WLS_ON.getEditServiceMBean()
  typeService = WLS_ON.getMBeanTypeService()
  scriptMode = WLS_ON.getScriptMode()
  evaluatePrompt()

# Un-documented
# @exclude
class WLSTException(Exception):
  cause = None
  def __init__(self, value):
    self.value = value
  def __str__(self):
    # See CR344726.
    # return repr(self.value)
    return PyString(self.value)
  def getCause(self):
    return self.cause
  def setCause(self, acause):
    self.cause = acause

# Un-documented
# @exclude
def raiseWLSTException(e):
  wlste = WLSTException(e.getMessage())
  wlste.setCause(e.getCause())
  raise wlste

# Un-documented
# @exclude
def makePropertiesObject(value):
  try:
    hideDisplay()
    return WLS_ON.makePropertiesObject(value)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

# Un-documented
# @exclude
def makeArrayObject(value):
  try:
    hideDisplay()
    return WLS_ON.makeArrayObject(value)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def redirect(outputFile=None, toStdOut="true"):
  try:
    WLS_ON.redirect(outputFile, toStdOut)
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def stopRedirect():
  try:
    WLS_ON.stopRedirect()
  except ScriptException,e:
    updateGlobals()
    if exitonerror=="true":
      raiseWLSTException(e)
    else:
      WLS_ON.println(e.getMessage())

def mergeDiagnosticData(inputDir, toFile="merged.csv") :
  WLS_ON.mergeDiagnosticData(inputDir,toFile)

def setShowLSResult(show):
  WLS.setShowLSResult(show)
  WLS_ON.setShowLSResult(show)
