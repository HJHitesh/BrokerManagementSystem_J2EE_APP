package com.ibms.repository;

import java.sql.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.insurance.model.Claim;

public class ClaimRepository {
	private final SessionFactory sessionFactory;

	public ClaimRepository() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}

	public List<Claim> getAllClaims() {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("from Claim", Claim.class).list();
		}
	}

	public List<Claim> getClaimsByPolicyId(String policyId) {
		try (Session session = sessionFactory.openSession()) {
			return session.createQuery("from Claim where policyId = :policyId", Claim.class)
					.setParameter("policyId", policyId).list();
		}
	}

	public List<Claim> filterClaimsByDate(List<Claim> claims, Date startDate, Date endDate) {
		return claims.stream()
				.filter(claim -> !claim.getClaimDate().before(startDate) && !claim.getClaimDate().after(endDate))
				.toList();
	}

	public List<Claim> filterClaimsByStatus(List<Claim> claims, String status) {
		return claims.stream().filter(claim -> claim.getStatus().equalsIgnoreCase(status)).toList();
	}
}
