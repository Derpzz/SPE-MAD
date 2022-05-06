package de.spe.view;

import java.awt.Color;
import java.awt.GridLayout;

import de.spe.control.Observable;
import de.spe.control.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

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

			//Add Yellow Bases to Array
			switch (i) {
				case 0:
					this.add(baseY[0] = new YellowField());
				break;
				case 1:
					this.add(baseY[1] = new YellowField());
				break;
				case 11:
					this.add(baseY[2] = new YellowField());
				break;
				case 12:
					this.add(baseY[3] = new YellowField());
				break;
				//Add yellow home
				case 56:
					this.add(homeY[0] = new YellowField());
				break;
				case 57:
					this.add(homeY[1] = new YellowField());
				break;
				case 58:
					this.add(homeY[2] = new YellowField());
				break;
				case 59:
					this.add(homeY[3] = new YellowField());
				break;
				case 44:
					this.add(fieldPanelPosition[0] = new YellowField());
				break;
				//Add Green Bases to Array
				case 9:
					this.add(baseG[0] = new GreenField());
				break;
				case 10:
					this.add(baseG[1] = new GreenField());
				break;
				case 20:
					this.add(baseG[2] = new GreenField());
				break;
				case 21:
					this.add(baseG[3] = new GreenField());
				break;
				//Add Green Homes to Array	
				case 16:
					this.add(homeG[0] = new GreenField());
				break;
				case 27:
					this.add(homeG[1] = new GreenField());
				break;
				case 38:
					this.add(homeG[2] = new GreenField());
				break;
				case 49:
					this.add(homeG[3] = new GreenField());
				break;
				case 6:
					this.add(fieldPanelPosition[10] = new GreenField());
				break;
				//Add Blue Bases to Array
				case 99:
					this.add(baseB[0] = new BlueField());
				break;
				case 100:
					this.add(baseB[1] = new BlueField());
				break;
				case 110:
					this.add(baseB[2] = new BlueField());
				break;
				case 111:
					this.add(baseB[3] = new BlueField());
				break;
				//Add Blue Homes to Array	
				case 71:
					this.add(homeB[0] = new BlueField());
				break;
				case 82:
					this.add(homeB[1] = new BlueField());
				break;
				case 93:
					this.add(homeB[2] = new BlueField());
				break;
				case 104:
					this.add(homeB[3] = new BlueField());
				break;
				case 114:
					this.add(fieldPanelPosition[30] = new BlueField());
				break;
				//Add Red Bases to Array
				case 108:
					this.add(baseR[0] = new RedField());
				break;
				case 109:
					this.add(baseR[1] = new RedField());
				break;
				case 119:
					this.add(baseR[2] = new RedField());
				break;
				case 120:
					this.add(baseR[3] = new RedField());
				break;
				//Add Red Homes to Array	
				case 61:
					this.add(homeR[0] = new RedField());
				break;
				case 62:
					this.add(homeR[1] = new RedField());
				break;
				case 63:
					this.add(homeR[2] = new RedField());
				break;
				case 64:
					this.add(homeR[3] = new RedField());
				break;
				case 76:
					this.add(fieldPanelPosition[20] = new RedField());
				break;
				case 45:
					this.add(fieldPanelPosition[1] = new WhiteField());
				break;
				case 46:
					this.add(fieldPanelPosition[2] = new WhiteField());
				break;
				case 47:
					this.add(fieldPanelPosition[3] = new WhiteField());
				break;
				case 48:
					this.add(fieldPanelPosition[4] = new WhiteField());
				break;
				case 37:
					this.add(fieldPanelPosition[5] = new WhiteField());
				break;
				case 26:
					this.add(fieldPanelPosition[6] = new WhiteField());
				break;
				case 15:
					this.add(fieldPanelPosition[7] = new WhiteField());
				break;
				case 4:
					this.add(fieldPanelPosition[8] = new WhiteField());
				break;
				case 5:
					this.add(fieldPanelPosition[9] = new WhiteField());
				break;
				case 17:
					this.add(fieldPanelPosition[11] = new WhiteField());
				break;
				case 28:
					this.add(fieldPanelPosition[12] = new WhiteField());
				break;
				case 39:
					this.add(fieldPanelPosition[13] = new WhiteField());
				break;
				case 50:
					this.add(fieldPanelPosition[14] = new WhiteField());
				break;
				case 51:
					this.add(fieldPanelPosition[15] = new WhiteField());
				break;
				case 52:
					this.add(fieldPanelPosition[16] = new WhiteField());
				break;
				case 53:
					this.add(fieldPanelPosition[17] = new WhiteField());
				break;
				case 54:
					this.add(fieldPanelPosition[18] = new WhiteField());
				break;
				case 65:
					this.add(fieldPanelPosition[19] = new WhiteField());
				break;
				case 75:
					this.add(fieldPanelPosition[21] = new WhiteField());
				break;
				case 74:
					this.add(fieldPanelPosition[22] = new WhiteField());
				break;
				case 73:
					this.add(fieldPanelPosition[23] = new WhiteField());
				break;
				case 72:
					this.add(fieldPanelPosition[24] = new WhiteField());
				break;
				case 83:
					this.add(fieldPanelPosition[25] = new WhiteField());
				break;
				case 94:
					this.add(fieldPanelPosition[26] = new WhiteField());
				break;
				case 105:
					this.add(fieldPanelPosition[27] = new WhiteField());
				break;
				case 116:
					this.add(fieldPanelPosition[28] = new WhiteField());
				break;
				case 115:
					this.add(fieldPanelPosition[29] = new WhiteField());
				break;
				case 103:
					this.add(fieldPanelPosition[31] = new WhiteField());
				break;
				case 92:
					this.add(fieldPanelPosition[32] = new WhiteField());
				break;
				case 81:
					this.add(fieldPanelPosition[33] = new WhiteField());
				break;
				case 70:
					this.add(fieldPanelPosition[34] = new WhiteField());
				break;
				case 69:
					this.add(fieldPanelPosition[35] = new WhiteField());
				break;
				case 68:
					this.add(fieldPanelPosition[36] = new WhiteField());
				break;
				case 67:
					this.add(fieldPanelPosition[37] = new WhiteField());
				break;
				case 66:
					this.add(fieldPanelPosition[38] = new WhiteField());
				break;
				case 55:
					this.add(fieldPanelPosition[39] = new WhiteField());
				break;
				default:
					InvisiblePanel x = new InvisiblePanel();
					this.add(x);
				break;
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
