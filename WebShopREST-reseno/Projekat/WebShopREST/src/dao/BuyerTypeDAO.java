package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import beans.Enums.BuyerTypeEnum;
import java.time.LocalDate;
import beans.Enums.DateHelper;

import beans.BuyerType;

/***
 * <p>Klasa namenjena da uèita korisnike iz fajla i pruža operacije nad njima (poput pretrage).
 * Korisnici se nalaze u fajlu WebContent/users.txt u obliku: <br>
 * firstName;lastName;email;username;password</p>
 * <p><b>NAPOMENA:</b> Lozinke se u praksi <b>nikada</b> ne snimaju u èistu tekstualnom obliku.</p>
 * @author Lazar
 *
 */
public class BuyerTypeDAO {
	
	private static BuyerTypeDAO instance = null;
	
	private Map<Integer, BuyerType> buyers = new HashMap<>();
	
	
	private BuyerTypeDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
	 */
	private BuyerTypeDAO(String contextPath) {
		loadBuyers(contextPath);
	}
	
	
	public static BuyerTypeDAO getInstance(String contextPath) {
		if(instance == null) {
			instance = new BuyerTypeDAO(contextPath);
		}
		
		return instance;
	}
	
	
	public BuyerType find(int id) {
		return buyers.get(id);
	}
	
	public Collection<BuyerType> findAll() {
		return buyers.values();
	}
	
	public BuyerType save(BuyerType buyer) {
		Integer maxId = -1;
		for (int id : buyers.keySet()) {
			int idNum = id;
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		buyer.setId(maxId);
		buyers.put(buyer.getId(), buyer);
		return buyer;
	}
	
	/**
	 * Uèitava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Kljuè je korisnièko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	public void loadBuyers(String contextPath) {
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/users.txt");
			in = new BufferedReader(new FileReader(file));
			String line;
			StringTokenizer st;
			while ((line = in.readLine()) != null) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				st = new StringTokenizer(line, ";");
				while (st.hasMoreTokens()) {
					
					int id = Integer.parseInt(st.nextToken().trim());
					
					int buyerType = Integer.parseInt(st.nextToken().trim());
					BuyerTypeEnum[] buyerTypes = BuyerTypeEnum.values();
					BuyerTypeEnum buyerTypefromFile = buyerTypes[buyerType];
					
					double discount = Double.parseDouble(st.nextToken().trim());
					
					int points = Integer.parseInt(st.nextToken().trim());
					
					
						
					buyers.put(id, new BuyerType(id, buyerTypefromFile, discount, points));
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				}
				catch (Exception e) { }
			}
		}
	}
		
		public BuyerType change(BuyerType buyer) {
			buyers.put(buyer.getId(), buyer);
			return buyer;
		}
		
		public BuyerType delete(int id) {
			return buyers.remove(id);
		}
		
		
}
