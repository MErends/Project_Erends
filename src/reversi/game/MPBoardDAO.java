package reversi.game;



import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


public abstract class MPBoardDAO {
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("reversi");

	public static MPBoard create() {
		MPBoard board = new MPBoard();
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.persist(board);
		t.commit();
		em.close();
		return board;

	}
	
	public static MPBoard update(MPBoard board) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		em.merge(board);
		t.commit();
		em.close();
		return board;

	}
	
	
	public static void remove(Long boardID) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		MPBoard board = em.find(MPBoard.class, boardID);
		if(board != null){
			em.remove( board );
		}
		t.commit();
		em.close();

	}
	
	public static MPBoard getByPlayerID(long ID) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		long boardID = ((BigInteger) em.createNativeQuery("SELECT MPBoard_id FROM mpboard_player WHERE players_id ='" + ID + "'").getSingleResult()).longValue();
		t.commit();
		if(boardID == 0L) return null;
		
		t = em.getTransaction();
		t.begin();
		MPBoard board = em.find(MPBoard.class, boardID);
		t.commit();
		em.close();
		return board;
	}
	
	public static List<MPBoard> all() {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		List<MPBoard> boards = em.createQuery("from MPBoard", MPBoard.class).getResultList();
		t.commit();
		em.close();
		return boards;
	}
	
	public static void removeAll() {
		List<MPBoard> boards = MPBoardDAO.all();
		for(MPBoard board : boards) {
			remove(board.getId());
		}
	}
}
