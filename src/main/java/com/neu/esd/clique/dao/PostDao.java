package com.neu.esd.clique.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.neu.esd.clique.model.Post;
import com.neu.esd.clique.model.Request;

@Repository
@Transactional
public class PostDao {
	
	@Autowired
	private SessionFactory sessionFactory;
 
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}
	
	public Post save(Post post){
		 Session session = this.sessionFactory.openSession();
		 session.save(post);
		 return post;
	}
	

	public Post findById(int id) {
		Session session = sessionFactory.openSession();
		Post post = (Post) session.get(Post.class, id);
		session.close();
		return post;
	}

	public Post update(Post post) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.update(post);
		transaction.commit();
		session.close();
		return post;
	}
	
	public List<Post> getAllPostForFeed(Integer userId) {
		Session session = sessionFactory.openSession();
		TypedQuery<Post> query = session.getNamedQuery("getAllFeed");
		query.setParameter("userId", (int) userId);
		List<Post> feed = query.getResultList();
		session.close();
		return feed;
	}

//	public List<Post> updateLike(long postId) {
//		Session session = sessionFactory.openSession();
//		TypedQuery<Post> query = session.getNamedQuery("getAllFeed");
//		query.setParameter("postId",  postId);
//		List<Post> feed = query.getResultList();
//		session.close();
//		return feed;
//	}
	public void delete(Post post) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.delete(post);
		transaction.commit();
		session.close();
	}
}
