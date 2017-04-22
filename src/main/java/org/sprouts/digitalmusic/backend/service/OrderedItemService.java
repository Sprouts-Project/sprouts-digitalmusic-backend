package org.sprouts.digitalmusic.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sprouts.digitalmusic.backend.da.OrderedItemDAO;
import org.sprouts.digitalmusic.model.OrderedItem;

import java.util.List;

@Service
public class OrderedItemService {

    // Managed Data Access Objects --------------------------------------------

    @Autowired
    private OrderedItemDAO orderedItemDAO;

    // Managed Services -------------------------------------------------------


    // Simple CRUD Methods ----------------------------------------------------

    public Integer saveAll(List<OrderedItem> orderedItems){
        orderedItemDAO.save(orderedItems);
        return 1;
    }

}
