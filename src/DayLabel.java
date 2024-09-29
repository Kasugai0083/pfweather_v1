import java.awt.Image;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DayLabel {

	private ImageIcon icon;
	private JLabel img_l;
	private Map<String,JLabel> ttls;
	private Map<String,JLabel> vals;
	private JPanel panel;
	private String icon_id;
	
	public DayLabel(int i) {
		
		panel = new JPanel();
		
		// 日付を初期化
        Date date = new Date();
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, i);
        
        Date new_date = calendar.getTime();
        
        SimpleDateFormat date_f = new SimpleDateFormat("MM/dd");
		
		ttls = new HashMap<>() {{
			put("day_l", new JLabel(date_f.format(new_date)));
			put("temp_l", new JLabel("気温(℃)"));
			put("humidity_l", new JLabel("湿度(%)"));
			put("wind_l", new JLabel("風速(m/s)"));
		}};
		
		vals = new HashMap<>() {{
			put("temp_v", new JLabel("-"));
			put("humidity_v", new JLabel("-"));
			put("wind_v", new JLabel("-"));
		}};
		
	    icon = new ImageIcon("img/no_image_square.jpg");
		
	    // 画像のリサイズ
	    icon = this.resizeImage(icon);

		img_l = new JLabel(icon);
		
		panel.add(ttls.get("day_l"));
		panel.add(img_l);
		panel.add(ttls.get("temp_l"));
		panel.add(vals.get("temp_v"));
		panel.add(ttls.get("humidity_l"));
		panel.add(vals.get("humidity_v"));
		panel.add(ttls.get("wind_l"));
		panel.add(vals.get("wind_v"));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	}

	public JPanel getDayPanel() {
		return panel;
	}
	public void setWeatherData(String kind, String data) {
		vals.get(kind).setText(data);
	}

	public void setImage(String id) {
		icon_id = id;
		try {
			URL url = new URL("https://openweathermap.org/img/wn/" + icon_id + "@2x.png");
			
			icon = new ImageIcon(url);
	        
	        // 画像のリサイズ処理			
			icon = resizeImage(icon);
			
			img_l.setIcon(icon);

		}catch(MalformedURLException e){
	        icon = new ImageIcon("img/no_image_square.jpg");
	        panel.add(img_l);
		}
	}
	
	private ImageIcon resizeImage(ImageIcon icn) {
		int newWidth = 1280 / 5;
        int newHeight = icn.getIconHeight() * newWidth / icn.getIconWidth();
        
        // 画像のリサイズ処理
        Image img = icn.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        
        icn = new ImageIcon(img);
        
        return icn;
	}
}
