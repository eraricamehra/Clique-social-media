package com.neu.esd.clique.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neu.esd.clique.model.Comment;
import com.neu.esd.clique.model.Post;

@Repository
@Transactional
public class CommentDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	public Comment save(Comment comment) {
		Session session = this.sessionFactory.openSession();
		session.save(comment);
		return comment;
	}

	public Comment findById(int id) {
		Session session = sessionFactory.openSession();
		Comment comment = (Comment) session.get(Comment.class, id);
		session.close();
		return comment;
	}

	public List<Comment> getAllCommentsOfPost(int postId) {
		Session session = sessionFactory.openSession();
		TypedQuery<Comment> query = session.getNamedQuery("getCommentsOfPost");
		query.setParameter("postId", (int) postId);
		List<Comment> comments = query.getResultList();
		session.close();
		return comments;
	}

	public Comment update(Comment comment) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.update(comment);
		transaction.commit();
		session.close();
		return comment;
	}

	public void delete(Comment comment) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(comment);
		transaction.commit();
		session.close();
	}

}
