package it.codeland.academy.Liliane.core.models;

import javax.annotation.PostConstruct;
 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.apache.sling.api.resource.Resource;
 
import org.apache.sling.api.SlingHttpServletRequest;
 
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
 
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
@Model(adaptables = SlingHttpServletRequest.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class MultiDataFetcher {
 
 
    @SlingObject
    private Resource componentResource;     //Used to get the current resource of the component
 
    private List<Map<String, String>> details = new ArrayList<>();
 
    public List<Map<String, String>> getMultiDataMap() {
 
        return details;
 
    }
 
    @PostConstruct
    protected void init() {
 
        Resource supportPoints = componentResource.getChild("details");  //now we will get the details resource which holds the data in different item node.
        if (supportPoints != null) {
            for (Resource supportPoint : supportPoints.getChildren()) {
 
                Map<String, String> detailsMap = new HashMap<>();
                detailsMap.put("title",
                        supportPoint.getValueMap().get("title", String.class));     // Saving the property into a map, which will be fetched in HTML.
                detailsMap.put("link",
                        supportPoint.getValueMap().get("link", String.class));
 
                details.add(detailsMap);
            }
        }
 
    }
 
}