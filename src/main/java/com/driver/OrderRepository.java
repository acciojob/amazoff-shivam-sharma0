package com.driver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Getter
@Setter
public class OrderRepository {

    Map<String,Order> orderMap=new HashMap<>();
    Map<String,DeliveryPartner> partnerMap=new HashMap<>();
    Map<String,String> orderPartner=new HashMap<>();
    Map<String,List<String>>partnerOrderPairMap=new HashMap<>();
    public void addOrder(Order order) {
        orderMap.put(order.getId(),order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner deliveryPartner=new DeliveryPartner(partnerId);
        partnerMap.put(partnerId,deliveryPartner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        Optional<Order> order=getOrderById(orderId);
        Optional<DeliveryPartner> deliveryPartner=getPartnerById(partnerId);
        if(order.isEmpty() || deliveryPartner.isEmpty()){
            throw new RuntimeException("OrderId or Partner is Not right");
        }
        orderPartner.put(orderId,partnerId);
        List<String>orderList=partnerOrderPairMap.getOrDefault(partnerId,new ArrayList<>());
        orderList.add(orderId);
        partnerOrderPairMap.put(partnerId,orderList);
    }

    public Optional<Order> getOrderById(String orderId) {
       if(orderMap.containsKey(orderId)){
           return Optional.of(orderMap.get(orderId));
       }
        return Optional.empty();
    }

    public Optional<DeliveryPartner> getPartnerById(String partnerId) {
        if(partnerMap.containsKey(partnerId)){
            return Optional.of(partnerMap.get(partnerId));
        }
        return Optional.empty();
    }

}
