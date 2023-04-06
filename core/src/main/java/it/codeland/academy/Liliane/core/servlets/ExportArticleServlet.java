package it.codeland.academy.Liliane.core.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.propertytypes.ServiceDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = {Servlet.class})
@SlingServletResourceTypes(
    resourceTypes="cq:Page", 
    methods = "GET",
    extensions = "json",
    selectors = "export")
@ServiceDescription("Article Servlet")
public class ExportArticleServlet extends SlingSafeMethodsServlet{
    private static final long serialVersionUID = 1L;
    private static final Logger log = LoggerFactory.getLogger(ExportArticleServlet.class);

    @Override
    protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse res) throws ServletException, IOException{
        SimpleDateFormat dateFormater = new SimpleDateFormat("E dd MMM YYYY");
        try{
            Resource resource = req.getResource();
            Resource page = resource.getChild("jcr:content");
            ValueMap content = page.adaptTo(ValueMap.class);
            String text = content.get("text", String.class);
            String title = content.get("jcr:title", String.class);
            String img = content.get("image", String.class);
            String[] tags = content.get("cq:tags", String[].class);
            String url = page.getParent().getPath()+".html";
            Date displayedDate = content.get("date", Date.class);
            
            ArticleBean article = new ArticleBean(title, tags, img, text, dateFormater.format(displayedDate), url);

            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(article);
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json");
            res.getWriter().write(jsonString);
        }catch(Exception e){
            log.error(e.getMessage(), e);
            res.setCharacterEncoding("UTF-8");
            res.setContentType("application/json");
            res.getWriter().write("Resource not found!");
        }
    }

}