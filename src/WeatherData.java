import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherData {
	
	// 天気予報APIの取得
	private URL url;
	private Vector<String> icon_ids;
	private Vector<Double> temps;
	private Vector<Double> humidities;
	private Vector<Double> winds;
	private JsonNode forecastList;
    final double TEMP_K = -273.15;
	
	public WeatherData(String requestUrl){
		icon_ids = new Vector<>();
		temps = new Vector<>();
		humidities = new Vector<>();
		winds = new Vector<>();
		
		try {
			url = new URL(requestUrl);
			
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			
			int responseCode = connection.getResponseCode();
			
			if(responseCode == HttpURLConnection.HTTP_OK) {
				
				InputStreamReader isr = new InputStreamReader(connection.getInputStream());
				
				BufferedReader br = new BufferedReader(isr);
				
		        ObjectMapper mapper = new ObjectMapper();
		        JsonNode node = mapper.readTree(br);

		        forecastList = node.get("list");

			}else {
				System.out.println("取得失敗");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        for(JsonNode forecast : forecastList) {
        	String forecast_time = forecast.get("dt_txt").asText();		        	
        				        
	        if (forecast_time.endsWith("06:00:00")) {
		        
		        
		        /*	コンソールで出力
		         * 
		         *  String icon_id = forecast.get("weather").get(0).get("icon").asText();
				 *
		         *　Map<String, Double> weather = new HashMap<>(){{
		         *		put("temp",roundUpToFirstDecimalPlace(forecast.get("main").get("temp").asDouble() + TEMP_K));
		         *		put("humidity",roundUpToFirstDecimalPlace(forecast.get("main").get("humidity").asDouble()));
		         *		put("wind",roundUpToFirstDecimalPlace(forecast.get("wind").get("speed").asDouble()));
	        	 *　}};
	        	 * 
		         *	System.out.println("時間：" + forecast_time);
		         *	System.out.println("天気id：" + icon_id);
		         *	System.out.println("気温：" + weather.get("temp"));
		         *	System.out.println("湿度：" + weather.get("humidity") + "%");
		         *	System.out.println("風速(m/s)：" + weather.get("wind"));
		         *	System.out.println("-------------------------------------");
		         *
		         */
		        
		        icon_ids.add(forecast.get("weather").get(0).get("icon").asText());
		        temps.add(roundUpToFirstDecimalPlace(forecast.get("main").get("temp").asDouble() + TEMP_K));
		        humidities.add(roundUpToFirstDecimalPlace(forecast.get("main").get("humidity").asDouble()));
		        winds.add(roundUpToFirstDecimalPlace(forecast.get("wind").get("speed").asDouble()));
	        }
        } 
	}
	
	// 実数値の小数点切り上げ関数
	public static double roundUpToFirstDecimalPlace(double value) {
	    return Math.ceil(value * 10) / 10.0;
	}
	
	public String getIconID(int i){
		return icon_ids.get(i);
	}
	public Double getTemp(int i){
		return temps.get(i);
	}
	public Double getHumidity(int i){
		return humidities.get(i);
	}
	public Double getWind(int i){
		return winds.get(i);
	}
}
