package model;

import java.awt.Color;
import java.awt.Component;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import entities.Empresa;

public class MyTableCellRender extends DefaultTableCellRenderer{
	
	//private RelationTableModel myModel;
	
	public MyTableCellRender(JTable myTable){
		//myModel = model;
		setOpaque(true);
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
	
		if (value == null) return this;
	    Component renderer = super.getTableCellRendererComponent(
	      table, value, isSelected, hasFocus, row, column
	    );
	    //Number myValue = (Number)table.size();
	    System.out.println("VAI: "+table.getValueAt(0, 0));
	    if (table.getValueAt(0, 0).equals("4350")) {
	      renderer.setForeground(Color.red);
	      //renderer.setBackground(Color.red);
	   // table.
	    }
	    else {
	      renderer.setForeground(Color.green);
	    }
	    return this;
	  
		
		/*DefaultTableCellRenderer tableRenderer = new DefaultTableCellRenderer();
		tableRenderer.setBackground(new Color(0,200,0));
		
		DefaultTableCellRenderer render2 = new DefaultTableCellRenderer();
		render2.setBackground(new Color(0,100,255));

		
			table.getColumnModel().getColumn(1).setCellRenderer(tableRenderer);*/
				
			//table.getColumnModel().getColumn(2).setCellRenderer(render2);
			//table.getModel().getValueAt(0,1).setCellRenderer(render2);
			//System.out.println("O q e: "+ table.getColumnModel().getColumn(1));
			
			//setBackground(Color.white);
			//setForeground(Color.red);
			
		//}
		
		/*if (myModel.getValueAt(0, 0).equals("4350")){
		setBackground(Color.white);
		setForeground(Color.yellow);

		}*/
		//if (myModel.getValueAt(1, 1).equals("4265")){
			//setBackground(Color.white);
			//setForeground(Color.red);

			//}
		
	
		
		//setText(value !=null ? value.toString() : "");
		//return this;
		 
	}
} 

