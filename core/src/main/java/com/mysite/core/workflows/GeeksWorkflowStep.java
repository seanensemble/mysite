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
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;

@Component(
        service = WorkflowProcess.class,
        immediate = true,
        property = {
                "process.label" + " = Geeks Workflow Process", // process.label is the name used to show
                Constants.SERVICE_VENDOR + "=AEM Geeks",
                Constants.SERVICE_DESCRIPTION + " = Custom geeks workflow step."
        }
)
public class GeeksWorkflowStep implements WorkflowProcess {
    private static final Logger log = LoggerFactory.getLogger(GeeksWorkflowStep.class);

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap processArguments) {
        try {
            WorkflowData workflowData = workItem.getWorkflowData();
            if (workflowData.getPayloadType().equals("JCR_PATH")) {
                Session session = workflowSession.adaptTo(Session.class);
                String path = workflowData.getPayload().toString() + "/jcr:content";
                Node node = (Node) session.getItem(path);

                String[] processArgs = processArguments.get("PROCESS_ARGS", "string").toString().split(",");

                for (String wfArgs : processArgs) {
                    String[] args = wfArgs.split(":");
                    String prop = args[0];
                    String value = args[1];
                    if(node != null) {
                        node.setProperty(prop, value);
                    }
                }
            }
        }
        catch( Exception e) {
            System.out.println("exception");
        }
    }
}
