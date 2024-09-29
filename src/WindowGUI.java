import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * Window
 * アプリケーションのウィンドウを管理するクラス
 */

public class WindowGUI extends JFrame implements ItemListener{
	
	private JComboBox box;
	private JLabel select_l;
	private Map<String, JPanel> panel;
	private DayLabel[] days = new DayLabel[5];
	private Container content;
	
	/*
	 * コンストラクタ
	 * 
	 *  title => アプリのタイトルを指定
	 *  w_width => ウィンドウの幅を指定
	 *  w_height => ウィンドウの高さを指定
	 */
	WindowGUI(String title,int w_width, int w_height){
		// タイトルを指定		
		super(title);
		
		// ウィンドウサイズを指定		
		this.setSize(w_width, w_height);
		
		// ウィンドウのリサイズを無効化
		this.setResizable(false);
		
		//ウィンドウをモニターの中央に配置
		this.setLocationRelativeTo(null);
		
		//ウィンドウを閉じたらプログラムを終了
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		// メインのJPanelを初期化
		panel = new HashMap<>(){{
			put("select_p", new JPanel());
			put("main_p", new JPanel());
		}};
		
		// JComboBoxのリスト用配列を初期化、代入
		String[] init_data = {
			"都市を選択してください",
			"東京",
			"ニューヨーク",
			"ロンドン",
			"パリ",
			"シドニー",
			"ベルリン",
			"モスクワ",
			"ソウル",
			"上海",
			"メキシコ"
		};
		box = new JComboBox<String>(init_data);
		
		box.addItemListener(this);
		
		// セレクター用のラベルを初期化
		select_l = new JLabel("都市");
		
		// 都市選択のGUIを配置
		panel.get("select_p").add(select_l);
		panel.get("select_p").add(box);
		panel.get("select_p").setLayout(new BoxLayout(panel.get("select_p"), BoxLayout.Y_AXIS));
		
		// 日付別のGUIを初期化
		for(int i = 0; i < days.length; i++) {
			// インスタンス化
			days[i] = new DayLabel(i);	
			
			// 日付別のGUIを配置
			panel.get("main_p").add(days[i].getDayPanel());
		}
		
		panel.get("main_p").setLayout(new BoxLayout(panel.get("main_p"), BoxLayout.X_AXIS));

		
		content = getContentPane();
		content.add(panel.get("select_p"),BorderLayout.NORTH);
		content.add(panel.get("main_p"),BorderLayout.CENTER);
		setVisible(true);
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
        	switch(e.getItem().toString()) {
        		case "東京": // Tokyo,jp
        			createWeatherData("Tokyo,jp");
        			break;
        		case "ニューヨーク": // New York,us
        			createWeatherData("New%20York,us");
        			break;
        		case "ロンドン": // London,gb
        			createWeatherData("London,gb");	                	
        			break;
        		case "パリ": // Paris,fr
        			createWeatherData("Paris,fr");                	
        			break;
        		case "シドニー": // Sydney,au
        			createWeatherData("Sydney,au");	                	
        			break;
        		case "ベルリン": // Berlin,de
        			createWeatherData("Berlin,de");	                	
        			break;
        		case "モスクワ": // Moscow,ru
        			createWeatherData("Moscow,ru");	                	
        			break;
        		case "ソウル": // Seoul,kr
        			createWeatherData("Seoul,kr");	                	
        			break;
        		case "上海": // Shanghai,cn
        			createWeatherData("Shanghai,cn");	                	
        			break;
        		case "メキシコ": // Mexico City,mx
        			createWeatherData("Mexico%20City,mx");                	
        			break;
            	default:
            		System.out.println("テスト: " + e.getItem());	
            		break;
            }
        }
		
	}
	
	public void createWeatherData(String url) {
		// 気象情報の初期化
		WeatherData weather_data = new WeatherData("https://api.openweathermap.org/data/2.5/forecast?APPID=02711d57554026067d424493a5a72fcb&q=" + url);
		
		for(int i = 0; i < days.length; i++) {
			// IDの入力,画像の再生成
			days[i].setImage(weather_data.getIconID(i));
			
			// 気温の入力
			days[i].setWeatherData("temp_v", weather_data.getTemp(i).toString());
			
			// 湿度の入力
			days[i].setWeatherData("humidity_v", weather_data.getHumidity(i).toString());
			
			// 風速の入力
			days[i].setWeatherData("wind_v", weather_data.getWind(i).toString());
		}
		
		// JComboBoxを閉じる
		box.hidePopup();
		
		// ポップアップの表示
		JOptionPane.showMessageDialog(this,"通信が完了しました。");
	}
	
}
