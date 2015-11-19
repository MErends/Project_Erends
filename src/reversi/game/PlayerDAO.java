package reversi.game;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class PlayerDAO {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("reversi");

	public static Player create(String name) {
		Player player = new Player();
		player.setName(name);
		System.out.println("Storing player in DB: " + name);
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(player);
		t.commit();
		em.close();
		return player;

	}
	
	public static Player update(Player player) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.merge(player);
		t.commit();
		em.close();
		return player;

	}
	
	
	public static void remove(Long playerID) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		Player player = em.find(Player.class, playerID);
		if(player != null){
			em.remove( player );
		}
		t.commit();
		em.close();

	}
	
	public static Player getByID(long ID) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		Player player = em.find(Player.class, ID);
		t.commit();
		em.close();
		return player;
	}
	
	public static List<Player> all() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		List<Player> players = em.createQuery("from Player", Player.class).getResultList();
		t.commit();
		em.close();
		return players;
	}
	
	public static void removeAll() {
		List<Player> players = PlayerDAO.all();
		for(Player player : players) {
			remove(player.getId());
		}
	}
}
