package controllers;




import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import beans.Weather;
import dao.DataAccessInterface;


/*
 * Vien Nguyen
 * CST361, Controller class
 * This class will receive requests and redirect the processing.
 */

@ManagedBean
@ViewScoped
public class FormController {
	
	@Inject 
	DataAccessInterface<Weather> dataAccessWeather;
	public String readWeather(Weather weather) {
		
		List<Weather> weatherList = dataAccessWeather.read(weather);
		FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("weatherList", weatherList);

		
		return "viewWeather.xhtml";
	}

	

}
