package com.mysite.core.workflows;

//import com.adobe.granite.rest.Constants;
//import com.day.cq.workflow.WorkflowSession;
import com.adobe.granite.workflow.WorkflowSession;
//import com.day.cq.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.exec.WorkItem;
//import com.day.cq.workflow.exec.WorkflowData;
//import com.day.cq.workflow.exec.WorkflowProcess;
//import com.day.cq.workflow.exec.WorkflowProcess;
//import com.day.cq.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.dam.api.Asset;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Component(
        service = WorkflowProcess.class,
        immediate = true,
        property = {
                "process.label" + " = Geeks Workflow Process", // process.label is the name used to show
                Constants.SERVICE_VENDOR + "=AEM Geeks",
                Constants.SERVICE_DESCRIPTION + " = Custom geeks workflow step."
        }
)
public class GeeksWorkflowProcess implements WorkflowProcess {
    private static final Logger LOG = LoggerFactory.getLogger(GeeksWorkflowProcess.class);

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap processArguments) {
        LOG.info("\n ===================================");

        try {
            ResourceResolver resolver = workflowSession.adaptTo(ResourceResolver.class);


            String assetPath = "/content/dam/mysite/snowy-mountain-glacier.jpg";
            Resource resource = resolver.getResource(assetPath);
            Asset asset = resource.adaptTo(Asset.class);


            String assetPath1 = "/content/dam/mysite/mountain-range.jpg";
            Resource resource1 = resolver.getResource(assetPath1);
            Asset asset1 = resource1.adaptTo(Asset.class);


            String assetPath2 = "/content/dam/we-retail-screens/we-retail-instore-logo.png";
            Resource resource2 = resolver.getResource(assetPath2);
            Asset asset2 = resource2.adaptTo(Asset.class);


            Resource parentResource = resolver.getResource("/content/dam/mysite");

            Resource currentResource = parentResource.getChild("3683.jpeg");


            if(currentResource == null) {
                currentResource = resolver.create(parentResource, "3683.jpeg", new HashMap<>());
            }

            Session session = workflowSession.adaptTo(Session.class);

            String currentPath = "/content/dam/mysite/3683.jpeg";

            String newPath = "/content/dam/mysite/3683_copied.jpeg";

            Map<String, Object> properties = new HashMap<>();
            properties.put("jcr:primaryType", "nt:folder");

            Resource movedFolder = parentResource.getChild("mountain");

            if (movedFolder == null) {
                movedFolder = resolver.create(parentResource, "mountain",  properties);
            }

            Resource movedInnerFolder = movedFolder.getChild("size_over_100k");

            if (movedInnerFolder == null) {
                movedInnerFolder = resolver.create(movedFolder, "size_over_100k",  properties);
            }

            // repeat the same process above for "movedFolder" as new parentResource

//            resolver.move(currentResource.getPath(), newResource.getPath());

            resolver.move(currentPath, movedFolder.getPath());

            WorkflowData workflowData = workItem.getWorkflowData();

            retrieveWorkflowPayload(workflowData);

            if (workflowData.getPayloadType().equals("JCR_PATH")) {
//                Session session = workflowSession.adaptTo(Session.class);
                String path = workflowData.getPayload().toString() + "/jcr:content";
//                Node node = (Node) session.getItem(path);

                String[] processArgs = processArguments.get("PROCESS_ARGS", "string").toString().split(","); //all the inputs splitted by "," will be inputted here.

                MetaDataMap wfd = workItem.getWorkflow().getWorkflowData().getMetaDataMap();

                for (String wfArgs : processArgs) {
                    String[] args = wfArgs.split(":");
                    String prop = args[0];
                    String value = args[1];

                    LOG.info(" wfArgssssss args[0] {}", args[0]);
                    LOG.info(" wfArgssssss args[1] {}", args[1]);
//                    if(node != null) {
                        wfd.put(prop, value); // Passing it to the next step execution, including "title" attribute and the process properties.
//                        node.setProperty(prop, value); // taking the properties and add to JCR contents.
//                    }
                }
                Set<String> keyset = wfd.keySet();
                Iterator<String> i = keyset.iterator();
                while (i.hasNext()) {
                    String key = i.next();
                    LOG.info("\n ITEM key - {}, value - {}", key, wfd.get(key));
                }
            }
        }
        catch( Exception e) {
            LOG.info(" EXCEPTION CAUGHT ____ from process 1");
            LOG.info(e.getMessage());
        }
    }

    private void retrieveWorkflowPayload(WorkflowData workflowData)  {
        LOG.info("workflowData.getPayload().toString()");
        LOG.info(workflowData.getPayload().toString());
    }
}
