package com.driver;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Getter
@Setter
public class OrderRepository {

    Map<String,Order> orderMap=new HashMap<>();
    Map<String,DeliveryPartner> partnerMap=new HashMap<>();
    Map<DeliveryPartner,List<Order>>partnerOrderPairMap=new HashMap<>();
    public void addOrder(Order order) {
        orderMap.put(order.getId(),order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner deliveryPartner=new DeliveryPartner(partnerId);
        partnerMap.put(partnerId,deliveryPartner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        Order order=getOrderById(orderId);
        DeliveryPartner deliveryPartner=getPartnerById(partnerId);
        deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders()+1);
        List<Order>orderList=partnerOrderPairMap.getOrDefault(deliveryPartner,new ArrayList<>());
        orderList.add(order);
        partnerOrderPairMap.put(deliveryPartner,orderList);
    }

    public Order getOrderById(String orderId) {
        Order order=orderMap.getOrDefault(orderId,null);
        return order;
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        DeliveryPartner deliveryPartner=partnerMap.getOrDefault(partnerId,null);
        return deliveryPartner;
    }

}
