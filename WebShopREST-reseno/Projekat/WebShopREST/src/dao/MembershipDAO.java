package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
 * <p>Klasa namenjena da uèita korisnike iz fajla i pruža operacije nad njima (poput pretrage).
 * Korisnici se nalaze u fajlu WebContent/users.txt u obliku: <br>
 * firstName;lastName;email;username;password</p>
 * <p><b>NAPOMENA:</b> Lozinke se u praksi <b>nikada</b> ne snimaju u èistu tekstualnom obliku.</p>
 * @author Lazar
 *
 */
public class MembershipDAO {
	
	private static MembershipDAO instance = null;
	private static String contextPath = "";
	
	private Map<Integer, Membership> memberships = new HashMap<>();
	
	
	private MembershipDAO() {
		
	}
	
	/***
	 * @param contextPath Putanja do aplikacije u Tomcatu. Može se pristupiti samo iz servleta.
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
	
	public Membership newMembershipAdded(Membership membership) {
		if(membership.getMembershipType() == MembershipTypeEnum.Day) {
			membership.setExpirationDay(DateHelper.dateToString( membership.getStartDay().plusDays(1)));
		}else if(membership.getMembershipType() == MembershipTypeEnum.Month) {
			membership.setExpirationDay(DateHelper.dateToString( membership.getStartDay().plusMonths(1)));
		}else {
			membership.setExpirationDay(DateHelper.dateToString( membership.getStartDay().plusYears(1)));
		}
		membership.setMembershipStatus(MembershipStatusEnum.Active);
		
		membership = save(membership);
		saveToFile();
		
		return membership;
		
	}
	
	public void checkMemberships() {
		for(Membership membership : memberships.values()) {
			if(membership.getMembershipStatus() == MembershipStatusEnum.Inactive) {
				continue;
			}
			LocalDate trenutno = LocalDate.now();
			
			int kupljenoTermina = 0;
			if(membership.getMembershipType() == MembershipTypeEnum.Day) {
				kupljenoTermina = 1;
			}else if(membership.getMembershipType() == MembershipTypeEnum.Month) {
				kupljenoTermina = 30;
			}else {
				kupljenoTermina = 360;
			}
			if(membership.getExpirationDay().isBefore(trenutno)){
				int iskoristeno = kupljenoTermina - membership.getNumberOfAppointment();
				double brojDobijenih = (membership.getPrice() / 1000) * (iskoristeno);
				double izgubljeni = 0;
				if(iskoristeno <= kupljenoTermina / 3) {
					izgubljeni = (membership.getPrice() / 1000) * (133 * 4);
				}
				int poeni = membership.getBuyer().getBuyerType().getPoints();
				poeni += (int) Math.round(brojDobijenih - izgubljeni);
				membership.getBuyer().getBuyerType().setPoints(poeni);
				membership.setMembershipStatus(MembershipStatusEnum.Inactive);
			}
			
			
		}
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
	 * Uèitava korisnike iz WebContent/users.txt fajla i dodaje ih u mapu {@link #users}.
	 * Kljuè je korisnièko ime korisnika.
	 * @param contextPath Putanja do aplikacije u Tomcatu
	 */
	public void loadMemberships(String contextPath) {
		this.contextPath = contextPath;
		BufferedReader in = null;
		try {
			File file = new File(contextPath + "/Baza/memberships.txt");
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
	
	public void saveToFile() {
		BufferedWriter out = null;
		try {
			File file = new File(contextPath + "/Baza/memberships.txt");
			out = new BufferedWriter(new FileWriter(file));
			String line;
			StringTokenizer st;
			for(Membership membership : memberships.values()) {
				out.write(membership.fileLine() + '\n');
			}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();             
		} finally {
			if (out != null) {
				try {
					out.close();
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