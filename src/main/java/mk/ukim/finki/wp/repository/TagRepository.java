package mk.ukim.finki.wp.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import mk.ukim.finki.wp.model.Tag;

import org.springframework.stereotype.Repository;

@Repository
public class TagRepository {
	@PersistenceContext
	private EntityManager em;

	public Tag findById(String id) {
		return em.find(Tag.class, id);
	}

	public List<Tag> findAll() {
		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<Tag> query = builder.createQuery(Tag.class);
		Root<Tag> variableRoot = query.from(Tag.class);
		query.select(variableRoot);

		return em.createQuery(query).getResultList();
	}

	@Transactional
	public void create(Tag tag) {
		em.persist(tag);
	}

	@Transactional
	public void delete(Long id) {
		Tag tag = em.find(Tag.class, id);
		em.remove(tag);
	}
}
