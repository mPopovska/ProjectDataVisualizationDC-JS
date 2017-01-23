package mk.ukim.finki.wp.repository;

import mk.ukim.finki.wp.model.Project;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ProjectRepository {
	
	@PersistenceContext
	private EntityManager em;

	public Project findById(String id) {
		return em.find(Project.class, id);
	}

	public List<Project> findAll() {
		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<Project> query = builder.createQuery(Project.class);
		Root<Project> variableRoot = query.from(Project.class);
		query.select(variableRoot);

		return em.createQuery(query).getResultList();
	}

	@Transactional
	public void create(Project project) {
		em.persist(project);
	}

	@Transactional
	public void delete(Long id) {
		Project project = em.find(Project.class, id);
		em.remove(project);
	}
}
