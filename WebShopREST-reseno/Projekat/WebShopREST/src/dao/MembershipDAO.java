package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import beans.Enums.MembershipTypeEnum;
import beans.Enums.MembershipStatusEnum;
import java.time.LocalDate;
import beans.Enums.DateHelper;
import beans.User;

import beans.Membership;

/***
 * <p>Klasa namenjena da u�ita korisnike iz fajla i pru�a operacije nad njima (poput pretrage).
 * Korisnici se nalaze u fajlu WebContent/users.txt u obliku: <br>
 * firstName;lastName;email;username;password</p>
 * <p><b>NAPOMENA:</b> Lozinke se u praksi <b>nikada</b> ne snimaju u �istu tekstualnom obliku.</p>
 * @author Lazar
 *
 */
public class MembershipDAO {
	
	private static MembershipDAO instance = null;
	
	private Map<Integer, Membership> memberships = new HashMap<>();
	
	
	private MembershipDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Mo�e se pristupiti samo iz servleta.
	 */
	private MembershipDAO(String contextPath) {
		loadMemberships(contextPath);
	}
	
	
	public static MembershipDAO getInstance() {
		if(instance == null) {
			instance = new MembershipDAO();
		}
		
		return instance;
	}
	
	public Membership find(int id) {
		return memberships.get(id);
	}
	
	public Collection<Membership> findAll() {
		return memberships.values();
	}
	
	public Membership save(Membership membership) {
		Integer maxId = -1;
		for (int id : memberships.keySet()) {
			int idNum = id;
			if (idNum > maxId) {
				maxId = idNum;
			}
		}
		maxId++;
		membership.setId(maxId);
		memberships.put(membership.getId(), membership);
		return membership;
	}
	
	/**
	 * U�itava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Klju� je korisni�ko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	private void loadMemberships(String contextPath) {
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
					
					String MembershipId = st.nextToken().trim();
					
					int membershipType = Integer.parseInt(st.nextToken().trim());
					MembershipTypeEnum[] membershipTypes = MembershipTypeEnum.values();
					MembershipTypeEnum typefromFile = membershipTypes[membershipType];
					
					LocalDate paymentDay = DateHelper.stringToDate(st.nextToken().trim());
					LocalDate startDay = DateHelper.stringToDate(st.nextToken().trim());
					LocalDate expirationDay = DateHelper.stringToDate(st.nextToken().trim());
					
					double price = Double.parseDouble(st.nextToken().trim());
					
					User buyer = new User(Integer.parseInt(st.nextToken().trim()));
					
					
					int membershipStatus = Integer.parseInt(st.nextToken().trim());
					MembershipStatusEnum[] membershipStatuses = MembershipStatusEnum.values();
					MembershipStatusEnum statusfromFile = membershipStatuses[membershipStatus];
					
					int numberOfAppointment = Integer.parseInt(st.nextToken().trim());
					
	
					memberships.put(id, new Membership(id, MembershipId, typefromFile, paymentDay, startDay, expirationDay, price, buyer, statusfromFile,
						numberOfAppointment));
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
		
		public Membership change(Membership membership) {
			memberships.put(membership.getId(), membership);
			return membership;
		}
		
		public Membership delete(int id) {
			return memberships.remove(id);
		}
		
		
}