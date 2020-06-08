package beans;




import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.annotation.XmlRootElement;

/*
 * Vien Nguyen
 * CST361
 * This class is a User model.
 */
@ManagedBean
@ViewScoped
@XmlRootElement(name = "weather")
public class Weather {
	
	int temp;
	int humidity;
	int windSpeed;
	String city;
	String state;
	
	public Weather() {
		
	}
	
	public Weather(int temp, int humidity, int precipitation, String city, String state) {
		this.temp = temp;
		this.humidity = humidity;
		this.windSpeed = precipitation;
		this.city = city;
		this.state = state;
	}

	public int getTemp() {
		return temp;
	}

	public void setTemp(int temp) {
		this.temp = temp;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public int getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(int windSpeed) {
		this.windSpeed = windSpeed;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}


}
