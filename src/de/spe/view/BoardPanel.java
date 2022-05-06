package de.spe.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class BoardPanel extends JPanel implements Observer{

	
	public BoardPanel() {
		
		//Layout of Board
		this.setLayout(new GridLayout(11,11,5,5));
		this.setBackground(new Color(0xffffc0));
		this.setBorder(BorderFactory.createLineBorder(Color.RED));
		
		//Array with All Game-Panels
		JPanel[] fieldPanelPosition = new JPanel[40];
		
		//Arraya with All Base-Panels
		JPanel[] baseY = new JPanel[4];
		JPanel[] baseG = new JPanel[4];
		JPanel[] baseB = new JPanel[4];
		JPanel[] baseR = new JPanel[4];
		
		//Arrays with All Home-Panels
		JPanel[] homeY = new JPanel[4];
		JPanel[] homeG = new JPanel[4];
		JPanel[] homeB = new JPanel[4];
		JPanel[] homeR = new JPanel[4];
		
		
		//Create Board-Panels
		for (int i = 0; i < 121; i++) {

			if (i == 0 | i == 1 | i == 11 | i == 12 | i == 44 | i >= 56 && i <= 59) {
				//Add Yellow Bases to Array
				if(i==0) {
					this.add(baseY[0] = new YellowField());
					
				}else if(i==1) {
					this.add(baseY[1] = new YellowField());
				
				}else if(i==11) {
					this.add(baseY[2] = new YellowField());
				
				}else if(i==12) {
					this.add(baseY[3] = new YellowField());
				
				//Add Yellow Homes to Array	
				}else if(i==56) {
					this.add(homeY[0] = new YellowField());
				
				}else if(i==57) {
					this.add(homeY[1] = new YellowField());
				
				}else if(i==58) {
					this.add(homeY[2] = new YellowField());
				
				}else if(i==59) {
					this.add(homeY[3] = new YellowField());
				
				}else if(i==44) {
					this.add(fieldPanelPosition[0] = new YellowField());
				}

			} else if (i == 9 | i == 10 | i == 20 | i == 21 | i==6 |5 == i % 11 && i <= 49 && i>5) {
				//Add Green Bases to Array
				if(i==9) {
					this.add(baseG[0] = new GreenField());
					
				}else if(i==10) {
					this.add(baseG[1] = new GreenField());
				
				}else if(i==20) {
					this.add(baseG[2] = new GreenField());
				
				}else if(i==21) {
					this.add(baseG[3] = new GreenField());
					
					//Add Green Homes to Array	
				}else if(i==16) {
					this.add(homeG[0] = new GreenField());
				
				}else if(i==27) {
					this.add(homeG[1] = new GreenField());
				
				}else if(i==38) {
					this.add(homeG[2] = new GreenField());
				
				}else if(i==49) {
					this.add(homeG[3] = new GreenField());
				
				}else if(i==6) {
					this.add(fieldPanelPosition[10] = new GreenField());
				}
				
			} else if (i == 99 | i == 100 | i == 110 | i == 111 | i==114 | 5 == i % 11 && i <= 114 && i>60) {
				//Add Blue Bases to Array
				if(i==99) {
					this.add(baseB[0] = new BlueField());
					
				}else if(i==100) {
					this.add(baseB[1] = new BlueField());
				
				}else if(i==110) {
					this.add(baseB[2] = new BlueField());
				
				}else if(i==111) {
					this.add(baseB[3] = new BlueField());
					
					//Add Blue Homes to Array	
				}else if(i==71) {
					this.add(homeB[0] = new BlueField());
				
				}else if(i==82) {
					this.add(homeB[1] = new BlueField());
				
				}else if(i==93) {
					this.add(homeB[2] = new BlueField());
				
				}else if(i==104) {
					this.add(homeB[3] = new BlueField());
				
				}else if(i==114) {
					this.add(fieldPanelPosition[30] = new BlueField());
				}
				
			} else if (i == 108 | i == 109 | i == 119 | i == 120 | i==76 || i >= 61 && i <= 64) {
				//Add Red Bases to Array
				if(i==108) {
					this.add(baseR[0] = new RedField());
					
				}else if(i==109) {
					this.add(baseR[1] = new RedField());
				
				}else if(i==119) {
					this.add(baseR[2] = new RedField());
				
				}else if(i==120) {
					this.add(baseR[3] = new RedField());
					
					//Add Red Homes to Array	
				}else if(i==61) {
					this.add(homeR[0] = new RedField());
				
				}else if(i==62) {
					this.add(homeR[1] = new RedField());
				
				}else if(i==63) {
					this.add(homeR[2] = new RedField());
				
				}else if(i==64) {
					this.add(homeR[3] = new RedField());
					
				}else if(i==76) {
					this.add(fieldPanelPosition[20] = new RedField());
				}
				
			} else if (i >= 44 && i <= 76 && i!=60  || (4 == i % 11 || 5 == i % 11 || 6 == i % 11) && i!=60) {

				//Add White-Panels to Array fieldPanelPosition
				if(i==45) {
					this.add(fieldPanelPosition[1] = new WhiteField());
					
				}else if(i==46) {
					this.add(fieldPanelPosition[2] = new WhiteField());
					
				}else if(i==47) {
					this.add(fieldPanelPosition[3] = new WhiteField());
					
				}else if(i==48) {
					this.add(fieldPanelPosition[4] = new WhiteField());
					
				}else if(i==37) {
					this.add(fieldPanelPosition[5] = new WhiteField());
					
				}else if(i==26) {
					this.add(fieldPanelPosition[6] = new WhiteField());
					
				}else if(i==15) {
					this.add(fieldPanelPosition[7] = new WhiteField());
					
				}else if(i==4) {
					this.add(fieldPanelPosition[8] = new WhiteField());
					
				}else if(i==5) {
					this.add(fieldPanelPosition[9] = new WhiteField());
					
				}else if(i==17) {
					this.add(fieldPanelPosition[11] = new WhiteField());
					
				}else if(i==28) {
					this.add(fieldPanelPosition[12] = new WhiteField());
					
				}else if(i==39) {
					this.add(fieldPanelPosition[13] = new WhiteField());
					
				}else if(i==50) {
					this.add(fieldPanelPosition[14] = new WhiteField());
					
				}else if(i==51) {
					this.add(fieldPanelPosition[15] = new WhiteField());
					
				}else if(i==52) {
					this.add(fieldPanelPosition[16] = new WhiteField());
					
				}else if(i==53) {
					this.add(fieldPanelPosition[17] = new WhiteField());
					
				}else if(i==54) {
					this.add(fieldPanelPosition[18] = new WhiteField());
					
				}else if(i==65) {
					this.add(fieldPanelPosition[19] = new WhiteField());
					
				}else if(i==75) {
					this.add(fieldPanelPosition[21] = new WhiteField());
					
				}else if(i==74) {
					this.add(fieldPanelPosition[22] = new WhiteField());
					
				}else if(i==73) {
					this.add(fieldPanelPosition[23] = new WhiteField());
					
				}else if(i==72) {
					this.add(fieldPanelPosition[24] = new WhiteField());
					
				}else if(i==83) {
					this.add(fieldPanelPosition[25] = new WhiteField());
					
				}else if(i==94) {
					this.add(fieldPanelPosition[26] = new WhiteField());
					
				}else if(i==105) {
					this.add(fieldPanelPosition[27] = new WhiteField());
					
				}else if(i==116) {
					this.add(fieldPanelPosition[28] = new WhiteField());
					
				}else if(i==115) {
					this.add(fieldPanelPosition[29] = new WhiteField());
					
				}else if(i==103) {
					this.add(fieldPanelPosition[31] = new WhiteField());
					
				}else if(i==92) {
					this.add(fieldPanelPosition[32] = new WhiteField());
					
				}else if(i==81) {
					this.add(fieldPanelPosition[33] = new WhiteField());
					
				}else if(i==70) {
					this.add(fieldPanelPosition[34] = new WhiteField());
					
				}else if(i==69) {
					this.add(fieldPanelPosition[35] = new WhiteField());
					
				}else if(i==68) {
					this.add(fieldPanelPosition[36] = new WhiteField());
					
				}else if(i==67) {
					this.add(fieldPanelPosition[37] = new WhiteField());
					
				}else if(i==66) {
					this.add(fieldPanelPosition[38] = new WhiteField());
					
				}else if(i==55) {
					this.add(fieldPanelPosition[39] = new WhiteField());
					
				}
			
			}

			else {
				InvisiblePanel x = new InvisiblePanel();
				this.add(x);

			}
			
			
			
			
		}
		
		
		
	}

	@Override
	public void update(Observable o, Object arg) {
		this.moveFigure();
		
	}
	
	private void moveFigure() {
		
	}
	
	public void activateDice() {
		
	}
}
