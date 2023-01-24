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

        String saved_path = "";

        try {
            ResourceResolver resolver = workflowSession.adaptTo(ResourceResolver.class);


            String payload_path = workItem.getWorkflowData().getPayload().toString();
            saved_path = this.getStringBeforeJcrContent(payload_path);
//            resolver.move(saved_path, "/content/dam/mysite/mountain/images.png");

//            Resource sourceResource = resolver.getResource("/content/dam/mysite/images.png");
            Resource sourceResource = resolver.getResource(saved_path);
            if (sourceResource != null) {
                Node sourceNode = sourceResource.adaptTo(Node.class);
                if (sourceNode != null) {
                    Session session = sourceNode.getSession();
                    session.move(sourceNode.getPath(), "/content/dam/mysite/mountain/images.png");
                    session.save();
                }
            }
        }
        catch( Exception e) {
            LOG.info(" EXCEPTION CAUGHT ____ from process 8");
            LOG.info(e.getMessage());
        }
    }


    private String getStringBeforeJcrContent(String input) {
        int index = input.indexOf("/jcr:content");
        if (index == -1) {
            return input;
        } else {
            return input.substring(0, index);
        }
    }
}
