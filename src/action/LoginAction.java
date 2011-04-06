package action;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import model.Booking;
import model.Extra;
import model.Guest;
import model.Room;
import model.RoomFacility;
import model.Structure;
import model.User;
import model.listini.ItemListinoCamera;
import model.listini.ListinoCamera;
import model.listini.Period;
import model.listini.Season;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Actions;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

@ParentPackage(value="default")
public class LoginAction extends ActionSupport implements SessionAware{
	private Map<String, Object> session = null;
	private String email = null;
	private String password;
	
	@Actions(value={
			@Action(value="/login", results={
					@Result(name="input",location="/WEB-INF/jsp/login.jsp"),
					@Result(name="loginSuccess", location="/homeLogged.jsp"),	
					@Result(name="loginError", location="/login.jsp")
			})
			
	})	
	
	public String execute(){
		String ret = null;	
		User user = null;
		Structure structure = null;
		Locale locale = null;
		SimpleDateFormat sdf = null;
		String datePattern = null;
		
		if(this.getEmail().trim().equals("locanda@locanda.it") &&
				this.getPassword().trim().equals("locanda")){
			user = new User();
			
			user.setEmail(this.getEmail());
			structure = this.buildStructure();
			user.setStructure(structure);
			this.getSession().put("user", user);
			locale = this.getLocale();
			sdf = (SimpleDateFormat) DateFormat.getDateInstance(DateFormat.SHORT,locale);
			datePattern = sdf.toPattern();
			this.getSession().put("datePattern", datePattern);
			ret = "loginSuccess";
		}else{
			this.getSession().put("user", null);
			ret = "loginError";
		}		
		return ret;
	}
	

	
	private Structure buildStructure(){
		Structure ret = null;
			
		
		ret = new Structure();
		ret.setName("polaris");
		ret.setEmail("polaris@locanda.it");
		
		this.buildRooms(ret);
		this.buildRoomFacilities(ret);
		this.buildGuests(ret);
		this.buildBookings(ret);
		this.buildExtras(ret);
		this.buildSeasons(ret);
		this.buildListiniCamere(ret);
		return ret;		
	}
	
	private void buildRooms(Structure structure){
		Room aRoom = null;		
			
		aRoom = new Room();
		aRoom.setId(structure.nextKey());
		aRoom.setName("101");
		aRoom.setRoomType("singola");
		aRoom.setPrice(80.0);
		aRoom.setMaxGuests(1);
		structure.addRoom(aRoom);
		
		aRoom = new Room();
		aRoom.setId(structure.nextKey());
		aRoom.setName("201");
		aRoom.setRoomType("doppia");
		aRoom.setPrice(120.0);
		aRoom.setMaxGuests(2);
		structure.addRoom(aRoom);
	}
	
	private void buildRoomFacilities(Structure structure){
		RoomFacility aRoomFacility = null;
		
		aRoomFacility = new RoomFacility();
		aRoomFacility.setId(structure.nextKey());
		aRoomFacility.setName("AAD");
		aRoomFacility.setFileName("AAD.gif");
		structure.addRoomFacility(aRoomFacility);
		
		aRoomFacility = new RoomFacility();
		aRoomFacility.setId(structure.nextKey());
		aRoomFacility.setName("BAR");
		aRoomFacility.setFileName("BAR.gif");
		structure.addRoomFacility(aRoomFacility);
		
		aRoomFacility = new RoomFacility();
		aRoomFacility.setId(structure.nextKey());
		aRoomFacility.setName("PHO");
		aRoomFacility.setFileName("PHO.gif");
		structure.addRoomFacility(aRoomFacility);
		
		aRoomFacility = new RoomFacility();
		aRoomFacility.setId(structure.nextKey());
		aRoomFacility.setName("RAD");
		aRoomFacility.setFileName("RAD.gif");
		structure.addRoomFacility(aRoomFacility);
		
		aRoomFacility = new RoomFacility();
		aRoomFacility.setId(structure.nextKey());
		aRoomFacility.setName("TEL");
		aRoomFacility.setFileName("TEL.gif");
		structure.addRoomFacility(aRoomFacility);
		
	}
	
	private void buildGuests(Structure structure){
		Guest aGuest = null;
		
		aGuest = new Guest();
		aGuest.setId(structure.nextKey());
		aGuest.setFirstName("Paolo");
		aGuest.setLastName("Rossi");
		aGuest.setCountry("Italy");
		aGuest.setAddress("Roma, Via Rossini 82");
		aGuest.setEmail("paolo@rossi.it");
		aGuest.setPhone("06-6789458");
		aGuest.setZipCode("09123");
		aGuest.setNotes("Note su Paolo Rossi");
		structure.addGuest(aGuest);
	}
	
	private void buildBookings(Structure structure){
		Booking aBooking = null;
		Room aRoom = null;
		Guest aGuest = null;
		Date dateIn = null;
		Date dateOut = null;
		
		aBooking = new Booking();
		aRoom = structure.findRoomByName("101");
		aGuest = structure.getGuests().get(0);
		aBooking.setGuest(aGuest);
		aBooking.setRoom(aRoom);
		dateIn = new Date(System.currentTimeMillis());
		dateOut = new Date(System.currentTimeMillis() + 3*24*3600*1000);
		aBooking.setDateIn(dateIn);
		aBooking.setDateOut(dateOut);
		aBooking.setId(structure.nextKey());
		aBooking.setNrGuests(1);
		aBooking.setSubtotal(50.0);
		structure.addBooking(aBooking);
		
	}
	
	private void buildExtras(Structure structure){
		Booking aBooking = null;
		Extra anExtra = null;
		
		anExtra = new Extra();
		aBooking = structure.getBookings().get(0);
		anExtra.setId(structure.nextKey());
		anExtra.setName("Breakfast");
		anExtra.setPrice(10.0);
		anExtra.setResourcePriceType("per Room");
		anExtra.setTimePriceType("per Night");
		aBooking.addExtra(anExtra);
		structure.addExtra(anExtra);
		
		anExtra = new Extra();
		aBooking = structure.getBookings().get(0);
		anExtra.setId(structure.nextKey());
		anExtra.setName("Parking");
		anExtra.setPrice(15.0);
		anExtra.setResourcePriceType("per Room");
		anExtra.setTimePriceType("per Night");
		aBooking.addExtra(anExtra);
		structure.addExtra(anExtra);
	}
	
	private void buildSeasons(Structure structure){
		Season aSeason = null;
		Period aPeriod = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");		
		
		//Bassa Stagione
		aSeason = new Season();
		aSeason.setId(structure.nextKey());
		aSeason.setName("Bassa Stagione");
		aSeason.setYear(2011);
		
		aPeriod = new Period();
		aPeriod.setId(structure.nextKey());		
		try {
			aPeriod.setStartDate(sdf.parse("01/01/2011"));
			aPeriod.setEndDate(sdf.parse("30/04/2011"));
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		aSeason.addPeriod(aPeriod);
		
		aPeriod = new Period();
		aPeriod.setId(structure.nextKey());		
		try {
			aPeriod.setStartDate(sdf.parse("01/09/2011"));
			aPeriod.setEndDate(sdf.parse("31/12/2011"));
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		aSeason.addPeriod(aPeriod);		
		structure.addSeason(aSeason);
		
		//Alta Stagione
		aSeason = new Season();
		aSeason.setId(structure.nextKey());
		aSeason.setName("Alta Stagione");
		aSeason.setYear(2011);
		
		aPeriod = new Period();
		aPeriod.setId(structure.nextKey());		
		try {
			aPeriod.setStartDate(sdf.parse("01/05/2011"));
			aPeriod.setEndDate(sdf.parse("31/08/2011"));
		} catch (ParseException e) {
			e.printStackTrace();
		}		
		aSeason.addPeriod(aPeriod);		
		structure.addSeason(aSeason);
	}
	
	private void buildListiniCamere(Structure structure){
		ListinoCamera listinoCamera = null;
		ItemListinoCamera itemListinoCamera = null;
		Double[] prices = null;
		
		//Listino Camera Singola Bassa Stagione
		listinoCamera =	new ListinoCamera();
		listinoCamera.setId(structure.nextKey());
		listinoCamera.setRoomType("singola");
		listinoCamera.setSeason(structure.findSeasonByName("Bassa Stagione"));
		itemListinoCamera = new ItemListinoCamera();
		itemListinoCamera.setId(structure.nextKey());
		itemListinoCamera.setNumGuests(1);
		prices = new Double[7];
		prices[0] = 50.0;//lun
		prices[1] = 50.0;//mar
		prices[2] = 50.0;//mer
		prices[3] = 50.0;//gio
		prices[4] = 50.0;//ven
		prices[5] = 50.0;//sab
		prices[6] = 50.0;//dom
		itemListinoCamera.setPrices(prices);
		listinoCamera.addItem(itemListinoCamera);
		
		structure.addListinoCamera(listinoCamera);
		
		//Listino Camera Singola Alta Stagione
		listinoCamera =	new ListinoCamera();
		listinoCamera.setId(structure.nextKey());
		listinoCamera.setRoomType("singola");
		listinoCamera.setSeason(structure.findSeasonByName("Alta Stagione"));
		itemListinoCamera = new ItemListinoCamera();
		itemListinoCamera.setId(structure.nextKey());
		itemListinoCamera.setNumGuests(1);
		prices = new Double[7];
		prices[0] = 80.0;//lun
		prices[1] = 80.0;//mar
		prices[2] = 80.0;//mer
		prices[3] = 80.0;//gio
		prices[4] = 80.0;//ven
		prices[5] = 80.0;//sab
		prices[6] = 80.0;//dom
		itemListinoCamera.setPrices(prices);
		listinoCamera.addItem(itemListinoCamera);
		
		structure.addListinoCamera(listinoCamera);
		
		//Listino Camera Doppia Bassa Stagione
		listinoCamera =	new ListinoCamera();
		listinoCamera.setId(structure.nextKey());
		listinoCamera.setRoomType("doppia");
		listinoCamera.setSeason(structure.findSeasonByName("Bassa Stagione"));
		
		itemListinoCamera = new ItemListinoCamera();
		itemListinoCamera.setId(structure.nextKey());
		itemListinoCamera.setNumGuests(1);
		prices = new Double[7];
		prices[0] = 80.0;//lun
		prices[1] = 80.0;//mar
		prices[2] = 80.0;//mer
		prices[3] = 80.0;//gio
		prices[4] = 80.0;//ven
		prices[5] = 80.0;//sab
		prices[6] = 80.0;//dom
		itemListinoCamera.setPrices(prices);
		listinoCamera.addItem(itemListinoCamera);
		
		itemListinoCamera = new ItemListinoCamera();
		itemListinoCamera.setId(structure.nextKey());
		itemListinoCamera.setNumGuests(2);
		prices = new Double[7];
		prices[0] = 100.0;//lun
		prices[1] = 100.0;//mar
		prices[2] = 100.0;//mer
		prices[3] = 100.0;//gio
		prices[4] = 100.0;//ven
		prices[5] = 100.0;//sab
		prices[6] = 100.0;//dom
		itemListinoCamera.setPrices(prices);
		listinoCamera.addItem(itemListinoCamera);
		
		structure.addListinoCamera(listinoCamera);
		
		//Listino Camera Doppia Alta Stagione
		listinoCamera =	new ListinoCamera();
		listinoCamera.setId(structure.nextKey());
		listinoCamera.setRoomType("doppia");
		listinoCamera.setSeason(structure.findSeasonByName("Alta Stagione"));
		
		itemListinoCamera = new ItemListinoCamera();
		itemListinoCamera.setId(structure.nextKey());
		itemListinoCamera.setNumGuests(1);
		prices = new Double[7];
		prices[0] = 90.0;//lun
		prices[1] = 90.0;//mar
		prices[2] = 90.0;//mer
		prices[3] = 90.0;//gio
		prices[4] = 90.0;//ven
		prices[5] = 90.0;//sab
		prices[6] = 90.0;//dom
		itemListinoCamera.setPrices(prices);
		listinoCamera.addItem(itemListinoCamera);
		
		itemListinoCamera = new ItemListinoCamera();
		itemListinoCamera.setId(structure.nextKey());
		itemListinoCamera.setNumGuests(2);
		prices = new Double[7];
		prices[0] = 130.0;//lun
		prices[1] = 130.0;//mar
		prices[2] = 130.0;//mer
		prices[3] = 130.0;//gio
		prices[4] = 130.0;//ven
		prices[5] = 130.0;//sab
		prices[6] = 130.0;//dom
		itemListinoCamera.setPrices(prices);
		listinoCamera.addItem(itemListinoCamera);	
		
		structure.addListinoCamera(listinoCamera);				
	}
	
	public Map<String, Object> getSession() {
		return session;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
		
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
