package com.ibms.repository;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.ibms.model.Policy;
import com.ibms.util.HibernateUtil;

public class PolicyRepository {
    private static final Logger LOGGER = Logger.getLogger(PolicyRepository.class.getName());

    public List<Policy> getAllPolicies() {
        List<Policy> policies = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Policy> query = session.createQuery("FROM Policy", Policy.class);
            policies = query.getResultList();
            transaction.commit();
            LOGGER.info("Retrieved all policies: " + policies.size());
        } catch (Exception e) {
            if (transaction != null) {
				transaction.rollback();
			}
            LOGGER.severe("Error retrieving policies: " + e.getMessage());
            e.printStackTrace();
        }
        return policies;
    }

    public void savePolicy(Policy policy) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(policy);
            transaction.commit();
            LOGGER.info("Policy saved successfully: " + policy.getPolicyNumber());
        } catch (Exception e) {
            if (transaction != null) {
				transaction.rollback();
			}
            LOGGER.severe("Error saving policy: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updatePolicy(Policy updatedPolicy) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Policy> query = session.createQuery(
                "FROM Policy WHERE policyNumber = :policyNumber", Policy.class);
            query.setParameter("policyNumber", updatedPolicy.getPolicyNumber());
            Policy existingPolicy = query.uniqueResult();

            if (existingPolicy != null) {
                session.update(updatedPolicy);
                transaction.commit();
                LOGGER.info("Policy updated successfully: " + updatedPolicy.getPolicyNumber());
            } else {
                transaction.rollback();
                LOGGER.warning("Policy not found: " + updatedPolicy.getPolicyNumber());
            }
        } catch (Exception e) {
            if (transaction != null) {
				transaction.rollback();
			}
            LOGGER.severe("Error updating policy: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void deletePolicy(String policyNumber) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Policy> query = session.createQuery(
                "FROM Policy WHERE policyNumber = :policyNumber", Policy.class);
            query.setParameter("policyNumber", policyNumber);
            Policy policy = query.uniqueResult();

            if (policy != null) {
                session.delete(policy);
                transaction.commit();
                LOGGER.info("Policy deleted successfully: " + policyNumber);
            } else {
                transaction.rollback();
                LOGGER.warning("Policy not found for deletion: " + policyNumber);
            }
        } catch (Exception e) {
            if (transaction != null) {
				transaction.rollback();
			}
            LOGGER.severe("Error deleting policy: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Policy getPolicyByNumber(String policyNumber) {
        Policy policy = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Policy> query = session.createQuery(
                "FROM Policy WHERE policyNumber = :policyNumber", Policy.class);
            query.setParameter("policyNumber", policyNumber);
            policy = query.uniqueResult();
            transaction.commit();

            if (policy != null) {
                LOGGER.info("Retrieved policy: " + policyNumber);
            } else {
                LOGGER.warning("Policy not found: " + policyNumber);
            }
        } catch (Exception e) {
            if (transaction != null) {
				transaction.rollback();
			}
            LOGGER.severe("Error retrieving policy: " + e.getMessage());
            e.printStackTrace();
        }
        return policy;
    }

    public List<Policy> searchPolicies(String searchTerm) {
        List<Policy> policies = new ArrayList<>();
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Query<Policy> query = session.createQuery(
                "FROM Policy WHERE policyNumber LIKE :searchTerm " +
                "OR type LIKE :searchTerm", Policy.class);
            query.setParameter("searchTerm", "%" + searchTerm + "%");
            policies = query.getResultList();
            transaction.commit();
            LOGGER.info("Found " + policies.size() + " policies matching: " + searchTerm);
        } catch (Exception e) {
            if (transaction != null) {
				transaction.rollback();
			}
            LOGGER.severe("Error searching policies: " + e.getMessage());
            e.printStackTrace();
        }
        return policies;
    }

    public Policy getPolicyById(Long id) {
        Policy policy = null;
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            policy = session.get(Policy.class, id);
            transaction.commit();
            if (policy != null) {
                LOGGER.info("Retrieved policy with ID: " + id);
            } else {
                LOGGER.warning("Policy not found with ID: " + id);
            }
        } catch (Exception e) {
            if (transaction != null) {
				transaction.rollback();
			}
            LOGGER.severe("Error retrieving policy by ID: " + e.getMessage());
            e.printStackTrace();
        }
        return policy;
    }

    public long countPolicies() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return (long) session.createQuery("SELECT COUNT(p) FROM Policy p").uniqueResult();
		}
	}

	public long countClaimsByStatus(ClaimStatus status) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			Query<Long> query = session.createQuery("SELECT COUNT(c) FROM Claim c WHERE c.status = :status",
					Long.class);
			query.setParameter("status", status);
			return query.uniqueResult();
		}
	}

	public Map<Customer, Long> countCustomersByBroker() {
		Map<Customer, Long> customerCountMap = new HashMap<>();
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			List<Object[]> results = session
					.createQuery("SELECT p.customer, COUNT(p) FROM Policy p GROUP BY p.customer").getResultList();
			for (Object[] row : results) {
				customerCountMap.put((Customer) row[0], (Long) row[1]);
			}
		}
		return customerCountMap;
	}
}
