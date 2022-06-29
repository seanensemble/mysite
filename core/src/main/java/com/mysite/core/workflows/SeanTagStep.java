package com.mysite.core.workflows;

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.jcr.Node;
import javax.jcr.Session;


@Component(
        service = WorkflowProcess.class,
        property = {
                "process.label" + " = Geeks Workflow Step",
                Constants.SERVICE_VENDOR + "=AEM Geeks",
                Constants.SERVICE_DESCRIPTION + " = Custom geeks workflow step."
        }
)
public class SeanTagStep implements WorkflowProcess {
    private static final Logger LOG = LoggerFactory.getLogger(SeanTagStep.class);

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap processArguments) {

        try {

            WorkflowData workflowData = workItem.getWorkflowData();

            if (workflowData.getPayloadType().equals("JCR_PATH")) {
                Session session = workflowSession.adaptTo(Session.class);
                String path;

                Node payload2 = (Node) session.getItem(workflowData.getPayload().toString());

                String payloadType = payload2.getProperty("jcr:primaryType").getString();

                switch(payloadType) {
                    case "cq:Page":
                        path = workflowData.getPayload().toString() + "/jcr:content";
                        break;
                    case "dam:Asset":
                        path = workflowData.getPayload().toString() + "/jcr:content/metadata";
                        break;
                    default:
                        path = workflowData.getPayload().toString(); ///content/forms/af
                }


                Node node = (Node) session.getItem(path);

                String[] cars = {};
                String[] tags = processArguments.get("TAGS",cars);

                node.setProperty("cq:tags", tags);
            }

        }catch (Exception e){
            LOG.info("SeanTagStep_______------===========error");
            LOG.info("\n ERROR {} ",e.getMessage());
        }
    }

}



