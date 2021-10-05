package main;

import java.awt.EventQueue;
import java.sql.SQLException;

import dao.DAOMiembro;
import vista.Vista;

public class Inicio {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Vista frame = new Vista();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}
}
