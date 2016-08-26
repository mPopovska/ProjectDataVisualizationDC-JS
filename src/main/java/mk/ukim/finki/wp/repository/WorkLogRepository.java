package mk.ukim.finki.wp.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import mk.ukim.finki.wp.model.WorkLog;

import org.springframework.stereotype.Repository;

@Repository
public class WorkLogRepository {
	
	@PersistenceContext
	private EntityManager em;

	public WorkLog findById(Long id) {
		return em.find(WorkLog.class, id);
	}

	public List<WorkLog> findAll() {
		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<WorkLog> query = builder.createQuery(WorkLog.class);
		Root<WorkLog> variableRoot = query.from(WorkLog.class);
		query.select(variableRoot);

		return em.createQuery(query).getResultList();
	}

	@Transactional
	public void create(WorkLog workLog) {
		em.persist(workLog);
	}

	@Transactional
	public void delete(Long id) {
		WorkLog workLog = em.find(WorkLog.class, id);
		em.remove(workLog);
	}
	
	@Transactional
	public void deleteAll() {
		em.createQuery("DELETE FROM WorkLog").executeUpdate();
		em.createQuery("DELETE FROM Project").executeUpdate();
		em.createQuery("DELETE FROM Tag").executeUpdate();
	}
}
