//package com.ibms.repository;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.logging.Logger;
//
//import org.hibernate.Session;
//import org.hibernate.query.Query;
//
//import com.ibms.model.Customer;
//import com.ibms.util.HibernateUtil;
//
//public class CustomerRepository {
//    private static final Logger LOGGER = Logger.getLogger(CustomerRepository.class.getName());
//
//    public List<Customer> getAllCustomers() {
//        List<Customer> customers = new ArrayList<>();
//        try {
//            Session session = HibernateUtil.getCurrentSession();
//            HibernateUtil.beginTransaction();
//
//            Query<Customer> query = session.createQuery("FROM Customer", Customer.class);
//            customers = query.getResultList();
//
//            HibernateUtil.commitTransaction();
//            LOGGER.info("Retrieved all customers: " + customers.size());
//        } catch (Exception e) {
//            HibernateUtil.rollbackTransaction();
//            LOGGER.severe("Error retrieving customers: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            HibernateUtil.closeCurrentSession();
//        }
//        return customers;
//    }
//
//    public void saveCustomer(Customer customer) {
//        try {
//            Session session = HibernateUtil.getCurrentSession();
//            HibernateUtil.beginTransaction();
//
//            session.save(customer);
//
//            HibernateUtil.commitTransaction();
//            LOGGER.info("Customer saved successfully: " + customer.getId());
//        } catch (Exception e) {
//            HibernateUtil.rollbackTransaction();
//            LOGGER.severe("Error saving customer: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            HibernateUtil.closeCurrentSession();
//        }
//    }
//
//    public void updateCustomer(Customer updatedCustomer) {
//        try {
//            Session session = HibernateUtil.getCurrentSession();
//            HibernateUtil.beginTransaction();
//
//            Customer existingCustomer = session.get(Customer.class, updatedCustomer.getId());
//
//            if (existingCustomer != null) {
//                session.update(updatedCustomer);
//                HibernateUtil.commitTransaction();
//                LOGGER.info("Customer updated successfully: " + updatedCustomer.getId());
//            } else {
//                HibernateUtil.rollbackTransaction();
//                LOGGER.warning("Customer not found: " + updatedCustomer.getId());
//            }
//        } catch (Exception e) {
//            HibernateUtil.rollbackTransaction();
//            LOGGER.severe("Error updating customer: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            HibernateUtil.closeCurrentSession();
//        }
//    }
//
//    public boolean deleteCustomerById(Long id) {
//        try {
//            Session session = HibernateUtil.getCurrentSession();
//            HibernateUtil.beginTransaction();
//
//            Customer customer = session.get(Customer.class, id);
//            if (customer != null) {
//                session.delete(customer);
//                HibernateUtil.commitTransaction();
//                LOGGER.info("Customer deleted successfully: " + id);
//                return true;
//            } else {
//                HibernateUtil.rollbackTransaction();
//                LOGGER.warning("Customer not found for deletion: " + id);
//                return false;
//            }
//        } catch (Exception e) {
//            HibernateUtil.rollbackTransaction();
//            LOGGER.severe("Error deleting customer: " + e.getMessage());
//            e.printStackTrace();
//            return false;
//        } finally {
//            HibernateUtil.closeCurrentSession();
//        }
//    }
//
//    public Customer getCustomerById(Long id) {
//        Customer customer = null;
//        try {
//            Session session = HibernateUtil.getCurrentSession();
//            HibernateUtil.beginTransaction();
//
//            customer = session.get(Customer.class, id);
//
//            HibernateUtil.commitTransaction();
//            if (customer != null) {
//                LOGGER.info("Retrieved customer: " + id);
//            } else {
//                LOGGER.warning("Customer not found: " + id);
//            }
//        } catch (Exception e) {
//            HibernateUtil.rollbackTransaction();
//            LOGGER.severe("Error retrieving customer: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            HibernateUtil.closeCurrentSession();
//        }
//        return customer;
//    }
//
//    public List<Customer> searchCustomers(String searchTerm) {
//        List<Customer> customers = new ArrayList<>();
//        try {
//            Session session = HibernateUtil.getCurrentSession();
//            HibernateUtil.beginTransaction();
//
//            Query<Customer> query = session.createQuery(
//                "FROM Customer WHERE firstName LIKE :searchTerm " +
//                "OR lastName LIKE :searchTerm " +
//                "OR email LIKE :searchTerm", Customer.class);
//            query.setParameter("searchTerm", "%" + searchTerm + "%");
//            customers = query.getResultList();
//
//            HibernateUtil.commitTransaction();
//            LOGGER.info("Found " + customers.size() + " customers matching: " + searchTerm);
//        } catch (Exception e) {
//            HibernateUtil.rollbackTransaction();
//            LOGGER.severe("Error searching customers: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            HibernateUtil.closeCurrentSession();
//        }
//        return customers;
//    }
//
//    public Customer getCustomerByEmail(String email) {
//        Customer customer = null;
//        try {
//            Session session = HibernateUtil.getCurrentSession();
//            HibernateUtil.beginTransaction();
//
//            Query<Customer> query = session.createQuery(
//                "FROM Customer WHERE email = :email", Customer.class);
//            query.setParameter("email", email);
//            customer = query.uniqueResult();
//
//            HibernateUtil.commitTransaction();
//            if (customer != null) {
//                LOGGER.info("Retrieved customer by email: " + email);
//            } else {
//                LOGGER.warning("Customer not found with email: " + email);
//            }
//        } catch (Exception e) {
//            HibernateUtil.rollbackTransaction();
//            LOGGER.severe("Error retrieving customer by email: " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            HibernateUtil.closeCurrentSession();
//        }
//        return customer;
//    }
//}