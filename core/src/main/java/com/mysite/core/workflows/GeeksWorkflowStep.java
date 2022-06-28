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
import java.util.Iterator;
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
public class GeeksWorkflowStep implements WorkflowProcess {
    private static final Logger LOG = LoggerFactory.getLogger(GeeksWorkflowStep.class);

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap processArguments) {
        LOG.info("\n ===================================");

        try {
            WorkflowData workflowData = workItem.getWorkflowData();
            if (workflowData.getPayloadType().equals("JCR_PATH")) {
                Session session = workflowSession.adaptTo(Session.class);
                String path = workflowData.getPayload().toString() + "/jcr:content";
                Node node = (Node) session.getItem(path);

                String[] processArgs = processArguments.get("PROCESS_ARGS", "string").toString().split(","); //all the inputs splitted by "," will be inputted here.

                MetaDataMap wfd = workItem.getWorkflow().getWorkflowData().getMetaDataMap();

                for (String wfArgs : processArgs) {
                    String[] args = wfArgs.split(":");
                    String prop = args[0];
                    String value = args[1];

                    LOG.info(" wfArgssssss args[0] {}", args[0]);
                    LOG.info(" wfArgssssss args[1] {}", args[1]);
                    if(node != null) {
                        wfd.put(prop, value); // Passing it to the next step execution, including "title" attribute and the process properties.
                        node.setProperty(prop, value); // taking the properties and add to JCR contents.
                    }
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
            LOG.info(" EXCEPTION CAUGHT ____");
            LOG.info(e.getMessage());
        }
    }
}
