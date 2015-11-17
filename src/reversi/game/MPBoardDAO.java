package reversi.game;



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
	
	
	public static void remove(int boardID) {
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
	
	public static MPBoard getByID(String session) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		t.begin();
		String boardID = (String) em.createQuery("SELECT MPBoard_id FROM mpboard_player WHERE players_sessionID =\"" + session + "\";").getSingleResult();
		t.commit();
		em.close();
		if(boardID == null) return null;
		
		t = em.getTransaction();
		t.begin();
		MPBoard board = em.find(MPBoard.class, boardID);
		t.commit();
		em.close();
		return board;
	}
}
