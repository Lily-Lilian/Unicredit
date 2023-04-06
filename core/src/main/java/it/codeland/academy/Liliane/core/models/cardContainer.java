package it.codeland.academy.Liliane.core.models;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.sling.api.resource.Resource;

import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.SlingObject;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Model(adaptables = Resource.class)
public class cardContainer {
    private final Logger LOG = LoggerFactory.getLogger(cardContainer.class);

    @SlingObject
    private Resource currentResource;

    // @ValueMapValue
    // @Named("cardsNumber")
    private int cardsNumber;
    
    public int getcardNumber(){
      return cardsNumber;
        }

    // public int[] getCardsNumber(){
    //     LOG.debug("VVVVVVVVVVVVVVVVVVV {}", cardsNumber);

    //     return new int[cardsNumber];

    // }

    @PostConstruct
    public void init(){
        //   cardsNumber = currentResource.getValueMap().get("cardsNumber", int.class);
         cardsNumber = 2;
          LOG.debug("VVVVVVVVVVVVVVVVVVV {}", cardsNumber);
    }

}
