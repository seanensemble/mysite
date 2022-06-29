package com.mysite.core.workflows;

import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowData;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Node;
import javax.jcr.Session;
import javax.jcr.Value;
import java.util.*;


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

    @ValueMapValue
    private String about;

    @ValueMapValue
    private List<String> books;

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap processArguments) {
        LOG.info("\n ====================================Custom Workflow SeanTagStep========================================");
        try {

            LOG.info("BOOKBBBBBBBBBBBBBBBBBBBB");
//            LOG.info(this.getAbout());
//
//            List<String> booklist = this.getBooks();
//            for (int i = 0; i < booklist.size(); i++) {
//                LOG.info(booklist.get(i));
//            }

            LOG.info("BOOKBBBBBBBBBBBBBBBBBBBB");


            WorkflowData workflowData = workItem.getWorkflowData();
            LOG.info("");
            LOG.info("");
            LOG.info("workflowData.getPayload().toString()workflowData.getPayload().toString()");


            LOG.info(workflowData.getPayload().toString());
            if (workflowData.getPayloadType().equals("JCR_PATH")) {
                Session session = workflowSession.adaptTo(Session.class);
                String path;

//                Node payload = (Node) workflowData.getPayload();
//
//                LOG.info("xxxxxxxxxxxxxx from getPayload directly");
//                LOG.info(payload.getProperty("jcr:primaryType").getString());


                LOG.info("yyyyyyyyyyy from getPayload with session");
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


                LOG.info(payload2.getProperty("jcr:primaryType").getString());


                Node node = (Node) session.getItem(path);

                LOG.info("node.getName()   node.getName()   node.getName()");
                LOG.info(node.getName()); // jcr:content

//                ArrayList<String> tagList = new ArrayList<>();


//                node.setProperty("cq:tags", tagList);

                LOG.info("0000 tttttttttttttttttttttttttttttttt");


//                List emptylist = new ArrayList();
//                emptylist.add("empty with one item");
//                List tags = processArguments.get("TAGS",emptylist);

//                LOG.info("tsize: {}", tsize);
//                for (int i = 0; i < tsize; i++) {
//                    LOG.info("taggeeeeeeeeed");
//                }

                String[] cars = {"Volvo", "BMW", "Ford", "Mazda", "Mustang"};

                String[] tags = processArguments.get("TAGS",cars);
                LOG.info("array length: {}", tags.length);

//                for (String temp: tags) {
//                    LOG.info(temp);
//                }

                LOG.info("11111 tttttttttttttttttttttttttttttttt");


                LOG.info(processArguments.get("TAGS","failed...conversion").toString());

                for (int i = 0; i < tags.length; i++) {
                    LOG.info("00000000000000000");

                    LOG.info(tags[i]);
                }

                node.setProperty("cq:tags", tags);

//                String[] tagList = {tags};

//                LOG.info("\n TAGXXXXXXXXXXXXXXXXXXX : {} ", tags);


                String brand = processArguments.get("BRAND","");
                boolean multinational =processArguments.get("MULTINATIONAL",false);
                LOG.info("\n BRAND : {} , MULTINATIONAL : {} ",brand,multinational);
                String[] countries = processArguments.get("COUNTRIES",new String[]{});
                for (String country : countries) {
                    LOG.info("\n Countries {} ",country);
                }
            }
            LOG.info("");
            LOG.info("");
        }catch (Exception e){
            LOG.info("SeanTagStep_______------===========error");
            LOG.info("\n ERROR {} ",e.getMessage());
        }
    }


//    public String getAbout() {
//        return this.about;
//    }
//
//
//    public List<String> getBooks() {
//        return this.books;
//    }
}



