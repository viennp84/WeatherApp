package dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;

import beans.Weather;


@Stateless
@Local(DataAccessInterface.class)
@LocalBean
@Alternative
public class WeatherDataAccessService implements DataAccessInterface<Weather> {

	@Override
	public boolean create(Weather t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Weather> read(Weather t) {

		List<Weather> weatherData = new ArrayList<>();
		weatherData.add(new Weather(80, 40, 20, "Scottsdale", "AZ"));
		weatherData.add(new Weather(70, 50, 20, "Scottsdale", "AZ"));
		return weatherData;
	}

	@Override
	public boolean update(Weather t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Weather t) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	


}
