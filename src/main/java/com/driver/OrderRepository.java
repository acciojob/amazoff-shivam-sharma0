package com.driver;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
    Map<String, Order> orderMap=new HashMap<>();
    Map<String, DeliveryPartner> partnerMap=new HashMap<>();
    Map<String,String> assignedOrder=new HashMap<>();
    Map<DeliveryPartner,List<Order>> partnerOrderMap=new HashMap<>();
    public void addOrder(Order order) {
        orderMap.put(order.getId(), order);
    }

    public void addPartner(String partnerId) {
        DeliveryPartner deliveryPartner=new DeliveryPartner(partnerId);
        partnerMap.put(partnerId,deliveryPartner);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        assignedOrder.put(orderId,partnerId);
        Order order=getOrderById(orderId);
        DeliveryPartner deliveryPartner=getPartnerById(partnerId);

        if(partnerOrderMap.containsKey(deliveryPartner)){
            List<Order> orderList=partnerOrderMap.get(deliveryPartner);
            orderList.add(order);
            partnerOrderMap.put(deliveryPartner,orderList);
            return;
        }
        List<Order> orderList=new ArrayList<>();
        orderList.add(order);
        partnerOrderMap.put(deliveryPartner,orderList);
    }

    public DeliveryPartner getPartnerById(String partnerId) {
        return partnerMap.get(partnerId);
    }

    public Order getOrderById(String orderId) {
        return orderMap.get(orderId);
    }

    public Integer getOrderCountByPartnerId(String partnerId) {
        return partnerOrderMap.get(getPartnerById(partnerId)).size();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
      List<Order> orderList=partnerOrderMap.get(getPartnerById(partnerId));
      List<String > ids=new ArrayList<>();
      for(Order order:orderList){
          ids.add(order.getId());
      }
        return ids;
    }

    public List<String> getAllOrders() {
        return new ArrayList<>(orderMap.keySet());
    }

    public Integer getCountOfUnassignedOrders() {
        return orderMap.size()-assignedOrder.size();
    }
}
