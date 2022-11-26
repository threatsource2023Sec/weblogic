# Copyright (c) 2015, 2016, Oracle and/or its affiliates. All rights reserved.
#
# Non-MT domain migration script
# LiftDomain exportForMigration
#
#

import os
import sys
import handler
import componentList
import time

from java.lang import System
from java.io import File
from java.io import FileInputStream
from java.io import FileOutputStream
from java.io import ByteArrayInputStream
from java.io import ByteArrayOutputStream
from java.lang import String
from java.lang import StringBuffer
from java.util import Date
from java.util import ArrayList
from java.util import Map
from java.util import HashMap
from java.util.jar import JarEntry
from java.util.jar import JarOutputStream
from weblogic.management.tools.migration import DescriptorHelper

htmlSuccessDict={}
htmlFailedDict={}
htmlUnScoppedResourcesDict={}
htmlMultiTargetedResourcesDict={}

# create directory
def _createDir(dirName):
    dirFile=File(dirName)
    if (dirFile.exists() == true):
        if (dirFile.isDirectory() == false):
            raise Exception, "Error: dir path "+dirName+" exists and is a file!"
    else:
        if (dirFile.mkdirs() == false):
            raise Exception, "Error: Cannot create directory "+dirName
    return dirFile

def _createJMSModuleFile(filePath):
    jmsModuleFile=File(filePath);
    print 'JMS Module Descriptor file path:' + filePath
    parentDirAbsPath = jmsModuleFile.getParentFile().getAbsolutePath()
    parentDir = jmsModuleFile.getParentFile()
    if (parentDir.exists() == true):
        if (parentDir.isDirectory() == false):
            raise Exception, "Error: dir path " + parentDirAbsPath + " exists and is a file!"
    else:
        if (parentDir.mkdirs() == false):
            raise Exception, "Error: Cannot create directory " + parentDirAbsPath
    File(parentDirAbsPath, jmsModuleFile.getName()).createNewFile()
    return jmsModuleFile

# pipe to the streams
def _pipe(instrem, outstrem):
    buffer=String("                                ").getBytes()
    lengthRead=instrem.read(buffer)
    while (lengthRead >= 0):
          outstrem.write(buffer, 0, lengthRead)
          lengthRead=instrem.read(buffer)
    return


# write to file
def _writeToFile(outFile, contentString):
    if (outFile == None):
       raise Exception, "Error: write to file is None"
    if (contentString == None):
       raise Exception, "Error: write content is None"
    fout=None
    bin=None
    try:
        fout=FileOutputStream(outFile, true)
        cbytes=String(contentString).getBytes()
        bin=ByteArrayInputStream(cbytes)
        _pipe(bin, fout)
    except Exception, ex:
        dumpStack()
        print "Error: write to file failed by:"
        print ex.getMessage()
    if (fout != None):
        fout.close()
    if (bin != None):
        bin.close()
    return


# read file
def _readFile(srcFile, contentStrBuf):
    if (srcFile == None):
       raise Exception, "Error: read file is None"
    if (contentStrBuf == None):
       raise Exception, "Error: content buffer is None"
    fin=None
    bout=None
    try:
        fin=FileInputStream(srcFile)
        bout=ByteArrayOutputStream()
        _pipe(fin, bout)
    except Exception, ex:
        dumpStack()
        print "Error: read file failed by :"
        print ex.getMessage()
    if (bout != None):
        bout.close()
        contentStrBuf.append(bout.toString())
    if (fin != None):
        fin.close()
    return
    

# copy file
def _copyFile(srcFile, destFile):
    fout=None
    fin=None
    if (destFile == None):
        raise Exception, "Error: copy file dest is None"
    if (srcFile == None):
        raise Exception, "Error: copy file src is None"
    if (srcFile.exists() == false):
        raise Exception, "Error: copy file src does not exists "+srcFile.getName()
    if (srcFile.isDirectory() == true):
        raise Exception, "Error: copy file src is a directory "+srcFile.getName()
    fin = FileInputStream(srcFile)
    try:
        fout = FileOutputStream(destFile)
        _pipe(fin, fout);
    finally:
        if (fout != None):
            fout.close()
        if (fin != None):
            fin.close()
    return


# copy dir
def _copyDir(srcFile, destFile):
    if (srcFile == None):
       raise Exception, "Error: copy dir src is None"
    if (destFile == None):
       raise Exception, "Error: copy dir dest is None"
    if (srcFile.exists() == false):
       raise Exception, "Error: copy dir src does not exists "+srcFile.getName()
    if (srcFile.isDirectory() == false):
       raise Exception, "Error: copy dir src is not a directory "+srcFile.getName()
    if (destFile.exists() == false):
       raise Exception, "Error: copy dir dest does not exists "+destFile.getName()
    if (destFile.isDirectory() == false):
       raise Exception, "Error: copy dir dest is a not directory "+destFile.getName()
    files=srcFile.listFiles()
    for i in range(len(files)):
        childFile=files[i]
        if (childFile.isDirectory() == true):
            newFile=_createDir(destFile.getCanonicalPath()+"/"+childFile.getName())
            _copyDir(childFile, newFile)
        else:
            newFile=File(destFile, childFile.getName())
            newFile.createNewFile()
            _copyFile(childFile, newFile)
    return


# rm dir
def _rmDir(dirFile):
    if (dirFile == None):
       raise Exception, "Error: rm dir is None"
    files=dirFile.listFiles()
    for i in range(len(files)):
        childFile=files[i]
        if (childFile.isFile() == true):
            childFile.delete()
        else:
            _rmDir(childFile)
    dirFile.delete()
    return


# put files into jar/zip
def _putFileToJar(file, base, out):
    newBase = file.getName()
    if (base != None):
        newBase = base+'/'+file.getName()
    fin=None
    try:
        if (file.isDirectory()):
            files = file.listFiles()
            for i in range(len(files)):
                _putFileToJar(files[i], newBase, out)
        else:
            out.putNextEntry(JarEntry(newBase))
            if (file.exists()):
                fin = FileInputStream(file)
                _pipe(fin, out)
            out.closeEntry()
    finally:
        if (fin != None):
            fin.close()
    return


# adjust file path separator
def _adjustFilePathSep(fileName):
    adjFileName=fileName
    try:
        if (os.environ.get('OS','') == 'Windows_NT'):
           adjFileName=String(fileName).replace('\\','/')
    except Exception, ex:
        print "could not adjust file path for "+fileName
    return adjFileName


# create jar/zip file
def _createArchive(dirFile, destJar):
        fout=None
        out=None
        try:
            fout = FileOutputStream(destJar)
            out = JarOutputStream(fout)
            files = dirFile.listFiles()
            for i in range(len(files)):
                _putFileToJar(files[i], None, out)
        finally:
            if (out != None):
                out.close()
            if (fout != None):
                fout.close()
        return


# Server
def _processServer(svrName, configFile, jsonVTBuf, jsonAvailableTargetsBuf, dictExportedServers, dictFailedServers, targetsMap):
    if (svrName == None):
       raise Exception, "Error: processServer: Server name is None"
    if (configFile == None):
       raise Exception, "Error: processServer: script file is None"
    print "Capturing the details of the WebLogic Server "+svrName
    cd(svrName)
    try:
        outBuf=StringBuffer("  <server>\n")
        outBuf.append("    <name>").append(svrName).append("</name>\n")
        sslList=ls(returnMap='true')
        if (sslList.size() == 1):
            if (sslList[0] == 'SSL'):
                cd('SSL')
                servName=ls(returnMap='true')
                if (servName.size()==1):
                    cd(servName[0])
                    outBuf.append("    <ssl>\n")
                    outBuf.append("      <name>").append(svrName).append("</name>\n")
                    if (cmo.isEnabled()):
                        outBuf.append("      <enabled>true</enabled>\n")  
                        outBuf.append("      <listen-port>").append(cmo.getListenPort()).append("</listen-port>\n")
                    else: 
                        outBuf.append("      <enabled>false</enabled>\n")  
                    outBuf.append("    </ssl>\n")
                    cd('..')
                cd('..')
        outBuf.append("    <listen-port>").append(cmo.getListenPort()).append("</listen-port>\n")
        clusterProxy=cmo.getCluster()
        if (clusterProxy != None):
            outBuf.append("    <cluster>").append(clusterProxy.getName()).append("</cluster>\n")
        outBuf.append("    <listen-address>").append(cmo.getListenAddress()).append("</listen-address>\n")
        outBuf.append("  </server>\n")
        _writeToFile(configFile , outBuf)
        if (clusterProxy == None):
            jsonVTBuf.append("{\"name\":\"${PARTITION_NAME}-").append(svrName).append("-virtualTarget\"")
            jsonVTBuf.append(",\"target\":\"").append(svrName).append("\"")
            jsonVTBuf.append(",\"host-names\":\"").append("__EXISTING_VALUE__").append("\"")
            jsonVTBuf.append(",\"uri-prefix\":\"\\/${PARTITION_NAME}\"},")
            targetsMap.put("${PARTITION_NAME}-" + svrName,svrName);
            jsonAvailableTargetsBuf.append("{\"virtual-target\":{")
            jsonAvailableTargetsBuf.append("\"name\":\"${PARTITION_NAME}-").append(svrName).append("-virtualTarget\"}},")
        dictExportedServers[svrName]=str("${PARTITION_NAME}-"+svrName+"-virtualTarget")
    except Exception, ex:
        dumpStack()
        print "Error: Capturing the details of the server "+svrName+" failed."
        print ex.getMessage()
        dictFailedServers[svrName]=str(ex.getMessage())
    cd('..')
    return

# Cluster
def _processCluster(clusName, configFile, jsonVTBuf):
    if (clusName == None):
       raise Exception, "Error: Cluster name is None"
    if (configFile == None):
       raise Exception, "Error: processCluster: script file is None"
    print "Capturing the details of the Cluster "+clusName
    cd(clusName)
    try:
        outBuf=StringBuffer("  <cluster>\n")
        outBuf.append("    <name>").append(clusName).append("</name>\n")
        outBuf.append("    <cluster-messaging-mode>").append(cmo.getClusterMessagingMode()).append("</cluster-messaging-mode>\n")
        outBuf.append("  </cluster>\n")
        _writeToFile(configFile , outBuf)
        jsonVTBuf.append("{\"name\":\"${PARTITION_NAME}-").append(clusName).append("-virtualTarget\"")
        jsonVTBuf.append(",\"target\":\"").append(clusName).append("\"")
        jsonVTBuf.append(",\"host-names\":\"").append("__EXISTING_VALUE__").append("\"")
        jsonVTBuf.append(",\"uri-prefix\":\"\\/${PARTITION_NAME}\"},")
        htmlSuccessDict['Cluster'].append(clusName)
    except Exception, ex:
        dumpStack()
        print "Error: Capturing the details of the cluster "+clusName+" failed."
        print ex.getMessage()
        htmlFailedDict[str(clusName)]=str(ex.getMessage())
    cd('..')
    return

# MigratableTarget
def _processMigratableTarget(migtName, configFile):
    if (migtName == None):
       raise Exception, "Error: processMigratableTarget: MigratableTarget name is None"
    if (configFile == None):
       raise Exception, "Error: processMigratableTarget: script file is None"
    print "Capturing the details of the Migratable Target "+migtName
    migtnameStr=String(migtName)
    cd(migtnameStr)
    try:
        outBuf=StringBuffer("  <migratable-target>\n")
        outBuf.append("    <name>").append(migtName).append("</name>\n")
        proxyObj = cmo.getUserPreferredServer()
        if (proxyObj != None):
            outBuf.append("    <user-preferred-server>").append(proxyObj.getName()).append("</user-preferred-server>\n")
        proxyObj = cmo.getCluster()
        if (proxyObj != None):
            outBuf.append("    <cluster>").append(proxyObj.getName()).append("</cluster>\n")
        migratePolicy=cmo.getMigrationPolicy()
        #if (migratePolicy == None or migratePolicy == 'null'):
        #    migratePolicy="manual"
        if (migratePolicy != None and migratePolicy != 'null'):
            outBuf.append("    <migration-policy>").append(migratePolicy).append("</migration-policy>\n")
        outBuf.append("  </migratable-target>\n")
        _writeToFile(configFile , outBuf)
        htmlSuccessDict['MigratableTarget'].append(migtName)
    except Exception, ex:
        dumpStack()
        print "Error: Capturing the details of the migratable target "+migtName+" failed."
        print ex.getMessage()
        htmlFailedDict[str(migtName)]=str(ex.getMessage())
    cd('..')
    return

# Get Targets from any Targetable MBean
def _getTargets(targetable):
    targets = jarray.array([], ObjectName)
    try:
        attrList=ls(returnMap='true', returnType='a')
        target=attrList.get('Target')
        if target:
            targets = targetable.getTargets()
    except Exception, ex:
        try:
            targetNames=_getTargetsForSubDeployments(targetable).split(",")
            targets=_getTargetsByName(targetNames)
        except Exception, ex:
            # Worst case scenario
            # do not throw exception instead return empty array
            print 'Got exception while getting targets: ' + ex.getMessage()
            targets = jarray.array([], ObjectName)
    return targets

def _getPWD() :
    cwd = pwd().split('/')
    str = ''
    i=2
    while i < len(cwd):
        str = str + cwd[i] + '/'
        i=i+1
    return str

def _getTargetsByName(targets):
    i=0;
    targetMBeans = ArrayList()
    cwd=_getPWD()
    cd ('/')
    print 'Total number of targets to be resolved: ' + str(len(targets))
    while i < len(targets):
        print "finding target:" + str(targets[i])
        cd ('/Cluster')
        map=ls(returnMap='true')
        if (map.contains(targets[i])):
            cd (targets[i])
            targetMBeans.add(cmo)
            i=i+1
            continue
        cd ('/MigratableTarget')
        map=ls(returnMap='true')
        if (map.contains(targets[i])):
            cd (targets[i])
            targetMBeans.add(cmo)
            i=i+1
            continue
        cd ('/Server')
        map=ls(returnMap='true')
        if (map.contains(targets[i])):
            cd (targets[i])
            targetMBeans.add(cmo)
            i=i+1
            continue
        i=i+1
    cd('/')
    print 'Targets: ' + str(targetMBeans)
    print 'cd ing to path: /' + cwd
    cd('/' + cwd)
    return targetMBeans.toArray()

# Special handling for subdeployments. In some of the previous releases of WebLogic, calling getTargets() on a subdeployment 
# fails even when the subdeployment is targeted.
# Get targets for subdeployment in string format, multiple targets are separated by comma
def _getTargetsForSubDeployments(targetable):
    targets = ""
    try:
        attrList=ls(returnMap='true', returnType='a')
        targets=attrList.get('Target')
    except Exception, ex:
        # Worst case scenario, unfortunately we need to have at least one statement in except
        targets = ""
    return targets

# Find out the resources which should be targeted to targetName
def _fillScopedResources(cmo, resourceName, targetName, scopedResources, svrList, migtgList, resourceType):
    targetList=_getTargets(cmo)
    # Except a subdeployment, no other resource targeted to multiple WL Serves and/or Clusters are exported by the current version of the D-PCT tool
    if ( (targetList != None) and (len(targetList)>1)):
        if resourceName not in htmlMultiTargetedResourcesDict[resourceType]:
            print "WARNING!! The resource "+ resourceName +" is targeted to more than one WebLogic Servers and/or Clusters. This is not a supported feature of D-PCT and is skipped"
            htmlMultiTargetedResourcesDict[resourceType].append(resourceName)
        return
    if (len(targetList)>0):
        for j in range(len(targetList)):
            if (targetList[j].getName() == targetName):                
                scopedResources.add(resourceName)
            else:
                # Handle the use case when the resource is targeted to one or more Servers or Migratable Targets of a Cluster
                if (svrList.contains(targetList[j].getName()) or migtgList.contains(targetList[j].getName())):
                    if (targetList[j].getCluster() != None and targetList[j].getCluster().getName() == targetName):
                        scopedResources.add(resourceName)
    else:
        if resourceName not in htmlUnScoppedResourcesDict[resourceType]:
            htmlUnScoppedResourcesDict[resourceType].append(resourceName)
    return

# JMS Server
def _processJMSServersForTarget(targetName, configFile, jsonFile, svrList, migtgList):
    cd('/')
    allJMSServers = cmo.getJMSServers()
    scopedJMSServers = ArrayList()
    cd('/JMSServer')
    for i in range(len(allJMSServers)):
        cd(allJMSServers[i].getName())
        _fillScopedResources(cmo,allJMSServers[i].getName(),targetName, scopedJMSServers, svrList, migtgList, 'JMSServer')
        cd('..')
    cd('/')    
    if (scopedJMSServers.size() > 0):
        cd('/JMSServer')
        jsonString = ",\"jms-server\":["
        _writeToFile(jsonFile , jsonString)
    else:
        return
    for i in range(scopedJMSServers.size()):
        jsonJMSServerBuffer = StringBuffer()
        if (i > 0):
            jsonJMSServerBuffer.append(",")
        _processJMSSvr(scopedJMSServers[i], configFile)
        jsonJMSServerBuffer.append("{\"name\":\"").append(scopedJMSServers[i]).append("\"")
        jsonJMSServerBuffer.append(",\"exclude-from-import\":\"false\"}")
        _writeToFile(jsonFile, jsonJMSServerBuffer)
    jsonString="]"
    _writeToFile(jsonFile , jsonString)    
    cd('/')
    return

def _processJMSSvr(jmsName, configFile):
    if (jmsName == None):
       raise Exception, "Error: processJMSServer: JMS name is None"
    if (configFile == None):
       raise Exception, "Error: processJMSServer: script file is None"
    print "Capturing the details of the JMS Server "+jmsName
    cd(jmsName)
    try:
        outBuf=StringBuffer("  <jms-server>\n")
        outBuf.append("    <name>").append(jmsName).append("</name>\n")
        outBuf.append("    <target>")
        targetList=_getTargets(cmo)
        for i in range(len(targetList)):
            outBuf.append(targetList[i].getName())
            if (i < len(targetList)-1):
               outBuf.append(",")
        outBuf.append("</target>\n")
        pstore=cmo.getPersistentStore()
        if (pstore != None):
            outBuf.append("    <persistent-store>").append(pstore.getName()).append("</persistent-store>\n")
        outBuf.append("  </jms-server>\n")
        _writeToFile(configFile , outBuf)
        htmlSuccessDict['JMSServer'].append(jmsName)
    except Exception, ex:
        dumpStack()
        print "Error: Capturing the details of the JMS Server "+jmsName+" failed."
        print ex.getMessage()
        htmlFailedDict[str(jmsName)]=str(ex.getMessage())
    cd('..')
    return

def _processAppDeploymentsForTarget(targetName, applicationNames, migrateConfigFile, migrateRootDir, includeAppsFlag, jsonFile, domainPath, dphelper, userKeyFile, svrList, migtgList):
    cd('/')
    appDeployments = cmo.getAppDeployments()
    scopedAppDeployments = ArrayList()
    cd('/AppDeployment')
    for i in range(len(appDeployments)):
        cd(appDeployments[i].getName())    
        _fillScopedResources(cmo,appDeployments[i].getName(),targetName, scopedAppDeployments, svrList, migtgList, 'AppDeployment')                        
        cd('..')
    cd ('/')
    jsonAppDeploymentsBuffer = StringBuffer()
    if (scopedAppDeployments.size() > 0):
        jsonAppDeploymentsBuffer.append(",\"app-deployment\":[")
        _writeToFile(jsonFile, jsonAppDeploymentsBuffer)
        cd('/AppDeployment')
    else:
        return
    appCounter = 0
    for i in range(scopedAppDeployments.size()):
        if ((applicationNames == None) or (scopedAppDeployments[i] == applicationNames) or (scopedAppDeployments[i] in applicationNames)):
            if (appCounter > 0):
                jsonString=","
                _writeToFile(jsonFile , jsonString)
            _processAppDep(scopedAppDeployments[i], migrateConfigFile, migrateRootDir, includeAppsFlag, jsonFile, domainPath, dphelper, userKeyFile)
            appCounter += 1
    jsonString="]"
    _writeToFile(jsonFile , jsonString)
    cd ('/')
    return

# process app in AppDeployment
def _processAppDep(appName, configFile, rootDir, includeBits, jsonFile, domainPath, dphelper, userKeyFile):
    if (appName == None):
       raise Exception, "Error: processApp: app name is None"
    if (configFile == None):
       raise Exception, "Error: processApp: script file is None"
    if (rootDir == None):
       raise Exception, "Error: processApp: export root dir is None"
    print "Capturing the details of the application deployment with the name "+appName
    cd(appName)
    try:
        # app-deployment
        attrList=ls(returnMap='true', returnType='a')
        outBuf=StringBuffer("  <app-deployment>\n")
        outBuf.append("    <name>").append(appName).append("</name>\n")
        outBuf.append("    <target>")
        targetList=_getTargets(cmo)
        for i in range(len(targetList)):
            outBuf.append(targetList[i].getName())
            if (i < len(targetList)-1):
               outBuf.append(",")
        outBuf.append("</target>\n")
        outBuf.append("    <module-type>").append(cmo.getModuleType()).append("</module-type>\n")
        toAppDir="deployment/"+appName
        sourcePath=cmo.getSourcePath()
        if (sourcePath != None):
            sourcePath=_adjustFilePathSep(sourcePath)
            appSrcFile=File(sourcePath)
            if (appSrcFile.exists() == false):
                appSrcFile=File(domainPath + "/" + sourcePath)
            if (includeBits):
                outBuf.append("    <source-path>").append(appSrcFile.getName()).append("</source-path>\n")
            else:
                outBuf.append("    <source-path>").append(sourcePath).append("</source-path>\n")
        planDir=cmo.getPlanDir()
        if (planDir != None):
            planDir=_adjustFilePathSep(planDir)
            outBuf.append("    <plan-dir>").append("plan").append("</plan-dir>\n")
        planPath=cmo.getPlanPath()
        if (planPath != None):
            planPath=_adjustFilePathSep(planPath)
            planSrcFile=File(planPath)
            outBuf.append("    <plan-path>").append(planSrcFile.getName()).append("</plan-path>\n")
        localPlanPath=None
        #try:
        #    localPlanPath=cmo.getLocalPlanPath()
        #except Exception, exlpp: 
        #    localPlanPath=None 
        if (localPlanPath != None):
            outBuf.append("    <local-plan-path>").append(localPlanPath).append("</local-plan-path>\n")
        outBuf.append("    <deployment-order>").append(cmo.getDeploymentOrder()).append("</deployment-order>\n")
        secDDMod=attrList.get('SecurityDDModel')
        if (secDDMod == None):
            secDDMod=attrList.get('SecurityDdModel')
        if (secDDMod != None):
            outBuf.append("    <security-dd-model>").append(secDDMod).append("</security-dd-model>\n")
        stagingMode=cmo.getStagingMode()
        if (stagingMode != None):
           outBuf.append("    <staging-mode>").append(stagingMode).append("</staging-mode>\n")
        planStagingMode=attrList.get('PlanStagingMode')
        if (planStagingMode != None):
           outBuf.append("    <plan-staging-mode>").append(planStagingMode).append("</plan-staging-mode>\n")
        # json
        jsonBuf=StringBuffer("{\"name\":\"").append(appName).append("\"")
        jsonBuf.append(",\"exclude-from-import\":\"false\"")
        if (includeBits == false):
            if (sourcePath != None):
                jsonBuf.append(",\"source-path\":\"").append(sourcePath).append("\"")
            if (planPath != None):
                jsonBuf.append(",\"plan-path\":\"").append(planPath).append("\"")
        # Changes to handle sub module targets when the app contains JMS modules starts here
        currAppList=ls(returnMap='true')
        if(currAppList.contains('SubDeployment')):
            subDepList=cmo.getSubDeployments()
            cd('SubDeployment')            
            if (subDepList != None):
                #jsonBuf.append(",\"subd-targets\": [")
                jmsModulesCounter=0
                for j in range(len(subDepList)):
                    if jmsModulesCounter>0:jsonBuf.append(",")
                    outBuf.append("    <sub-deployment>\n")
                    outBuf.append("        <name>").append(subDepList[j].getName()).append("</name>\n")                    
                    cd(subDepList[j].getName())
                    currInnerSubList=ls(returnMap='true')
                    if(currInnerSubList.contains('SubDeployment')):
                        if jmsModulesCounter==0:jsonBuf.append(",\"jms-modules\": [")
                        innerSubDepList=cmo.getSubDeployments()
                        cd('SubDeployment')                        
                        if (innerSubDepList != None):
                            jsonBuf.append("{\"name\":\"").append(subDepList[j].getName()).append("\"")
                            jsonBuf.append(",\"sub-module-targets\": [")
                            innerSubdCounter=0
                            for k in range(len(innerSubDepList)):
                                if innerSubdCounter>0:jsonBuf.append(",")
                                cd(innerSubDepList[k].getName())
                                outBuf.append("        <sub-deployment>\n")                            
                                outBuf.append("          <name>").append(innerSubDepList[k].getName()).append("</name>\n")
                                innerSubDepTargets=_getTargetsForSubDeployments(cmo)
                                outBuf.append("          <target>")
                                jsonBuf.append("{\"name\":\"").append(innerSubDepList[k].getName()).append("\"")
                                jsonBuf.append(",\"targets\":\"").append("__EXISTING_VALUE__").append("\"}")
                                if (innerSubDepTargets != ""):
                                    outBuf.append(innerSubDepTargets)
                                outBuf.append("</target>\n")
                                outBuf.append("        </sub-deployment>\n")
                                cd ('..')
                                innerSubdCounter+=1
                            jsonBuf.append("]");
                            jsonBuf.append("}")
                        cd('..')
                    else:
                        if jmsModulesCounter==0:jsonBuf.append(",\"sub-module-targets\": [")
                        outBuf.append("        <target>")
                        jsonBuf.append("{\"name\":\"").append(subDepList[j].getName()).append("\"")
                        jsonBuf.append(",\"targets\":\"").append("__EXISTING_VALUE__").append("\"}")
                        subDepTargets=_getTargetsForSubDeployments(cmo)
                        if (subDepTargets != ""):
                            outBuf.append(subDepTargets)
                        outBuf.append("</target>\n")
                    jmsModulesCounter+=1
                    outBuf.append("    </sub-deployment>\n")
                    cd('..')
                jsonBuf.append("]")
            cd('..')
        outBuf.append("  </app-deployment>\n")
        _writeToFile(configFile , outBuf)
        # app deployment sub-dir
        appDepDir=rootDir+"/"+toAppDir
        _createDir(appDepDir)
        # app ear
        if (includeBits and sourcePath != None):
            appSrcFile=File(sourcePath)
            if (appSrcFile.exists() == false):
                appSrcFile=File(domainPath + "/" + sourcePath)
            if (appSrcFile.isDirectory()):
                appDestFile=_createDir(appDepDir+"/"+appSrcFile.getName())
                _copyDir(appSrcFile, appDestFile)
            else:
                appDestFile=File(appDepDir+"/"+appSrcFile.getName())
                appDestFile.createNewFile()
                _copyFile(appSrcFile, appDestFile)
        # plan-file
        if (planPath != None):
            planSrcFile=File(planPath)
            planDestFile=File(appDepDir+"/"+planSrcFile.getName())
            planDestFile.createNewFile()
            _copyFile(planSrcFile, planDestFile)
        # plan-dir
        if (planDir != None):
            pdirSrcFile=File(planDir)
            pdirDestFile=_createDir(appDepDir+"/plan")
            _copyDir(pdirSrcFile, pdirDestFile)

        jsonBuf.append("}");
        _writeToFile(jsonFile , jsonBuf)
        htmlSuccessDict['AppDeployment'].append(appName)
    except Exception, ex:
        dumpStack()
        print "Error: Capturing the details of the application deployment "+appName+" failed."
        print ex.getMessage()
        htmlFailedDict[str(appName)]=str(ex.getMessage())
    cd('..')
    return

def _processLibrariesForTarget(targetName, migrateConfigFile, migrateRootDir, includeAppsFlag, jsonFile, domainPath, svrList, migtgList):
    cd('/')
    libraries = cmo.getLibraries()
    scopedLibraries = ArrayList()
    cd('/Library')
    for i in range(len(libraries)):
        cd(libraries[i].getName())
        _fillScopedResources(cmo,libraries[i].getName(),targetName, scopedLibraries, svrList, migtgList, 'Library')
        cd('..')
    cd('/')
    jsonLibrariesBuffer = StringBuffer()
    if (scopedLibraries.size() > 0):
        cd('/Library')
        jsonString = ",\"library\":["
        _writeToFile(jsonFile, jsonString)
    else:
        return
    for i in range(scopedLibraries.size()):
        if (i > 0):
            jsonString = ","
            _writeToFile(jsonFile, jsonString)
        _processLib(scopedLibraries[i], migrateConfigFile, migrateRootDir, includeAppsFlag, jsonFile, domainPath)
    jsonString="]"
    _writeToFile(jsonFile , jsonString)
    cd('/')
    return

# process Library
def _processLib(libName, configFile, rootDir, includeBits, jsonFile, domainPath):
    if (libName == None):
       raise Exception, "Error: processLib: lib name is None"
    if (configFile == None):
       raise Exception, "Error: processLib: script file is None"
    if (rootDir == None):
       raise Exception, "Error: processLib: export root dir is None"
    print "Capturing the details of the library deployment "+libName
    cd(libName)
    try:
        # library
        attrList=ls(returnMap='true', returnType='a')
        outBuf=StringBuffer("  <library>\n")
        outBuf.append("    <name>").append(libName).append("</name>\n")
        outBuf.append("    <target>")
        targetList=_getTargets(cmo)
        for i in range(len(targetList)):
            outBuf.append(targetList[i].getName())
            if (i < len(targetList)-1):
               outBuf.append(",")
        outBuf.append("</target>\n")
        outBuf.append("    <module-type>").append(cmo.getModuleType()).append("</module-type>\n")
        toLibDir="library/"+libName
        sourcePath=cmo.getSourcePath()
        if (sourcePath != None):
            sourcePath=_adjustFilePathSep(sourcePath)
            libSrcFile=File(sourcePath)
            if (libSrcFile.exists() == false):
                libSrcFile=File(domainPath + "/" + sourcePath)
            if (includeBits):
                outBuf.append("    <source-path>").append(libSrcFile.getName()).append("</source-path>\n")
            else:
                outBuf.append("    <source-path>").append(sourcePath).append("</source-path>\n")
        planDir=cmo.getPlanDir()
        if (planDir != None):
            planDir=_adjustFilePathSep(planDir)
            outBuf.append("    <plan-dir>").append("plan").append("</plan-dir>\n")
        planPath=cmo.getPlanPath()
        if (planPath != None):
            planPath=_adjustFilePathSep(planPath)
            planSrcFile=File(planPath)
            outBuf.append("    <plan-path>").append(planSrcFile.getName()).append("</plan-path>\n")
        outBuf.append("    <deployment-order>").append(cmo.getDeploymentOrder()).append("</deployment-order>\n")
        secDDMod=attrList.get('SecurityDDModel')
        if (secDDMod == None):
            secDDMod=attrList.get('SecurityDdModel')
        if (secDDMod != None):
            outBuf.append("    <security-dd-model>").append(secDDMod).append("</security-dd-model>\n")
        stagingMode=cmo.getStagingMode()
        if (stagingMode != None):
           outBuf.append("    <staging-mode>").append(stagingMode).append("</staging-mode>\n")
        planStagingMode=attrList.get('PlanStagingMode')
        if (planStagingMode != None):
           outBuf.append("    <plan-staging-mode>").append(planStagingMode).append("</plan-staging-mode>\n")
        outBuf.append("  </library>\n")
        _writeToFile(configFile , outBuf)
        # library sub-dir
        libDir=rootDir+"/"+toLibDir
        _createDir(libDir)
        # lib war/jar
        if (includeBits and sourcePath != None):
            libSrcFile=File(sourcePath)
            if (libSrcFile.exists() == false):
                libSrcFile=File(domainPath + "/" + sourcePath)
            if (libSrcFile.isDirectory()):
                libDestFile=_createDir(libDir+"/"+libSrcFile.getName())
                _copyDir(libSrcFile, libDestFile)
            else:
                libDestFile=File(libDir+"/"+libSrcFile.getName())
                libDestFile.createNewFile()
                _copyFile(libSrcFile, libDestFile)
        # plan-file
        if (planPath != None):
            planSrcFile=File(planPath)
            planDestFile=File(libDir+"/"+planSrcFile.getName())
            planDestFile.createNewFile()
            _copyFile(planSrcFile, planDestFile)
        # plan-dir
        if (planDir != None):
            pdirSrcFile=File(planDir)
            pdirDestFile=_createDir(libDir+"/plan")
            _copyDir(pdirSrcFile, pdirDestFile)
        # json
        jsonBuf=StringBuffer("{\"name\":\"").append(libName).append("\"")
        jsonBuf.append(",\"exclude-from-import\":\"false\"")
        if (sourcePath != None):
            sourcePathStr=String(sourcePath)
            wldplIndex=sourcePathStr.indexOf("/common/deployable-libraries")
            if (wldplIndex > 0):
                sourcePathStr="${WL_HOME}\\/"+sourcePathStr.substring(wldplIndex+1)
            if (includeBits == false or wldplIndex > 0):
                jsonBuf.append(",\"source-path\":\"").append(sourcePathStr).append("\"")
                jsonBuf.append(",\"require-exact-version\":\"false\"")
        if (planPath != None):
            planPathStr=String(planPath)
            wldplIndex=planPathStr.indexOf("/common/deployable-libraries")
            if (wldplIndex > 0):
                planPathStr="${WL_HOME}\\/"+planPathStr.substring(wldplIndex+1)
            if (includeBits == false or wldplIndex > 0):
                jsonBuf.append(",\"plan-path\":\"").append(planPathStr).append("\"")
        jsonBuf.append("}");
        _writeToFile(jsonFile , jsonBuf)
        htmlSuccessDict['Library'].append(libName)
    except Exception, ex:
        dumpStack()
        print "Error: Capturing the details of the library deployment "+libName+" failed."
        print ex.getMessage()
        htmlFailedDict[str(libName)]=str(ex.getMessage())
    cd('..')
    return

def _processFileStoresForTarget(targetName, migrateConfigFile, jsonFile, svrList, migtgList):
    cd('/')
    fileStores = cmo.getFileStores()
    scopedFileStores = ArrayList()
    cd('/FileStore')
    for i in range(len(fileStores)):
        cd(fileStores[i].getName())
        _fillScopedResources(cmo,fileStores[i].getName(),targetName, scopedFileStores, svrList, migtgList, 'FileStore')        
        cd('..')
    cd('/')
    if (scopedFileStores.size() > 0):
        cd('/FileStore')
        jsonString = ",\"file-store\":["
        _writeToFile(jsonFile, jsonString)
    else:
        return
    for i in range(scopedFileStores.size()):
        _processFileStore(scopedFileStores[i], migrateConfigFile)
        jasonFSBuf=StringBuffer()
        if (i > 0):
            jasonFSBuf.append(",")
        jasonFSBuf.append("{\"name\":\"").append(scopedFileStores[i]).append("\"")
        jasonFSBuf.append(",\"exclude-from-import\":\"false\"}")
        _writeToFile(jsonFile , jasonFSBuf)
    jsonString="]"
    _writeToFile(jsonFile , jsonString)
    cd('/')
    return

# FileStore
def _processFileStore(fileStoreName, configFile):
    if (fileStoreName == None):
       raise Exception, "Error: processFileStore: name is None"
    if (configFile == None):
       raise Exception, "Error: processFileStore: script file is None"
    print "Capturing the details of the File Store "+fileStoreName
    cd(fileStoreName)
    try:
        attrList=ls(returnMap='true', returnType='a')
        outBuf=StringBuffer("  <file-store>\n")
        outBuf.append("    <name>").append(fileStoreName).append("</name>\n")
        direc=cmo.getDirectory()
        if (direc != None):
            outBuf.append("    <directory>").append(direc).append("</directory>\n")
        distribute=attrList.get('DistributionPolicy')
        if (distribute != None):
            outBuf.append("    <distribution-policy>").append(distribute).append("</distribution-policy>\n")
        migpolicy=attrList.get('MigrationPolicy')
        if (migpolicy != None):
            outBuf.append("    <migration-policy>").append(migpolicy).append("</migration-policy>\n")
        target=attrList.get('Target')
        if (target != None):
            outBuf.append("    <target>").append(target).append("</target>\n")
        outBuf.append("  </file-store>\n")
        _writeToFile(configFile , outBuf)
        htmlSuccessDict['FileStore'].append(fileStoreName)
    except Exception, ex:
        dumpStack()
        print "Error: Capturing the details of the File Store "+fileStoreName+" failed."
        print ex.getMessage()
        htmlFailedDict[str(fileStoreName)]=str(ex.getMessage())
    cd('..')
    return

def _processJDBCStoresForTarget(targetName, migrateConfigFile, jsonFile, svrList, migtgList):
    cd('/')
    jdbcStores = cmo.getJDBCStores()
    scopedJDBCStores = ArrayList()
    cd('/JDBCStore')
    for i in range(len(jdbcStores)):
        cd(jdbcStores[i].getName())
        _fillScopedResources(cmo,jdbcStores[i].getName(),targetName, scopedJDBCStores, svrList, migtgList, 'JDBCStore')
        cd('..')
    cd ('/')
    if (scopedJDBCStores.size() > 0):
        cd('/JDBCStore')
        jsonString = ",\"jdbc-store\":["
        _writeToFile(jsonFile, jsonString)
    else:
        return
    for i in range(scopedJDBCStores.size()):
        _processJDBCStore(scopedJDBCStores[i], migrateConfigFile)
        jsonSBuf=StringBuffer()
        if (i > 0):
            jsonSBuf.append(",")
        jsonSBuf.append("{\"name\":\"").append(scopedJDBCStores[i]).append("\"")
        jsonSBuf.append(",\"exclude-from-import\":\"false\"")
        jsonSBuf.append(",\"prefix-name\":\"__EXISTING_VALUE__\"}")
        _writeToFile(jsonFile , jsonSBuf)
    jsonString="]"
    _writeToFile(jsonFile , jsonString)    
    cd('/')
    return

# JDBCStore
def _processJDBCStore(jdbcStoreName, configFile):
    if (jdbcStoreName == None):
       raise Exception, "Error: processJDBCStore: name is None"
    if (configFile == None):
       raise Exception, "Error: processJDBCStore: script file is None"
    print "Capturing the details of the JDBC Store "+jdbcStoreName
    cd(jdbcStoreName)
    try:
        attrList=ls(returnMap='true', returnType='a')
        outBuf=StringBuffer("  <jdbc-store>\n")
        outBuf.append("    <name>").append(jdbcStoreName).append("</name>\n")
        prefixName=attrList.get('PrefixName')
        if (prefixName != None):
            outBuf.append("    <prefix-name>").append(prefixName).append("</prefix-name>\n")
        dataSource=attrList.get('DataSource')
        if (dataSource != None):
            outBuf.append("    <data-source>").append(dataSource).append("</data-source>\n")
        target=attrList.get('Target')
        if (target != None):
            outBuf.append("    <target>").append(target).append("</target>\n")
        outBuf.append("  </jdbc-store>\n")
        _writeToFile(configFile , outBuf)
        htmlSuccessDict['JDBCStore'].append(jdbcStoreName)
    except Exception, ex:
        dumpStack()
        print "Error: Capturing the details of the File Store "+jdbcStoreName+" failed."
        print ex.getMessage()
        htmlFailedDict[str(jdbcStoreName)]=str(ex.getMessage())
    cd('..')
    return

def _processPathServicesForTarget(targetName, migrateConfigFile, jsonFile, svrList, migtgList):
    cd('/')
    pathServices = cmo.getPathServices()
    scopedPathServices = ArrayList()
    cd('/PathService')
    for i in range(len(pathServices)):
        cd(pathServices[i].getName())
        _fillScopedResources(cmo,pathServices[i].getName(),targetName, scopedPathServices, svrList, migtgList, 'PathService')
        cd('..')
    cd ('/')
    if (scopedPathServices.size() > 0):
        cd('/PathService')
        jsonString = ",\"path-service\":["
        _writeToFile(jsonFile, jsonString)
    else:
        return
    for i in range(scopedPathServices.size()):
        _processPathService(scopedPathServices[i], migrateConfigFile)
        jsonSBuf=StringBuffer()
        if (i > 0):
            jsonSBuf.append(",")
        jsonSBuf.append("{\"name\":\"").append(scopedPathServices[i]).append("\"")
        jsonSBuf.append(",\"exclude-from-import\":\"false\"}")
        _writeToFile(jsonFile , jsonSBuf)
    jsonString="]"
    _writeToFile(jsonFile , jsonString)
    cd('/')
    return

# PathService
def _processPathService(pathServiceName, configFile):
    if (pathServiceName == None):
       raise Exception, "Error: processPathService: name is None"
    if (configFile == None):
       raise Exception, "Error: _processPathService: script file is None"
    print "Capturing the details of the Path Service "+pathServiceName
    cd(pathServiceName)
    try:
        attrList=ls(returnMap='true', returnType='a')
        outBuf=StringBuffer("  <path-service>\n")
        outBuf.append("    <name>").append(pathServiceName).append("</name>\n")
		
        target=attrList.get('Target')
        if (target != None):
            outBuf.append("    <target>").append(target).append("</target>\n")
        pathfilestore=attrList.get('PersistentStore')
        if (pathfilestore != None and pathfilestore != 'null' and pathfilestore != 'NULL'):
            outBuf.append("    <persistent-store>").append(pathfilestore).append("</persistent-store>\n")			
        outBuf.append("  </path-service>\n")
        _writeToFile(configFile , outBuf)
        htmlSuccessDict['PathService'].append(pathServiceName)
    except Exception, ex:
        dumpStack()
        print "Error: Capturing the details of the Path Service "+pathServiceName+" failed."
        print ex.getMessage()
        htmlFailedDict[str(pathServiceName)]=str(ex.getMessage())
    cd('..')
    return

def _processMessagingBridgesForTarget(targetName, migrateConfigFile, jsonFile, svrList, migtgList):
    cd('/')
    messagingBridges = cmo.getMessagingBridges()
    scopedMessagingBridges = ArrayList()
    cd('/MessagingBridge')
    for i in range(len(messagingBridges)):
        cd(messagingBridges[i].getName())
        _fillScopedResources(cmo,messagingBridges[i].getName(),targetName, scopedMessagingBridges, svrList, migtgList, 'MessagingBridge')
        cd('..')
    cd('/')
    if (scopedMessagingBridges.size() > 0):
        cd('/MessagingBridge')
        jsonString = ",\"messaging-bridge\":["
        _writeToFile(jsonFile, jsonString)
    else:
        return
    for i in range(scopedMessagingBridges.size()):
        _processMessagingBridge(scopedMessagingBridges[i], migrateConfigFile)
        cd(scopedMessagingBridges[i])
        attrList=ls(returnMap='true', returnType='a')
        srcDestination = attrList.get('SourceDestination')
        tgtDestination = attrList.get('TargetDestination')
        cd('..')
        jsonSBuf=StringBuffer()
        if (i > 0):
            jsonSBuf.append(",")
        jsonSBuf.append("{\"name\":\"").append(scopedMessagingBridges[i]).append("\"")
        jsonSBuf.append(",\"exclude-from-import\":\"false\"")
        jsonSBuf.append(",\"jms-bridge-destination\":[")
        if srcDestination is not None or not 'null':
            jsonSBuf.append("{\"name\":\"").append(srcDestination).append("\"")
            jsonSBuf.append(",\"connection-url\":\"__EXISTING_VALUE__\"")
            jsonSBuf.append(",\"user-name\":\"__EXISTING_VALUE__\"")
            jsonSBuf.append(",\"user-password\":\"__EXISTING_VALUE__\"")
            jsonSBuf.append("},")
        if tgtDestination is not None or not 'null':
            jsonSBuf.append("{\"name\":\"").append(tgtDestination).append("\"")
            jsonSBuf.append(",\"connection-url\":\"__EXISTING_VALUE__\"")
            jsonSBuf.append(",\"user-name\":\"__EXISTING_VALUE__\"")
            jsonSBuf.append(",\"user-password\":\"__EXISTING_VALUE__\"")
            jsonSBuf.append("}")
        jsonSBuf.append("]")
        jsonSBuf.append("}")
        _writeToFile(jsonFile , jsonSBuf)
    jsonString="]"
    _writeToFile(jsonFile , jsonString)
    cd('/')
    return

# MessagingBridge
def _processMessagingBridge(msgBridgeName, configFile):
    if (msgBridgeName == None):
       raise Exception, "Error: processMessagingBridge: name is None"
    if (configFile == None):
       raise Exception, "Error: processMessagingBridge: script file is None"
    print "Capturing the details of the Messaging Bridge "+msgBridgeName
    cd(msgBridgeName)
    try:
        attrList=ls(returnMap='true', returnType='a')
        outBuf=StringBuffer("  <messaging-bridge>\n")
        outBuf.append("    <name>").append(msgBridgeName).append("</name>\n")
        sourceDest=attrList.get('SourceDestination')
        if (sourceDest != None):
            outBuf.append("    <source-destination>").append(sourceDest).append("</source-destination>\n")
        targetDest=attrList.get('TargetDestination')
        if (targetDest != None):
            outBuf.append("    <target-destination>").append(targetDest).append("</target-destination>\n")
        target=attrList.get('Target')
        if (target != None):
            outBuf.append("    <target>").append(target).append("</target>\n")
        quality=attrList.get('QualityOfService')
        if (quality != None):
            outBuf.append("    <quality-of-service>").append(quality).append("</quality-of-service>\n")
        qosda=attrList.get('QosDegradationAllowed')
        if (qosda != None):
            outBuf.append("    <qos-degradation-allowed>").append(qosda).append("</qos-degradation-allowed>\n")
        dural=attrList.get('DurabilityEnabled')
        if (dural != None):
            outBuf.append("    <durability-enabled>").append(dural).append("</durability-enabled>\n")
        async=attrList.get('AsyncEnabled')
        if (async != None):
            outBuf.append("    <async-enabled>").append(async).append("</async-enabled>\n")
        preserv=attrList.get('PreserveMsgProperty')
        if (preserv != None):
            outBuf.append("    <preserve-msg-property>").append(preserv).append("</preserve-msg-property>\n")
        started=attrList.get('Started')
        if (started != None):
            outBuf.append("    <started>").append(started).append("</started>\n")
        idlemax=attrList.get('IdleTimeMaximum')
        if (idlemax != None):
            outBuf.append("    <idle-time-maximum>").append(idlemax).append("</idle-time-maximum>\n")
        batchinterval=attrList.get('BatchInterval')
        if (batchinterval != None):
            outBuf.append("    <batch-interval>").append(batchinterval).append("</batch-interval>\n")
        batchsize=attrList.get('BatchSize')
        if (batchsize != None):
            outBuf.append("    <batch-size>").append(batchsize).append("</batch-size>\n")    
        bridgenotes=attrList.get('Notes')
        if (bridgenotes != None):
            outBuf.append("    <notes>").append(bridgenotes).append("</notes>\n")
        reconnectdelayincrease=attrList.get('ReconnectDelayIncrease')
        if (reconnectdelayincrease != None):
            outBuf.append("    <reconnect-delay-increase>").append(reconnectdelayincrease).append("</reconnect-delay-increase>\n")
        reconnectdelaymax=attrList.get('ReconnectDelayMaximum')
        if (reconnectdelaymax != None):
            outBuf.append("    <reconnect-delay-maximum>").append(reconnectdelaymax).append("</reconnect-delay-maximum>\n")
        reconnectdelaymin=attrList.get('ReconnectDelayMinimum')
        if (reconnectdelaymin != None):
            outBuf.append("    <reconnect-delay-minimum>").append(reconnectdelaymin).append("</reconnect-delay-minimum>\n")     
        msgselector=attrList.get('Selector')
        if (msgselector != None):
            outBuf.append("    <selector>").append(msgselector).append("</selector>\n")
        txntimeout=attrList.get('TransactionTimeout')
        if (txntimeout != None):
            outBuf.append("    <transaction-timeout>").append(txntimeout).append("</transaction-timeout>\n")
        outBuf.append("  </messaging-bridge>\n")
        _writeToFile(configFile , outBuf)
        htmlSuccessDict['MessagingBridge'].append(msgBridgeName)
    except Exception, ex:
        dumpStack()
        print "Error: Capturing the details of the Messaging Bridge "+msgBridgeName+" failed."
        print ex.getMessage()
        htmlFailedDict[msgBridgeName]=str(ex.getMessage())
    cd('..')
    return

# Bridge destinations won't be available in the generated json.
# Rather we will capture them in migrate-config.xml
def _processJMSBridgeDestinations(migrateConfigFile, dphelper, userKeyFile):
    cd('/')
    jmsBridgeDestinations = cmo.getJMSBridgeDestinations()
    scopedJMSBridgeDestinations = ArrayList()
    cd('/JMSBridgeDestination')
    for i in range(len(jmsBridgeDestinations)):
        cd(jmsBridgeDestinations[i].getName())
        scopedJMSBridgeDestinations.add(jmsBridgeDestinations[i].getName())
        cd('..')    
    cd('/')
    if (scopedJMSBridgeDestinations.size() > 0):
        cd('/JMSBridgeDestination')
    else:
        return
    for i in range(scopedJMSBridgeDestinations.size()):
        _processJMSBridge(scopedJMSBridgeDestinations[i], migrateConfigFile, dphelper, userKeyFile)
    cd('/')
    return

# JMSBridgeDestination
def _processJMSBridge(jmsBridgeName, configFile, dphelper, userKeyFile):
    if (jmsBridgeName == None):
       raise Exception, "Error: processJMSBridge: name is None"
    if (configFile == None):
       raise Exception, "Error: processJMSBridge: script file is None"
    print "Capturing the details of the JMS Bridge Destination "+jmsBridgeName
    jmsBridgeNameStr=String(jmsBridgeName)
    cd(jmsBridgeNameStr)
    try:
        attrList=ls(returnMap='true', returnType='a')
        outBuf=StringBuffer("  <jms-bridge-destination>\n")
        outBuf.append("    <name>").append(jmsBridgeName).append("</name>\n")
        adpjndiname=attrList.get('AdapterJNDIName')
        if (adpjndiname != None):
            outBuf.append("    <adapter-jndi-name>").append(adpjndiname).append("</adapter-jndi-name>\n")
        classpath=attrList.get('Classpath')
        if (classpath != None):
            outBuf.append("    <classpath>").append(classpath).append("</classpath>\n")
        connfac=attrList.get('ConnectionFactoryJndiName')
        if (connfac != None):
            outBuf.append("    <connection-factory-jndi-name>").append(connfac).append("</connection-factory-jndi-name>\n")
        connurl=attrList.get('ConnectionURL')
        if (connurl != None):
            outBuf.append("    <connection-url>").append(connurl).append("</connection-url>\n")
        destjndi=attrList.get('DestinationJNDIName')
        if (destjndi != None):
            outBuf.append("    <destination-jndi-name>").append(destjndi).append("</destination-jndi-name>\n")
        desttype=attrList.get('DestinationType')
        if (desttype != None and desttype != 'null'):
            outBuf.append("    <destination-type>").append(desttype).append("</destination-type>\n")
        initcntx=attrList.get('InitialContextFactory')
        if (initcntx != None and initcntx != 'null'):
            outBuf.append("    <initial-context-factory>").append(initcntx).append("</initial-context-factory>\n")
        username=attrList.get('UserName')
        if (username != None and username != 'null'):
            outBuf.append("    <user-name>").append(username).append("</user-name>\n")
        upencry=dphelper.getEncryptedAttribute('JMSBridgeDestination', jmsBridgeNameStr, 'UserPasswordEncrypted', userKeyFile);
        if (upencry != None and upencry != 'null' and upencry != ''):
            outBuf.append("    <user-password-encrypted>").append(upencry).append("</user-password-encrypted>\n")
        notes=attrList.get('Notes')
        if (notes != None and notes != 'null'):
            outBuf.append("    <notes>").append(notes).append("</notes>\n")
        outBuf.append("  </jms-bridge-destination>\n")
        _writeToFile(configFile , outBuf)
        htmlSuccessDict['JMSBridgeDestination'].append(jmsBridgeName)
    except Exception, ex:
        dumpStack()
        print "Error: Capturing the details of the JMS Bridge Destination "+jmsBridgeName+" failed."
        print ex.getMessage()
        htmlFailedDict[str(jmsBridgeName)]=str(ex.getMessage())
    cd('..')
    return

def _processJDBCSystemResourcesForTarget(targetName, migrateConfigFile, jsonFile, jdbcOVBuf, migrateRootDir, domainPath, dphelper, userKeyFile, svrList, migtgList):
    cd('/')
    jdbcSystemResources = cmo.getJDBCSystemResources()
    scopedJDBCSystemResources = ArrayList()
    cd('/JDBCSystemResource')
    for i in range(len(jdbcSystemResources)):
        cd(jdbcSystemResources[i].getName())
        _fillScopedResources(cmo,jdbcSystemResources[i].getName(),targetName, scopedJDBCSystemResources, svrList, migtgList, 'JDBCSystemResource')
        cd('..')
    cd('/')
    if (scopedJDBCSystemResources.size() > 0):
        cd('/JDBCSystemResource')
        jsonString = ",\"jdbc-system-resource\":["
        _writeToFile(jsonFile, jsonString)        
    else:
        return
    for i in range(scopedJDBCSystemResources.size()):
        _processJDBC(scopedJDBCSystemResources[i], migrateConfigFile, migrateRootDir, domainPath+"/config", dphelper, userKeyFile)
        jsonSBuf=StringBuffer()
        if (i > 0):
            jsonSBuf.append(",")
        jsonSBuf.append("{\"name\":\"").append(scopedJDBCSystemResources[i]).append("\"")
        jsonSBuf.append(",\"exclude-from-import\":\"false\"}")
        _writeToFile(jsonFile , jsonSBuf)
        jdbcOVBuf.append("{\"name\":\"").append(scopedJDBCSystemResources[i]).append("\"")
        jdbcOVBuf.append(",\"url\":\"__EXISTING_VALUE__\"")
        jdbcOVBuf.append(",\"user\":\"__EXISTING_VALUE__\"")
        jdbcOVBuf.append(",\"password\":\"__EXISTING_VALUE__\"")
        jdbcOVBuf.append("},")
    jsonString="]"
    _writeToFile(jsonFile , jsonString)
    cd('/')
    return

# JDBC
def _processJDBC(jdbcName, configFile, rootDir, domainConfigDir, dphelper, userKeyFile):
    if (jdbcName == None):
       raise Exception, "Error: processJDBC: JDBC name is None"
    if (configFile == None):
       raise Exception, "Error: processJDBC: script file is None"
    if (rootDir == None):
       raise Exception, "Error: processJDBC: export root dir is None"
    print "Capturing the details of the JDBC System Resource "+jdbcName
    cd(jdbcName)
    try:
        outBuf=StringBuffer("  <jdbc-system-resource>\n")
        outBuf.append("    <name>").append(jdbcName).append("</name>\n")
        outBuf.append("    <target>")
        targetList=_getTargets(cmo)
        for i in range(len(targetList)):
            outBuf.append(targetList[i].getName())
            if (i < len(targetList)-1):
               outBuf.append(",")
        outBuf.append("</target>\n")
        jdbcDescriptor=cmo.getDescriptorFileName()
        outBuf.append("    <descriptor-file-name>").append(jdbcDescriptor).append("</descriptor-file-name>\n")
        outBuf.append("  </jdbc-system-resource>\n")
        _writeToFile(configFile , outBuf)
        # descriptor file
        jdbcDir=_createDir(rootDir+"/config/jdbc")
        jdbcDespSrcFile=File(domainConfigDir+"/"+jdbcDescriptor)
        jdbcDespTgtFile=File(jdbcDir, jdbcDespSrcFile.getName())
        jdbcDespTgtFile.createNewFile()
        descriptorDescBuf=StringBuffer()
        _readFile(jdbcDespSrcFile, descriptorDescBuf)
        descriptorDesc=String(descriptorDescBuf)
        beanPath=pwd()
        if (dphelper != None):
            beanDescriptorWithNewPwd = dphelper.exportDescriptor(beanPath, descriptorDesc, userKeyFile, true)
        else:
            beanDescriptorWithNewPwd = descriptorDesc
        _writeToFile(jdbcDespTgtFile, beanDescriptorWithNewPwd)
        htmlSuccessDict['JDBCSystemResource'].append(jdbcName)
    except Exception, ex:
        dumpStack()
        print "Error: Capturing the details of the JDBC Store "+jdbcName+" failed."
        print ex
        ex.printStackTrace()
        htmlFailedDict[str(jdbcName)]=str(ex.getMessage())
    cd('..')
    return

def _processJMSSystemResourcesForTarget(targetName, migrateConfigFile, jmsRsOVBuf, jsonFile, migrateRootDir, domainPath, dphelper, userKeyFile, svrList, migtgList):
    cd('/')
    jmsSystemResources = cmo.getJMSSystemResources()
    scopedJMSSystemResources = ArrayList()
    cd('/JMSSystemResource')
    for i in range(len(jmsSystemResources)):
        cd(jmsSystemResources[i].getName())
        _fillScopedResources(cmo,jmsSystemResources[i].getName(),targetName, scopedJMSSystemResources, svrList, migtgList, 'JMSSystemResource')
        cd('..')
    cd('/')
    if (scopedJMSSystemResources.size() > 0):
        cd('/JMSSystemResource')
        jsonString = ",\"jms-system-resource\":["
        _writeToFile(jsonFile, jsonString)
    else:
        return
    for i in range(scopedJMSSystemResources.size()):
        if (i > 0):
            jsonString=","
            _writeToFile(jsonFile , jsonString)
        jmsRsForeignSvrOVBuf=StringBuffer("[ ")
        _processJMSResource(scopedJMSSystemResources[i], migrateConfigFile, migrateRootDir, domainPath+"/config", jmsRsForeignSvrOVBuf, jsonFile, dphelper, userKeyFile)
        if (jmsRsForeignSvrOVBuf.length() > 2):
            jmsRsForeignSvrOVBuf.append("]")
            jmsRsOVBuf.append("{\"name\":\"").append(scopedJMSSystemResources[i]).append("\"")
            jmsRsOVBuf.append(",\"foreign-server-override\":").append(jmsRsForeignSvrOVBuf)
            jmsRsOVBuf.append("},")
    jsonString="]"
    _writeToFile(jsonFile , jsonString)
    cd('/')
    return

# JMS Resource
def _processJMSResource(jmsName, configFile, rootDir, domainConfigDir, jmsRsForeignSvrOVBuf, jsonFile, dphelper, userKeyFile):
    if (jmsName == None):
       raise Exception, "Error: processJMSResource: JMS name is None"
    if (configFile == None):
       raise Exception, "Error: processJMSResource: script file is None"
    if (rootDir == None):
       raise Exception, "Error: processJMSResource: export root dir is None"
    print "Capturing the details of the JMS System Resource "+jmsName
    safRemoteCtxList = None
    try:
        cd('/JMSSystemResource/' + jmsName + '/JmsResource/NO_NAME_0/')
        jmsResourceTypes = ls(returnMap='true')
        if jmsResourceTypes.contains("SAFRemoteContext"):
            cd('SAFRemoteContext')
            safRemoteCtxList = ls(returnMap='true')
    except Exception,ex:
        print "[Warning]: Capturing the details of the SAF Remote Context(s) failed for JMS System Resource "+jmsName
        print ex.getMessage()
    cd('/JMSSystemResource')
    cd(jmsName)
    try:
        # json
        jsonBuf=StringBuffer("{\"name\":\"").append(jmsName).append("\"")
        jsonBuf.append(",\"exclude-from-import\":\"false\"")
        # config
        outBuf=StringBuffer("  <jms-system-resource>\n")
        outBuf.append("    <name>").append(jmsName).append("</name>\n")
        outBuf.append("    <target>")
        targetList=_getTargets(cmo)
        for i in range(len(targetList)):
            outBuf.append(targetList[i].getName())
            if (i < len(targetList)-1):
               outBuf.append(",")
        outBuf.append("</target>\n")
        jmsRDescriptor=cmo.getDescriptorFileName()
        outBuf.append("    <descriptor-file-name>").append(jmsRDescriptor).append("</descriptor-file-name>\n")
        currRootList=ls(returnMap='true')
        if(currRootList.contains('SubDeployment')):
            cd('SubDeployment')
            jsonBuf.append(",\"sub-deployment\":[")
            subDepList=ls(returnMap='true')
            for i in range(subDepList.size()):
                cd(subDepList[i])
                outBuf.append("    <sub-deployment>\n")
                outBuf.append("      <name>").append(subDepList[i]).append("</name>\n")
                outBuf.append("      <target>")
                subDepTargets=_getTargetsForSubDeployments(cmo)
                if (subDepTargets != ""):
                    outBuf.append(subDepTargets)
                outBuf.append("</target>\n")
                outBuf.append("    </sub-deployment>\n")
                if (i > 0):
                    jsonBuf.append(",")
                jsonBuf.append("{\"name\":\"").append(subDepList[i]).append("\"")
                jsonBuf.append(",\"exclude-from-import\":\"false\"}")
                cd('..')
            cd('..')
            jsonBuf.append("]")
        outBuf.append("  </jms-system-resource>\n")
        _writeToFile(configFile , outBuf)
        # descriptor file
        jmsDespSrcFile=File(domainConfigDir+"/"+jmsRDescriptor)
        jmsDespTgtFile=_createJMSModuleFile(rootDir + "/config/" + jmsRDescriptor)
        descriptorDescBuf=StringBuffer()
        _readFile(jmsDespSrcFile, descriptorDescBuf)
        descriptorDesc=String(descriptorDescBuf)
        beanDescriptorWithNewPwd=descriptorDesc
        beanPath=pwd()
        if (dphelper != None):
            beanDescriptorWithNewPwd = dphelper.exportDescriptor(beanPath, descriptorDesc, userKeyFile, true)
        _writeToFile(jmsDespTgtFile, beanDescriptorWithNewPwd)
        fsbegin = descriptorDesc.indexOf("<foreign-server ")
        while (fsbegin > 0):
           fsend = descriptorDesc.indexOf("</foreign-server", fsbegin)
           namebgn = descriptorDesc.indexOf("name=", fsbegin) + 6
           nameend = descriptorDesc.indexOf("\"", namebgn) 
           jmsRsForeignSvrOVBuf.append("{\"name\":\"").append(descriptorDesc.substring(namebgn,nameend)).append("\"") 
           foreignDestOVBuf=StringBuffer("[ ")
           fdsbgn = descriptorDesc.indexOf("<foreign-destination ", fsbegin)
           while ((fdsbgn > 0) and (fdsbgn < fsend)):
               namebgn = descriptorDesc.indexOf("name=", fdsbgn) + 6
               nameend = descriptorDesc.indexOf("\"", namebgn) 
               foreignDestOVBuf.append("{\"name\":\"").append(descriptorDesc.substring(namebgn,nameend)).append("\"")
               foreignDestOVBuf.append(",\"remote-jndi-name\":\"__EXISTING_VALUE__\"},")     
               fdsbgn = descriptorDesc.indexOf("<foreign-destination ", nameend)
           foreignDestOVBuf.deleteCharAt(foreignDestOVBuf.length()-1)
           foreignDestOVBuf.append("]")
           jmsRsForeignSvrOVBuf.append(",\"foreign-destination-override\":").append(foreignDestOVBuf)
           foreignConnFacOVBuf=StringBuffer("[ ")
           fcfbgn = descriptorDesc.indexOf("<foreign-connection-factory ", fsbegin)
           while ((fcfbgn > 0) and (fcfbgn < fsend)):
               namebgn = descriptorDesc.indexOf("name=", fcfbgn) + 6
               nameend = descriptorDesc.indexOf("\"", namebgn) 
               foreignConnFacOVBuf.append("{\"name\":\"").append(descriptorDesc.substring(namebgn,nameend)).append("\"")
               foreignConnFacOVBuf.append(",\"remote-jndi-name\":\"__EXISTING_VALUE__\"")     
               foreignConnFacOVBuf.append(",\"username\":\"__EXISTING_VALUE__\"")     
               foreignConnFacOVBuf.append(",\"password\":\"__EXISTING_VALUE__\"},")     
               fcfbgn = descriptorDesc.indexOf("<foreign-connection-factory ", nameend)
           foreignConnFacOVBuf.deleteCharAt(foreignConnFacOVBuf.length()-1)
           foreignConnFacOVBuf.append("]")
           jmsRsForeignSvrOVBuf.append(",\"foreign-connection-factory-override\":").append(foreignConnFacOVBuf) 
           jmsRsForeignSvrOVBuf.append("},")
           fsbegin = descriptorDesc.indexOf("<foreign-server ", fsend)
        # 
        jmsRsForeignSvrOVBuf.deleteCharAt(jmsRsForeignSvrOVBuf.length()-1)
        #jsonBuf.append(",\"queue\":[")
        #queuebegin = descriptorDesc.indexOf("queue name=")
        #while (queuebegin > 0):
        #   namebgn = queuebegin + 12 
        #   nameend = descriptorDesc.indexOf("\"", namebgn) 
        #   jsonBuf.append("{\"name\":\"").append(descriptorDesc.substring(namebgn,nameend)).append("\"") 
        #   jsonBuf.append(",\"exclude-from-import\":\"false\"}")
        #   queuebegin = descriptorDesc.indexOf("queue name=", nameend)
        #   if (queuebegin > 0):
        #       jsonBuf.append(",")
        #jsonBuf.append("]");
        #
        if safRemoteCtxList is not None:
            jsonBuf.append(",\"saf-remote-context\":")
            jsonBuf.append("[")
            for i in range(safRemoteCtxList.size()):
                if i>0:
                    jsonBuf.append(',')
                jsonBuf.append("{")
                jsonBuf.append("\"name\":")
                jsonBuf.append("\"").append(safRemoteCtxList[i]).append("\"")
                jsonBuf.append(",\"loginURL\":\"__EXISTING_VALUE__\"")
                jsonBuf.append(",\"username\":\"__EXISTING_VALUE__\"")
                jsonBuf.append(",\"password\":\"__EXISTING_VALUE__\"")
                jsonBuf.append("}")
            jsonBuf.append("]")
        jsonBuf.append("}");
        _writeToFile(jsonFile , jsonBuf)
        htmlSuccessDict['JMSSystemResource'].append(jmsName)
    except Exception, ex:
        dumpStack()
        print "Error: Capturing the details of the JMS System Resource "+jmsName+" failed."
        print ex.getMessage()
        htmlFailedDict[str(jmsName)]=str(ex.getMessage())
    cd('..')
    return

def _processSAFAgentsForTarget(targetName, migrateConfigFile, jsonFile, svrList, migtgList):
    cd('/')
    safAgents = cmo.getSAFAgents()
    scopedSAFAgents = ArrayList()
    cd('/SAFAgent')
    for i in range(len(safAgents)):
        cd(safAgents[i].getName())
        _fillScopedResources(cmo,safAgents[i].getName(),targetName, scopedSAFAgents, svrList, migtgList, 'SAFAgent')
        cd('..')
    cd ('/')
    if (scopedSAFAgents.size() > 0):
        cd('/SAFAgent')
        jsonString = ",\"saf-agent\":["
        _writeToFile(jsonFile, jsonString)        
    else:
        return
    for i in range(scopedSAFAgents.size()):
        _processSAF(scopedSAFAgents[i], migrateConfigFile)
        jsonSBuf=StringBuffer()
        if (i > 0):
            jsonSBuf.append(",")
        jsonSBuf.append("{\"name\":\"").append(scopedSAFAgents[i]).append("\"")
        jsonSBuf.append(",\"exclude-from-import\":\"false\"}")
        _writeToFile(jsonFile , jsonSBuf)        
    jsonString="]"
    _writeToFile(jsonFile , jsonString)
    cd('/')
    return

# SAFAgent
def _processSAF(safName, configFile):
    if (safName == None):
       raise Exception, "Error: processSAF: saf name is None"
    if (configFile == None):
       raise Exception, "Error: processSAF: script file is None"
    print "Capturing the details of the SAF Agent "+safName
    cd(safName)
    try:
        outBuf=StringBuffer("  <saf-agent>\n")
        outBuf.append("    <name>").append(safName).append("</name>\n")
        outBuf.append("    <target>")
        targetList=_getTargets(cmo)
        for i in range(len(targetList)):
            outBuf.append(targetList[i].getName())
            if (i < len(targetList)-1):
                outBuf.append(",")
        outBuf.append("</target>\n")
        serviceType=cmo.getServiceType()
        if (serviceType != None):
            outBuf.append("    <service-type>").append(serviceType).append("</service-type>\n")
        store=cmo.getStore()
        if (store != None):
            outBuf.append("    <store>").append(store.getName()).append("</store>\n")
        outBuf.append("  </saf-agent>\n")
        _writeToFile(configFile , outBuf)
        htmlSuccessDict['SAFAgent'].append(safName)
    except Exception, ex:
        dumpStack()
        print "Error: Capturing the details of the SAF Agent "+safName+" failed."
        print ex.getMessage()
        htmlFailedDict[str(safName)]=str(ex.getMessage())
    cd('..')
    return

def _processMailSessionsForTarget(targetName, migrateConfigFile, jsonFile, svrList, migtgList):
    cd('/')
    mailSessions = cmo.getMailSessions()
    scopedMailSessions = ArrayList()
    cd('/MailSession')
    for i in range(len(mailSessions)):
        nameStr=String(mailSessions[i].getName())
        if (nameStr.indexOf("/") >= 0):
            nameStr=String("("+mailSessions[i].getName()+")")
            cd(nameStr)
        else:
            cd(mailSessions[i].getName())
        _fillScopedResources(cmo,mailSessions[i].getName(),targetName, scopedMailSessions, svrList, migtgList, 'MailSession')
        cd('..')
    cd('/')
    if (scopedMailSessions.size() > 0):
        cd('/MailSession')
        jsonString = ",\"mail-session\":["
        _writeToFile(jsonFile, jsonString)        
    else:
        return
    for i in range(scopedMailSessions.size()):
        _processMailSess(scopedMailSessions[i], migrateConfigFile)
        jsonSBuf=StringBuffer()
        if (i > 0):
            jsonSBuf.append(",")
        jsonSBuf.append("{\"name\":\"").append(scopedMailSessions[i]).append("\"")
        jsonSBuf.append(",\"exclude-from-import\":\"false\"}")
        _writeToFile(jsonFile , jsonSBuf)
    jsonString="]"
    _writeToFile(jsonFile , jsonString)
    cd('/')
    return

# MailSession
def _processMailSess(mailName, configFile):
    if (mailName == None):
       raise Exception, "Error: processMailSess: mail name is None"
    if (configFile == None):
       raise Exception, "Error: processMailSess: script file is None"
    print "Capturing the details of the Mail Session "+mailName
    nameStr=String(mailName)
    if (nameStr.indexOf("/") >= 0):
        nameStr=String("("+mailName+")")
        cd(nameStr)
    else:
        cd(mailName)
    try:
        outBuf=StringBuffer("  <mail-session>\n")
        outBuf.append("    <name>").append(mailName).append("</name>\n")
        outBuf.append("    <jndi-name>").append(cmo.getJNDIName()).append("</jndi-name>\n")
        properties=cmo.getProperties()
        if (properties != None):
            propStr=""
            keys=properties.stringPropertyNames()
            for i in range(keys.size()):
                pname=keys[i]
                propStr=propStr+pname+"="+properties.getProperty(pname)+";" 
            outBuf.append("    <properties>").append(propStr).append("</properties>\n")
        outBuf.append("  </mail-session>\n")
        _writeToFile(configFile , outBuf)
        htmlSuccessDict['MailSession'].append(mailName)
    except Exception, ex:
        dumpStack()
        print "Error: Capturing the details of the Mail Session "+mailName+" failed."
        print ex.getMessage()
        htmlFailedDict[str(mailName)]=str(ex.getMessage())
    cd('..')
    return

# component-specific export handlers
def _processComponentHandlers(configFile, compDir):
    print "processing component-specific export handlers"
    h = handler.BaseComponentHandler(None)
    h.run(configFile, compDir)
    return


# add contents to export-report
def _getExportReportHTML(htmlFile, dphelper, version, domainName, domainPath, dictExportedServers, dictFailedServers):
    htmlHeader='''<html xmlns:t="http://xmlns.oracle.com/liftandshift/activity"
	           <body><h2 align="center">Domain to Partition Conversion Tool (D-PCT) Export Activity Report</h2>'''
    _writeToFile(htmlFile, htmlHeader)

    htmlRowTemplate = str('''<tr style="outline: thin solid">
        <td style="outline: thin solid">%s</td><td style="outline: thin solid">%s</td></tr>''')
    htmlRowHeader = str('''<tr bgcolor="#F2F5A9" style="outline: thin solid">
        <th style="outline: thin solid">%s</th><th style="outline: thin solid">%s</th></tr>''')

    domainSummaryHeader = str('''<tr bgcolor="#F2F5A9" style="outline: thin solid"><th style="outline: thin solid">Domain Name</th>
	    <th style="outline: thin solid">Version</th><th style="outline: thin solid">Domain Home Location</th><th style="outline: thin solid">Date Created</th></tr>''')
    domainSummaryTemplate = str('''<tr style="outline: thin solid"><td style="outline: thin solid">%s</td>
        <td style="outline: thin solid">%s</td><td style="outline: thin solid">%s</td><td style="outline: thin solid">%s</td></tr> ''')

    _writeToFile(htmlFile, str('''<table cellpadding="5">'''))
    _writeToFile(htmlFile, domainSummaryHeader)
    _writeToFile(htmlFile, domainSummaryTemplate % (domainName, version, domainPath, str(time.strftime("%c"))))
    _writeToFile(htmlFile, str('''</table>'''))

    _writeToFile(htmlFile, str('''<table cellpadding="5">'''))
    listExportSupported=['PathService','MailSession','SAFAgent','JMSSystemResource','JMS Server','JDBCSystemResource','JMSBridgeDestination','MessagingBridge','JDBCStore',
        'FileStore','Library','AppDeployment','MigratableTarget','Cluster','Server']
    exportSupportedRowHeader = '''<tr bgcolor="#F2F5A9" style="outline: thin solid">
        <th style="outline: thin solid">D-PCT supports only the following list of resource types to be exported</th></tr>'''
    exportSupportedTemplate = str('''<tr style="outline: thin solid"><td style="outline: thin solid">%s</td></tr>''')
    _writeToFile(htmlFile, exportSupportedRowHeader)
    _writeToFile(htmlFile, exportSupportedTemplate % (', '.join(listExportSupported)))
    _writeToFile(htmlFile, str('''</table>'''))

    _writeToFile(htmlFile, str('''<table cellpadding="5">'''))
    if(dictExportedServers):
        htmlHeader1 = '''<tr><td colspan="5"><br/><br/></td></tr> <tr><td colspan="5"> <h4> Successfully Exported Servers </h4> </td></tr>'''
        _writeToFile(htmlFile, htmlHeader1)
        _writeToFile(htmlFile, htmlRowHeader %("Server Name","Equivalent Virtual Target"))
        for key, value in dictExportedServers.iteritems():
            _writeToFile(htmlFile , htmlRowTemplate % (key,value))

    if(dictFailedServers):
        htmlHeader2 = '''<tr><td colspan="5"><br/><br/></td></tr> <tr><td colspan="5"> <h4> Servers Failed To Export </h4> </td></tr>'''
        _writeToFile(htmlFile, htmlHeader2)
        _writeToFile(htmlFile, htmlRowHeader %("Server Name","Exception Message"))
        for key, value in dictFailedServers.iteritems():
            _writeToFile(htmlFile , htmlRowTemplate % (key,value))

    for v in htmlSuccessDict.values():
        if v:
            htmlHeader3 = '''<tr><td colspan="5"><br/><br/></td></tr> <tr><td colspan="5"> <h4> Successfully Exported Resources </h4> </td></tr>'''
            _writeToFile(htmlFile, htmlHeader3)
            _writeToFile(htmlFile, htmlRowHeader %("Resource Type","Resource Names"))
            for key, value in htmlSuccessDict.iteritems():
                if value: _writeToFile(htmlFile , htmlRowTemplate % (key,', '.join(value)))
            break

    if(htmlFailedDict):
        htmlHeader4 = '''<tr><td colspan="5"><br/><br/></td></tr> <tr><td colspan="5"> <h4> Resources Failed To Export </h4> </td></tr>'''
        _writeToFile(htmlFile, htmlHeader4)
        _writeToFile(htmlFile, htmlRowHeader %("Resource Name","Exception Message"))
        for key, value in htmlFailedDict.iteritems():
            _writeToFile(htmlFile , htmlRowTemplate % (key,value))

    jmap = dphelper.skippedResources
    skipResourcesMessageMap ={'SAFID':'The SAF Imported Destinations which are default targeted are ignored for export',
       'UDD':'The Uniform Distributed Destinations which are default targeted are ignored for export',
       'WDD':'The Weighted Distributed Destinations are ignored for export'}

    if(not(jmap.isEmpty())):
        htmlHeader5 = '''<tr><td colspan="5"><br/><br/></td></tr><tr><td colspan="5"> <h4>JDBC/JMS Resources For Which Export is Not supported</h4> </td></tr>'''
        _writeToFile(htmlFile, htmlHeader5)
        _writeToFile(htmlFile, htmlRowHeader %("Message","List of Resources"))
        for key in jmap:
            if jmap[key] : _writeToFile(htmlFile , htmlRowTemplate % (skipResourcesMessageMap[key],', '.join(jmap[key])))

    for v in htmlUnScoppedResourcesDict.values():
        if v:
            htmlHeader6 = '''<tr><td colspan="5"><br/><br/></td></tr> <tr><td colspan="5"> <h4>Resources Which Are Un-Targeted Will Not Be Exported</h4> </td></tr>'''
            _writeToFile(htmlFile, htmlHeader6)
            _writeToFile(htmlFile, htmlRowHeader %("Resource Type","Resource Names"))
            for key, value in htmlUnScoppedResourcesDict.iteritems():
                if value: _writeToFile(htmlFile , htmlRowTemplate % (key,', '.join(value)))
            break

    for skr in htmlMultiTargetedResourcesDict.values():
        if skr:
            htmlHeader7 = '''<tr><td colspan="5"><br/><br/></td></tr> <tr><td colspan="5"> <h4>Resources Targeted To More Than One WebLogic Servers and/or Clusters, Will Not Be Exported</h4> </td></tr>'''
            _writeToFile(htmlFile, htmlHeader7)
            _writeToFile(htmlFile, htmlRowHeader %("Resource Type","Resource Names"))
            for key, value in htmlMultiTargetedResourcesDict.iteritems():
                if value: _writeToFile(htmlFile , htmlRowTemplate % (key,', '.join(value)))
            break

    _writeToFile(htmlFile , "</table> </body> </html>")
    return

# MAIN tool command 
# exportForMigration
def exportForMigration(expArchPath, domainPath, includeAppsNLibs=None, userKeyFile=None, applicationNames=None, implementationVersion=None):
    print "Exporting a domain for migration ...."
    print "Reading the domain from path "+domainPath
    readDomain(domainPath)
    configDirPath=domainPath+"/config" 
    dphelper=DescriptorHelper(configDirPath)

    cd('/')
    domainName=cmo.getName()
    print "Domain Name: "+domainName

    migrateRootDir=expArchPath+"/"+domainName
    outDomainDir=_createDir(migrateRootDir)
    outConfigDir=_createDir(migrateRootDir+"/config")

    if (dphelper != None):
        dphelper.setSecretKeyFileDir(migrateRootDir)

    migrateConfigFile=File(migrateRootDir+"/"+DescriptorHelper.MIGRATION_XML)
    if (migrateConfigFile.createNewFile() == false):
        raise Exception, "Cannot create new migrate script file at "+migrateRootDir

    jsonFile=File(expArchPath+"/"+domainName+DescriptorHelper.ATTRIBUTES_JSON)
    if (jsonFile.exists() == true):
        jsonFile.delete()
    if (jsonFile.createNewFile() == false):
        raise Exception, "Cannot create new json file at "+expArchPath
    jsonCFile=File(migrateRootDir+"/"+domainName+DescriptorHelper.ATTRIBUTES_JSON)
    if (jsonCFile.createNewFile() == false):
        raise Exception, "Cannot create new json file at "+migrateRootDir

    htmlFile=File(expArchPath+"/"+domainName+DescriptorHelper.REPORT_HTML)
    if (htmlFile.exists() == true):
        htmlFile.delete()
    if (htmlFile.createNewFile() == false):
        raise Exception, "Cannot create new html file at "+expArchPath

    # domain header string
    outString="<?xml version='1.0' encoding='UTF-8'?>\n"+ \
              "<domain xmlns=\"http://xmlns.oracle.com/weblogic/domain\">\n"+ \
              "  <name>${DOMAIN_NAME}</name>\n  <domain-version>${DOMAIN_VERSION}</domain-version>\n"+ \
              "  <!-- export-domain-version:"+cmo.getDomainVersion()+":export-domain-version -->\n"
    _writeToFile(migrateConfigFile , outString)

    # json file begin
    jsonString="{"
    _writeToFile(jsonFile , jsonString)

    # domain component list
    if (String(version).contains('Server 12.2')):
        setShowLSResult(false)
    cd('/')
    domainRootList=ls(returnMap='true')

    # json virtual-target
    jsonSvrsVTBuf=StringBuffer("[ ")
    jsonAvailableTargetsBuf=StringBuffer("[ ")
    jsonVTBuf=StringBuffer("\"virtual-target\":[ ")

    # Dictionaries used for reporting
    htmlSuccessDict['MailSession']=[]
    htmlSuccessDict['SAFAgent']=[]
    htmlSuccessDict['JMSSystemResource']=[]
    htmlSuccessDict['JDBCSystemResource']=[]
    htmlSuccessDict['JMSBridgeDestination']=[]
    htmlSuccessDict['MessagingBridge']=[]
    htmlSuccessDict['PathService']=[]
    htmlSuccessDict['JDBCStore']=[]
    htmlSuccessDict['FileStore']=[]
    htmlSuccessDict['Library']=[]
    htmlSuccessDict['AppDeployment']=[]
    htmlSuccessDict['JMSServer']=[]

    htmlUnScoppedResourcesDict['JMSServer']=[]
    htmlUnScoppedResourcesDict['AppDeployment']=[]
    htmlUnScoppedResourcesDict['Library']=[]
    htmlUnScoppedResourcesDict['FileStore']=[]
    htmlUnScoppedResourcesDict['JDBCStore']=[]
    htmlUnScoppedResourcesDict['PathService']=[]
    htmlUnScoppedResourcesDict['MessagingBridge']=[]
    htmlUnScoppedResourcesDict['JDBCSystemResource']=[]
    htmlUnScoppedResourcesDict['JMSSystemResource']=[]
    htmlUnScoppedResourcesDict['SAFAgent']=[]
    htmlUnScoppedResourcesDict['MailSession']=[]

    htmlMultiTargetedResourcesDict['JMSServer']=[]
    htmlMultiTargetedResourcesDict['AppDeployment']=[]
    htmlMultiTargetedResourcesDict['Library']=[]
    htmlMultiTargetedResourcesDict['FileStore']=[]
    htmlMultiTargetedResourcesDict['JDBCStore']=[]
    htmlMultiTargetedResourcesDict['PathService']=[]
    htmlMultiTargetedResourcesDict['MessagingBridge']=[]
    htmlMultiTargetedResourcesDict['JDBCSystemResource']=[]
    htmlMultiTargetedResourcesDict['JMSSystemResource']=[]
    htmlMultiTargetedResourcesDict['SAFAgent']=[]
    htmlMultiTargetedResourcesDict['MailSession']=[]

    # Map to store the prefix for future RG/RGT/VT and the associated target
    targetsMap = HashMap()
    svrList = ArrayList()
    migtgList = ArrayList()

    # Server
    if (domainRootList.contains('Server')):
        print "Working on WebLogic Server(s) ..."
        cd('/Server')
        svrList=ls(returnMap='true')
        dictExportedServers={}
        dictFailedServers={}
        for i in range(svrList.size()):
            _processServer(svrList[i], migrateConfigFile, jsonVTBuf, jsonAvailableTargetsBuf, dictExportedServers, dictFailedServers, targetsMap)
            jsonSvrsVTBuf.append("{\"virtual-target\":{")
            jsonSvrsVTBuf.append("\"name\":\"${PARTITION_NAME}-").append(svrList[i]).append("-virtualTarget\"}},")
    cd('/')
    jsonSvrsVTBuf.deleteCharAt(jsonSvrsVTBuf.length()-1)
    jsonSvrsVTBuf.append("]")

    # Cluster
    jsonClusterVTBuf=None
    if (domainRootList.contains('Cluster')):
        print "Working on WebLogic Cluster(s) ..."
        jsonClusterVTBuf=StringBuffer("[ ")
        cd('/Cluster')
        clusList=ls(returnMap='true')
        htmlSuccessDict['Cluster']=[]
        for i in range(clusList.size()):
            _processCluster(clusList[i], migrateConfigFile, jsonVTBuf)
            jsonClusterVTBuf.append("{\"virtual-target\":{")
            jsonClusterVTBuf.append("\"name\":\"${PARTITION_NAME}-").append(clusList[i]).append("-virtualTarget\"}},")
            jsonAvailableTargetsBuf.append("{\"virtual-target\":{")
            jsonAvailableTargetsBuf.append("\"name\":\"${PARTITION_NAME}-").append(clusList[i]).append("-virtualTarget\"}},")
            targetsMap.put("${PARTITION_NAME}-" + clusList[i],clusList[i]);
    cd('/')
    jsonVTBuf.deleteCharAt(jsonVTBuf.length()-1)
    jsonVTBuf.append("]")
    jsonAvailableTargetsBuf.deleteCharAt(jsonAvailableTargetsBuf.length()-1)
    jsonAvailableTargetsBuf.append("]")
    _writeToFile(jsonFile , jsonVTBuf)
    if (jsonClusterVTBuf != None):
        jsonClusterVTBuf.deleteCharAt(jsonClusterVTBuf.length()-1)
        jsonClusterVTBuf.append("]")

    # MigratableTarget
    if (domainRootList.contains('MigratableTarget')):
        print "Working on Migratable Target(s) ..."
        cd('/MigratableTarget')
        migtgList=ls(returnMap='true')
        htmlSuccessDict['MigratableTarget']=[]
        for i in range(migtgList.size()):
            _processMigratableTarget(migtgList[i], migrateConfigFile)
    cd('/')

    # bits flag
    includeAppsFlag=true
    if (includeAppsNLibs != None):
        if ('false' == includeAppsNLibs):
            includeAppsFlag=false
        else:
            if (false == includeAppsNLibs):
                includeAppsFlag=false

    # Create a resource group for every WL Server and Cluster in the source domain
    jsonRGBuf = None
    # There will be at least one element in the targetsMap, for admin server
    jsonString = ",\"resource-group\":["
    _writeToFile(jsonFile , jsonString)
    counter = 0;

    jdbcOVBuf=StringBuffer("[ ")
    jmsRsOVBuf=StringBuffer("[ ")
             
    for entry in targetsMap.entrySet():
        mapKey = entry.key
        rgName =  mapKey + "-RG"
        vtName = mapKey + "-virtualTarget"
        targetName = entry.value

        if (counter > 0):
            jsonString = ","
            _writeToFile(jsonFile , jsonString)

        jsonString = "{\"name\":\"" + rgName + "\", \"target\":[{\"virtual-target\":{\"name\":\"" + vtName + "\"}}]"
        _writeToFile(jsonFile , jsonString)

        # Handle all the resources targeted to this RG
        # process app deployments
        if (domainRootList.contains('AppDeployment')):
            _processAppDeploymentsForTarget(targetName, applicationNames, migrateConfigFile, migrateRootDir, includeAppsFlag, jsonFile, domainPath, dphelper, userKeyFile, svrList, migtgList)

        # process libraries
        if (domainRootList.contains('Library')):
            _processLibrariesForTarget(targetName, migrateConfigFile, migrateRootDir, includeAppsFlag, jsonFile, domainPath, svrList, migtgList)

        # process file stores
        if (domainRootList.contains('FileStore')):
            _processFileStoresForTarget(targetName, migrateConfigFile, jsonFile, svrList, migtgList)

        # process JDBC stores
        if (domainRootList.contains('JDBCStore')):
            _processJDBCStoresForTarget(targetName, migrateConfigFile, jsonFile, svrList, migtgList)

        # process Path Services
        if (domainRootList.contains('PathService')):
            _processPathServicesForTarget(targetName, migrateConfigFile, jsonFile, svrList, migtgList)

        # process Messaging Bridges
        if (domainRootList.contains('MessagingBridge')):
            _processMessagingBridgesForTarget(targetName, migrateConfigFile, jsonFile, svrList, migtgList)

        # process JDBC System Resources            
        if (domainRootList.contains('JDBCSystemResource')):
            _processJDBCSystemResourcesForTarget(targetName, migrateConfigFile, jsonFile, jdbcOVBuf, migrateRootDir, domainPath, dphelper, userKeyFile, svrList, migtgList)

        # process JMS Servers
        if (domainRootList.contains('JMSServer')):
            _processJMSServersForTarget(targetName, migrateConfigFile, jsonFile, svrList, migtgList)

        # process JMS System Resources            
        if (domainRootList.contains('JMSSystemResource')):
            _processJMSSystemResourcesForTarget(targetName, migrateConfigFile, jmsRsOVBuf, jsonFile, migrateRootDir, domainPath, dphelper, userKeyFile, svrList, migtgList)

        # process Mail Sessions
        if (domainRootList.contains('MailSession')):
            _processMailSessionsForTarget(targetName, migrateConfigFile, jsonFile, svrList, migtgList)

        # process SAF Agents
        if (domainRootList.contains('SAFAgent')):
            _processSAFAgentsForTarget(targetName, migrateConfigFile, jsonFile, svrList, migtgList)

        # Resource Group Template
        jsonRGBuf = StringBuffer(",\"resource-group-template\":{")
        jsonRGBuf.append("\"name\":\"").append(mapKey).append("-RGTemplate").append("\"}")
        jsonRGBuf.append("}")
        counter += 1
        _writeToFile(jsonFile , jsonRGBuf)
    jsonString = "]"
    _writeToFile(jsonFile , jsonString)
    # Writing resource-group(s) to json ends here

    jdbcOVBuf.deleteCharAt(jdbcOVBuf.length()-1)
    jdbcOVBuf.append("]")

    jmsRsOVBuf.deleteCharAt(jmsRsOVBuf.length()-1)
    jmsRsOVBuf.append("]")
    cd('/')

    # process JMS Bridge Destinations once per domain
    if (domainRootList.contains('JMSBridgeDestination')):
        _processJMSBridgeDestinations(migrateConfigFile, dphelper, userKeyFile)

    # Security Security SecurityConfiguration Credential Keystore

    # embedded-ldap EmbeddedLDAP

    jsonBuf=StringBuffer()
    # json partition
    jsonBuf.append(",\"partition\":{")
    jsonDfVTBuf=jsonSvrsVTBuf
    if (jsonClusterVTBuf != None):
        jsonDfVTBuf=jsonClusterVTBuf
    jsonBuf.append("\"default-target\":").append(jsonDfVTBuf)
    jsonBuf.append(",\"jdbc-system-resource-override\":").append(jdbcOVBuf)
    jsonBuf.append(",\"jms-system-resource-override\":").append(jmsRsOVBuf)
    jsonBuf.append(",\"realm\":\"__EXISTING_VALUE__\"")
    jsonBuf.append(",\"available-target\":").append(jsonAvailableTargetsBuf)
    jsonBuf.append("}")
    # Add WebLogic version information for the version information    
    if (implementationVersion != None):
        jsonBuf.append(",\"implementation-version\":\"").append(implementationVersion).append("\"")
    # json file end
    jsonBuf.append("}")
    _writeToFile(jsonFile , jsonBuf)
    _copyFile(jsonFile,jsonCFile)
    #generate the final json file
    jsonCFile=dphelper.formatJson(jsonFile,expArchPath,domainName,migrateRootDir)
    if(jsonCFile.length()>0):
        _copyFile(jsonCFile,jsonFile)
    if(jsonCFile.length()==0):
        _copyFile(jsonFile,jsonCFile)
    #_copyFile(tempJsonFile, jsonCFile)

    # component-specific export handlers
    compDir=_createDir(migrateRootDir+"/component")
    _processComponentHandlers(migrateConfigFile, compDir)

    # domain foot string
    _writeToFile(migrateConfigFile , "</domain>\n")

    # create archive
    print "create archive zip file ..."
    print "Generating the domain archive file ..."
    cd('/')
    archiveZipFile=File(expArchPath+"/"+domainName+".zip")
    archiveZipFile.createNewFile()
    manifestBuf=StringBuffer()
    manifestBuf.append("Implementation-Title: Archive of domain applications resources\n")
    manifestBuf.append("Implementation-Vendor: Oracle, Inc. \n")
    manifestBuf.append("Implementation-Version: 12.2.1.0 \n")
    manifestBuf.append("Build-Time: ").append(Date().toString()).append("\n")
    manifestDir=File(migrateRootDir+"/META-INF")
    manifestDir.mkdirs()
    manifestFile=File(migrateRootDir+"/META-INF/MANIFEST.MF")
    manifestFile.createNewFile()
    _writeToFile(manifestFile, manifestBuf)
    _createArchive(outDomainDir, archiveZipFile)
    _rmDir(outDomainDir)

    if (String(version).contains('Server 12.2')):
        setShowLSResult(true)

    # Add Content to html file
    print "Generating the Report File ..."
    _getExportReportHTML(htmlFile, dphelper, cmo.getDomainVersion(), domainName, domainPath, dictExportedServers, dictFailedServers)

    print "Domain migration archive file: "+archiveZipFile.getCanonicalPath()
    print "Generating the domain migration archive completed."
    return

#

