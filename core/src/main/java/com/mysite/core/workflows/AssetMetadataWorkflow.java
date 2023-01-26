package com.mysite.core.workflows;

import com.adobe.granite.workflow.WorkflowSession;
import com.adobe.granite.workflow.exec.WorkItem;
import com.adobe.granite.workflow.exec.WorkflowProcess;
import com.adobe.granite.workflow.metadata.MetaDataMap;
import com.day.cq.dam.api.Asset;
import com.day.cq.dam.api.DamConstants;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
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
                "process.label" + " = Asset Metadata Workflow Process",
                Constants.SERVICE_VENDOR + "=Mysite example",
                Constants.SERVICE_DESCRIPTION + " = Custom asset metadata workflow."
        }
)
public class AssetMetadataWorkflow implements WorkflowProcess {
    private static final Logger LOG = LoggerFactory.getLogger(AssetMetadataWorkflow.class);

    @Override
    public void execute(WorkItem workItem, WorkflowSession workflowSession, MetaDataMap processArguments) {

        String saved_path = "";

        String addedFolderParent = "mountain";
        String addedFolderChild = "size_over_100k";
        String rootFolder = "/content/dam/mysite";
        String addedFolderParentPath = rootFolder + "/" + addedFolderParent;
        String addedFolderChildPath = rootFolder + "/" + addedFolderParent + "/" + addedFolderChild;


        this.createFolder(workflowSession, "/content/dam/mysite", addedFolderParent);
        this.createFolder(workflowSession, addedFolderParentPath, addedFolderChild);


        try {
            String payload_path = workItem.getWorkflowData().getPayload().toString();
            saved_path = this.getStringBeforeJcrContent(payload_path);

            ResourceResolver resolver = workflowSession.adaptTo(ResourceResolver.class);
            Resource resource = resolver.getResource(saved_path);
            Asset asset = resource.adaptTo(Asset.class);

            String title = ((Object[]) asset.getMetadata(DamConstants.DC_TITLE))[0].toString();
            Long size = ((Long) asset.getMetadata(DamConstants.DAM_SIZE));

            String moved_path = "";

            String file_name = asset.getName();

            if (title.contains("mountain") && size > 100000) {
                moved_path = addedFolderChildPath;
            } else if (title.contains("mountain") && size < 100000) {
                moved_path = addedFolderParentPath;
            }


            Resource sourceResource = resolver.getResource(saved_path);
            if (sourceResource != null) {
                Node sourceNode = sourceResource.adaptTo(Node.class);
                if (sourceNode != null && moved_path != "") {
                    Session session = sourceNode.getSession();
                    session.move(sourceNode.getPath(), moved_path + "/" + file_name);
                    session.save();
                }
            }
        }
        catch( Exception e) {
            LOG.info(e.getMessage());
        }
    }

    private void createFolder(WorkflowSession workflowSession, String parent_path, String folder_name) {

        try {
            Session session = workflowSession.adaptTo(Session.class);
            Node parentNode = session.getNode(parent_path); // "/content/dam/mysite"

            if(parentNode != null) {
                if (!parentNode.hasNode(folder_name)) {
                    parentNode.addNode(folder_name, "nt:folder");
                    session.save();
                }
            }
        }
        catch( Exception e) {
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
