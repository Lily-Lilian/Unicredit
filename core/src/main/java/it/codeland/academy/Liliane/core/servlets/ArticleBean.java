package it.codeland.academy.Liliane.core.servlets; 
 
 
public class ArticleBean  {
 
    private String title;
    private String[] tags;
    private String image;
    private String articleAbstract;
    private String date;
    private String link;

    public ArticleBean(){}

    public ArticleBean(String title, String[] tags, String img, String abs, String dte, String link){
        this.title = title;
        this.tags = tags;
        this.image = img;
        this.articleAbstract = abs;
        this.date = dte;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getArticleAbstract() {
        return articleAbstract;
    }

    public String getDate() {
        return date;
    }

    public String getLink() {
        return link;
    }

    public String[] getTags() {
        return tags;
    }

}
