package org.example;

import org.example.entity.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            // TODO: 여기 비즈니스 로직이 들어가야 함

            Member member = new Member();
            member.setName("Chamy");
            member.setCity("Seoul");
            member.setStreet("Dobong-ro");
            member.setZipcode("00011");
            em.persist(member);

            Item item = new Item();
            item.setName("Air pot 4");
            item.setPrice(100);
            item.setStockQuantity(5);
            em.persist(item);

            OrderItem orderItem = new OrderItem();
            orderItem.setItem(item);
            orderItem.setOrderPrice(100);
            em.persist(orderItem);

            Order order = new Order();
            order.setOrderDate(new Date());
            order.setOrderStatus(OrderStatus.ORDER);
            order.setMember(member);
            order.addOrderItem(orderItem);
            em.persist(order);

            Order o = em.find(Order.class, order.getId());
            Member m = o.getMember();

            System.out.println("Order ID: " + o.getId());
            System.out.println("Member Name: " + m.getName());

            OrderItem oi = o.getOrderItems().get(0);
            System.out.println("Order Item ID: " + oi.getId());
            Item i = oi.getItem();
            System.out.println("Item Name: " + i.getName());

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}