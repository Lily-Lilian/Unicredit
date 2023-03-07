package it.codeland.academy.Liliane.core.models;

import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;

import javax.annotation.PostConstruct;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;

@Model(adaptables = Resource.class)
public class Header {

    @SlingObject
    Resource componentResource;
    Page currentPage;

    @PostConstruct
    public void init () {
        PageManager pageManager = componentResource.getResourceResolver().adaptTo(PageManager.class);
        currentPage = pageManager.getContainingPage(componentResource);
    }

    public Page getFirstLevelItems() {
        return currentPage.getAbsoluteParent(3);
    }

    public Page getSecondLevelItems() {
        return currentPage.getAbsoluteParent(2);
    }
}
